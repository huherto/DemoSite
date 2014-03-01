package com.mycompany.config.framework;

import java.util.HashSet;
import java.util.Set;

import org.broadleafcommerce.cms.web.processor.ContentProcessor;
import org.broadleafcommerce.common.web.dialect.BLCDialect;
import org.broadleafcommerce.common.web.processor.ResourceBundleProcessor;
import org.broadleafcommerce.core.web.processor.AddSortLinkProcessor;
import org.broadleafcommerce.core.web.processor.CategoriesProcessor;
import org.broadleafcommerce.core.web.processor.FormProcessor;
import org.broadleafcommerce.core.web.processor.GoogleAnalyticsProcessor;
import org.broadleafcommerce.core.web.processor.HeadProcessor;
import org.broadleafcommerce.core.web.processor.NamedOrderProcessor;
import org.broadleafcommerce.core.web.processor.PaginationPageLinkProcessor;
import org.broadleafcommerce.core.web.processor.PriceTextDisplayProcessor;
import org.broadleafcommerce.core.web.processor.ProductOptionDisplayProcessor;
import org.broadleafcommerce.core.web.processor.ProductOptionValueProcessor;
import org.broadleafcommerce.core.web.processor.ProductOptionsProcessor;
import org.broadleafcommerce.core.web.processor.RatingsProcessor;
import org.broadleafcommerce.core.web.processor.RelatedProductProcessor;
import org.broadleafcommerce.core.web.processor.RemoveFacetValuesLinkProcessor;
import org.broadleafcommerce.core.web.processor.ToggleFacetLinkProcessor;
import org.broadleafcommerce.core.web.processor.UrlRewriteProcessor;
import org.thymeleaf.processor.IProcessor;

public class InitializedBLCDialect extends BLCDialect {

	public InitializedBLCDialect() {

		Set<IProcessor> pset = new HashSet<IProcessor>();
		pset.add(new ContentProcessor());
		pset.add(new AddSortLinkProcessor());
		pset.add(new CategoriesProcessor());
		pset.add(new FormProcessor());

		pset.add(new GoogleAnalyticsProcessor());
		pset.add(new HeadProcessor());
		pset.add(new NamedOrderProcessor());
		pset.add(new PaginationPageLinkProcessor());

		pset.add(new PriceTextDisplayProcessor());
		pset.add(new ProductOptionValueProcessor());
		pset.add(new ProductOptionsProcessor());
		pset.add(new ProductOptionDisplayProcessor());

		pset.add(new RatingsProcessor());
		pset.add(new RelatedProductProcessor());
		pset.add(new RemoveFacetValuesLinkProcessor());
		pset.add(new ToggleFacetLinkProcessor());

		pset.add(new UrlRewriteProcessor());
		pset.add(new ResourceBundleProcessor());
		setProcessors(pset);
	}
}
