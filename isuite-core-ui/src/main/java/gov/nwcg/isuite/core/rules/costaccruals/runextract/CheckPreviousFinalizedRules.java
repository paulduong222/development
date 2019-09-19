package gov.nwcg.isuite.core.rules.costaccruals.runextract;

import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckPreviousFinalizedRules extends AbstractRunExtractRule implements IRule {
	public static final String _RULE_NAME=RunExtractRuleFactory.RuleEnum.CHECK_PREVIOUS_FINALIZED.name();
	
	public CheckPreviousFinalizedRules(ApplicationContext ctx)
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
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		/*
		 * Defect 4182 obsoletes this rule 
		 */
		return _OK;
		
		/*
		 * Check if previous accrual was finalized
		 */
		/*
		Date previousDate=DateUtil.subtractDaysFromDate(extractDate, 1);
		
		CostAccrualExtract prevEntity = dao.getExtractByDate(incidentId, incidentGroupId, previousDate);
		if(null != prevEntity){
			dao.flushAndEvict(prevEntity);
			
			Boolean isFinalized = StringBooleanEnum.toBooleanValue(prevEntity.isFinalized());
			
			if(isFinalized==false){
				String msg="The Cost Accrual Extract for " + DateUtil.toDateString(previousDate, DateUtil.MM_SLASH_DD_SLASH_YYYY) + " "+
				   "has not been finalized yet.  The previous extract needs to finalized before running a new extract for a new day.";
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.costAccruals"
												, "info.generic"
												, new String[]{msg}
												, MessageTypeEnum.CRITICAL));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
						
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
			}
		}
		
		return _OK;
		*/
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
