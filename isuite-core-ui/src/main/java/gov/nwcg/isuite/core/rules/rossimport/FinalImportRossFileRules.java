package gov.nwcg.isuite.core.rules.rossimport;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.domain.impl.RossXmlFileImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.RossXmlFileDao;
import gov.nwcg.isuite.core.persistence.RossXmlFileDataDao;
import gov.nwcg.isuite.core.rules.rossimport.finalimport.BlacklistResources;
import gov.nwcg.isuite.core.rules.rossimport.finalimport.CreateRossIncident;
import gov.nwcg.isuite.core.rules.rossimport.finalimport.IncidentResourceBuilder;
import gov.nwcg.isuite.core.rules.rossimport.finalimport.RossResourceSync;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.RossImportProcessDataErrorVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.NavigateVo;
import gov.nwcg.isuite.core.vo.rossimport.DataConflictWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.MatchIncidentsWizardVo;
import gov.nwcg.isuite.framework.core.rules.IWizardRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class FinalImportRossFileRules extends AbstractRossImportRule implements IWizardRule{
	private static final String _RULE_NAME="FINAL_IMPORT";

	private Incident incidentEntity;
	
	public FinalImportRossFileRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IWizardRule#syncData(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int syncData(DialogueVo dialogueVo) throws Exception {
		// create a navigate courseofaction
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		if(null != coaVo){
			coaVo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
			coaVo.setNavigateVo(
					buildNavigateVo(
							RossImportProcessRuleFactory.getRuleDestinationByName(_RULE_NAME)));
			dialogueVo.setCourseOfActionVo(coaVo);
		}else{
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
			 * Do the actual import of ross incident data in the e-isuite tables
			 * Steps to complete:
			 *    1.  Determine if we need to create the ross incident in isw_incident table.
			 *    2.  Identify the resources that have been blacklisted (excluded) and create the isw_ross_inc_data_blacklist records.
			 *    3.  Update the e-isuite resources that have been matched to the resources in the ross file.
			 *           - check the qualifications are up to date with the quals in the ross file.
			 *    4.  Determine which ross resources need to be added to the e-isuite isw_incident_resource table.
			 *           - use the resolved data for any errors
			 *           - create the work period, assignment
			 *           - construct the crews/teams
			 *    6.  
			 */

			// create the incident if necessary
			incidentEntity = this.getEISuiteIncidentMatch(dialogueVo);
			if(null==incidentEntity){
				String unitCode="";
				String agencyCode="";
				String eventType="";
				
				// verify we have a valid incident unit code
				if(hasUnknownUnitError(dialogueVo) ){
					unitCode=getUnitErrorResolution(dialogueVo);
					if(!StringUtility.hasValue(unitCode)){
						dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						dialogueVo.getCourseOfActionVo().setMessageVo(new MessageVo("text.rossImport","info.generic",new String[]{"Cannot complete import until unknown Incident unit code is resolved."},MessageTypeEnum.CRITICAL));
						
						return _FAIL;
					}
				}

				// verify we have a valid incident agency code
				if(hasUnknownAgencyError(dialogueVo) ){
					agencyCode=getAgencyErrorResolution(dialogueVo);
					if(!StringUtility.hasValue(unitCode)){
						dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						dialogueVo.getCourseOfActionVo().setMessageVo(new MessageVo("text.rossImport","info.generic",new String[]{"Cannot complete import until unknown Incident agency code is resolved."},MessageTypeEnum.CRITICAL));
						
						return _FAIL;
					}
				}
				
				incidentEntity=CreateRossIncident.createIncident(context, this.rxfVo,unitCode,agencyCode,eventType, super.userVo,dialogueVo);
				
			}
			
			// blacklist any resources marked as excluded
			BlacklistResources.blacklistResources(context,this.rxfVo,dialogueVo);

			// synchronize the resources 
			Collection<RossImportProcessResourceVo> resVos = RossResourceSync.synchronizeResources(context,this.rxfVo,dialogueVo);
	
			// create any new e-isuite resources if necessary
			IncidentResourceBuilder irBuilder = new IncidentResourceBuilder(context,this.rxfVo, incidentEntity);
			int resourceCount=irBuilder.createIncidentResources(resVos, dialogueVo);

			if(resourceCount > 0){
				// update isw_ross_xml_file status as imported
				RossXmlFileDao rxfDao = (RossXmlFileDao)context.getBean("rossXmlFileDao");
				RossXmlFile rxfEntity = rxfDao.getById(rxfVo.getId(), RossXmlFileImpl.class);
				rxfEntity.setImportStatus("IMPORTED");
				rxfEntity.setImportedDate(Calendar.getInstance().getTime());
				
				rxfDao.save(rxfEntity);
	
				// update the isw_ross_xml_file_data resources as imported
				Collection<Long> ids = RossImportProcessResourceVo.toRossResourceIds(resVos);
				RossXmlFileDataDao rxfdDao = (RossXmlFileDataDao)context.getBean("rossXmlFileDataDao");
				rxfdDao.updateStatuses(ids, rxfEntity.getRossIncId(), "IMPORTED");
	
				// update the e-isuite resources if necessary
	    
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("COMPLETE");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			
			coaVo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
			coaVo.setNavigateVo(new NavigateVo("DEST_CLOSE_WIZARD"));
			coaVo.setMessageVo(new MessageVo("text.rossImport", "text.rossImportCompleted" , null, MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
		
		}catch(Exception e){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("ServiceException");
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
			coaVo.setIsDialogueEnding(true);
			ErrorObject errorObject = new ErrorObject(ErrorEnum._90000_ERROR,new String[]{e.getMessage()});
			coaVo.getErrorObjectVos().add(errorObject);
			dialogueVo.setCourseOfActionVo(coaVo);
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private Incident getEISuiteIncidentMatch(DialogueVo dialogueVo) throws Exception {
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

		if(MatchIncidentsWizardVo.hasEISuiteIncidentMatch(ripVo.getMatchIncidentsWizardVo())){
			// get the e-isuite incident
			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			
			return incidentDao.getById(ripVo.getMatchIncidentsWizardVo().getMatchingIncidentId());
		}
				
		return null;
	}

	/**
	 * Return whether or not there wasn an unknown incident unit code error.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private static Boolean hasUnknownUnitError(DialogueVo dialogueVo){
		
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

		if(null != ripVo.getDataConflictWizardVo()){
			RossImportProcessDataErrorVo errVo = 
				DataConflictWizardVo.getDataErrorVo("UNKNOWN_INCIDENT_UNIT_CODE", ripVo.getDataConflictWizardVo().getRossImportProcessDataErrorVos());
			return (errVo == null ? false : true);
		}
		
		return false;
	}

	private static String getUnitErrorResolution(DialogueVo dialogueVo){
		String unitCode=null;

		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

		if(null != ripVo.getDataConflictWizardVo()){
			RossImportProcessDataErrorVo errVo = DataConflictWizardVo.getDataErrorVo("UNKNOWN_INCIDENT_UNIT_CODE", ripVo.getDataConflictWizardVo().getRossImportProcessDataErrorVos());
			if(null != errVo && null != errVo.getNewValue()){
				OrganizationVo ovo = (OrganizationVo)errVo.getNewValue();
				unitCode=ovo.getUnitCode();
			}
		}
		
		return unitCode;
	}

	/**
	 * Return whether or not there was an unknown incident agency code error.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private static Boolean hasUnknownAgencyError(DialogueVo dialogueVo){
		
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

		if(null != ripVo.getDataConflictWizardVo()){
			RossImportProcessDataErrorVo errVo = 
				DataConflictWizardVo.getDataErrorVo("UNKNOWN_INCIDENT_AGENCY_CODE", ripVo.getDataConflictWizardVo().getRossImportProcessDataErrorVos());
			return (errVo == null ? false : true);
		}
		
		return false;
	}

	private static String getAgencyErrorResolution(DialogueVo dialogueVo){
		String unitCode=null;

		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

		if(null != ripVo.getDataConflictWizardVo()){
			RossImportProcessDataErrorVo errVo = DataConflictWizardVo.getDataErrorVo("", ripVo.getDataConflictWizardVo().getRossImportProcessDataErrorVos());
			if(null != errVo && StringUtility.hasValue((String)errVo.getNewValue())){
				unitCode=String.valueOf(errVo.getNewValue());
			}
		}
		
		return unitCode;
	}
}
