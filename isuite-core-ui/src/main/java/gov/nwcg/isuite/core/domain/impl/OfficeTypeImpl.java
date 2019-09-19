/**
 * 
 */
package gov.nwcg.isuite.core.domain.impl;



import gov.nwcg.isuite.core.domain.OfficeType;
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

/**
 * Office type of an organizaton.
 * @author doug
 *
 */
@Entity
@SequenceGenerator(name="SEQ_OFFICE_TYPE", sequenceName="SEQ_OFFICE_TYPE")
@Table(name="isw_office_type")
public class OfficeTypeImpl extends PersistableImpl implements OfficeType {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_OFFICE_TYPE")
	private Long id = 0L;

	@Column(name="OT_NAME")
	private String name;


	/**
	 * Default constructor.
	 *
	 */
	public OfficeTypeImpl() {

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
	 * @see gov.nwcg.isuite.domain.access.impl.OfficeType#getName()
	 */
	public final String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.access.impl.OfficeType#setName(java.lang.String)
	 */
	public final void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name can not be null");
		}
		this.name = name;
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
		OfficeTypeImpl o = (OfficeTypeImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,name},
				new Object[]{o.id,o.name})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,name})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("name", name)
		.appendSuper(super.toString())
		.toString();
	}   

}
