package gov.nwcg.isuite.core.rules.incidentgroup.save;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IapMasterFrequencyDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class MoveIAPMasterFrequenceyListRules extends AbstractIncidentGroupSaveRule implements IRule{
	public static final String _RULE_NAME=IncidentGroupSaveRuleFactory.RuleEnum.MOVE_IAP_MASTER_FREQUENCY_LIST.name();

	public MoveIAPMasterFrequenceyListRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{

			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;
			
					
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;

			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(_RULE_NAME,true));
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * If saving a new incident group, 
		 *  create default incident group iap settings
		 */
		
		if (!LongUtility.hasValue(newVo.getId())) 
			dialogueVo.getProcessedCourseOfActionVos().add(super.buildAdditionalActionCoaVo(_RULE_NAME,true));
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa && coa.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
			if(null != super.entity){
				
				Long incidentGroupId = super.entity.getId();
				
				// Create two collections:
				// 1. List of newly added incidents to this incident group
				// 2. List of incidents removed from this incident group. 
				List<Long> addedIncidents = new ArrayList<Long>();
				List<Long> removedIncidents = new ArrayList<Long>();
				
				if(LongUtility.hasValue(newVo.getId())){ // verify how to use this line
					// Iterate to find newlly added incidents
					for(IncidentVo newIncidentVo : newVo.getIncidentVos()){
						Boolean newlyAddedIncident = true;
						
						for(IncidentVo originalIncidentVo : originalVo.getIncidentVos()) {
							if(originalIncidentVo.getId().compareTo(newIncidentVo.getId())==0){
								newlyAddedIncident = false;
								break;
							}
						}
						
						if(newlyAddedIncident) {
							addedIncidents.add(newIncidentVo.getId());
						}
					}
					
					// Iterate to find recently removed incidents
					for(IncidentVo oldIncidentVo : originalVo.getIncidentVos()){
						Boolean recentlyRemovedIncident = true;
						
						for(IncidentVo newIncidentVo : newVo.getIncidentVos()) {
							if(oldIncidentVo.getId().compareTo(newIncidentVo.getId())==0){
								recentlyRemovedIncident = false;
								break;
							}
						}
					
						if(recentlyRemovedIncident) {
							removedIncidents.add(oldIncidentVo.getId());
						}
					}
					
					IapMasterFrequencyDao dao = (IapMasterFrequencyDao)context.getBean("iapMasterFrequencyDao");
					
					// For newly added incidents to an incident group: 
					for(Long addedIncidentId: addedIncidents) {
						dao.transferMFLFromIncidentToIncidentGroup(addedIncidentId, incidentGroupId);
					}
					
					// For incidents removed from an incident group:
					for(Long removedIncidentId: removedIncidents) {
						dao.copyMFLFromIncidentGroupToIncident(removedIncidentId, incidentGroupId);
					}
				}
			}
		}
	}

}
