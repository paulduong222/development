create table iswl_fuel_type (
  id bigint not null,
  code varchar(40) not null,
  description varchar(255),
  CREATED_DATE timestamp default now(),
  CREATED_BY varchar(50),
  CREATED_BY_ID bigint,
  LAST_MODIFIED_DATE timestamp default now(),
  LAST_MODIFIED_BY varchar(50),
  LAST_MODIFIED_BY_ID bigint,
  TRANSFERABLE_IDENTITY varchar(255)
)
/
alter table iswl_fuel_type add constraint pk_fuel_type primary key (id)
/
create sequence seq_fuel_type
/
create table iswl_complexity (
  id bigint not null,
  code varchar(40) not null,
  description varchar(255),
  CREATED_DATE timestamp default now(),
  CREATED_BY varchar(50),
  CREATED_BY_ID bigint,
  LAST_MODIFIED_DATE timestamp default now(),
  LAST_MODIFIED_BY varchar(50),
  LAST_MODIFIED_BY_ID bigint,
  TRANSFERABLE_IDENTITY varchar(255)
)
/
alter table iswl_complexity add constraint pk_complexity primary key (id)
/
create sequence seq_complexity
/
create table iswl_priority_program (
  id bigint not null,
  code varchar(40) not null,
  description varchar(255),
  CREATED_DATE timestamp default now(),
  CREATED_BY varchar(50),
  CREATED_BY_ID bigint,
  LAST_MODIFIED_DATE timestamp default now(),
  LAST_MODIFIED_BY varchar(50),
  LAST_MODIFIED_BY_ID bigint,
  TRANSFERABLE_IDENTITY varchar(255)
)
/
alter table iswl_priority_program add constraint pk_priority_program primary key (id)
/

create sequence seq_priority_program
/


create table isw_resource_training (
  id bigint not null,
  incident_resource_id bigint not null,
  kind_id bigint not null,
  is_initial_assignment varchar(1) default 'N',
  start_date timestamp,
  end_date timestamp,
  is_FS_priority_trainee varchar(1) default 'N',
  priority_program_id bigint,
  is_valid_red_card varchar(1) default 'N',
  objective_issuer varchar(20),
  home_unit_contact_last_name varchar(35),
  home_unit_contact_first_name varchar(35),
  home_unit_contact_title varchar(35),
  home_unit_contact_work_phone varchar(20),
  home_unit_contact_address_id bigint,
  home_unit_contact_email varchar(50),
  CREATED_DATE timestamp default now(),
  CREATED_BY varchar(50),
  CREATED_BY_ID bigint,
  LAST_MODIFIED_DATE timestamp default now(),
  LAST_MODIFIED_BY varchar(50),
  LAST_MODIFIED_BY_ID bigint,
  TRANSFERABLE_IDENTITY varchar(255)
)
/

alter table isw_resource_training add constraint pk_resource_training primary key (id)
/
alter table isw_resource_training add constraint fk_resource_training_ir foreign key (incident_resource_id) references isw_incident_resource (id) on delete cascade
/
alter table isw_resource_training add constraint fk_resource_training_kind foreign key (kind_id) references iswl_kind (id)
/
alter table isw_resource_training add constraint fk_resource_training_pp foreign key (priority_program_id) references iswl_priority_program (id)
/
alter table isw_resource_training add constraint fk_resource_training_adr foreign key (home_unit_contact_address_id) references isw_address (id)
/
create sequence seq_resource_training
/

create table isw_rsc_training_objective (
  id bigint not null,
  resource_training_id bigint not null,
  objective varchar(255),
  position_num integer,
  CREATED_DATE timestamp default now(),
  CREATED_BY varchar(50),
  CREATED_BY_ID bigint,
  LAST_MODIFIED_DATE timestamp default now(),
  LAST_MODIFIED_BY varchar(50),
  LAST_MODIFIED_BY_ID bigint,
  TRANSFERABLE_IDENTITY varchar(255)
)
/

alter table isw_rsc_training_objective add constraint pk_rsc_training_objective primary key (id)
/
alter table isw_rsc_training_objective add constraint fk_rsc_training_obj_rsc_trn foreign key (resource_training_id) references isw_resource_training (id) on delete cascade
/
create sequence seq_rsc_training_objective
/

create table  isw_rsc_training_trainer (
  id bigint not null,
  resource_training_id bigint not null,
  last_name varchar(35),
  first_name varchar(35),
  reqest_number varchar(20),
  kind_id bigint,
  home_unit varchar(40),
  address_id bigint,  
  email varchar(50),
  phone varchar(20),
  start_date timestamp,
  end_date timestamp,
  PTB_percentage integer,
  recommendation_level integer,
  complexity_id bigint, 
  number_of_acres bigint,
  TNSP_comments varchar(255),
  CREATED_DATE timestamp default now(),
  CREATED_BY varchar(50),
  CREATED_BY_ID bigint,
  LAST_MODIFIED_DATE timestamp default now(),
  LAST_MODIFIED_BY varchar(50),
  LAST_MODIFIED_BY_ID bigint,
  TRANSFERABLE_IDENTITY varchar(255)
)
/

