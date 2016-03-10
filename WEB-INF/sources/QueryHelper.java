import java.math.RoundingMode;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.List;
import java.util.LinkedList;

import org.jsoup.Jsoup;

public class QueryHelper {
	private static DecimalFormat df = new DecimalFormat("##.####");
	static {
		df.setRoundingMode(RoundingMode.DOWN);
	}
	
        private static int getTermID(String searchTerm, Connection conn) throws SQLException {

                String query = "SELECT id FROM terms WHERE term = ?";

                PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, searchTerm);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getInt(1);
        }

	public static String findTextInFile(String url, String searchTerm) {
                //parse fileName from URL
                String fileName = url.replace("http://", "");
                fileName = fileName.replaceAll("[/%_?]", ""); //check later 

                int distanceFromTerm = 30;

                File currFile = new File("/opt/tomcat/webapps/searchEngine/resources/index/" + fileName);
                org.jsoup.nodes.Document doc;
		StringBuilder sb = new StringBuilder();
                try {
                        doc = Jsoup.parse(currFile,"UTF-8","");
                        String textContent = doc.body().text();
                        int indexOfTerm = textContent.toLowerCase().indexOf(searchTerm);
                        int start, end;

                        start = indexOfTerm > distanceFromTerm ? indexOfTerm - distanceFromTerm : 0;
                        end = textContent.length() - indexOfTerm > distanceFromTerm ? indexOfTerm + distanceFromTerm : textContent.length();

			sb.append("...");
			sb.append(textContent.substring(start, indexOfTerm));
			sb.append("<strong>");
			sb.append(textContent.substring(indexOfTerm, indexOfTerm + searchTerm.length()));
			sb.append("</strong>");
			sb.append(textContent.substring(indexOfTerm + searchTerm.length(), end));
			sb.append("...");

			System.out.println(String.format("findTextInFile: %s", sb.toString()));
			
			return sb.toString();
                } catch (Exception e) {
//                        e.printStackTrace();
			return sb.toString();
                }
        }

	public static List<PageInfo> queryForTerm(String[] searchTerms, Connection conn, int page) throws Exception {
                //printing out corresponding URLs that contain searchTerm
		List<PageInfo> pages = new LinkedList<PageInfo>();
                String query = "";
		PreparedStatement statement = null;

                if(searchTerms.length == 1) {
                        int termId = getTermID(searchTerms[0], conn);
//                      System.out.println(termId);
                        query = "SELECT B.url, B.id, A.TFIDF " +
				"FROM (SELECT A.term_id, A.document_id, A.tf * B.idf AS TFIDF " +
                                      "FROM tf A, idf B " +
                                      "WHERE A.term_id = B.term_id AND A.term_id = ? AND B.term_id = ?) AS A, documents B " +
                                "WHERE B.id = A.document_id " +
                                "ORDER BY TFIDF DESC " +
                                "LIMIT 10 OFFSET ?";

			statement = conn.prepareStatement(query);
			statement.setInt(1, termId);
			statement.setInt(2, termId);
			statement.setInt(3, page * 10);
                }
                else {
                        int termId1 = getTermID(searchTerms[0], conn);
                        int termId2 = getTermID(searchTerms[1], conn);

                        query = "SELECT B.url, B.id, SUM(A.TFIDF) as sumTFIDF " +
                                "FROM (SELECT A.term_id, A.document_id, A.tf * B.idf AS TFIDF " +
                                      "FROM tf A, idf B " +
                                      "WHERE ((A.term_id = ? AND B.term_id = ?) OR (A.term_id = ? AND B.term_id = ?))) AS A, documents B " +
                                "WHERE B.id = A.document_id " +
                                "GROUP BY B.url " +
                                "ORDER BY sumTFIDF DESC " +
                                "LIMIT 10 OFFSET ?";
			
			statement = conn.prepareStatement(query);
			statement.setInt(1, termId1);
                        statement.setInt(2, termId1);
                        statement.setInt(3, termId2);
                        statement.setInt(4, termId2);
			statement.setInt(5, page * 10);
                }

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                        String url = rs.getString(1);
			int pageId = rs.getInt(2);
			float tfIdf = rs.getFloat(3);
                        System.out.println(String.format("queryForTerm().results: %s | %d | %f", url, rs.getInt(2), rs.getFloat(3)));
                        String surrounding = findTextInFile(url,searchTerms[0]);
                        if(searchTerms.length > 1)
                                surrounding += findTextInFile(url, searchTerms[1]);

                        pages.add(new PageInfo(
                                url,
                                pageId,
                                df.format(tfIdf).toString(),
				surrounding));
                }
		rs.close();
		statement.close();
		return pages;
        }
}
