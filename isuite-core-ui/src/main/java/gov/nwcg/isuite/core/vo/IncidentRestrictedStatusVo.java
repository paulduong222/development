package gov.nwcg.isuite.core.vo;

/**
 * 
 * @author dprice
 */
public class IncidentRestrictedStatusVo {
	private String name;
	private String description;

	public IncidentRestrictedStatusVo() {
		super();
	}

	public IncidentRestrictedStatusVo(String name, String desc) {
		super();
		setName(name);
		setDescription(desc);
	}

	/**
	 * Returns the name.
	 *
	 * @return 
	 *		the name to return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name 
	 *			the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the description.
	 *
	 * @return 
	 *		the description to return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description 
	 *			the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


}