alter table isw_rsc_training_trainer add constraint pk_rsc_traning_trainer primary key (id)
/
alter table isw_rsc_training_trainer add constraint fk_rsc_traning_trainer_rt foreign key (resource_training_id) references isw_resource_training (id) on delete cascade
/
alter table isw_rsc_training_trainer add constraint fk_rsc_traning_trainer_adr foreign key (address_id) references isw_address (id)
/
alter table isw_rsc_training_trainer add constraint fk_rsc_traning_trainer_cpx foreign key (complexity_id) references iswl_complexity (id)
/
create sequence seq_rsc_training_trainer
/

alter table isw_incident add complexity_id bigint
/
alter table isw_incident add constraint fk_incident_complexity foreign key (complexity_id) references iswl_complexity (id)
/

alter table isw_incident add number_of_acres bigint
/

create table  isw_incident_fuel_type (
  incident_id bigint not null,
  fuel_type_id bigint not null
)
/

alter table isw_incident_fuel_type add constraint fk_incident_fuel_type foreign key (incident_id) references isw_incident(id) on delete cascade
/
alter table isw_incident_fuel_type add constraint fk_incident_fuel_type_id foreign key (fuel_type_id) references iswl_fuel_type(id)
/

create table isw_rsc_training_fuel_type (
  resource_training_id bigint not null,
  fuel_type_id bigint not null
)
/

