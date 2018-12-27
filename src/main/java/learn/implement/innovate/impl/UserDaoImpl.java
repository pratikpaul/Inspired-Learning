package learn.implement.innovate.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.SecretKey;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import learn.implement.innovate.dao.UserDao;
import learn.implement.innovate.encryption.AESEncryption;
import learn.implement.innovate.encryption.SecretKeyGeneration;
import learn.implement.innovate.model.StatusMessage;
import learn.implement.innovate.model.User;
import learn.implement.innovate.model.UserValidation;
import learn.implement.innovate.util.Database;

public class UserDaoImpl implements UserDao {

	public UserDaoImpl() throws NamingException, SQLException {

	}

	private DataSource datasource = Database.getDataSource();

	public Response getUser(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		
		String sql = "select id, first_name, last_name, user_name, "
				+ "pass from user " + "where id = ?";

		try {
			conn = datasource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("pass"));
			} else {
				StatusMessage statusMessage = new StatusMessage();
				statusMessage.setStatus(Status.NOT_FOUND.getStatusCode());
				statusMessage.setMessage(String.format("user with ID of %d not found", id));
				return Response.status(404).type("text/plain").entity(statusMessage.toString()).build();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(user.toString()).build();
	}
	
	public Response validateUser(UserValidation json){
		
		String userName = json.getUserName();
		String inPass = json.getPassword();
		int id = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SecretKey key = SecretKeyGeneration.Instance.getEncryptionKey();
		
		String sql = "SELECT id FROM user WHERE user_name = ? AND pass = SHA1(?)";

		try {
			conn = datasource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, inPass);
			rs = ps.executeQuery();

			if (rs.next()) {
				id = rs.getInt("id");
			} else {
				StatusMessage statusMessage = new StatusMessage();
				statusMessage.setStatus(Status.NOT_FOUND.getStatusCode());
				statusMessage.setMessage(String.format("user not registered"));
				return Response.status(404).type("text/plain").entity(statusMessage.toString()).build();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
//		dbPass = AESEncryption.decrypt(dbPass, key)
		
		return Response.status(200).type(MediaType.TEXT_PLAIN).entity("Validated").build();
	}

	public Response createUser(User user) {
		

		Connection conn = null;
		PreparedStatement ps = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		int autoId = -1;

		String sql = "insert into user (first_name, last_name, user_name, pass) values (?,?,?,SHA1(?))";

		try {

			conn = datasource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getUserName());
			ps.setString(4, user.getPassword());
			int rows = ps.executeUpdate();

			if (rows == 0) {
				StatusMessage statusMsg = new StatusMessage();
				statusMsg.setStatus(Status.NOT_FOUND.getStatusCode());
				statusMsg.setMessage("Unable to create user..");
				return Response.status(404).entity(statusMsg).build();
			}

			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT last_insert_id()");

			if (rs.next()) {
				autoId = rs.getInt(1);
				user.setId(autoId);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return Response.status(200).type("text/plain").entity("User succesfully Created").build();
	}

	public Response deleteUser(int id) {
		

		Connection conn = null;
		PreparedStatement ps = null;
		StatusMessage statusMessage = null;

		String sql = "delete from user where id = ?";

		try {
			conn = datasource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			int rows = ps.executeUpdate();

			if (rows == 0) {
				statusMessage = new StatusMessage();
				statusMessage.setStatus(Status.NOT_FOUND.getStatusCode());
				statusMessage.setMessage(String.format("Unable to DELETE customer with ID of %d...", id));
				return Response.status(404).entity(statusMessage).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		statusMessage = new StatusMessage();
		statusMessage.setStatus(Status.OK.getStatusCode());
		statusMessage.setMessage(String.format("Successfully deleted customer with ID of %d...", id));
		return Response.status(200).entity(statusMessage).build();
	}

}
