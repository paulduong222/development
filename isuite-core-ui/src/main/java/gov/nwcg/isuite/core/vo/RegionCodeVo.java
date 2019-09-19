package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.RegionCode;
import gov.nwcg.isuite.core.domain.impl.RegionCodeImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class RegionCodeVo extends AbstractVo implements PersistableVo {
   
   private String code;
   private String description;

	public static RegionCodeVo getInstance(RegionCode entity,boolean cascadable,Persistable... persistableObjects) throws Exception {
		RegionCodeVo vo = new RegionCodeVo();

		if(null == entity)
			throw new Exception("Unable to get RegionCodeVo instance from null RegionCode entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setCode(entity.getCode());
			vo.setDescription(entity.getDescription());
		}

		return vo;
	}

	/**
	 * @param entity
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static RegionCode toEntity(RegionCode entity, RegionCodeVo vo,boolean cascadable,Persistable... persistables) throws Exception {
		if(null == entity)
			entity = new RegionCodeImpl();

		entity.setId(vo.getId());
		if(cascadable){
		}

		return entity;
	}
   
   
   /**
    * @return the code
    */
   public String getCode() {
      return code;
   }
   /**
    * @param code the code to set
    */
   public void setCode(String code) {
      this.code = code;
   }
   /**
    * @return the description
    */
   public String getDescription() {
      return description;
   }
   /**
    * @param description the description to set
    */
   public void setDescription(String description) {
      this.description = description;
   }
}
