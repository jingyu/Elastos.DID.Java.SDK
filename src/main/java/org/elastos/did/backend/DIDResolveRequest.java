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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DIDResolveRequest extends ResolveRequest<DIDResolveRequest, DIDResolveRequest.Parameters> {
	protected static final String PARAMETER_DID = "did";
	protected static final String PARAMETER_ALL = "all";

	public static final String METHOD_NAME = "resolvedid";

	protected static class Parameters {
		@JsonProperty(PARAMETER_DID)
		private DID did;

		@JsonProperty(PARAMETER_ALL)
		@JsonInclude(Include.NON_NULL)
		private Boolean all;

		public Parameters(DID did, Boolean all) {
			this.did = did;
			this.all = all;
		}

		@JsonCreator
		public Parameters(@JsonProperty(value = PARAMETER_DID, required = true)DID did) {
			this(did, null);
		}

		@Override
		public int hashCode() {
			int hash = did.hashCode();

			if (all != null)
				hash += all.hashCode();

			return hash;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Parameters))
				return false;

			Parameters p = (Parameters)o;

			if (!did.equals(p.did))
				return false;

			if ((all == null || p.all == null) && (all != p.all))
				return false;

			return all.equals(p.all);
		}

	}

	@JsonCreator
	public DIDResolveRequest(@JsonProperty(value = ID)String requestId) {
		super(requestId, METHOD_NAME);
	}

	public void setParameters(DID did, boolean all) {
		setParameters(new Parameters(did, all));
	}

	public void setParameters(String did, boolean all) {
		setParameters(DID.valueOf(did), all);
	}

	public DID getDid() {
		return getParameters().did;
	}

	public boolean isResolveAll() {
		return getParameters().all == null ? false : getParameters().all;
	}

	@Override
	public String toString() {
		DIDURL.Builder builder = new DIDURL.Builder(getParameters().did);
		if (getParameters().all != null)
			builder.setQueryParameter(PARAMETER_ALL, String.valueOf(getParameters().all));

		return builder.build().toString();
	}
}