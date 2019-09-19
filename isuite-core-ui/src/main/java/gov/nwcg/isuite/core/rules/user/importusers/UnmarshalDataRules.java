package gov.nwcg.isuite.core.rules.user.importusers;

import gov.nwcg.isuite.core.domain.DataAuditConfig;
import gov.nwcg.isuite.core.domain.DataAuditTracking;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserImportFailure;
import gov.nwcg.isuite.core.domain.impl.DataAuditTrackingImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.persistence.DataAuditConfigDao;
import gov.nwcg.isuite.core.persistence.DataAuditTrackingDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserImportFailureVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorType;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.DataAuditEvent;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.xml.UserTransfer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class UnmarshalDataRules extends AbstractImportUsersRule implements IRule {

	public static final String _RULE_NAME=ImportUserAccountsRuleFactory.RuleEnum.UNMARSHAL_DATA_RULE.name();

	public UnmarshalDataRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	@Override
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
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		try {
			Collection<UserImportFailure> failures = new ArrayList<UserImportFailure>();

			FIPSEncryptor encryptor = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMFIPSTripleDES);
			byte[] xmlBytes = encryptor.decrypt(xmlByteArray);

			StringBuffer xmlBuffer = new StringBuffer().append(new String(xmlBytes));
			UserTransfer userTransfer = (UserTransfer)xmlHandler.unmarshall(xmlBuffer);

			if(userTransfer.getUser().size()>0){
				uifDao.deleteAll();

				Collection<User> userEntities = UserVo.toEntityList(userTransfer.getUser());
				
				Collection<SystemRoleVo> userRoleVos = super.getGlobalCacheVo().getSystemRoleVos();

				for(User user : userEntities){
					Boolean proceed=true;
					
					user.setSystemRoles(SystemRoleVo.toEntityList(user.getSystemRoles(), userRoleVos));

					//if(null != user.getHomeUnit() && null != user.getPrimaryDispatchCenter()){
					if(null != user.getHomeUnit() ){
						OrganizationVo homeUnit = OrganizationVo.getByUnitCode(user.getHomeUnit().getUnitCode(), super.getGlobalCacheVo().getOrganizationVos());
						//OrganizationVo pdc = OrganizationVo.getDispatchCenterByUnitCode(user.getPrimaryDispatchCenter().getUnitCode(), super.getGlobalCacheVo().getOrganizationVos());
						//if(null != homeUnit && null != pdc){
						if(null != homeUnit ){
							user.setHomeUnit(OrganizationVo.toEntity(null, homeUnit, true));
							//user.setPrimaryDispatchCenter(OrganizationVo.toEntity(null, pdc, true));
						}else{
							UserImportFailure uifEntity = UserImportFailureVo.toEntity(user);
							uifEntity.setFailureReason("No organization exists with unit code: "+user.getHomeUnit().getUnitCode()+".");
							failures.add(uifEntity);
							proceed=false;
						}
					}else {
						UserImportFailure uifEntity = UserImportFailureVo.toEntity(user);
						uifEntity.setFailureReason("Home Unit ID cannot be empty.");
						failures.add(uifEntity);
						proceed=false;
					}

					this.checkUserName(dialogueVo, user, proceed, failures);

				}//End for(User user : userEntities)

				if(failures.size()>0){
					uifDao.saveAll(failures);
				}

			}//End if(userTransfer.getUser().size()>0)
		}catch(Exception e){
			ServiceException se = new ServiceException(e);
			if(se.getMessage().indexOf("file") != -1) {
				super.handleException(ErrorEnum._0040_USER_IMPORTS_CORRUPT_FILE);
			} else {
				throw new Exception(e);
			}
		}
		
		return _OK;
	}

	private void checkUserName(DialogueVo dialogueVo, User user, Boolean proceed, Collection<UserImportFailure> failures) throws Exception {
		if(null == user.getLoginName() || user.getLoginName().isEmpty()) {
			UserImportFailure uifEntity = UserImportFailureVo.toEntity(user);
			uifEntity.setFailureReason("Login Name is a required field.");
			failures.add(uifEntity);
			proceed=false;
		}
		
		if(proceed) {
			UserDao userDao = (UserDao)super.context.getBean("userDao");
			// is there already a user with the same loginname?
			if(null != userDao.getByLoginName(user.getLoginName())){
				UserImportFailure uifEntity = UserImportFailureVo.toEntity(user);
				uifEntity.setFailureReason("User with loginname already exists.");
				failures.add(uifEntity);
			}else{
				// try and save it
				try{
					FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMMD5);
					String newPwd= new String(enc.encrypt(defaultPassword.getBytes()));
					user.setPassword(newPwd);
					user.setShowDataSavedMsg(StringBooleanEnum.N);
					
					userDao.save(user);
					
					Integer saveCount=0;
				    if(null != dialogueVo.getResultObjectAlternate4())
				        saveCount=(Integer)dialogueVo.getResultObjectAlternate4();
				    saveCount++;
				    dialogueVo.setResultObjectAlternate4(saveCount);

					// add user to all incidents if non-priv user
					Boolean isPrivUser=false;
					for(SystemRole sr : user.getSystemRoles()){
						if(sr.getId().compareTo(1L)==0)
							isPrivUser=true;
					}
					if(!isPrivUser){
						if(LongUtility.hasValue(user.getId())){
							IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
							incidentDao.executeInsertIncidentNewUser(user.getId());
							
							// add user to group
							IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
							incidentGroupDao.executeInsertIncidentGroupNewUser(user.getId());
						}
					}
					
					// add audit event
					this.addAuditEvent(user);
					
				}catch(Exception e){
					UserImportFailure uifEntity = UserImportFailureVo.toEntity(user);
					uifEntity.setFailureReason("PersistenceException["+e.getMessage()+"]");
					if(uifEntity.getFailureReason().length()>254)
						uifEntity.setFailureReason(uifEntity.getFailureReason().substring(0,254));
					failures.add(uifEntity);
				}
			}
		}
		
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

	private void addAuditEvent(User userEntity) {
		try{
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
//			dataAuditTracking.setAuditField5(userEntity.getPrimaryDispatchCenter().getUnitCode());
			User user = new UserImpl();
			user.setId(super.getUserSessionVo().getUserId());
			dataAuditTracking.setUserName(super.getUserSessionVo().getUserLoginName());
			dataAuditTracking.setOldValue("NO ACCOUNT");
			dataAuditTracking.setNewValue("ACCOUNT CREATED");
			
			DataAuditTrackingDao datDao = (DataAuditTrackingDao)context.getBean("dataAuditTrackingDao");
			datDao.save(dataAuditTracking);
			
		}catch(Exception e){
			
		}
	}
}
