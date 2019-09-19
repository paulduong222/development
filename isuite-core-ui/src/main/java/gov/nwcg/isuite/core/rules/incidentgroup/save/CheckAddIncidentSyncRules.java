package gov.nwcg.isuite.core.rules.incidentgroup.save;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.PriorityProgramDao;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckAddIncidentSyncRules extends AbstractIncidentGroupSaveRule implements IRule{
	public static final String _RULE_NAME=IncidentGroupSaveRuleFactory.RuleEnum.CHECK_ADD_INCIDENT_SYNC.name();
	private Collection<Long> incidentIds = new ArrayList<Long>();
	
	public CheckAddIncidentSyncRules(ApplicationContext ctx, String rname)
	{
		super(ctx,rname);
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
			if(runRuleCheck(dialogueVo) == _FAIL) 
				return _FAIL;
			
			dialogueVo.getProcessedCourseOfActionVos().add(super.buildNoActionCoaVo(ruleName, true));
			
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
		
		if(hasNewIncidents()){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			coaVo.setStoredObject(this.incidentIds);
			dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
		}
		
		return _OK;
	}

	
	private Boolean hasNewIncidents() {
		if(null != entity){
			for(IncidentVo ivo : newVo.getIncidentVos()){
				boolean bFound = false;
				
				for(Incident i : entity.getIncidents()){
					if(ivo.getId().compareTo(i.getId())==0)
						bFound=true;
				}
				
				if(bFound==false){
					this.incidentIds.add(ivo.getId());
				}
			}
		}else{
			for(IncidentVo ivo : newVo.getIncidentVos()){
				this.incidentIds.add(ivo.getId());
			}
		}

		if(CollectionUtility.hasValue(incidentIds))
			return true;
		else
			return false;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		if(coaVo != null && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			Long incidentGroupId = super.entity.getId();
			PriorityProgramDao ppDao = (PriorityProgramDao)context.getBean("priorityProgramDao");

			Collection<Long> newIncidentIds = (Collection<Long>)coaVo.getStoredObject();
			for(Long i : newIncidentIds){
				Collection<PriorityProgramVo> vos2 =ppDao.getGrid(i, null);
				for(PriorityProgramVo v : vos2){
					ppDao.syncNewFromIncident(v.getCode(), incidentGroupId);
				}
			}
			
			
			Collection<PriorityProgramVo> vos =ppDao.getGrid(null, incidentGroupId);
			if(CollectionUtility.hasValue(vos)){
				for(PriorityProgramVo v : vos){
					ppDao.syncNewWithGroup(v.getCode(), incidentGroupId);
				}
			}

		}

	}

}
	
