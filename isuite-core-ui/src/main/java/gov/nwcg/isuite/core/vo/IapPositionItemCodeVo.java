package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapPositionItemCode;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

import java.util.ArrayList;
import java.util.Collection;

public class IapPositionItemCodeVo extends AbstractVo implements PersistableVo {
	private Long incidentId;
	private Long incidentGroupId;
	private String position;
	private KindVo kindVo;
	private AgencyVo agencyVo;
	private String form;
	private String section;

	public IapPositionItemCodeVo(){
		
	}

	public static IapPositionItemCodeVo getInstance(IapPositionItemCode entity, Boolean cascadable) throws Exception {
		IapPositionItemCodeVo vo = new IapPositionItemCodeVo();
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			if(null != entity.getIncident())
				vo.setIncidentId(entity.getIncident().getId());
			if(null != entity.getIncidentGroup())
				vo.setIncidentGroupId(entity.getIncidentGroup().getId());
			vo.setPosition(entity.getPosition());
			vo.setForm(entity.getForm());
			vo.setSection(entity.getSection().name());

			if(null != entity.getKind()){
				vo.setKindVo(KindVo.getInstance(entity.getKind(), true));
			}
			
			if(null != entity.getAgency()){
				vo.setAgencyVo(AgencyVo.getInstance(entity.getAgency(), true));
			}
		}
		
		return vo;
	}
	
	public static Collection<IapPositionItemCodeVo> getInstances(Collection<IapPositionItemCode> entities, Boolean cascadable) throws Exception {
		Collection<IapPositionItemCodeVo> vos = new ArrayList<IapPositionItemCodeVo>();
		
		for(IapPositionItemCode entity : entities){
			vos.add(getInstance(entity,cascadable));
		}
		
		return vos;
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
	
	
	
}
