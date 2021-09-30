import org.junit.Before;
import org.junit.Test;
import ru.ngonzo.MyServer;

import java.io.IOException;

public class unitTests {
    MyServer sutMyServer;

    private int getRandomPort() {
        return (int) (Math.random() * 65535 + 1);
    }

    @Before
    public void setUp() throws IOException {
        sutMyServer = new MyServer(getRandomPort());
    }

    @Test
    public void shouldStartServer() {

    }


}
