package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.GridColumnTypeEnum;
import gov.nwcg.isuite.framework.types.GridNameEnum;

public interface GridColumn extends Persistable {
	
   /**
    * Sets the grid name.
    * 
    * @param gridName 
    * 		the grid name to set
    */
   public void setGridName(GridNameEnum gridName);
   
   /**
    * Returns the grid name.
    * 
    * @return 
    * 		the grid name to return
    */
   public GridNameEnum getGridName();

   /**
    * Sets the column name.
    * 
    * @param columnName 
    * 		the column name to set
    */
   public void setColumnName(String columnName);
   
   /**
    * Returns the column name.
    * 
    * @return 
    * 		the column name to return
    */
   public String getColumnName();
 
   /**
    * Sets the column type.
    * 
    * @param type 
    * 		the column type to set
    */
   public void setColumnType(GridColumnTypeEnum columnType);
   
   /**
    * Returns the column type.
    * 
    * @return 
    * 		the column type to return
    */
   public GridColumnTypeEnum getColumnType();
   
   /**
    * Sets the property msg's name.
    * 
    * @param name 
    * 		the property msg's name to set
    */
   public void setPropertyMsgName(String name);
   
   /**
    * Returns the property msg's name.
    * 
    * @return 
    * 		the name to return
    */
   public String getPropertyMsgName();
  
   /**
    * Returns the column width.
    * 
    * @return 
    * 		the column width to return
    */
   public void setColumnWidth(Integer val);
   
   /**
    * Sets the column width.
    * 
    * @param name 
    * 		the column width to set
    */
   public Integer getColumnWidth();

   /**
    * Returns the column alignment.
    * 
    * @return 
    * 		the column alignment to return
    */
   public void setColumnAlignment(String val);
   
   /**
    * Sets the column alignment.
    * 
    * @param name 
    * 		the column alignment to set
    */
   public String getColumnAlignment();
   
   /**
    * Accessor for default flag.
    * @return the default flag, will not be null
    * @see #setDefault(Boolean)
    */
   public abstract Boolean isDefault();

   /**
    * Mutator for default.
    * @param default the flag to set if the field is a default or not, cannot be null
    * @see #isDefault()
    */
   public abstract void setDefault(Boolean isDefault);

	public Integer getDefaultPosition();

	public void setDefaultPosition(Integer defaultPosition);

}