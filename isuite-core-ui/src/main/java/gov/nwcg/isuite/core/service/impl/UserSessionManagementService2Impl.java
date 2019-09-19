package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserSessionLog;
import gov.nwcg.isuite.core.domain.UserSessionLogActivity;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.domain.impl.UserSessionLogActivityImpl;
import gov.nwcg.isuite.core.domain.impl.UserSessionLogImpl;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.UserSessionLogActivityDao;
import gov.nwcg.isuite.core.persistence.UserSessionLogDao;
import gov.nwcg.isuite.core.service.UserSessionManagementService2;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.UserSessionLogActivityVo;
import gov.nwcg.isuite.core.vo.UserSessionLogVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.NavigateVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.ApplicationNavigationTypeEnum;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionCauseEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.text.StrTokenizer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserSessionManagementService2Impl extends BaseService implements UserSessionManagementService2 {
	private UserSessionLogDao userSessionLogDao = null;
	private UserSessionLogActivityDao userSessionLogActivityDao = null;
	private UserDao userDao = null;
	
	private static HashMap<Long, UserSessionLogVo> enterpriseSessionsMap = new HashMap<Long, UserSessionLogVo>();
	private static HashMap<String, HashMap> siteSessionsMap = new HashMap<String, HashMap>();
	
	private String sysRunMode;
	
	public UserSessionManagementService2Impl() {
	}

	public void initialization(){
		userSessionLogDao = (UserSessionLogDao)context.getBean("userSessionLogDao");
		userSessionLogActivityDao = (UserSessionLogActivityDao)context.getBean("userSessionLogActivityDao");
		userDao = (UserDao)context.getBean("userDao");
	}

	private User buildUser(UserSessionVo userSessionVo) {
		User user = new UserImpl();
		user.setId(userSessionVo.getUserId());
		user.setFirstName(userSessionVo.getFirstName());
		user.setLastName(userSessionVo.getLastName());
		user.setLoginName(userSessionVo.getUserLoginName());
		Organization org = new OrganizationImpl();
			org.setUnitCode(userSessionVo.getHomeUnitCode());
		user.setHomeUnit(org);
		Organization pdc = new OrganizationImpl();
			pdc.setUnitCode(userSessionVo.getPdcUnitCode());
		user.setPrimaryDispatchCenter(pdc);
		
		return user;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserSessionManagementService2#endSession(gov.nwcg.isuite.core.vo.UserVo, java.lang.String, java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo endSession(UserVo userVo, String databaseName, String actionType, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			UserSessionLogVo userSessionLogVo = null;
			Long userId=0L;
			
			// get UserSessionLogVo from sessions map
			if(super.getRunMode().equals("ENTERPRISE")){
				if(enterpriseSessionsMap.containsKey(userVo.getId())){
					userSessionLogVo = enterpriseSessionsMap.get(userVo.getId());
				}
			}else{
				if(siteSessionsMap.containsKey(databaseName.toLowerCase())){
					HashMap<Long,UserSessionLogVo> siteMap=siteSessionsMap.get(databaseName.toLowerCase());
					if(siteMap.containsKey(userVo.getId()))
						userSessionLogVo = siteMap.get(userVo.getId());
				}
			}
			
			if(null != userSessionLogVo){
				userId=userSessionLogVo.getUserVo().getId();
				
				// user session log activity vo
				UserSessionLogActivity userSessionLogActivity = new UserSessionLogActivityImpl();	 
				userSessionLogActivity.setActionType(UserSessionActionTypeEnum.LOGOUT);

				if(actionType.equalsIgnoreCase("USER_LOGOUT"))
					userSessionLogActivity.setActionCause(UserSessionActionCauseEnum.USER_INITIATED);
				else if(actionType.equalsIgnoreCase("DISCONNECTED"))
					userSessionLogActivity.setActionCause(UserSessionActionCauseEnum.DISCONNECTED);
				else if(actionType.equalsIgnoreCase("TIMEOUT"))
					userSessionLogActivity.setActionCause(UserSessionActionCauseEnum.TIMEOUT);
				else if(actionType.equalsIgnoreCase("LOCKED_DISABLED"))
					userSessionLogActivity.setActionCause(UserSessionActionCauseEnum.ACCOUNT_LOCKED_OR_DISABLED);

				UserSessionLog userSessionLog = new UserSessionLogImpl();
				userSessionLog.setId(userSessionLogVo.getId());
				userSessionLogActivity.setUserSessionLog(userSessionLog);
				
				userSessionLogActivity.setCreatedDate(new Date());
				
				//if(!actionType.equals("TIMEOUT")){
					this.userSessionLogActivityDao.save(userSessionLogActivity);
					this.userSessionLogActivityDao.flushAndEvict(userSessionLogActivity);
				//}
				
				// remove from map
				if(super.getRunMode().equals("ENTERPRISE")){
					if(enterpriseSessionsMap.containsKey(userVo.getId())){
						enterpriseSessionsMap.remove(userVo.getId());
					}
				}else{
					if(siteSessionsMap.containsKey(databaseName.toLowerCase())){
						HashMap<Long,UserSessionLogVo> siteMap=siteSessionsMap.get(databaseName.toLowerCase());
						if(siteMap.containsKey(userVo.getId())){
							siteMap.remove(userVo.getId());
							siteSessionsMap.put(databaseName.toLowerCase(), siteMap);
						}
					}
				}
			}
			
			if(!actionType.equals("TIMEOUT")){
				if(LongUtility.hasValue(userId)){
					User user = userDao.getById(userId, UserImpl.class);
					createAuditEventRec("LOGIN EVENT","SUCCESSFUL LOGOUT",user.getId(),user);
					userDao.flushAndEvict(user);
				}
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserSessionManagementService2#startSession(gov.nwcg.isuite.core.vo.UserSessionVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo startSession(UserSessionVo userSessionVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			UserSessionLogVo userSessionLogVo = new UserSessionLogVo();
			HttpServletRequest hsr = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
									.getRequest();
			String remoteAddress=super.getIpFromRequest(hsr);
			//String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
			//			.getRequest().getRemoteAddr();
			String sessionId = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
						.getRequest().getSession().getId();
			Date lastStatusCheck = Calendar.getInstance().getTime();

			User user = userDao.getById(userSessionVo.getUserId(), UserImpl.class);
			
			userSessionLogVo.setIpAddress(remoteAddress);
			userSessionLogVo.setSessionId(sessionId);
			userSessionLogVo.setLastStatusCheck(lastStatusCheck);
			UserVo userVo = new UserVo();
			userVo.setId(userSessionVo.getUserId());
			userSessionLogVo.setUserVo(userVo);
			userSessionLogVo.setLastName(user.getLastName());
			userSessionLogVo.setFirstName(user.getFirstName());
			userSessionLogVo.setUserLoginName(user.getLoginName());
			userSessionLogVo.setUnitCode(user.getHomeUnit().getUnitCode());
			userSessionLogVo.setCreatedBy(userSessionVo.getUserLoginName());
			userSessionLogVo.setCreatedDate(new Date());

			userDao.flushAndEvict(user);
			
			// user session log activity vo
			UserSessionLogActivityVo userSessionLogActivityVo = new UserSessionLogActivityVo();	 
			userSessionLogActivityVo.setActionType(UserSessionActionTypeEnum.LOGIN);
			userSessionLogActivityVo.setActionCause(UserSessionActionCauseEnum.USER_INITIATED);
			userSessionLogActivityVo.setCreatedDate(new Date());

			userSessionLogVo.getUserSessionLogActivityVos().add(userSessionLogActivityVo);
			
			UserSessionLog entity = UserSessionLogVo.toEntity(null, userSessionLogVo, true);
			
			this.userSessionLogDao.save(entity);
			Long id = entity.getId();
			this.userSessionLogDao.flushAndEvict(entity);
			entity=this.userSessionLogDao.getById(id, UserSessionLogImpl.class);
			userSessionLogVo.setId(entity.getId());
			
			// add to sessions map
			if(super.getRunMode().equals("ENTERPRISE")){
				enterpriseSessionsMap.put(userVo.getId(), userSessionLogVo);
			}else{
				String databaseName=userSessionVo.getSiteDatabaseName().toLowerCase();
				if(this.siteSessionsMap.containsKey(databaseName)){
					HashMap<Long,UserSessionLogVo> siteMap=siteSessionsMap.get(databaseName);
					siteMap.put(userVo.getId(), userSessionLogVo);
					siteSessionsMap.put(databaseName, siteMap);
				}else{
					HashMap<Long,UserSessionLogVo> siteMap=new HashMap<Long, UserSessionLogVo>();
					siteMap.put(userVo.getId(), userSessionLogVo);
					siteSessionsMap.put(databaseName, siteMap);
				}
			}
			
			createAuditEventRec("LOGIN EVENT","SUCCESSFUL LOGIN",user.getId(),user);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserSessionManagementService2#isUserLoggedIn(java.lang.Long, java.lang.String)
	 */
	public Boolean isUserLoggedIn(Long userId, String databaseName) throws ServiceException {
		try{
			UserSessionLogVo userSessionLogVo =null;
			HttpServletRequest hsr = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
										.getRequest();
			String remoteAddress=super.getIpFromRequest(hsr);

			//String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
			//							.getRequest().getRemoteAddr();
			
			if(super.getRunMode().equals("ENTERPRISE")){
				if(enterpriseSessionsMap.containsKey(userId)){
					userSessionLogVo = enterpriseSessionsMap.get(userId);
					if(userSessionLogVo.getIpAddress().equals(remoteAddress)){
						// if user has orphaned session from same ip address
						// then clear out orphaned session
						enterpriseSessionsMap.remove(userId);
						
						// log it
						UserSessionLogActivity userSessionLogActivity = new UserSessionLogActivityImpl();	 
						userSessionLogActivity.setActionType(UserSessionActionTypeEnum.LOGOUT);
						userSessionLogActivity.setActionCause(UserSessionActionCauseEnum.ORPHANED_OR_ABANDONED);

						UserSessionLog userSessionLog = new UserSessionLogImpl();
						userSessionLog.setId(userSessionLogVo.getId());
						userSessionLogActivity.setUserSessionLog(userSessionLog);
						
						userSessionLogActivity.setCreatedDate(new Date());
						
						this.userSessionLogActivityDao.save(userSessionLogActivity);
						this.userSessionLogActivityDao.flushAndEvict(userSessionLogActivity);
						
					}else
						return true;
				}
			}else{
				if(siteSessionsMap.containsKey(databaseName.toLowerCase())){
					HashMap<Long,UserSessionLogVo> siteMap=siteSessionsMap.get(databaseName.toLowerCase());
					if(siteMap.containsKey(userId)){
						userSessionLogVo = siteMap.get(userId);
						if(userSessionLogVo.getIpAddress().equals(remoteAddress)){
							// if user has orphaned session from same ip address
							// then clear out orphaned session
							siteMap.remove(userId);
							siteSessionsMap.put(databaseName.toLowerCase(), siteMap);
							
							// log it
							UserSessionLogActivity userSessionLogActivity = new UserSessionLogActivityImpl();	 
							userSessionLogActivity.setActionType(UserSessionActionTypeEnum.LOGOUT);
							userSessionLogActivity.setActionCause(UserSessionActionCauseEnum.ORPHANED_OR_ABANDONED);

							UserSessionLog userSessionLog = new UserSessionLogImpl();
							userSessionLog.setId(userSessionLogVo.getId());
							userSessionLogActivity.setUserSessionLog(userSessionLog);
							
							userSessionLogActivity.setCreatedDate(new Date());
							
							this.userSessionLogActivityDao.save(userSessionLogActivity);
							this.userSessionLogActivityDao.flushAndEvict(userSessionLogActivity);
							
						}else
							return true;
					}
				}
			}
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserSessionManagementService2#userSessionPing(java.lang.Long, java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo userSessionPing(Long userId, String databaseName, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Boolean isSessionActive=false;

			HttpServletRequest hsr = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
										.getRequest();
			String remoteAddress=super.getIpFromRequest(hsr);
			//String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
			//							.getRequest().getRemoteAddr();
			String sessionId = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
										.getRequest().getSession().getId();
			
			if(super.getRunMode().equals("ENTERPRISE")){
				if(enterpriseSessionsMap.containsKey(userId)){
					UserSessionLogVo userSessionLogVo = enterpriseSessionsMap.get(userId);
					if(userSessionLogVo.getSessionId().equals(sessionId)){
						Date dtStatusCheck=Calendar.getInstance().getTime();
						userSessionLogVo.setLastStatusCheck(dtStatusCheck);
						//userSessionLogVo.setLastStatusCheck(new Date());
						enterpriseSessionsMap.put(userId, userSessionLogVo);
						isSessionActive=true;
					}
				}
			}else{
				if(siteSessionsMap.containsKey(databaseName.toLowerCase())){
					HashMap<Long,UserSessionLogVo> siteMap=siteSessionsMap.get(databaseName.toLowerCase());
					if(siteMap.containsKey(userId)){
						UserSessionLogVo userSessionLogVo = siteMap.get(userId);
						if(userSessionLogVo.getSessionId().equals(sessionId)){
							Date dtStatusCheck=Calendar.getInstance().getTime();
							userSessionLogVo.setLastStatusCheck(dtStatusCheck);
							//userSessionLogVo.setLastStatusCheck(new Date());
							siteMap.put(userId, userSessionLogVo);
							siteSessionsMap.put(databaseName.toLowerCase(), siteMap);
							isSessionActive=true;
						}
					}
				}
			}

			if(isSessionActive==false){
				PromptVo promptVo = new PromptVo();
				promptVo.setMessageProperty("text.youHaveBeenLoggedOut");
				promptVo.setTitleProperty("text.login");
				
				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaName("Logged Out");
				coa.setPromptVo(promptVo);
				coa.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
				coa.setIsDialogueEnding(true);
				
				NavigateVo nv = new NavigateVo();
				nv.setDestination(ApplicationNavigationTypeEnum.DEST_LOGOUT_EISUITE.getNavigationDestination());
				coa.setNavigateVo(nv);
				
				dialogueVo.setCourseOfActionVo(coa);
			}else{
				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaType(CourseOfActionTypeEnum.NOACTION);
				dialogueVo.setCourseOfActionVo(coa);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserSessionManagementService2#getAllUserSessions(java.lang.Long, java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getAllUserSessions(Long userId, String databaseName, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{

			Collection<UserVo> userVos = new ArrayList<UserVo>();
			
			if(super.getRunMode().equals("ENTERPRISE")){
				for(UserSessionLogVo userSessionLogVo : enterpriseSessionsMap.values()){
					UserVo vo = new UserVo();
					vo.setId(userSessionLogVo.getUserVo().getId());
					vo.setLoginName(userSessionLogVo.getUserLoginName());
					vo.setFirstName(userSessionLogVo.getFirstName());
					vo.setLastName(userSessionLogVo.getLastName());
					OrganizationVo homeUnitVo = new OrganizationVo();
					homeUnitVo.setUnitCode(userSessionLogVo.getUnitCode());
					vo.setHomeUnitVo(homeUnitVo);
					userVos.add(vo);
				}
			}else{
				if(siteSessionsMap.containsKey(databaseName.toLowerCase())){
					HashMap<Long,UserSessionLogVo> siteMap=siteSessionsMap.get(databaseName.toLowerCase());
					for(UserSessionLogVo userSessionLogVo : siteMap.values()){
						UserVo vo = new UserVo();
						vo.setId(userSessionLogVo.getUserVo().getId());
						vo.setLoginName(userSessionLogVo.getUserLoginName());
						vo.setFirstName(userSessionLogVo.getFirstName());
						vo.setLastName(userSessionLogVo.getLastName());
						OrganizationVo homeUnitVo = new OrganizationVo();
						homeUnitVo.setUnitCode(userSessionLogVo.getUnitCode());
						vo.setHomeUnitVo(homeUnitVo);
						userVos.add(vo);
					}
				}
			}

			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_GRID");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setRecordset(userVos);
			dialogueVo.setCourseOfActionVo(coa);
			return dialogueVo;
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserSessionManagementService2#disconnectUserSessions(java.util.Collection, java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo disconnectUserSessions(Collection<Integer> userIds, String databaseName, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Collection<Long> uids = IntegerUtility.convertToLongs(userIds);

			// derive logged in user executing the disconnect
			UserSessionVo userSessionVo = (UserSessionVo)context.getBean("userSessionVo");
			User loggedInUser = buildUser(userSessionVo);
			
			if(super.getRunMode().equals("ENTERPRISE")){
				for(Long userId : uids){
					if(enterpriseSessionsMap.containsKey(userId)){
						UserSessionLogVo userSessionLogVo = enterpriseSessionsMap.get(userId);

						// create user session activity log
						UserSessionLogActivity userSessionLogActivity = new UserSessionLogActivityImpl();	 
						userSessionLogActivity.setActionType(UserSessionActionTypeEnum.LOGOUT);
						userSessionLogActivity.setActionCause(UserSessionActionCauseEnum.DISCONNECTED_BY_ADMIN);
						userSessionLogActivity.setCreatedDate(new Date());
						UserSessionLog userSessionLog = new UserSessionLogImpl();
						userSessionLog.setId(userSessionLogVo.getId());
						userSessionLogActivity.setUserSessionLog(userSessionLog);
						
						this.userSessionLogActivityDao.save(userSessionLogActivity);
						this.userSessionLogActivityDao.flushAndEvict(userSessionLogActivity);
						
						// create disconnect audit event log
						createAuditEventRec("LOGIN EVENT","DISCONNECTED BY ANOTHER USER",userId,loggedInUser);
						
						// remove from active sessions
						enterpriseSessionsMap.remove(userId);
					}
				}
			}else{
				if(siteSessionsMap.containsKey(databaseName.toLowerCase())){
					HashMap<Long,UserSessionLogVo> siteMap=siteSessionsMap.get(databaseName.toLowerCase());
					for(Long userId : uids){
						if(siteMap.containsKey(userId)){
							UserSessionLogVo userSessionLogVo = siteMap.get(userId);

							// create user session activity log
							UserSessionLogActivity userSessionLogActivity = new UserSessionLogActivityImpl();	 
							userSessionLogActivity.setActionType(UserSessionActionTypeEnum.LOGOUT);
							userSessionLogActivity.setActionCause(UserSessionActionCauseEnum.DISCONNECTED_BY_ADMIN);
							userSessionLogActivity.setCreatedDate(new Date());
							UserSessionLog userSessionLog = new UserSessionLogImpl();
							userSessionLog.setId(userSessionLogVo.getId());
							userSessionLogActivity.setUserSessionLog(userSessionLog);
							
							this.userSessionLogActivityDao.save(userSessionLogActivity);
							this.userSessionLogActivityDao.flushAndEvict(userSessionLogActivity);
							
							// create disconnect audit event log
							createAuditEventRec("LOGIN EVENT","DISCONNECTED BY ANOTHER USER",userId,loggedInUser);
							
							// remove from active sessions
							siteMap.remove(userId);
							siteSessionsMap.put(databaseName.toLowerCase(), siteMap);
						}
					}
				}
			}

			dialogueVo.setRecordset(userIds);
			MessageVo mvo = new MessageVo();
				mvo.setMessageType(MessageTypeEnum.INFO);
				mvo.setTitleProperty("text.disconnectUsers");
				mvo.setMessageProperty("info.selectedUsersDisconnected");
				
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coa.setCoaName("Disconnnect Users");
			coa.setMessageVo(mvo);
			dialogueVo.setCourseOfActionVo(coa);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}


	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserSessionManagementService2#switchDatabaseSession(java.lang.Long, java.lang.String, gov.nwcg.isuite.core.vo.UserSessionVo, java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo switchDatabaseSession(Long oldUserId, String oldDatabase, UserSessionVo newUserSessionVo, String newDatabase, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public  int closeStaleUserSessions() throws ServiceException {
		int age = 5;
		Collection<UserSessionLogVo> staleSessions = new ArrayList<UserSessionLogVo>();
		
		try{
//			age = Integer.valueOf(this.getGlobalCacheVo().getSystemVo().getParameter(
//					SystemParameterTypeEnum.STALE_SESSION_AGE.name()));
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -age);
			Date time = cal.getTime();
			
			if(this.sysRunMode.equals("ENTERPRISE")){
				for (Map.Entry<Long, UserSessionLogVo> us : enterpriseSessionsMap.entrySet()) {
					System.out.println("LastStatusCheck:"+DateUtil.toDateString(us.getValue().getLastStatusCheck(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS));
					System.out.println("Time:"+DateUtil.toDateString(time, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS));
					if(us.getValue().getLastStatusCheck().before(time)) {
						staleSessions.add(us.getValue());
					}
				}
			}else{
				for (HashMap<Long,UserSessionLogVo> dbMap : siteSessionsMap.values()) {
					for (Map.Entry<Long, UserSessionLogVo> us : dbMap.entrySet()) {
						if(us.getValue().getLastStatusCheck().before(time)) {
							staleSessions.add(us.getValue());
						}
					}
				}
			}
			
			for (UserSessionLogVo uslv : staleSessions) {
				this.endSession(uslv.getUserVo(), uslv.getDatabaseName(), "TIMEOUT", null);
			}
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return 1;
	}

	/**
	 * @param sysRunMode the sysRunMode to set
	 */
	public void setSysRunMode(String sysRunMode) {
		this.sysRunMode = sysRunMode;
	}

	/**
	 * @return the sysRunMode
	 */
	public String getSysRunMode() {
		return sysRunMode;
	}
	
	public DialogueVo clearSession(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			HttpServletRequest hsr = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
									.getRequest();
			String remoteAddress=super.getIpFromRequest(hsr);
			//String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
			//							.getRequest().getRemoteAddr();

			// remove session by ipaddress from map if it exists
			if(super.getRunMode().equals("ENTERPRISE")){
				Set<Long> ids=enterpriseSessionsMap.keySet();
				Long idToRemove=0L;
				
				if(CollectionUtility.hasValue(ids)){
					for(Long id : ids){
						if(enterpriseSessionsMap.containsKey(id)){
							UserSessionLogVo userSessionLogVo = enterpriseSessionsMap.get(id);
							if(StringUtility.hasValue(userSessionLogVo.getIpAddress())){
								if(userSessionLogVo.getIpAddress().equals(remoteAddress)){
									idToRemove=id;
									break;
								}
							}
						}
					}

					if(LongUtility.hasValue(idToRemove)){
						enterpriseSessionsMap.remove(idToRemove);
					}

				}
			}else{
				Set<String> dbNameKeys=siteSessionsMap.keySet();
				if(CollectionUtility.hasValue(dbNameKeys)){
					for(String dbname : dbNameKeys){
						HashMap<Long,UserSessionLogVo> siteMap = siteSessionsMap.get(dbname);
						Set<Long> ids=siteMap.keySet();
						Long idToRemove=0L;
						if(CollectionUtility.hasValue(ids)){
							for(Long id : ids){
								UserSessionLogVo userSessionLogVo = siteMap.get(id);
								if(userSessionLogVo.getIpAddress().equals(remoteAddress)){
									idToRemove=id;
									break;
								}
							}
						}
						
						if(LongUtility.hasValue(idToRemove)){
							siteMap.remove(idToRemove);
							siteSessionsMap.put(dbname, siteMap);
						}
					}
				}
			}
			
		}catch(Exception e){
			// smother the exception      super.dialogueException(dialogueVo, e);
			System.out.println(e.getMessage());
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
		coa.setCoaName("CLEAR_SESSION");
		dialogueVo.setCourseOfActionVo(coa);
		
		return dialogueVo;
		
	}
	
}
