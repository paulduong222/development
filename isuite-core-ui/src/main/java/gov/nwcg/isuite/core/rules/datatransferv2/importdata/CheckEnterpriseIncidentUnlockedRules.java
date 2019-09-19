package gov.nwcg.isuite.core.rules.datatransferv2.importdata;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.xmlv2.DataTransferInc;
import gov.nwcg.isuite.core.persistence.IncidentDao;
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

import org.springframework.context.ApplicationContext;

public class CheckEnterpriseIncidentUnlockedRules extends AbstractImportDataRule implements IRule {
	public static final String _RULE_NAME = ImportDataRuleFactory.RuleEnum.CHECK_ENTERPRISE_INCIDENT_UNLOCKED.name();

	public CheckEnterpriseIncidentUnlockedRules(ApplicationContext ctx) {
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
		 * When importing into Enterprise, 
		 * we want verify the incident is locked.
		 * 
		 */
		try{
			Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
			
			if(super.getRunMode().equals("ENTERPRISE")){
				IncidentDao incDao = (IncidentDao)context.getBean("incidentDao");
	
				if(CollectionUtility.hasValue(super.dataTransferXml.getDataTransferIncs())){
					Collection<DataTransferInc> incidents = super.dataTransferXml.getDataTransferIncs();
					
					for(DataTransferInc inc : incidents){
						String ti=inc.getTI();
						
						Long existingIncidentId=incDao.getIncidentIdByTi(ti);
						
						if(LongUtility.hasValue(existingIncidentId)){
							String incomingName=inc.getName();
							
							Incident incEntity = incDao.getById(existingIncidentId);
							if(null != incEntity){
								
								incDao.flushAndEvict(incEntity);
								
								if(BooleanUtility.isFalse(incEntity.getIsSiteManaged().getValue())){
									String error="The Incident ("+incomingName+") in the Transition File is already being managed in the Enterprise System, therefore the Transition process will be aborted.";
									ErrorObject error2 = 
										new ErrorObject("error.800000"
															,error);
									errorObjects.add(error2);
									
									CourseOfActionVo coaVo = new CourseOfActionVo();
									coaVo.setCoaName("ValidationError");
									coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
									coaVo.setIsDialogueEnding(true);
						
									coaVo.setErrorObjectVos(errorObjects);
									dialogueVo.setCourseOfActionVo(coaVo);
									
									return _FAIL;
								}
							}
						}
					}
				}
			}

			/*
			if(CollectionUtility.hasValue(errorObjects)){
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("ValidationError");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
				coaVo.setIsDialogueEnding(true);
	
				coaVo.setErrorObjectVos(errorObjects);
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
			}
			*/
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
