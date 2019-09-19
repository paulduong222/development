package gov.nwcg.isuite.core.reports.data;

public class DemobCheckoutSectionData {
	private String sectionName = "";
	private Integer position;
	private String label = "";
	private Boolean selected = false;
	
	public DemobCheckoutSectionData() {
	}
	
	/**
	 * @return the sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}
	
	/**
	 * @param sectionName the sectionName to set
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		if(null == label)
			return "";
		else
			return label;
	}
	
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * @return the selected
	 */
	public Boolean getSelected() {
		return selected;
	}
	
	/**
	 * @param selected the checked to set
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	
}
