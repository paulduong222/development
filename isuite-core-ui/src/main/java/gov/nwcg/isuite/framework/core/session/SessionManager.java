package gov.nwcg.isuite.framework.core.session;

import gov.nwcg.isuite.core.vo.UserSessionLogVo;

import java.util.ArrayList;
import java.util.Collection;

public class SessionManager {
	private Collection<UserSessionLogVo> activeUserSessions = new ArrayList<UserSessionLogVo>();
	
	public SessionManager(){
		
	}
	
	/**
	 * Returns whether or not the userId is in the list of active sessions.
	 * 
	 * @param userId
	 * @return
	 */
	public synchronized Boolean isUserActive(Long userId){
		
		for(UserSessionLogVo uslVo : activeUserSessions){
			if(uslVo.getUserVo().getId().compareTo(userId)==0)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Adds a userSession to the active user sessions collection.
	 * 
	 * @param uslVo
	 */
	public synchronized void addUserSession(UserSessionLogVo uslVo){
		if(!isUserActive(uslVo.getUserVo().getId()))
			activeUserSessions.add(uslVo);
	}
	
	/**
	 * Removes a userSession from the active sessions collection
	 * @param uslVo
	 */
	public synchronized void removeUserSession(UserSessionLogVo uslVo){
		UserSessionLogVo removeVo = null;
		for(UserSessionLogVo vo : activeUserSessions){
			if(vo.getUserVo().getId().compareTo(uslVo.getUserVo().getId())==0)
				removeVo=vo;
		}
		if(null != removeVo)
			this.activeUserSessions.remove(removeVo);
	}

	
}
