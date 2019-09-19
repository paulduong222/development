package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

public class QuickStatsVo {
	private Long incidentId;
	private Long incidentGroupId;
	private Collection<QuickStatsResourceVo> quickStatsResourceVos = new ArrayList<QuickStatsResourceVo>();
	private Collection<QuickStatsResourceTypeVo> quickStatsResourceTypeVos = new ArrayList<QuickStatsResourceTypeVo>();
	
	private QuickStatsTotalsVo qsTotalsVo = new QuickStatsTotalsVo();
	
	public QuickStatsVo(){
		   
	}

	/**
	 * Returns the quickStatsResourceVos.
	 *
	 * @return 
	 *		the quickStatsResourceVos to return
	 */
	public Collection<QuickStatsResourceVo> getQuickStatsResourceVos() {
		return quickStatsResourceVos;
	}

	/**
	 * Sets the quickStatsResourceVos.
	 *
	 * @param quickStatsResourceVos 
	 *			the quickStatsResourceVos to set
	 */
	public void setQuickStatsResourceVos(Collection<QuickStatsResourceVo> quickStatsResourceVos) {
		this.quickStatsResourceVos = quickStatsResourceVos;
	}

	/**
	 * Returns the quickStatsResourceTypeVos.
	 *
	 * @return 
	 *		the quickStatsResourceTypeVos to return
	 */
	public Collection<QuickStatsResourceTypeVo> getQuickStatsResourceTypeVos() {
		return quickStatsResourceTypeVos;
	}

	/**
	 * Sets the quickStatsResourceTypeVos.
	 *
	 * @param quickStatsResourceTypeVos 
	 *			the quickStatsResourceTypeVos to set
	 */
	public void setQuickStatsResourceTypeVos(Collection<QuickStatsResourceTypeVo> quickStatsResourceTypeVos) {
		this.quickStatsResourceTypeVos = quickStatsResourceTypeVos;
	}

	/**
	 * @return the qsTotalsVo
	 */
	public QuickStatsTotalsVo getQsTotalsVo() {
		return qsTotalsVo;
	}

	/**
	 * @param qsTotalsVo the qsTotalsVo to set
	 */
	public void setQsTotalsVo(QuickStatsTotalsVo qsTotalsVo) {
		this.qsTotalsVo = qsTotalsVo;
	}

	public void addQuickStatResourceVos(Collection<QuickStatsResourceVo> qsrVos) {
		this.quickStatsResourceVos.addAll(qsrVos);
	}
	
	public void addQuickStatsResourceTypeVos(Collection<QuickStatsResourceTypeVo> qsrtVos) {
		this.quickStatsResourceTypeVos.addAll(qsrtVos);
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	   
}
