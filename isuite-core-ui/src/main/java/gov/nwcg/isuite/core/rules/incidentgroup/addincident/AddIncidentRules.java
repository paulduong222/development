package gov.nwcg.isuite.core.rules.incidentgroup.addincident;

import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class AddIncidentRules extends AbstractAddIncidentRule implements IRule{
	
	public AddIncidentRules(ApplicationContext ctx, String rname)
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
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		findAddedIncidents();
		
		if(addedIncidents.size()>0) {
			String msg = "";
			for(IncidentVo igInc : addedIncidents) {
				if(msg.length()==0)
					msg = msg + igInc.getIncidentName();
				else
					msg = msg + ", " + igInc.getIncidentName();
			}
			
			MessageVo msgVo = new MessageVo();
			msgVo.setMessageType(MessageTypeEnum.INFO);
			msgVo.setTitleProperty("text.incidentGroup");
			msgVo.setMessageProperty("info.itemAddedToItem");
			msgVo.setParameters(new String[]{"Incident"
					,"Incident Group"
					,msg});
			
			dialogueVo.getCourseOfActionByName(ruleName).setMessageVo(msgVo);
		}
	}
		

}
