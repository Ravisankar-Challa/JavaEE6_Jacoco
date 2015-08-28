package com.example.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class LoggerProducer {
	
	private Map<String, Logger> loggers = new HashMap<>();
	
	public LoggerProducer() {
		//System.out.println("Inside Logger Producer Construct");
	}
	
	@PostConstruct
	public void setup() {
		//System.out.println("Inside Logger Producer PostConstruct");
	}
	
	@Produces
	public Logger getLogger(InjectionPoint ip) {
		 //System.out.println("Inside Logger Producer getLogger method");
		 String loggerName = ip.getMember().getDeclaringClass().getName();
	     if (!loggers.containsKey(loggerName)) {
	    	 loggers.put(loggerName, Logger.getLogger(loggerName));
	     }
	     //System.out.println(loggerName);
	     return loggers.get(loggerName);
	}
}
