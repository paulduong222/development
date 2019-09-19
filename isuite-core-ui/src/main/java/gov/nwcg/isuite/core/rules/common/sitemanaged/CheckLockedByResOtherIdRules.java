package gov.nwcg.isuite.core.rules.common.sitemanaged;

import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceOtherDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckLockedByResOtherIdRules extends AbstractSiteManagedRule implements IRule{
	public static final String _RULE_NAME=SiteManagedRuleFactory.RuleEnum.IS_INCIDENT_LOCKED_BY_RES_OTHER_ID.name();
	
	public CheckLockedByResOtherIdRules(ApplicationContext ctx)
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
				&& super.idType.equals("RESOURCEOTHER")
				&& super.getRunMode().equals("ENTERPRISE")){
			IncidentDao dao = (IncidentDao)context.getBean("incidentDao");
			
			Long incidentId=0L;

			IncidentResourceOtherDao iroDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");
			incidentId=iroDao.getIncidentId(super.id);
			
			if(LongUtility.hasValue(incidentId)){
				Boolean isLocked=dao.isIncidentLocked(incidentId, "INCIDENT");
				
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
