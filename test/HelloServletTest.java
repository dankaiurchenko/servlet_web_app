import org.junit.Test;

import static org.junit.Assert.*;

public class HelloServletTest {

    @Test
    public void doPost() {
        assertTrue(true);
    }

    @Test
    public void doGet() {
        assertFalse(false);
    }
}