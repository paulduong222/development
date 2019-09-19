package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;

import java.util.HashMap;
import java.util.Map;

public class SystemVo {
	private String runMode="";
	private Integer sessionTimeout;
	private Integer sessionTimeoutTick;
	private Integer sessionTimeoutWarning;
	private Integer userSessionPingInterval;
	private Boolean autoPopulateGrids;
	
	private Map<String, String> systemParameters = new HashMap<String, String>();

	public SystemVo(){
		this.systemParameters.put("RUN_MODE","SITE");
	}
	
	/**
	 * Add a system parameter key-value pair to a map of parameters
	 * 
	 * @param key
	 * @param value
	 */
	public void setParameter(String key, String value) {
		systemParameters.put(key, value);
	}

	/**
	 * Return the String value of a system parameter
	 * 
	 * @param key
	 * @return String value
	 */
	public String getParameter(String key) {
		return systemParameters.get(key);
	}

	/**
	 * Returns the runMode.
	 * 
	 * @return the runMode to return
	 */
	@Deprecated
	public String getRunMode() {
		// return runMode;
		return getParameter(SystemParameterTypeEnum.RUN_MODE.name());
	}

	/**
	 * Sets the runMode.
	 * 
	 * @param runMode
	 *            the runMode to set
	 */
	public void setRunMode(String runMode) {
		// this.runMode = runMode;
		setParameter(SystemParameterTypeEnum.RUN_MODE.name(), runMode);
	}

	public Integer getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(Integer sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public Integer getSessionTimeoutTick() {
		return sessionTimeoutTick;
	}

	public void setSessionTimeoutTick(Integer sessionTimeoutTick) {
		this.sessionTimeoutTick = sessionTimeoutTick;
	}

	public Integer getSessionTimeoutWarning() {
		return sessionTimeoutWarning;
	}

	public void setSessionTimeoutWarning(Integer sessionTimeoutWarning) {
		this.sessionTimeoutWarning = sessionTimeoutWarning;
	}

	public Integer getUserSessionPingInterval() {
		return userSessionPingInterval;
	}

	public void setUserSessionPingInterval(Integer userSessionPingInterval) {
		this.userSessionPingInterval = userSessionPingInterval;
	}

	public Boolean getAutoPopulateGrids() {
		return autoPopulateGrids;
	}

	public void setAutoPopulateGrids(Boolean autoPopulateGrids) {
		this.autoPopulateGrids = autoPopulateGrids;
	}


}
