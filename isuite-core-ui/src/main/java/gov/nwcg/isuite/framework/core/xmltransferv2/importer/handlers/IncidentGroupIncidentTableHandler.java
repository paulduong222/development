package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;


public class IncidentGroupIncidentTableHandler extends BaseTableHandler implements TableHandler {
	
	public IncidentGroupIncidentTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		this.doPreProcessing();
		return false;
	}

	private Object doPreProcessing() throws Exception {
		
		// create incident_group_incident record if needed
		XmlField xfIncTi=xmlTable.getXmlFieldBySqlName("INCIDENT_ID");
		XmlField xfIncGrpTi=xmlTable.getXmlFieldBySqlName("INCIDENT_GROUP_ID");
		
		Object xmlIncTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, xfIncTi.name);
		Object xmlIncGrpTiValue = XmlTransferUtil.invokeGetMethod(xmlObject, xfIncGrpTi.name);

		// check if this incident is part of group
		String sql="SELECT incident_id FROM isw_incident_group_incident " +
		  			"WHERE incident_group_id = (select min(id) from isw_incident_group where transferable_identity = '"+(String)xmlIncGrpTiValue+"' ) " +
		  			"AND incident_id = (select min(id) from isw_incident where transferable_identity = '"+(String)xmlIncTiValue+"' ) " 
		  			;
		Object rslt=super.executeQuery(sql);
		if(null == rslt){
			// add it
			sql="INSERT INTO isw_incident_group_incident " +
				 " ( incident_group_id, incident_id ) " +
				 "VALUES " +
				 " ( " +
				 "  (select id from isw_incident_group where transferable_identity = '"+(String)xmlIncGrpTiValue+"' )" +
				 "  ,(select id from isw_incident where transferable_identity = '"+(String)xmlIncTiValue+"' ) "+
				 ") ";
			dao.executeUpdate(sql);
		}
		
		return false;
	}

	public void doPostInsertProcesses() throws Exception {
	}

	public void doPostUpdateProcesses() throws Exception {

	}

}
