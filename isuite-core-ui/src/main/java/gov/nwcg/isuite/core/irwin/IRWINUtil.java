package gov.nwcg.isuite.core.irwin;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.filter.impl.IrwinSearchFilterImpl;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class IRWINUtil {
	
	private String runMode;
	private SystemParameterDao dao;
	
	public IRWINUtil(String runMode, SystemParameterDao dao) {
		this.runMode = runMode;
		this.dao = dao;
		
	}
	
	/**
	 * Returns the system parameter value if found.
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	protected String getSystemParamValue(SystemParameterTypeEnum paramName) throws Exception {
		
		//SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
		
		SystemParameter entity = dao.getByParameterName(paramName.name());
		
		//if(null == entity)
			//this.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemParameter["+paramName.name()+"]");
		
		return entity.getParameterValue();
	}
	
	
	public void getIRWINStatus(IncidentVo vo, Incident entity) {		
		try {
			if (this.runMode.equalsIgnoreCase("ENTERPRISE")) {
				String irwinFlag = this.getSystemParamValue(SystemParameterTypeEnum.IRWIN_MODE);
				if (!irwinFlag.equals("0") && entity.getIrwinIrwinId() != null) {

					TokenModel token;
					String uri = this.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GENERATETOKEN);
					RequestPackage p = new RequestPackage();
					p.setMethod("POST");
					p.setUri(uri);
					p.setParam("username", this.getSystemParamValue(SystemParameterTypeEnum.IRWIN_USERNAME));
					p.setParam("password", this.getSystemParamValue(SystemParameterTypeEnum.IRWIN_PASSWORD));
					p.setParam("client", "requestip");
					p.setParam("f", "json");

					String response = HttpManager.getData(p);
					token = IRWINJSONParser.generateToken(response);	
					String code = token.getToken();

					IncidentModel inc;

					uri = this.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GETINCIDENTS);
					
					String active_or_valid = this.getSystemParamValue(SystemParameterTypeEnum.IRWIN_ISVALID); //0=active, 1=valid
					String fields;
					
					if (active_or_valid.equals("0")) {
						fields = "IrwinID,FireDiscoveryDateTime,POOProtectingUnit,LocalIncidentIdentifier,IncidentName,IncidentTypeKind,IncidentTypeCategory,FireCode,FSJobCode,FSOverrideCode,IsActive,RecordSource,CreatedBySystem,CreatedOnDateTime,ModifiedBySystem,ModifiedOnDateTime,UniqueFireIdentifier,IsComplex,ComplexParentIrwinID,ABCDMisc";

					}
					else {
						//testing only
						//fields = "IrwinID,FireDiscoveryDateTime,POOProtectingUnit,LocalIncidentIdentifier,IncidentName,IncidentTypeKind,IncidentTypeCategory,FireCode,FSJobCode,FSOverrideCode,IsActive,RecordSource,CreatedBySystem,CreatedOnDateTime,ModifiedBySystem,ModifiedOnDateTime,UniqueFireIdentifier,IsComplex,ComplexParentIrwinID,ABCDMisc";

						fields = "IrwinID,FireDiscoveryDateTime,POOProtectingUnit,LocalIncidentIdentifier,IncidentName,IncidentTypeKind,IncidentTypeCategory,FireCode,FSJobCode,FSOverrideCode,IsValid,RecordSource,CreatedBySystem,CreatedOnDateTime,ModifiedBySystem,ModifiedOnDateTime,UniqueFireIdentifier,IsComplex,ComplexParentIrwinID,ABCDMisc";
					}
					
					p = new RequestPackage();
					p.setMethod("POST");
					p.setUri(uri);
					p.setParam("token", code);
					p.setParam("irwinIds", entity.getIrwinIrwinId());
					p.setParam("fields", fields);
					p.setParam("f", "json");

					response = HttpManager.getData(p);
					
					//testing only
					//response = response.replace("isActive", "isValid");

					inc =  IRWINJSONParser.getIncident(response, active_or_valid);

					String irwinStatus = "SYNC";

					if (inc == null) {
						irwinStatus = "This incident cannot be found in IRWIN. \nThis will not affect your ability to manage this incident in e-ISuite."; //"NOTFOUND";
					}
					else {
						if (inc.getIsValid().equals("false")) {	
							irwinStatus = "This incident has been deactivated in IRWIN. \nThis will not affect your ability to manage this incident in e-ISuite."; //"INACTIVE";
						}
						else {
							if (vo.getIrwinModifiedOnDateTime() != null) {
								if (!vo.getIrwinModifiedOnDateTime().equalsIgnoreCase(inc.getModifiedOnDateTime())) {
									irwinStatus = "This incident has been updated in IRWIN. \nSelect Sync IRWIN Data to review updates."; //"UPDATE";
								}
							}
							else {
								irwinStatus = "This incident has been updated in IRWIN. \nSelect Sync IRWIN Data to review updates."; //"UPDATE";
							}
						}
					}
					vo.setIrwinStatus(irwinStatus);

				}

			}		

		}catch(Exception smother) {}
	}
	
	
	
	public void findMatches(IncidentVo incidentVo, String unitCode, String incidentNumber){
	
		// Find IRWIN ID begin:

		try{

			if (this.runMode.equalsIgnoreCase("ENTERPRISE")) {
				String irwinFlag = this.getSystemParamValue(SystemParameterTypeEnum.IRWIN_MODE);
				if (!irwinFlag.equals("0") && incidentVo.getIrwinIrwinId() == null) {


					TokenModel token;
					String uri = this.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GENERATETOKEN);
					RequestPackage p = new RequestPackage();
					p.setMethod("POST");
					p.setUri(uri);
					p.setParam("username", this.getSystemParamValue(SystemParameterTypeEnum.IRWIN_USERNAME));
					p.setParam("password", this.getSystemParamValue(SystemParameterTypeEnum.IRWIN_PASSWORD));
					p.setParam("client", "requestip");
					p.setParam("f", "json");

					String response = HttpManager.getData(p);
					token = IRWINJSONParser.generateToken(response);	
					String code = token.getToken();

					String daysBefore = this.getSystemParamValue(SystemParameterTypeEnum.IRWIN_FINDMATCH_THROTTLE);

					Boolean isNumber;

					try {

						Integer.parseInt(daysBefore);

						isNumber = true;

					} catch (NumberFormatException ne) {

						isNumber = false;

					}

					if (!isNumber) {
						daysBefore = "30";
					}

					IrwinSearchFilterImpl searchFilter = new IrwinSearchFilterImpl();
					//searchFilter.setStartDate(incidentVo.getIncidentStartDate());

					searchFilter.setUnitId(unitCode.replaceAll("-", ""));
					searchFilter.setNumber(incidentNumber);

					searchFilter.setDays(Long.valueOf(daysBefore));
					Calendar cal = Calendar.getInstance();
					
					cal.add(Calendar.DATE,-(searchFilter.getDays().intValue()));
					Date dateBefore= cal.getTime();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); 
					simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
					String startDate = simpleDateFormat.format(dateBefore);

					uri = this.getSystemParamValue(SystemParameterTypeEnum.IRWIN_GETUPDATES);
					p = new RequestPackage();
					p.setMethod("POST");
					p.setUri(uri);
					p.setParam("token", code);
					p.setParam("pooProtectingUnit", searchFilter.getUnitId());
					p.setParam("fromDateTime", startDate); 
					p.setParam("f", "json");

					response = HttpManager.getData(p);
					
					//testing only
					//response = response.replace("isActive", "isValid");
					
					String active_or_valid = this.getSystemParamValue(SystemParameterTypeEnum.IRWIN_ISVALID); //0=active, 1=valid

					List<IncidentModel> irwinIncidentList = IRWINJSONParser.getUpdates(response, active_or_valid);

					for (IncidentModel inc: irwinIncidentList) {
						if (inc.getIsValid().equalsIgnoreCase("true")) {
							if (inc.getIncidentTypeCategory().equalsIgnoreCase("WF")) {
								if (inc.getPOOProtectingUnit().equalsIgnoreCase(searchFilter.getUnitId()) && inc.getLocalIncidentIdentifier().equalsIgnoreCase(searchFilter.getNumber())) {							
									incidentVo.setIrwinIrwinId(inc.getIrwinID());
									incidentVo.setIrwinAbcdMisc(inc.getABCDMisc());
									incidentVo.setIrwinComplexParentIrwinId(inc.getComplexParentIrwinID());
									incidentVo.setIrwinCreatedBySystem(inc.getCreatedBySystem());
									incidentVo.setIrwinCreatedOnDateTime(inc.getCreatedOnDateTime());
									incidentVo.setIrwinFireCode(inc.getFireCode());
									incidentVo.setIrwinFireDiscoveryDateTime(inc.getFireDiscoveryDateTime());
									incidentVo.setIrwinFsJobCode(inc.getFSJobCode());
									incidentVo.setIrwinFsOverrideCode(inc.getFSOverrideCode());
									incidentVo.setIrwinIncidentName(inc.getIncidentName());
									incidentVo.setIrwinIncidentTypeCategory(inc.getIncidentTypeCategory());
									incidentVo.setIrwinIncidentTypeKind(inc.getIncidentTypeKind());
									incidentVo.setIrwinIsActive(inc.getIsActive());
									incidentVo.setIrwinIsComplex(inc.getIsComplex());
									incidentVo.setIrwinLocalIncidentIdentifier(inc.getLocalIncidentIdentifier());
									incidentVo.setIrwinModifiedBySystem(inc.getModifiedBySystem());
									incidentVo.setIrwinModifiedOnDateTime(inc.getModifiedOnDateTime());
									incidentVo.setIrwinPooProtectingUnit(inc.getPOOProtectingUnit());
									incidentVo.setIrwinUniqueFireIdentifier(inc.getUniqueFireIdentifier());
									incidentVo.setIrwinIsValid(inc.getIsValid());
									
									break;
								}
							}
						}
					}	
				}
			}
		}catch(Exception smother){}
	}

}



