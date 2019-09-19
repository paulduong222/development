package gov.nwcg.isuite.framework.mail;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.mail.EmailAttachment;

public class MailProperties {
	private String hostname;
	private String subject;
	private String message;
	private Collection<EmailAttachment> attachments= new ArrayList<EmailAttachment>();
	private Collection<MailAddress> mailToList = new ArrayList<MailAddress>();
	private MailAddress mailFrom;

	public MailProperties(){
		
	}
	
	/**
	 * Returns the hostname.
	 *
	 * @return 
	 *		the hostname to return
	 */
	public String getHostname() {
		return hostname;
	}



	/**
	 * Sets the hostname.
	 *
	 * @param hostname 
	 *			the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}



	/**
	 * Returns the subject.
	 *
	 * @return 
	 *		the subject to return
	 */
	public String getSubject() {
		return subject;
	}



	/**
	 * Sets the subject.
	 *
	 * @param subject 
	 *			the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}



	/**
	 * Returns the message.
	 *
	 * @return 
	 *		the message to return
	 */
	public String getMessage() {
		return message;
	}



	/**
	 * Sets the message.
	 *
	 * @param message 
	 *			the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}



	/**
	 * Returns the attachments.
	 *
	 * @return 
	 *		the attachments to return
	 */
	public Collection<EmailAttachment> getAttachments() {
		return attachments;
	}


	/**
	 * Sets the attachments.
	 *
	 * @param attachments 
	 *			the attachments to set
	 */
	public void setAttachments(Collection<EmailAttachment> attachments) {
		this.attachments = attachments;
	}

	public void addAttachment(EmailAttachment attachment){
		this.attachments.add(attachment);
	}

	/**
	 * Returns the mailToList.
	 *
	 * @return 
	 *		the mailToList to return
	 */
	public Collection<MailAddress> getMailToList() {
		return mailToList;
	}



	/**
	 * Sets the mailToList.
	 *
	 * @param mailToList 
	 *			the mailToList to set
	 */
	public void setMailToList(Collection<MailAddress> mailToList) {
		this.mailToList = mailToList;
	}



	/**
	 * Returns the mailFrom.
	 *
	 * @return 
	 *		the mailFrom to return
	 */
	public MailAddress getMailFrom() {
		return mailFrom;
	}

	public MailAddress getMailAddressInstance() {
		return new MailAddress();
	}

	/**
	 * Sets the mailFrom.
	 *
	 * @param mailFrom 
	 *			the mailFrom to set
	 */
	public void setMailFrom(MailAddress mailFrom) {
		this.mailFrom = mailFrom;
	}
	
	public class MailAddress {
		public String emailAddress="";
		public String firstName="";
		public String lastName="";
		
		public MailAddress(){
		}
	}




}
