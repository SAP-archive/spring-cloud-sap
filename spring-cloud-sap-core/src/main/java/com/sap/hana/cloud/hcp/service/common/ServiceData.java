package com.sap.hana.cloud.hcp.service.common;


public enum ServiceData
{
	HANA("hana", "HDB"),
	SAPDB("sapdb", "SAP DB"),
	DERBY("derby", "Apache Derby");
	
	/**
	 * The id to be used.
	 */
	private final String id;
	
	/**
	 * The name to be used.
	 */
	private final String srvName;

	/**
	 * Creates a new {@link ServiceData} entity with the specified id & name.
	 * 
	 * @param id The id to be used
	 * @param name The name to be used
	 */
    private ServiceData(String id, String name) 
    {
        this.id = id;
        this.srvName = name;
    }

    /**
     * Returns the id of this service.
     * 
     * @return The id of this service
     */
    public String getID() 
    {
        return this.id;
    }
    
    /**
     * Returns the name of this service.
     * 
     * @return The name of this service
     */
    public String getServiceName() 
    {
        return this.srvName;
    }
    
    /**
     * Returns the respective {@link ServiceData} element or <code>NULL</code> if no matching {@link ServiceData} element was found.
     * 
     * @param name The name of the service to return
     * @return The respective {@link ServiceData} element or <code>NULL</code> if no matching {@link ServiceData} element was found
     */
    public static ServiceData getServiceDataByName(String name)
    {
    	ServiceData retVal = null;
    		
    	for (ServiceData serviceData : ServiceData.values())
    	{
    		if (serviceData.getServiceName().equals(name))
    		{
    			retVal = serviceData;
    			break;
    		}
    	}
    	
    	return retVal;
    	
    }
    
}
