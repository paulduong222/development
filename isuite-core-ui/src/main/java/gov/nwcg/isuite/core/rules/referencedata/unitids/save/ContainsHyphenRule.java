package gov.nwcg.isuite.core.rules.referencedata.unitids.save;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class ContainsHyphenRule extends AbstractSaveUnitIdRule implements IRule {
	
	public static final String _RULE_NAME=ReferenceDataSaveUnitIdRuleFactory.RuleEnum.CONTAINS_HYPHEN.name();

	public ContainsHyphenRule(ApplicationContext ctx) {
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
	
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		if(super.organizationVo.getUnitCode().length() < 3) {
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.unitID",
							"info.generic",
						    new String[] {"The unit code must contain at least 3 characters."},
							MessageTypeEnum.CRITICAL));
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			
			return _FAIL;
		}else {
			if(organizationVo.getUnitCode().charAt(2) != '-') {
				String uc = super.organizationVo.getUnitCode().toUpperCase();
				uc = new StringBuilder(uc).insert(2,"-").toString();
				super.organizationVo.setUnitCode(uc);
			}
		}
		
		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub
	}

	

}
