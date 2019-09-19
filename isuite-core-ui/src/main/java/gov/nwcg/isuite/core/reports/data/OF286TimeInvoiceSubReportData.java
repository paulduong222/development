package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.report.data.TimeInvoiceDetail;
import gov.nwcg.isuite.framework.util.DecimalUtil;

import java.util.Date;

public class OF286TimeInvoiceSubReportData extends TimeInvoiceDetail  implements Cloneable{

	private String objectCode;

	private String agreementNumber;
	private Date beginningDateOfAgreement;
	private Date endingDateOfAgreement;
	private Date dateOfHire;

	private String pointOfHire;

	private Double workedUnits;
	private String workedUnitType;
	private Double workedRate;
	private Double workedTotalAmount;
	private Double specialUnits;
	private String specialUnitType;
	private Double specialRate;
	private Double specialTotalAmount;
	private Double totalAmount;
	private Double guaranteeAmount;
	private Double finalAmount;

	private Boolean isSupplies;

	private Boolean isOperator;

	private Boolean equipmentReleased;
	private Boolean equipmentWithdrawn;
	private Date equipmentReleasedDateAndTime;
	private Date equipmentWithdrawnDateAndTime;

	private String noLineTotal = "";

	private String devPostingType="";
	private Double devTotalAmount;
	private Double devGuaranteeAmount;
	private String devUom;
	private Date devDate;
	private String devContractorRateId;

	public OF286TimeInvoiceSubReportData clone() throws CloneNotSupportedException {
		return (OF286TimeInvoiceSubReportData)super.clone();
	}
	
	public void setIsOperator(Boolean isOperator) {
		this.isOperator = isOperator;
	}

	/**
	 * @return the operatorFurnishedByContractor
	 */
	public String getOperatorFurnishedByContractor() {
		return getBoxChecked(!this.isOperator);
	}

	/**
	 * @return the operatorFurnishedByGovernment
	 */
	public String getOperatorFurnishedByGovernment() {
		return getBoxChecked(this.isOperator);
	}

	public void setEquipmentReleased(Boolean equipmentReleased) {
		this.equipmentReleased = equipmentReleased;
	}

	/**
	 * @return the equipmentReleased
	 */
	public String getEquipmentReleased() {
		return getBoxChecked(this.equipmentReleased);
	}

	public void setEquipmentWithdrawn(Boolean equipmentWithdrawn) {
		this.equipmentWithdrawn = equipmentWithdrawn;
	}

	/**
	 * @return the equipmentWithdrawn
	 */
	public String getEquipmentIsWithdrawn() {
		return getBoxChecked(this.equipmentWithdrawn);
	}

	public void setIsSupplies(Boolean isSupplies) {
		this.isSupplies = isSupplies;
	}

	/**
	 * @return
	 */
	public String getContractorDry() {
		return getBoxChecked(this.isSupplies);
	}

	/**
	 * 
	 * @return
	 */
	public String getContractorWet() {
		return getBoxChecked(!this.isSupplies);
	}

	public Date getEquipmentReleasedDateAndTime() {
		return equipmentReleasedDateAndTime;
	}

	public void setEquipmentReleasedDateAndTime(Date equipmentReleasedDateAndTime) {
		this.equipmentReleasedDateAndTime = equipmentReleasedDateAndTime;
	}

	public Date getEquipmentWithdrawnDateAndTime() {
		return equipmentWithdrawnDateAndTime;
	}

	public void setEquipmentWithdrawnDateAndTime(Date equipmentWithdrawnDateAndTime) {
		this.equipmentWithdrawnDateAndTime = equipmentWithdrawnDateAndTime;
	}

	public Double getWorkedUnits() {
		return workedUnits;
	}

	public void setWorkedUnits(Double workedUnits) {
		this.workedUnits = workedUnits;
	}

	public String getWorkedUnitType() {
		return workedUnitType;
	}

	public void setWorkedUnitType(String workedUnitType) {
		this.workedUnitType = workedUnitType;
	}

	public Double getWorkedRate() {
		return workedRate;
	}

	public void setWorkedRate(Double workedRate) {
		this.workedRate = workedRate;
	}

	public Double getWorkedTotalAmount() {
		if(null != workedTotalAmount){
			double d=DecimalUtil.formatAsDecimalRounded(workedTotalAmount);
			return new Double(d);
		}
		return workedTotalAmount;
	}

	public void setWorkedTotalAmount(Double workedTotalAmount) {
		this.workedTotalAmount = workedTotalAmount;
	}

	public Double getSpecialUnits() {
		return specialUnits;
	}

	public void setSpecialUnits(Double specialUnits) {
		this.specialUnits = specialUnits;
	}

	public String getSpecialUnitType() {
		return specialUnitType;
	}

	public void setSpecialUnitType(String specialUnitType) {
		this.specialUnitType = specialUnitType;
	}

	public Double getSpecialRate() {
		return specialRate;
	}

	public void setSpecialRate(Double specialRate) {
		this.specialRate = specialRate;
	}

