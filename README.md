Since the original project is dead... just use CFJavaEnv

Assuming you are using spring boot 2.x

simply add
```xml
		<dependency>
			<groupId>io.pivotal.cfenv</groupId>
			<artifactId>java-cfenv-boot</artifactId>
			<version>2.1.0.RELEASE</version>
		</dependency>
```
to your pom and add the wiring:

```java
	@Bean
	@Primary
	@Profile("cloud")
	public DataSourceProperties dataSourceProperties() {
		CfJdbcEnv cfJdbcEnv = new CfJdbcEnv();
		DataSourceProperties properties = new DataSourceProperties();
		CfCredentials hanaCredentials = cfJdbcEnv.findCredentialsByTag("hana");

		if (hanaCredentials != null) {

			String uri = hanaCredentials.getUri("hana");
			properties.setUrl(uri);
			properties.setUsername(hanaCredentials.getUsername());
			properties.setPassword(hanaCredentials.getPassword());
		}

		return properties;
	}
```
This is enough to wire up the datasource.
Do not take the snippet literally... just meant to bring the point across...
