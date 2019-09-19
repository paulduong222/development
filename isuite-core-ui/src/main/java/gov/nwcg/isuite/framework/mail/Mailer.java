package gov.nwcg.isuite.framework.mail;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

public class Mailer {
	private static final String NOTICE="\n\n\n\nThis is a notification message only.  Please do not reply.";
	private static final String DISCLAIMER="\n\nThis email and any files transmitted with it are confidential and intended solely for the use of the individual or entity to whom they are addressed. If you have received this email in error please notify the system manager. This message contains confidential information and is intended only for the individual named. If you are not the named addressee you should not disseminate, distribute or copy this e-mail. Please notify the sender immediately by e-mail if you have received this e-mail by mistake and delete this e-mail from your system. If you are not the intended recipient you are notified that disclosing, copying, distributing or taking any action in reliance on the contents of this information is strictly prohibited.";
	
	public static void sendEmail(MailProperties props) throws Exception {
		try{

			if(validateProperties(props)){
				MultiPartEmail email = new MultiPartEmail();
				email.setHostName(props.getHostname());

				for(MailProperties.MailAddress mailTo : props.getMailToList()){
					email.addTo(mailTo.emailAddress, mailTo.firstName + " " + mailTo.lastName);
				}
				email.setFrom(props.getMailFrom().emailAddress, 
						props.getMailFrom().firstName + " " + props.getMailFrom().lastName);

				email.setSubject(props.getSubject());
				email.setMsg(props.getMessage() + NOTICE + DISCLAIMER);
				
				for(EmailAttachment attach : props.getAttachments()){
					email.attach(attach);
				}

				
				// send the email
				email.send();

			}

		}catch(Exception e){
			throw e;
		}
	}

	private static Boolean validateProperties(MailProperties props) throws Exception {
		return true;
	}
}
