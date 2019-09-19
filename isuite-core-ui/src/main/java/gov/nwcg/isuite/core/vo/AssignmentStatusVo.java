package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;



/**
 * 
 * @author dprice
 */
public class AssignmentStatusVo extends AbstractVo{

   private String code;
   private String description;
   
   public AssignmentStatusVo() {
      super();
   }

   public AssignmentStatusVo(Long id,String code, String desc) {
      super();
      setId(id);
  	  setCode(code);
   	  setDescription(desc);
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

   
}
