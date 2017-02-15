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
import java.util.Properties;

import org.openecomp.sdnc.sli.aai.data.AAIDatum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.openecomp.aai.inventory.v8.Tenant;

public class TenantRequest extends AAIRequest {

	// tenant (1602)
	public static final String TENANT_PATH			  = "org.openecomp.sdnc.sli.aai.path.tenant";
	public static final String TENANT_QUERY_PATH	  = "org.openecomp.sdnc.sli.aai.path.tenant.query";
	
	private final String tenant_path;
	private final String tenant_query_path;
	
	public static final String TENANT_ID = "tenant_id";
	public static final String TENANT_NAME = "tenant_name";
	public static final String TENANT_TENANT_ID = "tenant.tenant_id";
	public static final String TENANT_TENANT_NAME = "tenant.tenant_name";

	public TenantRequest() {
		tenant_path = configProperties.getProperty(TENANT_PATH);
		tenant_query_path = configProperties.getProperty(TENANT_QUERY_PATH);
	}

	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {

		if(requestProperties.getProperty(TENANT_ID) == null && requestProperties.getProperty(TENANT_NAME) != null) {
			return getRequestQueryUrl(method);
		}
		
		String request_url = target_uri+tenant_path;
//		String encoded_vnf = encodeQuery(requestProperties.getProperty(TENANT_ID));
//		request_url = request_url.replace("{tenant-id}", encoded_vnf) ;
		
		request_url = TenantRequest.processPathData(request_url, requestProperties);
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

		String request_url = target_uri+tenant_query_path;
		String encoded_vnf = encodeQuery(requestProperties.getProperty(TENANT_NAME));
		request_url = request_url.replace("{tenant-name}", encoded_vnf) ;
		
		if(requestProperties.containsKey(CloudRegionRequest.CLOUD_REGION_CLOUD_OWNER)) {
			request_url = CloudRegionRequest.processPathData(request_url, requestProperties);
		}
		
		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());
		aaiService.LOGwriteDateTrace("tenant_name", requestProperties.getProperty(TENANT_NAME));
		
		return http_req_url;
	}


	@Override
	public String toJSONString() {
		ObjectMapper mapper = getObjectMapper();
		Tenant tenant = (Tenant)requestDatum;
		String json_text = null;
		try {
			json_text = mapper.writeValueAsString(tenant);
		} catch (JsonProcessingException exc) {
			handleException(this, exc);
			return null;
		}
		return json_text;
	}


	@Override
	public String[] getArgsList() {
		String[] args = {TENANT_ID, TENANT_NAME, TENANT_TENANT_ID, TENANT_TENANT_NAME, CloudRegionRequest.CLOUD_REGION_CLOUD_OWNER, CloudRegionRequest.CLOUD_REGION_CLOUD_REGION_ID };
		return args;
	}


	@Override
	public Class<? extends AAIDatum> getModelClass() {
		return Tenant.class;
	}
	
	public static String processPathData(String request_url, Properties requestProperties) throws UnsupportedEncodingException {
		String key = TENANT_ID;
		if(requestProperties.containsKey(TENANT_TENANT_ID)) {
			key = TENANT_TENANT_ID;
		}
		
		if(!requestProperties.containsKey(key)) {
			aaiService.logKeyError(String.format("%s,%s", TENANT_ID, TENANT_TENANT_ID));
		}
		
		String encoded_vnf = encodeQuery(requestProperties.getProperty(key));
		request_url = request_url.replace("{tenant-id}", encoded_vnf) ;
		
		aaiService.LOGwriteDateTrace("tenant-id", requestProperties.getProperty(key));
		return request_url;
	}
}
