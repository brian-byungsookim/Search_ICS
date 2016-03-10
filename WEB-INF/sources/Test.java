import java.sql.Connection;
import java.sql.DriverManager;

public class Test {
	
	public static void main(String[] args) {
		System.out.println("starting");
		//QueryHelper.findTextInFile("www.ics.uci.edu/~thornton/cs141", "generally");
		
		try {
                	Class.forName("com.mysql.jdbc.Driver").newInstance();
                	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crawler", "root", "root");
			
			String[] single  = "machine".split(" ");
			QueryHelper.queryForTerm(single, conn, 1);

			String[] twoTerms = "student affairs".split(" ");
                        QueryHelper.queryForTerm(twoTerms, conn, 1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("finished");
	}

}
