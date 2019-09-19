package gov.nwcg.isuite.core.rules.costprojections.createprojections;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.domain.ProjectionItem;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import org.springframework.context.ApplicationContext;

public class CheckAlreadyRunCostRule extends AbstractCreateProjectionRule implements IRule {
	public static final String _RULE_NAME=RunCreateProjectionRuleFactory.RuleEnum.CHECK_IF_RUNCOST.name();
	
	public CheckAlreadyRunCostRule(ApplicationContext ctx)
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
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * Check if there are skipped dates
		 */
		Collection<ProjectionItem> projectionItems = new ArrayList<ProjectionItem>();
	    Long incidentId = 0L;
	    Long incidentGroupId = 0L;
	    try{
	    	if(incident != null) {
	    		incidentId = incident.getId();
	    	}
	    	else if(incidentGroup != null) {
	    		incidentGroupId = incidentGroup.getId();
	    	}
		
	    	//projectionItems = dao.getProjectionItemData(incidentId, incidentGroupId);
	    	projectionItems = dao.getProjectionItemDataForCurrentDay(incidentId, incidentGroupId, today);
		
	    	if(projectionItems == null || projectionItems.isEmpty()) {
	    		dialogueVo.setResultObjectAlternate4("There is no cost data. You must run Daily Costs before creating a Cost Projection.");
	    		return _FAIL;
	    	}
	    	else
	    		return _OK;
	    } catch (Exception e) {
	    	throw new Exception("Run cost rule check exception:" + e.getMessage());
	    }
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
