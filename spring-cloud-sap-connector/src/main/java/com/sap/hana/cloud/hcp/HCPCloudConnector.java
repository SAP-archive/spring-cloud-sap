package com.sap.hana.cloud.hcp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.cloud.CloudConnector;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.util.EnvironmentAccessor;

import com.sap.hana.cloud.hcp.service.common.ServiceData;


/**
 * Spring-Cloud connector for SAP HANA Cloud Platform (HCP).
 */
public class HCPCloudConnector implements CloudConnector
{
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
    private final static Logger LOG = Logger.getLogger(HCPCloudConnector.class.getName()); 
	
	/**
	 * Environment variable used to check whether or not the application runs on
	 * SAP HANA Cloud Platform.
	 * 
     * <p>For further information please refer to: https://help.hana.ondemand.com/help/frameset.htm?d553d78bf9bd4ecbac201b873f557db6.html</p>
	 */
	private static final String HCP_ENV_CHECK = "HC_LANDSCAPE";

	/**
	 * Abstraction layer used to obtain environment variables.
	 */
	private EnvironmentAccessor environment = new EnvironmentAccessor();

	/**
	 * The HCP-specific creator of {@link ApplicationInstanceInfo}s.
	 */
	private ApplicationInstanceInfoCreator applicationInstanceInfoCreator = new ApplicationInstanceInfoCreator(environment);

	/**
	 * Default constructor
	 */
	public HCPCloudConnector()
	{
	}

	/**
	 * Returns the {@link ApplicationInstanceInfo} for SAP HANA Cloud Platform.
	 * 
	 * @return The {@link ApplicationInstanceInfo}
	 * 
	 * @see CloudConnector#getApplicationInstanceInfo()
	 */
	public ApplicationInstanceInfo getApplicationInstanceInfo()
	{
		try
		{
			return applicationInstanceInfoCreator.createApplicationInstanceInfo();
		}
		catch (Exception ex)
		{
			throw new CloudException(ex);
		}
	}

	/**
	 * 
	 * @see CloudConnector#getServiceInfos()
	 */
	public List<ServiceInfo> getServiceInfos()
	{
		List<ServiceInfo> retVal = new ArrayList<ServiceInfo>(1);
		
		retVal.add(HCPRelationalServiceInfoCreatorFactory.getInstance().getActiveRelationalServiceInfo());
		    
		return retVal;
	}

	/**
	 * Checks whether or not the application runs on SAP HANA Cloud Platform
	 * based on the availability of the respective environment variable:
	 * <code>HC_LANDSCAPE</code>
	 * 
	 * <p>For further information please refer to: https://help.hana.ondemand.com/help/frameset.htm?d553d78bf9bd4ecbac201b873f557db6.html</p>
	 * 
	 * @see CloudConnector#isInMatchingCloud()
	 */
	public boolean isInMatchingCloud()
	{
		boolean retVal = environment.getEnvValue(HCP_ENV_CHECK) != null;
		
		LOG.exiting(this.getClass().getName(), "isInMatchingCloud()",retVal);
		
		return retVal;
	}

	/**
	 * 
	 * 
	 */
	protected List<ServiceData> getServicesData()
	{
		return Arrays.asList(ServiceData.values());
	}
}
