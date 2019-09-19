drop table isw_user_message
/

drop table isw_message
/

create table isw_message (
id bigint not null,
effective_date timestamp,
expire_date timestamp,
message_text text,
type varchar(20),
status varchar(20),
CREATED_BY VARCHAR(50),
CREATED_BY_ID bigint,
CREATED_DATE TIMESTAMP DEFAULT now(),
LAST_MODIFIED_BY VARCHAR(50),
LAST_MODIFIED_BY_ID bigint,
LAST_MODIFIED_DATE TIMESTAMP DEFAULT now()
)
/

alter table isw_message add constraint pk_message primary key(id)
/

alter table isw_message add constraint fk_message_user_c foreign key (CREATED_BY_ID) references isw_user (id)
/

alter table isw_message add constraint fk_message_user_u foreign key (LAST_MODIFIED_BY_ID) references isw_user (id)
/

create table isw_user_message (
id bigint not null,
user_id bigint,
message_id bigint not null,
acknowledge_date timestamp
)
/

alter table isw_user_message add constraint pk_user_message primary key (id)
/

alter table isw_user_message add constraint fk_user_message_user foreign key (user_id) references isw_user (id) on delete cascade
/

alter table isw_user_message add constraint fk_msg_msg_user foreign key (message_id) references isw_message (id) on delete cascade
/


CREATE OR REPLACE FUNCTION update_res_num(p_incident_resource_id bigint)
  RETURNS bigint AS
$BODY$
DECLARE
  v_rnum_id BIGINT;
BEGIN
  SELECT case when RES_NUM_ID is not null then res_num_id else 0 end
  INTO v_rnum_id
  FROM ISW_INCIDENT_RESOURCE
  WHERE ID = p_incident_resource_id;
  IF v_rnum_id > 0 THEN
    RETURN v_rnum_id;
  ELSE
    SELECT MAX(RES_NUM_ID)
    INTO v_rnum_id
    FROM ISW_INCIDENT_RESOURCE
    WHERE INCIDENT_ID =
      (SELECT INCIDENT_ID
      FROM ISW_INCIDENT_RESOURCE
      WHERE ID = p_incident_resource_id
      );
	  if v_rnum_id is null then
		v_rnum_id = 0;
	  end if;
    UPDATE ISW_INCIDENT_RESOURCE
    SET res_num_id = v_rnum_id + 1
    WHERE ID       = p_incident_resource_id;
    RETURN v_rnum_id + 1;
  END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
/

drop view iswv_cr_person_plan
/

CREATE OR REPLACE VIEW iswv_cr_person_plan AS 
 SELECT DISTINCT i.id AS incident_id, unit.unit_code || '-' || i.nbr AS incident_number, i.incident_name, 
    acc.code AS accrual, wp.dm_release_date AS actual_release_date, 
    wp.dm_release_remarks AS actual_release_remarks, 
    wp.dm_release_date AS actual_release_time, ag.agency_code AS agency, 
    cpia.agreement_number, 
        CASE
            WHEN air.is_dispatch_notified THEN 'Y'::text
            ELSE 'N'::text
        END AS is_air_travel_to_dispatch, 
    air.airline, cd.assign_date, wp.ci_check_in_date AS check_in_date, 
    wp.ci_check_in_date AS check_in_time, 
        CASE
            WHEN wp.dm_is_checkout_form_printed THEN 'N'::text
            ELSE 'Y'::text
        END AS is_checkout_form_printed, 
        CASE
            WHEN r.is_contracted THEN 'Y'::text
            ELSE 'N'::text
        END AS is_contracted, 
    contr.name AS contractor_name, 
    wp.dm_tentative_demob_city AS demobilization_city, 
        CASE
            WHEN NOT wp.ci_first_work_date IS NULL AND NOT wp.ci_length_at_assignment IS NULL THEN wp.ci_first_work_date + '1 day'::interval * wp.ci_length_at_assignment::double precision
            ELSE NULL::timestamp without time zone
        END AS demobilization_date, 
    ics.cs_abbreviation AS demobilization_state, 
        CASE
            WHEN wp.dm_is_release_dispatch_notif THEN 'Y'::text
            ELSE 'N'::text
        END AS is_disp_notified_of_act_rel, 
        CASE
            WHEN wp.dm_is_planning_dispatch_notif THEN 'Y'::text
            ELSE 'N'::text
        END AS is_disp_notified_of_tent_rel, 
    at.employment_type, wp.dm_estimated_arrival_date AS estimated_arrival_time, 
    wp.dm_estimated_arrival_date AS estimated_date_of_arrival, 
    at.fax AS fax_number, wp.ci_first_work_date AS first_day_of_work, 
    r.first_name, air.flight_time, dmjp.code AS incident_jetport, 
        CASE
            WHEN r.parent_resource_id IS NULL THEN 'N'::text
            ELSE 'Y'::text
        END AS is_rostered, 
    k.code AS item_code, k.description AS item_code_description, 
        CASE
            WHEN air.is_itinerary_received THEN 'Y'::text
            ELSE 'N'::text
        END AS is_itinerary_received, 
    ijp.code AS jetport, r.last_name, 
        CASE
            WHEN r.is_leader THEN 'Y'::text
            ELSE 'N'::text
        END AS is_leader, 
    ( SELECT isw_resource.last_name::text || 
                CASE
                    WHEN isw_resource.first_name IS NULL THEN ''::text
                    ELSE ', '::text || isw_resource.first_name::text
                END AS name
           FROM isw_resource
          WHERE isw_resource.id = (( SELECT min(r4.id) AS min
                   FROM isw_resource r4
                  WHERE r4.parent_resource_id = r.id AND r4.leader_type = 1))) AS leader_name, 
    wp.ci_length_at_assignment AS length_of_assignment, 
    get_resource_level(r.id) AS resource_level, 
    rm.start_date AS mobilization_date, r.name_on_picture_id AS name_on_id, 
    r.number_of_personnel, r.other_1 AS other1, r.other_2 AS other2, 
    r.other_3 AS other3, 
        CASE
            WHEN r.is_person THEN 'Y'::text
            ELSE 'N'::text
        END AS is_overhead, 
        CASE
            WHEN k.is_line_overhead THEN 'Y'::text
            ELSE 'N'::text
        END AS is_line_overhead, 
    r.parent_resource_id AS parent_id, pag.agency_code AS payment_agency, 
    r.phone AS phone_number, 
        CASE
            WHEN wp.dm_is_reassignable THEN 'Y'::text
            ELSE 'N'::text
        END AS is_reassignment, 
    cd.cost_remarks AS remarks_cost, 
    wp.ci_pre_planning_remarks AS remarks_plans, rq.code AS request_category, 
    a.request_number, r.id AS resource_id, 
        CASE
            WHEN r.is_person THEN (r.last_name::text || 
            CASE
                WHEN r.first_name IS NULL THEN ''::text
                ELSE ', '::text || r.first_name::text
            END)::character varying
            ELSE r.resource_name
        END AS resource_name, 
    wp.dm_travel_method AS return_method_of_travel, dpt.code AS section_code, 
    dpt.description AS section_description, air.remarks AS special_instructions, 
    a.assignment_status AS status, 
    wp.dm_tentative_release_date AS tentative_release_date, 
    wp.dm_planning_remarks AS tentative_release_remarks, 
    wp.dm_tentative_release_date AS tentative_release_time, 
        CASE
            WHEN a.is_training THEN 'Y'::text
            ELSE 'N'::text
        END AS is_trainee, 
    air.hours_to_airport AS travel_hours, wp.ci_travel_method AS travel_method, 
    air.minutes_to_airport AS travel_minutes, org.unit_code AS unit_id
   FROM isw_resource r
   LEFT JOIN iswl_agency ag ON r.agency_id = ag.id
   LEFT JOIN isw_resource_mobilization rm ON r.id = rm.resource_id
   LEFT JOIN isw_organization org ON r.organization_id = org.id, 
    isw_incident i
   LEFT JOIN isw_organization unit ON i.unit_id = unit.id, 
    isw_incident_resource ir
   LEFT JOIN isw_work_period wp ON ir.id = wp.incident_resource_id
   LEFT JOIN iswl_country_subdivision ics ON wp.dm_tentative_demob_state_id = ics.id
   LEFT JOIN iswl_jet_port ijp ON wp.ci_arrival_jet_port_id = ijp.id
   LEFT JOIN isw_work_period_assignment wpa ON wp.id = wpa.work_period_id
   LEFT JOIN isw_assignment a ON wpa.assignment_id = a.id
   LEFT JOIN isw_assignment_time at ON a.id = at.assignment_id
   LEFT JOIN isw_contr_payment_info cpi ON at.id = cpi.assignment_time_id AND at.employment_type::text = 'CONTRACTOR'::text
   LEFT JOIN isw_contractor_agreement cpia ON cpi.contractor_agreement_id = cpia.id
   LEFT JOIN isw_contractor contr ON cpi.contractor_id = contr.id
   LEFT JOIN iswl_kind k ON a.kind_id = k.id
   LEFT JOIN iswl_request_category rq ON k.request_category_id = rq.id
   LEFT JOIN iswl_department dpt ON k.department_id = dpt.id
   LEFT JOIN isw_air_travel air ON wp.dm_air_travel_id = air.id
   LEFT JOIN iswl_jet_port dmjp ON air.jet_port_id = dmjp.id
   LEFT JOIN isw_cost_data cd ON ir.cost_data_id = cd.id
   LEFT JOIN iswl_agency pag ON cd.payment_agency_id = pag.id
   LEFT JOIN iswl_accrual_code acc ON cd.accrual_code_id = acc.id
  WHERE i.id = ir.incident_id AND r.id = ir.resource_id AND r.deleted_date IS NULL AND r.is_person = true
