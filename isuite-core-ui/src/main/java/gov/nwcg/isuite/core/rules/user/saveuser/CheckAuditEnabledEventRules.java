package gov.nwcg.isuite.core.rules.user.saveuser;

import gov.nwcg.isuite.core.domain.DataAuditConfig;
import gov.nwcg.isuite.core.domain.DataAuditTracking;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.DataAuditTrackingImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.persistence.DataAuditConfigDao;
import gov.nwcg.isuite.core.persistence.DataAuditTrackingDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.DataAuditEvent;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Calendar;

import org.springframework.context.ApplicationContext;

public class CheckAuditEnabledEventRules extends AbstractSaveUserRule implements IRule{
	public static final String _RULE_NAME=SaveUserRuleFactory.RuleEnum.CHECK_AUDIT_ENABLED_CHANGE.name();

	public CheckAuditEnabledEventRules(ApplicationContext ctx)
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
			dac=dacDao.getByEventName("ISW_USER", DataAuditEvent.ENABLED_DISABLED.name());
		}catch(Exception e){
			//smother
		}
		
		// check if user's state changed from disabled to enabled?
		if(null != dac && dac.getEnabled()==StringBooleanEnum.Y ){
			if(userEntity != null && !userEntity.isEnabled() && userVo.getEnabled()) {
				// user's state was changed
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaName(_RULE_NAME);
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
				courseOfActionVo.setIsComplete(true);
				courseOfActionVo.setStoredObject("DISABLED_TO_ENABLED");
				dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
			}
		}

		// check if user's state changed from enabled to disabled?
		if(null != dac && dac.getEnabled()==StringBooleanEnum.Y ){
			if(userEntity != null && userEntity.isEnabled() && !userVo.getEnabled()) {
				// user's state was changed
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaName(_RULE_NAME);
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
				courseOfActionVo.setIsComplete(true);
				courseOfActionVo.setStoredObject("ENABLED_TO_DISABLED");
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
					dac=dacDao.getByEventName("ISW_USER", DataAuditEvent.ENABLED_DISABLED.name());
				}catch(Exception e){
					//smother
				}
				
				// log the event
				DataAuditTracking dataAuditTracking = new DataAuditTrackingImpl();
				dataAuditTracking.setDataAuditConfig(dac);
				dataAuditTracking.setTablePrimaryKeyId(userEntity.getId());
				dataAuditTracking.setChangeDate(Calendar.getInstance().getTime());
				dataAuditTracking.setAuditField1(userEntity.getLoginName());
				dataAuditTracking.setAuditField2(userEntity.getLastName());
				dataAuditTracking.setAuditField3(userEntity.getFirstName());
				dataAuditTracking.setAuditField4(userEntity.getHomeUnit().getUnitCode());
				//dataAuditTracking.setAuditField5(userEntity.getPrimaryDispatchCenter().getUnitCode());
				
				User user = new UserImpl();
				user.setId(super.getUserSessionVo().getUserId());
				dataAuditTracking.setUserName(super.getUserSessionVo().getUserLoginName());
				
				String type = (String)coaVo.getStoredObject();
				
				if(type.equals("DISABLED_TO_ENABLED")){
					dataAuditTracking.setOldValue("ACCOUNT DISABLED");
					dataAuditTracking.setNewValue("ACCOUNT ENABLED");
				}else if(type.equals("ENABLED_TO_DISABLED")){
					// log the event
					dataAuditTracking.setOldValue("ACCOUNT ENABLED");
					dataAuditTracking.setNewValue("ACCOUNT DISABLED");
				}
				
				DataAuditTrackingDao datDao = (DataAuditTrackingDao)context.getBean("dataAuditTrackingDao");
				datDao.save(dataAuditTracking);
			}
		}
		
	}

}
