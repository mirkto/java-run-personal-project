package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Logger;

public class Handler implements HttpHandler {
    private static final Logger LOGGER = Logger.getLogger("Handler");

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
        LinkedHashSet<String> queryArray = parseQuery();
        if (queryArray == null) {
            return "[]";
        }
        ArrayList<User> usersArray = searchUsers(queryArray);
        return responseBuilder(usersArray);
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

    private LinkedHashSet<String> parseQuery() {
        LOGGER.info("- query parse");
        LinkedHashSet<String> queryArray = new LinkedHashSet<>();
        int key = 0;
        String[] array = queryStr.split("&");
        for (String str : array) {
            String[] pair = str.split("=");
            if (pair.length != 2) {
                LOGGER.severe("- not correct query");
                return null;
            }
            if ("name".equals(pair[0]) || "id".equals(pair[0])) {
                queryArray.add(pair[1].toLowerCase());
            }
        }
        return queryArray;
    }

    private ArrayList<User> searchUsers(LinkedHashSet<String> queryArray) {

        ArrayList<User> foundUsers = new ArrayList<>();
        for (String value : queryArray) {
            for (User user : usersBase) {
                if (user.matches(value)) {
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
        tmpResponse.append("[");
        for (User user : usersArr) {
            if (tmpResponse.length() > 1) {
                tmpResponse.append(",");
            }
            tmpResponse.append(user.toString());
        }
        tmpResponse.append("]");
        return tmpResponse.toString();
    }
}