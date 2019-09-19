package gov.nwcg.isuite.framework.types;

/**
 * @author bsteiner
 *
 */
public enum ResourceRequestCategoryEnum {
   A("Aircraft"),
   C("Crews"),
   E("Equipment"),
   I("Initial Attack"),
   O("Overhead"),
   S("Supply");
//   P("PERSONNEL");
   
   private String description;
   
   private ResourceRequestCategoryEnum(String description) {
      this.description = description;
   }

   /**
    * @return the description
    */
   public String getDescription() {
      return description;
   }
}
