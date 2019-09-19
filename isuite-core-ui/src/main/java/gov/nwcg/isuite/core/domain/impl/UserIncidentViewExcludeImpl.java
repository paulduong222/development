package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserIncidentViewExclude;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import javax.persistence.CascadeType;
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
@SequenceGenerator(name="SEQ_USERINCVIEWEX", sequenceName="SEQ_USERINCVIEWEX")
@Table(name = "isw_user_incview_exclude")
public class UserIncidentViewExcludeImpl extends PersistableImpl implements UserIncidentViewExclude {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_USERINCVIEWEX")
	private Long id = 0L;

    @ManyToOne(targetEntity=UserImpl.class, fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name = "USER_ID", insertable = true, updatable = true, unique = false, nullable = true)
    private User user;
    
    @Column(name="USER_ID", insertable = false, updatable = false, nullable = true)
	private Long userId;
	
    @Column(name="INCIDENT_ID")
	private Long incidentId;

    @Column(name="INCIDENT_GROUP_ID")
	private Long incidentGroupId;
	
	public UserIncidentViewExcludeImpl(){

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

}
