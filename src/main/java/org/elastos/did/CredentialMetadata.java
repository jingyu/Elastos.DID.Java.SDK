/*
 * Copyright (c) 2019 Elastos Foundation
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.elastos.did;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The interface for Credential's meta data(include alias name, last modified time for Credential
 * and user's extra element).
 */
/**
 * The class defines the implement of Credential Meta data.
 */
public class CredentialMetadata extends AbstractMetadata implements Cloneable {
	private final static String ALIAS = RESERVED_PREFIX + "alias";
	private final static String PUBLISHED = RESERVED_PREFIX + "published";
	private final static String REVOKED = RESERVED_PREFIX + "revoked";

	private final static SimpleDateFormat dateFormat =
			new SimpleDateFormat(Constants.DATE_FORMAT);

	/**
	 * Construct the empty CredentialMetadataImpl.
	 */
	protected CredentialMetadata() {
		this(null);
	}

	/**
	 * Construct the CredentialMetadataImpl with the given store.
	 *
	 * @param store the specified DIDStore
	 */
	protected CredentialMetadata(DIDStore store) {
		super(store);
	}

	/**
	 * Set alias for credential.
	 *
	 * @param alias alias string
	 */
	public void setAlias(String alias) {
		put(ALIAS, alias);
	}

	/**
	 * Get alias from credential.
	 *
	 * @return alias string
	 */
	public String getAlias() {
		return (String)get(ALIAS);
	}

	/**
	 * Set published time into CredentialMetadata.
	 *
	 * @param timestamp the time published
	 */
	protected void setPublished(Date timestamp) {
		put(PUBLISHED, dateFormat.format(timestamp));
	}

	/**
	 * Get the time of the lastest declare transaction.
	 *
	 * @return the published time
	 */
	public Date getPublished() {
		try {
			String published = (String)get(PUBLISHED);
			return published == null ? null : dateFormat.parse(published);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * Set revoked status into CredentialMetadata.
	 *
	 * @param revoked the revocation status
	 */
	protected void setRevoked(boolean revoked) {
		put(REVOKED, revoked);
	}

	/**
	 * the DID revoked status.
	 *
	 * @return the returned value is true if the did is revoked.
	 *         the returned value is false if the did is not revoked.
	 */
	public boolean isRevoked( ) {
		Boolean v = (Boolean)get(REVOKED);
		return v == null ? false : v;
	}

    /**
     * Returns a shallow copy of this instance: the keys and values themselves
     * are not cloned.
     *
     * @return a shallow copy of this object
     */
	@Override
	public CredentialMetadata clone() {
		try {
			return (CredentialMetadata)super.clone();
		} catch (CloneNotSupportedException ignore) {
			ignore.printStackTrace();
			return null;
		}
    }
}

