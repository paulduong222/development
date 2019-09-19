package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.CustomReport;
import gov.nwcg.isuite.core.domain.CustomReportColumn;
import gov.nwcg.isuite.core.domain.impl.CustomReportColumnImpl;
import gov.nwcg.isuite.core.domain.impl.CustomReportImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.customreports.enumerators.FieldAggregatorTypeEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.FieldFormatterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class CustomReportColumnVo extends AbstractVo implements PersistableVo {
	
	private Long customReportId;
	private CustomReportViewFieldVo customReportViewFieldVo;
	private String header; // This is what the user can rename the displayName to, and what shows in the pdf/excel output.
	private Integer width = 50;	// Default width being set to 50
	private Integer displayOrder; 
	private CustomReportColumnAggregateVo aggregateVo;
	private CustomReportColumnFormatVo formatVo;
	private Integer sortBySequence;
	private String sortByType; 
	private Integer groupBySequence;
	private String groupByType;
	
	public static final String SORT_ORDER_ASCENDING_INSTANCE = "ASCENDING";
	public static final String SORT_ORDER_ASCENDING_ENTITY = "A";
	
	public static final String SORT_ORDER_DESCENDING_INSTANCE = "DESCENDING";
	public static final String SORT_ORDER_DESCENDING_ENTITY = "D";
	
	
	public CustomReportColumnVo() {
		super();
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static CustomReportColumnVo getInstance(CustomReportColumn entity, boolean cascadable) throws Exception {
		CustomReportColumnVo vo = new CustomReportColumnVo();
		
		if(null == entity)
			throw new Exception("Unable to create CustomReportColumnVo from null CustomReport entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			
			vo.setCustomReportId(entity.getCustomReportId());
			vo.setCustomReportViewFieldVo(CustomReportViewFieldVo.getInstance(entity.getCustomReportViewField(), cascadable));
			
			// Set the default header of the column based on the default display name of the field
			vo.setHeader(entity.getCaption()!=null?entity.getCaption():entity.getCustomReportViewField().getDisplayName());
			
			vo.setWidth(entity.getWidth().intValue());
			vo.setDisplayOrder(entity.getDisplayOrder().intValue());
			
			if(null != entity.getFormatType()) {
				vo.setFormatVo(FieldFormatterTypeEnum.getCRColomnFormatVo(entity.getFormatType()));
			}
			
			if(null != entity.getAggregateType()) {
				vo.setAggregateVo(FieldAggregatorTypeEnum.getCRColumnAggregateVo(entity.getAggregateType()));
			}
			
			vo.setSortBySequence(entity.getSortBySeq().intValue());
			if(StringUtility.hasValue(entity.getSortByType())) {
				vo.setSortByType(getInstanceSortByType(entity.getSortByType()));
			}
			
			vo.setGroupBySequence(entity.getGroupBySeq().intValue());
			vo.setGroupByType(entity.getGroupByType());
			
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<CustomReportColumnVo> getInstances(Collection<CustomReportColumn> entities, boolean cascadable) throws Exception {
		Collection<CustomReportColumnVo> vos = new ArrayList<CustomReportColumnVo>();
		
		for(CustomReportColumn entity : entities) {
			vos.add(CustomReportColumnVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	/**
	 * @param entity
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static CustomReportColumn toEntity(CustomReportColumn entity, CustomReportColumnVo vo, boolean cascadable,Persistable...persistables)throws Exception {
		if(null == entity){
			entity = new CustomReportColumnImpl();
		}
		
		entity.setId(vo.getId());

		if(cascadable){
			
			CustomReport customReport = (CustomReport)getPersistableObject(persistables,CustomReportImpl.class);
			if(null != customReport) {
				entity.setCustomReport(customReport);
			}
			
			//entity.setFieldName(vo.getFieldName());
			entity.setCustomReportViewField(CustomReportViewFieldVo.toEntity(vo.getCustomReportViewFieldVo(), cascadable));
			
			entity.setCaption(vo.getHeader());
			entity.setWidth(vo.getWidth().shortValue());
			entity.setDisplayOrder(vo.getDisplayOrder().shortValue());
			
			if(null != vo.getFormatVo()) {
				entity.setFormatType(vo.getFormatVo().getFormatType());
			}else{
				entity.setFormatType(null);
			}
			
			if(null != vo.getAggregateVo()) {
				entity.setAggregateType(vo.getAggregateVo().getAggregateType());
			}else{
				entity.setAggregateType(null);
			}
			
			entity.setSortBySeq(vo.getSortBySequence().shortValue());
			if(StringUtility.hasValue(vo.getSortByType())){
				entity.setSortByType(toEntitySortByType(vo.getSortByType()));
			}
			
			entity.setGroupBySeq(vo.getGroupBySequence().shortValue());
			entity.setGroupByType(vo.getGroupByType());
		}
		
		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<CustomReportColumn> toEntityList(Collection<CustomReportColumnVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<CustomReportColumn> entities = new ArrayList<CustomReportColumn>();
		
		for(CustomReportColumnVo vo : vos) {
			entities.add(CustomReportColumnVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	
	/**
	 * @param colVos
	 * @param cols
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static Collection<CustomReportColumn> toAddList(Collection<CustomReportColumnVo> colVos, Collection<CustomReportColumn> cols, CustomReport entity) throws Exception {
		
		Collection<CustomReportColumn> addList = new ArrayList<CustomReportColumn>();
		
		if(!CollectionUtility.hasValue(cols)){
			/*
			 * Add all
			 */
			for(CustomReportColumnVo colVo : colVos){
				CustomReportColumn colEntity=CustomReportColumnVo.toEntity(null, colVo, true, entity);
				addList.add(colEntity);
			}
			return addList;
		}
		
		for(CustomReportColumnVo vo : colVos) {
			Boolean bFound = false;
			
			if(CollectionUtility.hasValue(cols)) {
				for(CustomReportColumn col : cols) {
					if(col.getId().compareTo(vo.getId()) == 0) {
						bFound = true;
						break;
					}
				}
			}
			
			if(!bFound) {
				addList.add(CustomReportColumnVo.toEntity(null, vo, true, entity));
			}
		}
		
		return addList;
	}
	
	/**
	 * @param colVos
	 * @param cols
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static Collection<CustomReportColumn> toUpdateList(Collection<CustomReportColumnVo> colVos, Collection<CustomReportColumn> cols, CustomReport entity) throws Exception {
		
		Collection<CustomReportColumn> updateList = new ArrayList<CustomReportColumn>();
		
		if(!CollectionUtility.hasValue(cols))
			return updateList;
		
		for(CustomReportColumnVo vo : colVos) {
			for(CustomReportColumn col : cols) {
				if(col.getId().compareTo(vo.getId()) == 0) {
					col = CustomReportColumnVo.toEntity(col, vo, true, entity);
					updateList.add(col);
				}
			}
		}
		
		return updateList;
	}
	
	private static String getInstanceSortByType(String sortByTypeFromEntity) {
		if(sortByTypeFromEntity == null || sortByTypeFromEntity.equalsIgnoreCase(SORT_ORDER_DESCENDING_ENTITY)) 
			return SORT_ORDER_DESCENDING_INSTANCE;
		else 
			return SORT_ORDER_ASCENDING_INSTANCE;
	}
	
	private static String toEntitySortByType(String sortByTypeFromInstance) {
		if(sortByTypeFromInstance == null || sortByTypeFromInstance.equalsIgnoreCase(SORT_ORDER_DESCENDING_INSTANCE))
			return SORT_ORDER_DESCENDING_ENTITY;
		else 
			return SORT_ORDER_ASCENDING_ENTITY;
	}
	
	/**
	 * @param customReportId the customReportId to set
	 */
	public void setCustomReportId(Long customReportId) {
		this.customReportId = customReportId;
	}

	/**
	 * @return the customReportId
	 */
	public Long getCustomReportId() {
		return customReportId;
	}
	
	/**
	 * @return the customReportViewFieldVo
	 */
	public CustomReportViewFieldVo getCustomReportViewFieldVo() {
		return customReportViewFieldVo;
	}

	/**
	 * @param customReportViewFieldVo the customReportViewFieldVo to set
	 */
	public void setCustomReportViewFieldVo(
			CustomReportViewFieldVo customReportViewFieldVo) {
		this.customReportViewFieldVo = customReportViewFieldVo;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}


	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}


	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}


	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return the displayOrder
	 */
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param aggregateVo the aggregateVo to set
	 */
	public void setAggregateVo(CustomReportColumnAggregateVo aggregateVo) {
		this.aggregateVo = aggregateVo;
	}

	/**
	 * @return the aggregateVo
	 */
	public CustomReportColumnAggregateVo getAggregateVo() {
		return aggregateVo;
	}

	/**
	 * @param formatVo the formatVo to set
	 */
	public void setFormatVo(CustomReportColumnFormatVo formatVo) {
		this.formatVo = formatVo;
	}

	/**
	 * @return the formatVo
	 */
	public CustomReportColumnFormatVo getFormatVo() {
		return formatVo;
	}

	/**
	 * @param sortBySequence the sortBySequence to set
	 */
	public void setSortBySequence(Integer sortBySequence) {
		this.sortBySequence = sortBySequence;
	}

	/**
	 * @return the sortBySequence
	 */
	public Integer getSortBySequence() {
		return sortBySequence;
	}

	/**
	 * @param sortByType the sortByType to set
	 */
	public void setSortByType(String sortByType) {
		this.sortByType = sortByType;
	}

	/**
	 * @return the sortByType
	 */
	public String getSortByType() {
		return sortByType;
	}

	/**
	 * @param groupBySequence the groupBySequence to set
	 */
	public void setGroupBySequence(Integer groupBySequence) {
		this.groupBySequence = groupBySequence;
	}

	/**
	 * @return the groupBySequence
	 */
	public Integer getGroupBySequence() {
		return groupBySequence;
	}

	/**
	 * @param groupByType the groupByType to set
	 */
	public void setGroupByType(String groupByType) {
		this.groupByType = groupByType;
	}

	/**
	 * @return the groupByType
	 */
	public String getGroupByType() {
		return groupByType;
	}

}
