package com.douzone.mysite.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoadListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce)  { 
		System.out.println("Application starts....");
	}
    public void contextDestroyed(ServletContextEvent sce)  { 
        
    }
	
}
