package gov.nwcg.isuite.core.rules.incidentgroup.save;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.IncidentRefDataConflictVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.CustomPromptVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckAddIncidentReferenceDataConflictsRules extends AbstractIncidentGroupSaveRule implements IRule{
	
	public CheckAddIncidentReferenceDataConflictsRules(ApplicationContext ctx, String rname)
	{
		super(ctx,rname);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;

			if(isCurrentCourseOfAction(dialogueVo, ruleName)){

				// add to processed
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				return checkPromptResult(dialogueVo);
				
			}else{
				/*
				 * Run rule check
				 */
				if(runRuleCheck(dialogueVo)==_FAIL)
					return _FAIL;
				
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
		 *	  When a user adds an Incident to an Incident Group, 
		 *    the system must determine if there are conflicts with 
		 *    reference data associated with resources in the incident(s) being added.
		 */
		if(hasNewIncidents()){
			Collection<IncidentRefDataConflictVo> conflictVos = new ArrayList<IncidentRefDataConflictVo>();
			Collection<KindVo> kinds = super.getGlobalCacheVo().getKindVos();
			Collection<AgencyVo> agencies=super.getGlobalCacheVo().getAgencyVos();
			
			KindVo kindVo=null;
			AgencyVo agencyVo=null;
			
			IncidentRefDataConflictVo vo1=new IncidentRefDataConflictVo();
			vo1.setIncidentName("BULL FLAT");
			vo1.setConflictDescription("Item Code");
			vo1.setSourceConflictingType("KINDCODE");
			vo1.setSourceConflictingValue("FFT1");
			kindVo = KindVo.getByCode("FFT1", kinds);
			vo1.setKindVo(kindVo);
			
			IncidentRefDataConflictVo vo2=new IncidentRefDataConflictVo();
			vo2.setIncidentName("BULL FLAT");
			vo2.setSourceConflictingType("KINDCODE");
			vo2.setSourceConflictingValue("FFT2");
			vo2.setConflictDescription("Item Code");
			kindVo = KindVo.getByCode("FFT2", kinds);
			vo2.setKindVo(kindVo);
			
			IncidentRefDataConflictVo vo3=new IncidentRefDataConflictVo();
			vo3.setIncidentName("BULL FLAT");
			vo3.setConflictDescription("Agency Code");
			vo3.setSourceConflictingType("AGENCYCODE");
			vo3.setSourceConflictingValue("BLM");
			agencyVo = new AgencyVo();
			agencyVo.setId(3L);
			agencyVo.setAgencyCd("BLM");
			vo3.setAgencyVo(agencyVo);

			conflictVos.add(vo1);
			conflictVos.add(vo2);
			conflictVos.add(vo3);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(ruleName);
			coaVo.setCoaType(CourseOfActionTypeEnum.CUSTOMPROMPT);
			coaVo.setCustomPromptVo(new CustomPromptVo("INCIDENT_REF_DATA_CONFLICTS","text.incidentGroup" ,"action.none",conflictVos));
			
			dialogueVo.setCourseOfActionVo(coaVo);

			return _FAIL;
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
				
				if(bFound==false)
					return true;
			}
		}

		return false;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {

		if(getCustomPromptResult(dialogueVo) == PromptVo._OK) {

			// continue
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getCourseOfActionVo().setIsComplete(true);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else if(getCustomPromptResult(dialogueVo) == PromptVo._CANCEL){
			
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			/*
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentGroup", "text.abortProcess" , new String[]{"post adjustment"}, MessageTypeEnum.INFO));
			dialogueVo.setCourseOfActionVo(coaVo);
	
			return _FAIL;
			*/
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
	
