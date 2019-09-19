package gov.nwcg.isuite.framework.types;

public enum QuickStatsSit209CodesEnum {
   D("Dozer"),
   E("Engines"),
   ES("Engines ST"),
   H1("Helicopter Type 1"),
   H2("Helicopter Type 2"),
   H3("Helicopter Type 3"),
   H4("Helicopter Type 4"),
   W("Water Tender");
   
   private final String description;
   
   QuickStatsSit209CodesEnum(String description) {
      this.description = description;
   }
   
   public String getDescription() {
      return this.description;
   }
}
