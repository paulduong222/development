package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupUser;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupUserImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentQuestionImpl;
import gov.nwcg.isuite.core.domain.impl.UserGroupImpl;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.IncidentGroupFilter;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupUserDao;
import gov.nwcg.isuite.core.persistence.IncidentQuestionDao;
import gov.nwcg.isuite.core.persistence.UserGroupDao;
import gov.nwcg.isuite.core.service.IncidentGroupService;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserGroupGridVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class IncidentGroupServiceImpl extends BaseService implements IncidentGroupService {
	private IncidentGroupDao incidentGroupDao=null;
	private IncidentGroupUserDao incidentGroupUserDao=null;

	public IncidentGroupServiceImpl(){
		super();
	}

	public void initialization(){
		incidentGroupDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
		incidentGroupUserDao = (IncidentGroupUserDao)super.context.getBean("incidentGroupUserDao");
	}

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.IncidentGroupService#deleteIncidentGroup(java.lang.Long)
    */
	public Long deleteIncidentGroup(Long incidentGroupId) throws ServiceException {
		if (incidentGroupId == null) 
			throw new ServiceException("incidentGroupId cannot be null");

		try {
			IncidentGroup entity = incidentGroupDao.getById(incidentGroupId, IncidentGroupImpl.class);
			
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentGroup[id="+incidentGroupId+"]");
				
			incidentGroupDao.delete(entity);
			return incidentGroupId;
		}catch ( Exception e) {
			super.handleException(e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService#getAvailableIncidents()
	 */
	public Collection<IncidentGridVo> getAvailableIncidents(Long incidentGroupId, Long workAreaId, IncidentFilter filter) throws ServiceException {
		Long loggedInUserId = null;
		//If the user has a Data Steward role, only retrieve restricted Incidents to which they have access.
	   if(this.getUserSessionVo().getPrivilegedUser() == false) {
		   loggedInUserId = this.getUserSessionVo().getUserId();
		}
	   try {
			return incidentGroupDao.getAvailableIncidents(incidentGroupId, workAreaId, 
					filter, loggedInUserId, super.getUserSessionVo().getPrivilegedUser());
		}
		catch ( Exception e ) {
			super.handleException(e);
		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService#getGrid(gov.nwcg.isuite.core.filter.IncidentGroupFilter, java.lang.Boolean)
	 */
	public Collection<IncidentGroupGridVo> getGrid(IncidentGroupFilter filter, Boolean isPrivilegedUser) throws ServiceException {
		
		Collection<IncidentGroupGridVo> vos = new ArrayList<IncidentGroupGridVo>();
		try {
			vos = incidentGroupDao.getGrid(filter, isPrivilegedUser);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
		//############################################################
		//TODO: 
		//THE COMMENTED OUT CODE BELOW IS TO BE APPLIED TO THE INCIDENT DROP DOWN LIST IN THE TOP RIGHT
      //CORNER OF THE APPLICATION SCREEN.  THAT LIST WILL INCLUDE INCIDENTS AS WELL AS INCIDENT GROUPS. -dbudge
		//############################################################
		
		/************************ Begin **************************************/
		/*Long loggedInUserId = ((UserSessionVo)super.context.getBean("userSessionVo")).getUserId();
		Boolean remove = new Boolean(false);
		Collection<IncidentGroupGridVo> groupsToRemove = new ArrayList<IncidentGroupGridVo>();
		
		for(IncidentGroupGridVo iggvo : vos) {
		   Collection<UserGridVo> assignedUsers = this.getAssignedUsers(iggvo.getId(), new UserFilterImpl());
		   remove = true;
		   for(UserGridVo ugvo : assignedUsers) {
		      if(loggedInUserId.equals(ugvo.getId())) {
		         remove = false;
		         break;//We've determined that this group should NOT be removed, so break out of the inner loop.
		      }
		   }
		   //Remove groups that don't contain the logged in user in the group access list.
		   if(remove == true) {
		      groupsToRemove.add(iggvo);
		   }
		}
		vos.removeAll(groupsToRemove);*/
		/************************ End ***************************************/
		
		return vos;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService#getById(java.lang.Long)
	 */
	public IncidentGroupVo getById(Long id) throws ServiceException {
		if (id == null) 
			throw new ServiceException("incidentGroupId is required.");

		try {
			
			IncidentGroup entity = incidentGroupDao.getById(id, IncidentGroupImpl.class);
			
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentGroup[id="+id+"]");
			
			IncidentGroupVo vo = IncidentGroupVo.getInstance(entity, true);
			
			if(null != vo.getIncidentVos()){
				IncidentQuestionDao iqDao = (IncidentQuestionDao)context.getBean("incidentQuestionDao");
				Collection<IncidentVo> newIncidentList = new ArrayList<IncidentVo>();
				for(IncidentVo ivo : vo.getIncidentVos()){
					IncidentVo newIncidentVo = ivo;
					
					Collection<IncidentQuestionVo> airTravelQuestions = iqDao.getAirTravelQuestions(ivo.getId(), false);
					if(null != airTravelQuestions && airTravelQuestions.size()>0){
						for(IncidentQuestionVo iqvo : airTravelQuestions){
							if(iqvo.getVisible())
								newIncidentVo.getAirTravelQuestions().add(iqvo);
						}
					}
					Collection<IncidentQuestionVo> checkInQuestions = iqDao.getCheckInQuestions(ivo.getId(), false);
					if(null != checkInQuestions && checkInQuestions.size()>0){
						for(IncidentQuestionVo iqvo : checkInQuestions){
							if(iqvo.getVisible())
								newIncidentVo.setCheckInQuestions(checkInQuestions);
						}
					}
					
					newIncidentList.add(newIncidentVo);
				}
				vo.setIncidentVos(newIncidentList);
			}
			
			return vo;
		}
		catch ( Exception e ) {
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService#save(gov.nwcg.isuite.core.vo.IncidentGroupVo)
	 */
	public IncidentGroupVo save(IncidentGroupVo vo) throws ServiceException {
		IncidentGroup entity = null;

		try {
			if (null == vo) 
				throw new ServiceException("incidentGroupVo cannot be null.");


			if( (null!=vo.getId()) && (vo.getId()>0) )
				entity=incidentGroupDao.getById(vo.getId(), IncidentGroupImpl.class);

			if(null == entity){
			   /*
			    *  Check if group name is unique
			    */
			   //if(incidentGroupDao.getByGroupName(vo.getGroupName().toUpperCase(), vo.getWorkAreaVo().getId()) != null) {
			    //  super.handleException(ErrorEnum._0200_DUPLICATE_INCIDENT_GROUP_NAME, vo.getGroupName().toUpperCase());
			   //}
			}else{
			   /*
			    * if group name changed, verify new one is unique
			    */
			   if(!vo.getGroupName().trim().equals(entity.getGroupName().trim())){
			     // if(incidentGroupDao.getByGroupName(vo.getGroupName(),vo.getWorkAreaVo().getId()) != null) {
			      //   super.handleException(ErrorEnum._0200_DUPLICATE_INCIDENT_GROUP_NAME, vo.getGroupName().toUpperCase());
			      //}
			   }
			}
			
			entity = IncidentGroupVo.toEntity(entity, vo, true);
			
			incidentGroupDao.save(entity);
			
			incidentGroupDao.flushAndEvict(entity);
			
			return this.getById(entity.getId());
		} catch ( Exception e ) {
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService#getAssignedIncidents(java.lang.Long, gov.nwcg.isuite.core.filter.IncidentFilter)
	 */
	public Collection<IncidentGridVo> getAssignedIncidents(Long incidentGroupId, IncidentFilter filter) throws ServiceException {
		try{
			
			if( (null == incidentGroupId) || (incidentGroupId<1) )
				throw new ServiceException("Incident Group id cannot be null");
			
			return incidentGroupDao.getAssignedIncidents(incidentGroupId, filter, super.getUserSessionVo().getPrivilegedUser());
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService#addIncident(java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public Boolean addIncident(Long groupId, Long incidentId, String questionType) throws ServiceException {
		try{
			
		   if(incidentGroupDao.getIncidentGroupsIncidentId(incidentId) != null) {
		      //The system must prevent the user from adding the same Incident to multiple Incident Groups. 
		      //(Applies to Use Cases 21.0003, 21.0004, 21.0005 and 21.0006.)
		      return false;
		   }
		   
			IncidentGroup entity = incidentGroupDao.getById(groupId, IncidentGroupImpl.class);
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentGroup[id="+groupId+"]");
			
			IncidentDao incidentDao = (IncidentDao)super.context.getBean("incidentDao");
			
			Incident incidentEntity=incidentDao.getById(incidentId,IncidentImpl.class);
			if(null==incidentEntity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident[id="+incidentId+"]");
			
			//Add all Non-Standard questions for this Incident to this Incident Group.
			IncidentQuestionDao incidentQuestionDao = (IncidentQuestionDao)super.context.getBean("incidentQuestionDao");
			Collection<IncidentQuestion> incidentQuestions = incidentQuestionDao.getNonStandardQuestionEntities(incidentId, questionType);
			/* dan 9/8/2011 switch to isw_incident_group_question
			for(IncidentQuestion iq : incidentQuestions) {
			   iq.setIncidentGroup(entity);
			   incidentQuestionDao.save(iq);
			}
			*/
			
			entity.getIncidents().add(incidentEntity);
			
			incidentGroupDao.save(entity);
			
			incidentGroupDao.flushAndEvict(entity);
			entity = incidentGroupDao.getById(groupId, IncidentGroupImpl.class);
			
			//Add all Non-Standard questions to each Incident in the group.
			for(IncidentQuestion iq : incidentQuestions) {
			   for(Incident i : entity.getIncidents()) {
			      if(iq.getIncident().getId() != i.getId()) {
			         //Avoid adding duplicate questions to an Incident.
			         if(incidentQuestionDao.getByQuestionIdAndIncidentId(iq.getQuestion().getId(), i.getId()) == null) {
			            IncidentQuestion newIq = new IncidentQuestionImpl();
			            newIq.setIncident(i);
			            newIq.setQuestion(iq.getQuestion());
			            newIq.setVisible(iq.isVisible());
			            newIq.setPosition(iq.getPosition());
			            incidentQuestionDao.save(newIq);
			         }
			      }
			   }
			}
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return true;
	}
	
	public Boolean addIncidents(Long groupId, Collection<Long> incidentIds) throws ServiceException {

		try{
			
			Collection<Long> ids = new ArrayList<Long>();
//			for(Object o : incidentIds){
//				Long l = TypeConverter.convertToLong(o);
//				ids.add(l);
//			}
			
			// Use safe string conversion to prevent errors on Linux OS
			Iterator iter = incidentIds.iterator();
			
			while(iter.hasNext()){
				String s = String.valueOf(iter.next());
				Long l = TypeConverter.convertToLong(s);
				ids.add(l);
			}
			

			if(ids.size()>0){
				IncidentGroup entity = incidentGroupDao.getById(groupId, IncidentGroupImpl.class);
				if(null==entity)
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentGroup[id="+groupId+"]");
				
				IncidentDao incidentDao = (IncidentDao)super.context.getBean("incidentDao");
				Collection<Incident> incidentEntities = incidentDao.getAllByIds(ids);
				
				entity.getIncidents().addAll(incidentEntities);
				
			}
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService#removeIncident(java.lang.Long, java.lang.Long)
	 */
	public void removeIncident(Long groupId, Long incidentId) throws ServiceException {
		try{
			
			IncidentGroup entity = incidentGroupDao.getById(groupId, IncidentGroupImpl.class);
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentGroup[id="+groupId+"]");
			
			IncidentDao incidentDao = (IncidentDao)super.context.getBean("incidentDao");
			
			Incident incidentEntity=incidentDao.getById(incidentId,IncidentImpl.class);
			if(null==incidentEntity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident[id="+incidentId+"]");
			
			entity.getIncidents().remove(incidentEntity);
			
			incidentGroupDao.save(entity);
			
		}catch(Exception e){
			super.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService#getAssignedUsers(java.lang.Long, gov.nwcg.isuite.core.filter.UserFilter)
	 */
	public Collection<UserGridVo> getAssignedUsers(Long groupId,UserFilter filter) throws ServiceException{
		try{
			if( (null == groupId) || (groupId<1) )
				throw new ServiceException("Incident Group Id cannot be null");
			
			return incidentGroupDao.getAssignedUsers(groupId, filter);
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService#getAvailableUsers(java.lang.Long, java.lang.Long)
	 */
	public Collection<UserGridVo> getAvailableUsers(Long groupId, Long workAreaId, UserFilter filter) throws ServiceException{
		try{
			if( (null == groupId) || (groupId<1) )
				throw new ServiceException("Incident Group Id cannot be null");
			if( (null == workAreaId) || (workAreaId<1) )
				throw new ServiceException("WorkArea Id cannot be null");
			
			return incidentGroupDao.getAvailableUsers(workAreaId,groupId, filter);
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	public void addUsers(Long groupId, Collection<UserGridVo> vos/*, Collection<SystemRoleVo> roleVos*/) throws ServiceException{
		try{
			Collection<IncidentGroupUser> iguEntities = new ArrayList<IncidentGroupUser>();
			
			IncidentGroup entity = incidentGroupDao.getById(groupId, IncidentGroupImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentGroup");
			
			for(UserGridVo vo : vos){
				IncidentGroupUser iguEntity = new IncidentGroupUserImpl();
				
				UserVo userVo = new UserVo();
				userVo.setId(vo.getId());
				
				iguEntity.setId(incidentGroupDao.getIncidentGroupUserId(vo.getId(), groupId));
				iguEntity.setUser(UserVo.toEntity(null, userVo, false, iguEntity));
				iguEntity.setIncidentGroup(entity);
//				iguEntity.setIncidentGroupUserRoles(SystemRoleVo.toEntityList(roleVos, false));
				
				//entity.getIncidentGroupUsers().add(iguEntity);
				iguEntities.add(iguEntity);
			}
			
			incidentGroupUserDao.saveAll(iguEntities);
			
		}catch(Exception e){
			super.handleException(e);
		}
	}

	public void removeUsers(Long groupId,Collection<UserGridVo> vos) throws ServiceException {
		
		try{
			IncidentGroup entity = incidentGroupDao.getById(groupId, IncidentGroupImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentGroup");

			Collection<IncidentGroupUser> removeEntities = new ArrayList<IncidentGroupUser>();
			for(IncidentGroupUser iguEntity : entity.getIncidentGroupUsers()){
				for(UserGridVo userGridVo : vos){
					if(iguEntity.getUserId().compareTo(userGridVo.getId())==0)
						removeEntities.add(iguEntity);
				}
			}
			
			entity.getIncidentGroupUsers().removeAll(removeEntities);
			for(IncidentGroupUser iguEntity : removeEntities){
				incidentGroupUserDao.delete(iguEntity);
			}
		}catch(Exception e){
			super.handleException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService#saveUserRoles(java.lang.Long, java.lang.Long, java.util.Collection)
	 */
	public void saveUserRoles(Long groupId, Long userId, Collection<SystemRoleVo> roleVos) throws ServiceException{
		try{
			
		}catch(Exception e){
			super.handleException(e);
		}
	}

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.IncidentGroupService#getUserById(java.lang.Long)
    */
	public UserGridVo getUserById(Long userId, Long groupId) throws ServiceException {
	   if(groupId == null) 
	      throw new ServiceException("incidentGroupId is required.");
	   if(userId == null)
	      throw new ServiceException("userId is required.");
	   
	   try {

	      Long incidentGroupUserId = incidentGroupDao.getIncidentGroupUserId(userId, groupId);
	      IncidentGroupUser entity = incidentGroupUserDao.getById(incidentGroupUserId, IncidentGroupUserImpl.class);

	      if(null==entity)
	         super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentGroupUser[id="+incidentGroupUserId+"]");

	      UserGridVo userGridVo = new UserGridVo();
	      userGridVo.setId(entity.getUser().getId());
	      userGridVo.setFirstName(entity.getUser().getFirstName());
	      userGridVo.setLastName(entity.getUser().getLastName());
	      userGridVo.setHomeUnitCode(entity.getUser().getHomeUnit().getUnitCode());
//	      userGridVo.setUserRoles(SystemRoleVo.getInstances(entity.getIncidentGroupUserRoles(), true));
	      return userGridVo;

	   } catch ( Exception e ) {
	      super.handleException(e);
	   }
	   return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentGroupService#getAvailableUserGroups()
	 */
	public Collection<UserGroupGridVo> getAvailableUserGroups() throws ServiceException {
	   try {
	      return incidentGroupDao.getAvailableUserGroups();
	   }
	   catch ( Exception e ) {
	      super.handleException(e);
	   }
	   return null;
	}

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.IncidentGroupService#assignUserGroupUsers(java.lang.Long, java.lang.Long)
    */
	public void assignUserGroupUsers(Long incidentGroupId, Long userGroupId) throws ServiceException {
	   UserGroupDao userGroupDao = (UserGroupDao)super.context.getBean("userGroupDao");
	   UserGroup userGroup = null;
	   try {
	      userGroup = userGroupDao.getById(userGroupId, UserGroupImpl.class);
	      Collection<UserGroupUser> userGroupUsers = userGroup.getUserGroupUsers();
	      Collection<UserGridVo> userGridVos = null;
	      for(UserGroupUser ugu : userGroupUsers) {
	         userGridVos = new ArrayList<UserGridVo>();
	         userGridVos.add(UserGridVo.getInstance(ugu.getUser(), true));
	         //Users are saved to the IncidentGroup separately in this for loop because each user has different roles.
	         this.addUsers(incidentGroupId, userGridVos /*SystemRoleVo.getInstances(ugu.getUserGroupUserRoles(), true*/);
	      }
	   }
	   catch ( Exception e ) {
	      super.handleException(e);
	   }
	}

//	/*
//	 * (non-Javadoc)
//	 * @see gov.nwcg.isuite.core.service.IncidentGroupService#assignIncidentGroupRoles(java.lang.Long, java.util.Collection, java.lang.Long)
//	 */
//	public void assignIncidentGroupRoles(Long userId, Collection<Integer> incidentIds, Long incidentGroupId) throws ServiceException {
//	 //NOTE:  BlazeDS serializes an AS3 ArrayCollection of Numbers to a Java Collection of Integers.
//	   IncidentGroupUserDao igUserDao = (IncidentGroupUserDao)super.context.getBean("incidentGroupUserDao");
//	   RestrictedIncidentUserDao riUserDao = (RestrictedIncidentUserDao)super.context.getBean("restrictedIncidentUserDao");
//	   try {
//	      IncidentGroupUser igUser = igUserDao.getIncidentGroupUserByUserIdAndIncidentGroupId(userId, incidentGroupId);
//	      Collection<SystemRole> igUserRoles = new ArrayList<SystemRole>();
////	      for(SystemRole sr : igUser.getIncidentGroupUserRoles()) {
////	         igUserRoles.add(sr);
////	      }
//	      Collection<RestrictedIncidentUser> riUsers = new ArrayList<RestrictedIncidentUser>();
//	      riUsers = riUserDao.getRIUsersByUserIdAndIncidentIds(igUser.getUserId(), IntegerUtility.convertToLongs(incidentIds));
//	      for(RestrictedIncidentUser riUser : riUsers) {
//	         riUser.setUserRoles(igUserRoles);//Replace the RI User's Roles with the Incident Group roles.
//	      }
//	      riUserDao.saveAll(riUsers);
//	   }
//	   catch ( PersistenceException e ) {
//	      super.handleException(e);
//	   }
//	}

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.IncidentGroupService#isUserAssignedToIncidentsInTheGroup(java.lang.Long, java.util.Collection)
    */
   public Boolean isUserAssignedToIncidentsInTheGroup(Long userId, Collection<Integer> incidentIds) throws ServiceException {
      //NOTE:  BlazeDS serializes an AS3 ArrayCollection of Numbers to a Java Collection of Integers.
      IncidentDao incidentDao = (IncidentDao)super.context.getBean("incidentDao");
      Collection<Incident> incidentsInGroup;
      try {
         incidentsInGroup = incidentDao.getAllByIds(IntegerUtility.convertToLongs(incidentIds));
         for(Incident inc : incidentsInGroup) {
            Collection<RestrictedIncidentUser> rius = new ArrayList<RestrictedIncidentUser>();
            rius = inc.getRestrictedIncidentUsers();
            for(RestrictedIncidentUser riu : rius) {
               if(userId.equals(riu.getUserId())) {
                  return true;
               }
            }
         }
      }
      catch ( PersistenceException e ) {
         super.handleException(e);
      }

      return false;
   }
   
//   /*
//    * (non-Javadoc)
//    * @see gov.nwcg.isuite.core.service.IncidentGroupService#pointToIGRolesIfNecessary(java.lang.Long, java.lang.Long, java.lang.Long)
//    */
//   public Collection<String> pointToIGRolesIfNecessary(Long userId, Long restrictedIncidentId, Long workAreaId) throws ServiceException {
//      RestrictedIncidentUserDao riUserDao = (RestrictedIncidentUserDao)super.context.getBean("restrictedIncidentUserDao");
//      SystemRolePermDao systemRolePermDao = (SystemRolePermDao)super.context.getBean("systemRolePermDao");
//      Collection<Long> userIdsInRIAccessList = new ArrayList<Long>();
//      Collection<IncidentGroup> incidentGroups = new ArrayList<IncidentGroup>();
//      try {
//         userIdsInRIAccessList = riUserDao.getUserIdsByIncidentIdAndUserId(restrictedIncidentId, userId);
//         if(userIdsInRIAccessList != null && userIdsInRIAccessList.size() > 0) {
//            incidentGroups = this.incidentGroupDao.getByWorkAreaId(workAreaId);
//            for(IncidentGroup ig : incidentGroups) {
//               for(Incident i : ig.getIncidents()) {
//                  if(i.getId().equals(restrictedIncidentId)) {
//                     for(IncidentGroupUser igu : ig.getIncidentGroupUsers()) {
//                        if(igu.getUserId().equals(userId)) {
//                           Collection<SystemRole> iguRoles = igu.getIncidentGroupUserRoles();
//                           //######################################################################################################################
//                           //## TODO:  Associate a KEY with the returned collection indicating the permission type, i.e., WA, RI, or IG. -dbudge ##
//                           //######################################################################################################################
//                           return systemRolePermDao.getBySystemRoles(iguRoles);//We found what we are looking for.  No need to continue looping.
//                        }
//                     }
//                  }
//               }
//            }
//         } 
//      }
//      catch ( PersistenceException e ) {
//         super.handleException(e);
//      }
//      return null;
//   }
}
