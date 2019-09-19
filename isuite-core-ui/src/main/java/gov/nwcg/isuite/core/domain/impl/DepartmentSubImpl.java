package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.DepartmentSub;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_DEPARTMENT_SUB", sequenceName="SEQ_DEPARTMENT_SUB")
@Table(name = "iswl_department_sub")
public class DepartmentSubImpl extends PersistableImpl implements DepartmentSub {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_DEPARTMENT_SUB")
	private Long id = 0L;

	@Column(name = "CODE", length = 10, nullable=false)
	private String code;

	@Column(name = "DESCRIPTION", length = 75, nullable=false)
	private String description;

	@Column(name = "IS_STANDARD",nullable=false)
	private Boolean standard;

	@Column(name="DEPARTMENT_ID")
	private Long departmentId;

	public DepartmentSubImpl() {
		super();
	}

	/* 
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.Persistable#getId()
	 */
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	} 


	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.DepartmentSub#setCode(java.lang.String)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.DepartmentSub#getCode()
	 */
	public String getCode() {
		return this.code;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.DepartmentSub#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.DepartmentSub#getDescription()
	 */
	public String getDescription() {
		return this.description;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.DepartmentSub#isStandard()
	 */
	public Boolean isStandard() {
		return this.standard;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.DepartmentSub#setStandard(java.lang.Boolean)
	 */
	public void setStandard(Boolean isStandard) {
		this.standard = isStandard;
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
		DepartmentSubImpl impl = (DepartmentSubImpl)obj;
		return new EqualsBuilder()
		.appendSuper(super.equals(obj))
		.append(id,impl.id)
		.append(code, impl.code)
		.append(description, impl.description)
		.append(standard, impl.standard)
		.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(id)
		.append(code)
		.append(description)
		.append(standard)
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("code", code)
		.append("description", description)
		.append("standard", standard)
		.appendSuper(super.toString())
		.toString();
	}

	/**
	 * @return the departmentId
	 */
	public Long getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}   
}
