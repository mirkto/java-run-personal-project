package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class UserBaseLoader {
    public static List<User> loadBase() throws IOException {
    JsonReader reader = new JsonReader();
    String fileName = "server/src/data/userBase.json";

    String jsonFile = new String(Files.readAllBytes(Paths.get(fileName)));
    List<User> personList = reader.getPersonList(jsonFile);

    checkBaseLoad(personList);

    return personList;
    }

    private static void checkBaseLoad(List<User> userList) {
        System.out.print("--- checking the user_base load: ");
        for (User user : userList) {
//            System.out.print(user);
        }
        System.out.println("Ok ---");
    }
}
