package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.filter.UserFilter;

public class UserFilterData extends DialogueData {

	private UserFilter userFilter;

	public UserFilter getUserFilter() {
		return userFilter;
	}

	public void setUserFilter(UserFilter userFilter) {
		this.userFilter = userFilter;
	}
}
