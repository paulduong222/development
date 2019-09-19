package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.service.HelpDeskService;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.dbutil.RecoverAccountUtil;
import gov.nwcg.isuite.framework.dbutil.RecoverDbPasswordUtil;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

public class HelpDeskServiceImpl extends BaseService implements HelpDeskService {

	public HelpDeskServiceImpl(){
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.HelpDeskService#generateSiteAccessKey(java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateSiteAccessKey(String code, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{

			// check if valid code
			Boolean isCodeValid=false;
			try{
				isCodeValid=RecoverAccountUtil.isSiteCodeValid(code);
			}catch(Exception ee){
			}
			
			if(isCodeValid==false){
				String msg="The Site Access Code is invalid. Please enter another code.";
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("validationerror");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
				coaVo.setIsDialogueEnding(true);
				ErrorObject errorObject = new ErrorObject("error.800000",new String[]{msg});
				coaVo.getErrorObjectVos().add(errorObject);

				dialogueVo.setCourseOfActionVo(coaVo);
				
				return dialogueVo;
			}
			
			String accessKey=RecoverAccountUtil.generateSiteKey(code);
			// System.out.println(accessKey);
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GEN_SITE_ACCESS_KEY");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsComplete(true);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setResultObject(accessKey);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.HelpDeskService#recoverSiteDatabasePassword(java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo recoverSiteDatabasePassword(String code, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			RecoverDbPasswordUtil util = new RecoverDbPasswordUtil(context);
			String pwd=util.generatePassword(code);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("RECOVER_SITE_PWD");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsComplete(true);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setResultObject(pwd);
			System.out.println(pwd);
			dialogueVo.setCourseOfActionVo(coaVo);

			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public static void main(String[] args) throws Exception {
		HelpDeskServiceImpl service = new HelpDeskServiceImpl();
		//service.recoverSiteDatabasePassword("avsqmXMm2ThG/1eQsVjDVO6xIVqXkLi6", null);
		service.generateSiteAccessKey("2/cJ1rkTdnOgHTA4VcxltJqwJJ/GnVyG", null);
		
	}

}
