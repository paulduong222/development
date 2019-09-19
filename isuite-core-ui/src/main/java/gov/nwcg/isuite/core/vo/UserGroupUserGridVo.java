package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Common user group user information to be provided for Manage User Group User Screen.
 * 
 * @author bsteiner
 */
public class UserGroupUserGridVo extends AbstractVo {
   private String loginNm;
   private String firstNm;
   private String lastNm;
   private Collection<SystemRoleVo> userRoles = null;
   private String primaryOrgCd;
   private String dispatchCenterCd;
   private Long userId;
   private boolean selected = false;
   
   /**
    * Default constructor
    *
    */
   public UserGroupUserGridVo() {}
   
   /**
    * Full constructor
    * @param ugu
    */
   @SuppressWarnings("unchecked")
   public UserGroupUserGridVo(UserGroupUser ugu) {
      super();
      setId(ugu.getId());
      
      User user = ugu.getUser();
      if (user != null) {
         setLoginNm(user.getLoginName());
         setFirstNm(user.getFirstName());
         setLastNm(user.getLastName());
         if (user.getHomeUnit() != null) {
        	setPrimaryOrgCd(user.getHomeUnit().getUnitCode());
         }
         if(user.getPrimaryDispatchCenter() != null) {
        	 setDispatchCenterCd(user.getPrimaryDispatchCenter().getUnitCode());
         }
      }
      /*
      if (ugu.getUserRoles() != null) {
         Collection<UserGroupUserAuthorityVo> uguaList = new ArrayList(ugu.getUserGroupUserAuthorities().size());
         for (UserGroupUserAuthority currentUGUA : ugu.getUserGroupUserAuthorities()) {
            try {
               uguaList.add(UserGroupUserAuthorityVo.getInstance(currentUGUA, true));
            }
            catch ( Exception e ) {
               e.printStackTrace();
            }
         }
         setRoles(uguaList);
      }
      */
   }

	/**
	 * Returns a UserGroupUserGridVo instance from a UserGroupUser entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @return
	 * 		instance of UserGroupUserGridVo
	 * @throws Exception
	 */
	public static UserGroupUserGridVo getInstance(UserGroupUser entity) throws Exception {
		UserGroupUserGridVo vo = new UserGroupUserGridVo();

		if(null == entity)
			throw new Exception("Unable to create UserGroupUserGridVo from null UserGroupUser entity.");

		vo.setId(entity.getId());
		if(null != entity.getUser()){
			vo.setLoginnm(entity.getUser().getLoginName());
			vo.setFirstnm(entity.getUser().getFirstName());
			vo.setLastnm(entity.getUser().getLastName());
			if(null != entity.getUser().getHomeUnit()){
	        	vo.setPrimaryOrgCd(entity.getUser().getHomeUnit().getUnitCode());
			}
			if(null != entity.getUser().getPrimaryDispatchCenter()){
				vo.setDispatchCenterCd(entity.getUser().getPrimaryDispatchCenter().getUnitCode());
			}
			
//	        if (null != entity.getUserGroupUserRoles()) {
//	        	//vo.setRoles(UserGroupUserAuthorityVo.getInstances(entity.getUserGroupUserAuthorities(),true));
//	        }
		}

		return vo;
	}

	public static Collection<UserGroupUserGridVo> getInstances(Collection<UserGroupUser> entities) throws Exception {
		Collection<UserGroupUserGridVo> vos = new ArrayList<UserGroupUserGridVo>();

		for(UserGroupUser entity : entities){
			vos.add(UserGroupUserGridVo.getInstance(entity));
		}

		return vos;
	}
   
   /**
    * @return the dispatchCenterCd
    */
   public String getDispatchCenterCd() {
      return dispatchCenterCd;
   }

   /**
    * @param dispatchCenterCd the dispatchCenterCd to set
    */
   public void setDispatchCenterCd(String workUnitCode) {
      this.dispatchCenterCd = workUnitCode;
   }


   /**
    * @return the homeUnitCode
    */
   public String getPrimaryOrgCd() {
      return primaryOrgCd;
   }

   /**
    * @param homeUnitCode the homeUnitCode to set
    */
   public void setPrimaryOrgCd(String homeUnitCode) {
      primaryOrgCd = homeUnitCode;
   }

   /**
    * 
    * @return First Name
    */
	public String getFirstNm() {
		return firstNm;
	}

   /**
    * 
    * @return Last Name
    */
	public String getLastNm() {
		return lastNm;
	}

   /**
    * 
    * @return Login Name
    */
	public String getLoginNm() {
		return loginNm;
	}

   /**
    * 
    * @return String of Reader Friendly Roles separated by comma's
    */
   public String getReaderFriendlyRoles() {
		StringBuffer commaSeparatedRoles = new StringBuffer();
		/*
		if(null != this.getRoles()){
			for (UserGroupUserAuthorityVo vo : this.getRoles()) {
			   String role = vo.getRoleVo().getRoleName();
			   if (commaSeparatedRoles.length() > 0) {
			      commaSeparatedRoles.append(", ");
			   }
	         commaSeparatedRoles.append(role);
			}
		}
		*/
      if (commaSeparatedRoles.length() < 1) {
         commaSeparatedRoles.append("none");
      }
		
		return commaSeparatedRoles.toString();
	}

