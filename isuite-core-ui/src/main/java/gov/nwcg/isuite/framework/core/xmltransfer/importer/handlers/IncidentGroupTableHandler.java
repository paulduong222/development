package gov.nwcg.isuite.framework.core.xmltransfer.importer.handlers;

import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.framework.core.xmltransfer.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import org.apache.log4j.Level;


public class IncidentGroupTableHandler extends BaseTableHandler implements TableHandler {
	
	private String[] postInsertCleanupSqls= {
			"delete from isw_incident_group_prefs"
			,"delete from isw_incident_group_question"

	};
	
	public IncidentGroupTableHandler(){
	}

	public Boolean doPreProcess() throws Exception {
		
		if(super.runMode.equals("SITE")){
			this.doSitePreProcess();
		}else{
			// for enterprise, check if user was prompted
			this.doEnterprisePreProcess();
		}
		
		return true;
	}
	
	private Object doSitePreProcess() throws Exception {
		/*
		 * For site mode, since there is always only 1 incident group for site,
		 * and the one and only site group should always have an id of 1,
		 * we need to make sure the site group transferable identity matches 
		 * the one in the incoming xml.
		 */
		
		// check if existing site group has transferable identity
		String dbTi = "";
		String sql="SELECT transferable_identity from isw_incident_group where id = 1";
		Object result=super.executeQuery(sql);
		if(null != result){
			dbTi = TypeConverter.convertToString(result);
		}

		Object xmlTiValue = XmlTransferUtil.invokeGetMethod(xmlObject, "TransferableIdentity");
		String xmlTi = "";
		if(null != xmlTiValue){
			xmlTi=TypeConverter.convertToString(xmlTiValue);
		}
		
		if(StringUtility.hasValue(dbTi)){
			// dbTi should match the one in the incoming xml?
			
			if(!dbTi.equals(xmlTi)){
				// this is an issue?
				// see CheckSiteGroupMatchRules.java
			}
			
		}else{
			if(StringUtility.hasValue(xmlTi)){
				String updateSql="UPDATE isw_incident_group " +
				 "SET transferable_identity = '" + xmlTi + "' " +
				 "WHERE id = 1";

				dao.executeUpdate(updateSql);
			}
		}
		
		return null;
	}

	private Object doEnterprisePreProcess() throws Exception {
		
		// for enterprise, check if user was prompted to enter incident group name
		CourseOfActionVo coaVo=dialogueVo.getCourseOfActionByName("CHECK_ENTERPRISE_GROUP_MATCH");
		if(null != coaVo && null != coaVo.getStoredObject2()){
			String groupName=(String)coaVo.getStoredObject2();
			
			// update xmlObject with new groupName
			XmlTransferUtil.invokeSetMethod(super.xmlObject, "GroupName", groupName.toUpperCase(), "STRING");
		}
		
		return null;
	}
	
