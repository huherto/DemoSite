package com.mycompany.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import org.broadleafcommerce.cms.file.service.operation.StaticMapNamedOperationComponent;
import org.broadleafcommerce.common.util.BroadleafMergeResourceBundleMessageSource;
import org.broadleafcommerce.core.search.service.SearchService;
import org.broadleafcommerce.core.search.service.solr.SolrSearchServiceImpl;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.xml.sax.SAXException;

@Configuration
public class AppConfig {


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

	@Bean
	public MapFactoryBean blMergedDataSources() {

		MapFactoryBean bean = new MapFactoryBean();
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("jdbc/web", webDS());
		map.put("jdbc/webSecure", webSecureDS());
		map.put("jdbc/cmsStorage", cmsStorageDS());
		bean.setSourceMap(map);
		return bean;
	}


    /*<bean id="blMergedPersistenceXmlLocations" class="org.springframework.beans.factory.config.ListFactoryBean">
    <property name="sourceList">
        <list>
            	<value>classpath*:/META-INF/persistence.xml</value>
            </list>
        </property>
    </bean>*/

	@Bean
	public List<String> blMergedPersistenceXmlLocations() {
		return Arrays.asList("classpath*:/META-INF/persistence.xml");
	}

    /*
    <bean id="messageSource" class="org.broadleafcommerce.common.util.BroadleafMergeResourceBundleMessageSource">
    	<property name="basenames">
        	<list>
            	<value>classpath:messages</value>
        	</list>
    	</property>
	</bean>
	 */

	@Bean
	public MessageSource  messageSource() {
		BroadleafMergeResourceBundleMessageSource bean = new BroadleafMergeResourceBundleMessageSource();
		bean.setBasenames("classpath:messages");
		return bean;
	}
/*
    <bean id="blMergedEntityContexts" class="org.springframework.beans.factory.config.ListFactoryBean">
    <property name="sourceList">
        <list>
            <value>classpath:applicationContext-entity.xml</value>
        </list>
    </property>
</bean>
*/
	@Bean
	public List<String> blMergedEntityContexts() {
		return  Arrays.asList("classpath*:applicationContext-entity.xml");
	}
/*
    <!-- Delete this bean to enable caching - leaving it on for development is recommended -->
    <!-- as it will allow changes made in the admin or directly on the database to be reflected -->
    <!-- immediately. However, caching is obviously beneficial in production. -->

    <bean id="blMergedCacheConfigLocations" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list>
                <value>classpath:bl-override-ehcache.xml</value>
            </list>
        </property>
    </bean>
    */

	@Bean
	public List<String> blMergedCacheConfigLocations() {
		return Arrays.asList("classpath:bl-override-ehcache.xml");
	}

	/*
    <!-- Delete this section to disable the embedded solr search service. Although this will result in a smaller -->
    <!-- application footprint, it will default the search service to use the database implementation, which  -->
    <!-- is slower and less full-featured. Broadleaf suggests maintaining this solr implementation in the vast -->
    <!-- majority of cases. -->
    <bean id="solrEmbedded" class="java.lang.String">
        <constructor-arg value="solrhome"/>
    </bean> */
	@Bean
	public String solrEmbedded() {
		return "solrhome";
	}

	@Bean
	SearchService blSearchService() throws IOException, ParserConfigurationException, SAXException {
		// TODO: fix values.
		return new SolrSearchServiceImpl("${solr.source}", "${solr.source.reindex}");
	}

	/* <bean id="blStaticMapNamedOperationComponent" class="org.broadleafcommerce.cms.file.service.operation.StaticMapNamedOperationComponent">
    <property name="namedOperations">
        <map>
            <entry key="browse">
                <map>
                    <entry key="resize-width-amount" value="400"/>
                    <entry key="resize-height-amount" value="400"/>
                    <entry key="resize-high-quality" value="false"/>
                    <entry key="resize-maintain-aspect-ratio" value="true"/>
                    <entry key="resize-reduce-only" value="true"/>
                </map>
            </entry>
            <entry key="thumbnail">
                <map>
                    <entry key="resize-width-amount" value="60"/>
                    <entry key="resize-height-amount" value="60"/>
                    <entry key="resize-high-quality" value="false"/>
                    <entry key="resize-maintain-aspect-ratio" value="true"/>
                    <entry key="resize-reduce-only" value="true"/>
                </map>
            </entry>
        </map>
    </property>
	</bean> */
	@Bean
	StaticMapNamedOperationComponent blStaticMapNamedOperationComponent() {

		LinkedHashMap<String, LinkedHashMap<String, String>> namedOperations  = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		StaticMapNamedOperationComponent bean = new StaticMapNamedOperationComponent();

		ImageOperations browse = new ImageOperations();
		browse.resizeHeightAmount = 400;
		browse.resizeWidthAmount  = 400;
		namedOperations.put("browse", browse.asMap());

		ImageOperations thumbnail = new ImageOperations();
		thumbnail.resizeHeightAmount = 60;
		thumbnail.resizeWidthAmount  = 60;
		namedOperations.put("thumbnail", thumbnail.asMap());

		bean.setNamedOperations(namedOperations);

		return bean;
	}

}
