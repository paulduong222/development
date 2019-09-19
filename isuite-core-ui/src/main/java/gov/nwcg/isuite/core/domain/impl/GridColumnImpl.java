package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.GridColumn;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.GridColumnTypeEnum;
import gov.nwcg.isuite.framework.types.GridNameEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_GRID_COLUMN", sequenceName="SEQ_GRID_COLUMN")
@Table(name = "isw_grid_column")
public class GridColumnImpl extends PersistableImpl implements GridColumn {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_GRID_COLUMN")
	private Long id = 0L;

	@Column(name = "GRID_NAME", length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private GridNameEnum gridName;

	@Column(name = "COLUMN_NAME", length = 25, nullable = false)
	private String columnName;

   @Column(name = "COLUMN_TYPE", nullable = false, length = 10)
   @Enumerated(EnumType.STRING)
   private GridColumnTypeEnum columnType;

	@Column(name = "COLUMN_WIDTH",nullable = false)
	private Integer columnWidth;

	@Column(name = "COLUMN_ALIGN", length = 10, nullable = true)
	private String columnAlignment;
	
	@Column(name = "PROPERTY_MSG_NAME", nullable = false, length=100)
	private String propertyMsgName;
	
   @Column(name="IS_DEFAULT")
   private Boolean isDefault;

	@Column(name = "DEFAULT_POSITION",nullable = false)
	private Integer defaultPosition;
   
	public GridColumnImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.Persistable#getId()
	 */
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.core.domain.GridColumn#getGridName()
	 */
	public GridNameEnum getGridName() {
		return gridName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.core.domain.GridColumn#setGridName(gov.nwcg.isuite.framework
	 * .types.GridNameEnum)
	 */
	public void setGridName(GridNameEnum gridName) {
		this.gridName = gridName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.core.domain.GridColumn#getColumnName()
	 */
	public String getColumnName() {
		return columnName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.core.domain.GridColumn#setColumnName(java.lang.String)
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.core.domain.GridColumn#getColumnType()
	 */
	public GridColumnTypeEnum getColumnType() {
		return columnType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.core.domain.GridColumn#setColumnType(gov.nwcg.isuite.
	 * framework.types.GridColumnTypeEnum)
	 */
	public void setColumnType(GridColumnTypeEnum columnType) {
		this.columnType = columnType;
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumn#getColumnWidth()
	 */
	public Integer getColumnWidth() {
		return columnWidth;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumn#setColumnWidth(java.lang.Integer)
	 */
	public void setColumnWidth(Integer columnWidth) {
		this.columnWidth = columnWidth;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumn#getColumnAlignment()
	 */
	public String getColumnAlignment() {
		return columnAlignment;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumn#setColumnAlignment(java.lang.String)
	 */
	public void setColumnAlignment(String columnAlign) {
		this.columnAlignment = columnAlign;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.core.domain.GridColumn#getPropertyMsgName()
	 */
	public String getPropertyMsgName() {
		return propertyMsgName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.core.domain.GridColumn#setPropertyMsgName(java.lang.String)
	 */
	public void setPropertyMsgName(String propertyMsgName) {
		this.propertyMsgName = propertyMsgName;
	}
	
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.GridColumn#isDefault()
    */
	public Boolean isDefault() {
      return isDefault;
   }

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.GridColumn#setDefault(java.lang.Boolean)
	 */
   public void setDefault(Boolean isDefault) {
      this.isDefault = isDefault;
   }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == null)return false;
		if (this == obj)return true;
		if (getClass() != obj.getClass())return false;
		GridColumnImpl o = (GridColumnImpl) obj;
		return new EqualsBuilder()
			.append(
					new Object[] { id, gridName, columnName, columnType, columnWidth, columnAlignment, propertyMsgName,isDefault},
					new Object[] { o.id, o.gridName, o.columnName, o.columnType, o.columnWidth,o.columnAlignment,o.propertyMsgName,o.isDefault})
			.appendSuper(super.equals(o)).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31, 33)
					.append(super.hashCode())
					.append(new Object[] { id, gridName, columnName, columnType, columnWidth,columnAlignment,propertyMsgName,isDefault })
					.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
					.append("id", id)
					.append("gridName", gridName)
					.append("columnName", columnName)
					.append("columnType", columnType)
					.append("columnWidth", columnWidth)
					.append("columnAlignment", columnAlignment)
					.append("propertyMsgName",propertyMsgName)
               .append("isDefault",isDefault)
					.appendSuper(super.toString())
					.toString();
	}

	public Integer getDefaultPosition() {
		return defaultPosition;
	}

	public void setDefaultPosition(Integer defaultPosition) {
		this.defaultPosition = defaultPosition;
	}


}
