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

import org.openecomp.aai.inventory.v8.L3Network;

public class L3NetworkRequest extends AAIRequest {

	public static final String L3_NETWORK_PATH		  = "org.openecomp.sdnc.sli.aai.path.l3.network";
	public static final String L3_NETWORK_QUERY_PATH  = "org.openecomp.sdnc.sli.aai.path.l3network.query.name";
	
	private final String l3_network_path;
	private final String l3_network_query_path;
	
	public static final String NETWORK_ID	= "l3-network.network-id";
	public static final String NETWORK_NAME	= "l3-network.network-name";

	
	public L3NetworkRequest() {
		l3_network_path = configProperties.getProperty(L3_NETWORK_PATH);
		l3_network_query_path = configProperties.getProperty(L3_NETWORK_QUERY_PATH);
	}

	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {
		if(requestProperties.containsKey(NETWORK_NAME)) {
			return getRequestQueryUrl(method);
		}

		String request_url = target_uri+l3_network_path;

		String encoded_vnf = null; 

		if(requestProperties.containsKey(NETWORK_ID)) {
			encoded_vnf = encodeQuery(requestProperties.getProperty(NETWORK_ID));
			request_url = request_url.replace("{network-id}", encoded_vnf) ;
			
		}
		
		if(resourceVersion != null) {
			request_url = request_url +"?resource-version="+resourceVersion;
		}
		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());
		aaiService.LOGwriteDateTrace(NETWORK_ID, requestProperties.getProperty(NETWORK_ID));
	
		return http_req_url;
	}
	
	@Override
	public URL getRequestQueryUrl(String method) throws UnsupportedEncodingException, MalformedURLException {

		String request_url = target_uri+l3_network_query_path;

		String encoded_vnf = null; 

		if(requestProperties.containsKey(NETWORK_NAME)) {
			encoded_vnf = encodeQuery(requestProperties.getProperty(NETWORK_NAME));
			request_url = request_url.replace("{network-name}", encoded_vnf) ;
			
		}

		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());
		aaiService.LOGwriteDateTrace(NETWORK_NAME, requestProperties.getProperty(NETWORK_NAME));
	
		return http_req_url;
	}


	@Override
	public String toJSONString() {
		ObjectMapper mapper = getObjectMapper();
		L3Network l3Network = (L3Network)requestDatum;
		String json_text = null;
		try {
			json_text = mapper.writeValueAsString(l3Network);
		} catch (JsonProcessingException exc) {
			handleException(this, exc);
			return null;
		}
		return json_text;
	}

	@Override
	public String[] getArgsList() {
		String[] args = {NETWORK_ID, NETWORK_NAME};
		return args;
	}
	
	@Override
	public Class<? extends AAIDatum> getModelClass() {
		return L3Network.class;
	}
	
	public String getPrimaryResourceName(String resource) {
		return "l3-network";
	}
}
