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

package org.openecomp.sdnc.sli.resource.sql;

import org.openecomp.sdnc.sli.SvcLogicResource;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlResourceActivator implements BundleActivator {

	private static final String SVCLOGIC_PROP_PATH = "/svclogic.properties";

	private ServiceRegistration registration = null;

	private static final Logger LOG = LoggerFactory
			.getLogger(SqlResourceActivator.class);

	@Override
	public void start(BundleContext ctx) throws Exception {



		// Advertise Sql resource adaptor
		SvcLogicResource impl = new SqlResource();
		String regName = impl.getClass().getName();

		if (registration == null)
		{
			LOG.debug("Registering SqlResource service "+regName);
			registration =ctx.registerService(regName, impl, null);
		}

	}

	@Override
	public void stop(BundleContext ctx) throws Exception {

		if (registration != null)
		{
			registration.unregister();
			registration = null;
		}
	}

}
