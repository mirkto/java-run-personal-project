import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Test;
import ru.ngonzo.MyServer;

import java.io.IOException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class unitTests {
    int port;
    MyServer sutMyServer;
    OkHttpClient sutClient;
    static final int MAX_PORT = 65535;
    static final int MIN_PORT = 49152;
    static final int RANGE = MAX_PORT - MIN_PORT + 1;

    private String StripTags(String text) {
        if (!text.matches("<.+?>.+</.+?>")) {
            return text;
        }
        int first = text.indexOf(">");
        int last = text.lastIndexOf("<");
        return text.substring(first + 1, last);
    }

    @Before
    public void setUp() throws IOException {
        sutMyServer = new MyServer();
        port = new Random().nextInt(RANGE) + MIN_PORT;
        sutMyServer.usePort(port);
        sutClient = new OkHttpClient();
    }

    @Test
    public void shouldReturnFriendsList() throws IOException {
        String url = "http://localhost:" + port + "/my/friends/search";
        Request testRequest = new Request.Builder().url(url).build();
        Response testResponse = sutClient.newCall(testRequest).execute();
        String responseString = StripTags(testResponse.body().string());
        assertThat(answers[0]).isEqualTo(responseString);
    }

    @Test
    public void shouldSearchFriends() throws IOException {
        String url = "http://localhost:" + port + "/my/friends/search?name=ivan";
        Request testRequest = new Request.Builder().url(url).build();
        Response testResponse = sutClient.newCall(testRequest).execute();
        String responseString = StripTags(testResponse.body().string());
        assertThat(answers[1]).isEqualTo(responseString);
    }

    @Test
    public void shouldSearchFriendsMultiArgs() throws IOException {
        String url = "http://localhost:" + port + "/my/friends/search?name=pavel&id=03&id=04&name=dasha";
        Request testRequest = new Request.Builder().url(url).build();
        Response testResponse = sutClient.newCall(testRequest).execute();
        String responseString = StripTags(testResponse.body().string());
        assertThat(answers[2]).isEqualTo(responseString);
    }

    @Test
    public void shouldSearchFriendsWrongInput() throws IOException {
        String url = "http://localhost:" + port + "/my/friends/search?name=ivan&id=02=02";
        Request testRequest = new Request.Builder().url(url).build();
        Response testResponse = sutClient.newCall(testRequest).execute();
        String responseString = StripTags(testResponse.body().string());
        assertThat(answers[3]).isEqualTo(responseString);
    }

    @Test
    public void shouldSearchFriendsNotInFriendsList() throws IOException {
        String url = "http://localhost:" + port + "/my/friends/search?name=vadim&id=999";
        Request testRequest = new Request.Builder().url(url).build();
        Response testResponse = sutClient.newCall(testRequest).execute();
        String responseString = StripTags(testResponse.body().string());
        assertThat(answers[4]).isEqualTo(responseString);
    }

    String[] answers = new String[] {
            "[{\"id\":\"01\",\"name\":\"Ivan\"},{\"id\":\"02\",\"name\":\"Sasha\"}," +
                    "{\"id\":\"03\",\"name\":\"Dasha\"},{\"id\":\"04\",\"name\":\"Pavel\"}," +
                    "{\"id\":\"05\",\"name\":\"IvAn\"}]",
            "[{\"id\":\"01\",\"name\":\"Ivan\"},{\"id\":\"05\",\"name\":\"IvAn\"}]",
            "[{\"id\":\"04\",\"name\":\"Pavel\"},{\"id\":\"03\",\"name\":\"Dasha\"}]",
            "[]",
            "[]"
    };

}