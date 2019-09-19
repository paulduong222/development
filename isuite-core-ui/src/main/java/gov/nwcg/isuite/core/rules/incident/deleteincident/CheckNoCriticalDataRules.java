package gov.nwcg.isuite.core.rules.incident.deleteincident;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import java.math.BigDecimal;

import org.springframework.context.ApplicationContext;

public class CheckNoCriticalDataRules extends AbstractRule implements IRule{
	public static final String _RULE_NAME=DeleteIncidentRuleFactory.RuleEnum.CHECK_NO_CRITICAL_DATA.name();

	private IncidentVo vo=null;
	private Incident incidentEntity=null;
	
	public CheckNoCriticalDataRules(ApplicationContext ctx)
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
					 *   3.	If an Incident is manually added to the system, 
					 *      the system must allow the user to delete that Incident 
					 *      and all associated data from the system, 
					 *      unless the user entered any critical data for the incident 
					 *      (e.g., Time Postings, Injury/Illness Records, Issued Supplies). 
					 *      If the user entered critical data for that Incident, 
					 *      the system must prevent the user from deleting that Incident, 
					 *      unless the critical data is removed from the Incident.  
					 *      
					 *      When the user attempts to delete an Incident that has 
					 *      critical data associated with it, the Incident is not deleted 
					 *      and a message displays. [Message 0054]
					 */
					
					if(this.checkForTimePostings(dialogueVo) == _FAIL) {
						dialogueVo = this.buildDialogVo(dialogueVo, "Time Postings");
						return _FAIL;
					}
					
					if(this.checkForInjuryIllness(dialogueVo) == _FAIL) {
						dialogueVo = this.buildDialogVo(dialogueVo, "Injury/Illness Records");
						return _FAIL;
					}
					
					if(this.checkForIssuedSupplies(dialogueVo) == _FAIL) {
						dialogueVo = this.buildDialogVo(dialogueVo, "Issued Supplies");
						return _FAIL;
					}
				}
			
			}

		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	private DialogueVo buildDialogVo(DialogueVo dialogueVo, String criticalDataElement) {
		MessageVo messageVo = new MessageVo(
				"text.incidents", 
				"error.0054", 
				new String[]{vo.getIncidentNumber(), vo.getIncidentName(), criticalDataElement}, 
				MessageTypeEnum.CRITICAL);
		CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
		courseOfActionVo.setMessageVo(messageVo);
		courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		dialogueVo.setCourseOfActionVo(courseOfActionVo);
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

	/**
	 * Returns whether or not the incident has time postings for resources.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int checkForTimePostings(DialogueVo dialogueVo) throws ServiceException {
		try {

			IncidentDao incidentDao = (IncidentDao)super.context.getBean("incidentDao");
			int numTimePostings = incidentDao.checkForTimePostings(incidentEntity.getId());
			if(numTimePostings > 0) {
				return _FAIL;
			}

		}catch(Exception e) {
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * Returns whether or not the incident has injury illness records.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int checkForInjuryIllness(DialogueVo dialogueVo) {
		return _OK;
	}
	
	/**
	 * Returns whether or not the incident has active issued supplies.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int checkForIssuedSupplies(DialogueVo dialogueVo) {
		return _OK;
	}
}
