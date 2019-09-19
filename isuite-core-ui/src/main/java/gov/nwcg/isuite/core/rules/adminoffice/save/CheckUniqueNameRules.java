package gov.nwcg.isuite.core.rules.adminoffice.save;

import gov.nwcg.isuite.core.domain.AdminOffice;
import gov.nwcg.isuite.core.domain.impl.AdminOfficeImpl;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckUniqueNameRules extends AbstractAdminOfficeSaveRule implements IRule{
	public static final String _RULE_NAME=AdminOfficeSaveRuleFactory.RuleEnum.CHECK_UNIQUE_NAME.name();

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
	 * @throws PersistenceException 
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {	
		
		/*
		 * B.R. 6.0014
		 *  2.	The system must ensure the user enters an Office Name 
		 *      that does not already exist in the system
		 */	
		  AdminOffice checkAdminOffice = dao.getById(vo.getId(), AdminOfficeImpl.class);
					  
					  
		  // if object is not null, this is an edited Admin Office.  If the name has been edited, it requires a unique check
		  // if object is null, this is a new Admin Office and the name requires a unique check
		  if(null == checkAdminOffice 
					  || (null != checkAdminOffice 
					  && !vo.getOfficeName().trim().toUpperCase().equals(checkAdminOffice.getOfficeName().trim().toUpperCase()))){
			  if (null != dao.getByAdminOfficeName(vo.getOfficeName().trim().toUpperCase())) {

					ErrorObject errorObj = new ErrorObject(ErrorEnum.DUPLICATE_ADMIN_OFFICE_NAME, vo.getOfficeName());
								
					MessageVo messageVo = new MessageVo("text.adminOffice", errorObj.getErrorProperty(), errorObj.getParameters(), MessageTypeEnum.CRITICAL);
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName("COMPLETE");
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(messageVo);
					dialogueVo.setCourseOfActionVo(coaVo);
					return _FAIL;
				}
		  }

		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
