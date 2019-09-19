package gov.nwcg.isuite.core.rules.referencedata.agencycodes.delete;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class CheckIfStandardAgencyCodeIsCurrentlyInUseRules extends AbstractDeleteAgencyCodeRule implements IRule {

	public static final String _RULE_NAME=
		ReferenceDataDeleteAgencyCodeRuleFactory.RuleEnum.CHECK_IF_STANDARD_AGENCY_CODE_IS_CURRENTLY_IN_USE.name();

	public CheckIfStandardAgencyCodeIsCurrentlyInUseRules(ApplicationContext ctx) {
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
		/*B.	16.0002- Manage Standard Agency Codes in Enterprise
				Use Case ID: 16.0002	 				Goal: Manage Reference Data for Standard Agency Codes
				Actors: Global Reference Data Manager	Category: Enterprise

				Business Requirements
		
				Delete Standard Agency Codes
			
				1.	The system must allow the Global Reference Data Manager to 
				    delete Standard Agency Codes that were not used in the system.
				2.	If a Standard Agency Code was used in the system and the Global 
				    Reference Data Manager attempts to delete it, the system must
				    not delete the code and must allow the user to deactivate it instead.
		*/

		if(null != agencyEntity){
			
			if(BooleanUtility.isTrue(agencyEntity.isStandard())) {
		
				if(CollectionUtility.hasValue(agencyEntity.getAccountCodes())
						|| CollectionUtility.hasValue(agencyEntity.getIncidents())
						|| CollectionUtility.hasValue(agencyEntity.getOrganizations())
						|| CollectionUtility.hasValue(agencyEntity.getResources())
						|| CollectionUtility.hasValue(agencyEntity.getResourceOthers())
						|| CollectionUtility.hasValue(agencyEntity.getCostDatas()) 
						|| CollectionUtility.hasValue(agencyEntity.getIncidentCostRateStates())
						|| CollectionUtility.hasValue(agencyEntity.getSysCostRateStates())
						|| CollectionUtility.hasValue(agencyEntity.getCostGroupAgencyDaySharePercentages())) {
					
					PromptVo promptVo = new PromptVo();
					promptVo.setMessageProperty("action.0282");
					promptVo.setTitleProperty("text.agency"); 
				    promptVo.setPromptValue(PromptVo._YES | PromptVo._NO);
					
					CourseOfActionVo coa = new CourseOfActionVo();
					coa.setCoaName(ruleName);
				    coa.setCoaType(CourseOfActionTypeEnum.PROMPT);
				    coa.setPromptVo(promptVo);
				    
				    dialogueVo.setCourseOfActionVo(coa);
					
					return _FAIL;
					
				}
			}
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

}
