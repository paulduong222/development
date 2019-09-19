package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlTable;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.Collection;

import org.apache.log4j.Level;
import org.springframework.context.ApplicationContext;

public class BaseTableHandler {
	protected ApplicationContext context=null;
	protected DataTransferDao dao=null;
	protected XmlTable xmlTable=null;
	protected Object xmlObject=null;
	protected String runMode="";
	protected Boolean fromWebServlet=false;
	protected Long fromWebServletUserId=0L;
	protected Long dataStewardUserId=0L;
	protected DialogueVo dialogueVo;
	protected int incidentCount=0;
	protected int incidentGroupCount=0;

	// special vars for incident/group prefs other fields
	protected Boolean isOtherLabelField=false;
	protected int otherLabelFieldCount=0;
	
	protected void log(Level level,String val){
		System.out.println(val);
		
		// log errors
		if(level.toInt()==Level.ERROR_INT){
			
		}
	}
	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(DataTransferDao dao) {
		this.dao = dao;
	}
	
	public void setContext(ApplicationContext ctx) {
		this.context = ctx;
	}
	
	/**
	 * @param xmlTable the xmlTable to set
	 */
	public void setXmlTable(XmlTable xmlTable) {
		this.xmlTable = xmlTable;
	}
	/**
	 * @param xmlObject the xmlObject to set
	 */
	public void setXmlObject(Object xmlObject) {
		this.xmlObject = xmlObject;
	}

	/**
	 * @param runMode the runMode to set
	 */
	public void setRunMode(String runMode) {
		this.runMode = runMode;
	}
	
	public void setFromWebServlet(Boolean val) {
		this.fromWebServlet = val;
	}

	public void setFromWebServletUserId(Long val) {
		this.fromWebServletUserId = val;
	}
	
	public void setDataStewardUserId(Long val) {
		this.dataStewardUserId = val;
	}

	protected Object executeQuery(String sql) throws Exception {
		Object queryResult=dao.executeQuery(sql);

		if(null != queryResult){
			Collection results = (Collection)queryResult;
			if(CollectionUtility.hasValue(results)){
				for(Object object : results){
					return object;
				}
			}
		}
		
		return null;
	}

	/**
	 * @param dialogueVo the dialogueVo to set
	 */
	public void setDialogueVo(DialogueVo dialogueVo) {
		this.dialogueVo = dialogueVo;
	}

	/**
	 * @return the xmlObject
	 */
	public Object getXmlObject() {
		return xmlObject;
	}

	public void setIncidentCount(int incidentCount) {
		this.incidentCount = incidentCount;
	}

	public void setIncidentGroupCount(int incidentGroupCount) {
		this.incidentGroupCount = incidentGroupCount;
	}

	public Boolean getIsOtherLabelField(){
		return this.isOtherLabelField;
	}
	
	public void setOtherLabelFieldCount(int cnt){
		this.otherLabelFieldCount=cnt;
	}
	
}
