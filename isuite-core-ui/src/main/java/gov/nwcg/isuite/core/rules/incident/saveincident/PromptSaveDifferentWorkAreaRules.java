package gov.nwcg.isuite.core.rules.incident.saveincident;

import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.RestrictedIncidentUserImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;

public class PromptSaveDifferentWorkAreaRules extends AbstractIncidentSaveRule implements IRule{
	public static final String _RULE_NAME=SaveIncidentRuleFactory.RuleEnum.PROMPT_SAVE_DIFFERENT_WORK_AREA.name();

	public PromptSaveDifferentWorkAreaRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;
			

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				/*
				 * Check prompt result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;
				
			}else{

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
				
			}
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		/*
		 * Only check if run mode is enterprise
		 */
		if(!super.getRunMode().equals("ENTERPRISE")){
			return _OK;
		}
		
		if(null != workAreaVo && null != vo){
			Boolean prompt=false;
			
			if(null == workAreaVo.getStandardOrganizationVo()){
				prompt=true;
			}else{
				if(workAreaVo.getStandardOrganizationVo().getId().compareTo(vo.getPdcVo().getId())!=0)
					prompt=true;
			}
			
			//else if(!vo.getHomeUnitVo().getUnitCode().equals(workAreaVo.getStandardOrganizationVo().getUnitCode())){
			//	prompt=true;
			//}
			
			if(prompt){
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
				coaVo.setPromptVo(new PromptVo("text.incident","action.0245",new String[]{vo.getIncidentName().toUpperCase(),"ANOTHER WORK AREA"},PromptVo._YES | PromptVo._NO ));
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
			}
		}
	
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {
	
		// check prompt result
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// continue;
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			// cannot continue if prompt was cancel post
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incident", "text.abortProcess" , new String[]{"saving incident"}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa && coa.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
			if(null != incidentEntity){
				
				incidentEntity.setRestricted(true);
				
				RestrictedIncidentUser riu = new RestrictedIncidentUserImpl();
				
				User user = new UserImpl();
				user.setId(super.getUserVo().getId());
				riu.setUser(user);
				riu.setUserType(RestrictedIncidentUserTypeEnum.OWNER);
				riu.setIncident(incidentEntity);
				
				incidentEntity.setRestrictedIncidentUsers(new ArrayList<RestrictedIncidentUser>());
				incidentEntity.getRestrictedIncidentUsers().add(riu);
				
				IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
				
				incidentDao.save(incidentEntity);
				
			}
		}
		
	}

}
