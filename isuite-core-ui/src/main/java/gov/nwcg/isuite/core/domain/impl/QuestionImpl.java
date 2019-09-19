package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.Question;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;

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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_QUESTION", sequenceName="SEQ_QUESTION")
@Table(name = "isw_question")
public class QuestionImpl extends PersistableImpl implements Question {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_QUESTION")
	private Long id = 0L;

	@Column(name = "QUESTION_TYPE", length = 15, nullable=false)
    @Enumerated(EnumType.STRING)
	private QuestionTypeEnum questionType;

	@Column(name = "QUESTION", length = 65, nullable=false)
	private String question;
	
	@Column(name = "IS_STANDARD",nullable=false)
	private Boolean standard;

	@OneToMany(targetEntity=IncidentQuestionImpl.class,fetch = FetchType.LAZY, mappedBy = "question")
	private Collection<IncidentQuestion> incidentQuestions = new ArrayList<IncidentQuestion>();
	
	@OneToMany(targetEntity=IncidentGroupQuestionImpl.class,fetch = FetchType.LAZY, mappedBy = "question")
	private Collection<IncidentGroupQuestion> incidentGroupQuestions = new ArrayList<IncidentGroupQuestion>();
	
	public QuestionImpl() {
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
	 * @see gov.nwcg.isuite.core.domain.Question#getQuestionType()
	 */
	public QuestionTypeEnum getQuestionType() {
		return questionType;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Question#setQuestionType(gov.nwcg.isuite.framework.types.QuestionTypeEnum)
	 */
	public void setQuestionType(QuestionTypeEnum questionType) {
		this.questionType = questionType;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Question#getQuestion()
	 */
	public String getQuestion() {
		return question;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Question#setQuestion(java.lang.String)
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Question#isStandard()
	 */
	public Boolean isStandard() {
		return this.standard;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Question#setStandard(java.lang.Boolean)
	 */
	public void setStandard(Boolean isStandard) {
		this.standard = isStandard;
	}    

	/**
	 * Returns the incidentQuestions.
	 *
	 * @return 
	 *		the incidentQuestions to return
	 */
	public Collection<IncidentQuestion> getIncidentQuestions() {
		return incidentQuestions;
	}

	/**
	 * Sets the incidentQuestions.
	 *
	 * @param incidentQuestions 
	 *			the incidentQuestions to set
	 */
	public void setIncidentQuestions(Collection<IncidentQuestion> incidentQuestions) {
		this.incidentQuestions = incidentQuestions;
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
		QuestionImpl o = (QuestionImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,questionType,question,standard},
				new Object[]{o.id,o.questionType,o.question,o.standard})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,questionType,question,standard})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("questionType", questionType)
		.append("question", question)
		.append("standard", standard)
		.appendSuper(super.toString())
		.toString();
	}

	/**
	 * @return the incidentGroupQuestions
	 */
	public Collection<IncidentGroupQuestion> getIncidentGroupQuestions() {
		return incidentGroupQuestions;
	}

	/**
	 * @param incidentGroupQuestions the incidentGroupQuestions to set
	 */
	public void setIncidentGroupQuestions(
			Collection<IncidentGroupQuestion> incidentGroupQuestions) {
		this.incidentGroupQuestions = incidentGroupQuestions;
	}


}
