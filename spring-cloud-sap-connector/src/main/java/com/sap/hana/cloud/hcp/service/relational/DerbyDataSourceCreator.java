package com.sap.hana.cloud.hcp.service.relational;

import java.util.logging.Logger;

import com.sap.hana.cloud.hcp.service.common.DerbyServiceInfo;

public class DerbyDataSourceCreator extends HCPDataSourceCreator<DerbyServiceInfo> 
{
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
	@SuppressWarnings("unused")
    private final static Logger LOG = Logger.getLogger(DerbyDataSourceCreator.class.getName()); 
	
	private static final String[] DRIVERS = new String[]{"org.apache.derby.jdbc.EmbeddedDriver"};
	private static final String VALIDATION_QUERY = "VALUES 1";

	public DerbyDataSourceCreator() 
	{	
		super("spring-cloud.derby-memory.driver", DRIVERS, VALIDATION_QUERY);
	}
}
