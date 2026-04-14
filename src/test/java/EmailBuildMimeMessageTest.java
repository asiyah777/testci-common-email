import org.junit.Test;
import static org.junit.Assert.*;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.Message;

import org.apache.commons.mail.EmailConstants;
import org.apache.commons.mail.EmailException;

/*
 * This class holds the unit tests for the following method:
 * void     buildMimeMessage()
 */

public class EmailBuildMimeMessageTest extends BaseEmailTest {

	// Covers: if (this.message != null) -> throw IllegalStateException
    @Test(expected = IllegalStateException.class)
    public void testBuildMimeMessage_AlreadyBuilt() throws Exception {
        email.setHostName("localhost");
        email.setFrom("me@test.com");
        email.addTo("you@test.com");
        email.buildMimeMessage(); 
        email.buildMimeMessage(); 
    }

    // Covers: if (EmailUtils.isNotEmpty(this.subject)) 
    // AND: if (EmailUtils.isNotEmpty(this.charset)) -> this.message.setSubject(this.subject, this.charset)
    // AND: if (this.content != null) 
    // AND: if (this.toList.size() > 0)
    @Test
    public void testBuildMimeMessage_StandardWithCharset() throws Exception {
        email.setHostName("localhost");
        email.setFrom("me@test.com");
        email.addTo("you@test.com");
        email.setSubject("Test Subject");
        email.setCharset("UTF-8");
        email.setContent("Hello", EmailConstants.TEXT_PLAIN);

        email.buildMimeMessage();
        
        assertNotNull(email.getMimeMessage());
        assertEquals("Test Subject", email.getMimeMessage().getSubject());
    }

    // Covers: else { this.message.setSubject(this.subject); } (Subject without charset)
    @Test
    public void testBuildMimeMessage_SubjectNoCharset() throws Exception {
        email.setHostName("localhost");
        email.setFrom("me@test.com");
        email.addTo("you@test.com");
        email.setSubject("Subject");

        email.buildMimeMessage();
        assertEquals("Subject", email.getMimeMessage().getSubject());
    }
    
    // Covers: else { this.message.setContent(this.content, this.contentType); } (Non-text/plain content)
    @Test
    public void testBuildMimeMessage_HtmlContent() throws Exception {
        email.setHostName("localhost");
        email.setFrom("me@test.com");
        email.addTo("you@test.com");
        email.setContent("<html><body>Hello World!</body></html>", "text/html");

        email.buildMimeMessage();
        
        assertTrue(email.getMimeMessage().getContent().toString().contains("Hello World!"));
    }

    // Covers: else { this.message.setText(""); } (No content provided)
    @Test
    public void testBuildMimeMessage_NoContent() throws Exception {
        email.setHostName("localhost");
        email.setFrom("me@test.com");
        email.addTo("you@test.com");

        email.buildMimeMessage();
        assertEquals("", email.getMimeMessage().getContent());
    }
    

    // Covers: else { if (session.getProperty(MAIL_SMTP_FROM) == null) -> throw EmailException }
    @Test(expected = EmailException.class)
    public void testBuildMimeMessage_NoFromError() throws Exception {
        email.setHostName("localhost");
        email.addTo("you@test.com");

        email.buildMimeMessage();
    }

    // Covers: if (this.toList.size() + this.ccList.size() + this.bccList.size() == 0) -> throw EmailException
    @Test(expected = EmailException.class)
    public void testBuildMimeMessage_NoRecipientsError() throws Exception {
        email.setHostName("localhost");
        email.setFrom("me@test.com");

        email.buildMimeMessage();
    }

    // Covers: if (this.ccList.size() > 0), if (this.bccList.size() > 0), if (this.replyList.size() > 0)
    // AND: if (this.headers.size() > 0) -> header folding loop
    @Test
    public void testBuildMimeMessage_AllListsAndHeaders() throws Exception {
        email.setHostName("localhost");
        email.setFrom("me@test.com");
        email.addTo("to@test.com");
        email.addCc("cc@test.com");
        email.addBcc("bcc@test.com");
        email.addReplyTo("reply@test.com");
        email.addHeader("X-Custom", "CustomValue");

        email.buildMimeMessage();
        
        MimeMessage msg = email.getMimeMessage();
        assertEquals(1, msg.getRecipients(Message.RecipientType.CC).length);
        assertEquals(1, msg.getRecipients(Message.RecipientType.BCC).length);
        assertEquals("CustomValue", msg.getHeader("X-Custom")[0]);
    }

    // Covers: if (this.popBeforeSmtp)
    @Test
    public void testBuildMimeMessage_PopBeforeSmtp() throws Exception {
        email.setHostName("localhost");
        email.setFrom("me@test.com");
        email.addTo("you@test.com");
        email.setPopBeforeSmtp(true, "pop.test.com", "user", "pass");

        try {
            email.buildMimeMessage();
        } catch (Exception e) {
        }
    }
    
}