package gov.nwcg.isuite.core.rules.incident.saveincacctcode;

import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class CheckFirstCodeSetResDefaultRules extends AbstractSaveIncAcctCodeRule implements IRule{
	public static final String _RULE_NAME=SaveIncAcctCodeRuleFactory.RuleEnum.CHECK_FIRST_CODE_SET_RES_DEFAULT.name();

	public CheckFirstCodeSetResDefaultRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
				
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
		 * Determine if incidentEntity.getIncidentAccountCodes collection is empty.
		 */
		if(!CollectionUtility.hasValue(incidentEntity.getIncidentAccountCodes())){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}
		
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa && coa.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
			// Set the incidentaccountcode as the default for all resources
			// that do not already have a default account code
			Long iacId=entity.getId();
			Long incidentId = entity.getIncidentId();
			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			irDao.setResourceDefIAC(iacId, incidentId);
		}
		
	}

}
