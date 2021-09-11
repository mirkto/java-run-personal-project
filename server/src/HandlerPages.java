package src;

import java.io.IOException;
import java.util.List;

public class HandlerPages {

    public static Handler DefaultPage() {
        return new Handler();
    }

    public static Handler MyPageHandler() {
        return new Handler().setResponse("<h1>My Account page</h1>");
    }

    public static Handler FriendsListHandler(List<User> usersBase) throws IOException {
        return new Handler().setResponse("<h1>" + usersBase.toString() + "</h1>");
    }

    public static Handler FriendsSearchHandler(List<User> usersBase) {
        Handler response = new Handler();
        response.setUserBase(usersBase);
        response.setCommand("search");
        return response;
    }

}
