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

import java.util.List;
import java.util.Map;

import org.apereo.portlet.soffit.model.v1_0.UserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Responsible for issuing and parsing Bearer tokens.
 *
 * @author drewwills
 */
@Service
public class UserDetailsService {

    public static final String JWT_ISSUER = "Soffit";

    private enum Keys {

        GROUPS("groups"),

        ATTRIBUTES("attributes");

        /*
         * Implementation
         */

        private final String name;

        private Keys(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    @Value("${org.apereo.portlet.soffit.model.v1_0.UserDetailsService.signatureKey}")
    private String signatureKey;

    public UserDetails createUserDetails(String username, Map<String,List<String>> attributes, List<String> groups) {

System.out.println(" ## signatureKey="+signatureKey);

        final Claims claims = Jwts.claims()
                .setIssuer(JWT_ISSUER)
                .setSubject(username);
        claims.put(Keys.ATTRIBUTES.toString(), attributes);
        claims.put(Keys.GROUPS.toString(), groups);

        final String bearerToken = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, signatureKey)
                .compact();

        // TODO:  Encryption

        return new UserDetails(bearerToken, username, attributes, groups);

    }

    public UserDetails parseBearerToken(String bearerToken) {

        final Jws<Claims> claims = Jwts.parser()
                .setSigningKey(signatureKey)
                .parseClaimsJws(bearerToken);

        final String username = claims.getBody().getSubject();
        final Map<String,List<String>> attributes = (Map<String, List<String>>) claims.getBody().get(Keys.ATTRIBUTES.toString());
        final List<String> groups = (List<String>) claims.getBody().get(Keys.GROUPS.toString());

        return new UserDetails(bearerToken, username, attributes, groups);

    }

}
