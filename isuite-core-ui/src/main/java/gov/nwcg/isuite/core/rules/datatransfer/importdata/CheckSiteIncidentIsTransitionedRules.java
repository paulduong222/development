package gov.nwcg.isuite.core.rules.datatransfer.importdata;

import gov.nwcg.isuite.core.domain.xml.IswIncident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckSiteIncidentIsTransitionedRules extends AbstractImportDataRule implements IRule {
	public static final String _RULE_NAME = ImportDataRuleFactory.RuleEnum.CHECK_SITE_INCIDENT_IS_TRANSITIONED.name();

	public CheckSiteIncidentIsTransitionedRules(ApplicationContext ctx) {
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
		 * When importing into Site, we want to check if there is already an existing 
		 * Incident that matches the incoming ti. If an incident
		 * already exists, stop the import and force users to use sync option.
		 * 
		 */
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		
		if(super.getRunMode().equals("SITE")){
			IncidentDao incDao = (IncidentDao)context.getBean("incidentDao");
			OrganizationDao orgDao = (OrganizationDao)context.getBean("organizationDao");

			boolean incidentExistsWithTi=false;
			String incidentInfo="";
			
			if(CollectionUtility.hasValue(super.dataTransferXml.getIncidents())){
				Collection<IswIncident> incidents = dataTransferXml.getIncidents();
				
				for(IswIncident xmlIncident : incidents){
					String incomingTi=xmlIncident.getTransferableIdentity();
					Date incomingStartDate=xmlIncident.getIncidentStartDate();
					Integer incomingYear=xmlIncident.getIncidentYear();
					String incomingNumber=xmlIncident.getNbr();
					String incomingName=xmlIncident.getIncidentName();
					String incomingUnitCodeTi=xmlIncident.getHomeUnitTransferableIdentity();

					// can we match by incidentTi?  if yes, move on
					Long existingIncidentId=incDao.getIncidentIdByTi(incomingTi);

					if(LongUtility.hasValue(existingIncidentId)) {
						incidentInfo=incomingName;
						incidentExistsWithTi=true;
					}
				}
			}

			if(BooleanUtility.isTrue(incidentExistsWithTi)){
				ErrorObject error2 = 
					new ErrorObject("error.800000"
										,"The system detected the following Incident ("+incidentInfo+") was already transitioned to Enterprise or was already transitioned from Enterprise. Subsequent imports should be handled through the Sync Process.");
				//errorObjects.add(error2);
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
		
		return _OK;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
