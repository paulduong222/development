package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.views.CRResourceCostRateView;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iswv_cr_resource_cost_rate")
public class CRResourceCostRateViewImpl implements CRResourceCostRateView {
	
	@Id
	@Column(name = "INCIDENT_ID", insertable = false, updatable = false)
	private Long incidentId;
	
	@Id
	@Column(name = "ITEM_CODE", insertable = false, updatable = false)
	private String itemCode;

	@Column(name = "ITEM_DESCRIPTION", insertable = false, updatable = false)
	private String itemCodeDescription;

	@Column(name = "RESOURCE_CATEGORY", insertable = false, updatable = false)
	private String resourceCategory;

	@Column(name = "FED_RATE", insertable = false, updatable = false)
	private BigDecimal fedRate;

	@Column(name = "STATE_DEFAULT_RATE", insertable = false, updatable = false)
	private BigDecimal stateRate;

	@Column(name = "CONTRACTOR_RATE", insertable = false, updatable = false)
	private BigDecimal contractorRate;
	
	@Column(name = "STATE_RATE_AK", insertable = false, updatable = false)
	private BigDecimal stateRateAK;

	@Column(name = "STATE_RATE_AL", insertable = false, updatable = false)
	private BigDecimal stateRateAL;

	@Column(name = "STATE_RATE_AR", insertable = false, updatable = false)
	private BigDecimal stateRateAR;

	@Column(name = "STATE_RATE_AZ", insertable = false, updatable = false)
	private BigDecimal stateRateAZ;

	@Column(name = "STATE_RATE_CA", insertable = false, updatable = false)
	private BigDecimal stateRateCA;

	@Column(name = "STATE_RATE_CO", insertable = false, updatable = false)
	private BigDecimal stateRateCO;

	@Column(name = "STATE_RATE_CT", insertable = false, updatable = false)
	private BigDecimal stateRateCT;

	@Column(name = "STATE_RATE_DC", insertable = false, updatable = false)
	private BigDecimal stateRateDC;

	@Column(name = "STATE_RATE_DE", insertable = false, updatable = false)
	private BigDecimal stateRateDE;

	@Column(name = "STATE_RATE_FL", insertable = false, updatable = false)
	private BigDecimal stateRateFL;

	@Column(name = "STATE_RATE_GA", insertable = false, updatable = false)
	private BigDecimal stateRateGA;

	@Column(name = "STATE_RATE_HI", insertable = false, updatable = false)
	private BigDecimal stateRateHI;

	@Column(name = "STATE_RATE_IA", insertable = false, updatable = false)
	private BigDecimal stateRateIA;

	@Column(name = "STATE_RATE_ID", insertable = false, updatable = false)
	private BigDecimal stateRateID;

	@Column(name = "STATE_RATE_IL", insertable = false, updatable = false)
	private BigDecimal stateRateIL;

	@Column(name = "STATE_RATE_IN", insertable = false, updatable = false)
	private BigDecimal stateRateIN;

	@Column(name = "STATE_RATE_KS", insertable = false, updatable = false)
	private BigDecimal stateRateKS;

	@Column(name = "STATE_RATE_KY", insertable = false, updatable = false)
	private BigDecimal stateRateKY;

	@Column(name = "STATE_RATE_LA", insertable = false, updatable = false)
	private BigDecimal stateRateLA;

	@Column(name = "STATE_RATE_MA", insertable = false, updatable = false)
	private BigDecimal stateRateMA;

	@Column(name = "STATE_RATE_MD", insertable = false, updatable = false)
	private BigDecimal stateRateMD;

	@Column(name = "STATE_RATE_ME", insertable = false, updatable = false)
	private BigDecimal stateRateME;

	@Column(name = "STATE_RATE_MI", insertable = false, updatable = false)
	private BigDecimal stateRateMI;

	@Column(name = "STATE_RATE_MN", insertable = false, updatable = false)
	private BigDecimal stateRateMN;

	@Column(name = "STATE_RATE_MO", insertable = false, updatable = false)
	private BigDecimal stateRateMO;

	@Column(name = "STATE_RATE_MS", insertable = false, updatable = false)
	private BigDecimal stateRateMS;

	@Column(name = "STATE_RATE_MT", insertable = false, updatable = false)
	private BigDecimal stateRateMT;

	@Column(name = "STATE_RATE_NC", insertable = false, updatable = false)
	private BigDecimal stateRateNC;

	@Column(name = "STATE_RATE_ND", insertable = false, updatable = false)
	private BigDecimal stateRateND;

	@Column(name = "STATE_RATE_NE", insertable = false, updatable = false)
	private BigDecimal stateRateNE;

