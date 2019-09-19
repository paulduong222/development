package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.DatabaseMgmtFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

import java.util.Date;

public class DatabaseMgmtFilterImpl extends FilterImpl implements DatabaseMgmtFilter {

	private Long id;
	private String databaseName;
	private String crypticDateFilterCode;
	private Date createdDate;
	private String createdBy;
	private String createdTime;
	
	public DatabaseMgmtFilterImpl(){
		
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
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param databaseName the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * @return the crypticDateFilterCode
	 */
	public String getCrypticDateFilterCode() {
		return crypticDateFilterCode;
	}

	/**
	 * @param crypticDateFilterCode the crypticDateFilterCode to set
	 */
	public void setCrypticDateFilterCode(String crypticDateFilterCode) {
		this.crypticDateFilterCode = crypticDateFilterCode;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdTime
	 */
	public String getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
}
