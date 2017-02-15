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

import org.openecomp.aai.inventory.v8.Vnfc;

public class VolumeGroupRequest extends AAIRequest {

	// volume-group-id
	public static final String VOLUME_GROUP_PATH	= "org.openecomp.sdnc.sli.aai.path.volume.group";
	
	private final String volume_group_path;
	
	public static final String VOLUME_GROUP_NAME	= "volume-group-id";
	public static final String VOLUME_GROUP_VOLUME_GROUP_NAME	= "volume-group.volume-group-id";


	public VolumeGroupRequest() {
		volume_group_path = configProperties.getProperty(VOLUME_GROUP_PATH);
	}

	@Override
	public URL getRequestQueryUrl(String method) throws UnsupportedEncodingException, MalformedURLException {
		return this.getRequestUrl(method, null);
	}
	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {

		String request_url = target_uri+volume_group_path;

		String key = VOLUME_GROUP_NAME;
		if(requestProperties.containsKey(VOLUME_GROUP_VOLUME_GROUP_NAME)) {
			key = VOLUME_GROUP_VOLUME_GROUP_NAME;
		}
		String encoded_vnf = encodeQuery(requestProperties.getProperty(key));
		request_url = request_url.replace("{volume-group-id}", encoded_vnf) ;
		
		request_url = CloudRegionRequest.processPathData(request_url, requestProperties);

		if(resourceVersion != null) {
			request_url = request_url +"?resource-version="+resourceVersion;
		}
		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());
		aaiService.LOGwriteDateTrace("volume-group-id", requestProperties.getProperty(key));
		
		return http_req_url;
	}


	@Override
	public String toJSONString() {
		ObjectMapper mapper = getObjectMapper();
		Vnfc vnfc = (Vnfc)requestDatum;
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
		String[] args =  {VOLUME_GROUP_NAME, VOLUME_GROUP_VOLUME_GROUP_NAME, CloudRegionRequest.CLOUD_REGION_CLOUD_OWNER, CloudRegionRequest.CLOUD_REGION_CLOUD_REGION_ID};

		return args;
	}

	@Override
	public Class<? extends AAIDatum> getModelClass() {
		return Vnfc.class;
	}
}
