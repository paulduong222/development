package gov.nwcg.isuite.core.rules.referencedata._209codes.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckForDuplicate209CodeRules extends AbstractSave209CodeRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataSave209CodeRuleFactory.RuleEnum.CHECK_FOR_DUPLICATE_209_CODE.name();

	public CheckForDuplicate209CodeRules(ApplicationContext ctx) {
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
		/* 	A. 16.0001- Manage Standard 209 Codes in Enterprise
			Use Case ID: 16.0001					Goal: Manage Standard 209 Codes in the Enterprise
			Actors: Global Reference Data Manager	Category: Enterprise
			
			Business Requirements
			
				Add Standard 209 Codes

		  			3.	The system must prevent the user from adding duplicate, 
		  			    Standard 209 Codes to the e-ISuite Enterprise System.
		 */

		if(vo.getStandard()) {
			int cnt = super.sit209Dao.getDuplicateCodeCount(super.vo.getCode().toUpperCase(), super.vo.getId());
			if(cnt>0){
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(
						new MessageVo(
								"text.209Codes",
								"error.0219",
								new String[]{"209 Code", vo.getCode().toUpperCase()},
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
		// TODO Auto-generated method stub

	}

}
