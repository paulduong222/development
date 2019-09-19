package gov.nwcg.isuite.core.rules.datatransferv2.importdata;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckSiteGroupMatchRules extends AbstractImportDataRule implements IRule {
	public static final String _RULE_NAME = ImportDataRuleFactory.RuleEnum.CHECK_SITE_GROUP_MATCH.name();

	public CheckSiteGroupMatchRules(ApplicationContext ctx) {
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
		 * When importing into Site, we want to check if the existing Site
		 * Incident Group already has a transferable identity, if it does and then
		 * we want to check if the incoming group ti matches the one already in the db.
		 * 
		 * We want to prevent importing a different Incident Group if the existing 
		 * Incident Group has already been transitioned.
		 */
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		
		if(super.getRunMode().equals("SITE")){
			IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
			String dbTi=igDao.getSiteGroupTransferableIdentity(1L);
			
			if(CollectionUtility.hasValue(super.dataTransferXml.getDataTransferIncGroups())
						&& StringUtility.hasValue(dbTi)){
				
				String incomingTi=super.dataTransferXml.getDataTransferIncGroups().iterator().next().getTI();
				if(StringUtility.hasValue(incomingTi)){
					if(!dbTi.equals(incomingTi)){
						ErrorObject error2 = 
							new ErrorObject("error.800000"
												,"The Incident Group in the database has already been transitioned to " +
														"a different Incident Group than the one in the transition file does not match the one in the database.");
						//errorObjects.add(error2);
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
		
		return _OK;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
