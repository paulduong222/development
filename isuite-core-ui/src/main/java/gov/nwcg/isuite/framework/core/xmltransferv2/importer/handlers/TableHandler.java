package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlTable;

import org.springframework.context.ApplicationContext;

public interface TableHandler {

	public void setDialogueVo(DialogueVo dvo);
	
	public void setDao(DataTransferDao dao);
	
	public void setXmlObject(Object xmlObject);
	
	public Object getXmlObject();
	
	public void setXmlTable(XmlTable xt);
	
	public void setContext(ApplicationContext context);

	public void setRunMode(String mode);

	public void setFromWebServlet(Boolean val);
	
	public void setFromWebServletUserId(Long id);

	public void setDataStewardUserId(Long id);
	
	public void setIncidentCount(int cnt);
	
	public void setIncidentGroupCount(int cnt);
	
	public Boolean doPreProcess() throws Exception;
	
	public void doPostInsertProcesses() throws Exception;

	public void doPostUpdateProcesses() throws Exception;

	public Boolean getIsOtherLabelField();	
	
	public void setOtherLabelFieldCount(int cnt);
}
