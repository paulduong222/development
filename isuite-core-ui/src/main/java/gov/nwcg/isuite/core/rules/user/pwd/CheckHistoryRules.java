package gov.nwcg.isuite.core.rules.user.pwd;

import gov.nwcg.isuite.core.domain.PasswordHistory;
import gov.nwcg.isuite.core.persistence.PasswordHistoryDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorType;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckHistoryRules extends AbstractPasswordValidationRule implements IRule{
	public static final String _RULE_NAME=PasswordValidationRuleFactory.RuleEnum.CHECK_HISTORY.name();

	public CheckHistoryRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
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
		 * Verify password not already used in last 24 versions
		 */
		FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);
		String newPwd= new String(enc.encrypt(newPassword.getBytes()));
		
		int historyCount = Integer.parseInt(super.getSystemParamValue(SystemParameterTypeEnum.PASSWORD_HISTORY_COUNT));
		
		PasswordHistoryDao phDao=(PasswordHistoryDao)context.getBean("passwordHistoryDao");
		Collection<PasswordHistory> passwords = phDao.getUserHistory(userEntity.getId(), historyCount);
		
		// check if new password has already been used
		for(PasswordHistory phEntity : passwords){
			if(phEntity.getUserPassword().equals(newPwd)){
				
				//throw new ServiceException(new ErrorObject(ErrorEnum._900007_PWD_ERROR));
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setCoaName("PASSWORD_PREVIOUSLY_USED");
				coaVo.setMessageVo(new MessageVo("text.changePassword"
												, ErrorEnum._900007_PWD_ERROR.getErrorName()
												, null
												, MessageTypeEnum.CRITICAL));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
								
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