alter table isw_rsc_training_fuel_type add constraint fk_rsc_training_fuel_type_rt foreign key (resource_training_id) references isw_resource_training (id) on delete cascade
/
alter table isw_rsc_training_fuel_type add constraint fk_rsc_training_fuel_type_ft foreign key (fuel_type_id) references iswl_fuel_type (id)
/
insert into isw_system_parameter (ID, PARAMETER_NAME, PARAMETER_VALUE) VALUES (nextVal('SEQ_SYSTEM_PARAMETER'),'IRWIN_SEARCH_THROTTLE','10')
/
insert into isw_system_parameter (ID, PARAMETER_NAME, PARAMETER_VALUE) VALUES (nextVal('SEQ_SYSTEM_PARAMETER'),'IRWIN_FINDMATCH_THROTTLE','30')
/
update isw_system_parameter set PARAMETER_VALUE='https://irwin.doi.gov/arcgis/tokens/generateToken' where PARAMETER_NAME='IRWIN_GENERATETOKEN'
/
update isw_system_parameter set PARAMETER_VALUE='https://irwin.doi.gov/arcgis/rest/services/Irwin_v2/MapServer/exts/Irwin_v2/GetUpdates' where PARAMETER_NAME='IRWIN_GETUPDATES'
/
update isw_system_parameter set PARAMETER_VALUE='https://irwin.doi.gov/arcgis/rest/services/Irwin_v2/MapServer/exts/Irwin_v2/GetIncidents' where PARAMETER_NAME='IRWIN_GETINCIDENTS'
/
update isw_system_parameter set PARAMETER_VALUE='https://irwin.doi.gov/arcgis/rest/services/Irwin_v2/MapServer/exts/Irwin_v2/SubmitIncident' where PARAMETER_NAME='IRWIN_SUBMITINCIDENT'
/
update isw_system_parameter set PARAMETER_VALUE='https://irwin.doi.gov/arcgis/rest/services/Irwin_v2/MapServer/exts/Irwin_v2/UpdateIncident' where PARAMETER_NAME='IRWIN_UPDATEINCIDENT'
/
update isw_system_parameter set PARAMETER_VALUE='https://irwin.doi.gov/arcgis/rest/services/Irwin_v2/MapServer/exts/Irwin_v2/GetConflicts' where PARAMETER_NAME='IRWIN_GETCONFLICTS'
/
update isw_system_parameter set PARAMETER_VALUE='30' where PARAMETER_NAME='IRWIN_FROMDATETIME'
/
update isw_system_parameter set PARAMETER_VALUE='eisuite' where PARAMETER_NAME='IRWIN_USERNAME'
/
update isw_system_parameter set PARAMETER_VALUE='5HfPKV6A8Lrs' where PARAMETER_NAME='IRWIN_PASSWORD'
/
update isw_system_parameter set PARAMETER_VALUE='1' where PARAMETER_NAME='IRWIN_MODE'
/
INSERT INTO ISW_MESSAGE (ID, MESSAGE_TEXT, TYPE) 
VALUES (nextVal('SEQ_MESSAGE'), '<TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="0"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="CENTER"><FONT FACE="ARIAL" SIZE="16" COLOR="#000000" LETTERSPACING="0" KERNING="0"><B>WELCOME TO e-ISUITE!</B></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="CENTER"><FONT FACE="ARIAL" SIZE="16" COLOR="#000000" LETTERSPACING="0" KERNING="0"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="CENTER"><FONT FACE="ARIAL" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="0"><B>On this Message Board, you will find helpful information concerning application updates, outages or other situations that may impact the availability of the system.</B></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="ARIAL" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="0"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="ARIAL" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="0">ON THIS MESSAGE BOARD, YOU WILL FIND HELPFUL INFORMATION CONCERNING APPLICATION UPDATES, OUTAGES OR OTHER SITUATIONS THAT MAY IMPACT THE AVAILABILITY OF THE SYSTEM.</FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="ARIAL" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="0"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="ARIAL" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"><B><U>APRIL 27, 2015</U></B><B> </B></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="ARIAL" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1">1. Help is available by calling the Interagency Incident Helpdesk at: <B>1-866-224-7677</B> </FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="ARIAL" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1">2. Known Issues are provided in a document available on the e-ISuite webpage, as well as additional information about e-ISuite: <B>http://eisuite.nwcg.gov</B> </FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="ARIAL" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1">We&apos;d like to thank everyone who played a significant role in helping develop e-ISuite. Your assistance has been invaluable, and has made it possible for Release 1 to happen. </FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="CENTER"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="CENTER"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"><B>We hope the upgrades in functionality to e-ISuite contribute to your business practices, and welcome feedback on ways to make it even better.</B></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="CENTER"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="CENTER"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"><B>Please continue to send your comments and suggestions to the I-Suite Suggestion Box:  i-suite-suggestion@dms.nwcg.gov.</B></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="CENTER"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="CENTER"><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="1"><B>Information on the e-ISuite Suggestion Box will be available soon.</B></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><LI><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="0"></FONT></LI></TEXTFORMAT><TEXTFORMAT LEADING="2"><LI><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="0"></FONT></LI></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="ARIAL" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="0"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><P ALIGN="LEFT"><FONT FACE="ARIAL" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="0"></FONT></P></TEXTFORMAT><TEXTFORMAT LEADING="2"><LI><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="0"></FONT></LI></TEXTFORMAT><TEXTFORMAT LEADING="2"><LI><FONT FACE="Arial" SIZE="12" COLOR="#000000" LETTERSPACING="0" KERNING="0"></FONT></LI></TEXTFORMAT>', 'STATIC')
/
insert into isw_system_role (id, role_name, is_privileged_role, display_name) values (nextVal('seq_system_role'), 'ROLE_TRAINING_SPECIALIST',false,'Training Specialist')
/
insert into iswl_complexity (id, code, description, transferable_identity) values (nextVal('seq_complexity'), 'IA', 'INITIAL ATTACK','18D7E437DEB04789A91FD881347165E8')
/
insert into iswl_complexity (id, code, description, transferable_identity) values (nextVal('seq_complexity'), 'T1', 'TYPE 1','6A8BA021422B46709BE6D7C65C24B640')
/
insert into iswl_complexity (id, code, description, transferable_identity) values (nextVal('seq_complexity'), 'T2', 'TYPE 2','3B6E8D586D344B92982D2D4746350071')
/
insert into iswl_complexity (id, code, description, transferable_identity) values (nextVal('seq_complexity'), 'T3', 'TYPE 3','3FA42CCD0D9E4D5B9FC13AE4059C5EA8')
/
insert into iswl_complexity (id, code, description, transferable_identity) values (nextVal('seq_complexity'), 'T4', 'TYPE 4','80A3153006EA4E3FA8993145E6FE9950')
/
insert into iswl_complexity (id, code, description, transferable_identity) values (nextVal('seq_complexity'), 'AC', 'AREA COMMAND','ABD9E860E89C44E491BB4E5F638467FC')
/
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextVal('seq_fuel_type'), 'B', 'BRUSH','1867529D3B30492BBE92A59FC239981F')
/
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextVal('seq_fuel_type'), 'G', 'GRASS','8AB1DE24B02349E3B724B8481C1293AF')
/
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextVal('seq_fuel_type'), 'S', 'SLASH','B58C91ABC0594B84AD2497AC273C377B')
/
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextVal('seq_fuel_type'), 'T', 'TIMBER','60E4145528F24DD6AF5BF5AF0D8FB307')
/
CREATE OR REPLACE VIEW iswv_cr_person_post AS
 SELECT i.id AS incident_id, ac.account_code AS accounting_code,
    kind.code AS item_code, kind.description AS item_code_description,
    atp.quantity AS number_of_hours, atp.rate_amount,
    rcr.rate_classcode AS rate_class, a.request_number,
    r.last_name::text ||
        CASE
            WHEN r.first_name IS NULL THEN ''::text
            ELSE ', '::text || r.first_name::text
        END AS resource_name,
    department.code AS section_code,
    department.description AS section_code_description,
    atp.post_start_date AS start_date, atp.post_start_date AS start_time,
    atp.post_stop_date AS stop_date, atp.post_stop_date AS stop_time
   FROM isw_account_code ac, isw_incident_account_code iac,
    isw_assign_time_post atp
   LEFT JOIN iswl_rate_class_rate rcr ON rcr.id = atp.rate_class_rate_id,
    isw_assignment_time at, isw_assignment a, isw_work_period_assignment wpa,
    isw_work_period wp, isw_incident_resource ir, isw_incident i,
    isw_resource r,
    iswl_kind kind
   LEFT JOIN iswl_department department ON department.id = kind.department_id
  WHERE ac.id = iac.account_code_id AND iac.id = atp.incident_account_code_id AND atp.assignment_time_id = at.id
  AND at.assignment_id = a.id AND a.id = wpa.assignment_id AND wpa.work_period_id = wp.id AND wp.incident_resource_id = ir.id
  AND ir.incident_id = i.id AND ir.resource_id = r.id AND kind.id = atp.kind_id AND r.is_person = true
