package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Handler implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger(Handler.class.getName());

    private List<User> usersBase = null;
    private String queryStr = null;
    private String command = "default";
    private String response = "";

    //------------- getters and setters -------------
    public void setCommand(String command) {
        this.command = command;
    }

    public void setUserBase(List<User> newBase) {
        this.usersBase = newBase;
    }

    //------------- public methods -------------
    public Handler setResponse(String message) {
        response = message;
        return this;
    }

    @Override
    public void handle(HttpExchange exc) throws IOException {
        LOGGER.info("--- new request came ---");

        queryStr = exc.getRequestURI().getQuery();
        if (command.equals("search")) {
            response = commandSearch();
        }
        exc.sendResponseHeaders(200, response.length());
        OutputStream os = exc.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    //------------- private methods -------------
    private String commandSearch() {
        LOGGER.info("- request command is: search");

        if (!checkBaseConnect()) {
            return "[]";
        }
        if (queryStr == null) {
            return usersBase.toString();
        }
        ArrayList<Pair> queryArray = parseQuery();
        if (queryArray == null) {
            return "[]";
        }
        ArrayList<User> usersArr = searchUsers(queryArray);
        return responseBuilder(usersArr);
    }

    private boolean checkBaseConnect() {
        LOGGER.info("- connect usersBase");
        if (usersBase == null) {
            LOGGER.severe("- database loading error");
            return false;
        }
        if (usersBase.isEmpty()) {
            LOGGER.warning("- database is empty");
            return false;
        }
        return true;
    }

    private ArrayList<Pair> parseQuery() {
        LOGGER.info("- query parse");
        ArrayList<Pair> queryArray = new ArrayList<>();

        String[] array = queryStr.split("&");
        for (String str : array) {
            Pair pair = createAndCheckPair(str);
            if (pair == null) {
                return null;
            }
            if (!queryArray.contains(pair)) {
                queryArray.add(pair);
            }
        }
        return queryArray;
    }

    private Pair createAndCheckPair(String str) {
        Pair pair;
        try {
            pair = new Pair(str.split("="));
        } catch (Pair.manyArgumentsException e) {
            LOGGER.severe("- wrong argument for query value");
            return null;
        }
        if (!pair.getKey().equalsIgnoreCase("name") &&
                !pair.getKey().equalsIgnoreCase("id")) {
            LOGGER.severe("- \"" + pair.getKey() + "\" is not correct query");
            return null;
        }
        return pair;
    }

    private ArrayList<User> searchUsers(ArrayList<Pair> queryArr) {

        ArrayList<User> foundUsers = new ArrayList<>();
        for (Pair pair : queryArr) {
            for (User user : usersBase) {
                if (pair.getValue().equalsIgnoreCase(user.getName()) ||
                        pair.getValue().equalsIgnoreCase(user.getId())) {
                    if (!foundUsers.contains(user)) {
                        foundUsers.add(user);
                    }
                }
            }
        }
        return foundUsers;
    }

    private String responseBuilder(ArrayList<User> usersArr) {
        StringBuilder tmpResponse = new StringBuilder();
        for (User user : usersArr) {
            if (!tmpResponse.toString().equals("")) {
                tmpResponse.append(",");
            }
            tmpResponse.append(user.toString());
        }
        tmpResponse.insert(0,"[");
        tmpResponse.append("]");
        return tmpResponse.toString();
    }
}