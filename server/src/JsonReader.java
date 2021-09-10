package src;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonReader {
    public List<User> getPersonList(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<User> objectList = new ArrayList<>();
        try {
            objectList = Arrays.asList(mapper.readValue(json, User[].class));
            checkReader(objectList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("--- Error: JsonReader ---");
        }
        return objectList;
    }

    private static void checkReader(List<User> userList) {
        System.out.print("--- checking the JsonReader:");
        for (User user : userList) {
            //System.out.print(user);
        }
        System.out.println(" OK ---");
    }
}