/

drop view iswv_cr_person_time
/

CREATE OR REPLACE VIEW iswv_cr_person_time AS 
 SELECT DISTINCT i.id AS incident_id, unit.unit_code || '-' || i.nbr AS incident_number, i.incident_name, 
    acc.code AS accrual, wp.dm_release_date AS actual_release_date, 
    wp.dm_release_remarks AS actual_release_remarks, 
    wp.dm_release_date AS actual_release_time, ao.office_name AS admin_office, 
    ag.agency_code AS agency, ca.agreement_number, 
        CASE
            WHEN air.is_dispatch_notified THEN 'Y'::text
            ELSE 'N'::text
        END AS is_air_travel_to_dispatch, 
    air.airline, cd.assign_date, ca.start_date AS beginning_date, 
    wp.ci_check_in_date AS check_in_date, wp.ci_check_in_date AS check_in_time, 
        CASE
            WHEN wp.dm_is_checkout_form_printed THEN 'Y'::text
            ELSE 'N'::text
        END AS is_checkout_form_printed, 
    rcls.rate_classname AS class_name, 
        CASE
            WHEN k.is_strike_team THEN 'Y'::text
            ELSE 'N'::text
        END AS is_strike_team_task_force, 
        CASE
            WHEN r.is_contracted THEN 'Y'::text
            ELSE 'N'::text
        END AS is_contracted, 
    (((
        CASE
            WHEN cadr.address_line_1 IS NULL THEN ''::character varying
            ELSE cadr.address_line_1
        END::text || 
        CASE
            WHEN cadr.address_line_2 IS NULL THEN ''::text
            ELSE ', '::text || cadr.address_line_2::text
        END) || 
        CASE
            WHEN cadr.city IS NULL THEN ''::text
            ELSE ', '::text || cadr.city::text
        END) || 
        CASE
            WHEN ccounty.cs_abbreviation IS NULL THEN ''::text
            ELSE ', '::text || ccounty.cs_abbreviation::text
        END) || 
        CASE
            WHEN cadr.postal_code IS NULL THEN ''::text
            ELSE ', '::text || cadr.postal_code::text
        END AS contractor_address, 
    r.name_on_picture_id AS contractor_name, c.phone AS contractor_telephone, 
    irk.rate_type AS cost_uom, 
    wp.dm_tentative_demob_city AS demobilization_city, 
        CASE
            WHEN NOT wp.ci_first_work_date IS NULL AND NOT wp.ci_length_at_assignment IS NULL THEN wp.ci_first_work_date + '1 day'::interval * wp.ci_length_at_assignment::double precision
            ELSE NULL::timestamp without time zone
        END AS demobilization_date, 
    demobstate.cs_abbreviation AS demobilization_state, 
    cpi.desc1 AS description_1, cpi.desc2 AS description_2, 
        CASE
            WHEN wp.dm_is_release_dispatch_notif THEN 'Y'::text
            ELSE 'N'::text
        END AS is_disp_notified_of_act_rel, 
        CASE
            WHEN wp.dm_is_planning_dispatch_notif THEN 'Y'::text
            ELSE 'N'::text
        END AS is_disp_notified_of_tent_rel, 
    c.duns AS duns_number, at.employment_type, 
    wp.dm_estimated_arrival_date AS estimated_date_of_arrival, 
    wp.dm_estimated_arrival_date AS estimated_time_of_arrival, 
    ca.end_date AS expiration_date, at.fax AS fax_number, 
    wp.ci_first_work_date AS first_day_of_work, r.first_name, air.flight_time, 
    cr.guarantee_amount AS guarantee_rate, cpi.hired_date AS hire_date, 
    cpi.hired_date AS hire_time, dmjp.code AS incident_jetport, 
    at.of_remarks AS invoice_remarks, 
        CASE
            WHEN r.parent_resource_id IS NULL THEN 'N'::text
            ELSE 'Y'::text
        END AS is_rostered, 
    k.code AS item_code, k.description AS item_code_description, 
        CASE
            WHEN air.is_itinerary_received THEN 'Y'::text
            ELSE 'N'::text
        END AS is_itinerary_received, 
    ijp.code AS jetport, r.last_name, 
        CASE
            WHEN r.is_leader THEN 'Y'::text
            ELSE 'N'::text
        END AS is_leader, 
    ( SELECT isw_resource.last_name::text || 
                CASE
                    WHEN isw_resource.first_name IS NULL THEN ''::text
                    ELSE ', '::text || isw_resource.first_name::text
                END AS name
           FROM isw_resource
          WHERE isw_resource.id = (( SELECT min(r4.id) AS min
                   FROM isw_resource r4
                  WHERE r4.parent_resource_id = r.id AND r4.leader_type = 1))) AS leader_name, 
    wp.ci_length_at_assignment AS length_of_assignment, 
    get_resource_level(r.id) AS resource_level, 
    wp.ci_travel_method AS method_of_travel, a.start_date AS mobilization_date, 
    r.name_on_picture_id AS name_on_id, r.number_of_personnel, 
    r.other_1 AS other1, r.other_2 AS other2, r.other_3 AS other3, 
        CASE
            WHEN r.is_person THEN 'Y'::text
            ELSE 'N'::text
        END AS is_overhead, 
        CASE
            WHEN k.is_line_overhead THEN 'Y'::text
            ELSE 'N'::text
        END AS is_line_overhead, 
    r.parent_resource_id AS parent_id, pag.agency_code AS payment_agency, 
    (((
        CASE
            WHEN atadr.address_line_1 IS NULL THEN ''::character varying
            ELSE atadr.address_line_1
        END::text || 
        CASE
            WHEN atadr.address_line_2 IS NULL THEN ''::text
            ELSE ', '::text || atadr.address_line_2::text
        END) || 
        CASE
            WHEN atadr.city IS NULL THEN ''::text
            ELSE ', '::text || atadr.city::text
        END) || 
        CASE
            WHEN atcounty.cs_abbreviation IS NULL THEN ''::text
            ELSE ', '::text || atcounty.cs_abbreviation::text
        END) || 
        CASE
            WHEN atadr.postal_code IS NULL THEN ''::text
            ELSE ', '::text || atadr.postal_code::text
        END AS person_address_info, 
    r.phone AS phone_number, ca.point_of_hire, cr.rate_amount AS rate, 
    cr.rate_type, 
        CASE
            WHEN wp.dm_is_reassignable THEN 'Y'::text
            ELSE 'N'::text
        END AS is_reassignment, 
    cd.cost_remarks AS remarks_cost, wp.dm_planning_remarks AS remarks_plans, 
    reqcat.code AS request_category, a.request_number, r.id AS resource_id, 
        CASE
            WHEN r.is_person THEN (r.last_name::text || 
            CASE
                WHEN r.first_name IS NULL THEN ''::text
                ELSE ', '::text || r.first_name::text
            END)::character varying
            ELSE r.resource_name
        END AS resource_name, 
    wp.dm_travel_method AS return_travel_method, dpt.code AS section_code, 
    dpt.description AS section_description, air.remarks AS special_instructions, 
    cr.rate_amount AS special_rate, a.assignment_status AS status, 
    r.phone AS telephone, 
    wp.dm_tentative_release_date AS tentative_release_date, 
    wp.dm_planning_remarks AS tentative_release_remarks, 
    wp.dm_tentative_release_date AS tentative_release_time, 
    cr.unit_of_measure AS time_uom, 
        CASE
            WHEN a.is_training THEN 'Y'::text
            ELSE 'N'::text
        END AS is_trainee, 
    air.hours_to_airport AS travel_hours, 
    air.minutes_to_airport AS travel_minutes, cpi.vin_name AS unique_name_vin, 
    o.unit_code AS unit_id
   FROM isw_incident i
   LEFT JOIN isw_organization unit ON i.unit_id = unit.id
   LEFT JOIN isw_incident_resource ir ON i.id = ir.incident_id
   LEFT JOIN isw_resource r ON ir.resource_id = r.id
   LEFT JOIN iswl_agency ag ON r.agency_id = ag.id
   LEFT JOIN isw_resource_contractor rc ON r.id = rc.resource_id
   LEFT JOIN isw_contractor c ON rc.contractor_id = c.id
   LEFT JOIN isw_contractor_agreement ca ON c.id = ca.contractor_id
   LEFT JOIN isw_contr_payment_info cpi ON ca.id = cpi.contractor_agreement_id
   LEFT JOIN isw_contr_payinfo_rate cpr ON cpi.id = cpr.contractor_pay_info_id
   LEFT JOIN isw_contractor_rate cr ON cpr.contractor_rate_id = cr.id
   LEFT JOIN isw_organization o ON r.organization_id = o.id
   LEFT JOIN isw_work_period wp ON ir.id = wp.incident_resource_id
   LEFT JOIN isw_work_period_assignment wpa ON wp.id = wpa.work_period_id
   LEFT JOIN isw_assignment a ON wpa.assignment_id = a.id
   LEFT JOIN isw_assignment_time at ON a.id = at.assignment_id
   LEFT JOIN iswl_jet_port ijp ON wp.ci_arrival_jet_port_id = ijp.id
   LEFT JOIN isw_address atadr ON at.mailing_address_id = atadr.id
   LEFT JOIN iswl_country_subdivision atcounty ON atadr.country_subdivision_id = atcounty.id
   LEFT JOIN isw_address cadr ON c.address_id = cadr.id
   LEFT JOIN iswl_country_subdivision ccounty ON cadr.country_subdivision_id = ccounty.id
   LEFT JOIN isw_cost_data cd ON ir.cost_data_id = cd.id
   LEFT JOIN iswl_agency pag ON cd.payment_agency_id = pag.id
   LEFT JOIN iswl_kind k ON a.kind_id = k.id
   LEFT JOIN isw_air_travel air ON wp.dm_air_travel_id = air.id
   LEFT JOIN iswl_jet_port dmjp ON air.jet_port_id = dmjp.id
   LEFT JOIN iswl_request_category reqcat ON k.request_category_id = reqcat.id
   LEFT JOIN isw_inccost_rate_kind irk ON k.id = irk.kind_id
   LEFT JOIN iswl_department dpt ON k.department_id = dpt.id
   LEFT JOIN iswl_accrual_code acc ON cd.accrual_code_id = acc.id
   LEFT JOIN isw_ad_payment_info adpi ON at.id = adpi.assignment_time_id
   LEFT JOIN iswl_rate_class_rate rcls ON adpi.rate_class_rate_id = rcls.id
   LEFT JOIN isw_admin_office ao ON ao.address_id = cadr.id
   LEFT JOIN iswl_country_subdivision demobstate ON wp.dm_tentative_demob_state_id = demobstate.id
  WHERE r.is_person = true
