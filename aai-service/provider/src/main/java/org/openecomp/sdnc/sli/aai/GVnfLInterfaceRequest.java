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

import org.openecomp.aai.inventory.v8.LInterface;

public class GVnfLInterfaceRequest extends AAIRequest {

	private final String generic_vnf_linterface_path;
	private final String generic_vnf_linterface_query_path;

	
	public static final String INTERFACE_NAME = "interface-name";
	public static final String LINTERFACE_INTERFACE_NAME 	= "l-interface.interface-name";

	
	public GVnfLInterfaceRequest() 
	{
	
		String tmpGenericVnfLinterfacePath = configProperties.getProperty(GenericVnfRequest.GENERIC_VNF_PATH);

		generic_vnf_linterface_query_path = configProperties.getProperty(GenericVnfRequest.GENERIC_VNF_QUERY_PATH);
		
		if(tmpGenericVnfLinterfacePath != null) {
			tmpGenericVnfLinterfacePath = tmpGenericVnfLinterfacePath + "/l-interfaces/l-interface/{interface-name}";
			LOG.debug("GVnfLInterfaceRequest.PATH = " + tmpGenericVnfLinterfacePath);
		} else {
			LOG.warn("PATH " + GenericVnfRequest.GENERIC_VNF_PATH + " not defined");
		}

		generic_vnf_linterface_path = tmpGenericVnfLinterfacePath;
	}

	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {

		String request_url = null; 
		

		request_url = target_uri + generic_vnf_linterface_path;

		String vnfId = requestProperties.getProperty(GenericVnfRequest.VNF_ID);
		if(vnfId == null) {
			vnfId = requestProperties.getProperty(GenericVnfRequest.GENERIC_VNF_ID);
		}

		String encoded_vnf = encodeQuery(vnfId);
		request_url = request_url.replace("{vnf-id}", encoded_vnf);
		
		String interfaceName = requestProperties.getProperty(INTERFACE_NAME);
		if(interfaceName == null || interfaceName.isEmpty()) {
			interfaceName = requestProperties.getProperty(LINTERFACE_INTERFACE_NAME);
		}
		encoded_vnf = encodeQuery(interfaceName);
		request_url = request_url.replace("{interface-name}", encoded_vnf) ;


		if(resourceVersion != null) {
			request_url = request_url +"?resource-version="+resourceVersion;
		}
		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());
		aaiService.LOGwriteDateTrace("vnf-id", vnfId);
		aaiService.LOGwriteDateTrace("interface-name", interfaceName);

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
	public String[] getArgsList() {
		String[] args = {INTERFACE_NAME, LINTERFACE_INTERFACE_NAME, GenericVnfRequest.VNF_ID, GenericVnfRequest.GENERIC_VNF_ID};
		return args;
	}
	
	@Override
	public Class<? extends AAIDatum> getModelClass() {
		return LInterface.class;
	}
	
	@Override
	public String getPrimaryResourceName(String resource) {
		return "l-interface";
	}

	
}
