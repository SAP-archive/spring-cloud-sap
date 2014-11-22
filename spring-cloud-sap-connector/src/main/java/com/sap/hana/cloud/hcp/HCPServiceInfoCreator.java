package com.sap.hana.cloud.hcp;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.cloud.ServiceInfoCreator;
import org.springframework.cloud.service.ServiceInfo;

import com.sap.hana.cloud.hcp.service.common.ServiceData;

public abstract class HCPServiceInfoCreator<SI extends ServiceInfo> implements ServiceInfoCreator<SI, ServiceData>
{
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
    private final static Logger LOG = Logger.getLogger(HCPServiceInfoCreator.class.getName()); 
	
    /**
     * Returns whether or not this {@link ServiceInfoCreator} accepts the specified {@link ServiceData}.
     * 
     * @return whether or not this {@link ServiceInfoCreator} accepts the specified {@link ServiceData}
     * 
     * @see ServiceInfoCreator#accept(Object)
     */
	public boolean accept(ServiceData serviceData)
    {
    	boolean retVal = false;
    	
    	if (LOG.isLoggable(Level.FINER))
    	{
    		LOG.entering(this.getClass().getName(), "accept", serviceData);
    	}
    	
    	retVal = (serviceData == this.getServiceData());
    	
    	if (LOG.isLoggable(Level.FINER))
    	{
    		LOG.exiting(this.getClass().getName(), "accept", retVal);
    	}
    	
    	return retVal;
    }
	
	/**
	 * Returns the associated {@link ServiceData} element.
	 * 
	 * @return The associated {@link ServiceData} element
	 * 
	 * @see ServiceData
	 */
    abstract public ServiceData getServiceData();

}