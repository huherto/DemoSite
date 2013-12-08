package com.mycompany.config.framework;

import java.util.Arrays;
import java.util.List;

import org.broadleafcommerce.common.web.BroadleafThymeleafMessageResolver;
import org.broadleafcommerce.common.web.NullBroadleafTemplateResolver;
import org.broadleafcommerce.common.web.dialect.BLCDialect;
import org.broadleafcommerce.common.web.expression.BroadleafVariableExpression;
import org.broadleafcommerce.common.web.expression.NullBroadleafVariableExpression;
import org.broadleafcommerce.common.web.expression.PropertiesVariableExpression;
import org.broadleafcommerce.core.web.order.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.dialect.SpringStandardDialect;
import org.thymeleaf.spring3.messageresolver.SpringMessageResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.mycompany.config.common.BlCommonAppCtx;

@Configuration
@ComponentScan(basePackages="org.broadleafcommerce.core.web")
public class BlFrameworkWebAppCtx {

	@Autowired
	BlCommonAppCtx bcac;

    /**** Scan Broadleaf defined web utility classes ****/
    /* <context:component-scan base-package="org.broadleafcommerce.core.web"/> */

    /*<bean id="blOrderState"
          class="org.broadleafcommerce.core.web.order.OrderState" scope="request"/> */
	@Bean @Scope("request")
	public OrderState object() {
		return new OrderState();
	}

    /* <bean id="orderStateAOP"
          class="org.broadleafcommerce.core.web.order.OrderStateAOP"/> */

    /* <aop:config>
        <aop:aspect id="orderStateAspect" ref="orderStateAOP">
            <aop:pointcut id="orderRetrievalMethod"
                          expression="execution(* org.broadleafcommerce.core.order.dao.OrderDao.readCartForCustomer(org.broadleafcommerce.profile.core.domain.Customer))"/>
            <aop:around method="processOrderRetrieval" pointcut-ref="orderRetrievalMethod"/>
        </aop:aspect>
    </aop:config> */

    /**** This core Broadleaf dialect should not be extended by implementors. Instead, define your ****/
    /**** own dialect and add your processors there. ****/
    /* <bean id="blDialect" class="org.broadleafcommerce.common.web.dialect.BLCDialect">
        <property name="processors">
          <set>
            <ref bean="blContentProcessor"/>
            <ref bean="blAddSortLinkProcessor" />
            <ref bean="blCategoriesProcessor" />
            <ref bean="blFormProcessor" />
            <ref bean="blGoogleAnalyticsProcessor" />
            <ref bean="blHeadProcessor" />
            <ref bean="blNamedOrderProcessor" />
            <ref bean="blPaginationPageLinkProcessor" />
            <ref bean="blPriceTextDisplayProcessor" />
            <ref bean="blProductOptionValueProcessor" />
            <ref bean="blProductOptionsProcessor" />
            <ref bean="blProductOptionDisplayProcessor" />
            <ref bean="blRatingsProcessor" />
            <ref bean="blRelatedProductProcessor" />
            <ref bean="blRemoveFacetValuesLinkProcessor" />
            <ref bean="blToggleFacetLinkProcessor" />
            <ref bean="blUrlRewriteProcessor" />
            <ref bean="blResourceBundleProcessor" />
          </set>
        </property>
    </bean> */
	@Bean
	public BLCDialect blDialect() {
		return new InitializedBLCDialect();
	}

    /**** This list factory bean will accept classes that implment the BroadleafVariableExpression interface.
          This provides the ability to inject custom expression evaluators into Thymeleaf. ****/
    /* <bean id="blVariableExpressions" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list>
                <bean class="org.broadleafcommerce.common.web.expression.NullBroadleafVariableExpression" />
                <bean class="org.broadleafcommerce.common.web.expression.PropertiesVariableExpression" />
            </list>
        </property>
    </bean> */
	@Bean
	public List<BroadleafVariableExpression> blVariableExpressions() {
		return Arrays.asList(new NullBroadleafVariableExpression(), new PropertiesVariableExpression() );
	}

