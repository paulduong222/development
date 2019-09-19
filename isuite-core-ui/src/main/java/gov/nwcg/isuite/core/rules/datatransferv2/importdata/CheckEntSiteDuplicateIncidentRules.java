package gov.nwcg.isuite.core.rules.datatransferv2.importdata;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.xmlv2.DataTransferInc;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckEntSiteDuplicateIncidentRules extends AbstractImportDataRule implements IRule {
	public static final String _RULE_NAME = ImportDataRuleFactory.RuleEnum.CHECK_ENTSITE_DUPLICATE_INCIDENT.name();

	public CheckEntSiteDuplicateIncidentRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			
			/*
			 * if rule check has been completed, return
			 */
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
		 * When importing into Enterprise/Site, we want to check if there is already an existing 
		 * Incident that matches the incoming incident number/start date.  If an incident
		 * already exists, verify the transferable identity matches between the existing record
		 * and incoming incident.  We want to prevent duplicates.
		 * 
		 */
		try{
			Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
			
			//if(super.getRunMode().equals("SITE")){
				IncidentDao incDao = (IncidentDao)context.getBean("incidentDao");
				OrganizationDao orgDao = (OrganizationDao)context.getBean("organizationDao");
	
				String errorIncidents="";
				
				if(CollectionUtility.hasValue(super.dataTransferXml.getDataTransferIncs())){
					Collection<DataTransferInc> incidents = dataTransferXml.getDataTransferIncs();
					
					for(DataTransferInc xmlIncident : incidents){
						String incomingTi=xmlIncident.getTI();
						Integer incomingYear=xmlIncident.getYear();
						String incomingNumber=xmlIncident.getNbr();
						String incomingName=xmlIncident.getName();
						String incomingUnitCodeTi=xmlIncident.getHomeUnitTI();
	
						// can we match by incidentTi?  if yes, move on
						Long existingIncidentId=incDao.getIncidentIdByTi(incomingTi);
	
						if(!LongUtility.hasValue(existingIncidentId)) {
							if(StringUtility.hasValue(incomingUnitCodeTi)){
								// get org unit code
								String incomingUnitCode=orgDao.getUnitCodeByTi(incomingUnitCodeTi);
								
								if(StringUtility.hasValue(incomingUnitCode)
										&& StringUtility.hasValue(incomingNumber)
										  && IntegerUtility.hasValue(incomingYear)){
									
									Long homeUnitId=0L;
									
									homeUnitId=orgDao.getOrgIdByTi(incomingUnitCodeTi);
									
									if(LongUtility.hasValue(homeUnitId)){
										Collection<Incident> existingIncidents 
											= incDao.getByIncNbrAndIncidentYear(homeUnitId, incomingNumber, incomingYear);
	
										if(CollectionUtility.hasValue(existingIncidents)){
											for(Incident i : existingIncidents){
												String conflictIncidentInfo=incomingName+" "+incomingUnitCode+"-"+incomingNumber;
												if(StringUtility.hasValue(errorIncidents))
													errorIncidents=errorIncidents+", "+conflictIncidentInfo;
												else
													errorIncidents=conflictIncidentInfo;
												incDao.flushAndEvict(i);
											}
										}
									}
									
								}
							}
						}
					}
				//}
	
				if(StringUtility.hasValue(errorIncidents)){
					ErrorObject error2 = 
						new ErrorObject("error.800000"
											,"The system detected the following Incidents are already defined in the system with a non-matching identity and would result in duplicate Incidents after an import. " +
													"("+errorIncidents+")");
					errorObjects.add(error2);
				}
			}
	
			if(CollectionUtility.hasValue(errorObjects)){
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("ValidationError");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
				coaVo.setIsDialogueEnding(true);
	
				coaVo.setErrorObjectVos(errorObjects);
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
			}
		}catch(Exception fallback){
			
		}
		
		return _OK;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
