package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.domain.ProjectionItem;
import gov.nwcg.isuite.core.domain.impl.ProjectionImpl;
import gov.nwcg.isuite.core.domain.impl.ProjectionItemImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ProjectionItemVo extends AbstractVo implements PersistableVo {
	private KindVo kindVo;
	private Long itemId;
	private Long projectionId;
	private String costName;
	private Boolean isManuallyAdded;
	private Boolean isSupportCost;
	private Integer quantity;
	private BigDecimal averageCost;
	private BigDecimal totalCost;
	private Integer numberOfPersonnel;
	private ProjectionVo projectionVo;
	private Collection<ProjectionItemWorksheetVo> projectionItemWorksheetVos;
	private String itemCodeGroup;
	private Date lastModifiedDate;
	
	//@Transient 
	public String itemCode;
	
	public ProjectionItemVo() {
	}

	/**
	 * Returns a ProjectionItemVo instance from an ProjectionItem entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of ProjectionItemVo
	 * @throws Exception
	 */  
	public static ProjectionItemVo getInstance(ProjectionItem entity,boolean cascadable) throws Exception {
		ProjectionItemVo vo = new ProjectionItemVo();

		if(null == entity)
			throw new Exception("Unable to create ProjectionItemVo from null ProjectionItem entity.");

		vo.setId(entity.getId());

		vo.setCostName(entity.getCostName());
		vo.setQuantity(entity.getQuantity());
		vo.setAverageCost(entity.getAverageCost());
		vo.setNumberOfPersonnel(entity.getNumberOfPersonnel());
		vo.setIsManuallyAdded(StringBooleanEnum.toBooleanValue(entity.getIsManuallyAdded()));
		vo.setIsSupportCost(StringBooleanEnum.toBooleanValue(entity.getIsSupportCost()));
		vo.setTotalCost(entity.getTotalCost());
		vo.setLastModifiedDate(entity.getLastModifiedDate());
		//vo.setItemCodeGroup(entity.getItemCodeGroup());
		
		if(cascadable){
			if(null != entity.getProjection()){
				vo.setProjectionVo(ProjectionVo.getInstance(entity.getProjection(), false));
			}
			
			if(null != entity.getKind()){
				vo.setKindVo(KindVo.getInstance(entity.getKind(), true));
			}
			
			if(CollectionUtility.hasValue(entity.getProjectionItemWorksheets())){
				vo.setProjectionItemWorksheetVos(ProjectionItemWorksheetVo.getInstances(entity.getProjectionItemWorksheets(), false));
			}
		}
		
		return vo;
	}

	public static Collection<ProjectionItemVo> getInstances(Collection<ProjectionItem> entities, boolean cascadable) throws Exception {
		Collection<ProjectionItemVo> vos = new ArrayList<ProjectionItemVo>();

		for(ProjectionItem entity : entities){
			vos.add(ProjectionItemVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a ProjectionItem entity from a vo.
	 * 
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of ProjectionItem entity
	 * @throws Exception
	 */
	public static ProjectionItem toEntity(ProjectionItem entity, ProjectionItemVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity)
			entity=new ProjectionItemImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setCostName(vo.getCostName());
			entity.setQuantity(vo.getQuantity());
			entity.setAverageCost(vo.getAverageCost());
			entity.setNumberOfPersonnel(vo.getNumberOfPersonnel());
			entity.setIsManuallyAdded(StringBooleanEnum.toEnumValue(vo.getIsManuallyAdded()));
			entity.setIsSupportCost(StringBooleanEnum.toEnumValue(vo.getIsSupportCost()));
			entity.setTotalCost(vo.getTotalCost());
			entity.setLastModifiedDate(vo.getLastModifiedDate());
			//entity.setItemCodeGroup(vo.getItemCodeGroup());
			
			Projection projection = (Projection)AbstractVo.getPersistableObject(persistables, ProjectionImpl.class);
			if(null != projection)
				entity.setProjection(projection);
			/*
			if(null != vo.getProjectionVo()){
				entity.setProjection(ProjectionVo.toEntity(null,vo.getProjectionVo(), false));
			}
			*/
			if(null != vo.getKindVo()){
				entity.setKind(KindVo.toEntity(null,vo.getKindVo(), false));
			}
			
			if(CollectionUtility.hasValue(vo.getProjectionItemWorksheetVos())){
				entity.setProjectionItemWorksheets(ProjectionItemWorksheetVo.toEntities(vo.getProjectionItemWorksheetVos(), true));
			}

		}

		return entity;
	}

	public static Collection<ProjectionItem> toEntities(Collection<ProjectionItemVo> vos, boolean cascadable,Persistable...persistables) throws Exception {
		Collection<ProjectionItem> entities = new ArrayList<ProjectionItem>();

		for(ProjectionItemVo vo : vos){
			entities.add(ProjectionItemVo.toEntity(null, vo, cascadable,persistables));
		}

		return entities;
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
	 * @return the costName
	 */
	public String getCostName() {
		return costName;
	}

	/**
	 * @param costName the costName to set
	 */
	public void setCostName(String costName) {
		this.costName = costName;
	}

	/**
	 * @return the isManuallyAdded
	 */
	public Boolean getIsManuallyAdded() {
		return isManuallyAdded;
	}

	/**
	 * @param isManuallyAdded the isManuallyAdded to set
	 */
	public void setIsManuallyAdded(Boolean isManuallyAdded) {
		this.isManuallyAdded = isManuallyAdded;
	}

	/**
	 * @return the isSupportCost
	 */
	public Boolean getIsSupportCost() {
		return isSupportCost;
	}

	/**
	 * @param isSupportCost the isSupportCost to set
	 */
	public void setIsSupportCost(Boolean isSupportCost) {
		this.isSupportCost = isSupportCost;
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
	 * @return the projectionVo
	 */
	public ProjectionVo getProjectionVo() {
		return projectionVo;
	}

	/**
	 * @param projectionVo the projectionVo to set
	 */
	public void setProjectionVo(ProjectionVo projectionVo) {
		this.projectionVo = projectionVo;
	}

	/**
	 * @return the projectionItemWorksheetVos
	 */
	public Collection<ProjectionItemWorksheetVo> getProjectionItemWorksheetVos() {
		return projectionItemWorksheetVos;
	}

	/**
	 * @param projectionItemWorksheetVos the projectionItemWorksheetVos to set
	 */
	public void setProjectionItemWorksheetVos(
			Collection<ProjectionItemWorksheetVo> projectionItemWorksheetVos) {
		this.projectionItemWorksheetVos = projectionItemWorksheetVos;
	}

	public Long getProjectionId() {
		return projectionId;
	}

	public void setProjectionId(Long projectionId) {
		this.projectionId = projectionId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemCodeGroup() {
		return itemCodeGroup;
	}

	public void setItemCodeGroup(String itemCodeGroup) {
		this.itemCodeGroup = itemCodeGroup;
	}
	
	/*
	 * convenience method to tranform y/n to boolean
	 */
	public void setIsManuallyAddedString(String val){
		if(StringUtility.hasValue(val) && val.equals("Y"))
			this.isManuallyAdded=true;
		else
			this.isManuallyAdded=false;
	}
	
	
	/*
	 * convenience method to tranform y/n to boolean
	 */
	public void setIsSupportCostString(String val){
		if(StringUtility.hasValue(val) && val.equals("Y"))
			this.isSupportCost=true;
		else
			this.isSupportCost=false;
	}
	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
