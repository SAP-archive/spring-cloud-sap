package com.sap.hana.cloud.hcp;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Binding;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.cloud.CloudException;
import org.springframework.cloud.ServiceInfoCreator;
import org.springframework.cloud.service.common.RelationalServiceInfo;

import com.sap.hana.cloud.hcp.service.common.ServiceData;

/**
 * Factory for {@link RelationalServiceInfoCreator}s.
 */
public class HCPRelationalServiceInfoCreatorFactory
{
	/**
	 * {@link Logger} instance used for logging/tracing.
	 */
	private final static Logger LOG = Logger.getLogger(HCPRelationalServiceInfoCreatorFactory.class.getName());

	/**
	 * Singleton instance of {@link HCPRelationalServiceInfoCreatorFactory}
	 */
	private static final HCPRelationalServiceInfoCreatorFactory instance = new HCPRelationalServiceInfoCreatorFactory();

	/**
	 * Returns a reference to the singleton instance of
	 * {@link HCPRelationalServiceInfoCreatorFactory}.
	 * 
	 * @return A reference to the singleton instance of
	 *         {@link HCPRelationalServiceInfoCreatorFactory}
	 */
	public static HCPRelationalServiceInfoCreatorFactory getInstance()
	{
		return instance;
	}

	/**
	 * Private constructor to ensure singleton pattern.
	 */
	private HCPRelationalServiceInfoCreatorFactory()
	{
		initialize();
	}

	/**
	 * The default {@link DataSource} provided automatically by the runtime.
	 * 
	 * <p>
	 * For further information please refer to:
	 * https://help.hana.ondemand.com/help
	 * /frameset.htm?39b1fcd42c864eea9fcdf381a64c13b8.html
	 * </p>
	 */
	protected DataSource defaultDS = null;

	/**
	 * Database information of the default {@link DataSource}
	 */
	protected DBInformation dbInfo = null;

	/**
	 * The active relational DB.
	 */
	protected ServiceData activeRelationalService = null;

