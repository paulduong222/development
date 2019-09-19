package gov.nwcg.isuite.core.rules.incidentgroup.addincident;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckIncidentIsRestrictedRules extends AbstractAddIncidentRule implements IRule{
	public CheckIncidentIsRestrictedRules(ApplicationContext ctx, String rname)
	{
		super(ctx, rname);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;
					
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _OK) {
				/*
				 * Rule check passed, mark as completed
				 */
				dialogueVo.getProcessedCourseOfActionVos()
					.add(super.buildNoActionCoaVo(ruleName,true));
			}
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
		 * 
		 * B.R. 21.0003  
		 *     3.	The system must only allow the user to add 
		 *          restricted Incidents that are in the same Work Area 
		 *          to an Incident Group.
		 *          
		 *          3/9/12 - workarea check not required; can only add incidents that the user has access to
		 */
		findAddedIncidents();
		int value = _OK;
		IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
		for(IncidentVo vo : addedIncidents) {
			
			Incident inc=incidentDao.getById(vo.getId());
			if(null != inc && !inc.getRestricted()){
				value = _FAIL;
				((ArrayList<IncidentVo>)newVo.getIncidentVos()).remove(vo);
				
				CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(ruleName);
				MessageVo msgVo;
				if(coaVo == null) {
					coaVo = new CourseOfActionVo();
					coaVo.setCoaName(ruleName);
					coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
					coaVo.setIsComplete(true);
					
					dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
					
					msgVo = new MessageVo();
					msgVo.setMessageType(MessageTypeEnum.INFO);
					msgVo.setTitleProperty("text.incidentGroup");
					msgVo.setMessageProperty("info.itemNotAddedBecause");
					String msg = "\n" + vo.getIncidentName();
					msgVo.setParameters(new String[]{"Incident"
							,"Incident Group"
							,"only restricted incidents can be added to an incident group"
							,msg});
					
					coaVo.setMessageVo(msgVo);
				} else {
					msgVo = coaVo.getMessageVo();
					String msg = msgVo.getParameters()[3];
					msg = msg + "\n" + vo.getIncidentName();
					msgVo.getParameters()[3] = msg;
				}
			} else {
				Collection<RestrictedIncidentUser> rius = inc.getRestrictedIncidentUsers();
				Boolean found = false;
				for(RestrictedIncidentUser riu : rius) {
					if(riu.getUserId().equals(super.getUserVo().getId() )) {
						found = true;
						break;
					}
				}
				if(!found) {
					value = _FAIL;
					((ArrayList<IncidentVo>)newVo.getIncidentVos()).remove(vo);
					
					CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(ruleName);
					MessageVo msgVo;
					if(coaVo == null) {
						coaVo = new CourseOfActionVo();
						coaVo.setCoaName(ruleName);
						coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
						coaVo.setIsComplete(true);
						
						dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
						
						msgVo = new MessageVo();
						msgVo.setMessageType(MessageTypeEnum.INFO);
						msgVo.setTitleProperty("text.incidentGroup");
						msgVo.setMessageProperty("info.itemNotAddedBecause");
						String msg = "\n" + vo.getIncidentName();
						msgVo.setParameters(new String[]{"Incident"
								,"Incident Group"
								,"current user is not in the restricted incident user access list"
								,msg});
						
						coaVo.setMessageVo(msgVo);
					} else {
						msgVo = coaVo.getMessageVo();
						String msg = msgVo.getParameters()[2];
						msg = msg + "\n" + vo.getIncidentName();
						msgVo.getParameters()[2] = msg;
					}
				}
			}
			incidentDao.flushAndEvict(inc);
		}
		
		return value;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
