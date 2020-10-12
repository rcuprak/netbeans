/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.lsp.client.bindings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.TextDocumentSyncOptions;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.editor.BaseDocumentEvent;
import org.netbeans.lib.editor.util.swing.DocumentUtilities;
import org.netbeans.modules.editor.*;
import org.netbeans.modules.lsp.client.LSPBindings;
import org.netbeans.modules.lsp.client.Utils;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.modules.OnStart;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;

/** TODO: follow the synchronization options
 *  TODO: close
 *
 * @author lahvac
 */
public class TextDocumentSyncServerCapabilityHandler {

    private final RequestProcessor WORKER = new RequestProcessor(TextDocumentSyncServerCapabilityHandler.class.getName(), 1, false, false);
    private final Set<JTextComponent> lastOpened = Collections.newSetFromMap(new IdentityHashMap<>());

    private void handleChange() {
        assert SwingUtilities.isEventDispatchThread();
        Set<JTextComponent> currentOpened = Collections.newSetFromMap(new IdentityHashMap<>());
        currentOpened.addAll(EditorRegistry.componentList());
        Set<JTextComponent> newOpened = Collections.newSetFromMap(new IdentityHashMap<>());
        newOpened.addAll(currentOpened);
        newOpened.removeAll(lastOpened);
        Set<JTextComponent> newClosed = Collections.newSetFromMap(new IdentityHashMap<>());
        newClosed.addAll(lastOpened);
        newClosed.removeAll(currentOpened);
        lastOpened.clear();
        lastOpened.addAll(currentOpened);

        for (JTextComponent opened : newOpened) {
            FileObject file = NbEditorUtilities.getFileObject(opened.getDocument());

            if (file == null)
                continue; //ignore

            Document doc = opened.getDocument();

            ensureOpenedInServer(opened);

            doc.addDocumentListener(new DocumentListener() { //XXX: listener
                int version; //XXX: proper versioning!
                @Override
                public void insertUpdate(DocumentEvent e) {
                    try {
                        fireEvent(e.getOffset(), e.getDocument().getText(e.getOffset(), e.getLength()), "");
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    fireEvent(e.getOffset(), "", ((BaseDocumentEvent) e).getText());
                }
                private void fireEvent(int start, String newText, String oldText) {
                    try {
                        Position startPos = Utils.createPosition(doc, start);
                        Position endPos = Utils.computeEndPositionForRemovedText(startPos, oldText);
                        TextDocumentContentChangeEvent[] event = new TextDocumentContentChangeEvent[1];
                        event[0] = new TextDocumentContentChangeEvent(new Range(startPos,
                                                                             endPos),
                                                                   oldText.length(),
                                                                   newText);

                        boolean typingModification = DocumentUtilities.isTypingModification(doc);
                        long documentVersion = DocumentUtilities.getDocumentVersion(doc);

                        WORKER.post(() -> {
                            LSPBindings server = LSPBindings.getBindings(file);

                            if (server == null)
                                return ; //ignore

                            TextDocumentSyncKind syncKind = TextDocumentSyncKind.None;
                            Either<TextDocumentSyncKind, TextDocumentSyncOptions> sync = server.getInitResult().getCapabilities().getTextDocumentSync();
                            if (sync != null) {
                                if (sync.isLeft()) {
                                    syncKind = sync.getLeft();
                                } else {
                                    TextDocumentSyncKind change = sync.getRight().getChange();
                                    if (change != null)
                                        syncKind = change;
                                }
                            }
                            switch (syncKind) {
                                case None:
                                    return ;
                                case Full:
                                    doc.render(() -> {
                                        try {
                                            event[0] = new TextDocumentContentChangeEvent(doc.getText(0, doc.getLength()));
                                        } catch (BadLocationException ex) {
                                            Exceptions.printStackTrace(ex);
                                            event[0] = new TextDocumentContentChangeEvent("");
                                        }
                                    });
                                    break;
                                case Incremental:
                                    //event already filled
                                    break;
                            }

                            VersionedTextDocumentIdentifier di = new VersionedTextDocumentIdentifier(++version);
                            di.setUri(org.netbeans.modules.lsp.client.Utils.toURI(file));
                            DidChangeTextDocumentParams params = new DidChangeTextDocumentParams(di, Arrays.asList(event));

                            server.getTextDocumentService().didChange(params);

                            if (typingModification && oldText.isEmpty() && event.length == 1) {
                                if (newText.equals("}") || newText.equals("\n")) {
                                    List<TextEdit> edits = new ArrayList<>();
                                    doc.render(() -> {
                                        if (documentVersion != DocumentUtilities.getDocumentVersion(doc))
                                            return ;
                                        edits.addAll(Utils.computeDefaultOnTypeIndent(doc, start, startPos, newText));
                                    });
                                    NbDocument.runAtomic((StyledDocument) doc, () -> {
                                        if (documentVersion == DocumentUtilities.getDocumentVersion(doc)) {
                                            Utils.applyEditsNoLock(doc, edits);
                                        }
                                    });
                                }
                            }
                            server.scheduleBackgroundTasks(file);
                        });
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {}
            });
        }
    }

    private void ensureOpenedInServer(JTextComponent opened) {
        FileObject file = NbEditorUtilities.getFileObject(opened.getDocument());

        if (file == null)
            return; //ignore

        Document doc = opened.getDocument();
        WORKER.post(() -> {
            LSPBindings server = LSPBindings.getBindings(file);

            if (server == null)
                return ; //ignore

            if (!server.getOpenedFiles().add(file)) {
                //already opened:
                return ;
            }

            doc.putProperty(HyperlinkProviderImpl.class, Boolean.TRUE);

            String uri = Utils.toURI(file);
            String[] text = new String[1];

            doc.render(() -> {
                try {
                    text[0] = doc.getText(0, doc.getLength());
                } catch (BadLocationException ex) {
                    Exceptions.printStackTrace(ex);
                    text[0] = "";
                }
            });

            TextDocumentItem textDocumentItem = new TextDocumentItem(uri,
                                                                     FileUtil.getMIMEType(file),
                                                                     0,
                                                                     text[0]);

            server.getTextDocumentService().didOpen(new DidOpenTextDocumentParams(textDocumentItem));
            if (opened.getClientProperty(MarkOccurrences.class) == null) {
                MarkOccurrences mo = new MarkOccurrences(opened);
                LSPBindings.addBackgroundTask(file, mo);
                opened.putClientProperty(MarkOccurrences.class, mo);
            }
            server.scheduleBackgroundTasks(file);
        });
    }

    public static void refreshOpenedFilesInServers() {
        SwingUtilities.invokeLater(() -> {
            assert SwingUtilities.isEventDispatchThread();
            for (JTextComponent c : EditorRegistry.componentList()) {
                h.ensureOpenedInServer(c);
            }
        });
    }

    private static final TextDocumentSyncServerCapabilityHandler h = new TextDocumentSyncServerCapabilityHandler();
    @OnStart
    public static class Init implements Runnable {

        @Override
        public void run() {
            EditorRegistry.addPropertyChangeListener(evt -> h.handleChange());
            SwingUtilities.invokeLater(() -> h.handleChange());
        }
        
    }
}
