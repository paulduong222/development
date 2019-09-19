package gov.nwcg.isuite.core.rules.rossimport;

import gov.nwcg.isuite.core.persistence.RossImportProcessDao;
import gov.nwcg.isuite.core.vo.RossImportProcessEISuiteResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.rossimport.MatchIncidentsWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.UnmatchedResourcesWizardVo;
import gov.nwcg.isuite.framework.core.rules.IWizardRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class UnmatchedResourcesRules extends AbstractRossImportRule implements IWizardRule{
	private static final String _RULE_NAME="UNMATCHED_RESOURCES";

	public UnmatchedResourcesRules(ApplicationContext ctx){
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
			
			if(buildResourceUnMatches(dialogueVo)){
				
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
				
			if(super.isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				/*
				 * add rule to processedCoa's
				 */
				dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
				dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		
				return _OK;
			}

			/*
			 * Build the wizard data
			 */
			if(this.validateProceedThisWizard(dialogueVo)){
				if(buildWizardData(dialogueVo)==_FAIL)
					return _FAIL;
			}else{
				
				// skip the rule
				dialogueVo.getProcessedCourseOfActionVos().add(
						super.buildNoActionCoaVo(_RULE_NAME, false));
				return _OK;
			}
			
			
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
		 * Determine if there any unmatched resources from the ross file
		 */
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

		if(MatchIncidentsWizardVo.hasEISuiteIncidentMatch(ripVo.getMatchIncidentsWizardVo())){
			
			if(buildResourceUnMatches(dialogueVo)){
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
		}else{
			// skip the rule
			dialogueVo.getProcessedCourseOfActionVos().add(
					super.buildNoActionCoaVo(_RULE_NAME, false));
			return _OK;
			/*
			if(buildResourceUnMatches(dialogueVo)){
				
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
			*/
		}

		// skip the rule
		dialogueVo.getProcessedCourseOfActionVos().add(
				super.buildNoActionCoaVo(_RULE_NAME, false));
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
	private Boolean buildResourceUnMatches(DialogueVo dialogueVo) throws Exception{
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		Long incidentId=ripVo.getMatchIncidentsWizardVo().getMatchingIncidentId();
		RossImportProcessDao ripDao = (RossImportProcessDao)super.context.getBean("rossImportProcessDao");

		// Get the list of resources for the incident in eisuite
		Collection<RossImportProcessEISuiteResourceVo> eisuiteResources = ripDao.getEISuiteResources(incidentId);
		
		HashMap<Long,RossImportProcessResourceVo> rossMap = RossImportProcessResourceVo.toHashMapEisuiteId(ripVo.getRossImportProcessResourceVos());
	
		Collection<RossImportProcessResourceVo> ripresvos = new ArrayList<RossImportProcessResourceVo>();
		Collection<RossImportProcessEISuiteResourceVo> eresvos = new ArrayList<RossImportProcessEISuiteResourceVo>();
		
		/*
		 * Determine if there are ross resources not yet matched and not excluded.
		 */
		for(RossImportProcessResourceVo ripResVo : ripVo.getRossImportProcessResourceVos()){
			if(!ripResVo.getExcludeResource() && 
					!LongUtility.hasValue(ripResVo.getEisuiteResourceId())){

				if(!UnmatchedResourcesWizardVo.hasVo(ripVo.getUnmatchedResourcesWizardVo().getRossUnmatchedVos(), ripResVo)){
					if(ripResVo.getRequestNumber().equals("S-52")){
						System.out.println("");
					}
					ripVo.getUnmatchedResourcesWizardVo().getRossUnmatchedVos().add(ripResVo);
					ripresvos.add(ripResVo);
				}
			}
		}

		/*
		 * Determine if there are any eisuite resources not yet matched 
		 */
		for(RossImportProcessEISuiteResourceVo eisuiteVo : eisuiteResources){
			//System.out.println("Checking " + eisuiteVo.getResourceId());
			
			if(!rossMap.containsKey(eisuiteVo.getResourceId()) &&
					!UnmatchedResourcesWizardVo.hasEisuiteVo(ripVo.getUnmatchedResourcesWizardVo().getEisuiteUnmatchedVos(), eisuiteVo)){
			//	System.out.println("Adding " + eisuiteVo.getResourceId());
				if(eisuiteVo.getRequestNumber().equals("S-52")){
					System.out.println("");
				}
				if(!LongUtility.hasValue(eisuiteVo.getRossResReqId())){
					ripVo.getUnmatchedResourcesWizardVo().getEisuiteUnmatchedVos().add(eisuiteVo);
					eresvos.add(eisuiteVo);
				}
			}
		}
		
		if(ripVo.getUnmatchedResourcesWizardVo().getRossUnmatchedVos().size()>0){
			// update the rossimportprocessVo in dialogue.resultObject
			if(CollectionUtility.hasValue(ripresvos)){
				((RossImportProcessVo)dialogueVo.getResultObject()).getUnmatchedResourcesWizardVo().setRossUnmatchedVos(ripresvos);
			}
			if(CollectionUtility.hasValue(eresvos)){
				((RossImportProcessVo)dialogueVo.getResultObject()).getUnmatchedResourcesWizardVo().setEisuiteUnmatchedVos(eresvos);
			}
			//dialogueVo.setResultObject(ripVo);
			return true;
		}else
			return false;
	}

	private void removeAlreadyMatched(DialogueVo dialogueVo) throws Exception {
		// disregard for now, they will just show has matched in the wizard
	}
	
}
