package gov.nwcg.isuite.core.rules.referencedata.agencygroups.delete;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.domain.impl.AgencyGroupImpl;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class CheckIfAgencyGroupIsCurrentlyInUseRules extends AbstractDeleteAgencyGroupRule implements IRule {

	public static final String _RULE_NAME=
		ReferenceDataDeleteAgencyGroupRuleFactory.RuleEnum.CHECK_IF_AGENCY_GROUP_IS_CURRENTLY_IN_USE.name();

	public CheckIfAgencyGroupIsCurrentlyInUseRules(ApplicationContext ctx) {
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
		/*C.	16.0003 - Manage Standard Agency Groups on the Enterprise
				Use Case ID: 16.0003	 				Goal: Manage Reference Data Agency Group Codes
				Actors: Global Reference Data Manager	Category: Enterprise and Site
		
				Business Requirements
		
				Delete Standard Agency Group Codes
			
				1.	The system must allow the Global Reference Data Manager to 
				    delete Standard Agency Group Codes that were not used in the system.
				2.	If a Standard Agency Group Code was used in the system and the Global 
				    Reference Data Manager attempts to delete it, the system must
				    not delete the code and must allow the user to deactivate it instead.
		*/


		AgencyGroup agencyGroup = super.agencyGroupDao.getById(super.agencyGroupVo.getId(), AgencyGroupImpl.class);
		
		if(CollectionUtility.hasValue(agencyGroup.getAgencies())) {
			PromptVo promptVo = new PromptVo();
			promptVo.setMessageProperty("action.0282");
			promptVo.setTitleProperty("text.agencyGroup"); 
		    promptVo.setPromptValue(PromptVo._YES | PromptVo._NO);
			
		    CourseOfActionVo coa = new CourseOfActionVo();
		    coa.setCoaName(ruleName);
		    coa.setCoaType(CourseOfActionTypeEnum.PROMPT);
		    coa.setPromptVo(promptVo);
		    
		    dialogueVo.setCourseOfActionVo(coa);
			
			return _FAIL;
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

}
