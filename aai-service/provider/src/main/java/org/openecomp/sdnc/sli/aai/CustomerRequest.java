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

import org.openecomp.aai.inventory.v8.Customer;

public class CustomerRequest extends AAIRequest {

	// tenant (1602)
	public static final String CUSTOMER_PATH		= "org.openecomp.sdnc.sli.aai.path.customer";
	public static final String CUSTOMER_QUERY_PATH	= "org.openecomp.sdnc.sli.aai.path.customer.query";
	
	private final String customer_path;
	private final String customer_query_path;
	
	public static final String CUSTOMER_GLOBAL_CUSTOMER_ID = "customer.global-customer-id";


	public CustomerRequest() {
		customer_path = configProperties.getProperty(CUSTOMER_PATH);
		customer_query_path = configProperties.getProperty(CUSTOMER_QUERY_PATH);
	}

	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {

		String request_url = target_uri + customer_path;
		String encoded_vnf = null;
		
		String hostname = null;

		if(requestProperties.containsKey(CUSTOMER_GLOBAL_CUSTOMER_ID)) {
			hostname = requestProperties.getProperty(CUSTOMER_GLOBAL_CUSTOMER_ID);
			encoded_vnf = encodeQuery(hostname);
			request_url = request_url.replace("{customer-id}", encoded_vnf) ;
		} else {
			LOG.error("Required key "+CUSTOMER_GLOBAL_CUSTOMER_ID+" is misssing.");
		}

		if(resourceVersion != null) {
			request_url = request_url +"?resource-version="+resourceVersion;
		}
		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());
		aaiService.LOGwriteDateTrace("global-customer-id", hostname);
		
		return http_req_url;
	}
	
	@Override
	public URL getRequestQueryUrl(String method) throws UnsupportedEncodingException, MalformedURLException {
		return this.getRequestUrl(method, null);
	}

	public static String processPathData(String request_url, Properties requestProperties) throws UnsupportedEncodingException {

		String key = CUSTOMER_GLOBAL_CUSTOMER_ID;
		
		if(!requestProperties.containsKey(key)) {
			aaiService.logKeyError(CUSTOMER_GLOBAL_CUSTOMER_ID);
		}
		

		String encoded_vnf = encodeQuery(requestProperties.getProperty(key));
		request_url = request_url.replace("{global-customer-id}", encoded_vnf) ;
		aaiService.LOGwriteDateTrace("global-customer-id", requestProperties.getProperty(key));

		return request_url;
	}
	
	@Override
	public String toJSONString() {
		ObjectMapper mapper = getObjectMapper();
		Customer vnfc = (Customer)requestDatum;
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
		String[] args = {CUSTOMER_GLOBAL_CUSTOMER_ID};
		return args;
	}
	
	@Override
	public Class<? extends AAIDatum> getModelClass() {
		return Customer.class;
	}
}
