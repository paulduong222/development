alter table isw_incident add IRWIN_ISVALID VARCHAR(5);
update isw_incident set IRWIN_ISVALID = IRWIN_ISACTIVE;
insert into isw_system_parameter (ID, PARAMETER_NAME, PARAMETER_VALUE) 
  VALUES (nextval('SEQ_SYSTEM_PARAMETER'),'IRWIN_ISVALID','0');
CREATE OR REPLACE VIEW ISWV_CR_RESOURCE_COST AS 
  SELECT DISTINCT
            i.id AS incident_id,
             i.incident_name AS incident_name,
             unit.unit_code || '-' || i.nbr AS incident_number,
             ac.account_code AS accounting_code,
             acc.code AS accrual_code,
             acc.description AS accrual_description,
             irdc.activity_date AS activity_date,
             agcy.agency_code AS agency_code,
             agcy.agency_name AS agency_description,
             irdc.aircraft_cost_amount AS aircraft_cost,
             cg.cost_group_name AS cost_group,
             cg.description AS cost_group_description,
             irdc.rate_type AS cost_UOM,
             CAST (
                CASE WHEN k.is_direct THEN 'Y' ELSE 'N' END AS text)
                AS is_direct,
             irdc.flight_hours AS flight_hrs,
             irdc.total_cost_amount AS grand_total_cost,
             gc.code AS group_code,
             k.code AS item_code,
             k.description AS item_description,
             irdc.cargo_pounds AS lbs_cargo,
             irdc.number_of_trips AS num_trips,
             r.parent_resource_id AS parent_id,
             irdc.number_of_passengers AS passenger,
             pagcy.agency_code AS payment_agency_code,
             pagcy.agency_name AS payment_agency_description,
             irdc.primary_total_amount AS primary_cost,
             a.request_number AS request_number,
             CAST (
                    CASE
                        WHEN r.is_person THEN
                            r.last_name
                            || CASE
                                    WHEN r.first_name IS NULL THEN ''
                                    ELSE ', ' || r.first_name
                                END
                        ELSE
                            r.resource_name
                    END AS character varying)
                    AS Resource_Name,
             irdc.retardant_gallons AS retardant_gal,
             dpt.code AS section_code,
             dpt.description AS section_description,
             shft.shift_name AS shift,
             sgc.code AS sub_group_code,
             irdc.subordinate_total_amount AS subordinate_cost,
             irdc.unit_cost_amount AS unit_cost,
             o.organization_name AS unit_description,
             o.unit_code AS unit_id,
             irdc.units AS units,
             irdc.water_gallons AS water_gal
      FROM isw_incident i
             LEFT JOIN isw_organization unit
                 ON i.unit_id = unit.id
             LEFT OUTER JOIN isw_incident_resource ir
                 ON i.id = ir.incident_id
             LEFT OUTER JOIN isw_inc_res_daily_cost irdc
                 ON ir.id = irdc.incident_resource_id
             LEFT OUTER JOIN isw_cost_group cg
                 ON irdc.cost_group_id = cg.id
             LEFT OUTER JOIN isw_incident_account_code iac
                ON irdc.incident_account_code_id = iac.id
             LEFT OUTER JOIN isw_account_code ac
                 ON iac.account_code_id = ac.id
             LEFT OUTER JOIN iswl_accrual_code acc
                 ON irdc.accrual_code_id = acc.id
             LEFT OUTER JOIN isw_resource r
                 ON ir.resource_id = r.id
             LEFT OUTER JOIN iswl_agency agcy
                 ON r.agency_id = agcy.id
             LEFT OUTER JOIN isw_work_period wp
                 ON ir.id = wp.incident_resource_id
             LEFT OUTER JOIN isw_work_period_assignment wpa
                 ON wp.id = wpa.work_period_id
             LEFT OUTER JOIN isw_assignment a
                 ON wpa.assignment_id = a.id    
             LEFT OUTER JOIN iswl_kind k
                 ON a.kind_id = k.id
             LEFT OUTER JOIN iswl_group_category gc
                 ON k.group_category_id = gc.id
             LEFT OUTER JOIN isw_cost_data cd
                 ON ir.cost_data_id = cd.id
             LEFT OUTER JOIN iswl_agency pagcy
                 ON cd.payment_agency_id = pagcy.id
             LEFT OUTER JOIN iswl_department dpt
                 ON k.department_id = dpt.id
             LEFT OUTER JOIN isw_incident_shift shft
                 ON irdc.incident_shift_id = shft.id
             LEFT OUTER JOIN iswl_sub_group_category sgc
                 ON k.sub_group_category_id = sgc.id
             LEFT OUTER JOIN isw_organization o
                 ON r.organization_id = o.id;
INSERT INTO SCHEMACHANGELOG( ID, MAJORRELEASENUMBER, MINORRELEASENUMBER, POINTRELEASENUMBER, SCRIPTNAME, DATEAPPLIED)
VALUES (236,'01','01','03.00','patch.236.sc-pg.01_01_03.00.sql', now());
UPDATE REVISION SET REVISIONLEVEL = 236;

