package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

public class Handler implements HttpHandler {
    private List<User> usersBase = null;
    private String queryStr = null;
    private String command = "default";
    private String response = "<h1>This is \"java run personal task\" by ngonzo.</h1>";

    public void setCommand(String command) { this.command = command; }
    public void setUserBase(List<User> newBase) { this.usersBase = newBase; }
    public Handler setResponse(String message) { response = message; return this; }

    @Override
    public void handle(HttpExchange exc) throws IOException {
        System.out.println("\n--- new request came ---");
        queryStr = exc.getRequestURI().getQuery();
        if (command == "search") {
            response = commandSearch();
        }
        exc.sendResponseHeaders(200, response.length());
        OutputStream os = exc.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    private String commandSearch() {
        System.out.println("- request command is: search");
        String request;

        if (checkBaseConnect() == false) {
            return "<h1>Error: database loading error</h1>";
        }
        if (checkQuery() == false) {
            return usersBase.toString();
        }
        request = parseQuery();
        if (request == null){
            return "<h1>Error: not correct query</h1>\n<h1>[]</h1>";
        }
        return searchAndResponse(request);
    }

    private boolean checkBaseConnect() {
        System.out.print("- check users_base connect: ");
        if (usersBase == null) {
            System.out.print("Error");
            return false;
        }
        System.out.println("Ok");
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
        System.out.print("- query: ");

        int index = queryStr.lastIndexOf("=");
        String param = queryStr.substring(0, index).toLowerCase();
        String value = queryStr.substring(++index).toLowerCase();
        System.out.print("\"" + param + "\":\"" + value + "\"");

        if (!param.equals("name")) {
            System.out.print("Error: not correct query");
            return null;
        }
        return value;
    }

    private String searchAndResponse(String value) {
        String result = "";
        String userName;

        for (User user: usersBase) {
            userName = user.getName().toLowerCase();
            if (userName.equals(value)) {
                System.out.print("\n- request found ");
                if (!result.equals("")) {
                    result += ",";
                }
                result += user.toString();
            }
        }
        return "[" + result +"]";
    }
}
