package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserNotification;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.UserNotificationDao;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

public class CustomUserDetailsServiceImpl implements ApplicationContextAware, UserDetailsService {
	private UserDao userDao=null;
	private ApplicationContext context;
	
	public CustomUserDetailsServiceImpl(UserDao userDao){
		this.userDao=userDao;
	}
	
    public UserDetails loadUserByUsername(String userId)
            throws UsernameNotFoundException, DataAccessException {

    	UserSessionVo userSessionVo=null;
    	Set<String> roles = new HashSet<String>();

        if( (null==userId) || (userId.isEmpty())){
        	return null;
        	//throw new RuntimeException("Unable to authenticate");
        }
        
        if(!StringUtils.isNumeric(userId)){
        	return null;
        }
        Long id = 0L;
        
        try{
        	id=TypeConverter.convertToLong(userId);
        }catch(Exception e){
        	throw new RuntimeException("Unable to convert userId .");
        }
        
        try{
        	User user = userDao.getById(id,UserImpl.class);
        	
        	if(null!=user){
        		
        		long start=Calendar.getInstance().getTimeInMillis();
        		
				userSessionVo = UserSessionVo.getInstance(user);

				long stop=Calendar.getInstance().getTimeInMillis();

				//System.out.println("diff:"+(stop-start));
				
				//Collection<String> perms = userDao.getPermissionsForUser(id);
				Collection<String> perms = new ArrayList<String>();
				
				GlobalCacheVo gcVo = (GlobalCacheVo)this.context.getBean("globalCacheVo");
				
				//Collection<WorkAreaVo> waVos=new ArrayList<WorkAreaVo>();
				//WorkAreaVo currentWorkAreaVo = new WorkAreaVo();
				//WorkAreaDao waDao = (WorkAreaDao)this.context.getBean("workAreaDao");

				/* dan - 03/19/2012
				if(gcVo.getSystemVo().getRunMode().equals("SITE")){
					WorkArea waEntity = waDao.getById(1L, WorkAreaImpl.class);
					currentWorkAreaVo = WorkAreaVo.getInstance(waEntity, true);
					waVos.add(WorkAreaVo.getInstance(waEntity, true));
				}else{
					// remove sync in the future
					// once a db is reloaded, we can remove the next line
					waDao.syncUserRestrictedWorkArea(id);
					
					WorkArea primaryWorkArea = waDao.getByStandardOrganizationId(user.getPrimaryDispatchCenter().getId());
					currentWorkAreaVo = WorkAreaVo.getInstance(primaryWorkArea, true);
					
					Collection<WorkArea> was = waDao.getWorkAreasForUser(id);
				
					if((null != was)&&(was.size()>0)){
						waVos=WorkAreaVo.getInstances(was, true);
					}
				}
				*/
				
				/*
				 * Check if user has any unread notifications
				 */
				UserNotificationDao unDao = (UserNotificationDao)this.context.getBean("userNotificationDao");
				Collection<UserNotification> notifications = unDao.getUnreadNotifications(user.getId());
				for(UserNotification notif : notifications){
					((UserSessionVo)context.getBean("userSessionVo")).getNotifications().add(notif.getMessage());
				}
				
				/*
				 * Check if user rules of behavior is valid
				 */
				Boolean robValid=this.isUserRulesOfBehaviorValid(user.getRobAgreementDate());
				
				/*
				 * We need to transfer contents of userSessionVo to session scoped instance
				 * from spring
				 */
				((UserSessionVo)context.getBean("userSessionVo")).setLastName(userSessionVo.getLastName());
				((UserSessionVo)context.getBean("userSessionVo")).setFirstName(userSessionVo.getFirstName());
				((UserSessionVo)context.getBean("userSessionVo")).setUserLoginName(userSessionVo.getUserLoginName());
				((UserSessionVo)context.getBean("userSessionVo")).setUserId(userSessionVo.getUserId());
				((UserSessionVo)context.getBean("userSessionVo")).setGridResourceColumns(userSessionVo.getGridResourceColumns());
				((UserSessionVo)context.getBean("userSessionVo")).setGridResourceCostColumns(userSessionVo.getGridResourceCostColumns());
				((UserSessionVo)context.getBean("userSessionVo")).setGridOtherCostColumns(userSessionVo.getGridOtherCostColumns());
				((UserSessionVo)context.getBean("userSessionVo")).setGridResourceDemobColumns(userSessionVo.getGridResourceDemobColumns());
				((UserSessionVo)context.getBean("userSessionVo")).setGridResourceTimeColumns(userSessionVo.getGridResourceTimeColumns());
				((UserSessionVo)context.getBean("userSessionVo")).setGridWorkAreaResourceColumns(userSessionVo.getGridWorkAreaResourceColumns());
//				((UserSessionVo)context.getBean("userSessionVo")).setCurrentWorkAreaVo(userSessionVo.getCurrentWorkAreaVo());
				//((UserSessionVo)context.getBean("userSessionVo")).setCurrentWorkAreaVo(currentWorkAreaVo);
				//((UserSessionVo)context.getBean("userSessionVo")).setWorkAreaVos(waVos);
				((UserSessionVo)context.getBean("userSessionVo")).setUserOrganizationVos(userSessionVo.getUserOrganizationVos());
				((UserSessionVo)context.getBean("userSessionVo")).setUserRoleVos(userSessionVo.getUserRoleVos());
				((UserSessionVo)context.getBean("userSessionVo")).setPermissions(perms);
				((UserSessionVo)context.getBean("userSessionVo")).setRobAgreementValid(robValid);
				((UserSessionVo)context.getBean("userSessionVo")).setPrivilegedUser(userSessionVo.getPrivilegedUser());
				((UserSessionVo)context.getBean("userSessionVo")).setFsUser(userSessionVo.getFsUser());
				//((UserSessionVo)context.getBean("userSessionVo")).setPermissions(getPermissions());
				((UserSessionVo)context.getBean("userSessionVo")).setHomeUnitCode(userSessionVo.getHomeUnitCode());
				((UserSessionVo)context.getBean("userSessionVo")).setPdcUnitCode(userSessionVo.getPdcUnitCode());
				((UserSessionVo)context.getBean("userSessionVo")).setDispatchCenterVo(userSessionVo.getDispatchCenterVo());
				((UserSessionVo)context.getBean("userSessionVo")).setWorkPhone(userSessionVo.getWorkPhone());
				((UserSessionVo)context.getBean("userSessionVo")).setCellPhone(userSessionVo.getCellPhone());
				((UserSessionVo)context.getBean("userSessionVo")).setEmail(userSessionVo.getEmail());
				((UserSessionVo)context.getBean("userSessionVo")).setEnabled(userSessionVo.getEnabled());
				((UserSessionVo)context.getBean("userSessionVo")).setShowDataSavedMsg(userSessionVo.getShowDataSavedMsg());
				
        	}else{
        		roles.add("ROLE_NONE");
        	}
        }catch(Exception e){
        	throw new RuntimeException(e);
        }
        
        for(SystemRoleVo srv : userSessionVo.getUserRoleVos()) {
        	roles.add(srv.getRoleName());
        }
       
        org.springframework.security.userdetails.User user = new org.springframework.security.userdetails.User(
                userSessionVo.getUserLoginName(),
                "pwd",
                true,
                true,
                true,
                true,
                makeGrantedAuthorities(roles));
       
        //userSessionVo.setUserPrivilegesVo(new UserPrivilegesVo(user.getAuthorities()));
        
        return user;
    }

