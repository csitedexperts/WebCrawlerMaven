import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
/*
 
# MySQL Workbench Datbase commands
 
create database IF NOT EXISTS webcrawler ;

CREATE TABLE IF NOT EXISTS webcrawler.`Record` (
  `RecordID` INT(11) NOT NULL AUTO_INCREMENT,
  `URL` text NOT NULL,
  PRIMARY KEY (`RecordID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;


SELECT * FROM webcrawler.record;

  
 */

public class WebcrawlerDB {
 
	public Connection conn = null;
 
	public WebcrawlerDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/webcrawler";
			conn = DriverManager.getConnection(url, "root", "P@ssword");
			System.out.println("Connection established...");
		} catch (SQLException e) {
			System.out.println("Could not connect to the database...");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
 
	public ResultSet runSql(String sql) throws SQLException {
		Statement sta = conn.createStatement();
		return sta.executeQuery(sql);
	}
 
	public boolean runSql2(String sql) throws SQLException {
		Statement sta = conn.createStatement();
		return sta.execute(sql);
	}
 
	@Override
	protected void finalize() throws Throwable {
		if (conn != null || !conn.isClosed()) {
			conn.close();
		}
	}
}