package code.drmayx.server;

import code.drmayx.controllers.MainController;
import code.drmayx.servlets.DatabaseServlet;
import code.drmayx.servlets.RootServlet;
import code.drmayx.servlets.SimpleServlet;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) {
        System.out.println("Launching server ...");

        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8080),0);
        } catch (IOException e) {
            System.out.println("An error occured creating the server: \n" + e.getMessage());
            return;
        }

        server.createContext("/", new RootServlet());
        server.createContext("/root", new RootServlet());
        server.createContext("/app", new SimpleServlet());
        server.createContext("/demo", new SimpleServlet());
        server.createContext("/database", new DatabaseServlet());
        server.setExecutor(null);
        System.out.println("Server Lunched!");
        server.start();

        MainController.Start();
    }
}
