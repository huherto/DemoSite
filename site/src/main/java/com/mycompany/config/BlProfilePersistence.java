package com.mycompany.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transaction;

import org.broadleafcommerce.common.extensibility.cache.ehcache.MergeEhCacheManagerFactoryBean;
import org.broadleafcommerce.common.jmx.ExplicitNameFactoryBean;
import org.broadleafcommerce.common.jmx.MetadataMBeanInfoAssembler;
import org.hibernate.jmx.StatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Configuration
public class BlProfilePersistence {

	/*
	<bean id="blMergedPersistenceXmlLocations" class="org.springframework.beans.factory.config.ListFactoryBean">
    	<property name="sourceList">
        	<list>
            	<value>classpath*:/META-INF/persistence-profile.xml</value>
        	</list>
    	</property>
	</bean> */
	@SuppressWarnings("rawtypes")
	@Bean
	List blMergedPersistenceXmlLocations() {
		return Arrays.asList("classpath*:/META-INF/persistence-profile.xml");
	}
	
	
	/* <bean id="blCacheManager" class="org.broadleafcommerce.common.extensibility.cache.ehcache.MergeEhCacheManagerFactoryBean">
    	<property name="shared" value="true"/>
	</bean> */
	@Bean
	MergeEhCacheManagerFactoryBean blCacheManager() {
		return new MergeEhCacheManagerFactoryBean();
	}
	
	/*
	<bean id="blMergedCacheConfigLocations" class="org.springframework.beans.factory.config.ListFactoryBean">
	    <property name="sourceList">
	        <list>
	            <value>classpath:bl-ehcache.xml</value>
	        </list>
	    </property>
	</bean> */
	@Bean
	List<String> blMergedCacheConfigLocations() {
		return Arrays.asList("classpath:bl-ehcache.xml");
	}
	
	/*
	<bean id="blMergedEntityContexts" class="org.springframework.beans.factory.config.ListFactoryBean">
	    <property name="sourceList">
	        <list>
	            <value>classpath:bl-profile-applicationContext-entity.xml</value>
	        </list>
	    </property>
	</bean> */
	@Bean
	List<String> blMergedEntityContexts() {
		return Arrays.asList("classpath:bl-profile-applicationContext-entity.xml");
	}
	
	/*
	<bean id="hibernateExporter" class="org.springframework.jmx.export.MBeanExporter" depends-on="entityManagerFactory">
	    <property name="autodetect" value="false" />
	    <property name="assembler">
	        <bean id="jmxAssembler"
	            class="org.broadleafcommerce.common.jmx.MetadataMBeanInfoAssembler">
	            <property name="attributeSource">
	                <bean
	                    class="org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource" />
	            </property>
	        </bean>
	    </property>
	    <property name="beans">
	        <map>
	            <entry>
	                <key>
	                    <bean class="org.broadleafcommerce.common.jmx.ExplicitNameFactoryBean">
	                        <constructor-arg value="org.broadleafcommerce:name=hibernate.statistics"/>
	                        <constructor-arg>
	                            <bean class="org.springframework.jndi.JndiObjectFactoryBean">
	                                <property name="jndiName">
	                                    <value>java:/comp/env/appName</value>
	                                </property>
	                                <property name="defaultObject" value="broadleaf"/>
	                            </bean>
	                        </constructor-arg>
	                    </bean>
	                </key>
	                <bean class="org.hibernate.jmx.StatisticsService">
	                    <property name="statisticsEnabled" value="false" />
	                    <property name="sessionFactory">
	                        <util:property-path
	                            path="entityManagerFactory.sessionFactory" />
	                    </property>
	                </bean>
	            </entry>
	        </map>
	    </property>
	</bean> */
	
	@Bean MBeanExporter hibernateExporter() {
		MBeanExporter bean = new MBeanExporter();
		bean.setAutodetect(false);

		MetadataMBeanInfoAssembler jmxAssembler = new MetadataMBeanInfoAssembler();
		jmxAssembler.setAttributeSource(new AnnotationJmxAttributeSource());		
		bean.setAssembler(jmxAssembler);
		
		Map<String, Object> beans = new HashMap<String, Object>();
		JndiObjectFactoryBean jndiObject = new JndiObjectFactoryBean();
		jndiObject.setJndiName("java:/comp/env/appName");
		jndiObject.setDefaultObject("broadleaf");
		String key = "org.broadleafcommerce:name=hibernate.statistics-"+jndiObject.getObject();
		StatisticsService statsService = new StatisticsService();
		statsService.setStatisticsEnabled(false);
		// statsService.setSessionFactory(entityManagerFactory().sessionFactory);
		beans.put(key, statsService);		
		bean.setBeans(beans);		
		return bean;
	}
	
