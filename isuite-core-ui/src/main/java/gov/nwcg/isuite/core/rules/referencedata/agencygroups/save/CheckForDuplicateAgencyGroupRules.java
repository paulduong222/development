package gov.nwcg.isuite.core.rules.referencedata.agencygroups.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckForDuplicateAgencyGroupRules extends AbstractSaveAgencyGroupRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataSaveAgencyGroupRuleFactory.RuleEnum.CHECK_FOR_DUPLICATE_AGENCY_GROUP.name();

	public CheckForDuplicateAgencyGroupRules(ApplicationContext ctx) {
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
		/*C.	16.0003- Manage Standard Agency Groups on the Enterprise
		Use Case ID: 16.0003	                Goal: Manage Reference Data Agency Group Codes
		Actors: Global Reference Data Manager	Category: Enterprise

			Business Requirements

				Add Agency Group Codes

					3. The system must prevent the user from adding duplicate, 
					   Standard Agency Groups to the e-ISuite Enterprise System.
		*/
		
		if(agencyGroupVo.getStandard()) {
			int cnt = super.agencyGroupDao.getDuplicateCodeCount(super.agencyGroupVo.getCode().toUpperCase(), super.agencyGroupVo.getId());
			if(cnt>0){
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(
						new MessageVo(
								"text.agencyGroup",
								"error.0219",
								new String[]{"Agency Group", super.agencyGroupVo.getCode().toUpperCase()},
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