	@Column(name = "STATE_RATE_NH", insertable = false, updatable = false)
	private BigDecimal stateRateNH;

	@Column(name = "STATE_RATE_NJ", insertable = false, updatable = false)
	private BigDecimal stateRateNJ;

	@Column(name = "STATE_RATE_NM", insertable = false, updatable = false)
	private BigDecimal stateRateNM;

	@Column(name = "STATE_RATE_NV", insertable = false, updatable = false)
	private BigDecimal stateRateNV;

	@Column(name = "STATE_RATE_NY", insertable = false, updatable = false)
	private BigDecimal stateRateNY;

	@Column(name = "STATE_RATE_OH", insertable = false, updatable = false)
	private BigDecimal stateRateOH;

	@Column(name = "STATE_RATE_OK", insertable = false, updatable = false)
	private BigDecimal stateRateOK;

	@Column(name = "STATE_RATE_OR", insertable = false, updatable = false)
	private BigDecimal stateRateOR;

	@Column(name = "STATE_RATE_PA", insertable = false, updatable = false)
	private BigDecimal stateRatePA;

	@Column(name = "STATE_RATE_PR", insertable = false, updatable = false)
	private BigDecimal stateRatePR;

	@Column(name = "STATE_RATE_RI", insertable = false, updatable = false)
	private BigDecimal stateRateRI;

	@Column(name = "STATE_RATE_SC", insertable = false, updatable = false)
	private BigDecimal stateRateSC;

	@Column(name = "STATE_RATE_SD", insertable = false, updatable = false)
	private BigDecimal stateRateSD;

	@Column(name = "STATE_RATE_TN", insertable = false, updatable = false)
	private BigDecimal stateRateTN;

	@Column(name = "STATE_RATE_TX", insertable = false, updatable = false)
	private BigDecimal stateRateTX;

	@Column(name = "STATE_RATE_UT", insertable = false, updatable = false)
	private BigDecimal stateRateUT;

	@Column(name = "STATE_RATE_VA", insertable = false, updatable = false)
	private BigDecimal stateRateVA;

	@Column(name = "STATE_RATE_VI", insertable = false, updatable = false)
	private BigDecimal stateRateVI;

	@Column(name = "STATE_RATE_VT", insertable = false, updatable = false)
	private BigDecimal stateRateVT;

	@Column(name = "STATE_RATE_WA", insertable = false, updatable = false)
	private BigDecimal stateRateWA;

	@Column(name = "STATE_RATE_WI", insertable = false, updatable = false)
	private BigDecimal stateRateWI;

	@Column(name = "STATE_RATE_WV", insertable = false, updatable = false)
	private BigDecimal stateRateWV;

	@Column(name = "STATE_RATE_WY", insertable = false, updatable = false)
	private BigDecimal stateRateWY;
	
