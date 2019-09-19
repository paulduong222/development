package gov.nwcg.isuite.framework.types;


/**
 * 
 * @author bsteiner
 */
public enum GridNameEnum {
	RESOURCE("Resource") 
  ,OTHERCOST("Other Cost")
  ,RESOURCECOST("Resource Cost")
	,RESOURCEDEMOB("Resource Demob")
	,RESOURCETIME("Resource Time")
	,WORKAREARESOURCE("Work Area Resource")
	,TIME("Time")
	,SUPPLY("Supply");

	private String description = "";

	GridNameEnum(String desc) {
		this.description = desc;
	}

	public String getDescription() {
		return this.description;
	}
	
}