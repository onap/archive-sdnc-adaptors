/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
 *             reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.openecomp.sdnc.sli.aai;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openecomp.sdnc.sli.aai.data.AAIDatum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.openecomp.aai.inventory.v8.Pserver;

public class PServerRequest extends AAIRequest {

	// tenant (1602)
	public static final String PSERVER_PATH			= "org.openecomp.sdnc.sli.aai.path.pserver";
	public static final String PSERVER_QUERY_PATH	= "org.openecomp.sdnc.sli.aai.path.pserver.query";
	
	private final String pserver_path;
	private final String pserver_query_path;
	
	public static final String HOSTNAME = "hostname";
	public static final String PSERVER_HOSTNAME = "pserver.hostname";


	public PServerRequest() {
		pserver_path = configProperties.getProperty(PSERVER_PATH);
		pserver_query_path = configProperties.getProperty(PSERVER_QUERY_PATH);
	}

	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {

		String request_url = target_uri + pserver_path;
		String encoded_vnf = null;
		
		String hostname = null;
		if(requestProperties.containsKey(HOSTNAME)) {
			hostname = requestProperties.getProperty(HOSTNAME);
			encoded_vnf = encodeQuery(hostname);
			request_url = request_url.replace("{hostname}", encoded_vnf) ;

		} else if(requestProperties.containsKey(PSERVER_HOSTNAME)) {
			hostname = requestProperties.getProperty(PSERVER_HOSTNAME);
			encoded_vnf = encodeQuery(hostname);
			request_url = request_url.replace("{hostname}", encoded_vnf) ;

		} else {
			LOG.error("Required key "+PSERVER_HOSTNAME+" is misssing.");
		}
		
		if(resourceVersion != null) {
			request_url = request_url +"?resource-version="+resourceVersion;
		}
		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());
		aaiService.LOGwriteDateTrace("hostname", hostname);
		
		return http_req_url;
	}
	
	@Override
	public URL getRequestQueryUrl(String method) throws UnsupportedEncodingException, MalformedURLException {
		return this.getRequestUrl(method, null);
	}


	@Override
	public String toJSONString() {
		ObjectMapper mapper = getObjectMapper();
		Pserver vnfc = (Pserver)requestDatum;
		String json_text = null;
		try {
			json_text = mapper.writeValueAsString(vnfc);
		} catch (JsonProcessingException exc) {
			handleException(this, exc);
			return null;
		}
		return json_text;
	}

	@Override
	public String[] getArgsList() {
		String[] args = {HOSTNAME, PSERVER_HOSTNAME};
		return args;
	}
	
	@Override
	public Class<? extends AAIDatum> getModelClass() {
		return Pserver.class;
	}
}
