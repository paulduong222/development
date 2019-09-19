package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;

public interface QuestionFilter extends Filter {
	
	/**
	 * Returns the question type.
	 * 
	 * @return
	 * 		the question type to return
	 */
	public QuestionTypeEnum getQuestionType();
	
	/**
	 * Sets the question type.
	 * 
	 * @param type
	 * 			the question type to set
	 */
	public void setQuestionType(QuestionTypeEnum type);
}
