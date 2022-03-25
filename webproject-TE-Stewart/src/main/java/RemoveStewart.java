

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemoveStewart
 */
@WebServlet("/RemoveStewart")
public class RemoveStewart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveStewart() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      String title = request.getParameter("title");
	      String author = request.getParameter("author");
	      String location = request.getParameter("location");
	      remove(title, author, location, response);
	}
	
    void remove(String title, String author, String location, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String com = "Search Result";
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
              "transitional//en\">\n"; //
        out.println(docType + //
              "<html>\n" + //
              "<head><title>" + com + "</title></head>\n" + //
              "<body bgcolor=\"powderblue\">\n" + //
              "<h1 align=\"center\">" + com + "</h1>\n");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
           DBConnectionStewart.getDBConnection(getServletContext());
           connection = DBConnectionStewart.connection;

           if (location.isEmpty()) {
        	   String theTitle = title + "%";
               String theAuthor = author + "%";
               String selectSQL = "DELETE FROM bookTable WHERE (TITLE LIKE '" + theTitle + "') AND (AUTHOR LIKE '" + theAuthor + "')";
               preparedStatement = connection.prepareStatement(selectSQL);
           } 
           else {
        	   String theTitle = title + "%";
               String theAuthor = author + "%";
               String theLocation = location + "%";
               String selectSQL = "DELETE FROM bookTable WHERE (TITLE LIKE '" + theTitle + "') AND (AUTHOR LIKE '" + theAuthor + "') AND (LOCATION LIKE '" + theLocation + "')";
               preparedStatement = connection.prepareStatement(selectSQL);
           }

          preparedStatement.execute();
          out.println("Removed: " + title + ", ");
          out.println(author + "<br>");
          out.println("<a href=/webproject-TE-Stewart/Index>Home</a> <br>");
          out.println("</body></html>");
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
