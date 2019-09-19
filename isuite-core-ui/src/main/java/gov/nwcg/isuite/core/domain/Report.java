package gov.nwcg.isuite.core.domain;

import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

/**
 * The interface definition for a Report.
 * 
 * @author aroundy
 *
 */
public interface Report extends Persistable {

	/**
	 * Return the name of the report.
	 * 
	 * @return
	 * 		the report name
	 */
	public String getReportName();
	
	/**
	 * Set the name of the report.
	 * 
	 * @param reportName
	 * 		the report name to set
	 */
	public void setReportName(String reportName);
	
	/**
	 * Return the date of the request.
	 * 
	 * @return
	 * 		the date of the request
	 */
	public Date getDateRequested();
	
	/**
	 * Set the date of the request.
	 * 
	 * @param dateRequested
	 * 		the date of the request to set
	 */
	public void setDateRequested(Date dateRequested);
	
	/**
	 * Return the date the report was generated.
	 * 
	 * @return
	 * 		the date that the report was generated
	 */
	public Date getDateGenerated();
	
	/**
	 * Set the date the report was generated.
	 * 
	 * @param dateGenerated
	 * 		the generation date to set
	 */
	public void setDateGenerated(Date dateGenerated);
	
	/**
	 * Return the name of the file.
	 * 
	 * @return
	 * 		the file name
	 */
	public String getFileName();
	
	/**
	 * Set the name of the file.
	 * 
	 * @param fileName
	 * 		the name of file to set
	 */
	public void setFileName(String fileName);
	
	/**
	 * Return the result code.
	 * 
	 * @return
	 * 		the result code
	 */
	public String getResultCode();
	
	/**
	 * Set the result code.
	 * 
	 * @param resultCode
	 * 		the result code to set
	 */
	public void setResultCode(String resultCode);
	
	/**
	 * Return the error description.
	 * 
	 * @return
	 * 		the error description
	 */
	public String getErrorDesc();
	
	/**
	 * Set the error description.
	 * 
	 * @param errorDesc
	 * 		the error description to set
	 */
	public void setErrorDesc(String errorDesc);
	
	/** 
	 * Return the User
	 * 
	 * @return
	 * 		the User
	 */
	public User getUser();
	
	/**
	 * Set the User
	 * 
	 * @param user
	 * 		the User to set
	 */
	public void setUser(User user);
	
	/** 
	 * Return the userId
	 * 
	 * @return
	 * 		the userId
	 */
	public Long getUserId();
	
	/**
	 * Set the UserId
	 * 
	 * @param userId
	 * 		the userId to set
	 */
	public void setUserId(Long userId);
	
	public void setIncidentId(Long incidentId);

	public Long getIncidentId();
	
	public Incident getIncident();

	public void setIncident(Incident incidentId);

	public Long getIncidentGroupId();

	public void setIncidentGroupId(Long incidentGroupId);

	public IncidentGroup getIncidentGroup();
	
	public void setIncidentGroup(IncidentGroup incidentGroupId);
	
	public Long getOriginalReportId();

	public void setOriginalReportId(Long originalReportId);
	
	public Report getOriginalReport();

	public void setOriginalReport(Report originalReport);
	
}
