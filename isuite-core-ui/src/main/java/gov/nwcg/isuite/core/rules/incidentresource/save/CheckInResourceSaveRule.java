package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;

public class CheckInResourceSaveRule extends AbstractIncidentResourceSaveRule implements IRule {

	public static final String _RULE_NAME="CHECK_IN_DATA_VALIDATION";
	
	public CheckInResourceSaveRule(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
		
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
			
		} catch(Exception e) {
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

		try {
			
			ArrayList<ErrorObject> errors = new ArrayList<ErrorObject>();
			
			//check-in date and time
			//if(StringUtility.hasValue(vo.getWorkPeriodVo().getCiCheckInTime())) {
			if(DateTransferVo.hasTimeString(vo.getWorkPeriodVo().getCiCheckInDateVo())){
				if(!DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiCheckInDateVo())){
					errors.add(new ErrorObject("text.requiredCheckInDateAndTime"));
				}
				
				//must be valid military time
				if(!DateUtil.isMilitaryTime(vo.getWorkPeriodVo().getCiCheckInDateVo().getTimeString())) {
					errors.add(new ErrorObject("error.invalidTimeFormat", "text.checkInTime"));
				}
				
			}
			
			if(CollectionUtility.hasValue(errors)){
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("ValidationError");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
				coaVo.setIsDialogueEnding(true);

				coaVo.setErrorObjectVos(errors);
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
			}
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
