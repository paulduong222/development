package gov.nwcg.isuite.core.rules.timepost.contractor;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class ContractorPrimaryHourlyOverlapRules extends AbstractContractorRule implements IRule {
	public static final String _RULE_NAME="CONTRACTOR_PRIMARY_HOURLY_OVERLAP";

	public ContractorPrimaryHourlyOverlapRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			
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
		 * Determine if time posting hourly times overlap midnight
		 */
		if((postType.equals("PRIMARY") || postType.equals("BOTH")) 
				&& getPrimaryUnitOfMeasure().equals("HOURLY")){
			Date startTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
			Date stopTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStopTime());
			
			if(null != startTime && null != stopTime){
				if(stopTime.before(startTime) && !vo.getPostStopTime().equals("0000")){

					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
					coaVo.setPromptVo(new PromptVo("text.time","action.0138",new String[]{"Primary","Primary"},PromptVo._YES | PromptVo._NO));
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
							coaVo.setCoaName(_RULE_NAME);
							coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
							coaVo.setPromptVo(new PromptVo("text.time","action.0138",null,PromptVo._YES | PromptVo._NO));
							coaVo.getPromptVo().setYesLabel("Split Time");
							coaVo.getPromptVo().setNoLabel("Cancel Posting");
							coaVo.getPromptVo().setButtonWidth(150);
									
							dialogueVo.setCourseOfActionVo(coaVo);
										
							return _FAIL;
							
						}
					}else{
						dialogueVo.getProcessedCourseOfActionVos().add(super.buildNoActionCoaVo(_RULE_NAME,true));
					}
				}
			}
		}

		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {
	
		dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);


		// check prompt result
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// continue;
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
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
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
