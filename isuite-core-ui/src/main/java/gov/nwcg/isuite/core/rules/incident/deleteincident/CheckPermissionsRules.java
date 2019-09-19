package gov.nwcg.isuite.core.rules.incident.deleteincident;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class CheckPermissionsRules extends AbstractRule implements IRule{
	public static final String _RULE_NAME=DeleteIncidentRuleFactory.RuleEnum.CHECK_PERMISSIONS.name();

	private IncidentVo vo=null;
	private Incident incidentEntity=null;
	
	public CheckPermissionsRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteIncidentRuleFactory.ObjectTypeEnum.INCIDENT_VO.name()))
			vo = (IncidentVo)object;
		if(objectName.equals(DeleteIncidentRuleFactory.ObjectTypeEnum.INCIDENT_ENTITY.name()))
			incidentEntity = (Incident)object;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			if(!isCourseOfActionComplete(dialogueVo, _RULE_NAME)){

				/*
				 * This rule will be implemented when user roles are revisited in the future.
				 * Skip rule.
				 */
				dialogueVo.getProcessedCourseOfActionVos().add(super.buildNoActionCoaVo(_RULE_NAME, true));
				
			}

		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
