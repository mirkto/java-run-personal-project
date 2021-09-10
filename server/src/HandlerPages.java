package src;

import java.io.IOException;
import java.util.List;

public class HandlerPages {

    public static Handler DefaultPage() {
        return new Handler();
    }

    public static Handler MyPageHandler() {
        return new Handler().setMessage("<h1>My Account page</h1>");
    }

    public static Handler FriendsListHandler(List<User> usersBase) throws IOException {
        String response = "--- Users base:\n" + usersBase + "\n";
        return new Handler().setMessage(response);
    }

    public static Handler FriendsSearchHandler(List<User> usersBase) {
        Handler response = new Handler().setMessage("<h1>Friends Search page</1>");
        response.setUserBase(usersBase);
        response.setCommand("search");
        return response;
    }

}
