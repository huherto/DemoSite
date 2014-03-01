package com.mycompany.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.broadleafcommerce.cms.file.service.operation.StaticMapNamedOperationComponent;
import org.broadleafcommerce.common.extensibility.context.merge.LateStageMergeBeanPostProcessor;
import org.broadleafcommerce.common.util.BroadleafMergeResourceBundleMessageSource;
import org.broadleafcommerce.common.web.resource.BroadleafResourceHttpRequestHandler;
import org.broadleafcommerce.core.search.service.SearchService;
import org.broadleafcommerce.core.search.service.solr.SolrSearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import com.mycompany.config.common.BlCommonAppCtx;

@Configuration
public class AppCtx {

	@Autowired
	BlCommonAppCtx bcac;

	@Autowired
	AppCtxDataSources acds;

	@Autowired
	ApplicationContext appCtx;

	@Bean
	public MapFactoryBean blMergedDataSources() {
		MapFactoryBean bean = new MapFactoryBean();
		Map<Object,Object> map = new HashMap<Object,Object>();
		map.put("jdbc/web", acds.webDS());
		map.put("jdbc/webSecure", acds.webSecureDS());
		map.put("jdbc/cmsStorage", acds.cmsStorageDS());
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

    /* <bean id="messageSource" class="org.broadleafcommerce.common.util.BroadleafMergeResourceBundleMessageSource">
    	<property name="basenames">
        	<list>
            	<value>classpath:messages</value>
        	</list>
    	</property>
	</bean> */
	@Bean
	public MessageSource  messageSource() {
		BroadleafMergeResourceBundleMessageSource bean = new BroadleafMergeResourceBundleMessageSource();
		bean.setBasenames("classpath:messages");
		return bean;
	}

    /* <bean id="blMergedEntityContexts" class="org.springframework.beans.factory.config.ListFactoryBean">
	    <property name="sourceList">
	        <list>
	            <value>classpath:applicationContext-entity.xml</value>
	        </list>
	    </property>
	</bean> */
	@Bean
	public List<String> blMergedEntityContexts() {
		return  Arrays.asList("classpath*:applicationContext-entity.xml");
	}

    /*** Delete this bean to enable caching - leaving it on for development is recommended ***/
    /*** as it will allow changes made in the admin or directly on the database to be reflected ***/
    /*** immediately. However, caching is obviously beneficial in production. ***/
    /* <bean id="blMergedCacheConfigLocations" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list>
                <value>classpath:bl-override-ehcache.xml</value>
            </list>
        </property>
    </bean> */
	@Bean
	public List<String> blMergedCacheConfigLocations() {
		return Arrays.asList("classpath:bl-override-ehcache.xml");
	}

    /*** Delete this section to disable the embedded solr search service. Although this will result in a smaller
     * application footprint, it will default the search service to use the database implementation, which
     * is slower and less full-featured. Broadleaf suggests maintaining this solr implementation in the vast
     * majority of cases.
     */
    /* <bean id="solrEmbedded" class="java.lang.String">
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

    /** This is an example of a custom dialect definition that uses a custom processor
     *  The second bean registers the dialct to the blWebTemplateEngine
     * Note that the same thing could be done for the blEmailTemplateEngine
     */
    /* <bean id="myDialect" class="com.mycompany.common.web.dialect.MyDialect">
        <property name="processors">
          <set>
            <bean class="com.mycompany.common.web.processor.MyProcessor" />
          </set>
        </property>
    </bean>
    <bean id="blWebTemplateEngine" class="org.thymeleaf.spring3.SpringTemplateEngine">
        <property name="dialects">
            <set>
                <ref bean="myDialect" />
            </set>
        </property>
    </bean> */

	/**
    <!-- The following two beans are defined like this in Broadleaf Commerce. However, -->
    <!-- you may want to override the bean definitions by uncommenting these two beans -->
    <!-- to control whether or not templates are cacheable. This will generally be desireable -->
    <!-- in production environments, but likely not in development environments. -->
    <!--
    <bean id="blWebTemplateResolver" class="org.thymeleaf.templateresolver.ServletContextTemplateResolver">
        <property name="prefix" value="/WEB-INF/templates/" />
        <property name="suffix" value=".html" />
        <property name="templateMode" value="HTML5" />
        <property name="cacheable" value="${cache.page.templates}"/>
        <property name="cacheTTLMs" value="${cache.page.templates.ttl}" />
        <property name="characterEncoding" value="UTF-8" />
    </bean>
    <bean id="blEmailTemplateResolver" class="org.thymeleaf.templateresolver.ClassLoaderTemplateResolver">
        <property name="prefix" value="emailTemplates/" />
        <property name="suffix" value=".html" />
        <property name="templateMode" value="HTML5" />
        <property name="cacheable" value="${cache.page.templates}"/>
        <property name="cacheTTLMs" value="${cache.page.templates.ttl}" />
        <property name="characterEncoding" value="UTF-8" />
    </bean> */

    /* <bean id="jsLocations" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list>
                <value>/js/</value>
            </list>
        </property>
    </bean> */
	@Bean
	public List<Resource> jsLocations() {
		return Arrays.asList(appCtx.getResource("/js/"));
	}

    /* <bean class="org.broadleafcommerce.common.extensibility.context.merge.LateStageMergeBeanPostProcessor">
        <property name="collectionRef" value="jsLocations" />
        <property name="targetRef" value="blJsLocations" />
    </bean> */
	@Bean
	public LateStageMergeBeanPostProcessor lateStageMergeBeanPostProcessor1() {
		LateStageMergeBeanPostProcessor bean = new LateStageMergeBeanPostProcessor();
		bean.setCollectionRef("jsLocations");
		bean.setTargetRef("blJsLocations");
		return bean;
	}

    /* <bean id="cssLocations" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list>
                <value>/css/</value>
            </list>
        </property>
    </bean> */
	@Bean
	public List<Resource> cssLocations() {
		return Arrays.asList(appCtx.getResource("/css/"));
	}

    /* <bean class="org.broadleafcommerce.common.extensibility.context.merge.LateStageMergeBeanPostProcessor">
        <property name="collectionRef" value="cssLocations" />
        <property name="targetRef" value="blCssLocations" />
    </bean> */
	@Bean
	public LateStageMergeBeanPostProcessor lateStageMergeBeanPostProcessor2() {
		LateStageMergeBeanPostProcessor bean = new LateStageMergeBeanPostProcessor();
		bean.setCollectionRef("cssLocations");
		bean.setTargetRef("blCssLocations");
		return bean;
	}

	private <T> List<T> join(Collection<T> list1, Collection<T> list2) {
		List<T> join = new ArrayList<T>();
		join.addAll(list1);
		join.addAll(list2);
		return join;
	}

    /* <bean id="blJsResources" class="org.broadleafcommerce.common.web.resource.BroadleafResourceHttpRequestHandler">
        <property name="locations" ref="blJsLocations"/>
    </bean> */
	@Bean
	public BroadleafResourceHttpRequestHandler blJsResources() {
		BroadleafResourceHttpRequestHandler bean = new BroadleafResourceHttpRequestHandler();
		bean.setLocations(join(bcac.blJsLocations(), jsLocations()));
		return bean;
	}

    /* <bean id="blCssResources" class="org.broadleafcommerce.common.web.resource.BroadleafResourceHttpRequestHandler">
        <property name="locations" ref="blCssLocations"/>
    </bean> */
	@Bean
	public BroadleafResourceHttpRequestHandler blCssResources() {
		BroadleafResourceHttpRequestHandler bean = new BroadleafResourceHttpRequestHandler();
		bean.setLocations(join(bcac.blCssLocations(), cssLocations()));
		return bean;
	}

}
