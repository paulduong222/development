package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DecimalUtil;

import org.springframework.context.ApplicationContext;

public class OtherRateRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="CHECK_OTHER_RATE_OVER_HUNDRED";

	public OtherRateRules(ApplicationContext ctx){
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		/*
		 * B.R. 6.0002
		 * 
		 *  2.	When the Employment Type is Other and the user enters a 
		 *      Current Pay Rate that is greater than $100.00, the system 
		 *      must display a message confirming the rate. 
		 *      Yes and No buttons are available. 
		 *      [Message 0218] 
		 *      
		 *      2.1.	When the user selects Yes in response to message 0218, 
		 *              the system must save the Time data with the 
		 *              Current Pay Rate set at the amount higher than $100.00. 
		 *              
		 *      2.2.	When the user selects No in response to message 0218, 
		 *              the system must not save the Time data.
		 */
		AssignmentVo assignVo = vo.getWorkPeriodVo().getCurrentAssignmentVo();

		if(null != assignVo){
			if(null != assignVo.getAssignmentTimeVo() && assignVo.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.OTHER ){
				if(null != assignVo.getAssignmentTimeVo().getOtherDefaultRate() && assignVo.getAssignmentTimeVo().getOtherDefaultRate().doubleValue() > 100){
					
					// prompt user and verify to use amount greater than 100
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
					coaVo.setPromptVo(new PromptVo("text.incidentResources"
													,"action.0218"
													,new String[]{DecimalUtil.formatAsString(assignVo.getAssignmentTimeVo().getOtherDefaultRate().doubleValue(),2)}
													,PromptVo._YES | PromptVo._NO));
					dialogueVo.setCourseOfActionVo(coaVo);
				
					return _FAIL;
				}
			}
		}
		
		/*
		AssignmentVo assignVo = null;
		AssignmentVo assignmentVo = vo.getWorkPeriodVo().getCurrentAssignmentVo();

		if(null != vo.getId() && vo.getId()> 0)
			entity = irDao.getById(vo.getId(), IncidentResourceImpl.class);
		
		assignVo=assignmentVo;
		
		if(null != assignVo){
			if(null != assignVo.getAssignmentTimeVo() && assignVo.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.OTHER ){
				if(null != assignVo.getAssignmentTimeVo().getOtherDefaultRate() && assignVo.getAssignmentTimeVo().getOtherDefaultRate().doubleValue() > 100){
					
					// check if it is different from the previous value
					if(null != entity){
						WorkPeriod wpEntity = entity.getWorkPeriod();
						
						if(null != wpEntity && null != wpEntity.getAssignments()){
							Assignment assignment = null;
							
							for(Assignment a : wpEntity.getAssignments()){
								
								if(null == a.getEndDate()){
									assignment = a;
									break;
								}
							}
							
							if(null != assignment){
								if(null != assignment.getAssignmentTime() && null != assignment.getAssignmentTime().getOtherDefaultRate()){
									
									if(assignVo.getAssignmentTimeVo().getOtherDefaultRate().compareTo(assignment.getAssignmentTime().getOtherDefaultRate()) == 0){
										// same as before, skip prompting
									}else{
										// prompt user and verify to use amount greater than 100
										CourseOfActionVo coaVo = new CourseOfActionVo();
										coaVo.setCoaName(_RULE_NAME);
										coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
										coaVo.setPromptVo(new PromptVo("text.incidentResources","action.0218",new String[]{String.valueOf(assignment.getAssignmentTime().getOtherDefaultRate())},PromptVo._YES | PromptVo._NO));
										dialogueVo.setCourseOfActionVo(coaVo);
									
										return _FAIL;
									}
								}
							}
							
						}
					}
				}
					
			}
		}
		*/
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {
	
		// add to processed
		dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
		dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// continue;
		}else{
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
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
	
}
