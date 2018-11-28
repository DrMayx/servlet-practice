package code.drmayx.servlets;

import code.drmayx.Utilities;
import code.drmayx.dao.PostgresDao;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseServlet extends HttpServlet implements HttpHandler {

    PostgresDao dao = new PostgresDao();

    private static String genericResponse = "<html>" +
            "<head>" +
            "<title>Welcome on $TITLE</title>" +
            "</head>" +
            "<body>" +
            "<h2>Hello on $TITLE page!</h2><br><br>" +
            "<form method=\"post\">" +
            "<input type=\"text\" name=\"userText\" placeholder=\"Search for people\">" +
            "<input type=\"submit\" value=\"Search\">" +
            "</form>" +
            "$ADDITIONAL" +
            "</body>" +
            "</html>";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String generatedResponse = genericResponse.replace("$TITLE", "Database").replace("$ADDITIONAL", "");
        response.getWriter().write(generatedResponse);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String userText = request.getParameter("userText");
        String generatedResponse = getFromDatabase(userText);
        response.getWriter().write(generatedResponse);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if(httpExchange.getRequestMethod().equalsIgnoreCase("get")){
            String generatedResponse = genericResponse.replace("$TITLE", "Database").replace("$ADDITIONAL", "");
            httpExchange.sendResponseHeaders(200, generatedResponse.length());
            httpExchange.getResponseBody().write(generatedResponse.getBytes());
        }
        else if(httpExchange.getRequestMethod().equalsIgnoreCase("post")){
            System.out.println("posting");
            String userText = Utilities.getParameters(httpExchange).get("userText");
            String generatedResponse = getFromDatabase(userText);

            httpExchange.sendResponseHeaders(200, generatedResponse.length());
            httpExchange.getResponseBody().write(generatedResponse.getBytes());
        }
    }

    public String getFromDatabase(String userText){
        String additional = "";
        userText = userText == null ? "" : userText;
        try {
            ResultSet resultSet = dao.GetDataFromQuery("select name from testdata where name like '%" + userText + "%';");
            while(resultSet.next()){
                additional += "<p>" + resultSet.getString("name") + "</p>";
            }
        } catch (SQLException e) {
            additional = "An error occured\n\n" + e.getMessage();
            System.out.println(additional);
            return "";
        }
        System.out.println(additional + " " + additional.equals(""));
        if(additional.equals("")){
            additional = "No results found";
        }
        return genericResponse.replace("$TITLE", "Database").replace("$ADDITIONAL", additional);
    }
}
