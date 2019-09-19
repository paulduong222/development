package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class DateOverlapRules extends AbstractCrewRule implements IRule {
	public static final String _RULE_NAME="DATE_OVERLAP";

	public DateOverlapRules(ApplicationContext ctx){
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
		 * Determine if time posting overlaps midnight
		 */
		if( vo.getReturnTravelStartOnly() && !StringUtility.hasValue(vo.getPostStopTime()))
			return _OK;
		
		Date startTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
		Date stopTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStopTime());
					
		if(null != startTime && null != stopTime){
			if(stopTime.before(startTime) && !vo.getPostStopTime().equals("0000")){

				CourseOfActionVo coaVo = new CourseOfActionVo();

				if(LongUtility.hasValue(vo.getId())){
					coaVo.setStoredObject(vo.getId());
					/*
					// no split allowed on edit
					String errorMsg="Cannot save time posting.  " +
					"When editing an existing time posting, the hours cannot overlap midnight";
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
					*/
				}
				
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
				coaVo.setPromptVo(new PromptVo("text.time","action.0138",null,PromptVo._YES | PromptVo._NO));
				coaVo.getPromptVo().setYesLabel("Split Time");
				coaVo.getPromptVo().setNoLabel("Cancel Posting");
				coaVo.getPromptVo().setButtonWidth(150);
						
				dialogueVo.setCourseOfActionVo(coaVo);
							
				return _FAIL;
							
			}else{
				/*
				 * Check if times are equal
				 */
				if(StringUtility.hasValue(vo.getPostStartTime()) && StringUtility.hasValue(vo.getPostStopTime())){
					if(vo.getPostStartTime().equals(vo.getPostStopTime())){
						
						CourseOfActionVo coaVo = new CourseOfActionVo();
						
						if(LongUtility.hasValue(vo.getId())){
							coaVo.setStoredObject(vo.getId());
							/*
							// no split allowed on edit
							String errorMsg="Cannot save time posting.  " +
							"When editing an existing time posting, the hours cannot overlap midnight";
							coaVo.setCoaName(_MSG_FINISHED);
							coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
							coaVo.setMessageVo(new MessageVo("text.time"
															, "error.800000"
															, new String[]{errorMsg}
															, MessageTypeEnum.CRITICAL));
							coaVo.setIsDialogueEnding(Boolean.TRUE);
									
							dialogueVo.setCourseOfActionVo(coaVo);
										
							return _FAIL;
							*/
						}
						
						coaVo.setCoaName(_RULE_NAME);
						coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
						coaVo.setPromptVo(new PromptVo("text.time","action.0138",null,PromptVo._YES | PromptVo._NO));
						coaVo.getPromptVo().setYesLabel("Split Time");
						coaVo.getPromptVo().setNoLabel("Cancel Posting");
						coaVo.getPromptVo().setButtonWidth(150);
								
						dialogueVo.setCourseOfActionVo(coaVo);
									
						return _FAIL;
						
					}
				}
			}
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {

		// check prompt result
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// add to processed
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getCourseOfActionVo().setIsComplete(true);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		}else{
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.time", "text.abortProcess" , new String[]{"post time"}, MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
					
			return _FAIL;
		}
				
	
		return _OK;
	}

	/**
	 * @param entity
	 * @return
	 */
	private Boolean isNonContractor(AssignmentTime entity){
		if(null != entity){
			if(entity.getEmploymentType() != EmploymentTypeEnum.CONTRACTOR)
				return true;
			else return false;
		}else
			return false;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
