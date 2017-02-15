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

import org.openecomp.aai.inventory.v8.LInterface;

public class GenericVnfLInterfaceRequest extends AAIRequest {

	public static final String GENERIC_VNF_LINTERFACE_PATH	= "org.openecomp.sdnc.sli.aai.path.generic.vnf.linterface";

	private final String generic_vnf_linterface_path;

	
	public static final String INTERFACE_NAME = "interface-name";
	public static final String LINTERFACE_INTERFACE_NAME 	= "l-interface.interface-name";
	
	public GenericVnfLInterfaceRequest() {
		generic_vnf_linterface_path = configProperties.getProperty(GENERIC_VNF_LINTERFACE_PATH);
	}

	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {

		String request_url = null; 

		request_url = target_uri + generic_vnf_linterface_path;


		request_url = processPathData(request_url, requestProperties);
		request_url = GenericVnfRequest.processPathData(request_url, requestProperties);

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
		LInterface vnfc = (LInterface)requestDatum;
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
	public Class<? extends AAIDatum> getModelClass() {
		return LInterface.class;
	}
	
	@Override
	public String getPrimaryResourceName(String resource) {
		return "l-interface";
	}
	
	public static final String processPathData(String request_url, Properties requestProperties) throws UnsupportedEncodingException {
		String interfaceName = requestProperties.getProperty(INTERFACE_NAME);
		if(interfaceName == null || requestProperties.containsKey(LINTERFACE_INTERFACE_NAME)) {
			interfaceName = requestProperties.getProperty(LINTERFACE_INTERFACE_NAME);
		}
		
		if(interfaceName == null) {
			aaiService.logKeyError(String.format("%s,%s", INTERFACE_NAME, LINTERFACE_INTERFACE_NAME));
		}

		request_url = request_url.replace("{interface-name}", encodeQuery(interfaceName)) ;
		aaiService.LOGwriteDateTrace("interface-name", interfaceName);
		return request_url;
	}


	@Override
	public String[] getArgsList() {
		String[] tmpArray = {INTERFACE_NAME, LINTERFACE_INTERFACE_NAME, GenericVnfRequest.GENERIC_VNF_ID, GenericVnfRequest.VNF_ID};
		return tmpArray;
	}
}
