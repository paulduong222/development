package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.SystemModulePerm;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.SystemRolePerm;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "isw_system_role_perm")
@SequenceGenerator(name="SEQ_SYSTEM_ROLE_PERM", sequenceName="SEQ_SYSTEM_ROLE_PERM")
public class SystemRolePermImpl extends PersistableImpl implements SystemRolePerm {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SYSTEM_ROLE_PERM")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=SystemModulePermImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "module_perm_id", insertable=true, updatable=true,nullable = false)
	private SystemModulePerm systemModulePerm;

	@Column(name="MODULE_PERM_ID", length=19, insertable = false, updatable = false, nullable = true)
	private Long systemModulePermId;
	
	@ManyToOne(targetEntity=SystemRoleImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", insertable = true, updatable=true,nullable = false)
	private SystemRole systemRole;
	
	@Column(name="ROLE_ID", length=19, insertable = false, updatable = false, nullable = true)
	private Long systemRoleId;
	
	@Column(name = "role_flag", nullable = false)
	private Boolean roleFlag;

	public SystemRolePermImpl(){
		
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
	 * Returns the systemModulePerm.
	 *
	 * @return 
	 *		the systemModulePerm to return
	 */
	public SystemModulePerm getSystemModulePerm() {
		return systemModulePerm;
	}

	/**
	 * Sets the systemModulePerm.
	 *
	 * @param systemModulePerm 
	 *			the systemModulePerm to set
	 */
	public void setSystemModulePerm(SystemModulePerm systemModulePerm) {
		this.systemModulePerm = systemModulePerm;
	}

	/**
	 * Returns the systemModulePermId.
	 *
	 * @return 
	 *		the systemModulePermId to return
	 */
	public Long getSystemModulePermId() {
		return systemModulePermId;
	}

	/**
	 * Sets the systemModulePermId.
	 *
	 * @param systemModulePermId 
	 *			the systemModulePermId to set
	 */
	public void setSystemModulePermId(Long systemModulePermId) {
		this.systemModulePermId = systemModulePermId;
	}

	/**
	 * Returns the systemRole.
	 *
	 * @return 
	 *		the systemRole to return
	 */
	public SystemRole getSystemRole() {
		return systemRole;
	}

	/**
	 * Sets the systemRole.
	 *
	 * @param systemRole 
	 *			the systemRole to set
	 */
	public void setSystemRole(SystemRole systemRole) {
		this.systemRole = systemRole;
	}

	/**
	 * Returns the systemRoleId.
	 *
	 * @return 
	 *		the systemRoleId to return
	 */
	public Long getSystemRoleId() {
		return systemRoleId;
	}

	/**
	 * Sets the systemRoleId.
	 *
	 * @param systemRoleId 
	 *			the systemRoleId to set
	 */
	public void setSystemRoleId(Long systemRoleId) {
		this.systemRoleId = systemRoleId;
	}

	/**
	 * Returns the roleFlag.
	 *
	 * @return 
	 *		the roleFlag to return
	 */
	public Boolean getRoleFlag() {
		return roleFlag;
	}

	/**
	 * Sets the roleFlag.
	 *
	 * @param roleFlag 
	 *			the roleFlag to set
	 */
	public void setRoleFlag(Boolean roleFlag) {
		this.roleFlag = roleFlag;
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
		SystemRolePermImpl o = (SystemRolePermImpl)obj;
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
