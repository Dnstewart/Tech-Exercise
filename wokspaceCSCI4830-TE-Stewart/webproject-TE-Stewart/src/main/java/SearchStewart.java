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

@WebServlet("/SearchStewart")
public class SearchStewart extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchStewart() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String title = request.getParameter("title");
      String author = request.getParameter("author");
      search(title, author, response);
   }

   void search(String title, String author, HttpServletResponse response) throws IOException {
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

         if (title.isEmpty() && author.isEmpty()) {
            String selectSQL = "SELECT * FROM bookTable";
            preparedStatement = connection.prepareStatement(selectSQL);
         } 
         else if(author.isEmpty()) {
            String selectSQL = "SELECT * FROM bookTable WHERE TITLE LIKE ?";
            String theSearch = title + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theSearch);
         }
         else if(title.isEmpty()) {
        	 String selectSQL = "SELECT * FROM bookTable WHERE AUTHOR LIKE ?";
             String theSearch = author + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, theSearch);
         }
         else {
        	 
             String theTitle = title + "%";
             String theAuthor = author + "%";
             String selectSQL = "SELECT * FROM bookTable WHERE (TITLE LIKE '" + theTitle + "') AND (AUTHOR LIKE '" + theAuthor + "')";
             preparedStatement = connection.prepareStatement(selectSQL);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            String bookTitle = rs.getString("title").trim();
            String bookAuthor = rs.getString("author").trim();
            String bookDate = rs.getString("date").trim();
            String bookLocation = rs.getString("location").trim();

            if ((title.isEmpty() && author.isEmpty()) || bookTitle.contains(title) || bookAuthor.contains(author)) {
               out.println("Title:  " + bookTitle + ",  ");
               out.println("Author:  " + bookAuthor + ",  ");
               out.println("Date:  " + bookDate + ",  ");
               out.println("Location:  " + bookLocation + "<br>");
            }
         }
         out.println("<a href=/webproject-TE-Stewart/Index>Home</a> <br>");
         out.println("</body></html>");
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
