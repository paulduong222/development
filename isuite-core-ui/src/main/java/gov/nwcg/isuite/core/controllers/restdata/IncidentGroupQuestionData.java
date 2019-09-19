package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentGroupQuestionVo;

public class IncidentGroupQuestionData extends DialogueData {
	private String questionType;
	private IncidentGroupQuestionVo incidentGroupQuestionVo;
	private Collection<IncidentGroupQuestionVo> incidentGroupQuestionVos;
	
	public IncidentGroupQuestionVo getIncidentGroupQuestionVo() {
		return incidentGroupQuestionVo;
	}
	
	public void setIncidentGroupQuestionVo(IncidentGroupQuestionVo incidentGroupQuestionVo) {
		this.incidentGroupQuestionVo = incidentGroupQuestionVo;
	}
	
	public Collection<IncidentGroupQuestionVo> getIncidentGroupQuestionVos() {
		return incidentGroupQuestionVos;
	}
	
	public void setIncidentGroupQuestionVos(Collection<IncidentGroupQuestionVo> incidentGroupQuestionVos) {
		this.incidentGroupQuestionVos = incidentGroupQuestionVos;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
}
