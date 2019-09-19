package gov.nwcg.isuite.core.rules.incidentgroup.addincident;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class AbstractAddIncidentRule extends AbstractRule {
	protected IncidentGroupVo newVo=null;
	protected IncidentGroupDao incidentGroupDao=null;
	protected IncidentGroupVo originalVo=null;
	protected Collection<IncidentVo> addedIncidents = new ArrayList<IncidentVo>();
	protected Collection<IncidentVo> removedIncidents = new ArrayList<IncidentVo>();
	
	public AbstractAddIncidentRule(ApplicationContext ctx){
		context=ctx;
	}

	public AbstractAddIncidentRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentGroupAddIncidentRuleFactory.ObjectTypeEnum.NEW_IG_VO.name()))
			newVo = (IncidentGroupVo)object;
		if(objectName.equals(IncidentGroupAddIncidentRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_DAO.name()))
			incidentGroupDao = (IncidentGroupDao)object;
		if(objectName.equals(IncidentGroupAddIncidentRuleFactory.ObjectTypeEnum.ORIGINAL_IG_VO.name()))
			originalVo = (IncidentGroupVo)object;
	}
	
	@Override
	public void addAdditionalMessages(DialogueVo dialogueVo) throws Exception {

		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(ruleName);

		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			if(null != dialogueVo.getCourseOfActionVo().getMessageVo()){
				dialogueVo.getCourseOfActionVo().getMessageVo().getAdditionalMessageVos().add(coaVo.getMessageVo());
			}
		}
	}

	public void findAddedIncidents() {
		Collection<IncidentVo> originalIncidents = null;
		if(originalVo != null) {
			originalIncidents = originalVo.getIncidentVos();
		}
		
		Boolean found;
		if(originalIncidents != null && originalIncidents.size() > 0) {
			if(newVo.getIncidentVos() != null) {
				for(IncidentVo igiVo : newVo.getIncidentVos()) {
					found = false;
					for(IncidentVo inc : originalIncidents) {
						if(igiVo.getId().equals(inc.getId())) {
							found = true;
							break;
						}
					}
					if(!found) {
						addedIncidents.add(igiVo);
					}
				}
			}
		} else {
			if(newVo.getIncidentVos() != null) {
				addedIncidents.addAll(newVo.getIncidentVos());
			}
		}
	}
	
	public void findRemovedIncidents() {
		Collection<IncidentVo> originalIncidents = null;
		if(originalVo != null) {
			originalIncidents = originalVo.getIncidentVos();
		}
		
		Boolean found;
		if(originalIncidents != null && originalIncidents.size() > 0) {
			for(IncidentVo inc : originalIncidents) {
				found = false;
				if(newVo.getIncidentVos() != null) {
					for(IncidentVo igiVo : newVo.getIncidentVos()) {
						if(igiVo.getId().equals(inc.getId())) {
							found = true;
							break;
						}
					}
				}
				if(!found) {
					removedIncidents.add(inc);
				}
			}
		}
	}

}
