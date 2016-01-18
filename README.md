# Spring Cloud Connector for SAP HANA Cloud Platform (HCP) 

This project provides **Cloud Connectors** for [SAP HANA Cloud Platform](http://hcp.sap.com) (HCP) to be used in conjunction with the other connectors of the [Spring Cloud](http://projects.spring.io/spring-cloud/) project. More specifically, it adds HCP as an alternative runtime environment supported by **Spring Cloud**.

For more information regarding `Cloud Connectors` please refer to the respective documentation: 

* [Official documentation](http://cloud.spring.io/spring-cloud-connectors)
* [Github](https://github.com/spring-cloud/spring-cloud-connectors)



The project contains three submodules:


- **[spring-cloud-sap-core](spring-cloud-sap-core)**: The core library provides common data types needed by the other modules/connectors. 
- **[spring-cloud-sap-connector](spring-cloud-sap-connector)**: `Cloud Connector` for [SAP HANA Cloud Platform](http://hcp.sap.com).
- **[spring-cloud-cloudfoundry-hana-service-connector](spring-cloud-cloudfoundry-hana-service-connector)**: A `Service Connector` for SAP HANA, SAP's in-memory database platform, to be used in a Cloud Foundry environment. This module is supposed to accompany the standard [Cloud Foundry Cloud Connector](https://github.com/spring-cloud/spring-cloud-connectors/tree/master/spring-cloud-cloudfoundry-connector). 

##Getting Started

The connectors are written for Maven; simply include the appropriate dependencies for your build system.

###Including cloud connectors

Include the connector for each cloud platform you want to be discoverable. Including multiple connectors is perfectly fine; each connector will determine whether it should be active in a particular environment.

````xml
<!-- to use Spring Cloud for development -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-localconfig-connector</artifactId>
    <version>1.2.0.RELEASE</version>
</dependency>

<!-- If you intend to deploy your app to a Cloud Foundry environment within SAP HCP-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-cloudfoundry-connector</artifactId>
    <version>1.2.0.RELEASE</version>
</dependency>
<dependency>
    <groupId>com.sap.hana.cloud</groupId>
    <artifactId>spring-cloud-cloudfoundry-hana-service-connector</artifactId>
    <version>1.0.4.RELEASE</version>
</dependency>

<!-- If you intend to deploy the app to SAP HANA Cloud Platform-->
<dependency>
    <groupId>com.sap.hana.cloud</groupId>
    <artifactId>spring-cloud-sap-connector</artifactId>
    <version>1.0.4.RELEASE</version>
</dependency>
````

###Spring applications

Add the [`spring-service-connector`](https://github.com/spring-cloud/spring-cloud-connectors/tree/master/spring-cloud-spring-service-connector) in addition to your cloud connectors:

````xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-spring-service-connector</artifactId>
	<version>1.2.0.RELEASE</version>
</dependency>
````

###Sample application

A simple sample application is provided here: [https://github.com/SAP/cloud-hello-spring-cloud](https://github.com/SAP/cloud-hello-spring-cloud)

