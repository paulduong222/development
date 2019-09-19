package gov.nwcg.isuite.core.vo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.AuditableVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorType;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;
import gov.nwcg.isuite.framework.util.Validator;
import gov.nwcg.isuite.framework.util.VoValidator;
import gov.nwcg.isuite.xml.UserType;

@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public class UserVo extends AbstractVo implements PersistableVo {
	@JsonIgnore
	public static String DISPLAY_PASSWORD="Ww!2E4@m9y89";
	
	private boolean access = true;
	private String loginName;
	private String firstName;
	private String lastName;
	private Date lastLoginDate;
	private OrganizationVo homeUnitVo;
	private OrganizationVo primaryDispatchCenterVo;
	private String password; // password used to login to the system (this is hashed)
	private String enteredPassword; // password entered when changing password
	private String desiredPassword; // new password desired
	private String confirmPassword; // confirmation of desired password
	private Boolean resetPassword = false;
	private Boolean showDataSavedMsg = true;
	private String email;
	private String eauthId;
	private String workPhone;
	private String cellPhone;
	private Date lockedDate;
	private int failedLoginAttempts;
	private Date accountExpirationDate;
	private Date passwordCreatedDate;
	private Boolean enabled = false;
	
	private Collection<SystemRoleVo> userRoleVos = new ArrayList<SystemRoleVo>();

	private Collection<OrganizationVo> organizationVos = new ArrayList<OrganizationVo>();
	
	private Calendar dateOfLastPasswordChange;

	private Boolean adminUser=false;

	private String dbname;
	private String dbpwd;
	private String dbconfirmpwd;
	private String dbinitial;
	
	public UserVo() {

	}

	public static Collection<UserVo> buildList(String result) throws JsonParseException, JsonMappingException, IOException {
		Collection<UserVo> userVos = new ArrayList<UserVo>();

		StringTokenizer record = new StringTokenizer(result,"|");
		while(record.hasMoreTokens()){
			String userstr=(String)record.nextToken();
			
			UserVo vo = new UserVo();
			
			StringTokenizer attribute = new StringTokenizer(userstr,",");
			while(attribute.hasMoreTokens()){
				String fieldstr=(String)attribute.nextToken();
				StringTokenizer st3 = new StringTokenizer(fieldstr,"=");
				int i=0;
				String field ="";
				String fieldvalue="";
				while(st3.hasMoreTokens()){
					if(i==0)
						field=(String)st3.nextToken();
					else
						fieldvalue=(String)st3.nextToken();
					i++;
				}
				
				if(field.equalsIgnoreCase("ID")){
					Long userid = 0L;
					try{
						userid=TypeConverter.convertToLong(fieldvalue);
					}catch(Exception smother){}
					vo.setId(userid);
				}
				if(field.equalsIgnoreCase("LOGINNAME")){
					vo.setLoginName(fieldvalue);
					userVos.add(vo);
				}
				if(field.equalsIgnoreCase("FIRSTNAME")){
					vo.setFirstName(fieldvalue);
				}
				if(field.equalsIgnoreCase("LASTNAME")){
					vo.setLastName(fieldvalue);
				}
			}
			
		}
		
		return userVos;
	}
	
	/**
	 * Returns a UserVo instance from a User entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of UserVo
	 * @throws Exception
	 */
	public static UserVo getInstance(User entity,boolean cascadable) throws Exception {
		UserVo vo = new UserVo();

		if(null == entity) {
			throw new Exception("Unable to create UserVo from null User entity.");
		}

		vo.setId(entity.getId());

		if(cascadable){
			vo.setLastName(entity.getLastName());
			vo.setFirstName(entity.getFirstName());
			vo.setUserLoginName(entity.getLoginName());
			vo.setLoginName(entity.getLoginName());
			vo.setEnabled(entity.isEnabled());
			vo.setShowDataSavedMsg(entity.getShowDataSavedMsg().getValue());

			if(null != entity.getHomeUnit()){
				vo.setHomeUnitVo(OrganizationVo.getInstance(entity.getHomeUnit(), true));
			}

			if(null != entity.getPrimaryDispatchCenter()){
				vo.setPrimaryDispatchCenterVo(OrganizationVo.getInstance(entity.getPrimaryDispatchCenter(), true));
			}

			vo.setCellPhone(entity.getCellPhone());
			vo.setWorkPhone(entity.getWorkPhone());
			vo.setEauthId(entity.getEauthId());

			vo.setPassword(DISPLAY_PASSWORD);
			vo.setConfirmPassword(DISPLAY_PASSWORD);
			
			vo.setEmail(entity.getEmail());

			if(null != entity.getSystemRoles()){
				boolean privUser=false;
				for(SystemRole v : entity.getSystemRoles()){
					if(BooleanUtility.isTrue(v.getPrivilegedRole()))
						privUser=true;
					/*
					if(v.getRoleName().equals("ROLE_ACCOUNT_MANAGER")
						|| v.getRoleName().equals("ROLE_ACCOUNT_ADMINISTRATOR"))
						privUser=true;
					*/
				}
				vo.setAdminUser(privUser);
				vo.setUserRoleVos(SystemRoleVo.getInstances(entity.getSystemRoles(), true));
			}
			
			if(null != entity.getOrganizations()) {
				vo.setOrganizationVos(OrganizationVo.getInstances(entity.getOrganizations(), true));
			}

			vo.setAuditableVo(new AuditableVo(entity));
			vo.setLastLoginDate(entity.getLastLoginDate());
		}

		return vo;
	}

	public static Collection<UserVo> getInstances(Collection<User> entities, boolean cascadable) throws Exception {
		Collection<UserVo> vos = new ArrayList<UserVo>();

		for(User entity : entities){
			vos.add(UserVo.getInstance(entity, cascadable));
		}
		return vos;
	}

	/**
	 * Returns a User entity from a vo.
	 * 
	 * @param entity
	 * 			user entity if available
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of User entity
	 * @throws Exception
	 */
	public static User toEntity(User entity, UserVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity)
			entity=new UserImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setFirstName(vo.getFirstName().toUpperCase());
			entity.setLastName(vo.getLastName().toUpperCase());
			entity.setLoginName(vo.getLoginName());
			entity.setEnabled(vo.getEnabled());
			entity.setEauthId(vo.getEauthId());
			entity.setEmail(vo.getEmail());
			entity.setCellPhone(vo.getCellPhone());
			entity.setWorkPhone(vo.getWorkPhone());
			entity.setCreatedBy(vo.getCreatedBy());
			entity.setCreatedDate(vo.getCreatedDate());
			entity.setResetPassword((null!=vo.getResetPassword() ? vo.getResetPassword() : false ));
			entity.setShowDataSavedMsg(StringBooleanEnum.toEnumValue(vo.getShowDataSavedMsg()));
			
			if(!LongUtility.hasValue(vo.getId()))
				entity.setShowDataSavedMsg(StringBooleanEnum.Y);
				
			if(vo.getFailedLoginAttempts() > -1) {
				entity.setFailedLoginAttempts(vo.getFailedLoginAttempts());
			} else {
				entity.setFailedLoginAttempts(0);
			}

			if((!StringUtility.hasValue(vo.getPassword())) 
					||
				(!vo.getPassword().equals(DISPLAY_PASSWORD))){
				FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);
				if(StringUtility.hasValue(vo.getPassword())){
					entity.setPassword(new String(enc.encrypt(vo.getPassword().getBytes())));
					Date dt=Calendar.getInstance().getTime();
					dt=DateUtil.addMilitaryTimeToDate(dt, "0001");
					entity.setPasswordCreatedDate(dt);
				}
			}

			if(VoValidator.isValidAbstractVo((AbstractVo)vo.getPrimaryDispatchCenterVo())){
				entity.setPrimaryDispatchCenter(OrganizationVo.toEntity(null, vo.getPrimaryDispatchCenterVo(), false));
			}

			if(VoValidator.isValidAbstractVo((AbstractVo)vo.getHomeUnitVo())){
				entity.setHomeUnit(OrganizationVo.toEntity(null, vo.getHomeUnitVo(), false));
			}
			
			if(null != vo.getOrganizationVos()) {
				entity.setOrganizations(OrganizationVo.toEntityList(vo.getOrganizationVos(), false));
			}

			// check if the homeUnit and pdcUnits are in the organization collection
			boolean hasHomeUnit=false;
			boolean hasPdc=false;
			
			// remove old homeunit / pdc if it has changed
			if(null != entity.getHomeUnit().getId() && entity.getHomeUnit().getId().compareTo(vo.getHomeUnitVo().getId())==0 ){
				// they are the same, do nothing here
			}else{
				Organization removeOrg=null;
				// remove it from the list
				for(Organization orgEntity : entity.getOrganizations()){
					if(orgEntity.getId().compareTo(entity.getHomeUnit().getId())==0){
						removeOrg=orgEntity;
					}
				}
				if(null != removeOrg)
					entity.getOrganizations().remove(removeOrg);
			}
			
			/*
			if(null != entity.getPrimaryDispatchCenter().getId() && entity.getPrimaryDispatchCenter().getId().compareTo(vo.getPrimaryDispatchCenterVo().getId())==0 ){
				// they are the same, do nothing here
			}else{
				Organization removeOrg=null;
				// remove it from the list
				for(Organization orgEntity : entity.getOrganizations()){
					if(orgEntity.getId().compareTo(entity.getPrimaryDispatchCenter().getId())==0){
						removeOrg=orgEntity;
					}
				}
				if(null != removeOrg)
					entity.getOrganizations().remove(removeOrg);
			}		
			*/
			
			
			for(Organization orgEntity : entity.getOrganizations()){
				if(orgEntity.getId().compareTo(vo.getHomeUnitVo().getId())==0)
					hasHomeUnit=true;
				if(orgEntity.getId().compareTo(vo.getPrimaryDispatchCenterVo().getId())==0)
					hasPdc=true;
			}
			if(!hasHomeUnit){
				entity.getOrganizations().add(OrganizationVo.toEntity(null, vo.getHomeUnitVo(), false));
			}
			/*
			if(!hasPdc){
				if(null != vo.getPrimaryDispatchCenterVo().getId()){
					if(vo.getHomeUnitVo().getId().compareTo(vo.getPrimaryDispatchCenterVo().getId())!=0)
						entity.getOrganizations().add(OrganizationVo.toEntity(null, vo.getPrimaryDispatchCenterVo(), false));
				}
			}
			*/
			if(null != vo.getUserRoleVos()){
				entity.setSystemRoles(SystemRoleVo.toEntityList(vo.getUserRoleVos(),true));
			}

