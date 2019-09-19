package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.core.domain.xmlv2.IswIncidentGroupQuestion;
import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.TypeConverter;


public class IncidentGroupQuestionTableHandler extends BaseTableHandler implements TableHandler {
	
	public IncidentGroupQuestionTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		if(super.runMode.equals("SITE")){
			this.doSitePreProcess();
		}
		
		return true;
	}

	private Object doSitePreProcess() throws Exception {
		/*
		 * For site mode, try and match with existing question in the database.
		 */
		Object xmlQuestionObject = null; //((IswIncidentGroupQuestion)xmlObject).getQ();
		
		// get xml values need to match with existing site group questions
		//Object xmlVisibleValue = XmlTransferUtil.invokeGetMethod(xmlObject, "Visible");
		XmlField xf1=xmlTable.getXmlFieldBySqlName("TRANSFERABLE_IDENTITY");
		XmlField xf2=xmlTable.getXmlFieldBySqlName("QUESTION_ID");
		XmlField xf3=xmlTable.getXmlFieldBySqlName("INCIDENT_GROUP_ID");
		
		Object xmlTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, xf1.name);
		Object xmlQuestionTiValue = XmlTransferUtil.invokeGetMethod(xmlObject, xf2.name);

		// get the isw_question.id value for the incoming xmlQuestionTiValue
		String sql = "SELECT id FROM isw_question WHERE transferable_identity = '" + (String)xmlQuestionTiValue + "' ";
		Object rslt=super.executeQuery(sql);
		if(null != rslt){
			Long questionId = TypeConverter.convertToLong(rslt);
			
			// check if there is an existing site group isw_incident_group_question 
			// that has the same questionId (isw_question.id)
			sql="SELECT id FROM isw_incident_group_question WHERE question_id = " + questionId + " ";
			rslt=super.executeQuery(sql);
			if(null != rslt){
				Long incidentGroupQuestionId = TypeConverter.convertToLong(rslt);
				
				sql="UPDATE isw_incident_group_question " +
					"SET transferable_identity = '" + (String)xmlTiValue + "' " +
					"WHERE id = " + incidentGroupQuestionId + " ";
				
				dao.executeUpdate(sql);
			}
		}
		return null;
	}

	public void doPostInsertProcesses() throws Exception {
	}

	public void doPostUpdateProcesses() throws Exception {

	}

}
