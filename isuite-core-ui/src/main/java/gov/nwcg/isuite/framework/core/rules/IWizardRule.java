package gov.nwcg.isuite.framework.core.rules;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;


public interface IWizardRule extends IRule{

	public int syncData(DialogueVo dialogueVo) throws Exception;
	
	public boolean validateProceedThisWizard(DialogueVo dialogueVo) throws Exception;
}
