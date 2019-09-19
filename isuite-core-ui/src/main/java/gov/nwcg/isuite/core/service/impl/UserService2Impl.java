package gov.nwcg.isuite.core.service.impl;

import java.security.Security;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.nwcg.www.webservices.security.getUsers.GetUsers;
import org.nwcg.www.webservices.security.getUsers.GetUsersRequest;
import org.nwcg.www.webservices.security.getUsers.GetUsersRequestFilter;
import org.nwcg.www.webservices.security.getUsers.GetUsersResponse;
import org.nwcg.www.webservices.security.getUsers.GetUsersServiceLocator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ibm.xml.crypto.util.Base64;

import gov.nwcg.isuite.core.domain.DataAuditConfig;
import gov.nwcg.isuite.core.domain.DataAuditTracking;
import gov.nwcg.isuite.core.domain.PasswordHistory;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserImportFailure;
import gov.nwcg.isuite.core.domain.impl.DataAuditTrackingImpl;
import gov.nwcg.isuite.core.domain.impl.PasswordHistoryImpl;
import gov.nwcg.isuite.core.domain.impl.SystemParameterImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.domain.impl.UserImportFailureImpl;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.UserImportFailureFilter;
import gov.nwcg.isuite.core.filter.impl.NapSearchFilterImpl;
import gov.nwcg.isuite.core.persistence.DataAuditConfigDao;
import gov.nwcg.isuite.core.persistence.DataAuditTrackingDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.PasswordHistoryDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.SystemRoleDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.UserImportFailureDao;
import gov.nwcg.isuite.core.rules.ChangePasswordRulesHandler;
import gov.nwcg.isuite.core.rules.SaveSiteAdminRulesHandler;
import gov.nwcg.isuite.core.rules.UserDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.UserDisableRulesHandler;
import gov.nwcg.isuite.core.rules.UserImportAccountsRulesHandler;
import gov.nwcg.isuite.core.rules.UserImportResolveFailuresRulesHandler;
import gov.nwcg.isuite.core.rules.UserSaveRulesHandler;
import gov.nwcg.isuite.core.service.DatabaseMgmtService;
import gov.nwcg.isuite.core.service.UserService2;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.core.vo.NapUserVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserImportFailureVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorType;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.DataAuditEvent;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.xml.XmlHandler;
import gov.nwcg.isuite.framework.xml.XmlSchemaTypeEnum;
import gov.nwcg.isuite.xml.SystemRoleType;
import gov.nwcg.isuite.xml.UserTransfer;

@SuppressWarnings("unchecked")
public class UserService2Impl extends BaseService implements UserService2 {
	protected UserDao userDao;
	protected Boolean isCreateNewAccountManager=false;
	
	public UserService2Impl(){
		super();
	}

	public void initialization(){
		userDao = (UserDao)super.context.getBean("userDao");
	}
	
	@Override
	public DialogueVo deleteUser(Long userId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) {
			dialogueVo = new DialogueVo();
		}
		
