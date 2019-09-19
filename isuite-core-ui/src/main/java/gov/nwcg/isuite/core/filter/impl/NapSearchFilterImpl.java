package gov.nwcg.isuite.core.filter.impl;

import org.nwcg.www.webservices.security.getUsers.UserComplexType;

import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.util.StringUtility;

public class NapSearchFilterImpl extends FilterImpl {

	private static final long serialVersionUID = 3109805558842919394L;
	private String unitId;
	private String userName;
	private String lastName;
	private String firstName;
	
	public static Boolean isMatch(NapSearchFilterImpl filter,String userName, String firstName, String lastName) {
		Boolean matchUserName=true;
		Boolean matchFirstName=true;
		Boolean matchLastName=true;

		if(StringUtility.hasValue(filter.getUserName())){
			String userNameFilter=filter.getUserName();
			if(userNameFilter.startsWith("%") && userNameFilter.endsWith("%")){
				// contains
				if(userNameFilter.length()>2){
					String filterValue=userNameFilter.substring(1, (userNameFilter.length()-2));
					if(StringUtility.hasValue(filterValue)){
						if(!userName.toUpperCase().contains(filterValue.toUpperCase()))
							matchUserName=false;
					}
				}
			}else if(!userNameFilter.startsWith("%") && userNameFilter.endsWith("%")){
				// like
				if(userNameFilter.length()>1){
					String filterValue=userNameFilter.substring(0, (userNameFilter.length()-1));
					if(StringUtility.hasValue(filterValue)){
						if(!userName.toUpperCase().startsWith(filterValue.toUpperCase()))
							matchUserName=false;
					}
				}
			}else if(userNameFilter.startsWith("%") && !userNameFilter.endsWith("%")){
				// ends with
				if(userNameFilter.length()>1){
					String filterValue=userNameFilter.substring(1, (userNameFilter.length()-1));
					if(StringUtility.hasValue(filterValue)){
						if(!userName.toUpperCase().endsWith(filterValue.toUpperCase()))
							matchUserName=false;
					}
				}
			}else{
				if(!userName.toUpperCase().equals(userNameFilter.toUpperCase()))
					matchUserName=false;
			}
		}
		
		if(StringUtility.hasValue(filter.getFirstName())){
			String firstNameFilter=filter.getFirstName();
			if(firstNameFilter.startsWith("%") && firstNameFilter.endsWith("%")){
				// contains
				if(firstNameFilter.length()>2){
					String filterValue=firstNameFilter.substring(1, (firstNameFilter.length()-2));
					if(StringUtility.hasValue(filterValue)){
						if(!firstName.toUpperCase().contains(filterValue.toUpperCase()))
							matchFirstName=false;
					}
				}
			}else if(!firstNameFilter.startsWith("%") && firstNameFilter.endsWith("%")){
				// like
				if(firstNameFilter.length()>1){
					String filterValue=firstNameFilter.substring(0, (firstNameFilter.length()-1));
					if(StringUtility.hasValue(filterValue)){
						if(!firstName.toUpperCase().startsWith(filterValue.toUpperCase()))
							matchFirstName=false;
					}
				}
			}else if(firstNameFilter.startsWith("%") && !firstNameFilter.endsWith("%")){
				// ends with
				if(firstNameFilter.length()>1){
					String filterValue=firstNameFilter.substring(1, (firstNameFilter.length()-1));
					if(StringUtility.hasValue(filterValue)){
						if(!firstName.toUpperCase().endsWith(filterValue.toUpperCase()))
							matchFirstName=false;
					}
				}
			}else{
				if(!firstName.toUpperCase().equals(firstNameFilter.toUpperCase()))
					matchFirstName=false;
			}
		}
		
		if(StringUtility.hasValue(filter.getLastName())){
			String lastNameFilter=filter.getLastName();
			if(lastNameFilter.startsWith("%") && lastNameFilter.endsWith("%")){
				// contains
				if(lastNameFilter.length()>2){
					String filterValue=lastNameFilter.substring(1, (lastNameFilter.length()-2));
					if(StringUtility.hasValue(filterValue)){
						if(!lastName.toUpperCase().contains(filterValue.toUpperCase()))
							matchLastName=false;
					}
				}
			}else if(!lastNameFilter.startsWith("%") && lastNameFilter.endsWith("%")){
				// like
				if(lastNameFilter.length()>1){
					String filterValue=lastNameFilter.substring(0, (lastNameFilter.length()-1));
					if(StringUtility.hasValue(filterValue)){
						if(!lastName.toUpperCase().startsWith(filterValue.toUpperCase()))
							matchLastName=false;
					}
				}
			}else if(lastNameFilter.startsWith("%") && !lastNameFilter.endsWith("%")){
				// ends with
				if(lastNameFilter.length()>1){
					String filterValue=lastNameFilter.substring(1, (lastNameFilter.length()-1));
					if(StringUtility.hasValue(filterValue)){
						if(!lastName.toUpperCase().endsWith(filterValue.toUpperCase()))
							matchLastName=false;
					}
				}
			}else{
				if(!lastName.toUpperCase().equals(lastNameFilter.toUpperCase()))
					matchLastName=false;
			}
		}
		
		return (matchLastName==true && matchFirstName==true && matchUserName==true ? true : false );
	}
	
	
	/**
	 * @return the unitId
	 */
	public String getUnitId() {
		return unitId;
	}
	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
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

}
