package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import org.springframework.context.ApplicationContext;

public class CheckStatusChangeRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="CHECK_STATUS_CHANGE";

	public CheckStatusChangeRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try {

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
			
		} catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * Status Change Matrix Rules
		 *           
		 */
		
		if(null != irEntity){
			Assignment aEntity = null;
			for(Assignment a : irEntity.getWorkPeriod().getAssignments()){
				if(!DateUtil.hasValue(a.getEndDate())){
					aEntity = a;
				}
			}
			
			if(aEntity.getAssignmentStatus() != super.vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus()){
				
				if(checkStatusChangeMatrix(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus(),aEntity.getAssignmentStatus(), dialogueVo)==_FAIL)
					return _FAIL;
			}
		}
		
		return _OK;
	}

	private int checkStatusChangeMatrix(AssignmentStatusTypeEnum origStatus, AssignmentStatusTypeEnum newStatus, DialogueVo dvo) throws Exception {

		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {
		
		dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// continue;
		}else{
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentResources", "text.abortProcess" , new String[]{"save"}, MessageTypeEnum.INFO));

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

	private int checkFromFilled(AssignmentStatusTypeEnum origStatus, AssignmentStatusTypeEnum newStatus, DialogueVo dvo) throws Exception {
		
		/*
		 * From Status   To Status    Action
		 *  F				F			n/a
		 *  F			    C			n/a
		 *  F				P			display message
		 *  F				D			display message
		 *  F				R			display message
		 */
		
		return _OK;
	}

	private int checkFromCheckedIn(AssignmentStatusTypeEnum origStatus, AssignmentStatusTypeEnum newStatus, DialogueVo dvo) throws Exception {
		
		/*
		 * From Status   To Status    Action
		 *  C				F			not allowed if there is critical data
		 *  C			    C			n/a
		 *  C				P			n/a
		 *  C				D			n/a
		 *  C				R			n/a
		 */
		
		return _OK;
	}

	private int checkFromPendingDemob(AssignmentStatusTypeEnum origStatus, AssignmentStatusTypeEnum newStatus, DialogueVo dvo) throws Exception {
		
		/*
		 * From Status   To Status    Action
		 *  P				F			not allowed if there is critical data
		 *  P			    C			show message, clean tentative data
		 *  P				P			n/a
		 *  P				D			n/a
		 *  P				R			n/a
		 */
		
		return _OK;
	}

	private int checkFromDemob(AssignmentStatusTypeEnum origStatus, AssignmentStatusTypeEnum newStatus, DialogueVo dvo) throws Exception {
		
		/*
		 * From Status   To Status    Action
		 *  D				F			not allowed if there is critical data
		 *  D			    C			show message, clean tentative data
		 *  D				P			show message, clean actual data
		 *  D				D			n/a
		 *  D				R			n/a
		 */
		
		return _OK;
	}

	private int checkFromReassigned(AssignmentStatusTypeEnum origStatus, AssignmentStatusTypeEnum newStatus, DialogueVo dvo) throws Exception {
		
		/*
		 * From Status   To Status    Action
		 *  R				F			not allowed if there is critical data
		 *  R			    C			show message, clean tentative data
		 *  R				P			n/a
		 *  R				D			n/a
		 *  R				R			n/a
		 */
		
		return _OK;
	}

}
