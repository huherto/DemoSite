package com.mycompany.config;

import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppCtxEMail {


    /* <bean id="blMailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
	    <property name="host"><value>localhost</value></property>
	    <property name="port"><value>30000</value></property>
	    <property name="protocol"><value>smtp</value></property>
	    <property name="javaMailProperties">
	        <props>
	            <prop key="mail.smtp.starttls.enable">true</prop>
	            <prop key="mail.smtp.timeout">25000</prop>
	        </props>
	    </property>
	</bean>

	<bean id="blEmailTemplateResolver" class="org.thymeleaf.templateresolver.ClassLoaderTemplateResolver">
	    <property name="prefix" value="emailTemplates/" />
	    <property name="suffix" value=".html" />
	    <property name="cacheable" value="${cache.page.templates}"/>
	    <property name="cacheTTLMs" value="${cache.page.templates.ttl}" />
	</bean>

	<bean id="blEmailTemplateEngine" class="org.thymeleaf.spring3.SpringTemplateEngine">
	    <property name="templateResolvers">
	        <set>
	            <ref bean="blEmailTemplateResolver" />
	        </set>
	    </property>
	    <property name="dialects">
	        <set>
	            <bean class="org.thymeleaf.spring3.dialect.SpringStandardDialect" />
	            <ref bean="blDialect" />
	        </set>
	    </property>
	</bean>

	<bean id="blMessageCreator" class="org.broadleafcommerce.common.email.service.message.ThymeleafMessageCreator">
	    <constructor-arg ref="blEmailTemplateEngine"/>
	    <constructor-arg ref="blMailSender"/>
	</bean> */

	/* <bean id="blMessageCreator" class="org.broadleafcommerce.common.email.service.message.NullMessageCreator">
	    <constructor-arg ref="blMailSender"/>
	</bean> */

	/* <bean id="blEmailInfo" class="org.broadleafcommerce.common.email.service.info.EmailInfo">
	    <property name="fromAddress"><value>support@mycompany.com</value></property>
	    <property name="sendAsyncPriority"><value>2</value></property>
	    <property name="sendEmailReliableAsync"><value>false</value></property>
	</bean> */
	@Bean
	public EmailInfo blEmailInfo() {
		EmailInfo bean = new EmailInfo();
		bean.setFromAddress("support@mycompany.com");
		bean.setSendAsyncPriority("2");
		bean.setSendEmailReliableAsync("false");
		return bean;
	}

	/* <bean id="blRegistrationEmailInfo" parent="blEmailInfo">
	    <property name="subject" value="You have successfully registered!"/>
	    <property name="emailTemplate" value="register-email"/>
	</bean> */
	@Bean
	public EmailInfo blRegistrationEmailInfo() {
		EmailInfo bean = blEmailInfo().clone();
		bean.setSubject("You have successfully registered!");
		bean.setEmailTemplate("register-email");
		return bean;
	}

	/* <bean id="blForgotPasswordEmailInfo" parent="blEmailInfo">
	    <property name="subject" value="Reset password request"/>
	    <property name="emailTemplate" value="resetPassword-email"/>
	</bean> */
	@Bean
	public EmailInfo blForgotPasswordEmailInfo() {
		EmailInfo bean = blEmailInfo().clone();
		bean.setSubject("Reset password request");
		bean.setEmailTemplate("resetPassword-email");
		return bean;
	}

	/* <bean id="blOrderConfirmationEmailInfo" parent="blEmailInfo">
	    <property name="subject" value="Your order with The Heat Clinic"/>
	    <property name="emailTemplate" value="orderConfirmation-email"/>
	</bean> */
	@Bean
	public EmailInfo blOrderConfirmationEmailInfo() {
		EmailInfo bean = blEmailInfo().clone();
		bean.setSubject("Your order with The Heat Clinic");
		bean.setEmailTemplate("orderConfirmation-email");
		return bean;
	}

}
