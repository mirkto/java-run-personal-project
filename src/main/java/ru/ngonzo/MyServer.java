package ru.ngonzo;

import com.sun.net.httpserver.HttpServer;
import ru.ngonzo.server.HandlerPages;
import ru.ngonzo.server.User;
import ru.ngonzo.server.UserBaseLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.logging.LogManager;

public class MyServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(8080);
        HttpServer server = HttpServer.create(address, 0);
        List<User> usersBase = UserBaseLoader.loadBase();

        server.createContext("/", HandlerPages.DefaultPage());
        server.createContext("/my", HandlerPages.MyPageHandler());
        server.createContext("/my/friends", HandlerPages.FriendsListHandler(usersBase));
        server.createContext("/my/friends/search", HandlerPages.FriendsSearchHandler(usersBase));

        server.setExecutor(null);
        server.start();
    }

    static {
        // JVM-D java.util.logging.config.file=<server/src/data/log.config>
        String fileName = "src/main/resources/log.config";
        try (FileInputStream config = new FileInputStream(fileName)) {
            LogManager.getLogManager().readConfiguration(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}