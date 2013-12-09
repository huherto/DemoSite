package com.mycompany.config;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.broadleafcommerce.cms.web.PageHandlerMapping;
import org.broadleafcommerce.common.web.BroadleafThymeleafViewResolver;
import org.broadleafcommerce.core.web.catalog.CategoryHandlerMapping;
import org.broadleafcommerce.core.web.catalog.ProductHandlerMapping;
import org.broadleafcommerce.core.web.checkout.validator.USShippingInfoFormValidator;
import org.broadleafcommerce.core.web.interceptor.NonResourceWebContentInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.mycompany.config.framework.BlFrameworkWebAppCtx;

@Configuration
@ComponentScan(basePackages={
	"org.broadleafcommerce.common.web",
	"org.broadleafcommerce.core.web",
	"org.broadleafcommerce.profile.web",
	"com.mycompany.controller"})
@EnableWebMvc
public class AppCtxServlet extends WebMvcConfigurerAdapter  {

	@Autowired
	AppCtx ac;

	@Autowired
	BlFrameworkWebAppCtx bfwac;

/*
    /** Scan Broadleaf defined web utility classes -->
    <!--  context:component-scan base-package="org.broadleafcommerce.cms.web"/ -->
    <context:component-scan base-package="org.broadleafcommerce.common.web"/>
    <context:component-scan base-package="org.broadleafcommerce.core.web"/>
    <context:component-scan base-package="org.broadleafcommerce.profile.web"/> */

    /* Turn on AOP annotations (required by Broadleaf)
    <aop:config/> */

    /* Checks for a URL match to a product's SEO URL.
    <bean class="org.broadleafcommerce.core.web.catalog.ProductHandlerMapping">
      <property name="order" value="2"/>
    </bean> */
	@Bean
	public ProductHandlerMapping productHandlerMapping() {
		ProductHandlerMapping bean = new ProductHandlerMapping();
		bean.setOrder(2);
		return bean;
	}

    /* <Checks for a URL match to a CMS managed page URL.
    <bean class="org.broadleafcommerce.cms.web.PageHandlerMapping">
      <property name="order" value="3"/>
    </bean> */
	@Bean
	public PageHandlerMapping pageHandlerMapping() {
		PageHandlerMapping bean = new PageHandlerMapping();
		bean.setOrder(3);
		return bean;
	}

    /* Checks for a URL match to a category's SEO URL
    <bean class="org.broadleafcommerce.core.web.catalog.CategoryHandlerMapping">
      <property name="order" value="4"/>
    </bean> */
	@Bean
	public CategoryHandlerMapping categoryHandlerMapping() {
		CategoryHandlerMapping bean = new CategoryHandlerMapping();
		bean.setOrder(4);
		return bean;
	}

    /* Scan for custom controllers
    <context:component-scan base-package="com.mycompany.controller" /> */

    /* Allow annotation driven controllers
    <mvc:annotation-driven/> */

