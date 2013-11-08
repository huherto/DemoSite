package com.mycompany.config;

import java.io.IOException;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.core.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;


public class SolarTasks {
	
	@Autowired
	SearchService searchService;
	
	@Scheduled(fixedRate=5000)
	public void rebuildIndex() throws ServiceException, IOException {
		searchService.rebuildIndex();
	}

}
