package gov.nwcg.isuite.core.rules.timepost.personnel;

import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class DuplicateTimePostSpecialRules extends AbstractPersonnelRule implements IRule {
	public static final String _RULE_NAME="DUPLICATE_TIME_POST_SPECIAL";

	public DuplicateTimePostSpecialRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{

			/*
			 * if rule check completed,
			 * return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;
			

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

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
		// if other rule found duplicate, skip this check
		for(CourseOfActionVo coaVo : dialogueVo.getProcessedCourseOfActionVos()){
			if(coaVo.getCoaName().equals(DuplicateTimePostRules._RULE_NAME)){
				if(null != coaVo.getPromptVo()){
					if(coaVo.getPromptVo().getPromptResult()==PromptVo._YES){
						return _OK;
					}
				}
			}
		}

		/*
		 *	If the current posting for a Resource overlaps a previous posting, 
		 *  the system must display an action message confirming that the current 
		 *  posting should replace the previous posting. Yes and No buttons are available. 
		 *  [Message 0139] (Applies to Use Cases 6.0011 and 6.0012.)
		 *  
		 *  This rule checks for Special Travel overlap when ReturnTravelStartOnly=true
		 */
		if(null != vo){
			
			/*
			 * Only fed,ad, and other resources require start and stop times.
			 * 
			 * Determine if the new start and stop times overlap 
			 * an existing time posting time period.
			 */
			if(super.hasOverlap(dialogueVo) || super.hasDuplicate(dialogueVo)) {
				return _OK;
			}
			
			if(null != incidentResourceEntity && incidentResourceEntity.getId() > 0){

				if(BooleanUtility.isTrue(vo.getReturnTravelStartOnly())){
					Date startTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
					
					if(null != startTime){
						int dupCount = tpDao.getDuplicateTimePostCountSpecial(vo.getId(),incidentResourceEntity.getId(), false, startTime,null);
									
						if(dupCount > 0){
							Collection<Long> ids = tpDao.getDuplicateTimePostIdsSpecial(vo.getId(),incidentResourceEntity.getId(), false, startTime,null);
							
							CourseOfActionVo coaVo = new CourseOfActionVo();
							coaVo.setCoaName(_RULE_NAME);
							coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
							coaVo.setPromptVo(new PromptVo("text.time","action.0139",new String[]{DateUtil.toDateString(startTime)},PromptVo._YES | PromptVo._NO));
							coaVo.setStoredObject(ids);


							dialogueVo.setCourseOfActionVo(coaVo);
							return _FAIL;
						}
					}else{
						dialogueVo.setCourseOfActionVo(
								super.buildErrorCoaVo("text.time"
													,"validationerror"
													,"error.null"
													,new String[]{"Start Time"}));	
						return _FAIL;
					}
				}
							
						
			}
		}
		
		return _OK;

	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {

		// check prompt result
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// add to processed
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getCourseOfActionVo().setIsComplete(true);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		}else{
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.time", "text.abortProcess" , new String[]{"post time"}, MessageTypeEnum.INFO));

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
		
		if(null != coa){
			if(getPromptResult(dialogueVo) == PromptVo._YES) {
				
				if(null != coa.getStoredObject()){
					Collection<Object> ids = (Collection<Object>)coa.getStoredObject();
					/* 
					 * Delete the original time posts that this new time post
					 * replaces.
					 */
					/*
					TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");
					for(Object id : ids){
						Long longId=TypeConverter.convertToLong(id);
						tpDao.deleteById(longId);
					}
					*/
				}
				
			}
		}
	}
	
}
