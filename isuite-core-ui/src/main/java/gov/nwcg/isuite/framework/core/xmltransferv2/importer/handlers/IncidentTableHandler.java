package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import java.util.Collection;

import gov.nwcg.isuite.core.persistence.PriorityProgramDao;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.apache.log4j.Level;


public class IncidentTableHandler extends BaseTableHandler implements TableHandler {
	
	
	private String[] postInsertCleanupSqls= {
			"delete from isw_incident_prefs where incident_id = :incidentid"
			,"delete from isw_incident_question where incident_id = :incidentid"
			/* turn off for now, we are not transferring inc cost rates that have last modified by value
			,"delete from isw_inccost_rate_ovhd where inccost_rate_id in (select id from isw_inccost_rate where incident_id = :incidentid )"
			,"delete from isw_inccost_rate_kind where inccost_rate_id in (select id from isw_inccost_rate where incident_id = :incidentid )"
			,"delete from isw_inccost_rate_state_kind where INCCOST_RATE_STATE_ID in ( "+
				"select id from isw_inccost_rate_state where inccost_rate_id in ( " +
					"  select id from isw_inccost_rate where incident_id = :incidentid " +
				") " +
			 ") "
			,"delete from isw_inccost_rate_state where INCCOST_RATE_ID in ( "+
				"  select id from isw_inccost_rate where incident_id = :incidentid " +
			 ") " 
			,"delete from isw_inccost_rate where incident_id = :incidentid"
			*/
	};

	public IncidentTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		return true;
	}

	public void doPostInsertProcesses() throws Exception {
		Long incidentId=(Long)XmlTransferUtil.invokeGetMethod(xmlObject, "Id");
		
		/*
		 * For the incident table, we need to remove some records
		 * created by the incident oninsert trigger, and allow the
		 * the data transfer process to create the correct
		 * records with transferable_identities.
		 */
		for(String sql : postInsertCleanupSqls){
			String s = sql.replaceAll(":incidentid", String.valueOf(incidentId));
			//+ " where incident_id = " + incidentId;
			try{
				dao.executeUpdate(s);
			}catch(Exception e){
				this.log(Level.ERROR,"IncidentTableHandler.doPostInsertProcesses() Exception on sql:"+s);
				this.log(Level.ERROR,e.getMessage());
				throw new RuntimeException(e);
			}
		}
		
		/*
		 * For Site Mode, add incident to incidentgroupincidents
		 */
		if(StringUtility.hasValue(super.runMode) && super.runMode.equalsIgnoreCase("SITE")){
			String sql = "insert into isw_incident_group_incident (incident_group_id,incident_id) "+
					     "values ( (select id from isw_incident_group) , "+incidentId+") ";
			try{
				dao.executeUpdate(sql);
			}catch(Exception e){
				this.log(Level.ERROR,"IncidentTableHandler.doPostInsertProcesses() Exception on sql:"+sql);
				this.log(Level.ERROR,e.getMessage());
				throw new RuntimeException(e);
			}
			
			PriorityProgramDao ppDao = (PriorityProgramDao)context.getBean("priorityProgramDao");
			Collection<PriorityProgramVo> vos2 =ppDao.getGrid(incidentId, null);
			for(PriorityProgramVo v : vos2){
				ppDao.syncNewFromIncident(v.getCode(), 1L);
			}

			Collection<PriorityProgramVo> vos =ppDao.getGrid(null, 1L);
			if(CollectionUtility.hasValue(vos)){
				for(PriorityProgramVo v : vos){
					ppDao.syncNewWithGroup(v.getCode(), 1L);
				}
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
				String sql = "SELECT id FROM isw_restricted_incident_user " +
							 "WHERE user_id = " + userId + " " +
							 "AND incident_id = " + incidentId + " " ;
				Object rslt=super.executeQuery(sql);
				if(null == rslt){
					// add user to incident
					sql = "INSERT INTO " +
						  "isw_restricted_incident_user " +
						  " (ID, INCIDENT_ID, USER_ID, USER_TYPE ) " +
						  "VALUES (" +
						  	(dao.isOracleDialect() ? "SEQ_RESTRICTED_INCIDENT_USER.nextVal" : "nextVal('SEQ_RESTRICTED_INCIDENT_USER') " ) + ", "+incidentId + ", "+userId+", 'OWNER'" +
						  ") ";
					dao.executeUpdate(sql);
				}
			}
		}
		
		/*
		 * For Enterprise Mode, add the assigned data steward user 
		 * to incident restricted access list
		 */
		if(StringUtility.hasValue(super.runMode) && super.runMode.equalsIgnoreCase("ENTERPRISE")){
			if(LongUtility.hasValue(super.dataStewardUserId)){
				// check if user is already added to incident user list
				String sql = "SELECT id FROM isw_restricted_incident_user " +
							 "WHERE user_id = " + dataStewardUserId + " " +
							 "AND incident_id = " + incidentId + " " ;
				Object rslt=super.executeQuery(sql);
				if(null == rslt){
					// add user to incident
					sql = "INSERT INTO " +
						  "isw_restricted_incident_user " +
						  " (ID, INCIDENT_ID, USER_ID, USER_TYPE ) " +
						  "VALUES (" +
						  	(dao.isOracleDialect() ? "SEQ_RESTRICTED_INCIDENT_USER.nextVal" : "nextVal('SEQ_RESTRICTED_INCIDENT_USER') " ) + ", "+incidentId + ", "+dataStewardUserId+", 'OWNER'" +
						  ") ";
					dao.executeUpdate(sql);
				}
			}
		}

		/*
		 * if enterprise, set is_site_managed=N and is_site_synced_once=Y
		 */
		if(StringUtility.hasValue(super.runMode) && super.runMode.equalsIgnoreCase("ENTERPRISE")){
			if(LongUtility.hasValue(incidentId)){
				String s="update isw_incident set is_site_managed='N', is_site_synced_once='Y' where id = " + incidentId + " ";
				try{
					dao.executeUpdate(s);
				}catch(Exception e){
					this.log(Level.ERROR,"IncidentTableHandler.doPostInsertProcesses() Exception on sql:"+s);
					this.log(Level.ERROR,e.getMessage());
					throw new RuntimeException(e);
				}
				
			}
		}
		
	}

	public void doPostUpdateProcesses() throws Exception {
		Long incidentId=(Long)XmlTransferUtil.invokeGetMethod(xmlObject, "Id");

		/*
		 * if enterprise, set is_site_managed=N and is_site_synced_once=Y
		 */
		if(StringUtility.hasValue(super.runMode) && super.runMode.equalsIgnoreCase("ENTERPRISE")){
			if(LongUtility.hasValue(incidentId)){
				String s="update isw_incident set is_site_managed='N', is_site_synced_once='Y' where id = " + incidentId + " ";
				try{
					dao.executeUpdate(s);
				}catch(Exception e){
					this.log(Level.ERROR,"IncidentTableHandler.doPostUpdateProcesses() Exception on sql:"+s);
					this.log(Level.ERROR,e.getMessage());
					throw new RuntimeException(e);
				}
				
			}
		}
	}

}
