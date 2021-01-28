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

import java.util.Date;

import org.elastos.did.DIDObject;
import org.elastos.did.exception.MalformedIDChainRequestException;
import org.elastos.did.exception.MalformedIDChainTransactionException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ IDTransaction.TXID,
	IDTransaction.TIMESTAMP,
	IDTransaction.OPERATION })
public abstract class IDTransaction<T, R extends IDChainRequest<R>> extends DIDObject<T> {
	protected final static String TXID = "txid";
	protected final static String TIMESTAMP = "timestamp";
	protected final static String OPERATION = "operation";

	@JsonProperty(TXID)
	private String txId;
	@JsonProperty(TIMESTAMP)
	private Date timestamp;
	@JsonProperty(OPERATION)
	private R request;

	@JsonCreator
	protected IDTransaction() {}

	/**
	 * Constructs the DIDTransaction with the given value.
	 *
	 * @param txid the transaction id string
	 * @param timestamp the time stamp
	 * @param request the IDChainRequest content
	 */
	protected IDTransaction(String txid, Date timestamp, R request) {
		this.txId = txid;
		this.timestamp = timestamp;
		this.request = request;
	}

	public String getTransactionId() {
		return txId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Get request object of transaction.
	 *
	 * @return the IDRequest object
	 */
	public R getRequest() {
		return request;
	}

	@Override
	protected void sanitize() throws MalformedIDChainTransactionException {
		if (txId == null || txId.isEmpty())
			throw new MalformedIDChainTransactionException("Missing txid");

		if (timestamp == null)
			throw new MalformedIDChainTransactionException("Missing timestamp");

		if (request == null)
			throw new MalformedIDChainTransactionException("Missing request");

		try {
			request.sanitizeHelper();
		} catch (MalformedIDChainRequestException e) {
			throw new MalformedIDChainTransactionException("Invalid request", e);
		}
	}
}