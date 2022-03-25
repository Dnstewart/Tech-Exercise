

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Index() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	      String com = "Library Book Database";
	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
	            "transitional//en\">\n"; //
	      out.println(docType + //
	            "<html>\n" + //
	            "<head><style>"
	            + "body {background-color: powderblue;}\r\n"
	            + "header {\r\n"
	            + "    background-color:powderblue;\r\n"
	            + "    color:black;\r\n"
	            + "    text-align:center;\r\n"
	            + "    padding:5px;	 \r\n"
	            + "}\r\n"
	            + "nav {\r\n"
	            + "    line-height:30px;\r\n"
	            + "    background-color:#eeeeee;\r\n"
	            + "    height:300px;\r\n"
	            + "    width:100px;\r\n"
	            + "    float:left;\r\n"
	            + "    padding:5px;	      \r\n"
	            + "}\r\n"
	            + "section {\r\n"
	            + "    width:350px;\r\n"
	            + "    float:left;\r\n"
	            + "    padding:10px;	 	 \r\n"
	            + "}\r\n"
	            + "footer {\r\n"
	            + "    background-color:powderblue;\r\n"
	            + "    color:black;\r\n"
	            + "    clear:both;\r\n"
	            + "    text-align:center;\r\n"
	            + "    padding:5px;	 	 \r\n"
	            + "}\r\n"
	            + "#booklist {\r\n"
	            + "     background-color:#eeeeee;\r\n"
	            + "		height: 290px;\r\n"
	            + "		width: 800px;\r\n"
	            + "		overflow: scroll;\r\n"
	            + "		border: 1px #aabbcc solid;\r\n"
	            + "		padding: 10px;\r\n"
	            + "}"
	            + "table {margin-left: auto; margin-right: auto;}\n"
	            + "table, th, td {border: 10px solid #eeeeee;}\n"
	            + "</style>\n"
	            + "<title>" + com + "</title></head>\n"
	            + "<body>\n <header><h1>" + com + "</h1><header>\n"
	            + "<nav> \r\n"
	            + "<p style=\"text-align:left;\">Choose an option</p>\r\n"
	            + "<a href=\"/webproject-TE-Stewart/insert_stewart.html\">Book Insert</a> <br>\r\n"
	            + "<a href=\"/webproject-TE-Stewart/search_stewart.html\">Book Search</a> <br>\r\n"
	            + "<a href=\"/webproject-TE-Stewart/remove_stewart.html\">Remove Book</a> <br>\r\n"
	            + "</nav>\n <div id=\"booklist\"><div>Books</div>"
	            + "<table>\n <tr>\n<th>Title</th>\n<th>Author</th>\n<th>Year</th>\n<th>Location</th>\n</tr>");
	      
	      Connection connection = null;
	      PreparedStatement preparedStatement = null;
	      try {
	         DBConnectionStewart.getDBConnection(getServletContext());
	         connection = DBConnectionStewart.connection;
	         String selectSQL = "SELECT * FROM bookTable";
	         preparedStatement = connection.prepareStatement(selectSQL);
	         ResultSet rs = preparedStatement.executeQuery();

	         while (rs.next()) {
	            String bookTitle = rs.getString("title").trim();
	            String bookAuthor = rs.getString("author").trim();
	            String bookDate = rs.getString("date").trim();
	            String bookLocation = rs.getString("location").trim();
	            out.println("<td>" + bookTitle + "  </td>\n");
	            out.println("<td>" + bookAuthor + "  </td>\n");
	            out.println("<td>" + bookDate + "  </td>\n");
	            out.println("<td>" + bookLocation + "  </td>\n</tr>\n");
	         }
	         out.println("</table><div></div>\n </div></body></html>");
	         rs.close();
	         preparedStatement.close();
	         connection.close();
		} catch (SQLException se) {
	         se.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (preparedStatement != null)
	               preparedStatement.close();
	         } catch (SQLException se2) {
	         }
	         try {
	            if (connection != null)
	               connection.close();
	         } catch (SQLException se) {
	            se.printStackTrace();
	         }
	      }
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
