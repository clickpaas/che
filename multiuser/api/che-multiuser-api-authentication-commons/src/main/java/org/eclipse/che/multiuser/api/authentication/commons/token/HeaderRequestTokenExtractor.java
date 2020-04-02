/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.multiuser.api.authentication.commons.token;

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.HttpHeaders;

/** Extract sso token from request headers. */
public class HeaderRequestTokenExtractor implements RequestTokenExtractor {

  private static final Pattern splitPattern = Pattern.compile("\\s+");

  @Override
  public String getToken(HttpServletRequest req) {
    if (req.getHeader(HttpHeaders.AUTHORIZATION) == null) {
      return null;
    }
    if (req.getHeader(HttpHeaders.AUTHORIZATION).toLowerCase().startsWith("bearer")) {
      String[] parts = splitPattern.split(req.getHeader(HttpHeaders.AUTHORIZATION));
      if (parts.length != 2) {
        throw new BadRequestException("Invalid authorization header format.");
      }
      return parts[1];
    } else {
      return req.getHeader(HttpHeaders.AUTHORIZATION);
    }
  }
}