		try {
			UserDeleteRulesHandler rulesHandler = new UserDeleteRulesHandler(context);

			User userEntity = null;
			userEntity = userDao.getById(userId, UserImpl.class);
			UserVo userVo = UserVo.getInstance(userEntity, true);

			if(rulesHandler.execute(userEntity, userVo, dialogueVo) == UserDeleteRulesHandler._OK) {
				userDao.delete(userEntity);

				// execute follow up actions
				rulesHandler.executeProcessedActions(userEntity, userVo, dialogueVo);

				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_USER_COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(
						new MessageVo(
								"text.userAccounts", 
								"text.affirmDelete", 
								new String[]{userVo.getLoginName()}, 
								MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(userVo.getId());
			}
		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	@Override
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) {
			dialogueVo = new DialogueVo();
		}
		
		try {
			User userEntity = userDao.getById(id, UserImpl.class);
			UserVo userVo = null;
			
			if(null != userEntity) {
				userVo = UserVo.getInstance(userEntity, true);
			}
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_BY_ID_USER_ACCOUNT");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setResultObject(userVo);
			
		} catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	@Override
	public DialogueVo getGrid(UserFilter userFilter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) {
			dialogueVo = new DialogueVo();
		}
		
		Collection<UserGridVo> userGridVos = new ArrayList<UserGridVo>();
		
		try {
			userGridVos = userDao.getGrid(userFilter);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_GRID_USER_ACCOUNTS");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(userGridVos);
			
		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	@Override
	public DialogueVo save(UserVo userVo, String dbName, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) {
			dialogueVo = new DialogueVo();
		}
		
		try{
			UserSaveRulesHandler rulesHandler = new UserSaveRulesHandler(context);
			
			Boolean pwdChanged = false;
			
			User userEntity = null;
			if(LongUtility.hasValue(userVo.getId())){
				userEntity = userDao.getById(userVo.getId(), UserImpl.class);
			}

			if(rulesHandler.execute(userEntity, userVo, dialogueVo)==UserSaveRulesHandler._OK){

				if(null != userEntity){
					FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);

					if(!userVo.getPassword().equals(UserVo.DISPLAY_PASSWORD)){
						pwdChanged=true;
					}
				}else
					pwdChanged=true;
					
				userEntity = UserVo.toEntity(userEntity, userVo, true);
				userEntity.setResetPassword(pwdChanged);
				
				userDao.save(userEntity);

				// generate password history if pwdChanged
				if(pwdChanged==true){
					PasswordHistoryDao phDao=(PasswordHistoryDao)context.getBean("passwordHistoryDao");
					PasswordHistory phEntity = new PasswordHistoryImpl();
					phEntity.setUser(userEntity);
					phEntity.setUserPassword(userEntity.getPassword());
					phEntity.setUserPasswordCreatedDate(userEntity.getPasswordCreatedDate());
					phDao.save(phEntity);
					
					if(super.getUserVo().getId().compareTo(userEntity.getId())!=0){
						// update the user record  and force password change
						User userEntity2 = userDao.getById(userEntity.getId(), UserImpl.class);
						if(null != userEntity2){
							userEntity2.setResetPassword(true);
							userDao.save(userEntity2);
							userDao.flushAndEvict(userEntity2);
						}
					}
					
				}

				// if saving new user , add user to all incidents and groups(site)
				if(super.getRunMode().equals("SITE")){
					if(!UserVo.isPrivilegedUser(userVo)){
						if(!LongUtility.hasValue(userVo.getId())){
							IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
							incidentDao.executeInsertIncidentNewUser(userEntity.getId());
							
							// add user to group
							IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
							incidentGroupDao.executeInsertIncidentGroupNewUser(userEntity.getId());
						}
					}
				}
			
				userVo.setId(userEntity.getId());
				userDao.flushAndEvict(userEntity);

				// execute follow up actions
				rulesHandler.executeProcessedActions(userEntity, userVo, dbName, dialogueVo);


				userDao.flushAndEvict(userEntity);
				userEntity = userDao.getById(userEntity.getId(), UserImpl.class);
				userVo=UserVo.getInstance(userEntity, true);

				// get the gridVo
				UserGridVo gridVo = UserGridVo.getInstance(userEntity, true);

				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_USER_ACCOUNT");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.userAccounts", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(userVo);
				dialogueVo.setResultObjectAlternate(gridVo);
					
				rulesHandler.addAdditionalMessages(userVo, dialogueVo);
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	@Override
	public DialogueVo disableUsers(Collection<UserGridVo> userGridVos, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(null == dialogueVo) {
				dialogueVo = new DialogueVo();
			}
			
			UserDisableRulesHandler rulesHandler = new UserDisableRulesHandler(context);
			
			Collection<Long> userIds = new ArrayList<Long>();
			for(UserGridVo userGridVo : userGridVos) {
				userIds.add(userGridVo.getId());
			}
			
			Collection<User> userEntities = userDao.getUsersByIds(userIds);
			Collection<UserVo> userVos = UserVo.getInstances(userEntities, true);
			
			if(rulesHandler.execute(userEntities, userVos, dialogueVo)==UserDisableRulesHandler._OK){
				userDao.disableUsers(userIds);
				
//				userVo.setId(userEntity.getId());
				for(UserVo uvo : userVos) {
					for(User user : userEntities) {
						uvo.setId(user.getId());
					}
				}
//				userDao.flushAndEvict(userEntity);
				for(User user : userEntities) {
					userDao.flushAndEvict(user);
				}
				
				// execute follow up actions
				rulesHandler.executeProcessedActions(userEntities, userVos, dialogueVo);

//				userDao.flushAndEvict(userEntity);
				for(User user : userEntities) {
					userDao.flushAndEvict(user);
				}
//				userVo=UserVo.getInstance(userDao.getById(userEntity.getId(), UserImpl.class), true);
				userVos = UserVo.getInstances(userEntities, true);

				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.userAccounts", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setRecordset(userVos);
			}
			
		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	@Override
	public DialogueVo changePassword(
			Long userId,
			String currentPassword,
			String newPassword,
			String confirmPassword,
			DialogueVo dialogueVo) throws ServiceException {
		try {

			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}

			ChangePasswordRulesHandler rulesHandler = new ChangePasswordRulesHandler(context);

			User userEntity = null;
			userEntity = userDao.getById(userId, UserImpl.class);
			UserVo userVo = UserVo.getInstance(userEntity, true);

			if(rulesHandler.execute(
					userEntity, 
					userVo,
					currentPassword,
					newPassword, 
					confirmPassword,
					dialogueVo) == ChangePasswordRulesHandler._OK) {
				
				FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);
				String newEncPwd = new String(enc.encrypt(newPassword.getBytes()));
				userEntity.setPassword(newEncPwd);

				String expireTime=super.getSystemParamValue(SystemParameterTypeEnum.PWD_EXPIRE_TIME);
				if(null==expireTime || expireTime.isEmpty())
					expireTime="60";
				
				Date now = Calendar.getInstance().getTime();
				userEntity.setAccountExpirationDate(DateUtil.addDaysToDate(now,Integer.parseInt(expireTime)));
				userEntity.setPasswordCreatedDate(Calendar.getInstance().getTime());
				userEntity.setLastLoginDate(Calendar.getInstance().getTime());
				userEntity.setResetPassword(false);
				
				userDao.save(userEntity);

				PasswordHistoryDao phDao=(PasswordHistoryDao)context.getBean("passwordHistoryDao");
				PasswordHistory phEntity = new PasswordHistoryImpl();
				phEntity.setUser(userEntity);
				phEntity.setUserPassword(userEntity.getPassword());
				phEntity.setUserPasswordCreatedDate(userEntity.getPasswordCreatedDate());
				phDao.save(phEntity);
				
				// execute follow up actions
				rulesHandler.executeProcessedActions(
						userEntity, 
						userVo, 
						currentPassword,
						newPassword, 
						confirmPassword,
						dialogueVo);

				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("CHANGE_PASSWORD_COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(
						new MessageVo(
								"text.changePassword", 
								"text.affirmChangePassword", 
								null, 
								MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(userVo.getId());
				dialogueVo.setResultObjectAlternate(DateUtil.toDateString(userEntity.getAccountExpirationDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
			}
		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
		
	}
	
	@Override
	public DialogueVo importUserAccounts(byte[] xmlByteArray, String defaultPassword, String confirmDefaultPassword, DialogueVo dialogueVo) {
		try {
			if(null == dialogueVo) {
				dialogueVo = new DialogueVo();
			}
			
		    //initial the save count
		    dialogueVo.setResultObjectAlternate4(0);
						
			UserImportFailureDao uifDao = (UserImportFailureDao)super.context.getBean("userImportFailureDao");
			UserImportAccountsRulesHandler rulesHandler = new UserImportAccountsRulesHandler(this.context);
			if(rulesHandler.execute(xmlByteArray, defaultPassword, confirmDefaultPassword, dialogueVo, uifDao, this.userDao) == UserImportAccountsRulesHandler._OK) {
				DialogueVo dialogueVo2 = this.getLastImportFailures(null, dialogueVo);
				Collection<UserImportFailureVo> vos = (Collection<UserImportFailureVo>)dialogueVo2.getResultObjectAlternate();
				
				if(CollectionUtility.hasValue(vos)){
					return dialogueVo2;
				}

				// show import success message
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaName("USER_IMPORT_COMPLETE");
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(new MessageVo("text.userAccounts", "info.generic" , new String[]{"The import was successful"}, MessageTypeEnum.INFO));
				courseOfActionVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(courseOfActionVo);
								
				return dialogueVo;
			}
			
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);		
		}

		return dialogueVo;
	}
	
	@Override
	public DialogueVo getLastImportFailures(UserImportFailureFilter filter, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(null == dialogueVo) {
				dialogueVo = new DialogueVo();
			}

			UserImportFailureDao uifDao = (UserImportFailureDao)super.context.getBean("userImportFailureDao");
			SystemRoleDao systemRoleDao = (SystemRoleDao)super.context.getBean("systemRoleDao");

			Collection<UserImportFailureVo> vos = uifDao.getGrid(filter);
			Collection<SystemRole> systemRoles = systemRoleDao.getAllRoles();
			
			for(UserImportFailureVo vo : vos){
				// reset password, don't send back
				vo.setPassword("");

				if( (null != vo.getHomeUnitCode()) && (!vo.getHomeUnitCode().isEmpty()) )
					vo.setHomeUnitVo(OrganizationVo.getByUnitCode(vo.getHomeUnitCode(), super.getGlobalCacheVo().getOrganizationVos()));
				//if( (null != vo.getPdcUnitCode()) && (!vo.getPdcUnitCode().isEmpty()) )
				//	vo.setPrimaryDispatchCenterVo(OrganizationVo.getDispatchCenterByUnitCode(vo.getPdcUnitCode(), super.getGlobalCacheVo().getOrganizationVos()));
				
				// 6/10/2019 rebuild roles, they are not getting populated correctly
				// the UserImportFailureDao.getGrid is populating roles as isw_user_failure_role's
				// we want system_roles
				if( (null != vo.getRoleVos()) && (!vo.getRoleVos().isEmpty()) ) {
					Collection<SystemRoleVo> newRoleVos = new ArrayList<SystemRoleVo>();
					for(SystemRoleVo v: vo.getRoleVos()){
						for(SystemRole sr : systemRoles) {
							if(v.getId().compareTo(sr.getId()) == 0){
								SystemRoleVo newRoleVo = new SystemRoleVo();
								newRoleVo.setId(sr.getId());
								newRoleVo.setDisplayName(sr.getDisplayName());
								newRoleVo.setRoleName(sr.getRoleName());
								newRoleVo.setPrivilegedRole(sr.getPrivilegedRole());
								
								newRoleVos.add(newRoleVo);
							}
						}
					}
					vo.setRoleVos(newRoleVos);
					vo.setIsPrivilegedUser(this.determinePrivilegedStatus(vo.getRoleVos()));
				}
			}
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_LAST_IMPORT_FAILURES");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setResultObjectAlternate(vos);
			return dialogueVo;

		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	@Override
	public DialogueVo resolveImportFailure(Collection<UserVo> validUserVos, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(null == dialogueVo) {
				dialogueVo = new DialogueVo();
			}

			UserImportFailureDao uifDao = (UserImportFailureDao)super.context.getBean("userImportFailureDao");

			UserImportResolveFailuresRulesHandler rulesHandler = new UserImportResolveFailuresRulesHandler(context);
			Collection<Long> resolvedFailureIds = new ArrayList<Long>();
			Collection<UserGridVo> resolvedUserImportFailures = new ArrayList<UserGridVo>();
			Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
			
			if(!CollectionUtility.hasValue(validUserVos)){
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("NOTHING_TO_IMPORT");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
				coaVo.getErrorObjectVos().add(new ErrorObject("info.generic",new String[]{"There are no users included to import."}));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				return dialogueVo;
			}
			boolean saveOccured = false;
			int index = 0;
			for(UserVo validUserVo : validUserVos) {
				Long uifId = validUserVo.getId();
				//Reset COAVos for next iteration of the loop.
				dialogueVo.setProcessedCourseOfActionVos(new ArrayList<CourseOfActionVo>());
				
				UserImportFailure uifEntity = uifDao.getById(uifId, UserImportFailureImpl.class);
				
				if(rulesHandler.execute(dialogueVo, validUserVo, uifDao, userDao, uifEntity) == UserImportResolveFailuresRulesHandler._OK) {
					
					if(null==uifEntity)
						super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserImportFailure["+uifId+"]");

					/*
					if( (null == validUserVo.getPrimaryDispatchCenterVo()) ||(null==validUserVo.getPrimaryDispatchCenterVo().getId())){
						if( (null != validUserVo.getHomeUnitVo()) && (!validUserVo.getHomeUnitVo().getUnitCode().isEmpty()) ){
							OrganizationVo huOrgVo = OrganizationVo.getById(validUserVo.getHomeUnitVo().getId(), super.getGlobalCacheVo().getOrganizationVos());
							if(null != huOrgVo)
								validUserVo.setPrimaryDispatchCenterVo(huOrgVo.getManagingOrganization());
						}
					}
					*/
					User userEntity = UserVo.toEntity(new UserImpl(), validUserVo, true);
					userEntity.setId(null);

					try{
						userDao.save(userEntity);
						
						// add user to all incidents
						Boolean isPrivUser=false;
						for(SystemRole sr : userEntity.getSystemRoles()){
							if(sr.getId().compareTo(1L)==0)
								isPrivUser=true;
						}
						if(!isPrivUser){
							if(LongUtility.hasValue(userEntity.getId())){
								IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
								incidentDao.executeInsertIncidentNewUser(userEntity.getId());
							}
						}
						
						uifDao.delete(uifEntity);
						saveOccured = true;
					}catch(Exception e){
						e.printStackTrace();
						uifEntity.setFailureReason("PersistenceException["+e.getMessage()+"]");
						if(uifEntity.getFailureReason().length()>254)
							uifEntity.setFailureReason(uifEntity.getFailureReason().substring(0,254));

						uifDao.save(uifEntity);
						UserImportFailureVo uifVo = UserImportFailureVo.getInstance(uifEntity, true);
						uifVo.setIsPrivilegedUser(this.determinePrivilegedStatus(uifVo.getRoleVos()));

						CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
						courseOfActionVo.setCoaName("RESOLVE_IMPORT_FAILURES");
						courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						courseOfActionVo.setMessageVo(
								new MessageVo(
										"text.importUserAccounts",
										"text.error",
										null,
										MessageTypeEnum.CRITICAL));
						courseOfActionVo.setIsDialogueEnding(true);
						dialogueVo.setCourseOfActionVo(courseOfActionVo);
						dialogueVo.setResultObject(uifVo);
						return dialogueVo;
					}

					UserGridVo userGridVo = UserGridVo.getInstance(userEntity, true);

					resolvedFailureIds.add(uifEntity.getId());
					resolvedUserImportFailures.add(userGridVo);
					
				} else {
					errorObjects.addAll(dialogueVo.getCourseOfActionVo().getErrorObjectVos());
				}// end if(rulesHandler.execute(dialogueVo) == UserImportResolveFailuresRulesHandler._OK)
				index++;
			}// end for(UserVo validUserVo : validUserVos)
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			if(saveOccured == true) {
				coaVo.setCoaName("RESOLVE_IMPORT_FAILURE_SUCCESS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(
						new MessageVo(
								"text.importUserAccounts",
								"info.0038",
								null,
								MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(resolvedFailureIds);
				dialogueVo.setResultObjectAlternate(resolvedUserImportFailures);
			} else {
				coaVo.setCoaName("RESOLVE_IMPORT_FAILURE_FAIL");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
				//coaVo.getErrorObjectVos().add(new ErrorObject("info.nothingToImport"));
				coaVo.setIsDialogueEnding(true);
			}
			
			coaVo.getErrorObjectVos().addAll(errorObjects);//setErrorObjectVos(errorObjects);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	@Override
	public DialogueVo deleteImportFailure(Long uifId, DialogueVo dialogueVo) throws ServiceException {
		try{
			if(null == dialogueVo) {
				dialogueVo = new DialogueVo();
			}

			UserImportFailureDao uifDao = (UserImportFailureDao)super.context.getBean("userImportFailureDao");

			UserImportFailure entity = uifDao.getById(uifId, UserImportFailureImpl.class);

			if(null != entity) {
				uifDao.delete(entity);
			}else {
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserImportFailure["+uifId+"]");
				//TODO:  Create COAVO
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	private Boolean determinePrivilegedStatus(Collection<SystemRoleVo> roleVos) {
		for(SystemRoleVo svo : roleVos) {
			if(svo.getPrivilegedRole() != null) {
				if(svo.getPrivilegedRole() == true) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public byte[] exportUserAccounts(Collection<UserGridVo> vos, DialogueVo dialogueVo) throws ServiceException {
		//Returning null if not returning byte[], need look at
		try {
			if(null == dialogueVo) {
				dialogueVo = new DialogueVo();
			}
			
			if(vos.size() == 0) {
				CourseOfActionVo coaVo = new CourseOfActionVo();
//				coaVo.setCoaName("ValidationError");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
				Collection<ErrorObject> errorObjectVos = new ArrayList<ErrorObject>();
				errorObjectVos.add(new ErrorObject("error.exportUsers.noSelection"));
				coaVo.setErrorObjectVos(errorObjectVos);
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				return null;
			}
			
			XmlHandler xmlHandler = new XmlHandler();
			Boolean formatXml=false;
			
			if(super.getSystemParamValue(SystemParameterTypeEnum.FORMAT_XML).equals("TRUE"))
				formatXml=true;
			
			Collection<User> entities = userDao.getUsersByIds(UserGridVo.toIds(vos));
			
			UserTransfer userTransfer = new UserTransfer();

			for(User entity : entities){
				userTransfer.getUser().add(UserVo.toXmlObject(entity));
			}

			xmlHandler.setFormatXml(formatXml);
			
			xmlHandler.setXmlSchemaType(XmlSchemaTypeEnum.USER_TRANSFER);
			StringBuffer xmlString = xmlHandler.marshall(userTransfer);
			
			//TODO: compress & encrypt
			FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMFIPSTripleDES);
			byte[] encrypted = enc.encrypt(xmlString.toString().getBytes());
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("EXPORT_USER_ACCOUNTS");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
//			coaVo.setMessageVo(
//					new MessageVo(
//							"text.exportUserAccounts",
//							"info.0043",
//							null,
//							MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(encrypted);
			
			return encrypted;
			
		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return null; 
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService2#createSiteAdminUserAccount(gov.nwcg.isuite.core.vo.UserVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo createSiteAdminUserAccount(UserVo userVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			User entity=null;
			
			SaveSiteAdminRulesHandler rulesHandler = new SaveSiteAdminRulesHandler(this.context);
			
			if(rulesHandler.execute(entity, userVo, dialogueVo)==AbstractRule._OK){
				
				if(this.isCreateNewAccountManager==false){
					String nwcgBackupFolder=super.getSystemParamValue(SystemParameterTypeEnum.NWCG_BACKUP_FOLDER);

					// create the default database, only on first login access
					// do not create during site recovery process
					DbAvailVo dbAvailVo = new DbAvailVo();
					dbAvailVo.setDatabaseName(userVo.getDbname().toLowerCase());
					dbAvailVo.setPassword(userVo.getDbpwd());
					dbAvailVo.setConfirmPassword(userVo.getDbpwd());
					dbAvailVo.setDatabaseNameActual(userVo.getDbname().toLowerCase());
					dbAvailVo.setBackupInterval(Integer.valueOf("1"));
					dbAvailVo.setIsAutoBackup(true);
					dbAvailVo.setBackupDestination(nwcgBackupFolder);
					dbAvailVo.setCreatedBy(userVo.getLoginName());
					dbAvailVo.setMasterDb("master");
					
					DatabaseMgmtService dbservice = (DatabaseMgmtService)context.getBean("databaseMgmtService");
					DialogueVo dbDialogueVo = null;
					dbDialogueVo = dbservice.saveDb(dbAvailVo, null);
					if(dbDialogueVo.getCourseOfActionVo().getCoaType()==CourseOfActionTypeEnum.ERROR){
						return dbDialogueVo;
					}
					DbAvailVo updateDbAvailVo = (DbAvailVo)dbDialogueVo.getResultObject();
					String dsName=updateDbAvailVo.getDatasource();
					((UserSessionVo)context.getBean("userSessionVo")).setSiteDatabaseName(userVo.getDbname().toLowerCase());
					((UserSessionVo)context.getBean("userSessionVo")).setSiteDatasourceName(dsName);
					
					// remove default eisuite database
					DbAvailVo dbAvailVo2 = new DbAvailVo();
					dbAvailVo2.setDatabaseName("eisuite");
					dbAvailVo2.setPassword("eisuite");
					dbAvailVo2.setConfirmPassword("eisuite");
					dbAvailVo2.setDatasource("default0DataSource");
					dbAvailVo2.setId(1L);
					dbDialogueVo = null;
					dbDialogueVo = dbservice.removeDb(dbAvailVo2, null);
				}
				
				entity = new UserImpl();
				entity.setLoginName(userVo.getLoginName());
				entity.setFirstName(userVo.getFirstName());
				entity.setLastName(userVo.getLastName());
				entity.setEnabled(true);
				entity.setResetPassword(false);
				entity.setLastLoginDate(Calendar.getInstance().getTime());
				entity.setPasswordCreatedDate(Calendar.getInstance().getTime());
				entity.setShowDataSavedMsg(StringBooleanEnum.Y);
				
				FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);
				String newPwd= new String(enc.encrypt(userVo.getPassword().getBytes()));
				entity.setPassword(newPwd);
				entity.setHomeUnit(OrganizationVo.toEntity(null, userVo.getHomeUnitVo(), false));
				//entity.setPrimaryDispatchCenter(OrganizationVo.toEntity(null,userVo.getPrimaryDispatchCenterVo(), false));
				entity.getSystemRoles().clear();
				
				// add roles
				Collection<SystemRoleVo> vos = super.getGlobalCacheVo().getSystemRoleVos();
				for(SystemRoleVo vo : vos){
					if(vo.getRoleName().equals(SystemRoleType.ROLE_ACCOUNT_MANAGER.name()))
						entity.getSystemRoles().add(SystemRoleVo.toEntity(vo,false));
				}
				
				entity.getOrganizations().add(OrganizationVo.toEntity(null, userVo.getHomeUnitVo(), false));
				
				// add all dispatch centers for the homeunit
				for(OrganizationVo orgVo : super.getGlobalCacheVo().getOrganizationVos()){
					if(orgVo.getId().compareTo(userVo.getHomeUnitVo().getId())==0){
						if(CollectionUtility.hasValue(orgVo.getDispatchCenters())){
							// set dispatch center to first
							// entity.setPrimaryDispatchCenter(OrganizationVo.toEntity(null,orgVo.getDispatchCenters().iterator().next() , false));
							// add all
							// entity.getOrganizations().addAll(OrganizationVo.toEntityList(orgVo.getDispatchCenters(),false));
						}
					}
				}

				entity.setCreatedBy(userVo.getLoginName());
				userDao.save(entity);
				userDao.flushAndEvict(entity);
				
				entity = userDao.getById(entity.getId(), UserImpl.class);

				if(this.isCreateNewAccountManager==false){
					SystemParameterDao spDao = (SystemParameterDao)super.context.getBean("systemParameterDao");
					SystemParameter spEntity = new SystemParameterImpl();
					spEntity.setParameterName(SystemParameterTypeEnum.SITE_ADMIN_USER.toString());
					spEntity.setParameterValue(entity.getLoginName());
					
					spDao.save(spEntity);
				}
				
				PasswordHistoryDao phDao=(PasswordHistoryDao)context.getBean("passwordHistoryDao");
				PasswordHistory phEntity = new PasswordHistoryImpl();
				phEntity.setUser(entity);
				phEntity.setUserPassword(entity.getPassword());
				phEntity.setUserPasswordCreatedDate(entity.getPasswordCreatedDate());
				
				phDao.save(phEntity);
				
				userVo= UserVo.getInstance(entity, true);

				// create audit record
				DataAuditConfigDao dacDao = (DataAuditConfigDao)context.getBean("dataAuditConfigDao");
				DataAuditConfig dac = null;
				try{
					dac=dacDao.getByEventName("ISW_USER", DataAuditEvent.ACCOUNT_CREATED.name());
				}catch(Exception e){
					//smother
				}
				DataAuditTracking dataAuditTracking = new DataAuditTrackingImpl();
				dataAuditTracking.setDataAuditConfig(dac);
				dataAuditTracking.setTablePrimaryKeyId(entity.getId());
				dataAuditTracking.setChangeDate(Calendar.getInstance().getTime());
				dataAuditTracking.setAuditField1(entity.getLoginName());
				dataAuditTracking.setAuditField2(entity.getLastName());
				dataAuditTracking.setAuditField3(entity.getFirstName());
				dataAuditTracking.setAuditField4(entity.getHomeUnit().getUnitCode());
				//dataAuditTracking.setAuditField5(entity.getPrimaryDispatchCenter().getUnitCode());
				dataAuditTracking.setUserName(entity.getLoginName());
				dataAuditTracking.setOldValue("");
				dataAuditTracking.setNewValue("ACCOUNT CREATED");
				
				DataAuditTrackingDao datDao = (DataAuditTrackingDao)context.getBean("dataAuditTrackingDao");
				datDao.save(dataAuditTracking);
				datDao.flushAndEvict(dataAuditTracking);
				
		    	// get client ip
				HttpServletRequest hsr = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
											.getRequest();
				String remoteAddress=super.getIpFromRequest(hsr);
		    	//String remoteAddress = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();    	
		    	((UserSessionVo)this.context.getBean("userSessionVo")).setClientIp(remoteAddress);

				// add to session map
		    	/*
				UserSessionVo usvo = (UserSessionVo)this.context.getBean("userSessionVo");
				usvo.setUserId(entity.getId());
				usvo.setSiteDatabaseName(usvo.getSiteDatabaseName());
		    	UserSessionManagementService2 usms2 = (UserSessionManagementService2)context.getBean("userSessionManagementService2");
				DialogueVo dvo2 = usms2.startSession(usvo, new DialogueVo());
				*/
		    	
				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("CREATE_SITE_ADMIN");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(this.isCreateNewAccountManager==false){
					coaVo.setMessageVo(
							new MessageVo(
									"text.complete", 
									"info.0030.01", 
									new String[]{"Site Admin User Account"}, 
									MessageTypeEnum.INFO));
				}else{
					coaVo.setMessageVo(
							new MessageVo(
									"text.complete", 
									"info.0030.01", 
									new String[]{"New Site Account Manager"}, 
									MessageTypeEnum.INFO));
				}
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(userVo.getId());

			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo createNewAccountManager(UserVo userVo, DialogueVo dialogueVo) throws ServiceException {
		this.isCreateNewAccountManager=true;
		dialogueVo = this.createSiteAdminUserAccount(userVo, dialogueVo);
		this.isCreateNewAccountManager=false;
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService2#searchNap(gov.nwcg.isuite.core.filter.impl.NapSearchFilterImpl, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo searchNap(NapSearchFilterImpl napFilter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Security.setProperty("ssl.SocketFactory.provider", "com.ibm.jsse2.SSLSocketFactoryImpl");
			Security.setProperty("ssl.ServerSocketFactory.provider", "com.ibm.jsse2.SSLServerSocketFactoryImpl");

			GetUsersServiceLocator locator = new GetUsersServiceLocator();
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
			
			GetUsers getUsers = locator.getGetUsersSoap11();
			
			GetUsersRequest request = new GetUsersRequest();
			request.setApplicationInstance(super.getSystemParamValue(SystemParameterTypeEnum.NAP_APP_INSTANCE));
			request.setAscendingOrder(true);
			request.setMaxCount(8000);
			request.setSkipCount(0);
			request.setFilterValue("");

			GetUsersRequestFilter filter = GetUsersRequestFilter.fromValue("");
			request.setFilter(filter);
			
			UserSessionVo currentUser=super.getUserSessionVo();
			Boolean isAccountManager=false;
			Boolean isDataManager=false;
			for(SystemRoleVo srvo : currentUser.getUserRoleVos()){
				if(srvo.getRoleName().equals("ROLE_ACCOUNT_MANAGER"))
					isAccountManager=true;
				if(srvo.getRoleName().equals("ROLE_DATA_MANAGER"))
					isDataManager=true;
			}
			
			Boolean adUsersOnly=false;
			if(isAccountManager==false && isDataManager==true)
				adUsersOnly=true;
			
			GetUsersResponse response = getUsers.getUsers(request);
			Collection<NapUserVo> napUserVos = new ArrayList<NapUserVo>();
			Collection<NapUserVo> napUserVos2 = new ArrayList<NapUserVo>();

			if(null != response){
				napUserVos = NapUserVo.getInstances(response,adUsersOnly, napFilter);


				//System.out.println(napUserVos.size());
				for(NapUserVo vo : napUserVos){
					// check if already in eisuite
					User entity2 = userDao.getByLoginName(vo.getUserName());
					if(null == entity2)
						napUserVos2.add(vo);
					else
						userDao.flushAndEvict(entity2);
				}
			}
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("SEARCH_NAP");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(napUserVos2);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService2#saveNapUser(gov.nwcg.isuite.core.vo.UserVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveNapUser(UserVo userVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) {
			dialogueVo = new DialogueVo();
		}
		
		try{
			UserSaveRulesHandler rulesHandler = new UserSaveRulesHandler(context);
			
			User userEntity = null;
			if(LongUtility.hasValue(userVo.getId())){
				userEntity = userDao.getById(userVo.getId(), UserImpl.class);
			}

			if(rulesHandler.execute(userEntity, userVo, dialogueVo)==UserSaveRulesHandler._OK){

				userEntity = UserVo.toEntity(userEntity, userVo, true);
				
				userDao.save(userEntity);

				userVo.setId(userEntity.getId());
				userDao.flushAndEvict(userEntity);

				// execute follow up actions
				rulesHandler.executeProcessedActions(userEntity, userVo, null, dialogueVo);

				userDao.flushAndEvict(userEntity);
				userEntity = userDao.getById(userEntity.getId(), UserImpl.class);
				userVo=UserVo.getInstance(userEntity, true);

				// get the gridVo
				UserGridVo gridVo = UserGridVo.getInstance(userEntity, true);

				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_USER_ACCOUNT");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.userAccounts", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(userVo);
				dialogueVo.setResultObjectAlternate(gridVo);
					
				rulesHandler.addAdditionalMessages(userVo, dialogueVo);
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public String checkPasswordStatus(Long userId) throws ServiceException {
		try
		{
			User entity = userDao.getById(userId, UserImpl.class);

			String strChkDays = super.getSystemParamValue(SystemParameterTypeEnum.PWD_EXPIRE_NOTIFY_DAYS);
			int chkDays = Integer.parseInt( (null != strChkDays && !strChkDays.isEmpty() ? strChkDays : "10"));
			 	
			if(null==entity)
			{
				super.handleException(new ServiceException(new ErrorObject(ErrorEnum._900001_ENTITY_NOT_FOUND,"User")));
			}
			
			if(null==entity.getLastLoginDate())
			{
				return "FIRSTTIME";
			}
			else if((null!=entity.isResetPassword()) && (entity.isResetPassword()))
			{
				return "RESET";
			}
			//else if((null==entity.getAccountExpirationDate()) || (entity.getAccountExpirationDate().before(Calendar.getInstance().getTime())) )
			//	return "EXPIRED";
			else 
			{
				//if(null == entity.getPasswordCreatedDate() || null != entity.getPasswordCreatedDate()){
				if(null == entity.getPasswordCreatedDate() || ((entity.getPasswordCreatedDate()).toString()).isEmpty())
				{
					entity.setPasswordCreatedDate(entity.getCreatedDate());
				}
				
				String expireTime=super.getSystemParamValue(SystemParameterTypeEnum.PWD_EXPIRE_TIME);

				if(null==expireTime || expireTime.isEmpty())
					expireTime="60";
				
				Date expireDate=DateUtil.addDaysToDate(entity.getPasswordCreatedDate(),Integer.parseInt(expireTime));
				Date now=Calendar.getInstance().getTime();
	 
				long diffDays=0;
				if(now.before(expireDate))
				{
					diffDays=DateUtil.diffDays(now,expireDate);
				}
				else
				{
					if(now.after(expireDate))
					{
						return "EXPIRED";
					}
				}
		 	
				if(diffDays >= 0 && diffDays > Long.parseLong(expireTime))
				{
					return "EXPIRED";
				}
				else
				{
					if(diffDays <= chkDays)
					{
						return String.valueOf(diffDays);
					}
					else
					{
						return "OK";
					}
				}
			}
		//	else
		//		return "OK";
		}catch(Exception e){
			super.handleException(e);
		}
		return "";
	}
	
	public DialogueVo updateROBAgreementDate(Long userId, String robType) throws ServiceException {
		DialogueVo dvo = new DialogueVo();
		
		try{
			User entity = userDao.getById(userId, UserImpl.class);
 
			if(null==entity)
				super.handleException(new ServiceException(new ErrorObject(ErrorEnum._900001_ENTITY_NOT_FOUND,"User")));
			
			entity.setRobAgreementDate(Calendar.getInstance().getTime());
			userDao.save(entity);
			createAuditEvent("ROB ACCEPTED",robType,entity.getId(),entity);
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("UPDATE_ROB");
			coa.setCoaType(CourseOfActionTypeEnum.NOACTION);
			dvo.setCourseOfActionVo(coa);
		}catch(Exception e){
			super.dialogueException(dvo, e);
		}
		
		return dvo;
	}
	
	private void createAuditEvent(String oldValue, String newValue, Long primaryKey, User user){
		DataAuditConfigDao dacDao = (DataAuditConfigDao)context.getBean("dataAuditConfigDao");
		DataAuditConfig dac = null;
		try{
			dac=dacDao.getByEventName("ISW_USER", DataAuditEvent.ROB_ACCEPTED.name());
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