//			UserGroupUser uguEntity = (UserGroupUser)getPersistableObject(persistables,UserGroupUserImpl.class);
//			if(null != uguEntity)
//				entity.getUserGroupUsers().add(uguEntity);
			
//			IncidentGroupUser iguEntity = (IncidentGroupUser)getPersistableObject(persistables,IncidentGroupUserImpl.class);
//			if(null != iguEntity)
//				entity.getIncidentGroupUsers().add(iguEntity);
			
			validateEntity(entity);

		}

		return entity;
	}

	public static Collection<User> toEntityList(Collection<UserVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		
		return null;
	}
	
	/**
	 * Perform some validation on the user entity field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source user entity
	 * @throws ValidationException
	 */
	private static void validateEntity(User entity) throws ValidationException {
		Validator.validateBooleanField("enabled", entity.isEnabled(),true);
		Validator.validateStringField("firstName", entity.getFirstName(), 30, false);
		Validator.validateStringField("lastName", entity.getLastName(), 35, false);
		Validator.validateStringField("loginName", entity.getLoginName(), 255, false);
		Validator.validateIntegerField("failedLoginAttempts", entity.getFailedLoginAttempts(), true);
	}

	public static Boolean isPrivilegedUser(UserVo vo){
		Boolean rtn=false;
		
		for (SystemRoleVo srvo : vo.getUserRoleVos()){
			if(BooleanUtility.isTrue(srvo.getPrivilegedRole())){
				rtn=true;
			}
		}
		return rtn;
	}
	
	/**
	 * Converts an user entity into a UserType xml object.
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static UserType toXmlObject(User entity) throws Exception {
		UserType xmlObject = new UserType();
		
		xmlObject.setLoginName(entity.getLoginName());
		xmlObject.setFirstName(entity.getFirstName());
		xmlObject.setLastName(entity.getLastName());
		xmlObject.setWorkPhone(entity.getWorkPhone());
		xmlObject.setCellPhone(entity.getCellPhone());
		xmlObject.setEmail(entity.getEmail());
		if(null != entity.getHomeUnitId()) {
			xmlObject.setHomeUnitCode(entity.getHomeUnit().getUnitCode());
		}
		if(null != entity.getPrimaryDispatchCenterId()) {
			xmlObject.setPrimaryDispatchCenterCode(entity.getPrimaryDispatchCenter().getUnitCode());
		}
		if(null != entity.getSystemRoles()){
			for(SystemRole srEntity : entity.getSystemRoles()){
				xmlObject.getRole().add(SystemRoleVo.toXmlObject(srEntity));
			}
		}
		return xmlObject;
	}

	/**
	 * Convert a xml object User Type into a user entity.
	 * 
	 * @param userType
	 * @return
	 * @throws Exception
	 */
	public static User toEntity(UserType userType) throws Exception {
		User entity = new UserImpl();
		
		entity.setLoginName(userType.getLoginName());
		entity.setFirstName(userType.getFirstName());
		entity.setLastName(userType.getLastName());
		entity.setCellPhone(userType.getCellPhone());
		entity.setWorkPhone(userType.getWorkPhone());
		entity.setEmail(userType.getEmail());
		entity.setEnabled(Boolean.TRUE);
		entity.setSystemRoles(SystemRoleVo.toEntityList(userType.getRole()));

		Organization orgEntity = new OrganizationImpl();
		if(userType.getHomeUnitCode() != null && !userType.getHomeUnitCode().isEmpty()){
			orgEntity.setUnitCode(userType.getHomeUnitCode());
			entity.setHomeUnit(orgEntity);
		}
		Organization orgEntityPdc = new OrganizationImpl();
		if(userType.getPrimaryDispatchCenterCode() != null && !userType.getPrimaryDispatchCenterCode().isEmpty()) {
			// orgEntityPdc.setUnitCode(userType.getPrimaryDispatchCenterCode());
			// entity.setPrimaryDispatchCenter(orgEntityPdc);
		}
		return entity;
	}
	
	/**
	 * @param userTypes
	 * @return
	 * @throws Exception
	 */
	public static Collection<User> toEntityList(Collection<UserType> userTypes) throws Exception {
		Collection<User> entities = new ArrayList<User>();
		
		for(UserType userType : userTypes){
			entities.add(UserVo.toEntity(userType));
		}
		
		return entities;
	}

	/**
	 * @return the access
	 */
	@JsonIgnore
	 public boolean isAccess() {
		 return access;
	 }

	 /**
	  * @param access the access to set
	  */
	@JsonIgnore
	 public void setAccess(boolean access) {
		 this.access = access;
	 }

	 /**
	  * @return
	  */
	 public OrganizationVo getHomeUnitVo() {
		 if(null==homeUnitVo)
			 homeUnitVo=new OrganizationVo();
		 return homeUnitVo;
	 }

	 /**
	  * @param homeUnitVo
	  */
	 public void setHomeUnitVo(OrganizationVo homeUnitVo) {
		 this.homeUnitVo = homeUnitVo;
	 }

	 /**
	  * @return
	  */
	 public OrganizationVo getPrimaryDispatchCenterVo() {
		 if(null==primaryDispatchCenterVo)
			 primaryDispatchCenterVo=new OrganizationVo();
		 return primaryDispatchCenterVo;
	 }

	 /**
	  * @param primaryDispatchCenterVo
	  */
	 public void setPrimaryDispatchCenterVo(OrganizationVo primaryDispatchCenterVo) {
		 this.primaryDispatchCenterVo = primaryDispatchCenterVo;
	 }

	 /**
	  * Convenience method for transforming.
	  * 
	  * @param primaryDispatchCenter
	  */
	 @JsonIgnore
	 public void setPrimaryDispatchCenter(Organization primaryDispatchCenter){
		 this.primaryDispatchCenterVo = new OrganizationVo(primaryDispatchCenter);
	 }

	 /**
	  * Returns the userRoleVos.
	  *
	  * @return 
	  *		the userRoleVos to return
	  */
	 public Collection<SystemRoleVo> getUserRoleVos() {
		 return userRoleVos;
	 }

	 /**
	  * Sets the userRoleVos.
	  *
	  * @param userRoleVos 
	  *			the userRoleVos to set
	  */
	 public void setUserRoleVos(Collection<SystemRoleVo> userRoleVos) {
		 this.userRoleVos = userRoleVos;
	 }

	/**
	 * Returns the organizationVos.
	 *
	 * @return 
	 *		the organizationVos to return
	 */
	 @JsonIgnore
	public Collection<OrganizationVo> getOrganizationVos() {
		return organizationVos;
	}

	/**
	 * Sets the organizationVos.
	 *
	 * @param organizationVos 
	 *			the organizationVos to set
	 */
	 @JsonIgnore
	public void setOrganizationVos(Collection<OrganizationVo> organizationVos) {
		this.organizationVos = organizationVos;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnteredPassword() {
		return enteredPassword;
	}

	public void setEnteredPassword(String enteredPassword) {
		this.enteredPassword = enteredPassword;
	}

	public String getDesiredPassword() {
		return desiredPassword;
	}

	public void setDesiredPassword(String desiredPassword) {
		this.desiredPassword = desiredPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Boolean getResetPassword() {
		return resetPassword;
	}

	public void setResetPassword(Boolean resetPassword) {
		this.resetPassword = resetPassword;
	}

	public Boolean getShowDataSavedMsg() {
		return showDataSavedMsg;
	}

	public void setShowDataSavedMsg(Boolean showDataSavedMsg) {
		this.showDataSavedMsg = showDataSavedMsg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEauthId() {
		return eauthId;
	}

	public void setEauthId(String eauthId) {
		this.eauthId = eauthId;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public int getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(int failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public Date getAccountExpirationDate() {
		return accountExpirationDate;
	}

	public void setAccountExpirationDate(Date accountExpirationDate) {
		this.accountExpirationDate = accountExpirationDate;
	}

	public Date getPasswordCreatedDate() {
		return passwordCreatedDate;
	}

	public void setPasswordCreatedDate(Date passwordCreatedDate) {
		this.passwordCreatedDate = passwordCreatedDate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Calendar getDateOfLastPasswordChange() {
		return dateOfLastPasswordChange;
	}

	public void setDateOfLastPasswordChange(Calendar dateOfLastPasswordChange) {
		this.dateOfLastPasswordChange = dateOfLastPasswordChange;
	}

	public Boolean getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(Boolean adminUser) {
		this.adminUser = adminUser;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getDbpwd() {
		return dbpwd;
	}

	public void setDbpwd(String dbpwd) {
		this.dbpwd = dbpwd;
	}

	public String getDbconfirmpwd() {
		return dbconfirmpwd;
	}

	public void setDbconfirmpwd(String dbconfirmpwd) {
		this.dbconfirmpwd = dbconfirmpwd;
	}

	public String getDbinitial() {
		return dbinitial;
	}

	public void setDbinitial(String dbinitial) {
		this.dbinitial = dbinitial;
	}


}
