package com.sap.hana.cloud.hcp;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.cloud.ServiceInfoCreator;
import org.springframework.cloud.service.common.RelationalServiceInfo;

import com.sap.hana.cloud.hcp.service.common.SAPDBServiceInfo;
import com.sap.hana.cloud.hcp.service.common.ServiceData;

public class SAPDBServiceInfoCreator extends HCPServiceInfoCreator<SAPDBServiceInfo> implements ServiceInfoCreator<SAPDBServiceInfo, ServiceData>
{
	/**
	 * The associated {@link ServiceData} element.
	 * 
	 * @see ServiceData
	 */
	final static ServiceData SRV_DATA = ServiceData.SAPDB;
	
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
    private final static Logger LOG = Logger.getLogger(SAPDBServiceInfoCreator.class.getName()); 
	
	/**
	 * Default constructor.
	 */
	public SAPDBServiceInfoCreator() 
	{
	}
	
	/**
	 * Returns the associated {@link ServiceData} element.
	 * 
	 * @return The associated {@link ServiceData} element
	 * 
	 * @see ServiceData
	 */
	public ServiceData getServiceData()
	{
		return SRV_DATA;
	}
	
	/**
	 * Creates the matching {@link RelationalServiceInfo} for the specified {@link ServiceData}.
	 * 
	 * @return The matching {@link RelationalServiceInfo} for the specified {@link ServiceData}
	 * 
	 * @see ServiceInfoCreator#createServiceInfo(Object)
	 * @see SAPDBServiceInfo
	 */
    public SAPDBServiceInfo createServiceInfo(ServiceData serviceData)
    {
    	SAPDBServiceInfo retVal = null;
    	
    	if (LOG.isLoggable(Level.FINER))
    	{
    		LOG.entering(this.getClass().getName(), "createServiceInfo", serviceData);
    	}
    	
    	String uriString = HCPRelationalServiceInfoCreatorFactory.getInstance().getDbInfo().getUrl();
    	
    	if (uriString != null)
		{
			retVal = new SAPDBServiceInfo(ServiceData.SAPDB.getID(), uriString);
		}
		else
		{
			retVal = new SAPDBServiceInfo(ServiceData.SAPDB.getID(), "<none>");
		}
    	
    	if (LOG.isLoggable(Level.FINER))
    	{
    		LOG.exiting(this.getClass().getName(), "createServiceInfo", retVal);
    	}
    	
    	return retVal;
    }
}