    /* This component should be overridden to provide templates outside of the WAR or CLASSPATH   */
    /* <bean id="blWebCustomTemplateResolver" class="org.broadleafcommerce.common.web.NullBroadleafTemplateResolver" */
	@Bean
	public NullBroadleafTemplateResolver blWebCustomTemplateResolver() {
		return new NullBroadleafTemplateResolver();
	}

	@Value("${cache.page.templates}")
	private boolean cacheable;

	@Value("${cache.page.templates.ttl}")
	private Long cacheTTLMs;

    /* <bean id="blWebTemplateResolver" class="org.thymeleaf.templateresolver.ServletContextTemplateResolver">
        <property name="prefix" value="/WEB-INF/templates/" />
        <property name="suffix" value=".html" />
        <property name="templateMode" value="HTML5" />
        <property name="characterEncoding" value="UTF-8" />
        <property name="cacheable" value="${cache.page.templates}"/>
        <property name="cacheTTLMs" value="${cache.page.templates.ttl}" />
        <property name="order" value="200"/>
    </bean> */
	@Bean
	public ServletContextTemplateResolver blWebTemplateResolver() {
		ServletContextTemplateResolver bean = new ServletContextTemplateResolver();
		bean.setPrefix("/WEB-INF/templates/");
		bean.setSuffix(".html");
		bean.setTemplateMode("HTML5");
		bean.setCharacterEncoding("UTF-8");
		bean.setCacheable(cacheable);
		bean.setCacheTTLMs(cacheTTLMs);
		bean.setOrder(200);
		return bean;
	}

    /* <bean id="blWebClasspathTemplateResolver" class="org.thymeleaf.templateresolver.ClassLoaderTemplateResolver">
        <property name="prefix" value="webTemplates/" />
        <property name="suffix" value=".html" />
        <property name="templateMode" value="HTML5" />
        <property name="characterEncoding" value="UTF-8" />
        <property name="cacheable" value="${cache.page.templates}"/>
        <property name="cacheTTLMs" value="${cache.page.templates.ttl}" />
        <property name="order" value="300"/>
    </bean> */
	@Bean
	public ClassLoaderTemplateResolver blWebClasspathTemplateResolver() {
		ClassLoaderTemplateResolver bean = new ClassLoaderTemplateResolver();
		bean.setPrefix("webTemplates/");
		bean.setSuffix(".html");
		bean.setTemplateMode("HTML5");
		bean.setCharacterEncoding("UTF-8");
		bean.setCacheable(cacheable);
		bean.setCacheTTLMs(cacheTTLMs);
		bean.setOrder(300);
		return bean;
	}

    /**** This component should be overridden to provide templates outside of the WAR or CLASSPATH   ****/
    /* <bean id="blEmailCustomTemplateResolver" class="org.broadleafcommerce.common.web.NullBroadleafTemplateResolver" */
	@Bean
	public NullBroadleafTemplateResolver blEmailCustomTemplateResolver() {
		return new NullBroadleafTemplateResolver();
	}

    /* <bean id="blEmailTemplateResolver" class="org.thymeleaf.templateresolver.ServletContextTemplateResolver">
        <property name="prefix" value="/WEB-INF/emailTemplates/" />
        <property name="suffix" value=".html" />
        <property name="templateMode" value="HTML5" />
        <property name="characterEncoding" value="UTF-8" />
        <property name="cacheable" value="${cache.page.templates}"/>
        <property name="cacheTTLMs" value="${cache.page.templates.ttl}" />
        <property name="order" value="200"/>
    </bean> */
	@Bean
	public ServletContextTemplateResolver blEmailTemplateResolver() {
		ServletContextTemplateResolver bean = new ServletContextTemplateResolver();
		bean.setPrefix("/WEB-INF/emailTemplates/");
		bean.setSuffix(".html");
		bean.setTemplateMode("HTML5");
		bean.setCharacterEncoding("UTF-8");
		bean.setCacheable(cacheable);
		bean.setCacheTTLMs(cacheTTLMs);
		bean.setOrder(200);
		return bean;
	}

