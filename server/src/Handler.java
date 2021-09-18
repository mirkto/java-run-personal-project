package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class Handler implements HttpHandler {
    private List<User> usersBase = null;
    private String queryStr = null;
    private String command = "default";
    private String response = "<h1>This is \"java run personal task\" by ngonzo.</h1>";

    public void setCommand(String command) { this.command = command; }
    public void setUserBase(List<User> newBase) { this.usersBase = newBase; }
    public Handler setResponse(String message) { response = message; return this; }

    private static final Logger LOGGER = Logger.getLogger(Handler.class.getName());

    @Override
    public void handle(HttpExchange exc) throws IOException {
        LOGGER.info( "--- new request came ---");

        queryStr = exc.getRequestURI().getQuery();
        if (command.equals("search")) {
            response = commandSearch();
        }
        exc.sendResponseHeaders(200, response.length());
        OutputStream os = exc.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    private String commandSearch() {
        LOGGER.info( "- request command is: search");

        response = "";
        if (!checkBaseConnect()) {
            return "<h1>Error: database loading error</h1>";
        }
        if (!checkQuery()) {
            return usersBase.toString();
        }
        ArrayList<Pair> queryArr = parseQuery();
        if (queryArr == null){
            return "<h1>Error: not correct query</h1>\n<h1>[]</h1>";
        }
        for (Pair p: queryArr) {
            System.out.println(" - " + p.getKey() + ":" + p.getValue() );
        }

        LOGGER.info( "--- response done --- ");
        return response;
    }

    private boolean checkBaseConnect() {
        LOGGER.info( "- check users_base connect");
        if (usersBase == null) {
            LOGGER.severe( "- database loading error");
            return false;
        }
        return true;
    }

    private boolean checkQuery() {
        LOGGER.info( "- check query");
        if (queryStr == null) {
            LOGGER.info( "- no query: return user_base");
            return false;
        }
        return true;
    }

    private ArrayList<Pair> parseQuery() {
        LOGGER.info( "- query parse: ");
        ArrayList<Pair> query = new ArrayList<>();

        String[] array = queryStr.split("&");
        for (String str: array) {
            String[] pair = str.split("=");
            if (!checkPair(pair)) {
                return null;
            }
            Pair tmp = new Pair(pair[0],pair[1]);
            if (!query.contains(tmp)) {
                query.add(tmp);
            }
        }
        return query;
    }

    private boolean checkPair(String[] pair) {
        if (!pair[0].equalsIgnoreCase("name") &&
                !pair[0].equalsIgnoreCase("id")) {
            LOGGER.severe( "Error: \"" + pair[0] + "\"not correct query");
            return false;
        }
        return true;
    }


    private String addToResponse(String[] pair) {
        String result = "";
        for (User user: usersBase) {
            if (pair[1].equalsIgnoreCase(user.getName()) ||
                    pair[1].equalsIgnoreCase(user.getId())) {
                LOGGER.info( " request found");
                if (!result.equals("")) {
                    result += ",";
                }
                result += user.toString();
            }
        }
        return result;
    }
}
