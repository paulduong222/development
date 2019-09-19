package gov.nwcg.isuite.core.rules.dailycost.save;

import gov.nwcg.isuite.core.cost.CostGenerator;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class CheckPropagateCostGroupRules extends AbstractSaveDailyCostRule implements IRule {
	public static final String _RULE_NAME=SaveDailyCostRuleFactory.RuleEnum.CHECK_PROPAGATE_COST_GROUP.name();

	public CheckPropagateCostGroupRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
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
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				// add to processed
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				return checkPromptResult(dialogueVo);
				
			}else{
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
				
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		/*
		 * When the user selects a Primary Resource with Subordinate Resources rostered to it, 
		 * the system must ask the user whether they want to propagate the cost group to the 
		 * Subordinate Resources if user changes the cost group for the primary. 
		 */
		

		if(null != super.dailyCost)
			if(null != irEntity){
				// does this resource have children?
				if(CollectionUtility.hasValue(irEntity.getResource().getChildResources())){
					Boolean promptPropagateCostGroup = false;
					
					if(null==super.dailyCost.getCostGroup() && null != super.irdcVo.getCostGroupVo())
						promptPropagateCostGroup=true;

					if(null!=super.dailyCost.getCostGroup() && null == super.irdcVo.getCostGroupVo())
						promptPropagateCostGroup=true;
					
					if(null!=super.dailyCost.getCostGroup() 
							&& null != super.irdcVo.getCostGroupVo()
							&& !dailyCost.getCostGroup().getCostGroupName().equals(super.irdcVo.getCostGroupVo().getCostGroupName()))
						promptPropagateCostGroup=true;
					

					if(promptPropagateCostGroup==true){
						// Prompt the user
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName(_RULE_NAME);
						coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
						coaVo.setPromptVo(new PromptVo("text.dailyCost"
											,"info.generic"
											,new String[]{"Do you want to propagate the Cost Group to the Subordinate Resources?"}
											,PromptVo._YES | PromptVo._NO));
						
						dialogueVo.setCourseOfActionVo(coaVo);
					
						return _FAIL;
					}
				}
			}
		
		return _OK;
		
	}

	private int checkPromptResult(DialogueVo dialogueVo) {
		
		if(getPromptResult(dialogueVo) == PromptVo._YES) {

			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(_RULE_NAME+"ACTION");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			courseOfActionVo.setIsComplete(true);
			dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
	
		}

		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME+"ACTION");

		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			Long costGroupId = null;
			Long shiftId=null;
			
			if(null != irdcVo.getCostGroupVo() && LongUtility.hasValue(irdcVo.getCostGroupVo().getId()))
				costGroupId=irdcVo.getCostGroupVo().getId();
			
			if(null != irdcVo.getIncidentShiftVo() && LongUtility.hasValue(irdcVo.getIncidentShiftVo().getId()))
				shiftId=irdcVo.getIncidentShiftVo().getId();

			CostGenerator costGen = new CostGenerator(super.context);
			costGen.runPropagateCostGroup(super.irEntity, costGroupId, shiftId);
		}

	}

}