/

drop view iswv_cr_resource_cost
/

CREATE OR REPLACE VIEW iswv_cr_resource_cost AS 
 SELECT DISTINCT i.id AS incident_id, i.incident_name, unit.unit_code || '-' || i.nbr AS incident_number, 
    ac.account_code AS accounting_code, acc.code AS accrual_code, 
    acc.description AS accrual_description, irdc.activity_date, 
    agcy.agency_code, agcy.agency_name AS agency_description, 
    irdc.aircraft_cost_amount AS aircraft_cost, 
    cg.cost_group_name AS cost_group, cg.description AS cost_group_description, 
    irdc.rate_type AS cost_uom, 
        CASE
            WHEN k.is_direct THEN 'Y'::text
            ELSE 'N'::text
        END AS is_direct, 
    irdc.flight_hours AS flight_hrs, irdc.total_cost_amount AS grand_total_cost, 
    gc.code AS group_code, k.code AS item_code, 
    k.description AS item_description, irdc.cargo_pounds AS lbs_cargo, 
    irdc.number_of_trips AS num_trips, r.parent_resource_id AS parent_id, 
    irdc.number_of_passengers AS passenger, 
    pagcy.agency_code AS payment_agency_code, 
    pagcy.agency_name AS payment_agency_description, 
    irdc.primary_total_amount AS primary_cost, a.request_number, 
        CASE
            WHEN r.is_person THEN (r.last_name::text || 
            CASE
                WHEN r.first_name IS NULL THEN ''::text
                ELSE ', '::text || r.first_name::text
            END)::character varying
            ELSE r.resource_name
        END AS resource_name, 
    irdc.retardant_gallons AS retardant_gal, dpt.code AS section_code, 
    dpt.description AS section_description, shft.shift_name AS shift, 
    sgc.code AS sub_group_code, 
    irdc.subordinate_total_amount AS subordinate_cost, 
    irdc.unit_cost_amount AS unit_cost, o.organization_name AS unit_description, 
    o.unit_code AS unit_id, irdc.units, irdc.water_gallons AS water_gal
   FROM isw_incident i
   LEFT JOIN isw_organization unit ON i.unit_id = unit.id
   LEFT JOIN isw_incident_resource ir ON i.id = ir.incident_id
   LEFT JOIN isw_inc_res_daily_cost irdc ON ir.id = irdc.incident_resource_id
   LEFT JOIN isw_cost_group cg ON irdc.cost_group_id = cg.id
   LEFT JOIN isw_account_code ac ON irdc.incident_account_code_id = ac.id
   LEFT JOIN iswl_accrual_code acc ON irdc.accrual_code_id = acc.id
   LEFT JOIN isw_resource r ON ir.resource_id = r.id
   LEFT JOIN iswl_agency agcy ON r.agency_id = agcy.id
   LEFT JOIN isw_work_period wp ON ir.id = wp.incident_resource_id
   LEFT JOIN isw_work_period_assignment wpa ON wp.id = wpa.work_period_id
   LEFT JOIN isw_assignment a ON wpa.assignment_id = a.id
   LEFT JOIN iswl_kind k ON a.kind_id = k.id
   LEFT JOIN iswl_group_category gc ON k.group_category_id = gc.id
   LEFT JOIN isw_cost_data cd ON ir.cost_data_id = cd.id
   LEFT JOIN iswl_agency pagcy ON cd.payment_agency_id = pagcy.id
   LEFT JOIN iswl_department dpt ON k.department_id = dpt.id
   LEFT JOIN isw_incident_shift shft ON irdc.incident_shift_id = shft.id
   LEFT JOIN iswl_sub_group_category sgc ON k.sub_group_category_id = sgc.id
   LEFT JOIN isw_organization o ON r.organization_id = o.id
