package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.SystemModule;
import gov.nwcg.isuite.core.domain.SystemModulePerm;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "isw_system_module")
@SequenceGenerator(name="SEQ_SYSTEM_MODULE", sequenceName="SEQ_SYSTEM_MODULE")
public class SystemModuleImpl extends PersistableImpl implements SystemModule {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SYSTEM_MODULE")
	private Long id = 0L;
	
	@Column(name = "name", unique = true, nullable = false, length = 20)
	private String name;
	
	@OneToMany(targetEntity=SystemModulePermImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systemModule")
	private Collection<SystemModulePerm> systemModulePerms = new ArrayList<SystemModulePerm>();

	public SystemModuleImpl(){
		
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
	 * Returns the name.
	 *
	 * @return 
	 *		the name to return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name 
	 *			the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the systemModulePerms.
	 *
	 * @return 
	 *		the systemModulePerms to return
	 */
	public Collection<SystemModulePerm> getSystemModulePerms() {
		return systemModulePerms;
	}

	/**
	 * Sets the systemModulePerms.
	 *
	 * @param systemModulePerms 
	 *			the systemModulePerms to set
	 */
	public void setSystemModulePerms(Collection<SystemModulePerm> systemModulePerms) {
		this.systemModulePerms = systemModulePerms;
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
		SystemModuleImpl o = (SystemModuleImpl)obj;
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
		.append(new Object[]{id})
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
	
}
