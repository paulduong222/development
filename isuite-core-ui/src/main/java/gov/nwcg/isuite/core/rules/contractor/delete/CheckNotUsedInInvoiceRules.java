package gov.nwcg.isuite.core.rules.contractor.delete;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.rules.adminoffice.delete.AdminOfficeDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

public class CheckNotUsedInInvoiceRules extends AbstractContractorDeleteRule implements IRule {
	public static final String _RULE_NAME=AdminOfficeDeleteRuleFactory.RuleEnum.CHECK_NOT_USED_IN_INVOICE.name();
	
	public CheckNotUsedInInvoiceRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
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
		 *	The user is not allowed to delete a contractor that is included in an original invoice.
		 *
		 */
		if(LongUtility.hasValue(entity.getId())) {
			
			Collection<ContractorAgreement> entities = dao.getIncludedInOriginalInvoice(entity.getId());
			
			if (CollectionUtility.hasValue(entities)) {
				dialogueVo.setCourseOfActionVo(
						super.buildErrorCoaVo("text.contractor"
								  ,"validationerror"
								  ,"error.0172"
								  ,null));	
	
				return _FAIL;
			}
		}else{
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.contractor", "error.0172" , null, MessageTypeEnum.CRITICAL));

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
