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

package org.elastos.did.backend;

import org.elastos.did.DID;
import org.elastos.did.DIDURL;
import org.elastos.did.exception.MalformedDIDException;
import org.elastos.did.exception.MalformedDIDURLException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CredentialResolveRevocation extends ResolveRequest<CredentialResolveRevocation, CredentialResolveRevocation.Parameters> {
	protected static final String PARAMETER_ID = "id";
	protected static final String PARAMETER_SIGNER = "signer";

	protected static final String METHOD_NAME = "resolverevocation";

	protected static class Parameters {
		@JsonProperty(PARAMETER_ID)
		private DIDURL id;

		@JsonProperty(PARAMETER_SIGNER)
		private DID signer;

		@JsonCreator
		public Parameters(@JsonProperty(value = PARAMETER_ID, required = true)DIDURL id,
				@JsonProperty(value = PARAMETER_SIGNER, required = true)DID signer) {
			this.signer = signer;
		}
	}

	@JsonCreator
	public CredentialResolveRevocation(@JsonProperty(value = ID)String requestId) {
		super(requestId, METHOD_NAME);
	}

	public void setParameters(DIDURL id, DID signer) {
		setParameters(new Parameters(id, signer));
	}

	public void setParameters(String id, String signer) {
		try {
			setParameters(new DIDURL(id), new DID(signer));
		} catch (MalformedDIDException | MalformedDIDURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public DIDURL getId() {
		return getParameters().id;
	}

	public DID getSigner() {
		return getParameters().signer;
	}
}