package com.sap.hana.cloud.hcp.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import org.springframework.cloud.util.UriInfo;

import com.sap.hana.cloud.hcp.service.common.DerbyServiceInfo;

public class DerbyUriInfo extends UriInfo
{
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
	@SuppressWarnings("unused")
    private final static Logger LOG = Logger.getLogger(DerbyUriInfo.class.getName()); 

	
	protected String uriString = null;
	
	protected String scheme;
	protected String host;
	protected int port;
	protected String userName;
	protected String password;
	protected String path;
	protected URI uri;
	protected String query;
	
	public DerbyUriInfo(String uriString)
	{
		super(DerbyServiceInfo.URI_SCHEME, null, -1, null, null);
		
		this.uriString = uriString;
		
		try
        {
	        this.uri = new URI(uriString);
        }
        catch (URISyntaxException e)
        {
	        // not my fault!
        }
		
	}
	
	public String getScheme() 
	{
		return DerbyServiceInfo.URI_SCHEME;
	}

	public String getHost() 
	{
		return host;
	}

	public int getPort() 
	{
		return port;
	}

	public String getUserName() 
	{
		return userName;
	}

	public String getPassword() 
	{
		return password;
	}

	public String getPath() 
	{
		return path;
	}

	public String getQuery() 
	{
		return query;
	}

	public URI getUri() 
	{
		return uri;
	}

	@Override
	public String toString() 
	{
		return uriString;
	}
	
}
