package gov.nwcg.isuite.core.rules.iap.savemasterfrequency;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class CheckNotBlankRecordRules extends AbstractMasterFrequencySaveRule implements IRule {
	
	public static final String _RULE_NAME=MasterFrequencySaveRuleFactory.RuleEnum.CHECK_NOT_BLANK.name();
	
	public CheckNotBlankRecordRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
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
	
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * This rule is not in the use case.  It comes from I-Suite.  A
		 * blank master frequency record cannot be saved.
		 */
		if(!BooleanUtility.isTrue(super.iapMasterFrequencyVo.getShow()) 
				&& null == super.iapMasterFrequencyVo.getSystemTypeVo()
				&& !StringUtility.hasValue(super.iapMasterFrequencyVo.getGroup())
				&& !StringUtility.hasValue(super.iapMasterFrequencyVo.getChannel())
				&& !StringUtility.hasValue(super.iapMasterFrequencyVo.getRfunction())
				&& !StringUtility.hasValue(super.iapMasterFrequencyVo.getRx())
				&& !StringUtility.hasValue(super.iapMasterFrequencyVo.getTx())
				&& !StringUtility.hasValue(super.iapMasterFrequencyVo.getTone())
				&& !StringUtility.hasValue(super.iapMasterFrequencyVo.getAssignment())
				&& !StringUtility.hasValue(super.iapMasterFrequencyVo.getRemarks())) {
			
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					courseOfActionVo.setMessageVo(
							new MessageVo(
									"text.masterFrequency",
									"info.generic",
									new String[]{"At least one field is required to save a master frequency."},
									MessageTypeEnum.CRITICAL));
					courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
					
					dialogueVo.	setCourseOfActionVo(courseOfActionVo);
					
					return _FAIL;
		}
		
		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}
	
}