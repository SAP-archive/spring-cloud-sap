package com.sap.hana.cloud.hcp.cf;

import static org.springframework.cloud.service.common.RelationalServiceInfo.JDBC_PREFIX;

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
		super(new Tags("hana"), HANAServiceInfo.URI_SCHEME);
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

		// we always construct the URI manually! 
		String host = getStringFromCredentials(credentials, "hostname", "host");
		int port = Integer.parseInt(credentials.get("port").toString()); // allows the port attribute to be quoted or plain

		String username = getStringFromCredentials(credentials, "user", "username");
		String password = (String) credentials.get("password");

		String query = getQueryFromUrl(url);
		String uri = new UriInfo(getDefaultUriScheme(), host, port, username, password, null, query).toString();
		return createServiceInfo(id, uri);
    }

	private String getQueryFromUrl(final String urlFromCredentials) {
		String trimmedUrl = urlFromCredentials.replaceFirst("jdbc:", ""); // trim off jdbc so it's parsable as an URL
		try {
			URI uri = new URI(trimmedUrl);
			return uri.getQuery();
		} catch (URISyntaxException e) {
			LOG.log(Level.SEVERE,"Invalid URI " + trimmedUrl + " in credentials, falling back to empty query String");
			return "";
		}
	}
	
	@Override
	protected boolean jdbcUrlMatchesScheme(Map<String, Object> serviceData) 
	{		
		String uriScheme = getDefaultUriScheme();
		
		if (uriScheme == null) 
		{
			return false;
		}

		Map<String, Object> credentials = getCredentials(serviceData);
		
		String jdbcUrl = getStringFromCredentials(credentials, "url");

		if (jdbcUrl != null && jdbcUrl.startsWith(JDBC_PREFIX + uriScheme + ":"))
		{
			return true;
		}

		return false;
	}
}
