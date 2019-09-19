package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.Question;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentQuestionQuery;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NamedQueries({
	@NamedQuery(name=IncidentQuestionQuery.GET_MAX_INCIDENT_QUESTION_POSITION,query=IncidentQuestionQuery.GET_MAX_INCIDENT_QUESTION_POSITION_QUERY)
})
@SequenceGenerator(name="SEQ_INCIDENT_QUESTION", sequenceName="SEQ_INCIDENT_QUESTION")
@Table(name = "isw_incident_question")
public class IncidentQuestionImpl extends PersistableImpl implements IncidentQuestion {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_QUESTION")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=QuestionImpl.class,cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name = "QUESTION_ID", insertable=true, updatable=true, nullable = false)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)	
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	@BatchSize(size=100)
	private Question question;
	
	@Column(name = "QUESTION_ID", length = 19, insertable=false, updatable=false, nullable=false)
	private Long questionId = 0L;

	@ManyToOne(targetEntity=IncidentImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID", insertable=true, updatable=true, nullable = true)
	private Incident incident;
	
	@Column(name = "INCIDENT_ID", length = 19, insertable=false, updatable=false, nullable=true)
	private Long incidentId = 0L;

   @Column(name = "POSITION", nullable = false)
   private Integer position;

	@Column(name = "IS_VISIBLE",nullable=false)
	private Boolean visible;

	public IncidentQuestionImpl() {
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
	 * @see gov.nwcg.isuite.core.domain.IncidentQuestion#getQuestion()
	 */
	public Question getQuestion() {
		return question;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.IncidentQuestion#setQuestion(gov.nwcg.isuite.core.domain.Question)
	 */
	public void setQuestion(Question question){
		this.question=question;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.IncidentQuestion#getQuestionId()
	 */
	public Long getQuestionId() {
		return questionId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.IncidentQuestion#setQuestionId(java.lang.Long)
	 */
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.IncidentQuestion#getIncident()
	 */
	public Incident getIncident(){
		return incident;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.IncidentQuestion#setIncident(gov.nwcg.isuite.core.domain.Incident)
	 */
	public void setIncident(Incident incident){
		this.incident=incident;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.IncidentQuestion#getIncidentId()
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.IncidentQuestion#setIncidentId(java.lang.Long)
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.IncidentQuestion#getPosition()
	 */
   public Integer getPosition(){
      return position;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentQuestion#setPosition(java.lang.Integer)
    */
   public void setPosition(Integer position){
      this.position=position;
   }
   
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.IncidentQuestion#isVisible()
	 */
	public Boolean isVisible() {
		return visible;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.IncidentQuestion#setVisible(java.lang.Boolean)
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
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		IncidentQuestionImpl o = (IncidentQuestionImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,questionId,incidentId,position,visible},
				new Object[]{o.id,o.questionId,o.incidentId,o.position,o.visible})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,questionId,incidentId,position,visible})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("questionId", questionId)
		.append("incidentId", incidentId)
      .append("position", position)
		.append("visible", visible)
		.appendSuper(super.toString())
		.toString();
	}   
}
