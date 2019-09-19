package gov.nwcg.isuite.framework.core.rules;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;

import org.springframework.context.ApplicationContext;

public interface IRule {

	/**
	 * Returns the name of the rule.
	 * 
	 * @return String
	 * 		name of the rule
	 */
	public String getRuleName();
	
	/**
	 * Sets the Application Context.
	 * 
	 * @param ctx
	 * 		the spring application context object
	 */
	public void setContext(ApplicationContext ctx);

	/**
	 * Generic method to pass objects to the rule implementation.
	 * 
	 * @param object Object
	 * 		object to pass
	 * @param objectName String 
	 * 		name identifier of the object
	 */
	public void setObject(Object object, String objectName);
	
	/**
	 * Executes the rules.
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception;

	/**
	 * Executes any post processing actions needed by rules.
	 * 
	 * @param dialogueVo
	 * 
	 * @throws Exception
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception;
	
	public void addAdditionalMessages(DialogueVo dialogueVo) throws Exception;
}
