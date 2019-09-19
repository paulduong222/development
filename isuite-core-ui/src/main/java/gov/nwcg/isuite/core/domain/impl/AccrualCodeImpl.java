package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AccrualCode;
import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_ACCRUAL_CODE", sequenceName="SEQ_ACCRUAL_CODE")
@Table(name = "iswl_accrual_code")
public class AccrualCodeImpl extends PersistableImpl implements AccrualCode {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ACCRUAL_CODE")
	private Long id = 0L;

	@Column(name = "CODE", nullable = false, length = 20)
	private String code;

	@Column(name = "DESCRIPTION", nullable = false, length = 75)
	private String description;
	
	@Column(name = "IS_STANDARD", precision = 1, scale = 0)
	private Boolean standard;

	@Column(name = "RC_LINE_NUMBER", nullable = false, length = 20)
	private String rcLineNumber;

	@Column(name = "REPORTABLE", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum reportable;
	
	@OneToMany(targetEntity=CostDataImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accrualCode")
	private Collection<CostData> costDatas = new ArrayList<CostData>();
	

	public AccrualCodeImpl() {
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}


	/**
	 * @param standard the standard to set
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}


	/**
	 * @return the costDatas
	 */
	public Collection<CostData> getCostDatas() {
		return costDatas;
	}


	/**
	 * @param costDatas the costDatas to set
	 */
	public void setCostDatas(Collection<CostData> costDatas) {
		this.costDatas = costDatas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		AccrualCodeImpl o = (AccrualCodeImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id},
				new Object[]{o.id})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(id)
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.appendSuper(super.toString())
		.toString();
	}

	/**
	 * @return the rcLineNumber
	 */
	public String getRcLineNumber() {
		return rcLineNumber;
	}

	/**
	 * @param rcLineNumber the rcLineNumber to set
	 */
	public void setRcLineNumber(String rcLineNumber) {
		this.rcLineNumber = rcLineNumber;
	}

	/**
	 * @return the reportable
	 */
	public StringBooleanEnum getReportable() {
		return reportable;
	}

	/**
	 * @param reportable the reportable to set
	 */
	public void setReportable(StringBooleanEnum reportable) {
		this.reportable = reportable;
	}


}
