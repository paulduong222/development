package gov.nwcg.isuite.core.rules.referencedata.unitids.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class StandardUnitIdChangeLocalSettingRules extends AbstractSaveUnitIdRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataSaveUnitIdRuleFactory.RuleEnum.STANDARD_UNIT_ID_CHANGE_LOCAL_SETTING.name();

	public StandardUnitIdChangeLocalSettingRules(ApplicationContext ctx) {
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
		/*D.	16.0004- Manage Unit ID Codes
		Use Case ID: 16.0004	Goal: Manage Reference Data for Unit Identification Codes
		Actors: Data Steward	Category: Enterprise and Site

		Business Requirements
		
			Edit a Unit ID
			
			2.	When a user edits a Standard Unit ID, the 
				system must only allow the user to change 
				the Local setting for the Unit ID.*/

		if(null != organizationEntity) {
			if(organizationEntity.isStandard()) {
				if(!(super.organizationVo.getUnitCode().equalsIgnoreCase(super.organizationEntity.getUnitCode())) ||
						!(super.organizationVo.getName().equalsIgnoreCase(super.organizationEntity.getName())) ||
						!(super.organizationVo.getAgencyVo().getAgencyCd().equalsIgnoreCase(super.organizationEntity.getAgency().getAgencyCode()))){
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					courseOfActionVo.setMessageVo(
							new MessageVo(
									"text.units",
									"Only the local setting can be changed for a standard Unit",
									null,
									MessageTypeEnum.CRITICAL));
					courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
					
					dialogueVo.setCourseOfActionVo(courseOfActionVo);
					
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
