package gov.nwcg.isuite.core.rules.timepost.crews.finalactions;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;

public interface ICrewFinalAction {

	public void setOriginalVo(AssignmentTimePostVo vo);
	
	public void setTimePostDao(TimePostDao dao);
	
	public AssignmentTimePost getFirstEntity();
	
	public int saveCrewPosting(AssignmentTime atEntity,AssignmentTimePostVo vo,DialogueVo dialogueVo) throws Exception;
		
	public void setMemberIndex(int i);
	
}
