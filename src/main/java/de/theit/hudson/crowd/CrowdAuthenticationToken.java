/*
 * @(#)CrowdAuthenticationToken.java
 * 
 * The MIT License
 * 
 * Copyright (C)2011 Thorsten Heit.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.theit.hudson.crowd;

import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.providers.AbstractAuthenticationToken;

/**
 * This class represents an authentication token that is created after a user
 * was successfully authenticated against the remote Crowd server.
 * 
 * @author <a href="mailto:theit@gmx.de">Thorsten Heit (theit@gmx.de)</a>
 * @since 07.09.2011
 * @version $Id$
 */
public class CrowdAuthenticationToken extends AbstractAuthenticationToken {
	/** For serialization. */
	private static final long serialVersionUID = 7685110934682676618L;

	/** The SSO token. */
	private String credentials;

	/** The authenticated Crowd user. */
	private String principal;

	/** The Crowd SSO token after a successful login. */
	private String ssoToken;

	/** The display name of the user. */
	private String displayName;

	/**
	 * Creates a new authorization token.
	 * 
	 * @param pPrincipal
	 *            The name of the authenticated Crowd user. May not be
	 *            <code>null</code>.
	 * @param pCredentials
	 *            The credentials. Normally the users password. May only be
	 *            <code>null</code> when the SSO token is given.
	 * @param authorities
	 *            The list of granted authorities for the user. May not be
	 *            <code>null</code>.
	 * @param pSsoToken
	 *            The Crowd SSO token. May be <code>null</code> if the token is
	 *            not (yet) available.
	 * @param pDisplayName
	 *            The display name of the user. May be <code>null</code>.
	 */
	public CrowdAuthenticationToken(String pPrincipal, String pCredentials,
			List<GrantedAuthority> authorities, String pSsoToken,
			String pDisplayName) {
		super(authorities.toArray(new GrantedAuthority[authorities.size()]));
		this.principal = pPrincipal;
		this.credentials = pCredentials;
		this.ssoToken = pSsoToken;
		this.displayName = pDisplayName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.acegisecurity.Authentication#getCredentials()
	 */
	@Override
	public String getCredentials() {
		return this.credentials;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.acegisecurity.Authentication#getPrincipal()
	 */
	@Override
	public String getPrincipal() {
		return this.principal;
	}

	/**
	 * Returns the SSO token.
	 * 
	 * @return The SSO token. <code>null</code> if the token is not (yet)
	 *         available.
	 */
	public String getSSOToken() {
		return this.ssoToken;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.acegisecurity.providers.AbstractAuthenticationToken#getName()
	 */
	@Override
	public String getName() {
		if (null == this.displayName) {
			return super.getName();
		}
		// append the user Id stored in getName() at the end of the display name
		return this.displayName + " (" + super.getName() + ')';
	}
}
