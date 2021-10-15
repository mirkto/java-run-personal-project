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
    public static final int DEFAULT_PORT = 8080;
    public static final int MAX_PORT = 65535;
    public static final int MIN_PORT = 49152;
    public static final String DEFAULT_BASE = "src/main/resources/userBase.json";

    HttpServer server;
    int port;
    List<User> usersBase;

    public MyServer() throws IOException {
        this(DEFAULT_PORT, DEFAULT_BASE);
    }

    public MyServer(Integer port) throws IOException {
        this(port, DEFAULT_BASE);
    }

    public MyServer(String userBasePath) throws IOException {
        this(DEFAULT_PORT, userBasePath);
    }

    public MyServer(Integer serverPort, String userBasePath) throws IOException {
        initPort(serverPort);
        initServer();
        initUsersBase(userBasePath);
        initContent();
        startServer();
    }

    private void initPort(int port){
        final Logger LOGGER = Logger.getLogger(MyServer.class.getName());
        if (port != DEFAULT_PORT && (port < MIN_PORT || port > MAX_PORT)) {
            this.port = DEFAULT_PORT;
            LOGGER.warning("wrong input port! Use DEFAULT_PORT");
        } else {
            this.port = port;
        }
        LOGGER.info("--- input port number is " + port + " ---");
    }

    private void initServer() throws IOException {
        InetSocketAddress address = new InetSocketAddress(port);
        server = HttpServer.create(address, 0);
    }

    private void initUsersBase(String userBase) throws IOException {
        usersBase = UserBaseLoader.loadBase(userBase);
    }

    private void initContent() {
        server.createContext("/", HandlerPages.DefaultPage());
        server.createContext("/my", HandlerPages.MyPageHandler());
        server.createContext("/my/friends", HandlerPages.FriendsListHandler(usersBase));
        server.createContext("/my/friends/search", HandlerPages.FriendsSearchHandler(usersBase));
    }

    private void startServer() {
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