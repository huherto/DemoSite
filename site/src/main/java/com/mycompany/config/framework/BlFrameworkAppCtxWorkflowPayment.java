package com.mycompany.config.framework;

import java.util.ArrayList;
import java.util.List;

import org.broadleafcommerce.core.payment.service.PaymentService;
import org.broadleafcommerce.core.payment.service.PaymentServiceImpl;
import org.broadleafcommerce.core.payment.service.module.DefaultModule;
import org.broadleafcommerce.core.payment.service.module.NullCreditCardPaymentModule;
import org.broadleafcommerce.core.payment.service.workflow.CompositeActivity;
import org.broadleafcommerce.core.payment.service.workflow.PaymentActionType;
import org.broadleafcommerce.core.payment.service.workflow.PaymentActivity;
import org.broadleafcommerce.core.payment.service.workflow.PaymentProcessContextFactory;
import org.broadleafcommerce.core.payment.service.workflow.SimplePaymentProcessContextFactory;
import org.broadleafcommerce.core.workflow.Activity;
import org.broadleafcommerce.core.workflow.ErrorHandler;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.broadleafcommerce.core.workflow.SequenceProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlFrameworkAppCtxWorkflowPayment {
	
	@SuppressWarnings("unchecked")
	private Activity<ProcessContext> asActivity(Activity<? extends ProcessContext> obj) {
		// TODO: Don'treally know if this will work.
		return (Activity<ProcessContext>)obj;
	}
	
	public @Bean ErrorHandler blDefaultErrorHandler() {
		return null; // TODO: Find appropiate bean.
	}

	/*<bean p:order="1000" id="blPaymentActivity" class="org.broadleafcommerce.core.payment.service.workflow.CompositeActivity">
        <property name="workflow" ref="blAuthorizeAndDebitWorkflow"/>
    </bean>*/
	@Bean
	public Activity<? extends ProcessContext> blPaymentActivity() {
		CompositeActivity bean = new CompositeActivity();
		bean.setWorkflow(blAuthorizeAndDebitWorkflow());
		bean.setOrder(1000);
		return bean;
	}
	
    /*<bean id="blPaymentWorkflow" class="org.broadleafcommerce.core.workflow.SequenceProcessor">
        <property name="processContextFactory">
            <bean class="org.broadleafcommerce.core.payment.service.workflow.SimplePaymentProcessContextFactory"/>
        </property>
        <property name="activities">
            <list>
                <ref bean="blPaymentActivity" />
            </list>
        </property>
        <property name="defaultErrorHandler" ref="blDefaultErrorHandler"/>
    </bean> */
	@Bean
	public SequenceProcessor blPaymentWorkflow() {
		SequenceProcessor bean = new SequenceProcessor();
		bean.setProcessContextFactory(new SimplePaymentProcessContextFactory());
		List<Activity<ProcessContext>> activities = new ArrayList<Activity<ProcessContext> >();
		activities.add(asActivity(blPaymentActivity()));
		bean.setActivities(activities);
		return bean;
	}

    /* <bean id="blGiftCardModule" class="org.broadleafcommerce.core.payment.service.module.DefaultModule"/> */
	@Bean
	public DefaultModule  blGiftCardModule() {
		return new DefaultModule();
	}
	
    /* <bean id="blCreditCardModule" class="org.broadleafcommerce.core.payment.service.module.NullCreditCardPaymentModule"/> */
	@Bean
	public NullCreditCardPaymentModule blCreditCardModule() {
		return new NullCreditCardPaymentModule();
	}
	
    /* <bean id="blBankAccountModule" class="org.broadleafcommerce.core.payment.service.module.DefaultModule"/> */
	@Bean
	public DefaultModule blBankAccountModule() {
		return new DefaultModule();
	}
	
    /* <bean id="blCheckPaymentModule" class="org.broadleafcommerce.core.payment.service.module.DefaultModule"/> */
	@Bean
	public DefaultModule blCheckPaymentModule() {
		return new DefaultModule();
	}
	
    /* <bean id="blElectronicCheckPaymentModule" class="org.broadleafcommerce.core.payment.service.module.DefaultModule"/> */
	@Bean
	public DefaultModule blElectronicCheckPaymentModule() {
		return  new DefaultModule();
	}
	
    /* <bean id="blWirePaymentModule" class="org.broadleafcommerce.core.payment.service.module.DefaultModule"/> */
	@Bean
	public DefaultModule blWirePaymentModule() {
		return  new DefaultModule();
	}
	
    /* <bean id="blMoneyOrderPaymentModule" class="org.broadleafcommerce.core.payment.service.module.DefaultModule"/> */
	@Bean
	public DefaultModule blMoneyOrderPaymentModule() {
		return  new DefaultModule();
	}
	
    /* <bean id="blAccountCreditPaymentModule" class="org.broadleafcommerce.core.payment.service.module.DefaultModule"/> */
	@Bean
	public DefaultModule blAccountCreditPaymentModule() {
		return  new DefaultModule();
	}
	
    /* <bean id="blCustomerCreditPaymentModule" class="org.broadleafcommerce.core.payment.service.module.DefaultModule"/> */
	@Bean
	public DefaultModule blCustomerCreditPaymentModule() {
		return  new DefaultModule();
	}
	
    /* <bean id="blAccountPaymentModule" class="org.broadleafcommerce.core.payment.service.module.DefaultModule"/> */
	@Bean
	public DefaultModule blAccountPaymentModule() {
		return  new DefaultModule();
	}
	
    /*<bean id="blGiftCardService" class="org.broadleafcommerce.core.payment.service.PaymentServiceImpl">
        <property name="paymentModule" ref="blGiftCardModule"/>
    </bean> */
	@Bean
	public PaymentService blGiftCardService() {
		PaymentServiceImpl bean =  new PaymentServiceImpl();
		bean.setPaymentModule(blGiftCardModule());
		return bean;
	}
	
    /* <bean id="blCreditCardService" class="org.broadleafcommerce.core.payment.service.PaymentServiceImpl">
        <property name="paymentModule" ref="blCreditCardModule"/>
    </bean> */
	@Bean
	public PaymentService blCreditCardService() {
		PaymentServiceImpl bean =  new PaymentServiceImpl();
		bean.setPaymentModule(blCreditCardModule());
		return bean;
	}
	
    /* <bean id="blBankAccountService" class="org.broadleafcommerce.core.payment.service.PaymentServiceImpl">
        <property name="paymentModule" ref="blBankAccountModule"/>
    </bean> */
	@Bean
	public PaymentService blBankAccountService() {
		PaymentServiceImpl bean =  new PaymentServiceImpl();
		bean.setPaymentModule(blBankAccountModule());
		return bean;
	}
	
    /* <bean id="blCheckPaymentService" class="org.broadleafcommerce.core.payment.service.PaymentServiceImpl">
        <property name="paymentModule" ref="blCheckPaymentModule"/>
    </bean> */ 
	@Bean
	public PaymentService blCheckPaymentService() {
		PaymentServiceImpl bean =  new PaymentServiceImpl();
		bean.setPaymentModule(blCheckPaymentModule());
		return bean;
	}
	
    /* <bean id="blElectronicCheckPaymentService" class="org.broadleafcommerce.core.payment.service.PaymentServiceImpl">
        <property name="paymentModule" ref="blElectronicCheckPaymentModule"/>
    </bean> */
	@Bean
	public PaymentService blElectronicCheckPaymentService() {
		PaymentServiceImpl bean =  new PaymentServiceImpl();
		bean.setPaymentModule(blElectronicCheckPaymentModule());
		return bean;
	}
	
    /* <bean id="blWirePaymentService" class="org.broadleafcommerce.core.payment.service.PaymentServiceImpl">
        <property name="paymentModule" ref="blWirePaymentModule"/>
    </bean> */
	@Bean
	public PaymentService blWirePaymentService() {
		PaymentServiceImpl bean =  new PaymentServiceImpl();
		bean.setPaymentModule(blWirePaymentModule());
		return bean;
	}
	
    /* <bean id="blMoneyOrderPaymentService" class="org.broadleafcommerce.core.payment.service.PaymentServiceImpl">
        <property name="paymentModule" ref="blMoneyOrderPaymentModule"/>
    </bean> */
	@Bean
	public PaymentService blMoneyOrderPaymentService() {
		PaymentServiceImpl bean =  new PaymentServiceImpl();
		bean.setPaymentModule(blMoneyOrderPaymentModule());
		return bean;
	}
	
    /* <bean id="blAccountCreditPaymentService" class="org.broadleafcommerce.core.payment.service.PaymentServiceImpl">
        <property name="paymentModule" ref="blAccountCreditPaymentModule"/>
    </bean> */
	@Bean
	public PaymentService blAccountCreditPaymentService() {
		PaymentServiceImpl bean =  new PaymentServiceImpl();
		bean.setPaymentModule(blAccountCreditPaymentModule());
		return bean;
	}
	
    /* <bean id="blCustomerCreditPaymentService" class="org.broadleafcommerce.core.payment.service.PaymentServiceImpl">
        <property name="paymentModule" ref="blCustomerCreditPaymentModule"/>
    </bean> */
	@Bean
	public PaymentService blCustomerCreditPaymentService() {
		PaymentServiceImpl bean =  new PaymentServiceImpl();
		bean.setPaymentModule(blCustomerCreditPaymentModule());
		return bean;
	}
	
    /* <bean id="blAccountPaymentService" class="org.broadleafcommerce.core.payment.service.PaymentServiceImpl">
        <property name="paymentModule" ref="blAccountPaymentModule"/>
    </bean> */
	@Bean
	public PaymentService blAccountPaymentService() {
		PaymentServiceImpl bean =  new PaymentServiceImpl();
		bean.setPaymentModule(blAccountPaymentModule());
		return bean;
	}
	
    /* <bean p:order="1000" id="blAuthorizeGiftCardActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blGiftCardService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blAuthorizeGiftCardActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blGiftCardService());
		bean.setUserName("web");
		bean.setOrder(1000);
		return bean;
	}
		
    /* <bean p:order="2000" id="blAuthorizeAccountPaymentActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blAccountPaymentService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blAuthorizeAccountPaymentActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blAccountPaymentService());
		bean.setUserName("web");
		bean.setOrder(2000);
		return bean;
	}

    /* <bean p:order="3000" id="blAuthorizeCreditCardActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blCreditCardService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blAuthorizeCreditCardActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blCreditCardService());
		bean.setUserName("web");
		bean.setOrder(3000);
		return bean;
	}
	
    /* <bean id="blAuthorizeWorkflow" class="org.broadleafcommerce.core.workflow.SequenceProcessor">
        <property name="processContextFactory">
            <bean class="org.broadleafcommerce.core.payment.service.workflow.PaymentProcessContextFactory">
                <property name="paymentActionType" value="AUTHORIZE"/>
            </bean>
        </property>
        <property name="activities">
            <list>
                <ref bean="blAuthorizeGiftCardActivity" />
                <ref bean="blAuthorizeAccountPaymentActivity" />
                <ref bean="blAuthorizeCreditCardActivity" />
            </list>
        </property>
        <property name="defaultErrorHandler" ref="blDefaultErrorHandler"/>
    </bean> */
	@Bean
	public SequenceProcessor blAuthorizeWorkflow() {
		SequenceProcessor bean =  new SequenceProcessor();
		
		PaymentProcessContextFactory ppcf = new PaymentProcessContextFactory();
		ppcf.setPaymentActionType(PaymentActionType.AUTHORIZE);
		bean.setProcessContextFactory(ppcf);

		List<Activity<ProcessContext>> activities = new ArrayList<Activity<ProcessContext> >();
		activities.add(asActivity(blAuthorizeGiftCardActivity()));
		activities.add(asActivity(blAuthorizeAccountPaymentActivity()));
		activities.add(asActivity(blAuthorizeCreditCardActivity()));
		bean.setActivities(activities);
		bean.setDefaultErrorHandler(blDefaultErrorHandler());
		return bean;
	}

    /* <bean p:order="1000" id="blDebitGiftCardActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blGiftCardService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blDebitGiftCardActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blGiftCardService());
		bean.setUserName("web");
		bean.setOrder(1000);
		return bean;
	}

	/* <bean p:order="2000" id="blDebitAccountPaymentActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blAccountPaymentService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blDebitAccountPaymentActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blAccountPaymentService());
		bean.setUserName("web");
		bean.setOrder(2000);
		return bean;
	}
	
    /* <bean p:order="3000" id="blDebitCreditCardActivity"  class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blCreditCardService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blDebitCreditCardActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blCreditCardService());
		bean.setUserName("web");
		bean.setOrder(1000);
		return bean;
	}
	
    /* <bean id="blDebitWorkflow" class="org.broadleafcommerce.core.workflow.SequenceProcessor">
        <property name="processContextFactory">
            <bean class="org.broadleafcommerce.core.payment.service.workflow.PaymentProcessContextFactory">
                <property name="paymentActionType" value="DEBIT"/>
            </bean>
        </property>
        <property name="activities">
            <list>
                <ref bean="blDebitGiftCardActivity" />
                <ref bean="blDebitAccountPaymentActivity" />
                <ref bean="blDebitCreditCardActivity" />
            </list>
        </property>
        <property name="defaultErrorHandler" ref="blDefaultErrorHandler"/>
    </bean> */
	@Bean
	public SequenceProcessor blDebitWorkflow() {
		SequenceProcessor bean =  new SequenceProcessor();
		
		PaymentProcessContextFactory ppcf = new PaymentProcessContextFactory();
		ppcf.setPaymentActionType(PaymentActionType.DEBIT);
		bean.setProcessContextFactory(ppcf);

		List<Activity<ProcessContext>> activities = new ArrayList<Activity<ProcessContext> >();
		activities.add(asActivity(blDebitGiftCardActivity()));
		activities.add(asActivity(blDebitAccountPaymentActivity()));
		activities.add(asActivity(blDebitCreditCardActivity()));
		bean.setActivities(activities);
		bean.setDefaultErrorHandler(blDefaultErrorHandler());
		return bean;
	}

    /* <bean p:order="1000" id="blAuthorizeAndDebitAccountCreditActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blAccountCreditPaymentService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blAuthorizeAndDebitAccountCreditActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blAccountCreditPaymentService());
		bean.setUserName("web");
		bean.setOrder(1000);
		return bean;
	}
		
    /* <bean p:order="2000" id="blAuthorizeAndDebitCustomerCreditActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blCustomerCreditPaymentService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blAuthorizeAndDebitCustomerCreditActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blCustomerCreditPaymentService());
		bean.setUserName("web");
		bean.setOrder(2000);
		return bean;
	}
	
    /* <bean p:order="3000" id="blAuthorizeAndDebitAccountPaymentActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blAccountPaymentService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blAuthorizeAndDebitAccountPaymentActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blAccountPaymentService());
		bean.setUserName("web");
		bean.setOrder(3000);
		return bean;
	}
	
    /* <bean p:order="4000" id="blAuthorizeAndDebitGiftCardActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blGiftCardService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blAuthorizeAndDebitGiftCardActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blGiftCardService());
		bean.setUserName("web");
		bean.setOrder(4000);
		return bean;
	}
	
    /* <bean p:order="5000" id="blAuthorizeAndDebitCreditCardActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blCreditCardService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blAuthorizeAndDebitCreditCardActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blCreditCardService());
		bean.setUserName("web");
		bean.setOrder(5000);
		return bean;
	}
	
    /* <bean p:order="6000" id="blAuthorizeAndDebitBankAccountActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blBankAccountService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blAuthorizeAndDebitBankAccountActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blBankAccountService());
		bean.setUserName("web");
		bean.setOrder(6000);
		return bean;
	}
	
    /* <bean p:order="7000" id="blAuthorizeAndDebitCheckActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blCheckPaymentService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blAuthorizeAndDebitCheckActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blCheckPaymentService());
		bean.setUserName("web");
		bean.setOrder(7000);
		return bean;
	}
	
    /* <bean p:order="8000" id="blAuthorizeAndDebitElectronicCheckActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blElectronicCheckPaymentService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blAuthorizeAndDebitElectronicCheckActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blElectronicCheckPaymentService());
		bean.setUserName("web");
		bean.setOrder(8000);
		return bean;
	}
	
    /* <bean p:order="9000" id="blAuthorizeAndDebitWireActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blWirePaymentService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blAuthorizeAndDebitWireActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blWirePaymentService());
		bean.setUserName("web");
		bean.setOrder(9000);
		return bean;
	}
	
    /* <bean p:order="10000" id="blAuthorizeAndDebitMoneyOrderActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blMoneyOrderPaymentService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blAuthorizeAndDebitMoneyOrderActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blMoneyOrderPaymentService());
		bean.setUserName("web");
		bean.setOrder(10000);
		return bean;
	}
	
    /* <bean id="blAuthorizeAndDebitWorkflow" class="org.broadleafcommerce.core.workflow.SequenceProcessor">
        <property name="processContextFactory">
            <bean class="org.broadleafcommerce.core.payment.service.workflow.PaymentProcessContextFactory">
                <property name="paymentActionType" value="AUTHORIZEANDDEBIT"/>
            </bean>
        </property>
        <property name="activities">
            <list>
                <ref bean="blAuthorizeAndDebitAccountCreditActivity" />
                <ref bean="blAuthorizeAndDebitCustomerCreditActivity" />
                <ref bean="blAuthorizeAndDebitAccountPaymentActivity" />
                <ref bean="blAuthorizeAndDebitGiftCardActivity" />
                <ref bean="blAuthorizeAndDebitCreditCardActivity" />
                <ref bean="blAuthorizeAndDebitBankAccountActivity" />
                <ref bean="blAuthorizeAndDebitCheckActivity" />
                <ref bean="blAuthorizeAndDebitElectronicCheckActivity" />
                <ref bean="blAuthorizeAndDebitWireActivity" />
                <ref bean="blAuthorizeAndDebitMoneyOrderActivity" />
            </list>
        </property>
        <property name="defaultErrorHandler" ref="blDefaultErrorHandler"/>
    </bean> */
	@Bean
	public SequenceProcessor blAuthorizeAndDebitWorkflow() {
		SequenceProcessor bean =  new SequenceProcessor();
		
		PaymentProcessContextFactory ppcf = new PaymentProcessContextFactory();
		ppcf.setPaymentActionType(PaymentActionType.AUTHORIZEANDDEBIT);
		bean.setProcessContextFactory(ppcf);

		List<Activity<ProcessContext>> activities = new ArrayList<Activity<ProcessContext> >();
		activities.add(asActivity(blAuthorizeAndDebitAccountCreditActivity()));
		activities.add(asActivity(blAuthorizeAndDebitCustomerCreditActivity()));
		activities.add(asActivity(blAuthorizeAndDebitAccountPaymentActivity()));
		activities.add(asActivity(blAuthorizeAndDebitGiftCardActivity()));
		activities.add(asActivity(blAuthorizeAndDebitCreditCardActivity()));
		activities.add(asActivity(blAuthorizeAndDebitBankAccountActivity()));
		activities.add(asActivity(blAuthorizeAndDebitCheckActivity()));
		activities.add(asActivity(blAuthorizeAndDebitElectronicCheckActivity()));
		activities.add(asActivity(blAuthorizeAndDebitWireActivity()));
		activities.add(asActivity(blAuthorizeAndDebitMoneyOrderActivity()));
		bean.setActivities(activities);
		bean.setDefaultErrorHandler(blDefaultErrorHandler());
		return bean;
	}
	
    /* <bean p:order="1000" id="blCreditCreditCardActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blCreditCardService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blCreditCreditCardActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blCreditCardService());
		bean.setUserName("web");
		bean.setOrder(1000);
		return bean;
	}
	
    /* <bean p:order="2000" id="blCreditGiftCardActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blGiftCardService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blCreditGiftCardActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blGiftCardService());
		bean.setUserName("web");
		bean.setOrder(2000);
		return bean;
	}
	
    /* <bean p:order="3000" id="blCreditAccountPaymentActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blAccountPaymentService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blCreditAccountPaymentActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blAccountPaymentService());
		bean.setUserName("web");
		bean.setOrder(3000);
		return bean;
	}
	
    /* <bean id="blCreditWorkflow" class="org.broadleafcommerce.core.workflow.SequenceProcessor">
        <property name="processContextFactory">
            <bean class="org.broadleafcommerce.core.payment.service.workflow.PaymentProcessContextFactory">
                <property name="paymentActionType" value="CREDIT"/>
            </bean>
        </property>
        <property name="activities">
            <list>
                <ref bean="blCreditCreditCardActivity" />
                <ref bean="blCreditGiftCardActivity" />
                <ref bean="blCreditAccountPaymentActivity" />
            </list>
        </property>
        <property name="defaultErrorHandler" ref="blDefaultErrorHandler"/>
    </bean> */
	@Bean
	public SequenceProcessor blCreditWorkflow() {
		SequenceProcessor bean =  new SequenceProcessor();
		
		PaymentProcessContextFactory ppcf = new PaymentProcessContextFactory();
		ppcf.setPaymentActionType(PaymentActionType.CREDIT);
		bean.setProcessContextFactory(ppcf);

		List< Activity<ProcessContext> > activities = new ArrayList< Activity<ProcessContext> >();
		activities.add(asActivity(blCreditCreditCardActivity()));
		activities.add(asActivity(blCreditGiftCardActivity()));
		activities.add(asActivity(blCreditAccountPaymentActivity()));
		bean.setActivities(activities);
		bean.setDefaultErrorHandler(blDefaultErrorHandler());
		return bean;
	}

    /* <bean p:order="1000" id="blVoidCreditCardActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blCreditCardService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blVoidCreditCardActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blCreditCardService());
		bean.setUserName("web");
		bean.setOrder(1000);
		return bean;
	}
	
    /* <bean p:order="2000" id="blVoidGiftCardActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blGiftCardService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blVoidGiftCardActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blGiftCardService());
		bean.setUserName("web");
		bean.setOrder(2000);
		return bean;
	}
	
    /* <bean p:order="3000" id="blVoidAccountPaymentActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blAccountPaymentService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blVoidAccountPaymentActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blAccountPaymentService());
		bean.setUserName("web");
		bean.setOrder(3000);
		return bean;
	}
	
    /* <bean id="blVoidWorkflow" class="org.broadleafcommerce.core.workflow.SequenceProcessor">
        <property name="processContextFactory">
            <bean class="org.broadleafcommerce.core.payment.service.workflow.PaymentProcessContextFactory">
                <property name="paymentActionType" value="VOID"/>
            </bean>
        </property>
        <property name="activities">
            <list>
                <ref bean="blVoidCreditCardActivity" />
                <ref bean="blVoidGiftCardActivity" />
                <ref bean="blVoidAccountPaymentActivity" />
            </list>
        </property>
        <property name="defaultErrorHandler" ref="blDefaultErrorHandler"/>
    </bean> */
	@Bean
	public SequenceProcessor blVoidWorkflow() {
		SequenceProcessor bean =  new SequenceProcessor();
		
		PaymentProcessContextFactory ppcf = new PaymentProcessContextFactory();
		ppcf.setPaymentActionType(PaymentActionType.VOID);
		bean.setProcessContextFactory(ppcf);

		List< Activity<ProcessContext> > activities = new ArrayList< Activity<ProcessContext> >();
		activities.add(asActivity(blVoidCreditCardActivity()));
		activities.add(asActivity(blVoidGiftCardActivity()));
		activities.add(asActivity(blVoidAccountPaymentActivity()));
		bean.setActivities(activities);
		bean.setDefaultErrorHandler(blDefaultErrorHandler());
		return bean;
	}
	
    /* <bean p:order="1000" id="blReverseAuthCreditCardActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blCreditCardService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blReverseAuthCreditCardActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blCreditCardService());
		bean.setUserName("web");
		bean.setOrder(1000);
		return bean;
	}
	
    /* <bean p:order="2000" id="blReverseAuthGiftCardActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blGiftCardService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blReverseAuthGiftCardActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blGiftCardService());
		bean.setUserName("web");
		bean.setOrder(2000);
		return bean;
	}
	
    /* <bean p:order="3000" id="blReverseAuthAccountPaymentActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blAccountPaymentService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blReverseAuthAccountPaymentActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blAccountPaymentService());
		bean.setUserName("web");
		bean.setOrder(3000);
		return bean;
	}	
	
    /* <bean id="blReverseAuthWorkflow" class="org.broadleafcommerce.core.workflow.SequenceProcessor">
        <property name="processContextFactory">
            <bean class="org.broadleafcommerce.core.payment.service.workflow.PaymentProcessContextFactory">
                <property name="paymentActionType" value="REVERSEAUTHORIZE"/>
            </bean>
        </property>
        <property name="activities">
            <list>
                <ref bean="blReverseAuthCreditCardActivity" />
                <ref bean="blReverseAuthGiftCardActivity" />
                <ref bean="blReverseAuthAccountPaymentActivity" />
            </list>
        </property>
        <property name="defaultErrorHandler" ref="blDefaultErrorHandler"/>
    </bean> */
	@Bean
	public SequenceProcessor blReverseAuthWorkflow() {
		SequenceProcessor bean =  new SequenceProcessor();
		
		PaymentProcessContextFactory ppcf = new PaymentProcessContextFactory();
		ppcf.setPaymentActionType(PaymentActionType.REVERSEAUTHORIZE);
		bean.setProcessContextFactory(ppcf);

		List< Activity<ProcessContext> > activities = new ArrayList< Activity<ProcessContext> >();
		activities.add(asActivity(blReverseAuthCreditCardActivity()));
		activities.add(asActivity(blReverseAuthGiftCardActivity()));
		activities.add(asActivity(blReverseAuthAccountPaymentActivity()));
		bean.setActivities(activities);
		bean.setDefaultErrorHandler(blDefaultErrorHandler());
		return bean;
	}

    /* <bean p:order="1000" id="blPartialPaymentCreditCardActivity" class="org.broadleafcommerce.core.payment.service.workflow.PaymentActivity">
        <property name="paymentService" ref="blCreditCardService"/>
        <property name="userName" value="web"/>
    </bean> */
	@Bean
	public PaymentActivity blPartialPaymentCreditCardActivity() {
		PaymentActivity bean =  new PaymentActivity();
		bean.setPaymentService(blCreditCardService());
		bean.setUserName("web");
		bean.setOrder(1000);
		return bean;
	}
	
    /* <bean id="blPartialPaymentWorkflow" class="org.broadleafcommerce.core.workflow.SequenceProcessor">
        <property name="processContextFactory">
            <bean class="org.broadleafcommerce.core.payment.service.workflow.PaymentProcessContextFactory">
                <property name="paymentActionType" value="PARTIALPAYMENT"/>
            </bean>
        </property>
        <property name="activities">
            <list>
                <ref bean="blPartialPaymentCreditCardActivity" />
            </list>
        </property>
        <property name="defaultErrorHandler" ref="blDefaultErrorHandler"/>
    </bean> */
	@Bean
	public SequenceProcessor blPartialPaymentWorkflow() {
		SequenceProcessor bean =  new SequenceProcessor();
		
		PaymentProcessContextFactory ppcf = new PaymentProcessContextFactory();
		ppcf.setPaymentActionType(PaymentActionType.PARTIALPAYMENT);
		bean.setProcessContextFactory(ppcf);

		List< Activity<ProcessContext> > activities = new ArrayList< Activity<ProcessContext> >();
		activities.add(asActivity(blPartialPaymentCreditCardActivity()));
		bean.setActivities(activities);
		bean.setDefaultErrorHandler(blDefaultErrorHandler());
		return bean;
	}
}
