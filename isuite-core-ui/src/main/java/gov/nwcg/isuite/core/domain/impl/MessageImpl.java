package gov.nwcg.isuite.core.domain.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.Message;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_MESSAGE", sequenceName="SEQ_MESSAGE")
@Table(name = "isw_message")
public class MessageImpl extends PersistableImpl implements Message {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_MESSAGE")
	private Long id = 0L;
	
	@Column(name = "TYPE", length = 20)
	private String type;
	
	@Column(name = "EFFECTIVE_DATE", length = 29)
	private Date effectiveDate;
	
	@Column(name = "EXPIRE_DATE", length = 29)
	private Date expireDate;
	
	@Column(name = "STATUS", length = 20)
	private String status;
	
	@Lob
	@Column(name = "MESSAGE_TEXT")
	private String messageText;
	
	
	public MessageImpl() {
	}
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the effictiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @return the expireDate
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the messageText
	 */
	public String getMessageText() {
		return messageText;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @param messageText the messageText to set
	 */
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

}
