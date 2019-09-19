package gov.nwcg.isuite.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.filter.impl.IrwinSearchFilterImpl;
import gov.nwcg.isuite.core.irwin.HttpManager;
import gov.nwcg.isuite.core.irwin.IRWINJSONParser;
import gov.nwcg.isuite.core.irwin.IncidentModel;
import gov.nwcg.isuite.core.irwin.RequestPackage;
import gov.nwcg.isuite.core.irwin.TokenModel;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.service.IrwinService;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.IrwinIncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.IrwinException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.TaskException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;

public class IrwinServiceImpl extends BaseService implements IrwinService {
	public IrwinServiceImpl() {
		super();
	}
	
	public void initialization() {
	}
	
	public DialogueVo searchIrwinIncidents(DialogueVo dialogueVo, IrwinSearchFilterImpl searchFilter) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			
			Collection<IrwinIncidentVo> irwinIncidentVos = new ArrayList<IrwinIncidentVo>();
			IrwinIncidentVo vo = null;
			String code = generateToken();
						
			//need to refactor the following to deal with production 504 timeout issue
			double daysChunk = Double.parseDouble(super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_SEARCH_THROTTLE));
			int iter = (int) Math.ceil(searchFilter.getDays() / daysChunk);
			
			List<IncidentModel> irwinIncidentList = new ArrayList<IncidentModel>();
			IrwinSearchFilterImpl tempSF = new IrwinSearchFilterImpl();
			
			Calendar tday = Calendar.getInstance(); 
			tday.add(Calendar.DATE,-searchFilter.getDays().intValue());
			Date startDay= tday.getTime();		
			
			tempSF.setStartDate(startDay);
			tempSF.setIncidentName(searchFilter.getIncidentName());
			tempSF.setIncidentState(searchFilter.getIncidentState());
			tempSF.setNumber(searchFilter.getNumber());
			tempSF.setUnitId(searchFilter.getUnitId());
			tempSF.setDays((long)daysChunk);
			
			for(int i = 0; i < iter; i = i+1) {
				
				List<IncidentModel> tempIrwinIncidentList = getUpdates(code, tempSF);		
				for (IncidentModel inc: tempIrwinIncidentList) {
					irwinIncidentList.add(inc);
				}
				
				Calendar beginD = Calendar.getInstance(); 
				beginD.setTime(tempSF.getStartDate());
				beginD.add(Calendar.DATE, tempSF.getDays().intValue());
				
				Date beginDay= beginD.getTime();
				tempSF.setStartDate(beginDay);
				
		    }
			
			String active_or_valid = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_ISVALID); //0=active, 1=valid
			
			for (IncidentModel inc: irwinIncidentList) {
				if (active_or_valid.equals("0")) {
					if (inc.getIsActive().equalsIgnoreCase("true")) {	
						//if (inc.getIncidentTypeCategory().equalsIgnoreCase("WF")) {
							if (IRWINJSONParser.matches(searchFilter, inc)) {
								vo = new IrwinIncidentVo();
								vo.setIrwinID(inc.getIrwinID());
								vo.setFireDiscoveryDateTime(inc.getFireDiscoveryDateTime());
								vo.setPooProtectingUnit(inc.getPOOProtectingUnit());
								vo.setLocalIncidentIdentifier(inc.getLocalIncidentIdentifier());
								vo.setIncidentName(inc.getIncidentName());
								vo.setIncidentTypeKind(inc.getIncidentTypeKind());
								vo.setIncidentTypeCategory(inc.getIncidentTypeCategory());
								vo.setFireCode(inc.getFireCode());
								vo.setFsJobCode(inc.getFSJobCode());
								vo.setFsOverrideCode(inc.getFSOverrideCode());
								vo.setIsActive(inc.getIsActive());
								vo.setIsValid(inc.getIsActive());
								vo.setRecordSource(inc.getRecordSource());
								vo.setCreatedBySystem(inc.getCreatedBySystem());
								vo.setCreatedOnDateTime(inc.getCreatedOnDateTime());
								vo.setModifiedBySystem(inc.getModifiedBySystem());
								vo.setModifiedOnDateTime(inc.getModifiedOnDateTime());
								vo.setModifiedOnDateTimeAsDate(inc.getModifiedOnDateTimeAsDate());
								vo.setCreatedOnDateTimeAsDate(inc.getCreatedOnDateTimeAsDate());
								vo.setFireDiscoveryDateTimeAsDate(inc.getFireDiscoveryDateTimeAsDate());
								
								vo.setUniqueFireIdentifier(inc.getUniqueFireIdentifier());
								vo.setIsComplex(inc.getIsComplex());
								vo.setComplexParentIrwinID(inc.getComplexParentIrwinID());
								
								vo.setAbcdMisc(inc.getABCDMisc());
												
								irwinIncidentVos.add(vo);	
							}	
						//}
					}
				}
				else {
					if (inc.getIsValid().equalsIgnoreCase("true")) {	
						//if (inc.getIncidentTypeCategory().equalsIgnoreCase("WF")) {
							if (IRWINJSONParser.matches(searchFilter, inc)) {
								vo = new IrwinIncidentVo();
								vo.setIrwinID(inc.getIrwinID());
								vo.setFireDiscoveryDateTime(inc.getFireDiscoveryDateTime());
								vo.setPooProtectingUnit(inc.getPOOProtectingUnit());
								vo.setLocalIncidentIdentifier(inc.getLocalIncidentIdentifier());
								vo.setIncidentName(inc.getIncidentName());
								vo.setIncidentTypeKind(inc.getIncidentTypeKind());
								vo.setIncidentTypeCategory(inc.getIncidentTypeCategory());
								vo.setFireCode(inc.getFireCode());
								vo.setFsJobCode(inc.getFSJobCode());
								vo.setFsOverrideCode(inc.getFSOverrideCode());
								vo.setIsValid(inc.getIsValid());
								vo.setRecordSource(inc.getRecordSource());
								vo.setCreatedBySystem(inc.getCreatedBySystem());
								vo.setCreatedOnDateTime(inc.getCreatedOnDateTime());
								vo.setModifiedBySystem(inc.getModifiedBySystem());
								vo.setModifiedOnDateTime(inc.getModifiedOnDateTime());
								vo.setModifiedOnDateTimeAsDate(inc.getModifiedOnDateTimeAsDate());
								vo.setCreatedOnDateTimeAsDate(inc.getCreatedOnDateTimeAsDate());
								vo.setFireDiscoveryDateTimeAsDate(inc.getFireDiscoveryDateTimeAsDate());
								
								vo.setUniqueFireIdentifier(inc.getUniqueFireIdentifier());
								vo.setIsComplex(inc.getIsComplex());
								vo.setComplexParentIrwinID(inc.getComplexParentIrwinID());
								
								vo.setAbcdMisc(inc.getABCDMisc());
												
								irwinIncidentVos.add(vo);	
							}	
						//}
					}
				}
			}		
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SEARCH_IRWIN_INCIDENTS");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(irwinIncidentVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getIrwinIncident(DialogueVo dialogueVo, String irwinId, String flag) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			if(irwinId.length() != 36){
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("INVALID IRWIN ID");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.irwin"
												, "info.generic"
												, new String[]{"Invalid Irwin ID"}
												, MessageTypeEnum.CRITICAL));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coaVo);
				return dialogueVo;
			}
			String code = generateToken();
			IncidentModel inc;
			inc = getIncident(code, irwinId);
			
			if (inc != null) {
				IrwinIncidentVo vo = new IrwinIncidentVo();
				
				vo.setIrwinID(inc.getIrwinID());
				vo.setFireDiscoveryDateTime(inc.getFireDiscoveryDateTime());
				//private String POOResponsibleUnit;
				vo.setPooProtectingUnit(inc.getPOOProtectingUnit());
				vo.setLocalIncidentIdentifier(inc.getLocalIncidentIdentifier());
				vo.setIncidentName(inc.getIncidentName());
				vo.setIncidentTypeKind(inc.getIncidentTypeKind());
				vo.setIncidentTypeCategory(inc.getIncidentTypeCategory());
				vo.setFireCode(inc.getFireCode());
				vo.setFsJobCode(inc.getFSJobCode());
				vo.setFsOverrideCode(inc.getFSOverrideCode());
				vo.setIsActive(inc.getIsActive());
				vo.setRecordSource(inc.getRecordSource());
				vo.setCreatedBySystem(inc.getCreatedBySystem());
				vo.setCreatedOnDateTime(inc.getCreatedOnDateTime());
				vo.setModifiedBySystem(inc.getModifiedBySystem());
				vo.setModifiedOnDateTime(inc.getModifiedOnDateTime());
				vo.setModifiedOnDateTimeAsDate(inc.getModifiedOnDateTimeAsDate());
				vo.setCreatedOnDateTimeAsDate(inc.getCreatedOnDateTimeAsDate());
				vo.setFireDiscoveryDateTimeAsDate(inc.getFireDiscoveryDateTimeAsDate());
				
				vo.setUniqueFireIdentifier(inc.getUniqueFireIdentifier());
				vo.setIsComplex(inc.getIsComplex());
				vo.setComplexParentIrwinID(inc.getComplexParentIrwinID());
				
				vo.setAbcdMisc(inc.getABCDMisc());
				
				vo.setIsValid(inc.getIsValid());
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				//coaVo.setCoaName("POPULATEWITHIRWINDATA");
				coaVo.setCoaName(flag);
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coaVo);			
				dialogueVo.setResultObject(vo);
			}
			else {
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("INCIDENT NOTFOUND");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.irwin"
												, "info.generic"
												, new String[]{"Incident not found in IRWIN"}
												, MessageTypeEnum.CRITICAL));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coaVo);
				return dialogueVo;
				
			}
			

			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
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
		token = IRWINJSONParser.generateToken(response);	
		String code = token.getToken();
		
		return code;
	}
	
	private List<IncidentModel> getUpdates(String code, IrwinSearchFilterImpl searchFilter) throws Exception, TaskException, IrwinException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); 
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(searchFilter.getStartDate());
		Date startD = cal.getTime();
		String startDate = simpleDateFormat.format(startD);
		
		cal.add(Calendar.DATE,searchFilter.getDays().intValue());
		Date endD = cal.getTime();
		String endDate = simpleDateFormat.format(endD);		
		
		String active_or_valid = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_ISVALID); //0=active, 1=valid
		String uri = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GETUPDATES);
		RequestPackage p = new RequestPackage();
		p.setMethod("POST");
		p.setUri(uri);
		p.setParam("token", code);
		p.setParam("fromDateTime", startDate);
		p.setParam("toDateTime", endDate);
		p.setParam("f", "json");
		
		String response = HttpManager.getData(p);
		
		//System.out.println("response" + response);
		//testing only
		//response = response.replace("isActive", "isValid");
		
		return IRWINJSONParser.getUpdates(response, active_or_valid);
	}
	
	private IncidentModel getIncident(String code, String irwinId) throws Exception, TaskException, IrwinException {
		String uri = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GETINCIDENTS);
		String active_or_valid = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_ISVALID); //0=active, 1=valid
		String fields;
		
		if (active_or_valid.equals("0")) {
			fields = "IrwinID,FireDiscoveryDateTime,POOProtectingUnit,LocalIncidentIdentifier,IncidentName,IncidentTypeKind,IncidentTypeCategory,FireCode,FSJobCode,FSOverrideCode,IsActive,RecordSource,CreatedBySystem,CreatedOnDateTime,ModifiedBySystem,ModifiedOnDateTime,UniqueFireIdentifier,IsComplex,ComplexParentIrwinID,ABCDMisc";

		}
		else {
			//testing only
			//fields = "IrwinID,FireDiscoveryDateTime,POOProtectingUnit,LocalIncidentIdentifier,IncidentName,IncidentTypeKind,IncidentTypeCategory,FireCode,FSJobCode,FSOverrideCode,IsActive,RecordSource,CreatedBySystem,CreatedOnDateTime,ModifiedBySystem,ModifiedOnDateTime,UniqueFireIdentifier,IsComplex,ComplexParentIrwinID,ABCDMisc";

			fields = "IrwinID,FireDiscoveryDateTime,POOProtectingUnit,LocalIncidentIdentifier,IncidentName,IncidentTypeKind,IncidentTypeCategory,FireCode,FSJobCode,FSOverrideCode,IsValid,RecordSource,CreatedBySystem,CreatedOnDateTime,ModifiedBySystem,ModifiedOnDateTime,UniqueFireIdentifier,IsComplex,ComplexParentIrwinID,ABCDMisc";
		}

		RequestPackage p = new RequestPackage();
		p.setMethod("POST");
		p.setUri(uri);
		p.setParam("token", code);
		p.setParam("irwinIds", irwinId);
		p.setParam("fields", fields);
		p.setParam("f", "json");
		
		String response = HttpManager.getData(p);
		//System.out.println("response: " + response);
		
		//testing only
		//response = response.replace("isActive", "isValid");
		
		return IRWINJSONParser.getIncident(response, active_or_valid);
	}
	
