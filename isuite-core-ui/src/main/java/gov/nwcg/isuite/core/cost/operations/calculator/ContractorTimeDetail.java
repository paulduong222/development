package gov.nwcg.isuite.core.cost.operations.calculator;


public class ContractorTimeDetail implements Cloneable {

	private String postType;
	private Double workedUnits=0.0;
	private String workedUnitType;
	private Double workedRate=0.0;
	private Double workedTotalAmount=0.0;
	private Double specialUnits;
	private String specialUnitType;
	private Double specialRate=0.0;
	private Double specialTotalAmount=0.0;
	private Double totalAmount=0.0;
	private Double guaranteeAmount=0.0;
	private Double finalAmount=0.0;
	private String uom;
	private String contractorRateId;
	
	public ContractorTimeDetail(){
		
	}

	public ContractorTimeDetail clone() throws CloneNotSupportedException {
		return (ContractorTimeDetail)super.clone();
	}
	
	/**
	 * @return the workedUnits
	 */
	public Double getWorkedUnits() {
		return workedUnits;
	}

	/**
	 * @param workedUnits the workedUnits to set
	 */
	public void setWorkedUnits(Double workedUnits) {
		this.workedUnits = workedUnits;
	}

	/**
	 * @return the workedUnitType
	 */
	public String getWorkedUnitType() {
		return workedUnitType;
	}

	/**
	 * @param workedUnitType the workedUnitType to set
	 */
	public void setWorkedUnitType(String workedUnitType) {
		this.workedUnitType = workedUnitType;
	}

	/**
	 * @return the workedRate
	 */
	public Double getWorkedRate() {
		return workedRate;
	}

	/**
	 * @param workedRate the workedRate to set
	 */
	public void setWorkedRate(Double workedRate) {
		this.workedRate = workedRate;
	}

	/**
	 * @return the workedTotalAmount
	 */
	public Double getWorkedTotalAmount() {
		return workedTotalAmount;
	}

	/**
	 * @param workedTotalAmount the workedTotalAmount to set
	 */
	public void setWorkedTotalAmount(Double workedTotalAmount) {
		this.workedTotalAmount = workedTotalAmount;
	}

	/**
	 * @return the specialUnits
	 */
	public Double getSpecialUnits() {
		return specialUnits;
	}

	/**
	 * @param specialUnits the specialUnits to set
	 */
	public void setSpecialUnits(Double specialUnits) {
		this.specialUnits = specialUnits;
	}

	/**
	 * @return the specialUnitType
	 */
	public String getSpecialUnitType() {
		return specialUnitType;
	}

	/**
	 * @param specialUnitType the specialUnitType to set
	 */
	public void setSpecialUnitType(String specialUnitType) {
		this.specialUnitType = specialUnitType;
	}

	/**
	 * @return the specialRate
	 */
	public Double getSpecialRate() {
		return specialRate;
	}

	/**
	 * @param specialRate the specialRate to set
	 */
	public void setSpecialRate(Double specialRate) {
		this.specialRate = specialRate;
	}

	/**
	 * @return the specialTotalAmount
	 */
	public Double getSpecialTotalAmount() {
		return specialTotalAmount;
	}

	/**
	 * @param specialTotalAmount the specialTotalAmount to set
	 */
	public void setSpecialTotalAmount(Double specialTotalAmount) {
		this.specialTotalAmount = specialTotalAmount;
	}

	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the guaranteeAmount
	 */
	public Double getGuaranteeAmount() {
		return guaranteeAmount;
	}

	/**
	 * @param guaranteeAmount the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(Double guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	/**
	 * @return the finalAmount
	 */
	public Double getFinalAmount() {
		return finalAmount;
	}

	/**
	 * @param finalAmount the finalAmount to set
	 */
	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}

	/**
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}

	/**
	 * @param uom the uom to set
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}

	/**
	 * @return the postType
	 */
	public String getPostType() {
		return postType;
	}

	/**
	 * @param postType the postType to set
	 */
	public void setPostType(String postType) {
		this.postType = postType;
	}

	/**
	 * @return the contractorRateId
	 */
	public String getContractorRateId() {
		return contractorRateId;
	}

	/**
	 * @param contractorRateId the contractorRateId to set
	 */
	public void setContractorRateId(String contractorRateId) {
		this.contractorRateId = contractorRateId;
	}
}
