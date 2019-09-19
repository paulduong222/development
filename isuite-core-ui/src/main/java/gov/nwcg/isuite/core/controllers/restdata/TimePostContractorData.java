package gov.nwcg.isuite.core.controllers.restdata;

import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;

public class TimePostContractorData extends DialogueData {

	private String postType;
	private AssignmentTimePostVo primaryVo;
	private AssignmentTimePostVo specialVo;

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public AssignmentTimePostVo getPrimaryVo() {
		return primaryVo;
	}

	public void setPrimaryVo(AssignmentTimePostVo primaryVo) {
		this.primaryVo = primaryVo;
	}

	public AssignmentTimePostVo getSpecialVo() {
		return specialVo;
	}

	public void setSpecialVo(AssignmentTimePostVo specialVo) {
		this.specialVo = specialVo;
	}

}
