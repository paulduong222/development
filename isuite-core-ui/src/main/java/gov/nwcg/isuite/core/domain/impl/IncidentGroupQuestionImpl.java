package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
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
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NamedQueries({
	@NamedQuery(name=IncidentQuestionQuery.GET_MAX_INCIDENT_GRP_QUESTION_POSITION,query=IncidentQuestionQuery.GET_MAX_INCIDENT_GRP_QUESTION_POSITION_QUERY)
})
@SequenceGenerator(name="SEQ_INCIDENT_GROUP_QUESTION", sequenceName="SEQ_INCIDENT_GROUP_QUESTION")
@Table(name = "isw_incident_group_question")
public class IncidentGroupQuestionImpl extends PersistableImpl implements IncidentGroupQuestion {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_GROUP_QUESTION")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=QuestionImpl.class,cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTION_ID", insertable=true, updatable=true, nullable = false)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)	
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	private Question question;
	
	@Column(name = "QUESTION_ID", length = 19, insertable=false, updatable=false, nullable=false)
	private Long questionId;

	@ManyToOne(targetEntity=IncidentGroupImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_GROUP_ID", insertable=true, updatable=true, nullable = true)
	private IncidentGroup incidentGroup;
	
	@Column(name = "INCIDENT_GROUP_ID", length = 19, insertable=false, updatable=false, nullable=true)
	private Long incidentGroupId;

   @Column(name = "POSITION", nullable = false)
   private Integer position;

	@Column(name = "IS_VISIBLE",nullable=false)
	private Boolean visible;

	public IncidentGroupQuestionImpl() {
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

	public IncidentGroup getIncidentGroup(){
		return incidentGroup;
	}
	
	public void setIncidentGroup(IncidentGroup incidentGroup){
		this.incidentGroup=incidentGroup;
	}
	
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
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
		IncidentGroupQuestionImpl o = (IncidentGroupQuestionImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,questionId,incidentGroupId,position,visible},
				new Object[]{o.id,o.questionId,o.incidentGroupId,o.position,o.visible})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,questionId,incidentGroupId,position,visible})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("questionId", questionId)
		.append("incidentGroupId", incidentGroupId)
      .append("position", position)
		.append("visible", visible)
		.appendSuper(super.toString())
		.toString();
	}   
}
