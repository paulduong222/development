package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class CostGroupAgencyDayShareGridVo extends AbstractVo {
	private Collection<CostGroupAgencyDayShareGridVo> children = new ArrayList<CostGroupAgencyDayShareGridVo>();
	private Date agencyShareDate;
	private String agency;
	private String percentage;
	private Long parentId;
	
	public CostGroupAgencyDayShareGridVo() {
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<CostGroupAgencyDayShareGridVo> getHierarchicalInstances(Collection<CostGroupAgencyDayShare> entities, CostGroupAgencyDayShareFilter filter, boolean cascadable) throws Exception {
		Collection<CostGroupAgencyDayShareGridVo> vos = new ArrayList<CostGroupAgencyDayShareGridVo>();
		
		for(CostGroupAgencyDayShare entity : entities) {
			
			vos.add(CostGroupAgencyDayShareGridVo.getHierarchicalInstance(entity, filter, cascadable));
		}
		
		return vos;
	}
	
	public static CostGroupAgencyDayShareGridVo getHierarchicalInstance(CostGroupAgencyDayShare entity, CostGroupAgencyDayShareFilter filter, boolean cascadable) throws Exception {
		CostGroupAgencyDayShareGridVo vo = new CostGroupAgencyDayShareGridVo();
		
		if(null == entity)
			throw new Exception("Unable to create CostGroupAgencyDayShareGridVo from null CostGroupAgencyDayShare entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			if(null != entity.getAgencyShareDate()) {
				vo.setAgencyShareDate(entity.getAgencyShareDate());
			}
			
			Collection<CostGroupAgencyDayShareGridVo> costGroupAgencyDayShareGridVos = getHierarchicalChildInstances(entity, filter, cascadable);
			
			if(CollectionUtility.hasValue(costGroupAgencyDayShareGridVos)) {
				vo.setChildren(costGroupAgencyDayShareGridVos);
			}
		} 
		
		return vo;	
	}
	
	private static Collection<CostGroupAgencyDayShareGridVo> getHierarchicalChildInstances(CostGroupAgencyDayShare entity, CostGroupAgencyDayShareFilter filter, boolean cascadable) throws Exception {
		Collection<CostGroupAgencyDayShareGridVo> costGroupAgencyDayShareGridVos = new ArrayList<CostGroupAgencyDayShareGridVo>();
		
		for(CostGroupAgencyDaySharePercentage percentEntity : entity.getCostGroupAgencyDaySharePercentages()) {
			Boolean addRecord = true;
			if(null != filter){
				if(StringUtility.hasValue(filter.getAgency())) {
					if(!percentEntity.getAgency().getAgencyCode().toUpperCase().startsWith(filter.getAgency().toUpperCase())) {
						addRecord = false;
					}
				}
				if(StringUtility.hasValue(filter.getPercentage())) {
					if(!percentEntity.getPercentage().toString().startsWith(filter.getPercentage())) {
						addRecord = false;
					}
				}
			} 
			
			if(addRecord) {
				costGroupAgencyDayShareGridVos.add(getHierarchicalChildInstance(entity.getId(), percentEntity, cascadable));
			}
		}
		
		return costGroupAgencyDayShareGridVos;
	}
	
	private static CostGroupAgencyDayShareGridVo getHierarchicalChildInstance(Long parentId, CostGroupAgencyDaySharePercentage entity, boolean cascadable) throws Exception {
		CostGroupAgencyDayShareGridVo vo = new CostGroupAgencyDayShareGridVo();
		
		if(null == entity)
			throw new Exception("Unable to create CostGroupAgencyDayShareGridVo from null CostGroupAgencyDaySharePercent entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setParentId(parentId);
			
			if(null != entity.getAgency()) {
				vo.setAgency(entity.getAgency().getAgencyCode());
			}
			if(null != entity.getPercentage()) {
				vo.setPercentage(entity.getPercentage().toString() + "%");
			}
		}
		
		return vo;
	}
	
	/**
	 * @return the children
	 */
	public Collection<CostGroupAgencyDayShareGridVo> getChildren() {
		return children;
	}
	
	/**
	 * @param children
	 */
	public void setChildren(Collection<CostGroupAgencyDayShareGridVo> children) {
		this.children = children;
	}
	
	/**
	 * @return the agencyShareDate
	 */
	public Date getAgencyShareDate() {
		return agencyShareDate;
	}
	
	/**
	 * @param agencyShareDate
	 */
	public void setAgencyShareDate(Date agencyShareDate) {
		this.agencyShareDate = agencyShareDate;
	}
	
	/**
	 * @return the agency
	 */
	public String getAgency() {
		return agency;
	}
	
	/**
	 * @param agency
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}
	
	/**
	 * @return the percentage
	 */
	public String getPercentage() {
		return percentage;
	}
	
	/**
	 * @param percentage
	 */
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}
	
	/**
	 * @param parentId
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
