package com.mycompany.config.framework;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.mycompany.config.common.BlCommonAppCtxPersistence;

@Configuration
public class BlFrameworkAppCtxPersistence {
	
	@Autowired
	BlCommonAppCtxPersistence blcap;
	
	/*
    <bean id="blMergedEntityContexts" class="org.springframework.beans.factory.config.ListFactoryBean">
	    <property name="sourceList">
	        <list>
	            <value>classpath:bl-framework-applicationContext-entity.xml</value>
	        </list>
	    </property>
	</bean> */

	@Bean
	public List<String> blMergedEntityContexts() {
		return Arrays.asList("classpath:bl-framework-applicationContext-entity.xml");		
	}
	
	/*<bean id="blMergedPersistenceXmlLocations" class="org.springframework.beans.factory.config.ListFactoryBean">
	    <property name="sourceList">
	        <list>
	            <value>classpath*:/META-INF/persistence-framework.xml</value>
	        </list>
	    </property>
	</bean>*/
	@Bean 
	public List<String> blMergedPersistenceXmlLocations() {
		return Arrays.asList("classpath*:/META-INF/persistence-framework.xml");
	}

	/*<bean id="blEntityManagerFactorySecureInfo" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean" depends-on="blCacheManager">
	    <property name="jpaVendorAdapter" ref="blJpaVendorAdapter"/>
	    <property name="persistenceUnitManager" ref="blPersistenceUnitManager" />
	    <property name="persistenceUnitName" value="blSecurePU"/>
	</bean>*/
	@Bean
	public LocalContainerEntityManagerFactoryBean blEntityManagerFactorySecureInfo() {		
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setJpaVendorAdapter(blcap.blJpaVendorAdapter());
		bean.setPersistenceUnitManager(blcap.blPersistenceUnitManager());
		bean.setPersistenceUnitName("blSecurePU");
		return bean;
	}
	
	/*<bean id="blTransactionManagerSecureInfo" class="org.springframework.orm.jpa.JpaTransactionManager">
	    <property name="entityManagerFactory" ref="blEntityManagerFactorySecureInfo" />
	</bean>*/
	@Bean
	public JpaTransactionManager blTransactionManagerSecureInfo() {
		return  new JpaTransactionManager(blEntityManagerFactorySecureInfo().getObject());		
	}
	
	/*<tx:advice id="blTxAdviceSecureInfo" transaction-manager="blTransactionManagerSecureInfo">
	    <tx:attributes>
	      <tx:method name="*" propagation="REQUIRED"/>
	    </tx:attributes>
	</tx:advice>
	
	<aop:config>
	    <aop:pointcut id="blLegacyOrderServiceOperation" expression="execution(* org.broadleafcommerce.core.order.service.legacy.LegacyOrderService.*(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blLegacyOrderServiceOperation" order="1"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blLegacyCartServiceOperation" expression="execution(* org.broadleafcommerce.core.order.service.legacy.LegacyCartService.*(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blLegacyCartServiceOperation" order="1"/>
	</aop:config>
	
	<!--<aop:config>
	    <aop:pointcut id="blOrderDaoOperation" expression="execution(* org.broadleafcommerce.core.order.dao.OrderDao.save(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blOrderDaoOperation"/>
	</aop:config>-->
	
	<!--<aop:config>
	    <aop:pointcut id="blOrderDaoOperation2" expression="execution(* org.broadleafcommerce.core.order.dao.OrderDao.delete(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blOrderDaoOperation2"/>
	</aop:config>-->
	
	<!--<aop:config>
	    <aop:pointcut id="blOrderDaoOperation3" expression="execution(* org.broadleafcommerce.core.order.dao.OrderDao.submit*(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blOrderDaoOperation3"/>
	</aop:config>-->
	
	<!--<aop:config>
	    <aop:pointcut id="blOrderDaoOperation4" expression="execution(* org.broadleafcommerce.core.order.dao.OrderDao.create*(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blOrderDaoOperation4"/>
	</aop:config>-->
	
	<aop:config>
	    <aop:pointcut id="blOrderDaoOperation5" expression="execution(* org.broadleafcommerce.core.order.dao.OrderDao.update*(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blOrderDaoOperation5"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blSecurePaymentInfoServiceOperation" expression="execution(* org.broadleafcommerce.core.payment.service.SecurePaymentInfoService.save(..))"/>
	    <aop:advisor advice-ref="blTxAdviceSecureInfo" pointcut-ref="blSecurePaymentInfoServiceOperation"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blSecurePaymentInfoServiceOperation2" expression="execution(* org.broadleafcommerce.core.payment.service.SecurePaymentInfoService.remove(..))"/>
	    <aop:advisor advice-ref="blTxAdviceSecureInfo" pointcut-ref="blSecurePaymentInfoServiceOperation2"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blPaymentInfoServiceOperation" expression="execution(* org.broadleafcommerce.core.payment.service.PaymentInfoService.save(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blPaymentInfoServiceOperation"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blPaymentInfoServiceOperation2" expression="execution(* org.broadleafcommerce.core.payment.service.PaymentInfoService.delete(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blPaymentInfoServiceOperation2"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blCodeTypeServiceOperation" expression="execution(* org.broadleafcommerce.core.util.service.CodeTypeService.*(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blCodeTypeServiceOperation"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blOrderMultishipOption" expression="execution(* org.broadleafcommerce.core.order.dao.OrderMultishipOptionDao.save*(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blOrderMultishipOption"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blOrderItemDaoOperation" expression="execution(* org.broadleafcommerce.core.order.dao.OrderItemDao.save*(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blOrderItemDaoOperation"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blOrderItemDaoOperation2" expression="execution(* org.broadleafcommerce.core.order.dao.OrderItemDao.save(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blOrderItemDaoOperation2"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blOrderItemDaoOperation3" expression="execution(* org.broadleafcommerce.core.order.dao.OrderItemDao.delete(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blOrderItemDaoOperation3"/>
	</aop:config>
	
	<!--<aop:config>
	    <aop:pointcut id="blOfferServiceOperation" expression="execution(* org.broadleafcommerce.core.offer.service.OfferService.save*(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blOfferServiceOperation"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blOfferServiceOperation2" expression="execution(* org.broadleafcommerce.core.offer.service.OfferService.save(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blOfferServiceOperation2"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blOfferServiceOperation3" expression="execution(* org.broadleafcommerce.core.offer.service.OfferService.apply*(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blOfferServiceOperation3"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blOfferServiceOperation4" expression="execution(* org.broadleafcommerce.core.offer.service.OfferService.build*(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blOfferServiceOperation4"/>
	</aop:config>-->
	
	<aop:config>
	    <aop:pointcut id="blShippingRateServiceOperation" expression="execution(* org.broadleafcommerce.core.pricing.service.ShippingRateService.*(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blShippingRateServiceOperation"/>
	</aop:config>
	
	<aop:config>
	    <aop:pointcut id="blAvailabilityServiceOperation" expression="execution(* org.broadleafcommerce.core.inventory.service.AvailabilityService.*(..))"/>
	    <aop:advisor advice-ref="blTxAdvice" pointcut-ref="blAvailabilityServiceOperation"/>
	</aop:config>
*/
}
