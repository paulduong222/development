package gov.nwcg.isuite.core.persistence.hibernate.query;

public class ResourceInventoryQuery2 {

	public static String getResInvImportInsertQuery(String type) {
		String sql=""+
		"insert into isw_resinv_import ( "+
		"	    id, filename,dispatch_center_code,gacc_unit_code "+
		"	    ,error_description,status,processed_date "+
		"	    ,new_import_count,updated_import_count,deleted_count) "+
		"	select seq_resinv_import.nextVal "+
		"	    , '"+type+"', fd.res_disp_org_unit_code,'' "+
		"	    ,'','INWORK',sysdate "+
		"	    ,0,0,0 "+
		"	from ( "+
		"	    select distinct(res_disp_org_unit_code) as res_disp_org_unit_code "+
		"	    from isw_resinv_file_data "+
		"	    where pdc_id is not null "+
		"	    and pdc_id > 0 "+
		"	) fd "+
		"";
		
		return sql;
	}

	public static String getResInvImportInsertQuery2(String type) {
		String sql=""+
		"insert into isw_resinv_import ( "+
		"	    id, filename,dispatch_center_code,gacc_unit_code "+
		"	    ,error_description,status,processed_date "+
		"	    ,new_import_count,updated_import_count,deleted_count) "+
		"values (seq_resinv_import.nextVal "+
		"	    , '"+type+"', 'ALL','ALL' "+
		"	    ,'','INWORK',sysdate "+
		"	    ,0,0,0) "+
		"";
		
		return sql;
	}

	
	public static String getResInvImportInsertCountQuery() {
		String sql=""+
		"update isw_resinv_import ri "+
		"set (ri.new_import_count)=( "+
		"    select count(fd.ross_res_id) "+
		"    from isw_resinv_file_data fd "+
		"    where fd.res_disp_org_unit_code=ri.dispatch_center_code "+
		"    and fd.ross_res_id not in ( "+
		"        SELECT ross_res_id "+
		"        FROM isw_resource  "+
		"        where is_permanent = 1 "+
		"    ) "+
		"    and( "+
		"     (fd.pdc_id is not null and fd.pdc_id > 0) "+
		"      and length(fd.res_name) > 0 "+
		"      and length(fd.last_name) > 0 "+
		"      and length(fd.first_name) > 0 "+
		"     ) "+
		") "+
		"where ri.status='INWORK' "+
		"";
		
		return sql;
	}

	public static String getResInvImportInsertCountQuery2() {
		String sql=""+
		"update isw_resinv_import ri "+
		"set (ri.new_import_count)=( "+
		"    select count(fd.ross_res_id) "+
		"    from isw_resinv_file_data fd "+
		//"    where fd.res_disp_org_unit_code=ri.dispatch_center_code "+
		"    where fd.ross_res_id not in ( "+
		"        SELECT ross_res_id "+
		"        FROM isw_resource  "+
		"        where is_permanent = 1 "+
		"    ) "+
		"    and( "+
		"     (fd.pdc_id is not null and fd.pdc_id > 0) "+
		"      and length(fd.res_name) > 0 "+
		"      and length(fd.last_name) > 0 "+
		"      and length(fd.first_name) > 0 "+
		"     ) "+
		") "+
		"where ri.status='INWORK' "+
		"and ri.FILENAME='OH' " +
		"";
		
		return sql;
	}

	public static String getResInvImportInsertNonOHCountQuery2() {
		String sql=""+
		"update isw_resinv_import ri "+
		"set (ri.new_import_count)=( "+
		"    select count(fd.ross_res_id) "+
		"    from isw_resinv_file_data fd "+
		//"    where fd.res_disp_org_unit_code=ri.dispatch_center_code "+
		"    where fd.ross_res_id not in ( "+
		"        SELECT ross_res_id "+
		"        FROM isw_resource  "+
		"        where is_permanent = 1 "+
		"    ) "+
		"    and( "+
		"     (fd.pdc_id is not null and fd.pdc_id > 0) "+
		"      and length(fd.res_name) > 0 "+
		"      and (fd.kind_id is not null and fd.kind_id > 0) "+
		"     ) "+
		") "+
		"where ri.status='INWORK' "+
		"and ri.FILENAME='NONOH' " +
		"";
		
		return sql;
	}
	
	public static String getResInvImportUpdateCountQuery() {
		String sql=""+
		"update isw_resinv_import ri "+
		"set (ri.updated_import_count)=( "+
		"    select count(fd.ross_res_id) "+
		"    from isw_resinv_file_data fd "+
		"    where fd.res_disp_org_unit_code=ri.dispatch_center_code "+
		"    and fd.ross_res_id in ( "+
		"        SELECT ross_res_id "+
		"        FROM isw_resource  "+
		"        where is_permanent = 1 "+
		"    ) "+
		"    and( "+
		"     (fd.pdc_id is not null and fd.pdc_id > 0) "+
		"      and length(fd.res_name) > 0 "+
		"      and length(fd.last_name) > 0 "+
		"      and length(fd.first_name) > 0 "+
		"     ) "+
		") "+
		"where ri.status='INWORK' "+
		"and ri.FILENAME='OH' " +
		"";
		
		return sql;
	}
	
