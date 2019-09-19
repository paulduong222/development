package gov.nwcg.isuite.core.rules.login;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.UserNotificationDao;
import gov.nwcg.isuite.core.service.UserSessionManagementService2;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CreateAuthenticationTokenRules extends AbstractLoginRule implements IRule{
	public static final String _RULE_NAME=LoginRuleFactory.RuleEnum.CREATE_AUTHENTICATION_TOKEN.name();

	public CreateAuthenticationTokenRules(ApplicationContext ctx)
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
		
		// add authenticationToken
		AuthenticationManager manager = (AuthenticationManager)context.getBean("_authenticationManager");
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
			new UsernamePasswordAuthenticationToken(TypeConverter.convertToString(user.getId()) ,"pwd");
		
		Authentication authentication = manager.authenticate(usernamePasswordAuthenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// get http session id
		String sid = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
						.getRequest().getSession().getId();

		
		//sessionAdded = usms.manageUserSession(user, UserSessionActionTypeEnum.LOGIN, 
		//		UserSessionActionCauseEnum.USER_INITIATED, null);
		    	
    	//if(!sessionAdded) {
    	//	createLogoutUserPrompt(user, dialogueVo);
    	//	return _FAIL;
    	//}

		

		/*
		 * Site Installed on REmote Server  192.1.1.1
		 * 
		 * Remote Client 1 --  http://192.1.1.1/isuite/fx/welcome.html
		 * 		--Server updated sysparam table with 192.1.1.1 for the report url 
		 * 
		 * Remote Client 2 --  http://192.1.1.1/isuite/fx/welcome.html
		 * 		--Server updated sysparam table with 192.1.1.1 for the report url 
		 * 
		 * CTSP is on remote server, logged in using http://localhost/isuite/fx/welcome.html
		 *   if we implement writing 'localhost' to the sysparam after a user logins, then there will some issues
		 * 		--Server update syspram table to localhost
		 *   This causes an issue for users running site locally on a single laptop, the system has no way
		 *   of knowing if server is same machine or a different machine.  If running on a single machine,
		 *   and reports do not show, do the fix below.
		 * 
		 * Remote Client 1 -- view a report  , http://localhost/isuite/reportsoutput/some.pdf
		 * 
		 * If users experience issues viewing reports, depending on local site server verses remote site server
		 * The fix is to login one time using the "Real" Server Ip address -->  http://192.1.1.1/......
		 */
		Date previousLoginDate = Calendar.getInstance().getTime();
			
		if(super.getRunMode().equals("SITE") && null != dialogueVo.getResultObjectAlternate4()){
			// get tomcat url address from client
			String clientUrlAddr=(String)dialogueVo.getResultObjectAlternate4();

			if(StringUtility.hasValue(clientUrlAddr)){
				String sysParam=super.getSystemParamValue(SystemParameterTypeEnum.REPORT_OUTPUT_URL);
				if(StringUtility.hasValue(sysParam)){
					int idx=sysParam.indexOf("isuite") - 1;
					String beginningPath=sysParam.substring(0, idx);

					// ignoring 'localhost',  see comments above
					if(!clientUrlAddr.contains("localhost")){
						String newReportOutputUrl=sysParam.replaceAll(beginningPath, clientUrlAddr);
						
						SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
						SystemParameter spEntity=spDao.getByParameterName(SystemParameterTypeEnum.REPORT_OUTPUT_URL.name());
						spEntity.setParameterValue(newReportOutputUrl);
						spDao.save(spEntity);
						spDao.flushAndEvict(spEntity);
					}else{
	    		    	((UserSessionVo)this.context.getBean("userSessionVo")).setLocalHostLogin(true);
					}
				}
			}
		}
		
    	// get client local time
    	String clientTime=(String)dialogueVo.getResultObjectAlternate3();
    	if(StringUtility.hasValue(clientTime)){
    		StringTokenizer st = new StringTokenizer(clientTime,"|");
    		int i=0;
    		Date clientDate=null;
    		while(st.hasMoreTokens()){
    			String val=(String)st.nextToken();
    			if(i==0){
    		    	((UserSessionVo)this.context.getBean("userSessionVo")).setClientLocalDate(val);
    		    	clientDate=DateUtil.toDate(val, DateUtil.MM_SLASH_DD_SLASH_YYYY);
    		    	i++;
    			}else{
    		    	((UserSessionVo)this.context.getBean("userSessionVo")).setClientLocalHour(val);
    		    	
    		    	clientDate=DateUtil.addMilitaryTimeToDate(clientDate, DateUtil.toMilitaryTime(val));
    		    	break;
    			}
    		}
    		
    		// get differential
    		String sHourDiff=DateUtil.getHoursDifference(Calendar.getInstance().getTime(), clientDate);
	    	((UserSessionVo)this.context.getBean("userSessionVo")).setClientToServerHourDifference(sHourDiff);
    		
    	}

    	// get client ip
		HttpServletRequest hsr = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
									.getRequest();
    	String remoteAddress = super.getIpFromRequest(hsr);
    	//String remoteAddress = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();    	
    	((UserSessionVo)this.context.getBean("userSessionVo")).setClientIp(remoteAddress);
    	
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
				
				// capture lastlogindate prior to this login before we update it
				previousLoginDate = user.getLastLoginDate();
				userDao.updateLastLogin(usvo.getUserId());
				
				userDao.resetSkipAuditInfo();
			}

			usvo.setLastLoginDate(DateUtil.toDateString(previousLoginDate, DateUtil.MM_SLASH_DD_SLASH_YYYY));
			
			if(DateUtil.hasValue(user.getAccountExpirationDate()))
				usvo.setPasswordExpireDate(DateUtil.toDateString(user.getAccountExpirationDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
			else{
				Date now = Calendar.getInstance().getTime();
				String expireTime=super.getSystemParamValue(SystemParameterTypeEnum.PWD_EXPIRE_TIME);
				if(!StringUtility.hasValue(expireTime))
					expireTime="60";
				usvo.setPasswordExpireDate(DateUtil.toDateString(DateUtil.addDaysToDate(now,Integer.parseInt(expireTime)),DateUtil.MM_SLASH_DD_SLASH_YYYY));
			}
			
			usvo.setUserRoleVos(systemRoleVos);
		}

		if(usvo.getNotifications().size()>0){
			UserNotificationDao unDao = (UserNotificationDao)this.context.getBean("userNotificationDao");
			unDao.updateReadFlag(usvo.getUserId());
		}

		UserSessionManagementService2 usms2 = (UserSessionManagementService2)context.getBean("userSessionManagementService2");
		
		if(super.fromServlet==false){
			DialogueVo sessionDvo=usms2.startSession(usvo, new DialogueVo());
		}
		
		// set client browser time

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaName("AUTHENTICATION");
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setCourseOfActionVo(coa);
		
		// need to create safe uservo without spring context info
		UserSessionVo newUsvo = new UserSessionVo();
		newUsvo.setUserId(usvo.getUserId());
		newUsvo.setUserRoleVos(usvo.getUserRoleVos());
		newUsvo.setRobAgreementValid(usvo.getRobAgreementValid());
		newUsvo.setPrivilegedUser(usvo.getPrivilegedUser());
		if(DateUtil.hasValue(previousLoginDate))
			newUsvo.setLastLoginDate(DateUtil.toDateString(previousLoginDate, DateUtil.MM_SLASH_DD_SLASH_YYYY));
		if(DateUtil.hasValue(user.getAccountExpirationDate()))
			newUsvo.setPasswordExpireDate(DateUtil.toDateString(user.getAccountExpirationDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
		dialogueVo.setResultObject(newUsvo);

		dialogueVo.setResultObjectAlternate4(sid);
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
