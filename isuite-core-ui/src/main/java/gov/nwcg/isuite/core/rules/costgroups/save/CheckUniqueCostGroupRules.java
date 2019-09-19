package gov.nwcg.isuite.core.rules.costgroups.save;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckUniqueCostGroupRules extends AbstractCostGroupsSaveRule implements IRule {
	public static final String _RULE_NAME=CostGroupsSaveRuleFactory.RuleEnum.CHECK_UNIQUE_COST_GROUP.name();
	
	public CheckUniqueCostGroupRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
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
		 * Verify cost group name is unique
		 */
		String name=costGroupVo.getCostGroupName();
		String shift=costGroupVo.getIncidentShiftVo().getShiftName();
		Long incidentId=costGroupVo.getIncidentVo().getId();
		Boolean showMsg=false;
		
		CostGroup cg = super.cgDao.getByCostGroupNameShift(name, shift,incidentId);
		if(null != cg){
			if(!DateUtil.hasValue(cg.getDeletedDate())){
				if(!LongUtility.hasValue(super.costGroupVo.getId())){
					showMsg=true;
				}else{
					if(costGroupVo.getId().compareTo(cg.getId())!=0)
						showMsg=true;
				}
			}
		}
		
		if(showMsg==true){
			String msg="There is already a Cost Group with the same name and shift for the incident.";
			
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
