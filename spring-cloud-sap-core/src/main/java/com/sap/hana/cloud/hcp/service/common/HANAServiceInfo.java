package com.sap.hana.cloud.hcp.service.common;

import java.util.logging.Logger;

import org.springframework.cloud.service.ServiceInfo.ServiceLabel;
import org.springframework.cloud.service.common.RelationalServiceInfo;

@ServiceLabel("hana")
public class HANAServiceInfo extends RelationalServiceInfo 
{
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
	@SuppressWarnings("unused")
    private final static Logger LOG = Logger.getLogger(HANAServiceInfo.class.getName()); 
	
    public static final String JDBC_URL_TYPE = "sap";

    public static final String URI_SCHEME = "sap";

	public HANAServiceInfo(String id, String url) 
	{	
		super(id, url, JDBC_URL_TYPE);
	}
}
