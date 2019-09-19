package gov.nwcg.isuite.core.rules.incidentgroup.save;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class AbstractIncidentGroupSaveRule extends AbstractRule {
	protected IncidentGroupVo newVo=null;
	protected IncidentGroupVo originalVo = null;
	protected IncidentGroupDao incidentGroupDao=null;
	protected IncidentGroup entity=null;
	
	public AbstractIncidentGroupSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/**
	 * @param object
	 * @param objectName
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentGroupSaveRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_DAO.name()))
			incidentGroupDao = (IncidentGroupDao)object;
		if(objectName.equals(IncidentGroupSaveRuleFactory.ObjectTypeEnum.NEW_INCIDENT_GROUP_VO.name()))
			newVo = (IncidentGroupVo)object;
		if(objectName.equals(IncidentGroupSaveRuleFactory.ObjectTypeEnum.ORIGINAL_ENTITY_VO.name()))
			originalVo = (IncidentGroupVo)object;
		if(objectName.equals(IncidentGroupSaveRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_ENTITY.name()))
			entity = (IncidentGroup)object;
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
}
