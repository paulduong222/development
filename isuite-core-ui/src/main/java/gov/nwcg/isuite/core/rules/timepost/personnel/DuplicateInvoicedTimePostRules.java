package gov.nwcg.isuite.core.rules.timepost.personnel;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class DuplicateInvoicedTimePostRules extends AbstractPersonnelRule implements IRule {
	public static final String _RULE_NAME="DUPLICATE_INVOICED_TIME_POST";

	public DuplicateInvoicedTimePostRules(ApplicationContext ctx){
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
		 *  The system must prevent the user from posting Time 
		 *  for a Resource during any period of time that overlaps a portion 
		 *  of a time period included on an Original Invoice. 
		 *  (Applies to Use Cases 6.0011, 6.0012 and 6.0013.)			  
		 */
		if(null != vo){
			
			Long assignmentTimeId = super.assignmentTimeEntity.getId();

			// get last invoice date
			Date lastInvoiceDate = tpDao.getLastInvoiceDate(assignmentTimeId);
			
			if(DateUtil.hasValue(lastInvoiceDate)){
				lastInvoiceDate=DateUtil.addMilitaryTimeToDate(lastInvoiceDate, "2359");
				Date postDate=DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(),"2359");

				Boolean showMessage=false;
				
				if(DateUtil.isSameDate(lastInvoiceDate, postDate))
					showMessage=true;
				else{
					if(postDate.before(lastInvoiceDate))
						showMessage=true;
				}

				if(showMessage==true){
					String msg="The posting date cannot be prior to or the same as a date that was included in an original invoice.  Please enter another posting date.";
					
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.time"
												  ,"validationerror"
												  ,"error.800000"
												  , new String[]{msg}));	
					return _FAIL;
					
				}
				
			}
			
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
