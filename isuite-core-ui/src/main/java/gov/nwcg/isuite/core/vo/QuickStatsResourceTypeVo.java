package gov.nwcg.isuite.core.vo;


public class QuickStatsResourceTypeVo  {
	private String description;
	private String type;
	private String status;
	private Integer count;
	
	public QuickStatsResourceTypeVo(){
		   
	}

	/**
	 * Returns the type.
	 *
	 * @return 
	 *		the type to return
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type 
	 *			the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the status.
	 *
	 * @return 
	 *		the status to return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status 
	 *			the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Returns the count.
	 *
	 * @return 
	 *		the count to return
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count 
	 *			the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	   
}
