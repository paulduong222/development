package gov.nwcg.isuite.core.rules.incident.deleteincident;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckRestrictedOwnerRules extends AbstractRule implements IRule{
	public static final String _RULE_NAME=DeleteIncidentRuleFactory.RuleEnum.CHECK_IF_USER_IS_RESTRICTED_OWNER.name();

	private IncidentVo vo=null;
	private Incident incidentEntity=null;
	
	public CheckRestrictedOwnerRules(ApplicationContext ctx)
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

				if(super.isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
					
					// add to processed
					dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
					dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
				
				}else{

					/*
					 * B.R. 3.0005 
					 *   2.	The system must only allow owners 
					 *      of a Restricted incident to delete that incident.
					 */
					if(BooleanUtility.isTrue(incidentEntity.getRestricted())) {
						Collection<RestrictedIncidentUserVo> riuVos = this.vo.getRestrictedOwners();
						for(RestrictedIncidentUserVo riuVo : riuVos) {
							if(riuVo.getUserVo().getId().equals(super.getUserSessionVo().getUserId())) {
								return _OK;
							}
						}

						/*
						 *  turrning off 05/21/2012 - djp
						 */
						/*
						MessageVo messageVo = new MessageVo("text.incidents", "text.youMustBeAnRIOwner", null, MessageTypeEnum.CRITICAL);
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						coaVo.setMessageVo(messageVo);
						dialogueVo.setCourseOfActionVo(coaVo);
						
						return _FAIL;
						*/
					}
				}
			
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
