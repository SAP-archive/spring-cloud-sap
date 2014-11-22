package com.sap.hana.cloud.hcp;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.cloud.ServiceInfoCreator;
import org.springframework.cloud.service.common.RelationalServiceInfo;

import com.sap.hana.cloud.hcp.service.common.HANAServiceInfo;
import com.sap.hana.cloud.hcp.service.common.ServiceData;

public class HANAServiceInfoCreator extends HCPServiceInfoCreator<HANAServiceInfo> implements ServiceInfoCreator<HANAServiceInfo, ServiceData>
{
	/**
	 * The associated {@link ServiceData} element.
	 * 
	 * @see ServiceData
	 */
	final static ServiceData SRV_DATA = ServiceData.HANA;
	
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
    private final static Logger LOG = Logger.getLogger(HANAServiceInfoCreator.class.getName()); 
	
	/**
	 * Default constructor.
	 */
	public HANAServiceInfoCreator() 
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
	 * @see HANAServiceInfo
	 */
    public HANAServiceInfo createServiceInfo(ServiceData serviceData)
    {
    	HANAServiceInfo retVal = null;
    	
    	if (LOG.isLoggable(Level.FINER))
    	{
    		LOG.entering(this.getClass().getName(), "createServiceInfo", serviceData);
    	}
    	
    	String uriString = HCPRelationalServiceInfoCreatorFactory.getInstance().getDbInfo().getUrl();
    	
    	if (uriString != null)
		{
			retVal = new HANAServiceInfo(ServiceData.HANA.getID(), uriString);
		}
		else
		{
			retVal = new HANAServiceInfo(ServiceData.HANA.getID(), "<none>");
		}
    	
    	if (LOG.isLoggable(Level.FINER))
    	{
    		LOG.exiting(this.getClass().getName(), "createServiceInfo", retVal);
    	}
    	
    	return retVal;
    }

}