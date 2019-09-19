package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.UserSessionLog;
import gov.nwcg.isuite.core.domain.UserSessionLogActivity;
import gov.nwcg.isuite.core.domain.impl.UserSessionLogActivityImpl;
import gov.nwcg.isuite.core.domain.impl.UserSessionLogImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.UserSessionActionCauseEnum;
import gov.nwcg.isuite.framework.types.UserSessionActionTypeEnum;

public class UserSessionLogActivityVo extends AbstractVo {

	private UserSessionActionTypeEnum actionType; 
	private UserSessionActionCauseEnum actionCause;
	private UserVo adminUserVo;
	private Long adminUserId;
	private String modifier = null;

	public UserSessionLogActivityVo() {
	}

	public UserSessionLogActivityVo(UserSessionActionTypeEnum actionType,
			UserSessionActionCauseEnum actionCause,
			UserVo adminUserVo) {
		this.actionType = actionType;
		this.actionCause = actionCause;
		if(adminUserVo != null) {
			this.adminUserVo = adminUserVo;
			this.adminUserId = adminUserVo.getId();
		}
	}

	public static UserSessionLogActivityVo getInstance(UserSessionLogActivity usla) {
		return null;
	}

	public static UserSessionLogActivity toEntity(UserSessionLogActivity entity, UserSessionLogActivityVo sourceVo, boolean cascadable, Persistable...persistables) throws Exception 
	{
		if(entity == null)
			entity = new UserSessionLogActivityImpl();

		entity.setId(sourceVo.getId());

		if(cascadable){
			entity.setActionCause(sourceVo.getActionCause());
			entity.setActionType(sourceVo.getActionType());

			UserSessionLog usl = (UserSessionLog)getPersistableObject(persistables,UserSessionLogImpl.class);
			if(null != usl)
				entity.setUserSessionLog(usl);

			entity.setCreatedBy(sourceVo.getCreatedBy());
			entity.setCreatedDate(sourceVo.getCreatedDate());
			
		}

		return entity;
	}



	/**
	 * @return the actionType
	 */
	public UserSessionActionTypeEnum getActionType() {
		return actionType;
	}

	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(UserSessionActionTypeEnum actionType) {
		this.actionType = actionType;
	}

	/**
	 * @return the actionCause
	 */
	public UserSessionActionCauseEnum getActionCause() {
		return actionCause;
	}

	/**
	 * @param actionCause the actionCause to set
	 */
	public void setActionCause(UserSessionActionCauseEnum actionCause) {
		this.actionCause = actionCause;
	}

	/**
	 * @param overrideUser
	 */
	public void setAdminUserVo(UserVo adminUserVo) {
		this.adminUserVo = adminUserVo;
	}

	/**
	 * @return
	 */
	public UserVo getAdminUserVo() {
		return adminUserVo;
	}

	public void setAdminUserId(Long adminUserId) {
		this.adminUserId = adminUserId;
	}

	public Long getAdminUserId() {
		return adminUserId;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifier() {
		return modifier;
	}
}
