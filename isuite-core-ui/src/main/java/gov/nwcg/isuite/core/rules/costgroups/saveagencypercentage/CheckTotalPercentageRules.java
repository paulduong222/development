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

import java.math.BigDecimal;

import org.springframework.context.ApplicationContext;

public class CheckTotalPercentageRules extends AbstractCostGroupsSaveAgencyPercentageRule implements IRule {
	public static final String _RULE_NAME=CostGroupsSaveAgencyPercentageRuleFactory.RuleEnum.CHECK_TOTAL_PERCENTAGE.name();
	
	public CheckTotalPercentageRules(ApplicationContext ctx)
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
		BigDecimal totalPct = BigDecimal.valueOf(0.0);
		
		if(null != super.dayShareEntity){
				
			for(CostGroupAgencyDaySharePercentage pct : dayShareEntity.getCostGroupAgencyDaySharePercentages()){
				if(LongUtility.hasValue(super.daySharePctVo.getId())){
					if(super.daySharePctVo.getId().compareTo(pct.getId())!=0)
						totalPct=totalPct.add(pct.getPercentage());
				}
			}

			totalPct=totalPct.add(super.daySharePctVo.getPercentage());
		
		}else{
			totalPct=totalPct.add(super.daySharePctVo.getPercentage());
		}
		
		if(totalPct.doubleValue() > 100){
			String msg="The total Agency/Percentage allocation cannot exceed 100.00% for the date specified.";
			
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

		return _OK;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
