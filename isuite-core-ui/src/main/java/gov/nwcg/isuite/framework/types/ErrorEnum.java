package gov.nwcg.isuite.framework.types;

/**
 * Ultimately these enums will be looked up in a properties
 * file so that specific error messages can be displayed to the
 * user.
 * @author ncollette
 *
 */
public enum ErrorEnum {
   _0002_NO_ACCOUNT_ISUITE("error.0002"),
   _0003_INVALID_PASSWORD("error.0003"),
   _0004_DISABLED_LDAP("error.0004"),
   _0005_DISABLED_ISUITE("error.0005"),
   _0006_NO_ACCESS_TO_ISUITE_IN_LDAP("error.0006"),
   _0007_MUST_BE_SETUP_USER("error.0007"),
   _0009_NO_ACCOUNT_LDAP("error.0009"),
   _0016_SETUP_LOGIN_COUNT_EXCEEDED("error.0016"),
   _0020_CONCURRENT_LOGIN("error.0020"),
   _0043_DISABLE_OWN_ACCOUNT ("error.0043"),
   _0058_WORK_AREA_NAME_ALREADY_ASSIGNED("error.0058"),
   _0132_PERSON_DESIGNATION_CHANGE("error.0132"),
   _0132a_PERSON_DESIGNATION_CHANGE("error.0132a"),
   _0133_PERSON_DESIGNATION_CHANGE("error.0133"),
   _0143_PERSON_DESIGNATION_CHANGE("error.0143"),
   _0157_ERROR ("error.0157"),
   _0160_NOTHING_SELECTED_FOR_OPERATION("error.0160"),
   _0163_ACCOUNT_EXPIRED("error.0163"),
   _0174_DUPLICATE_USER_GROUP_NAME("error.0174"),
   _0175_DUPLICATE_USER_GROUP_NAME_IN_SYSTEM("error.0175"),
   _0200_DUPLICATE_INCIDENT_GROUP_NAME("error.0200"),
   _0219_DUPLICATE_REFERENCE_DATA("error.0219"),
   INVALID_LOGIN (""),
   INVALID_NAME (""),
   _0024_1_MISSING_LENGTH ("error.0024.1"),
   _0024_2_MISSING_UPPERCASE ("error.0024.2"),
   _0024_3_MISSING_LOWERCASE ("error.0024.3"),
   _0024_4_MISSING_NUMERIC ("0024error..4"),
   _0024_5_MISSING_SPECIAL ("error.0024.5"),
   _0021_NO_ROLES_DEFINED ("error.0021"),
   _0023_PASSWORDS_DONT_MATCH ("error.0023"),
   _0025_RECENTLY_USED_PASSWORD ("error.0025"),
   _0036_CANT_CREATE_EXPORT_FILE("error.0036"),
   _0039_FAILED_IMPORTS ("error.0039"),
   _0040_USER_IMPORTS_CORRUPT_FILE("error.0040"),
   _0045_DUPLICATE_USER_NAME("error.0045"),
   _0046_DUPLICATE_INCIDENT_FOR_YEAR("error.0046"),
   _0204_LESS_THAN_TWO_PREFERENCE_COLUMN_SELECTIONS("error.0204"),
   _0237_DUPLICATE_AGREEMENT_NUMBER("error.0237"),
   _0238_DUPLICATE_CONTRACTOR_NAME("error.0238"),
   SYSTEM_ADMIN_NOT_IN_ISOLATION (""),
   USER_ACCOUNT_EXPORT_FAILED (""),
   USER_ACCOUNT_IMPORT_FAILED (""),
   USER_IMPORT_FAILED_USERNAME_EXISTS (""),
   USER_ORGANIZATION_REQUIRED (""),
   USERNAME_ALREADY_EXISTS (""),
   USERNAME_SETUP (""),
   ERROR_NULL("error.null"),
   DUPLICATE_INCIDENT_ACCOUNT_CODE("error.duplicateIncidentAccountCode"),
   DUPLICATE_ACCOUNT_CODE("error.duplicateAccountCode"),
   DUPLICATE_ADMIN_OFFICE_NAME("error.duplicateAdminOfficeName"),
   DUPLICATE_ITEM_CODE("error.duplicateItemCode"),
   DUPLICATE_INCIDENT_QUESTION("text.duplicateQuestionsAreNotPermitted"),
   LOAD_USER_ORGANIZATIONS_FAILED("error.unableToRetrieveUserOrganizations"),
   CANNOT_EDIT_STANDARD_RECORDS("error.cannotEditStandardRecords"),
   REQUIRED_FIELD("error.requiredField"),
   VALIDATION_ERROR("error.validationError"),
   _80000_ERROR("error.800000"),
   _90000_ERROR("error.900000"),
   _900001_ENTITY_NOT_FOUND("error.900001"),
   _900002_REQUIRD_FIELD("error.900002"),
   _900003_FIELD_EXCEEDS_MAXIMUM_ALLOWED("error.900003"),
   _900004_AUTHENTICATION_FAILED("error.900004"),
   _900006_PERSISTENCE_ERROR("error.900006"),
   _900007_PWD_ERROR("error.900007"),
   _900008_INVALID_CURRENT_PASSWORD("error.900008"),
   _900009_CANNOT_UNRESTRICT_WITH_USERS_ASSIGNED("error.900009"),
   _900010_NO_REPORT_DATA_AVAILABLE("error.900010"),
   _900011_CONTRACTOR_DESIGNATION_CHANGE("error.900011"),
   _900012_CONTRACTOR_DESIGNATION_CHANGE("error.900012"),
   _900013_UNIQUE_NAME_OR_VIN_NOT_UNIQUE("error.900013"),
   _900017_NON_PRIV_USERS_ONLY_INC_GROUP("error.900017"),
   info_9912("info.9912"),
   UNABLE_TO_IMPORT_DATA("error.unableToImportData"),
   UNABLE_TO_RETRIEVE_REQUEST_NUMBERS_FOR_SELECTED_INCIDENT("error.unableToRetrieveRequestNumbersForSelectedIncident"),
   UNABLE_TO_RETRIEVE_PERSON_RESOURCE_NAMES_FOR_SELECTED_INCIDENT("error.unableToRetrievePersonResourceNamesForSelectedIncident"),
   UNABLE_TO_RETRIEVE_CREW_NAMES_FOR_SELECTED_INCIDENT("error.unableToRetrieveCrewNamesForSelectedIncident"),
   UNABLE_TO_RETRIEVE_CREW_REQUEST_NUMBERS_FOR_SELECTED_INCIDENT("error.unableToRetrieveCrewRequestNumbersForSelectedIncident"),
   UNABLE_TO_RETRIEVE_AGENCY_RESOURCES_FOR_SELECTED_INCIDENT("error.unableToRetrieveAgencyResourcesForSelectedIncident"),
   SELECTED_RESOURCE_HAS_NO_TIME_POSTINGS("error.selectedResourceNoTimePostings"), 
   UNABLE_TO_RETRIEVE_REPRINT_LIST_FOR_SELECTED_INCIDENT("error.unableToRetrieveReprintListForSelectedIncident")
   ,_GENERIC("info.generic")
   ,_ADMIN_OFFICE_DELETE_STANDARD("error.adminOfficeDeleteStandard")
   ,_ADMIN_OFFICE_EDIT_STANDARD("error.adminOfficeEditStandard")
   ,_EMAIL_FORMAT_ERROR("error.emailFormatError")
   ,_PHONE_NUMBER_NON_DIGIT_TOO_SHORT("error.phoneMinLengthError")
   ,_PHONE_NUMBER_LENGTH_TOO_LONG("error.phoneLength")
   ,_PHONE_NUMBER_FORMAT_ERROR("error.phoneFormat")
   ,_ZIP_CODE_INVALID_LENGTH("error.zipCodeInvalidLength")
   ,DUPLICATE_IAP_PLANNAME("error.duplicatedIapPlanName")
   ,IAP_PLAN_UNEDITABLE("error.uneditableIapPlan")
   ,IAP_PLAN_UNABLE_TO_SAVE_SELECTED_DATES("error.invalidPlanDates")
   ;
   
   private final String errorNumber;
   
   public String getErrorName() {
      return this.errorNumber;
   }
   
   ErrorEnum(String errorNumber) {
      this.errorNumber = errorNumber;
   }

   @Override
   public String toString() {
      return ("error." + (this.errorNumber.equals("") 
               ? this.name().toLowerCase() 
               : this.errorNumber));
   }
}
