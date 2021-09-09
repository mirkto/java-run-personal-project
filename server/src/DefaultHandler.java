package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class DefaultHandler implements HttpHandler {
    private String str = "<h1>Hello! This is \"java run personal task\" by ngonzo.</h1>";

    public DefaultHandler setMessage(String newMessage) { str = newMessage; return this; }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        byte[] bytes = str.getBytes();
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
