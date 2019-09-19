delete from iswl_fuel_type;
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextVal('seq_fuel_type'), 'B', 'BRUSH GROUP','1867529D3B30492BBE92A59FC239981F');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextVal('seq_fuel_type'), 'G', 'GRASS GROUP','8AB1DE24B02349E3B724B8481C1293AF');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextVal('seq_fuel_type'), 'S', 'SLASH GROUP','B58C91ABC0594B84AD2497AC273C377B');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextVal('seq_fuel_type'), 'T', 'TIMBER GROUP','60E4145528F24DD6AF5BF5AF0D8FB307');
alter table isw_training_contact add is_active varchar(1) default 'Y';
alter table isw_training_contact add transferable_identity varchar(255);
alter table isw_incident_group add all_tnsp_settings varchar(1) default 'Y';
alter table isw_incident_group add all_tnsp_pri_pro varchar(1) default 'Y';
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'ISWL_PRIORITY_PROGRAM','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'ISWL_COMPLEXITY','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'ISWL_RECOMMENDATION','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'ISW_RESOURCE_TRAINING','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'ISW_RSC_TRAINING_TRAINER','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'ISW_RSC_TRAINING_OBJECTIVE','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'ISW_TRAINING_CONTACT','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'ISW_TRAINING_SETTINGS','Y');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'ISW_TRAINING_SET_FUEL_TYPE','N');
Insert into INC_EXP_TABLES (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'ISW_RSC_TRAINING_FUEL_TYPE','N');


update iswl_complexity set description='TYPE 2 TEAM ASSIGNED' where code='TYPE 2'
/

INSERT INTO SCHEMACHANGELOG( ID, MAJORRELEASENUMBER, MINORRELEASENUMBER, POINTRELEASENUMBER, SCRIPTNAME, DATEAPPLIED)
VALUES (239,'01','02','00.00','patch.239.sc-pg.01_02_00.00.sql', now())
/
UPDATE REVISION SET REVISIONLEVEL = 239
/
