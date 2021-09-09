package src;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
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

        String json = "[\n" +
                "    {\n" +
                "       \"id\":\"01\",\n" +
                "       \"name\":\"Ivan\"\n" +
                "   },\n" +
                "    {\n" +
                "       \"id\":\"02\",\n" +
                "       \"name\":\"Dasha\"\n" +
                "   }\n" +
                "]";
//        FileReader jsonFile = new FileReader("server/src/data/userBaseAlter.json");

        List<Person> personList = reader.getPersonList(json);
        for (Person person : personList) {
            System.out.println(person);
        }

//        src.Person user = new src.Person("0", "Anonim");
//        String str = new ObjectMapper().writeValueAsString(user);
        String str = "--- Users base:\n" + personList.toString() + "\n";

        return new DefaultHandler().setMessage(str);
    }
}
