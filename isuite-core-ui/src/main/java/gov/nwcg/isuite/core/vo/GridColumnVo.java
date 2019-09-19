package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.GridColumn;
import gov.nwcg.isuite.core.domain.impl.GridColumnImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.GridColumnTypeEnum;
import gov.nwcg.isuite.framework.types.GridNameEnum;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

public class GridColumnVo extends AbstractVo implements PersistableVo {
	private GridNameEnum gridName;
	private String columnName;
	private GridColumnTypeEnum columnType;
	private Integer columnWidth;
	private String columnAlignment;
	private String propertyMsgName;
	private Boolean isDefault;
	private Integer defaultPosition;

	public GridColumnVo(){
		super();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public GridColumn toEntity(Persistable entity) throws Exception {
		return null;
	}

	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static GridColumnVo getInstance(GridColumn entity,boolean cascadable) throws Exception {
		GridColumnVo vo = new GridColumnVo();

		if(null==entity)
			throw new Exception("");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setGridName(entity.getGridName());
			vo.setColumnName(entity.getColumnName());
			vo.setColumnType(entity.getColumnType());
			vo.setColumnWidth(entity.getColumnWidth());
			vo.setColumnAlignment(entity.getColumnAlignment());
			vo.setPropertyMsgName(entity.getPropertyMsgName());
			vo.setIsDefault(entity.isDefault());
			vo.setDefaultPosition(entity.getDefaultPosition());
		}

		return vo;
	}

	public static Collection<GridColumnVo> getInstances(Collection<GridColumn> entities,boolean cascadable) throws Exception {
		Collection<GridColumnVo> vos = new ArrayList<GridColumnVo>();

		for(GridColumn entity : entities){
			vos.add(GridColumnVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static GridColumn toEntity(GridColumnVo vo, boolean cascadable, Persistable...persistables ) throws Exception {
		GridColumn entity = new GridColumnImpl();

		if(null==vo)
			throw new Exception("Unable to create GridColumn entity from null GridColumnVo.");

		entity.setId(vo.getId());

		if(cascadable){
			entity.setColumnName(vo.getColumnName());
			entity.setColumnType(vo.getColumnType());
			entity.setGridName(vo.getGridName());
			entity.setPropertyMsgName(vo.getPropertyMsgName());
			entity.setColumnWidth(vo.getColumnWidth());
			entity.setColumnAlignment(vo.getColumnAlignment());
			entity.setDefault(vo.getIsDefault());
			entity.setDefaultPosition(vo.getDefaultPosition());

			validateEntity(entity);
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
	public static Collection<GridColumn> toEntityList(Collection<GridColumnVo> vos, boolean cascadable, Persistable...persistables ) throws Exception {
		Collection<GridColumn> entities = new ArrayList<GridColumn>();

		for(GridColumnVo vo : vos){
			entities.add(GridColumnVo.toEntity(vo,false));
		}
		return entities;
	}

	/**
	 * Perform some validation on the GridColumn entity field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source GridColumn entity
	 * @throws ValidationException
	 */
	private static void validateEntity(GridColumn entity) throws ValidationException {
		Validator.validateStringField("gridName",entity.getGridName().toString(),20,true);
		Validator.validateStringField("columnName", entity.getColumnName(), 25, true);
		Validator.validateStringField("columnType", entity.getColumnType().toString(), 10, true);
		Validator.validateStringField("propertyMsgName", entity.getPropertyMsgName(), 100, true);
		Validator.validateIntegerField("columnWidth", entity.getColumnWidth(), true);
		Validator.validateStringField("columnAlignment", entity.getColumnAlignment(),10, false);
		Validator.validateBooleanField("isDefault", entity.isDefault(),true);
	}


	public GridNameEnum getGridName() {
		return gridName;
	}

	public void setGridName(GridNameEnum gridName) {
		this.gridName = gridName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public GridColumnTypeEnum getColumnType() {
		return columnType;
	}

	public void setColumnType(GridColumnTypeEnum columnType) {
		this.columnType = columnType;
	}

	public Integer getColumnWidth(){
		return columnWidth;
	}

	public void setColumnWidth(Integer val){
		this.columnWidth=val;
	}

	public String getColumnAlignment(){
		return columnAlignment;
	}

	public void setColumnAlignment(String val){
		this.columnAlignment=val;
	}

	public String getPropertyMsgName() {
		return propertyMsgName;
	}

	public void setPropertyMsgName(String propertyMsgName) {
		this.propertyMsgName = propertyMsgName;
	}

	/**
	 * Returns the isDefault flag.
	 *
	 * @return 
	 *    the is Default flag to return
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}

	/**
	 * Sets the isDefault flag.
	 *
	 * @param isDefault 
	 *       the isDefault flag to set
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getDefaultPosition() {
		return defaultPosition;
	}

	public void setDefaultPosition(Integer defaultPosition) {
		this.defaultPosition = defaultPosition;
	}

}
