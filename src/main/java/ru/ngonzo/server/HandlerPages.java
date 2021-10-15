package ru.ngonzo.server;

import java.util.List;

public class HandlerPages {

    public static Handler DefaultPage() {
        return new Handler().setResponse("<h1>This is \"personal project\" by ru.ngonzo.</h1>");
    }

    public static Handler MyPageHandler() {
        return new Handler().setResponse("<h1>My Account page</h1>");
    }

    public static Handler FriendsListHandler(List<User> usersBase) {
        return new Handler().setResponse("<h1>" + usersBase.toString().replace(", {", ",{") + "</h1>");
    }

    public static Handler FriendsSearchHandler(List<User> usersBase) {
        Handler response = new Handler();
        response.setUserBase(usersBase);
        response.setCommand("search");
        return response;
    }
}