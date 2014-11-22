package com.sap.hana.cloud.hcp.util;

import java.util.logging.Logger;

import org.springframework.cloud.util.StandardUriInfoFactory;
import org.springframework.cloud.util.UriInfo;
import org.springframework.cloud.util.UriInfoFactory;

public class DerbyUriInfoFactory extends StandardUriInfoFactory implements UriInfoFactory
{
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
	@SuppressWarnings("unused")
    private final static Logger LOG = Logger.getLogger(DerbyUriInfoFactory.class.getName()); 
	
	@Override
	public UriInfo createUri(String uriString) 
	{
		if (uriString.contains("url")) // trim if needed
		{
			// e.g. jdbc:derby://localhost:1527/DemoDB;url=jdbc:derby://localhost:1527/DemoDB;create=true;
			int startPos = uriString.indexOf("url=", 0);
			uriString = uriString.substring(startPos + 4);
			
			int endPos = uriString.indexOf(';');
			uriString = uriString.substring(0, endPos);
		}
		
		if (uriString.contains(":memory:"))
		{
			// for in-memory Derby instances
			return new DerbyUriInfo(uriString);
		}
		else
		{
			return super.createUri(uriString);
		}
	}
}
