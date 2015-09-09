package com.example;

import java.util.logging.Logger;

import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.example.boundary.Logged;
import com.example.control.MemberController;
import com.example.entity.Member;
import com.example.util.DataSourceConfiguration;

@Path("/")
public class ApplicationResource {

	@Inject
	Logger LOG;
	
	//@Inject
	//DataSourceConfiguration dataSourceConfiguration;
	
	@Inject
	MemberController memberController;
	
	@GET
	@Path("/hello/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello(@PathParam("name")  @Pattern(regexp="[A-Z]{3}", message="Hai") String name) {
		LOG.warning(name);
		return "Hello World!";
	}
	
	@POST
	@Path("/member")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response addMember(Member m, @Context UriInfo uriInfo) {
		UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(memberController.addMember(m)));
		return Response.created(builder.build()).build();
	}
	
	@GET
	@Path("/member/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Member readMember(@PathParam("id") long id) {
		return memberController.readMember(id);
	}
	
}
