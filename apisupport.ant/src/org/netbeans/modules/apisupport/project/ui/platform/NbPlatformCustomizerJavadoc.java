/*
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

package org.netbeans.modules.apisupport.project.ui.platform;

import java.awt.EventQueue;
import java.io.File;
import java.net.URL;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import org.netbeans.modules.apisupport.project.ui.ModuleUISettings;
import org.netbeans.modules.apisupport.project.ui.platform.NbPlatformCustomizerSources.ListListener;
import org.netbeans.modules.apisupport.project.universe.JavadocRootsProvider;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 * Represents <em>Javadoc</em> tab in the NetBeans platforms customizer.
 *
 * @author Martin Krauskopf
 */
public final class NbPlatformCustomizerJavadoc extends JPanel {

    private static final RequestProcessor RP = new RequestProcessor(NbPlatformCustomizerJavadoc.class);
    
    private JavadocRootsProvider jrp;
    private PlatformComponentFactory.JavadocRootsModel model;
    private final ListListener listListener;
    
    /** Creates new form NbPlatformCustomizerModules */
    public NbPlatformCustomizerJavadoc() {
        initComponents();
        initAccessibility();
        this.listListener = new ListListener() {
            void listChanged() {
                updateEnabled();
            }
        };
        updateEnabled();
    }
    
    public void addNotify() {
        super.addNotify();
        javadocList.addListSelectionListener(listListener);
        javadocList.getModel().addListDataListener(listListener);
    }
    
    public void removeNotify() {
        javadocList.removeListSelectionListener(listListener);
        javadocList.getModel().removeListDataListener(listListener);
        super.removeNotify();
    }
    
    private void updateEnabled() {
        // update buttons enability appropriately
        removeButton.setEnabled(javadocList.getModel().getSize() > 0 && javadocList.getSelectedIndex() != -1);
        moveUpButton.setEnabled(javadocList.getSelectionModel().getMinSelectionIndex() > 0);
        RP.post(new Runnable() {
            @Override public void run() {
                final int rootCount = jrp != null ? jrp.getJavadocRoots().length : 0;
                EventQueue.invokeLater(new Runnable() {
                    @Override public void run() {
                        moveDownButton.setEnabled(javadocList.getSelectionModel().getMaxSelectionIndex() < rootCount - 1);
                    }
                });
            }
        });
    }
    
