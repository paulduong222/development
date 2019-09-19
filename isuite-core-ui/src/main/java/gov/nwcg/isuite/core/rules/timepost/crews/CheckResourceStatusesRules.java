package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckResourceStatusesRules extends AbstractCrewRule implements IRule{
	public static final String _RULE_NAME=TimePostCrewRuleFactory.RuleEnum.CHECK_RESOURCE_STATUSES.name();

	public CheckResourceStatusesRules(ApplicationContext ctx)
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
		Collection<String> failedNames = new ArrayList<String>();
		
		for(AssignmentTime atEntity : super.entities){
			if(null != atEntity){
				if(null != atEntity.getAssignment().getAssignmentStatus()){
					String status = atEntity.getAssignment().getAssignmentStatus().name();
					
					if(!status.equals("C") && !status.equals("P")){
						WorkPeriod wp = atEntity.getAssignment().getWorkPeriods().iterator().next();
						Resource resource = wp.getIncidentResource().getResource();
						
						failedNames.add(resource.getFirstName()+ " " + resource.getLastName());
						
					}
				}
			}
		}
		
		if(CollectionUtility.hasValue(failedNames)){
			/*
			 * This is an error, 
			 * this scenario is not allowed.
			 * 
			 * Build Error message 
			 */
			String names=CollectionUtility.toCommaDelimitedString(failedNames);
			
			String errorMsg="You cannot post time to a Resource(s) that do not have an active status." +
							"The following resources do not have an active status: " + names;
			
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
