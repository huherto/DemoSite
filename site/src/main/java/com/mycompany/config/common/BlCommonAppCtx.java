package com.mycompany.config.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.broadleafcommerce.common.config.RuntimeEnvironmentPropertiesConfigurer;
import org.broadleafcommerce.common.config.RuntimeEnvironmentPropertiesManager;
import org.broadleafcommerce.common.email.service.LoggingMailSender;
import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.email.service.info.NullEmailInfo;
import org.broadleafcommerce.common.email.service.info.ServerInfo;
import org.broadleafcommerce.common.email.service.message.NullMessageCreator;
import org.broadleafcommerce.common.web.BroadleafSiteResolver;
import org.broadleafcommerce.common.web.BroadleafThemeResolver;
import org.broadleafcommerce.common.web.NullBroadleafSiteResolver;
import org.broadleafcommerce.common.web.NullBroadleafThemeResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
@ComponentScan(basePackages={"org.broadleafcommerce.common"})
public class BlCommonAppCtx {

	/*
    <context:component-scan base-package="org.broadleafcommerce.common"/>
    */

    /* <bean id="blConfiguration" class="org.broadleafcommerce.common.config.RuntimeEnvironmentPropertiesConfigurer" /> */
	@Bean 
	public RuntimeEnvironmentPropertiesConfigurer blConfiguration() {
		return new RuntimeEnvironmentPropertiesConfigurer();
	}

    /* <bean id="blConfigurationManager" class="org.broadleafcommerce.common.config.RuntimeEnvironmentPropertiesManager"/> */
	@Bean
	public RuntimeEnvironmentPropertiesManager blConfigurationManager() {
		return new RuntimeEnvironmentPropertiesManager();
	}
	
    /*<bean id="blMessageCreator" class="org.broadleafcommerce.common.email.service.message.NullMessageCreator">
        <constructor-arg ref="blMailSender"/>
    </bean> */
    @Bean
    public NullMessageCreator blMessageCreator() {
    	return new NullMessageCreator(blMailSender());
    }

    /*<!-- to be overriden by 3rd party modules -->
    <bean id="blMergedClassTransformers" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list />
        </property>
    </bean>*/
    @Bean
    public List<String> blMergedClassTransformers() {
    	return new ArrayList<String>();
    }

    /*<bean id="blServerInfo" class="org.broadleafcommerce.common.email.service.info.ServerInfo">
        <property name="serverName" value="localhost"/>
        <property name="serverPort" value="8080"/>
    </bean>*/
    @Bean
    public ServerInfo blServerInfo() {
    	ServerInfo bean = new ServerInfo();
    	bean.setServerName("localhost");
    	bean.setServerPort(8080);
    	return bean;
    }

    /*<bean id="blMailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
        <property name="host"><value>localhost</value></property>
        <property name="port"><value>25</value></property>
        <property name="protocol"><value>smtp</value></property>
        <property name="javaMailProperties">
            <props>
                <prop key="mail.smtp.starttls.enable">true</prop>
                <prop key="mail.smtp.timeout">25000</prop>
            </props>
        </property>
    </bean>*/
    @Bean
    public JavaMailSenderImpl blMailSender() {
    	JavaMailSenderImpl bean = new JavaMailSenderImpl();
    	bean.setHost("localhost");
    	bean.setPort(25);
    	bean.setProtocol("smtp");
    	
    	Properties javaMailProperties = new Properties();
    	javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
    	javaMailProperties.setProperty("mail.smtp.timeout", "25000");
    	bean.setJavaMailProperties(javaMailProperties);
    	return bean;
    }
    
    /*<!-- This mail sender will log the html content generated for the email to the console  -->
    <!-- To enable logging set log level in Log4j to INFO  -->
    <bean id="blLoggingMailSender" class="org.broadleafcommerce.common.email.service.LoggingMailSender" />*/
    @Bean
    public LoggingMailSender blLoggingMailSender() {
    	return new LoggingMailSender();
    }

    /*<bean id="blVelocityEngine" class="org.springframework.ui.velocity.VelocityEngineFactoryBean">
      <property name="velocityProperties">
         <value>
            resource.loader=class
            class.resource.loader.class=org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader
            <!-- class.resource.loader.path=classpath:/config/velocity/templates/ -->
            <!-- Note that jar specification for the .path configuration property conforms to the same rules for the java.net.JarUrlConnection class-->
            <!-- jar.resource.loader.class =org.apache.velocity.runtime.resource.loader.JarResourceLoader
            jar.resource.loader.path = jar:file:/broadleaf-profile.jar/emailTemplates
            file.resource.loader.class=org.apache.velocity.runtime.resource.loader.FileResourceLoader
            file.resource.loader.cache = false
            file.resource.loader.path=${file.root}/WEB-INF/config/velocity/templates-->
         </value>
      </property>
   </bean>*/
    @Bean
    public VelocityEngineFactoryBean blVelocityEngine() {
    	VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
    	Properties velocityProperties = new Properties();
    	// TODO: We probably don't need to use properties, may be we can do direct method calls.
    	velocityProperties.setProperty("resource.loader", "class");
    	velocityProperties.setProperty("jar.resource.loader.path", "jar:file:/broadleaf-profile.jar/emailTemplates");
    	velocityProperties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
    	velocityProperties.setProperty("file.resource.loader.cache", "false");
    	velocityProperties.setProperty("file.resource.loader.path", "${file.root}/WEB-INF/config/velocity/templates");
    	bean.setVelocityProperties(velocityProperties);
    	return bean;
    }
    