/

drop view iswv_cr_resource_plan
/

CREATE OR REPLACE VIEW iswv_cr_resource_plan AS 
 SELECT DISTINCT i.id AS incident_id, unit.unit_code || '-' || i.nbr AS incident_number, i.incident_name, 
    acc.code AS accrual, wp.dm_release_date AS actual_release_date, 
    wp.dm_release_remarks AS actual_release_remarks, 
    wp.dm_release_date AS actual_release_time, ag.agency_code AS agency, 
    cpia.agreement_number, 
        CASE
            WHEN air.is_dispatch_notified THEN 'Y'::text
            ELSE 'N'::text
        END AS is_air_travel_to_dispatch, 
    air.airline, cd.assign_date, wp.ci_check_in_date AS check_in_date, 
    wp.ci_check_in_date AS check_in_time, 
        CASE
            WHEN wp.dm_is_checkout_form_printed THEN 'N'::text
            ELSE 'Y'::text
        END AS is_checkout_form_printed, 
        CASE
            WHEN r.is_contracted THEN 'Y'::text
            ELSE 'N'::text
        END AS is_contracted, 
    contr.name AS contractor_name, 
    wp.dm_tentative_demob_city AS demobilization_city, 
        CASE
            WHEN NOT wp.ci_first_work_date IS NULL AND NOT wp.ci_length_at_assignment IS NULL THEN wp.ci_first_work_date + '1 day'::interval * wp.ci_length_at_assignment::double precision
            ELSE NULL::timestamp without time zone
        END AS demobilization_date, 
    ics.cs_abbreviation AS demobilization_state, 
        CASE
            WHEN wp.dm_is_release_dispatch_notif THEN 'Y'::text
            ELSE 'N'::text
        END AS is_disp_notified_of_act_rel, 
        CASE
            WHEN wp.dm_is_planning_dispatch_notif THEN 'Y'::text
            ELSE 'N'::text
        END AS is_disp_notified_of_tent_rel, 
    at.employment_type, wp.dm_estimated_arrival_date AS estimated_arrival_time, 
    wp.dm_estimated_arrival_date AS estimated_date_of_arrival, 
    at.fax AS fax_number, wp.ci_first_work_date AS first_day_of_work, 
    r.first_name, air.flight_time, dmjp.code AS incident_jetport, 
        CASE
            WHEN r.parent_resource_id IS NULL THEN 'N'::text
            ELSE 'Y'::text
        END AS is_rostered, 
    k.code AS item_code, k.description AS item_code_description, 
        CASE
            WHEN air.is_itinerary_received THEN 'Y'::text
            ELSE 'N'::text
        END AS is_itinerary_received, 
    ijp.code AS jetport, r.last_name, 
        CASE
            WHEN r.is_leader THEN 'Y'::text
            ELSE 'N'::text
        END AS is_leader, 
    ( SELECT isw_resource.last_name::text || 
                CASE
                    WHEN isw_resource.first_name IS NULL THEN ''::text
                    ELSE ', '::text || isw_resource.first_name::text
                END AS name
           FROM isw_resource
          WHERE isw_resource.id = (( SELECT min(r4.id) AS min
                   FROM isw_resource r4
                  WHERE r4.parent_resource_id = r.id AND r4.leader_type = 1))) AS leader_name, 
    wp.ci_length_at_assignment AS length_of_assignment, 
    get_resource_level(r.id) AS resource_level, 
    rm.start_date AS mobilization_date, r.name_on_picture_id AS name_on_id, 
    r.number_of_personnel, r.other_1 AS other1, r.other_2 AS other2, 
    r.other_3 AS other3, 
        CASE
            WHEN r.is_person THEN 'Y'::text
            ELSE 'N'::text
        END AS is_overhead, 
        CASE
            WHEN k.is_line_overhead THEN 'Y'::text
            ELSE 'N'::text
        END AS is_line_overhead, 
    r.parent_resource_id AS parent_id, pag.agency_code AS payment_agency, 
    r.phone AS phone_number, 
        CASE
            WHEN wp.dm_is_reassignable THEN 'Y'::text
            ELSE 'N'::text
        END AS is_reassignment, 
    cd.cost_remarks AS remarks_cost, 
    wp.ci_pre_planning_remarks AS remarks_plans, rq.code AS request_category, 
    a.request_number, r.id AS resource_id, 
        CASE
            WHEN r.is_person THEN (r.last_name::text || 
            CASE
                WHEN r.first_name IS NULL THEN ''::text
                ELSE ', '::text || r.first_name::text
            END)::character varying
            ELSE r.resource_name
        END AS resource_name, 
    wp.dm_travel_method AS return_method_of_travel, dpt.code AS section_code, 
    dpt.description AS section_description, air.remarks AS special_instructions, 
    a.assignment_status AS status, 
    wp.dm_tentative_release_date AS tentative_release_date, 
    wp.dm_planning_remarks AS tentative_release_remarks, 
    wp.dm_tentative_release_date AS tentative_release_time, 
        CASE
            WHEN a.is_training THEN 'Y'::text
            ELSE 'N'::text
        END AS is_trainee, 
    air.hours_to_airport AS travel_hours, wp.ci_travel_method AS travel_method, 
    air.minutes_to_airport AS travel_minutes, org.unit_code AS unit_id
   FROM isw_resource r
   LEFT JOIN iswl_agency ag ON r.agency_id = ag.id
   LEFT JOIN isw_resource_mobilization rm ON r.id = rm.resource_id
   LEFT JOIN isw_organization org ON r.organization_id = org.id, 
    isw_incident i
   LEFT JOIN isw_organization unit ON i.unit_id = unit.id, 
    isw_incident_resource ir
   LEFT JOIN isw_work_period wp ON ir.id = wp.incident_resource_id
   LEFT JOIN iswl_country_subdivision ics ON wp.dm_tentative_demob_state_id = ics.id
   LEFT JOIN iswl_jet_port ijp ON wp.ci_arrival_jet_port_id = ijp.id
   LEFT JOIN isw_work_period_assignment wpa ON wp.id = wpa.work_period_id
   LEFT JOIN isw_assignment a ON wpa.assignment_id = a.id
   LEFT JOIN isw_assignment_time at ON a.id = at.assignment_id
   LEFT JOIN isw_contr_payment_info cpi ON at.id = cpi.assignment_time_id AND at.employment_type::text = 'CONTRACTOR'::text
   LEFT JOIN isw_contractor_agreement cpia ON cpi.contractor_agreement_id = cpia.id
   LEFT JOIN isw_contractor contr ON cpi.contractor_id = contr.id
   LEFT JOIN iswl_kind k ON a.kind_id = k.id
   LEFT JOIN iswl_request_category rq ON k.request_category_id = rq.id
   LEFT JOIN iswl_department dpt ON k.department_id = dpt.id
   LEFT JOIN isw_air_travel air ON wp.dm_air_travel_id = air.id
   LEFT JOIN iswl_jet_port dmjp ON air.jet_port_id = dmjp.id
   LEFT JOIN isw_cost_data cd ON ir.cost_data_id = cd.id
   LEFT JOIN iswl_agency pag ON cd.payment_agency_id = pag.id
   LEFT JOIN iswl_accrual_code acc ON cd.accrual_code_id = acc.id
  WHERE i.id = ir.incident_id AND r.id = ir.resource_id AND r.deleted_date IS NULL
