package gov.nwcg.isuite.core.rules.incidentgroup.addincident;

import java.util.ArrayList;

import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class NoDuplicateIncidentsRules extends AbstractAddIncidentRule implements IRule{
	
	public NoDuplicateIncidentsRules(ApplicationContext ctx, String rname)
	{
		super(ctx, rname);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;
					
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _OK) {
				/*
				 * Rule check passed, mark as completed
				 */
				dialogueVo.getProcessedCourseOfActionVos()
					.add(super.buildNoActionCoaVo(ruleName,true));
			}
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
		 * Do not allow the same incident to be added twice
		 */
		if(newVo.getIncidentVos() != null) {
			ArrayList<IncidentVo> list = new ArrayList<IncidentVo>();
			list.addAll(newVo.getIncidentVos());
			int count = 0;
		
			for(IncidentVo iVo : list) {
				count = 0;
				for(int i=newVo.getIncidentVos().size()-1; i>-1; i--) {
					if(iVo.getId().equals( ((ArrayList<IncidentVo>)newVo.getIncidentVos()).get(i).getId() )) {
						count++;
						if(count > 1) {
							((ArrayList<IncidentVo>)newVo.getIncidentVos()).remove(i);
						}
					}
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
