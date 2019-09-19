package gov.nwcg.isuite.core.domain.impl;

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

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserResourceInventoryViewExclude;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_USERRESINVVIEWEX", sequenceName="SEQ_USERRESINVVIEWEX")
@Table(name = "isw_user_resinvview_exclude")
public class UserResourceInventoryViewExcludeImpl extends PersistableImpl implements UserResourceInventoryViewExclude {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_USERRESINVVIEWEX")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=UserImpl.class, fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name = "USER_ID", insertable = true, updatable = true, unique = false, nullable = true)
    private User user;
    
    @Column(name="USER_ID", insertable = false, updatable = false, nullable = true)
	private Long userId;
	
    @Column(name="RESOURCE_ID")
	private Long resourceId;

    public UserResourceInventoryViewExcludeImpl() {
    	
    }
    
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl#getId()
	 */
    public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.UserResourceInventoryViewExclude#getResourceId()
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.UserResourceInventoryViewExclude#getUser()
	 */
	public User getUser() {
		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.UserResourceInventoryViewExclude#getUserId()
	 */
	public Long getUserId() {
		return userId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.UserResourceInventoryViewExclude#setResourceId(java.lang.Long)
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.UserResourceInventoryViewExclude#setUser(gov.nwcg.isuite.core.domain.User)
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.UserResourceInventoryViewExclude#setUserId(java.lang.Long)
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
