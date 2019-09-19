package gov.nwcg.isuite.core.rules.adminoffice.delete;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckNotUsedInInvoiceRules extends AbstractAdminOfficeDeleteRule implements IRule{
	public static final String _RULE_NAME=AdminOfficeDeleteRuleFactory.RuleEnum.CHECK_NOT_USED_IN_INVOICE.name();

	public CheckNotUsedInInvoiceRules(ApplicationContext ctx)
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
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
	
		/*
		 * B.R. 6.0014
		 *    1.	The system must allow the user to delete an Admin Office 
		 *          for Payment that was not included on an Original Invoice.
		 *    
		 *    2.	The system must prevent the user from deleting an Admin Office 
		 *          for Payment that was included on an Original Invoice.
		 * 
		 */
		if(LongUtility.hasValue(vo.getId())) {
//			TimeInvoiceDao timeInvoiceDao = (TimeInvoiceDao)context.getBean("timeInvoiceDao");
//			Collection<TimeInvoice> entities = timeInvoiceDao.getByAdminId(vo.getId());
			
			Collection<ContractorAgreement> entities = dao.getIncludedInOriginalInvoice(vo.getId());
			
			if (CollectionUtility.hasValue(entities)) {
				dialogueVo.setCourseOfActionVo(
						super.buildErrorCoaVo("text.adminOffice"
								  ,"validationerror"
								  ,"error.0265"
								  ,null));	
	
				return _FAIL;
			}
		}else{
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.adminOffice", "error.0265" , null, MessageTypeEnum.CRITICAL));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
