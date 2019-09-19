package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.GridColumn;
import gov.nwcg.isuite.core.domain.GridColumnUser;
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
import org.hibernate.annotations.BatchSize;

@Entity
@SequenceGenerator(name="SEQ_GRID_COLUMN_USER", sequenceName="SEQ_GRID_COLUMN_USER")
@Table(name = "isw_grid_column_user")
public class GridColumnUserImpl extends PersistableImpl implements GridColumnUser {

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GRID_COLUMN_USER")
	@Column(name = "ID", length = 19)
	private Long id = 0L;

    //@ManyToOne(targetEntity=gov.nwcg.isuite.core.domain.impl.GridColumnImpl.class,fetch=FetchType.LAZY)
    @ManyToOne(targetEntity=gov.nwcg.isuite.core.domain.impl.GridColumnImpl.class)
    @JoinColumn(name = "GRID_COLUMN_ID", insertable = true, updatable = true, unique = false, nullable = false)
    @BatchSize(size=200)
	private GridColumn gridColumn;
	
	@Column(name = "GRID_COLUMN_ID", length = 19, updatable=false, insertable=false, nullable = false)
	private Long gridColumnId;

	@Column(name = "POSITION", nullable = false)
	private Integer position;

    @ManyToOne(targetEntity=gov.nwcg.isuite.core.domain.impl.UserImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private User user;
	
	@Column(name = "USER_ID", nullable = false, length = 19, updatable=false, insertable=false)
	private Long userId;

	@Column(name = "IS_VISIBLE",nullable=false)
	private Boolean visible;
	
	public GridColumnUserImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.Persistable#getId()
	 */
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumnUser#getGridColumnId()
	 */
	public Long getGridColumnId() {
		return gridColumnId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumnUser#setGridColumnId(java.lang.Long)
	 */
	public void setGridColumnId(Long gridColumnId) {
		this.gridColumnId = gridColumnId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumnUser#getGridColumn()
	 */
	public GridColumn getGridColumn() {
		return gridColumn;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumnUser#setGridColumn(GridColumn)
	 */
	public void setGridColumn(GridColumn gridColumn) {
		this.gridColumn = gridColumn;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumnUser#getPosition()
	 */
	public Integer getPosition() {
		return position;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumnUser#setPosition(java.lang.Integer)
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumnUser#getUserId()
	 */
	public Long getUserId() {
		return userId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumnUser#setUserId(java.lang.Long)
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumnUser#getUser()
	 */
	public User getUser() {
		return user;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumnUser#setUser(gov.nwcg.isuite.core.domain.User)
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumnUser#isVisible()
	 */
	public Boolean isVisible() {
		return visible;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumnUser#setVisible(java.lang.Boolean)
	 */
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == null)return false;
		if (this == obj)return true;
		if (getClass() != obj.getClass())return false;
		GridColumnUserImpl o = (GridColumnUserImpl) obj;
		return new EqualsBuilder()
			.append(
					new Object[] { id, gridColumnId, position, userId,visible},
					new Object[] { o.id, o.gridColumnId, o.position, o.userId,o.visible})
			.appendSuper(super.equals(o)).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31, 33)
					.append(super.hashCode())
					.append(new Object[] { id, gridColumnId, position, userId,visible})
					.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
					.append("id", id)
					.append("gridColumnId", gridColumnId)
					.append("position", position)
					.append("userId", userId)
					.append("visible", visible)
					.appendSuper(super.toString())
					.toString();
	}

}
