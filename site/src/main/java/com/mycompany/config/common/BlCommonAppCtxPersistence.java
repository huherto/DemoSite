package com.mycompany.config.common;

import java.util.Arrays;
import java.util.List;

import org.broadleafcommerce.common.extensibility.jpa.JPAPropertiesPersistenceUnitPostProcessor;
import org.broadleafcommerce.common.extensibility.jpa.MergePersistenceUnitManager;
import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class BlCommonAppCtxPersistence {
	/*
    <tx:annotation-driven/>
    */

	/* <bean id="blJpaVendorAdapter" class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"/> */
	@Bean
	public HibernateJpaVendorAdapter blJpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}
	
    /*<bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean" depends-on="blCacheManager">
        <property name="jpaVendorAdapter" ref="blJpaVendorAdapter"/>
        <property name="persistenceUnitManager" ref="blPersistenceUnitManager"/>
        <property name="persistenceUnitName" value="blPU"/>
    </bean>*/
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setJpaVendorAdapter(blJpaVendorAdapter());
		bean.setPersistenceUnitManager(blPersistenceUnitManager());
		bean.setPersistenceUnitName("blPU");
		return bean;
	}
	
	/*<bean id="blMergedCacheConfigLocations" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list>
                <value>classpath:bl-common-ehcache.xml</value>
            </list>
        </property>
    </bean> */
	@Bean
	public List<String> blMergedCacheConfigLocations() {
		return Arrays.asList("classpath:bl-common-ehcache.xml");
	}
	
    /* <bean id="blMergedPersistenceXmlLocations" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list>
                <value>classpath*:/META-INF/persistence-common.xml</value>
            </list>
        </property>
    </bean> */
	@Bean
	public List<String> blMergedPersistenceXmlLocations() {
		return Arrays.asList("classpath*:/META-INF/persistence-common.xml");
	}
	

    /*<bean id="blMergedEntityContexts" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list>
                <value>classpath:bl-common-applicationContext-entity.xml</value>
            </list>
        </property>
    </bean>
    */
	@Bean
	public List<String> blMergedEntityContexts() {
		return Arrays.asList("classpath:bl-common-applicationContext-entity.xml");
	}
		
    /*<bean id="blPersistenceUnitManager" class="org.broadleafcommerce.common.extensibility.jpa.MergePersistenceUnitManager">
        <property name="persistenceUnitPostProcessors">
            <list>
                <bean class="org.broadleafcommerce.common.extensibility.jpa.JPAPropertiesPersistenceUnitPostProcessor"/>
            </list>
        </property>
    </bean>*/
	@Bean
	public MergePersistenceUnitManager blPersistenceUnitManager() {
		MergePersistenceUnitManager bean = new MergePersistenceUnitManager();
		bean.setPersistenceUnitPostProcessors(new JPAPropertiesPersistenceUnitPostProcessor());
		return bean;
	}
	
	/*
    <bean id="blEntityConfiguration" class="org.broadleafcommerce.common.persistence.EntityConfiguration"/>
    */	
	@Bean
	EntityConfiguration blEntityConfiguration() {
		return new EntityConfiguration();
	}
	

}
