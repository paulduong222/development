package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.SystemModule;
import gov.nwcg.isuite.core.domain.SystemModulePerm;
import gov.nwcg.isuite.core.domain.SystemRolePerm;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "isw_system_module_perm")
@SequenceGenerator(name="SEQ_SYSTEM_MODULE_PERM", sequenceName="SEQ_SYSTEM_MODULE_PERM")
public class SystemModulePermImpl extends PersistableImpl implements SystemModulePerm {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SYSTEM_MODULE_PERM")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=SystemModuleImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id", insertable = true, updatable=true,nullable = false)
	private SystemModule systemModule;
	
	@Column(name="MODULE_ID", length=19, insertable = false, updatable = false, nullable = true)
	private Long systemModuleId;
	
	@Column(name = "permission_key", unique = true, nullable = false, length = 125)
	private String permissionKey;

	@Column(name = "description")
	private String description;
	
	@OneToMany(targetEntity=SystemRolePermImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "systemModulePerm")
	private Collection<SystemRolePerm> systemRolePerms = new ArrayList<SystemRolePerm>();

	public SystemModulePermImpl(){
		
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
	 * Returns the systemModule.
	 *
	 * @return 
	 *		the systemModule to return
	 */
	public SystemModule getSystemModule() {
		return systemModule;
	}

	/**
	 * Sets the systemModule.
	 *
	 * @param systemModule 
	 *			the systemModule to set
	 */
	public void setSystemModule(SystemModule systemModule) {
		this.systemModule = systemModule;
	}

	/**
	 * Returns the systemModuleId.
	 *
	 * @return 
	 *		the systemModuleId to return
	 */
	public Long getSystemModuleId() {
		return systemModuleId;
	}

	/**
	 * Sets the systemModuleId.
	 *
	 * @param systemModuleId 
	 *			the systemModuleId to set
	 */
	public void setSystemModuleId(Long systemModuleId) {
		this.systemModuleId = systemModuleId;
	}

	/**
	 * Returns the permissionKey.
	 *
	 * @return 
	 *		the permissionKey to return
	 */
	public String getPermissionKey() {
		return permissionKey;
	}

	/**
	 * Sets the permissionKey.
	 *
	 * @param permissionKey 
	 *			the permissionKey to set
	 */
	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}

	/**
	 * Returns the description.
	 *
	 * @return 
	 *		the description to return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description 
	 *			the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the systemRolePerms.
	 *
	 * @return 
	 *		the systemRolePerms to return
	 */
	public Collection<SystemRolePerm> getSystemRolePerms() {
		return systemRolePerms;
	}

	/**
	 * Sets the systemRolePerms.
	 *
	 * @param systemRolePerms 
	 *			the systemRolePerms to set
	 */
	public void setSystemRolePerms(Collection<SystemRolePerm> systemRolePerms) {
		this.systemRolePerms = systemRolePerms;
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
		SystemModulePermImpl o = (SystemModulePermImpl)obj;
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