//	public IncidentModel findMatches(String unitCode, String incidentNumber) throws ServiceException {
//		
//		IncidentModel retIncidentModel = null;
//
//		try{
//			SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
//			SystemParameter spToken = spDao.getByParameterName(SystemParameterTypeEnum.IRWIN_GENERATETOKEN.name());
//			SystemParameter spUsername = spDao.getByParameterName(SystemParameterTypeEnum.IRWIN_USERNAME.name());
//			SystemParameter spPassword = spDao.getByParameterName(SystemParameterTypeEnum.IRWIN_PASSWORD.name());
//			SystemParameter spDays = spDao.getByParameterName(SystemParameterTypeEnum.IRWIN_FROMDATETIME.name());
//			SystemParameter spURI = spDao.getByParameterName(SystemParameterTypeEnum.IRWIN_GETUPDATES.name());
//
//			TokenModel token;
//			//String uri = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GENERATETOKEN);
//			String uri = spToken.getParameterValue();
//			RequestPackage p = new RequestPackage();
//			p.setMethod("POST");
//			p.setUri(uri);
//			//p.setParam("username", super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_USERNAME));
//			//p.setParam("password", super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_PASSWORD));
//			p.setParam("username", spUsername.getParameterValue());
//			p.setParam("password", spPassword.getParameterValue());
//			p.setParam("client", "requestip");
//			p.setParam("f", "json");
//
//			String response = HttpManager.getData(p);
//			token = IRWINJSONParser.generateToken(response);	
//			String code = token.getToken();
//
//			//String daysBefore = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_FROMDATETIME);
//			String daysBefore = spDays.getParameterValue();
//
//			Boolean isNumber;
//
//			try {
//
//				Integer.parseInt(daysBefore);
//
//				isNumber = true;
//
//			} catch (NumberFormatException ne) {
//
//				isNumber = false;
//
//			}
//
//			if (!isNumber) {
//				daysBefore = "30";
//			}
//
//			IrwinSearchFilterImpl searchFilter = new IrwinSearchFilterImpl();
//			//searchFilter.setStartDate(incidentVo.getIncidentStartDate());
//
//			searchFilter.setUnitId(unitCode.replaceAll("-", ""));
//			searchFilter.setNumber(incidentNumber);
//
//			searchFilter.setDays(Long.valueOf(daysBefore));
//			Calendar cal = Calendar.getInstance();
//			//cal.setTime(searchFilter.getStartDate());
//
//			cal.add(Calendar.DATE,-(searchFilter.getDays().intValue()));
//			Date dateBefore= cal.getTime();
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); 
//			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//			String startDate = simpleDateFormat.format(dateBefore);
//
//			//uri = super.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GETUPDATES);
//			uri = spURI.getParameterValue();
//			
//			p = new RequestPackage();
//			p.setMethod("POST");
//			p.setUri(uri);
//			p.setParam("token", code);
//
//			p.setParam("fromDateTime", startDate); 
//			p.setParam("f", "json");
//
//			response = HttpManager.getData(p);
//
//			List<IncidentModel> irwinIncidentList = IRWINJSONParser.getUpdates(response);
//
//			for (IncidentModel inc: irwinIncidentList) {
//				if (inc.getIsActive().equalsIgnoreCase("true")) {
//					if (inc.getIncidentTypeCategory().equalsIgnoreCase("WF")) {
//						if (inc.getPOOProtectingUnit().equalsIgnoreCase(searchFilter.getUnitId()) && inc.getLocalIncidentIdentifier().equalsIgnoreCase(searchFilter.getNumber())) {
//							retIncidentModel = new IncidentModel();
//							retIncidentModel.setIrwinID(inc.getIrwinID());
//							retIncidentModel.setABCDMisc(inc.getABCDMisc());
//							retIncidentModel.setComplexParentIrwinID(inc.getComplexParentIrwinID());
//							retIncidentModel.setCreatedBySystem(inc.getCreatedBySystem());
//							retIncidentModel.setCreatedOnDateTime(inc.getCreatedOnDateTime());
//							retIncidentModel.setFireCode(inc.getFireCode());
//							retIncidentModel.setFireDiscoveryDateTime(inc.getFireDiscoveryDateTime());
//							retIncidentModel.setFSJobCode(inc.getFSJobCode());
//							retIncidentModel.setFSOverrideCode(inc.getFSOverrideCode());
//							retIncidentModel.setIncidentName(inc.getIncidentName());
//							retIncidentModel.setIncidentTypeCategory(inc.getIncidentTypeCategory());
//							retIncidentModel.setIncidentTypeKind(inc.getIncidentTypeKind());
//							retIncidentModel.setIsActive(inc.getIsActive());
//							retIncidentModel.setIsComplex(inc.getIsComplex());
//							retIncidentModel.setLocalIncidentIdentifier(inc.getLocalIncidentIdentifier());
//							retIncidentModel.setModifiedBySystem(inc.getModifiedBySystem());
//							retIncidentModel.setModifiedOnDateTime(inc.getModifiedOnDateTime());
//							retIncidentModel.setPOOProtectingUnit(inc.getPOOProtectingUnit());
//							retIncidentModel.setUniqueFireIdentifier(inc.getUniqueFireIdentifier());
//							break;
//						}
//					}
//				}
//			}	
//
//		} catch(Exception smother){
//			System.out.println(smother.getMessage());
//		}
//
//
//		return retIncidentModel;
//	
//	}	
	
}	
