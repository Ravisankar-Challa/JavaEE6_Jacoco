package com.example;

import static org.junit.Assert.*;

import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.Date;

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

import com.example.entity.Member;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArqIntegrationTestIT {
	
	private static String baseUrl;
	
	@Deployment(testable=true)
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackages(true, "com")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
	}
	
	@Before
	public void setup() throws Exception {
		baseUrl = "http://"+ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName("jboss.as:interface=public"), "inet-address")+":"+ManagementFactory.getPlatformMBeanServer().getAttribute(new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http"), "port"); ;
	}
	
	@Test
	public void testAddMember() {
		Client client = ClientBuilder.newClient();
		Member member = Member.Builder.newMember()
							  .age(12)
							  .name("Ravi")
							  .dateOfBirth(null)
							  .build();
		Response response = client.target(baseUrl)
								  .path("/test/api/member")
								  .request()
								  .post(Entity.entity(member, MediaType.APPLICATION_JSON));
		assertEquals(201, response.getStatus());
		//assertTrue(response.getLocation().toString().contains("/TestingEE/api/member/"));
	}
	
	@Test
	public void testAddAndReadMember() {
		Member member = Member.Builder.newMember()
							  .age(12)
							  .name("Ravi")
							  .dateOfBirth(new Date())
							  .build();
		Response response = ClientBuilder.newClient()
								  .target(baseUrl)
								  .path("/test/api/member")
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
	
/*	@Test
	@RunAsClient
    public void AshouldBeAbleToCallServlet(@ArquillianResource URL deployedApplicationUrl) throws Exception {
        String body = new URL(deployedApplicationUrl, "/Test").toString();
        System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL"+body);
        baseURL = body;
   }*/
	
}
