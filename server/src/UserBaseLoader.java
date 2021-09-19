package src;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class UserBaseLoader {
    private static final Logger LOGGER = Logger.getLogger(Handler.class.getName());

    public static List<User> loadBase() throws IOException {
        String fileName = "server/src/data/userBase.json";
        String jsonFile = new String(Files.readAllBytes(Paths.get(fileName)));

        ObjectMapper mapper = new ObjectMapper();
        List<User> objectList = Arrays.asList(mapper.readValue(jsonFile, User[].class));

        checkBase(objectList);
        return objectList;
    }

    static void checkBase(List<User> userList) {
        LOGGER.info("--- check UserBase");
        if (userList.isEmpty()) {
            LOGGER.warning("- empty base");
        }
        for (User user : userList) {
            if (user.getId() == null ||
                    user.getName() == null) {
                LOGGER.warning("- found empty user ");
            }
            System.out.println(user);
        }
    }
}