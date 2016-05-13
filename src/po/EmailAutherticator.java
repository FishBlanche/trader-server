package po;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailAutherticator extends Authenticator {
	 private String username;
	    private String password;

	    public EmailAutherticator() {
	        super();
	    }

	    public EmailAutherticator(String user, String pwd) {
	        super();
	        username = user;
	        password = pwd;
	    }

	    @Override
	    public PasswordAuthentication getPasswordAuthentication() {
	        return new PasswordAuthentication(username, password);
	    }
}
