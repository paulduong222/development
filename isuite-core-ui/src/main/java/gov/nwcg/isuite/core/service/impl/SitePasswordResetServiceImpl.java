package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.rules.ChangePasswordRulesHandler;
import gov.nwcg.isuite.core.rules.SupportSitePasswordResetRulesHandler;
import gov.nwcg.isuite.core.service.SitePasswordResetService;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorType;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import com.ibm.xml.enc.dom.Base64;

public class SitePasswordResetServiceImpl extends BaseService implements SitePasswordResetService {

	public SitePasswordResetServiceImpl(){
		super();
	}

	
	public DialogueVo resetSiteAdminPassword(String newPwd,DialogueVo dialogueVo) throws ServiceException{
		try{
			if(null == dialogueVo) {
				dialogueVo=new DialogueVo();
			}
			
			byte[] bytes = Base64.decode(newPwd);
			
			FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMFIPSTripleDES);
			String pwd = new String(enc.decrypt(bytes));
				
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
	
		return dialogueVo;
	}
	
	public DialogueVo generateNewPasswordKey(String pwd, String confirmPwd, DialogueVo dialogueVo) throws ServiceException{
		try{
			if(null == dialogueVo) {
				dialogueVo=new DialogueVo();
			}
			
			SupportSitePasswordResetRulesHandler rulesHandler = new SupportSitePasswordResetRulesHandler(context);

			if(rulesHandler.execute(
					pwd,
					confirmPwd, 
					dialogueVo) == ChangePasswordRulesHandler._OK) {
				
				FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMFIPSTripleDES);
				byte[] bytes = enc.encrypt(pwd.getBytes());
				String newEncPwd = Base64.encode(bytes);
				//dialogueVo.setResultObject(newEncPwd);
				dialogueVo.setResultObject(newEncPwd);
				
				// execute follow up actions
				rulesHandler.executeProcessedActions(
						pwd, 
						confirmPwd,
						dialogueVo);

				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("GENERATE_PASSWORD_KEY_COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(
						new MessageVo(
								"", 
								"info.generic", 
								new String[] {"Generating password key complete."}, 
								MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}	
	
		return dialogueVo;
	}
}
