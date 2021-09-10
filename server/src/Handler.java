package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Handler implements HttpHandler {
    private String str = "<h1>Hello! This is \"java run personal task\" by ngonzo.</h1>";

    public Handler setMessage(String newMessage) { str = newMessage; return this; }



    @Override
    public void handle(HttpExchange exc) throws IOException {

        exc.sendResponseHeaders(200, str.length());
        OutputStream os = exc.getResponseBody();
        os.write(str.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
