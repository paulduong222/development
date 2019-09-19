package gov.nwcg.isuite.core.rules.costgroups.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class CheckForAssignedActiveResources extends AbstractCostGroupsDeleteRule implements IRule {
	public static final String _RULE_NAME=CostGroupsDeleteRuleFactory.RuleEnum.CHECK_FOR_ASSIGNED_ACTIVE_RESOURCES.name();
	
	public CheckForAssignedActiveResources(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				/*
				 * Check prompt result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;
				
			}else{
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
				
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
	
		/*
		 * obsolete:
		 * 	 	If a user selects a cost group with assigned resources the system will
		 *      apply the following rules:
		 * 		- If all the resources have an expired release date or estimated time 
		 *        of arrival date then do not prompt a message
		 *      - If the cost group has active resources prompt a Y/N message 
		 *      
		 * june 2013 - new req
		 * If the user attempts to delete a Cost Group that has one or more Cost records associated with it, the system must notify the user that there are Cost records associated with that Cost Group and ask the user if they want to continue with the deletion. Yes and No buttons are available. [Message 0312]
				3.1. If the user answers Yes in response to message 0312, the system must delete the Cost Group and remove that Cost Group from all Cost records with which it was associated. The system must also remove the Cost Group from any Resources with which it was associated.
				3.2. If the user answers No in response to message 0312, the system must not delete the Cost Group.
		 */
		
		//check for active assigned resources
		int cnt = this.getDailyCostAssignedCount(costGroupEntity);
			
		if (cnt > 0) {
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
			coaVo.setPromptVo(new PromptVo("text.costGroups","error.0312",null,PromptVo._YES | PromptVo._NO));
			coaVo.getPromptVo().setYesLabel("YES");
			coaVo.getPromptVo().setNoLabel("NO");
			dialogueVo.setCourseOfActionVo(coaVo);

			return _FAIL;
		}
				
		return _OK;
	}
	
	/**
	 * Returns the count of the active resources assigned to the cost group.
	 * 
	 * @param costGroupEntity
	 * @return
	 * @throws Exception 
	 */
	private int getActiveAssignedResourcesCount(CostGroup costGroupEntity) throws Exception {
		return cgDao.getActiveAssignedResourcesCount(costGroupEntity.getId()); 
	}

	private int getDailyCostAssignedCount(CostGroup costGroupEntity) throws Exception {
		return cgDao.getDailyCostAssignedCount(costGroupEntity.getId());
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {

		if(getPromptResult(dialogueVo) == PromptVo._YES) {

			// continue
			// add to processed
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.costGroups", "text.abortProcess" , new String[]{"text.delete"}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa && coa.getCoaType() == CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
			// remove cost group from daily cost records
			Long costGroupId = costGroupEntity.getId();
			
			cgDao.removeDailyCostCostGroup(costGroupId);
			
		}

	}

}
