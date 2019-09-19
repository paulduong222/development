DROP VIEW iswv_cr_person_time
/

CREATE OR REPLACE VIEW iswv_cr_person_time AS 
 SELECT DISTINCT i.id AS incident_id, 
    (unit.unit_code::text || '-'::text) || i.nbr::text AS incident_number, 
    i.incident_name, acc.code AS accrual, 
    wp.dm_release_date AS actual_release_date, 
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
    c.name AS contractor_name, c.phone AS contractor_telephone, 
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
            WHEN r.leader_type = 1 OR r.leader_type = 2 THEN 'Y'::text
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
   LEFT JOIN isw_organization o ON r.organization_id = o.id
   LEFT JOIN isw_work_period wp ON ir.id = wp.incident_resource_id
   LEFT JOIN isw_work_period_assignment wpa ON wp.id = wpa.work_period_id
   LEFT JOIN isw_assignment a ON wpa.assignment_id = a.id
   LEFT JOIN isw_assignment_time at ON a.id = at.assignment_id
   LEFT JOIN isw_contr_payment_info cpi ON at.id = cpi.assignment_time_id
   LEFT JOIN isw_contractor_agreement ca ON cpi.contractor_agreement_id = ca.id
   LEFT JOIN isw_contractor c ON cpi.contractor_id = c.id
   LEFT JOIN isw_contr_payinfo_rate cpr ON cpi.id = cpr.contractor_pay_info_id
   LEFT JOIN isw_contractor_rate cr ON cpr.contractor_rate_id = cr.id
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
   LEFT JOIN isw_admin_office ao ON ao.id = ca.admin_office_id
   LEFT JOIN iswl_country_subdivision demobstate ON wp.dm_tentative_demob_state_id = demobstate.id
  WHERE r.is_person = true
/

drop view iswv_cr_resource_time
/

CREATE OR REPLACE VIEW iswv_cr_resource_time AS 
 SELECT DISTINCT i.id AS incident_id, 
    (unit.unit_code::text || '-'::text) || i.nbr::text AS incident_number, 
    i.incident_name, acc.code AS accrual, 
    wp.dm_release_date AS actual_release_date, 
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
    c.name AS contractor_name, c.phone AS contractor_telephone, 
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
            WHEN r.leader_type = 1 OR r.leader_type = 2 THEN 'Y'::text
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
   LEFT JOIN isw_organization o ON r.organization_id = o.id
   LEFT JOIN isw_work_period wp ON ir.id = wp.incident_resource_id
   LEFT JOIN isw_work_period_assignment wpa ON wp.id = wpa.work_period_id
   LEFT JOIN isw_assignment a ON wpa.assignment_id = a.id
   LEFT JOIN isw_assignment_time at ON a.id = at.assignment_id
   LEFT JOIN isw_contr_payment_info cpi ON at.id = cpi.assignment_time_id
   LEFT JOIN isw_contractor_agreement ca ON cpi.contractor_agreement_id = ca.id
   LEFT JOIN isw_contractor c ON cpi.contractor_id = c.id
   LEFT JOIN isw_contr_payinfo_rate cpr ON cpi.id = cpr.contractor_pay_info_id
   LEFT JOIN isw_contractor_rate cr ON cpr.contractor_rate_id = cr.id
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
   LEFT JOIN isw_admin_office ao ON ao.id = ca.admin_office_id
   LEFT JOIN iswl_country_subdivision demobstate ON wp.dm_tentative_demob_state_id = demobstate.id
/


INSERT INTO SCHEMACHANGELOG( ID, MAJORRELEASENUMBER, MINORRELEASENUMBER, POINTRELEASENUMBER, SCRIPTNAME, DATEAPPLIED)
VALUES (233,'01','01','00.02','patch.233.sc-pg.01_01_00.02.sql', now())
/
UPDATE REVISION SET REVISIONLEVEL = 233
/
