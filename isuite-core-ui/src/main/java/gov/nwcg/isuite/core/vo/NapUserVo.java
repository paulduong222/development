package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.filter.impl.NapSearchFilterImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

import java.util.ArrayList;
import java.util.Collection;

import org.nwcg.www.webservices.security.getUsers.GetUsersResponse;
import org.nwcg.www.webservices.security.getUsers.UserComplexType;

public class NapUserVo extends AbstractVo implements PersistableVo {
	private String userName;
	private String lastName;
	private String firstName;

	public NapUserVo(){
		   
	}

	public static Collection<NapUserVo> getInstances(GetUsersResponse response, Boolean adUsersOnly,NapSearchFilterImpl napFilter){
		Collection<NapUserVo> vos = new ArrayList<NapUserVo>();
		
		for(UserComplexType user : response.getUsers()){
			if(adUsersOnly==true ){
				String loginName=user.getUserName().toUpperCase();
				if(loginName.startsWith("AD.")){
					if(NapSearchFilterImpl.isMatch(napFilter, user.getUserName(),user.getFirstName(), user.getLastName())==true){
						NapUserVo vo = new NapUserVo();
						vo.setUserName(user.getUserName());
						vo.setLastName(user.getLastName());
						vo.setFirstName(user.getFirstName());
						
						vos.add(vo);
					}
				}
			}else{
				if(NapSearchFilterImpl.isMatch(napFilter, user.getUserName(), user.getFirstName(), user.getLastName())==true){
					NapUserVo vo = new NapUserVo();
					vo.setUserName(user.getUserName());
					vo.setLastName(user.getLastName());
					vo.setFirstName(user.getFirstName());
					
					vos.add(vo);
				}
			}
		}
		
		return vos;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	   
}