	public void doPostInsertProcesses() throws Exception {
		Long incidentGroupId=(Long)XmlTransferUtil.invokeGetMethod(xmlObject, "Id");
		
		/*
		 * For the incident group table, we need to some records
		 * created by the incident group trigger, and allow the
		 * the data transfer process to create the correct
		 * records with transferable_identities.
		 */
		for(String sql : postInsertCleanupSqls){
			String s = sql + " where incident_group_id = " + incidentGroupId;
			try{
				dao.executeUpdate(s);
			}catch(Exception e){
				this.log(Level.ERROR,"IncidentGroupTableHandler.doPostInsertProcesses() Exception on sql:"+s);
				this.log(Level.ERROR,e.getMessage());
				throw new RuntimeException(e);
			}
		}
		
		/*
		 * For Enterprise Mode, add current user to incident restricted access list
		 */
		Long userId=0L;
		
		if(StringUtility.hasValue(super.runMode) && super.runMode.equalsIgnoreCase("ENTERPRISE")){
			if(BooleanUtility.isFalse(super.fromWebServlet)){
				// get user from context.userSessionVo
				UserSessionVo userSessionVo = (UserSessionVo)super.context.getBean("userSessionVo");
				userId=userSessionVo.getUserId();
			}else{
				userId=super.fromWebServletUserId;
			}
			
			if(LongUtility.hasValue(userId)){
				// check if user is already added to incident user list
				String sql = "SELECT user_id FROM isw_incident_group_user " +
							 "WHERE user_id = " + userId + " " +
							 "AND incident_group_id = " + incidentGroupId + " " ;
				Object rslt=super.executeQuery(sql);
				if(null == rslt){
					// add user to incident
					sql = "INSERT INTO " +
						  "isw_incident_group_user " +
						  " (ID, INCIDENT_GROUP_ID, USER_ID ) " +
						  "VALUES (" +
						  	(dao.isOracleDialect() ? "SEQ_INCIDENT_GROUP_USER.nextVal" : "nextVal('SEQ_INCIDENT_GROUP_USER') " ) + ", "+incidentGroupId + ", "+userId+ " " +
						  ") ";
					dao.executeUpdate(sql);
				}
			}
		}
		
		/*
		 * For Enterprise Mode, add assigned data steward user 
		 * to incident restricted access list
		 */
		if(StringUtility.hasValue(super.runMode) && super.runMode.equalsIgnoreCase("ENTERPRISE")){
			if(LongUtility.hasValue(super.dataStewardUserId)){
				// check if user is already added to incident user list
				String sql = "SELECT user_id FROM isw_incident_group_user " +
							 "WHERE user_id = " + super.dataStewardUserId + " " +
							 "AND incident_group_id = " + incidentGroupId + " " ;
				Object rslt=super.executeQuery(sql);
				if(null == rslt){
					// add user to incident
					sql = "INSERT INTO " +
						  "isw_incident_group_user " +
						  " (ID, INCIDENT_GROUP_ID, USER_ID ) " +
						  "VALUES (" +
						  	(dao.isOracleDialect() ? "SEQ_INCIDENT_GROUP_USER.nextVal" : "nextVal('SEQ_INCIDENT_GROUP_USER') " ) + ", "+incidentGroupId + ", "+super.dataStewardUserId+ " " +
						  ") ";
					dao.executeUpdate(sql);
				}
			}
		}

		/*
		 * if enterprise, set is_site_managed=N and is_site_synced_once=Y
		 */
		if(StringUtility.hasValue(super.runMode) && super.runMode.equalsIgnoreCase("ENTERPRISE")){
			if(LongUtility.hasValue(incidentGroupId)){
				String s="update isw_incident_group set is_site_managed='N', is_site_synced_once='Y' where id = " + incidentGroupId + " ";
				try{
					dao.executeUpdate(s);
				}catch(Exception e){
					this.log(Level.ERROR,"IncidentGroupTableHandler.doPostInsertProcesses() Exception on sql:"+s);
					this.log(Level.ERROR,e.getMessage());
					throw new RuntimeException(e);
				}
				
				String s2="update isw_incident set is_site_managed='N', is_site_synced_once='Y' " +
						  "where id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ";
				try{
					dao.executeUpdate(s2);
				}catch(Exception e){
					this.log(Level.ERROR,"IncidentGroupTableHandler.doPostInsertProcesses() Exception on sql:"+s2);
					this.log(Level.ERROR,e.getMessage());
					throw new RuntimeException(e);
				}
			}
		}
		
	}

	public void doPostUpdateProcesses() throws Exception {
		Long incidentGroupId=(Long)XmlTransferUtil.invokeGetMethod(xmlObject, "Id");

		/*
		 * if enterprise, set is_site_managed=N and is_site_synced_once=Y
		 */
		if(StringUtility.hasValue(super.runMode) && super.runMode.equalsIgnoreCase("ENTERPRISE")){
			if(LongUtility.hasValue(incidentGroupId)){
				String s="update isw_incident_group set is_site_managed='N', is_site_synced_once='Y' where id = " + incidentGroupId + " ";
				try{
					dao.executeUpdate(s);
				}catch(Exception e){
					this.log(Level.ERROR,"IncidentGroupTableHandler.doPostUpdateProcesses() Exception on sql:"+s);
					this.log(Level.ERROR,e.getMessage());
					throw new RuntimeException(e);
				}
				
				String s2="update isw_incident set is_site_managed='N', is_site_synced_once='Y' " +
						  "where id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ") ";
				try{
					dao.executeUpdate(s2);
				}catch(Exception e){
					this.log(Level.ERROR,"IncidentGroupTableHandler.doPostUpdateProcesses() Exception on sql:"+s2);
					this.log(Level.ERROR,e.getMessage());
					throw new RuntimeException(e);
				}
			}
		}
	}

}