    /*<bean id="blEmailInfo" class="org.broadleafcommerce.common.email.service.info.EmailInfo" />*/
    @Bean
    public EmailInfo blEmailInfo() {
    	return new EmailInfo();
    }
    
    /*<bean id="blNullEmailInfo" class="org.broadleafcommerce.common.email.service.info.NullEmailInfo" />*/
    @Bean
    public EmailInfo blNullEmailInfo() throws IOException {
    	return new NullEmailInfo();
    }
    
    /*<bean id="blWebCommonClasspathTemplateResolver" class="org.thymeleaf.templateresolver.ClassLoaderTemplateResolver">
        <property name="prefix" value="common_templates/" />
        <property name="suffix" value=".html" />
        <property name="templateMode" value="HTML5" />        
        <property name="characterEncoding" value="UTF-8" />
        <property name="cacheable" value="${cache.page.templates}"/>
        <property name="cacheTTLMs" value="${cache.page.templates.ttl}" />
        <property name="order" value="500"/> 
    </bean> */ 
        
    @Value("${cache.page.templates}")
    boolean cacheable;
    
    @Value("${cache.page.templates.ttl}")
    Long cacheTTLMs;
    
    @Bean  
    public ClassLoaderTemplateResolver blWebCommonClasspathTemplateResolver() {
    	ClassLoaderTemplateResolver bean = new ClassLoaderTemplateResolver();
    	bean.setPrefix("common_templates");
    	bean.setSuffix(".html");
    	bean.setTemplateMode("HTML5");
    	bean.setCharacterEncoding("UTF-8");
    	bean.setCacheable(cacheable);
    	bean.setCacheTTLMs(cacheTTLMs);
    	bean.setOrder(500);
    	
    	return bean;
    }
    
    /*<bean id="blSiteResolver" class="org.broadleafcommerce.common.web.NullBroadleafSiteResolver" /> */
    @Bean
    public BroadleafSiteResolver blSiteResolver() {
    	return new NullBroadleafSiteResolver();
    }
    
    /*<bean id="blThemeResolver" class="org.broadleafcommerce.common.web.NullBroadleafThemeResolver" /> */
    @Bean
    public BroadleafThemeResolver blThemeResolver() {
    	return new NullBroadleafThemeResolver();
    }
    
    /*<!--  Message creator for velocity templates.  Now should be defined in client properties file. -->
    <!-- bean id="blMessageCreator" class="org.broadleafcommerce.common.email.service.message.VelocityMessageCreator">
        <constructor-arg ref="blVelocityEngine"/>
        <constructor-arg ref="blMailSender"/>
        <constructor-arg>
            <map>
                <entry key="number">
                    <bean class="org.apache.velocity.tools.generic.NumberTool" scope="prototype"/>
                </entry>
                <entry key="date">
                    <bean class="org.apache.velocity.tools.generic.ComparisonDateTool" scope="prototype"/>
                </entry>
                <entry key="list">
                    <bean class="org.apache.velocity.tools.generic.ListTool" scope="prototype"/>
                </entry>
                <entry key="math">
                    <bean class="org.apache.velocity.tools.generic.MathTool" scope="prototype"/>
                </entry>
                <entry key="iterator">
                    <bean class="org.apache.velocity.tools.generic.IteratorTool" scope="prototype"/>
                </entry>
                <entry key="alternator">
                    <bean class="org.apache.velocity.tools.generic.AlternatorTool" scope="prototype"/>
                </entry>
                <entry key="sorter">
                    <bean class="org.apache.velocity.tools.generic.SortTool" scope="prototype"/>
                </entry>
                <entry key="esc">
                    <bean class="org.apache.velocity.tools.generic.EscapeTool" scope="prototype"/>
                </entry>
                <entry key="serverInfo" value-ref="blServerInfo"/>
            </map>
        </constructor-arg>
    </bean  --> */
    
    /* <bean id="blJsLocations" class="org.springframework.beans.factory.config.ListFactoryBean" >
        <property name="sourceList">
            <list>
                <value>classpath:/common_js/</value>
                <value>classpath:/extensions/js/</value><!-- To allow a common place for modules or extensions to put js files. -->
            </list>
        </property>
    </bean> */
    @Bean
    public List<String> blJsLocations() {
    	return Arrays.asList("classpath:/common_js/", "classpath:/extensions/js/");
    }
    /*<bean id="blCssLocations" class="org.springframework.beans.factory.config.ListFactoryBean" >
        <property name="sourceList">
            <list>
            </list>
        </property>
    </bean>*/
    @Bean
    public List<String> blCssLocations() {
    	return Arrays.asList();
    }
    
    /*<bean id="blJsFileList" class="org.springframework.beans.factory.config.ListFactoryBean" >
        <property name="sourceList">
            <list>
            </list>
        </property>
    </bean>*/
    @Bean
    public List<String> blJsFileList() {
    	return Arrays.asList();
    }

}
