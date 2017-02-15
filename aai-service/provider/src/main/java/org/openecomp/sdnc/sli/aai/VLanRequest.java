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

import org.openecomp.aai.inventory.v8.Vlan;

public class VLanRequest extends AAIRequest {

	public static final String VLAN_PATH	= "org.openecomp.sdnc.sli.aai.path.vlan";
	public static final String GENERIC_VNF_VLAN_PATH	="org.openecomp.sdnc.sli.aai.path.generic.vnf.vlan";
	
	private final String vlan_path;
	
	public static final String VLAN_INTERFACE	= "vlan-interface";
	public static final String VLAN_VLAN_INTERFACE	= "vlan.vlan-interface";


	public VLanRequest() {
		vlan_path = configProperties.getProperty(VLAN_PATH);
	}

	@Override
	public URL getRequestQueryUrl(String method) throws UnsupportedEncodingException, MalformedURLException {
		return this.getRequestUrl(method, null);
	}
	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {

		String request_url = target_uri+vlan_path;

		String key = VLAN_INTERFACE;
		if(requestProperties.containsKey(VLAN_VLAN_INTERFACE)) {
			key = VLAN_VLAN_INTERFACE;
		}
		
		if(!requestProperties.containsKey(key)) {
			aaiService.logKeyError(String.format("%s,%s", VLAN_INTERFACE, VLAN_VLAN_INTERFACE));
		}
		String encoded_vnf = encodeQuery(requestProperties.getProperty(key));

		request_url = request_url.replace("{vlan-interface}", encoded_vnf) ;
		aaiService.LOGwriteDateTrace("vlan-interface", requestProperties.getProperty(key));

		if(resourceVersion != null) {
			request_url = request_url +"?resource-version="+resourceVersion;
		}
		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());
		
		return http_req_url;
	}


	@Override
	public String toJSONString() {
		ObjectMapper mapper = getObjectMapper();
		Vlan vnfc = (Vlan)requestDatum;
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
		String[] args =  
			{
				VLAN_INTERFACE,
				VLAN_VLAN_INTERFACE,
				LInterfaceRequest.INTERFACE_NAME, 
				LInterfaceRequest.LINTERFACE_INTERFACE_NAME,
				GenericVnfRequest.GENERIC_VNF_ID,
				GenericVnfRequest.VNF_ID,
				CloudRegionRequest.CLOUD_REGION_CLOUD_OWNER,
				CloudRegionRequest.CLOUD_REGION_CLOUD_REGION_ID
			};

		return args;
	}

	@Override
	public Class<? extends AAIDatum> getModelClass() {
		return Vlan.class;
	}
	
	public static String processPathData(String request_url, Properties requestProperties) throws UnsupportedEncodingException {
		String key = VLAN_INTERFACE;
		if(requestProperties.containsKey(VLAN_VLAN_INTERFACE)) {
			key = VLAN_VLAN_INTERFACE;
		}
		
		if(!requestProperties.containsKey(key)) {
			aaiService.logKeyError(String.format("%s,%s", VLAN_INTERFACE, VLAN_VLAN_INTERFACE));
		}
		
		String encoded_vnf = encodeQuery(requestProperties.getProperty(key));

		request_url = request_url.replace("{vlan-interface}", encoded_vnf) ;
		aaiService.LOGwriteDateTrace("vlan-interface", requestProperties.getProperty(key));
		return request_url;
	}
}
