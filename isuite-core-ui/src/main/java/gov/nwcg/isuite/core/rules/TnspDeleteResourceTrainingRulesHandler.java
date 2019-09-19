package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.ResourceTrainingVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class TnspDeleteResourceTrainingRulesHandler extends AbstractRuleHandler {
	
	public TnspDeleteResourceTrainingRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(ResourceTrainingVo resourceTrainingVo, DialogueVo dialogueVo) throws Exception {
		
		try{
//			ResourceTrainingDao resourceTrainingDao = (ResourceTrainingDao)context.getBean("resourceTrainingDao");
			
			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.traineeAssignment", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public void executeProcessedActions(ResourceTrainingVo resourceTrainingVo, DialogueVo dialogueVo) throws Exception {
		
		try{
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

}