/

drop view iswv_cr_resource_time
/

CREATE OR REPLACE VIEW iswv_cr_resource_time AS 
 SELECT DISTINCT i.id AS incident_id, unit.unit_code || '-' || i.nbr AS incident_number, i.incident_name, 
    acc.code AS accrual, wp.dm_release_date AS actual_release_date, 
    wp.dm_release_remarks AS actual_release_remarks, 
    wp.dm_release_date AS actual_release_time, ao.office_name AS admin_office, 
    ag.agency_code AS agency, ca.agreement_number, 
        CASE
            WHEN air.is_dispatch_notified THEN 'Y'::text
            ELSE 'N'::text
        END AS is_air_travel_to_dispatch, 
    air.airline, cd.assign_date, ca.start_date AS beginning_date, 
    wp.ci_check_in_date AS check_in_date, wp.ci_check_in_date AS check_in_time, 
        CASE
            WHEN wp.dm_is_checkout_form_printed THEN 'Y'::text
            ELSE 'N'::text
        END AS is_checkout_form_printed, 
    rcls.rate_classname AS class_name, 
        CASE
            WHEN k.is_strike_team THEN 'Y'::text
            ELSE 'N'::text
        END AS is_strike_team_task_force, 
        CASE
            WHEN r.is_contracted THEN 'Y'::text
            ELSE 'N'::text
        END AS is_contracted, 
    (((
        CASE
            WHEN cadr.address_line_1 IS NULL THEN ''::character varying
            ELSE cadr.address_line_1
        END::text || 
        CASE
            WHEN cadr.address_line_2 IS NULL THEN ''::text
            ELSE ', '::text || cadr.address_line_2::text
        END) || 
        CASE
            WHEN cadr.city IS NULL THEN ''::text
            ELSE ', '::text || cadr.city::text
        END) || 
        CASE
            WHEN ccounty.cs_abbreviation IS NULL THEN ''::text
            ELSE ', '::text || ccounty.cs_abbreviation::text
        END) || 
        CASE
            WHEN cadr.postal_code IS NULL THEN ''::text
            ELSE ', '::text || cadr.postal_code::text
        END AS contractor_address, 
    r.name_on_picture_id AS contractor_name, c.phone AS contractor_telephone, 
    irk.rate_type AS cost_uom, 
    wp.dm_tentative_demob_city AS demobilization_city, 
        CASE
            WHEN NOT wp.ci_first_work_date IS NULL AND NOT wp.ci_length_at_assignment IS NULL THEN wp.ci_first_work_date + '1 day'::interval * wp.ci_length_at_assignment::double precision
            ELSE NULL::timestamp without time zone
        END AS demobilization_date, 
    demobstate.cs_abbreviation AS demobilization_state, 
    cpi.desc1 AS description_1, cpi.desc2 AS description_2, 
        CASE
            WHEN wp.dm_is_release_dispatch_notif THEN 'Y'::text
            ELSE 'N'::text
        END AS is_disp_notified_of_act_rel, 
        CASE
            WHEN wp.dm_is_planning_dispatch_notif THEN 'Y'::text
            ELSE 'N'::text
        END AS is_disp_notified_of_tent_rel, 
    c.duns AS duns_number, at.employment_type, 
    wp.dm_estimated_arrival_date AS estimated_date_of_arrival, 
    wp.dm_estimated_arrival_date AS estimated_time_of_arrival, 
    ca.end_date AS expiration_date, at.fax AS fax_number, 
    wp.ci_first_work_date AS first_day_of_work, r.first_name, air.flight_time, 
    cr.guarantee_amount AS guarantee_rate, cpi.hired_date AS hire_date, 
    cpi.hired_date AS hire_time, dmjp.code AS incident_jetport, 
    at.of_remarks AS invoice_remarks, 
        CASE
            WHEN r.parent_resource_id IS NULL THEN 'N'::text
            ELSE 'Y'::text
        END AS is_rostered, 
    k.code AS item_code, k.description AS item_code_description, 
        CASE
            WHEN air.is_itinerary_received THEN 'Y'::text
            ELSE 'N'::text
        END AS is_itinerary_received, 
    ijp.code AS jetport, r.last_name, 
        CASE
            WHEN r.is_leader THEN 'Y'::text
            ELSE 'N'::text
        END AS is_leader, 
    ( SELECT isw_resource.last_name::text || 
                CASE
                    WHEN isw_resource.first_name IS NULL THEN ''::text
                    ELSE ', '::text || isw_resource.first_name::text
                END AS name
           FROM isw_resource
          WHERE isw_resource.id = (( SELECT min(r4.id) AS min
                   FROM isw_resource r4
                  WHERE r4.parent_resource_id = r.id AND r4.leader_type = 1))) AS leader_name, 
    wp.ci_length_at_assignment AS length_of_assignment, 
    get_resource_level(r.id) AS resource_level, 
    wp.ci_travel_method AS method_of_travel, a.start_date AS mobilization_date, 
    r.name_on_picture_id AS name_on_id, r.number_of_personnel, 
    r.other_1 AS other1, r.other_2 AS other2, r.other_3 AS other3, 
        CASE
            WHEN r.is_person THEN 'Y'::text
            ELSE 'N'::text
        END AS is_overhead, 
        CASE
            WHEN k.is_line_overhead THEN 'Y'::text
            ELSE 'N'::text
        END AS is_line_overhead, 
    r.parent_resource_id AS parent_id, pag.agency_code AS payment_agency, 
    (((
        CASE
            WHEN atadr.address_line_1 IS NULL THEN ''::character varying
            ELSE atadr.address_line_1
        END::text || 
        CASE
            WHEN atadr.address_line_2 IS NULL THEN ''::text
            ELSE ', '::text || atadr.address_line_2::text
        END) || 
        CASE
            WHEN atadr.city IS NULL THEN ''::text
            ELSE ', '::text || atadr.city::text
        END) || 
        CASE
            WHEN atcounty.cs_abbreviation IS NULL THEN ''::text
            ELSE ', '::text || atcounty.cs_abbreviation::text
        END) || 
        CASE
            WHEN atadr.postal_code IS NULL THEN ''::text
            ELSE ', '::text || atadr.postal_code::text
        END AS person_address_info, 
    r.phone AS phone_number, ca.point_of_hire, cr.rate_amount AS rate, 
    cr.rate_type, 
        CASE
            WHEN wp.dm_is_reassignable THEN 'Y'::text
            ELSE 'N'::text
        END AS is_reassignment, 
    cd.cost_remarks AS remarks_cost, wp.dm_planning_remarks AS remarks_plans, 
    reqcat.code AS request_category, a.request_number, r.id AS resource_id, 
        CASE
            WHEN r.is_person THEN (r.last_name::text || 
            CASE
                WHEN r.first_name IS NULL THEN ''::text
                ELSE ', '::text || r.first_name::text
            END)::character varying
            ELSE r.resource_name
        END AS resource_name, 
    wp.dm_travel_method AS return_travel_method, dpt.code AS section_code, 
    dpt.description AS section_description, air.remarks AS special_instructions, 
    cr.rate_amount AS special_rate, a.assignment_status AS status, 
    r.phone AS telephone, 
    wp.dm_tentative_release_date AS tentative_release_date, 
    wp.dm_planning_remarks AS tentative_release_remarks, 
    wp.dm_tentative_release_date AS tentative_release_time, 
    cr.unit_of_measure AS time_uom, 
        CASE
            WHEN a.is_training THEN 'Y'::text
            ELSE 'N'::text
        END AS is_trainee, 
    air.hours_to_airport AS travel_hours, 
    air.minutes_to_airport AS travel_minutes, cpi.vin_name AS unique_name_vin, 
    o.unit_code AS unit_id
   FROM isw_incident i
   LEFT JOIN isw_organization unit ON i.unit_id = unit.id
   LEFT JOIN isw_incident_resource ir ON i.id = ir.incident_id
   LEFT JOIN isw_resource r ON ir.resource_id = r.id
   LEFT JOIN iswl_agency ag ON r.agency_id = ag.id
   LEFT JOIN isw_resource_contractor rc ON r.id = rc.resource_id
   LEFT JOIN isw_contractor c ON rc.contractor_id = c.id
   LEFT JOIN isw_contractor_agreement ca ON c.id = ca.contractor_id
   LEFT JOIN isw_contr_payment_info cpi ON ca.id = cpi.contractor_agreement_id
   LEFT JOIN isw_contr_payinfo_rate cpr ON cpi.id = cpr.contractor_pay_info_id
   LEFT JOIN isw_contractor_rate cr ON cpr.contractor_rate_id = cr.id
   LEFT JOIN isw_organization o ON r.organization_id = o.id
   LEFT JOIN isw_work_period wp ON ir.id = wp.incident_resource_id
   LEFT JOIN isw_work_period_assignment wpa ON wp.id = wpa.work_period_id
   LEFT JOIN isw_assignment a ON wpa.assignment_id = a.id
   LEFT JOIN isw_assignment_time at ON a.id = at.assignment_id
   LEFT JOIN iswl_jet_port ijp ON wp.ci_arrival_jet_port_id = ijp.id
   LEFT JOIN isw_address atadr ON at.mailing_address_id = atadr.id
   LEFT JOIN iswl_country_subdivision atcounty ON atadr.country_subdivision_id = atcounty.id
   LEFT JOIN isw_address cadr ON c.address_id = cadr.id
   LEFT JOIN iswl_country_subdivision ccounty ON cadr.country_subdivision_id = ccounty.id
   LEFT JOIN isw_cost_data cd ON ir.cost_data_id = cd.id
   LEFT JOIN iswl_agency pag ON cd.payment_agency_id = pag.id
   LEFT JOIN iswl_kind k ON a.kind_id = k.id
   LEFT JOIN isw_air_travel air ON wp.dm_air_travel_id = air.id
   LEFT JOIN iswl_jet_port dmjp ON air.jet_port_id = dmjp.id
   LEFT JOIN iswl_request_category reqcat ON k.request_category_id = reqcat.id
   LEFT JOIN isw_inccost_rate_kind irk ON k.id = irk.kind_id
   LEFT JOIN iswl_department dpt ON k.department_id = dpt.id
   LEFT JOIN iswl_accrual_code acc ON cd.accrual_code_id = acc.id
   LEFT JOIN isw_ad_payment_info adpi ON at.id = adpi.assignment_time_id
   LEFT JOIN iswl_rate_class_rate rcls ON adpi.rate_class_rate_id = rcls.id
   LEFT JOIN isw_admin_office ao ON ao.address_id = cadr.id
   LEFT JOIN iswl_country_subdivision demobstate ON wp.dm_tentative_demob_state_id = demobstate.id
