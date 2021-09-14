package src;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class UserBaseLoader {
    public static List<User> loadBase() throws IOException {
        String fileName = "server/src/data/userBase.json";
        String jsonFile = new String(Files.readAllBytes(Paths.get(fileName)));

        ObjectMapper mapper = new ObjectMapper();
        List<User> objectList = Arrays.asList(mapper.readValue(jsonFile, User[].class));

        checkBase(objectList);
        return objectList;
    }

    static void checkBase(List<User> userList) {
        System.out.print("--- checking the UserBase:");

        if (userList == null) {
            System.out.println(" EMPTY base");
            return ;
        }

        for (User user : userList) {
            if (user == null) {
                System.out.println(" EMPTY user");
                return ;
            }
        }
        System.out.println(" OK ---");
    }
}
