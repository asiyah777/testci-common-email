import org.junit.Test;
import javax.mail.internet.MimeMessage;
import java.util.*;
import static org.junit.Assert.*;

/*
 * This class holds the unit tests for the following method:
 * void     addHeader(String name, String value)
 */

public class EmailHeaderTest extends BaseEmailTest {
    @Test
    // Covers: basic usage, should not throw exception 
    public void testAddHeader() {
        email.addHeader("X-Header", "value");
    }

    @Test(expected = IllegalArgumentException.class)
    // Covers: null header name
    public void testAddHeader_NameNull() {
        email.addHeader(null, "value");
    }

    @Test(expected = IllegalArgumentException.class)
    // Covers: empty header name
    public void testAddHeader_NameEmpty() {
        email.addHeader("", "value");
    }

    @Test(expected = IllegalArgumentException.class)
    // Covers: null header value
    public void testAddHeader_ValueNull() {
        email.addHeader("name", null);
    }

    @Test(expected = IllegalArgumentException.class)
    // Covers: empty header value
    public void testAddHeader_ValueEmpty() {
        email.addHeader("name", "");
    }
}