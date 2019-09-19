package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.hibernate.query.SystemParameterQuery;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@NamedQueries({
	@NamedQuery(name=SystemParameterQuery.FIND_BY_PARAMETER_NAME,query=SystemParameterQuery.FIND_BY_PARAMETER_NAME_QUERY)
})
@SequenceGenerator(name="SEQ_SYSTEM_PARAMETER", sequenceName="SEQ_SYSTEM_PARAMETER")
@Table(name="isw_system_parameter")
public class SystemParameterImpl extends PersistableImpl implements SystemParameter {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SYSTEM_PARAMETER")
	private Long id = 0L;

	@Column(name="PARAMETER_NAME",length=50)
	private String parameterName;

	@Column(name="PARAMETER_VALUE",length=255)
	private String parameterValue;

	public SystemParameterImpl(){

	}

	/**
	 * Returns the id.
	 *
	 * @return 
	 *		the id to return
	 */
	public Long getId() {
		return id;
	}


	/**
	 * Sets the id.
	 *
	 * @param id 
	 *			the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * Returns the parameterName.
	 *
	 * @return 
	 *		the parameterName to return
	 */
	public String getParameterName() {
		return parameterName;
	}


	/**
	 * Sets the parameterName.
	 *
	 * @param parameterName 
	 *			the parameterName to set
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}


	/**
	 * Returns the parameterValue.
	 *
	 * @return 
	 *		the parameterValue to return
	 */
	public String getParameterValue() {
		return parameterValue;
	}


	/**
	 * Sets the parameterValue.
	 *
	 * @param parameterValue 
	 *			the parameterValue to set
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
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
		SystemParameterImpl o = (SystemParameterImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,parameterName,parameterValue},
				new Object[]{o.id,o.parameterName,o.parameterValue})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,parameterName,parameterValue})
				.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("parameterName", parameterName)
		.append("parameterValue", parameterValue)
		.appendSuper(super.toString())
		.toString();
	}




}