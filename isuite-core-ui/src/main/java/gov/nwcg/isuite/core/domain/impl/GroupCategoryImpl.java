package gov.nwcg.isuite.core.domain.impl;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.GroupCategory;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

@Entity
@SequenceGenerator(name = "SEQ_GROUP_CATEGORY", sequenceName = "SEQ_GROUP_CATEGORY")
@Table(name = "iswl_group_category")
public class GroupCategoryImpl extends PersistableImpl implements GroupCategory {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GROUP_CATEGORY")
	private Long id = 0L;
   
	@Column(name = "CODE", length = 10, nullable=false)
	private String code;
   
	@Column(name = "DESCRIPTION", length = 75, nullable=false)
	private String description;
   
	@Column(name = "IS_STANDARD",nullable=false)
	private Boolean standard;
	
	@OneToMany(targetEntity=KindImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="groupCategory")
	private Collection<Kind> kinds;
	
	@Column(name = "IS_ACTIVE",nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum active;
	
	public GroupCategoryImpl() {
		super();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl#getId()
	 */
	public Long getId() {
		return this.id;
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
	 * @see gov.nwcg.isuite.core.domain.GroupCategory#getCode()
	 */
	public String getCode() {
		return this.code;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GroupCategory#getDescription()
	 */
	public String getDescription() {
		return this.description;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GroupCategory#isStandard()
	 */
	public Boolean isStandard() {
		return this.standard;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GroupCategory#setCode(java.lang.String)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GroupCategory#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GroupCategory#setStandard(java.lang.Boolean)
	 */
	public void setStandard(Boolean isStandard) {
		this.standard = isStandard;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GroupCategory#setKinds(java.util.Collection)
	 */
	public void setKinds(Collection<Kind> kinds) {
		this.kinds = kinds;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GroupCategory#getKinds()
	 */
	public Collection<Kind> getKinds() {
		return kinds;
	}
	
	/**
	 * @param active the active to set
	 */
	public void setActive(StringBooleanEnum active) {
		this.active = active;
	}
	
	/**
	 * @return the active
	 */
	public StringBooleanEnum isActive() {
		return active;
	}

}
