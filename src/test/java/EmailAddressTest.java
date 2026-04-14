import org.junit.Test;
import javax.mail.internet.InternetAddress;
import java.util.*;
import static org.junit.Assert.*;
import org.apache.commons.mail.EmailException;

/*
 * This class holds the unit tests for the following methods:
 * Email   addBcc(String... emails)
 * Email   addCc(String email)
 * Email   addReplyTo(String email, String name)
 */

public class EmailAddressTest extends BaseEmailTest {
	
// ------------------------- Email addBcc(String... emails) -------------------------
	@Test
    // Covers: valid BCC list 
    public void testAddBcc() throws Exception {
        List<InternetAddress> expected = new ArrayList<>();
        for (String s : VALID_EMAILS) {
            expected.add(new InternetAddress(s));
        }

        email.addBcc(VALID_EMAILS);

        assertEquals(expected.toString(), email.getBccAddresses().toString());
    }

    @Test(expected = EmailException.class)
    // Covers: null array 
    public void testAddBcc_NullArray() throws Exception {
        email.addBcc((String[]) null);
    }

    @Test(expected = EmailException.class)
    // Covers: empty array 
    public void testAddBcc_EmptyArray() throws Exception {
        email.addBcc(new String[0]);
    }


// ------------------------- Email addCc(String email) -------------------------
    @Test
    // Covers: single CC 
    public void testAddCc() throws Exception {
        email.addCc("test@example.com");
        assertEquals("test@example.com", email.getCcAddresses().get(0).getAddress());
    }

    @Test
    // Covers: multiple CC 
    public void testAddCc_Multiple() throws Exception {
        for (String s : VALID_EMAILS) {
            email.addCc(s);
        }
        assertEquals(3, email.getCcAddresses().size());
    }

    @Test(expected = EmailException.class)
    // Covers: invalid CC format
    public void testAddCc_Invalid() throws Exception {
        email.addCc("bad-email");
    }

    @Test(expected = EmailException.class)
    // Covers: null CC
    public void testAddCc_Null() throws Exception {
        email.addCc((String[]) null);
    }

  
// ------------------------- Email addReplyTo(String email, String name) -------------------------
    @Test
    // Covers: reply-to with names and null/empty handling
    public void testAddReplyTo() throws Exception {
        String[] names = {"Name1", "", null};

        for (int i = 0; i < VALID_EMAILS.length; i++) {
            email.addReplyTo(VALID_EMAILS[i], names[i]);
        }

        assertEquals(3, email.getReplyToAddresses().size());
    }

    @Test(expected = EmailException.class)
    // Covers: invalid reply-to email
    public void testAddReplyTo_Invalid() throws Exception {
        email.addReplyTo("bad-email", "Name");
    }

}