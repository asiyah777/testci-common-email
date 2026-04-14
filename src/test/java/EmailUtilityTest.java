import org.junit.Test;
import javax.mail.Session;
import java.util.Date;
import java.util.Properties;
import static org.junit.Assert.*;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/*
 * This class holds the unit tests for the following methods:
 * String  getHostName()
 * Session getMailSession()
 * Date    getSentDate()
 * int     getSocketConnectionTimeout()
 * Email   setFrom(String email)
 */

public class EmailUtilityTest extends BaseEmailTest {

// ------------------------- String  getHostName() -------------------------
    @Test
    // Covers: basic usage
    public void testGetHostName() {
        email.setHostName("local.com");
        assertEquals("local.com", email.getHostName());
    }
 
    // Covers: no host name and no session
    @Test
    public void testGetHostName_noHostSet() {
        SimpleEmail blankEmail = new SimpleEmail();
        assertNull(blankEmail.getHostName());
    }
 
    @Test
    // Covers: hostname (set directly) from field 
    public void testGetHostName_FromField() {
        email.setHostName("local.com");
        assertEquals("local.com", email.getHostName());
    }
    
 // ------------------------- Session getMailSession() -------------------------   
    @Test(expected = EmailException.class)
    // Covers: missing hostname error
    public void testGetMailSession_NoHostError() throws Exception {
        email.setHostName(null);
        System.getProperties().remove("mail.smtp.host");

        email.getMailSession();
    }

    @Test
    // Covers: SSL and properties setup (tests if settings are correctly applied)
    public void testGetMailSession_SSL() throws Exception {
        email.setHostName("smtp.com");
        email.setSSLOnConnect(true);
        email.setSslSmtpPort("465");

        Session s = email.getMailSession();
        assertEquals("465", s.getProperties().getProperty("mail.smtp.port"));
    }

    
// ------------------------- Date getSentDate() -------------------------
    @Test
    // Covers: manually set sent date
    public void testGetSentDate() {
        Date now = new Date();
        email.setSentDate(now);

        assertEquals(now, email.getSentDate());
    }


    // Covers: default sent date (no date provided)
    @Test
    public void testGetSentDate_Default() {
        email.setSentDate(null);
        long before = System.currentTimeMillis();
        Date result = email.getSentDate();
        assertTrue(result.getTime() >= before - 1000);
        assertTrue(result.getTime() <= System.currentTimeMillis());
    }

    
// ------------------------- int getSocketConnectionTimeout() -------------------------
    @Test
    // Covers: basic usage
    public void testSocketTimeout() {
        email.setSocketConnectionTimeout(5000);
        assertEquals(5000, email.getSocketConnectionTimeout());
    }

    @Test
    // Covers: default value
    public void testSocketTimeout_default() {
        assertEquals(60000, email.getSocketConnectionTimeout());
    }
// ------------------------- Email setFrom(String email) -------------------------   
    @Test
    // Covers: basic usage
    public void testSetFrom() throws Exception {
        email.setFrom("person1@example.com");
        assertEquals("person1@example.com", email.getFromAddress().getAddress());
    }

    @Test(expected = EmailException.class)
    // Covers: invalid email format
    public void testSetFrom_Invalid() throws Exception {
        email.setFrom("bad-email");
    }

    @Test
    // Covers: overwrite case
    public void testSetFrom_Overwrite() throws Exception {
        email.setFrom("first@example.com");
        email.setFrom("second@example.com");
        assertEquals("second@example.com", email.getFromAddress().getAddress());
    }
}