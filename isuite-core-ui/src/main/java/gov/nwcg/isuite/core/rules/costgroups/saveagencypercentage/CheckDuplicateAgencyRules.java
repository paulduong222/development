package gov.nwcg.isuite.core.rules.costgroups.saveagencypercentage;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckDuplicateAgencyRules extends AbstractCostGroupsSaveAgencyPercentageRule implements IRule {
	public static final String _RULE_NAME=CostGroupsSaveAgencyPercentageRuleFactory.RuleEnum.CHECK_DUPLICATE_AGENCY.name();
	
	public CheckDuplicateAgencyRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			/*
			 * This rule will be implemented when user roles are revisited in the future.
			 * Skip rule.
			 */
			dialogueVo.getProcessedCourseOfActionVos().add(super.buildNoActionCoaVo(_RULE_NAME, true));
				
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) {
	
		/*
		 * Verify when adding new day share percentage,
		 * that the agency does not already exists 
		 * for this date?
		 */
		if(LongUtility.hasValue(super.dayShareEntity.getId())){
			//if(!LongUtility.hasValue(super.daySharePctVo.getId())){
				Boolean exists=false;
				
				Long agencyId=super.daySharePctVo.getAgencyVo().getId();
				
				for(CostGroupAgencyDaySharePercentage dsPct : dayShareEntity.getCostGroupAgencyDaySharePercentages()){
					if(agencyId.compareTo(dsPct.getAgency().getId())==0){
						if(!LongUtility.hasValue(super.daySharePctVo.getId())){
							exists=true;
							break;
						}else{
							if(daySharePctVo.getId().compareTo(dsPct.getId()) != 0){
								exists=true;
								break;
							}
						}
					}
				}
				
				if(exists==true){
					String msg="There is already a cost share percentage allocation for the Agency on the date.";
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.costGroups"
							, "info.generic"
							, new String[]{msg}
							, MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(Boolean.TRUE);
							
					dialogueVo.setCourseOfActionVo(coaVo);
					
					return _FAIL;
				}
			//}
			
			
		}
		
		return _OK;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
