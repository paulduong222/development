package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

import java.util.Collection;

public class IapPositionItemCodeWrapperVo extends AbstractVo implements PersistableVo {
	private Long incidentId;
	private Long incidentGroupId;
	private String position;
	private String form;
	private String section;
	private Collection<IapPositionItemCodeVo> iapPositionItemCodeVos;
	private Collection<KindVo> itemCodeVos;
	
	public IapPositionItemCodeWrapperVo(){
		
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

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the form
	 */
	public String getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(String form) {
		this.form = form;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * @return the iapPositionItemCodeVos
	 */
	public Collection<IapPositionItemCodeVo> getIapPositionItemCodeVos() {
		return iapPositionItemCodeVos;
	}

	/**
	 * @param iapPositionItemCodeVos the iapPositionItemCodeVos to set
	 */
	public void setIapPositionItemCodeVos(
			Collection<IapPositionItemCodeVo> iapPositionItemCodeVos) {
		this.iapPositionItemCodeVos = iapPositionItemCodeVos;
	}

	/**
	 * @return the itemCodeVos
	 */
	public Collection<KindVo> getItemCodeVos() {
		return itemCodeVos;
	}

	/**
	 * @param itemCodeVos the itemCodeVos to set
	 */
	public void setItemCodeVos(Collection<KindVo> itemCodeVos) {
		this.itemCodeVos = itemCodeVos;
	}
	
	
	
}
