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

import org.openecomp.aai.inventory.v8.ServiceInstance;

public class ServiceInstanceRequest extends AAIRequest {

	// tenant (1602)
	public static final String REQUEST_PATH			= "org.openecomp.sdnc.sli.aai.path.service.instance";
	public static final String QUERY_REQUEST_PATH	= "org.openecomp.sdnc.sli.aai.path.service.instance.query";
	
	private final String request_path;
	private final String query_request_path;
	
	public static final String SERVICE_INSTANCE_ID = "service-instance.service-instance-id";



	public ServiceInstanceRequest() {
		request_path = configProperties.getProperty(REQUEST_PATH);
		query_request_path = configProperties.getProperty(QUERY_REQUEST_PATH);
	}

	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {

		String request_url = target_uri + request_path;
		String encoded_vnf = null;
		
		request_url = processPathData(request_url, requestProperties);
		request_url = ServiceSubscriptionRequest.processPathData(request_url, requestProperties);
		request_url = CustomerRequest.processPathData(request_url, requestProperties);

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


	public static String processPathData(String request_url, Properties requestProperties) throws UnsupportedEncodingException {

		String key = SERVICE_INSTANCE_ID;

		if(!requestProperties.containsKey(key)) {
			aaiService.logKeyError(SERVICE_INSTANCE_ID);
		}
		
		String encoded_vnf = encodeQuery(requestProperties.getProperty(key));
		request_url = request_url.replace("{service-instance-id}", encoded_vnf) ;
		aaiService.LOGwriteDateTrace("service-instance-id", requestProperties.getProperty(key));

		return request_url;
	}
	
	@Override
	public String toJSONString() {
		ObjectMapper mapper = getObjectMapper();
		ServiceInstance vnfc = (ServiceInstance)requestDatum;
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
		String[] args = {SERVICE_INSTANCE_ID, ServiceSubscriptionRequest.SERVICE_TYPE, CustomerRequest.CUSTOMER_GLOBAL_CUSTOMER_ID};
		return args;
	}
	
	@Override
	public Class<? extends AAIDatum> getModelClass() {
		return ServiceInstance.class;
	}
}
