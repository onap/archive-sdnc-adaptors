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
import java.util.Properties;

import org.openecomp.sdnc.sli.aai.data.AAIDatum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.openecomp.aai.inventory.v8.Pnf;

public class PnfRequest extends AAIRequest {

	// pnf
	public static final String PNF_PATH		  = "org.openecomp.sdnc.sli.aai.path.pnf";

	
	private final String pnf_path;

	
	public static final String PNF_NAME	= "pnf-name";
	public static final String PNF_PNF_NAME	= "pnf.pnf-name";


	public PnfRequest() {
		pnf_path = configProperties.getProperty(PNF_PATH);

	}

	@Override
	public URL getRequestQueryUrl(String method) throws UnsupportedEncodingException, MalformedURLException {
		return this.getRequestUrl(method, null);
	}
	
	@Override
	public URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException {

		String request_url = target_uri+pnf_path;
		request_url = processPathData(request_url, requestProperties); 

		if(resourceVersion != null) {
			request_url = request_url +"?resource-version="+resourceVersion;
		}
		URL http_req_url =	new URL(request_url);

		aaiService.LOGwriteFirstTrace(method, http_req_url.toString());
		
		return http_req_url;
	}

	public static String processPathData(String request_url, Properties requestProperties) throws UnsupportedEncodingException {

		String key = PNF_NAME;
		if(requestProperties.containsKey(PNF_PNF_NAME)) {
			key = PNF_PNF_NAME;
		}
		
		if(!requestProperties.containsKey(key)) {
			aaiService.logKeyError(String.format("%s,%s", PNF_NAME, PNF_PNF_NAME));
		}
		
		String encoded_vnf = encodeQuery(requestProperties.getProperty(key));
		request_url = request_url.replace("{pnf-name}", encoded_vnf) ;

		return request_url;
	}

	@Override
	public String toJSONString() {
		ObjectMapper mapper = getObjectMapper();
		Pnf pnf = (Pnf)requestDatum;
		String json_text = null;
		try {
			json_text = mapper.writeValueAsString(pnf);
		} catch (JsonProcessingException exc) {
			handleException(this, exc);
			return null;
		}
		return json_text;
	}

	@Override
	public String[] getArgsList() {
		String[] args =  {PNF_NAME, PNF_PNF_NAME};

		return args;
	}

	@Override
	public Class<? extends AAIDatum> getModelClass() {
		return Pnf.class;
	}
}
