package com.demidrolll.myphotos.web.listener;

import com.demidrolll.myphotos.common.annotation.qualifier.GooglePlus;
import com.demidrolll.myphotos.service.SocialService;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Inject
    private Logger logger;

    @Inject
    @GooglePlus
    private SocialService socialService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("googlePlusClientId", socialService.getClientId());
        logger.info("Application initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application destroyed");
    }
}
