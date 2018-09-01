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
package org.netbeans.modules.groovy.grailsproject.actions;

import java.io.File;
import java.util.Enumeration;
import static org.netbeans.modules.groovy.grailsproject.actions.Bundle.*;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle.Messages;

@Messages("CTL_GotoDomainClassAction=Go to Grails Domain class")
@ActionID(id = "org.netbeans.modules.groovy.grailsproject.actions.GotoDomainClassAction", category = "Groovy")
@ActionRegistration(lazy = false, displayName = "#CTL_GotoDomainClassAction")
@ActionReferences(value = {
    @ActionReference(path = "Menu/GoTo", position = 525),
    @ActionReference(path = "Editors/text/x-groovy/Popup/goto", position = 125),
    @ActionReference(path = "Editors/text/x-gsp/Popup/goto", position = 125)
})
/*
 * @author Martin Janicek
 */
public final class GotoDomainClassAction extends GotoBaseAction {

    public GotoDomainClassAction() {
        super(CTL_GotoDomainClassAction()); // NOI18N
    }

    @Override
    protected FileObject getTargetFO(String fileName, FileObject sourceFO) {
        if (isGspFO(sourceFO)) {
            String parentName = sourceFO.getParent().getName();
            fileName = parentName.substring(0, 1).toUpperCase() + parentName.substring(1);

            File file = new File(getExtendedBaseDir(sourceFO, "domain"));
            FileObject domainDirFO = FileUtil.toFileObject(FileUtil.normalizeFile(file));
            Enumeration<? extends FileObject> children = domainDirFO.getChildren(true);

            while (children.hasMoreElements()) {
                FileObject child = children.nextElement();
                if (fileName.equals(child.getName())) {
                    fileName = findPackagePath(child) + File.separator + fileName;
                    break;
                }
            }
        } else {
            fileName = findPackagePath(sourceFO) + File.separator + fileName;
        }
        
        File targetFile = new File(getTargetFilePath(fileName, sourceFO));
        FileObject targetFO = FileUtil.toFileObject(FileUtil.normalizeFile(targetFile));
        // do not navigate to itself
        if (sourceFO.equals(targetFO)) {
            return null;
        }

        return targetFO;
    }

    @Override
    protected String getTargetFilePath(String filename, FileObject sourceFO) {
        // this needs to be done if we are moving from controller
        if (filename.endsWith("Controller")) {
            filename = filename.replaceAll("Controller$", "");
        }
        return getExtendedBaseDir(sourceFO, "domain") + filename + ".groovy"; //NOI18N
    }
}
