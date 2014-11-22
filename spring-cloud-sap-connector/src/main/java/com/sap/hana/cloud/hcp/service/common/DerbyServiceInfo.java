package com.sap.hana.cloud.hcp.service.common;

import java.text.MessageFormat;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.cloud.service.ServiceInfo.ServiceLabel;
import org.springframework.cloud.service.UriBasedServiceInfo;
import org.springframework.cloud.service.common.RelationalServiceInfo;
import org.springframework.cloud.util.UriInfoFactory;

import com.sap.hana.cloud.hcp.HCPRelationalServiceInfoCreatorFactory;
import com.sap.hana.cloud.hcp.util.DerbyUriInfoFactory;

@ServiceLabel("Derby")
public class DerbyServiceInfo extends RelationalServiceInfo 
{
	/**
	 * {@link Logger} instance used for logging/tracing. 
	 */
	@SuppressWarnings("unused")
    private final static Logger LOG = Logger.getLogger(DerbyServiceInfo.class.getName()); 

	private static UriInfoFactory uriFactory = new DerbyUriInfoFactory();
	
    public static final String JDBC_URL_TYPE = "derby";

    public static final String URI_SCHEME = "derby";

	public DerbyServiceInfo(String id, String url) 
	{	
		super(id, url, JDBC_URL_TYPE);
	}
	
	/**
     * Returns a custom/special implementation of {@link UriInfoFactory} in case the underlying 
     * {@link DataSource} is a Derby database instance. In this case an instance of {@link DerbyUriInfoFactory}
     * is returned to accommodate for the unique nature of Derby JDBC URLs:
     * 
     * <p>e.g. for the in-memory Derby instance: <code>jdbc:derby:memory:DemoDB;create=true</code><p>
     * <p>e.g. for the network client Derby instance: <code>jdbc:derby://localhost:1527/DemoDB;url=jdbc:derby://localhost:1527/DemoDB;create=true;create=true</code><p>
     * @return Reference to an instance of {@link UriInfoFactory}
     * 
     * @see UriBasedServiceInfo#getUriInfoFactory()
     * @see DerbyUriInfoFactory
     */
    public UriInfoFactory getUriInfoFactory() 
    {	
    	String uriString = HCPRelationalServiceInfoCreatorFactory.getInstance().getDbInfo().getUrl();
    	
    	if (uriString != null && uriString.contains("derby"))
        {
        	return uriFactory;
        }
        else
        {
        	return super.getUriInfoFactory();
        }
    }	
    
    /**
     * Custom implementation to not expose credentials.
     * 
     * @return The {@link String} expression of this {@link RelationalServiceInfo}
     */
    @Override
    public String toString() 
    {
       final String str = MessageFormat.format("{0}[{1}]", getClass().getSimpleName(), getUri());
       return str;
    }
}
