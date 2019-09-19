package gov.nwcg.isuite.core.rules.user.saveuser;

import gov.nwcg.isuite.core.domain.DataAuditConfig;
import gov.nwcg.isuite.core.domain.DataAuditTracking;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.DataAuditTrackingImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.persistence.DataAuditConfigDao;
import gov.nwcg.isuite.core.persistence.DataAuditTrackingDao;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.DataAuditEvent;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckAuditRoleEventRules extends AbstractSaveUserRule implements IRule{
	public static final String _RULE_NAME=SaveUserRuleFactory.RuleEnum.CHECK_AUDIT_ROLE_CHANGE.name();

	public CheckAuditRoleEventRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(_RULE_NAME,true));
				
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) {
		/*
		 * The system must identify the role changes for the user
		 * and log in the tracking table.
		 */

		if(null==userEntity)
			return _OK;
		
		DataAuditConfigDao dacDao = (DataAuditConfigDao)context.getBean("dataAuditConfigDao");
		DataAuditConfig dac = null;
		
		try{
			dac=dacDao.getByEventName("ISW_USER", DataAuditEvent.ROLE_CHANGED.name());
		}catch(Exception e){
			System.out.println(e.getMessage());
			//smother
		}
		
		Collection<String> rolesChanges=new ArrayList<String>();
		
		// check if roles changed ?
		if(null != dac && dac.getEnabled()==StringBooleanEnum.Y ){

			// get list of roles removed?
			for(SystemRole role : userEntity.getSystemRoles()){
				Boolean roleStillExists=false;
				
				// is "role" still in users list?
				for(SystemRoleVo roleVo : userVo.getUserRoleVos()){
					if(role.getId().compareTo(roleVo.getId())==0){
						roleStillExists=true;
						break;
					}
				}
				
				if(!roleStillExists)
					rolesChanges.add("REMOVED ROLE - " + role.getDisplayName());
			}
			
			// get list of roles added?
			for(SystemRoleVo roleVo : userVo.getUserRoleVos()){
				Boolean roleAdded=true;
				
				// is "role" in users list?
				for(SystemRole role : userEntity.getSystemRoles()){
					if(roleVo.getId().compareTo(role.getId())==0){
						roleAdded=false;
						break;
					}
				}

				if(roleAdded)
					rolesChanges.add("ADDED ROLE - " + roleVo.getDisplayName());
				
			}

			// Did something change?
			if(CollectionUtility.hasValue(rolesChanges)){ 

				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaName(_RULE_NAME);
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
				courseOfActionVo.setIsComplete(true);
				courseOfActionVo.setStoredObject(rolesChanges);
				dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
				
			}

		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);

		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			if(null != coaVo.getStoredObject()){
				
				DataAuditConfigDao dacDao = (DataAuditConfigDao)context.getBean("dataAuditConfigDao");
				DataAuditConfig dac = null;
				try{
					dac=dacDao.getByEventName("ISW_USER", DataAuditEvent.ROLE_CHANGED.name());
				}catch(Exception e){
					//smother
				}
				
				UserSessionVo userSession = (UserSessionVo)context.getBean("userSessionVo");
				
				// log the event
				DataAuditTracking dataAuditTracking = new DataAuditTrackingImpl();
				dataAuditTracking.setDataAuditConfig(dac);
				dataAuditTracking.setTablePrimaryKeyId(userEntity.getId());
				dataAuditTracking.setAuditField1(userEntity.getLoginName());
				dataAuditTracking.setAuditField2(userEntity.getLastName());
				dataAuditTracking.setAuditField3(userEntity.getFirstName());
				dataAuditTracking.setAuditField4(userEntity.getHomeUnit().getUnitCode());
				//dataAuditTracking.setAuditField5(userEntity.getPrimaryDispatchCenter().getUnitCode());
				dataAuditTracking.setChangeDate(Calendar.getInstance().getTime());
				User user = new UserImpl();
				user.setId(super.getUserSessionVo().getUserId());
				dataAuditTracking.setUserName(super.getUserSessionVo().getUserLoginName());

				Collection<String> roleChanges = (Collection<String>)coaVo.getStoredObject();

				if(CollectionUtility.hasValue(roleChanges)){
					for(String event : roleChanges){
						dataAuditTracking.setOldValue("ROLE CHANGED");
						dataAuditTracking.setNewValue(event);
					}
				}
				
				DataAuditTrackingDao datDao = (DataAuditTrackingDao)context.getBean("dataAuditTrackingDao");
				datDao.save(dataAuditTracking);
			}
			
		}
		
	}

}
