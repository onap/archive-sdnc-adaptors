/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
 * 						reserved.
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
import java.util.Arrays;

import org.openecomp.sdnc.sli.aai.data.AAIDatum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.openecomp.aai.inventory.v8.Vserver;

public class VserverRequest extends AAIRequest {

	public static final String VSERVER_PATH		  = "org.openecomp.sdnc.sli.aai.path.vserver";
	public static final String VSERVER_QUERY_PATH	  = "org.openecomp.sdnc.sli.aai.path.vserver.query";
	
	private final String vserver_path;
	private final String vserver_query_path;
	
	public static final String VSERVER_ID = "vserver_id";
	public static final String VSERVER_VSERVER_ID = "vserver.vserver_id";

	public static final String TENANT_ID = "tenant_id";
	public static final String TENANT_TENANT_ID = "tenant.tenant_id";

	
	public static final String VSERVER_NAME = "vserver_name";

	public VserverRequest() {
		vserver_path = configProperties.getProperty(VSERVER_PATH);
		vserver_query_path = configProperties.getProperty(VSERVER_QUERY_PATH);
	}

	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {

		String request_url = target_uri+vserver_path;
		LOG.debug(Arrays.toString(requestProperties.entrySet().toArray()));

		String encoded_vnf = null; 
		if(requestProperties.containsKey(VSERVER_ID)) {
			encoded_vnf = encodeQuery(requestProperties.getProperty(VSERVER_ID));
			request_url = request_url.replace("{vserver-id}", encoded_vnf) ;
			aaiService.LOGwriteDateTrace(VSERVER_ID, requestProperties.getProperty(VSERVER_ID));
		}
		else if(requestProperties.containsKey(VSERVER_VSERVER_ID)) {
			encoded_vnf = encodeQuery(requestProperties.getProperty(VSERVER_VSERVER_ID));
			request_url = request_url.replace("{vserver-id}", encoded_vnf) ;
			aaiService.LOGwriteDateTrace(VSERVER_ID, requestProperties.getProperty(VSERVER_VSERVER_ID));
		}


		if(requestProperties.containsKey(TENANT_ID)) {
			encoded_vnf = encodeQuery(requestProperties.getProperty(TENANT_ID));
			request_url = request_url.replace("{tenant-id}", encoded_vnf) ;
			aaiService.LOGwriteDateTrace(TENANT_ID, requestProperties.getProperty(TENANT_ID));		
		}
		else if(requestProperties.containsKey(TENANT_TENANT_ID)) {
			encoded_vnf = encodeQuery(requestProperties.getProperty(TENANT_TENANT_ID));
			request_url = request_url.replace("{tenant-id}", encoded_vnf) ;
			aaiService.LOGwriteDateTrace(TENANT_ID, requestProperties.getProperty(TENANT_TENANT_ID));
		}

		if(requestProperties.containsKey(CloudRegionRequest.CLOUD_REGION_CLOUD_OWNER)) {
			request_url = CloudRegionRequest.processPathData(request_url, requestProperties);
		}

		
		if(resourceVersion != null) {
			request_url = request_url +"?resource-version="+resourceVersion;
		}
		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());
		
		return http_req_url;
	}
	
	@Override
	public URL getRequestQueryUrl(String method) throws UnsupportedEncodingException, MalformedURLException {
		return this.getRequestUrl(method, null);
	}


	@Override
	public String toJSONString() {
		ObjectMapper mapper = getObjectMapper();
		Vserver vnfc = (Vserver)requestDatum;
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
		String[] args = {VSERVER_ID, VSERVER_VSERVER_ID, VSERVER_NAME, TENANT_ID, TENANT_TENANT_ID, CloudRegionRequest.CLOUD_REGION_CLOUD_OWNER, CloudRegionRequest.CLOUD_REGION_CLOUD_REGION_ID};
		return args;
	}
	
	@Override
	public Class<? extends AAIDatum> getModelClass() {
		return Vserver.class;
	}
	
	@Override
	public String formatKey(String argument) {
		switch(argument) {
		case "tenant-id":
			return "tenant.tenant-id";
		case "vserver-id":
			return "vserver.vserver-id";
		case "vserver-name":
			return "vserver.vserver-name";
//		case "cloud-region-id":
//			return "cloud-region.cloud-region-id";
//		case "cloud-owner":
//			return "cloud-region.cloud-owner";
		default:
			return argument;
		}
	}
	
	public String getPrimaryResourceName(String resource) {
		return "vserver";
	}
}
