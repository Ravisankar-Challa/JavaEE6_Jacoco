package com.example.util;

import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;


/*@DataSourceDefinition(
	name="java:comp/DefaultDataSource",
	className="org.h2.Driver",
	url="jdbc:h2:mem:example;DB_CLOSE_DELAY=-1",
	user="sa",
	password="sa"
)*/

@Singleton
@ApplicationScoped
public class DataSourceConfiguration {
	/*@Resource
	DataSource ds;*/
	
	public DataSourceConfiguration() {
		//System.out.println("inside DataSourceConfiguration constructor");
	}
	
	@PostConstruct
	public void setup () {
		//System.out.println("inside DataSourceConfiguration constructor");
	}
	
	
}
