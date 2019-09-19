package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupUser;
import gov.nwcg.isuite.core.domain.User;
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
@SequenceGenerator(name="SEQ_INCIDENT_GROUP_USER", sequenceName="SEQ_INCIDENT_GROUP_USER")
@Table(name="isw_incident_group_user")
public class IncidentGroupUserImpl extends PersistableImpl implements IncidentGroupUser {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_GROUP_USER")
	private Long id = 0L;

	@ManyToOne(targetEntity=UserImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", insertable=true, updatable=true, unique=false, nullable=false)
	private User user;

	@Column(name="USER_ID", length=19, insertable=false, updatable=false)
	private Long userId;

	@ManyToOne(targetEntity=IncidentGroupImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="INCIDENT_GROUP_ID", insertable=true, updatable=true, unique=false, nullable=false)
	private IncidentGroup incidentGroup;

	@Column(name="INCIDENT_GROUP_ID", length=19, insertable=false, updatable=false)
	private Long incidentGroupId;

//	@ManyToMany(targetEntity=SystemRoleImpl.class,cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	@JoinTable(name = "isw_inc_group_user_role", 
//			joinColumns = { 
//			@JoinColumn(name = "incident_group_user_id", nullable = false, updatable = false) }
//	, inverseJoinColumns = { 
//			@JoinColumn(name = "role_id", nullable = false, updatable = false) })
//			private Collection<SystemRole> incidentGroupUserRoles;

	public IncidentGroupUserImpl(){

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
	 * Returns the user.
	 *
	 * @return 
	 *		the user to return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user 
	 *			the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Returns the userId.
	 *
	 * @return 
	 *		the userId to return
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the userId.
	 *
	 * @param userId 
	 *			the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Returns the incidentGroup.
	 *
	 * @return 
	 *		the incidentGroup to return
	 */
	public IncidentGroup getIncidentGroup() {
		return incidentGroup;
	}

	/**
	 * Sets the incidentGroup.
	 *
	 * @param incidentGroup 
	 *			the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup) {
		this.incidentGroup = incidentGroup;
	}

	/**
	 * Returns the incidentGroupId.
	 *
	 * @return 
	 *		the incidentGroupId to return
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * Sets the incidentGroupId.
	 *
	 * @param incidentGroupId 
	 *			the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

//	/**
//	 * Returns the incidentGroupUserRoles.
//	 *
//	 * @return 
//	 *		the incidentGroupUserRoles to return
//	 */
//	public Collection<SystemRole> getIncidentGroupUserRoles() {
//		return incidentGroupUserRoles;
//	}

//	/**
//	 * Sets the incidentGroupUserRoles.
//	 *
//	 * @param incidentGroupUserRoles 
//	 *			the incidentGroupUserRoles to set
//	 */
//	public void setIncidentGroupUserRoles(
//			Collection<SystemRole> incidentGroupUserRoles) {
//		this.incidentGroupUserRoles = incidentGroupUserRoles;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		IncidentGroupUserImpl o = (IncidentGroupUserImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,userId,incidentGroupId},
				new Object[]{o.id,o.userId,o.incidentGroupId})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,userId,incidentGroupId})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("userId", userId)
		.append("incidentGroupId", incidentGroupId)
		.appendSuper(super.toString())
		.toString();
	}
	
}
