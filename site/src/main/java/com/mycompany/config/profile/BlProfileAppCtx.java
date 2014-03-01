package com.mycompany.config.profile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.email.service.info.NullEmailInfo;
import org.broadleafcommerce.common.encryption.PassthroughEncryptionModule;
import org.broadleafcommerce.common.util.SpringAppContext;
import org.broadleafcommerce.common.vendor.service.monitor.ServiceMonitor;
import org.broadleafcommerce.profile.core.service.AddressVerificationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="org.broadleafcommerce.profile.core")
public class BlProfileAppCtx {
	/* <context:component-scan base-package="org.broadleafcommerce.profile.core" /> */

    /* <bean id="blEmailInfo" class="org.broadleafcommerce.common.email.service.info.EmailInfo" /> */
	@Bean
	public EmailInfo blEmailInfo() {
		return new EmailInfo();
	}

    /* <bean id="blNullEmailInfo" class="org.broadleafcommerce.common.email.service.info.NullEmailInfo" /> */
	@Bean
	public EmailInfo blNullEmailInfo() throws IOException {
		return new NullEmailInfo();
	}

    /* <bean id="blRegistrationEmailInfo" parent="blNullEmailInfo" /> */
	@Bean
	public EmailInfo blRegistrationEmailInfo() throws IOException {
		return blNullEmailInfo();
	}

    /* <bean id="blForgotPasswordEmailInfo" parent="blNullEmailInfo" /> */
	@Bean
	public EmailInfo blForgotPasswordEmailInfo() throws IOException {
		return blNullEmailInfo();
	}

    /* <bean id="blForgotUsernameEmailInfo" parent="blNullEmailInfo" /> */
	@Bean
	public EmailInfo blForgotUsernameEmailInfo() throws IOException {
		return blNullEmailInfo();
	}

    /* <bean id="blChangePasswordEmailInfo" parent="blNullEmailInfo" /> */
	@Bean
	public EmailInfo blChangePasswordEmailInfo() throws IOException {
		return blNullEmailInfo();
	}

    /* <bean id="blServiceMonitor" class="org.broadleafcommerce.common.vendor.service.monitor.ServiceMonitor" init-method="init"/> */
	@Bean
	public ServiceMonitor blServiceMonitor() {
		ServiceMonitor bean = new ServiceMonitor();
		bean.init();
		return bean;
	}

    /* <bean id="springAppContext" class="org.broadleafcommerce.common.util.SpringAppContext"/> */
	@Bean
	public SpringAppContext springAppContext() {
		return new SpringAppContext();
	}

    /* <bean id="blEncryptionModule" class="org.broadleafcommerce.common.encryption.PassthroughEncryptionModule"/> */
	@Bean
	public PassthroughEncryptionModule blEncryptionModule() {
		return new PassthroughEncryptionModule();
	}

    /* <bean id="blAddressVerificationProviders" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list/>
        </property>
    </bean> */
	List<AddressVerificationProvider> blAddressVerificationProviders() {
		return new ArrayList<AddressVerificationProvider>();
	}
}
