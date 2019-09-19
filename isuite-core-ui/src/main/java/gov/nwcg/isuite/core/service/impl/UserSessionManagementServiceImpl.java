package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.DataAuditConfig;
import gov.nwcg.isuite.core.domain.DataAuditTracking;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserSessionLog;
import gov.nwcg.isuite.core.domain.impl.DataAuditTrackingImpl;
import gov.nwcg.isuite.core.domain.impl.UserSessionLogImpl;
import gov.nwcg.isuite.core.filter.impl.DisconnectUsersFilterImpl;
import gov.nwcg.isuite.core.persistence.DataAuditConfigDao;
import gov.nwcg.isuite.core.persistence.DataAuditTrackingDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.UserSessionLogDao;
import gov.nwcg.isuite.core.service.UserSessionManagementService;
import gov.nwcg.isuite.core.vo.UserSessionLogVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.NavigateVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.core.service.JMSService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.ApplicationNavigationTypeEnum;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.DataAuditEvent;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionCauseEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionTypeEnum;
import gov.nwcg.isuite.framework.util.IntegerUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserSessionManagementServiceImpl extends BaseService implements UserSessionManagementService {

	private static final Logger LOG = Logger.getLogger(UserSessionManagementServiceImpl.class);

	private static Map<Long, UserSessionLogVo> sessionsMap = new HashMap<Long, UserSessionLogVo>();
	private static Map<String, UserSessionLogVo> dbSessionsMap = new HashMap<String, UserSessionLogVo>();

	
	/**
	 * use this map to keep a reference to the Spring SecurityContext so that
	 * admins can close the session. Replace this implementation with one that
	 * can get the Context from Spring, if that turns out to be possible.
	 */
	private static Map<String, SecurityContext> securityMap = new HashMap<String, SecurityContext>();

	public UserSessionManagementServiceImpl() {
	}

	public void closeDisabledSessions(Collection<Long> ids, Long adminUserId) throws ServiceException {
		try {
			UserDao userDao = (UserDao) super.context.getBean("userDao");
			UserVo adminUserVo;
			adminUserVo = userDao.getUserById(adminUserId);

			Collection<UserSessionLogVo> disabledSessions = new HashSet<UserSessionLogVo>();
			for (Long id : ids) {
				UserSessionLogVo uslv = sessionsMap.get(id);
				if (uslv != null) {
					disabledSessions.add(uslv);
				}
			}

			for (UserSessionLogVo uslv : disabledSessions) {
				updateUserSessionLog(uslv.getUserVo().getId(), UserSessionActionTypeEnum.LOGOUT,
						UserSessionActionCauseEnum.ACCOUNT_LOCKED_OR_DISABLED, adminUserVo, false);
				removeUserSession(uslv.getUserVo().getId(), uslv.getSessionId());
			}

		} catch (PersistenceException e) {
			super.handleException(e);
		} catch (Exception e1) {
			super.handleException(e1);
		}
	}

	public void closeStaleUserSessions() throws ServiceException {
		Collection<UserSessionLogVo> staleSessions = getStaleUserSessions();
		for (UserSessionLogVo uslv : staleSessions) {
			try {
				updateUserSessionLog(uslv.getUserVo().getId(), UserSessionActionTypeEnum.LOGOUT,
						UserSessionActionCauseEnum.DISCONNECTED, null, true);
				removeUserSession(uslv.getUserVo().getId(), uslv.getSessionId());
			} catch (Exception e) {
				super.handleException(e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserSessionManagementService#closeUserSessions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	@SuppressWarnings("unchecked")
	public DialogueVo closeUserSessions(DialogueVo dialogueVo) throws ServiceException {
		try {
			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}

			// Convert Integers to Longs (AS3 passes in Integers by default).
			Collection<Long> userIds = IntegerUtility.convertToLongs((Collection<Integer>) dialogueVo.getRecordset());

			for(Long id : userIds) {
				String sessionId = sessionsMap.get(id).getSessionId();
				if(sessionId != null)
					updateUserSessionLog(id, UserSessionActionTypeEnum.LOGOUT,
							UserSessionActionCauseEnum.USER_INITIATED, getLoggedInUserVo(), false);
				createAuditEvent("LOGIN EVENT","DISCONNECTED BY ANOTHER USER",id,getLoggedInUser());
				removeUserSession(id, sessionId);
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
			return dialogueVo;
		} catch (Exception e) {
			super.handleException(e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserSessionManagementService#getAllUserSessions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getAllUserSessions(DialogueVo dialogueVo, Long userId) throws ServiceException {
		if(dialogueVo == null) {dialogueVo = new DialogueVo();}
		
		try {
			Set<UserSessionLogVo> nonPrivilegedUsers = new HashSet<UserSessionLogVo>();
			
			// Exclude admin users
			/* djp - 5/24/2012 users would like to see everyone 
			for(UserSessionLogVo vo : sessionsMap.values()) {
				for(SystemRoleVo svo : vo.getUserVo().getUserRoleVos()) {
					if(!svo.getRoleName().equals(RoleEnum.ROLE_ACCOUNT_MANAGER.name())) {
						nonPrivilegedUsers.add(vo);
					}
				}
			}
			*/

			Collection<UserVo> userVos = new ArrayList<UserVo>();
			for(UserSessionLogVo uslgVo : this.sessionsMap.values()){
				//if(uslgVo.getUserVo().getId().compareTo(userId)!=0){
					userVos.add(uslgVo.getUserVo());
				//}
			}
			
			// Grid filters
			// User Name, First Name, Last Name, Unit ID
			/*
			Set<UserVo> filteredUsers = new HashSet<UserVo>();
			filteredUsers = this.applyGridFilters(nonPrivilegedUsers, disconnectUsersFilter);
			*/

			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_GRID");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			// dialogueVo.setRecordset(nonPrivilegedUsers);
			//dialogueVo.setRecordset(filteredUsers);
			dialogueVo.setRecordset(userVos);
			dialogueVo.setCourseOfActionVo(coa);
			return dialogueVo;
		} catch (Exception e) {
			super.handleException(e);
		}
		return null;
	}

	@Override
	public boolean isUserLoggedIn(Long userId) {
		return sessionsMap.containsKey(userId);
	}

	@Override
	public boolean isUserLoggedIn(Long userId, String sessionId) {
		if(sessionsMap.get(userId) != null) {
			return sessionsMap.get(userId).getSessionId().equals(sessionId);
		} else {
			return false;
		}
	}

	public String getUserSessionId(Long userId) {
		UserSessionLogVo uslv = sessionsMap.get(userId);
		if(uslv != null)
			return uslv.getSessionId();
		return null;
	}

	public void sendMessageToClient(String sessionId) throws ServiceException {
		try {
			Thread.sleep(10000);
			JMSService.sendSessionMessageToClient(sessionId);
		} catch (ServiceException e) {
			super.handleException(e);
		} catch (InterruptedException e) {
			super.handleException(e);
		}
	}

	public Boolean manageUserSession(User user, UserSessionActionTypeEnum actionType,
			UserSessionActionCauseEnum actionCause, User adminUser) throws ServiceException {
		Boolean addedSession = false;

		try {
			switch(actionType) {
				case INITIATE_LOGIN:
					// This will add the user to the sessionMap to keep two sessions from logging in at the same time
					// but it does not persist the UserSessionLogVo because the login process is not complete
					UserSessionLogVo usilv;
					usilv = createNewUserSessionLog(UserVo.getInstance(user, true), actionType, actionCause, false);
					addedSession = addUserSession(usilv, false);
					if(addedSession)
						addSecurityContext(usilv.getSessionId(), SecurityContextHolder.getContext());
					break;
				case LOGIN:
					// This will replace the INITIATE_LOGIN UserSessionLogVo in the sessionMap for the user
					// with a persisted the UserSessionLogVo now that the login process is complete
					UserSessionLogVo uslv;
					uslv = createNewUserSessionLog(UserVo.getInstance(user, true), actionType, actionCause, true);
					addedSession = addUserSession(uslv, true);
					if(addedSession){
						addSecurityContext(uslv.getSessionId(), SecurityContextHolder.getContext());
						createAuditEvent("LOGIN EVENT","SUCCESSFUL LOGIN",user.getId(),user);
					}
					break;
				case LOGOUT:
					if(!isUserLoggedIn(user.getId())) {
						break;
					}
					updateUserSessionLog(user.getId(), actionType, actionCause, null, false);
					createAuditEvent("LOGIN EVENT","SUCCESSFUL LOGOUT",user.getId(),user);
					removeUserSession(user.getId());
					break;
				case INVALID_LOGIN:
					createNewUserSessionLog(UserVo.getInstance(user, true), actionType, actionCause, true);
					createAuditEvent("LOGIN EVENT","FAILED LOGIN",user.getId(),user);
					break;
				case LOGIN_OVERRIDE:
					if (!isUserLoggedIn(user.getId())) {
						// TODO: throw error
					}
					updateUserSessionLog(user.getId(), actionType, actionCause, UserVo.getInstance(adminUser, true), false);
					break;
				default:
					// TODO: what to do here?
					break;
			}
		} catch (Exception e) {
			super.handleException(e);
		}

		return addedSession;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserSessionManagementService#userSessionPing(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, java.lang.Integer)
	 */
	public DialogueVo userSessionPing(DialogueVo dialogueVo, Integer userId) throws ServiceException {
		try {
			Long uId = IntegerUtility.convertToLong(userId);
			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}
			// UserSessionVo usvo =
			// (UserSessionVo)this.context.getBean("userSessionVo");
			// Long userId = usvo.getUserId();

			String sid = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
					.getSession().getId();

			if(isUserLoggedIn(uId, sid)) {
				UserSessionLogVo usl = getUserSessionLogVo(uId);
				updateUserSession(usl);
			} else {
				dialogueVo = getLoggedOutVo(dialogueVo);
				return dialogueVo;
			}
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaType(CourseOfActionTypeEnum.NOACTION);
			dialogueVo.setCourseOfActionVo(coa);
			return dialogueVo;
		} catch (Exception e) {
			super.handleException(e);
		}
		return null;
	}

	/**
	 * Add a userSessionVo to the sessionsMap with the current time, and return true.
	 * Return false if the user already has a value in the map
	 * 
	 * @param uslv
	 */
	private Boolean addUserSession(UserSessionLogVo uslv, Boolean update) {
		UserSessionLogVo orig = sessionsMap.get(uslv.getUserVo().getId());
		if(orig == null || (update && orig.getSessionId() == uslv.getSessionId()) ) {
			uslv.setLastStatusCheck(new Date());
			sessionsMap.put(uslv.getUserVo().getId(), uslv);
			return true;
		} 
		return false;
	}
	
	/**
	 * Update a userSessionVo to the sessionsMap with the current time
	 * 
	 * @param uslv
	 */
	private void updateUserSession(UserSessionLogVo uslv) {
		uslv.setLastStatusCheck(new Date());
		sessionsMap.put(uslv.getUserVo().getId(), uslv);
	}

	/**
	 * return the UserSessionVo for the given userId
	 * 
	 * @param userId
	 * @return
	 */
	private UserSessionLogVo getUserSessionLogVo(Long userId) {
		return sessionsMap.get(userId);
	}

	/**
	 * Return all of the userSessionLogVos that have a last checkin time of more
	 * than x minutes ago
	 * 
	 * @return Collection<UserSessionLogVo>
	 * @throws ServiceException
	 */
	private Collection<UserSessionLogVo> getStaleUserSessions() throws ServiceException {
		int age = 5;
		try {
			age = Integer.valueOf(this.getGlobalCacheVo().getSystemVo().getParameter(
					SystemParameterTypeEnum.STALE_SESSION_AGE.name()));
		} catch (Exception e) {
			super.handleException(e);
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -age);
		Date time = cal.getTime();

		Collection<UserSessionLogVo> stale = new HashSet<UserSessionLogVo>();

		for (Map.Entry<Long, UserSessionLogVo> us : sessionsMap.entrySet()) {
			if(us.getValue().getLastStatusCheck().before(time)) {
				stale.add(us.getValue());
			}
		}

		return stale;
	}

	/**
	 * Remove the user's session from the sessionsMap during logout
	 * 
	 * @param userId
	 */
	private void removeUserSession(Long userId) {
		sessionsMap.remove(userId);
	}

	/**
	 * Remove another user's session from the sessionMap and set the session's
	 * Spring's SecurityContext authentication to null
	 * 
	 * @param userId
	 * @param sessionId
	 * @throws ServiceException
	 */
	private void removeUserSession(Long userId, String sessionId) throws ServiceException {
		sessionsMap.remove(userId);
		removeSecurityContext(sessionId);
	}

	/**
	 * Create a new userSessionLogVo and save it to the database
	 * 
	 * @param userVo
	 * @param actionType
	 * @param actionCause
	 * @return
	 * @throws ServiceException
	 */
	private UserSessionLogVo createNewUserSessionLog(UserVo userVo, 
													 UserSessionActionTypeEnum actionType,
													 UserSessionActionCauseEnum actionCause,
													 Boolean save) throws ServiceException {
		UserSessionLogVo uslv = new UserSessionLogVo();
		try {
			String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest().getRemoteAddr();
			String sessionId = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest().getSession().getId();
			Date lastStatusCheck = Calendar.getInstance().getTime();

			uslv.setIpAddress(remoteAddress);
			uslv.setSessionId(sessionId);
			uslv.setLastStatusCheck(lastStatusCheck);
			uslv.setUserVo(userVo);

			if(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
					.getUserPrincipal() != null) {
				uslv.setCreatedBy(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
						.getRequest().getUserPrincipal().getName());
			}
			uslv.setCreatedDate(new Date());

			uslv.createUserSessionLogActivityVo(actionType, actionCause, null);

			if(save) //Is the login process complete (ie. has the Authentication Manager finished its work)?
				uslv.setId(saveUserSessionLog(uslv));
			/*
			 * The userSessionLogActivity is not needed for the sessionsMap, so
			 * it is being removed so that we don't have to find out its id for
			 * the vo and it doesn't get saved the the db again.
			 */
			//uslv.getUslavs().remove(0);
		} catch (Exception e) {
			super.handleException(e);
		}
		return uslv;
	}

	/**
	 * Create a new userSessionLogActivityVo for the given userId and save it to
	 * the database
	 * 
	 * @param userId
	 * @param actionType
	 * @param actionCause
	 * @param adminUser
	 * @param scheduledTask
	 *            - initiated by a scheduled task (true) or user initiation
	 *            (false)
	 * @return
	 * @throws ServiceException
	 */
	private UserSessionLogVo updateUserSessionLog(Long userId, UserSessionActionTypeEnum actionType,
			UserSessionActionCauseEnum actionCause, UserVo adminUser, boolean scheduledTask) throws ServiceException {
		UserSessionLogVo uslv = new UserSessionLogVo();

		if(isUserLoggedIn(userId)) {
			uslv = getUserSessionLogVo(userId);
			if(scheduledTask) {
				uslv.createUserSessionLogActivityVo(actionType, actionCause, adminUser, "ScheduledTask");
			} else {
				uslv.createUserSessionLogActivityVo(actionType, actionCause, adminUser);
			}

			saveUserSessionLog(uslv);
			/*
			 * The userSessionLogActivity is not needed for the sessionsMap, so
			 * it is being removed so that we don't have to find out its id for
			 * the vo and it doesn't get saved the the db again.
			 */
			//if(uslv.getUslavs().size() > 0) {
			//	uslv.getUslavs().remove(0);
			//}
		} 

		return uslv;
	}

	/**
	 * Save the userSessionLogVo to the database
	 * 
	 * @param uslv
	 * @return
	 * @throws ServiceException
	 */
	private Long saveUserSessionLog(UserSessionLogVo uslv) throws ServiceException {
		UserSessionLogDao uslDao = (UserSessionLogDao) super.context.getBean("userSessionLogDao");

		// convert Vo to domain object
		UserSessionLog usl = new UserSessionLogImpl();
		try {
			usl = uslv.toEntity(null, uslv, true);
			uslDao.save(usl);
		} catch (Exception e) {
			super.handleException(e);
		}
		return usl.getId();
	}

	/**
	 * Add the Session's SecurityContext to the static map to keep a reference
	 * to it.
	 * 
	 * @param sessionId
	 * @param sc
	 */
	private void addSecurityContext(String sessionId, SecurityContext sc) {
		securityMap.put(sessionId, sc);
	}

	/**
	 * Set the SecurityContext.authentication to null to kill the session on the
	 * server side, and remove the session reference from the securityMap.
	 * 
	 * @param sessionId
	 * @throws ServiceException
	 */
	private void removeSecurityContext(String sessionId) throws ServiceException {
		try {
			if(securityMap.get(sessionId) != null) {
				securityMap.get(sessionId).setAuthentication(null);
			}
			securityMap.remove(sessionId);

			JMSService.sendSessionMessageToClient(sessionId);
		} catch (Exception e) {
			super.handleException(e);
		}
	}

	/**
	 * Return the logged in userVo, created for closeUserSessions
	 * updateUserSessionLog AdminUserVo
	 * 
	 * @return
	 * @throws ServiceException
	 */
	private UserVo getLoggedInUserVo() throws ServiceException {
		UserDao userDao = (UserDao) context.getBean("userDao");
		User user = null;
		UserVo usv = null;
		try {
			user = userDao.getByLoginName(((UserSessionVo) super.context.getBean("userSessionVo")).getUserLoginName());
			usv = UserVo.getInstance(user, true);
		} catch (Exception e) {
			super.handleException(e);
		}

		return usv;
	}

	private DialogueVo getLoggedOutVo(DialogueVo dialogueVo) {
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
		return dialogueVo;
	}

	private User getLoggedInUser() throws ServiceException {
		UserDao userDao = (UserDao) context.getBean("userDao");
		User user = null;
		try {
			user = userDao.getByLoginName(((UserSessionVo) super.context.getBean("userSessionVo")).getUserLoginName());
		} catch (Exception e) {
			super.handleException(e);
		}

		return user;
	}
	
	/**
	 * Grid Filters. User Name, First Name, Last Name, Unit ID
	 * 
	 * @param nonPrivilegedUsers
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	private Set<UserVo> applyGridFilters(Collection<UserSessionLogVo> nonPrivilegedUsers,
			DisconnectUsersFilterImpl filter) throws ServiceException {
		Set<UserVo> filteredUsers = new HashSet<UserVo>();
		try {
			for(UserSessionLogVo vo : nonPrivilegedUsers) {
				filteredUsers.add(vo.getUserVo());
			}
			for(UserSessionLogVo uslvo : nonPrivilegedUsers) {
				if(filter != null) {
					if(filter.getUserName() != null) {
						if(!uslvo.getUserVo().getLoginName().startsWith(filter.getUserName())) {
							filteredUsers.remove(uslvo.getUserVo());
						}
					}
					if(filter.getFirstName() != null) {
						if(!uslvo.getUserVo().getFirstName().toUpperCase().startsWith(
								filter.getFirstName().toUpperCase())) {
							filteredUsers.remove(uslvo.getUserVo());
						}
					}
					if(filter.getLastName() != null) {
						if(!uslvo.getUserVo().getLastName().toUpperCase().startsWith(
								filter.getLastName().toUpperCase())) {
							filteredUsers.remove(uslvo.getUserVo());
						}
					}
					if(filter.getUnitCode() != null) {
						if(!uslvo.getUserVo().getHomeUnitVo().getUnitCode().toUpperCase().startsWith(
								filter.getUnitCode().toUpperCase())) {
							filteredUsers.remove(uslvo.getUserVo());
						}
					}
				}
			}
		} catch (Exception e) {
			super.handleException(e);
		}
		return filteredUsers;
	}

	private void createAuditEvent(String oldValue, String newValue, Long primaryKey, User user){
		DataAuditConfigDao dacDao = (DataAuditConfigDao)context.getBean("dataAuditConfigDao");
		DataAuditConfig dac = null;
		try{
			dac=dacDao.getByEventName("ISW_USER", DataAuditEvent.ACCOUNT_LOGIN.name());
		}catch(Exception e){
			//smother
		}
		try{
			if(null != dac && dac.getEnabled()==StringBooleanEnum.Y ){
				// log the event
				DataAuditTracking dataAuditTracking = new DataAuditTrackingImpl();
				dataAuditTracking.setDataAuditConfig(dac);
				dataAuditTracking.setChangeDate(Calendar.getInstance().getTime());
				dataAuditTracking.setOldValue(oldValue);
				dataAuditTracking.setNewValue(newValue);
				dataAuditTracking.setTablePrimaryKeyId(primaryKey);
				dataAuditTracking.setUserName(user.getLoginName());
				dataAuditTracking.setAuditField1(user.getLoginName());
				dataAuditTracking.setAuditField2(user.getLastName());
				dataAuditTracking.setAuditField3(user.getFirstName());
				dataAuditTracking.setAuditField4(user.getHomeUnit().getUnitCode());
				dataAuditTracking.setAuditField5(user.getPrimaryDispatchCenter().getUnitCode());
				DataAuditTrackingDao datDao = (DataAuditTrackingDao)context.getBean("dataAuditTrackingDao");
				datDao.save(dataAuditTracking);
			}
		}catch(Exception e){
			//smother
		}
	}
}
