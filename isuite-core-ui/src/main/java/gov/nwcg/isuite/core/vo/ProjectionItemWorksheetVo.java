package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.ProjectionItemWorksheet;
import gov.nwcg.isuite.core.domain.impl.ProjectionItemWorksheetImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ProjectionItemWorksheetVo extends AbstractVo implements PersistableVo {
	private Date projectionDate;
	private Integer quantity;
	private BigDecimal averageCost;
	private Integer numberOfPersonnel;
	private ProjectionItemVo projectionItemVo;

	public ProjectionItemWorksheetVo() {
	}

	/**
	 * Returns a ProjectionItemWorksheetVo instance from an ProjectionItemWorksheet entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of ProjectionItemWorksheetVo
	 * @throws Exception
	 */
	public static ProjectionItemWorksheetVo getInstance(ProjectionItemWorksheet entity,boolean cascadable) throws Exception {
		ProjectionItemWorksheetVo vo = new ProjectionItemWorksheetVo();

		if(null == entity)
			throw new Exception("Unable to create ProjectionItemWorksheetVo from null ProjectionItemWorksheet entity.");

		vo.setId(entity.getId());
		vo.setProjectionDate(entity.getProjectionDate());
		vo.setQuantity(entity.getQuantity());
		vo.setAverageCost(entity.getAverageCost());
		vo.setNumberOfPersonnel(entity.getNumberOfPersonnel());
		
		if(cascadable){
			if(null != entity.getProjectionItem())
				vo.setProjectionItemVo(ProjectionItemVo.getInstance(entity.getProjectionItem(), false));
		
		}

		return vo;
	}

	public static Collection<ProjectionItemWorksheetVo> getInstances(Collection<ProjectionItemWorksheet> entities, boolean cascadable) throws Exception {
		Collection<ProjectionItemWorksheetVo> vos = new ArrayList<ProjectionItemWorksheetVo>();

		for(ProjectionItemWorksheet entity : entities){
			vos.add(ProjectionItemWorksheetVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a ProjectionItemWorksheet entity from a vo.
	 * 
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of ProjectionItemWorksheet entity
	 * @throws Exception
	 */
	public static ProjectionItemWorksheet toEntity(ProjectionItemWorksheet entity, ProjectionItemWorksheetVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity)
			entity=new ProjectionItemWorksheetImpl();

		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setProjectionDate(vo.getProjectionDate());
			entity.setQuantity(vo.getQuantity());
			entity.setAverageCost(vo.getAverageCost());
			entity.setNumberOfPersonnel(vo.getNumberOfPersonnel());
			
			if(null != vo.getProjectionItemVo())
				entity.setProjectionItem(ProjectionItemVo.toEntity(null,vo.getProjectionItemVo(), true));
		}

		return entity;
	}

	public static Collection<ProjectionItemWorksheet> toEntities(Collection<ProjectionItemWorksheetVo> vos, boolean cascadable) throws Exception {
		Collection<ProjectionItemWorksheet> entities = new ArrayList<ProjectionItemWorksheet>();

		for(ProjectionItemWorksheetVo vo : vos){
			entities.add(ProjectionItemWorksheetVo.toEntity(null,vo, cascadable));
		}

		return entities;
	}

	
	/**
	 * @return the projectionDate
	 */
	public Date getProjectionDate() {
		return projectionDate;
	}

	/**
	 * @param projectionDate the projectionDate to set
	 */
	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the averageCost
	 */
	public BigDecimal getAverageCost() {
		return averageCost;
	}

	/**
	 * @param averageCost the averageCost to set
	 */
	public void setAverageCost(BigDecimal averageCost) {
		this.averageCost = averageCost;
	}

	/**
	 * @return the numberOfPersonnel
	 */
	public Integer getNumberOfPersonnel() {
		return numberOfPersonnel;
	}

	/**
	 * @param numberOfPersonnel the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Integer numberOfPersonnel) {
		this.numberOfPersonnel = numberOfPersonnel;
	}

	/**
	 * @return the projectionItemVo
	 */
	public ProjectionItemVo getProjectionItemVo() {
		return projectionItemVo;
	}

	/**
	 * @param projectionItemVo the projectionItemVo to set
	 */
	public void setProjectionItemVo(ProjectionItemVo projectionItemVo) {
		this.projectionItemVo = projectionItemVo;
	}

}
