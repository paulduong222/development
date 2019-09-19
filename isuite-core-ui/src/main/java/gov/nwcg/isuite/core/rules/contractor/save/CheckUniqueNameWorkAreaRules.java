package gov.nwcg.isuite.core.rules.contractor.save;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.ErrorEnum;

import org.springframework.context.ApplicationContext;

public class CheckUniqueNameWorkAreaRules extends AbstractContractorSaveRule implements IRule{
	public static final String _RULE_NAME=ContractorSaveRuleFactory.RuleEnum.CHECK_UNIQUE_NAME_WORK_AREA.name();

	public CheckUniqueNameWorkAreaRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
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
	 * @throws PersistenceException 
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {	
		
		try {

			/*
			 * Check if the vo is work area specific, if so check unique name.
			 */
			
			/* ?
					if (null != contractorDao.getByContractorNameIds(vo.getName().toUpperCase(), null, workAreaIds, vo.getId())) {
						super.handleException(ErrorEnum._0238_DUPLICATE_CONTRACTOR_NAME, vo.getName());
					}
			 * 
			 */
		} catch(Exception e){
			throw new ServiceException(e);
		}

		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
