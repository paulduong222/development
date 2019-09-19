package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class IncidentResourceOtherGridVo {
	private Long id;
	private String requestNumber;
    private String itemName;
    private String itemCode;
	private AssignmentStatusTypeEnum assignmentStatus=null;
    private String agency;
    private String paymentAgency;
    private Date actualReleaseDate;
    private String actualReleaseTime;
	private String incidentNumber;
	private String incidentName;
    private String remarks;
	private String accrualCode;
	private Date assignDate;
	private String costDescription;
    
    public IncidentResourceOtherGridVo(){
    	
    }

    /**
     * @param entities
     * @return
     */
    public static Collection<IncidentResourceOtherGridVo> getInstances(Collection<IncidentResourceOther> entities) {
    	Collection<IncidentResourceOtherGridVo> vos = new ArrayList<IncidentResourceOtherGridVo>();
    	
    	for(IncidentResourceOther entity : entities){
    		IncidentResourceOtherGridVo vo = new IncidentResourceOtherGridVo();
    		vo.setId(entity.getId());
    		
    		if(null != entity.getIncident()){
    			vo.setIncidentName(entity.getIncident().getIncidentName());
    			vo.setIncidentNumber(entity.getIncident().getIncidentNumber());
    		}
    		
    		if(null != entity.getResourceOther()){
    			vo.setRequestNumber(entity.getResourceOther().getRequestNumber());
    			vo.setActualReleaseDate(entity.getResourceOther().getActualReleaseDate());
    			vo.setCostDescription(entity.getResourceOther().getCostDescription());
    			
    			if(null != entity.getResourceOther().getAgency())
    				vo.setAgency(entity.getResourceOther().getAgency().getAgencyCode());
    			
    			if(null != entity.getResourceOther().getKind()){
    				vo.setItemCode(entity.getResourceOther().getKind().getCode());
    				vo.setItemName(entity.getResourceOther().getKind().getDescription());
    			}
    		}
    		
    		if(null != entity.getAssignmentStatus()) {
    			vo.setAssignmentStatus(entity.getAssignmentStatus());
    		}
    		
    		if(null != entity.getCostData()){
    			if(null != entity.getCostData().getAccrualCode()){
    				vo.setAccrualCode(entity.getCostData().getAccrualCode().getCode());
    			}
    			vo.setAssignDate(entity.getCostData().getAssignDate());
    			if(null != entity.getCostData().getPaymentAgency()) {
    				vo.setPaymentAgency(entity.getCostData().getPaymentAgency().getAgencyName());
    			}
    			vo.setRemarks(entity.getCostData().getCostRemarks());
    		}
    		
    		vos.add(vo);
    	}
    	
    	return vos;
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}


	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}


	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}


	/**
	 * @return the assignmentStatus
	 */
	public AssignmentStatusTypeEnum getAssignmentStatus() {
		return assignmentStatus;
	}


	/**
	 * @param assignmentStatus the assignmentStatus to set
	 */
	public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}


	/**
	 * @return the agency
	 */
	public String getAgency() {
		return agency;
	}


	/**
	 * @param agency the agency to set
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}


	/**
	 * @return the paymentAgency
	 */
	public String getPaymentAgency() {
		return paymentAgency;
	}


	/**
	 * @param paymentAgency the paymentAgency to set
	 */
	public void setPaymentAgency(String paymentAgency) {
		this.paymentAgency = paymentAgency;
	}


	/**
	 * @return the actualReleaseDate
	 */
	public Date getActualReleaseDate() {
		return actualReleaseDate;
	}


	/**
	 * @param actualReleaseDate the actualReleaseDate to set
	 */
	public void setActualReleaseDate(Date actualReleaseDate) {
		this.actualReleaseDate = actualReleaseDate;
	}


	/**
	 * @return the actualReleaseTime
	 */
	public String getActualReleaseTime() {
		return actualReleaseTime;
	}


	/**
	 * @param actualReleaseTime the actualReleaseTime to set
	 */
	public void setActualReleaseTime(String actualReleaseTime) {
		this.actualReleaseTime = actualReleaseTime;
	}


	/**
	 * @return the incidentNumber
	 */
	public String getIncidentNumber() {
		return incidentNumber;
	}


	/**
	 * @param incidentNumber the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}


	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}


	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}


	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}


	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	/**
	 * @return the accrualCode
	 */
	public String getAccrualCode() {
		return accrualCode;
	}


	/**
	 * @param accrualCode the accrualCode to set
	 */
	public void setAccrualCode(String accrualCode) {
		this.accrualCode = accrualCode;
	}


	/**
	 * @return the assignDate
	 */
	public Date getAssignDate() {
		return assignDate;
	}


	/**
	 * @param assignDate the assignDate to set
	 */
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	/**
	 * @return the costDescription
	 */
	public String getCostDescription() {
		return costDescription;
	}

	/**
	 * @param costDescription the costDescription to set
	 */
	public void setCostDescription(String costDescription) {
		this.costDescription = costDescription;
	}


}
