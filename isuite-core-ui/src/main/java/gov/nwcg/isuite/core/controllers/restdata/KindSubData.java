package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

public class KindSubData extends DialogueData {
	private Collection<DropdownData> requestCategoryTypeData;
	private Collection<DropdownData> dailyFormTypeData;
	private Collection<DropdownData> sit209TypeData;
	private Collection<DropdownData> subGroupCategoryTypeData;
	private Collection<DropdownData> groupCategoryTypeData;
	private Collection<DropdownData> departmentTypeData;
	public Collection<DropdownData> getRequestCategoryTypeData() {
		return requestCategoryTypeData;
	}
	public void setRequestCategoryTypeData(Collection<DropdownData> requestCategoryTypeData) {
		this.requestCategoryTypeData = requestCategoryTypeData;
	}
	public Collection<DropdownData> getDailyFormTypeData() {
		return dailyFormTypeData;
	}
	public void setDailyFormTypeData(Collection<DropdownData> dailyFormTypeData) {
		this.dailyFormTypeData = dailyFormTypeData;
	}
	public Collection<DropdownData> getSit209TypeData() {
		return sit209TypeData;
	}
	public void setSit209TypeData(Collection<DropdownData> sit209TypeData) {
		this.sit209TypeData = sit209TypeData;
	}
	public Collection<DropdownData> getSubGroupCategoryTypeData() {
		return subGroupCategoryTypeData;
	}
	public void setSubGroupCategoryTypeData(Collection<DropdownData> subGroupCategoryTypeData) {
		this.subGroupCategoryTypeData = subGroupCategoryTypeData;
	}
	public Collection<DropdownData> getGroupCategoryTypeData() {
		return groupCategoryTypeData;
	}
	public void setGroupCategoryTypeData(Collection<DropdownData> groupCategoryTypeData) {
		this.groupCategoryTypeData = groupCategoryTypeData;
	}
	public Collection<DropdownData> getDepartmentTypeData() {
		return departmentTypeData;
	}
	public void setDepartmentTypeData(Collection<DropdownData> departmentTypeData) {
		this.departmentTypeData = departmentTypeData;
	}
	
}
