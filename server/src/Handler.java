package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

public class Handler implements HttpHandler {
    private List<User> usersBase;
    private String queryStr = null;
    private String command = "default";
    private String response = "<h1>This is \"java run personal task\" by ngonzo.</h1>";

    public void setCommand(String command) { this.command = command; }
    public void setUserBase(List<User> newBase) { this.usersBase = newBase; }
    public Handler setMessage(String newMessage) { response = newMessage; return this; }

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

        System.out.print("- check users_base connect: ");
        if (usersBase == null) {
            System.out.print("Error");
            return "<h1>Error: database loading error</h1>";
        }
        System.out.println("Ok");

        if (queryStr == null) {
            System.out.print("- no query: return user_base");
            response = usersBase.toString();
            return response;
        }
        System.out.print("- query: ");
        int index = queryStr.lastIndexOf("=");
        String key = queryStr.substring(0, index).toLowerCase();
        String value = queryStr.substring(++index).toLowerCase();
        System.out.println("\"" + key + "\":\"" + value + "\"");

        if (!key.equals("name")){
            System.out.print("Error: not correct query");
            return "<h1>Error: not correct query</h1>\n<h1>[]</h1>";
        }
        String result = "";
        for (User user: usersBase) {
            if (user.getName().toLowerCase().equals(value)) {
                System.out.println("- request found ");
                if (!result.equals("")) {
                    result += ",";
                }
                result += user.toString();
            }
        }
        return "[" + result +"]";
    }
}
