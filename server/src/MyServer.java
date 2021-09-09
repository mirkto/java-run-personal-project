import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class MyServer {
    public static void main(String[] args) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/", new StartPageHandler());
        server.createContext("/myPage", new MyAccountHandler());
        server.createContext("/friends", new FriendsListHandler());
        server.createContext("/friends/search", new FriendsSearchHandler());
        server.setExecutor(null);
        server.start();
    }

    private static class StartPageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String str = "<h1>Hello! This is \"java run personal task\" by ngonzo.</h1>";
            byte[] bytes = str.getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    private static class MyAccountHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String str = "<h1>My Account page</h1>";
            byte[] bytes = str.getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    private static class FriendsListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
//            MyParser parser = new MyParser("data/userBase.json");


            String str = "Friends List page";
            byte[] bytes = str.getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }


    }

    private static class FriendsSearchHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String str = "Friends Search page";
            byte[] bytes = str.getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

}
