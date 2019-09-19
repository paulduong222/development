package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;


public class CostAccrualExtractTableHandler extends BaseTableHandler implements TableHandler {
	
	public CostAccrualExtractTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		return true;
	}

	private Object doSitePreProcessIG() throws Exception {
		
		return null;
	}

	public void doPostInsertProcesses() throws Exception {
		if(super.runMode.equals("SITE")){
			if(super.incidentCount==1 && super.incidentGroupCount==0){
				XmlField xf = xmlTable.getXmlFieldBySqlName("INCIDENT_ID");
				Object tiValue = XmlTransferUtil.invokeGetMethod(xmlObject, xf.name);
				
				String sql="update isw_cost_accrual_extract "+
						   "set is_from_single_incident = 'Y' " +
						   "where incident_id = (select id from isw_incident where transferable_identity='"+tiValue+"') ";
				
				dao.executeUpdate(sql);
			}
		}
	}

	public void doPostUpdateProcesses() throws Exception {

	}

}
