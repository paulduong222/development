package gov.nwcg.isuite.framework.types;

public enum MessageTypeEnum {

	CRITICAL("CRITICAL")
	, URGENT("URGENT")
	, VALIDATION_FAIL("VALIDATION_FAIL")
	, INFO("INFO");
	
	private String description="";
	
	MessageTypeEnum(String desc) {
		this.description = desc;
	}
	
	public String getDescription() {
		return this.description;
	}
}
