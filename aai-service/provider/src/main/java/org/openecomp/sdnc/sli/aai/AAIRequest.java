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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openecomp.sdnc.sli.aai.data.AAIDatum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public abstract class AAIRequest {
	protected static final Logger LOG = LoggerFactory.getLogger(AAIRequest.class);

	protected static final String TARGET_URI = "org.openecomp.sdnc.sli.aai.uri";

	protected static final String MASTER_REQUEST = "master-request";

	public static final String RESOURCE_VERSION = "resource-version";

	protected static Properties configProperties;
	protected final String target_uri;
	protected static AAIService aaiService;

	protected AAIDatum requestDatum;

	protected final Properties requestProperties = new Properties();


	public static AAIRequest createRequest(String resoourceName, Map<String, String> nameValues){

		String resoource = resoourceName;

		if(resoource == null)
			return null;

		if(resoource.contains(":")) {
			String[] tokens = resoource.split(":");
			if(tokens != null && tokens.length > 0) {
				resoource = tokens[0];
			}
		}

		if(nameValues.containsKey("selflink")){
			AAIRequest localRequest = createRequest(resoource, new HashMap<String, String>());
			if(localRequest != null)
				return new SelfLinkRequest(localRequest.getModelClass());
			else
				return null;
		}

		switch(resoource){
		case "cloud-region":
			return new CloudRegionRequest();
		case "customer":
			return new CustomerRequest();
		case "generic-query":
			return new GenericQueryRequest();
		case "generic-vnf":
			return new GenericVnfRequest();
		case "logical-link":
			return new LogicalLinkRequest();
		case "l-interface":
			if(nameValues.containsKey("generic_vnf.vnf_id") || nameValues.containsKey("vnf_id")) {
				return new GenericVnfLInterfaceRequest();
			}
		case "linterface":
			return new LInterfaceRequest(LInterfaceRequest.TYPE.L2_BRIDGE_SBG);
		case "l2-bridge-sbg":
			return new SubInterfaceRequest(SubInterfaceRequest.TYPE.L2_BRIDGE_SBG);
		case "l2-bridge-bgf":
			return new SubInterfaceRequest(SubInterfaceRequest.TYPE.L2_BRIDGE_BGF);
		case "l3-interface-ipv4-address-list":
			return new L3InterfaceIpv4AddressListRequest();
		case "l3-interface-ipv6-address-list":
			return new L3InterfaceIpv6AddressListRequest();
		case "l3-network":
			return new L3NetworkRequest();
		case "l3-network-subnet":
		case "subnet":
			return new SubnetRequest();
		case "lag-interface":
			return new LagInterfacePnfRequest();
		case "named-query":
			return new NamedQueryRequest();
		case "nodes-query":
			return new NodesQueryRequest();
		case "p-interface":
			if(nameValues.containsKey(PInterfaceRequest.HOSTNAME) || nameValues.containsKey(PInterfaceRequest.PSERVER_HOSTNAME)){
				return new PInterfaceRequest();
			}
			if(nameValues.containsKey(PnfRequest.PNF_NAME.replaceAll("-", "_")) || nameValues.containsKey(PnfRequest.PNF_PNF_NAME.replaceAll("-", "_"))){
				return new PInterfacePnfRequest();
			}
			return null;
		case "physical-link":
			return new PhysicalLinkRequest();
		case "pnf":
			return new PnfRequest();
		case "pserver":
			return new PServerRequest();
		case "service":
			return new ServiceRequest();
		case "service-instance":
			return new ServiceInstanceRequest();
		case "service-subscription":
			return new ServiceSubscriptionRequest();
		case "tenant":
			return new TenantRequest();
		case "vnfc":
			return new VnfcRequest();
		case "vserver":
		case "vserver2":
			return new VserverRequest();
		case "vf-module":
			return new VfModuleRequest();
		case "vlan":
			if(nameValues.containsKey(GenVnfrVLanRequest.VNF_ID))
				return new GenVnfrVLanRequest();
			else if(nameValues.containsKey(VServerVLanRequest.VSERVER_ID))
				return new VServerVLanRequest();
			else {
				LOG.warn("VLAN request is not supported based on the key structure " + nameValues.keySet().toString());
				return new VLanRequest();
			}
		case "volume-group":
			return new VolumeGroupRequest();
		case "echo":
		case "test":
			return new EchoRequest();
		default:
			return null;
		}
	}

	public static void setProperties(Properties props, AAIService aaiService) {
		AAIRequest.configProperties = props;
		AAIRequest.aaiService = aaiService;
	}

	public AAIRequest() {
		target_uri	= configProperties.getProperty(TARGET_URI);
	}

	public final void addRequestProperty(String key, String value) {
		requestProperties.put(key, value);
	}

	public final void setRequestObject(AAIDatum value) {
		requestDatum = value;
	}

	public final AAIDatum getRequestObject() {
		return requestDatum;
	}

	public final void addMasterRequest(AAIRequest masterRequest) {
		requestProperties.put(MASTER_REQUEST, masterRequest);
	}

	protected static String encodeQuery(String param) throws UnsupportedEncodingException {
		return URLEncoder.encode(param, "UTF-8").replace("+", "%20");
	}

	protected void handleException(AAIRequest lInterfaceRequest, JsonProcessingException exc) {
		aaiService.getLogger().warn("Could not deserialize object of type " + lInterfaceRequest.getClass().getSimpleName(), exc) ;
	}

	public abstract URL getRequestUrl(String method, String resourceVersion) throws UnsupportedEncodingException, MalformedURLException;
	public abstract URL getRequestQueryUrl(String method) throws UnsupportedEncodingException, MalformedURLException;

	public abstract String toJSONString();

	public abstract String[] getArgsList();

	public abstract Class<? extends AAIDatum> getModelClass() ;

	public String getPrimaryResourceName(String resource) {
		return resource;
	}

	public String formatKey(String argument) {
		return argument;
	}

	public AAIDatum jsonStringToObject(String jsonData) throws JsonParseException, JsonMappingException, IOException {
		if(jsonData == null) {
			return null;
		}

		AAIDatum response = null;
		ObjectMapper mapper = getObjectMapper();
		response = mapper.readValue(jsonData, getModelClass());
		return response;
	}

	public void processRequestPathValues(Map<String, String> nameValues) {

		String[] arguments = this.getArgsList();
		for(String name : arguments) {
			String tmpName = name.replaceAll("-", "_");
			String value = nameValues.get(tmpName);
			if(value != null && !value.isEmpty()) {
				value = value.trim().replace("'", "").replace("$", "").replace("'", "");
				this.addRequestProperty(name, value);
			}
		}
	}

	public static String processPathData(String request_url, Properties requestProperties) throws UnsupportedEncodingException {
		return request_url;
	}

	public boolean isDeleteDataRequired() {
		return false;
	}

	ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
	    AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
	 // if ONLY using JAXB annotations:
	    mapper.setAnnotationIntrospector(introspector);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
	}
}
