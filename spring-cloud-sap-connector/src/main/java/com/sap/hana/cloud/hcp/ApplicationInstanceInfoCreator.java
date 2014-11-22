package com.sap.hana.cloud.hcp;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.app.BasicApplicationInstanceInfo;
import org.springframework.cloud.util.EnvironmentAccessor;

/**
 * Provides {@link ApplicationInstanceInfo} for SAP HANA Cloud Platform
 */
public class ApplicationInstanceInfoCreator
{
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
	@SuppressWarnings("unused")
    private final static Logger LOG = Logger.getLogger(ApplicationInstanceInfoCreator.class.getName()); 

	
	/**
	 * Environment variable used to provide a unique instance/process ID: <code>HC_PROCESS_ID</code>
	 * 
	 * <p>For further information please refer to: https://help.hana.ondemand.com/help/frameset.htm?d553d78bf9bd4ecbac201b873f557db6.html</p>
	 */
	private static final String ENV_INSTANCE_ID = "HC_PROCESS_ID";
	
	/**
	 * Environment variable used to provide a unique instance/process ID: <code>HC_APPLICATION</code>
	 * 
	 * <p>For further information please refer to: https://help.hana.ondemand.com/help/frameset.htm?d553d78bf9bd4ecbac201b873f557db6.html</p>
	 */
	private static final String ENV_APP_ID = "HC_APPLICATION";
	
	
	/**
	 * Abstraction layer used to obtain environment variables.
	 */
	private EnvironmentAccessor environment = null;
	
	/**
	 * Default constructor. 
	 * 
	 * @param environmentAccessor The environment 
	 */
	public ApplicationInstanceInfoCreator(EnvironmentAccessor environmentAccessor) 
	{
		this.environment = environmentAccessor;
	}
	
	/**
	 * Creates the {@link ApplicationInstanceInfo} based on HCP-specific environment variables.
	 * 
	 * <p>For further information please refer to: https://help.hana.ondemand.com/help/frameset.htm?d553d78bf9bd4ecbac201b873f557db6.html</p>
     *
	 * @return The {@link ApplicationInstanceInfo}
	 */
	public ApplicationInstanceInfo createApplicationInstanceInfo()
	{
		String instanceID = environment.getEnvValue(ENV_INSTANCE_ID);
		String appID = environment.getEnvValue(ENV_APP_ID);
		
		if (instanceID == null)
		{
			instanceID = "<unknown>";
		}
		
		if (appID == null)
		{
			appID = "<unknown>";
		}

		/*
		 * HC_HOST	hana.ondemand.com / us1.hana.ondemand.com / hanatrial.ondemand.com	Base URL of the SAP HANA Cloud Platform landscape where the application is deployed
		 * HC_REGION	EU_1 / US_1	Region of the data center where the application is deployed
		 * HC_ACCOUNT	myaccount	Name of the account where the application is deployed
		 * HC_APPLICATION_URL	https://myapp.hana.ondemand.com	URL of the application
		 * HC_LOCAL_HTTP_PORT	9001	HTTP port of the application bound to localhost
		 * HC_LANDSCAPE	production / trial	Type of the landscape where the application is deployed
		 * HC_OP_HTTP_PROXY_HOST	localhost	Host of the HTTP Proxy for on-premise connectivity
		 * HC_OP_HTTP_PROXY_PORT	20003	Port of the HTTP Proxy for on-premise connectivity
		 */
		
		Map<String, Object> envAttributes = new HashMap<String, Object>();
		envAttributes.put("host", environment.getEnvValue("HC_HOST"));
		envAttributes.put("region", environment.getEnvValue("HC_REGION"));
		envAttributes.put("account", environment.getEnvValue("HC_ACCOUNT"));
		envAttributes.put("url", environment.getEnvValue("HC_APPLICATION_URL"));
		envAttributes.put("port", environment.getEnvValue("HC_LOCAL_HTTP_PORT"));
		envAttributes.put("landscape", environment.getEnvValue("HC_LANDSCAPE"));
		envAttributes.put("op_proxy_host", environment.getEnvValue("HC_OP_HTTP_PROXY_HOST"));
		envAttributes.put("op_proxy_port", environment.getEnvValue("HC_OP_HTTP_PROXY_PORT"));

		return new BasicApplicationInstanceInfo(instanceID, appID, envAttributes);
	}

}
