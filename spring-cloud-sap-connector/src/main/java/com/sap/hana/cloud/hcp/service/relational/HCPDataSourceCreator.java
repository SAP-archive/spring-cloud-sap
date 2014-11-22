package com.sap.hana.cloud.hcp.service.relational;

import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.cloud.service.common.RelationalServiceInfo;
import org.springframework.cloud.service.relational.DataSourceCreator;

import com.sap.hana.cloud.hcp.HCPRelationalServiceInfoCreatorFactory;

public abstract class HCPDataSourceCreator<SI extends RelationalServiceInfo> extends DataSourceCreator<SI>
{
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
	@SuppressWarnings("unused")
    private final static Logger LOG = Logger.getLogger(HCPDataSourceCreator.class.getName()); 

	public HCPDataSourceCreator(String driverSystemPropKey, String[] driverClasses, String validationQuery)
    {
	    super(driverSystemPropKey, driverClasses, validationQuery);
    }

	@Override
	public DataSource create(SI serviceInfo, ServiceConnectorConfig serviceConnectorConfig) 
	{
		return HCPRelationalServiceInfoCreatorFactory.getInstance().getDefaultDS();
	}
}
