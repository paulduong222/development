package gov.nwcg.isuite.core.persistence.hibernate.query;

public class TimePreviousInvoiceQuery {

	public static String getPreviousInvoiceQuery(String invNumber, Boolean isOracle) {
		StringBuffer sql = new StringBuffer()
		//.append("select distinct to_char(ti.first_include_date, 'MM/DD/YYYY')||' - '||to_char(ti.last_include_date, 'MM/DD/YYYY')||' for $' as val1 ")
		.append("select distinct to_char(ti.first_include_date, 'MM/DD/YYYY')||' - '||to_char(ti.last_include_date, 'MM/DD/YYYY')||'' as val1 ")
		.append(" ,tbl2.totalPostings as totalPostings ")
		.append(" ,tbl3.totalAdditions as totalAdditions ")
		.append(" ,tbl4.totalDeductions as totalDeductions ")
		.append("from isw_time_invoice ti ")
		.append(" left join ")
		.append("	( ")
		.append("	  select sum(atp.rate_amount * atp.quantity) as totalPostings ")
		.append("        , atpi.time_invoice_id as invoiceId ")
		.append("	  from isw_assign_time_post atp ")
		.append("       , isw_assign_time_post_invoice atpi ")
		.append("	  where atp.id = atpi.assign_time_post_id ")
		.append("     group by atpi.time_invoice_id " )
		.append("	) tbl2 on ti.id = tbl2.invoiceId ")
		.append("	left join ( ")
		.append("		select sum(taa1.adjustment_amount) as totalAdditions ")
		.append("        , taai1.time_invoice_id as invoiceId ")
		.append("		from isw_time_assign_adjust taa1, isw_time_assign_adj_invoice taai1  ")
		.append("		where taa1.id = taai1.time_post_adjust_id ")
		.append("		and taa1.adjustment_type = 'ADDITION' ")
		.append("       group by taai1.time_invoice_id ")
		.append("	) tbl3 on ti.id = tbl3.invoiceId ")
		.append("	left join ( ")
		.append("		select sum(taa2.adjustment_amount) as totalDeductions ")
		.append("        , taai2.time_invoice_id as invoiceId ")
		.append("		from isw_time_assign_adjust taa2 ,  isw_time_assign_adj_invoice taai2 ")
		.append("		where taa2.id = taai2.time_post_adjust_id ")
		.append("		and taa2.adjustment_type = 'DEDUCTION' ")
		.append("       group by taai2.time_invoice_id ")
		.append("	) tbl4 on ti.id = tbl4.invoiceId ")
		.append("where invoice_number like '"+invNumber+"'  ") 
		.append("and ti.deleted_date is null ") 
		.append("and ti.is_draft = " + (isOracle==true?0:false) + " ")
		.append("and ti.is_duplicate = " + (isOracle==true?0:false) + " ");		
		
		return sql.toString();
	}
}
