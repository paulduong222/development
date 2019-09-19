package gov.nwcg.isuite.core.rules.login;

import gov.nwcg.isuite.core.persistence.hibernate.WorkAreaDaoHibernate;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorType;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.security.Security;

import org.apache.log4j.Logger;
import org.nwcg.www.webservices.security.authentication.Authentication;
import org.nwcg.www.webservices.security.authentication.AuthenticationRequest;
import org.nwcg.www.webservices.security.authentication.AuthenticationResponse;
import org.nwcg.www.webservices.security.authentication.AuthenticationServiceLocator;
import org.springframework.context.ApplicationContext;

import com.ibm.xml.crypto.util.Base64;

public class CheckNapAuthenticationRules extends AbstractLoginRule implements IRule{
	public static final String _RULE_NAME=LoginRuleFactory.RuleEnum.CHECK_NAP_AUTHENTICATION.name();
	private static final Logger LOG = Logger.getLogger(WorkAreaDaoHibernate.class);

	public CheckNapAuthenticationRules(ApplicationContext ctx)
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
		 * only execute this rule if runmode==site or (runmode==enterprise && use_nap_auth==0)
		 */
		Boolean isNapAuth=false;
		String applicationInstance="";
		
		try{
			if(super.getRunMode().equals("ENTERPRISE") && super.getSystemParamValue(SystemParameterTypeEnum.USE_NAP_AUTH).equals("1"))
				isNapAuth=true;
			
		}catch(Exception e){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("AUTHENTICATION_ERROR");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(
					new MessageVo(
							"text.login",
							"info.generic",
							new String[]{"Unable to resolve system parameter USE_NAP_AUTH"},
							MessageTypeEnum.CRITICAL));
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			return _FAIL;
		}
		
		if(isNapAuth==false)
			return _OK;

		/*
		 * Proceed with nap authentication
		 */
		applicationInstance = super.getSystemParamValue(SystemParameterTypeEnum.NAP_APP_INSTANCE);
		
		Security.setProperty("ssl.SocketFactory.provider", "com.ibm.jsse2.SSLSocketFactoryImpl");
		Security.setProperty("ssl.ServerSocketFactory.provider", "com.ibm.jsse2.SSLServerSocketFactoryImpl");
		AuthenticationServiceLocator locator = new AuthenticationServiceLocator();
		
		String napSystemAccount=super.getSystemParamValue(SystemParameterTypeEnum.NAP_SYSTEM_ACCOUNT);
		String napSystemAccountPwd=super.getSystemParamValue(SystemParameterTypeEnum.NAP_SYSTEM_ACCOUNT_PWD);

		FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMFIPSTripleDES);
		Base64 encoder = new Base64();
		byte[] bytes = encoder.decode(napSystemAccount);
		byte[] bytes2=enc.decrypt(bytes);
		locator.userName=new String(bytes2);

		bytes = encoder.decode(napSystemAccountPwd);
		bytes2=enc.decrypt(bytes);
		locator.pwd=new String(bytes2);
		
		//locator.userName=super.userName;
		//locator.pwd=super.password;
		Authentication auth = locator.getAuthenticationSoap11();
		
		AuthenticationRequest request = new AuthenticationRequest();
		request.setApplicationInstance(applicationInstance);
		request.setUserName(super.userName);
		request.setPassword(super.password);

		try{
			LOG.debug("Before - Calling NAP Web Service:");
			AuthenticationResponse response = auth.authentication(request);
			LOG.debug("Before - NAP Web Service Response:" + response);
			if(null != response){
				if(StringUtility.hasValue(response.getStatus()) && response.getStatus().equalsIgnoreCase("ACTIVE")){
					
				}else{
					// account is not active?
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName("ACCOUNT_NOT_ACTIVE");
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE );
					coaVo.setMessageVo(
							new MessageVo(
									"text.login",
									"info.generic",
									new String[]{"The account is not active."},
									MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(true);
					dialogueVo.setCourseOfActionVo(coaVo);
					return _FAIL;
				}
			}
		}catch(Exception ne){
			LOG.error("Exception NAP - " + ne.getMessage());
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("AUTHENTICATION_ERROR");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(
					new MessageVo(
							"text.login",
							"info.generic",
							new String[]{ne.getMessage()},
							MessageTypeEnum.CRITICAL));
			coaVo.setIsDialogueEnding(true);
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

	public static void main(String[] args) {
		try{

			FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMFIPSTripleDES);
			byte[] bytes=enc.encrypt("Mar_72nap".getBytes());
			Base64 encoder = new Base64();
			String val=encoder.encode(bytes);
			System.out.println(val);

			byte[] bytes2 = encoder.decode(val);
			byte[] bytes3=enc.decrypt(bytes2);
			String val2=new String(bytes3);
			System.out.println(val2);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
