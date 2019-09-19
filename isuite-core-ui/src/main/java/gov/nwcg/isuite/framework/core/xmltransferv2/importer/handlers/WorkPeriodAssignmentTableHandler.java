package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class WorkPeriodAssignmentTableHandler extends BaseTableHandler implements TableHandler {
	
	
	public WorkPeriodAssignmentTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		XmlField xf1=xmlTable.getXmlFieldBySqlName("WORK_PERIOD_ID");
		XmlField xf2=xmlTable.getXmlFieldBySqlName("ASSIGNMENT_ID");
		
		String wpTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf1.name);
		String aTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf2.name);
		
		if(StringUtility.hasValue(wpTi)&& StringUtility.hasValue(aTi)){
			String sqlCheck="select work_period_id "+
						    "from isw_work_period_assignment " +
						    "where work_period_id = (select min(id) from isw_work_period where transferable_identity='"+wpTi+"') " +
						    "and assignment_id = (select min(id) from isw_assignment where transferable_identity='"+aTi+"') "; 
			
			Object rslt=super.executeQuery(sqlCheck);
			if(null == rslt){
				// create the record
				String sqlInsert="insert into isw_work_period_assignment (work_period_id, assignment_id) " +
								 "values (" +
								 "(select min(id) from isw_work_period where transferable_identity='"+wpTi+"')"+
								 ",(select min(id) from isw_assignment where transferable_identity='"+aTi+"')"+
								 ")";
				dao.executeUpdate(sqlInsert);
			}

		}
		return false;
	}

	public void doPostInsertProcesses() throws Exception {
		
	}
	
	public void doPostUpdateProcesses() throws Exception {
		
	}
}
