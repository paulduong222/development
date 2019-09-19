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
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Calendar;

import org.springframework.context.ApplicationContext;

public class UserCreatedEventRules extends AbstractSaveUserRule implements IRule{
	public static final String _RULE_NAME=SaveUserRuleFactory.RuleEnum.USER_CREATED_EVENT.name();

	public UserCreatedEventRules(ApplicationContext ctx)
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
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception{
	
		/*
		 * The system must identify the role changes for the user
		 * and log in the tracking table. (Site Only)
		 */
		String runMode=super.getRunMode();
		
		if(runMode.equals("SITE")){
			DataAuditConfigDao dacDao = (DataAuditConfigDao)context.getBean("dataAuditConfigDao");
			DataAuditConfig dac = null;
			
			try{
				dac=dacDao.getByEventName("ISW_USER", DataAuditEvent.ACCOUNT_CREATED.name());
			}catch(Exception e){
				//smother
			}
			
			// check if user is being created?
			if(null != dac && dac.getEnabled()==StringBooleanEnum.Y ){
				if(null==userEntity || !LongUtility.hasValue(super.userEntity.getId())){
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaName(_RULE_NAME);
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
					courseOfActionVo.setIsComplete(true);
					courseOfActionVo.setStoredObject("ACCOUNT_CREATED");
					dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
				}
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
					dac=dacDao.getByEventName("ISW_USER", DataAuditEvent.ACCOUNT_CREATED.name());
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
//				dataAuditTracking.setAuditField5(userEntity.getPrimaryDispatchCenter().getUnitCode());
				User user = new UserImpl();
				user.setId(super.getUserSessionVo().getUserId());
				dataAuditTracking.setUserName(super.getUserSessionVo().getUserLoginName());
				dataAuditTracking.setOldValue("NO ACCOUNT");
				dataAuditTracking.setNewValue("ACCOUNT CREATED");
				
				DataAuditTrackingDao datDao = (DataAuditTrackingDao)context.getBean("dataAuditTrackingDao");
				datDao.save(dataAuditTracking);
			}
		}
		
	}

}
