import com.sun.net.httpserver.HttpServer;
import src.HandlerPages;
import src.User;
import src.UserBaseLoader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class MyServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress   address = new InetSocketAddress(8080);
        HttpServer          server = HttpServer.create(address, 0);
        List<User>          usersBase = UserBaseLoader.loadBase();

        server.createContext("/", HandlerPages.DefaultPage());
        server.createContext("/my", HandlerPages.MyPageHandler());
        server.createContext("/friends", HandlerPages.FriendsListHandler(usersBase));
        server.createContext("/friends/search", HandlerPages.FriendsSearchHandler(usersBase));

        server.setExecutor(null);
        server.start();
    }
}
