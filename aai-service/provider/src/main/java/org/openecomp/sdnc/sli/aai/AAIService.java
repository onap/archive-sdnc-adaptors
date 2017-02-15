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


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.openecomp.aai.inventory.v8.AvailabilityZone;
import org.openecomp.aai.inventory.v8.GenericVnf;
import org.openecomp.aai.inventory.v8.L3Network;
import org.openecomp.aai.inventory.v8.PInterface;
import org.openecomp.aai.inventory.v8.PhysicalLink;
import org.openecomp.aai.inventory.v8.Pserver;
import org.openecomp.aai.inventory.v8.ResultData;
import org.openecomp.aai.inventory.v8.SearchResults;
import org.openecomp.aai.inventory.v8.Service;
import org.openecomp.aai.inventory.v8.ServiceInstance;
import org.openecomp.aai.inventory.v8.Tenant;
import org.openecomp.aai.inventory.v8.Vserver;
import org.openecomp.sdnc.sli.ConfigurationException;
import org.openecomp.sdnc.sli.MetricLogger;
import org.openecomp.sdnc.sli.SvcLogicContext;
import org.openecomp.sdnc.sli.SvcLogicException;
import org.openecomp.sdnc.sli.SvcLogicResource;
import org.openecomp.sdnc.sli.aai.data.AAIDatum;
import org.openecomp.sdnc.sli.aai.data.ErrorResponse;
import org.openecomp.sdnc.sli.aai.data.RequestError;
import org.openecomp.sdnc.sli.aai.data.ResourceVersion;
import org.openecomp.sdnc.sli.aai.data.ServiceException;
import org.openecomp.sdnc.sli.aai.data.notify.NotifyEvent;
import org.openecomp.sdnc.sli.aai.data.v1507.VServer;
import org.openecomp.sdnc.sli.aai.update.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;


public class AAIService extends AAIDeclarations implements AAIClient, SvcLogicResource {

	public static final String AAICLIENT_PROPERTIES = "/aaiclient.properties";

	private static final Logger LOG = LoggerFactory.getLogger(AAIService.class);

	private final String truststore_path;
	private final String truststore_password;
//	private final String keystore_path;
//	private final String keystore_password;
	private final Boolean ignore_certificate_host_error;

	private final String target_uri;
	private final String query_path;
	private final String network_vce_path;

	private final String network_vpe_path;

	private final String network_vpls_pe_path;

	private final String network_complex_path;

	private final String network_pserver_path;

	private final String network_vserver_path;

	private final String ctag_pool_path;

	private final String svc_instance_path;
	private final String svc_inst_qry_path;

	private final String generic_vnf_path;

	private final String network_dvsswitch_path;

	private final String l3_network_path;
	private final String l3_network_path_query_name;

	private final String vpn_binding_path;

	private final String vnf_image_path;
	private final String vnf_image_query_path;

	private final String param_format;  				//= "filter=%s:%s";
	private final String param_vnf_type;				//= "vnf-type";
	private final String param_physical_location_id; 	//= "physical-location-id";
	private final String param_service_type;			//= "service-type";

	private final String ubb_notify_path;
	private final String selflink_avpn;
	private final String selflink_fqdn;

	private final String p_interface_path;
	private final String physical_link_path;
	private final String customer_path;

	private final String service_path;
	private final String site_pair_set_path;

	private final int connection_timeout;
	private final int read_timeout;

	// 1602
	private final String query_nodes_path;
	private final String update_path;

	private final String application_id;

	// authentication credentials
	private String user_name;
	private String user_password;

	private final MetricLogger ml = new MetricLogger();

	private final AAIRequestExecutor executor;

