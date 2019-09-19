package gov.nwcg.isuite.core.rules.rossimport;

import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentFilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.RossImportProcessDao;
import gov.nwcg.isuite.core.vo.CountryCodeSubdivisionVo;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.RossImportProcessEISuiteResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.rossimport.MatchIncidentsWizardVo;
import gov.nwcg.isuite.framework.core.rules.IWizardRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class MatchIncidentRules extends AbstractRossImportRule implements IWizardRule {
	private static final String _RULE_NAME="MATCH_INCIDENT_PROCESS";

	public MatchIncidentRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IWizardRule#syncData(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int syncData(DialogueVo dialogueVo) throws Exception {
	
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
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;
				
			
			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
					
				// add to the processed collection
				dialogueVo.getCourseOfActionVo().setIsComplete(true);
				dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

				updateRossImportProcessVo(dialogueVo);
			
				return _OK;
			}
			
			if(buildWizardData(dialogueVo)==_FAIL)
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

		// build the MatchIncidentsWizardVo
		MatchIncidentsWizardVo matchIncidentsWizVo = new MatchIncidentsWizardVo();
		
		matchIncidentsWizVo.setRossIncidentVo(this.buildRossIncidentVo());
		matchIncidentsWizVo.setEisuiteIncidents(this.buildEISuiteIncidentsList());
		matchIncidentsWizVo.setProjectedMatchIncidentVo(
				this.getProjectedMatchIncident(matchIncidentsWizVo.getEisuiteIncidents()));

		// update the rossImportProcessVo
		((RossImportProcessVo)dialogueVo.getResultObject())
					.setMatchIncidentsWizardVo(matchIncidentsWizVo);
		
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

	/**
	 * @param dialogueVo
	 * @throws Exception
	 */
	private void updateRossImportProcessVo(DialogueVo dialogueVo) throws Exception {
		// continue
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		if(MatchIncidentsWizardVo.hasEISuiteIncidentMatch(ripVo.getMatchIncidentsWizardVo())){
			
			// load the eisuite resources
			Long incidentId=ripVo.getMatchIncidentsWizardVo().getMatchingIncidentId();
			RossImportProcessDao ripDao = (RossImportProcessDao)super.context.getBean("rossImportProcessDao");
				
			// Get the list of resources for the incident in eisuite
			Collection<RossImportProcessEISuiteResourceVo> eisuiteResources = ripDao.getEISuiteResources(incidentId);
				
			ripVo.setRossImportProcessEisuiteVos(eisuiteResources);
			
			// update the rossImportProcessVo
			dialogueVo.setResultObject(ripVo);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

	/**
	 * Builds and returns a ross incident vo.
	 * 
	 * @return
	 */
	private IncidentVo buildRossIncidentVo(){
		IncidentVo vo = new IncidentVo();
		vo.setIncidentNumber(rxfVo.getIncidentNumber());
		vo.setIncidentName(rxfVo.getIncidentName());
		DateTransferVo startDateVo=new DateTransferVo();
		if(null != rxfVo.getIncidentStartDate()){
			Date startDate=rxfVo.getIncidentStartDate();
			startDateVo.setDateString(DateUtil.toDateString(startDate, DateUtil.MM_SLASH_DD_SLASH_YYYY));
			startDateVo.setTimeString("");
		}
		if(DateTransferVo.hasDateString(startDateVo))
			vo.setIncStartDateTransferVo(startDateVo);
		else
			vo.setIncStartDateTransferVo(new DateTransferVo());
		
		CountryCodeSubdivisionVo ccsVo = new CountryCodeSubdivisionVo();
		if(StringUtility.hasValue(vo.getIncidentNumber()) 
				&&  
			vo.getIncidentNumber().trim().length()>2){
			
			ccsVo.setCountrySubAbbreviation(vo.getIncidentNumber().trim().substring(0, 2));
		}else
			ccsVo.setCountrySubAbbreviation("");
		
		vo.setCountryCodeSubdivisionVo(ccsVo);
		vo.setRossIncidentId(rxfVo.getRossIncidentId());
		
		return vo;
	}

	/**
	 * Builds and returns a collection of e-isuite incidents
	 * that are possible matches for the ross incident.
	 * 
	 * @return
	 */
	private Collection<IncidentVo> buildEISuiteIncidentsList() throws Exception{
		Collection<IncidentVo> vos = new ArrayList<IncidentVo>();
		Collection<IncidentVo> rtnVos = new ArrayList<IncidentVo>();

		IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
		
		IncidentFilter filter = new IncidentFilterImpl();

		/*
		 * do a look up by ross incident id
		 */
		filter.setRossIncId(TypeConverter.convertToLong(rxfVo.getRossIncidentId()));
		vos=incidentDao.getPossibleRossMatches(filter);
		
		if(null == vos || vos.size() < 1){
			// do a look up by other info
			filter.setRossIncId(null);
			String nbr="";
			String unitCode="";
			if(StringUtility.hasValue(rxfVo.getIncidentNumber())){
				filter.setIncidentTagNumber(rxfVo.getIncidentNumber());
				//unitCode=StringUtility.getTokenValue(rxfVo.getIncidentNumber(), "-", 2);
				//nbr=StringUtility.getTokenValue(rxfVo.getIncidentNumber(), "-", 3);
				//StringUtility.removeChar(nbr,'0');
			}

			if(DateUtil.hasValue(rxfVo.getIncidentStartDate())){
				Integer year=DateUtil.getYearAsInteger(rxfVo.getIncidentStartDate());
				filter.setIncidentYear(year);
			}
			
			//filter.setIncidentName(this.rxfVo.getIncidentName());
			//filter.setIncidentNumber(nbr);
			
			/*
			 * Query the incidents table and find best possible matches
			 */
			vos = incidentDao.getPossibleRossMatches(filter);
			
			return vos;
		}
		
		// reset rossincidentid to null
		for(IncidentVo vo : vos){
			vo.setRossIncidentId("");
			rtnVos.add(vo);
		}
		
		return rtnVos;
	}

	/**
	 * Returns the best possible ross incident match from 
	 * the incidents in the e-isuite system.
	 * 
	 * @return
	 */
	private IncidentVo getProjectedMatchIncident(Collection<IncidentVo> vos){
		
		String nbr="";
		if(StringUtility.hasValue(rxfVo.getIncidentNumber())){
			nbr=StringUtility.getTokenValue(rxfVo.getIncidentNumber(), "-", 2);
		}
		
		if(null != vos){
			for(IncidentVo vo : vos){
				if(vo.getIncidentNumber().equals(nbr)){
					return vo;
				}
			}
		}
		
		return null;
	}
}
