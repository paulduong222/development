/**
 * Used to define which messages are to be retrieved.
 */
package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.types.MessageCauseEnum;
import gov.nwcg.isuite.framework.types.MessageSeverityEnum;

import java.util.Date;

/**
 * Used to define which messages are to be retrieved.
 * <p>
 * See the general design notes for specifics on filter operations.
 * </p>
 * <p>
 * The default for this filter is to select all messages that were created on
 * the same day the the filter was created.
 * </p>
 * 
 * @author dougAnderson
 */
public interface MessageFilter extends Filter {

	/**
	 * Accessor for the first date (inclusive) in the date range of the
	 * selection criteria.
	 * 
	 * @return start date of date range, may be null
	 * @see #setDateRange(Date, Date)
	 */
	public Date getStartDate();

	/**
	 * Accessor for the last date(inclusive) in the date range of the selection
	 * criteria.
	 * <p>
	 * if either StartDate EndDate null, no dates will be used in the selection
	 * critera
	 * </p>
	 * 
	 * @return end date date range, may be null
	 * @see #setDateRange(Date, Date)
	 */
	public Date getEndDate();

	/**
	 * Convenience method to set Start and End day to today
	 * 
	 * @see #setDateRange(Date, Date)
	 */
	public void setTodayOnly();

	/**
	 * Accessor to set the range of days for selection criteria.
	 * <p>
	 * This date range will only select messages created between the startDay
	 * and endDay (inclusive)
	 * </p>
	 * 
	 * @param startDate
	 * @param endDate
	 * @see #setTodayOnly()
	 * @see #getStartDate()
	 * @see #getEndDate()
	 * @see #setStartDate()
	 * @see #setEndDate()
	 */
	public void setDateRange(Date startDate, Date endDate);

	/**
	 * Accessor for message severity in selection criteria.
	 * <p>
	 * If null, then message severity is to be used in selection criteria.
	 * </p>
	 * 
	 * @return message severity in selection criteria, may be null
	 * @see #setSeverity(MessageSeverityEnum)
	 */
	public MessageSeverityEnum getSeverity();

	/**
	 * Accessor for message severity in selection criteria.
	 * <p>
	 * If null, then message severity is to be used in selection criteria.
	 * </p>
	 * 
	 * @param severity
	 *            message severity in selection criteria, may be null
	 * @see #getSeverity()
	 */
	public void setSeverity(MessageSeverityEnum severity);

	/**
	 * Accessor for cause of message in selection criteria.
	 * <p>
	 * if null, then message cause is not to be used in selection criteria.
	 * </p>
	 * 
	 * @return cause of message in selection criteria, may be null
	 * @see #setCause(MessageCauseEnum)
	 */
	public MessageCauseEnum getCause();

	/**
	 * Accessor for cause of message in selection criteria.
	 * <p>
	 * if null, then message cause is not to be used in selection criteria.
	 * </p>
	 * 
	 * @param cause
	 *            cause of message in selection criteria, may be null
	 * @see #getCause()
	 */
	public void setCause(MessageCauseEnum cause);
	
	/**
	 * Accessor for Message title
	 */
	public String getTitle();

	/**
	 * Accessor for message title in selection criteria.
	 * <p>
	 * If null, then message title is to be used in selection criteria.
	 * </p>
	 * 
	 * @param title
	 *            message title in selection criteria, may be null
	 * @see #getTitle()
	 */
	public void setTitle(String title);

	/**
	 * Accessor for the first date (inclusive) in the date range of the
	 * selection criteria.
	 * <p>
	 * if either StartDate EndDate null, no dates will be used in the selection
	 * critera
	 * </p>
	 * 
	 * @param startDate
	 *            sets start date of date range, may be null
	 * @see #getStartDate()
	 */
	public void setStartDate(Date startDate);

	/**
	 * Accessor for the last date(inclusive) in the date range of the selection
	 * criteria.
	 * <p>
	 * if either StartDate EndDate null, no dates will be used in the selection
	 * critera
	 * </p>
	 * 
	 * @param endDate
	 *            sets end date of date range, may be null
	 * @see #getEndDate()
	 */
	public void setEndDate(Date endDate);
   
   /**
    * 
    * @return the global status of the message
    */
   public Boolean isGlobal();
   
   /**
    * 
    * @param isGlobal set the global status
    */
   public void setGlobal(Boolean isGlobal) throws Exception;
   
   /**
    * 
    * @return the active status
    */
   public Boolean isActive();
   
   /**
    * 
    * @param isActive set teh active status
    */
   public void setActive(Boolean isActive);
	
}
