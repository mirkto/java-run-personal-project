import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Test;
import ru.ngonzo.MyServer;

import java.io.IOException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class MyServerTests {
    static final int MAX_PORT = MyServer.MAX_PORT;
    static final int MIN_PORT = MyServer.MIN_PORT;
    static final int RANGE = MAX_PORT - MIN_PORT + 1;
    static final String USER_BASE_PATH =
            "src/test/resources/__files/testMyServer/userBase.json";

    int port;
    MyServer sutMyServer;
    OkHttpClient sutClient;
    String testUrl;
    String responseString;
    String answer;

    private String stripTags(String text) {
        if (!text.matches("<.+?>.+</.+?>")) {
            return text;
        }
        int first = text.indexOf(">");
        int last = text.lastIndexOf("<");
        return text.substring(first + 1, last);
    }

    private void buildRequestUrl(String str) {
        testUrl = "http://localhost:" + port + str;
    }

    private void buildResponse() throws IOException {
        Request request = new Request.Builder().url(testUrl).build();
        Response response = sutClient.newCall(request).execute();
        responseString = stripTags(response.body().string());
    }

    @Before
    public void setUp() throws IOException {
        port = new Random().nextInt(RANGE) + MIN_PORT;
        sutMyServer = new MyServer(port,USER_BASE_PATH);
        sutClient = new OkHttpClient();
    }

    @Test
    public void shouldReturnFriendsList() throws IOException {
        buildRequestUrl("/my/friends/search");
        buildResponse();

        answer = "[{\"id\":\"01\",\"name\":\"Ivan\"},{\"id\":\"03\",\"name\":\"Dasha\"}," +
                    "{\"id\":\"04\",\"name\":\"Pavel\"},{\"id\":\"05\",\"name\":\"IvAn\"}]";
        assertThat(responseString).isEqualTo(answer);
    }

    @Test
    public void shouldSearchFriends() throws IOException {
        buildRequestUrl("/my/friends/search?name=ivan");
        buildResponse();

        answer = "[{\"id\":\"01\",\"name\":\"Ivan\"},{\"id\":\"05\",\"name\":\"IvAn\"}]";
        assertThat(responseString).isEqualTo(answer);
    }

    @Test
    public void shouldSearchFriendsByNameAndId() throws IOException {
        buildRequestUrl("/my/friends/search?name=pavel&id=03&id=04&name=dasha");
        buildResponse();

        answer = "[{\"id\":\"04\",\"name\":\"Pavel\"},{\"id\":\"03\",\"name\":\"Dasha\"}]";
        assertThat(responseString).isEqualTo(answer);
    }

    @Test
    public void shouldSearchFriendsWrongInput() throws IOException {
        buildRequestUrl("/my/friends/search?name=ivan&id=02=02");
        buildResponse();

        assertThat(responseString).isEqualTo("[]");
    }

    @Test
    public void shouldSearchFriendsNotOnTheFriendsList() throws IOException {
        buildRequestUrl("/my/friends/search?name=vadim&id=999");
        buildResponse();

        assertThat(responseString).isEqualTo("[]");
    }
}