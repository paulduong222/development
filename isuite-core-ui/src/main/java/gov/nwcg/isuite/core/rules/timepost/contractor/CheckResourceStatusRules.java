package gov.nwcg.isuite.core.rules.timepost.contractor;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckResourceStatusRules extends AbstractContractorRule implements IRule{
	public static final String _RULE_NAME=TimePostContractorRuleFactory.RuleEnum.CHECK_RESOURCE_STATUS.name();

	public CheckResourceStatusRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(_RULE_NAME,true));
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) {
		Boolean bPassed=false;
		
		/*
		 * B.R. 6.0011
		 *  6.	The system must allow a user to only post time and adjustments to Resources 
		 *      with an active status. 
		 * 
		 * 	2.	The system must prevent the user from posting an adjustment 
		 *      to a Resource that does not have an active status 
		 *      (i.e., Checked In, Pending Demob).
		 *      
		 *    Resource status must be 'C' or 'P'.
		 */
		if(null != super.assignmentTimeEntity){
			if(null != assignmentTimeEntity.getAssignment().getAssignmentStatus()){
				String status = assignmentTimeEntity.getAssignment().getAssignmentStatus().name();
					
				if(status.equals("C") || status.equals("P"))
					bPassed=true;
			}
		}
		
		if(!bPassed){
			/*
			 * This is an error, 
			 * this scenario is not allowed.
			 * 
			 * Build Error message 
			 */
			String errorMsg="You cannot post time to a Resource that does not have an active status." ;	
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.time"
											, "error.800000"
											, new String[]{errorMsg}
											, MessageTypeEnum.CRITICAL));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
							
			dialogueVo.setCourseOfActionVo(coaVo);
								
			return _FAIL;
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
