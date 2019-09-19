package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class SystemNotifyUserVo extends AbstractVo {
	private Long userId;
	private Long systemNotificationId;

	public SystemNotifyUserVo(){
		
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the systemNotificationId
	 */
	public Long getSystemNotificationId() {
		return systemNotificationId;
	}

	/**
	 * @param systemNotificationId the systemNotificationId to set
	 */
	public void setSystemNotificationId(Long systemNotificationId) {
		this.systemNotificationId = systemNotificationId;
	}
}
