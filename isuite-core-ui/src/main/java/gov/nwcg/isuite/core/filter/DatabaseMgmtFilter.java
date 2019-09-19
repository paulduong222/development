package gov.nwcg.isuite.core.filter;


import java.util.Date;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface DatabaseMgmtFilter extends Filter {

	public Long getId();
	
	public void setId(Long id);

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() ;

	/**
	 * @param databaseName the databaseName to set
	 */
	public void setDatabaseName(String databaseName) ;

	/**
	 * @return the crypticDateFilterCode
	 */
	public String getCrypticDateFilterCode() ;

	/**
	 * @param crypticDateFilterCode the crypticDateFilterCode to set
	 */
	public void setCrypticDateFilterCode(String crypticDateFilterCode) ;

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() ;

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate);

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() ;

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) ;

	/**
	 * @return the createdTime
	 */
	public String getCreatedTime() ;

	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(String createdTime);
	
}
