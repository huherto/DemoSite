package com.mycompany.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
public class AppCtxDataSources {

    /** The "webDS" data source is the main data source for Broadleaf. It is referenced and
    should be configured via JNDI in your particular environment. For local testing and
    development using Jetty, the JNDI data source is configured in the /WEB-INF/jetty-env.xml file.
    The other data sources are required as well.  They allow Broadleaf to use different databases
    for secure information such as payment info when in a PCI compliant situation, and/or for CMS
    if you wish to store content in a separate database. **/

    private final JndiDataSourceLookup lookupJndi = new JndiDataSourceLookup();

	@Bean
	public DataSource webDS() {
		return lookupJndi.getDataSource("web");
	}

	@Bean
	public DataSource webSecureDS() {
		return lookupJndi.getDataSource("secure");
	}

	@Bean
	public DataSource cmsStorageDS() {
		return lookupJndi.getDataSource("jdbc/storage");
	}


}
