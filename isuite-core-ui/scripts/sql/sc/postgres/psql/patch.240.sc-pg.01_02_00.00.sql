insert into inc_exp_tables (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'isw_iap_personnel_res','Y');
delete from inc_exp_tables where lower(t_name)='iswl_priority_program';
delete from inc_exp_tables where lower(t_name)='isw_resource_training';
delete from inc_exp_tables where lower(t_name)='isw_rsc_training_trainer';
delete from inc_exp_tables where lower(t_name)='isw_rsc_training_objective';
delete from inc_exp_tables where lower(t_name)='isw_training_contact';
delete from inc_exp_tables where lower(t_name)='isw_training_settings';
delete from inc_exp_tables where lower(t_name)='isw_training_set_fuel_type';
delete from inc_exp_tables where lower(t_name)='isw_rsc_training_fuel_type';
insert into inc_exp_tables (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'iswl_fuel_type','Y');
insert into inc_exp_tables (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'iswl_priority_program','Y');
insert into inc_exp_tables (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'isw_incident_fuel_type','N');
insert into inc_exp_tables (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'isw_resource_home_unit_contact','Y');
insert into inc_exp_tables (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'isw_resource_training','Y');
insert into inc_exp_tables (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'isw_rsc_training_fuel_type','N');
insert into inc_exp_tables (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'isw_rsc_training_objective','Y');
insert into inc_exp_tables (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'isw_rsc_training_trainer','Y');
insert into inc_exp_tables (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'isw_training_contact','Y');
insert into inc_exp_tables (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'isw_training_settings','Y');
insert into inc_exp_tables (ID,T_NAME,HAS_TID) values (nextVal('seq_inc_exp'),'isw_training_set_fuel_type','N');
update inc_exp_tables set t_name=upper(t_name);
INSERT INTO SCHEMACHANGELOG( ID, MAJORRELEASENUMBER, MINORRELEASENUMBER, POINTRELEASENUMBER, SCRIPTNAME, DATEAPPLIED)
VALUES (240,'01','02','00.00','patch.240.sc-pg.01_02_00.00.sql', now());
UPDATE REVISION SET REVISIONLEVEL = 240;

