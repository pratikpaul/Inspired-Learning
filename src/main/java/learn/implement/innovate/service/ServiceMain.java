package learn.implement.innovate.service;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import learn.implement.innovate.dao.UserDao;
import learn.implement.innovate.impl.UserDaoImpl;
import learn.implement.innovate.model.User;
import learn.implement.innovate.model.UserValidation;


@Path("/")
public class ServiceMain {

	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getStatus(){
		return "working fine";
	}
	
	@Path("getUser")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@DefaultValue("0") @QueryParam("id") int id) throws NamingException, SQLException{
    	
    	UserDao daoImpl = new UserDaoImpl();
    	return daoImpl.getUser(id);
    }
    
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser2(@PathParam("id") int id) throws NamingException, SQLException{
    	UserDao daoImpl = new UserDaoImpl();
    	return daoImpl.getUser(id);
    }
	
	@POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) throws NamingException, SQLException{
    	
    	UserDao daoImpl = new UserDaoImpl();
    	return daoImpl.createUser(user);
    }

	@POST
    @Path("validateUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
	public Response validateUser(UserValidation json) throws NamingException, SQLException{
		
		UserDao daoImpl = new UserDaoImpl();
		
		return daoImpl.validateUser(json);
	}
	
	@DELETE
    @Path("deleteUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(@DefaultValue("0") @QueryParam("id") int id) throws NamingException, SQLException{
    	UserDao daoImpl = new UserDaoImpl();
    	return daoImpl.deleteUser(id);
    }
}
