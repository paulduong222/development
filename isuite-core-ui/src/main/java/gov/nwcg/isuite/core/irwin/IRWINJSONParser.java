package gov.nwcg.isuite.core.irwin;

import gov.nwcg.isuite.core.filter.impl.IrwinSearchFilterImpl;
import gov.nwcg.isuite.framework.exceptions.IrwinException;
import gov.nwcg.isuite.framework.exceptions.TaskException;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IRWINJSONParser {
	
	public static List<IncidentModel> getUpdates(String content, String active_or_valid) throws TaskException, IrwinException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"); //"yyyy-MM-dd'T'HH:mm:ss.SSSZ"
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		Date date;
		List<IncidentModel> incidentList = new ArrayList<IncidentModel>();
				
		try {
			JSONObject objMain = new JSONObject(content);
					
			JSONArray ar = objMain.getJSONArray("incidents");
						
			for (int i = 0; i < ar.length(); i++) {
				
				JSONObject obj = ar.getJSONObject(i);
								
				JSONObject attr = obj.getJSONObject("attributes");
				
				IncidentModel incident = new IncidentModel();
								
				incident.setCreatedBySystem(attr.optString("createdBySystem"));
				incident.setCreatedOnDateTime(attr.optString("createdOnDateTime"));
				//incident.setCreatedOnDateTime("2015-01-26T02:25:45.110Z");
				
				incident.setFireCode(attr.optString("fireCode"));
				incident.setFireDiscoveryDateTime(attr.optString("fireDiscoveryDateTime"));
				incident.setFSJobCode(attr.optString("fsJobCode"));
				incident.setFSOverrideCode(attr.optString("fsOverrideCode"));
				incident.setIncidentTypeCategory(attr.optString("incidentTypeCategory"));
				incident.setIncidentTypeKind(attr.optString("incidentTypeKind"));
				incident.setIrwinID(attr.optString("irwinID"));
				
				if (active_or_valid.equals("0")) {
					incident.setIsActive(attr.optString("isActive"));
					incident.setIsValid(attr.optString("isActive"));
				}
				else {
					incident.setIsValid(attr.optString("isValid"));
				}
								
				incident.setLocalIncidentIdentifier(attr.optString("localIncidentIdentifier"));
				incident.setModifiedBySystem(attr.optString("modifiedBySystem"));
				//incident.setPOOResponsibleUnit(attr.optString("POOResponsibleUnit"));
				incident.setPOOProtectingUnit(attr.optString("pooProtectingUnit"));
				incident.setRecordSource(attr.optString("recordSource"));
				incident.setIncidentName(attr.optString("incidentName"));
				incident.setModifiedOnDateTime(attr.optString("modifiedOnDateTime"));
																
				if(StringUtility.hasValue(incident.getFireDiscoveryDateTime()))
					incident.setFireDiscoveryDateTimeAsDate(simpleDateFormat.parse(incident.getFireDiscoveryDateTime().replace("Z", "")));	
				
				if(StringUtility.hasValue(incident.getCreatedOnDateTime()))
					incident.setCreatedOnDateTimeAsDate(simpleDateFormat.parse(incident.getCreatedOnDateTime().replace("Z", "")));	
				
				if(StringUtility.hasValue(incident.getModifiedOnDateTime()))
					incident.setModifiedOnDateTimeAsDate(simpleDateFormat.parse(incident.getModifiedOnDateTime().replace("Z", "")));	
				
				incident.setUniqueFireIdentifier(attr.optString("uniqueFireIdentifier"));
				incident.setIsComplex(attr.optString("isComplex"));
				incident.setComplexParentIrwinID(attr.optString("complexParentIrwinId"));
				
				incident.setABCDMisc(attr.optString("abcdMisc"));
				
								
//				incident.setCreatedBySystem(attr.optString("CreatedBySystem"));
//				incident.setCreatedOnDateTime(attr.optString("CreatedOnDateTime"));
//				incident.setFireCode(attr.optString("FireCode"));
//				incident.setFireDiscoveryDateTime(attr.optString("FireDiscoveryDateTime"));
//				incident.setFSJobCode(attr.optString("FSJobCode"));
//				incident.setFSOverrideCode(attr.optString("FSOverrideCode"));
//				incident.setIncidentTypeCategory(attr.optString("IncidentTypeCategory"));
//				incident.setIncidentTypeKind(attr.optString("IncidentTypeKind"));
//				incident.setIrwinID(attr.optString("IrwinID"));
//				//incident.setIrwinID(attr.optString("IrwinId"));
//				incident.setIsActive(attr.optString("IsActive"));
//				incident.setLocalIncidentIdentifier(attr.optString("LocalIncidentIdentifier"));
//				incident.setModifiedBySystem(attr.optString("ModifiedBySystem"));
//				//incident.setPOOResponsibleUnit(attr.optString("POOResponsibleUnit"));
//				incident.setPOOProtectingUnit(attr.optString("POOProtectingUnit"));
//				incident.setRecordSource(attr.optString("RecordSource"));
//				incident.setIncidentName(attr.optString("IncidentName"));
//				incident.setModifiedOnDateTime(attr.optString("ModifiedOnDateTime"));
//																
//				incident.setFireDiscoveryDateTimeAsDate(simpleDateFormat.parse(incident.getFireDiscoveryDateTime().replace("Z", "")));	
//				incident.setCreatedOnDateTimeAsDate(simpleDateFormat.parse(incident.getCreatedOnDateTime().replace("Z", "")));	
//				incident.setModifiedOnDateTimeAsDate(simpleDateFormat.parse(incident.getModifiedOnDateTime().replace("Z", "")));	
//				
//				incident.setUniqueFireIdentifier(attr.optString("UniqueFireIdentifier"));
//				incident.setIsComplex(attr.optString("IsComplex"));
//				incident.setComplexParentIrwinID(attr.optString("ComplexIrwinParentId")); //ComplexIrwinParentId, ComplexParentIrwinID, complexParentIrwinId?
				
				incidentList.add(incident);
			}
			Collections.sort(incidentList);  //sort
//			Iterator<IncidentModel> it = incidentList.iterator();
//			while(it.hasNext())
//			{
//				IncidentModel inc = it.next();
//				System.out.println("IRWIN ID: " + inc.getIrwinID());
//				System.out.println("IncidentName: " + inc.getIncidentName());
//				System.out.println("ModifiedOnDateTime: " + inc.getModifiedOnDateTime());
//			}
			
			return incidentList; //incidentList.get(incidentList.size()-1); //incidentList.get(0); //incidentList.get(incidentList.size()-1);
		} catch (JSONException e) {
			//e.printStackTrace();
			//return null;
			try {
				JSONObject obj = new JSONObject(content);
				JSONObject errorObj = obj.getJSONObject("error");
				ErrorModel error = new ErrorModel();
				error.setCode(errorObj.getLong("code"));
				error.setMessage(errorObj.getString("message"));
				throw new IrwinException("getUpdates - error code: " + error.getCode().toString() + " message: " + error.getMessage() + " response: " + content);
			}
			catch (JSONException ex) {
				throw new TaskException("getUpdates - json parsing error" + " response: " + content);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();  This is for the date parsing
			throw new TaskException("getUpdates - date parsing error: " + e.getMessage());
		}
		//return null;
	}
	
	public static IncidentModel getIncident(String content, String active_or_valid) throws TaskException, IrwinException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		try {
			JSONObject objMain = new JSONObject(content);
			
			//JSONArray ar = new JSONArray(content);
			
			JSONArray ar = objMain.getJSONArray("incidents");
			
			if (ar.length() > 0) {
				JSONObject obj = ar.getJSONObject(0);
				JSONObject attr = obj.getJSONObject("attributes");
				
				IncidentModel incident = new IncidentModel();
				
				incident.setCreatedBySystem(attr.optString("createdBySystem"));
				incident.setCreatedOnDateTime(attr.optString("createdOnDateTime"));
				//incident.setCreatedOnDateTime("2015-01-26T02:25:45.110Z");
				
				incident.setFireCode(attr.optString("fireCode"));
				incident.setFireDiscoveryDateTime(attr.optString("fireDiscoveryDateTime"));
				incident.setFSJobCode(attr.optString("fsJobCode"));
				incident.setFSOverrideCode(attr.optString("fsOverrideCode"));
				incident.setIncidentTypeCategory(attr.optString("incidentTypeCategory"));
				incident.setIncidentTypeKind(attr.optString("incidentTypeKind"));
				incident.setIrwinID(attr.optString("irwinID"));
				if (active_or_valid.equals("0")) {
					incident.setIsActive(attr.optString("isActive"));
					incident.setIsValid(attr.optString("isActive"));
				}
				else {
					incident.setIsValid(attr.optString("isValid"));
				}
				
				incident.setLocalIncidentIdentifier(attr.optString("localIncidentIdentifier"));
				incident.setModifiedBySystem(attr.optString("modifiedBySystem"));
				//incident.setPOOResponsibleUnit(attr.optString("POOResponsibleUnit"));
				incident.setPOOProtectingUnit(attr.optString("pooProtectingUnit"));
				incident.setRecordSource(attr.optString("recordSource"));
				incident.setIncidentName(attr.optString("incidentName"));
				incident.setModifiedOnDateTime(attr.optString("modifiedOnDateTime"));
							
				incident.setFireDiscoveryDateTimeAsDate(simpleDateFormat.parse(incident.getFireDiscoveryDateTime().replace("Z", "")));	
				incident.setCreatedOnDateTimeAsDate(simpleDateFormat.parse(incident.getCreatedOnDateTime().replace("Z", "")));	
				incident.setModifiedOnDateTimeAsDate(simpleDateFormat.parse(incident.getModifiedOnDateTime().replace("Z", "")));	
				
				incident.setUniqueFireIdentifier(attr.optString("uniqueFireIdentifier"));
				incident.setIsComplex(attr.optString("isComplex"));
				incident.setComplexParentIrwinID(attr.optString("complexParentIrwinId"));
				
				incident.setABCDMisc(attr.optString("abcdMisc"));
				
//				incident.setCreatedBySystem(attr.optString("CreatedBySystem"));
//				incident.setCreatedOnDateTime(attr.optString("CreatedOnDateTime"));
//				incident.setFireCode(attr.optString("FireCode"));
//				incident.setFireDiscoveryDateTime(attr.optString("FireDiscoveryDateTime"));
//				incident.setFSJobCode(attr.optString("FSJobCode"));
//				incident.setFSOverrideCode(attr.optString("FSOverrideCode"));
//				incident.setIncidentTypeCategory(attr.optString("IncidentTypeCategory"));
//				incident.setIncidentTypeKind(attr.optString("IncidentTypeKind"));
//				incident.setIrwinID(attr.optString("IrwinID"));
//				//incident.setIrwinID(attr.optString("IrwinId"));
//				incident.setIsActive(attr.optString("IsActive"));
//				incident.setLocalIncidentIdentifier(attr.optString("LocalIncidentIdentifier"));
//				incident.setModifiedBySystem(attr.optString("ModifiedBySystem"));
//				//incident.setPOOResponsibleUnit(attr.optString("POOResponsibleUnit"));
//				incident.setPOOProtectingUnit(attr.optString("POOProtectingUnit"));
//				incident.setRecordSource(attr.optString("RecordSource"));
//				incident.setIncidentName(attr.optString("IncidentName"));
//				incident.setModifiedOnDateTime(attr.optString("ModifiedOnDateTime"));
//																
//				incident.setFireDiscoveryDateTimeAsDate(simpleDateFormat.parse(incident.getFireDiscoveryDateTime().replace("Z", "")));	
//				incident.setCreatedOnDateTimeAsDate(simpleDateFormat.parse(incident.getCreatedOnDateTime().replace("Z", "")));	
//				incident.setModifiedOnDateTimeAsDate(simpleDateFormat.parse(incident.getModifiedOnDateTime().replace("Z", "")));	
//				
//				incident.setUniqueFireIdentifier(attr.optString("UniqueFireIdentifier"));
//				incident.setIsComplex(attr.optString("IsComplex"));
//				incident.setComplexParentIrwinID(attr.optString("ComplexIrwinParentId"));
				
				
				//List<TokenModel> tokenList = new ArrayList<TokenModel>();
				
//				for (int i = 0; i < ar.length(); i++) {
//					
//					JSONObject obj = ar.getJSONObject(i);
//					System.out.println("irwinID: " + obj.getString("irwinID"));
//					
//					JSONObject attr = obj.getJSONObject("attributes");
//					System.out.println("IncidentName: " + attr.getString("IncidentName"));
//					System.out.println("CreatedOnDateTime: " + attr.getString("CreatedOnDateTime"));
//					
//					
//					JSONObject origin = obj.getJSONObject("pointOfOrigin");
//					System.out.println("x: " + origin.getDouble("x"));
//					System.out.println("y: " + origin.getDouble("y"));
//					
//					//TokenModel token = new TokenModel();
//					
//					//token.setToken(obj.getString("token"));
//					//token.setExpires(obj.getLong("expires"));
//									
//					//tokenList.add(token);
//				}
				return incident;
			}
			else {
				return null;
			}

			
		} catch (JSONException e) {
			try {
				JSONObject obj = new JSONObject(content);
				JSONObject errorObj = obj.getJSONObject("error");
				ErrorModel error = new ErrorModel();
				error.setCode(errorObj.getLong("code"));
				error.setMessage(errorObj.getString("message"));
				throw new IrwinException("getIncident - error code: " + error.getCode().toString() + " message: " + error.getMessage() + " response: " + content);
			}
			catch (JSONException ex) {
				try {
					JSONObject obj = new JSONObject(content);
					JSONObject errorObj = obj.getJSONObject("error");
					ErrorModel error = new ErrorModel();
					error.setCode(errorObj.getLong("code"));
					error.setMessage(errorObj.getString("description"));
					throw new IrwinException("getIncident - error code: " + error.getCode().toString() + " message: " + error.getMessage() + " response: " + content);
				}
				catch (JSONException ex2) {
					throw new TaskException("getIncident - json parsing error");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();  This is for the date parsing
			throw new TaskException("getIncident - date parsing error " + e.getMessage());
		}
	}
	
	//not used
	public static void getIncidents(String content) throws TaskException, IrwinException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		try {
			JSONObject objMain = new JSONObject(content);
			
			//JSONArray ar = new JSONArray(content);
			
			JSONArray ar = objMain.getJSONArray("incidents");
			JSONObject obj = ar.getJSONObject(0);
			JSONObject attr = obj.getJSONObject("attributes");
			
			IncidentModel incident = new IncidentModel();
			
			incident.setCreatedBySystem(attr.optString("createdBySystem"));
			incident.setCreatedOnDateTime(attr.optString("createdOnDateTime"));
			//incident.setCreatedOnDateTime("2015-01-26T02:25:45.110Z");
			
			incident.setFireCode(attr.optString("fireCode"));
			incident.setFireDiscoveryDateTime(attr.optString("fireDiscoveryDateTime"));
			incident.setFSJobCode(attr.optString("fsJobCode"));
			incident.setFSOverrideCode(attr.optString("fsOverrideCode"));
			incident.setIncidentTypeCategory(attr.optString("incidentTypeCategory"));
			incident.setIncidentTypeKind(attr.optString("incidentTypeKind"));
			incident.setIrwinID(attr.optString("irwinID"));
			incident.setIsActive(attr.optString("isActive"));
			incident.setLocalIncidentIdentifier(attr.optString("localIncidentIdentifier"));
			incident.setModifiedBySystem(attr.optString("modifiedBySystem"));
			//incident.setPOOResponsibleUnit(attr.optString("POOResponsibleUnit"));
			incident.setPOOProtectingUnit(attr.optString("pooProtectingUnit"));
			incident.setRecordSource(attr.optString("recordSource"));
			incident.setIncidentName(attr.optString("incidentName"));
			incident.setModifiedOnDateTime(attr.optString("modifiedOnDateTime"));
						
			incident.setFireDiscoveryDateTimeAsDate(simpleDateFormat.parse(incident.getFireDiscoveryDateTime().replace("Z", "")));	
			incident.setCreatedOnDateTimeAsDate(simpleDateFormat.parse(incident.getCreatedOnDateTime().replace("Z", "")));	
			incident.setModifiedOnDateTimeAsDate(simpleDateFormat.parse(incident.getModifiedOnDateTime().replace("Z", "")));	
			
			incident.setUniqueFireIdentifier(attr.optString("uniqueFireIdentifier"));
			incident.setIsComplex(attr.optString("isComplex"));
			incident.setComplexParentIrwinID(attr.optString("complexParentIrwinId"));
			
			incident.setABCDMisc(attr.optString("abcdMisc"));
			
			//incident.setIsValid(attr.optString("isValid"));
			incident.setIsValid(attr.optString("isActive"));
			
//			incident.setCreatedBySystem(attr.optString("CreatedBySystem"));
//			incident.setCreatedOnDateTime(attr.optString("CreatedOnDateTime"));
//			incident.setFireCode(attr.optString("FireCode"));
//			incident.setFireDiscoveryDateTime(attr.optString("FireDiscoveryDateTime"));
//			incident.setFSJobCode(attr.optString("FSJobCode"));
//			incident.setFSOverrideCode(attr.optString("FSOverrideCode"));
//			incident.setIncidentTypeCategory(attr.optString("IncidentTypeCategory"));
//			incident.setIncidentTypeKind(attr.optString("IncidentTypeKind"));
//			incident.setIrwinID(attr.optString("IrwinID"));
//			//incident.setIrwinID(attr.optString("IrwinId"));
//			incident.setIsActive(attr.optString("IsActive"));
//			incident.setLocalIncidentIdentifier(attr.optString("LocalIncidentIdentifier"));
//			incident.setModifiedBySystem(attr.optString("ModifiedBySystem"));
//			//incident.setPOOResponsibleUnit(attr.optString("POOResponsibleUnit"));
//			incident.setPOOProtectingUnit(attr.optString("POOProtectingUnit"));
//			incident.setRecordSource(attr.optString("RecordSource"));
//			incident.setIncidentName(attr.optString("IncidentName"));
//			incident.setModifiedOnDateTime(attr.optString("ModifiedOnDateTime"));
//															
//			incident.setFireDiscoveryDateTimeAsDate(simpleDateFormat.parse(incident.getFireDiscoveryDateTime().replace("Z", "")));	
//			incident.setCreatedOnDateTimeAsDate(simpleDateFormat.parse(incident.getCreatedOnDateTime().replace("Z", "")));	
//			incident.setModifiedOnDateTimeAsDate(simpleDateFormat.parse(incident.getModifiedOnDateTime().replace("Z", "")));	
//			
//			incident.setUniqueFireIdentifier(attr.optString("UniqueFireIdentifier"));
//			incident.setIsComplex(attr.optString("IsComplex"));
//			incident.setComplexParentIrwinID(attr.optString("ComplexIrwinParentId"));
			
			//List<TokenModel> tokenList = new ArrayList<TokenModel>();
			
//			for (int i = 0; i < ar.length(); i++) {
//				
//				JSONObject obj = ar.getJSONObject(i);
//				System.out.println("irwinID: " + obj.getString("irwinID"));
//				
//				JSONObject attr = obj.getJSONObject("attributes");
//				System.out.println("IncidentName: " + attr.getString("IncidentName"));
//				System.out.println("CreatedOnDateTime: " + attr.getString("CreatedOnDateTime"));
//				
//				
//				JSONObject origin = obj.getJSONObject("pointOfOrigin");
//				System.out.println("x: " + origin.getDouble("x"));
//				System.out.println("y: " + origin.getDouble("y"));
//				
//				//TokenModel token = new TokenModel();
//				
//				//token.setToken(obj.getString("token"));
//				//token.setExpires(obj.getLong("expires"));
//								
//				//tokenList.add(token);
//			}
			//return incident;
			
		} catch (JSONException e) {
			try {
				JSONObject obj = new JSONObject(content);
				JSONObject errorObj = obj.getJSONObject("error");
				ErrorModel error = new ErrorModel();
				error.setCode(errorObj.getLong("code"));
				error.setMessage(errorObj.getString("message"));
				throw new IrwinException("getIncidents - error code: " + error.getCode().toString() + " message: " + error.getMessage() + " response: " + content);
			}
			catch (JSONException ex) {
				try {
					JSONObject obj = new JSONObject(content);
					JSONObject errorObj = obj.getJSONObject("error");
					ErrorModel error = new ErrorModel();
					error.setCode(errorObj.getLong("code"));
					error.setMessage(errorObj.getString("description"));
					throw new IrwinException("getIncidents - error code: " + error.getCode().toString() + " message: " + error.getMessage() + " response: " + content);
				}
				catch (JSONException ex2) {
					throw new TaskException("getIncidents - json parsing error");
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();  This is for the date parsing
			throw new TaskException("getIncidents - date parsing error " + e.getMessage());
		}
	}
	
	public static String submitIncident(String content) throws TaskException, IrwinException {
		try {
			System.out.println("from submitIncident - " + content);
			JSONObject obj = new JSONObject(content);
			//TokenModel token = new TokenModel();
			//token.setToken(obj.getString("token"));
			//token.setExpires(obj.getLong("expires"));
							
			return obj.getString("irwinID");
		} catch (JSONException e) {
			try {
				JSONObject obj = new JSONObject(content);
				JSONObject errorObj = obj.getJSONObject("error");
				ErrorModel error = new ErrorModel();
				error.setCode(errorObj.getLong("code"));
				error.setMessage(errorObj.getString("message"));
				throw new IrwinException("submitIncident - error code: " + error.getCode().toString() + " message: " + error.getMessage() + " response: " + content);
			}
			catch (JSONException ex) {
				try {
					JSONObject obj = new JSONObject(content);
					JSONObject errorObj = obj.getJSONObject("error");
					ErrorModel error = new ErrorModel();
					error.setCode(errorObj.getLong("code"));
					error.setMessage(errorObj.getString("description"));
					throw new IrwinException("submitIncident - error code: " + error.getCode().toString() + " message: " + error.getMessage() + " response: " + content);
				}
				catch (JSONException ex2) {
					throw new TaskException("submitIncident - json parsing error");
				}
			}
		}
	}
	
	public static String updateIncident(String content) throws TaskException, IrwinException {
		try {
			System.out.println("from updateIncident - " + content);
			JSONObject obj = new JSONObject(content);
			//TokenModel token = new TokenModel();
			//token.setToken(obj.getString("token"));
			//token.setExpires(obj.getLong("expires"));
							
			return obj.getString("irwinID");
		} catch (JSONException e) {
			try {
				JSONObject obj = new JSONObject(content);
				JSONObject errorObj = obj.getJSONObject("error");
				ErrorModel error = new ErrorModel();
				error.setCode(errorObj.getLong("code"));
				error.setMessage(errorObj.getString("message"));
				throw new IrwinException("updateIncident - error code: " + error.getCode().toString() + " message: " + error.getMessage() + " response: " + content);
			}
			catch (JSONException ex) {
				try {
					JSONObject obj = new JSONObject(content);
					JSONObject errorObj = obj.getJSONObject("error");
					ErrorModel error = new ErrorModel();
					error.setCode(errorObj.getLong("code"));
					error.setMessage(errorObj.getString("description"));
					throw new IrwinException("updateIncident - error code: " + error.getCode().toString() + " message: " + error.getMessage() + " response: " + content);
				}
				catch (JSONException ex2) {
					throw new TaskException("updateIncident - json parsing error");
				}
			}
		}
	}
		
	public static TokenModel generateToken(String content) throws TaskException, IrwinException {
			try {
				JSONObject obj = new JSONObject(content);
				TokenModel token = new TokenModel();
				token.setToken(obj.getString("token"));
				token.setExpires(obj.getLong("expires"));
								
				return token;
			} catch (JSONException e) {
				//e.printStackTrace();
				//return null;
				try {
					JSONObject obj = new JSONObject(content);
					JSONObject errorObj = obj.getJSONObject("error");
					ErrorModel error = new ErrorModel();
					error.setCode(errorObj.getLong("code"));
					error.setMessage(errorObj.getString("message"));
					throw new IrwinException("generateToken - error code: " + error.getCode().toString() + " message: " + error.getMessage() + " response: " + content);
				}
				catch (JSONException ex) {
					throw new TaskException("generateToken - json parsing error" + " response: " + content);
				}
			}
	}
		
	public static Boolean matches(IrwinSearchFilterImpl searchFilter, IncidentModel inc) {
		Boolean matchIncidentName = true;
		Boolean matchIncidentState = true;
		Boolean matchUnitId = true;
		Boolean matchNumber = true;
		
		if (searchFilter.getIncidentName() == null) searchFilter.setIncidentName("");
		if (searchFilter.getIncidentState() == null) searchFilter.setIncidentState("");
		if (searchFilter.getUnitId() == null) searchFilter.setUnitId("");
		if (searchFilter.getNumber() == null) searchFilter.setNumber("");
		
		if (searchFilter.getIncidentName().length() > 0) {
			matchIncidentName = (inc.getIncidentName().toLowerCase().indexOf(searchFilter.getIncidentName().toLowerCase()) != -1) ? true : false;
		}
		
		if (searchFilter.getIncidentState().length() > 0) {
			matchIncidentState = (inc.getPOOProtectingUnit().substring(0, 2).toLowerCase().indexOf(searchFilter.getIncidentState().toLowerCase()) != -1) ? true : false;
		}
		
		if (searchFilter.getUnitId().length() > 0) {
			matchUnitId = (inc.getPOOProtectingUnit().substring(2).toLowerCase().indexOf(searchFilter.getUnitId().toLowerCase()) != -1) ? true : false;
		}
		
		if (searchFilter.getNumber().length() > 0) {
			matchNumber = (inc.getLocalIncidentIdentifier().toLowerCase().indexOf(searchFilter.getNumber().toLowerCase()) != -1) ? true : false;
		}
		
		if (searchFilter.getIncidentState().length() > 0 && searchFilter.getUnitId().length() > 0 && searchFilter.getNumber().length() > 0) {
			return matchIncidentState && matchUnitId && matchNumber;
		}
		else {
			return matchIncidentName && matchIncidentState && matchUnitId && matchNumber;
		}
		
	}
		
}