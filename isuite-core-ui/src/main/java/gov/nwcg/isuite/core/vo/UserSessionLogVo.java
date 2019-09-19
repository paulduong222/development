package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserSessionLog;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.domain.impl.UserSessionLogImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.UserSessionActionCauseEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserSessionLogVo extends AbstractVo {
	private String sessionId;
	private String ipAddress;
	private Date lastStatusCheck;
	private UserVo userVo;
	private String firstName;
	private String lastName;
	private String unitCode;
	private Long userId;
	private Collection<UserSessionLogActivityVo> userSessionLogActivityVos = new ArrayList<UserSessionLogActivityVo>();
	private String databaseName;
	private String lastModifiedBy;
	private String createdBy;

	public UserSessionLogVo() {

	}

	/**
	 * Create a UserSessionLogVo as a result of a user initiated action
	 * 
	 * @param actionType
	 * @param actionCause
	 * @param adminUser
	 */
	public void createUserSessionLogActivityVo(UserSessionActionTypeEnum actionType,
			UserSessionActionCauseEnum actionCause,
			UserVo adminUser) 
	{
		UserSessionLogActivityVo uslav = new UserSessionLogActivityVo(actionType, actionCause, adminUser);	 

		if(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
				.getRequest().getUserPrincipal() != null
				&& ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
				.getRequest().getUserPrincipal().getName() != null) 
		{
			uslav.setCreatedBy(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
					.getRequest().getUserPrincipal().getName());
		} 
		uslav.setCreatedDate(new Date());

		//getUslavs().add(uslav);
	}

	/**
	 * Create a UserSessionLogVo as a result of a scheduled task initiated action
	 * 
	 * @param actionType
	 * @param actionCause	 * 
	 * @param adminUser
	 * @param creator
	 */
	public void createUserSessionLogActivityVo(UserSessionActionTypeEnum actionType,
			UserSessionActionCauseEnum actionCause,
			UserVo adminUser,
			String creator) {
		UserSessionLogActivityVo uslav = new UserSessionLogActivityVo(actionType, actionCause, null);

		uslav.setCreatedBy(creator);
		uslav.setCreatedDate(new Date());

		uslav.setModifier(creator);

		//getUslavs().add(uslav);
	}


	/**
	 * Creates and returns a UserSessionLog entity from a UserSessionLogVo.
	 * 
	 * @param entity 
	 *    a Report entity (can be null)
	 * @param sourceVo
	 *    the source ReportVo
	 * @param cascadable
	 *    flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 *    the Report entity
	 * @throws Exception
	 */
	public static UserSessionLog toEntity(UserSessionLog entity,UserSessionLogVo sourceVo,boolean cascadable) throws Exception {
		if(entity == null)
			entity = new UserSessionLogImpl();

		entity.setId(sourceVo.getId());

		if(cascadable){
			entity.setIpAddress(sourceVo.getIpAddress());
			entity.setSessionId(sourceVo.getSessionId());
			entity.setLastStatusCheckDate(sourceVo.getLastStatusCheck());
			entity.setCreatedBy(sourceVo.getCreatedBy());
			entity.setCreatedDate(sourceVo.getCreatedDate());
			entity.setLastModifiedBy(sourceVo.getLastModifiedBy());
			entity.setLastModifiedDate(new Date());

			if(null != sourceVo.getUserVo() && LongUtility.hasValue(sourceVo.getUserVo().getId())){
				User user = new UserImpl();
				user.setId(sourceVo.getUserVo().getId());
				entity.setUser(user);
			}else
				entity.setUser(null);

			if(CollectionUtility.hasValue(sourceVo.getUserSessionLogActivityVos())){
				for(UserSessionLogActivityVo uslav : sourceVo.getUserSessionLogActivityVos()) {
					try {
						entity.getUserSessionLogActivities().add(UserSessionLogActivityVo.toEntity(null,uslav,true,entity));
					} catch (Exception e) {
						//TODO: handle exception
					}
				}
			}
		}
		
		return entity;
	}

	private Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the lastStatusCheck
	 */
	public Date getLastStatusCheck() {
		return lastStatusCheck;
	}

	/**
	 * @param lastStatusCheck the lastStatusCheck to set
	 */
	public void setLastStatusCheck(Date lastStatusCheck) {
		this.lastStatusCheck = lastStatusCheck;
	}

	/**
	 * @return the userVo
	 */
	public UserVo getUserVo() {
		return userVo;
	}

	/**
	 * @param userVo the userVo to set
	 */
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}


	public void setUserSessionLogActivityVos(Collection<UserSessionLogActivityVo> userSessionLogActivityVos) {
		this.userSessionLogActivityVos = userSessionLogActivityVos;
	}

	public Collection<UserSessionLogActivityVo> getUserSessionLogActivityVos() {
		return userSessionLogActivityVos;
	}

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param databaseName the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
}
