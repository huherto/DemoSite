package com.mycompany.config.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.broadleafcommerce.core.checkout.service.workflow.CheckoutProcessContextFactory;
import org.broadleafcommerce.core.checkout.service.workflow.CommitTaxActivity;
import org.broadleafcommerce.core.checkout.service.workflow.CommitTaxRollbackHandler;
import org.broadleafcommerce.core.checkout.service.workflow.CompleteOrderActivity;
import org.broadleafcommerce.core.checkout.service.workflow.PaymentServiceActivity;
import org.broadleafcommerce.core.offer.service.workflow.RecordOfferUsageActivity;
import org.broadleafcommerce.core.offer.service.workflow.VerifyCustomerMaxOfferUsesActivity;
import org.broadleafcommerce.core.order.service.workflow.CartOperationProcessContextFactory;
import org.broadleafcommerce.core.order.service.workflow.PriceOrderIfNecessaryActivity;
import org.broadleafcommerce.core.order.service.workflow.VerifyFulfillmentGroupItemsActivity;
import org.broadleafcommerce.core.order.service.workflow.add.AddFulfillmentGroupItemActivity;
import org.broadleafcommerce.core.order.service.workflow.add.AddOrderItemActivity;
import org.broadleafcommerce.core.order.service.workflow.add.ValidateAddRequestActivity;
import org.broadleafcommerce.core.order.service.workflow.remove.RemoveFulfillmentGroupItemActivity;
import org.broadleafcommerce.core.order.service.workflow.remove.RemoveOrderItemActivity;
import org.broadleafcommerce.core.order.service.workflow.remove.RemoveOrderMultishipOptionActivity;
import org.broadleafcommerce.core.order.service.workflow.remove.ValidateRemoveRequestActivity;
import org.broadleafcommerce.core.order.service.workflow.update.UpdateFulfillmentGroupItemActivity;
import org.broadleafcommerce.core.order.service.workflow.update.UpdateOrderItemActivity;
import org.broadleafcommerce.core.order.service.workflow.update.UpdateOrderMultishipOptionActivity;
import org.broadleafcommerce.core.order.service.workflow.update.ValidateUpdateRequestActivity;
import org.broadleafcommerce.core.pricing.service.TaxService;
import org.broadleafcommerce.core.pricing.service.TaxServiceImpl;
import org.broadleafcommerce.core.pricing.service.fulfillment.SimpleFulfillmentLocationResolver;
import org.broadleafcommerce.core.pricing.service.fulfillment.provider.BandedFulfillmentPricingProvider;
import org.broadleafcommerce.core.pricing.service.fulfillment.provider.FixedPriceFulfillmentPricingProvider;
import org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider;
import org.broadleafcommerce.core.pricing.service.module.SimpleTaxModule;
import org.broadleafcommerce.core.pricing.service.workflow.ConsolidateFulfillmentFeesActivity;
import org.broadleafcommerce.core.pricing.service.workflow.FulfillmentGroupMerchandiseTotalActivity;
import org.broadleafcommerce.core.pricing.service.workflow.FulfillmentGroupPricingActivity;
import org.broadleafcommerce.core.pricing.service.workflow.FulfillmentItemPricingActivity;
import org.broadleafcommerce.core.pricing.service.workflow.OfferActivity;
import org.broadleafcommerce.core.pricing.service.workflow.PricingProcessContextFactory;
import org.broadleafcommerce.core.pricing.service.workflow.ShippingOfferActivity;
import org.broadleafcommerce.core.pricing.service.workflow.TaxActivity;
import org.broadleafcommerce.core.pricing.service.workflow.TotalActivity;
import org.broadleafcommerce.core.workflow.Activity;
import org.broadleafcommerce.core.workflow.DefaultErrorHandler;
import org.broadleafcommerce.core.workflow.ErrorHandler;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.broadleafcommerce.core.workflow.SequenceProcessor;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlFrameworkAppCtxWorkflow {

	@SuppressWarnings("unchecked")
	private Activity<ProcessContext> asActivity(Activity<? extends ProcessContext> obj) {
		// TODO: Don'treally know if this will work.
		return (Activity<ProcessContext>)obj;
	}

	public @Bean ErrorHandler blDefaultErrorHandler() {
		return null; // TODO: Find appropiate bean.
	}

/*
    <bean id="blTaxProviders" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list/>
        </property>
    </bean> */
	@Bean
	public List<String> blTaxProviders() {
		return Arrays.asList();
	}

    /* <bean id="blTaxModule" class="org.broadleafcommerce.core.pricing.service.module.SimpleTaxModule">
        <property name="factor" value="0.00"/>
    </bean> */
	@Bean
	public SimpleTaxModule blTaxModule() {
		SimpleTaxModule bean = new SimpleTaxModule();
		bean.setFactor(0.00);
		return bean;
	}

    /* <bean id="blTaxService" class="org.broadleafcommerce.core.pricing.service.TaxServiceImpl"/> */
	@Bean
	public TaxService blTaxService() {
		return new TaxServiceImpl();
	}

    /* <bean id="blFulfillmentLocationResolver" class="org.broadleafcommerce.core.pricing.service.fulfillment.SimpleFulfillmentLocationResolver" /> */
	@Bean
	public SimpleFulfillmentLocationResolver blFulfillmentLocationResolver() {
		return new SimpleFulfillmentLocationResolver();
	}

    /* <bean id="blFixedPriceFulfillmentPricingProvider"
            class="org.broadleafcommerce.core.pricing.service.fulfillment.provider.FixedPriceFulfillmentPricingProvider" /> */
	@Bean
	public FixedPriceFulfillmentPricingProvider blFixedPriceFulfillmentPricingProvider() {
		return new FixedPriceFulfillmentPricingProvider();
	}

    /* <bean id="blBandedFulfillmentPricingProvider"
            class="org.broadleafcommerce.core.pricing.service.fulfillment.provider.BandedFulfillmentPricingProvider" /> */
	@Bean
	public BandedFulfillmentPricingProvider blBandedFulfillmentPricingProvider() {
		return new BandedFulfillmentPricingProvider();
	}

    /* <bean id="blFulfillmentPricingProviders" class="org.springframework.beans.factory.config.ListFactoryBean">
        <property name="sourceList">
            <list>
               <ref bean="blFixedPriceFulfillmentPricingProvider" />
               <ref bean="blBandedFulfillmentPricingProvider" />
            </list>
        </property>
    </bean> */
	@Bean
	public List<FulfillmentPricingProvider> blFulfillmentPricingProviders() {
		return Arrays.asList(blFixedPriceFulfillmentPricingProvider(), blBandedFulfillmentPricingProvider());
	}

    /**** Pricing Workflow configuration ****/
    /* <bean p:order="1000" id="blOfferActivity" class="org.broadleafcommerce.core.pricing.service.workflow.OfferActivity" /> */
	@Bean
	public OfferActivity blOfferActivity() {
		OfferActivity bean = new OfferActivity();
		bean.setOrder(1000);
		return bean;
	}

    /* <bean p:order="2000" id="blConsolidateFulfillmentFeesActivity" class="org.broadleafcommerce.core.pricing.service.workflow.ConsolidateFulfillmentFeesActivity" /> */
	@Bean
	public ConsolidateFulfillmentFeesActivity blConsolidateFulfillmentFeesActivity() {
		ConsolidateFulfillmentFeesActivity bean = new ConsolidateFulfillmentFeesActivity();
		bean.setOrder(2000);
		return bean;
	}

    /* <bean p:order="3000" id="blFulfillmentItemPricingActivity" class="org.broadleafcommerce.core.pricing.service.workflow.FulfillmentItemPricingActivity" /> */
	@Bean
	public FulfillmentItemPricingActivity blFulfillmentItemPricingActivity() {
		FulfillmentItemPricingActivity bean = new FulfillmentItemPricingActivity();
		bean.setOrder(3000);
		return bean;
	}

    /* <bean p:order="4000" id="blFulfillmentGroupMerchandiseTotalActivity" class="org.broadleafcommerce.core.pricing.service.workflow.FulfillmentGroupMerchandiseTotalActivity" /> */
	@Bean
	public FulfillmentGroupMerchandiseTotalActivity blFulfillmentGroupMerchandiseTotalActivity() {
		FulfillmentGroupMerchandiseTotalActivity bean = new FulfillmentGroupMerchandiseTotalActivity();
		bean.setOrder(4000);
		return bean;
	}

    /* <bean p:order="5000" id="blFulfillmentGroupPricingActivity" class="org.broadleafcommerce.core.pricing.service.workflow.FulfillmentGroupPricingActivity" />*/
	@Bean
	public FulfillmentGroupPricingActivity blFulfillmentGroupPricingActivity() {
		FulfillmentGroupPricingActivity bean = new FulfillmentGroupPricingActivity();
		bean.setOrder(5000);
		return bean;
	}

    /* <bean p:order="6000" id="blShippingOfferActivity" class="org.broadleafcommerce.core.pricing.service.workflow.ShippingOfferActivity" /> */
	@Bean
	public ShippingOfferActivity blShippingOfferActivity() {
		ShippingOfferActivity bean = new ShippingOfferActivity();
		bean.setOrder(6000);
		return bean;
	}

    /* <bean p:order="7000" id="blTaxActivity" class="org.broadleafcommerce.core.pricing.service.workflow.TaxActivity">
        <property name="taxService" ref="blTaxService"/>
    </bean> */
	@Bean
	public TaxActivity blTaxActivity() {
		TaxActivity bean = new TaxActivity();
		bean.setOrder(7000);
		bean.setTaxService(blTaxService());
		return bean;
	}

    /* <bean p:order="8000" id="blTotalActivity" class="org.broadleafcommerce.core.pricing.service.workflow.TotalActivity" /> */
	@Bean
	public TotalActivity blTotalActivity() {
		TotalActivity bean = new TotalActivity();
		bean.setOrder(8000);
		return bean;
	}

    /* <bean id="blPricingWorkflow" class="org.broadleafcommerce.core.workflow.SequenceProcessor">
        <property name="processContextFactory">
            <bean class="org.broadleafcommerce.core.pricing.service.workflow.PricingProcessContextFactory"/>
        </property>
        <property name="activities">
            <list>
                <ref bean="blOfferActivity" />
                <ref bean="blConsolidateFulfillmentFeesActivity" />
                <ref bean="blFulfillmentItemPricingActivity" />
                <ref bean="blFulfillmentGroupMerchandiseTotalActivity" />
                <ref bean="blFulfillmentGroupPricingActivity" />
                <ref bean="blShippingOfferActivity" />
                <ref bean="blTaxActivity" />
                <ref bean="blTotalActivity"/>
            </list>
        </property>
        <property name="defaultErrorHandler">
            <bean class="org.broadleafcommerce.core.workflow.DefaultErrorHandler">
                <property name="unloggedExceptionClasses">
                    <list>
                        <value>org.hibernate.exception.LockAcquisitionException</value>
                    </list>
                </property>
            </bean>
        </property>
    </bean> */
	@Bean
	public SequenceProcessor blPricingWorkflow() {
		SequenceProcessor bean =  new SequenceProcessor();
		bean.setProcessContextFactory(new PricingProcessContextFactory());
		List<Activity<ProcessContext>> activities = new ArrayList<Activity<ProcessContext> >();
		activities.add(asActivity(blOfferActivity()));
		activities.add(asActivity(blConsolidateFulfillmentFeesActivity()));
		activities.add(asActivity(blFulfillmentItemPricingActivity()));
		activities.add(asActivity(blFulfillmentGroupMerchandiseTotalActivity()));
		activities.add(asActivity(blFulfillmentGroupPricingActivity()));
		activities.add(asActivity(blShippingOfferActivity()));
		activities.add(asActivity(blTaxActivity()));
		activities.add(asActivity(blTotalActivity()));
		bean.setActivities(activities);

		DefaultErrorHandler deh = new DefaultErrorHandler();
		deh.setUnloggedExceptionClasses(Arrays.asList(LockAcquisitionException.class.getName()));
		bean.setDefaultErrorHandler(deh);
		return bean;
	}

    /* <!-- Add Item Workflow Configuration --> */
    /* <bean p:order="1000" id="blValidateAddRequestActivity" class="org.broadleafcommerce.core.order.service.workflow.add.ValidateAddRequestActivity"/> */
	@Bean
	public ValidateAddRequestActivity blValidateAddRequestActivity() {
		ValidateAddRequestActivity bean = new ValidateAddRequestActivity();
		bean.setOrder(1000);
		return bean;
	}

    /* <bean p:order="2000" id="blAddOrderItemActivity" class="org.broadleafcommerce.core.order.service.workflow.add.AddOrderItemActivity"/> */
	@Bean
	public AddOrderItemActivity blAddOrderItemActivity() {
		AddOrderItemActivity bean = new AddOrderItemActivity();
		bean.setOrder(2000);
		return bean;
	}

    /* <bean p:order="3000" id="blAddFulfillmentGroupItemActivity" class="org.broadleafcommerce.core.order.service.workflow.add.AddFulfillmentGroupItemActivity"/> */
	@Bean
	public AddFulfillmentGroupItemActivity blAddFulfillmentGroupItemActivity() {
		AddFulfillmentGroupItemActivity bean = new AddFulfillmentGroupItemActivity();
		bean.setOrder(3000);
		return bean;
	}

    /* <bean p:order="4000" id="blAddWorkflowPriceOrderIfNecessaryActivity" class="org.broadleafcommerce.core.order.service.workflow.PriceOrderIfNecessaryActivity"/> */
	@Bean
	public PriceOrderIfNecessaryActivity blAddWorkflowPriceOrderIfNecessaryActivity() {
		PriceOrderIfNecessaryActivity bean = new PriceOrderIfNecessaryActivity();
		bean.setOrder(4000);
		return bean;
	}

	/* <bean p:order="5000" id="blAddWorkflowVerifyFulfillmentGroupItemsActivity" class="org.broadleafcommerce.core.order.service.workflow.VerifyFulfillmentGroupItemsActivity"/> */
	@Bean
	public VerifyFulfillmentGroupItemsActivity blAddWorkflowVerifyFulfillmentGroupItemsActivity() {
		VerifyFulfillmentGroupItemsActivity bean = new VerifyFulfillmentGroupItemsActivity();
		bean.setOrder(5000);
		return bean;
	}

    /* <bean id="blAddItemWorkflow" class="org.broadleafcommerce.core.workflow.SequenceProcessor">
        <property name="processContextFactory">
            <bean class="org.broadleafcommerce.core.order.service.workflow.CartOperationProcessContextFactory"/>
        </property>
        <property name="activities">
            <list>
                <ref bean="blValidateAddRequestActivity" />
                <ref bean="blAddOrderItemActivity" />
                <ref bean="blAddFulfillmentGroupItemActivity" />
                <ref bean="blAddWorkflowPriceOrderIfNecessaryActivity" />
                <ref bean="blAddWorkflowVerifyFulfillmentGroupItemsActivity" />
            </list>
        </property>
        <property name="defaultErrorHandler" ref="blDefaultErrorHandler"/>
    </bean> */
	@Bean
	public SequenceProcessor blAddItemWorkflow() {
		SequenceProcessor bean =  new SequenceProcessor();
		bean.setProcessContextFactory(new CartOperationProcessContextFactory());
		List<Activity<ProcessContext>> activities = new ArrayList<Activity<ProcessContext> >();
		activities.add(asActivity(blValidateAddRequestActivity()));
		activities.add(asActivity(blAddOrderItemActivity()));
		activities.add(asActivity(blAddFulfillmentGroupItemActivity()));
		activities.add(asActivity(blAddWorkflowPriceOrderIfNecessaryActivity()));
		activities.add(asActivity(blAddWorkflowVerifyFulfillmentGroupItemsActivity()));
		bean.setActivities(activities);
		bean.setDefaultErrorHandler(blDefaultErrorHandler());
		return bean;
	}

    /* <!-- Update Item Workflow Configuration --> */
    /* <bean p:order="1000" id="blValidateUpdateRequestActivity" class="org.broadleafcommerce.core.order.service.workflow.update.ValidateUpdateRequestActivity"/> */
	@Bean
	public ValidateUpdateRequestActivity blValidateUpdateRequestActivity() {
		ValidateUpdateRequestActivity bean = new ValidateUpdateRequestActivity();
		bean.setOrder(1000);
		return bean;
	}

    /* <bean p:order="2000" id="blUpdateOrderItemActivity" class="org.broadleafcommerce.core.order.service.workflow.update.UpdateOrderItemActivity"/> */
	@Bean
	public UpdateOrderItemActivity blUpdateOrderItemActivity() {
		UpdateOrderItemActivity bean = new UpdateOrderItemActivity();
		bean.setOrder(2000);
		return bean;
	}

    /* <bean p:order="3000" id="blUdateOrderMultishipOptionActivity" class="org.broadleafcommerce.core.order.service.workflow.update.UpdateOrderMultishipOptionActivity"/> */
	@Bean
	public UpdateOrderMultishipOptionActivity blUdateOrderMultishipOptionActivity() {
		UpdateOrderMultishipOptionActivity bean = new UpdateOrderMultishipOptionActivity();
		bean.setOrder(3000);
		return bean;
	}

    /* <bean p:order="4000" id="blUpdateFulfillmentGroupItemActivity" class="org.broadleafcommerce.core.order.service.workflow.update.UpdateFulfillmentGroupItemActivity"/> */
	@Bean
	public UpdateFulfillmentGroupItemActivity blUpdateFulfillmentGroupItemActivity() {
		UpdateFulfillmentGroupItemActivity bean = new UpdateFulfillmentGroupItemActivity();
		bean.setOrder(4000);
		return bean;
	}

    /* <bean p:order="5000" id="blUpdateWorkflowPriceOrderIfNecessaryActivity" class="org.broadleafcommerce.core.order.service.workflow.PriceOrderIfNecessaryActivity"/> */
	@Bean
	public PriceOrderIfNecessaryActivity blUpdateWorkflowPriceOrderIfNecessaryActivity() {
		PriceOrderIfNecessaryActivity bean = new PriceOrderIfNecessaryActivity();
		bean.setOrder(5000);
		return bean;
	}

    /* <bean p:order="6000" id="blUpdateWorkflowVerifyFulfillmentGroupItemsActivity" class="org.broadleafcommerce.core.order.service.workflow.VerifyFulfillmentGroupItemsActivity"/> */
	@Bean
	public VerifyFulfillmentGroupItemsActivity blUpdateWorkflowVerifyFulfillmentGroupItemsActivity() {
		VerifyFulfillmentGroupItemsActivity bean = new VerifyFulfillmentGroupItemsActivity();
		bean.setOrder(6000);
		return bean;
	}

    /* <bean id="blUpdateItemWorkflow" class="org.broadleafcommerce.core.workflow.SequenceProcessor">
        <property name="processContextFactory">
            <bean class="org.broadleafcommerce.core.order.service.workflow.CartOperationProcessContextFactory"/>
        </property>
        <property name="activities">
            <list>
                <ref bean="blValidateUpdateRequestActivity" />
                <ref bean="blUpdateOrderItemActivity" />
                <ref bean="blUdateOrderMultishipOptionActivity" />
                <ref bean="blUpdateFulfillmentGroupItemActivity" />
                <ref bean="blUpdateWorkflowPriceOrderIfNecessaryActivity" />
                <ref bean="blUpdateWorkflowVerifyFulfillmentGroupItemsActivity" />
            </list>
        </property>
        <property name="defaultErrorHandler" ref="blDefaultErrorHandler"/>
    </bean> */
	@Bean
	public SequenceProcessor blUpdateItemWorkflow() {
		SequenceProcessor bean =  new SequenceProcessor();
		bean.setProcessContextFactory(new CartOperationProcessContextFactory());
		List<Activity<ProcessContext>> activities = new ArrayList<Activity<ProcessContext> >();
		activities.add(asActivity(blValidateUpdateRequestActivity()));
		activities.add(asActivity(blUpdateOrderItemActivity()));
		activities.add(asActivity(blUdateOrderMultishipOptionActivity()));
		activities.add(asActivity(blUpdateFulfillmentGroupItemActivity()));
		activities.add(asActivity(blUpdateWorkflowPriceOrderIfNecessaryActivity()));
		activities.add(asActivity(blUpdateWorkflowVerifyFulfillmentGroupItemsActivity()));
		bean.setActivities(activities);
		bean.setDefaultErrorHandler(blDefaultErrorHandler());
		return bean;
	}

    /* <!-- Remove Item Workflow Configuration --> */
    /* <bean p:order="1000" id="blValidateRemoveRequestActivity" class="org.broadleafcommerce.core.order.service.workflow.remove.ValidateRemoveRequestActivity"/> */
	@Bean
	public ValidateRemoveRequestActivity blValidateRemoveRequestActivity() {
		ValidateRemoveRequestActivity bean = new ValidateRemoveRequestActivity();
		bean.setOrder(1000);
		return bean;
	}

	/* <bean p:order="2000" id="blRemoveOrderMultishipOptionActivity" class="org.broadleafcommerce.core.order.service.workflow.remove.RemoveOrderMultishipOptionActivity"/> */
	@Bean
	public RemoveOrderMultishipOptionActivity blRemoveOrderMultishipOptionActivity() {
		RemoveOrderMultishipOptionActivity bean = new RemoveOrderMultishipOptionActivity();
		bean.setOrder(2000);
		return bean;
	}

    /* <bean p:order="3000" id="blRemoveFulfillmentGroupItemActivity" class="org.broadleafcommerce.core.order.service.workflow.remove.RemoveFulfillmentGroupItemActivity"/> */
	@Bean
	public RemoveFulfillmentGroupItemActivity blRemoveFulfillmentGroupItemActivity() {
		RemoveFulfillmentGroupItemActivity bean = new RemoveFulfillmentGroupItemActivity();
		bean.setOrder(3000);
		return bean;
	}

    /* <bean p:order="4000" id="blRemoveOrderItemActivity" class="org.broadleafcommerce.core.order.service.workflow.remove.RemoveOrderItemActivity"/> */
	@Bean
	public RemoveOrderItemActivity blRemoveOrderItemActivity() {
		RemoveOrderItemActivity bean = new RemoveOrderItemActivity();
		bean.setOrder(4000);
		return bean;
	}

    /* <bean p:order="5000" id="blRemoveWorkflowPriceOrderIfNecessaryActivity" class="org.broadleafcommerce.core.order.service.workflow.PriceOrderIfNecessaryActivity"/> */
	@Bean
	public PriceOrderIfNecessaryActivity blRemoveWorkflowPriceOrderIfNecessaryActivity() {
		PriceOrderIfNecessaryActivity bean = new PriceOrderIfNecessaryActivity();
		bean.setOrder(5000);
		return bean;
	}

    /* <bean p:order="6000" id="blRemoveWorkflowVerifyFulfillmentGroupItemsActivity" class="org.broadleafcommerce.core.order.service.workflow.VerifyFulfillmentGroupItemsActivity"/> */
	@Bean
	public VerifyFulfillmentGroupItemsActivity blRemoveWorkflowVerifyFulfillmentGroupItemsActivity() {
		VerifyFulfillmentGroupItemsActivity bean = new VerifyFulfillmentGroupItemsActivity();
		bean.setOrder(6000);
		return bean;
	}

    /* <bean id="blRemoveItemWorkflow" class="org.broadleafcommerce.core.workflow.SequenceProcessor">
        <property name="processContextFactory">
            <bean class="org.broadleafcommerce.core.order.service.workflow.CartOperationProcessContextFactory"/>
        </property>
        <property name="activities">
            <list>
                <ref bean="blValidateRemoveRequestActivity" />
                <ref bean="blRemoveOrderMultishipOptionActivity" />
                <ref bean="blRemoveFulfillmentGroupItemActivity" />
                <ref bean="blRemoveOrderItemActivity" />
                <ref bean="blRemoveWorkflowPriceOrderIfNecessaryActivity" />
                <ref bean="blRemoveWorkflowVerifyFulfillmentGroupItemsActivity" />
            </list>
        </property>
        <property name="defaultErrorHandler" ref="blDefaultErrorHandler"/>
    </bean> */
	@Bean
	public SequenceProcessor blRemoveItemWorkflow() {
		SequenceProcessor bean =  new SequenceProcessor();
		bean.setProcessContextFactory(new CartOperationProcessContextFactory());
		List<Activity<ProcessContext>> activities = new ArrayList<Activity<ProcessContext> >();
		activities.add(asActivity(blValidateRemoveRequestActivity()));
		activities.add(asActivity(blRemoveOrderMultishipOptionActivity()));
		activities.add(asActivity(blRemoveFulfillmentGroupItemActivity()));
		activities.add(asActivity(blRemoveOrderItemActivity()));
		activities.add(asActivity(blRemoveWorkflowPriceOrderIfNecessaryActivity()));
		activities.add(asActivity(blRemoveWorkflowVerifyFulfillmentGroupItemsActivity()));
		bean.setActivities(activities);
		bean.setDefaultErrorHandler(blDefaultErrorHandler());
		return bean;
	}

    /**** Checkout Workflow Configuration  ****/
    /* <bean p:order="1000" id="blVerifyCustomerMaxOfferUsesActivity" class="org.broadleafcommerce.core.offer.service.workflow.VerifyCustomerMaxOfferUsesActivity"/> */
	public VerifyCustomerMaxOfferUsesActivity blVerifyCustomerMaxOfferUsesActivity() {
		VerifyCustomerMaxOfferUsesActivity bean = new VerifyCustomerMaxOfferUsesActivity();
		bean.setOrder(1000);
		return bean;
	}

    /* <bean p:order="2000" id="blPaymentServiceActivity" class="org.broadleafcommerce.core.checkout.service.workflow.PaymentServiceActivity"/> */
	public PaymentServiceActivity blPaymentServiceActivity() {
		PaymentServiceActivity bean = new PaymentServiceActivity();
		bean.setOrder(2000);
		return bean;
	}

    /* <bean p:order="3000" id="blRecordOfferUsageActivity" class="org.broadleafcommerce.core.offer.service.workflow.RecordOfferUsageActivity"/> */
	public RecordOfferUsageActivity blRecordOfferUsageActivity() {
		RecordOfferUsageActivity bean = new RecordOfferUsageActivity();
		bean.setOrder(3000);
		return bean;
	}

    /* <bean p:order="4000" id="blCommitTaxActivity" class="org.broadleafcommerce.core.checkout.service.workflow.CommitTaxActivity">
        <property name="rollbackHandler">
            <bean class="org.broadleafcommerce.core.checkout.service.workflow.CommitTaxRollbackHandler"/>
        </property>
    </bean> */
	public CommitTaxActivity blCommitTaxActivity() {
		CommitTaxActivity bean = new CommitTaxActivity();
		bean.setRollbackHandler(new CommitTaxRollbackHandler());
		bean.setOrder(4000);
		return bean;
	}

    /* <bean p:order="5000" id="blCompleteOrderActivity" class="org.broadleafcommerce.core.checkout.service.workflow.CompleteOrderActivity"/> */
	public CompleteOrderActivity blCompleteOrderActivity() {
		CompleteOrderActivity bean = new CompleteOrderActivity();
		bean.setOrder(5000);
		return bean;
	}

    /* <bean id="blCheckoutWorkflow" class="org.broadleafcommerce.core.workflow.SequenceProcessor">
        <property name="processContextFactory">
            <bean class="org.broadleafcommerce.core.checkout.service.workflow.CheckoutProcessContextFactory"/>
        </property>
        <property name="activities">
            <list>
                <ref bean="blVerifyCustomerMaxOfferUsesActivity" />
                <ref bean="blPaymentServiceActivity" />
                <ref bean="blRecordOfferUsageActivity" />
                <ref bean="blCommitTaxActivity" />
                <ref bean="blCompleteOrderActivity" />
            </list>
        </property>
        <property name="defaultErrorHandler" ref="blDefaultErrorHandler"/>
    </bean> */
	@Bean
	public SequenceProcessor blCheckoutWorkflow() {
		SequenceProcessor bean =  new SequenceProcessor();
		bean.setProcessContextFactory(new CheckoutProcessContextFactory());
		List<Activity<ProcessContext>> activities = new ArrayList<Activity<ProcessContext> >();
		activities.add(asActivity(blVerifyCustomerMaxOfferUsesActivity()));
		activities.add(asActivity(blPaymentServiceActivity()));
		activities.add(asActivity(blRecordOfferUsageActivity()));
		activities.add(asActivity(blCommitTaxActivity()));
		activities.add(asActivity(blCompleteOrderActivity()));
		bean.setActivities(activities);
		bean.setDefaultErrorHandler(blDefaultErrorHandler());
		return bean;
	}

    /* <aop:config>
        <aop:aspect id="qosAspect" ref="blServiceMonitor">
            <aop:pointcut id="qosMethod" expression="execution(* org.broadleafcommerce.common.vendor.service.monitor.ServiceStatusDetectable.process(..))"/>
            <aop:around method="checkServiceAOP" pointcut-ref="qosMethod"/>
        </aop:aspect>
    </aop:config> */
	// TODO: Implement this with annotations directly on the services.
}
