package gov.nwcg.isuite.core.persistence.hibernate.query;

public class TimePostStopDateFix {
	public static String queryListOra[] = {
		"update isw_assign_time_post " +
		"set post_stop_date = (post_start_date + ( trunc(quantity)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and trunc(quantity)=quantity  " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + ( trunc(quantity)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and (quantity-trunc(quantity)) = 0.50 " +
		"and to_char(post_start_date,'MI') = '00' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_start_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':30','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.50 " +
		"and to_char(post_start_date,'MI') = '00' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + ( (trunc(quantity)+1)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.50 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':00','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.50 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + ( trunc(quantity)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.25 " +
		"and to_char(post_start_date,'MI') = '00' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':15','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.25 " +
		"and to_char(post_start_date,'MI') = '00' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + ( trunc(quantity)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.25 " +
		"and to_char(post_start_date,'MI') = '15' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':30','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.25 " +
		"and to_char(post_start_date,'MI') = '15' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + ( trunc(quantity)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.25 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':45','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.25 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + ( (trunc(quantity)+1)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.25 " +
		"and to_char(post_start_date,'MI') = '45' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':00','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.25 " +
		"and to_char(post_start_date,'MI') = '45' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + ( trunc(quantity)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.50 " +
		"and to_char(post_start_date,'MI') = '15' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':45','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.50 " +
		"and to_char(post_start_date,'MI') = '15' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + ( (trunc(quantity)+1)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.50 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':00','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.50 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + ( (trunc(quantity)+1)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.50 " +
		"and to_char(post_start_date,'MI') = '45' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':15','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.50 " +
		"and to_char(post_start_date,'MI') = '45' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + ( trunc(quantity)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.75 " +
		"and to_char(post_start_date,'MI') = '00' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':45','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.75 " +
		"and to_char(post_start_date,'MI') = '00' " +
		"and employment_type != 'CONTRACTOR' "
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + ( (trunc(quantity)+1)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.75 " +
		"and to_char(post_start_date,'MI') = '15' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':00','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.75 " +
		"and to_char(post_start_date,'MI') = '15' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + ( (trunc(quantity)+1)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.75 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':15','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.75 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + ( (trunc(quantity)+1)/24)) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.75 " +
		"and to_char(post_start_date,'MI') = '45' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':30','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and post_stop_date is not null " +
		"and quantity-trunc(quantity) = 0.75 " +
		"and to_char(post_start_date,'MI') = '45' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' 23:59','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') = '59' " +
		"and employment_type != 'CONTRACTOR' " 
	};
	
	public static String queryListPg[] = {
		"update isw_assign_time_post " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.00 " +
		"and employment_type != 'CONTRACTOR' "  
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.50 " +
		"and to_char(post_start_date,'MI') = '00' " +
		"and employment_type != 'CONTRACTOR' "
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_start_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':30','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.50 " +
		"and to_char(post_start_date,'MI') = '00' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)+1) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.50 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':00','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.50 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.25 " +
		"and to_char(post_start_date,'MI') = '00' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':15','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.25 " +
		"and to_char(post_start_date,'MI') = '00' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.25 " +
		"and to_char(post_start_date,'MI') = '15' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':30','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.25 " +
		"and to_char(post_start_date,'MI') = '15' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.25 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' "
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':45','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.25 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' "
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)+1) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.25 " +
		"and to_char(post_start_date,'MI') = '45' " +
		"and employment_type != 'CONTRACTOR' "
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':00','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.25 " +
		"and to_char(post_start_date,'MI') = '45' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.50 " +
		"and to_char(post_start_date,'MI') = '15' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post " + 
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':45','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.50 " +
		"and to_char(post_start_date,'MI') = '15' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)+1) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.50 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':00','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.50 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)+1) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.50 " +
		"and to_char(post_start_date,'MI') = '45' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':15','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.50 " +
		"and to_char(post_start_date,'MI') = '45' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.75 " +
		"and to_char(post_start_date,'MI') = '00' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post " + 
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':45','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.75 " + 
		"and to_char(post_start_date,'MI') = '00' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)+1) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.75 " +
		"and to_char(post_start_date,'MI') = '15' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':00','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.75 " +
		"and to_char(post_start_date,'MI') = '15' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)+1) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.75 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post " + 
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':15','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.75 " +
		"and to_char(post_start_date,'MI') = '30' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = (post_start_date + interval '1 hour' * (trunc(quantity)+1) ) " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " +
		"and quantity % 1 = 0.75 " +
		"and to_char(post_start_date,'MI') = '45' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' ' || to_char(post_stop_date,'HH24') || ':30','MM/DD/YYYY HH24:MI')  " +
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') != '59' " + 
		"and quantity % 1 = 0.75 " +
		"and to_char(post_start_date,'MI') = '45' " +
		"and employment_type != 'CONTRACTOR' " 
		,
		"update isw_assign_time_post  " +
		"set post_stop_date = to_timestamp( to_char(post_stop_date,'MM/DD/YYYY') || ' 23:59','MM/DD/YYYY HH24:MI') " + 
		"where (special_pay_id is null or special_pay_id = (select id from iswl_special_pay where code='HP')) " +
		"and to_char(post_stop_date,'MI') = '59' " +
		"and employment_type != 'CONTRACTOR' "
	};
	
}
