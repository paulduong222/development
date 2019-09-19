package gov.nwcg.isuite.core.rules.incident.saveincident;

import gov.nwcg.isuite.core.persistence.WorkAreaDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckPdcChangeRules extends AbstractIncidentSaveRule implements IRule{
	public static final String _RULE_NAME=SaveIncidentRuleFactory.RuleEnum.CHECK_PDC_CHANGE.name();

	public CheckPdcChangeRules(ApplicationContext ctx)
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
			if(runRuleCheck(dialogueVo)==_FAIL){
				dialogueVo.getProcessedCourseOfActionVos().add(super.buildAdditionalActionCoaVo(_RULE_NAME,true));
				return _OK;
			}
				
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		/*
		 * Only check if run mode is enterprise
		 */
		if(!super.getRunMode().equals("ENTERPRISE")){
			return _OK;
		}

		/*
		 * If the user changes the incident pdc,
		 * remove the old association between the incident/standardworkarea's
		 */
		if( (null != originalPdcVo) 
				&& originalPdcVo.getId().compareTo(vo.getPdcVo().getId()) != 0){

			return _FAIL;
		}
	
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa && coa.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
			
			if( (null != originalPdcVo) && (null != newPdcVo)){

				WorkAreaDao waDao = (WorkAreaDao)super.context.getBean("workAreaDao");
				
				/*
				 * Remove old standard work area / incident association
				 */
				waDao.removeWorkAreaIncidentAssociation(originalPdcVo.getId(), incidentEntity.getId());

				/*
				 * Add new standard work area / incident association
				 */
				waDao.addWorkAreaIncidentAssociation(newPdcVo.getId(), incidentEntity.getId());
				
			}
			
			
		}
		
	}

}
