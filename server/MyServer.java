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

        address = new InetSocketAddress(8080);
        try {
            usersBase = UserBaseLoader.loadBase();
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