    /* Disable caching for any non-resource. This prevents the header from becoming stagnant
    <mvc:interceptors>
        <bean id="webContentInterceptor" class="org.broadleafcommerce.core.web.interceptor.NonResourceWebContentInterceptor">
            <property name="cacheSeconds" value="0"/>
            <property name="useExpiresHeader" value="true"/>
            <property name="useCacheControlHeader" value="true"/>
            <property name="useCacheControlNoStore" value="true"/>
        </bean>
        <!-- Interceptor for blLocalCode-->
        <bean class="org.springframework.web.servlet.i18n.LocaleChangeInterceptor">
            <property name="paramName" value="blLocaleCode"/>
        </bean>
    </mvc:interceptors> */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		NonResourceWebContentInterceptor webContentInterceptor = new NonResourceWebContentInterceptor();
		webContentInterceptor.setCacheSeconds(0);
		webContentInterceptor.setUseExpiresHeader(true);
		webContentInterceptor.setUseCacheControlHeader(true);
		webContentInterceptor.setUseCacheControlNoStore(true);
		registry.addInterceptor(webContentInterceptor);

		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("blLocaleCode");
	}

    /* Default locale set
    <bean id="localeResolver" class="org.springframework.web.servlet.i18n.CookieLocaleResolver">
        <property name="defaultLocale" value="en"/>
    </bean> */
	@Bean
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver bean = new CookieLocaleResolver();
		bean.setDefaultLocale(Locale.ENGLISH);
		return bean;
	}

    /* Tell Spring to not try to map things in these directories to controllers
     * Order must be set to supercede the handler configured by the mvc:annotation-driven annotation */
    /* <mvc:resources order="-10" location="/img/" mapping="/img/**" /> */
    /* <mvc:resources order="-10" location="/fonts/" mapping="/fonts/**" /> */
    /* <mvc:resources order="-10" location="favicon.ico" mapping="favicon.ico" /> */
    /* <mvc:resources order="-10" location="robots.txt" mapping="robots.txt" /> */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/img/**").addResourceLocations("/img/");
	    registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
	    registry.addResourceHandler("favicon.ico").addResourceLocations("favicon.ico");
	    registry.addResourceHandler("robots.txt").addResourceLocations("robots.txt");
	}

    /* Map various location URLs to our resource handlers
    <bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="order" value="-10" />
        <property name="mappings">
            <props>
                <prop key="/js/**">blJsResources</prop>
                <prop key="/css/**">blCssResources</prop>
            </props>
        </property>
    </bean> */
	@Bean
	public SimpleUrlHandlerMapping urlHandlerMapping() {
		SimpleUrlHandlerMapping bean = new SimpleUrlHandlerMapping();
		bean.setOrder(-10);
		Properties mappings = new Properties();
		mappings.put("/js/**", ac.blJsResources());
		mappings.put("/css/**", ac.blCssResources());
		bean.setMappings(mappings);
		return bean;
	}

	@Value("${thymeleaf.view.resolver.cache}")
	private boolean cache;

    /* Set up the view resolver to be used by Spring
    <bean class="org.broadleafcommerce.common.web.BroadleafThymeleafViewResolver">
        <property name="templateEngine" ref="blWebTemplateEngine" />
        <property name="order" value="1" />
        <property name="cache" value="${thymeleaf.view.resolver.cache}" />
        <property name="fullPageLayout" value="layout/fullPageLayout" />
        <property name="characterEncoding" value="UTF-8" />
        <property name="layoutMap">
            <map>
                <entry key="account/" value="layout/accountLayout" />
                <entry key="catalog/" value="NONE" />
                <entry key="checkout/" value="layout/checkoutLayout" />
                <entry key="checkout/confirmation" value="layout/fullPageNoNavLayout" />
                <entry key="layout/" value="NONE" />
            </map>
        </property>
    </bean> */
	@Bean
	public BroadleafThymeleafViewResolver broadleafThymeleafViewResolver() {
		BroadleafThymeleafViewResolver bean = new BroadleafThymeleafViewResolver();
		bean.setTemplateEngine(bfwac.blWebTemplateEngine());
		bean.setOrder(1);
		bean.setCache(cache);
		bean.setFullPageLayout("layout/fullPageLayout");
		bean.setCharacterEncoding("UTF-8");

		Map<String, String> layoutMap = new HashMap<String, String>();
		layoutMap.put("account/", "layout/accountLayout");
		layoutMap.put("catalog/", "NONE");
		layoutMap.put("checkout/", "layout/checkoutLayout");
		layoutMap.put("checkout/confirmation", "layout/fullPageNoNavLayout");
		layoutMap.put("layout/", "NONE");
		bean.setLayoutMap(layoutMap);
		return bean;
	}

    /* This validator will additionally require state on shipping addresses
    <bean id="blShippingInfoFormValidator" class="org.broadleafcommerce.core.web.checkout.validator.USShippingInfoFormValidator" /> */
	@Bean
	public USShippingInfoFormValidator blShippingInfoFormValidator() {
		return new USShippingInfoFormValidator();
	}

}
