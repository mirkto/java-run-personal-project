package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

public class Handler implements HttpHandler {
    private String response = "<h1>Hello! This is \"java run personal task\" by ngonzo.</h1>";
    public Handler setMessage(String newMessage) { response = newMessage; return this; }

    String command = "default";
    public void setCommand(String command) { this.command = command; }

    List<User> usersBase;
    public void setUserBase(List<User> newBase) { this.usersBase = newBase; }

    private String  queryStr;
    public String   getQueryStr() { return queryStr; }

    @Override
    public void handle(HttpExchange exc) throws IOException {
        queryStr = exc.getRequestURI().getQuery();

        if (usersBase != null) {
            System.out.println("--- users_base connect ");
        }
        if (queryStr != null) {
            System.out.print("---query in handler: ");
            System.out.println(queryStr);
            String  request;
            int index = queryStr.lastIndexOf("=");
            request = queryStr.substring(++index).toLowerCase();
            if (command == "search") {
                for (User user: usersBase) {
                    if (user.getName().toLowerCase().equals(request)) {
                        System.out.print("--- found: ");
                        System.out.println(request);
                    }
                }
            }
        }

        exc.sendResponseHeaders(200, response.length());
        OutputStream os = exc.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
