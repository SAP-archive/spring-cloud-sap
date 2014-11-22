package com.sap.hana.cloud.hcp.cf;

import java.util.Map;
import java.util.logging.Logger;

import org.springframework.cloud.cloudfoundry.RelationalServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;
import org.springframework.cloud.util.UriInfo;

import com.sap.hana.cloud.hcp.service.common.HANAServiceInfo;

public class HANAServiceInfoCreator extends RelationalServiceInfoCreator<HANAServiceInfo> 
{
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
	@SuppressWarnings("unused")
    private final static Logger LOG = Logger.getLogger(HANAServiceInfoCreator.class.getName()); 

	
	public HANAServiceInfoCreator() 
	{
		super(new Tags("hana", "relational"), HANAServiceInfo.URI_SCHEME);
	}

	@Override
	public HANAServiceInfo createServiceInfo(String id, String url) 
	{
		return new HANAServiceInfo(id, url);
	}
	
	@Override
	public HANAServiceInfo createServiceInfo(Map<String,Object> serviceData) 
	{
		@SuppressWarnings("unchecked")
		Map<String,Object> credentials = (Map<String, Object>) serviceData.get("credentials");

		String id = (String) serviceData.get("name");

		String uri = getStringFromCredentials(credentials, "uri", "url");

		if (uri == null) {
			String host = getStringFromCredentials(credentials, "hostname", "host");
			int port = Integer.parseInt(credentials.get("port").toString()); // allows the port attribute to be quoted or plain

			String username = getStringFromCredentials(credentials, "user", "username");
			String password = (String) credentials.get("password");

			String schema = (String) credentials.get("schema"); // passed as query
			
			uri = new UriInfo(getUriScheme(), host, port, username, password, null, "currentschema=" + schema).toString();
		}

		return createServiceInfo(id, uri);
	}
	
}