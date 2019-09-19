package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.QuestionVo;

public class QuestionData {

	private QuestionVo questionVo;
	private Collection<QuestionVo> questionVos;

	public QuestionVo getQuestionVo() {
		return questionVo;
	}

	public void setQuestionVo(QuestionVo questionVo) {
		this.questionVo = questionVo;
	}

	public Collection<QuestionVo> getQuestionVos() {
		return questionVos;
	}

	public void setQuestionVos(Collection<QuestionVo> questionVos) {
		this.questionVos = questionVos;
	}

}
