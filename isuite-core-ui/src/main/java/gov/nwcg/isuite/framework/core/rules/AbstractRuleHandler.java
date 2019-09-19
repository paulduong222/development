package gov.nwcg.isuite.framework.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;

public class AbstractRuleHandler {
	protected static final String _MSG_FINISHED="MSG_FINISHED";
	protected ApplicationContext context=null;
	
	public static final int _OK=1;
	public static final int _FAIL=-1;
	public static final int _SKIP=5;
	
	public AbstractRuleHandler(){
		
	}

	/**
	 * @param dvo
	 * @param coaName
	 * @return
	 */
	protected Boolean isCourseOfActionComplete(DialogueVo dvo, String coaName) {
		
		CourseOfActionVo coa = dvo.getCourseOfActionByName(coaName);
		
		return ((null != coa) && coa.getIsComplete());
	}
	
	/**
	 * @param dvo
	 * @param coaName
	 * @return
	 */
	protected Boolean isCurrentCourseOfAction(DialogueVo dvo, String coaName) {
	
		if(null != dvo.getCourseOfActionVo() 
				&& dvo.getCourseOfActionVo().getCoaName().equals(coaName)){
			return true;
		}else
			return false;
				
	}
	
	/**
	 * @param dvo
	 * @return
	 */
	protected int getPromptResult(DialogueVo dvo){
		if(null != dvo.getCourseOfActionVo().getPromptVo()){
			return dvo.getCourseOfActionVo().getPromptVo().getPromptResult().intValue();
		}else
			return -1;
	}
	
}
