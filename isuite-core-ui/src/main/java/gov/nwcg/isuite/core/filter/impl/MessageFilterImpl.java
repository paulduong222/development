package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.MessageFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.types.MessageCauseEnum;
import gov.nwcg.isuite.framework.types.MessageSeverityEnum;

import java.util.Date;


/**
 * @author bsteiner
 * 
 */
@SuppressWarnings("serial")
public class MessageFilterImpl extends FilterImpl implements MessageFilter {

	private MessageCauseEnum cause;
	private Date startDate = null;
	private Date endDate = null;
	private MessageSeverityEnum severity;
	private String title;
    private Boolean active = null;
    private Boolean global = null;

	/**
	 * 
	 */
	public MessageFilterImpl() {
		reset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFilter#getCause()
	 */
	public MessageCauseEnum getCause() {
		return this.cause;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFilter#getEndDate()
	 */
	public Date getEndDate() {
		return this.endDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFilter#getSeverity()
	 */
	public MessageSeverityEnum getSeverity() {
		return this.severity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFilter#getStartDate()
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFilter#getTitle()
	 */
	public String getTitle() {
		return this.title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFilter#setCause(gov.nwcg.isuite.domain.admin.MessageCauseEnum)
	 */
	public void setCause(MessageCauseEnum cause) {
		this.cause = cause;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFilter#setDateRange(java.util.Date,
	 *      java.util.Date)
	 */
	public void setDateRange(Date startDate, Date endDate) {
		setStartDate(startDate);
		setEndDate(endDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFilter#setSeverity(gov.nwcg.isuite.domain.admin.MessageSeverityEnum)
	 */
	public void setSeverity(MessageSeverityEnum severity) {
		this.severity = severity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFilter#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFilter#setTodayOnly()
	 */
	public void setTodayOnly() {
		Date today = new Date();
		this.startDate = today;
		this.endDate = today;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFilter#setEndDate()
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFilter#setStartDate()
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFilter#reset()
	 */
	public /*final*/ void reset() {
		this.cause = null;
//		this.setTodayOnly();
		this.severity = null;
		this.title = null;
      this.active = null;
      this.global = null;
	}

   public Boolean isActive() {
      return this.active;
   }

   public Boolean isGlobal() {
      return this.global;
   }

   public void setActive(Boolean isActive) {
      this.active = isActive;
   }

   public void setGlobal(Boolean isGlobal) throws Exception {
      this.global = isGlobal;
   }

}