   /**
    * 
    * @param firstName
    */
	public void setFirstNm(String firstName) {
		this.firstNm = firstName;
	}

   /**
    * 
    * @param lastName
    */
	public void setLastNm(String lastName) {
		this.lastNm = lastName;
	}

   /**
    * 
    * @param name
    */
	public void setLoginNm(String name) {
		this.loginNm = name;
	}

	/**
	 * 
	 * @return the selected flag (convenience method for flex)
	 */
	public boolean getSelected() {
	   return selected;
	}

   /**
    * 
    * @return the selected flag
    */
	@JsonIgnore
   public boolean isSelected() {
      return selected;
   }

   /**
    * 
    * @param selected
    */
   public void setSelected(boolean selected) {
      this.selected = selected;
   }

   /**
     * Constructs a <code>String</code> with all attributes in xml format.
     *
     * @return a <code>String</code> representation of this object.
     */
    @Override
    public String toString() {
        final String nl = System.getProperty("line.separator");
        
        StringBuffer retValue = new StringBuffer();
        String tab = "\t";
        
        retValue.append("<UserGroupUserGridVo>").append(nl)
    	    .append(tab).append(super.toString()).append(nl)
           .append(tab).append("<loginNm>").append(this.loginNm).append("</loginNm>").append(nl)   
           .append(tab).append("<firstNm>").append(this.firstNm).append("</firstNm>").append(nl)   
           .append(tab).append("<lastNm>").append(this.lastNm).append("</lastNm>").append(nl)   
           .append(tab).append("<roles>").append(this.getReaderFriendlyRoles()).append("</roles>").append(nl)   
           .append(tab).append("<primaryOrgCd>").append(this.primaryOrgCd).append("</primaryOrgCd>").append(nl)   
           .append(tab).append("<dispatchCenterCd>").append(this.dispatchCenterCd).append("</dispatchCenterCd>").append(nl)   
           .append(tab).append("<selected>").append(this.selected).append("</selected>").append(nl)   
           .append("</UserGroupUserGridVo>");
            
        return retValue.toString();
    }
   
   /**
    * Necessary setter for population coming in from database.
    * @param loginName
    */
   public void setLOGINNM(String loginName) {
      setLoginNm(loginName);
   }
   
   /**
    * Postgres setter
    * @param loginName
    */
   public void setLoginnm(String loginName) {
      setLoginNm(loginName);
   }
   
   /**
    * Necessary setter for population coming in from database (Oracle).
    * @param fname
    */
   public void setFIRSTNM(String fname) {
      setFirstNm(fname);
   }
   
   /**
    * Necessary setter for population coming in from database (PG).
    * @param fname
    */
   public void setFirstnm(String fname) {
      setFirstNm(fname);
   }
   
   /**
    * Necessary setter for population coming in from database.
    * @param lname
    */
   public void setLASTNM(String lname) {
      setLastNm(lname);
   }
   
   /**
    * Necessary setter for population coming in from database (PG).
    * @param lname
    */
   public void setLastnm(String lname) {
      setLastNm(lname);
   }
   
   /**
    * Necessary setter for population coming in from database.
    * @param cd
    */
   public void setPRIMARYORGCD(String cd) {
      setPrimaryOrgCd(cd);
   }
   
   /**
    * Necessary setter for population coming in from database.
    * @param cd
    */
   public void setPrimaryorgcd(String cd) {
      setPrimaryOrgCd(cd);
   }
   
   /**
    * Necessary setter for population coming in from database.
    * @param cd
    */
   public void setDISPATCHCENTERCD(String cd) {
      setDispatchCenterCd(cd);
   }
   
   /**
    * Necessary setter for population coming in from database.
    * @param cd
    */
   public void setDispatchcentercd(String cd) {
      setDispatchCenterCd(cd);
   }
   
   /**
    * Necessary setter for population coming in from database.
    * Oracle returns all uppercase characters for alias names.
    * @param id
    */
   public void setID(BigDecimal id) {
      setId(id.longValue());
   }
   
   /**
    * Postgres returns all lowercase characters for alias names.
    * @param id Our ids in Postgres are stored as BigInts
    */
   public void setid(BigInteger id) {
      setId(id.longValue());
   }
   
   /**
    * Get the userId
    * @return the userId
    */
   public Long getUserId() {
		return userId;
	}

	/**
	 * Get the userId
	 * @param userId
	 */
   public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Returns the userRoles.
	 *
	 * @return 
	 *		the userRoles to return
	 */
	public Collection<SystemRoleVo> getUserRoles() {
		return userRoles;
	}

	/**
	 * Sets the userRoles.
	 *
	 * @param userRoles 
	 *			the userRoles to set
	 */
	public void setUserRoles(Collection<SystemRoleVo> userRoles) {
		this.userRoles = userRoles;
	}
}
