package gov.nwcg.isuite.core.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.DailyForm;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_DAILY_FORM", sequenceName="SEQ_DAILY_FORM")
@Table(name = "iswl_daily_form")
public class DailyFormImpl extends PersistableImpl implements DailyForm {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_DAILY_FORM")
	private Long id = 0L;
   
	@Column(name = "CODE", length = 10, nullable=false)
	private String code;
   
	@Column(name = "DESCRIPTION", length = 75, nullable=false)
	private String description;
   
	@Column(name = "IS_STANDARD",nullable=false)
	private Boolean standard;
	
	public DailyFormImpl() {
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
	 * @see gov.nwcg.isuite.core.domain.DailyForm#getCode()
	 */
	public String getCode() {
		return this.code;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.DailyForm#getDescription()
	 */
	public String getDescription() {
		return this.description;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.DailyForm#isStandard()
	 */
	public Boolean isStandard() {
		return this.standard;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.DailyForm#setCode(java.lang.String)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.DailyForm#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.DailyForm#setStandard(java.lang.Boolean)
	 */
	public void setStandard(Boolean isStandard) {
		this.standard = isStandard;
	}

}