/

update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS')
, group_category_id = (select id from iswl_group_category where code='O')
, is_direct=false
, is_line_overhead=false
where code in ('AEMF', 'EMPF', 'EMTF', 'FEMP')
/

update iswl_kind set is_direct=false where code = 'WTOP'
/

update isw_syscost_rate_kind
set rate_type='DAILY'
where syscost_rate_id in (select id from isw_syscost_rate where cost_rate_category in ('FED','STATE_COOP','CONTRACTOR'))
and kind_id in (select id from iswl_kind where code in ('COMU','EXCA','FORW','RAWP','FWO','IRF'))
/

update isw_syscost_rate_kind
set rate_type='HOURLY'
where syscost_rate_id in (select id from isw_syscost_rate where cost_rate_category in ('FED','STATE_COOP','CONTRACTOR'))
and kind_id in (select id from iswl_kind where code in ('FWPT','LAAR'))
/

update isw_syscost_rate_kind
set rate_amount=0
where syscost_rate_id in (select id from isw_syscost_rate where cost_rate_category in ('FED'))
and kind_id in (select id from iswl_kind where code in ('COMU','EXCA'))
/

update isw_syscost_rate_kind
set rate_amount=1000
where syscost_rate_id in (select id from isw_syscost_rate where cost_rate_category in ('STATE_COOP','CONTRACTOR'))
and kind_id in (select id from iswl_kind where code in ('COMU'))
/

update isw_syscost_rate_kind
set rate_amount=1400
where syscost_rate_id in (select id from isw_syscost_rate where cost_rate_category in ('STATE_COOP','CONTRACTOR'))
and kind_id in (select id from iswl_kind where code in ('EXCA'))
/

update isw_syscost_rate_kind
set rate_amount=2600
where syscost_rate_id in (select id from isw_syscost_rate where cost_rate_category in ('STATE_COOP','CONTRACTOR'))
and kind_id in (select id from iswl_kind where code in ('FORW'))
/

update isw_syscost_rate_kind
set rate_amount=50
where syscost_rate_id in (select id from isw_syscost_rate where cost_rate_category in ('STATE_COOP'))
and kind_id in (select id from iswl_kind where code in ('FWPT'))
/

update isw_syscost_rate_kind
set rate_amount=50
where syscost_rate_id in (select id from isw_syscost_rate where cost_rate_category in ('STATE_COOP'))
and kind_id in (select id from iswl_kind where code in ('LAAR'))
/
update isw_syscost_rate_kind
set rate_amount=0
where syscost_rate_id in (select id from isw_syscost_rate where cost_rate_category in ('FED','STATE_COOP','CONTRACTOR'))
and kind_id in (select id from iswl_kind where code in ('FWO','IRF'))
/
update iswl_kind set description='EMERGENCY MEDICAL TECHNICIAN, FIRELINE' where code='AEMT'
/
INSERT INTO SCHEMACHANGELOG( ID, MAJORRELEASENUMBER, MINORRELEASENUMBER, POINTRELEASENUMBER, SCRIPTNAME, DATEAPPLIED)
VALUES (231,'01','01','00.00','patch.231.sc-pg.01_01_00.00.sql', now())
/
UPDATE REVISION SET REVISIONLEVEL = 231
/
