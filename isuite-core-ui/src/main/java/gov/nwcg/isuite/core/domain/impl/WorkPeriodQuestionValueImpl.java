package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue;
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
@SequenceGenerator(name="SEQ_WORK_PERIOD_QUESTION_VALUE", sequenceName="SEQ_WORK_PERIOD_QUESTION_VALUE")
@Table(name = "isw_work_period_question_value")
public class WorkPeriodQuestionValueImpl extends PersistableImpl implements WorkPeriodQuestionValue {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_WORK_PERIOD_QUESTION_VALUE")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=WorkPeriodImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "WORK_PERIOD_ID", insertable = true, updatable = true, nullable = false)
	private WorkPeriod workPeriod;
	
	@Column(name = "WORK_PERIOD_ID", length = 19, insertable=false, updatable=false, nullable=false)
	private Long workPeriodId;

	@ManyToOne(targetEntity=IncidentQuestionImpl.class,fetch = FetchType.EAGER)
	@JoinColumn(name = "INCIDENT_QUESTION_ID", insertable=true, updatable=true, nullable = false)
	private IncidentQuestion incidentQuestion;
	
	@Column(name = "INCIDENT_QUESTION_ID", length = 19, insertable=false, updatable=false, nullable=false)
	private Long incidentQuestionId;

	@Column(name = "QUESTION_VALUE",nullable=false)
	private Boolean questionValue;

	public WorkPeriodQuestionValueImpl() {
		super();
	}

	/* 
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.Persistable#getId()
	 */
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	} 

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue#getWorkPeriod()
	 */
	public WorkPeriod getWorkPeriod() {
		return workPeriod;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue#setWorkPeriod(gov.nwcg.isuite.core.domain.WorkPeriod)
	 */
	public void setWorkPeriod(WorkPeriod workPeriod) {
		this.workPeriod = workPeriod;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue#getWorkPeriodId()
	 */
	public Long getWorkPeriodId() {
		return workPeriodId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue#setWorkPeriodId(java.lang.Long)
	 */
	public void setWorkPeriodId(Long workPeriodId) {
		this.workPeriodId = workPeriodId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue#getIncidentQuestion()
	 */
	public IncidentQuestion getIncidentQuestion() {
		return incidentQuestion;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue#setIncidentQuestion(gov.nwcg.isuite.core.domain.IncidentQuestion)
	 */
	public void setIncidentQuestion(IncidentQuestion incidentQuestion) {
		this.incidentQuestion = incidentQuestion;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue#getIncidentQuestionId()
	 */
	public Long getIncidentQuestionId() {
		return incidentQuestionId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue#setIncidentQuestionId(java.lang.Long)
	 */
	public void setIncidentQuestionId(Long incidentQuestionId) {
		this.incidentQuestionId = incidentQuestionId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue#getQuestionValue()
	 */
	public Boolean getQuestionValue() {
		return questionValue;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue#setQuestionValue(java.lang.Boolean)
	 */
	public void setQuestionValue(Boolean questionValue) {
		this.questionValue = questionValue;
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
		WorkPeriodQuestionValueImpl o = (WorkPeriodQuestionValueImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,workPeriodId,incidentQuestionId,questionValue},
				new Object[]{o.id,o.workPeriodId,o.incidentQuestionId,o.questionValue})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,workPeriodId,incidentQuestionId,questionValue})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("workPeriodId", workPeriodId)
		.append("incidentQuestionId", incidentQuestionId)
		.append("questionValue", questionValue)
		.appendSuper(super.toString())
		.toString();
	}   
}
