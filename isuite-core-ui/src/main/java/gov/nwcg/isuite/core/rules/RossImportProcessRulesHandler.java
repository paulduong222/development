package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.rules.rossimport.FinalImportRossFileRules;
import gov.nwcg.isuite.core.rules.rossimport.RossImportProcessRuleFactory;
import gov.nwcg.isuite.core.rules.rossimport.RossImportProcessRuleFactory.ObjectTypeEnum;
import gov.nwcg.isuite.core.vo.RossXmlFileVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.NavigateVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IWizardRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class RossImportProcessRulesHandler extends AbstractRuleHandler {

	public RossImportProcessRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(String action,RossXmlFileVo vo, UserVo userVo,DialogueVo dialogueVo) throws Exception {
		
		try{
			int currentRuleIndex =0;
			CourseOfActionVo coaVo = dialogueVo.getCourseOfActionVo();
			if(null != coaVo){
				/*
				 * Get current rule index 
				 */ 
				currentRuleIndex = RossImportProcessRuleFactory.getRuleIndexByName(coaVo.getCoaName());
			}
					
			
			if(action.equalsIgnoreCase("REIMPORT")){
				
			}
			
			if(action.equalsIgnoreCase("CANCEL")){
				CourseOfActionVo cVo = new CourseOfActionVo();
				cVo.setIsDialogueEnding(Boolean.TRUE);
				cVo.setCoaName("CANCELIMPORT");
				cVo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
				cVo.setNavigateVo(new NavigateVo("DEST_CLOSE_WIZARD"));
				cVo.setPromptVo(new PromptVo("text.rossImport", "text.rossImportAbort",null,PromptVo._OK));

				dialogueVo.setCourseOfActionVo(cVo);
				return _OK;
			}
			
			if(action.equalsIgnoreCase("REVIEW_ROSTERED")){
				CourseOfActionVo cVo= getReviewRosterCourseOfActionVo(dialogueVo);
				if(null == cVo || cVo.getCoaType()==CourseOfActionTypeEnum.NOACTION){
					dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					dialogueVo.getCourseOfActionVo().setMessageVo(new MessageVo("text.rossImport","info.generic",new String[]{"There are no rostered resources to review."},MessageTypeEnum.CRITICAL));
					return _FAIL;
				}
				
				dialogueVo.setCourseOfActionVo(cVo);
				return _OK;
			}
			
			if(action.equalsIgnoreCase("COMPLETE")){
				/*
				 * Finalize the import process by importing the incident/resources
				 */
				IWizardRule ruleInstance = new FinalImportRossFileRules(super.context);
				ruleInstance.setObject(vo, ObjectTypeEnum.ROSS_XML_FILE_VO.name());
				ruleInstance.setObject(userVo, ObjectTypeEnum.USER_VO.name());
				
				ruleInstance.executeRules(dialogueVo);
				
				return _OK;
			}

			if(action.equalsIgnoreCase("PREVIOUS") ){
				// update the processedCoa's
				CourseOfActionVo cvo = dialogueVo.getCourseOfActionVo();
				if(!dialogueVo.hasCourseOfActionByName(cvo.getCoaName())){
					dialogueVo.getProcessedCourseOfActionVos().add(cvo);
				}
				
				CourseOfActionVo prevCoaVo = null;
				while(currentRuleIndex > 1){
					prevCoaVo = this.getPreviousCourseOfActionVo(currentRuleIndex, dialogueVo);
					if(null != prevCoaVo && prevCoaVo.getCoaType() != CourseOfActionTypeEnum.NOACTION){
						dialogueVo.setCourseOfActionVo(prevCoaVo);
						return _OK;
					}else
						prevCoaVo=null;
					
					currentRuleIndex--;
				}

				// cannot process previous when currentindex is 1
				// return _OK with dialogueVo untouched
				return _OK;
			}
			
			if(action.equalsIgnoreCase("NEXT")){
				if(currentRuleIndex >= (RossImportProcessRuleFactory.RuleEnum.values().length - 1)){
					// cannot process previous when currentindex is at max
					// return _OK with dialogueVo untouched
					return _OK;
				}else{
					// if current rule is viewexclude resources
					// sync the data first before proceeding
					if(currentRuleIndex==8){
						IWizardRule rule = RossImportProcessRuleFactory.getInstanceByName("VIEW_ROSS_RESOURCES_EXCLUDE", context, vo);
						if(null != rule){
							rule.syncData(dialogueVo);
						}
					}
					
					CourseOfActionVo nextCoaVo =null;
					while(currentRuleIndex < (RossImportProcessRuleFactory.RuleEnum.values().length - 1)){
					
						
						// check if we have a valid next coa
						nextCoaVo = this.getNextCourseOfActionVo(
																currentRuleIndex 
																,(RossImportProcessRuleFactory.RuleEnum.values().length - 1)
																,dialogueVo);
						//if(null != nextCoaVo && nextCoaVo.getCoaType() !=  CourseOfActionTypeEnum.NOACTION ){
						if(null != nextCoaVo){
							
							IWizardRule rule = RossImportProcessRuleFactory.getInstanceByName(nextCoaVo.getCoaName(), context, vo);

							if(null != rule){
								if(rule.syncData(dialogueVo)==_OK){
									if(!rule.validateProceedThisWizard(dialogueVo))
										return _FAIL;
									else{
										dialogueVo.setCourseOfActionVo(nextCoaVo);
										return _OK;
									}
								}
							}else{
								dialogueVo.setCourseOfActionVo(nextCoaVo);
								return _OK;
							}
						}else
							nextCoaVo=null;
						currentRuleIndex++;
					}

					// process the rules normally to get the next courseofaction
					for(RossImportProcessRuleFactory.RuleEnum ruleEnum : RossImportProcessRuleFactory.RuleEnum.values()){
						IWizardRule rule = RossImportProcessRuleFactory.getInstance(ruleEnum, context, vo,userVo);
							
						if(null != rule){
							if(_OK != rule.executeRules(dialogueVo)){
								return _FAIL;
							}
						}
					}
					
				}
				
			}
			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.rossImport", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	/**
	 * @param currentIndex
	 * @param dialogueVo
	 * @return
	 */
	private CourseOfActionVo getPreviousCourseOfActionVo(int currentIndex,DialogueVo dialogueVo) {
		CourseOfActionVo coaVo = null;
		int checkIndex=currentIndex - 1;
		
		/*
		 * need to find the first previously processed vo that has a coatype of navigation
		 */
		while(coaVo == null && checkIndex > 0){
			
			for(CourseOfActionVo vo : dialogueVo.getProcessedCourseOfActionVos()){
				int ruleIdx = RossImportProcessRuleFactory.getRuleIndexByName(vo.getCoaName());
				if((ruleIdx==checkIndex )){
					
					// if ruleidx == 9 (overheadresourcegroups) and noaction then skip
					if(ruleIdx==9 && vo.getCoaType()==CourseOfActionTypeEnum.NOACTION){
						// skip
					}else{
						if(vo.getCoaType().equals(CourseOfActionTypeEnum.NAVIGATION)){
							coaVo=vo;
							break;
						}
					}
				}
			}
			
			checkIndex--;
		}
		
		return coaVo;
	}
	
	/**
	 * @param currentIndex
	 * @param maxIndexes
	 * @param dialogueVo
	 * @return
	 */
	private CourseOfActionVo getNextCourseOfActionVo(int currentIndex,int maxIndexes,DialogueVo dialogueVo) {
		CourseOfActionVo coaVo = null;
		int checkIndex=currentIndex + 1;
		
		/*
		 * if current coa's index == currentIndex, return null
		 * most likely the client side is resubmitting the same coa with the coa resolution
		 */
		//CourseOfActionVo currentCoa = dialogueVo.getCourseOfActionVo();
		//if(null != currentCoa){
		//	int coaIndex = RossImportProcessRuleFactory.getRuleIndexByName(currentCoa.getCoaName());
		//}
		
		/*
		 * need to find the first next processed vo that has a coatype of navigation
		 * if available
		 */
		while(coaVo == null && checkIndex <= maxIndexes){
			
			for(CourseOfActionVo vo : dialogueVo.getProcessedCourseOfActionVos()){
				int ruleIdx = RossImportProcessRuleFactory.getRuleIndexByName(vo.getCoaName());
				// skip ruleidx 10 (review rostered)
				//if((ruleIdx==checkIndex ) && (ruleIdx != 10)){
				if((ruleIdx==checkIndex ) ){
					
					// if ruleidx == 9 (overheadresourcegroups) and noaction then skip
					if(ruleIdx==9 && vo.getCoaType()==CourseOfActionTypeEnum.NOACTION){
						// skip
					}else{
						
						//if(vo.getCoaType().equals(CourseOfActionTypeEnum.NAVIGATION)){
							coaVo=vo;
							break;
						//}
						
					}
				}
			}
			
			checkIndex++;
		}

		return coaVo;
	}

	private CourseOfActionVo getReviewRosterCourseOfActionVo(DialogueVo dialogueVo) {
		CourseOfActionVo coaVo = null;
		
		for(CourseOfActionVo vo : dialogueVo.getProcessedCourseOfActionVos()){
			if(vo.getCoaName().equals("REVIEW_ROSTERED_RESOURCES")){
				coaVo=vo;
				break;
			}
		}
		
		return coaVo;
	}
	
}
