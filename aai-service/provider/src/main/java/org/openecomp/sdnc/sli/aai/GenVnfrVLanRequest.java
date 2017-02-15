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

import org.openecomp.aai.inventory.v8.Vlan;

public class GenVnfrVLanRequest extends AAIRequest {
	//aai/v7/cloud-infrastructure/tenants/tenant/{tenant-id}/vservers/vserver/{vserver-id}/l-interfaces/l-interface/{interface-name}/vlans/vlan/{vlan-interface}

	// tenant (1602)
	public static final String VLAN_PATH		= "org.openecomp.sdnc.sli.aai.path.generic.vnf";
	public static final String VLAN_QUERY_PATH	= "org.openecomp.sdnc.sli.aai.path.generic.vnf.query";
	
	private final String vlan_path;
	private final String vlan_query_path;
	
	public static final String VNF_ID = "generic_vnf.vnf_id";
	public static final String INTERFACE_NAME = "l_interface.interface_name";
	public static final String VLAN_INTERFACE = "vlan.vlan_interface";

	public GenVnfrVLanRequest() {
		vlan_path = configProperties.getProperty(VLAN_PATH) + "/l-interfaces/l-interface/{interface-name}/vlans/vlan/{vlan-interface}";
		vlan_query_path = configProperties.getProperty(VLAN_QUERY_PATH);
	}

	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {
		
		String request_url = target_uri+vlan_path;
		String encoded_vnf = encodeQuery(requestProperties.getProperty(VNF_ID));
		request_url = request_url.replace("{vnf-id}", encoded_vnf) ;
		
		encoded_vnf = encodeQuery(requestProperties.getProperty(INTERFACE_NAME));
		request_url = request_url.replace("{interface-name}", encoded_vnf) ;
		
		encoded_vnf = encodeQuery(requestProperties.getProperty(VLAN_INTERFACE));
		request_url = request_url.replace("{vlan-interface}", encoded_vnf) ;
		
		if(resourceVersion != null) {
			request_url = request_url +"?resource-version="+resourceVersion;
		}
		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());
		aaiService.LOGwriteDateTrace("vnf-id", requestProperties.getProperty(VNF_ID));
		aaiService.LOGwriteDateTrace("interface-name", requestProperties.getProperty(INTERFACE_NAME));
		aaiService.LOGwriteDateTrace("vlan-interface", requestProperties.getProperty(VLAN_INTERFACE));
		
		return http_req_url;
	}
	
	@Override
	public URL getRequestQueryUrl(String method) throws UnsupportedEncodingException, MalformedURLException {

		String request_url = target_uri+vlan_path;
		String encoded_vnf = encodeQuery(requestProperties.getProperty(VNF_ID));
		request_url = request_url.replace("{vnf-id}", encoded_vnf) ;
		
		encoded_vnf = encodeQuery(requestProperties.getProperty(INTERFACE_NAME));
		request_url = request_url.replace("{interface-name}", encoded_vnf) ;
		
		encoded_vnf = encodeQuery(requestProperties.getProperty(VLAN_INTERFACE));
		request_url = request_url.replace("{vlan-interface}", encoded_vnf) ;
		
		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());

		aaiService.LOGwriteDateTrace("vnf-id", requestProperties.getProperty(VNF_ID));
		aaiService.LOGwriteDateTrace("interface-name", requestProperties.getProperty(INTERFACE_NAME));
		aaiService.LOGwriteDateTrace("vlan-interface", requestProperties.getProperty(VLAN_INTERFACE));
		
		return http_req_url;
	}


	@Override
	public String toJSONString() {
		ObjectMapper mapper = getObjectMapper();
		Vlan tenant = (Vlan)requestDatum;
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
		String[] args = {VNF_ID, INTERFACE_NAME, VLAN_INTERFACE};
		return args;
	}


	@Override
	public Class<? extends AAIDatum> getModelClass() {
		return Vlan.class;
	}
}
