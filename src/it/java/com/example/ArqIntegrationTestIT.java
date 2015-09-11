package com.example;

import static org.junit.Assert.*;

import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.Date;

import javax.inject.Inject;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.example.boundary.ConfigurationService;
import com.example.entity.Configuration;
import com.example.entity.Member;

@RunWith(Arquillian.class)
public class ArqIntegrationTestIT {
	
	
	@Deployment(testable=true)
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackages(true, "com")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
	}
	
	@ArquillianResource
	URL baseURL;
	
	@Test
	public void testAddMember() {
		Client client = ClientBuilder.newClient();
		Member member = Member.Builder.newMember()
							  .age(12)
							  .name("Ravi")
							  .dateOfBirth(null)
							  .build();
		Response response = client.target(baseURL.toString())
								  .path("api/member")
								  .request()
								  .post(Entity.entity(member, MediaType.APPLICATION_JSON));
		assertEquals(201, response.getStatus());
		//assertTrue(response.getLocation().toString().contains("/api/member/"));
	}
	
	@Test
	public void testAddAndReadMember() {
		Member member = Member.Builder.newMember()
							  .age(12)
							  .name("Ravi")
							  .dateOfBirth(new Date())
							  .build();
		Response response = ClientBuilder.newClient()
								  .target(baseURL.toString())
								  .path("api/member")
								  .request()
								  .post(Entity.entity(member, MediaType.APPLICATION_JSON));
		assertEquals(201, response.getStatus());
		System.out.println(response.getLocation().toString());
		member = ClientBuilder.newClient().target(response.getLocation().toString())
			  .request()
			  .accept(MediaType.APPLICATION_JSON)
			  .get(Member.class);
		assertEquals("Ravi", member.getName());
		assertEquals(12, member.getAge());
		assertTrue(member.getId()>0);
	}
	
	@Inject
	ConfigurationService configurationService;
	
	@Test
	public void createConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setApplication("ervletContext");
		configuration.setKey("url");
		configuration.setValue("http://www.google.com");
		configuration = configurationService.createConfig(configuration);
		System.out.println(configuration.getId());
	}
	
	@Test
	public void testFindConfiguration() {
		Configuration configuration = configurationService.findConfig("ervletContext").get(0);
		System.out.println(configuration.getId());
		System.out.println(configuration.getKey());
		System.out.println(configuration.getValue());
		System.out.println(configurationService.convertToKeyValuePair(configurationService.findConfig("ervletContext")));
	}
}
