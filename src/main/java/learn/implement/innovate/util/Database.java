package learn.implement.innovate.util;

import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Database {

	public static DataSource getDataSource() throws SQLException, NamingException{
		
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/venture");
//		Connection conn = ds.getConnection();
		
		return ds;
	}
}
