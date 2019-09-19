package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class CostGroupResourceGridVo extends AbstractVo {
	private String requestNumber;
	private String resourceName;
	private String itemCode;
	private String agencyCode;
	private String unitId;
	
	
	public static Collection<Long> toResourceIds(Collection<CostGroupResourceGridVo> vos) {
		Collection<Long> ids = new ArrayList<Long>();
		
		for(CostGroupResourceGridVo vo : vos){
			ids.add(vo.getId());
		}
		
		return ids;
	}
	
	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	
	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}
	
	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}
	
	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}
	
	/**
	 * @param agencyCode the agencyCode to set
	 */
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	
	/**
	 * @return the agencyCode
	 */
	public String getAgencyCode() {
		return agencyCode;
	}
	
	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	/**
	 * @return the unitId
	 */
	public String getUnitId() {
		return unitId;
	}

}