    /**
     * Returns a GrantedAuthority Array containing the user's granted roles.
     * 
     * @param roles 
     * 			Set<String>
     * @return 
     * 		GrantedAuthority[]
     */
    private GrantedAuthority[] makeGrantedAuthorities(Set<String> roles) {
        GrantedAuthority[] result = new GrantedAuthority[roles.size()];
       
        int i = 0;
       
        for (String role : roles) {
            result[i++] = new GrantedAuthorityImpl(role);
        }
       
        return result;
    }

    private Boolean isUserRulesOfBehaviorValid(Date robAgreementDate) throws Exception {
    	if(null==robAgreementDate)
    		return false;
    	
    	SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
    	
    	SystemParameter spEntity=spDao.getByParameterName(SystemParameterTypeEnum.USER_ROB_AGREEMENT_DAYS.value());
    	if(null == spEntity)
    		throw new Exception("Unable to read for user rules of behavior agreement days.");
    	
    	if( (null!=spEntity.getParameterValue()) &&(!spEntity.getParameterValue().isEmpty()) ){
    		long days = Long.parseLong(spEntity.getParameterValue());
    		
    		long diffDays=DateUtil.diffDays(robAgreementDate, Calendar.getInstance().getTime());
    		
    		if(diffDays >= days)
    			return false;
    		
    	}
    	
    	return true;
    }
    
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext ctx)throws BeansException {
		this.context=ctx;
	}

}
