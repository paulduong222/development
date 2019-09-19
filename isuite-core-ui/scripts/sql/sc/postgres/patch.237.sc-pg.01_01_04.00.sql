CREATE TABLE ISW_RESINV_FILE_DATA
(
  ROSS_RES_ID             bigint            NOT NULL,
  RES_NAME                varchar(55),
  LAST_NAME               varchar(55),
  FIRST_NAME              varchar(55),
  RES_DISP_ORG_UNIT_CODE  varchar(30),
  RES_PROV_UNIT_CODE      varchar(30),
  CATALOG_ITEM_NAME       varchar(200),
  ORG_ID                  bigint,
  PDC_ID                  bigint,
  KIND_ID                 bigint,
  CREATED_BY              varchar(50),
  CREATED_DATE            timestamp          DEFAULT now(),
  CREATED_BY_ID           bigint,
  LAST_MODIFIED_BY        varchar(50),
  LAST_MODIFIED_DATE      timestamp          DEFAULT now(),
  LAST_MODIFIED_BY_ID     bigint,
  TRANSFERABLE_IDENTITY   varchar(255)
)
/

Insert into isw_system_parameter (id, parameter_name, parameter_value) values (nextVal('seq_system_parameter'),'RES_INV_FOLDER','/opt/NWCG/tomcat/webapps/qa/resinvfiles/')
/

--alter table isw_resinv_import modify new_import_count integer
--/

--alter table isw_resinv_import modify updated_import_count interger
--/


create table isw_training_settings  
(
  id                      bigint not null,
  incident_id             bigint,
  incident_group_id       bigint,
  complexity_id           bigint,
  number_of_acres         bigint,
  CREATED_BY              varchar(50),
  CREATED_DATE            timestamp          DEFAULT now(),
  CREATED_BY_ID           bigint,
  LAST_MODIFIED_BY        varchar(50),
  LAST_MODIFIED_DATE      timestamp          DEFAULT now(),
  LAST_MODIFIED_BY_ID     bigint,
  TRANSFERABLE_IDENTITY   varchar(255)
)
/

alter table isw_training_settings add constraint pk_training_settings primary key (id)
/

alter table isw_training_settings add constraint fk_training_settings_inc foreign key (incident_id) references isw_incident(id)
/

alter table isw_training_settings add constraint fk_training_settings_inc_grp foreign key (incident_group_id) references isw_incident_group(id)
/

alter table isw_training_settings add constraint fk_training_settings_complexity foreign key (complexity_id) references iswl_complexity(id)
/

create sequence seq_training_settings
/

create table isw_training_set_fuel_type
(
  training_settings_id    bigint not null,
  fuel_type_id            bigint not null
)
/

alter table isw_training_set_fuel_type add constraint pk_training_set_fuel_type primary key (training_settings_id, fuel_type_id)
/

alter table isw_training_set_fuel_type add constraint fk_trn_set_fuel_type_trn_set foreign key (training_settings_id) references isw_training_settings (id)
/

alter table isw_training_set_fuel_type add constraint fk_trn_set_fuel_type_fuel_tp foreign key (fuel_type_id) references iswl_fuel_type (id)
/

create table isw_training_contact
(
  id                      bigint not null, 
  address_id              bigint,
  phone                   varchar(20),
  email                   varchar(50)
)
/

alter table isw_training_contact add constraint pk_training_contact primary key (id)
/

alter table isw_training_contact add constraint fk_training_contact_addr foreign key (address_id) references isw_address(id)
/

create sequence seq_training_contact
/


alter table isw_incident_resource add training_contact_id bigint
/

alter table isw_incident_resource add constraint fk_inc_resource_trn_contact foreign key (training_contact_id) references isw_training_contact (id)
/

alter table isw_resource_home_unit_contact add contact_name varchar(70)
/

alter table iswl_priority_program add incident_id bigint
/

alter table iswl_priority_program add constraint fk_priority_program_inc foreign key (incident_id) references isw_incident (id)
/

alter table iswl_priority_program add incident_group_id bigint
/

alter table iswl_priority_program add constraint fk_priority_program_inc_grp foreign key (incident_group_id) references isw_incident_group (id)
/


INSERT INTO SCHEMACHANGELOG( ID, MAJORRELEASENUMBER, MINORRELEASENUMBER, POINTRELEASENUMBER, SCRIPTNAME, DATEAPPLIED)
VALUES (237,'01','01','04.00','patch.237.sc-pg.01_01_04.00.sql', now())
/
UPDATE REVISION SET REVISIONLEVEL = 237
/
