package gov.nwcg.isuite.core.rules.workarea.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class CheckUniqueNameRules extends AbstractSaveWorkAreaRule implements IRule{
	public static final String _RULE_NAME=WorkAreaSaveRuleFactory.RuleEnum.CHECK_UNIQUE_NAME.name();

	public CheckUniqueNameRules(ApplicationContext ctx)
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
	
		/*
		 *  3.	The system must ensure that the name associated with a Work Area 
		 *      is unique to the User Account and is not the name of any Standard 
		 *      Work Areas in the system.
		 * 
		 */

		String msgProperty="";
		
		/*
		 * Check adding a new Entity
		 */
		if(null == entity){
			
			// new work area, check the name uniqueness
			if(!workAreaDao.isWorkAreaNameUniqueToUser(null, workAreaVo.getName(), super.getUserVo().getId())){
				//throw new ServiceException(ErrorEnum._0058_WORK_AREA_NAME_ALREADY_ASSIGNED);
				msgProperty=ErrorEnum._0058_WORK_AREA_NAME_ALREADY_ASSIGNED.getErrorName();
			}
			
			if(!workAreaDao.isWorkAreaNameUniqueStandard(workAreaVo.getName())){
				//throw new ServiceException(ErrorEnum._0058_WORK_AREA_NAME_ALREADY_ASSIGNED);
				msgProperty=ErrorEnum._0058_WORK_AREA_NAME_ALREADY_ASSIGNED.getErrorName();
			}
		}
		
		/*
		 * Check updating entity
		 */
		if(null != entity){
			
			if(!entity.getName().equals(workAreaVo.getName())){
				// verify new name is unique
				if(!workAreaDao.isWorkAreaNameUniqueToUser(entity.getId(), workAreaVo.getName(), super.getUserVo().getId())){
					//throw new ServiceException(ErrorEnum._0058_WORK_AREA_NAME_ALREADY_ASSIGNED);
					msgProperty=ErrorEnum._0058_WORK_AREA_NAME_ALREADY_ASSIGNED.getErrorName();
				}
				
				if(!workAreaDao.isWorkAreaNameUniqueStandard(workAreaVo.getName())){
					//throw new ServiceException(ErrorEnum._0058_WORK_AREA_NAME_ALREADY_ASSIGNED);
					msgProperty=ErrorEnum._0058_WORK_AREA_NAME_ALREADY_ASSIGNED.getErrorName();
				}
			}
			
			
		}

		
		if(StringUtility.hasValue(msgProperty)){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.workArea"
											, msgProperty
											, null
											, MessageTypeEnum.CRITICAL));
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
		
	}

}
