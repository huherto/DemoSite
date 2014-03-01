package com.mycompany.config.framework;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.broadleafcommerce.common.jmx.AnnotationJmxAttributeSource;
import org.broadleafcommerce.common.jmx.MetadataMBeanInfoAssembler;
import org.broadleafcommerce.common.jmx.MetadataNamingStrategy;
import org.broadleafcommerce.common.payment.AccountNumberMask;
import org.broadleafcommerce.common.payment.UnmaskRange;
import org.broadleafcommerce.core.order.dao.OrderDao;
import org.broadleafcommerce.core.order.service.PageCartRuleProcessor;
import org.broadleafcommerce.core.order.service.StructuredContentCartRuleProcessor;
import org.broadleafcommerce.openadmin.server.util.LocaleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
@ComponentScan(basePackages={"org.broadleafcommerce.core"})
public class BlFrameworkAppCtx {

	/*<context:component-scan base-package="org.broadleafcommerce.core">
        <context:exclude-filter type="regex" expression="org.broadleafcommerce.core.web.*"/>
    </context:component-scan>*/

    /* <task:annotation-driven/> */

	@Autowired
	@Qualifier("blOrderDao")
	OrderDao blOrderDao;

	/* <bean id="exporter" class="org.springframework.jmx.export.MBeanExporter">
        <property name="autodetect" value="true"/>
        <property name="assembler" ref="blAssembler"/>
        <property name="namingStrategy" ref="blNamingStrategy"/>
    </bean> */
	@Bean
	public MBeanExporter exporter() {
		MBeanExporter bean = new MBeanExporter();
		bean.setAutodetect(true);
		bean.setAssembler(blAssembler());
		bean.setNamingStrategy(blNamingStrategy());
		return bean;
	}

    /* <bean id="blAttributeSource" class="org.broadleafcommerce.common.jmx.AnnotationJmxAttributeSource">
        <constructor-arg>
            <bean class="org.springframework.jndi.JndiObjectFactoryBean">
                <property name="jndiName">
                    <value>java:/comp/env/appName</value>
                </property>
                <property name="defaultObject" value="broadleaf"/>
            </bean>
        </constructor-arg>
    </bean> */
	@Bean
	public AnnotationJmxAttributeSource blAttributeSource() {
		JndiObjectFactoryBean jndiBean = new JndiObjectFactoryBean();
		jndiBean.setJndiName("java:/comp/env/appName");
		jndiBean.setDefaultObject("broadleaf");
		// TODO: Not too sure about this. Converted object to string.
		return new AnnotationJmxAttributeSource(jndiBean.getObject().toString());
	}

    /* <bean id="blAssembler" class="org.broadleafcommerce.common.jmx.MetadataMBeanInfoAssembler">
        <property name="attributeSource" ref="blAttributeSource"/>
    </bean> */
	@Bean
	public MetadataMBeanInfoAssembler blAssembler() {
		MetadataMBeanInfoAssembler bean = new MetadataMBeanInfoAssembler();
		bean.setAttributeSource(blAttributeSource());
		return bean;
	}

    /* <bean id="blNamingStrategy" class="org.broadleafcommerce.common.jmx.MetadataNamingStrategy">
        <property name="attributeSource" ref="blAttributeSource"/>
    </bean> */
	@Bean
	public MetadataNamingStrategy blNamingStrategy() {
		MetadataNamingStrategy bean = new MetadataNamingStrategy();
		bean.setAttributeSource(blAttributeSource());
		return bean;
	}

    /* <bean id="blAccountNumberMask" class="org.broadleafcommerce.common.payment.AccountNumberMask">
        <constructor-arg>
            <list>
                <bean class="org.broadleafcommerce.common.payment.UnmaskRange">
                    <constructor-arg value="0"/>
                    <constructor-arg value="0"/>
                </bean>
                <bean class="org.broadleafcommerce.common.payment.UnmaskRange">
                    <constructor-arg value="1"/>
                    <constructor-arg value="4"/>
                </bean>
            </list>
        </constructor-arg>
        <constructor-arg value="X"/>
    </bean> */
	@Bean
	public AccountNumberMask blAccountNumberMask() {
		return new AccountNumberMask(Arrays.asList(new UnmaskRange(0, 0), new UnmaskRange(1, 4)), 'X');
	}

    /* <bean id="blContentCartRuleProcessor" class="org.broadleafcommerce.core.order.service.StructuredContentCartRuleProcessor">
        <property name="orderDao" ref="blOrderDao"/>
        <property name="contextClassNames">
            <map>
                <entry key="discreteOrderItem" value="org.broadleafcommerce.core.order.domain.DiscreteOrderItem" />
            </map>
        </property>
    </bean> */
	@Bean
	public StructuredContentCartRuleProcessor blContentCartRuleProcessor() {
		StructuredContentCartRuleProcessor bean = new StructuredContentCartRuleProcessor();
		bean.setOrderDao(blOrderDao);
		bean.setContextClassNames(Collections.singletonMap("discreteOrderItem", "org.broadleafcommerce.core.order.domain.DiscreteOrderItem"));
		return bean;
	}

    /**** Append the content rule processor that checks for cart contents ****/
    /* <bean id="blContentRuleProcessors" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list>
                <ref bean="blContentCartRuleProcessor"/>
            </list>
        </property>
    </bean> */
	@Bean
	public List<StructuredContentCartRuleProcessor> blContentRuleProcessors() {
		return Collections.singletonList(blContentCartRuleProcessor());
	}

    /* <bean id="blPageCartRuleProcessor" class="org.broadleafcommerce.core.order.service.PageCartRuleProcessor">
        <property name="orderDao" ref="blOrderDao"/>
        <property name="contextClassNames">
            <map>
                <entry key="discreteOrderItem" value="org.broadleafcommerce.core.order.domain.DiscreteOrderItem" />
            </map>
        </property>
    </bean> */
	@Bean
	public PageCartRuleProcessor blPageCartRuleProcessor() {
		PageCartRuleProcessor bean = new PageCartRuleProcessor();
		bean.setOrderDao(blOrderDao);
		bean.setContextClassNames(Collections.singletonMap("discreteOrderItem", "org.broadleafcommerce.core.order.domain.DiscreteOrderItem"));
		return bean;
	}

    /**** Append the content rule processor that checks for cart contents ****/
    /* <bean id="blPageRuleProcessors" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list>
                <ref bean="blPageCartRuleProcessor"/>
            </list>
        </property>
    </bean> */
	@Bean
	public List<PageCartRuleProcessor> blPageRuleProcessors() {
		return Collections.singletonList(blPageCartRuleProcessor());
	}

    /* <bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
        <property name="converters">
            <set>
                <bean class="org.broadleafcommerce.openadmin.server.util.LocaleConverter" />
            </set>
        </property>
    </bean> */
	@Bean
	public ConversionServiceFactoryBean conversionService() {
		ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
		bean.setConverters(Collections.singleton(new LocaleConverter()));
		return bean;
	}
}
