package gov.nwcg.isuite.core.rules;

import java.util.ArrayList;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class IncidentResourceTestDialogueHandler {
	private ApplicationContext context=null;
	
	public IncidentResourceTestDialogueHandler(ApplicationContext ctx){
		this.context=ctx;
	}

	public DialogueVo execute(DialogueVo dvo) throws Exception {

		if(null != dvo.getCourseOfActionVo()) {
			dvo.getProcessedCourseOfActionVos().add(dvo.getCourseOfActionVo());			
		}
		
		if(!isCourseOfActionComplete(dvo, "step1")) {
			buildCourseOfAction1(dvo);
		}  else {
			concludeDialogue(dvo);
		}
		
		return dvo;
	}
	
	private Boolean isCourseOfActionComplete(DialogueVo dvo, String coaName) {
		
		CourseOfActionVo coa = dvo.getCourseOfActionByName(coaName);
		
		return ((null != coa) && coa.getIsComplete());
	}
	
	private void buildCourseOfAction1(DialogueVo dvo) {
		
		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaName("step1");
		coa.setCoaType(CourseOfActionTypeEnum.PROMPT);
		
		PromptVo prompt = new PromptVo();
		prompt.setMessageProperty("action.9901");
		prompt.setTitleProperty("text.incidentResources");
		String[] params = {"disco inferno"};
		prompt.setParameters(params);
		prompt.setPromptValue(PromptVo._YES | PromptVo._NO);
		prompt.setDefaultValue(PromptVo._NO);
		
		coa.setPromptVo(prompt);
		dvo.setCourseOfActionVo(coa);
	}
	
	public void concludeDialogue(DialogueVo dvo) {
		
		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaName("conclusion");
		coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		
		CourseOfActionVo step1 = dvo.getCourseOfActionByName("step1");
		PromptVo addIncident = step1.getPromptVo();
		
		if(addIncident.getPromptResult() == PromptVo._YES) {

			MessageVo msgOnYes = new MessageVo();
			msgOnYes.setTitleProperty("text.affirmRosterIncidentResources");
			msgOnYes.setMessageType(MessageTypeEnum.URGENT);
			msgOnYes.setMessageProperty("info.0030");
			coa.setMessageVo(msgOnYes);
			
		} else {
			
			MessageVo msgOnElse = new MessageVo();
			msgOnElse.setTitleProperty("text.cancel");
			msgOnElse.setMessageType(MessageTypeEnum.CRITICAL);
			msgOnElse.setMessageProperty("text.actionCanceled");
			coa.setMessageVo(msgOnElse);
			
		}
		
		dvo.setCourseOfActionVo(coa);
	}
	
	public DialogueVo execute_old(DialogueVo vo) throws Exception {
		
		if(!isStep1Complete(vo)){
			return vo;
		}
		
		if(!isStep2Complete(vo)){
			return vo;
		}

		vo.setCourseOfActionVo(buildCourseOfActionVo3());
		
		return vo;
	}
	
	private PromptVo hasPrompt(DialogueVo dialogueVo, String messageProperty) {
		
		for(CourseOfActionVo coa : dialogueVo.getProcessedCourseOfActionVos()) {
			
			if(coa.getPromptVo().getMessageProperty() == messageProperty) {
				return coa.getPromptVo();
			}
		}
		
		return null;
	}
	
	private Boolean isStep1Complete(DialogueVo vo) {
		Boolean complete=false;
		
		PromptVo prompt = hasPrompt(vo, "action.9901"); 
		
		if(null != prompt) {
			//the question has been asked
			
			switch(prompt.getPromptResult()) {
				
				case PromptVo._YES:
					break;
				case PromptVo._NO:
					break;
				case PromptVo._CANCEL:
					break;
			}
			
			
		} else {
			//the question has not been asked

		}
		
		return complete;
	}
	
	private Boolean isStep1Complete_old(DialogueVo vo) {
		Boolean complete=false;

		if(null != vo.getProcessedCourseOfActionVos() && vo.getProcessedCourseOfActionVos().size() == 0){

			if(null != vo.getCourseOfActionVo()){
				if(null != vo.getCourseOfActionVo().getPromptVo()){
					if(null != vo.getCourseOfActionVo().getPromptVo().getPromptResult()
							&&
						vo.getCourseOfActionVo().getPromptVo().getPromptResult().intValue()>-1){
						// process result
						vo.getProcessedCourseOfActionVos().add(vo.getCourseOfActionVo());
						vo.setCourseOfActionVo(null);
						complete=true;
					}
				}
			}else{
				vo.setCourseOfActionVo(buildCourseOfActionVo1());
			}
		}
		
		return complete;
	}

	private Boolean isStep2Complete(DialogueVo vo) {
		Boolean complete=false;
		
		if(null != vo.getProcessedCourseOfActionVos() && vo.getProcessedCourseOfActionVos().size() == 1){

			if(null != vo.getCourseOfActionVo()){
				if(null != vo.getCourseOfActionVo().getPromptVo()){
					if(null != vo.getCourseOfActionVo().getPromptVo().getPromptResult()
							&&
						vo.getCourseOfActionVo().getPromptVo().getPromptResult().intValue()>-1){
						// process result
						vo.getProcessedCourseOfActionVos().add(vo.getCourseOfActionVo());
						vo.setCourseOfActionVo(null);
						complete=true;
					}
				}
			}else{
				vo.setCourseOfActionVo(buildCourseOfActionVo2());
			}
		}
		
		return complete;
	}
	
	private CourseOfActionVo buildCourseOfActionVo1() {
		CourseOfActionVo coaVo = new CourseOfActionVo();

		coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
		
		PromptVo promptVo = new PromptVo();
		promptVo.setMessageProperty("action.9901");
		promptVo.setParameters(new String[]{"test incident"});
		promptVo.setTitleProperty("text.aboutEIsuite");
		promptVo.setPromptValue(PromptVo._YES | PromptVo._NO);
		
		coaVo.setPromptVo(promptVo);
		
		return coaVo;
	}

	private CourseOfActionVo buildCourseOfActionVo2() {
		CourseOfActionVo coaVo = new CourseOfActionVo();

		coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
		
		PromptVo promptVo = new PromptVo();
		promptVo.setMessageProperty("action.0168");
		promptVo.setParameters(new String[]{});
		promptVo.setTitleProperty("text.aboutEIsuite");
		promptVo.setPromptValue(PromptVo._YES | PromptVo._NO);
		
		coaVo.setPromptVo(promptVo);
		
		return coaVo;
	}

	private CourseOfActionVo buildCourseOfActionVo3() {
		CourseOfActionVo coaVo = new CourseOfActionVo();

		coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE );

		MessageVo messageVo = new MessageVo();
		messageVo.setMessageProperty("info.0030");
		messageVo.setParameters(new String[]{});
		
		coaVo.setMessageVo(messageVo);
		
		return coaVo;
	}
	
	
}
