package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;



/**
 * 
 * @author dprice
 */
public class TravelMethodVo extends AbstractVo {
   private String code;
   private String displayName;
   private String description;
   
   public TravelMethodVo() {
      super();
   }

   public TravelMethodVo(Long id,String code,String displayName, String desc) {
      super();
      if (id != null) {
          setId(id);
      }
      if (code != null) {
         setCode(code);
      }
      if (desc != null) {
         setDescription(desc);
      }
      setDisplayName(displayName);
   }

   public static TravelMethodTypeEnum toEnum(String code){
	   return TravelMethodTypeEnum.valueOf(code);
   }
   
   /**
    * Returns the Code.
    * 
    * @return
    * 		the code to return
    */
   public String getCode() {
	   return code;
   }

   /**
    * Sets the code.
    * 
    * @param code
    * 			the code to set
    */
   public void setCode(String code) {
	   this.code=code;
   }

   /**
    * Returns the description.
    * 
    * @return
    * 		the description to return
    */
   public String getDescription() {
	   return description;
   }
   
   /**
    * Sets the Description.
    * 
    * @param description
    * 			the description to set
    */
   public void setDescription(String description) {
	   this.description = description;
   }

public String getDisplayName() {
	return displayName;
}

public void setDisplayName(String displayName) {
	this.displayName = displayName;
}

   
}
