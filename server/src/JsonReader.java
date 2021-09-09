package src;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonReader {
    public List<Person> getPersonList(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<Person> personList = new ArrayList<>();
        try {
            personList = Arrays.asList(mapper.readValue(json, Person[].class));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("--- Error: JsonReader ---");
        }
        return personList;
    }
}
