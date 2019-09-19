package gov.nwcg.isuite.framework.types;

public enum CourseOfActionTypeEnum {
	PROMPT("PROMPT")
	,CUSTOMPROMPT("CUSTOMPROMPT")
	,SHOWMESSAGE("SHOWMESSAGE")
	,HANDLE_RECORDSET("HANDLE_RECORDSET")
	,HANDLE_RESULT_OBJECT("HANDLE_RESULT_OBJECT")
	,ERROR("ERROR")
	,NOACTION("NOACTION")
	,SKIP("SKIP")
	,NAVIGATION("NAVIGATION")
	,ADDITIONAL_ACTION_NEEDED("ADDITIONAL_ACTION_NEEDED");

	private String description="";

	CourseOfActionTypeEnum(String desc){
		this.description=desc;
	}

	public String getDescription(){
		return this.description;
	}

}
