package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

public class MissingPostingDateVo {
	private Long incidentResourceId;
	private Long parentRecordId;
	private String requestNumber;
	private String resourceName;
	private String postDate;
	private String startTime;
	private String stopTime;
	
	private Collection<MissingPostingDateVo> children = new ArrayList<MissingPostingDateVo>();
	
	public MissingPostingDateVo(){
		
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the postDate
	 */
	public String getPostDate() {
		return postDate;
	}

	/**
	 * @param postDate the postDate to set
	 */
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the stopTime
	 */
	public String getStopTime() {
		return stopTime;
	}

	/**
	 * @param stopTime the stopTime to set
	 */
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @return the children
	 */
	public Collection<MissingPostingDateVo> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Collection<MissingPostingDateVo> children) {
		this.children = children;
	}

	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/**
	 * @return the parentRecordId
	 */
	public Long getParentRecordId() {
		return parentRecordId;
	}

	/**
	 * @param parentRecordId the parentRecordId to set
	 */
	public void setParentRecordId(Long parentRecordId) {
		this.parentRecordId = parentRecordId;
	}
}
