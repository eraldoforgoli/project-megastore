package eraldo.megastore.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import eraldo.megastore.model.Admin;
import eraldo.megastore.model.Item;
import eraldo.megastore.service.loginController;

@Path("/login")
public class loginResource {
	
	loginController login = new loginController();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Status login(@QueryParam("username") String username, @QueryParam("password") String password) {
		Admin a = new Admin(username,password);
		return login.login(a.getUsername(), a.getPassword());
	}
}
