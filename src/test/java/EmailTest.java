import org.apache.commons.mail.SimpleEmail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {
	private SimpleEmail email;

    @Before
    public void setUp() {
        email = new SimpleEmail();
    }

    @After
    public void tearDown() {
        email = null;
    }

    @Test
    public void testAddCc() throws Exception {
    	
    }
	
	/*
	 * Email   addBcc(String... emails)

Email   addCc(String email)

void     addHeader(String name, String value)

Email   addReplyTo(String email, String name)

void     buildMimeMessage()

String  getHostName()

Session getMailSession()

Date    getSentDate()

int        getSocketConnectionTimeout()

Email   setFrom(String email)
	 * 
	 * */
}

