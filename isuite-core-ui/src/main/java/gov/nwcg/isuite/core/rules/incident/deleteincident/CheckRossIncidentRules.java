package gov.nwcg.isuite.core.rules.incident.deleteincident;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.RossIncDataBlacklistDao;
import gov.nwcg.isuite.core.persistence.RossXmlFileDao;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class CheckRossIncidentRules extends AbstractRule implements IRule{
	public static final String _RULE_NAME=DeleteIncidentRuleFactory.RuleEnum.CHECK_IF_ROSS_INCIDENT.name();

	private IncidentVo vo=null;
	private Incident incidentEntity=null;
	
	public CheckRossIncidentRules(ApplicationContext ctx)
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
					 *   5.	If an Incident in the e-ISuite Enterprise System was obtained 
					 *      from an external system or was matched with an Incident in an  
					 *      external system (e.g., ROSS), the system must prevent the user 
					 *      from deleting that Incident from the e-ISuite Enterprise System.
					 *      
					 *      When the user attempts to delete an Incident on the e-ISuite 
					 *      Enterprise System that was matched with an Incident in an external system 
					 *      or was obtained from an external system, 
					 *      the Incident is not deleted and a message displays. [Message 0055]
					 *      
					 */
					/*
					 * djp- 05/22/2012, clients indicated this rule is no longer valid
					 */
					/*
					if(StringUtility.hasValue(incidentEntity.getRossIncidentId())) {
						if(NumberUtils.isNumber(incidentEntity.getRossIncidentId()) && Integer.valueOf(incidentEntity.getRossIncidentId()) > 0) {
							MessageVo messageVo = new MessageVo("text.incidents", "error.0055", new String[]{vo.getIncidentNumber(), vo.getIncidentName()}, MessageTypeEnum.CRITICAL);
							CourseOfActionVo coaVo = new CourseOfActionVo();
							coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
							coaVo.setMessageVo(messageVo);
							dialogueVo.setCourseOfActionVo(coaVo);

							return _FAIL;
						}
					}
					*/
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
		if(StringUtility.hasValue(incidentEntity.getRossIncidentId())) {
			// delete ross inc blacklist records
			RossIncDataBlacklistDao dao = (RossIncDataBlacklistDao)super.context.getBean("rossIncDataBlacklistDaoTarget");
			dao.deleteByRossIncidentId(incidentEntity.getRossIncidentId());
			
			RossXmlFileDao rxfDao = (RossXmlFileDao)context.getBean("rossXmlFileDao");
			rxfDao.deleteByRossIncidentId(incidentEntity.getRossIncidentId());
			
		}
		
	}

}
