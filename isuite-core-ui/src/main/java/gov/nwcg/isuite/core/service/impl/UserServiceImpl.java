package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.DataAuditConfig;
import gov.nwcg.isuite.core.domain.DataAuditTracking;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.PasswordHistory;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserImportFailure;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.WorkAreaUser;
import gov.nwcg.isuite.core.domain.impl.DataAuditTrackingImpl;
import gov.nwcg.isuite.core.domain.impl.PasswordHistoryImpl;
import gov.nwcg.isuite.core.domain.impl.SystemParameterImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.domain.impl.UserImportFailureImpl;
import gov.nwcg.isuite.core.domain.impl.WorkAreaImpl;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.UserImportFailureFilter;
import gov.nwcg.isuite.core.persistence.DataAuditConfigDao;
import gov.nwcg.isuite.core.persistence.DataAuditTrackingDao;
import gov.nwcg.isuite.core.persistence.PasswordHistoryDao;
import gov.nwcg.isuite.core.persistence.RestrictedIncidentUserDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.UserGroupUserDao;
import gov.nwcg.isuite.core.persistence.UserImportFailureDao;
import gov.nwcg.isuite.core.persistence.WorkAreaDao;
import gov.nwcg.isuite.core.persistence.WorkAreaUserDao;
import gov.nwcg.isuite.core.service.UserService;
import gov.nwcg.isuite.core.service.UserSessionManagementService;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserImportFailureVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorType;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.DataAuditEvent;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.xml.XmlHandler;
import gov.nwcg.isuite.framework.xml.XmlSchemaTypeEnum;
import gov.nwcg.isuite.xml.SystemRoleType;
import gov.nwcg.isuite.xml.UserTransfer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class UserServiceImpl extends BaseService implements UserService {
	protected UserDao userDao;

	public UserServiceImpl(){
		super();
	}

	public void initialization(){
		userDao = (UserDao)super.context.getBean("userDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#getGrid(gov.nwcg.isuite.core.filter.UserFilter)
	 */
	public Collection<UserGridVo> getGrid(UserFilter userFilter) throws ServiceException {
	   Collection<UserGridVo> userGridVos = new ArrayList<UserGridVo>();
	   try {
	      userFilter.setCurrentUserId(super.getUserSessionVo().getUserId());
	      userGridVos = userDao.getGrid(userFilter);
	   } catch (Exception e) {
	      super.handleException(e);
	   }

	   return userGridVos;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#getUserById(java.lang.Long)
	 */
	public UserVo getUserById(Long id) throws ServiceException {
		try {
			UserVo vo= userDao.getUserById(id);
			
			return vo;
			//return userDao.getUserById(id);
		} catch (Exception e) {
			super.handleException(e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#enableUsers(java.util.Collection)
	 */
	public Collection<UserGridVo> enableUsers(Collection<UserGridVo> userGridVos) throws ServiceException {
		try{
			Collection<Long> userIds = new ArrayList<Long>();
			for(UserGridVo vo : userGridVos){
				userIds.add(vo.getId());
			}
			userDao.enableUsers(userIds);
			return UserGridVo.getInstances(userDao.getUsersByIds(userIds), true);
		}catch(Exception e){
			super.handleException(e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#disableUsers(java.util.Collection)
	 */
	public Collection<UserGridVo> disableUsers(Collection<UserGridVo> userGridVos) throws ServiceException {
		try{
			Collection<Long> userIds = new ArrayList<Long>();
			for(UserGridVo vo : userGridVos){
				userIds.add(vo.getId());
			}
			
			userDao.disableUsers(userIds);
			
			this.removeUserAssociations(userIds);
			this.endUserSessions(userIds);
			return UserGridVo.getInstances(userDao.getUsersByIds(userIds), true);
		}catch(Exception e){
			super.handleException(new ErrorObject(ErrorEnum._90000_ERROR, e.getMessage()));
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#deleteUsers(java.util.Collection)
	 */
	public void deleteUsers(Collection<UserGridVo> userGridVos) throws ServiceException {
		try{
			Collection<Long> userIds = new ArrayList<Long>();
			for(UserGridVo vo : userGridVos){
				userIds.add(vo.getId());
			}
			userDao.deleteUsers(userIds);
		}catch(Exception e){
			super.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#getUsers(gov.nwcg.isuite.core.filter.UserFilter)
	 */
	public Collection<UserVo> getUsers(UserFilter filter) throws ServiceException {
		try{
			Collection<User> entities = userDao.getUsers(filter);
			if(null != entities){
				return UserVo.getInstances(entities,true);
			}
		}catch(Exception e){
			super.handleException(e);
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#save(gov.nwcg.isuite.core.vo.UserVo)
	 */
	public UserVo save(UserVo userVo) throws ServiceException {
		User entity = null;
		Long oldWorkAreaId = null;
		Boolean newPassword=false;
		Boolean nowDisabled=false;
		Boolean isNewUser=false;
		
		try{
			if( (userVo.getId() != null) && (userVo.getId() > 0) )
				entity = userDao.getById(userVo.getId(), UserImpl.class);

			if(null == entity){
				/*
				 *  Check if username is unique
				 */
				User checkUser=userDao.getByLoginName(userVo.getLoginName());
				if( checkUser != null) {
					super.handleException(ErrorEnum._0045_DUPLICATE_USER_NAME, 
					         checkUser.getLoginName(), checkUser.getFirstName(), checkUser.getLastName());
				}
				isNewUser=true;
			}else{
				/*
				 * if loginname changed, verify new one is unique
				 */
				if(!userVo.getLoginName().trim().equals(entity.getLoginName().trim())){
					if(userDao.getByLoginName(userVo.getLoginName()) != null) {
						super.handleException(ErrorEnum._0045_DUPLICATE_USER_NAME,
						         userVo.getLoginName(), userVo.getFirstName(), userVo.getLastName());
					}
				}
		
				/*
				 * if admin, changed the password, set reset_password to true
				 */
				if(null != entity){
					/*
					if( (!userVo.getPasswordVo().getNewPassword().equals(userVo.getPasswordVo().getBasePassword())) 
							&& 
						(!entity.getPassword().equals(userVo.getPasswordVo().getNewPassword())) ) {
						// validate the new password
						int historyCount = Integer.parseInt(super.getSystemParamValue(SystemParameterTypeEnum.PASSWORD_HISTORY_COUNT));
						PasswordHistoryDao phDao=(PasswordHistoryDao)context.getBean("passwordHistoryDao");
						Collection<PasswordHistory> passwords = phDao.getUserHistory(entity.getId(),historyCount);

						FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);
						String newPwd= new String(enc.encrypt(userVo.getPasswordVo().getNewPassword().getBytes()));
						
						// check if new password has already been used
						for(PasswordHistory phEntity : passwords){
							
							if(phEntity.getUserPassword().equals(newPwd)){
								throw new ServiceException(new ErrorObject(ErrorEnum._900007_PWD_ERROR));
							}
						}
						
						newPassword=true;
						userVo.setResetPassword(true);
						userVo.setPasswordCreatedDate(Calendar.getInstance().getTime());
					}
					*/
				}
				
				/*
				 * If user was enabled, and now is getting disabled, set flag
				 */
				if( (entity.isEnabled()) && (!userVo.getEnabled()) ){
					nowDisabled=true;
				}
			}
			
			if(entity == null) {
				entity = new UserImpl();
				userVo.setResetPassword(true);
				userVo.setPasswordCreatedDate(Calendar.getInstance().getTime());
				newPassword=true;
			}
			
			entity.getSystemRoles().clear();
			
			WorkAreaDao workAreaDao = (WorkAreaDao)super.context.getBean("workAreaDao");
			WorkAreaUserDao workAreaUserDao = (WorkAreaUserDao)super.context.getBean("workAreaUserDao");
			
			if(entity.getPrimaryDispatchCenter() != null) {
			   if(!entity.getPrimaryDispatchCenterId().equals(userVo.getPrimaryDispatchCenterVo().getId())) {
			      oldWorkAreaId = workAreaDao.getWorkAreaIdByStandardOrganizationId(entity.getPrimaryDispatchCenter().getId());
			   }
			}
			
			if(entity.getId()!=null && entity.getId()>0){
				/*
				 * Check if existing password is getting changed.
				 */
				if(!entity.getPassword().equals(userVo.getPassword())){
					int historyCount = Integer.parseInt(super.getSystemParamValue(SystemParameterTypeEnum.PASSWORD_HISTORY_COUNT));
					
					PasswordHistoryDao phDao=(PasswordHistoryDao)context.getBean("passwordHistoryDao");
					Collection<PasswordHistory> passwords = phDao.getUserHistory(entity.getId(),historyCount);
						
					// check if new password has already been used
					for(PasswordHistory phEntity : passwords){
						if(phEntity.getUserPassword().equals(userVo.getPassword())){
							throw new ServiceException(new ErrorObject(ErrorEnum._900007_PWD_ERROR));
						}
					}
				}
				
			}
			
			entity=UserVo.toEntity(entity, userVo, true);

			userDao.save(entity);

			if(newPassword){
				PasswordHistoryDao phDao=(PasswordHistoryDao)context.getBean("passwordHistoryDao");
				PasswordHistory phEntity = new PasswordHistoryImpl();
				phEntity.setUser(entity);
				phEntity.setUserPassword(entity.getPassword());
				phEntity.setUserPasswordCreatedDate(entity.getPasswordCreatedDate());
				
				phDao.save(phEntity);
			}

			if(nowDisabled){
				Collection<Long> userIds = new ArrayList<Long>();
				userIds.add(entity.getId());
				this.removeUserAssociations(userIds);
			}
			

			if(isNewUser && super.isEnterprise()){
				// create a restricted work area for the user
				WorkArea restrWorkArea = new WorkAreaImpl();
				restrWorkArea.setName("My Restricted Work Area");
				restrWorkArea.setDescription(restrWorkArea.getName());
				restrWorkArea.setUser(entity);
				restrWorkArea.setSharedOut(false);
				restrWorkArea.setCreatedBy(entity.getLoginName());
				restrWorkArea.setLastModifiedBy(entity.getLoginName());
				
				WorkAreaDao wadao = (WorkAreaDao)context.getBean("workAreaDao");
				wadao.save(restrWorkArea);
				
			}
			
			userDao.flushAndEvict(entity);
			for(Organization org : entity.getOrganizations()) {
			   userDao.flushAndEvict(org);
			}

			for(SystemRole role : entity.getSystemRoles()) {
			   userDao.flushAndEvict(role);
			}
			
			userDao.createWorkAreaUserAssoc1(entity.getId(), entity.getPrimaryDispatchCenter().getId());
			
			entity = userDao.getById(entity.getId(), UserImpl.class);

			// remove user from standard work area association if user.pdc changed
			if(oldWorkAreaId != null) {
			   WorkAreaUser workAreaUser = workAreaUserDao.getByWorkAreaIdAndUserId(oldWorkAreaId, entity.getId());
			   if(null != workAreaUser)
				   workAreaUserDao.delete(workAreaUser);
			}
			
			/*
			//TODO:  This is not the best approach, but it should do for now. -dbudge
	         Long workAreaId = workAreaDao.getWorkAreaIdByStandardOrganizationId(userVo.getPrimaryDispatchCenterVo().getId());
	         WorkArea waEntity = workAreaDao.getById(workAreaId, WorkAreaImpl.class);
	         WorkAreaUser wauEntity = workAreaUserDao.getByWorkAreaIdAndUserId(workAreaId, entity.getId());
	         if(wauEntity == null) {
	            wauEntity = new WorkAreaUserImpl();
	         }
	         wauEntity.setUser(entity);
	         wauEntity.setWorkArea(waEntity);
	         workAreaUserDao.save(wauEntity);
			*/
			return UserVo.getInstance(entity, true);
		}catch(Exception e){
			super.handleException(e);
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#addOrganizations(java.util.Collection, java.lang.Long)
	 */
	public Collection<OrganizationVo> addOrganizations(Collection<OrganizationVo> vos, Long userId) throws ServiceException {

		try {
			User entity = userDao.getById(userId, UserImpl.class);

			if(null != entity){
				if(null == entity.getOrganizations())
					entity.setOrganizations(new ArrayList<Organization>());

				for(OrganizationVo vo : vos){
					boolean hasOrg=false;
					for(Organization orgEntity : entity.getOrganizations()){
						if(orgEntity.getId().compareTo(vo.getId())==0){
							hasOrg=true;
							break;
						}
					}
					if(!hasOrg)
						entity.getOrganizations().add(OrganizationVo.toEntity(null, vo, false));    		  
				}

				userDao.save(entity);

				userDao.flushAndEvict(entity);
				
				userDao.createWorkAreaUserAssoc2(entity.getId());
				
				entity = userDao.getById(userId, UserImpl.class);
				
				return OrganizationVo.getInstances(entity.getOrganizations(), true);
				
			}else{
				super.handleException(new ServiceException(new ErrorObject(ErrorEnum._900001_ENTITY_NOT_FOUND,"User")));
			}
		}
		catch ( Exception e ) {
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#removeOrganizations(java.util.Collection, java.lang.Long)
	 */
	public Collection<OrganizationVo> removeOrganizations(Collection<OrganizationVo> vos, Long userId) throws ServiceException {
		try {
			User entity = userDao.getById(userId, UserImpl.class);

			if(null != entity){
				if(null == entity.getOrganizations())
					entity.setOrganizations(new ArrayList<Organization>());

				Collection<Organization> orgsToDelete = new ArrayList<Organization>();
				
				for(OrganizationVo vo : vos){
					for(Organization orgEntity : entity.getOrganizations()){
						if(orgEntity.getId().compareTo(vo.getId())==0){
							orgsToDelete.add(orgEntity);
							break;
						}
					}
				}
				
				Collection<WorkAreaUser> waUsersToDelete = new ArrayList<WorkAreaUser>();
				
				//entity.getWorkAreaUsers().removeAll(waUsersToDelete);
				entity.getOrganizations().removeAll(orgsToDelete);

				userDao.save(entity);

				userDao.flushAndEvict(entity);
				
				removeWorkAreaAssociations(userId);
				
				entity = userDao.getById(userId, UserImpl.class);
				
				return OrganizationVo.getInstances(entity.getOrganizations(), true);
				
			}else{
				super.handleException(new ServiceException(new ErrorObject(ErrorEnum._900001_ENTITY_NOT_FOUND,"User")));
			}
		}
		catch ( Exception e ) {
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#generateUserTransferData(java.util.Collection)
	 */
	public byte[] generateUserTransferData(Collection<UserGridVo> vos) throws ServiceException {
		XmlHandler xmlHandler = new XmlHandler();
		Boolean formatXml=false;
		
		try{
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

			return encrypted;
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#consumeUserTransferData(java.lang.String)
	 */
//	public void consumeUserTransferData(String xml, String defaultPassword) throws ServiceException {
	public void consumeUserTransferData(byte[] xmlByteArray, String defaultPassword) throws ServiceException {
		
		String xsdBasePath="";
		XmlHandler xmlHandler = new XmlHandler();
		xmlHandler.setXmlSchemaType(XmlSchemaTypeEnum.USER_TRANSFER);
		Collection<UserImportFailure> failures = new ArrayList<UserImportFailure>();
		
		try{
			FIPSEncryptor encryptor = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMFIPSTripleDES);
			byte[] xmlBytes = encryptor.decrypt(xmlByteArray);
			String xmlString = new String(xmlBytes);
			if(super.getSystemParamValue(SystemParameterTypeEnum.VALIDATE_XML).equals("TRUE")){
				xsdBasePath=super.getSystemParamValue(SystemParameterTypeEnum.XSD_BASE_PATH);
				xmlHandler.setXsdBasePath(xsdBasePath);
			}
			
			StringBuffer xmlBuffer = new StringBuffer().append(xmlString);
			
			UserTransfer userTransfer = (UserTransfer)xmlHandler.unmarshall(xmlBuffer);

			// Convert the xml objects into entities
			if(userTransfer.getUser().size()>0){
				UserImportFailureDao uifDao = (UserImportFailureDao)super.context.getBean("userImportFailureDao");

				uifDao.deleteAll();
				
				Collection<User> userEntities = UserVo.toEntityList(userTransfer.getUser());
				
				Collection<SystemRoleVo> sysRoleVos = super.getGlobalCacheVo().getSystemRoleVos();
				
				for(User user : userEntities){
					Boolean proceed=true;
					
					user.setSystemRoles(SystemRoleVo.toEntityList(user.getSystemRoles(), sysRoleVos));

					// set the homeunit entity from the homeunitcode
					if(null != user.getHomeUnit() && null != user.getPrimaryDispatchCenter()){
						OrganizationVo homeUnit = OrganizationVo.getByUnitCode(user.getHomeUnit().getUnitCode(), super.getGlobalCacheVo().getOrganizationVos());
						OrganizationVo pdc = OrganizationVo.getByUnitCode(user.getPrimaryDispatchCenter().getUnitCode(), super.getGlobalCacheVo().getOrganizationVos());
						if(null != homeUnit && null != pdc){
							user.setHomeUnit(OrganizationVo.toEntity(null, homeUnit, true));
							user.setPrimaryDispatchCenter(OrganizationVo.toEntity(null, pdc, true));
//							if(null != organizationVo.getManagingOrganization())
//								user.setPrimaryDispatchCenter(OrganizationVo.toEntity(null, organizationVo.getManagingOrganization(), true));
//							user.getOrganizations().add(OrganizationVo.toEntity(null, homeUnit, false));
//							user.getOrganizations().add(OrganizationVo.toEntity(null, pdc, false));
//							if(null != organizationVo.getManagingOrganization())
//								user.getOrganizations().add(OrganizationVo.toEntity(null, organizationVo.getManagingOrganization(), false));
						}else{
							UserImportFailure uifEntity = UserImportFailureVo.toEntity(user);
							uifEntity.setFailureReason("No organization exists with unit code: "+user.getHomeUnit().getUnitCode()+".");
							failures.add(uifEntity);
							proceed=false;
						}
					}else {
						UserImportFailure uifEntity = UserImportFailureVo.toEntity(user);
						uifEntity.setFailureReason("Home Unit / Primary Dispatch center cannot be empty.");
						failures.add(uifEntity);
							
						proceed=false;
					}

					if(null == user.getLoginName() || user.getLoginName().isEmpty()) {
						UserImportFailure uifEntity = UserImportFailureVo.toEntity(user);
						uifEntity.setFailureReason("Login Name is a required field.");
						failures.add(uifEntity);
						proceed = false;
					}
					
					if(proceed){
						// is there already a user with the same loginname?
						if(null != userDao.getByLoginName(user.getLoginName())){
							UserImportFailure uifEntity = UserImportFailureVo.toEntity(user);
							uifEntity.setFailureReason("User with loginname already exists.");
							failures.add(uifEntity);
						}else{
							// try and save it
							try{
								FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);
								String newPwd= new String(enc.encrypt(defaultPassword.getBytes()));
								user.setPassword(newPwd);
								userDao.save(user);
							}catch(Exception e){
								UserImportFailure uifEntity = UserImportFailureVo.toEntity(user);
								uifEntity.setFailureReason("PersistenceException["+e.getMessage()+"]");
								if(uifEntity.getFailureReason().length()>254)
									uifEntity.setFailureReason(uifEntity.getFailureReason().substring(0,254));
								failures.add(uifEntity);
							}
						}
					}
				}
				
				if(failures.size()>0){
					uifDao.saveAll(failures);
				}
				
			}
		}catch(Exception e){
			ServiceException se = new ServiceException(e);
			if(se.getMessage().indexOf("file") != -1) {
				super.handleException(ErrorEnum._0040_USER_IMPORTS_CORRUPT_FILE);
			} else {
				super.handleException(e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#getLastImportFailures()
	 */
	public Collection<UserImportFailureVo> getLastImportFailures(UserImportFailureFilter filter) throws ServiceException {
		try{
			UserImportFailureDao uifDao = (UserImportFailureDao)super.context.getBean("userImportFailureDao");
			
			Collection<UserImportFailureVo> vos = uifDao.getGrid(filter);
			
			for(UserImportFailureVo vo : vos){
				// reset password, don't send back
				vo.setPassword("");
				
				if( (null != vo.getHomeUnitCode()) && (!vo.getHomeUnitCode().isEmpty()) )
					vo.setHomeUnitVo(OrganizationVo.getByUnitCode(vo.getHomeUnitCode(), super.getGlobalCacheVo().getOrganizationVos()));
				if( (null != vo.getPdcUnitCode()) && (!vo.getPdcUnitCode().isEmpty()) )
					vo.setPrimaryDispatchCenterVo(OrganizationVo.getByUnitCode(vo.getPdcUnitCode(), super.getGlobalCacheVo().getOrganizationVos()));
				if( (null != vo.getRoleVos()) && (!vo.getRoleVos().isEmpty()) ) {
					vo.setIsPrivilegedUser(this.determinePrivilegedStatus(vo.getRoleVos()));
				}
			}
			
			return vos;
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#resolveImportFailure(gov.nwcg.isuite.core.vo.UserImportFailureVo, gov.nwcg.isuite.core.vo.UserVo)
	 */
	public UserImportFailureVo resolveImportFailure(UserImportFailureVo vo, UserVo validUserVo) throws ServiceException {
		
		try{
			UserImportFailureDao uifDao = (UserImportFailureDao)super.context.getBean("userImportFailureDao");
			
			UserImportFailure uifEntity = uifDao.getById(vo.getId(), UserImportFailureImpl.class);
			if(null==uifEntity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserImportFailure["+vo.getId()+"]");
			
			if( (null == validUserVo.getPrimaryDispatchCenterVo()) ||(null==validUserVo.getPrimaryDispatchCenterVo().getId())){
				if( (null != validUserVo.getHomeUnitVo()) && (!validUserVo.getHomeUnitVo().getUnitCode().isEmpty()) ){
					OrganizationVo huOrgVo = OrganizationVo.getById(validUserVo.getHomeUnitVo().getId(), super.getGlobalCacheVo().getOrganizationVos());
					if(null != huOrgVo)
						validUserVo.setPrimaryDispatchCenterVo(huOrgVo.getManagingOrganization());
				}
			}
			// is there already a user with the same loginname?
			if(null != userDao.getByLoginName(validUserVo.getLoginName())){
			   uifEntity.setFailureReason("User with loginname already exists.");
			   uifDao.save(uifEntity);
			   throw new ServiceException(uifEntity.getFailureReason());
			}
			User entity = UserVo.toEntity(new UserImpl(), validUserVo, true);
			
			try{
				userDao.save(entity);
				uifDao.delete(uifEntity);
			}catch(Exception e){
				uifEntity.setFailureReason("PersistenceException["+e.getMessage()+"]");
				if(uifEntity.getFailureReason().length()>254)
					uifEntity.setFailureReason(uifEntity.getFailureReason().substring(0,254));
				
				uifDao.save(uifEntity);
				UserImportFailureVo uifVo = UserImportFailureVo.getInstance(uifEntity, true);
				uifVo.setIsPrivilegedUser(this.determinePrivilegedStatus(uifVo.getRoleVos()));
				return uifVo;
			}
			
		}catch(ServiceException se){
			throw se;
		} catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#deleteImportFailure(gov.nwcg.isuite.core.vo.UserImportFailureVo)
	 */
	public void deleteImportFailure(UserImportFailureVo vo) throws ServiceException {
		try{
			UserImportFailureDao uifDao = (UserImportFailureDao)super.context.getBean("userImportFailureDao");
			
			UserImportFailure entity = uifDao.getById(vo.getId(), UserImportFailureImpl.class);
			
			if(null != entity)
				uifDao.delete(entity);
			else
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"UserImportFailure["+vo.getId()+"]");
		}catch(Exception e){
			super.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#checkPasswordStatus(java.lang.Long)
	 */
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
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#changePassword(java.lang.Long, java.lang.String)
	 */
	public void changePassword(Long userId, String newPassword) throws ServiceException {
		try{
			User entity = userDao.getById(userId, UserImpl.class);
 
			if(null==entity)
				super.handleException(new ServiceException(new ErrorObject(ErrorEnum._900001_ENTITY_NOT_FOUND,"User")));

			FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);
			String newPwd= new String(enc.encrypt(newPassword.getBytes()));
			
			if(entity.getPassword().equals(newPwd)){
				throw new ServiceException(new ErrorObject(ErrorEnum._900007_PWD_ERROR));
			}
			
			int historyCount = Integer.parseInt(super.getSystemParamValue(SystemParameterTypeEnum.PASSWORD_HISTORY_COUNT));
			
			PasswordHistoryDao phDao=(PasswordHistoryDao)context.getBean("passwordHistoryDao");
			Collection<PasswordHistory> passwords = phDao.getUserHistory(userId,historyCount);
			
			// check if new password has already been used
			for(PasswordHistory phEntity : passwords){
				if(phEntity.getUserPassword().equals(newPwd)){
					throw new ServiceException(new ErrorObject(ErrorEnum._900007_PWD_ERROR));
				}
			}
			
			PasswordHistory pwdHistory = new PasswordHistoryImpl();
			pwdHistory.setUser(entity);
			pwdHistory.setUserPassword(newPwd);
			pwdHistory.setUserPasswordCreatedDate(Calendar.getInstance().getTime());
			
			entity.setPassword(newPwd);
			
			String expireTime=super.getSystemParamValue(SystemParameterTypeEnum.PWD_EXPIRE_TIME);
			if(null==expireTime || expireTime.isEmpty())
				expireTime="60";
			
			Date now = Calendar.getInstance().getTime();
			entity.setAccountExpirationDate(DateUtil.addDaysToDate(now,Integer.parseInt(expireTime)));
			entity.setPasswordCreatedDate(Calendar.getInstance().getTime());
			entity.setLastLoginDate(Calendar.getInstance().getTime());
			entity.setResetPassword(false);
			
			userDao.save(entity);
			
			phDao.save(pwdHistory);
			
		}catch(Exception e){
			super.handleException(e);
		}
	}
	
	public void validateOldPassword(Long userId, String oldPassword) throws ServiceException {
		try{
			User entity = userDao.getById(userId, UserImpl.class);
 
			if(null==entity)
				super.handleException(new ServiceException(new ErrorObject(ErrorEnum._900001_ENTITY_NOT_FOUND,"User")));

			FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);
			String oldPwd= new String(enc.encrypt(oldPassword.getBytes()));
			
			// check if passwords match
			if(entity.getPassword().equals(oldPwd))
				return;
			else
				super.handleException(new ServiceException(new ErrorObject(ErrorEnum._900008_INVALID_CURRENT_PASSWORD)));
			
		}catch(Exception e){
			super.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#updateROBAgreementDate(java.lang.Long)
	 */
	public void updateROBAgreementDate(Long userId, String robType) throws ServiceException {
		try{
			User entity = userDao.getById(userId, UserImpl.class);
 
			if(null==entity)
				super.handleException(new ServiceException(new ErrorObject(ErrorEnum._900001_ENTITY_NOT_FOUND,"User")));
			
			entity.setRobAgreementDate(Calendar.getInstance().getTime());
			userDao.save(entity);
			createAuditEvent("ROB ACCEPTED",robType,entity.getId(),entity);
		}catch(Exception e){
			super.handleException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#createSiteAdminUserAccount(gov.nwcg.isuite.core.vo.UserVo)
	 */
	public UserVo createSiteAdminUserAccount(UserVo userVo) throws ServiceException {
//		OrganizationDao orgDao = (OrganizationDao) super.context.getBean("organizationDao");
		User entity = null;

		try{
			entity = new UserImpl();

			entity.setLoginName(userVo.getLoginName());
			entity.setFirstName(userVo.getFirstName());
			entity.setLastName(userVo.getLastName());
			entity.setEnabled(true);
			entity.setResetPassword(false);
			entity.setLastLoginDate(Calendar.getInstance().getTime());
			entity.setPasswordCreatedDate(Calendar.getInstance().getTime());
			
			FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);
			//String newPwd= new String(enc.encrypt(userVo.getPasswordVo().getNewPassword().getBytes()));
			String newPwd= ""; //new String(enc.encrypt(userVo.getPasswordVo().getNewPassword().getBytes()));
			entity.setPassword(newPwd);
			entity.setHomeUnit(OrganizationVo.toEntity(null, userVo.getHomeUnitVo(), false));
			entity.setPrimaryDispatchCenter(OrganizationVo.toEntity(null,userVo.getPrimaryDispatchCenterVo(), false));
			entity.getSystemRoles().clear();
			
			// add roles
			Collection<SystemRoleVo> vos = super.getGlobalCacheVo().getSystemRoleVos();
			for(SystemRoleVo vo : vos){
//				if(vo.getRoleName().equals("ROLE_ACCOUNT_ADMINISTRATOR"))
//					entity.getSystemRoles().add(SystemRoleVo.toEntity(vo,false));
				if(vo.getRoleName().equals(SystemRoleType.ROLE_ACCOUNT_MANAGER.name()))
					entity.getSystemRoles().add(SystemRoleVo.toEntity(vo,false));
				if(vo.getRoleName().equals(SystemRoleType.ROLE_DATA_STEWARD.name())) {
					entity.getSystemRoles().add(SystemRoleVo.toEntity(vo,false));
				}
			}
			
			entity.getOrganizations().add(OrganizationVo.toEntity(null, userVo.getHomeUnitVo(), false));
			
			if(null != entity.getPrimaryDispatchCenter().getId() && entity.getPrimaryDispatchCenter().getId().compareTo(entity.getHomeUnit().getId())!=0){
				entity.getOrganizations().add(OrganizationVo.toEntity(null, userVo.getPrimaryDispatchCenterVo(), false));
			}
			userDao.save(entity);
			userDao.flushAndEvict(entity);
			
			entity = userDao.getById(entity.getId(), UserImpl.class);
			
			SystemParameterDao spDao = (SystemParameterDao)super.context.getBean("systemParameterDao");
			SystemParameter spEntity = new SystemParameterImpl();
			spEntity.setParameterName(SystemParameterTypeEnum.SITE_ADMIN_USER.toString());
			spEntity.setParameterValue(entity.getLoginName());
			
			spDao.save(spEntity);
			
			PasswordHistoryDao phDao=(PasswordHistoryDao)context.getBean("passwordHistoryDao");
			PasswordHistory phEntity = new PasswordHistoryImpl();
			phEntity.setUser(entity);
			phEntity.setUserPassword(entity.getPassword());
			phEntity.setUserPasswordCreatedDate(entity.getPasswordCreatedDate());
			
			phDao.save(phEntity);
			
			return UserVo.getInstance(entity, true);
		}catch(Exception e){
			super.handleException(e);
		}

		return null;
	}

	/**
	 * Convenience method for User Use Case items.
	 * Page 41
	 * 
		3.	When a user disables a User Account in the e-ISuite Enterprise System, 
			the system must remove that User Account from any User Groups with which 
			that Account may be associated. 
			If the disabled User Account is subsequently re-enabled, 
			the system must not automatically add that User Account to the User Group 
			from which it was removed.
		4.	When a user disables a User Account in the e-ISuite Enterprise System 
			that was included in an access list for a Restricted Incident, 
			the system must remove that User Account from the Restricted Incident's access list. 
			If the User Account is subsequently re-enabled, 
			the system must not automatically 
			add that User Account to the access list for the Restricted Incident 
			from which it was removed. 
	 * @param userId
	 * @throws ServiceException
	 */
	private void removeUserAssociations(Collection<Long> ids) throws ServiceException {
		try{
			// remove the user from all user groups
			UserGroupUserDao uguDao = (UserGroupUserDao)super.context.getBean("userGroupUserDao");
			uguDao.deleteUsersById(ids);

			// remove the user from all restricted incidents
			RestrictedIncidentUserDao riuDao = (RestrictedIncidentUserDao)context.getBean("restrictedIncidentUserDao");
			riuDao.deleteUsersById(ids);
			
			// remove the user from all shared work areas
			WorkAreaUserDao wauDao = (WorkAreaUserDao)context.getBean("workAreaUserDao");
			wauDao.deleteUserFromSharedWorkAreas(ids);
			//the work area shared out flag needs to be updated after removing users from shared work areas
			WorkAreaDao waDao = (WorkAreaDao)context.getBean("workAreaDao");
			waDao.updateSharedOutFlag();
			
		}catch(Exception e){
			super.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserService#refreshUserSessionVo(java.lang.Long)
	 */
	public UserSessionVo refreshUserSessionVo(Long userId) throws ServiceException {
		try{
			User entity = userDao.getById(userId, UserImpl.class);
			
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"User["+userId+"]");

			((UserSessionVo)context.getBean("userSessionVo")).setEnabled(entity.isEnabled());
			((UserSessionVo)context.getBean("userSessionVo")).setUserRoleVos(SystemRoleVo.getInstances(entity.getSystemRoles(), false));
			
			UserSessionVo usvo = (UserSessionVo)super.context.getBean("userSessionVo");
			
			Collection<String> perms = userDao.getPermissionsForUser(entity.getId());
			
			usvo.setPermissions(perms);
			usvo.setEnabled(entity.isEnabled());
			usvo.setUserRoleVos(SystemRoleVo.getInstances(entity.getSystemRoles(), true));
			
	        return usvo;
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}
	
	
	/* 
	 * Method to return updated list of current work areas to actively verify access to work areas that may have been removed from user
	 * See use case E 7.0005 Validation Requirement 1
	 */
	public Collection<WorkAreaVo> getUpdatedUserWorkAreas(Long userId) throws ServiceException {
		try{
			WorkAreaDao workAreaDao = (WorkAreaDao)super.context.getBean("workAreaDao");
			Collection<WorkArea> userWorkAreas = workAreaDao.getWorkAreasForUser(userId);
						
			if(null != userWorkAreas){
				return WorkAreaVo.getInstances(userWorkAreas, true);
			}
		}catch(Exception e){
			super.handleException(e);
		}
		return null;
	}
	
	private void removeWorkAreaUserAssociations(Collection<WorkAreaUser> waUsersToDelete) throws ServiceException {
		WorkAreaUserDao dao = (WorkAreaUserDao)super.context.getBean("workAreaUserDao");
		for(WorkAreaUser wau : waUsersToDelete) {
			try {
				dao.delete(wau);
			} catch (PersistenceException e) {
				super.handleException(e);
			}
		}
	}
	
	private Boolean determinePrivilegedStatus(Collection<SystemRoleVo> roleVos) {
		for(SystemRoleVo svo : roleVos) {
			if(svo.getPrivilegedRole() == true) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * When a user changes a pdc or additional organization for a user account and  
	 * that user account has a shared or custom work area based on the standard work area
	 * associated with the removed pdc or additional organization, the system must
	 * ensure that all resources and incidents assigned to that standard work area are removed from
	 * the custom or shared work area.
	 */
	private void removeWorkAreaAssociations(Long userId) throws ServiceException {
		WorkAreaDao dao = (WorkAreaDao)super.context.getBean("workAreaDao");
		try {
			dao.removeWorkAreaOrgsNotInUserOrgs(userId);
			dao.removeWorkAreaIncidentsNotInUserOrgs(userId);
			dao.removeWorkAreaResourcesNotInUserOrgs(userId);
		} catch (PersistenceException e) {
			super.handleException(e);
		}
	}
	
	private void endUserSessions(Collection<Long> ids) throws ServiceException  {
	  UserSessionManagementService usm = (UserSessionManagementServiceImpl) super.context.getBean("userSessionManagementService");
	  usm.closeDisabledSessions(ids, super.getUserSessionVo().getUserId());
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
