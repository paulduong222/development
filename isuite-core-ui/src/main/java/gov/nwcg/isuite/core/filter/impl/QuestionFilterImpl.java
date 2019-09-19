package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.QuestionFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;

public class QuestionFilterImpl extends FilterImpl implements QuestionFilter {
	private QuestionTypeEnum questionType;
	

	/**
	 * Returns the questionType.
	 *
	 * @return 
	 *		the questionType to return
	 */
	public QuestionTypeEnum getQuestionType() {
		return questionType;
	}

	/**
	 * Sets the questionType.
	 *
	 * @param questionType 
	 *			the questionType to set
	 */
	public void setQuestionType(QuestionTypeEnum questionType) {
		this.questionType = questionType;
	}

	
	/**
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static Collection<FilterCriteria> getFilterCriteria(QuestionFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
		
		criteria.add( null != filter.getQuestionType() ?  new FilterCriteria("this.questionType",filter.getQuestionType(),FilterCriteria.TYPE_EQUAL): null);
		
		return criteria;
	}
	
}
