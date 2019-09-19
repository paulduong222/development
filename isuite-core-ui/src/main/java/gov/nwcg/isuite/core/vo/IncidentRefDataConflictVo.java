package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

public class IncidentRefDataConflictVo {
	private String incidentName;
	private String conflictDescription;

	private String sourceConflictingValue;
	private String sourceConflictingType;
	private String newValue;
	
	private KindVo kindVo;
	private KindVo newKindVo;
	
	private AgencyVo agencyVo;
	private AgencyVo newAgencyVo;
	
	public IncidentRefDataConflictVo(){
		
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the conflictDescrption
	 */
	public String getConflictDescription() {
		return conflictDescription;
	}

	/**
	 * @param conflictDescrption the conflictDescrption to set
	 */
	public void setConflictDescription(String conflictDescription) {
		this.conflictDescription = conflictDescription;
	}

	/**
	 * @return the kindVo
	 */
	public KindVo getKindVo() {
		return kindVo;
	}

	/**
	 * @param kindVo the kindVo to set
	 */
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
	}

	/**
	 * @return the newKindVo
	 */
	public KindVo getNewKindVo() {
		return newKindVo;
	}

	/**
	 * @param newKindVo the newKindVo to set
	 */
	public void setNewKindVo(KindVo newKindVo) {
		this.newKindVo = newKindVo;
	}

	/**
	 * @return the agencyVo
	 */
	public AgencyVo getAgencyVo() {
		return agencyVo;
	}

	/**
	 * @param agencyVo the agencyVo to set
	 */
	public void setAgencyVo(AgencyVo agencyVo) {
		this.agencyVo = agencyVo;
	}

	/**
	 * @return the newAgencyVo
	 */
	public AgencyVo getNewAgencyVo() {
		return newAgencyVo;
	}

	/**
	 * @param newAgencyVo the newAgencyVo to set
	 */
	public void setNewAgencyVo(AgencyVo newAgencyVo) {
		this.newAgencyVo = newAgencyVo;
	}

	/**
	 * @return the sourceConflictingValue
	 */
	public String getSourceConflictingValue() {
		return sourceConflictingValue;
	}

	/**
	 * @param sourceConflictingValue the sourceConflictingValue to set
	 */
	public void setSourceConflictingValue(String sourceConflictingValue) {
		this.sourceConflictingValue = sourceConflictingValue;
	}

	/**
	 * @return the sourceConflictingType
	 */
	public String getSourceConflictingType() {
		return sourceConflictingType;
	}

	/**
	 * @param sourceConflictingType the sourceConflictingType to set
	 */
	public void setSourceConflictingType(String sourceConflictingType) {
		this.sourceConflictingType = sourceConflictingType;
	}

	/**
	 * @return the newValue
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}


}
