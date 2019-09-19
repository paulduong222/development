package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserImportFailure;
import gov.nwcg.isuite.core.domain.impl.SystemRoleImpl;
import gov.nwcg.isuite.core.domain.impl.UserImportFailureImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author bsteiner
 *
 */
public class UserImportFailureVo extends AbstractVo implements PersistableVo {

	private String confirmDefaultPassword;
	private String confirmPassword;
	private String defaultPassword;
	private String failureReason;
	private String firstName;
	private OrganizationVo homeUnitVo;
	private String homeUnitCode;
	private String lastName;
	private String loginName;
	private String password;
	private OrganizationVo primaryDispatchCenterVo;
	private String pdcUnitCode;
	private Collection<SystemRoleVo> roleVos = new ArrayList<SystemRoleVo>();
	private Boolean isPrivilegedUser;

	public UserImportFailureVo() {
		
	}

	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static UserImportFailureVo getInstance(UserImportFailure entity, Boolean cascadable) throws Exception {
		UserImportFailureVo vo = new UserImportFailureVo();

		vo.setId(entity.getId());

		if(cascadable){
			vo.setFirstName(entity.getFirstName());
			vo.setLastName(entity.getLastName());
			vo.setLoginName(entity.getLoginName());
			vo.setPassword(entity.getPassword());
			vo.setFailureReason(entity.getFailureReason());
			vo.setHomeUnitCode(entity.getHomeUnitCode());
			vo.setPdcUnitCode(entity.getPdcUnitCode());
			vo.setRoleVos(SystemRoleVo.getInstances(entity.getRoles(), true));
		}

		return vo;
	}

	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<UserImportFailureVo> getIntances(Collection<UserImportFailure> entities, Boolean cascadable) throws Exception {
		Collection<UserImportFailureVo> vos = new ArrayList<UserImportFailureVo>();
		
		for(UserImportFailure entity : entities){
			vos.add(UserImportFailureVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}

	/**
	 * @param vo
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static UserImportFailure toEntity(UserImportFailureVo vo, Boolean cascadable) throws Exception {
		UserImportFailure entity = new UserImportFailureImpl();
		
		return entity;
	}
	
	/**
	 * @param userEntity
	 * @return
	 * @throws Exception
	 */
	public static UserImportFailure toEntity(User userEntity) throws Exception {
		UserImportFailure entity = new UserImportFailureImpl();
		
		entity.setLoginName(userEntity.getLoginName());
		entity.setFirstName(userEntity.getFirstName());
		entity.setLastName(userEntity.getLastName());
		Collection<SystemRole> srs = new ArrayList<SystemRole>();
		for(SystemRole sr : userEntity.getSystemRoles()){
			//System.out.println(sr.getRoleName());
			SystemRole tmp = new SystemRoleImpl();
			tmp.setId(sr.getId());
			srs.add(tmp);
		}
		entity.setRoles(srs);
		//entity.setRoles(userEntity.getSystemRoles());
		entity.setPassword(userEntity.getPassword());
		
		if(null != userEntity.getHomeUnit())
			entity.setHomeUnitCode(userEntity.getHomeUnit().getUnitCode());
		if(null != userEntity.getPrimaryDispatchCenter())
			entity.setPdcUnitCode(userEntity.getPrimaryDispatchCenter().getUnitCode());
		
		return entity;
	}
	
	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @return the failureReason
	 */
	public String getFailureReason() {
		return failureReason;
	}

	/**
	 * @param failureReason the failureReason to set
	 */
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the homeUnitVo
	 */
	public OrganizationVo getHomeUnitVo() {
		return homeUnitVo;
	}

	/**
	 * @param homeUnitVo the homeUnitVo to set
	 */
	public void setHomeUnitVo(OrganizationVo homeUnitVo) {
		this.homeUnitVo = homeUnitVo;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the roleVos
	 */
	public Collection<SystemRoleVo> getRoleVos() {
		return roleVos;
	}

	/**
	 * @param roleVos the roleVos to set
	 */
	public void setRoleVos(Collection<SystemRoleVo> roleVos) {
		this.roleVos = roleVos;
	}

	/**
	 * @return the homeUnitCode
	 */
	public String getHomeUnitCode() {
		return homeUnitCode;
	}

	/**
	 * @param homeUnitCode the homeUnitCode to set
	 */
	public void setHomeUnitCode(String homeUnitCode) {
		this.homeUnitCode = homeUnitCode;
	}

	/**
	 * @return the confirmDefaultPassword
	 */
	public String getConfirmDefaultPassword() {
		return confirmDefaultPassword;
	}

	/**
	 * @param confirmDefaultPassword the confirmDefaultPassword to set
	 */
	public void setConfirmDefaultPassword(String confirmDefaultPassword) {
		this.confirmDefaultPassword = confirmDefaultPassword;
	}

	/**
	 * @return the defaultPassword
	 */
	public String getDefaultPassword() {
		return defaultPassword;
	}

	/**
	 * @param defaultPassword the defaultPassword to set
	 */
	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	/**
	 * @return the primaryDispatchCenterVo
	 */
	public OrganizationVo getPrimaryDispatchCenterVo() {
		return primaryDispatchCenterVo;
	}

	/**
	 * @param primaryDispatchCenterVo the primaryDispatchCenterVo to set
	 */
	public void setPrimaryDispatchCenterVo(OrganizationVo primaryDispatchCenterVo) {
		this.primaryDispatchCenterVo = primaryDispatchCenterVo;
	}

	/**
	 * @return the pdcUnitCode
	 */
	public String getPdcUnitCode() {
		return pdcUnitCode;
	}

	/**
	 * @param pdcUnitCode the pdcUnitCode to set
	 */
	public void setPdcUnitCode(String pdcUnitCode) {
		this.pdcUnitCode = pdcUnitCode;
	}

	/**
	 * @return the isPrivilegedUser
	 */
	public Boolean getIsPrivilegedUser() {
		return isPrivilegedUser;
	}

	/**
	 * @param isPrivilegedUser the isPrivilegedUser to set
	 */
	public void setIsPrivilegedUser(Boolean isPrivilegedUser) {
		this.isPrivilegedUser = isPrivilegedUser;
	}

}
