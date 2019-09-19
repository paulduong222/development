package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.CostGroupDefaultAgencyDaySharePercentage;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="SEQ_COST_GROUP_DF_AG_PCT", sequenceName="SEQ_COST_GROUP_DF_AG_PCT")
@Table(name = "isw_cost_group_df_ag_pct")
public class CostGroupDefaultAgencyDaySharePercentageImpl extends PersistableImpl implements CostGroupDefaultAgencyDaySharePercentage {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_COST_GROUP_DF_AG_PCT")
	private Long id = 0L;

	@ManyToOne(targetEntity=CostGroupImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "COST_GROUP_ID", nullable = false)
	private CostGroup costGroup;

	@ManyToOne(targetEntity=AgencyImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENCY_ID")
	private Agency agency;
	
	@Column(name = "PERCENTAGE", precision = 22)
	private BigDecimal percentage;
	

	public CostGroupDefaultAgencyDaySharePercentageImpl() {
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
	 * @return the costGroup
	 */
	public CostGroup getCostGroup() {
		return costGroup;
	}


	/**
	 * @param costGroup the costGroup to set
	 */
	public void setCostGroup(CostGroup costGroup) {
		this.costGroup = costGroup;
	}


	/**
	 * @return the agency
	 */
	public Agency getAgency() {
		return agency;
	}


	/**
	 * @param agency the agency to set
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}


	/**
	 * @return the percentage
	 */
	public BigDecimal getPercentage() {
		return percentage;
	}


	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}


}