    /* <bean id="blEmailClasspathTemplateResolver" class="org.thymeleaf.templateresolver.ClassLoaderTemplateResolver">
        <property name="prefix" value="emailTemplates/" />
        <property name="suffix" value=".html" />
        <property name="templateMode" value="HTML5" />
        <property name="cacheable" value="${cache.page.templates}"/>
        <property name="cacheTTLMs" value="${cache.page.templates.ttl}" />
        <property name="characterEncoding" value="UTF-8" />
    </bean> */
	@Bean
	public ClassLoaderTemplateResolver blEmailClasspathTemplateResolver() {
		ClassLoaderTemplateResolver bean = new ClassLoaderTemplateResolver();
		bean.setPrefix("webTemplates/");
		bean.setSuffix(".html");
		bean.setTemplateMode("HTML5");
		bean.setCharacterEncoding("UTF-8");
		bean.setCacheable(cacheable);
		bean.setCacheTTLMs(cacheTTLMs);
		bean.setOrder(300);
		return bean;
	}

    /* <bean id="thymeleafSpringStandardDialect" class="org.thymeleaf.spring3.dialect.SpringStandardDialect" /> */
	@Bean
	public SpringStandardDialect thymeleafSpringStandardDialect() {
		return new SpringStandardDialect();
	}

    /* <bean id="blMessageResolver" class="org.broadleafcommerce.common.web.BroadleafThymeleafMessageResolver" /> */
	@Bean
	public BroadleafThymeleafMessageResolver blMessageResolver() {
		return new BroadleafThymeleafMessageResolver();
	}

    /* <bean id="blWebTemplateEngine" class="org.thymeleaf.spring3.SpringTemplateEngine">
        <property name="messageResolvers">
            <set>
                <ref bean="blMessageResolver" />
                <bean class="org.thymeleaf.spring3.messageresolver.SpringMessageResolver" />
            </set>
        </property>
        <property name="templateResolvers">
            <set>
                <ref bean="blWebTemplateResolver" />
                <ref bean="blWebClasspathTemplateResolver" />
                <ref bean="blWebCustomTemplateResolver" />
                <ref bean="blWebCommonClasspathTemplateResolver" />
            </set>
        </property>
        <property name="dialects">
            <set>
                <ref bean="thymeleafSpringStandardDialect" />
                <ref bean="blDialect" />
            </set>
        </property>
    </bean> */
	@Bean
	public SpringTemplateEngine blWebTemplateEngine() {
		SpringTemplateEngine bean = new SpringTemplateEngine();
		bean.addMessageResolver(blMessageResolver());
		bean.addMessageResolver(new SpringMessageResolver());
		bean.addTemplateResolver(blWebTemplateResolver());
		bean.addTemplateResolver(blWebClasspathTemplateResolver());
		bean.addTemplateResolver(blWebCustomTemplateResolver());
		bean.addTemplateResolver(bcac.blWebCommonClasspathTemplateResolver());
		bean.addDialect(thymeleafSpringStandardDialect());
		bean.addDialect(blDialect());
		return bean;
	}

    /* <bean id="blEmailTemplateEngine" class="org.thymeleaf.spring3.SpringTemplateEngine">
        <property name="templateResolvers">
            <set>
                <ref bean="blEmailTemplateResolver" />
                <ref bean="blEmailClasspathTemplateResolver" />
                <ref bean="blEmailCustomTemplateResolver" />
            </set>
        </property>
        <property name="dialects">
            <set>
                <ref bean="thymeleafSpringStandardDialect" />
                <ref bean="blDialect" />
            </set>
        </property>
    </bean> */
	@Bean
	public SpringTemplateEngine blEmailTemplateEngine() {
		SpringTemplateEngine bean = new SpringTemplateEngine();
		bean.addTemplateResolver(blEmailTemplateResolver());
		bean.addTemplateResolver(blEmailClasspathTemplateResolver());
		bean.addTemplateResolver(blEmailCustomTemplateResolver());
		bean.addDialect(thymeleafSpringStandardDialect());
		bean.addDialect(blDialect());
		return bean;
	}
}
