package gov.nwcg.isuite.core.rules.incident.saveincident;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.RestrictedIncidentUserImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;

public class SetRestrictedOnNewIncidentRules extends AbstractRule implements IRule{
	public static final String _RULE_NAME=SaveIncidentRuleFactory.RuleEnum.SET_RESTRICTED_ON_NEW_INCIDENT.name();

	private IncidentVo vo=null;
	private Incident incidentEntity=null;

	public SetRestrictedOnNewIncidentRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveIncidentRuleFactory.ObjectTypeEnum.INCIDENT_VO.name()))
			vo = (IncidentVo)object;
		if(objectName.equals(SaveIncidentRuleFactory.ObjectTypeEnum.INCIDENT_ENTITY.name()))
			incidentEntity = (Incident)object;
}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		/*
		 * User is manually adding an incident.
		 * Use Case requirements -
		 * 	when manually adding an incident, the incident is restricted by default,
		 * 	associate the user creating the incident as the restrictedIncidentUser owner
		 */
		if(!isCourseOfActionComplete(dialogueVo, _RULE_NAME)){
			
			if(super.isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				// add to processed
				dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
				dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
				
			}else{

				/*
				 * When saving new incident, 
				 * mark coa as additional action needed.
				 * 
				 * executePostProcessActions will need to restrict the incident
				 */
				if( !LongUtility.hasValue(vo.getId()))
					dialogueVo.getProcessedCourseOfActionVos().add(super.buildAdditionalActionCoaVo(_RULE_NAME,true));
				
			}
		}
		
		
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa && coa.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
			if(null != incidentEntity){
				
				incidentEntity.setRestricted(true);
				
				RestrictedIncidentUser riu = new RestrictedIncidentUserImpl();
				
				User user = new UserImpl();
				user.setId(super.getUserVo().getId());
				riu.setUser(user);
				riu.setUserType(RestrictedIncidentUserTypeEnum.OWNER);
				riu.setIncident(incidentEntity);
				
				incidentEntity.setRestrictedIncidentUsers(new ArrayList<RestrictedIncidentUser>());
				incidentEntity.getRestrictedIncidentUsers().add(riu);
				
				IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
				
				incidentDao.save(incidentEntity);
				
			}
		}
		
	}

}
