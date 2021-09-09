import com.sun.net.httpserver.HttpServer;
import src.Handler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyServer {
    public static void main(String[] args) {
          HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/", Handler.DefaultPage());
            server.createContext("/my", Handler.MyPageHandler());
            server.createContext("/friends", Handler.FriendsListHandler());
            server.createContext("/friends/search", Handler.FriendsSearchHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("--- Error: MyServer ---");
        }
    }
}
