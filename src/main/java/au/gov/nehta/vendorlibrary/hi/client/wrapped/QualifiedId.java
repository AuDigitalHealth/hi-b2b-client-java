/*
 * Copyright 2011 NEHTA
 * Copyright 2021-2026 ADHA (Australian Digital Health Agency)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package au.gov.nehta.vendorlibrary.hi.client.wrapped;

public class QualifiedId {

    private final String qualifier;
    private final String id;

    public QualifiedId(String qualifier, String id) {
        this.id = id;
        this.qualifier = qualifier;
    }

    public au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId as3Type() {
        au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId
                qi = new au.net.electronichealth.ns.hi.xsd.common.qualifiedidentifier._3.QualifiedId();

        qi.setId(this.id);
        qi.setQualifier(this.qualifier);
        return qi;
    }
}
