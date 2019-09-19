package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentQuestionVo;

public class IncidentQuestionData extends DialogueData {

	private String questionType;
	private IncidentQuestionVo incidentQuestionVo;
	private Collection<IncidentQuestionVo> incidentQuestionVos;

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public Collection<IncidentQuestionVo> getIncidentQuestionVos() {
		return incidentQuestionVos;
	}

	public void setIncidentQuestionVos(Collection<IncidentQuestionVo> incidentQuestionVos) {
		this.incidentQuestionVos = incidentQuestionVos;
	}
	
	public IncidentQuestionVo getIncidentQuestionVo() {
		return incidentQuestionVo;
	}
	
	public void setIncidentQuestionVo(IncidentQuestionVo incidentQuestionVo) {
		this.incidentQuestionVo = incidentQuestionVo;
	}
}
