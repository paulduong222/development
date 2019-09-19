package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.SystemRole;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.GrantedAuthority;

public class UserPrivilegesVo {
	private Collection<String> roles;
	
	public UserPrivilegesVo(){
		
	}

	public static UserPrivilegesVo getInstance(Collection<SystemRole> entities) throws Exception {
		UserPrivilegesVo vo = new UserPrivilegesVo();

		if(null != entities){
			for(SystemRole role : entities){
				vo.getRoles().add(role.getRoleName());
			}
		}
		
		return vo;
	}
	
	public UserPrivilegesVo(GrantedAuthority[] authorities){
		if(null!=authorities){
			for(GrantedAuthority auth : authorities){
				getRoles().add(auth.getAuthority());
			}
		}
	}
	
	public void setRoles(Collection<String> rolez){
		this.roles=rolez;
	}
	
	public Collection<String> getRoles(){
		if(null==roles)
			roles=new ArrayList<String>();
		
		return roles;
	}
	
	
}
