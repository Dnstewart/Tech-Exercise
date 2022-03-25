
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertStewart")
public class InsertStewart extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertStewart() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String title = request.getParameter("title");
      String author = request.getParameter("author");
      String date = request.getParameter("date");
      String location = request.getParameter("location");

      Connection connection = null;
      String insertSql = " INSERT INTO bookTable (TITLE, AUTHOR, DATE, LOCATION) values (?, ?, ?, ?)";

      try {
         DBConnectionStewart.getDBConnection(getServletContext());
         connection = DBConnectionStewart.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, title);
         preparedStmt.setString(2, author);
         preparedStmt.setString(3, date);
         preparedStmt.setString(4, location);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String com = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + com + "</title></head>\n" + //
            "<body bgcolor=\"powderblue\">\n" + //
            "<h2 align=\"center\">" + com + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Title</b>: " + title + "\n" + //
            "  <li><b>Author</b>: " + author + "\n" + //
            "  <li><b>Date</b>: " + date + "\n" + //
            "  <li><b>Location</b>: " + location + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject-TE-Stewart/Index>Home</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