	/*
	<bean id="blTransactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
    	<property name="entityManagerFactory" ref="entityManagerFactory" />
	</bean>
	 */
	@Bean
	JpaTransactionManager blTransactionManager() {
		JpaTransactionManager bean = new JpaTransactionManager();
//		bean.setEntityManagerFactory(entityManagerFactory());
		return bean;
	}
	
	
	/*
Here there is a suggestion...http://stackoverflow.com/questions/14068525/javaconfig-replacing-aopadvisor-and-txadvice

Probably the best long would be to mark the Methods using the @Transactional annotation.

<tx:advice id="blTxAdvice" transaction-manager="blTransactionManager">
    <tx:attributes>
      <tx:method name="*" propagation="REQUIRED"/>
      <tx:method name="findNextId" propagation="REQUIRES_NEW"/>
    </tx:attributes>
</tx:advice>

<aop:config>
    <aop:pointcut id="blCustomerAddressServiceOperation" expression="execution(* org.broadleafcommerce.profile.core.service.CustomerAddressService.save*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCustomerAddressServiceOperation"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blCustomerAddressServiceOperation2" expression="execution(* org.broadleafcommerce.profile.core.service.CustomerAddressService.make*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCustomerAddressServiceOperation2"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blCustomerAddressServiceOperation3" expression="execution(* org.broadleafcommerce.profile.core.service.CustomerAddressService.delete*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCustomerAddressServiceOperation3"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blAddressServiceOperation" expression="execution(* org.broadleafcommerce.profile.core.service.AddressService.save(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blAddressServiceOperation"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blPhoneServiceOperation" expression="execution(* org.broadleafcommerce.profile.core.service.PhoneService.save(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blPhoneServiceOperation"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blCustomerPhoneServiceOperation" expression="execution(* org.broadleafcommerce.profile.core.service.CustomerPhoneService.save*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCustomerPhoneServiceOperation"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blCustomerPhoneServiceOperation1" expression="execution(* org.broadleafcommerce.profile.core.service.CustomerPhoneService.make*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCustomerPhoneServiceOperation1"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blCustomerPhoneServiceOperation2" expression="execution(* org.broadleafcommerce.profile.core.service.CustomerPhoneService.delete*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCustomerPhoneServiceOperation2"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blCustomerServiceOperation" expression="execution(* org.broadleafcommerce.profile.core.service.CustomerService.save*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCustomerServiceOperation"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blCustomerServiceOperation1" expression="execution(* org.broadleafcommerce.profile.core.service.CustomerService.register*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCustomerServiceOperation1"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blCustomerServiceOperation2" expression="execution(* org.broadleafcommerce.profile.core.service.CustomerService.change*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCustomerServiceOperation2"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blCustomerServiceOperation3" expression="execution(* org.broadleafcommerce.profile.core.service.CustomerService.add*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCustomerServiceOperation3"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blCustomerServiceOperation4" expression="execution(* org.broadleafcommerce.profile.core.service.CustomerService.remove*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCustomerServiceOperation4"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blCustomerServiceOperation5" expression="execution(* org.broadleafcommerce.profile.core.service.CustomerService.reset*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCustomerServiceOperation5"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blCustomerServiceOperation6" expression="execution(* org.broadleafcommerce.profile.core.service.CustomerService.send*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCustomerServiceOperation6"/>
</aop:config>   

<aop:config>
    <aop:pointcut id="blIdGenerationDaoOperation" expression="execution(* org.broadleafcommerce.profile.core.dao.IdGenerationDao.*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blIdGenerationDaoOperation"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blRoleServiceOperation" expression="execution(* org.broadleafcommerce.profile.core.service.RoleService.*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blRoleServiceOperation"/>
</aop:config>

<aop:config>
    <aop:pointcut id="blChallengeQuestionServiceOperation" expression="execution(* org.broadleafcommerce.profile.core.service.ChallengeQuestionService.*(..))"/>
    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blChallengeQuestionServiceOperation"/>
</aop:config>
*/
}
