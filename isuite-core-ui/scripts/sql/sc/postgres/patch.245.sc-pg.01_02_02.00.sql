alter table isw_task_queue add TASK_DATA varchar(200)
/

update isw_system_parameter set parameter_value='1' where parameter_name='TASK_QUEUE_INTERVAL'
/

update iswl_sub_group_category set transferable_identity = 'ab5a4b5f-42bf-3e36-b546-328e395eafad' where code = 'C4'
/

INSERT INTO SCHEMACHANGELOG( ID, MAJORRELEASENUMBER, MINORRELEASENUMBER, POINTRELEASENUMBER, SCRIPTNAME, DATEAPPLIED)
VALUES (245,'01','02','02.00','patch.245.sc-pg.01_02_02.00.sql', now())
/
UPDATE REVISION SET REVISIONLEVEL = 245
/