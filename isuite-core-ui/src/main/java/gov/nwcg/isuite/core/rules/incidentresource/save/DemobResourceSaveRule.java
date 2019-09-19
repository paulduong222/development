package gov.nwcg.isuite.core.rules.incidentresource.save;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

public class DemobResourceSaveRule extends AbstractIncidentResourceSaveRule
		implements IRule {

	public static final String _RULE_NAME="DEMOB_DATA_VALIDATION";
	
	public DemobResourceSaveRule(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub
		return 1;
	}

}