	public CRResourceCostRateViewImpl() {
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemCodeDescription() {
		return itemCodeDescription;
	}

	public void setItemCodeDescription(String itemCodeDescription) {
		this.itemCodeDescription = itemCodeDescription;
	}

	public String getResourceCategory() {
		return resourceCategory;
	}

	public void setResourceCategory(String resourceCategory) {
		this.resourceCategory = resourceCategory;
	}

	public BigDecimal getFedRate() {
		return fedRate;
	}

	public void setFedRate(BigDecimal fedRate) {
		this.fedRate = fedRate;
	}

	public BigDecimal getStateRate() {
		return stateRate;
	}

	public void setStateRate(BigDecimal stateRate) {
		this.stateRate = stateRate;
	}

	public BigDecimal getContractorRate() {
		return contractorRate;
	}

	public void setContractorRate(BigDecimal contractorRate) {
		this.contractorRate = contractorRate;
	}

	public BigDecimal getStateRateAK() {
		return stateRateAK;
	}

	public void setStateRateAK(BigDecimal stateRateAK) {
		this.stateRateAK = stateRateAK;
	}

	public BigDecimal getStateRateAL() {
		return stateRateAL;
	}

	public void setStateRateAL(BigDecimal stateRateAL) {
		this.stateRateAL = stateRateAL;
	}

	public BigDecimal getStateRateAR() {
		return stateRateAR;
	}

	public void setStateRateAR(BigDecimal stateRateAR) {
		this.stateRateAR = stateRateAR;
	}

	public BigDecimal getStateRateAZ() {
		return stateRateAZ;
	}

	public void setStateRateAZ(BigDecimal stateRateAZ) {
		this.stateRateAZ = stateRateAZ;
	}

	public BigDecimal getStateRateCA() {
		return stateRateCA;
	}

	public void setStateRateCA(BigDecimal stateRateCA) {
		this.stateRateCA = stateRateCA;
	}

	public BigDecimal getStateRateCO() {
		return stateRateCO;
	}

	public void setStateRateCO(BigDecimal stateRateCO) {
		this.stateRateCO = stateRateCO;
	}

	public BigDecimal getStateRateCT() {
		return stateRateCT;
	}

	public void setStateRateCT(BigDecimal stateRateCT) {
		this.stateRateCT = stateRateCT;
	}

	public BigDecimal getStateRateDC() {
		return stateRateDC;
	}

	public void setStateRateDC(BigDecimal stateRateDC) {
		this.stateRateDC = stateRateDC;
	}

	public BigDecimal getStateRateDE() {
		return stateRateDE;
	}

	public void setStateRateDE(BigDecimal stateRateDE) {
		this.stateRateDE = stateRateDE;
	}

	public BigDecimal getStateRateFL() {
		return stateRateFL;
	}

	public void setStateRateFL(BigDecimal stateRateFL) {
		this.stateRateFL = stateRateFL;
	}

	public BigDecimal getStateRateGA() {
		return stateRateGA;
	}

	public void setStateRateGA(BigDecimal stateRateGA) {
		this.stateRateGA = stateRateGA;
	}

	public BigDecimal getStateRateHI() {
		return stateRateHI;
	}

	public void setStateRateHI(BigDecimal stateRateHI) {
		this.stateRateHI = stateRateHI;
	}

	public BigDecimal getStateRateIA() {
		return stateRateIA;
	}

	public void setStateRateIA(BigDecimal stateRateIA) {
		this.stateRateIA = stateRateIA;
	}

	public BigDecimal getStateRateID() {
		return stateRateID;
	}

	public void setStateRateID(BigDecimal stateRateID) {
		this.stateRateID = stateRateID;
	}

	public BigDecimal getStateRateIL() {
		return stateRateIL;
	}

	public void setStateRateIL(BigDecimal stateRateIL) {
		this.stateRateIL = stateRateIL;
	}

	public BigDecimal getStateRateIN() {
		return stateRateIN;
	}

	public void setStateRateIN(BigDecimal stateRateIN) {
		this.stateRateIN = stateRateIN;
	}

	public BigDecimal getStateRateKS() {
		return stateRateKS;
	}

	public void setStateRateKS(BigDecimal stateRateKS) {
		this.stateRateKS = stateRateKS;
	}

	public BigDecimal getStateRateKY() {
		return stateRateKY;
	}

	public void setStateRateKY(BigDecimal stateRateKY) {
		this.stateRateKY = stateRateKY;
	}

	public BigDecimal getStateRateLA() {
		return stateRateLA;
	}

	public void setStateRateLA(BigDecimal stateRateLA) {
		this.stateRateLA = stateRateLA;
	}

	public BigDecimal getStateRateMA() {
		return stateRateMA;
	}

	public void setStateRateMA(BigDecimal stateRateMA) {
		this.stateRateMA = stateRateMA;
	}

	public BigDecimal getStateRateMD() {
		return stateRateMD;
	}

	public void setStateRateMD(BigDecimal stateRateMD) {
		this.stateRateMD = stateRateMD;
	}

	public BigDecimal getStateRateME() {
		return stateRateME;
	}

	public void setStateRateME(BigDecimal stateRateME) {
		this.stateRateME = stateRateME;
	}

	public BigDecimal getStateRateMI() {
		return stateRateMI;
	}

	public void setStateRateMI(BigDecimal stateRateMI) {
		this.stateRateMI = stateRateMI;
	}

	public BigDecimal getStateRateMN() {
		return stateRateMN;
	}

	public void setStateRateMN(BigDecimal stateRateMN) {
		this.stateRateMN = stateRateMN;
	}

	public BigDecimal getStateRateMO() {
		return stateRateMO;
	}

	public void setStateRateMO(BigDecimal stateRateMO) {
		this.stateRateMO = stateRateMO;
	}

	public BigDecimal getStateRateMS() {
		return stateRateMS;
	}

	public void setStateRateMS(BigDecimal stateRateMS) {
		this.stateRateMS = stateRateMS;
	}

	public BigDecimal getStateRateMT() {
		return stateRateMT;
	}

	public void setStateRateMT(BigDecimal stateRateMT) {
		this.stateRateMT = stateRateMT;
	}

	public BigDecimal getStateRateNC() {
		return stateRateNC;
	}

	public void setStateRateNC(BigDecimal stateRateNC) {
		this.stateRateNC = stateRateNC;
	}

	public BigDecimal getStateRateND() {
		return stateRateND;
	}

	public void setStateRateND(BigDecimal stateRateND) {
		this.stateRateND = stateRateND;
	}

	public BigDecimal getStateRateNE() {
		return stateRateNE;
	}

	public void setStateRateNE(BigDecimal stateRateNE) {
		this.stateRateNE = stateRateNE;
	}

	public BigDecimal getStateRateNH() {
		return stateRateNH;
	}

	public void setStateRateNH(BigDecimal stateRateNH) {
		this.stateRateNH = stateRateNH;
	}

	public BigDecimal getStateRateNJ() {
		return stateRateNJ;
	}

	public void setStateRateNJ(BigDecimal stateRateNJ) {
		this.stateRateNJ = stateRateNJ;
	}

	public BigDecimal getStateRateNM() {
		return stateRateNM;
	}

	public void setStateRateNM(BigDecimal stateRateNM) {
		this.stateRateNM = stateRateNM;
	}

	public BigDecimal getStateRateNV() {
		return stateRateNV;
	}

	public void setStateRateNV(BigDecimal stateRateNV) {
		this.stateRateNV = stateRateNV;
	}

	public BigDecimal getStateRateNY() {
		return stateRateNY;
	}

	public void setStateRateNY(BigDecimal stateRateNY) {
		this.stateRateNY = stateRateNY;
	}

	public BigDecimal getStateRateOH() {
		return stateRateOH;
	}

	public void setStateRateOH(BigDecimal stateRateOH) {
		this.stateRateOH = stateRateOH;
	}

	public BigDecimal getStateRateOK() {
		return stateRateOK;
	}

	public void setStateRateOK(BigDecimal stateRateOK) {
		this.stateRateOK = stateRateOK;
	}

	public BigDecimal getStateRateOR() {
		return stateRateOR;
	}

	public void setStateRateOR(BigDecimal stateRateOR) {
		this.stateRateOR = stateRateOR;
	}

	public BigDecimal getStateRatePA() {
		return stateRatePA;
	}

	public void setStateRatePA(BigDecimal stateRatePA) {
		this.stateRatePA = stateRatePA;
	}

	public BigDecimal getStateRatePR() {
		return stateRatePR;
	}

	public void setStateRatePR(BigDecimal stateRatePR) {
		this.stateRatePR = stateRatePR;
	}

	public BigDecimal getStateRateRI() {
		return stateRateRI;
	}

	public void setStateRateRI(BigDecimal stateRateRI) {
		this.stateRateRI = stateRateRI;
	}

	public BigDecimal getStateRateSC() {
		return stateRateSC;
	}

	public void setStateRateSC(BigDecimal stateRateSC) {
		this.stateRateSC = stateRateSC;
	}

	public BigDecimal getStateRateSD() {
		return stateRateSD;
	}

	public void setStateRateSD(BigDecimal stateRateSD) {
		this.stateRateSD = stateRateSD;
	}

	public BigDecimal getStateRateTN() {
		return stateRateTN;
	}

	public void setStateRateTN(BigDecimal stateRateTN) {
		this.stateRateTN = stateRateTN;
	}

	public BigDecimal getStateRateTX() {
		return stateRateTX;
	}

	public void setStateRateTX(BigDecimal stateRateTX) {
		this.stateRateTX = stateRateTX;
	}

	public BigDecimal getStateRateUT() {
		return stateRateUT;
	}

	public void setStateRateUT(BigDecimal stateRateUT) {
		this.stateRateUT = stateRateUT;
	}

	public BigDecimal getStateRateVA() {
		return stateRateVA;
	}

	public void setStateRateVA(BigDecimal stateRateVA) {
		this.stateRateVA = stateRateVA;
	}

	public BigDecimal getStateRateVI() {
		return stateRateVI;
	}

	public void setStateRateVI(BigDecimal stateRateVI) {
		this.stateRateVI = stateRateVI;
	}

	public BigDecimal getStateRateVT() {
		return stateRateVT;
	}

	public void setStateRateVT(BigDecimal stateRateVT) {
		this.stateRateVT = stateRateVT;
	}

	public BigDecimal getStateRateWA() {
		return stateRateWA;
	}

	public void setStateRateWA(BigDecimal stateRateWA) {
		this.stateRateWA = stateRateWA;
	}

	public BigDecimal getStateRateWI() {
		return stateRateWI;
	}

	public void setStateRateWI(BigDecimal stateRateWI) {
		this.stateRateWI = stateRateWI;
	}

	public BigDecimal getStateRateWV() {
		return stateRateWV;
	}

	public void setStateRateWV(BigDecimal stateRateWV) {
		this.stateRateWV = stateRateWV;
	}

	public BigDecimal getStateRateWY() {
		return stateRateWY;
	}

	public void setStateRateWY(BigDecimal stateRateWY) {
		this.stateRateWY = stateRateWY;
	}
	
}
