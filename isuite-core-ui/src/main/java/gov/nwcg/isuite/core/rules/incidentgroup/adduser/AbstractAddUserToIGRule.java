package gov.nwcg.isuite.core.rules.incidentgroup.adduser;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.IncidentGroupUserVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class AbstractAddUserToIGRule extends AbstractRule {
	protected IncidentGroupVo newVo;
	protected IncidentGroupVo originalVo;
	protected Collection<IncidentGroupUserVo> addedUsers = new ArrayList<IncidentGroupUserVo>(); 
	protected Collection<IncidentGroupUserVo> removedUsers = new ArrayList<IncidentGroupUserVo>();
	protected IncidentGroupDao incidentGroupDao;
	
	public AbstractAddUserToIGRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/**
	 * @param object
	 * @param objectName
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentGroupAddUserRuleFactory.ObjectTypeEnum.NEW_IG_VO.name()))
			newVo = (IncidentGroupVo)object;
		if(objectName.equals(IncidentGroupAddUserRuleFactory.ObjectTypeEnum.ORIGINAL_IG_VO.name()))
			originalVo = (IncidentGroupVo)object;
		if(objectName.equals(IncidentGroupAddUserRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_DAO.name()))
			incidentGroupDao = (IncidentGroupDao)object;
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

	public void findAddedUsers() {
		Collection<IncidentGroupUserVo> originalUsers = null;
		if(originalVo != null)
			originalUsers = originalVo.getIncidentGroupUsers();
		
		Boolean found;
		if(originalUsers != null && originalUsers.size() > 0) {
			
			for(IncidentGroupUserVo uVo : newVo.getIncidentGroupUsers()) {
				found = false;
				for(IncidentGroupUserVo user : originalUsers) {
					if(uVo.getUserVo().getId().equals(user.getUserVo().getId())) {
						found = true;
						break;
					}
				}
				if(!found) {
					addedUsers.add(uVo);
				}
			}
		} else {
			addedUsers.addAll(newVo.getIncidentGroupUsers());
		}
	}
	
	public void findRemovedUsers() {
		Collection<IncidentGroupUserVo> originalUsers = null;
		if(originalVo != null)
			originalUsers = originalVo.getIncidentGroupUsers();
		
		Boolean found;
		if(originalUsers != null && originalUsers.size() > 0) {
			for(IncidentGroupUserVo user : originalUsers) {
				found = false;
				for(IncidentGroupUserVo uVo : newVo.getIncidentGroupUsers()) {
					if(uVo.getUserVo().getId().equals(user.getUserVo().getId())) {
						found = true;
						break;
					}
				}
				if(!found) {
					removedUsers.add(user);
				}
			}
		}
	}
	
}
