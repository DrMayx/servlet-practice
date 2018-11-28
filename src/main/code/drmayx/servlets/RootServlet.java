package code.drmayx.servlets;

import code.drmayx.Utilities;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RootServlet extends HttpServlet implements HttpHandler {

    private static String genericResponse = "<html>" +
            "<head>" +
            "<title>Welcome on $TITLE</title>" +
            "</head>" +
            "<body>" +
            "<h2>Hello on $TITLE page!</h2><br><br>" +
            "<form method=\"post\">" +
            "<input type=\"text\" name=\"userText\" placeholder=\"Enter text here\">" +
            "<input type=\"submit\" value=\"Submit\">" +
            "</form>" +
            "$ADDITIONAL" +
            "</body>" +
            "</html>";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String generatedResponse = genericResponse.replace("$TITLE", "Root").replace("$ADDITIONAL", "");
        response.getWriter().write(generatedResponse);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String newText = generateNextCharacter(request.getParameter("userText"));
        String generatedResponse = genericResponse.replace("$TITLE", "Root").replace("$ADDITIONAL", "You generated : " + newText);
        response.getWriter().write(generatedResponse);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if(httpExchange.getRequestMethod().equalsIgnoreCase("POST")){
            Map<String,String> data = Utilities.getParameters(httpExchange);
            for(String key : data.keySet()){
                System.out.println(key + ": " + data.get(key));
            }

            String generatedResponse = genericResponse.replace("$TITLE", "Root").replace("$ADDITIONAL", "You generated : " + generateNextCharacter(data.get("userText")));
            httpExchange.sendResponseHeaders(200, generatedResponse.length());
            httpExchange.getResponseBody().write(generatedResponse.getBytes());
        }
        else if(httpExchange.getRequestMethod().equalsIgnoreCase("GET")){
            String generatedResponse = genericResponse.replace("$TITLE", "Root").replace("$ADDITIONAL", "");
            httpExchange.sendResponseHeaders(200, generatedResponse.length());
            httpExchange.getResponseBody().write(generatedResponse.getBytes());
        }
    }

    private static String generateNextCharacter(String input){
        if(input == null){
            return "No input was given";
        }
        String newText = "";
        for(char c : input.toCharArray()){
            newText += (char) (c+1);
        }
        return newText;
    }
}
