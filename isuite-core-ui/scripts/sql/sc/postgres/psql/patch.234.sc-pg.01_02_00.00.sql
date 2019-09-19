CREATE SEQUENCE SEQ_TRANSFER_CONTROL START WITH 1;
CREATE TABLE ISW_TRANSFER_CONTROL
  (
    ID BIGINT,
    DS_USER_ID BIGINT,
    START_TIME TIMESTAMP (6),
    STOP_TIME  TIMESTAMP (6),
    IS_INCIDENT_GROUP varchar(1) default 'N',
    INCIDENT_TI  VARCHAR(255),
    INCIDENT_GROUP_TI VARCHAR(255),
    STATUS VARCHAR(255),
    LAST_ERROR VARCHAR(4000),
    LAST_MODIFIED_BY_ID BIGINT,
    LAST_MODIFIED_DATE TIMESTAMP (6),
    LAST_MODIFIED_BY VARCHAR(50),
    CREATED_BY_ID BIGINT,
    CREATED_DATE TIMESTAMP(6),
    CREATED_BY VARCHAR(50)
  );
alter table isw_transfer_control add t2 text;
update isw_transfer_control set t2=last_error;
alter table isw_transfer_control drop column last_error;
alter table isw_transfer_control rename column t2 to last_error;
update iswl_sit_209 set transferable_identity='3b697960-2fd1-2f53-f54f-6f039b9a577d' where code='M1';
update iswl_sit_209 set transferable_identity='71e965a2-debf-3d25-e27b-d3580bedcaaf' where code='S1';
insert into isw_system_parameter (id, parameter_name, parameter_value) values (nextVal('SEQ_SYSTEM_PARAMETER'), 'DT_PG_DUMP_SCRIPT_PATH', 'todo');
insert into isw_system_parameter (id, parameter_name, parameter_value) values (nextVal('SEQ_SYSTEM_PARAMETER'), 'DT_ORA_DUMP_SCRIPT_PATH', 'todo');
insert into isw_system_parameter (id, parameter_name, parameter_value) values (nextVal('SEQ_SYSTEM_PARAMETER'), 'DT_ORA_IMPORT_SCRIPT_PATH', 'todo');
insert into isw_system_parameter (id, parameter_name, parameter_value) values (nextVal('SEQ_SYSTEM_PARAMETER'), 'DT_PG_IMPORT_SCRIPT_PATH', 'todo');
insert into isw_system_parameter (id, parameter_name, parameter_value) values (nextVal('SEQ_SYSTEM_PARAMETER'), 'DT_VERSION', '3');
CREATE SEQUENCE  SEQ_INC_EXP START WITH 1;
CREATE TABLE INC_EXP_TABLES 
(
  ID BIGINT 
, T_NAME VARCHAR(128) 
, HAS_TID VARCHAR(1) DEFAULT 'Y' 
);
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_DEPARTMENT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_DEPARTMENT_SUB','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_REQUEST_CATEGORY','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_EVENT_TYPE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_DAILY_FORM','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_COUNTRY','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_SPECIAL_PAY','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_GROUP_CATEGORY','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_GRAPH_GROUP','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_RATE_GROUP','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_RATE_CLASS','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_RATE_CLASS_RATE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_ADDRESS','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_ADMIN_OFFICE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_GROUP','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_GROUP_INCIDENT','N');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_SIT_209','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_KIND','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_JET_PORT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISWL_AGENCY','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_ORGANIZATION','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_SHIFT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_COST_DEFAULTS','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_PREFS_OTHERFIELDS','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_MASTER_FREQUENCY','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_POSITION_ITEM_CODE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_ACCOUNT_CODE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_ACCOUNT_CODE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_QS_KIND','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_GROUP_QS_KIND','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_PREFS','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_GROUP_PREFS','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_QUESTION','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_QUESTION','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_GROUP_QUESTION','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCCOST_RATE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCCOST_RATE_STATE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCCOST_RATE_STATE_KIND','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCCOST_RATE_KIND','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCCOST_RATE_OVHD','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_COST_GROUP','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_COST_GROUP_AG_DS','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_COST_GROUP_AG_DS_PCT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_COST_GROUP_DF_AG_PCT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_CONTRACTOR','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_CONTRACTOR','N');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_CONTRACTOR_AGREEMENT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_PLAN','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_ATTACHMENT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_FORM_202','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_FORM_203','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_BRANCH','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_BRANCH_COMM_SUMMARY','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_BRANCH_PERSONNEL','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_BRANCH_PERSONNEL_RES','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_BRANCH_RSC_ASSIGN','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_FORM_205','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_FREQUENCY','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_FORM_206','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_HOSPITAL','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_MEDICAL_AID','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_AREA_LOC_CAP','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_REMOTE_CAMP_LOC','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_FORM_220','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_AIRCRAFT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_AIRCRAFT_FREQUENCY','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_AIRCRAFT_TASK','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_IAP_PERSONNEL','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_FINANCIAL_EXPORT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_RESOURCE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_RESOURCE_KIND','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_RESOURCE_MOBILIZATION','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_COST_DATA','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_RESOURCE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_RESOURCE_OTHER','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INCIDENT_RESOURCE_OTHER','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_AIR_TRAVEL','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_WORK_PERIOD','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_WP_OVERNIGHT_STAY_INFO','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_WORK_PERIOD_QUESTION_VALUE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_INC_RES_DAILY_COST','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_ASSIGNMENT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_WORK_PERIOD_ASSIGNMENT','N');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_ASSIGNMENT_TIME','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_AD_PAYMENT_INFO','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_CONTR_PAYMENT_INFO','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_CONTRACTOR_RATE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_CONTR_PAYINFO_RATE','N');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_ASSIGN_TIME_POST','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_TIME_ASSIGN_ADJUST','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_REPORT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_TIME_INVOICE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_ASSIGN_TIME_POST_INVOICE','N');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_TIME_ASSIGN_ADJ_INVOICE','N');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_RESOURCE_INVOICE','N');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_COST_ACCRUAL_EXTRACT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_COST_ACCRUAL_EXT_RSC','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_PROJECTION','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_PROJECTION_ITEM','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextval('SEQ_INC_EXP'),'ISW_PROJECTION_ITEM_WKSHT','Y');
UPDATE inc_exp_tables SET t_name=lower(t_name);
create table inc_exp_code_fld (
    table_name varchar(40),
    column_name varchar(40)
);
create table inc_exp_code (
    from_code varchar(10),
    to_code varchar(10)
);
insert into inc_exp_code_fld values ('isw_work_period', 'ci_pre_planning_remarks');
insert into inc_exp_code_fld values ('isw_work_period', 'dm_planning_remarks');
insert into inc_exp_code_fld values ('isw_work_period', 'dm_release_remarks');
insert into inc_exp_code_fld values ('isw_work_period', 'ci_pre_planning_remarks');
insert into inc_exp_code_fld values ('isw_cost_data', 'cost_remarks');
insert into inc_exp_code_fld values ('isw_air_travel', 'remarks');
insert into inc_exp_code_fld values ('isw_assignment_time', 'of_remarks');
insert into inc_exp_code_fld values ('isw_iap', 'n202_b7_general_safety_msg');
insert into inc_exp_code_fld values ('isw_iap', 'd202_b3_objectives');
insert into inc_exp_code_fld values ('isw_iap', 'd202_b4_command_emphasis');
insert into inc_exp_code_fld values ('isw_iap', 'd202_b4_gen_sit_awareness');
insert into inc_exp_code_fld values ('isw_iap', 'b206_medical_emergency_proc')
insert into inc_exp_code_fld values ('isw_iap', 'd205_b5_special_instruction');
insert into inc_exp_code_fld values ('isw_iap', 'n220_b3_remarks');
insert into inc_exp_code_fld values ('isw_iap', 'n220_b4_medivac_aircraft');
insert into inc_exp_code_fld values ('isw_iap', 'n220_b5_tfr');
insert into inc_exp_code_fld values ('isw_iap', 'd220_b4_remarks');
insert into inc_exp_code_fld values ('isw_iap_aircraft', 'remarks');
insert into inc_exp_code_fld values ('isw_iap_form_220', 'remarks');
insert into inc_exp_code_fld values ('isw_iap_frequency', 'remarks');
insert into inc_exp_code_fld values ('isw_iap_master_frequency', 'remarks');
insert into inc_exp_code_fld values ('isw_wp_overnight_stay_info', 'remarks');
insert into inc_exp_code values ('0X22', 34);
insert into inc_exp_code values ('0X0a', 10);
CREATE OR REPLACE FUNCTION gen_uuid()
 RETURNS text
 LANGUAGE plpgsql
