package gov.nwcg.isuite.core.rules.referencedata.adrates.save;

import java.util.Calendar;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class CheckValidYearADRateRules extends AbstractSaveADRateRule implements IRule {
	
	public static final String _RULE_NAME=ReferenceDataSaveADRateRuleFactory.RuleEnum.CHECK_FOR_VALID_YEAR.name();

	public CheckValidYearADRateRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}
	
	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
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
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		int yr = cal.get(Calendar.YEAR);
		
		if ((super.rateClassRateVo.getRateYear() > (yr + 1)) || (super.rateClassRateVo.getRateYear() < (yr - 1))) {
		
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.adClass",
							"info.generic",
							new String[]{"The year entered must be " +  (yr - 1) + ", " + yr + ", or " + (yr + 1) + "."},
							MessageTypeEnum.CRITICAL));
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			
			return _FAIL;
				
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
