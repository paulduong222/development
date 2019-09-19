package gov.nwcg.isuite.framework.types;

public enum ErrorTypeEnum {
	VALIDATION_ERROR("VALIDATION_ERROR")
	,EXCEPTION_ERROR("EXCEPTION_ERROR");
	
	private String description="";
	
	ErrorTypeEnum(String desc){
		this.description = desc;
	}
	
	public String getDescription(){
		return this.description;
	}
	
}
