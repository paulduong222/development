package gov.nwcg.isuite.core.rules.financial.OF286;

import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class HasTimeAdjustmentPostingRule extends AbstractInvoiceGenerationRule implements IRule {

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
				MessageVo messageVo = new MessageVo();
			    messageVo.setMessageProperty("error.selectedResourceNoTimeAdjustmentPostings");
			    messageVo.setTitleProperty("text.timeReports");

			    CourseOfActionVo coa = new CourseOfActionVo();
			    coa.setCoaName(ruleName);
			    coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			    coa.setMessageVo(messageVo);
			    dialogueVo.setCourseOfActionVo(coa);
			    
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
			if(irs != null || irs.size()!=0) {
			      for(IncidentResourceVo ir : irs) {
			        Collection<AssignmentVo> as = ir.getWorkPeriodVo().getAssignmentVos();
			        for(AssignmentVo a : as){
			          if(a.getTimeAssignAdjustVos().size()>0) {
			            for(TimeAssignAdjustVo taa : a.getTimeAssignAdjustVos()) {
			              if(taa.getDeletedDate()==null) {
			                return _OK;
			              }
			            }
			          }
			        }
			      }
			    }
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
