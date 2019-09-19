package gov.nwcg.isuite.framework.types;

/**
 * @author bsteiner
 *
 */
public enum ResourceStatusTypeEnum {
   INTRANSIT(""),
   AVAILABLE(""),
   OTHER(""),
   CHECKIN("C"),
   PENDING("P");
   
   private final String code;
   private ResourceStatusTypeEnum(String code) {
      this.code = code;
   }
   
   public String code() {
      return this.code;
   }
   
}
