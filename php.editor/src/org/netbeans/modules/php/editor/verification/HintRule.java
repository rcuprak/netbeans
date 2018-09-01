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

package org.netbeans.modules.php.editor.verification;

import java.util.Collections;
import java.util.Set;
import java.util.prefs.Preferences;
import javax.swing.JComponent;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.csl.api.Hint;
import org.netbeans.modules.csl.api.HintSeverity;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.api.RuleContext;

/**
 *
 * @author Ondrej Brejla <obrejla@netbeans.org>
 */
public abstract class HintRule implements CaretSensitiveRule, InvokableRule<Hint> {
    private int caretOffset = -1;
    private OffsetRange lineBounds;

    @Override
    public void setCaretOffset(int caretOffset) {
        this.caretOffset = caretOffset;
        this.lineBounds = null;
    }

    protected boolean showHint(OffsetRange hintOffsetRange, BaseDocument doc) {
        OffsetRange currentLineBounds = getLineBounds(doc);
        return currentLineBounds == OffsetRange.NONE || hintOffsetRange.overlaps(currentLineBounds);
    }

    private OffsetRange getLineBounds(BaseDocument doc) {
        if (lineBounds == null) {
            lineBounds = VerificationUtils.createLineBounds(caretOffset, doc);
        }
        return lineBounds;
    }

    @Override
    public Set<? extends Object> getKinds() {
        return Collections.singleton(PHPHintsProvider.DEFAULT_HINTS);
    }

    @Override
    public boolean getDefaultEnabled() {
        return true;
    }

    @Override
    public JComponent getCustomizer(Preferences node) {
        return null;
    }

    @Override
    public boolean appliesTo(RuleContext context) {
        return context instanceof PHPRuleContext;
    }

    @Override
    public boolean showInTasklist() {
        return false;
    }

    @Override
    public HintSeverity getDefaultSeverity() {
        return HintSeverity.WARNING;
    }
}