	/**
	 * Initializes the environment and searches for a default {@link DataSource}
	 * provided automatically by the runtime.
	 * 
	 * <p>
	 * Assumes that the default {@link DataSource} can be obtained via the
	 * following JNDI name: <code>java:comp/env/jdbc/DefaultDB</code>
	 * </p>
	 */
	protected void initialize()
	{
		final String jndiNameRootName = "java:comp/env/jdbc";
		final String defaultJndiName = "defaultManagedDataSource";

		InitialContext ctx = null;
		DataSource ds = null;

		// check if we can obtain a DataSource via JNDI
		try
		{
			ctx = new InitialContext();
	
			// loop bindings in context
			NamingEnumeration<Binding> bindingEnum = ctx.listBindings(jndiNameRootName);
		
			int i = 0;
			boolean defaultNameFound = false;
			
			while (bindingEnum != null && bindingEnum.hasMoreElements())
			{
				Binding binding = bindingEnum.next();

				try
				{
					
					if (binding.getObject() instanceof DataSource)
					{
	
						if (LOG.isLoggable(Level.INFO))
						{
							LOG.logp(Level.INFO, HCPRelationalServiceInfoCreatorFactory.class.getName(), "initialize", "Found DataSource defined in JNDI: {0}", binding.getName());
						} 
	
						if (binding.getName().equals(defaultJndiName))
						{
							defaultNameFound = true;
							ds = (DataSource) binding.getObject();
							break;
						}
						else
						{
							if (i == 0)
							{
								ds = (DataSource) binding.getObject();
							}						
						}
						
						i++;
						
					}

				}
				catch (Exception e)
				{
					// Ignore any Exceptions that e.g. binding.getObject() might cause and move on
				}

			}

			if ((i == 1) || defaultNameFound) // in case we found a single datasource or the default datasource
			{
				this.defaultDS = ds;
			}
			else
			{
				if (i == 0)
				{
					final String msg = MessageFormat.format("No unique service matching {0} found. Expected 1, found {1}", DataSource.class.toString(), 0);
					throw new CloudException(msg);
				}
				else
				{
					final String msg = MessageFormat.format("No unique service matching {0} found. Expected exactly 1 or one with name '{1}', but found {2} with other names.", DataSource.class.toString(), defaultJndiName, i);
					throw new CloudException(msg);
				}
			}

			if (LOG.isLoggable(Level.INFO))
			{
				LOG.logp(Level.INFO, HCPRelationalServiceInfoCreatorFactory.class.getName(), "initialize", "Found DataSource: {0}", ds);
			}
		}
		catch (NamingException ex)
		{
			final String msg = MessageFormat.format("No unique service matching {0} found. Expected 1, found {1}", DataSource.class.toString(), 0);
			throw new CloudException(msg);
		}

		Connection conn = null;

		try
		{
			conn = ds.getConnection();
			DatabaseMetaData metaData = conn.getMetaData();

			// obtain information about the data source
			this.dbInfo = new DBInformation(metaData);
			
			if (LOG.isLoggable(Level.INFO))
			{
				LOG.logp(Level.INFO, HCPRelationalServiceInfoCreatorFactory.class.getName(), "initialize", this.dbInfo.toString());
			}

			this.activeRelationalService = ServiceData.getServiceDataByName(metaData.getDatabaseProductName());

		}
		catch (Exception ex)
		{
			final String message = "Could not obtain information about the default Datasource";
			LOG.log(Level.SEVERE, message, ex);

			throw new CloudException(message, ex);
		}
		finally
		{
			if (conn != null) // try to close the DB connection
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{
				} // we are screwed!
			}
		}
	}

	/**
	 * Returns the respective {@link ServiceInfoCreator} for the {@link RelationalServiceInfo} associated with the bound {@link @DataSource}.
	 * 
	 * @return  The respective {@link ServiceInfoCreator} for the {@link RelationalServiceInfo} associated with the bound {@link @DataSource}
	 */
	public ServiceInfoCreator<? extends RelationalServiceInfo, ServiceData> getActiveRelationalServiceInfoCreator()
	{
		ServiceInfoCreator<? extends RelationalServiceInfo, ServiceData> retVal = null;

		if (this.activeRelationalService == ServiceData.HANA)
		{
			retVal = new HANAServiceInfoCreator();
		}
		else if (this.activeRelationalService == ServiceData.SAPDB)
		{
			retVal = new SAPDBServiceInfoCreator();
		}
		else if (this.activeRelationalService == ServiceData.DERBY)
		{
			retVal = new DerbyServiceInfoCreator();
		}

		return retVal;
	}
	
	/**
	 * 
	 * @return
	 */
	public RelationalServiceInfo getActiveRelationalServiceInfo()
	{
		return getActiveRelationalServiceInfoCreator().createServiceInfo(this.activeRelationalService);
	}
	
	/**
	 * 
	 * @return
	 */
	public DBInformation getDbInfo()
	{
		return this.dbInfo;
	}

	/**
	 * Provides meta information about the underlying DB of the bound {@link DataSource}. 
	 * 
	 * @see DataSource#getConnection()
	 * @see Connection#getMetaData()
	 */
	public static class DBInformation
	{
		String url = null;

		String dbName = null;
		int dbMajorVersion = 0;
		int dbMinorVersion = 0;

		String driverName = null;
		String driverVersion = null;

		String userName = null;

		public DBInformation(DatabaseMetaData metaData) throws SQLException
		{
			url = metaData.getURL();
			dbName = metaData.getDatabaseProductName();
			dbMajorVersion = metaData.getDatabaseMajorVersion();
			dbMinorVersion = metaData.getDatabaseMinorVersion();

			driverName = metaData.getDriverName();
			driverVersion = metaData.getDriverVersion();

			userName = metaData.getUserName();

		}

		public String getUrl()
		{
			return this.url;
		}

		public String getDbName()
		{
			return this.dbName;
		}

		public int getDbMajorVersion()
		{
			return this.dbMajorVersion;
		}

		public int getDbMinorVersion()
		{
			return this.dbMinorVersion;
		}

		public String getDriverName()
		{
			return this.driverName;
		}

		public String getDriverVersion()
		{
			return driverVersion;
		}

		public String getUserName()
		{
			return userName;
		}
		
		public String toString()
		{
			final String CRLF = System.getProperty("line.separator");
			
			StringWriter str = new StringWriter();
			
			str.write(CRLF);
			str.write(MessageFormat.format("Driver Name: {0}{1}", this.driverName, CRLF));
			str.write(MessageFormat.format("Driver Version: {0}{1}", this.driverVersion, CRLF));
			str.write(MessageFormat.format("URL: {0}{1}", this.url, CRLF));
			str.write(MessageFormat.format("DB Name: {0}{1}", this.dbName, CRLF));
			str.write(MessageFormat.format("Username: {0}{1}", this.userName, CRLF));	
		
			return str.toString();
		}
		
	}

	public DataSource getDefaultDS()
	{
		return this.defaultDS;
	}

	public ServiceData getActiveRelationalService()
	{
		return this.activeRelationalService;
	}

}
