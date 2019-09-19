package gov.nwcg.isuite.core.rules.dailycost.delete;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CostLevelEnum;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckForManualCostRules extends AbstractDeleteDailyCostRule implements IRule {
	public static final String _RULE_NAME=DeleteDailyCostRuleFactory.RuleEnum.CHECK_FOR_MANUAL_COST.name();

	public CheckForManualCostRules(ApplicationContext ctx) {
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
		 * Page 41 
		 * If the user deletes the only existing auto-generated cost record for a date 
		 * and a manual cost record also exists for that same date, 
		 * the system must remove the manually added cost record.
		 */
		
		// is this cost record not an 'M'
		if(!super.irdcVo.getCostLevel().equals("M")){
			
			// is there an 'M' for this date?
			int cnt=0;
			
			if(null != irdcVo.getIncidentResourceVo()){
				Date dt = DateTransferVo.getDate(irdcVo.getActivityDateVo());
				cnt=super.irdcDao.getManualCostCount(irdcVo.getIncidentResourceVo().getId(), null,dt);
			}
			
			if(null != irdcVo.getIncidentResourceOtherVo()){
				Date dt = DateTransferVo.getDate(irdcVo.getActivityDateVo());
				cnt=super.irdcDao.getManualCostCount(null,irdcVo.getIncidentResourceOtherVo().getId(), dt);
			}
			
			if(cnt > 0){
				Date dt = DateTransferVo.getDate(irdcVo.getActivityDateVo());
				
				// Prompt the user
				String msg="If you delete the cost record for " + 
							DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY) + 
							" all manually added cost records will also will also be deleted.  Do you want to continue?";
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
				coaVo.setPromptVo(new PromptVo("text.dailyCost"
									,"info.generic"
									,new String[]{msg}
									,PromptVo._YES | PromptVo._NO));
				
				dialogueVo.setCourseOfActionVo(coaVo);
			
				return _FAIL;
				
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
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.NOACTION);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
	
			return _FAIL;
		}

		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME+"ACTION");

		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			Date dt = DateTransferVo.getDate(irdcVo.getActivityDateVo());
			// remove all 'M' records for the date
			if(null != irdcVo.getIncidentResourceVo())
				super.irdcDao.deleteManualCost(irdcVo.getIncidentResourceVo().getId(), null,dt);
			
			if(null != irdcVo.getIncidentResourceOtherVo())
				super.irdcDao.deleteManualCost(null,irdcVo.getIncidentResourceOtherVo().getId(), dt);
		}

	}

}
