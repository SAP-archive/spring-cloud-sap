package com.sap.hana.cloud.hcp.cf.service.relational;

import java.util.logging.Logger;

import org.springframework.cloud.service.relational.DataSourceCreator;

import com.sap.hana.cloud.hcp.service.common.HANAServiceInfo;

public class HANADataSourceCreator extends DataSourceCreator<HANAServiceInfo> 
{
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
	@SuppressWarnings("unused")
    private final static Logger LOG = Logger.getLogger(HANADataSourceCreator.class.getName()); 
	
	private static final String[] DRIVERS = new String[]{"com.sap.db.jdbc.Driver"};
	private static final String VALIDATION_QUERY = "SELECT 1 FROM DUMMY";

	public HANADataSourceCreator() 
	{	
		super("spring-cloud.hana.driver", DRIVERS, VALIDATION_QUERY);
	}
}
