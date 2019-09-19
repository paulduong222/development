package gov.nwcg.isuite.core.task;

import gov.nwcg.isuite.core.cost.CostGenerator;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.TaskQueue;
import gov.nwcg.isuite.core.domain.TaskQueueLog;
import gov.nwcg.isuite.core.domain.impl.TaskQueueLogImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.TaskQueueLogDao;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.framework.core.task.BaseTask;
import gov.nwcg.isuite.framework.core.task.EISuiteTask;
import gov.nwcg.isuite.framework.exceptions.IrwinException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.TaskException;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import gov.nwcg.isuite.core.irwin.IRWINJSONParser;
import gov.nwcg.isuite.core.irwin.IncidentModel;
import gov.nwcg.isuite.core.irwin.RequestPackage;
import gov.nwcg.isuite.core.irwin.TokenModel;
import gov.nwcg.isuite.core.irwin.HttpManager;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class IrwinTask extends BaseTask implements EISuiteTask {
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.tasks.EISuiteTask#runScheduledTask()
	 */
	public int runScheduledTask() throws TaskException {
		
		try{
			//super.simulateLogin();
			//System.out.println("RunDailyCostTask");
				
			//System.out.println("IrwinTask");
			
			/*
			IncidentDao dao = (IncidentDao)context.getBean("incidentDao");
			IncidentResourceDao irdao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			
			// get list of active incidents
			Collection<Incident> incidents = dao.getAutoCostRunIncidents();
			
			// for each incident, process run cost
			for(Incident incident : incidents){
				Long id=incident.getId();

				// Try and run the cost process for the incident
				Collection<CostResourceDataVo> costResourceDataVos = irdao.getCostResourceData(null,id,null);

				CostGenerator costGen = new CostGenerator(this.context);
				for(CostResourceDataVo v : costResourceDataVos){
					// only process top level resources
					if(!LongUtility.hasValue(v.getParentResourceId())){
						costGen.generateCosts(v, costResourceDataVos,false);
					}
				}
				
			}
			*/
			processTaskComplete();
		}catch(Exception e){
			super.handleException(e);
		}
		
		return 1;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.task.EISuiteTask#postTask()
	 */
	public void postTask() throws TaskException{

		try{
			System.out.println("v2 api: postTask(): IrwinTask");

			//TokenModel token;
			//String code = generateGoogle();
			
			String code = generateToken();
			//String code = "";
			List<IncidentModel> irwinIncidentList = getUpdates(code);
			for (IncidentModel incidentModel: irwinIncidentList) {
				System.out.println("Incident Name: " + incidentModel.getIncidentName());
				System.out.println("IRWIN ID: " + incidentModel.getIrwinID());
				System.out.println("=================================================================");
			}		
			
			IncidentDao dao = (IncidentDao)context.getBean("incidentDao");
						
			// get list of active incidents
			Collection<Incident> incidents = dao.getAllActiveIncidents();
			IncidentModel inc;
			
			String irwinId = "";
						
			for(Incident incident : incidents){
//				System.out.println("incident: " + incident.getIncidentName());
//				if (incident.getIrwinIrwinId() == null) {
//					System.out.println("incident: " + incident.getIncidentName() + " has no irwin id");
//					String id = findMatch(code, incident);
//					System.out.println("irwin id: " + id);
//					incident.setIrwinIrwinId(id);
//				}
//				else {
//					System.out.println("incident: " + incident.getIncidentName() + " has irwin id");
//					inc = getIncident(code, incident.getIrwinIrwinId());
//					
//					if (incident.getIncidentName().equalsIgnoreCase(inc.getIncidentName())) {
//						//incident.setIrwinIrwinId(inc.getIrwinID());
//						//incident.setIrwinIrwinId(inc.getIrwinID().substring(1, inc.getIrwinID().length()-1));
//						incident.setIrwinCreatedBySystem(inc.getCreatedBySystem());
//						incident.setIrwinCreatedOnDateTime(inc.getCreatedOnDateTime());
//						incident.setIrwinFireCode(inc.getFireCode());
//						incident.setIrwinFireDiscoveryDateTime(inc.getFireDiscoveryDateTime());
//						incident.setIrwinFsJobCode(inc.getFSJobCode());
//						incident.setIrwinFsOverrideCode(inc.getFSOverrideCode());
//						incident.setIrwinIncidentName(inc.getIncidentName());
//						incident.setIrwinIncidentTypeCategory(inc.getIncidentTypeCategory());
//						incident.setIrwinIncidentTypeKind(inc.getIncidentTypeKind());
//						incident.setIrwinIsActive(inc.getIsActive());
//						incident.setIrwinLocalIncidentIdentifier(inc.getLocalIncidentIdentifier());
//						incident.setIrwinModifiedBySystem(inc.getModifiedBySystem());
//						incident.setIrwinModifiedOnDateTime(inc.getModifiedOnDateTime());
//						//incident.setIrwinPOOProtectingUnit(inc.getPOOProtectingUnit());
//						//incident.setIrwinP
//						incident.setIrwinRecordSource(inc.getRecordSource());
//						String description =   "IrwinID: " + inc.getIrwinID() + "\n" 
//						                     + "IncidentName: " + inc.getIncidentName() + "\n" 
//						                     + "FireCode: " + inc.getFireCode() + "\n" 
//						                     + "FSJobCode: " + inc.getFSJobCode() + "\n" 
//						                     + "FSOverrideCode: " + inc.getFSOverrideCode() + "\n" 
//						                     + "IncidentTypeCategory: " + inc.getIncidentTypeCategory() + "\n" 
//						                     + "IncidentTypeKind: " + inc.getIncidentTypeKind() + "\n" 
//						                     + "IsActive: " + inc.getIsActive() + "\n" 
//						                     + "LocalIncidentIdentifier: " + inc.getLocalIncidentIdentifier() + "\n" 
//						                     + "RecordSource: " + inc.getRecordSource() + "\n" 
//						                     + "ModifiedBySystem: " + inc.getModifiedBySystem() + "\n" 
//						                     + "ModifiedOnDateTime: " + inc.getModifiedOnDateTime() + "\n"
//						                     + "CreatedBySystem: " + inc.getCreatedBySystem() + "\n" 
//						                     + "CreatedOnDateTime: " + inc.getCreatedOnDateTime() + "\n"
//						                     + "FireDiscoveryDateTime: " + inc.getFireDiscoveryDateTime();
//						incident.setIncidentDescription(description);
//					}
//					else {
//						System.out.println("incident name changed");
//						incident.setIrwinIrwinId(null);
//					}
//					
//				}
//
//				dao.save(incident);
//				System.out.println("dao saved");
				
				
				if (incident.getIncidentDescription() != null) {
					if (incident.getIncidentDescription().length() == 36) {
						code = generateToken();
						//irwinId = incident.getIncidentDescription().substring(1, incident.getIncidentDescription().length()-1);
						irwinId = incident.getIncidentDescription();
						inc = getIncident(code, irwinId);
						incident.setIrwinIrwinId(inc.getIrwinID());
						//incident.setIrwinIrwinId(inc.getIrwinID().substring(1, inc.getIrwinID().length()-1));
						incident.setIrwinCreatedBySystem(inc.getCreatedBySystem());
						incident.setIrwinCreatedOnDateTime(inc.getCreatedOnDateTime());
						incident.setIrwinFireCode(inc.getFireCode());
						incident.setIrwinFireDiscoveryDateTime(inc.getFireDiscoveryDateTime());
						incident.setIrwinFsJobCode(inc.getFSJobCode());
						incident.setIrwinFsOverrideCode(inc.getFSOverrideCode());
						incident.setIrwinIncidentName(inc.getIncidentName());
						incident.setIrwinIncidentTypeCategory(inc.getIncidentTypeCategory());
						incident.setIrwinIncidentTypeKind(inc.getIncidentTypeKind());
						incident.setIrwinIsActive(inc.getIsActive());
						incident.setIrwinLocalIncidentIdentifier(inc.getLocalIncidentIdentifier());
						incident.setIrwinModifiedBySystem(inc.getModifiedBySystem());
						incident.setIrwinModifiedOnDateTime(inc.getModifiedOnDateTime());
						//incident.setIrwinPOOProtectingUnit(inc.getPOOProtectingUnit());
						//incident.setIrwinP
						incident.setIrwinRecordSource(inc.getRecordSource());
						incident.setIrwinIsValid(inc.getIsValid());
						String description =   "IrwinID: " + inc.getIrwinID() + "\n" 
						                     + "IncidentName: " + inc.getIncidentName() + "\n" 
						                     + "FireCode: " + inc.getFireCode() + "\n" 
						                     + "FSJobCode: " + inc.getFSJobCode() + "\n" 
						                     + "FSOverrideCode: " + inc.getFSOverrideCode() + "\n" 
						                     + "IncidentTypeCategory: " + inc.getIncidentTypeCategory() + "\n" 
						                     + "IncidentTypeKind: " + inc.getIncidentTypeKind() + "\n" 
						                     + "IsActive: " + inc.getIsActive() + "\n" 
						                     + "LocalIncidentIdentifier: " + inc.getLocalIncidentIdentifier() + "\n" 
						                     + "RecordSource: " + inc.getRecordSource() + "\n" 
						                     + "ModifiedBySystem: " + inc.getModifiedBySystem() + "\n" 
						                     + "ModifiedOnDateTime: " + inc.getModifiedOnDateTime() + "\n"
						                     + "CreatedBySystem: " + inc.getCreatedBySystem() + "\n" 
						                     + "CreatedOnDateTime: " + inc.getCreatedOnDateTime() + "\n"
						                     + "FireDiscoveryDateTime: " + inc.getFireDiscoveryDateTime() + "\n"
						                     + "IsValid: " + inc.getIsValid();
						incident.setIncidentDescription(description);
						//dao.save(incident);
						
					}
				}
			}
			
			
			
			
			//String uri2 = "https://irwint.doi.gov/arcgis/rest/services/Irwin/MapServer/exts/Irwin/GetUpdates";
			//List<IncidentModel> irwinIncidentList = getUpdates(code);
			
			//submit incidents 
			/* currently not allowed
			for(Incident incident : incidents){
				String incName = incident.getIncidentName();
				
				if (incident.getIrwinIrwinId()==null) {
					//Iterator<IncidentModel> it = irwinIncidentList.iterator();
					//while(it.hasNext())
					//{
						//IncidentModel inc = it.next();
						//System.out.println("IRWIN ID: " + inc.getIrwinID());
						//System.out.println("IncidentName: " + inc.getIncidentName());
						//System.out.println("ModifiedOnDateTime: " + inc.getModifiedOnDateTime());
					//}
					String irwinId = submitIncident(code, incName);
					incident.setIrwinIrwinId(irwinId);
					dao.save(incident);
					System.out.println("NEW IRWIN ID: " + irwinId);
					
				}
			}
			*/
			
			//IncidentModel inc = irwinIncidentList.get(irwinIncidentList.size()-1);  //use the last one	
			//setFromDateTime(inc.getModifiedOnDateTime());
			
			//System.out.println("irwinID: " + inc.getIrwinID());
			
//			for(Incident incident : incidents){
//				System.out.println("incident: " + incident.getIncidentName());
//				//incident.setIrwinIrwinId(inc.getIrwinID());
//				incident.setIrwinIrwinId(inc.getIrwinID().substring(1, inc.getIrwinID().length()-1));
//				incident.setIrwinCreatedBySystem(inc.getCreatedBySystem());
//				incident.setIrwinCreatedOnDateTime(inc.getCreatedOnDateTime());
//				incident.setIrwinFireCode(inc.getFireCode());
//				incident.setIrwinFireDiscoveryDateTime(inc.getFireDiscoveryDateTime());
//				incident.setIrwinFsJobCode(inc.getFSJobCode());
//				incident.setIrwinFsOverrideCode(inc.getFSOverrideCode());
//				incident.setIrwinIncidentName(inc.getIncidentName());
//				incident.setIrwinIncidentTypeCategory(inc.getIncidentTypeCategory());
//				incident.setIrwinIncidentTypeKind(inc.getIncidentTypeKind());
//				incident.setIrwinIsActive(inc.getIsActive());
//				incident.setIrwinLocalIncidentIdentifie(inc.getLocalIncidentIdentifier());
//				incident.setIrwinModifiedBySystem(inc.getModifiedBySystem());
//				incident.setIrwinModifiedOnDateTime(inc.getModifiedOnDateTime());
//				incident.setIrwinPooResponsibleUnit(inc.getPOOResponsibleUnit());
//				incident.setIrwinRecordSource(inc.getRecordSource());
//				dao.save(incident);
//			}
			
			
            //String uri3 = "https://irwint.doi.gov/arcgis/rest/services/Irwin/MapServer/exts/Irwin/GetIncidents";
            //getIncidents(code, inc.getIrwinID().substring(1, inc.getIrwinID().length()-1));		
            
            //updateIncident(code, inc.getIrwinID().substring(1, inc.getIrwinID().length()-1));
			//System.out.println("taskQueueLogDao before");			
			TaskQueueLogDao taskQueueLogDao = (TaskQueueLogDao)this.context.getBean("taskQueueLogDao");
			//System.out.println("taskQueueLogDao after");
			// create the tq log for this task run
			//TaskQueueLog taskQueueLogEntity = buildTaskQueueLog(taskQueueEntity, "Success: " + code);
			TaskQueueLog taskQueueLogEntity = buildTaskQueueLog(taskQueueEntity, "Success");
			//System.out.println("buildTaskQueueLog done");
			taskQueueLogDao.save(taskQueueLogEntity);
			//System.out.println("taskQueueLogDao saved");
			
		}catch(IrwinException ie){
            TaskQueueLogDao taskQueueLogDao = (TaskQueueLogDao)this.context.getBean("taskQueueLogDao");
			TaskQueueLog taskQueueLogEntity = buildTaskQueueLog(taskQueueEntity, "Failed, IrwinException: " + ie.getMessage());
			try {
				taskQueueLogDao.save(taskQueueLogEntity);
			} catch (PersistenceException pe) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw new TaskException(pe.getMessage());
			}
		}catch (Exception e) {
			//System.out.println(e.getMessage());
			//throw new TaskException(e.getMessage());
			TaskQueueLogDao taskQueueLogDao = (TaskQueueLogDao)this.context.getBean("taskQueueLogDao");
			TaskQueueLog taskQueueLogEntity = buildTaskQueueLog(taskQueueEntity, "Failed, Exception: " + e.getMessage());
			try {
				taskQueueLogDao.save(taskQueueLogEntity);
			} catch (PersistenceException pe) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw new TaskException(pe.getMessage());
			}
		}
	}

	private String findMatch(String code, Incident incident) throws Exception, TaskException, IrwinException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
		//String dt = "2008-01-01";  // Start date
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(incident.getIncidentStartDate());
		cStart.add(Calendar.DATE, -365);  // number of days to substract
		
		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(incident.getIncidentStartDate());
		cEnd.add(Calendar.DATE, 365);  // number of days to add
		
		String startDate = simpleDateFormat.format(cStart.getTime()).replace(" ", "T") + "Z";
		String endDate = simpleDateFormat.format(cEnd.getTime()).replace(" ", "T") + "Z";
		
		//System.out.println("startDate: " + startDate);
		//System.out.println("endDate: " + endDate);
		
		//dt = sdf.format(c.getTime());  // dt is now the new date
		
		String uri = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GETUPDATES);
		RequestPackage p = new RequestPackage();
		p.setMethod("POST");
		p.setUri(uri);
		p.setParam("token", code);
		//p.setParam("fromDateTime", super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_FROMDATETIME));
		//p.setParam("toDateTime", "2014-10-22T20:23:00Z");
		p.setParam("fromDateTime", startDate);
		p.setParam("toDateTime", endDate);
		p.setParam("f", "json");
		
		String response = HttpManager.getData(p);
		
		//System.out.println("findMatch response:" + response);
		
		//System.out.println("pass the second one: " + response2);
		
		String active_or_valid = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_ISVALID); //0=active, 1=valid
		
		List<IncidentModel> list = IRWINJSONParser.getUpdates(response, active_or_valid);
		
		//System.out.println("list returned");
		
		boolean foundMatch = false;
		String irwinId = "";
		
		if (list.size() == 0)
			return null;
		else {
			//IncidentModel inc = list.get(0); //use the first one
			//System.out.println("list size: " + list.size());
			
			//use incident name only for the time being
			for (Iterator<IncidentModel> iter = list.iterator(); iter.hasNext(); ) {
				IncidentModel inc = iter.next();
				if (incident.getIncidentName().equalsIgnoreCase(inc.getIncidentName())) {
					foundMatch = true;
					irwinId = inc.getIrwinID().substring(1, inc.getIrwinID().length()-1);
				}
			}
			//IncidentModel inc = list.get(list.size()-1);  //use the last one
			if (foundMatch)
			  return irwinId;
			else
			  return null;
		}
	}
	
	/*
	public String getMyIP () {
		  InetAddress myip;
		  try {
	 
			myip = InetAddress.getLocalHost();
			return myip.getHostAddress();
	 
		  } catch (UnknownHostException e) {
			  return "127.0.0.1"; 
		  }
	}
	
	public void setServerURL() {
		SystemParameter sysParamEntity = null;
		SystemParameterDao sysParamDao = (SystemParameterDao) context
				.getBean("systemParameterDao");

		try {
			
			sysParamEntity = sysParamDao.getByParameterName(SystemParameterTypeEnum.RUN_MODE.toString());
			if (sysParamEntity.getParameterValue().equals("SITE")) {
				sysParamEntity = sysParamDao.getByParameterName(SystemParameterTypeEnum.PROXY_MODE.toString());
				if (sysParamEntity.getParameterValue().equals("FALSE")) {
					sysParamEntity = sysParamDao.getByParameterName(SystemParameterTypeEnum.REPORT_OUTPUT_URL.toString());
					sysParamEntity.setParameterValue("http://" + getMyIP()+ "/isuite/reportsoutput/");
					sysParamDao.save(sysParamEntity);
					sysParamDao.flushAndEvict(sysParamEntity);
				}
			}
			
		} catch (Exception e) {

		}
	}
	*/		
	private void setFromDateTime(String fromDateTime) {
		SystemParameter sysParamEntity = null;
		SystemParameterDao sysParamDao = (SystemParameterDao) context
				.getBean("systemParameterDao");

		try {
			
			sysParamEntity = sysParamDao.getByParameterName(SystemParameterTypeEnum.IRWIN_FROMDATETIME.toString());
			sysParamEntity.setParameterValue(fromDateTime);
			sysParamDao.save(sysParamEntity);
			sysParamDao.flushAndEvict(sysParamEntity);
			
		} catch (Exception e) {

		}
	}
	
	private String submitIncident(String code, String name) throws Exception, TaskException, IrwinException {
		String uri = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_SUBMITINCIDENT);
		String fields = "IrwinID,FireDiscoveryDateTime,POOResponsibleUnit,LocalIncidentIdentifier,IncidentName,IncidentTypeKind,IncidentTypeCategory,FireCode,FSJobCode,FSOverrideCode,IsActive,RecordSource,CreatedBySystem,CreatedOnDateTime,ModifiedBySystem,ModifiedOnDateTime,IsValid";

		RequestPackage p = new RequestPackage();
		p.setMethod("POST");
		p.setUri(uri);
		p.setParam("token", code);
		//p.setParam("irwinIds", irwinIds);
		//p.setParam("fields", fields);
		p.setParam("f", "json");
		p.setParam("fireDiscoveryDateTime", "2013-10-29T10=30=00Z");
		p.setParam("pooResponsibleUnit", "ISSC");
		p.setParam("localIncidentIdentifier", "ISSC1");
		p.setParam("incidentName", name);
		p.setParam("incidentTypeKind", "FI");
		p.setParam("incidentTypeCategory", "WF");
		//& pooLongitude=-96.459961
		//& pooLatitude=43.325178
		
		String response = HttpManager.getData(p);
		
		//System.out.println("pass the third one: " + response);
		
		//IRWINJSONParser.submitIncident(response);
		
		//System.out.println(response.length());
		
		return IRWINJSONParser.submitIncident(response);
	}
	
	private String updateIncident(String code, String irwinId) throws Exception, TaskException, IrwinException {
		String uri = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_UPDATEINCIDENT);
		String fields = "IrwinID,FireDiscoveryDateTime,POOResponsibleUnit,LocalIncidentIdentifier,IncidentName,IncidentTypeKind,IncidentTypeCategory,FireCode,FSJobCode,FSOverrideCode,IsActive,RecordSource,CreatedBySystem,CreatedOnDateTime,ModifiedBySystem,ModifiedOnDateTime,IsValid";

		RequestPackage p = new RequestPackage();
		p.setMethod("POST");
		p.setUri(uri);
		p.setParam("token", code);
		p.setParam("irwinId", irwinId);
		//p.setParam("fields", fields);
		p.setParam("f", "json");
		//p.setParam("fireDiscoveryDateTime", "2013-10-29T10=30=00Z");
		p.setParam("pooResponsibleUnit", "ISSC");
		//p.setParam("localIncidentIdentifier", "ISSC1");
		//p.setParam("incidentName", name);
		//p.setParam("incidentTypeKind", "FI");
		//p.setParam("incidentTypeCategory", "WF");
		//& pooLongitude=-96.459961
		//& pooLatitude=43.325178
		
		String response = HttpManager.getData(p);
		
		//System.out.println("pass the third one: " + response);
		
		//IRWINJSONParser.submitIncident(response);
		
		//System.out.println(response.length());
		
		return IRWINJSONParser.updateIncident(response);
	}

	private IncidentModel getIncident(String code, String irwinId) throws Exception, TaskException, IrwinException {
		String uri = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GETINCIDENTS);
		String active_or_valid = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_ISVALID); //0=active, 1=valid
		String fields;
		if (active_or_valid.equals("0")) {
			fields = "IrwinID,FireDiscoveryDateTime,POOProtectingUnit,LocalIncidentIdentifier,IncidentName,IncidentTypeKind,IncidentTypeCategory,FireCode,FSJobCode,FSOverrideCode,IsActive,RecordSource,CreatedBySystem,CreatedOnDateTime,ModifiedBySystem,ModifiedOnDateTime";

		}
		else {
			fields = "IrwinID,FireDiscoveryDateTime,POOProtectingUnit,LocalIncidentIdentifier,IncidentName,IncidentTypeKind,IncidentTypeCategory,FireCode,FSJobCode,FSOverrideCode,IsValid,RecordSource,CreatedBySystem,CreatedOnDateTime,ModifiedBySystem,ModifiedOnDateTime";

		}

		RequestPackage p = new RequestPackage();
		p.setMethod("POST");
		p.setUri(uri);
		p.setParam("token", code);
		p.setParam("irwinIds", irwinId);
		p.setParam("fields", fields);
		p.setParam("f", "json");
		
		String response = HttpManager.getData(p);
		
		System.out.println("passed the getIncidents: " + response);	
		
		//System.out.println(response.length());

		return IRWINJSONParser.getIncident(response, active_or_valid);
	}

	//not used
	private void getIncidents(String code, String irwinIds) throws Exception, TaskException, IrwinException {
		String uri = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GETINCIDENTS);
		String fields = "IrwinID,FireDiscoveryDateTime,POOProtectingUnit,LocalIncidentIdentifier,IncidentName,IncidentTypeKind,IncidentTypeCategory,FireCode,FSJobCode,FSOverrideCode,IsActive,RecordSource,CreatedBySystem,CreatedOnDateTime,ModifiedBySystem,ModifiedOnDateTime,IsValid";

		RequestPackage p = new RequestPackage();
		p.setMethod("POST");
		p.setUri(uri);
		p.setParam("token", code);
		p.setParam("irwinIds", irwinIds);
		p.setParam("fields", fields);
		p.setParam("f", "json");
		
		String response = HttpManager.getData(p);
		
		System.out.println("passed the getIncidents: " + response);
		
		IRWINJSONParser.getIncidents(response);
		
		//System.out.println(response.length());
	}

	private List<IncidentModel> getUpdates(String code) throws Exception, TaskException, IrwinException {
		String uri = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GETUPDATES);
		RequestPackage p = new RequestPackage();
		p.setMethod("POST");
		p.setUri(uri);
		p.setParam("token", code);
		p.setParam("fromDateTime", super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_FROMDATETIME));
		//p.setParam("toDateTime", "2014-10-22T20:23:00Z");
		p.setParam("f", "json");
		
		String response = HttpManager.getData(p);
		
		//System.out.println("response:" + response);
		
		//System.out.println("pass the second one: " + response2);
		
		String active_or_valid = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_ISVALID); //0=active, 1=valid
		
		return IRWINJSONParser.getUpdates(response, active_or_valid);
		
		//System.out.println(response.length());
	}

	private String generateToken() throws Exception, TaskException, IrwinException {
		TokenModel token;
		String uri = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GENERATETOKEN);
		RequestPackage p = new RequestPackage();
		p.setMethod("POST");
		p.setUri(uri);
		p.setParam("username", super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_USERNAME));
		p.setParam("password", super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_PASSWORD));
		p.setParam("client", "requestip");
		p.setParam("f", "json");
		
		String response = HttpManager.getData(p);
		
		//System.out.println("pass the first one: " + response);
		
		token = IRWINJSONParser.generateToken(response);
				
		String code = token.getToken();
		
		//System.out.println("token: " + code);
		return code;
	}
	
	private String generateGoogle() throws Exception, TaskException, IrwinException {
		TokenModel token;
		String uri = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GENERATETOKEN);
		RequestPackage p = new RequestPackage();
		p.setMethod("GET");
		p.setUri(uri);
		//p.setParam("username", super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_USERNAME));
		//p.setParam("password", super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_PASSWORD));
		//p.setParam("client", "requestip");
		p.setParam("name", "tsai");
		
		String response = HttpManager.getData(p);
		
		//System.out.println("pass the first one: " + response);
		
		//token = IRWINJSONParser.generateToken(response);
				
		//String code = token.getToken();
		
		//System.out.println("token: " + code);
		
		return response;
	}
	
	private TaskQueueLog buildTaskQueueLog(TaskQueue tq, String msg){
		//System.out.println("buildTaskQueueLog enter");
    	TaskQueueLog entity = new TaskQueueLogImpl();
    	entity.setStartDate(Calendar.getInstance().getTime());
    	entity.setStatus("IRWIN: COMPLETED");
    	//entity.setStatusMessage(msg.substring(0, 295));
    	entity.setStatusMessage(msg); //need to make sure msg is not over 300, increase to 2000?
    	entity.setTaskQueue(tq);
    	//System.out.println("buildTaskQueueLog before return");
    	return entity;
    }
}