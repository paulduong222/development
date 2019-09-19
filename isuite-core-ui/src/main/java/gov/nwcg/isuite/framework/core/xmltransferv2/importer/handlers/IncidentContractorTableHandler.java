package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class IncidentContractorTableHandler extends BaseTableHandler implements TableHandler {
	
	
	public IncidentContractorTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		XmlField xf1=xmlTable.getXmlFieldBySqlName("INCIDENT_ID");
		XmlField xf2=xmlTable.getXmlFieldBySqlName("CONTRACTOR_ID");
		
		String incTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf1.name);
		String contrTi=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xf2.name);
		
		if(StringUtility.hasValue(incTi)&& StringUtility.hasValue(contrTi)){
			String sqlCheck="select incident_id "+
						    "from isw_incident_contractor " +
						    "where incident_id = (select min(id) from isw_incident where transferable_identity='"+incTi+"') " +
						    "and contractor_id = (select min(id) from isw_contractor where transferable_identity='"+contrTi+"') "; 
			
			Object rslt=super.executeQuery(sqlCheck);
			if(null == rslt){
				// create the record
				String sqlInsert="insert into isw_incident_contractor (incident_id, contractor_id) " +
								 "values (" +
								 "(select id from isw_incident where transferable_identity='"+incTi+"')"+
								 ",(select id from isw_contractor where transferable_identity='"+contrTi+"')"+
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
