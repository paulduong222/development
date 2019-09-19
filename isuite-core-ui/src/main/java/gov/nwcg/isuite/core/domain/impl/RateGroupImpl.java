package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.RateGroup;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

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

@Entity
@SequenceGenerator(name="SEQ_RATE_GROUP", sequenceName="SEQ_RATE_GROUP")
@Table(name = "iswl_rate_group")
public class RateGroupImpl extends PersistableImpl implements RateGroup {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RATE_GROUP")
	private Long id = 0L;
   
	@Column(name = "CODE", length = 10, nullable=false)
	private String code;
   
	@Column(name = "DESCRIPTION", length = 75, nullable=false)
	private String description;
   
	@Column(name = "IS_STANDARD",nullable=false)
	private Boolean standard;
	
	@OneToMany(targetEntity=AgencyImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rateGroup")
	private Collection<Agency> agencies;
	
	public RateGroupImpl() {
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
	 * @see gov.nwcg.isuite.core.domain.RateGroup#getCode()
	 */
	public String getCode() {
		return this.code;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.RateGroup#getDescription()
	 */
	public String getDescription() {
		return this.description;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.RateGroup#isStandard()
	 */
	public Boolean isStandard() {
		return this.standard;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.RateGroup#setCode(java.lang.String)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.RateGroup#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.RateGroup#setStandard(java.lang.Boolean)
	 */
	public void setStandard(Boolean isStandard) {
		this.standard = isStandard;
	}

	public Collection<Agency> getAgencies() {
		return agencies;
	}

	public void setAgencies(Collection<Agency> agencies) {
		this.agencies = agencies;
	}

}
