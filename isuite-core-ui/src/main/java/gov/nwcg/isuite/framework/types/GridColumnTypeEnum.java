package gov.nwcg.isuite.framework.types;


/**
 * 
 * @author bsteiner
 */
public enum GridColumnTypeEnum {
	TEXT("Text Field"), 
	DATE("Date Field"), 
	LIST("List Field");

	private String description = "";

	GridColumnTypeEnum(String desc) {
		this.description = desc;
	}

	public String getDescription() {
		return this.description;
	}
	
}