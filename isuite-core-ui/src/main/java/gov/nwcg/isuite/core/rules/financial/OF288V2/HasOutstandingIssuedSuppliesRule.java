package gov.nwcg.isuite.core.rules.financial.OF288V2;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class HasOutstandingIssuedSuppliesRule extends AbstractInvoiceGenerationRule implements IRule {

	public HasOutstandingIssuedSuppliesRule(ApplicationContext ctx, String rname)
	{
		super(ctx, rname);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;
					
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _FAIL) {
				return _FAIL;
			} 	
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(ruleName,true));
		
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
		// TODO (dec 1, 2011): see if any supplies have been issued to the
		// resource.
		// OF286 use case, Validation on Preview, #2
		/*
		 * 2. If the user is generating an Original OF-286 Invoice and there are
		 * trackable or durable Supply Items issued to the Contractor/Cooperator
		 * Resource, the system must display an action message indicating that
		 * supply items are still issued to the Resource. Yes and No buttons are
		 * available.
		 */
		if(false) {
			// get requestNumber and resourceName for prompt message
			PromptVo promptVo = new PromptVo();
		    promptVo.setMessageProperty("action.0141");
		    //promptVo.setParameters(new String[]{requestNumber, resourceName}); 
		    promptVo.setTitleProperty("text.timeReports"); 
		    promptVo.setPromptValue(PromptVo._YES | PromptVo._NO);
		    
		    CourseOfActionVo coa = new CourseOfActionVo();
		    coa.setCoaName(ruleName);
		    coa.setCoaType(CourseOfActionTypeEnum.PROMPT);
		    coa.setPromptVo(promptVo);
		    dialogueVo.setCourseOfActionVo(coa);
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
