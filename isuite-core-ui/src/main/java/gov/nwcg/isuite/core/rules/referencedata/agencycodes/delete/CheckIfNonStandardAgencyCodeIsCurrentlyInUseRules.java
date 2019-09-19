package gov.nwcg.isuite.core.rules.referencedata.agencycodes.delete;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class CheckIfNonStandardAgencyCodeIsCurrentlyInUseRules extends AbstractDeleteAgencyCodeRule implements IRule {
	
	public static final String _RULE_NAME=
		ReferenceDataDeleteAgencyCodeRuleFactory.RuleEnum.CHECK_IF_NON_STANDARD_AGENGY_CODE_IS_CURRENTLY_IN_USE.name();

	public CheckIfNonStandardAgencyCodeIsCurrentlyInUseRules(ApplicationContext ctx) {
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
		/*J.	16.0011- Manage Non-Standard Agency Codes
		Use Case ID: 16.0011	Goal: Manage Reference Data for Manage Agency Codes
		Actors: Data Steward	Category: Enterprise and Site

		Business Requirements
		
			Delete Agencies
			
			1.	The system must allow the Data Steward to delete a 
				Non-Standard Agency Code that is not currently in use.
			2.	The system must prevent a user from deleting a 
				Non-Standard Agency Code that is in use.*/

		if(null != agencyEntity){
			
			if(BooleanUtility.isFalse(agencyEntity.isStandard())) {
				
				if(CollectionUtility.hasValue(agencyEntity.getAccountCodes())
						|| CollectionUtility.hasValue(agencyEntity.getIncidents())
						|| CollectionUtility.hasValue(agencyEntity.getOrganizations())
						|| CollectionUtility.hasValue(agencyEntity.getResources())
						|| CollectionUtility.hasValue(agencyEntity.getResourceOthers())
						|| CollectionUtility.hasValue(agencyEntity.getCostDatas()) 
						|| CollectionUtility.hasValue(agencyEntity.getIncidentCostRateStates())
						|| CollectionUtility.hasValue(agencyEntity.getSysCostRateStates())
						|| CollectionUtility.hasValue(agencyEntity.getCostGroupAgencyDaySharePercentages())) {
					
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					courseOfActionVo.setMessageVo(
							new MessageVo(
									"text.agency",
									"error.0337",
									null,
									MessageTypeEnum.CRITICAL));
					courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
					
					dialogueVo.	setCourseOfActionVo(courseOfActionVo);
					
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