/

update iswl_kind set is_line_overhead=true where code='FAL1'
/
update iswl_kind set is_line_overhead=true where code='FAL2'
/
update iswl_kind set is_line_overhead=true where code='FAL3'
/
update iswl_kind set is_line_overhead=false where code='FEMO'
/
update iswl_kind set is_line_overhead=true where code='AEMT'
/
update iswl_kind set is_line_overhead=true where code='CRT1'
/
update iswl_kind set is_line_overhead=true where code='ENGI'
/
update iswl_kind set is_line_overhead=false where code='RXFA'
/
update iswl_kind set is_line_overhead=false where code='SASP'
/
update iswl_kind set is_line_overhead=true where code='UAMC'
/
update iswl_kind set is_line_overhead=true where code='UASO'
/
update iswl_kind set is_line_overhead=true where code='UASP'
/
update iswl_kind set is_line_overhead=true where code='WOBS'
/
update iswl_kind set is_line_overhead=true where code='XEDO'
/
update iswl_kind set is_direct=true where code='FAL1'
/
update iswl_kind set is_direct=true where code='FAL2'
/
update iswl_kind set is_direct=true where code='FAL3'
/
update iswl_kind set is_direct=false where code='FBAN'
/
update iswl_kind set is_direct=false where code='FBTS'
/
update iswl_kind set is_direct=false where code='HAZ1'
/
update iswl_kind set is_direct=false where code='HAZM'
/
update iswl_kind set is_direct=true where code='HCS3'
/
update iswl_kind set is_direct=false where code='HEIN'
/
update iswl_kind set is_direct=true where code='AIME'
/
update iswl_kind set is_direct=false where code='AIRB'
/
update iswl_kind set is_direct=true where code='ATBM'
/
update iswl_kind set is_direct=true where code='ATCO'
/
update iswl_kind set is_direct=false where code='AVIN'
/
update iswl_kind set is_direct=true where code='CRT1'
/
update iswl_kind set is_direct=false where code='DPSP'
/
update iswl_kind set is_direct=false where code='PWOP'
/
update iswl_kind set is_direct=true where code='RAWP'
/
update iswl_kind set is_direct=false where code='RXFA'
/
update iswl_kind set is_direct=false where code='SASP'
/
update iswl_kind set is_direct=false where code='SASR'
/
update iswl_kind set is_direct=true where code='SKDS'
/
update iswl_kind set is_direct=false where code='SOPL'
/
update iswl_kind set is_direct=false where code='THSC'
/
update iswl_kind set is_direct=true where code='UAMC'
/
update iswl_kind set is_direct=true where code='UASO'
/
update iswl_kind set is_direct=true where code='UASP'
/
update iswl_kind set is_direct=true where code='VLAT'
/
update iswl_kind set is_direct=false where code='IRFS'
/
update iswl_kind set is_direct=true where code='MAST'
/
update iswl_kind set is_direct=false where code='OCSP'
/
update iswl_kind set is_direct=true where code='OPS3'
/
update iswl_kind set is_direct=true where code='PID'
/
update iswl_kind set is_direct=true where code='PLDO'
/
update iswl_kind set is_indirect=true where code='FBAN'
/
update iswl_kind set is_indirect=true where code='FBTS'
/
update iswl_kind set is_indirect=true where code='FWPT'
/
update iswl_kind set is_indirect=true where code='HAZ1'
/
update iswl_kind set is_indirect=true where code='HAZM'
/
update iswl_kind set is_indirect=true where code='HEIN'
/
update iswl_kind set is_indirect=true where code='HYDT'
/
update iswl_kind set is_indirect=true where code='AEMT'
/
update iswl_kind set is_indirect=true where code='AIRB'
/
update iswl_kind set is_indirect=false where code='ATBM'
/
update iswl_kind set is_indirect=false where code='ATCO'
/
update iswl_kind set is_indirect=true where code='AVIN'
/
update iswl_kind set is_indirect=true where code='DECK'
/
update iswl_kind set is_indirect=true where code='DPSP'
/
update iswl_kind set is_indirect=true where code='PWOP'
/
update iswl_kind set is_indirect=true where code='REAC'
/
update iswl_kind set is_indirect=true where code='RXFA'
/
update iswl_kind set is_indirect=true where code='SASP'
/
update iswl_kind set is_indirect=true where code='SASR'
/
update iswl_kind set is_indirect=true where code='SOPL'
/
update iswl_kind set is_indirect=true where code='THSC'
/
update iswl_kind set is_indirect=true where code='INBA'
/
update iswl_kind set is_indirect=true where code='IRFS'
/
update iswl_kind set is_indirect=true where code='LELO'
/
update iswl_kind set is_indirect=true where code='LIOF'
/
update iswl_kind set is_indirect=true where code='OCSP'
/
update iswl_kind set is_indirect=false where code='PLDO'
/
update iswl_kind set is_single=true where code='FAL1'
/
update iswl_kind set is_single=true where code='FAL2'
/
update iswl_kind set is_single=true where code='FAL3'
/
update iswl_kind set is_single=true where code='HYDT'
/
update iswl_kind set is_single=true where code='AEMT'
/
update iswl_kind set is_single=true where code='CRT1'
/
update iswl_kind set is_single=true where code='REAC'
/
update iswl_kind set is_single=true where code='UAMC'
/
update iswl_kind set is_single=true where code='UASO'
/
update iswl_kind set is_single=true where code='UASP'
/
update iswl_kind set is_single=true where code='INBA'
/
update iswl_kind set is_single=true where code='LELO'
/
update iswl_kind set is_single=true where code='LIOF'
/
update iswl_kind set is_single=true where code='MLCO'
/
update iswl_kind set is_single=true where code='OPS3'
/
update iswl_kind set is_subordinate=true where code='AIME'
/
update iswl_kind set is_subordinate=false where code='MLCO'
/
update iswl_kind set standard_cost=700 where code='FAL1'
/
update iswl_kind set standard_cost=700 where code='FAL2'
/
update iswl_kind set standard_cost=700 where code='FAL3'
/
update iswl_kind set standard_cost=700 where code='HYDT'
/
update iswl_kind set standard_cost=700 where code='AEMT'
/
update iswl_kind set standard_cost=504 where code='AIME'
/
update iswl_kind set standard_cost=700 where code='CACB'
/
update iswl_kind set standard_cost=700 where code='CAMP'
/
update iswl_kind set standard_cost=700 where code='CRT1'
/
update iswl_kind set standard_cost=504 where code='CRWB'
/
update iswl_kind set standard_cost=504 where code='ENGB'
/
update iswl_kind set standard_cost=504 where code='ENGI'
/
update iswl_kind set standard_cost=504 where code='ENOP'
/
update iswl_kind set standard_cost=700 where code='REAC'
/
update iswl_kind set standard_cost=700 where code='UAMC'
/
update iswl_kind set standard_cost=700 where code='UASO'
/
update iswl_kind set standard_cost=700 where code='UASP'
/
update iswl_kind set standard_cost=0 where code='WTRK'
/
update iswl_kind set standard_cost=700 where code='INBA'
/
update iswl_kind set standard_cost=700 where code='LELO'
/
update iswl_kind set standard_cost=700 where code='LIOF'
/
update iswl_kind set standard_cost=1000 where code='MAST'
/
update iswl_kind set standard_cost=700 where code='MLCO'
/
update iswl_kind set standard_cost=700 where code='OPS3'
/
update iswl_kind set is_aircraft='Y' where code='VLAT'
/
update iswl_kind set is_strike_team=true where code='HCS3'
/
update iswl_kind set is_strike_team=true where code='HCS4'
/
update iswl_kind set description='FUEL TENDER, TYPE 1' where code='FT1'
/
update iswl_kind set description='FUEL TENDER, TYPE 2' where code='FT2'
/
update iswl_kind set description='FUEL TENDER, TYPE 3' where code='FT3'
/
update iswl_kind set description='GRAY WATER TRUCK, T1' where code='GWT1'
/
update iswl_kind set description='GRAY WATER TRUCK, T2' where code='GWT2'
/
update iswl_kind set description='GRAY WATER TRUCK, T3' where code='GWT3'
/
update iswl_kind set description='GRAY WATER TRUCK, T4' where code='GWT4'
/
update iswl_kind set description='CREW, STRIKE TEAM, MISC' where code='HCS4'
/
update iswl_kind set description='BATTALION MILITARY LIAISON' where code='BNML'
/
update iswl_kind set description='ENGINEER, STRUCTURAL' where code='ENGS'
/
update iswl_kind set description='POTABLE WATER TRUCK, TYPE 1' where code='POT1'
/
update iswl_kind set description='POTABLE WATER TRUCK, TYPE 2' where code='POT2'
/
update iswl_kind set description='POTABLE WATER TRUCK, TYPE 3' where code='POT3'
/
update iswl_kind set description='POTABLE WATER TRUCK, TYPE 4' where code='POT4'
/
update iswl_kind set description='TRUCK, SERVICE/MECHANIC' where code='MEC'
/
update iswl_kind set department_id = (select id from iswl_department where code='L') where code='AIRB'
/
update iswl_kind set department_id = (select id from iswl_department where code='E') where code='AMLO'
/
update iswl_kind set department_id = (select id from iswl_department where code='L') where code='PWOP'
/
update iswl_kind set department_id = (select id from iswl_department where code='E') where code='SACI'
/
update iswl_kind set department_id = (select id from iswl_department where code='E') where code='SAIL'
/
update iswl_kind set department_id = (select id from iswl_department where code='E') where code='SATM'
/
update iswl_kind set department_id = (select id from iswl_department where code='C') where code='INTM'
/
update iswl_kind set cat1b_id = (select id from iswl_cat_1_b where code='S') where code='BT25'
/
update iswl_kind set cat1b_id = (select id from iswl_cat_1_b where code='S') where code='BTOP'
/
update iswl_kind set sit_209_id = ( select id from iswl_sit_209 where code='C2IA') where code='HC2I'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='FBAN'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='FBTS'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='FWBM'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='FWCO'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='HAZ1'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='HAZM'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='HEIN'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='AALD'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='ABRO'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='AFUL'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='ART1'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='ART2'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='ARTL'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='ATFL'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='AVIN'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='BT25'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='BTOP'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='DART'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='DPSP'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='PWOP'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='RXFA'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='SALE'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='SASP'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='SATL'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='SATS'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='SEMG'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='SOPL'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='STSF'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='THSC'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='WTOP'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='IRFS'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='MAFF'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='O') where code='OCSP'
/
update iswl_kind set kind_group_id = ( select id from iswl_kind_group where code='L') where code='PLDO'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='FAL1'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='FAL2'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='FAL3'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='C2IA') where code='HCS3'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='HYDT'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='AEMT'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='AIME'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='CRT1'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='D1') where code='DOZ2'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='D1') where code='DZS2'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='REAC'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='S1') where code='SKDS'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='S1') where code='SKG1'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='S1') where code='SKG2'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='S1') where code='SKG3'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='S1') where code='SKG4'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='UAMC'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='UASO'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='UASP'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='INBA'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='LELO'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='LIOF'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='M1') where code='MAST'
/
update iswl_kind set sit_209_id = (select id from iswl_sit_209 where code='O') where code='OPS3'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='FAL1'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='FAL2'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='FAL3'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='HCS3'
/
update iswl_kind set department_id = (select id from iswl_department where code='P') where code='HYDT'
/
update iswl_kind set department_id = (select id from iswl_department where code='L') where code='AEMT'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='AIME'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='CRT1'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='RAWP'
/
update iswl_kind set department_id = (select id from iswl_department where code='P') where code='REAC'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='SKDS'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='UAMC'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='UASO'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='UASP'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='VLAT'
/
update iswl_kind set department_id = (select id from iswl_department where code='E') where code='INBA'
/
update iswl_kind set department_id = (select id from iswl_department where code='L') where code='LELO'
/
update iswl_kind set department_id = (select id from iswl_department where code='E') where code='LIOF'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='MAST'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='OPS3'
/
update iswl_kind set department_id = (select id from iswl_department where code='O') where code='PID'
/
update iswl_kind set department_id = null where code='LODG'
/
update iswl_kind set department_id = null where code='PERD'
/
update iswl_kind set department_id = null where code='TRAN'
/
update iswl_kind set department_id = null where code='SPT'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='FAL1'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='FAL2'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='FAL3'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='C') where code='HCS3'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='HYDT'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='AEMT'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='AIME'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='CRT1'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='E') where code='RAWP'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='REAC'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='E') where code='SKDS'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='S') where code='SPT'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='S') where code='SUP'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='S') where code='TRAN'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='UAMC'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='UASO'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='UASP'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='INBA'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='LELO'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='LIOF'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='E') where code='MAST'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='OD'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='OPS3'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='O') where code='OS'
/
update iswl_kind set request_category_id = (select id from iswl_request_category where code='E') where code='PID'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='L') where code='FAL1'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='L') where code='FAL2'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='L') where code='FAL3'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='C') where code='HCS3'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='O') where code='HYDT'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='O') where code='AEMT'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='L') where code='AIME'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='L') where code='CRT1'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='E') where code='RAWP'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='O') where code='REAC'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='E') where code='SKDS'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='L') where code='UAMC'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='L') where code='UASO'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='L') where code='UASP'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='A') where code='VLAT'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='O') where code='INBA'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='O') where code='LELO'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='O') where code='LIOF'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='E') where code='MAST'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='L') where code='OPS3'
/
update iswl_kind set kind_group_id = (select id from iswl_kind_group where code='E') where code='PID'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='FAL1'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='FAL2'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='FAL3'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='HCS3'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='HYDT'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='AEMT'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='AIME'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='CRT1'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='RAWP'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='REAC'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='SKDS'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='UAMC'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='UASO'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='UASP'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='A') where code='VLAT'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='INBA'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='LELO'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='LIOF'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='MAST'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='OPS3'
/
update iswl_kind set daily_form_id = (select id from iswl_daily_form where code='O') where code='PID'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='FAL1'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='FAL2'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='FAL3'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='C') where code='HCS3'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='HYDT'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='AEMT'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='AIME'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='CRT1'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='E') where code='RAWP'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='REAC'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='E') where code='SKDS'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='UAMC'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='UASO'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='UASP'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='A') where code='VLAT'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='INBA'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='LELO'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='LIOF'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='E') where code='MAST'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='OPS3'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='E') where code='PID'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='FBAN'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='FBTS'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='FWBM'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='FWCO'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='HAZ1'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='HAZM'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='HEIN'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='AALD'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='ABRO'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='AFUL'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='ART1'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='ART2'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='ARTL'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='ATFL'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='AVIN'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='BT25'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='BTOP'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='DART'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='DPSP'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='PWOP'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='RXFA'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='SALE'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='SASP'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='SATL'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='SATS'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='SEMG'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='SOPL'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='STSF'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='THSC'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='WTOP'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='IRFS'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='MAFF'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='O') where code='OCSP'
/
update iswl_kind set group_category_id = (select id from iswl_group_category where code='L') where code='PLDO'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='FBAN'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='FBTS'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='FLSI'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='FWBM'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='FWCO'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='HAZ1'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='HAZM'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='C4') where code='HC2I'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='HCCS'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='HCLR'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='HCLS'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='C3') where code='HCS4'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='HCWN'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='HEAC'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='HEAM'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='HEIN'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='HMGB'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='AALD'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='ABRO'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='AFUL'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='ART1'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='ART2'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='ARTL'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='ATFL'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='ATPC'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='ATSC'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='AVIN'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='DART'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='DFF1'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='DIVA'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='DIVR'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='DOZL'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='DPSP'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='DSAR'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='PWOP'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='REGS'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='RTCM'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='RXFA'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SALE'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='SASP'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SATL'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SATS'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SEMG'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SFDR'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SFF1'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SFOL'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='SOPL'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SPLO'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SRT3'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SRT4'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SRTL'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='STEQ'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='STOP'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='STSA'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='STSC'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='STSF'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SWF1'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SWF2'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SWFL'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SWR1'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SWR2'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SWR3'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SWR4'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SWRL'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='THSC'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='TMRT'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='TRT1'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='TRT2'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='TRTL'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='USR1'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='USR2'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='USR3'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='USR4'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='EQ') where code='VBOX'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='WTOP'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='IRFS'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='MAFF'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='MLCO'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='OCSP'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='OSA2'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='OSC3'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='PLDO'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='FAL1'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='FAL2'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='FAL3'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='C4') where code='HCS3'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='HYDT'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='AEMT'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='AIME'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='CRT1'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='EQ') where code='RAWP'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='REAC'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='EQ') where code='SKDS'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='SPOT'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='UAMC'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='UASO'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='UASP'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='AT') where code='VLAT'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='INBA'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='LELO'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OS') where code='LIOF'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='EQ') where code='MAST'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='OD') where code='OPS3'
/
update iswl_kind set sub_group_category_id = (select id from iswl_sub_group_category where code='EQ') where code='PID'
/
update iswl_kind set cat1a_id = (select id from iswl_cat_1_a where code='O')
where code in (
    'AEMT','AIME', 'CRT1', 'FAL1', 'FAL2', 'FAL3', 'HYDT', 'INBA', 'LELO', 'LIOF'
    , 'OPS3', 'PID', 'RAWP', 'REAC', 'SPOT', 'UAMC', 'UASO', 'UASP')
