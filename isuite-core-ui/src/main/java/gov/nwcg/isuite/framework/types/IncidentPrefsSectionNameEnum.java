package gov.nwcg.isuite.framework.types;

/**
 * @author mpoll
 *
 */

/*
 *
 * 
TODO: Aug 8, 2014
 
In future, IAP_204_BLOCK6("IAP_204_BLOCK6") should be updated to 
IAP_204_BLOCK5("IAP_204_BLOCK5") along with the following SQL update patch:

UPDATE isw_incident_prefs
   SET section_name='IAP_204_BLOCK5'
 WHERE section_name='IAP_204_BLOCK6';
 
UPDATE isw_incident_group_prefs
   SET section_name='IAP_204_BLOCK5'
 WHERE section_name='IAP_204_BLOCK6';
 
 *
 *
 *
*/

public enum IncidentPrefsSectionNameEnum {
   OTHER_LABEL("OTHER_LABEL")
   ,LOGISTICS("LOGISTICS")
   ,PLANNING("PLANNING")
   ,FINANCE("FINANCE")
   ,ICS221_OTHER("ICS221_OTHER")
   ,IAP_204_BLOCK6("IAP_204_BLOCK6") 
   ;
   
   private String description = "";

   IncidentPrefsSectionNameEnum(String desc) {
      this.description = desc;
   }

   public String getDescription() {
      return this.description;
   }

}
