package gov.nwcg.isuite.core.rules.referencedata.adrates.save;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class CheckForDuplicateADRateRules extends AbstractSaveADRateRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataSaveADRateRuleFactory.RuleEnum.CHECK_FOR_DUPLICATE_AD_RATE.name();
	
	public CheckForDuplicateADRateRules(ApplicationContext ctx) {
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
		/*I.	16.0009 - Manage AD Classes in the Enterprise
				Use Case ID: 16.0009	 				Goal: Manage Reference Data for AD Classes
				Actors: Global Reference Data Manager	Category: Enterprise
		
				Business Requirements
		
				Add AD Classes
			
				3.	The system must prevent the user from adding duplicate,  
				    AD Classes to the e-ISuite Enterprise System.
		*/
		
		if(rateClassRateVo.getStandard()) {
			int cnt = super.rateClassRateDao.getDuplicateCodeCount(super.rateClassRateVo.getRateClassName().toUpperCase(), super.rateClassRateVo.getRateYear(),  super.rateClassRateVo.getId());
			if(cnt>0){
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(
						new MessageVo(
								"text.adClass",
								"error.0219",
								new String[]{"AD class", super.rateClassRateVo.getRateClassName().toUpperCase() + ' ' + super.rateClassRateVo.getRateYear().toString()},
								MessageTypeEnum.CRITICAL));
				courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
				
				dialogueVo.setCourseOfActionVo(courseOfActionVo);
				
				return _FAIL;
				
			}
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
