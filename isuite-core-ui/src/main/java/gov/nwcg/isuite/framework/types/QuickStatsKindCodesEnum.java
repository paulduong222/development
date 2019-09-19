package gov.nwcg.isuite.framework.types;

public enum QuickStatsKindCodesEnum {
   HC1("CREW, TYPE 1"),
   HC2("CREW, TYPE 2"),
   HC2I("CREW, TYPE 2 IA"),
   HCS1("STRIKE TEAM, CREW, TYPE 1"),
   HCS2("STRIKE TEAM, CREW, TYPE 2");
   
   private final String description;
   
   QuickStatsKindCodesEnum(String description) {
      this.description = description;
   }
   
   public String getDescription() {
      return this.description;
   }
}
