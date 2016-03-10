import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class Search extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public String getServletInfo() {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	response.setContentType("text/html");
	
	PrintWriter out = response.getWriter();
    	String param = request.getParameter("param");
	String page = request.getParameter("page");
    	
    	try {
    		Context initCtx = new InitialContext();
    	        Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
            	Connection dbcon = ds.getConnection();

		String[] searchTerms = param.split(" ");

                List<PageInfo> pages = QueryHelper.queryForTerm(searchTerms, dbcon, Integer.parseInt(page));
		
		for (PageInfo p :  pages)
			out.println(p.getContext());
	
            	request.setAttribute("pages", pages);
            	request.getRequestDispatcher("/search.jsp").forward(request, response);
            
            	dbcon.close();
    	} catch (SQLException ex) {
    		ex.printStackTrace();
    		while (ex != null) {
    			System.out.println ("SQL Exception:  " + ex.getMessage ());
    			ex = ex.getNextException ();
    		} 
    	} catch(java.lang.Exception ex) {
    		out.println("<HTML>" +
    				"<HEAD><TITLE>" +
    				"MovieDB: Error" +
    				"</TITLE></HEAD>\n<BODY>" +
    				"<P>SQL error in doGet: " +
    				ex.getMessage() + "</P></BODY></HTML>");
    		return;
    	}
    	out.close();
    }
}
