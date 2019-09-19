package gov.nwcg.isuite.core.rules.incidentgroup.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class UniqueNameRules extends AbstractIncidentGroupSaveRule implements IRule{
	
	public UniqueNameRules(ApplicationContext ctx, String rname)
	{
		super(ctx,rname);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			if(isCourseOfActionComplete(dialogueVo, ruleName))
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
				.add(super.buildNoActionCoaVo(ruleName,true));
			
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
		 * B.R. 21.0003
		 * 
		 *   5.	The system must prevent the user from creating 
		 *      an Incident Group with the same name assigned to 
		 *      an existing Incident Group for the same Work Area.  
		 * 
		 */

		if (null == originalVo) {
			if (!isNameUnique()) {
				// cannot continue if name is not unique
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(
						new MessageVo(
							"text.incidentGroup", 
							ErrorEnum._0200_DUPLICATE_INCIDENT_GROUP_NAME.getErrorName(), 
							new String[] { newVo.getGroupName().toUpperCase() },
							MessageTypeEnum.CRITICAL));

				dialogueVo.setCourseOfActionVo(coaVo);
				return _FAIL;
			}
		} else {
			/*
			 * if group name changed, verify new one is unique
			 */
			if (!newVo.getGroupName().trim().equals(originalVo.getGroupName().trim())) {
				if (!isNameUnique()) {
					// cannot continue if name is not unique
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(
							new MessageVo(
									"text.incidentGroup",
									ErrorEnum._0200_DUPLICATE_INCIDENT_GROUP_NAME.getErrorName(),
									new String[] { newVo.getGroupName().toUpperCase() }, 
									MessageTypeEnum.INFO));

					dialogueVo.setCourseOfActionVo(coaVo);
					return _FAIL;
				}
			}
		}

		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

	private Boolean isNameUnique() throws Exception {
		// swap these lines (...workAreaVo.getId) when workArea is available 
		if(incidentGroupDao.getByGroupName(newVo.getGroupName().toUpperCase(), null) != null) 
		//if(incidentGroupDao.getByGroupName(newVo.getGroupName().toUpperCase(), newVo.getWorkAreaVo().getId()) != null) 
		    return false;
	   else
			return true;
	}
}
