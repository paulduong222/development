ALTER TABLE isw_assignment_time ADD COLUMN hiring_unit_name VARCHAR(200);
ALTER TABLE isw_assignment_time ADD COLUMN hiring_phone VARCHAR(30);
ALTER TABLE isw_assignment_time ADD COLUMN hiring_fax VARCHAR(30);
CREATE SEQUENCE seq_resource_home_unit_contact;
CREATE TABLE isw_resource_home_unit_contact
(
  id BIGINT NOT NULL,
  incident_resource_id BIGINT NOT NULL,
  last_name VARCHAR(35),
  first_name VARCHAR(35),
  unit_id BIGINT,
  address_id BIGINT,
  phone VARCHAR(20),
  email VARCHAR(50),
  created_by VARCHAR(50),
  created_by_id BIGINT,
  created_date TIMESTAMP DEFAULT now(),
  last_modified_by VARCHAR(50),
  last_modified_by_id BIGINT,
  last_modified_date TIMESTAMP DEFAULT now(),
  transferable_identity VARCHAR(255) 
);
ALTER TABLE isw_resource_home_unit_contact ADD CONSTRAINT pk_res_home_unit_contact PRIMARY KEY (id);
ALTER TABLE isw_resource_home_unit_contact ADD CONSTRAINT fk_res_home_un_cont_addr_id FOREIGN KEY (address_id) REFERENCES isw_address (id);
ALTER TABLE isw_resource_home_unit_contact ADD CONSTRAINT fk_res_home_un_cont_inc_res_id FOREIGN KEY (incident_resource_id) REFERENCES isw_incident_resource (id) ON DELETE CASCADE;
ALTER TABLE isw_resource_home_unit_contact ADD CONSTRAINT fk_res_home_un_cont_org_id FOREIGN KEY (unit_id) REFERENCES isw_organization (id);
CREATE SEQUENCE seq_recommendation;
create table iswl_recommendation (
  id BIGINT NOT NULL, 
  code VARCHAR(40) NOT NULL,
  description VARCHAR(255),
  created_date TIMESTAMP DEFAULT now(), 
  created_by VARCHAR(50), 
  created_by_id BIGINT, 
  last_modified_date TIMESTAMP DEFAULT now(), 
  last_modified_by VARCHAR(50), 
  last_modified_by_id BIGINT, 
  transferable_identity VARCHAR(255) 
);
ALTER TABLE iswl_recommendation ADD CONSTRAINT pk_iswl_recommendation PRIMARY KEY (id);
delete from iswl_complexity;
insert into iswl_complexity (id, code, description, transferable_identity) values (nextval('seq_complexity'), 'TYPE A', 'AREA COMMAND','ABD9E860E89C44E491BB4E5F638467FC');
insert into iswl_complexity (id, code, description, transferable_identity) values (nextval('seq_complexity'), 'TYPE 1', 'TYPE 1 TEAM ASSIGNED','6A8BA021422B46709BE6D7C65C24B640');
insert into iswl_complexity (id, code, description, transferable_identity) values (nextval('seq_complexity'), 'TYPE 2', 'TYPE 1 TEAM ASSIGNED','3B6E8D586D344B92982D2D4746350071');
insert into iswl_complexity (id, code, description, transferable_identity) values (nextval('seq_complexity'), 'TYPE 3', 'EXTENDED ATTACK WITH MULTIPLE RESOURCES','3FA42CCD0D9E4D5B9FC13AE4059C5EA8');
insert into iswl_complexity (id, code, description, transferable_identity) values (nextval('seq_complexity'), 'TYPE 4', 'INITIAL ATTACK','80A3153006EA4E3FA8993145E6FE9950');
insert into iswl_complexity (id, code, description, transferable_identity) values (nextval('seq_complexity'), 'TYPE 5', 'INITIAL ATTACK WITH VERY FEW RESOURCES','18D7E437DEB04789A91FD881347165E8');
insert into iswl_complexity (id, code, description, transferable_identity) values (nextval('seq_complexity'), 'TYPE 1 (PRESCRIBED FIRE)', 'HIGH','70178F38251039AC86EC0AA2AD7A3F90');
insert into iswl_complexity (id, code, description, transferable_identity) values (nextval('seq_complexity'), 'TYPE 2 (PRESCRIBED FIRE)', 'MODERATE','16DC4ED7C3283BE78D0AB57CE9319BC8');
insert into iswl_complexity (id, code, description, transferable_identity) values (nextval('seq_complexity'), 'TYPE 3 (PRESCRIBED FIRE)', 'LOW','C26EB01D0D3F3D1C8150B0076D808B22');
delete from iswl_fuel_type;
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextval('seq_fuel_type'), '1 GRASS', 'SHORT GRASS (1 FOOT)','8AB1DE24B02349E3B724B8481C1293AF');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextval('seq_fuel_type'), '2 GRASS', 'TIMBER WITH GRASS UNDERSTORY','E9FA42261DA332009CD10EC54AFA0B9E');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextval('seq_fuel_type'), '3 GRASS', 'TALL GRASS (1 1/2 - 2 FEET)','D6FED266B7683298A74D3BBFB2F8CBEA');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextval('seq_fuel_type'), '4 BRUSH', 'CHAPARRAL (6 FEET)','1867529D3B30492BBE92A59FC239981F');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextval('seq_fuel_type'), '5 BRUSH', 'BRUSH (2 FEET)','BA550D8AE0BF34909FF658E1849B3FF9');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextval('seq_fuel_type'), '6 BRUSH', 'DORMANT BRUSH/HARDWOOD SLASH','ACE5D83CCCFA3F898A0FADE2492C82EE');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextval('seq_fuel_type'), '7 BRUSH', 'SOUTHERN ROUGH','E63ACEDE41E53688AB10CEF427FF4A47');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextval('seq_fuel_type'), '8 TIMBER', 'CLOSED TIMBER LITTER','60E4145528F24DD6AF5BF5AF0D8FB307');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextval('seq_fuel_type'), '9 TIMBER', 'HARDWOOD LITTER','5DD66FC9F90B3DF088607EBFC3A25F48');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextval('seq_fuel_type'), '10 TIMBER', 'TIMBER (WITH LITTER UNDERSTORY)','11D2329DC06F3947847AA1DBB269AA8D');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextval('seq_fuel_type'), '11 SLASH', 'LIGHT LOGGING SLASH','B58C91ABC0594B84AD2497AC273C377B');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextval('seq_fuel_type'), '12 SLASH', 'MEDIUM LOGGING SLASH','F4A1CD7B54C63C99A10DE5609285C4C8');
insert into iswl_fuel_type (id, code, description, transferable_identity) values (nextval('seq_fuel_type'), '13 SLASH', 'HEAVY LOGGING SLASH','533251983651385988470FC117BF5DC3');
insert into iswl_recommendation (id, code, description, transferable_identity) values (nextVal('seq_recommendation'), '1', 'The trainee has successfully performed all tasks in the PTB for the position.  The final evaluator has completed the final evaluator''s verification section and recommended the trainee be considered for agency certification.','7E0CA8D129D732DA92C43C99A71BA9CC');
insert into iswl_recommendation (id, code, description, transferable_identity) values (nextVal('seq_recommendation'), '2', 'The tasks have been performed in a satisfactory manner.  However, opportunities were not available for all tasks (or all uncompleted tasks) to be performed and evaluated on this assignment.  An additional assignment is needed to complete the evaluation.','B6D807291C8F331382490E36B5A83A47');
insert into iswl_recommendation (id, code, description, transferable_identity) values (nextVal('seq_recommendation'), '3', 'The trainee did not complete certain tasks in the PTB in a satisfactory manner and additional training, guidance, or experience is recommended.','220DE34B17993831A5AD44D7F17A505F');
insert into iswl_recommendation (id, code, description, transferable_identity) values (nextVal('seq_recommendation'), '4', 'The individual is severely deficient in the performance of tasks in the PTB for the position and additional training, guidance, or experience is recommended prior to another training assignment.','A84A1C95FFAD3ACB838A2885994D9463');
ALTER TABLE isw_resource_training DROP COLUMN home_unit_contact_last_name;
ALTER TABLE isw_resource_training DROP COLUMN home_unit_contact_first_name;
ALTER TABLE isw_resource_training DROP COLUMN home_unit_contact_title;
ALTER TABLE isw_resource_training DROP COLUMN home_unit_contact_work_phone;
ALTER TABLE isw_resource_training DROP COLUMN home_unit_contact_address_id;
ALTER TABLE isw_resource_training DROP COLUMN home_unit_contact_email;
ALTER TABLE isw_rsc_training_trainer DROP COLUMN home_unit;
ALTER TABLE isw_rsc_training_trainer DROP COLUMN start_date;
ALTER TABLE isw_rsc_training_trainer DROP COLUMN end_date;
ALTER TABLE isw_rsc_training_trainer DROP COLUMN ptb_percentage;
ALTER TABLE isw_rsc_training_trainer DROP COLUMN recommendation_level;
ALTER TABLE isw_rsc_training_trainer DROP CONSTRAINT fk_rsc_traning_trainer_cpx;
ALTER TABLE isw_rsc_training_trainer DROP COLUMN complexity_id;
ALTER TABLE isw_rsc_training_trainer DROP COLUMN number_of_acres;
ALTER TABLE isw_rsc_training_trainer DROP COLUMN tnsp_comments;
ALTER TABLE isw_resource_training ADD COLUMN incident_task_book VARCHAR(1);
ALTER TABLE isw_resource_training ADD COLUMN ptb_percentage INTEGER;
ALTER TABLE isw_resource_training ADD COLUMN recommendation_level INTEGER;
ALTER TABLE isw_resource_training ADD COLUMN complexity_id BIGINT;
ALTER TABLE isw_resource_training ADD CONSTRAINT fk_resource_training_cpx FOREIGN KEY (complexity_id) REFERENCES iswl_complexity (id);
ALTER TABLE isw_resource_training ADD COLUMN number_of_acres BIGINT;
ALTER TABLE isw_resource_training ADD COLUMN tnsp_comments VARCHAR(255);
ALTER TABLE isw_rsc_training_trainer ADD COLUMN unit_id BIGINT;
ALTER TABLE isw_rsc_training_trainer ADD CONSTRAINT fk_unit_id FOREIGN KEY (unit_id) REFERENCES isw_organization (id);
INSERT INTO SCHEMACHANGELOG( ID, MAJORRELEASENUMBER, MINORRELEASENUMBER, POINTRELEASENUMBER, SCRIPTNAME, DATEAPPLIED)
VALUES (235,'01','01','02.00','patch.235.sc-pg.01_01_02.00.sql', now());
UPDATE REVISION SET REVISIONLEVEL = 235;
