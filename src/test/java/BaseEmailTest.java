import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Before;
import org.junit.After;

public abstract class BaseEmailTest {

    protected Email email;

    protected static final String[] VALID_EMAILS = {
        "person1@example.com",
        "employee@company.com",
        "johndoe@business.org"
    };

    @Before
    public void setUp() {
        email = new SimpleEmail();   // using SimpleEmail bc/ Email is abstract class 
        email.setHostName("localhost");
    }

    @After
    public void tearDown() {
        email = null;
    }
}