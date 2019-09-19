package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface SpecialPay extends Persistable {

   /**
    * Sets the code.
    * 
    * @param code 
    * 		the special pay code to set
    */
   public void setCode(String code);
   
   /**
    * Returns the special pay code.
    * 
    * @return 
    * 		the special pay code to return
    */
   public String getCode();

   /**
    * Sets the description.
    * 
    * @param description 
    * 		the special pay code description to set
    */
   public void setDescription(String description);
   
   /**
    * Returns the special pay code description.
    * 
    * @return 
    * 		the special pay code description to return
    */
   public String getDescription();
   
   /**
    * Returns whether or not the special pay code is a standard system code.
    * 
    * @return 
    * 		the special pay code standard status
    */
   public Boolean isStandard();
   
   /**
    * Sets whether or not the special pay code is a standard system code.
    * 
    * @param isStandard
    * 			the special pay code standard status
    */
   public void setStandard(Boolean isStandard);

   public Boolean getAvailableToAd();

   public void setAvailableToAd(Boolean availableToAd);

   public Boolean getAvailableToFed();

   public void setAvailableToFed(Boolean availableToFed);
   
   public Boolean getAvailableToOther();

   public void setAvailableToOther(Boolean availableToOther);
   
   public void setOrdinalValue(Integer ordinalValue);

   public Integer getOrdinalValue();
}