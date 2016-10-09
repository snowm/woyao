package com.woyao;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class TestJerseyService {

	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/test/plans/{id}")
	public String helloWorld() {
		return "hello world";
	}
}
