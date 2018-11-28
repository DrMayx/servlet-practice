package code.drmayx.servlets;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleServlet extends HttpServlet implements HttpHandler {

    private String genericResponse = "You are now on app or on demo. hello";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write(genericResponse);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if(httpExchange.getRequestMethod().equalsIgnoreCase("get")){
            httpExchange.sendResponseHeaders(200, genericResponse.length());
            httpExchange.getResponseBody().write(genericResponse.getBytes());
        }
    }
}
