package gov.nwcg.isuite.framework.core.xmltransfer.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransfer.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.TypeConverter;


public class QuestionTableHandler extends BaseTableHandler implements TableHandler {
	
	public QuestionTableHandler(){
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

		// get xml values need to match with existing site questions
		Object xmlQuestionTypeValue = XmlTransferUtil.invokeGetMethod(xmlObject, "QuestionType");
		Object xmlQuestionValue = XmlTransferUtil.invokeGetMethod(xmlObject, "Question");
		Object xmlTiValue=XmlTransferUtil.invokeGetMethod(xmlObject, "TransferableIdentity");
		Object xmlStandardValue=XmlTransferUtil.invokeGetMethod(xmlObject, "Standard");
		
		// first check if the xmlTiValue already exists in the db?
		String sql="SELECT id FROM isw_question WHERE transferable_identity = '"+(String)xmlTiValue+"' ";
		Object rslt = super.executeQuery(sql);
		if(null != rslt){
			// the record is already there
			return null;
		}else{
			// its not in the db yet
			// try and find a matching record in xml with one in the db?
			boolean isStandard=(Boolean)xmlStandardValue;
			
			sql="SELECT id " +
				    "FROM isw_question " +
				    "WHERE upper(question_type) = '" + ((String)xmlQuestionTypeValue).toUpperCase() + "' " +
				    "AND upper(question) = '" + ((String)xmlQuestionValue).toUpperCase() + "' ";
					if(dao.isOracleDialect()){
					    sql=sql+"AND is_standard = " + (isStandard==true ? 1 : 0 ) + " ";
					}else{
					    sql=sql+"AND is_standard = " + (isStandard==true ? true : false ) + " ";
					}
			rslt=super.executeQuery(sql);
			if(null != rslt){
				Long id = TypeConverter.convertToLong(rslt);
					
				sql="UPDATE isw_question " +
					"SET transferable_identity = '" + (String)xmlTiValue + "' " +
					"WHERE id = " + id + " " ;
					
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
