package gov.nwcg.isuite.core.rules.common.sitemanaged;

import gov.nwcg.isuite.core.persistence.IapPlanDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckLockedByIapPlanIdRules extends AbstractSiteManagedRule implements IRule{
	public static final String _RULE_NAME=SiteManagedRuleFactory.RuleEnum.IS_INCIDENT_LOCKED_BY_IAPPLAN_ID.name();
	
	public CheckLockedByIapPlanIdRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
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
		if(LongUtility.hasValue(super.id) 
				&& super.idType.equals("IAPPLAN")
				&& super.getRunMode().equals("ENTERPRISE")){

			IncidentDao dao = (IncidentDao)context.getBean("incidentDao");
			IapPlanDao iapdao = (IapPlanDao)context.getBean("iapPlanDao");
			
			Long typeId=0L;
			String type="";
			
			Long incidentId=iapdao.getIncidentId(super.id);
			if(LongUtility.hasValue(incidentId)){
				typeId=incidentId;
				type="INCIDENT";
			}

			Long incidentGroupId=iapdao.getIncidentGroupId(super.id);
			if(LongUtility.hasValue(incidentGroupId)){
				typeId=incidentGroupId;
				type="INCIDENTGROUP";
			}
			
			if(LongUtility.hasValue(typeId)){
				Boolean isLocked=dao.isIncidentLocked(typeId, type);
				
				if(BooleanUtility.isTrue(isLocked)){
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.incident"
												  ,"validationerror"
												  ,"error.800000"
												  , new String[]{super.lockedMessage}));	
					return _FAIL;
					
				}
			}
			
		}

		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
