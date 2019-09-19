package gov.nwcg.isuite.core.persistence.hibernate.query;

public class ReferenceDataQuery {
  
   /*
    * Tests to see if an entered sit209 Code is Unique to the Sit209 Table
    */
   public static final String IS_SIT_209_CODE_UNIQUE="IS_SIT_209_CODE_UNIQUE";
   public static final String IS_SIT_209_CODE_UNIQUE_QUERY =
      "SELECT count(*) from Sit209Impl sit209 " +
      "WHERE sit209.code = :code";
   
   /*
    * Tests to see if an entered jetPort Code is Unique to the JetPort Table
    */
   public static final String IS_JET_PORT_CODE_UNIQUE="IS_JET_PORT_CODE_UNIQUE";
   public static final String IS_JET_PORT_CODE_UNIQUE_QUERY =
      "SELECT count(*) from JetPortImpl jetPort " +
      "WHERE jetPort.code = :code";

   
   /*
    * Tests to see if an entered KindGroup Code is Unique to the KindGroup Table
    */
   public static final String IS_KIND_GROUP_CODE_UNIQUE="IS_KIND_GROUP_CODE_UNIQUE";
   public static final String IS_KIND_GROUP_CODE_UNIQUE_QUERY =
      "SELECT count(*) from KindGroupImpl kindGroup " +
      "WHERE kindGroup.code = :code";  
  
   
   /*
    * Tests to see if an entered AgencyGroup Code is Unique to the AgencyGroup Table
    */
   public static final String IS_AGENCY_GROUP_CODE_UNIQUE="IS_AGENCY_GROUP_CODE_UNIQUE";
   public static final String IS_AGENCY_GROUP_CODE_UNIQUE_QUERY =
      "SELECT count(*) from AgencyGroupImpl agencyGroup " +
      "WHERE agencyGroup.code = :code";  
   
   
   /*
    * Tests to see is an entered Country Subdivision code is unique to the country subdivision table
    */
   public static final String IS_COUNTRY_SUBDIVISION_ABRV_UNIQUE="IS_COUNTRY_SUBDIVISION_ABRV_UNIQUE_QUERY";
   public static final String IS_COUNTRY_SUBDIVISION_ABRV_UNIQUE_QUERY =
	      "SELECT count(*) from CountrySubdivisionImpl cs WHERE cs.abbreviation = :abbreviation";  

}
