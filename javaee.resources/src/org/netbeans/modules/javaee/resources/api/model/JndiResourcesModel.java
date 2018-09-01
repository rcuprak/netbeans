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
package org.netbeans.modules.javaee.resources.api.model;

import java.util.List;
import org.netbeans.modules.javaee.resources.api.JmsDestination;
import org.netbeans.modules.javaee.resources.api.JndiResource;

/**
 * JNDI Resources specific model.
 *
 * @author Martin Fousek <marfous@netbeans.org>
 */
public final class JndiResourcesModel {

    private final JndiResourcesAbstractModel modelImplementation;

    JndiResourcesModel(JndiResourcesAbstractModel modelImplementation) {
        this.modelImplementation = modelImplementation;
    }

    /**
     * Gets all resources available in the model.
     * @return list of resources
     */
    public List<JndiResource> getResources() {
        return modelImplementation.getProvider().getResources();
    }

    /**
     * Gets all JMS destination resources of the model.
     * @return list of JMS destinations
     */
    public List<JmsDestination> getJmsDestinations() {
        return modelImplementation.getProvider().getJmsDestinations();
    }

}
