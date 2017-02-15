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

import org.openecomp.aai.inventory.v8.LogicalLink;

public class LogicalLinkRequest extends AAIRequest {

	// tenant (1602)
	public static final String LOGICAL_LINK_PATH		= "org.openecomp.sdnc.sli.aai.path.logical.link";
	public static final String LOGICAL_LINK_QUERY_PATH	= "org.openecomp.sdnc.sli.aai.path.logical.link.query";
	
	private final String logical_link_path;
	private final String logical_link_query_path;
	
	public static final String LIK_NAME = "link-name";
	public static final String LOGICAL_LIK_NAME = "logical-link.link-name";

	public LogicalLinkRequest() {
		logical_link_path = configProperties.getProperty(LOGICAL_LINK_PATH);
		logical_link_query_path = configProperties.getProperty(LOGICAL_LINK_QUERY_PATH);
	}

	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {

		String request_url = target_uri + logical_link_path;
		String encoded_vnf = null;
		
		String linkNameKey = LIK_NAME;
		if(requestProperties.containsKey(LOGICAL_LIK_NAME)) {
			linkNameKey = LOGICAL_LIK_NAME;
		}

		encoded_vnf = encodeQuery(requestProperties.getProperty(linkNameKey));
		request_url = request_url.replace("{link-name}", encoded_vnf) ;


		
		if(resourceVersion != null) {
			request_url = request_url +"?resource-version="+resourceVersion;
		}
		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());
		aaiService.LOGwriteDateTrace("link-name", requestProperties.getProperty(linkNameKey));
		
		return http_req_url;
	}
	
	@Override
	public URL getRequestQueryUrl(String method) throws UnsupportedEncodingException, MalformedURLException {
		return this.getRequestUrl(method, null);
	}


	@Override
	public String toJSONString() {
		ObjectMapper mapper = getObjectMapper();
		LogicalLink vnfc = (LogicalLink)requestDatum;
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
		String[] args = {LIK_NAME, LOGICAL_LIK_NAME};
		return args;
	}
	
	@Override
	public Class<? extends AAIDatum> getModelClass() {
		return LogicalLink.class;
	}
}
