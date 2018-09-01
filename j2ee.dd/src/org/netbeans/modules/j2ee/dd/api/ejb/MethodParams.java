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

package org.netbeans.modules.j2ee.dd.api.ejb;

//
// This interface has all of the bean info accessor methods.
//

public interface MethodParams extends org.netbeans.modules.j2ee.dd.api.common.CommonDDBean {

        public static final String METHOD_PARAM = "MethodParam";	// NOI18N

        public void setMethodParam(int index, String value);

        public String getMethodParam(int index);

        public void setMethodParam(String[] value);

        public String[] getMethodParam();

	public int sizeMethodParam();

	public int addMethodParam(String value);

	public int removeMethodParam(String value);

}

