alter table isw_rsc_training_trainer add resource_name varchar(70)
/

alter table isw_rsc_training_trainer add incident_resource_id bigint
/

alter table isw_rsc_training_trainer add constraint fk_rsc_trn_trnr_inc_rsc foreign key (incident_resource_id) references isw_incident_resource (id) on delete cascade
/

alter table isw_resource_training add recommendation_id bigint
/

alter table isw_resource_training add constraint fk_rsc_trn_recommend foreign key (recommendation_id) references iswl_recommendation (id) on delete cascade
/

alter table isw_resource_training drop recommendation_level
/

alter table isw_training_contact add incident_resource_id bigint
/

alter table isw_training_contact add constraint fk_trn_contact_inc_rsc foreign key (incident_resource_id) references isw_incident_resource (id) on delete cascade
/

alter table isw_incident_resource drop training_contact_id
/

alter table isw_training_contact add CREATED_BY VARCHAR(50)
/

alter table isw_training_contact add CREATED_BY_ID BIGINT
/

alter table isw_training_contact add CREATED_DATE TIMESTAMP DEFAULT now()
/

alter table isw_training_contact add LAST_MODIFIED_BY VARCHAR(50)
/

alter table isw_training_contact add LAST_MODIFIED_BY_ID BIGINT
/

alter table isw_training_contact add LAST_MODIFIED_DATE TIMESTAMP DEFAULT now()
/

insert into iswl_event_type (id, event_type_code, event_type, transferable_identity)
values (100, 'CX', 'COMPLEX','94667393AE6E444A9933C4DAE5D79A1F')
/

INSERT INTO SCHEMACHANGELOG( ID, MAJORRELEASENUMBER, MINORRELEASENUMBER, POINTRELEASENUMBER, SCRIPTNAME, DATEAPPLIED)
VALUES (238,'01','01','05.00','patch.238.sc-pg.01_01_05.00.sql', now())
/
UPDATE REVISION SET REVISIONLEVEL = 238
/
