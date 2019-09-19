package gov.nwcg.isuite.core.rules.costprojections.createprojections;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class CheckProjectionNameRule extends AbstractCreateProjectionRule implements IRule {
	public static final String _RULE_NAME=RunCreateProjectionRuleFactory.RuleEnum.CHECK_PROJECTION_NAME.name();
	
	public CheckProjectionNameRule(ApplicationContext ctx)
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
		 * Check if dup projection name 
		 */
		Collection<Projection> projections = dao.getProjectionsByName(projectionVo.getProjectionName());
		
		if(projections != null && !projections.isEmpty())
			for(Projection p :projections) {
				//if(p.getCreatedDate().equals(projectionVo.getCreatedDate()))
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				String date = dateFormat.format(p.getCreatedDate());
				boolean isSameIncident = (p.getIncidentId().equals(incident.getId()));
				if(date.equals(super.today) && isSameIncident) {
					dialogueVo.setResultObjectAlternate4("<" + projectionVo.getProjectionName() + "> already exists. Please enter a different name.");
					return _FAIL;
				}
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
