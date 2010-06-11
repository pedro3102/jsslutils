/*-----------------------------------------------------------------------

  This file is part of the jSSLutils library.
  
Copyright (c) 2008, The University of Manchester, United Kingdom.
All rights reserved.

Redistribution and use in source and binary forms, with or without 
modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, 
      this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright 
      notice, this list of conditions and the following disclaimer in the 
      documentation and/or other materials provided with the distribution.
 * Neither the name of the The University of Manchester nor the names of 
      its contributors may be used to endorse or promote products derived 
      from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
POSSIBILITY OF SUCH DAMAGE.

  Author........: Bruno Harbulot

-----------------------------------------------------------------------*/

package org.jsslutils.sslcontext.test;

import javax.net.ssl.SSLServerSocket;


import org.jsslutils.sslcontext.X509SSLContextFactory;
import org.jsslutils.sslcontext.test.MiniSslClientServer;
import org.jsslutils.sslcontext.trustmanagers.TrustAllClientsWrappingTrustManager;


/**
 * Mini server that should accept any client certificate.
 * 
 * @author Bruno Harbulot
 */
public class TrustAllClientsServerTest extends MiniSslClientServer {
	public void run() throws Exception {
		X509SSLContextFactory sslContextFactory = new X509SSLContextFactory(
				getServerCertKeyStore(), MiniSslClientServer.KEYSTORE_PASSWORD,
				getCaKeyStore());
		sslContextFactory
				.setTrustManagerWrapper(new TrustAllClientsWrappingTrustManager.Wrapper());
		sslContextFactory.lockTrustManagerWrapper();
		SSLServerSocket socket = prepareServerSocket(sslContextFactory
				.buildSSLContext());
		System.out
				.println("Server listening on port: " + socket.getLocalPort());
		setServerRequestNumber(0);
		runServer(socket);
	}

	public static void main(String[] args) throws Exception {
		TrustAllClientsServerTest test = new TrustAllClientsServerTest();
		test.run();
	}
}