	public Double getSpecialTotalAmount() {
		if(null != specialTotalAmount){
			double d=DecimalUtil.formatAsDecimalRounded(specialTotalAmount);
			return new Double(d);
		}
		return specialTotalAmount;
	}

	public void setSpecialTotalAmount(Double specialTotalAmount) {
		this.specialTotalAmount = specialTotalAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getGuaranteeAmount() {
		return guaranteeAmount;
	}

	public void setGuaranteeAmount(Double guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	public Double getFinalAmount() {
		if(null != finalAmount){
			double d=DecimalUtil.formatAsDecimalRounded(finalAmount);
			return new Double(d);
		}
		return finalAmount;
	}

	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}

	/**
	 * @return the agreementNumber
	 */
	 public String getAgreementNumber() {
		return agreementNumber;
	}

	/**
	 * @return the beginningDateOfAgreement
	 */
	 public Date getBeginningDateOfAgreement() {
		 return beginningDateOfAgreement;
	 }

	 /**
	  * @return the dateOfHire
	  */
	 public Date getDateOfHire() {
		 return dateOfHire;
	 }

	 /**
	  * @return the endingDateOfAgreement
	  */
	 public Date getEndingDateOfAgreement() {
		 return endingDateOfAgreement;
	 }

	 /**
	  * @param agreementNumber
	  *          the agreementNumber to set
	  */
	 public void setAgreementNumber(String agreementNumber) {
		 this.agreementNumber = agreementNumber;
	 }

	 /**
	  * @param beginningDateOfAgreement
	  *          the beginningDateOfAgreement to set
	  */
	 public void setBeginningDateOfAgreement(Date beginningDateOfAgreement) {
		 this.beginningDateOfAgreement = beginningDateOfAgreement;
	 }

	 /**
	  * @param dateOfHire
	  *          the dateOfHire to set
	  */
	 public void setDateOfHire(Date dateOfHire) {
		 this.dateOfHire = dateOfHire;
	 }

	 /**
	  * @param endingDateOfAgreement
	  *          the endingDateOfAgreement to set
	  */
	 public void setEndingDateOfAgreement(Date endingDateOfAgreement) {
		 this.endingDateOfAgreement = endingDateOfAgreement;
	 }

	 public String getObjectCode() {
		 return objectCode;
	 }

	 public void setObjectCode(String objectCode) {
		 this.objectCode = objectCode;
	 }

	 public String getPointOfHire() {
		 return pointOfHire;
	 }

	 public void setPointOfHire(String pointOfHire) {
		 this.pointOfHire = pointOfHire;
	 }


	 public String getNoLineTotal() {
		 return noLineTotal;
	 }


	 public void setNoLineTotal(String noLineTotal) {
		 this.noLineTotal = noLineTotal;
	 }

	 /**
	  * @return the devPostingType
	  */
	 public String getDevPostingType() {
		 return devPostingType;
	 }

	 /**
	  * @param devPostingType the devPostingType to set
	  */
	 public void setDevPostingType(String devPostingType) {
		 this.devPostingType = devPostingType;
	 }

	 /**
	  * @return the devTotalAmount
	  */
	 public Double getDevTotalAmount() {
		 return devTotalAmount;
	 }

	 /**
	  * @param devTotalAmount the devTotalAmount to set
	  */
	 public void setDevTotalAmount(Double devTotalAmount) {
		 this.devTotalAmount = devTotalAmount;
	 }

	 /**
	  * @return the devGuaranteeAmount
	  */
	 public Double getDevGuaranteeAmount() {
		 return devGuaranteeAmount;
	 }

	 /**
	  * @param devGuaranteeAmount the devGuaranteeAmount to set
	  */
	 public void setDevGuaranteeAmount(Double devGuaranteeAmount) {
		 this.devGuaranteeAmount = devGuaranteeAmount;
	 }

	 /**
	  * @return the devUom
	  */
	 public String getDevUom() {
		 return devUom;
	 }

	 /**
	  * @param devUom the devUom to set
	  */
	 public void setDevUom(String devUom) {
		 this.devUom = devUom;
	 }

	 /**
	  * @return the devDate
	  */
	 public Date getDevDate() {
		 return devDate;
	 }

	 /**
	  * @param devDate the devDate to set
	  */
	 public void setDevDate(Date devDate) {
		 this.devDate = devDate;
	 }

	 /**
	  * @return the isSupplies
	  */
	 public Boolean getIsSupplies() {
		 return isSupplies;
	 }

	 /**
	  * @return the isOperator
	  */
	 public Boolean getIsOperator() {
		 return isOperator;
	 }

	 /**
	  * @return the equipmentWithdrawn
	  */
	 public Boolean getEquipmentWithdrawn() {
		 return equipmentWithdrawn;
	 }

	/**
	 * @return the devContractorRateId
	 */
	public String getDevContractorRateId() {
		return devContractorRateId;
	}

	/**
	 * @param devContractorRateId the devContractorRateId to set
	 */
	public void setDevContractorRateId(String devContractorRateId) {
		this.devContractorRateId = devContractorRateId;
	}


}