	public AAIService(URL propURL) {
		LOG.info("Entered AAIService.ctor");
		Properties props = null;
		try {
			props = initialize(propURL);
			AAIRequest.setProperties(props, this);

		} catch(Exception exc){
			LOG.error("AicAAIResource.static", exc);
		}

        executor = new AAIRequestExecutor();

        user_name			= props.getProperty(CLIENT_NAME);
        user_password		= props.getProperty(CLIENT_PWWD);

        if(user_name == null || user_name.isEmpty()){
        	LOG.debug("Basic user name is not set");
        }
        if(user_password == null || user_password.isEmpty()) {
        	LOG.debug("Basic password is not set");
        }

		truststore_path 	= props.getProperty(TRUSTSTORE_PATH);
		truststore_password = props.getProperty(TRUSTSTORE_PSSWD);

		target_uri 			= props.getProperty(TARGET_URI);
		query_path 			= props.getProperty(QUERY_PATH);
		update_path 		= props.getProperty(UPDATE_PATH);

		String applicationId =props.getProperty(APPLICATION_ID);
		if(applicationId == null || applicationId.isEmpty()) {
			applicationId = "SDNC";
		}
		application_id = applicationId;

		// connection timeout
		int tmpConnectionTimeout = 30000;
		int tmpReadTimeout = 30000;

		try {
			String tmpValue = null;
			tmpValue = props.getProperty(CONNECTION_TIMEOUT, "30000");
			tmpConnectionTimeout = Integer.parseInt(tmpValue);
			tmpValue = props.getProperty(READ_TIMEOUT, "30000");
			tmpReadTimeout = Integer.parseInt(tmpValue);
		} catch(Exception exc) {
			LOG.error("Failed setting connection timeout", exc);
			tmpConnectionTimeout = 30000;
			tmpReadTimeout = 30000;
		}
		connection_timeout = tmpConnectionTimeout;
		read_timeout = tmpReadTimeout;

		network_vce_path	= props.getProperty(NETWORK_VCE_PATH);

		network_vpe_path	= props.getProperty(NETWORK_VPE_PATH);

		network_vpls_pe_path =props.getProperty(NETWORK_VPLS_PE_PATH);

		network_complex_path  =props.getProperty(NETWORK_COMPLEX_PATH);

		network_pserver_path =props.getProperty(NETWORK_PSERVER_PATH);

		network_vserver_path =props.getProperty(NETWORK_VSERVER_PATH);

		ctag_pool_path =props.getProperty(CTAG_POOL_PATH);


		svc_instance_path	= props.getProperty(SVC_INSTANCE_PATH); // "/aai/v1/business/customers/customer/{customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances");
//		"/aai/v1/business/customers/customer/ma9181-203-customerid/service-subscriptions/service-subscription/ma9181%20Hosted%20Voice/service-instances";

//		svc_inst_qry_path	= props.getProperty(SVC_INST_QRY_PATH, "/aai/v1/search/generic-query?key=service-instance.service-instance-id:ma9181-204-instance&start-node-type=service-instance&include=service-instance");
		svc_inst_qry_path	= props.getProperty(SVC_INST_QRY_PATH); // "/aai/v1/search/generic-query?key=service-instance.service-instance-id:{svc-instance-id}&start-node-type=service-instance&include=service-instance");


		param_format 		= props.getProperty(QUERY_FORMAT, "filter=%s:%s");
		param_vnf_type 		= props.getProperty(PARAM_VNF_TYPE, "vnf-type");
		param_physical_location_id = props.getProperty(PARAM_PHYS_LOC_ID, "physical-location-id");
		param_service_type 	= props.getProperty(PARAM_SERVICE_TYPE, "service-type");

		// P-Interfaces
		p_interface_path   = props.getProperty(P_INTERFACE_PATH);

		// Physical Link
		physical_link_path = props.getProperty(PHYSICAL_LINK_PATH);

		generic_vnf_path	= props.getProperty(GENERIC_VNF_PATH);

		network_dvsswitch_path	= props.getProperty(NETWORK_DVSSWITCH_PATH);

		l3_network_path		= props.getProperty(L3_NETWORK_PATH);
		l3_network_path_query_name	= props.getProperty(L3_NETWORK_PATH_QUERY_NAME);

		vpn_binding_path	= props.getProperty(VPN_BINDING_PATH);

		vnf_image_path	= props.getProperty(VNF_IMAGE_PATH);
		vnf_image_query_path	= props.getProperty(VNF_IMAGE_QUERY_PATH);

		ubb_notify_path = props.getProperty(UBB_NOTIFY_PATH);
		selflink_avpn = props.getProperty(SELFLINK_AVPN);
		selflink_fqdn = props.getProperty(SELFLINK_FQDN);

		customer_path = props.getProperty(CUSTOMER_PATH);

		service_path  = props.getProperty(SERVICE_PATH);

		site_pair_set_path  = props.getProperty(SITE_PAIR_SET_PATH);

		query_nodes_path = props.getProperty(QUERY_NODES_PATH);

		String iche = props.getProperty(CERTIFICATE_HOST_ERROR);
		boolean host_error = false;
		if(iche != null && !iche.isEmpty()) {
			host_error = Boolean.valueOf(iche);
		}

		ignore_certificate_host_error = host_error;

		HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
			public boolean verify(String string,SSLSession ssls) {
				return ignore_certificate_host_error;
			}
		});

		if(truststore_path != null && truststore_password != null && (new File(truststore_path)).exists()) {
			System.setProperty("javax.net.ssl.trustStore", truststore_path);
			System.setProperty("javax.net.ssl.trustStorePassword", truststore_password);
		}

        LOG.info("AAIResource.ctor initialized.");
	}

	/**
	 *
	 * @param http_req_url
	 * @param method
	 * @return
	 * @throws Exception
	 */
	protected HttpURLConnection getConfiguredConnection(URL http_req_url, String method) throws Exception {
        HttpURLConnection con = (HttpURLConnection) http_req_url.openConnection();

        // Set up the connection properties
        con.setRequestProperty( "Connection", "close" );
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);
        con.setConnectTimeout( connection_timeout );
        con.setReadTimeout( read_timeout );
        con.setRequestMethod( method );
        con.setRequestProperty( "Accept", "application/json" );
        con.setRequestProperty( "Content-Type", "application/json" );
        con.setRequestProperty("X-FromAppId", application_id);
        con.setRequestProperty("X-TransactionId",TransactionIdTracker.getNextTransactionId());

	        if(user_name != null && !user_name.isEmpty() && user_password != null && !user_password.isEmpty()) {
	        	String basicAuth = "Basic " + new String(Base64.encodeBase64((user_name + ":" + user_password).getBytes()));
	        	con.setRequestProperty ("Authorization", basicAuth);
	        }

        return con;
	}


	@Override
	public GenericVnf requestGenericVnfData(String vnf_id) throws AAIServiceException {
		GenericVnf response = null;

		try {
			AAIRequest request = new GenericVnfRequest();
			request.addRequestProperty(GenericVnfRequest.GENERIC_VNF_ID, vnf_id);
			String rv = executor.get(request);
			if(rv != null) {
				ObjectMapper mapper = getObjectMapper();
				response = mapper.readValue(rv, GenericVnf.class);
			}
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn(new Object(){}.getClass().getEnclosingMethod().getName(), exc);
			throw new AAIServiceException(exc);
		}

		return response;

	}

	@Override
	public boolean postGenericVnfData(String vnf_id, GenericVnf data) throws AAIServiceException {
		boolean response = false;

		try {
			AAIRequest request = new GenericVnfRequest();
			request.addRequestProperty(GenericVnfRequest.GENERIC_VNF_ID, vnf_id);
			request.setRequestObject(data);
			response = executor.post(request);
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("requestGenericVnfData", exc);
			throw new AAIServiceException(exc);
		}
		return response;
	}

	@Override
	public boolean deleteGenericVnfData(String vnf_id, String resourceVersion) throws AAIServiceException {
		boolean response = false;
		InputStream inputStream = null;

		try {

			String request_url = target_uri+generic_vnf_path;

			String encoded_vnf = encodeQuery(vnf_id);
			request_url = request_url.replace("{vnf-id}", encoded_vnf) ;
			if(resourceVersion!=null) {
				request_url = request_url +"?resource-version="+resourceVersion;
			}
			URL http_req_url =	new URL(request_url);

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.DELETE);

            LOGwriteFirstTrace(HttpMethod.DELETE, http_req_url.toString());
            LOGwriteDateTrace("vnf_id", vnf_id);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
            String line = null;

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", stringBuilder.toString());
				response = true;
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
				LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
				response = false;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("deleteGenericVnfData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}



	@Override
	public SearchResults requestServiceInstanceURL(String svc_instance_id) throws AAIServiceException {
		SearchResults response = null;
		InputStream inputStream = null;

		try {
			String path = svc_inst_qry_path;
			path = path.replace("{svc-instance-id}", encodeQuery(svc_instance_id));

			String request_url = target_uri+path;
			URL http_req_url =	new URL(request_url);

			HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.GET);

            LOGwriteFirstTrace(HttpMethod.GET, http_req_url.toString());
            LOGwriteDateTrace("svc_instance_id", svc_instance_id);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );

            ObjectMapper mapper = new ObjectMapper();
		    AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
		 // if ONLY using JAXB annotations:
		    mapper.setAnnotationIntrospector(introspector);
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

			if (responseCode == HttpURLConnection.HTTP_OK) {
//				StringBuilder stringBuilder = new StringBuilder("\n");
//				String line = null;
//				while( ( line = reader.readLine() ) != null ) {
//					stringBuilder.append("\n").append( line );
//				}
//				LOG.info(stringBuilder.toString());
            	response = mapper.readValue(reader, SearchResults.class);
            	LOGwriteEndingTrace(HttpURLConnection.HTTP_OK, "SUCCESS", mapper.writeValueAsString(response));
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
            	LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
            	return response;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("requestServiceInstanceURL", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}


	@Override
	public ServiceInstance requestServiceInterfaceData(String svc_instance_id) throws AAIServiceException {
		ServiceInstance response = null;
		InputStream inputStream = null;

		try {
			SearchResults search_result = this.requestServiceInstanceURL(svc_instance_id);
			ResultData[] array = search_result.getResultData().toArray(new ResultData[0]);
			ResultData datum = array[0];

			String request_url = datum.getResourceLink();
			request_url = encodeCustomerURL(request_url);

			URL http_req_url =	new URL(request_url);
			HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.GET);

            LOGwriteFirstTrace(HttpMethod.GET, http_req_url.toString());
            LOGwriteDateTrace("svc_instance_id", svc_instance_id);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK) {
//				StringBuilder stringBuilder = new StringBuilder("\n");
//				String line = null;
//				while( ( line = reader.readLine() ) != null ) {
//					stringBuilder.append("\n").append( line );
//				}
//				LOG.info(stringBuilder.toString());
            	response = mapper.readValue(reader, ServiceInstance.class);
            	LOGwriteEndingTrace(HttpURLConnection.HTTP_OK, "SUCCESS", mapper.writeValueAsString(response));
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
            	LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
            	return response;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("requestServiceInterfaceData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}


	@Override
	public ServiceInstance requestServiceInterfaceData(String customer_id, String service_type, String svc_instance_id) throws AAIServiceException {
		ServiceInstance response = null;
		InputStream inputStream = null;

		try {
			String path = svc_instance_path;
			path = path.replace("{customer-id}",  encodeQuery(customer_id));
			path = path.replace("{service-type}", encodeQuery(service_type));
			String request_url = target_uri+path;
			if(!request_url.endsWith("/")) {
				request_url = request_url + "/service-instance/";
			} else {
				request_url = request_url + "service-instance/";
			}
			request_url = request_url + encodeQuery(svc_instance_id);
			URL http_req_url =	new URL(request_url);

			HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.GET);

            LOGwriteFirstTrace(HttpMethod.GET, http_req_url.toString());
            LOGwriteDateTrace("customer_id", customer_id);
            LOGwriteDateTrace("service_type", service_type);
            LOGwriteDateTrace("svc_instance_id", svc_instance_id);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK) {
//				StringBuilder stringBuilder = new StringBuilder("\n");
//				String line = null;
//				while( ( line = reader.readLine() ) != null ) {
//					stringBuilder.append("\n").append( line );
//				}
//				LOG.info(stringBuilder.toString());
            	response = mapper.readValue(reader, ServiceInstance.class);
            	LOGwriteEndingTrace(HttpURLConnection.HTTP_OK, "SUCCESS", mapper.writeValueAsString(response));
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
            	LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
            	return response;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("requestServiceInterfaceData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}

	@Override
	public boolean postServiceInterfaceData(String customer_id, String service_type, String svc_instance_id, ServiceInstance request) throws AAIServiceException {

		InputStream inputStream = null;

		try {
			ObjectMapper mapper = getObjectMapper();
			String json_text = mapper.writeValueAsString(request);

			String path = svc_instance_path;
			path = path.replace("{customer-id}",  encodeQuery(customer_id));
			path = path.replace("{service-type}", encodeQuery(service_type));
			String request_url = target_uri+path;
			if(!request_url.endsWith("/")) {
				request_url = request_url + "/service-instance/";
			} else {
				request_url = request_url + "service-instance/";
			}
			request_url = request_url + encodeQuery(svc_instance_id);
			URL http_req_url =	new URL(request_url);

			HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.PUT);

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
            osw.write(json_text);
            osw.flush();
            osw.close();


            LOGwriteFirstTrace("PUT", request_url);
            LOGwriteDateTrace("customer_id", customer_id);
            LOGwriteDateTrace("service_type", service_type);
            LOGwriteDateTrace("svc_instance_id", svc_instance_id);
            LOGwriteDateTrace("RelationshipList", json_text);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            BufferedReader reader;
            String line = null;
            reader = new BufferedReader( new InputStreamReader( inputStream ) );

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", (stringBuilder != null) ? stringBuilder.toString() : "{no-data}");
				return true;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));

            	throw new AAIServiceException(responseCode, errorresponse);
            }
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("postServiceInterfaceData", exc);
			throw new AAIServiceException(exc);
		} finally {
			try {
				if(inputStream != null)
				inputStream.close();
			} catch (Exception exc) {

			}
		}
	}

	/*
	 * Supporting methosd
	 */
	private URL getRequestURL(String service_type, String vnf_type, String physical_location_id) throws MalformedURLException, URISyntaxException, UnsupportedEncodingException {

		StringBuilder query = new StringBuilder();
		query.append("filter=vnf-type:").append(encodeQuery(vnf_type));
		query.append("&filter=service-type:").append(encodeQuery(service_type));
		query.append("&filter=physical-location-id:").append(physical_location_id);

		UriBuilder builder = javax.ws.rs.core.UriBuilder.fromUri(target_uri).path(query_path).replaceQuery(query.toString());
//		String y = x.build().toString();

		return builder.build().toURL();

	}

	private static Properties initialize(URL url ) throws ConfigurationException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		InputStream is = null;
		Properties props = new Properties();

		try {
			if(LOG.isDebugEnabled())
				LOG.info("Property file is: " + url.toString());

			is = url.openStream();

			props.load(is);
			if(LOG.isDebugEnabled()) {
				LOG.info("Properties loaded: " + props.size());
				Enumeration<Object> en = props.keys();

				while(en.hasMoreElements()) {
					String key = (String)en.nextElement();
					String property = props.getProperty(key);
					LOG.debug(key + " : " + property);
				}
			}
		} catch (Exception e) {
			throw new ConfigurationException("Could not load properties file.", e);
		}
		return props;
	}

	static class TransactionIdTracker {
//		protected static AtomicLong tracker = new AtomicLong();

		public static String getNextTransactionId() {
//			long id = tracker.getAndIncrement();
//			String transactionId = String.format("N%016X", id);
			String transactionId = UUID.randomUUID().toString();
			return transactionId;
		}

	}

	protected void LOGwriteFirstTrace(String method, String url) {
		String time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(System.currentTimeMillis());
		LOG.info("A&AI transaction :");
		LOG.info("Request Time : " + time + ", Method : " + method);
		LOG.info("Request URL : "+ url);
	}

	protected void LOGwriteDateTrace(String name, String data) {
		LOG.info("Input - " + name  + " : " + data);
	}

	protected void LOGwriteEndingTrace(int response_code, String comment, String data) {
		LOG.info("Response code : " + response_code +", " + comment);
		LOG.info(String.format("Response data : %s", data));
	}

	protected String encodeQuery(String param) throws UnsupportedEncodingException {
		return URLEncoder.encode(param, "UTF-8").replace("+", "%20");
	}

	private String encodeCustomerURL(final String selection)
	{
		String encrypted_url = selection;
		String apnpattern =
				"/aai/v2/business/customers/customer/(.+)/service-subscriptions/service-subscription/(.+)/service-instances/service-instance/(.+)/";
		Pattern pattern = Pattern.compile(apnpattern);

		try {
			URL url =	new URL(selection);
			String path = url.getPath();

			LOG.info("Trying to match apn to <" + path + ">");

			Matcher matcher = pattern.matcher(path);

			while(matcher.find()) {
				String customer = matcher.group(1);
				String subscription = matcher.group(2);
				String service = matcher.group(3);

				encrypted_url = selection.replace(customer, encodeQuery(customer));
				encrypted_url = encrypted_url.replace(subscription, encodeQuery(subscription));
				encrypted_url = encrypted_url.replace(service, encodeQuery(service));
			}
		} catch (Exception e) {
			LOG.warn("", e);
		}

		return encrypted_url;
	}

	/*
	 * (non-Javadoc)
	 * @see org.openecomp.sdnc.sli.aai.AAIClient#requestVServersData(java.lang.String, java.lang.String)
	 */
	@Override
	public Vserver requestVServerData(String tenant_id, String vserver_id, String cloudOwner, String cloudRegionId)	throws AAIServiceException {
		Vserver response = null;

		try {
			AAIRequest request = new VserverRequest();
			request.addRequestProperty(TenantRequest.TENANT_TENANT_ID, tenant_id);
			request.addRequestProperty(VserverRequest.VSERVER_VSERVER_ID, vserver_id);
			request.addRequestProperty(CloudRegionRequest.CLOUD_REGION_CLOUD_OWNER, cloudOwner);
			request.addRequestProperty(CloudRegionRequest.CLOUD_REGION_CLOUD_REGION_ID, cloudRegionId);

			String rv = executor.get(request);
			if(rv != null) {
				ObjectMapper mapper = getObjectMapper();
				response = mapper.readValue(rv, Vserver.class);
			}
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn(new Object(){}.getClass().getEnclosingMethod().getName(), exc);
			throw new AAIServiceException(exc);
		}
		return response;
	}


	@Override
	public boolean postVServerData(String tenantId, String vserverId, String cloudOwner, String cloudRegionId, Vserver request) throws AAIServiceException {
		InputStream inputStream = null;

		try {

			ObjectMapper mapper = getObjectMapper();
			String json_text = mapper.writeValueAsString(request);

			String local_network_complexes_path = network_vserver_path.replace("{tenant-id}", encodeQuery(tenantId));
			local_network_complexes_path = local_network_complexes_path.replace("{vserver-id}", encodeQuery(vserverId));

			local_network_complexes_path = local_network_complexes_path.replace("{cloud-owner}", encodeQuery(cloudOwner));
			local_network_complexes_path = local_network_complexes_path.replace("{cloud-region-id}", encodeQuery(cloudRegionId));

			String request_url = target_uri+local_network_complexes_path;

			URL http_req_url =	new URL(request_url);

			HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.PUT);

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
            osw.write(json_text);
            osw.flush();
            osw.close();


            LOGwriteFirstTrace("PUT", request_url);
            LOGwriteDateTrace("tenant-id", tenantId);
            LOGwriteDateTrace("vserver-id", vserverId);
            LOGwriteDateTrace("cloud-owner", cloudOwner);
            LOGwriteDateTrace("cloud-region-id", cloudRegionId);

            LOGwriteDateTrace("Vserver", json_text);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            BufferedReader reader;
            String line = null;
            reader = new BufferedReader( new InputStreamReader( inputStream ) );

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", (stringBuilder != null) ? stringBuilder.toString() : "{no-data}");
				return true;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));

            	throw new AAIServiceException(responseCode, errorresponse);
            }
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("postVServerData", exc);
			throw new AAIServiceException(exc);
		} finally {
			try {
				if(inputStream != null)
				inputStream.close();
			} catch (Exception exc) {

			}
		}
	}

	@Override
	public boolean deleteVServerData(String tenant_id, String vserver_id, String cloudOwner, String cloudRegionId, String resourceVersion) throws AAIServiceException {
		boolean response = false;
		InputStream inputStream = null;

		try {
			String local_network_complexes_path = network_vserver_path.replace("{tenant-id}", encodeQuery(tenant_id));
			local_network_complexes_path = local_network_complexes_path.replace("{vserver-id}", encodeQuery(vserver_id));
			local_network_complexes_path = local_network_complexes_path.replace("{cloud-owner}", encodeQuery(cloudOwner));
			local_network_complexes_path = local_network_complexes_path.replace("{cloud-region-id}", encodeQuery(cloudRegionId));

			String request_url = target_uri+local_network_complexes_path;
			if(resourceVersion!=null) {
				request_url = request_url +"?resource-version="+resourceVersion;
			}
			URL http_req_url =	new URL(request_url);

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.DELETE);

            LOGwriteFirstTrace(HttpMethod.DELETE, http_req_url.toString());
            LOGwriteDateTrace("tenant_id", tenant_id);
            LOGwriteDateTrace("vserver_id", vserver_id);
            LOGwriteDateTrace("cloud-owner", cloudOwner);
            LOGwriteDateTrace("cloud-region-id", cloudRegionId);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
            String line = null;

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", stringBuilder.toString());
				response = true;
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
				LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
				response = false;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("deleteVServerData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}


	//================== End of DvsSwitch =================
	//==================== PhysicalLink ======================
	@Override
	public PhysicalLink  requestPhysicalLinkData(String linkName) throws AAIServiceException {
		PhysicalLink response = null;
		InputStream inputStream = null;

		try {
			String request_url = target_uri+physical_link_path;

			String encoded_vnf = encodeQuery(linkName);
			request_url = request_url.replace("{link-name}", encoded_vnf) ;
			URL http_req_url =	new URL(request_url);

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.GET);

            LOGwriteFirstTrace(HttpMethod.GET, http_req_url.toString());
            LOGwriteDateTrace("link-name", linkName);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK) {
//				StringBuilder stringBuilder = new StringBuilder("\n");
//				String line = null;
//				while( ( line = reader.readLine() ) != null ) {
//					stringBuilder.append("\n").append( line );
//				}
//				LOG.info(stringBuilder.toString());
            	response = mapper.readValue(reader, PhysicalLink.class);
            	LOGwriteEndingTrace(HttpURLConnection.HTTP_OK, "SUCCESS", mapper.writeValueAsString(response));
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            	LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
            	return response;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("requestPhysicalLinkData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}

	@Override
	public boolean postPhysicalLinkData(String linkName, PhysicalLink request) throws AAIServiceException {
		InputStream inputStream = null;

		try {

			ObjectMapper mapper = getObjectMapper();
			String json_text = mapper.writeValueAsString(request);

			String request_url = target_uri+physical_link_path;
			String encoded_vnf = encodeQuery(linkName);
			request_url = request_url.replace("{link-name}", encoded_vnf) ;
			URL http_req_url =	new URL(request_url);

			HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.PUT);

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
            osw.write(json_text);
            osw.flush();
            osw.close();


            LOGwriteFirstTrace("PUT", request_url);
            LOGwriteDateTrace("link-name", linkName);
            LOGwriteDateTrace("PhysicalLink", json_text);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            BufferedReader reader;
            String line = null;
            reader = new BufferedReader( new InputStreamReader( inputStream ) );

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", (stringBuilder != null) ? stringBuilder.toString() : "{no-data}");
				return true;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));

            	throw new AAIServiceException(responseCode, errorresponse);
            }
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("postPhysicalLinkData", exc);
			throw new AAIServiceException(exc);
		} finally {
			try {
				if(inputStream != null)
				inputStream.close();
			} catch (Exception exc) {

			}
		}
	}

	@Override
	public boolean deletePhysicalLinkData(String linkName, String resourceVersion) throws AAIServiceException {
		boolean response = false;
		InputStream inputStream = null;

		try {
			String request_url = target_uri+physical_link_path;
			String encoded_vnf = encodeQuery(linkName);
			request_url = request_url.replace("{link-name}", encoded_vnf) ;
			if(resourceVersion!=null) {
				request_url = request_url +"?resource-version="+resourceVersion;
			}
			URL http_req_url =	new URL(request_url);

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.DELETE);

            LOGwriteFirstTrace("DELETE", http_req_url.toString());
            LOGwriteDateTrace("link-name", linkName);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
            String line = null;

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", stringBuilder.toString());
				response = true;
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
				LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
				response = false;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("deletePhysicalLinkData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}
	//================== End of PhysicalLink =================
	//==================== PInterface ======================
	@Override
	public PInterface  requestPInterfaceData(String hostname, String interfaceName) throws AAIServiceException {
		PInterface response = null;

		try {
			AAIRequest request = new PInterfaceRequest();
			request.addRequestProperty(PInterfaceRequest.PINTERFACE_INTERFACE_NAME, interfaceName);
			request.addRequestProperty(PInterfaceRequest.HOSTNAME, hostname);
			String rv = executor.get(request);
			if(rv != null) {
				ObjectMapper mapper = getObjectMapper();
				response = mapper.readValue(rv, PInterface.class);
			}
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn(new Object(){}.getClass().getEnclosingMethod().getName(), exc);
			throw new AAIServiceException(exc);
		}
		return response;
	}

	@Override
	public boolean postPInterfaceData(String hostname, String interfaceName, PInterface request) throws AAIServiceException {
		InputStream inputStream = null;

		try {

			ObjectMapper mapper = getObjectMapper();
			String json_text = mapper.writeValueAsString(request);

			String request_url = target_uri+p_interface_path;
			String encoded_vnf = encodeQuery(hostname);
			request_url = request_url.replace("{hostname}", encoded_vnf) ;
			encoded_vnf = encodeQuery(interfaceName);
			request_url = request_url.replace("{interface-name}", encoded_vnf) ;
			URL http_req_url =	new URL(request_url);

			HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.PUT);

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
            osw.write(json_text);
            osw.flush();
            osw.close();


            LOGwriteFirstTrace("PUT", request_url);
            LOGwriteDateTrace("hostname", hostname);
            LOGwriteDateTrace("interface-name", interfaceName);
            LOGwriteDateTrace("PInterface", json_text);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            BufferedReader reader;
            String line = null;
            reader = new BufferedReader( new InputStreamReader( inputStream ) );

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", (stringBuilder != null) ? stringBuilder.toString() : "{no-data}");
				return true;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));

            	throw new AAIServiceException(responseCode, errorresponse);
            }
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("postPInterfaceData", exc);
			throw new AAIServiceException(exc);
		} finally {
			try {
				if(inputStream != null)
				inputStream.close();
			} catch (Exception exc) {

			}
		}
	}

	@Override
	public boolean deletePInterfaceData(String hostname, String interfaceName, String resourceVersion) throws AAIServiceException {
		boolean response = false;
		InputStream inputStream = null;

		try {
			String request_url = target_uri+p_interface_path;
			String encoded_vnf = encodeQuery(hostname);
			request_url = request_url.replace("{hostname}", encoded_vnf) ;
			encoded_vnf = encodeQuery(interfaceName);
			request_url = request_url.replace("{interface-name}", encoded_vnf) ;
			if(resourceVersion!=null) {
				request_url = request_url +"?resource-version="+resourceVersion;
			}
			URL http_req_url =	new URL(request_url);

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.DELETE);

            LOGwriteFirstTrace("DELETE", http_req_url.toString());
            LOGwriteDateTrace("hostname", hostname);
            LOGwriteDateTrace("interface-name", interfaceName);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
            String line = null;

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", stringBuilder.toString());
				response = true;
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
				LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
				response = false;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("deletePInterfaceData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}

	//==================== Service ======================
	@Override
	public Service requestServiceData(String serviceId) throws AAIServiceException {
		Service response = null;
		InputStream inputStream = null;

		try {
			String request_url = target_uri+service_path;

			String encoded_vnf = encodeQuery(serviceId);
			request_url = request_url.replace("{service-id}", encoded_vnf) ;
			URL http_req_url =	new URL(request_url);

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.GET);

            LOGwriteFirstTrace(HttpMethod.GET, http_req_url.toString());
            LOGwriteDateTrace("service-id", serviceId);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK) {
//				StringBuilder stringBuilder = new StringBuilder("\n");
//				String line = null;
//				while( ( line = reader.readLine() ) != null ) {
//					stringBuilder.append("\n").append( line );
//				}
//				LOG.info(stringBuilder.toString());
            	response = mapper.readValue(reader, Service.class);
            	LOGwriteEndingTrace(HttpURLConnection.HTTP_OK, "SUCCESS", mapper.writeValueAsString(response));
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("requestServiceData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}

	@Override
	public boolean postServiceData(String linkName, Service request) throws AAIServiceException {
		InputStream inputStream = null;

		try {

			ObjectMapper mapper = getObjectMapper();
			String json_text = mapper.writeValueAsString(request);

			String request_url = target_uri+service_path;
			String encoded_vnf = encodeQuery(linkName);
			request_url = request_url.replace("{service-id}", encoded_vnf) ;
			URL http_req_url =	new URL(request_url);

			HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.PUT);

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
            osw.write(json_text);
            osw.flush();
            osw.close();


            LOGwriteFirstTrace("PUT", request_url);
            LOGwriteDateTrace("service-id", linkName);
            LOGwriteDateTrace("Service", json_text);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            BufferedReader reader;
            String line = null;
            reader = new BufferedReader( new InputStreamReader( inputStream ) );

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", (stringBuilder != null) ? stringBuilder.toString() : "{no-data}");
				return true;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));

            	throw new AAIServiceException(responseCode, errorresponse);
            }
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("postServiceData", exc);
			throw new AAIServiceException(exc);
		} finally {
			try {
				if(inputStream != null)
				inputStream.close();
			} catch (Exception exc) {

			}
		}
	}

	@Override
	public boolean deleteServiceData(String linkName, String resourceVersion) throws AAIServiceException {
		boolean response = false;
		InputStream inputStream = null;

		try {
			String request_url = target_uri+service_path;
			String encoded_vnf = encodeQuery(linkName);
			request_url = request_url.replace("{service-id}", encoded_vnf) ;
			if(resourceVersion!=null) {
				request_url = request_url +"?resource-version="+resourceVersion;
			}
			URL http_req_url =	new URL(request_url);

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.DELETE);

            LOGwriteFirstTrace("DELETE", http_req_url.toString());
            LOGwriteDateTrace("service-id", linkName);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
            String line = null;

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", stringBuilder.toString());
				response = true;
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
				LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
				response = false;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("deleteServiceData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}
	//================== End of Service =================



	// 1507 - Request
	@Override
	public VServer dataChangeRequestVServerData(URL url) throws AAIServiceException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		return this.getResource(url.toString(), VServer.class);
	}

	@Override
	public Pserver dataChangeRequestPServerData(URL url) throws AAIServiceException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		return this.getResource(url.toString(), Pserver.class);
	}

	@Override
	public AvailabilityZone dataChangeRequestAvailabilityZoneData(URL url) throws AAIServiceException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		return this.getResource(url.toString(), AvailabilityZone.class);
	}

	/* DELETE */
	public boolean dataChangeDeleteVServerData(URL url) throws AAIServiceException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		return deleteAAIEntity(url, new Object(){}.getClass().getEnclosingMethod().getName());
	}

	public boolean dataChangeDeleteCtagPoolData(URL url) throws AAIServiceException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		return deleteAAIEntity(url, new Object(){}.getClass().getEnclosingMethod().getName());
	}

	public boolean dataChangeDeleteVplsPeData(URL url) throws AAIServiceException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		return deleteAAIEntity(url, new Object(){}.getClass().getEnclosingMethod().getName());
	}

	public boolean dataChangeDeleteVpeData(URL url) throws AAIServiceException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		return deleteAAIEntity(url, new Object(){}.getClass().getEnclosingMethod().getName());
	}

	public boolean dataChangeDeleteDvsSwitchData(URL url) throws AAIServiceException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		return deleteAAIEntity(url, new Object(){}.getClass().getEnclosingMethod().getName());
	}
	//OAM-Network:
	public boolean dataChangeDeleteOAMNetworkData(URL url) throws AAIServiceException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		return deleteAAIEntity(url, new Object(){}.getClass().getEnclosingMethod().getName());
	}
	//Availability-Zone:
	public boolean dataChangeDeleteAvailabilityZoneData(URL url) throws AAIServiceException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		return deleteAAIEntity(url, new Object(){}.getClass().getEnclosingMethod().getName());
	}
	//Complex:
	public boolean dataChangeDeleteComplexData(URL url) throws AAIServiceException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		return deleteAAIEntity(url, new Object(){}.getClass().getEnclosingMethod().getName());
	}

	private boolean deleteAAIEntity(URL url, String caller) throws AAIServiceException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		boolean response = false;
		InputStream inputStream = null;

		try {
			URL http_req_url =	url;

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.DELETE);

            LOGwriteFirstTrace("DELETE", http_req_url.toString());


            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
            String line = null;

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", stringBuilder.toString());
				response = true;
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
				LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
				response = false;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn(caller, exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}

    /**
     * Generic method to GET json data from an A&AI callback URL.
     * Then convert that json to an Object.
     * If successful the Object is attempted to be cast to the type parameter.
     *
     * @param key
     *            callback url for A&AI
     * @param type
     *            the class of object that A&AI will return
     * @return the object created from json or null if the response code is not 200
     *
     * @throws AAIServiceException
     *             if empty or null key and or type or there's an error with processing
     */
    public <T> T dataChangeRequestAaiData(String key, Class<T> type) throws AAIServiceException {
        if (StringUtils.isEmpty(key) || type == null) {
            throw new AAIServiceException("Key is empty or null and or type is null");
        }

        T response = null;

        SvcLogicContext ctx = new SvcLogicContext();
        if(!key.contains(" = ") && isValidURL(key)) {
        	key = String.format("selflink = '%s'", key);
        }
        HashMap<String, String> nameValues = keyToHashMap(key, ctx);

        SelfLinkRequest request = new SelfLinkRequest(type);
        request.processRequestPathValues(nameValues);
        Object obj = this.getExecutor().query(request, type);
        response = type.cast(obj);

        return response != null ? type.cast(response) : response;
    }

	@Override
	public Pserver requestPServerData(String hostname) throws AAIServiceException {
		Pserver response = null;
		InputStream inputStream = null;

		try {
			String request_url = target_uri+network_pserver_path;
			String encoded_vnf = encodeQuery(hostname);
			request_url = request_url.replace("{hostname}", encoded_vnf) ;
			URL http_req_url =	new URL(request_url);

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.GET);

            LOGwriteFirstTrace(HttpMethod.GET, http_req_url.toString());
            LOGwriteDateTrace("hostname", hostname);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK) {
//				StringBuilder stringBuilder = new StringBuilder("\n");
//				String line = null;
//				while( ( line = reader.readLine() ) != null ) {
//					stringBuilder.append("\n").append( line );
//				}
//				LOG.info(stringBuilder.toString());
            	response = mapper.readValue(reader, Pserver.class);
            	LOGwriteEndingTrace(HttpURLConnection.HTTP_OK, "SUCCESS", mapper.writeValueAsString(response));
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
            	LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
            	return response;
            } else {
            	ErrorResponse errorresponse = new ErrorResponse();
            	try {
            		errorresponse = mapper.readValue(reader, ErrorResponse.class);
            		LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            		throw new AAIServiceException(responseCode, errorresponse);
            	} catch(Exception exc) {
            	}
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("requestPServerData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}

	@Override
	public boolean postPServerData(String hostname, Pserver request) throws AAIServiceException {
		InputStream inputStream = null;

		try {

			ObjectMapper mapper = getObjectMapper();
			String json_text = mapper.writeValueAsString(request);

			String request_url = target_uri+network_pserver_path;

			request_url = request_url.replace("{hostname}", encodeQuery(hostname)) ;
			URL http_req_url =	new URL(request_url);

			HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.PUT);

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
            osw.write(json_text);
            osw.flush();
            osw.close();


            LOGwriteFirstTrace("PUT", request_url);
            LOGwriteDateTrace("hostname", hostname);
            LOGwriteDateTrace("PServer", json_text);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            BufferedReader reader;
            String line = null;
            reader = new BufferedReader( new InputStreamReader( inputStream ) );

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", (stringBuilder != null) ? stringBuilder.toString() : "{no-data}");
				return true;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));

            	throw new AAIServiceException(responseCode, errorresponse);
            }
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("postPServerData", exc);
			throw new AAIServiceException(exc);
		} finally {
			try {
				if(inputStream != null)
				inputStream.close();
			} catch (Exception exc) {

			}
		}
	}

	@Override
	public boolean deletePServerData(String hostname, String resourceVersion) throws AAIServiceException {
		boolean response = false;
		InputStream inputStream = null;

		try {

			String request_url = target_uri+network_pserver_path;

			String encoded_vnf = encodeQuery(hostname);
			request_url = request_url.replace("{hostname}", encoded_vnf) ;
			if(resourceVersion!=null) {
				request_url = request_url +"?resource-version="+resourceVersion;
			}
			URL http_req_url =	new URL(request_url);

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.DELETE);

            LOGwriteFirstTrace("DELETE", http_req_url.toString());
            LOGwriteDateTrace("hostname", hostname);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
            String line = null;

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", stringBuilder.toString());
				response = true;
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
				LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
				response = true;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("deletePServersData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}


	@Override
	public L3Network requestL3NetworkData(String networkId) throws AAIServiceException {
		L3Network response = null;
		InputStream inputStream = null;

		try {
			String request_url = target_uri+l3_network_path;
			String encoded_vnf = encodeQuery(networkId);
			request_url = request_url.replace("{network-id}", encoded_vnf) ;
			URL http_req_url =	new URL(request_url);

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.GET);

            LOGwriteFirstTrace(HttpMethod.GET, http_req_url.toString());
            LOGwriteDateTrace("network-id", networkId);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK) {
//				StringBuilder stringBuilder = new StringBuilder("\n");
//				String line = null;
//				while( ( line = reader.readLine() ) != null ) {
//					stringBuilder.append("\n").append( line );
//				}
//				LOG.info(stringBuilder.toString());
            	response = mapper.readValue(reader, L3Network.class);
            	LOGwriteEndingTrace(HttpURLConnection.HTTP_OK, "SUCCESS", mapper.writeValueAsString(response));
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
            	LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
            	return response;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("requestL3NetworkData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}

	@Override
	public L3Network requestL3NetworkQueryByName(String networkName) throws AAIServiceException {
		L3Network response = null;
		InputStream inputStream = null;

		try {
			String request_url = target_uri+l3_network_path_query_name;
			String encoded_name = encodeQuery(networkName);
			request_url = request_url.replace("{network-name}", encoded_name) ;
			URL http_req_url =	new URL(request_url);

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.GET);

            LOGwriteFirstTrace(HttpMethod.GET, http_req_url.toString());
            LOGwriteDateTrace("network-name", networkName);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK) {
//				StringBuilder stringBuilder = new StringBuilder("\n");
//				String line = null;
//				while( ( line = reader.readLine() ) != null ) {
//					stringBuilder.append("\n").append( line );
//				}
//				LOG.info(stringBuilder.toString());
            	response = mapper.readValue(reader, L3Network.class);
            	LOGwriteEndingTrace(HttpURLConnection.HTTP_OK, "SUCCESS", mapper.writeValueAsString(response));
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
            	LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
            	return response;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("requestL3NetworkData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}

	@Override
	public boolean postL3NetworkData(String networkId, L3Network request) throws AAIServiceException {
		InputStream inputStream = null;

		try {

			ObjectMapper mapper = getObjectMapper();
			String json_text = mapper.writeValueAsString(request);

			String request_url = target_uri+l3_network_path;

			String encoded_vnf = encodeQuery(networkId);
			request_url = request_url.replace("{network-id}", encoded_vnf) ;
			URL http_req_url =	new URL(request_url);

			HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.PUT);

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
            osw.write(json_text);
            osw.flush();
            osw.close();


            LOGwriteFirstTrace("PUT", request_url);
            LOGwriteDateTrace("network-id", networkId);
            LOGwriteDateTrace("GenericVnf", json_text);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            BufferedReader reader;
            String line = null;
            reader = new BufferedReader( new InputStreamReader( inputStream ) );

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", (stringBuilder != null) ? stringBuilder.toString() : "{no-data}");
				return true;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));

            	throw new AAIServiceException(responseCode, errorresponse);
            }
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("postL3NetworkData", exc);
			throw new AAIServiceException(exc);
		} finally {
			try {
				if(inputStream != null)
				inputStream.close();
			} catch (Exception exc) {

			}
		}
	}

	@Override
	public boolean deleteL3NetworkData(String networkId, String resourceVersion) throws AAIServiceException {
		boolean response = false;
		InputStream inputStream = null;

		try {
			String request_url = target_uri+l3_network_path;

			String encoded_vnf = encodeQuery(networkId);
			request_url = request_url.replace("{network-id}", encoded_vnf) ;
			if(resourceVersion!=null) {
				request_url = request_url +"?resource-version="+resourceVersion;
			}
			URL http_req_url =	new URL(request_url);

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.DELETE);

            LOGwriteFirstTrace("DELETE", http_req_url.toString());
            LOGwriteDateTrace("network-id", networkId);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
            String line = null;

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", stringBuilder.toString());
				response = true;
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
				LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
				response = false;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("deleteL3NetworkData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}


	public boolean sendNotify(NotifyEvent event, String serviceInstanceId, String pathCode) throws AAIServiceException {
		InputStream inputStream = null;

		try {

			String selfLink = selflink_fqdn;
			if(SELFLINK_AVPN != null && SELFLINK_AVPN.equals(pathCode)) {
				selfLink = selflink_avpn;
			}
			selfLink = selfLink.replace("{service-instance-id}", encodeQuery(serviceInstanceId));
			event.setSelflink(selfLink);

			ObjectMapper mapper = getObjectMapper();
			String json_text = mapper.writeValueAsString(event);

			String request_url = target_uri+ubb_notify_path;
			URL http_req_url =	new URL(request_url);

			HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.PUT);

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
            osw.write(json_text);
            osw.flush();
            osw.close();


            LOGwriteFirstTrace("PUT", request_url);
            LOGwriteDateTrace("NotifyEvent", json_text);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            BufferedReader reader;
            String line = null;
            reader = new BufferedReader( new InputStreamReader( inputStream ) );

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", (stringBuilder != null) ? stringBuilder.toString() : "{no-data}");
				return true;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));

            	throw new AAIServiceException(responseCode, errorresponse);
            }
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("sendNotify", exc);
			throw new AAIServiceException(exc);
		} finally {
			try {
				if(inputStream != null)
				inputStream.close();
			} catch (Exception exc) {

			}
		}
	}

	@Override
	public SearchResults requestNodeQuery(String node_type, String entityIdentifier, String entityName) throws AAIServiceException {
		SearchResults response = null;
		InputStream inputStream = null;

		try {
			String request_url = target_uri+query_nodes_path;
			request_url = request_url.replace("{node-type}", encodeQuery(node_type)) ;
			request_url = request_url.replace("{entity-identifier}", entityIdentifier) ;
			request_url = request_url.replace("{entity-name}", encodeQuery(entityName)) ;
			URL http_req_url =	new URL(request_url);

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.GET);

            LOGwriteFirstTrace(HttpMethod.GET, http_req_url.toString());
            LOGwriteDateTrace("node_type", node_type);
            LOGwriteDateTrace("vnf_name", entityName);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK) {
            	response = mapper.readValue(reader, SearchResults.class);
            	LOGwriteEndingTrace(HttpURLConnection.HTTP_OK, "SUCCESS", mapper.writeValueAsString(response));
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            	LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
            	return response;
			} else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("requestNodeQuery", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;

	}


	@Override
	public String requestDataByURL(URL url) throws AAIServiceException {

		if(url ==  null) {
			throw new NullPointerException();
		}

		String response = null;
		InputStream inputStream = null;

		try {
			URL http_req_url = url;

            HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.GET);

            LOGwriteFirstTrace(HttpMethod.GET, http_req_url.toString());

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				StringBuilder stringBuilder = new StringBuilder("\n");
				String line = null;
				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOG.info(stringBuilder.toString());
//            	response = mapper.readValue(reader, String.class);
				response = stringBuilder.toString();
            	LOGwriteEndingTrace(HttpURLConnection.HTTP_OK, "SUCCESS", mapper.writeValueAsString(response));
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
				LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
				response = null;
			} else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("requestNetworkVceData", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}


	@Override
	public GenericVnf requestGenericVnfeNodeQuery(String vnf_name) throws AAIServiceException {

		if(vnf_name == null) {
			throw new NullPointerException();
		}

		GenericVnf entity = null;
		SearchResults resp = this.requestNodeQuery("generic-vnf", "vnf-name", vnf_name);

		List<ResultData> resultDataList = resp.getResultData();

		try {
			for (ResultData datum : resultDataList) {
				String data_type = datum.getResourceType();
				URL url = new URL(datum.getResourceLink());
				entity = this.getResource(url.toString(), GenericVnf.class);
			}
		}
		catch (Exception e)
		{
			LOG.error("Caught exception", e);
		}
		return entity;
	}

	@Override
	public Vserver requestVServerDataByURL(URL url) throws AAIServiceException {

		if(url == null) {
			throw new NullPointerException();
		}

		Vserver entity = null;

		try {
				entity = this.getResource(url.toString(), Vserver.class);
		}
		catch (Exception e)
		{
			LOG.error("Caught exception", e);
		}
		return entity;
	}

	@Override
	public URL requestVserverURLNodeQuery(String vserver_name) throws AAIServiceException {

		if(vserver_name == null) {
			throw new NullPointerException();
		}

		URL entity = null;
		SearchResults resp = this.requestNodeQuery("vserver", "vserver-name", vserver_name);

		List<ResultData> resultDataList = resp.getResultData();

		try {
			for (ResultData datum : resultDataList) {
				String data_type = datum.getResourceType();
				entity = new URL(datum.getResourceLink());
			}
		}
		catch (Exception e)
		{
			LOG.error("Caught exception", e);
		}
		return entity;
	}

	class AAIRequestExecutor implements AAIExecutorInterface {

		@Override
		public String get(AAIRequest request) throws AAIServiceException {
			String response = null;
			InputStream inputStream = null;
			HttpURLConnection con = null;
			URL requestUrl = null;

			try {

	            if(request.getRequestObject() != null) {
	            	 con = getConfiguredConnection(requestUrl = request.getRequestUrl(HttpMethod.POST, null), HttpMethod.POST);
	            	String json_text = request.toJSONString();
	            	LOGwriteDateTrace("data", json_text);
	            	logMetricRequest("POST "+requestUrl.getPath(), json_text);
	            	OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
	            	osw.write(json_text);
	            	osw.flush();
	            } else {
	            	con = getConfiguredConnection(requestUrl = request.getRequestUrl(HttpMethod.GET, null), HttpMethod.GET);
	            	logMetricRequest("GET "+requestUrl.getPath(), "");
	            }

	            // Check for errors
	            String responseMessage = con.getResponseMessage();
	            int responseCode = con.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	            	inputStream = con.getInputStream();
	            } else {
	            	inputStream = con.getErrorStream();
	            }

	            // Process the response
	            LOG.debug("HttpURLConnection result:" + responseCode + " : " + responseMessage);
	            logMetricResponse(responseCode, responseMessage);

	            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
	            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );

	            ObjectMapper mapper = getObjectMapper();

				if (responseCode == HttpURLConnection.HTTP_OK) {
					StringBuilder stringBuilder = new StringBuilder();
					String line = null;
					while( ( line = reader.readLine() ) != null ) {
						stringBuilder.append( line );
					}
					response = stringBuilder.toString();
					try {
						Object object = mapper.readValue(response, Object.class);
						LOGwriteEndingTrace(HttpURLConnection.HTTP_OK, responseMessage, mapper.writeValueAsString(object));
					} catch(Exception exc) {
						LOGwriteEndingTrace(HttpURLConnection.HTTP_OK, responseMessage, mapper.writeValueAsString(response));
					}
	            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
	            	LOGwriteEndingTrace(responseCode, responseMessage, "Entry does not exist.");
	            	ErrorResponse errorresponse = null;
	            	try {
	            		errorresponse = mapper.readValue(reader, ErrorResponse.class);
	            	} catch(Exception exc) {
	            		errorresponse = new ErrorResponse();
	            		RequestError requestError = new RequestError();
	            		ServiceException serviceException = new ServiceException();
	            		serviceException.setText("Entry does not exist.");
						requestError.setServiceException(serviceException);
						errorresponse.setRequestError(requestError );
	            	}
	            	throw new AAIServiceException(responseCode, errorresponse);
	            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
					StringBuilder stringBuilder = new StringBuilder();
					String line = null;
					while( ( line = reader.readLine() ) != null ) {
						stringBuilder.append( line );
					}
	            	LOGwriteEndingTrace(responseCode, responseMessage, stringBuilder.toString());
	            	ServiceException serviceException = new ServiceException();
	            	serviceException.setMessageId("HTTP_UNAUTHORIZED");
	            	serviceException.setText(stringBuilder.toString());
	            	RequestError requestError = new RequestError();
	            	requestError.setServiceException(serviceException);
	            	ErrorResponse errorresponse = new ErrorResponse();
	            	errorresponse.setRequestError(requestError);
	            	throw new AAIServiceException(responseCode, errorresponse);
	            } else {
//
//					StringBuilder stringBuilder = new StringBuilder("\n");
//					String line = null;
//					while( ( line = reader.readLine() ) != null ) {
//						stringBuilder.append("\n").append( line );
//					}
//					LOG.info(stringBuilder.toString());
//
//	            	ErrorResponse errorresponse = mapper.readValue(stringBuilder.toString(), ErrorResponse.class);
	            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
	            	LOGwriteEndingTrace(responseCode, responseMessage, mapper.writeValueAsString(errorresponse));
	            	throw new AAIServiceException(responseCode, errorresponse);
	            }

			} catch(AAIServiceException aaiexc) {
				throw aaiexc;
			} catch (Exception exc) {
				LOG.warn("GET", exc);
				throw new AAIServiceException(exc);
			} finally {
				if(inputStream != null){
					try {
						inputStream.close();
					} catch(Exception exc) {

					}
				}
			}
			return response;
		}

		@Override
		public Boolean post(AAIRequest request) throws AAIServiceException {
			InputStream inputStream = null;

			try {
				String resourceVersion = null;
				AAIDatum instance = request.getRequestObject();
				if(instance instanceof ResourceVersion) {
					resourceVersion = ((ResourceVersion)instance).getResourceVersion();
				}

				URL requestUrl = null;
				HttpURLConnection con = getConfiguredConnection(requestUrl = request.getRequestUrl(HttpMethod.PUT, resourceVersion), HttpMethod.PUT);
				ObjectMapper mapper = getObjectMapper();
				String json_text = request.toJSONString();

				LOGwriteDateTrace("data", json_text);
				logMetricRequest("PUT "+requestUrl.getPath(), json_text);

				OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
	            osw.write(json_text);
	            osw.flush();

	            // Check for errors
	            String responseMessage = con.getResponseMessage();
	            int responseCode = con.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
	            	inputStream = con.getInputStream();
	            } else {
	            	inputStream = con.getErrorStream();
	            }

	            LOG.debug("HttpURLConnection result:" + responseCode + " : " + responseMessage);
	            logMetricResponse(responseCode, responseMessage);

	            // Process the response
	            BufferedReader reader;
	            String line = null;
	            reader = new BufferedReader( new InputStreamReader( inputStream ) );
	            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

				if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
					StringBuilder stringBuilder = new StringBuilder();

					while( ( line = reader.readLine() ) != null ) {
						stringBuilder.append( line );
					}
					LOGwriteEndingTrace(responseCode, responseMessage, (stringBuilder != null) ? stringBuilder.toString() : "{no-data}");
					return true;
	            } else {
	            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
	            	LOGwriteEndingTrace(responseCode, responseMessage, mapper.writeValueAsString(errorresponse));

	            	throw new AAIServiceException(responseCode, errorresponse);
	            }
			} catch(AAIServiceException aaiexc) {
				throw aaiexc;
			} catch (Exception exc) {
				LOG.warn("AAIRequestExecutor.post", exc);
				throw new AAIServiceException(exc);
			} finally {
				try {
					if(inputStream != null)
					inputStream.close();
				} catch (Exception exc) {

				}
			}
		}

		@Override
		public Boolean delete(AAIRequest request, String resourceVersion) throws AAIServiceException {
			Boolean response = null;
			InputStream inputStream = null;

			if(resourceVersion == null) {
				throw new AAIServiceException("resource-version is required for DELETE request");
			}

			try {
				URL requestUrl = null;
	            HttpURLConnection conn = getConfiguredConnection(requestUrl = request.getRequestUrl(HttpMethod.DELETE, resourceVersion), HttpMethod.DELETE);
	            logMetricRequest("DELETE "+requestUrl.getPath(), "");
	            conn.setDoOutput(true);
//	            if(request.isDeleteDataRequired()) {
//					String json_text = request.toJSONString();
//
//					LOGwriteDateTrace("data", json_text);
//					OutputStream os = con.getOutputStream();
//		            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
//		            osw.write(json_text);
//		            osw.flush();
//	            }

	            // Check for errors
	            String responseMessage = conn.getResponseMessage();
	            int responseCode = conn.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
	            	inputStream = conn.getInputStream();
	            } else {
	            	inputStream = conn.getErrorStream();
	            }

	            // Process the response
	            LOG.debug("HttpURLConnection result:" + responseCode + " : " + responseMessage);
	            logMetricResponse(responseCode, responseMessage);

	            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
	            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
	            String line = null;

	            ObjectMapper mapper = getObjectMapper();

				if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
					StringBuilder stringBuilder = new StringBuilder();

					while( ( line = reader.readLine() ) != null ) {
						stringBuilder.append( line );
					}
					LOGwriteEndingTrace(responseCode, responseMessage, stringBuilder.toString());
					response = true;
				} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
					LOGwriteEndingTrace(responseCode, responseMessage, "Entry does not exist.");
					response = false;
	            } else {
	            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
	            	LOGwriteEndingTrace(responseCode, responseMessage, mapper.writeValueAsString(errorresponse));
	            	throw new AAIServiceException(responseCode, errorresponse);
	            }
			} catch(AAIServiceException aaiexc) {
				throw aaiexc;
			} catch (Exception exc) {
				LOG.warn("delete", exc);
				throw new AAIServiceException(exc);
			} finally {
				if(inputStream != null){
					try {
						inputStream.close();
					} catch(Exception exc) {

					}
				}
			}
			return response;
		}

		@Override
		public Object query(AAIRequest request, Class clas) throws AAIServiceException {
			Object response = null;
			InputStream inputStream = null;
			HttpURLConnection con = null;
			URL requestUrl = null;

			try {
	            con = getConfiguredConnection(requestUrl = request.getRequestQueryUrl(HttpMethod.GET), HttpMethod.GET);
	            logMetricRequest("GET "+requestUrl.getPath(), "");

	            // Check for errors
	            String responseMessage = con.getResponseMessage();
	            int responseCode = con.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	            	inputStream = con.getInputStream();
	            } else {
	            	inputStream = con.getErrorStream();
	            }

	            logMetricResponse(responseCode, responseMessage);
	            ObjectMapper mapper = getObjectMapper();

				if (responseCode == HttpURLConnection.HTTP_OK) {
					// Process the response
					BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
					response = mapper.readValue(reader, clas);
	            	LOGwriteEndingTrace(HttpURLConnection.HTTP_OK, "SUCCESS", mapper.writeValueAsString(response));
	            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
	            	LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
	            	return response;
				} else {
					BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
	            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
	            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
	            	throw new AAIServiceException(responseCode, errorresponse);
	            }

			} catch(AAIServiceException aaiexc) {
				throw aaiexc;
			} catch (Exception exc) {
				LOG.warn("GET", exc);
				throw new AAIServiceException(exc);
			} finally {
				if(inputStream != null){
					try {
						inputStream.close();
					} catch(Exception exc) {

					}
				}
				con = null;
			}
			return response;
		}

	}

	@Override
	public Tenant requestTenantData(String tenant_id, String cloudOwner, String cloudRegionId) throws AAIServiceException {
		Tenant response = null;

		try {
			AAIRequest request = new TenantRequest();
			request.addRequestProperty(TenantRequest.TENANT_ID, tenant_id);
			request.addRequestProperty(CloudRegionRequest.CLOUD_REGION_CLOUD_OWNER, cloudOwner);
			request.addRequestProperty(CloudRegionRequest.CLOUD_REGION_CLOUD_REGION_ID, cloudRegionId);

			String rv = executor.get(request);
			if(rv != null) {
				ObjectMapper mapper = getObjectMapper();
				response = mapper.readValue(rv, Tenant.class);
			}
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("requestTenantData", exc);
			throw new AAIServiceException(exc);
		}

		return response;
	}

	@Override
	public Tenant requestTenantDataByName(String tenant_name, String cloudOwner, String cloudRegionId) throws AAIServiceException {
		Tenant response = null;

		try {
			AAIRequest request = new TenantRequest();
			request.addRequestProperty(TenantRequest.TENANT_NAME, tenant_name);
			request.addRequestProperty(CloudRegionRequest.CLOUD_REGION_CLOUD_OWNER, cloudOwner);
			request.addRequestProperty(CloudRegionRequest.CLOUD_REGION_CLOUD_REGION_ID, cloudRegionId);
			Object rv = executor.query(request, Tenant.class);
			if(rv == null)
				return (Tenant)null;
			else
				response = (Tenant)rv;
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("requestTenantDataByName", exc);
			throw new AAIServiceException(exc);
		}

		return response;
	}


	@Override
	public boolean postTenantData(String tenant_id, String cloudOwner, String cloudRegionId, Tenant tenannt) throws AAIServiceException {
		Boolean response = null;

		try {
			AAIRequest request = new TenantRequest();
			request.addRequestProperty(TenantRequest.TENANT_ID, tenant_id);
			request.addRequestProperty(CloudRegionRequest.CLOUD_REGION_CLOUD_OWNER, cloudOwner);
			request.addRequestProperty(CloudRegionRequest.CLOUD_REGION_CLOUD_REGION_ID, cloudRegionId);
			request.setRequestObject(tenannt);
			response = executor.post(request);
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("postTenantData", exc);
			throw new AAIServiceException(exc);
		}

		return response;
	}


	@Override
	public String getTenantIdFromVserverUrl(URL url) {

		String path = url.getPath();

		String[] split = path.split("/tenants/tenant/");
		if(split.length > 1) {
			split = split[1].split("/");
			return split[0];
		} else {
			return null;
		}
	}

	@Override
	public String getCloudOwnerFromVserverUrl(URL url) {

		String path = url.getPath();

		String[] split = path.split("/cloud-regions/cloud-region/");
		if(split.length > 1) {
			split = split[1].split("/");
			return split[0];
		} else {
			return null;
		}
	}

	@Override
	public String getCloudRegionFromVserverUrl(URL url) {

		String path = url.getPath();

		String[] split = path.split("/cloud-regions/cloud-region/");
		if(split.length > 1) {
			split = split[1].split("/");
			return split[1];
		} else {
			return null;
		}
	}

	@Override
	public String getVServerIdFromVserverUrl(URL url, String tenantId) {
		String pattern =  network_vserver_path;
		pattern = pattern.replace("{tenant-id}", tenantId);

		int end = pattern.indexOf("{vserver-id}");
		String prefix = pattern.substring(0, end);

		String path = url.getPath();

		if(path.startsWith(prefix)) {
			path = path.substring(prefix.length());
		}

		return path;
	}

	protected  Logger getLogger(){
		return LOG;
	}

	@Override
	public boolean updateAnAIEntry(Update request) throws AAIServiceException {
		InputStream inputStream = null;

		try {

			ObjectMapper mapper = getObjectMapper();
			String json_text = mapper.writeValueAsString(request);

			String request_url = target_uri+update_path;
			URL http_req_url =	new URL(request_url);

			HttpURLConnection con = getConfiguredConnection(http_req_url, HttpMethod.PUT);

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
            osw.write(json_text);
            osw.flush();
            osw.close();

            LOGwriteFirstTrace("PUT", request_url);
            LOGwriteDateTrace("Update", json_text);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            BufferedReader reader;
            String line = null;
            reader = new BufferedReader( new InputStreamReader( inputStream ) );

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_ACCEPTED || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", (stringBuilder != null) ? stringBuilder.toString() : "{no-data}");
				return true;
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
				ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
				LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
				throw new AAIServiceException(responseCode, errorresponse);
			} else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));

            	throw new AAIServiceException(responseCode, errorresponse);
            }
		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("updateAnAIEntry", exc);
			throw new AAIServiceException(exc);
		} finally {
			try {
				if(inputStream != null)
				inputStream.close();
			} catch (Exception exc) {

			}
		}
	}

	@Override
	public AAIRequestExecutor getExecutor() {
		return executor;
	}

	/**
	 * Creates a current time stamp in UTC i.e. 2016-03-08T22:15:13.343Z.
	 * If there are any parameters the values are appended to the time stamp.
	 *
	 * @param parameters
	 *            values to be appended to current time stamp
	 * @param ctx
	 *            used to set an attribute for a DG
	 * @throws SvcLogicException
	 */
	public void setStatusMethod(Map<String, String> parameters, SvcLogicContext ctx) throws SvcLogicException {
		if (ctx == null) {
			throw new SvcLogicException("SvcLogicContext is null.");
		}

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%tFT%<tTZ", Calendar.getInstance(TimeZone.getTimeZone("Z")))).append(" - ");

		for (Entry<String, String> entry : parameters.entrySet()) {
			sb.append(entry.getValue()).append("  ");
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 2);
		}

		ctx.setAttribute("aai-summary-status-message", sb.toString());
		LOG.info("aai-summary-status-message: " + sb.toString());
	}

    /**
     * Generic method to GET json data from an A&AI using key structure.
     * Then convert that json to an Object.
     * If successful the Object is attempted to be cast to the type parameter.
     *
     * @param key
     *            key identifying the resource to be retrieved from AAI
     * @param type
     *            the class of object that A&AI will return
     * @return the object created from json or null if the response code is not 200
     *
     * @throws AAIServiceException
     *             if empty or null key and or type or there's an error with processing
     */

	public <T> T getResource(String key, Class<T> type) throws AAIServiceException {
	        if (StringUtils.isEmpty(key) || type == null) {
	            throw new AAIServiceException("Key is empty or null and or type is null");
	        }

	        T response = null;

	        SvcLogicContext ctx = new SvcLogicContext();
	        if(!key.contains(" = ")) {
	        	if(isValidURL(key)) {
	        		key = String.format("selflink = '%s'", key);
	        	} else {
	        		return response;
	        	}
	        }

	        HashMap<String, String> nameValues = keyToHashMap(key, ctx);

	        AAIRequest request = new SelfLinkRequest(type);
	        if(nameValues.containsKey(PathRequest.RESOURCE_PATH.replaceAll("-", "_"))) {
	        	request = new PathRequest(type);
	        }

	        request.processRequestPathValues(nameValues);
	        Object obj = this.getExecutor().query(request, type);
	        response = type.cast(obj);

	        return response != null ? type.cast(response) : response;
	 }

	 public boolean isValidURL(String url) {

		    URL u = null;

		    try {
		        u = new URL(url);
		    } catch (MalformedURLException e) {
		        return false;
		    }

		    try {
		        u.toURI();
		    } catch (URISyntaxException e) {
		        return false;
		    }

		    return true;
		}

	@Override
	protected boolean deleteRelationshipList(URL httpReqUrl, String json_text) throws AAIServiceException {
		if(httpReqUrl ==  null) {
			throw new NullPointerException();
		}

		boolean response = false;
		InputStream inputStream = null;

		try {
            HttpURLConnection con = getConfiguredConnection(httpReqUrl, HttpMethod.DELETE);

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
            osw.write(json_text);
            osw.flush();
            osw.close();


            LOGwriteFirstTrace("DELETE", httpReqUrl.toString());
    		LOGwriteDateTrace("data", json_text);

            // Check for errors
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            	inputStream = con.getInputStream();
            } else {
            	inputStream = con.getErrorStream();
            }

            // Process the response
            LOG.debug("HttpURLConnection result:" + responseCode);
            if(inputStream == null) inputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
            String line = null;

            ObjectMapper mapper = getObjectMapper();

			if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				StringBuilder stringBuilder = new StringBuilder();

				while( ( line = reader.readLine() ) != null ) {
					stringBuilder.append( line );
				}
				LOGwriteEndingTrace(responseCode, "SUCCESS", stringBuilder.toString());
				response = true;
			} else if(responseCode == HttpURLConnection.HTTP_NOT_FOUND ) {
				LOGwriteEndingTrace(responseCode, "HTTP_NOT_FOUND", "Entry does not exist.");
				response = false;
            } else {
            	ErrorResponse errorresponse = mapper.readValue(reader, ErrorResponse.class);
            	LOGwriteEndingTrace(responseCode, "FAILURE", mapper.writeValueAsString(errorresponse));
            	throw new AAIServiceException(responseCode, errorresponse);
            }

		} catch(AAIServiceException aaiexc) {
			throw aaiexc;
		} catch (Exception exc) {
			LOG.warn("deleteRelationshipList", exc);
			throw new AAIServiceException(exc);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch(Exception exc) {

				}
			}
		}
		return response;
	}

	public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
	    AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
	    AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
	    mapper.setAnnotationIntrospector(AnnotationIntrospector.pair(introspector, secondary));
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
	}

	public void logMetricRequest(String targetServiceName, String msg){
		String svcInstanceId = "";
		String svcName = null;
		String partnerName = null;
		String targetEntity = "A&AI";
		String targetVirtualEntity = null;

		targetServiceName = "";

		ml.logRequest(svcInstanceId, svcName, partnerName, targetEntity, targetServiceName, targetVirtualEntity, msg);
	}

	public void logMetricResponse(int responseCode, String responseDescription){
		ml.logResponse(responseCode < 400 ? "SUCCESS" : "FAILURE", Integer.toBinaryString(responseCode), responseDescription);
	}

	public void logKeyError(String keys){
	    LOG.error("Atleast one of the keys [" + keys + "] should have been populated. This will cause a NPE.");
	}

}
