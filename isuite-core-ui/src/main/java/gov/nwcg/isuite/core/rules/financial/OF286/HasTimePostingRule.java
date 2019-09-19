package gov.nwcg.isuite.core.rules.financial.OF286;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.persistence.TimePostDao;
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
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class HasTimePostingRule extends AbstractInvoiceGenerationRule implements IRule {

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
				MessageVo messageVo = new MessageVo();
				messageVo.setMessageType(MessageTypeEnum.CRITICAL);
			    messageVo.setMessageProperty("error.selectedResourceNoTimePostings");
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
		int cnt=0;

		TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");
		
		if (irs != null && irs.size() > 0) {
			for (IncidentResourceVo ir : irs) {
				Collection<AssignmentVo> as = ir.getWorkPeriodVo().getAssignmentVos();
				Collection<AssignmentTimePost> atps = tpDao.getTimePostings(filter.getLastDateToIncludeOnReport(),ir.getId(),0L);
				
				if(CollectionUtility.hasValue(atps))
					cnt=cnt+atps.size();

				/*
				for (AssignmentVo a : as) {
					Long atId=a.getAssignmentTimeVo().getId();
					Collection<AssignmentTimePostVo> atps = a.getAssignmentTimeVo().getAssignmentTimePostVos();
					for(AssignmentTimePostVo atpvo : atps){
						if(!DateUtil.hasValue(atpvo.getLastInvoiceDate())){
							cnt++;
						}
					}
				}
				*/
				/*
				AssignmentVo a = ir.getWorkPeriodVo().getCurrentAssignmentVo();
				Collection<AssignmentTimePostVo> atps = a.getAssignmentTimeVo().getAssignmentTimePostVos();
				if (atps.size() > 0) {
					return _OK;
				}
				*/
			}
		}
		
		if(cnt > 0)
			return _OK;
		
		return _FAIL;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
