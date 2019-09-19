package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.UserNotificationDao;
import gov.nwcg.isuite.core.rules.LoginRulesHandler;
import gov.nwcg.isuite.core.service.AuthenticationService;
import gov.nwcg.isuite.core.service.UserSessionManagementService;
import gov.nwcg.isuite.core.service.UserSessionManagementService2;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorType;
import gov.nwcg.isuite.framework.exceptions.AuthenticationException;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.security.ldap.LdapClient;
import gov.nwcg.isuite.framework.security.ldap.LdapProperties;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionCauseEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class AuthenticationServiceImpl extends BaseService implements AuthenticationService {
	private UserDao userDao;
	private LdapProperties ldapProperties;
	
	public AuthenticationServiceImpl(){
		
	}

	public void setLdapProperties(LdapProperties ldapProperties) {
		this.ldapProperties=ldapProperties;
	}
	
	public AuthenticationServiceImpl(UserDao userDao){
		if ( userDao == null ) {throw new IllegalArgumentException("userDao cannot be null");}
		this.userDao = userDao;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AuthenticationService#simulateLogin(java.lang.Long)
	 */
	public UserSessionVo simulateLogin(Long userId) throws ServiceException,AuthenticationException {
		UserSessionVo userSessionVos= new UserSessionVo();
		
		if( null == userId || userId<1L)
			throw new ServiceException("Unable to simulate login with an invalid userId");
		
		try{
			/*
			 * Read the user table for userId
			 */
			User user = userDao.getById(userId,UserImpl.class);
	
			if( (null==user)){
				throw new AuthenticationException("auth failure","error.0019");
			}

			if(user.getId().equals(userId))
			{
		        AuthenticationManager manager = (AuthenticationManager)context.getBean("_authenticationManager");
		        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
		                new UsernamePasswordAuthenticationToken(TypeConverter.convertToString(user.getId()) ,"pwd");
			
		        Authentication authentication = manager.authenticate(usernamePasswordAuthenticationToken);
		        SecurityContextHolder.getContext().setAuthentication(authentication);
			        
		        UserSessionVo usvo = (UserSessionVo)this.context.getBean("userSessionVo");
		        if(null != usvo){
		        	userDao.updateLastLogin(usvo.getUserId());
		        }
			        
		        return usvo;
			}
		}catch(AuthenticationException ae){
			throw ae;
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return null;
	}

	/**
	 * @param userName
	 * @param password
	 * @return
	 * @throws ServiceException
	 * @throws AuthenticationException
	 */
	public UserSessionVo loginLdapOld(String userName, String password) throws ServiceException,AuthenticationException {
		
		try{
			User user = userDao.getByLoginName(userName);

			if(null != user)
				return this.simulateLogin(user.getId());
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AuthenticationService#loginLdap(java.lang.String, java.lang.String)
	 */
	public UserSessionVo loginLdap(String userName, String password) throws ServiceException,AuthenticationException {
		
		try{
			LdapClient client = new LdapClient(ldapProperties);
			
			if(client.checkCredentials(userName, password)){
				
				User user = userDao.getByLoginName(userName.toUpperCase());
				
				if( (null==user)){
					throw new AuthenticationException("auth failure","error.900004");
				}

				if( !user.isEnabled()){
					throw new AuthenticationException("auth failure","error.900005");
				}
				
		        AuthenticationManager manager = (AuthenticationManager)context.getBean("_authenticationManager");
		        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
		                new UsernamePasswordAuthenticationToken(TypeConverter.convertToString(user.getId()) ,"pwd");
				
		        Authentication authentication = manager.authenticate(usernamePasswordAuthenticationToken);
		        SecurityContextHolder.getContext().setAuthentication(authentication);
				        
	        	userDao.updateLastLogin(user.getId());
				        
	        	UserSessionVo usvo = (UserSessionVo)this.context.getBean("userSessionVo");
	        	return usvo;
			}
			

		}catch(Exception e){
			throw new ServiceException(new ErrorObject(ErrorEnum._900004_AUTHENTICATION_FAILED));
		}
		
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AuthenticationService#login(java.lang.String, java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo login(String userName, String password, DialogueVo dialogueVo) throws ServiceException,AuthenticationException {
		Boolean sessionAdded = false;
		
		try{
		  
		  if(dialogueVo == null) {
			  dialogueVo = new DialogueVo();
		  }
		  
	    UserSessionManagementService usms = (UserSessionManagementService)context.getBean("userSessionManagementService");

	    /*
	     * Read the user table for userId
	     */
	    User user = userDao.getByLoginName(userName);

	    if( (null==user)){
	      throw new AuthenticationException(getIsuiteProperty("error.0022"));
	    }else if((null!=user.isEnabled())&&(!user.isEnabled())){
	      throw new AuthenticationException(getIsuiteProperty("error.0005"));
	    }else if(null!=user.getDeletedDate()){
	      throw new AuthenticationException(getIsuiteProperty("error.0005"));
	    }

	    // verify password
	    FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);
	    String pwd = new String(enc.encrypt(password.getBytes()));
	    if(!user.getPassword().equals(pwd)){
	      user.setFailedLoginAttempts(user.getFailedLoginAttempts()+1);

	      usms.manageUserSession(user, UserSessionActionTypeEnum.INVALID_LOGIN, 
            UserSessionActionCauseEnum.USER_INITIATED, null);
	      
	      String lockCount=super.getSystemParamValue(SystemParameterTypeEnum.FAILED_LOGIN_ATTEMPTS_LOCK);
	      if(null != lockCount && !lockCount.isEmpty()){
	        int lockVal=Integer.parseInt(lockCount);
	        if(user.getFailedLoginAttempts() >= lockVal){
	          user.setEnabled(false);
	          userDao.save(user);
	          throw new AuthenticationException(getIsuiteProperty("error.8002"));
	        }else{
	          throw new AuthenticationException(getIsuiteProperty("error.0003"));
	        }
	      }else{
	        throw new ServiceException("Unable to get setting for failed login attempts.");
	      }
	    } else {
	      // verify user does not have a session open
	      if(usms.isUserLoggedIn(user.getId())) {
	    	  return createLogoutUserPrompt(user, dialogueVo);
	      }
	      
	      sessionAdded = usms.manageUserSession(user, UserSessionActionTypeEnum.INITIATE_LOGIN, 
	                             UserSessionActionCauseEnum.USER_INITIATED, null);
	      
	      user.setFailedLoginAttempts(0);
	      userDao.setSkipSetAuditInfo(true);
	      userDao.save(user);
	      userDao.resetSkipAuditInfo();
	      
	      if(!sessionAdded) {
	    	  return createLogoutUserPrompt(user, dialogueVo);
	      }
	    }

	    // add authenticationToken
	    AuthenticationManager manager = (AuthenticationManager)context.getBean("_authenticationManager");
	    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
	      new UsernamePasswordAuthenticationToken(TypeConverter.convertToString(user.getId()) ,"pwd");
	    /*
      usernamePasswordAuthenticationToken.setDetails(new SessionIdentifierAware(){

      	@Override
      	public String getSessionId() {
          	ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
          	return attr.getSessionId();
      	}

    	});
	     */
	    Authentication authentication = manager.authenticate(usernamePasswordAuthenticationToken);
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    
	    String sid = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
	    	.getRequest().getSession().getId();
	    
	    
    	sessionAdded = usms.manageUserSession(user, UserSessionActionTypeEnum.LOGIN, 
			UserSessionActionCauseEnum.USER_INITIATED, null);
	    	
    	if(!sessionAdded) {
    		return createLogoutUserPrompt(user, dialogueVo);
    	}
		
	    
	    // get roles
	    GrantedAuthority[] userAuthories = authentication.getAuthorities();
	    Collection<SystemRoleVo> systemRoleVos = new ArrayList<SystemRoleVo>();
	    for(GrantedAuthority userAuth : userAuthories) {
	      SystemRoleVo srv = new SystemRoleVo();
	      srv.setRoleName(userAuth.getAuthority());
	      systemRoleVos.add(srv);
	    }

	    // 
	    UserSessionVo usvo = (UserSessionVo)this.context.getBean("userSessionVo");
	    if(null != usvo){
	      // only update if lastlogindate is not null
	      // if lastlogindate is null, this is the first time the user is logging in
	      // we want to force them to change password
	      if(null!=user.getLastLoginDate()){
	        userDao.setSkipSetAuditInfo(true);
	        userDao.updateLastLogin(usvo.getUserId());
	        userDao.resetSkipAuditInfo();
	      }
	      usvo.setUserRoleVos(systemRoleVos);
	    }

	    if(usvo.getNotifications().size()>0){
	      UserNotificationDao unDao = (UserNotificationDao)this.context.getBean("userNotificationDao");
	      unDao.updateReadFlag(usvo.getUserId());
	    }
	    
	    CourseOfActionVo coa = new CourseOfActionVo();
	    coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
	    dialogueVo.setCourseOfActionVo(coa);
	    dialogueVo.setResultObject(usvo);
	    
		Collection<String> values = new ArrayList<String>();
		values.add(sid);
		dialogueVo.setRecordset(values);// Return the single sessionId.
  
	    return dialogueVo;
	  }catch(AuthenticationException ae){
	    throw ae;
	  }catch(Exception e){
	    throw new ServiceException(e);
	  }
		
	}

	public DialogueVo login2(String userName, String password, DialogueVo dialogueVo) throws ServiceException,AuthenticationException {
		if(dialogueVo == null)dialogueVo = new DialogueVo();

		try{
			
			LoginRulesHandler ruleHandler = new LoginRulesHandler(context);
			if(ruleHandler.execute(userName, password, false, dialogueVo) == AbstractRule._OK){
				try{
					if(super.getRunMode().equals("ENTERPRISE") && super.getSystemParamValue(SystemParameterTypeEnum.USE_NAP_AUTH).equals("1"))
						dialogueVo.setResultObjectAlternate4("USENAP");
				}catch(Exception e){
				}
				
				return dialogueVo;
			}

		}catch(AuthenticationException ae){
			super.dialogueException(dialogueVo, ae);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	private DialogueVo createLogoutUserPrompt(User user, DialogueVo dialogueVo) throws Exception {
		PromptVo promptVo = new PromptVo();
        promptVo.setMessageProperty("action.0246");
        promptVo.setTitleProperty("text.login");
        promptVo.setPromptValue(PromptVo._YES | PromptVo._NO);
        CourseOfActionVo coa = new CourseOfActionVo();
        coa.setCoaType(CourseOfActionTypeEnum.PROMPT);
        coa.setPromptVo(promptVo);
        dialogueVo.setCourseOfActionVo(coa);
        UserSessionVo userSessionVo = UserSessionVo.getInstance(user);
        dialogueVo.setResultObject(userSessionVo);
        return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AuthenticationService#loginOverride(java.lang.String, java.lang.String)
	 */
	public UserSessionVo loginOverride(String userName, String password) throws ServiceException,AuthenticationException {
		try{
			/*
			 * Read the user table for userId
			 */
			User user = userDao.getByLoginName(userName);
	
			if( (null==user)){
				throw new AuthenticationException(getIsuiteProperty("error.0022"));
			}else if((null!=user.isEnabled())&&(!user.isEnabled())){
				throw new AuthenticationException(getIsuiteProperty("error.0005"));
			}else if(null!=user.getDeletedDate()){
				throw new AuthenticationException(getIsuiteProperty("error.0005"));
			}

			// verify password
			FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);
			String newPwd= new String(enc.encrypt(password.getBytes()));
			if(!user.getPassword().equals(newPwd)){
				throw new AuthenticationException(getIsuiteProperty("error.0003"));
			}
			
			UserSessionManagementService usms = 
			    (UserSessionManagementService)context.getBean("userSessionManagementService");
			User sessionUser = userDao.getByLoginName(
			    ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
			        .getRequest().getUserPrincipal().getName() );
			usms.manageUserSession(sessionUser, UserSessionActionTypeEnum.LOGIN_OVERRIDE, 
          UserSessionActionCauseEnum.USER_INITIATED, user);
			
			UserSessionVo usvo = UserSessionVo.getInstance(user);
			
			Collection<String> perms = userDao.getPermissionsForUser(user.getId());
			usvo.setPermissions(perms);
			
	        return usvo;
		}catch(AuthenticationException ae){
			throw ae;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AuthenticationService#endSession(gov.nwcg.isuite.core.vo.WorkAreaVo, java.lang.Integer, java.lang.Boolean, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo endSession(WorkAreaVo currentWorkAreaVo, Integer userId, Boolean isConcurrentSessionLogout, DialogueVo dialogueVo) throws ServiceException {
		try {
			Long uId = IntegerUtility.convertToLong(userId);
			UserDao userDao = (UserDao)super.context.getBean("userDao");

			User user = userDao.getById(uId, UserImpl.class);

			UserSessionManagementService2 usms2 = (UserSessionManagementService2)context.getBean("userSessionManagementService2");
			
			UserSessionVo userSessionVo = (UserSessionVo)context.getBean("userSessionVo");
			UserVo userVo = new UserVo();
			userVo.setId(uId);
			if(null != dialogueVo && null != dialogueVo.getResultObjectAlternate4()){
				if(BooleanUtility.isTrue((Boolean)dialogueVo.getResultObjectAlternate4())){
					dialogueVo=usms2.endSession(userVo, userSessionVo.getSiteDatabaseName(), "TIMEOUT", dialogueVo);
				}else{
					dialogueVo=usms2.endSession(userVo, userSessionVo.getSiteDatabaseName(), "USER_LOGOUT", dialogueVo);
				}
			}else
				dialogueVo=usms2.endSession(userVo, userSessionVo.getSiteDatabaseName(), "USER_LOGOUT", dialogueVo);

			/*
			String sessionId = usms.getUserSessionId(uId);
			if(sessionId != null) {
				usms.manageUserSession(user, UserSessionActionTypeEnum.LOGOUT, 
						UserSessionActionCauseEnum.USER_INITIATED, null);
	
				SecurityContext securityContext = SecurityContextHolder.getContext();
				securityContext.setAuthentication(null);
				
				if(isConcurrentSessionLogout) {
					usms.sendMessageToClient(sessionId);
				}
			}
			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}
			*/
			
			((UserSessionVo)context.getBean("userSessionVo")).setSiteDatabaseName("");
			((UserSessionVo)context.getBean("userSessionVo")).setSiteDatasourceName("");
			
			MessageVo mvo = new MessageVo();
			mvo.setMessageType(MessageTypeEnum.INFO);
			mvo.setMessageProperty("error.0249");//Being used as an info message.
			mvo.setTitleProperty("text.login");
			mvo.setParameters(new String[]{user.getLoginName()});
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coa.setMessageVo(mvo);
			coa.setStoredObject("SUCCESS");
			dialogueVo.setCourseOfActionVo(coa);
			dialogueVo.setResultObject(isConcurrentSessionLogout);
			
			CourseOfActionVo coaVo2 = new CourseOfActionVo();
			coaVo2.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			dialogueVo.addSupplementalCourseOfActionVo(coaVo2);
			
			return dialogueVo;

		}
		catch(Exception e) {
			super.handleException(e);
		}

		return null;
	}
	
	
}
