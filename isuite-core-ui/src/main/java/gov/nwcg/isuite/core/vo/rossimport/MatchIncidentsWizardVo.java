package gov.nwcg.isuite.core.vo.rossimport;

import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

public class MatchIncidentsWizardVo {
	
	/*
	 * 		Incident Information from the ross import file
	 * 		Collection of possible eisuite incidents that match the ross incident
	 *		Projected best guess matching eisuite incident vo
	 */
	private IncidentVo rossIncidentVo;
	private Collection<IncidentVo> eisuiteIncidents = new ArrayList<IncidentVo>();
	private IncidentVo projectedMatchIncidentVo;
	private Long matchingIncidentId;

	
	public MatchIncidentsWizardVo(){
		
	}

	public static Boolean hasEISuiteIncidentMatch(MatchIncidentsWizardVo vo) {
		
		if(null != vo){
			if(LongUtility.hasValue(vo.getMatchingIncidentId()))
				return true;
		}
		return false;
	}

	public static Long getEISuiteIncidentId(MatchIncidentsWizardVo vo) {
		
		if(null != vo){
			if(LongUtility.hasValue(vo.getMatchingIncidentId()))
				return vo.getMatchingIncidentId();
		}
		
		return 0L;
	}
	
	/**
	 * @return the rossIncidentVo
	 */
	public IncidentVo getRossIncidentVo() {
		return rossIncidentVo;
	}


	/**
	 * @param rossIncidentVo the rossIncidentVo to set
	 */
	public void setRossIncidentVo(IncidentVo rossIncidentVo) {
		this.rossIncidentVo = rossIncidentVo;
	}


	/**
	 * @return the eisuiteIncidents
	 */
	public Collection<IncidentVo> getEisuiteIncidents() {
		return eisuiteIncidents;
	}


	/**
	 * @param eisuiteIncidents the eisuiteIncidents to set
	 */
	public void setEisuiteIncidents(Collection<IncidentVo> eisuiteIncidents) {
		this.eisuiteIncidents = eisuiteIncidents;
	}


	/**
	 * @return the projectedMatchIncidentVo
	 */
	public IncidentVo getProjectedMatchIncidentVo() {
		return projectedMatchIncidentVo;
	}


	/**
	 * @param projectedMatchIncidentVo the projectedMatchIncidentVo to set
	 */
	public void setProjectedMatchIncidentVo(IncidentVo projectedMatchIncidentVo) {
		this.projectedMatchIncidentVo = projectedMatchIncidentVo;
	}


	/**
	 * @return the matchingIncidentId
	 */
	public Long getMatchingIncidentId() {
		return matchingIncidentId;
	}


	/**
	 * @param matchingIncidentId the matchingIncidentId to set
	 */
	public void setMatchingIncidentId(Long matchingIncidentId) {
		this.matchingIncidentId = matchingIncidentId;
	}
	
}
