package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IapPositionItemCode;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.IapSectionEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

public class IapPositionVo extends AbstractVo {
	private IncidentVo incidentVo;
	private IncidentGroupVo incidentGroupVo;
	private SectionVo sectionVo;
	private String form;
	private Collection<KindVo> itemCodeVos = new ArrayList<KindVo>();
	private String position;
	
	/**
	 * Constructor
	 */
	public IapPositionVo() {
		
	}
	
	/**
	 * @param entities
	 * @return
	 * @throws Exception
	 */
	public static IapPositionVo getInstance(Collection<IapPositionItemCode> entities) throws Exception {
		IapPositionVo vo = new IapPositionVo();
		
		for(IapPositionItemCode entity : entities) {
			
			vo.getItemCodeVos().add(KindVo.getInstance(entity.getKind(), true));
			vo.setSectionVo(IapSectionEnum.getSectionVoByCode(entity.getSection().getDescription()));
			vo.setPosition(entity.getPosition());
			
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapPositionVo> getInstances(Collection<IapPositionItemCode> entities) throws Exception {
		Collection<IapPositionVo> vos = new ArrayList<IapPositionVo>();
		
		for(IapPositionItemCode entity : entities) {
			IapPositionVo iapPositionVo = new IapPositionVo();
			Boolean bMatched = false;
			
			
			for(IapPositionVo vo : vos) {
				if(entity.getSection().getDescription().equals(vo.getSectionVo().getCode()) && entity.getPosition().equals(vo.getPosition())) {
					iapPositionVo = vo;
					bMatched = true;
					break;
				}
			}
			
			iapPositionVo.getItemCodeVos().add(KindVo.getInstance(entity.getKind(), true));
			
			if(!bMatched) {
				iapPositionVo.setSectionVo(IapSectionEnum.getSectionVoByCode(entity.getSection().getDescription()));
				iapPositionVo.setPosition(entity.getPosition());
				vos.add(iapPositionVo);
			}
		}
		
		return vos;
	}
	
	/**
	 * @param entities
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapPositionItemCode> toEntityList(Collection<IapPositionItemCode> entities, IapPositionVo vo) throws Exception {
		if(!CollectionUtility.hasValue(entities)) {
			entities = new ArrayList<IapPositionItemCode>();
		}
		
		//TODO - Finish 
		
		return entities;
	}
	
	/**
	 * @param incidentVo the incidentVo to set
	 */
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}

	/**
	 * @return the incidentVo
	 */
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}

	/**
	 * @param sectionVo the sectionVo to set
	 */
	public void setSectionVo(SectionVo sectionVo) {
		this.sectionVo = sectionVo;
	}

	/**
	 * @return the sectionVo
	 */
	public SectionVo getSectionVo() {
		return sectionVo;
	}

	/**
	 * @param itemCodeVos the itemCodeVos to set
	 */
	public void setItemCodeVos(Collection<KindVo> itemCodeVos) {
		this.itemCodeVos = itemCodeVos;
	}

	/**
	 * @return the itemCodeVos
	 */
	public Collection<KindVo> getItemCodeVos() {
		return itemCodeVos;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @return the incidentGroupVo
	 */
	public IncidentGroupVo getIncidentGroupVo() {
		return incidentGroupVo;
	}

	/**
	 * @param incidentGroupVo the incidentGroupVo to set
	 */
	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
		this.incidentGroupVo = incidentGroupVo;
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

}
