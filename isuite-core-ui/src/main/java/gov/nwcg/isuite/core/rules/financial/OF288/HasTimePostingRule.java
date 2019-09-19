package gov.nwcg.isuite.core.rules.financial.OF288;

import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class HasTimePostingRule extends AbstractInvoiceGenerationRule implements IRule {
	public static final String _RULE_NAME=OF288InvoiceGeneratorRuleFactory.RuleEnum.INVC_HAS_TIME_POSTS.name();
	private int timepostingcount=0;
	
	public HasTimePostingRule(ApplicationContext ctx, String rname)
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
			CourseOfActionVo coavo = super.buildNoActionCoaVo(ruleName,true);
			coavo.setStoredObject(String.valueOf(this.timepostingcount));
			dialogueVo.getProcessedCourseOfActionVos()
				.add(coavo);
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
		int cnt=0;

		if (irs != null && irs.size() > 0) {
			for (IncidentResourceVo ir : irs) {
				Collection<AssignmentVo> as = ir.getWorkPeriodVo().getAssignmentVos();
				for (AssignmentVo a : as) {
					Collection<AssignmentTimePostVo> atps = a.getAssignmentTimeVo().getAssignmentTimePostVos();
					for(AssignmentTimePostVo atpvo : atps){
						if(!DateUtil.hasValue(atpvo.getLastInvoiceDate())){
							cnt++;
						}
					}
				}
			}
		}
		
		if( (cnt < 1) && BooleanUtility.isFalse(super.filter.getIsPerson())){
			MessageVo messageVo = new MessageVo();
			messageVo.setMessageType(MessageTypeEnum.CRITICAL);
			if(filter.getIsCrew()==true)
				messageVo.setMessageProperty("error.selectedResourceNoTimePostingsCrew");
			else
				messageVo.setMessageProperty("error.selectedResourceNoTimePostings");
			
		    messageVo.setTitleProperty("text.timeReports");

		    CourseOfActionVo coa = new CourseOfActionVo();
		    coa.setCoaName(_RULE_NAME);
		    coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		    coa.setMessageVo(messageVo);
		    dialogueVo.setCourseOfActionVo(coa);
		    
			return _FAIL;
		}

		this.timepostingcount=cnt;
		return _OK;
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
