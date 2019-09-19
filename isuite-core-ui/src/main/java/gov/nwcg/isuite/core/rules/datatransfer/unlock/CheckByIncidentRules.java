package gov.nwcg.isuite.core.rules.datatransfer.unlock;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckByIncidentRules extends AbstractUnlockDataRule implements IRule {
	public static final String _RULE_NAME = UnlockDataRuleFactory.RuleEnum.CHECK_BY_INCIDENT_ID.name();

	public CheckByIncidentRules(ApplicationContext ctx) {
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
		 * The rule is an incident cannot only be unlocked
		 * if it has not been transferred back to enterprise at least once.
		 */
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		DataTransferDao dao = (DataTransferDao)context.getBean("dataTransferDao");
	
		if(LongUtility.hasValue(super.dataTransferVo.getIncidentId())){
			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			Boolean isLocked=incidentDao.isIncidentLocked(dataTransferVo.getIncidentId(), "INCIDENT");
			
			if(isLocked==true){
				Boolean isSiteSyncedOnce=incidentDao.isSyncedOnce(dataTransferVo.getIncidentId());
				if(isSiteSyncedOnce == true){
					// cannot unlock
					String msg="The Incident cannot be unlocked until it has been transitioned back to Enterprise.";

					ErrorObject error2 = 
						new ErrorObject("error.800000"
											,msg);
					//errorObjects.add(error2);
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
