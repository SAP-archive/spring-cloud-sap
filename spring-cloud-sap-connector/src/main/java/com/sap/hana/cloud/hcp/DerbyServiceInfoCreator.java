package com.sap.hana.cloud.hcp;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.cloud.ServiceInfoCreator;
import org.springframework.cloud.service.common.RelationalServiceInfo;

import com.sap.hana.cloud.hcp.service.common.DerbyServiceInfo;
import com.sap.hana.cloud.hcp.service.common.ServiceData;

public class DerbyServiceInfoCreator extends HCPServiceInfoCreator<DerbyServiceInfo> implements ServiceInfoCreator<DerbyServiceInfo, ServiceData>
{
	/**
	 * The associated {@link ServiceData} element.
	 * 
	 * @see ServiceData
	 */
	final static ServiceData SRV_DATA = ServiceData.DERBY;
	
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
    private final static Logger LOG = Logger.getLogger(DerbyServiceInfoCreator.class.getName()); 
	
	/**
	 * Default constructor.
	 */
	public DerbyServiceInfoCreator() 
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
	 * @see DerbyServiceInfo
	 */
    public DerbyServiceInfo createServiceInfo(ServiceData serviceData)
    {
    	DerbyServiceInfo retVal = null;
    	
    	if (LOG.isLoggable(Level.FINER))
    	{
    		LOG.entering(this.getClass().getName(), "createServiceInfo", serviceData);
    	}
    	
    	String uriString = HCPRelationalServiceInfoCreatorFactory.getInstance().getDbInfo().getUrl();
    	
    	if (uriString != null)
		{
			retVal = new DerbyServiceInfo(ServiceData.DERBY.getID(), uriString);
		}
		else
		{
			retVal = new DerbyServiceInfo(ServiceData.DERBY.getID(), "<none>");
		}
    	
    	if (LOG.isLoggable(Level.FINER))
    	{
    		LOG.exiting(this.getClass().getName(), "createServiceInfo", retVal);
    	}
    	
    	return retVal;
    }
}