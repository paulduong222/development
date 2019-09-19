package gov.nwcg.isuite.core.rules.datatransferv2.importdata;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
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

public class CheckEnterpriseGroupUnlockedRules extends AbstractImportDataRule implements IRule {
	public static final String _RULE_NAME = ImportDataRuleFactory.RuleEnum.CHECK_ENTERPRISE_GROUP_UNLOCKED.name();

	public CheckEnterpriseGroupUnlockedRules(ApplicationContext ctx) {
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
		 * we want verify the group is locked.
		 * 
		 */
		try{
			Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
			
			if(super.getRunMode().equals("ENTERPRISE")){
				IncidentGroupDao incGrpDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
	
				String errorGroup="The Incident Group in the Transition File is already being managed in the Enterprise System, therefore the Transition process will be aborted.";
				
				if(CollectionUtility.hasValue(super.dataTransferXml.getDataTransferIncGroups())){
					String incomingTi=super.dataTransferXml.getDataTransferIncGroups().iterator().next().getTI();

					// can we match by ti?  
					Long existingIncidentGroupId=incGrpDao.getIncidentGroupIdByTi(incomingTi);
					
					if(LongUtility.hasValue(existingIncidentGroupId)){
						IncidentGroup ig = incGrpDao.getById(existingIncidentGroupId);
						if(null != ig){
							
							incGrpDao.flushAndEvict(ig);
							
							if(BooleanUtility.isFalse(ig.getIsSiteManaged().getValue())){
								ErrorObject error2 = 
									new ErrorObject("error.800000"
														,errorGroup);
								errorObjects.add(error2);
							}
						}
					}
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
