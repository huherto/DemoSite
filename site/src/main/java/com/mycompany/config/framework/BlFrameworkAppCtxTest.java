package com.mycompany.config.framework;

import java.util.HashMap;
import java.util.Map;

import org.broadleafcommerce.common.jmx.MetadataMBeanInfoAssembler;
import org.hibernate.jmx.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;

import com.mycompany.config.common.BlCommonAppCtxPersistence;

@Configuration
public class BlFrameworkAppCtxTest {
	
	@Autowired
	private BlCommonAppCtxPersistence blcacp;

    /* <bean id="attributeSource" class="org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource"/> */
	@Bean
	public AnnotationJmxAttributeSource attributeSource() {
		return new AnnotationJmxAttributeSource();
	}
    
    /* <bean id="hibernateExporter" class="org.springframework.jmx.export.MBeanExporter">
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
                <entry key="org.broadleafcommerce:name=hibernate.statistics">
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
	@Bean 
	public MBeanExporter hibernateExporter() {
		MBeanExporter bean = new MBeanExporter();
		bean.setAutodetect(false);
		bean.setAssembler(jmxAssembler());
		
		StatisticsService statisticsService = new StatisticsService();
		statisticsService.setStatisticsEnabled(false);
		// TODO: fix this.
		// statisticsService.setSessionFactory(blcacp.entityManagerFactory().getObject().sessionFactory);
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("org.broadleafcommerce:name=hibernate.statistics", statisticsService);
		bean.setBeans(beans);
		
		return bean;
	}
	
	@Bean
	public MetadataMBeanInfoAssembler jmxAssembler() {
		MetadataMBeanInfoAssembler bean = new MetadataMBeanInfoAssembler();
		bean.setAttributeSource(new AnnotationJmxAttributeSource());
		return bean;
	}

}
