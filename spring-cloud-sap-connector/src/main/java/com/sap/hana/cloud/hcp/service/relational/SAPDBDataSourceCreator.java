package com.sap.hana.cloud.hcp.service.relational;

import java.util.logging.Logger;

import com.sap.hana.cloud.hcp.service.common.SAPDBServiceInfo;

public class SAPDBDataSourceCreator extends HCPDataSourceCreator<SAPDBServiceInfo> 
{
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
	@SuppressWarnings("unused")
    private final static Logger LOG = Logger.getLogger(SAPDBDataSourceCreator.class.getName()); 

	private static final String[] DRIVERS = new String[]{"com.sap.dbtech.jdbc.DriverSapDB"};
	private static final String VALIDATION_QUERY = "SELECT 1 FROM DUAL";

	public SAPDBDataSourceCreator() 
	{	
		super("spring-cloud.sapdb.driver", DRIVERS, VALIDATION_QUERY);
	}
}
