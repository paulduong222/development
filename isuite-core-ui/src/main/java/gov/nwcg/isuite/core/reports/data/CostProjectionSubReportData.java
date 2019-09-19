package gov.nwcg.isuite.core.reports.data;
            
public class CostProjectionSubReportData {
	private Long id;
	private String category = "";
	private String directIndirectName = "";
	private String kindGroupDescription = "";
	private String subGroupCatDescription = "";
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getDirectIndirectName() {
		return directIndirectName;
	}
	public void setDirectIndirectName(String directIndirectName) {
		this.directIndirectName = directIndirectName;
	}
	public String getKindGroupDescription() {
		return kindGroupDescription;
	}
	public void setKindGroupDescription(String kindGroupDescription) {
		this.kindGroupDescription = kindGroupDescription;
	}
	
	public String getSubGroupCatDescription() {
		return subGroupCatDescription;
	}
	public void setSubGroupCatDescription(String subGroupCatDescription) {
		this.subGroupCatDescription = subGroupCatDescription;
	}
}

