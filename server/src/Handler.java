package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Handler {

    public static DefaultHandler DefaultPage() {
        return new DefaultHandler();
    }

    public static DefaultHandler MyPageHandler() {
        return new DefaultHandler().setMessage("<h1>My Account page</h1>");
    }

    public static DefaultHandler FriendsSearchHandler() {
        return new DefaultHandler().setMessage("<h1>Friends Search page</1>");
    }

    public static DefaultHandler FriendsListHandler() throws IOException {
        JsonReader reader = new JsonReader();
        String fileName = "server/src/data/userBaseAlter.json";

        String jsonFile = new String(Files.readAllBytes(Paths.get(fileName)));
        List<Person> personList = reader.getPersonList(jsonFile);

        checkBaseLoad(personList);

        String str = "--- Users base:\n" + personList + "\n";
        return new DefaultHandler().setMessage(str);
    }

    private static void checkBaseLoad(List<Person> personList) {
        System.out.print("--- checking the user_base load:");
        for (Person person : personList) {
            System.out.print(person);
        }
        System.out.println("\n--- end checking");
    }
}