	public static String getResInvImportUpdateCountQuery2() {
		String sql=""+
		"update isw_resinv_import ri "+
		"set (ri.updated_import_count)=( "+
		"    select count(fd.ross_res_id) "+
		"    from isw_resinv_file_data fd "+
		//"    where fd.res_disp_org_unit_code=ri.dispatch_center_code "+
		"    where fd.ross_res_id in ( "+
		"        SELECT ross_res_id "+
		"        FROM isw_resource  "+
		"        where is_permanent = 1 "+
		"    ) "+
		"    and( "+
		"     (fd.pdc_id is not null and fd.pdc_id > 0) "+
		"      and length(fd.res_name) > 0 "+
		"      and length(fd.last_name) > 0 "+
		"      and length(fd.first_name) > 0 "+
		"     ) "+
		") "+
		"where ri.status='INWORK' "+
		"and ri.FILENAME='NONOH' " +
		"";
		
		return sql;
	}

	public static String getResInvImportUpdateNonOHCountQuery2() {
		String sql=""+
		"update isw_resinv_import ri "+
		"set (ri.updated_import_count)=( "+
		"    select count(fd.ross_res_id) "+
		"    from isw_resinv_file_data fd "+
		//"    where fd.res_disp_org_unit_code=ri.dispatch_center_code "+
		"    where fd.ross_res_id in ( "+
		"        SELECT ross_res_id "+
		"        FROM isw_resource  "+
		"        where is_permanent = 1 "+
		"    ) "+
		"    and( "+
		"     (fd.pdc_id is not null and fd.pdc_id > 0) "+
		"      and length(fd.res_name) > 0 "+
		"      and (fd.kind_id is not null and fd.kind_id > 0) "+
		"     ) "+
		") "+
		"where ri.status='INWORK' "+
		"";
		
		return sql;
	}

	public static String getUpdateOHCountQuery() {
		String sql=""+
		"select count(r.id) "+
		"from isw_resource r "+
		"where r.ross_res_id in ( "+
		"    SELECT ross_res_id "+
		"    FROM isw_resinv_file_data  "+
		"    where (org_id is not null and org_id>0)  "+
		"    and (pdc_id is not null and pdc_id>0)  "+
		"    and length(first_name)>0 "+
		"    and length(last_name)>0 "+
		"    and length(res_name)>0 "+
		")";		
		
		return sql;
	}
	
	public static String getUpdateNonOHCountQuery() {
		String sql=""+
		"select count(r.id) "+
		"from isw_resource r "+
		"where r.ross_res_id in ( "+
		"    SELECT ross_res_id "+
		"    FROM isw_resinv_file_data  "+
		"    where (org_id is not null and org_id>0)  "+
		"    and (pdc_id is not null and pdc_id>0)  "+
		"    and length(res_name)>0 "+
		")";		
		
		return sql;
	}

	public static String getUpdateOHQuery(){
		String sql=""+
		"update isw_resource r " +
		"set (r.ross_resource_name, r.last_name, r.first_name) = ( " +
		"        SELECT fd.res_name, fd.last_name, fd.first_name " +
		"        FROM isw_resinv_file_data fd  " +
		"        where r.ross_res_id = fd.ross_res_id " +
		"        and (fd.org_id is not null and fd.org_id>0)  " +
		"        and (fd.pdc_id is not null and fd.pdc_id>0)  " +
		"        and length(fd.first_name)>0 " +
		"        and length(fd.last_name)>0 " +
		"        and length(fd.res_name)>0 " +
		"    ) " +
		"where r.is_permanent=1 " +
		"and r.ross_res_id in ( " +
		"    SELECT ross_res_id " +
		"    FROM isw_resinv_file_data  " +
		"    where (org_id is not null and org_id>0)  " +
		"    and (pdc_id is not null and pdc_id>0)  " +
		"    and length(first_name)>0 " +
		"    and length(last_name)>0 " +
		"    and length(res_name)>0 " +
		") " +
		"";
		return sql;
	}
	
	public static String getUpdateNonOHQuery(){
		String sql=""+
		"update isw_resource r " +
		"set (r.ross_resource_name, r.last_name, r.first_name) = ( " +
		"        SELECT fd.res_name, fd.last_name, fd.first_name " +
		"        FROM isw_resinv_file_data fd  " +
		"        where r.ross_res_id = fd.ross_res_id " +
		"        and (fd.org_id is not null and fd.org_id>0)  " +
		"        and (fd.pdc_id is not null and fd.pdc_id>0)  " +
		"        and length(fd.res_name)>0 " +
		"    ) " +
		"where r.is_permanent=1 " +
		"and r.ross_res_id in ( " +
		"    SELECT ross_res_id " +
		"    FROM isw_resinv_file_data  " +
		"    where (org_id is not null and org_id>0)  " +
		"    and (pdc_id is not null and pdc_id>0)  " +
		"    and length(res_name)>0 " +
		") " +
		"";
		return sql;
	}