/
update iswl_kind set cat1a_id = (select id from iswl_cat_1_a where code='E')
where code in ('HCS3', 'MAST', 'SKDS')
/
update iswl_kind set cat1a_id = (select id from iswl_cat_1_a where code='A')
where code in ('VLAT')
/
update iswl_kind set cat1b_id = (select id from iswl_cat_1_b where code='S')
where code in (
    'AEMT', 'AIME', 'CRT1', 'FAL1', 'FAL2', 'FAL3', 'HYDT', 'INBA', 'LELO', 'LIOF'
    ,'OPS3', 'REAC', 'SPOT', 'UAMC', 'UASO', 'UASP')
/
update iswl_kind set cat1b_id = (select id from iswl_cat_1_b where code='AF')
where code in ('VLAT')
/
update iswl_kind set cat1b_id = (select id from iswl_cat_1_b where code='C')
where code in ('HCS4')
/
update iswl_kind set cat1b_id = (select id from iswl_cat_1_b where code='C')
where code in ('HCS3')
/
update iswl_kind set cat1b_id = (select id from iswl_cat_1_b where code='Q')
where code in ('MAST', 'PID', 'RAWP', 'SKDS')
/
update iswl_kind set sit_209_id = null where code='HCS4'
/
update iswl_kind set cat1b_id = (select id from iswl_cat_1_b where code='S') where code in ('PID','RAWP')
/
INSERT INTO SCHEMACHANGELOG( ID, MAJORRELEASENUMBER, MINORRELEASENUMBER, POINTRELEASENUMBER, SCRIPTNAME, DATEAPPLIED)
VALUES (230,'01','00','00.00','patch.230.sc-pg.01_00_00.00.sql', now())
/
UPDATE REVISION SET REVISIONLEVEL = 230
/
