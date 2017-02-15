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

import org.openecomp.aai.inventory.v8.Subnet;

public class SubnetRequest extends AAIRequest {

	public static final String SUBNET_PATH		  = "org.openecomp.sdnc.sli.aai.path.subnet";
	public static final String SUBNET_QUERY_PATH  = "org.openecomp.sdnc.sli.aai.path.subnet.query";
	
	private final String subnet_path;
	private final String subnet_query_path;
	
	public static final String SUBNET_ID	= "subnet.subnet-id";
	public static final String NETWORK_ID	= "l3-network.network-id";

	
	public SubnetRequest() {
		subnet_path = configProperties.getProperty(SUBNET_PATH);
		subnet_query_path = configProperties.getProperty(SUBNET_QUERY_PATH);
	}

	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {

		String request_url = target_uri+subnet_path;

		String encoded_vnf = null; 
		if(requestProperties.getProperty(SUBNET_ID) != null) {
			encoded_vnf = encodeQuery(requestProperties.getProperty(SUBNET_ID));
			request_url = request_url.replace("{subnet-id}", encoded_vnf) ;
		}

		if(requestProperties.getProperty(NETWORK_ID) != null) {
			encoded_vnf = encodeQuery(requestProperties.getProperty(NETWORK_ID));
			request_url = request_url.replace("{network-id}", encoded_vnf) ;
			
		}
		
		if(resourceVersion != null) {
			request_url = request_url +"?resource-version="+resourceVersion;
		}
		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());
		aaiService.LOGwriteDateTrace(SUBNET_ID, requestProperties.getProperty(SUBNET_ID));
		aaiService.LOGwriteDateTrace(NETWORK_ID, requestProperties.getProperty(NETWORK_ID));
	
		return http_req_url;
	}
	
	@Override
	public URL getRequestQueryUrl(String method) throws UnsupportedEncodingException, MalformedURLException {
		return this.getRequestUrl(method, null);
	}


	@Override
	public String toJSONString() {
		ObjectMapper mapper = getObjectMapper();
		Subnet vnfc = (Subnet)requestDatum;
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
		String[] args = {SUBNET_ID, NETWORK_ID};
		return args;
	}
	
	@Override
	public Class<? extends AAIDatum> getModelClass() {
		return Subnet.class;
	}
	
	public String getPrimaryResourceName(String resource) {
		return "subnet";
	}
}
