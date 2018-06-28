/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package org.netbeans.modules.web.struts.ui;

import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import org.netbeans.modules.web.api.webmodule.ExtenderController;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

public class StrutsConfigurationPanelVisual extends javax.swing.JPanel implements HelpCtx.Provider, DocumentListener {

    private StrutsConfigurationPanel panel;
    private final boolean enableComponents;

    /** Creates new form StrutsConfigurationPanelVisual */
    public StrutsConfigurationPanelVisual(StrutsConfigurationPanel panel, boolean customizer) {
        this.panel = panel;
        initComponents();
        
        jTextFieldAppResource.getDocument().addDocumentListener(this);
        jCheckBoxWAR.setVisible(false);
        if (customizer) {
            jCheckBoxTLD.setVisible(false);
            //jCheckBoxWAR.setVisible(false);
            enableComponents = false;
        }        
        else {
            jCheckBoxTLD.setVisible(true);
            //jCheckBoxWAR.setVisible(true);
            enableComponents = true;
        }
        enableComponents(enableComponents);
        
        ((JTextComponent)jComboBoxURLPattern.getEditor().getEditorComponent()).getDocument().addDocumentListener(this);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabelServletName = new javax.swing.JLabel();
        jTextFieldServletName = new javax.swing.JTextField();
        jLabelURLPattern = new javax.swing.JLabel();
        jComboBoxURLPattern = new javax.swing.JComboBox();
        jLabelAppResource = new javax.swing.JLabel();
        jTextFieldAppResource = new javax.swing.JTextField();
        jCheckBoxTLD = new javax.swing.JCheckBox();
        jCheckBoxWAR = new javax.swing.JCheckBox();

        setLayout(new java.awt.GridBagLayout());

        jLabelServletName.setDisplayedMnemonic(org.openide.util.NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "MNE_ConfigPanel_ActionServletName_Mnemonic").charAt(0));
        jLabelServletName.setLabelFor(jTextFieldServletName);
        jLabelServletName.setText(org.openide.util.NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "LBL_ConfigPanel_ActionServletName")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 12);
        add(jLabelServletName, gridBagConstraints);

        jTextFieldServletName.setEditable(false);
        jTextFieldServletName.setText("action");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(jTextFieldServletName, gridBagConstraints);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/netbeans/modules/web/struts/ui/Bundle"); // NOI18N
        jTextFieldServletName.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_jTextFieldServletName")); // NOI18N

        jLabelURLPattern.setDisplayedMnemonic(org.openide.util.NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "MNE_ConfigPanel_URLPattern_Mnemonic").charAt(0));
        jLabelURLPattern.setLabelFor(jComboBoxURLPattern);
        jLabelURLPattern.setText(org.openide.util.NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "LBL_ConfigPanel_URLPattern")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 12);
        add(jLabelURLPattern, gridBagConstraints);

        jComboBoxURLPattern.setEditable(true);
        jComboBoxURLPattern.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "*.do", "/do/*" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(jComboBoxURLPattern, gridBagConstraints);
        jComboBoxURLPattern.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_jComboBoxURLPattern")); // NOI18N

        jLabelAppResource.setDisplayedMnemonic(org.openide.util.NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "MNE_ConfigPanel_ApplicationResource_Mnemonic").charAt(0));
        jLabelAppResource.setLabelFor(jTextFieldAppResource);
        jLabelAppResource.setText(org.openide.util.NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "LBL_ConfigPanel_ApplicationResource")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 12);
        add(jLabelAppResource, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.01;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 12, 0);
        add(jTextFieldAppResource, gridBagConstraints);
        jTextFieldAppResource.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_jTextFieldAppResource")); // NOI18N

        jCheckBoxTLD.setMnemonic(org.openide.util.NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "MNE_ConfigPanel_InstallStrutsTLDs_Mnemonic").charAt(0));
        jCheckBoxTLD.setText(org.openide.util.NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "LBL_ConfigPanel_InstallStrutsTLDs")); // NOI18N
        jCheckBoxTLD.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxTLD.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(jCheckBoxTLD, gridBagConstraints);
        jCheckBoxTLD.getAccessibleContext().setAccessibleDescription(bundle.getString("ACSD_jCheckBoxTLD")); // NOI18N

        jCheckBoxWAR.setMnemonic(org.openide.util.NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "MNE_ConfigPanel_PackageStrutsJars_Mnemonic").charAt(0));
        jCheckBoxWAR.setSelected(true);
        jCheckBoxWAR.setText(org.openide.util.NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "LBL_ConfigPanel_PackageStrutsJars")); // NOI18N
        jCheckBoxWAR.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxWAR.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        add(jCheckBoxWAR, gridBagConstraints);
        jCheckBoxWAR.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "ACSD_jCheckBoxWAR")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBoxTLD;
    private javax.swing.JCheckBox jCheckBoxWAR;
    private javax.swing.JComboBox jComboBoxURLPattern;
    private javax.swing.JLabel jLabelAppResource;
    private javax.swing.JLabel jLabelServletName;
    private javax.swing.JLabel jLabelURLPattern;
    private javax.swing.JTextField jTextFieldAppResource;
    private javax.swing.JTextField jTextFieldServletName;
    // End of variables declaration//GEN-END:variables
    
    boolean valid() {
        // #119806
        if (!enableComponents) {
            return true;
        }
        ExtenderController controller = panel.getController();
        String urlPattern = (String)jComboBoxURLPattern.getEditor().getItem();
        if (urlPattern == null || urlPattern.trim().length() == 0){
            controller.setErrorMessage(NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "MSG_URLPatternIsEmpty"));
            return false;
        }
        if (!isPatternValid(urlPattern)){
            controller.setErrorMessage(NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "MSG_URLPatternIsNotValid"));
            return false;
        }
        
        String appResource = getAppResource();
        if (appResource == null || appResource.trim().length() == 0) {
            controller.setErrorMessage(NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "MSG_ApplicationResourceIsEmpty"));
            return false;
        }
        if (!isResourceValid(appResource)){
            controller.setErrorMessage(NbBundle.getMessage(StrutsConfigurationPanelVisual.class, "MSG_ApplicationResourceNotValid"));
            return false;
        }
        controller.setErrorMessage(null);
        return true;
    }

    private static final char[] INVALID_PATTERN_CHARS = {'<', '\\', '\"', '%', '&', '+', '?', ';'}; // NOI18N

    private boolean isPatternValid(String pattern){
        for (char c : INVALID_PATTERN_CHARS) {
            if (pattern.indexOf(c) != -1) {
                return false;
            }
        }
        
        if (pattern.startsWith("*.")){
            String p = pattern.substring(2);
            if (p.indexOf('.') == -1 && p.indexOf('*') == -1 
                    && p.indexOf('/') == -1 && !p.trim().equals(""))
                return true;
        }
        // pattern = "/.../*", where ... can't be empty.
        if ((pattern.length() > 3) && pattern.endsWith("/*") && pattern.startsWith("/"))
            return true;
        return false;
    }
    
    private static final char[] INVALID_RESOURCE_CHARS = {'<', '>', '*', '\\', ':', '\"', '/', '%', '|', '?'}; // NOI18N
    
    private boolean isResourceValid(String resource){
        for (char c : INVALID_RESOURCE_CHARS) {
            if (resource.indexOf(c) != -1) {
                return false;
            }
        }
        
        return true;
    }

    void enableComponents(boolean enable) {
        jComboBoxURLPattern.setEnabled(enable);
        jTextFieldAppResource.setEnabled(enable);
        jTextFieldServletName.setEnabled(enable);
        jCheckBoxTLD.setEnabled(enable);
        jCheckBoxWAR.setEnabled(enable);
        jLabelAppResource.setEnabled(enable);
        jLabelServletName.setEnabled(enable);
        jLabelURLPattern.setEnabled(enable);
    }
    
    public String getURLPattern(){
        return (String)jComboBoxURLPattern.getSelectedItem();
    }
    
    public void setURLPattern(String pattern){
        jComboBoxURLPattern.setSelectedItem(pattern);
    }
    
    public String getServletName(){
        return jTextFieldServletName.getText();
    }
    
    public void setServletName(String name){
        jTextFieldServletName.setText(name);
    }
    
    public String getAppResource(){
        return jTextFieldAppResource.getText();
    }
    
    public void setAppResource(String resource){
        jTextFieldAppResource.setText(resource);
    }
    
    public boolean addTLDs(){
        return jCheckBoxTLD.isSelected();
    }
    
    public boolean packageWars(){
        return jCheckBoxWAR.isSelected();
    }
    /** Help context where to find more about the paste type action.
     * @return the help context for this action
     */
    public HelpCtx getHelpCtx() {
        return new HelpCtx(StrutsConfigurationPanelVisual.class);
    }

    public void removeUpdate(javax.swing.event.DocumentEvent e) {
        panel.fireChangeEvent();
    }

    public void insertUpdate(javax.swing.event.DocumentEvent e) {
        panel.fireChangeEvent();
    }

    public void changedUpdate(javax.swing.event.DocumentEvent e) {
        panel.fireChangeEvent();
    }

}