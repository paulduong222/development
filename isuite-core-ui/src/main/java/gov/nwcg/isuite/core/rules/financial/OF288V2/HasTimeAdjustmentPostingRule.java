package gov.nwcg.isuite.core.rules.financial.OF288V2;

import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class HasTimeAdjustmentPostingRule extends AbstractInvoiceGenerationRule implements IRule {
	public static final String _RULE_NAME=OF288InvoiceGeneratorRuleFactory2.RuleEnum.INVC_HAS_ADJMT_POSTS.name();

	public HasTimeAdjustmentPostingRule(ApplicationContext ctx, String rname)
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
		if(filter.getPrintItemizedDeductionsOnly()) {
			if(CollectionUtility.hasValue(super.timeAdjustDataVos)){
				return _OK;
			}

			MessageVo messageVo = new MessageVo();
			messageVo.setMessageType(MessageTypeEnum.CRITICAL);
			if(filter.getIsCrew()==true)
				messageVo.setMessageProperty("error.selectedResourceNoTimeAdjustmentPostingsCrew");
			else
				messageVo.setMessageProperty("error.selectedResourceNoTimeAdjustmentPostings");
			messageVo.setTitleProperty("text.timeReports");

			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName(_RULE_NAME);
			coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coa.setMessageVo(messageVo);
			dialogueVo.setCourseOfActionVo(coa);

			return _FAIL;
		}else{
			CourseOfActionVo coavo = dialogueVo.getCourseOfActionByName(HasTimePostingRule._RULE_NAME);
			if(null != coavo && null != coavo.getStoredObject()){
				int postingCount=Integer.parseInt((String)coavo.getStoredObject());
				if(postingCount==0){
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
