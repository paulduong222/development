package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.core.domain.impl.EntryImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.input.InputDataSource;
import gov.nwcg.isuite.framework.input.UpdateDataTypeEnum;
import gov.nwcg.isuite.framework.types.UpdateStateEnum;

import java.util.Calendar;

public class EntryVo extends AbstractVo implements PersistableVo {


	   /* (non-Javadoc)
	    * @see gov.nwcg.isuite.framework.core.vo.PersistableVo#toEntity()
	    */
	   public Entry toEntity(Persistable entity) throws Exception {
		   return populateEntity(this, ((Entry)entity));
	   }
	   
	   /**
	    * Populates and returns an entity object with the values from the vo object.
	    * 
	    * @param vo
	    * 			the source vo object
	    * @param entity
	    * 			the entity to populate and return
	    * @return
	    * 		the entity to return
	    */
	   private static Entry populateEntity(EntryVo vo, Entry entity) {
		   if(null==entity){
			   entity = new EntryImpl();
		   }
		   
		   entity.setId(vo.getId());
		   
		   return entity;
	   }

   public Calendar getCompleteTimeStamp() {
	      return null;
   }

   public byte [] getData() {
      // TODO Auto-generated method stub
      return null;
   }

   public Calendar getInitialTimeStamp() {
      // TODO Auto-generated method stub
      return null;
   }

   public Calendar getProcessTimeStamp() {
      // TODO Auto-generated method stub
      return null;
   }

   public InputDataSource getSource() {
      // TODO Auto-generated method stub
      return null;
   }

   public String getUniqueIdentifier() {
      // TODO Auto-generated method stub
      return null;
   }

   public UpdateDataTypeEnum getUpdateDataType() {
      // TODO Auto-generated method stub
      return null;
   }

   public UpdateStateEnum getUpdateState() {
      // TODO Auto-generated method stub
      return null;
   }

   public void setUpdateState(UpdateStateEnum updateState) {
      // TODO Auto-generated method stub

   }

}