    public void setJavadocRootsProvider(JavadocRootsProvider jrp) {
        this.jrp = jrp;
        this.model = new PlatformComponentFactory.JavadocRootsModel(jrp);
        javadocList.setModel(model);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        javadocLabel = new javax.swing.JLabel();
        javadocSP = new javax.swing.JScrollPane();
        javadocList = new javax.swing.JList();
        buttonPanel = new javax.swing.JPanel();
        addFolderButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        moveUpButton = new javax.swing.JButton();
        moveDownButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
        javadocLabel.setLabelFor(javadocList);
        org.openide.awt.Mnemonics.setLocalizedText(javadocLabel, org.openide.util.NbBundle.getMessage(NbPlatformCustomizerJavadoc.class, "LBL_PlatformJavadoc"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(javadocLabel, gridBagConstraints);

        javadocList.setCellRenderer(PlatformComponentFactory.getURLListRenderer());
        javadocSP.setViewportView(javadocList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(javadocSP, gridBagConstraints);

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 12, 0, 0));
        org.openide.awt.Mnemonics.setLocalizedText(addFolderButton, org.openide.util.NbBundle.getMessage(NbPlatformCustomizerJavadoc.class, "CTL_AddZipOrFolder"));
        addFolderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addZipFolder(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        buttonPanel.add(addFolderButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(removeButton, org.openide.util.NbBundle.getMessage(NbPlatformCustomizerJavadoc.class, "CTL_Remove"));
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFolder(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 12, 0);
        buttonPanel.add(removeButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(moveUpButton, org.openide.util.NbBundle.getMessage(NbPlatformCustomizerJavadoc.class, "CTL_MoveUp"));
        moveUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUp(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
        buttonPanel.add(moveUpButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(moveDownButton, org.openide.util.NbBundle.getMessage(NbPlatformCustomizerJavadoc.class, "CTL_MoveDown"));
        moveDownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDown(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        buttonPanel.add(moveDownButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        add(buttonPanel, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents
    
    private void moveDown(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDown
        int[] selIndices = javadocList.getSelectedIndices();
        model.moveJavadocRootsDown(selIndices);
        for (int i = 0; i < selIndices.length; i++) {
            selIndices[i] = ++selIndices[i];
        }
        javadocList.setSelectedIndices(selIndices);
    }//GEN-LAST:event_moveDown
    
    private void moveUp(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUp
        int[] selIndices = javadocList.getSelectedIndices();
        model.moveJavadocRootsUp(selIndices);
        for (int i = 0; i < selIndices.length; i++) {
            selIndices[i] = --selIndices[i];
        }
        javadocList.setSelectedIndices(selIndices);
    }//GEN-LAST:event_moveUp
    
    private void removeFolder(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFolder
        Object[] selVals = javadocList.getSelectedValues();
        int toSelect = javadocList.getSelectedIndex() - 1;
        URL[] selURLs = new URL[selVals.length];
        System.arraycopy(selVals, 0, selURLs, 0, selVals.length);
        model.removeJavadocRoots(selURLs);
        javadocList.setSelectedIndex(toSelect);
    }//GEN-LAST:event_removeFolder
    
    private void addZipFolder(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addZipFolder
        JFileChooser chooser = new JFileChooser(ModuleUISettings.getDefault().getLastUsedNbPlatformLocation());
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setFileFilter(new FileFilter() {
            public boolean accept(File f)  {
                return f.isDirectory() ||
                        f.getName().toLowerCase(Locale.US).endsWith(".jar") || // NOI18N
                        f.getName().toLowerCase(Locale.US).endsWith(".zip"); // NOI18N
            }
            public String getDescription() {
                return getMessage("CTL_JavadocTab");
            }
        });
        int ret = chooser.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File javadocRoot = FileUtil.normalizeFile(chooser.getSelectedFile());
            URL newUrl = FileUtil.urlForArchiveOrDir(javadocRoot);
            if (model.containsRoot(newUrl)) {
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                    getMessage("MSG_ExistingJavadocRoot")));
            } else {
                ModuleUISettings.getDefault().setLastUsedNbPlatformLocation(javadocRoot.getParentFile().getAbsolutePath());
                model.addJavadocRoot(newUrl);
                javadocList.setSelectedValue(newUrl, true);
            }
        }
        // XXX support adding Javadoc URL too (see java.j2seplatform)
    }//GEN-LAST:event_addZipFolder
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFolderButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel javadocLabel;
    private javax.swing.JList javadocList;
    private javax.swing.JScrollPane javadocSP;
    private javax.swing.JButton moveDownButton;
    private javax.swing.JButton moveUpButton;
    private javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables
    
    private void initAccessibility() {
        addFolderButton.getAccessibleContext().setAccessibleDescription(getMessage("ACS_CTL_addFolderButton"));
        javadocList.getAccessibleContext().setAccessibleDescription(getMessage("ACS_CTL_javadocList"));
        moveDownButton.getAccessibleContext().setAccessibleDescription(getMessage("ACS_CTL_moveDownButton"));
        moveUpButton.getAccessibleContext().setAccessibleDescription(getMessage("ACS_CTL_moveUpButton"));
        removeButton.getAccessibleContext().setAccessibleDescription(getMessage("ACS_CTL_removeButton"));
    }
    
    private String getMessage(String key) {
        return NbBundle.getMessage(NbPlatformCustomizerJavadoc.class, key);
    }
    
}