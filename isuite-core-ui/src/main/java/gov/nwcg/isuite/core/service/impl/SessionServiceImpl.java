package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.filter.GridColumnUserFilter;
import gov.nwcg.isuite.core.filter.impl.GridColumnUserFilterImpl;
import gov.nwcg.isuite.core.service.GridColumnUserService;
import gov.nwcg.isuite.core.vo.GridColumnUserVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.GridNameEnum;

import java.util.ArrayList;
import java.util.Collection;

public class SessionServiceImpl {
	private GridColumnUserService gridColumnUserService = null;
	private UserSessionVo userSessionVo=null;

	public SessionServiceImpl(){
		
	}
	
	public SessionServiceImpl(UserSessionVo userSessionVo,GridColumnUserService gcuService){
		this.userSessionVo=userSessionVo;
		this.gridColumnUserService=gcuService;
	}

	/**
	 * Returns the userSessionVo loaded in the flex session context.
	 * 
	 * @return
	 * 		the userSessionVo to return
	 * @throws ServiceException
	 */
	public UserSessionVo getSessionObject() throws ServiceException {
		
		try{

			/*
	        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

	        if(null!=authentication){
	        	UserPrivilegesVo privileges = new UserPrivilegesVo(authentication.getAuthorities());
	        	userSessionVo.setUserPrivilegesVo(privileges);
	        }
	        */
			
			userSessionVo=new UserSessionVo();
			userSessionVo.setFirstName("devuser");
			userSessionVo.setLastName("lastname");
			userSessionVo.setUserId(700L);

			/*
			 * Get User's grid column definitions
			 */
			GridColumnUserFilter filter = new GridColumnUserFilterImpl();
			filter.setUserId(userSessionVo.getUserId());
			Collection<GridColumnUserVo> gridColumnUserVos = gridColumnUserService.getUserGridColumns(filter);
			if(null != gridColumnUserVos){
				for(GridColumnUserVo vo : gridColumnUserVos){
					if(vo.getGridColumnVo().getGridName().equals(GridNameEnum.RESOURCE)){
						userSessionVo.getGridResourceColumns().add(vo);
					}
					else if(vo.getGridColumnVo().getGridName().equals(GridNameEnum.RESOURCEDEMOB)){
						userSessionVo.getGridResourceDemobColumns().add(vo);
					}
					else if(vo.getGridColumnVo().getGridName().equals(GridNameEnum.RESOURCETIME)) {
						userSessionVo.getGridResourceTimeColumns().add(vo);
					}
				}
			}
			
			Collection<String> roles = new ArrayList<String>();
			roles.add("ROLE_ADMIN_USER");
			roles.add("ROLE_USER");
			roles.add("ROLE_RESOURCES");
			roles.add("ROLE_COST");
			roles.add("ROLE_TIME");

			
			//userSessionVo.getUserPrivilegesVo().setRoles(roles);
			
			return this.userSessionVo;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
}
