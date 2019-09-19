package gov.nwcg.isuite.core.rules.rossimport;

import gov.nwcg.isuite.core.persistence.RossImportProcessDao;
import gov.nwcg.isuite.core.vo.RossImportProcessDataErrorVo;
import gov.nwcg.isuite.core.vo.RossImportProcessEISuiteResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.rossimport.ExcludeResourceWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.MatchByRequestNumberAndNameWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.MatchIncidentsWizardVo;
import gov.nwcg.isuite.framework.core.rules.IWizardRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class MatchResourcesByReqNbrAndNameRules extends AbstractRossImportRule implements IWizardRule{
	public static final String _RULE_NAME="MATCH_RESOURCES_RQ_NBR_AND_NAME";

	public MatchResourcesByReqNbrAndNameRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
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

			/*
			 * Re-evaluate any excluded resources
			 */
			removeExcluded(dialogueVo);

			if(this.buildResourceMatches(dialogueVo)){
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

				RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

				/*
				 * Verify confirmMatches = true
				 */
				if(!ripVo.getMatchByRequestNumberAndNameWizardVo().getConfirmMatches()){
					dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					dialogueVo.getCourseOfActionVo().setMessageVo(new MessageVo("text.rossImport","info.generic",new String[]{"You must confirm matches before continuing."},MessageTypeEnum.CRITICAL));

					return _FAIL;
				}

				/*
				 * add rule to processedCoa's
				 */
				dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
				dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

				/*
				 * Update ripvo.ripResourceVos with the match eisuite id
				 */
				updateRossImportProcessVo(dialogueVo);

				return _OK;
			}


			if(buildWizardData(dialogueVo)==_FAIL)
				return _FAIL;
			else{
				//load the resource into ripvo
				this.loadInitialRossImportProcessVo(dialogueVo);
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
		 * If the ross incident was not matched, then there are
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
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

		/*
		 * loop through collections and build hashmaps 
		 * for faster searches
		 */
		HashMap<Long,RossImportProcessResourceVo> ripResVoMap = RossImportProcessResourceVo.toHashMapRossResReqId(ripVo.getRossImportProcessResourceVos());
		//HashMap<Long,RossImportProcessResourceVo> ripMatchResVoMap = RossImportProcessResourceVo.toHashMapRossResReqId(ripVo.getMatchByRequestNumberAndNameWizardVo().getRossMatchReqNumNameVos());

		for(RossImportProcessResourceVo ripResMatchVo : ripVo.getMatchByRequestNumberAndNameWizardVo().getRossMatchReqNumNameVos()){

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
		//Long incidentId=ripVo.getMatchIncidentsWizardVo().getMatchingIncidentId();

		RossImportProcessDao ripDao = (RossImportProcessDao)super.context.getBean("rossImportProcessDao");
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		Long incidentId = 0L;
		
		if(MatchIncidentsWizardVo.hasEISuiteIncidentMatch(ripVo.getMatchIncidentsWizardVo())){
			incidentId = MatchIncidentsWizardVo.getEISuiteIncidentId(ripVo.getMatchIncidentsWizardVo());
		}

		Collection<RossImportProcessResourceVo> vos = ripDao.getValidRossResources(this.rxfVo.getId(), incidentId);

		ripVo.setRossImportProcessResourceVos(vos);

		/*
		 * Sort through both rossResources and eisuiteResources,
		 * and determine which resources match each other based on
		 * (ross_res_id), request number and name
		 */
		for(RossImportProcessResourceVo ripResVo : ripVo.getRossImportProcessResourceVos()){

			if(!ExcludeResourceWizardVo.isExcluded(ripResVo.getRossResReqId(), ripVo.getExcludeResourceWizardVo().getExcludedRossImportProcessResourceVos())){

				/*
				 * Determine if we have a potential match in the incident resources
				 */
				for(RossImportProcessEISuiteResourceVo resourceVo : ripVo.getRossImportProcessEisuiteVos()){

					//System.out.println(ripResVo.getRequestNumber() + " " +
					//		resourceVo.getRequestNumber());
					
					if(ripResVo.getRequestNumber().equals("0-87")){
						System.out.println("");
					}
					
					boolean match=false;
					
					if(StringUtility.hasValue(ripResVo.getRequestNumber()) && 
							StringUtility.hasValue(resourceVo.getRequestNumber())){

						//if(ripResVo.getRequestNumber().equals("O-45")
						//		&& resourceVo.getRequestNumber().equals("O-45")){
						//	System.out.println("");
						//}
						
						String reqCatalogName=(StringUtility.hasValue(ripResVo.getRequestCatalogName()) ? ripResVo.getRequestCatalogName() : "" );
						String reqCategoryName=(StringUtility.hasValue(ripResVo.getRequestCategoryName()) ? ripResVo.getRequestCategoryName() : "" );

						String rossResourceName="";
						String eisuiteResourceName=resourceVo.getResourceName();
						
						if(reqCatalogName.equals("OVERHEAD") && (reqCategoryName.equals("POSITIONS") ) ){
							if(StringUtility.hasValue(ripResVo.getLastName()) && StringUtility.hasValue(ripResVo.getFirstName()) ){
								rossResourceName=ripResVo.getLastName().trim()+", "+ripResVo.getFirstName() + (StringUtility.hasValue(ripResVo.getMiddleName()) ? " " + ripResVo.getMiddleName().trim() : "");
							}else{
								if(StringUtility.hasValue(ripResVo.getResourceName())){
									rossResourceName=StringUtility.leftTrim(ripResVo.getResourceName(),55);
								}else
									rossResourceName="";
							}
						}else{
							if(StringUtility.hasValue(ripResVo.getResourceName())){
								rossResourceName=StringUtility.leftTrim(ripResVo.getResourceName(),55);
							}else
								rossResourceName="";
						}
						
						// check request number
						if(ripResVo.getRequestNumber().equalsIgnoreCase(resourceVo.getRequestNumber())){

							// check name
							boolean nameMatch=false;
							if(eisuiteResourceName.trim().equalsIgnoreCase(rossResourceName.trim()))
								nameMatch=true;
							
							if(nameMatch==true){
								match=true;
								
								ripResVo.setEisuiteResourceId(resourceVo.getResourceId());
								
								Boolean hasRossResReqId = (LongUtility.hasValue(resourceVo.getRossResReqId()) ? true : false );
								resourceVo.setRossResReqId(ripResVo.getRossResReqId());
	
								// add both to wizard data if not already added
								if(!MatchByRequestNumberAndNameWizardVo.exists(
										ripResVo
										, ripVo.getMatchByRequestNumberAndNameWizardVo().getRossMatchReqNumNameVos())){
									// and if ross req id is empty on the eisuite resource side
									if(BooleanUtility.isFalse(hasRossResReqId)){
										ripVo.getMatchByRequestNumberAndNameWizardVo().getRossMatchReqNumNameVos().add(ripResVo);
										ripVo.getMatchByRequestNumberAndNameWizardVo().getEisuiteMatchReqNumNameVos().add(resourceVo);
									}
								}
							}else{
								//System.out.println("");
							}

							//if(ripResVo.getRossResReqId().compareTo(resourceVo.getRossResReqId())==0){
							//	if(match==false){
									//System.out.println("");
							//	}
							//}
							break;

						}
					}      
				}
				//				for(RossImportProcessEISuiteResourceVo resourceVo : ripVo.getRossImportProcessEisuiteVos()){
				//					
				//					// check request number
				//					if(ripResVo.getRequestNumber().equalsIgnoreCase(resourceVo.getRequestNumber())){
				//						
				//						ripResVo.setEisuiteResourceId(resourceVo.getResourceId());
				//						
				//						resourceVo.setRossResReqId(ripResVo.getRossResReqId());
				//
				//						// add both to wizard data if not already added
				//						if(!MatchByRequestNumberAndNameWizardVo.exists(
				//															ripResVo
				//															, ripVo.getMatchByRequestNumberAndNameWizardVo().getRossMatchReqNumNameVos())){
				//							ripVo.getMatchByRequestNumberAndNameWizardVo().getRossMatchReqNumNameVos().add(ripResVo);
				//							ripVo.getMatchByRequestNumberAndNameWizardVo().getEisuiteMatchReqNumNameVos().add(resourceVo);
				//						}
				//					
				//						break;
				//					}
				//					
				//				}

			}
		}


		if(ripVo.getMatchByRequestNumberAndNameWizardVo().getRossMatchReqNumNameVos().size()>0){
			// update the rossimportprocessVo in dialogue.resultObject
			dialogueVo.setResultObject(ripVo);
			return true;
		}else
			return false;
	}

	private void removeExcluded(DialogueVo dialogueVo) throws Exception {
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

		Collection<RossImportProcessResourceVo> excludedResources = ripVo.getExcludeResourceWizardVo().getExcludedRossImportProcessResourceVos();	

		Collection<RossImportProcessResourceVo> newRipResourceVos = new ArrayList<RossImportProcessResourceVo>();
		Collection<RossImportProcessEISuiteResourceVo> newIsuiteResourceVos = new ArrayList<RossImportProcessEISuiteResourceVo>();

		for(RossImportProcessResourceVo ripResVo : ripVo.getMatchByRequestNumberAndNameWizardVo().getRossMatchReqNumNameVos()){
			Boolean match=false;

			for(RossImportProcessResourceVo excludedResource : excludedResources){

				if(excludedResource.getRossResReqId().compareTo(ripResVo.getRossResReqId())==0){
					match=true;
					break;
				}
			}

			if(!match){

				for(RossImportProcessEISuiteResourceVo resourceVo : ripVo.getRossImportProcessEisuiteVos()){

					// check request number
					if(ripResVo.getRequestNumber().equalsIgnoreCase(resourceVo.getRequestNumber())){

						ripResVo.setEisuiteResourceId(resourceVo.getResourceId());

						resourceVo.setRossResReqId(ripResVo.getRossResReqId());

						newRipResourceVos.add(ripResVo);
						newIsuiteResourceVos.add(resourceVo);
					}
				}


			}

		}

		ripVo.getMatchByRequestNumberAndNameWizardVo().setRossMatchReqNumNameVos(newRipResourceVos);
		ripVo.getMatchByRequestNumberAndNameWizardVo().setEisuiteMatchReqNumNameVos(newIsuiteResourceVos);

		dialogueVo.setResultObject(ripVo);

	}

	private void loadInitialRossImportProcessVo(DialogueVo dialogueVo) throws Exception {
		RossImportProcessDao ripDao = (RossImportProcessDao)super.context.getBean("rossImportProcessDao");

		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		Long incidentId = 0L;
		
		if(MatchIncidentsWizardVo.hasEISuiteIncidentMatch(ripVo.getMatchIncidentsWizardVo())){
			incidentId = MatchIncidentsWizardVo.getEISuiteIncidentId(ripVo.getMatchIncidentsWizardVo());
		}
		
		Collection<RossImportProcessResourceVo> vos = ripDao.getValidRossResources(this.rxfVo.getId(),incidentId);

		//Collection<RossImportProcessResourceVo> vos = ripVo.getRossImportProcessResourceVos();//Dao.getValidRossResources(this.rxfVo.getId());
		ripVo.setRossImportProcessResourceVos(vos);

		Collection<Long> excludeRxfdIds = new ArrayList<Long>();

		for(RossImportProcessDataErrorVo ripDataErrorVo : ripVo.getDataConflictWizardVo().getRossImportProcessDataErrorVos()){
			if(ripDataErrorVo.getExcludeFromImport()){
				excludeRxfdIds.add(ripDataErrorVo.getRossXmlFileDataId());
			}
		}

		HashMap<Long,RossImportProcessResourceVo> map = RossImportProcessResourceVo.toHashMapRxfdId(vos);

		for(Long id : excludeRxfdIds){
			if(map.containsKey(id)){
				RossImportProcessResourceVo rvo = map.get(id);
				rvo.setExcludeResource(true);
				map.put(id, rvo);
			}
		}

		vos = map.values();

		((RossImportProcessVo)dialogueVo.getResultObject())
			.setRossImportProcessResourceVos(vos);
	}

}
