package gov.nwcg.isuite.core.rules.costaccruals.runextract;

import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckAlreadyFinalizedRules extends AbstractRunExtractRule implements IRule {
	public static final String _RULE_NAME=RunExtractRuleFactory.RuleEnum.CHECK_IF_FINALIZED.name();
	
	public CheckAlreadyFinalizedRules(ApplicationContext ctx)
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
		Boolean isIncidentGroup=false;
		Long incId=0L;
		Long incGrpId=0L;
		
		if(LongUtility.hasValue(incidentGroupId)){
			incGrpId=super.incidentGroupId;
			isIncidentGroup=true;
		}else{
			if(LongUtility.hasValue(incidentId)){
				incId=incidentId;
				
				// check if incident is part of group
				IncidentDao incidentDao=(IncidentDao)context.getBean("incidentDao");
				incGrpId=incidentDao.getIncidentGroupId(incidentId);
				if(LongUtility.hasValue(incGrpId)){
					incId=0L;
					isIncidentGroup=true;
				}
			}
		}
		
		/*
		 * Check for the current day do we already have an finalized extract for this the 
		 */
		//CostAccrualExtract costAccrualExtract = super.dao.getExtractByDate(incId, incGrpId,extractDate);
		int cnt=super.dao.getExtractFinalizedCountByDate(incId, incGrpId, extractDate);
		
		if(cnt>0){

			//String msg="The Cost Accrual Extract for " + DateUtil.toDateString(extractDate, DateUtil.MM_SLASH_DD_SLASH_YYYY) + " "+
			//		   "was already finalized.";
			String msg="There is already a finalized Cost Accrual Extract for " + DateUtil.toDateString(extractDate, DateUtil.MM_SLASH_DD_SLASH_YYYY) + ". ";
			
			//if(StringBooleanEnum.toBooleanValue(costAccrualExtract.isFinalized())==true){
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
			//}
			
			//super.dao.flushAndEvict(costAccrualExtract);
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