	public static String getInsertOHCountQuery() {
		String sql=""+
		"select count(fd.ross_res_id) " +
		"from isw_resinv_file_data fd " +
		"where fd.ross_res_id not in ( " +
		"    SELECT ross_res_id " +
		"    FROM isw_resource  " +
		"    where is_permanent = 1 " +
		") " +
		"";
		
		return sql;
	}

	public static String getInsertNonOHCountQuery() {
		String sql=""+
		"select count(fd.ross_res_id) " +
		"from isw_resinv_file_data fd " +
		"where fd.ross_res_id not in ( " +
		"    SELECT ross_res_id " +
		"    FROM isw_resource  " +
		"    where is_permanent = 1 " +
		") " +
		"";
		
		return sql;
	}

	public static String getInsertOHQuery() {
		String sql=""+
		"insert into isw_resource "+
		"(id, is_permanent, is_enabled, is_person, is_component, is_contracted, is_leader "+
		",resource_name,last_name, first_name, number_of_personnel, leader_type "+
		",ross_res_id, ross_resource_name,ross_last_name, ross_first_name "+
		",organization_id, primary_disp_ctr_id, transferable_identity, last_ross_updated_date "+
		") "+
		"select seq_resource.nextVal "+
		"        ,1,1,1,0,0,0 "+
		"        ,fd.res_name,fd.last_name,fd.first_name,0,99 "+
		"        ,fd.ross_res_id,fd.res_name,fd.last_name,fd.first_name "+
		"        ,fd.org_id,fd.pdc_id,'',null "+
		"from isw_resinv_file_data fd "+
		"where fd.ross_res_id is not null  "+
		"and fd.ross_res_id > 0 "+
		"and length(fd.res_name) > 0 "+
		"and length(fd.last_name) > 0 "+
		"and length(fd.first_name) > 0 "+
		"and length(fd.res_name) > 0 "+
		"and fd.ross_res_id not in (select ross_res_id from isw_resource where is_permanent=1) "+
		"";
		
		return sql;
	}
	
	public static String getInsertNonOHQuery() {
		String sql=""+
		"insert into isw_resource "+
		"(id, is_permanent, is_enabled, is_person, is_component, is_contracted, is_leader "+
		",resource_name,last_name, first_name, number_of_personnel, leader_type "+
		",ross_res_id, ross_resource_name,ross_last_name, ross_first_name "+
		",organization_id, primary_disp_ctr_id, transferable_identity, last_ross_updated_date "+
		") "+
		"select seq_resource.nextVal "+
		"        ,1,1,0,0,0,0 "+
		"        ,fd.res_name,fd.last_name,fd.first_name,0,99 "+
		"        ,fd.ross_res_id,fd.res_name,fd.last_name,fd.first_name "+
		"        ,fd.org_id,fd.pdc_id,'',null "+
		"from isw_resinv_file_data fd "+
		"where fd.ross_res_id is not null  "+
		"and fd.ross_res_id > 0 "+
		"and length(fd.res_name) > 0 "+
		"and fd.ross_res_id not in (select ross_res_id from isw_resource where is_permanent=1) "+
		"";
		
		return sql;
	}

	public static String getInsertNonOHKindCountQuery() {
		//get count of non-oh resources that need resource kind record
		String sql=""+
				   "select count(id) " +
				   "from isw_resource " +
				   "where is_person=0 " +
				   "and id not in ( " +
				   "	select resource_id from isw_resource_kind " +
				   ") " +
				   "and ross_res_id in ( " +
				   "	select ross_res_id " +
				   "	from isw_resinv_file_data " +
				   "	where kind_id is not null " +
				   ") " +
		"";
		
		return sql;
	}
	
	public static String getInsertNonOHKindQuery() {
		//get count of non-oh resources that need resource kind record
		String sql=""+
		"insert into isw_resource_kind (id, resource_id, kind_id, is_training) " +
		"select seq_resource_kind.nextVal " +
		"        , r.id " +
		"        , (select rfd.kind_id from isw_resinv_file_data rfd where rfd.ross_res_id = r.ross_res_id ) " +
		"        , 0 " +
		"from isw_resource r " +
		"where r.is_person=0 " +
		"and r.ross_res_id in ( " +
		"    select ross_res_id " +
		"    from isw_resinv_file_data " +
		"    where kind_id is not null " +
		") " +
		"and r.id not in (select resource_id from isw_resource_kind) " +
		"";
		
		return sql;
	}
}
