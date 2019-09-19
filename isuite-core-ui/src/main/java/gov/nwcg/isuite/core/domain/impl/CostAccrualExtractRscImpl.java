package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.domain.CostAccrualExtractRsc;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity                  
@SequenceGenerator(name="seq_COST_ACCRUAL_EXT_RSC", sequenceName="seq_COST_ACCRUAL_EXT_RSC")
@Table(name="isw_cost_accrual_ext_rsc")
public class CostAccrualExtractRscImpl extends PersistableImpl implements CostAccrualExtractRsc {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_COST_ACCRUAL_EXT_RSC")
	private Long id = 0L;

	@ManyToOne(targetEntity=CostAccrualExtractImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name = "COST_ACCRUAL_EXTRACT_ID", insertable = true, updatable = true, unique = false, nullable = false)
	private CostAccrualExtract costAccrualExtract;
	
	@Column(name="COST_ACCRUAL_EXTRACT_ID", insertable = false, updatable = false, nullable = false, length=19)
	private Long costAccrualExtractId;
	
	@ManyToOne(targetEntity=IncidentResourceImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_RESOURCE_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private IncidentResource incidentResource;
	
	@Column(name="INCIDENT_RESOURCE_ID", insertable = false, updatable = false, nullable = true, length=19)
	private Long incidentResourceId;
	
	@ManyToOne(targetEntity=IncidentResourceOtherImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_RESOURCE_OTHER_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private IncidentResourceOther incidentResourceOther;
	
	@Column(name="INCIDENT_RESOURCE_OTHER_ID", insertable = false, updatable = false, nullable = true, length=19)
	private Long incidentResourceOtherId;

	@Column(name = "TOTAL_AMOUNT", precision = 22, scale = 0)
	private BigDecimal totalAmount;

	@Column(name = "CHANGE_AMOUNT", precision = 22, scale = 0)
	private BigDecimal changeAmount;
	
	@Column(name = "COST_ACCRUAL_CODE", nullable = true, length = 20)
	private String costAccrualCode;
	
	@Column(name = "ACCOUNT_CODE", nullable = true, length = 50)
	private String accountCode;
	
	@Column(name = "FISCAL_YEAR", length = 2)
	private String fiscalYear;
	
	@Column(name = "DRAW_DOWN", length = 1, nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum drawDown;
	
	
	/**
	 * Default constructor.
	 *
	 */
	public CostAccrualExtractRscImpl() {
		super();
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
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the changeAmount
	 */
	public BigDecimal getChangeAmount() {
		return changeAmount;
	}

	/**
	 * @param changeAmount the changeAmount to set
	 */
	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}

	public CostAccrualExtract getCostAccrualExtract() {
		return costAccrualExtract;
	}

	public void setCostAccrualExtract(CostAccrualExtract costAccrualExtract) {
		this.costAccrualExtract = costAccrualExtract;
	}

	public Long getCostAccrualExtractId() {
		return costAccrualExtractId;
	}

	public void setCostAccrualExtractId(Long costAccrualExtractId) {
		this.costAccrualExtractId = costAccrualExtractId;
	}

	public IncidentResource getIncidentResource() {
		return incidentResource;
	}

	public void setIncidentResource(IncidentResource incidentResource) {
		this.incidentResource = incidentResource;
	}

	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	public IncidentResourceOther getIncidentResourceOther() {
		return incidentResourceOther;
	}

	public void setIncidentResourceOther(IncidentResourceOther incidentResourceOther) {
		this.incidentResourceOther = incidentResourceOther;
	}

	public Long getIncidentResourceOtherId() {
		return incidentResourceOtherId;
	}

	public void setIncidentResourceOtherId(Long incidentResourceOtherId) {
		this.incidentResourceOtherId = incidentResourceOtherId;
	}
	
	/**
	 * @return the costAccrualCode
	 */
	public String getCostAccrualCode() {
		return costAccrualCode;
	}

	/**
	 * @param costAccrualCode the costAccrualCode to set
	 */
	public void setCostAccrualCode(String costAccrualCode) {
		this.costAccrualCode = costAccrualCode;
	}

	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param fiscalYear the fiscalYear to set
	 */
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	/**
	 * @return the fiscalYear
	 */
	public String getFiscalYear() {
		return fiscalYear;
	}

	/**
	 * @param drawDown the drawDown to set
	 */
	public void setDrawDown(StringBooleanEnum drawDown) {
		this.drawDown = drawDown;
	}

	/**
	 * @return the drawDown
	 */
	public StringBooleanEnum getDrawDown() {
		return drawDown;
	}
}
