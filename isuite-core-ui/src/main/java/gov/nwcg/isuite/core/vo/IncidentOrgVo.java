package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentOrg;
import gov.nwcg.isuite.core.domain.impl.IncidentOrgImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class IncidentOrgVo extends AbstractVo implements PersistableVo {
	private IncidentVo incidentVo;
	private OrganizationVo organizationVo;
	private String type;
	
	public IncidentOrgVo() {
	}
	
	public static IncidentOrgVo getInstance(IncidentOrg entity, boolean cascadable) throws Exception {
		IncidentOrgVo vo = new IncidentOrgVo();
		
		if(null == entity)
			throw new Exception("Unable to create IncidentOrgVo from null IncidentOrg entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			
			if(null != entity.getIncident()) {
				vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), false));
			}
			
			if(null != entity.getOrganization()) {
				vo.setOrganizationVo(OrganizationVo.getInstance(entity.getOrganization(), false));
			}
			
			vo.setType(entity.getType());
		}
		
		return vo;
	}
	
	public static Collection<IncidentOrgVo> getInstances(Collection<IncidentOrg> entities, boolean cascadable) throws Exception {
		Collection<IncidentOrgVo> vos = new ArrayList<IncidentOrgVo>();
		
		for(IncidentOrg entity : entities) {
			vos.add(IncidentOrgVo.getInstance(entity, cascadable));
		}
		return vos;
	}
	
	public static IncidentOrg toEntity(IncidentOrg entity, IncidentOrgVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity)
			entity = new IncidentOrgImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			
			if(null != vo.getIncidentVo()) {
				entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(), false));
			}
			
			if(null != vo.getOrganizationVo()) {
				entity.setOrganization(OrganizationVo.toEntity(null, vo.getOrganizationVo(), false));
			}
			
			entity.setType(vo.getType());
		}
		
		return entity;
	}
	
	public static Collection<IncidentOrg> toEntityList(Collection<IncidentOrgVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IncidentOrg> entities = new ArrayList<IncidentOrg>();
		
		for(IncidentOrgVo vo : vos) {
			entities.add(IncidentOrgVo.toEntity(null, vo, cascadable, persistables));
		}
		
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
	 * @param organizationVo the organizationVo to set
	 */
	public void setOrganizationVo(OrganizationVo organizationVo) {
		this.organizationVo = organizationVo;
	}

	/**
	 * @return the organizationVo
	 */
	public OrganizationVo getOrganizationVo() {
		return organizationVo;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	
	
}
