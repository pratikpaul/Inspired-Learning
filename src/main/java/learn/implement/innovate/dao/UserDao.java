package learn.implement.innovate.dao;

import javax.ws.rs.core.Response;

import learn.implement.innovate.model.User;
import learn.implement.innovate.model.UserValidation;

public interface UserDao {

	public Response getUser(int id);

	public Response createUser(User user);

	public Response deleteUser(int id);
	
	public Response validateUser(UserValidation userinfo);

}
