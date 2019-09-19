package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.GridColumnUser;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.GridNameEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;


@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class UserSessionVo extends AbstractVo{
	private String clientIp;
	private String clientLocalDate;
	private String clientLocalHour;
	private String clientToServerHourDifference;
	private Long userId;
	private String firstName;
	private String lastName;
	private String homeUnitCode;
	private String pdcUnitCode;
	private String workPhone;
	private String cellPhone;
	private String email;
	private Boolean enabled;
	private String lastLoginDate;
	private String passwordExpireDate;
	private OrganizationVo dispatchCenterVo;
	private Boolean showDataSavedMsg;

	private String siteDatabaseName;
	private String siteDatasourceName;
	private String siteForceMaster;

	private Boolean localHostLogin=false;
	
	@JsonIgnore
	private Collection<OrganizationVo> userOrganizationVos=new ArrayList<OrganizationVo>();

	/*
	 * User's Current WorkArea
	 */
	@JsonIgnore
	private WorkAreaVo currentWorkAreaVo=null;

	/*
	 * User's Work Area Vo's
	 */
	@JsonIgnore
	private Collection<WorkAreaVo> workAreaVos=null;

	/*
	 * User's grid column definitions
	 */
	@JsonIgnore
	private Collection<GridColumnUserVo> gridResourceColumns=null;
	@JsonIgnore
	private Collection<GridColumnUserVo> gridOtherCostColumns=null;
	@JsonIgnore
	private Collection<GridColumnUserVo> gridResourceCostColumns=null;
	@JsonIgnore
	private Collection<GridColumnUserVo> gridResourceDemobColumns=null;
	@JsonIgnore
	private Collection<GridColumnUserVo> gridResourceTimeColumns=null;
	@JsonIgnore
	private Collection<GridColumnUserVo> gridWorkAreaResourceColumns=null;

	/*
	 * User's permissions based on user's roles
	 */
	@JsonIgnore
	private Collection<String> permissions = new ArrayList<String>();
	@JsonIgnore
	private Collection<String> secondaryPermissions = new ArrayList<String>();

	private Collection<SystemRoleVo> userRoleVos=null;

	@JsonIgnore
	private Collection<String> notifications=new ArrayList<String>();

	private Boolean robAgreementValid=true;
	private Boolean privilegedUser=false;
	private Boolean fsUser=false;

	public UserSessionVo(){

	}

	public static UserSessionVo getInstance(User entity) throws Exception {
		UserSessionVo vo = new UserSessionVo();

		if(null==entity)
			throw new Exception("Unable to create UserSessionVo from null User entity.");

		vo.setFirstName(entity.getFirstName().toUpperCase());
		vo.setLastName(entity.getLastName().toUpperCase());
		vo.setUserId(entity.getId());
		vo.setUserLoginName(entity.getLoginName());
		vo.setWorkPhone(entity.getWorkPhone());
		vo.setCellPhone(entity.getCellPhone());
		vo.setEmail(entity.getEmail());
		vo.setEnabled(entity.isEnabled());
		vo.setShowDataSavedMsg(entity.getShowDataSavedMsg().getValue());
		// check for null date, first time a user logs in, there is no lastlogindate
		if(DateUtil.hasValue(entity.getLastLoginDate())){
			vo.setLastLoginDate(DateUtil.toDateString(entity.getLastLoginDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM));
		}

		if(DateUtil.hasValue(entity.getAccountExpirationDate())){
			vo.setPasswordExpireDate(DateUtil.toDateString(entity.getAccountExpirationDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM));
		}

		if(null != entity.getHomeUnit()){
			vo.setHomeUnitCode(entity.getHomeUnit().getUnitCode());
		}

		if(null != entity.getPrimaryDispatchCenter()){
			vo.setPdcUnitCode(entity.getPrimaryDispatchCenter().getUnitCode());
			vo.setDispatchCenterVo(OrganizationVo.getInstance(entity.getPrimaryDispatchCenter(), false));
		}

		if(null!=entity.getGridColumnsUser()){
			System.out.println(entity.getGridColumnsUser().size());
			for(GridColumnUser gcUser : entity.getGridColumnsUser()){
				if(null!=gcUser.getGridColumn())
				{
					//System.out.println(gcUser.getGridColumn().getGridName() + " " + gcUser.getGridColumn().getColumnName());
					if(gcUser.getGridColumn().getGridName().equals(GridNameEnum.RESOURCE))
						vo.getGridResourceColumns().add(GridColumnUserVo.getInstance(gcUser, true));
					else if(gcUser.getGridColumn().getGridName().equals(GridNameEnum.RESOURCECOST))
						vo.getGridResourceCostColumns().add(GridColumnUserVo.getInstance(gcUser, true));
					else if(gcUser.getGridColumn().getGridName().equals(GridNameEnum.OTHERCOST))
						vo.getGridOtherCostColumns().add(GridColumnUserVo.getInstance(gcUser, true));
					else if(gcUser.getGridColumn().getGridName().equals(GridNameEnum.RESOURCEDEMOB))
						vo.getGridResourceDemobColumns().add(GridColumnUserVo.getInstance(gcUser, true));
					else if(gcUser.getGridColumn().getGridName().equals(GridNameEnum.RESOURCETIME))
						vo.getGridResourceTimeColumns().add(GridColumnUserVo.getInstance(gcUser, true));
					else if(gcUser.getGridColumn().getGridName().equals(GridNameEnum.WORKAREARESOURCE))
						vo.getGridWorkAreaResourceColumns().add(GridColumnUserVo.getInstance(gcUser, true));

				}
			}
		}

		if(null != entity.getSystemRoles()){
			vo.setUserRoleVos(SystemRoleVo.getInstances(entity.getSystemRoles(), true));
			for(SystemRoleVo srvo : vo.getUserRoleVos()){
				if(srvo.getPrivilegedRole())
					vo.setPrivilegedUser(true);
			}
		}

		if(null != entity.getOrganizations()){
			vo.setUserOrganizationVos(OrganizationVo.getInstances(entity.getOrganizations(), true));
		}

		return vo;
	}

	/**
	 * Returns the logged-in user's first name.
	 * 
	 * @return
	 * 		the logged-in user's first name to return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the logged-in user's first name.
	 * 
	 * @param firstName
	 * 			the logged-in user's first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Return the logged-in user's last name.
	 * 
	 * @return
	 * 		the logged-in user's last name to return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the logged-in user's last name.
	 * 
	 * @param lastName
	 * 			the logged-in user's last name to return
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the logged-in user's id.
	 * 
	 * @return 
	 * 		the logged-in user's id to return
	 * 				
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the logged-in user's id.
	 * 
	 * @param userId 
	 * 			the logged-in user's id to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Collection<GridColumnUserVo> getGridResourceColumns() {
		if(null==gridResourceColumns)
			gridResourceColumns=new ArrayList<GridColumnUserVo>();
		return gridResourceColumns;
	}

	@SuppressWarnings("unchecked")
	public void setGridResourceColumns(Collection<GridColumnUserVo> gridResourceColumns) {
		this.gridResourceColumns = gridResourceColumns;
		Collections.sort((List)gridResourceColumns,new PositionComparator());
	}

	public Collection<GridColumnUserVo> getGridOtherCostColumns() {
		if(null==gridOtherCostColumns)
			gridOtherCostColumns=new ArrayList<GridColumnUserVo>();
		return gridOtherCostColumns;
	}

	@SuppressWarnings("unchecked")
	public void setGridOtherCostColumns(Collection<GridColumnUserVo> gridOtherCostColumns) {
		this.gridOtherCostColumns = gridOtherCostColumns;
		Collections.sort((List)gridOtherCostColumns,new PositionComparator());
	}

	public Collection<GridColumnUserVo> getGridResourceCostColumns() {
		if(null==gridResourceCostColumns)
			gridResourceCostColumns=new ArrayList<GridColumnUserVo>();
		return gridResourceCostColumns;
	}

	@SuppressWarnings("unchecked")
	public void setGridResourceCostColumns(Collection<GridColumnUserVo> gridResourceCostColumns) {
		this.gridResourceCostColumns = gridResourceCostColumns;
		Collections.sort((List)gridResourceCostColumns,new PositionComparator());
	}

	public Collection<GridColumnUserVo> getGridResourceDemobColumns() {
		if(null==gridResourceDemobColumns)
			gridResourceDemobColumns=new ArrayList<GridColumnUserVo>();
		return gridResourceDemobColumns;
	}

	@SuppressWarnings("unchecked")
	public void setGridResourceDemobColumns(Collection<GridColumnUserVo> gridResourceDemobColumns) {
		this.gridResourceDemobColumns = gridResourceDemobColumns;
		Collections.sort((List)gridResourceDemobColumns,new PositionComparator());
	}

	public Collection<GridColumnUserVo> getGridWorkAreaResourceColumns() {
		if(null==gridWorkAreaResourceColumns)
			gridWorkAreaResourceColumns=new ArrayList<GridColumnUserVo>();
		return gridWorkAreaResourceColumns;
	}

	@SuppressWarnings("unchecked")
	public void setGridWorkAreaResourceColumns(Collection<GridColumnUserVo> gridWorkAreaResourceColumns) {
		this.gridWorkAreaResourceColumns = gridWorkAreaResourceColumns;
		Collections.sort((List)gridWorkAreaResourceColumns,new PositionComparator());
	}

	/**
	 * @return the gridResourceTimeColumns
	 */
	public Collection<GridColumnUserVo> getGridResourceTimeColumns() {
		if(null == this.gridResourceTimeColumns) {
			this.gridResourceTimeColumns = new ArrayList<GridColumnUserVo>();
		}
		return this.gridResourceTimeColumns;
	}

	/**
	 * @param gridResourceTimeColumns the gridResourceTimeColumns to set
	 */
	@SuppressWarnings("unchecked")
	public void setGridResourceTimeColumns(Collection<GridColumnUserVo> gridResourceTimeColumns) {
		this.gridResourceTimeColumns = gridResourceTimeColumns;
		Collections.sort((List)gridResourceTimeColumns, new PositionComparator());
	}

	/**
	 * Returns the currentWorkAreaVo.
	 *
	 * @return 
	 *		the currentWorkAreaVo to return
	 */
	public WorkAreaVo getCurrentWorkAreaVo() {
		return currentWorkAreaVo;
	}

	/**
	 * Sets the currentWorkAreaVo.
	 *
	 * @param currentWorkAreaVo 
	 *			the currentWorkAreaVo to set
	 */
	public void setCurrentWorkAreaVo(WorkAreaVo currentWorkAreaVo) {
		this.currentWorkAreaVo = currentWorkAreaVo;
	}

	/**
	 * Returns the workAreaVos.
	 *
	 * @return 
	 *		the workAreaVos to return
	 */
	public Collection<WorkAreaVo> getWorkAreaVos() {
		if(null == workAreaVos)
			workAreaVos = new ArrayList<WorkAreaVo>();

		return workAreaVos;
	}

	/**
	 * Sets the workAreaVos.
	 *
	 * @param workAreaVos 
	 *			the workAreaVos to set
	 */
	public void setWorkAreaVos(Collection<WorkAreaVo> workAreaVos) {
		this.workAreaVos = workAreaVos;
	}

	class PositionComparator implements Comparator{

		public int compare(Object vo1, Object vo2){

			Integer vo1Position = ( (GridColumnUserVo) vo1).getPosition();
			Integer vo2Position = ( (GridColumnUserVo) vo2).getPosition();

			return vo1Position.compareTo(vo2Position);
		}
	}

	/**
	 * Returns the userOrganizationVos.
	 *
	 * @return 
	 *		the userOrganizationVos to return
	 */
	public Collection<OrganizationVo> getUserOrganizationVos() {
		return userOrganizationVos;
	}

	/**
	 * Sets the userOrganizationVos.
	 *
	 * @param userOrganizationVos 
	 *			the userOrganizationVos to set
	 */
	public void setUserOrganizationVos(
			Collection<OrganizationVo> userOrganizationVos) {
		this.userOrganizationVos = userOrganizationVos;
	}

	/**
	 * Returns the permissions.
	 *
	 * @return 
	 *		the permissions to return
	 */
	public Collection<String> getPermissions() {
		return permissions;
	}

	/**
	 * Sets the permissions.
	 *
	 * @param permissions 
	 *			the permissions to set
	 */
	public void setPermissions(Collection<String> permissions) {
		this.permissions = permissions;
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
	 * Returns the notifications.
	 *
	 * @return 
	 *		the notifications to return
	 */
	public Collection<String> getNotifications() {
		return notifications;
	}

	/**
	 * Sets the notifications.
	 *
	 * @param notifications 
	 *			the notifications to set
	 */
	public void setNotifications(Collection<String> notifications) {
		this.notifications = notifications;
	}

	/**
	 * Returns the robAgreementValid.
	 *
	 * @return 
	 *		the robAgreementValid to return
	 */
	public Boolean getRobAgreementValid() {
		return robAgreementValid;
	}

	/**
	 * Sets the robAgreementValid.
	 *
	 * @param robAgreementValid 
	 *			the robAgreementValid to set
	 */
	public void setRobAgreementValid(Boolean robAgreementValid) {
		this.robAgreementValid = robAgreementValid;
	}

	/**
	 * Returns the privilegedUser.
	 *
	 * @return 
	 *		the privilegedUser to return
	 */
	public Boolean getPrivilegedUser() {
		return privilegedUser;
	}

	/**
	 * Sets the privilegedUser.
	 *
	 * @param privilegedUser 
	 *			the privilegedUser to set
	 */
	public void setPrivilegedUser(Boolean privilegedUser) {
		this.privilegedUser = privilegedUser;
	}

	/**
	 * Returns the fsUser.
	 *
	 * @return 
	 *		the fsUser to return
	 */
	public Boolean getFsUser() {
		return fsUser;
	}

	/**
	 * Sets the fsUser.
	 *
	 * @param fsUser 
	 *			the fsUser to set
	 */
	public void setFsUser(Boolean fsUser) {
		this.fsUser = fsUser;
	}

	public String getHomeUnitCode() {
		return homeUnitCode;
	}

	public void setHomeUnitCode(String homeUnitCode) {
		this.homeUnitCode = homeUnitCode;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPdcUnitCode() {
		return pdcUnitCode;
	}

	public void setPdcUnitCode(String pdcUnitCode) {
		this.pdcUnitCode = pdcUnitCode;
	}

	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled 
	 *			the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the secondaryPermissions
	 */
	public Collection<String> getSecondaryPermissions() {
		return secondaryPermissions;
	}

	/**
	 * @param secondaryPermissions the secondaryPermissions to set
	 */
	public void setSecondaryPermissions(Collection<String> secondaryPermissions) {
		this.secondaryPermissions = secondaryPermissions;
	}

	/**
	 * @return the lastLoginDate
	 */
	public String getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate the lastLoginDate to set
	 */
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the passwordExpireDate
	 */
	public String getPasswordExpireDate() {
		return passwordExpireDate;
	}

	/**
	 * @param passwordExpireDate the passwordExpireDate to set
	 */
	public void setPasswordExpireDate(String passwordExpireDate) {
		this.passwordExpireDate = passwordExpireDate;
	}

	/**
	 * @param dispatchCenter the dispatchCenter to set
	 */
	public void setDispatchCenterVo(OrganizationVo dispatchCenter) {
		this.dispatchCenterVo = dispatchCenter;
	}

	/**
	 * @return the dispatchCenter
	 */
	public OrganizationVo getDispatchCenterVo() {
		return dispatchCenterVo;
	}

	public Boolean getShowDataSavedMsg() {
		return this.showDataSavedMsg;
	}

	public void setShowDataSavedMsg(Boolean showDataSavedMsg) {
		this.showDataSavedMsg = showDataSavedMsg;
	}

	/**
	 * @return the siteDatabaseName
	 */
	public String getSiteDatabaseName() {
		return siteDatabaseName;
	}

	/**
	 * @param siteDatabaseName the siteDatabaseName to set
	 */
	public void setSiteDatabaseName(String siteDatabaseName) {
		this.siteDatabaseName = siteDatabaseName;
	}

	/**
	 * @return the siteDatasourceName
	 */
	public String getSiteDatasourceName() {
		return siteDatasourceName;
	}

	/**
	 * @param siteDatasourceName the siteDatasourceName to set
	 */
	public void setSiteDatasourceName(String siteDatasourceName) {
		this.siteDatasourceName = siteDatasourceName;
	}

	/**
	 * @return the clientIp
	 */
	public String getClientIp() {
		return clientIp;
	}

	/**
	 * @param clientIp the clientIp to set
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * @return the clientLocalDate
	 */
	public String getClientLocalDate() {
		return clientLocalDate;
	}

	/**
	 * @param clientLocalDate the clientLocalDate to set
	 */
	public void setClientLocalDate(String clientLocalDate) {
		this.clientLocalDate = clientLocalDate;
	}

	/**
	 * @return the clientLocalHour
	 */
	public String getClientLocalHour() {
		return clientLocalHour;
	}

	/**
	 * @param clientLocalHour the clientLocalHour to set
	 */
	public void setClientLocalHour(String clientLocalHour) {
		this.clientLocalHour = clientLocalHour;
	}

	/**
	 * @return the siteForceMaster
	 */
	public String getSiteForceMaster() {
		return siteForceMaster;
	}

	/**
	 * @param siteForceMaster the siteForceMaster to set
	 */
	public void setSiteForceMaster(String siteForceMaster) {
		this.siteForceMaster = siteForceMaster;
	}

	/**
	 * @return the localHostLogin
	 */
	public Boolean getLocalHostLogin() {
		return localHostLogin;
	}

	/**
	 * @param localHostLogin the localHostLogin to set
	 */
	public void setLocalHostLogin(Boolean localHostLogin) {
		this.localHostLogin = localHostLogin;
	}

	public String getClientToServerHourDifference() {
		return clientToServerHourDifference;
	}

	public void setClientToServerHourDifference(String clientToServerHourDifference) {
		this.clientToServerHourDifference = clientToServerHourDifference;
	}


}
