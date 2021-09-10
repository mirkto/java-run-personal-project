import com.sun.net.httpserver.HttpServer;
import src.HandlerPages;
import src.User;
import src.UserBaseLoader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class MyServer {
    public static void main(String[] args) {
        InetSocketAddress   address;
        HttpServer          server;
        List<User>          usersBase;

        try {
            usersBase = UserBaseLoader.loadBase();
            address = new InetSocketAddress(8080);
            server = HttpServer.create(address, 0);

            server.createContext("/", HandlerPages.DefaultPage());
            server.createContext("/my", HandlerPages.MyPageHandler());
            server.createContext("/friends", HandlerPages.FriendsListHandler(usersBase));
            server.createContext("/friends/search", HandlerPages.FriendsSearchHandler(usersBase));

            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("--- Error: MyServer ---");
        }
    }
}
