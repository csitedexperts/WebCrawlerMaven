import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// Source: https://www.programcreek.com/2012/12/how-to-make-a-web-crawler-using-java/

 
public class WebcrawlerMain {
	public static WebcrawlerDB db = new WebcrawlerDB();
 
	public static void main(String[] args) throws SQLException, IOException {
		db.runSql2("TRUNCATE Record;");
		processPage("https://www.cdc.gov/coronavirus/");
	}
 
	public static void processPage(String URL) throws SQLException, IOException{
		//check if the given URL is already in database
		String sql = "select * from Record where URL = '"+URL+"'";
		ResultSet rs = db.runSql(sql);
		if(rs.next()){
 
		}else{
			//store the URL to database to avoid parsing again
			sql = "INSERT INTO  `webcrawler`.`Record` " + "(`URL`) VALUES " + "(?);";
			PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, URL);
			stmt.execute();
 
			//get useful information
			Document doc = Jsoup.connect("https://www.cdc.gov/").get();
 
			if(doc.text().contains("coronavirus transmission")){
				System.out.println(URL);
			}
 
			//get all links and recursively call the processPage method
			Elements questions = doc.select("a[href]");
			for(Element link: questions){
				if(link.attr("href").contains("cdc.gov"))
					processPage(link.attr("abs:href"));
			}
		}
	}
}