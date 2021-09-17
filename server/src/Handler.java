package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Handler implements HttpHandler {
    private List<User> usersBase = null;
    private String queryStr = null;
    private String command = "default";
    private String response = "<h1>This is \"java run personal task\" by ngonzo.</h1>";

    public void setCommand(String command) { this.command = command; }
    public void setUserBase(List<User> newBase) { this.usersBase = newBase; }
    public Handler setResponse(String message) { response = message; return this; }

    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("server/src/data/log.config")) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Handler.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpExchange exc) throws IOException {
        LOGGER.log(Level.INFO, "--- new request came ---");

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
        LOGGER.log(Level.INFO, "- request command is: search");

        response = "";
        if (!checkBaseConnect()) {
            return "<h1>Error: database loading error</h1>";
        }
        if (!checkQuery()) {
            return usersBase.toString();
        }
        response = parseQuery();
        if (response == null){
            return "<h1>Error: not correct query</h1>\n<h1>[]</h1>";
        }
        return response;
    }

    private boolean checkBaseConnect() {
        LOGGER.log(Level.INFO, "- check users_base connect: ");

        if (usersBase == null) {
            System.out.print("Error");
            return false;
        }
        LOGGER.log(Level.INFO, "Ok");
        return true;
    }

    private boolean checkQuery() {
        if (queryStr == null) {
            System.out.print("- no query: return user_base");
            return false;
        }
        return true;
    }

    private String parseQuery() {
        LOGGER.log(Level.INFO, "- query parse: ");

        String[] array = queryStr.split("&");
        for (String str: array) {
            String[] pair = str.split("=");
            if (!checkPair(pair)) {
                return null;
            }
            String tmp = addToResponse(pair);
            if (!tmp.isEmpty() && !response.isEmpty()) {
                response += ",";
            }
            response += tmp;
        }
        LOGGER.log(Level.INFO, "--- response done --- ");
        return "[" + response +"]" ;
    }

    private boolean checkPair(String[] pair) {
        if (!pair[0].equalsIgnoreCase("name") &&
                !pair[0].equalsIgnoreCase("id")) {
            System.out.print("Error: \"" + pair[0] + "\"not correct query");
            return false;
        }
        return true;
    }


    private String addToResponse(String[] pair) {
        String result = "";
        for (User user: usersBase) {
            if (pair[1].equalsIgnoreCase(user.getName()) ||
                    pair[1].equalsIgnoreCase(user.getId())) {
                LOGGER.log(Level.INFO, " request found");
                if (!result.equals("")) {
                    result += ",";
                }
                result += user.toString();
            }
        }
        return result;
    }
}
