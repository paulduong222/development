package gov.nwcg.isuite.core.rules.rossimport;

import gov.nwcg.isuite.core.vo.RossImportProcessEISuiteResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.rossimport.MatchByRequestNumberAndNameWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.MatchIncidentsWizardVo;
import gov.nwcg.isuite.framework.core.rules.IWizardRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class MatchResourcesByReqNbrRules extends AbstractRossImportRule implements IWizardRule{
	private static final String _RULE_NAME="MATCH_RESOURCES_RQ_NBR";

	public MatchResourcesByReqNbrRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.rules.rossimport.AbstractRossImportRule#validateProceedThisWizard(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public boolean validateProceedThisWizard(DialogueVo dialogueVo) throws Exception{
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

		if(MatchIncidentsWizardVo.hasEISuiteIncidentMatch(ripVo.getMatchIncidentsWizardVo())){
			if(CollectionUtility.hasValue(ripVo.getRossImportProcessResourceVos())){
				CourseOfActionVo coavo = dialogueVo.getCourseOfActionByName(MatchResourcesByReqNbrAndNameRules._RULE_NAME.toString());
				if(null != coavo && coavo.getCoaType() != CourseOfActionTypeEnum.NOACTION){
					/*
					 * Verify confirmMatches = true
					 */
					if(!ripVo.getMatchByRequestNumberAndNameWizardVo().getConfirmMatches()){
						dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						dialogueVo.getCourseOfActionVo().setMessageVo(new MessageVo("text.rossImport","info.generic",new String[]{"You must confirm matches before continuing."},MessageTypeEnum.CRITICAL));
						
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IWizardRule#syncData(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int syncData(DialogueVo dialogueVo) throws Exception {
	
		/*
		 * Synchronize the wizard data
		 */
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		
		if(MatchIncidentsWizardVo.hasEISuiteIncidentMatch(ripVo.getMatchIncidentsWizardVo())){
			
			this.removeAlreadyMatched(dialogueVo);
			
			if(buildResourceMatches(dialogueVo)){
				// navigate to match req num / name 
				
				// create a navigate courseofaction
				CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
				coaVo.setNavigateVo(
						buildNavigateVo(
								RossImportProcessRuleFactory.getRuleDestinationByName(_RULE_NAME)));
				dialogueVo.setCourseOfActionVo(coaVo);
			}else{
				CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.NOACTION);
				DialogueVo.updateCourseOfAction(dialogueVo, coaVo);
				return _FAIL;
			}
		}else{
			CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.NOACTION);
			DialogueVo.updateCourseOfAction(dialogueVo, coaVo);
			return _FAIL;
		}
		
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			/*
			 * if rule check has been completed, return
			 */
			if( (isCourseOfActionComplete(dialogueVo, _RULE_NAME))
					|| super.isCourseOfActionNoAction(dialogueVo, _RULE_NAME))
				return _OK;
				
			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				/*
				 * add rule to processedCoa's
				 */
				dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
				dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

				updateRossImportProcessVo(dialogueVo);
				
				return _OK;
			}
		
			if(this.validateProceedThisWizard(dialogueVo)){
				if(buildWizardData(dialogueVo)==_FAIL)
					return _FAIL;
			}else
				return _FAIL;
			
		}catch(Exception e){
			throw e;
		}
		
		return _OK;
	}

	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int buildWizardData(DialogueVo dialogueVo) throws Exception {
		/*
		 * Is there one already in the processedCoa's but not complete?
		 */
		CourseOfActionVo cvo = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		if(null != cvo){
			cvo.setCoaName(_RULE_NAME);
			cvo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
			cvo.setNavigateVo(
					buildNavigateVo(
							RossImportProcessRuleFactory.getRuleDestinationByName(_RULE_NAME)));
			dialogueVo.setCourseOfActionVo(cvo);
			return _FAIL;
		}
		
		/*
		 * Determine if this rule should be skipped?
		 * If the ross incident was not matched, then there
		 * no e-isuite resources to match against (skip rule).
		 */
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		
		if(MatchIncidentsWizardVo.hasEISuiteIncidentMatch(ripVo.getMatchIncidentsWizardVo())){
			
			if(buildResourceMatches(dialogueVo)){
				// navigate to match req num / name 
				
				// create a navigate courseofaction
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
				coaVo.setNavigateVo(
						buildNavigateVo(
								RossImportProcessRuleFactory.getRuleDestinationByName(_RULE_NAME)));
				dialogueVo.setCourseOfActionVo(coaVo);
				return _FAIL;
			}
		}

		// skip the rule
		dialogueVo.getProcessedCourseOfActionVos().add(
				super.buildNoActionCoaVo(_RULE_NAME, false));
	
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @throws Exception
	 */
	private void updateRossImportProcessVo(DialogueVo dialogueVo) throws Exception {
		/*
		 * Update ripvo.ripResourceVos with the match eisuite id
		 */
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		
		/*
		 * loop through collections and build hashmaps 
		 * for faster searches
		 */
		HashMap<Long,RossImportProcessResourceVo> ripResVoMap = RossImportProcessResourceVo.toHashMapRossResReqId(ripVo.getRossImportProcessResourceVos());
		//HashMap<Long,RossImportProcessResourceVo> ripMatchResVoMap = RossImportProcessResourceVo.toHashMapRossResReqId(ripVo.getMatchByRequestNumberAndNameWizardVo().getRossMatchReqNumNameVos());
		
		for(RossImportProcessResourceVo ripResMatchVo : ripVo.getMatchByRequestNumberWizardVo().getRossMatchReqNumVos()){

			if(LongUtility.hasValue(ripResMatchVo.getEisuiteResourceId())){
				
				if(ripResVoMap.containsKey(ripResMatchVo.getRossResReqId())){
					
					RossImportProcessResourceVo mapVo = ripResVoMap.get(ripResMatchVo.getRossResReqId());
					mapVo.setEisuiteResourceId(ripResMatchVo.getEisuiteResourceId());
				
					ripResVoMap.put(ripResMatchVo.getRossResReqId(), mapVo);
				}
			}else{
				ripResMatchVo.setEisuiteResourceId(null);
				ripResVoMap.put(ripResMatchVo.getRossResReqId(), ripResMatchVo);
			}
		}
		
		if(ripResVoMap.values().size()>0){
			Collection<RossImportProcessResourceVo> resourceVos = ripResVoMap.values();
			
			((RossImportProcessVo)dialogueVo.getResultObject()).setRossImportProcessResourceVos(resourceVos);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
	/**
	 * @param incidentId
	 * @return
	 */
	private Boolean buildResourceMatches(DialogueVo dialogueVo) throws Exception{
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		//Long incidentId=ripVo.getMatchIncidentsWizardVo().getMatchingIncidentId();

		// Get the list of resources for the incident in eisuite
		HashMap<String,RossImportProcessEISuiteResourceVo> reqNumMap = RossImportProcessEISuiteResourceVo.toHashMapReqNum(ripVo.getRossImportProcessEisuiteVos());
		
		/*
		 * Determine if there are ross resources not yet matched and not excluded.
		 */
		for(RossImportProcessResourceVo ripResVo : ripVo.getRossImportProcessResourceVos()){

			if(StringUtility.hasValue(ripResVo.getRequestNumber())){

				if(!ripResVo.getExcludeResource() && 
						!LongUtility.hasValue(ripResVo.getEisuiteResourceId())){
					
					if(ripResVo.getRequestNumber().equals("O-87")){
						System.out.println("");
					}
					
					// This resource has not been matched yet
					// Determine if we have a possible match
					if(reqNumMap.containsKey(ripResVo.getRequestNumber())){
						
						// add both to wizard data if not already added
						if(!MatchByRequestNumberAndNameWizardVo.exists(
															ripResVo
															, ripVo.getMatchByRequestNumberWizardVo().getRossMatchReqNumVos())){
							
							RossImportProcessEISuiteResourceVo eisuiteVo = reqNumMap.get(ripResVo.getRequestNumber());
							if(!LongUtility.hasValue(eisuiteVo.getRossResReqId())){
								eisuiteVo.setRossResReqId(ripResVo.getRossResReqId());
								ripVo.getMatchByRequestNumberWizardVo().getRossMatchReqNumVos().add(ripResVo);
								ripVo.getMatchByRequestNumberWizardVo().getEisuiteMatchReqNumVos().add(eisuiteVo);
							}
						}
					}
				}
			}
		}
		
		if(ripVo.getMatchByRequestNumberWizardVo().getRossMatchReqNumVos().size()>0){
			// update the rossimportprocessVo in dialogue.resultObject
			dialogueVo.setResultObject(ripVo);
			return true;
		}else
			return false;
	}
	
	private void removeAlreadyMatched(DialogueVo dialogueVo) throws Exception {
		// disregard for now, they will just show has matched in the wizard
		
	}
	
}