AS $function$
    BEGIN
		RETURN uuid_in(md5(random()::text || clock_timestamp()::text)::cstring);
	END;
$function$;
CREATE OR REPLACE FUNCTION rep_chars_before_export()
  RETURNS text AS
$BODY$
DECLARE
  error_stack text;
  from_str text;
  fld_rec inc_exp_code_fld%rowtype;
  code_rec inc_exp_code%rowtype;
  
BEGIN
 FOR fld_rec IN SELECT * FROM inc_exp_code_fld ORDER BY table_name, column_name LOOP
   FOR code_rec IN SELECT * FROM inc_exp_code ORDER BY from_code LOOP
   from_str = quote_literal(code_rec.from_code);
     EXECUTE
     'UPDATE ' || quote_ident(fld_rec.table_name) || ' SET ' || quote_ident(fld_rec.column_name) || ' = replace('|| quote_ident(fld_rec.column_name) || ', chr(' || code_rec.to_code || '), ' || from_str || ')';
  END LOOP;
END LOOP; 
   return 'rep_chars_before_export() complete';
  EXCEPTION
    WHEN others
      THEN
      GET STACKED DIAGNOSTICS error_stack = MESSAGE_TEXT;
      return(error_stack);
END $BODY$ LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION gen_ti()
  RETURNS text AS
$BODY$
DECLARE
  error_stack text;
  tbl inc_exp_tables%rowtype;
BEGIN
  FOR tbl IN SELECT * FROM  inc_exp_tables LOOP
    IF tbl.has_tid = 'Y' THEN
      EXECUTE
      'update ' || quote_ident(tbl.t_name) ||' set transferable_identity=gen_uuid() where transferable_identity IS NULL OR transferable_identity  = ''''';
    END IF;
  END LOOP;
   return 'gen_ti() complete';
  EXCEPTION
    WHEN others
      THEN
      GET STACKED DIAGNOSTICS error_stack = MESSAGE_TEXT;
      return(error_stack);
END $BODY$ LANGUAGE plpgsql;
INSERT INTO SCHEMACHANGELOG( ID, MAJORRELEASENUMBER, MINORRELEASENUMBER, POINTRELEASENUMBER, SCRIPTNAME, DATEAPPLIED)
VALUES (234,'01','02','00.00','patch.234.sc-pg.01_02_00.00.sql', now());
UPDATE REVISION SET REVISIONLEVEL = 234;
