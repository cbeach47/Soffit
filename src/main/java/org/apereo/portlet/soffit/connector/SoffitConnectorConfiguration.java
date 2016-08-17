/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
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

package org.apereo.portlet.soffit.connector;

import org.apereo.portlet.soffit.service.BearerService;
import org.apereo.portlet.soffit.service.DefinitionService;
import org.apereo.portlet.soffit.service.PortalRequestService;
import org.apereo.portlet.soffit.service.PreferencesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Defines beans for the Soffit Connector, which runs inside uPortal
 *
 * @author drewwills
 */
@Configuration
public class SoffitConnectorConfiguration {

    /*
     * Services
     *
     * These beans know how to produce & consume the JWTs that represent the
     * data model of Soffit.
     */

    @Bean
    public BearerService bearerService() {
        return new BearerService();
    }

    @Bean
    public PortalRequestService portalRequestService() {
        return new PortalRequestService();
    }

    @Bean
    public PreferencesService preferencesService() {
        return new PreferencesService();
    }

    @Bean
    public DefinitionService definitionService() {
        return new DefinitionService();
    }

    /*
     * Header Providers
     *
     * These beans know how to produce HTTP headers based on JWTs.
     * (NOTE:  there are more of these in uPortal.)
     */

    @Bean
    public PortalRequestHeaderProvider portalRequestHeaderProvider() {
        return new PortalRequestHeaderProvider();
    }

    @Bean
    public PreferencesHeaderProvider preferencesHeaderProvider() {
        return new PreferencesHeaderProvider();
    }

}
