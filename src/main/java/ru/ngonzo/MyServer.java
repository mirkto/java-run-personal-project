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
import java.util.logging.Logger;

public class MyServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(initPort(args));
        HttpServer server = HttpServer.create(address, 0);
        List<User> usersBase = UserBaseLoader.loadBase();

        server.createContext("/", HandlerPages.DefaultPage());
        server.createContext("/my", HandlerPages.MyPageHandler());
        server.createContext("/my/friends", HandlerPages.FriendsListHandler(usersBase));
        server.createContext("/my/friends/search", HandlerPages.FriendsSearchHandler(usersBase));

        server.setExecutor(null);
        server.start();
    }

    private static int initPort(String[] args) throws IOException {
        final Logger LOGGER = Logger.getLogger(MyServer.class.getName());
        int port = 8080;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        LOGGER.info("--- input port number is " + port + " ---");
        return port;
    }

    public void usePort(Integer port) throws IOException {
        if (port != null) {
            main(new String[]{port.toString()});
        }
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