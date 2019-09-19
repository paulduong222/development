package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.AccountCode;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.RestrictedIncidentUserImpl;
import gov.nwcg.isuite.core.filter.IncidentAccountCodeFilter;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentFilterImpl;
import gov.nwcg.isuite.core.persistence.AccountCodeDao;
import gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentQuestionDao;
import gov.nwcg.isuite.core.persistence.RestrictedIncidentUserDao;
import gov.nwcg.isuite.core.persistence.UserGroupUserDao;
import gov.nwcg.isuite.core.persistence.WorkAreaDao;
import gov.nwcg.isuite.core.service.IncidentService;
import gov.nwcg.isuite.core.vo.AccountCodeVo;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentPicklistVo;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.RegionCodeVo;
import gov.nwcg.isuite.core.vo.RestrictedIncidentUserVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserGroupPicklistVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Service layer area used for accessing incident functionality. 
 * 
 * @author bsteiner
 */
public class IncidentServiceImpl extends BaseService implements IncidentService {
	private AccountCodeDao accountCodeDao;
	private IncidentAccountCodeDao incidentAccountCodeDao;
	private IncidentDao incidentDao;
	private RestrictedIncidentUserDao restrictedIncidentUserDao;

	public IncidentServiceImpl() {
		super();
	}
	
	public void initialization() {
		accountCodeDao = (AccountCodeDao)super.context.getBean("accountCodeDao");
		incidentAccountCodeDao = (IncidentAccountCodeDao)super.context.getBean("incidentAccountCodeDao");
		incidentDao = (IncidentDao)super.context.getBean("incidentDao");
		restrictedIncidentUserDao = (RestrictedIncidentUserDao)super.context.getBean("restrictedIncidentUserDao");
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.incident.IncidentService#getById(java.lang.Long)
	 */
	public IncidentVo getById(Long id) throws ServiceException {
		try {
			Incident entity = incidentDao.getById(id, IncidentImpl.class);
			IncidentVo vo = null;
			if (entity != null) {
				vo = IncidentVo.getInstance(entity, true);
			}

			// redo the incident question table and add constraint for incidentId
			// include the following info as part of the incident mapping
			IncidentQuestionDao iqDao = (IncidentQuestionDao)context.getBean("incidentQuestionDao");
			Collection<IncidentQuestionVo> airTravelQuestions = iqDao.getAirTravelQuestions(vo.getId(), false);
			vo.setAirTravelQuestions(new ArrayList<IncidentQuestionVo>());
			if(null != airTravelQuestions && airTravelQuestions.size()>0){
				for(IncidentQuestionVo iqvo : airTravelQuestions){
					if(iqvo.getVisible())
						vo.getAirTravelQuestions().add(iqvo);
				}
			}
			Collection<IncidentQuestionVo> checkInQuestions = iqDao.getCheckInQuestions(vo.getId(), false);
			vo.setCheckInQuestions(new ArrayList<IncidentQuestionVo>());
			if(null != checkInQuestions && checkInQuestions.size()>0){
				for(IncidentQuestionVo iqvo : checkInQuestions){
					if(iqvo.getVisible()){
						vo.getCheckInQuestions().add(iqvo);
					}
				}
			}

			
			return vo;
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}catch (Exception ee){
			throw new ServiceException(ee);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService#getRestrictedIncidentUsers(java.lang.Long, gov.nwcg.isuite.core.filter.UserFilter)
	 */
	public Collection<RestrictedIncidentUserVo> getRestrictedIncidentUsers(Long restrictedIncidentId, UserFilter filter) throws ServiceException {
		try {
			if (restrictedIncidentId == null) {
				throw new ServiceException("restrictedIncidentId cannot be null");
			}
			
			Collection<RestrictedIncidentUserVo> vos = restrictedIncidentUserDao.getUsersInRestrictedIncident(restrictedIncidentId, filter);
				
			return vos;
		} catch (PersistenceException e) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.incident.IncidentService#save(gov.nwcg.isuite.domain.incident.IncidentVo)
	 */
	public IncidentVo save(IncidentVo incidentVo) throws ServiceException {
		Incident entity=null;

		try {
			if (null == incidentVo) {
				throw new ServiceException("incidentVo cannot be null.");
			}

			if( (null!=incidentVo.getId()) && (incidentVo.getId() != 0) ){
				/*
				 * Load the entity
				 */
				entity=incidentDao.getById(incidentVo.getId(), IncidentImpl.class);

				/*
				 * Determine if there are any restrictedincidentuser's being removed,
				 * and handle accordingly
				 */
				if((null != entity) && (null != entity.getRestrictedIncidentUsers()) ){
					for(RestrictedIncidentUser riuEntity : entity.getRestrictedIncidentUsers()){
						if(null != incidentVo.getRestrictedIncidentUserVos()){
							if(!IncidentVo.hasRestrictedIncidentUser(riuEntity.getId(), incidentVo.getRestrictedIncidentUserVos())){
								// need to remove it
								entity.getRestrictedIncidentUsers().remove(riuEntity);
								this.removeRestrictedUser(RestrictedIncidentUserVo.getInstance(riuEntity, false));
							}
						}else{
							// need to remove it
							entity.getRestrictedIncidentUsers().remove(riuEntity);
							this.removeRestrictedUser(RestrictedIncidentUserVo.getInstance(riuEntity, false));
						}
					}
				}
				
			}else{
				/*
				 * User is manually adding an incident.
				 * Use Case requirements -
				 * 	when manually adding an incident, the incident is restricted by default,
				 * 	associate the user creating the incident as the restrictedIncidentUser owner
				 */
				incidentVo.setRestricted(true);

				RestrictedIncidentUserVo riuVo = new RestrictedIncidentUserVo();
				riuVo.setUserVo(getUserVo());
				riuVo.setUserType(RestrictedIncidentUserTypeEnum.OWNER);

				/*
				 * Set the default restricted user roles from the user's primary roles
				 */
				riuVo.setUserRoleVos(super.getUserVo().getUserRoleVos());
				
				incidentVo.setRestrictedIncidentUserVos(new ArrayList<RestrictedIncidentUserVo>());
				incidentVo.getRestrictedIncidentUserVos().add(riuVo);
			}

			/*
			 * if adding a new incident, check for duplicate nbr and year combination
			 */
			if ( null == incidentVo.getId() || incidentVo.getId() == 0 ) {
				try {
					Date incStartDate=null;
					if(DateTransferVo.hasDateString(incidentVo.getIncStartDateTransferVo())){
						incStartDate=DateTransferVo.getDate(incidentVo.getIncStartDateTransferVo());
					}
					Incident duplicateIncident = incidentDao.getByNbrAndIncidentYear(incidentVo
							, DateUtil.getYearAsInteger(incStartDate));
					if(duplicateIncident != null) {
						super.handleException(new ServiceException(new ErrorObject(ErrorEnum._0046_DUPLICATE_INCIDENT_FOR_YEAR)));
					}
				} catch (PersistenceException e) {
					super.handleException(e);
				}
			}

			/*
			 * Before we persist the incidenVo, we need to check if there are incidentAccountCodes.accountCodes being
			 * referenced that have not yet been created.  Incident entity will cascade IncidentAccountCode's, but
			 * IncidentAccountCodes does not cascade AccountCodes, so we will need to manually create/update
			 * the accountCodes as needed.
			 */
			//this.serviceAccountCodes(incidentVo);

			/*
			 * populate the entity from the vo passed in
			 */
			entity = IncidentVo.toEntity(entity, incidentVo, true);

			if( (null!=entity.getId()) && (entity.getId()>0L))
				incidentDao.merge(entity);
			else
				incidentDao.save(entity);
			return IncidentVo.getInstance(entity, true);
			
		} catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

	private void serviceAccountCodes(IncidentVo incidentVo) throws Exception {
		Collection<IncidentAccountCodeVo> incidentAccountCodeVos = new ArrayList<IncidentAccountCodeVo>();

		if(null != incidentVo.getIncidentAccountCodeVos()){

			for( Object obj : incidentVo.getIncidentAccountCodeVos()){
				IncidentAccountCodeVo iacVo = (IncidentAccountCodeVo)obj;
				if(null != iacVo.getAccountCodeVo()){
					AccountCode acEntity = null;

					// check if the account code already exists
					acEntity = accountCodeDao.getByKey(iacVo.getAccountCodeVo().getAccountCode(), iacVo.getAccountCodeVo().getAgencyVo().getId());

					if(null==acEntity){
						// this is a new account code
						acEntity = AccountCodeVo.toEntity(acEntity, iacVo.getAccountCodeVo(), true);
						accountCodeDao.save(acEntity);
					}else{
						// is the existing account code dirty?
						boolean dirty=iacVo.getAccountCodeVo().equals(AccountCodeVo.getInstance(acEntity, true));
						if(!dirty){
							acEntity = AccountCodeVo.toEntity(acEntity, iacVo.getAccountCodeVo(), true);
							accountCodeDao.save(acEntity);
						}
					}

					iacVo.setAccountCodeVo(AccountCodeVo.getInstance(acEntity, true));
					
					incidentAccountCodeVos.add(iacVo);
				}
			}

			// update the incidentVo with new incidentAccountCodes collection
			incidentVo.setIncidentAccountCodeVos(incidentAccountCodeVos);
		}	   
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.incident.IncidentService#delete(java.lang.Long)
	 */
	public void deleteIncident(Long incidentId) throws ServiceException {
		if (incidentId == null) {
			throw new ServiceException("incidentId cannot be null");
		}
		try {
			this.delete(incidentDao.getById(incidentId, IncidentImpl.class));
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.PersistableService#delete(gov.nwcg.isuite.domain.Persistable)
	 */
	public void delete(Incident persistable) throws ServiceException {
		try {
			incidentDao.delete(persistable);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.incident.IncidentService#getAllIncidents(gov.nwcg.isuite.domain.incident.IncidentFilter)
	 */
	public Collection<IncidentGridVo> getGrid(IncidentFilter filter) throws ServiceException {
		try {
			filter.setCurrentUserId(super.getUserVo().getId());
			
			Collection<IncidentGridVo> vos = incidentDao.getGrid(filter, super.getUserSessionVo().getPrivilegedUser());
			
			//TODO:  Temporary if statement until deletable criteria can be determined. -dbudge
			if(vos == null) {
			   return null;
			}
			
			Collection<IncidentGridVo> returnVos = new ArrayList<IncidentGridVo>();
			
			for(IncidentGridVo vo : vos){
				if(vo.getSharedUser()){
					// get the shared user perms
					Collection<String> perms = incidentDao.getPermissionsForSharedUser(vo.getId(), super.getUserSessionVo().getUserId());
					
					vo.setRestrictedAccessPermissions(perms);
				}
				returnVos.add(vo);
			}
			
			return returnVos;
		}
		catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService#getIncidentGridVo(java.lang.Long)
	 */
	public IncidentGridVo getIncidentGridVo(Long incidentId) throws ServiceException {
		try{
			IncidentFilter filter = new IncidentFilterImpl();
			filter.setId(incidentId);
			filter.setCurrentUserId(super.getUserVo().getId());
			
			Collection<IncidentGridVo> vos = incidentDao.getGrid(filter, super.getUserSessionVo().getPrivilegedUser());
			if( (null!=vos) && (vos.size()>0) ){
				IncidentGridVo vo = vos.iterator().next();
				return vo;
			}
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.incident.IncidentService#restrictIncident(java.lang.Long)
	 */
	public void restrictIncident(Long incidentId) throws ServiceException {
		try {
			Incident entity = incidentDao.getById(incidentId);

			if (entity != null) {

				if(entity.getRestricted()){
					throw new ServiceException("Incident is already restricted");
					//throw new ServiceException(new ErrorObject(ErrorEnum.ERROR_GENERAL,"Incident is already restricted"));
				}
				
				entity.setRestricted(true);

				RestrictedIncidentUser riuEntity = new RestrictedIncidentUserImpl();
				riuEntity.setIncident(entity);
				riuEntity.setUser(UserVo.toEntity(null, getUserVo(), false));
//				riuEntity.setUserRoles(SystemRoleVo.toEntityList(getUserVo().getUserRoleVos(), true));
				riuEntity.setUserType(RestrictedIncidentUserTypeEnum.OWNER);

				if(null == entity.getRestrictedIncidentUsers())
					entity.setRestrictedIncidentUsers(new ArrayList<RestrictedIncidentUser>());

				entity.getRestrictedIncidentUsers().add(riuEntity);

				incidentDao.save(entity);
	
				this.syncUserRestrictedWorkArea(getUserVo().getId());
			}
			else {
				throw new ServiceException("Unable to retrieve the incident.");
			}
		}
		catch ( PersistenceException pe ) {
			throw new ServiceException(pe);
		}catch(ServiceException se){
			throw se;
		}catch( Exception e){
			throw new ServiceException(e);
		}
	}

	private void syncUserRestrictedWorkArea(Long userId) throws Exception {
		if(super.isEnterprise()){
			WorkAreaDao waDao = (WorkAreaDao)context.getBean("workAreaDao");
			
			waDao.syncUserRestrictedWorkArea(userId);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.incident.IncidentService#getIncidentsForPicklist()
	 */
	public Collection<IncidentPicklistVo> getPicklist(IncidentFilter filter) throws ServiceException{
		try {
			return incidentDao.getIncidentPickList(filter);
		} catch (PersistenceException e) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.service.incident.IncidentService#unrestrictIncident(java.lang.Long)
	 */
	public void unrestrictIncident(Long incidentId) throws ServiceException {
		try {
			IncidentGroupDao incidentGroupDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
			Incident incident = incidentDao.getById(incidentId);

			if (incident != null) {
				
				// check if there users assigned to the restricted incident
//				if(incident.getRestrictedIncidentUsers().size()>1){
//					super.handleException(ErrorEnum._900009_CANNOT_UNRESTRICT_WITH_USERS_ASSIGNED);
//				}
				
				incident.setRestricted(false);

				incidentDao.save(incident);
				
				restrictedIncidentUserDao.unRestrictIncidentUsersAndOwners(incidentId);
				
				//Remove the now unrestricted Incident from its Incident Group.
				incidentGroupDao.removeIncidentFromGroup(incident.getId());
				
			}
			else {
				throw new ServiceException("Unable to retrieve the incident.");
			}
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService#deleteIncidentAccountCode(java.lang.Long)
	 */
	public void deleteIncidentAccountCode(Long incidentAccountCodeId) throws ServiceException {
		try{
			IncidentAccountCode entity = incidentAccountCodeDao.getById(incidentAccountCodeId, IncidentAccountCodeImpl.class);

			if(null != entity){
				Incident incEntity = incidentDao.getById(entity.getIncidentId());
				
				if(null != incEntity){

					// is this incident account code the default?
					if(entity.getDefaultFlag()){
						// is this the only incident account code?
						if(incEntity.getIncidentAccountCodes() != null && incEntity.getIncidentAccountCodes().size()>1){
							// cannot delete default if there are other codes
							super.handleException(new ErrorObject(ErrorEnum.info_9912));
						}else{
							// if this is the only code, proceed with deleting it
							// proceed
						}
					}else{
						// proceed
					}
					
					if(null != entity)
						incidentAccountCodeDao.delete(entity);
				}else{
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident");
				}
			}else{
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentAccountCode");
			}
		}catch(Exception e){
			super.handleException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService#getRestrictedIncidentUserVo(java.lang.Long)
	 */
	public RestrictedIncidentUserVo getRestrictedIncidentUserVo(Long riuId) throws ServiceException {

		try{
			RestrictedIncidentUser entity = restrictedIncidentUserDao.getById(riuId);
			
			if(null != entity)
				return RestrictedIncidentUserVo.getInstance(entity, true);
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService#removeRestrictedUsers(java.util.Collection)
	 */
	public void removeRestrictedUsers(Collection<RestrictedIncidentUserVo> vos) throws ServiceException {
		try{
			if(null != vos){
				for(RestrictedIncidentUserVo vo : vos){
					removeRestrictedUser(vo);
					this.syncUserRestrictedWorkArea(vo.getUserVo().getId());
				}
			}
		}catch(Exception e){
			super.handleException(e);
		}
	}

	public void removeRestrictedUser(RestrictedIncidentUserVo vo) throws ServiceException {
		try{
			if(null != vo){
				RestrictedIncidentUser entity = restrictedIncidentUserDao.getById(vo.getId());
				if(null != entity){
//					if(null != entity.getUserRoles()){
//						entity.getUserRoles().clear();
//					}
					restrictedIncidentUserDao.delete(entity);
					this.syncUserRestrictedWorkArea(entity.getUserId());
				}else
					super.handleException(new ServiceException(new ErrorObject(ErrorEnum._900001_ENTITY_NOT_FOUND,"RestrictedIncidentUser")));
			}
		}catch(Exception e){
			super.handleException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService#addRestrictedUsers(java.lang.Long, java.util.Collection)
	 */
	public UserVo addRestrictedUsers(Long incidentId,Collection<RestrictedIncidentUserVo> vos) throws ServiceException{
		Incident entity = null;
		
		try {
			if( (null!=incidentId) && (incidentId > 0L) ){
				
				entity = incidentDao.getById(incidentId);

				//Prevent duplicate user add attempt.
				for(RestrictedIncidentUser riUser : entity.getRestrictedIncidentUsers()) {
					for(RestrictedIncidentUserVo riUserVo : vos) {
						if(riUserVo.getUserVo().getId().equals(riUser.getUser().getId())) {
							return riUserVo.getUserVo();
						}
					}
				}
				Collection<RestrictedIncidentUser> entities = RestrictedIncidentUserVo.toEntityList(null, vos, true,entity);
				
				restrictedIncidentUserDao.saveAll(entities);
				
				for(RestrictedIncidentUserVo riuvo : vos){
					this.syncUserRestrictedWorkArea(riuvo.getUserVo().getId());
				}
			}
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService#addRestrictedUserGroupUsers(java.lang.Long, java.util.Collection)
	 */
	public void addRestrictedUserGroupUsers(Long incidentId,Collection<UserGroupPicklistVo> vos) throws ServiceException {
		Incident entity=null;

		try {
			Collection<Long> ids = new ArrayList<Long>();
			
			if(null != vos){
				for(UserGroupPicklistVo ugpVo : vos){
					ids.add(ugpVo.getId());
				}
			}
			if( (null!=incidentId) && (incidentId > 0L) ){
				/*
				 * Load the entity
				 */
				entity=incidentDao.getById(incidentId, IncidentImpl.class);
			}else{
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND, "Incident");
			}
			
			if(null == entity.getRestrictedIncidentUsers()){
				entity.setRestrictedIncidentUsers(new ArrayList<RestrictedIncidentUser>());
			}

			/*
			 * Grab all users assigned to the user groups with the supplied ids
			 */
			UserGroupUserDao uguDao = (UserGroupUserDao)context.getBean("userGroupUserDao");
			Collection<UserGroupUser> uguEntities = uguDao.getUserGroupUsers(ids);
			
			if(null != uguEntities){
				/*
				 * Loop through the user group users, add to restrictedIncidentUsers if
				 * not already added.
				 */
				Collection<RestrictedIncidentUser> riUsers = new ArrayList<RestrictedIncidentUser>();
				riUsers.addAll(entity.getRestrictedIncidentUsers());
				for(UserGroupUser uguEntity : uguEntities){
					boolean isRiuUser=false;
					for(RestrictedIncidentUser riuEntity :  riUsers){
						if(riuEntity.getUserId().equals(uguEntity.getUserId()))
							isRiuUser=true;
					}
					
					if(!isRiuUser){
						RestrictedIncidentUser newRiuEntity = new RestrictedIncidentUserImpl();
						newRiuEntity.setIncident(entity);
						newRiuEntity.setUserType(RestrictedIncidentUserTypeEnum.USER);
						newRiuEntity.setUser(uguEntity.getUser());
//						Collection<SystemRole> roleEntities = new ArrayList<SystemRole>();
//						for(SystemRole srEntity : uguEntity.getUserGroupUserRoles()){
//							SystemRole roleEntity = new SystemRoleImpl();
//							roleEntity.setId(srEntity.getId());
//							roleEntities.add(roleEntity);
//						}
//						newRiuEntity.setUserRoles(roleEntities);
						
						entity.getRestrictedIncidentUsers().add(newRiuEntity);
					}
					
					this.syncUserRestrictedWorkArea(uguEntity.getUserId());
				}
			}
			
			incidentDao.save(entity);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			super.handleException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService#saveRestrictedUserRoles(java.lang.Long, java.util.Collection)
	 */
	public RestrictedIncidentUserVo saveRestrictedUserRoles(Long restrictedIncidentUserId,Collection<SystemRoleVo> vos) throws ServiceException {
		RestrictedIncidentUser entity = null;
		
		try{
			entity = restrictedIncidentUserDao.getById(restrictedIncidentUserId);
			
			if(null != entity){
				
//				if((null != entity.getUserRoles())){// && (entity.getUserRoles().size()>0) ){
//					entity.getUserRoles().clear();
//					entity.getUserRoles().addAll(SystemRoleVo.toEntityList(vos, true));
//				}
				
				restrictedIncidentUserDao.save(entity);

				entity = restrictedIncidentUserDao.getById(entity.getId());
				
				RestrictedIncidentUserVo riuVo = RestrictedIncidentUserVo.getInstance(entity, true);
				
				return riuVo;
			}else{
				super.handleException(new Exception("Unable to get restrictedIncidentUser entity"));
			}
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService#getAccountCode(gov.nwcg.isuite.core.vo.AgencyVo, java.lang.String)
	 */
	public AccountCodeVo getAccountCode(AgencyVo vo, String code) throws ServiceException {
		try{
			AccountCode entity = accountCodeDao.getByKey(code, vo.getId());
		
			if(null != entity)
				return AccountCodeVo.getInstance(entity, true);
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService#removeIncidentAccountCode(gov.nwcg.isuite.core.vo.IncidentAccountCodeVo)
	 */
	public void removeIncidentAccountCode(IncidentAccountCodeVo vo) throws ServiceException{
		try{
			IncidentAccountCode entity = incidentAccountCodeDao.getById(vo.getId(), IncidentAccountCodeImpl.class);

			if(null != entity){
				Incident incEntity = incidentDao.getById(entity.getIncidentId());
				
				if(null != incEntity){

					// is this incident account code the default?
					if(entity.getDefaultFlag()){
						// is this the only incident account code?
						if(incEntity.getIncidentAccountCodes() != null && incEntity.getIncidentAccountCodes().size()>1){
							// cannot delete default if there are other codes
							super.handleException(new ErrorObject(ErrorEnum.info_9912));
						}else{
							// if this is the only code, proceed with deleting it
							// proceed
						}
					}else{
						// proceed
					}
					
					incidentAccountCodeDao.deleteBySql(vo.getId());
				}else{
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident");
				}
			}else{
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentAccountCode");
			}
		}catch(Exception e){
			super.handleException(e);
		}
	}

	public IncidentAccountCodeVo getIncidentAccountCode(Long id) throws ServiceException {
		
		try{
			IncidentAccountCode entity = incidentAccountCodeDao.getById(id, IncidentAccountCodeImpl.class);
			
			if(null != entity)
				return IncidentAccountCodeVo.getInstance(entity, true);
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService#getIncidentAccountCodes(java.lang.Long)
	 */
	public Collection<IncidentAccountCodeVo> getIncidentAccountCodes(Long incidentId, IncidentAccountCodeFilter filter) throws ServiceException {
	
		try{
			return this.incidentAccountCodeDao.getIncidentAccountCodesByIncidentId(incidentId, filter);
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentService#saveIncidentAccountCode(java.lang.Long, gov.nwcg.isuite.core.vo.IncidentAccountCodeVo)
	 */
	public IncidentAccountCodeVo saveIncidentAccountCode(Long incidentId, IncidentAccountCodeVo vo) throws ServiceException {
		
		try{
			Incident incEntity = incidentDao.getById(incidentId);
            if(null == incEntity)
            	super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Incident[id="+incidentId+"]");

			IncidentAccountCode iacEntity = null;
			AccountCode acEntity = null;

			if( (null == vo.getId()) || (vo.getId() < 1) ){
					Boolean alreadyExists=false;
					for(IncidentAccountCode iac : incEntity.getIncidentAccountCodes()){
						if(
							(iac.getAccountCode().getAccountCode().toUpperCase().equals(vo.getAccountCodeVo().getAccountCode().toUpperCase()))){
								//&&
							//(iac.getAccountCode().getAgency().getId().compareTo(vo.getAccountCodeVo().getId())==0) )){
							alreadyExists=true;
						}
					}
					
					if(alreadyExists){
						super.handleException(ErrorEnum.DUPLICATE_INCIDENT_ACCOUNT_CODE);
					}else{
						iacEntity = new IncidentAccountCodeImpl();
						
						// force defaultFlag = true if there are no other incidentaccountcodes
						if((null==incEntity.getIncidentAccountCodes() || incEntity.getIncidentAccountCodes().size()<1))
							iacEntity.setDefaultFlag(true);
						else
							iacEntity.setDefaultFlag(vo.getDefaultFlag());
						//acEntity.getIncidentAccountCodes().add(iacEntity);
						iacEntity.setAccountCode(AccountCodeVo.toEntity(null, vo.getAccountCodeVo(), true, iacEntity));
						iacEntity.setIncident(incEntity);
						
						incidentAccountCodeDao.save(iacEntity);
						
						// if this iac is now the default, update the others to false
						if(iacEntity.getDefaultFlag()){
							incidentAccountCodeDao.removeOtherDefault(incEntity.getId(),iacEntity.getId());
						}
					}
				//}
			}else{
				
				iacEntity = incidentAccountCodeDao.getById(vo.getId(), IncidentAccountCodeImpl.class);
				
				Boolean alreadyExists=false;
				for(IncidentAccountCode iac : incEntity.getIncidentAccountCodes()){
					if(
						(iac.getAccountCode().getAccountCode().equals(vo.getAccountCodeVo().getAccountCode())
							//&&
						//(iac.getAccountCode().getAgency().getId().compareTo(vo.getAccountCodeVo().getAgencyVo().getId())==0) )
						    &&
						 (iac.getId().compareTo(vo.getId()) != 0 ) )
						){
						alreadyExists=true;
					}
				}
				
				if(alreadyExists){
					super.handleException(ErrorEnum.DUPLICATE_INCIDENT_ACCOUNT_CODE);
				}
				
				iacEntity.setDefaultFlag(vo.getDefaultFlag());
				iacEntity.getAccountCode().setAccountCode(vo.getAccountCodeVo().getAccountCode().toUpperCase());
				iacEntity.getAccountCode().setAgency(AgencyVo.toEntity(null, vo.getAccountCodeVo().getAgencyVo(), false));
				if(null != vo.getAccountCodeVo().getRegionUnitVo() && vo.getAccountCodeVo().getRegionUnitVo().getId() > 0)
					iacEntity.getAccountCode().setRegionUnit(RegionCodeVo.toEntity(null, vo.getAccountCodeVo().getRegionUnitVo(), false));
				else
					iacEntity.getAccountCode().setRegionUnit(null);
				
				incidentAccountCodeDao.save(iacEntity);

				// if this iac is now the default, update the others to false
				if(iacEntity.getDefaultFlag()){
					incidentAccountCodeDao.removeOtherDefault(incEntity.getId(),iacEntity.getId());
				}
				
				// check accountCode
				/*
				acEntity = accountCodeDao.getByKey(vo.getAccountCodeVo().getAccountCode(), vo.getAccountCodeVo().getAgencyVo().getId());

				if(null == acEntity){
					iacEntity.setDefaultFlag(vo.getDefaultFlag());
					iacEntity.getAccountCode().setAccountCode(vo.getAccountCodeVo().getAccountCode().toUpperCase());
					iacEntity.getAccountCode().setAgency(AgencyVo.toEntity(vo.getAccountCodeVo().getAgencyVo(), false));
					if(null != vo.getAccountCodeVo().getRegionUnitVo())
						iacEntity.getAccountCode().setRegionUnit(OrganizationVo.toEntity(null, vo.getAccountCodeVo().getRegionUnitVo(), false));

					iacEntity = IncidentAccountCodeVo.toEntity(null, vo, true);
				}else{
					Long tmpId=acEntity.getId();
					
					// is this the same accountCode record (same id) ?
					if(vo.getAccountCodeVo().getId().compareTo(tmpId)==0){
						iacEntity.setDefaultFlag(vo.getDefaultFlag());
						iacEntity.getAccountCode().setAccountCode(vo.getAccountCodeVo().getAccountCode().toUpperCase());
						iacEntity.getAccountCode().setAgency(AgencyVo.toEntity(vo.getAccountCodeVo().getAgencyVo(), false));
						if(null != vo.getAccountCodeVo().getRegionUnitVo() && vo.getAccountCodeVo().getRegionUnitVo().getId() > 0)
							iacEntity.getAccountCode().setRegionUnit(OrganizationVo.toEntity(null, vo.getAccountCodeVo().getRegionUnitVo(), false));
	
						incidentAccountCodeDao.flushAndEvict(acEntity);
						incidentAccountCodeDao.save(iacEntity);

						// if this iac is now the default, update the others to false
						if(iacEntity.getDefaultFlag()){
							incidentAccountCodeDao.removeOtherDefault(incEntity.getId(),iacEntity.getId());
						}
					
					}else{
						super.handleException(ErrorEnum.DUPLICATE_INCIDENT_ACCOUNT_CODE);
					}
				}
				*/
			}

			incidentAccountCodeDao.flushAndEvict(iacEntity.getAccountCode());
			incidentAccountCodeDao.flushAndEvict(iacEntity);
		
			iacEntity = incidentAccountCodeDao.getById(iacEntity.getId(), IncidentAccountCodeImpl.class);
					
			return IncidentAccountCodeVo.getInstance(iacEntity, true);

		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}
	
	/**
	 * @param incidentId
	 * @param agencyId
	 * @param code
	 * @return
	 * @throws ServiceException
	 */
	public Boolean isAccountFireCodeInUse(Long incidentId, Long agencyId, String code ) throws ServiceException {
		
		try{
			
			Incident incEntity = incidentDao.getById(incidentId);
			
			// check accountCode
			Collection<AccountCode> acList = accountCodeDao.getAllByCode(code);
			
			for(AccountCode acEntity : acList){
				if(null != acEntity){
					Boolean alreadyExistsForThisIncident=false;
					
					if(null != incEntity){
						for(IncidentAccountCode iac : incEntity.getIncidentAccountCodes()){
							if(
								(iac.getAccountCode().getAccountCode().equals(code))){
									//&&
								//(iac.getAccountCode().getAgency().getId().compareTo(agencyId)==0) )){
								alreadyExistsForThisIncident=true;
							}
						}
					
						if(alreadyExistsForThisIncident){
							super.handleException(ErrorEnum.DUPLICATE_INCIDENT_ACCOUNT_CODE);
						}
					}
					
					return true;
				}
			}
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return false;
	}

	public Boolean isAccountCodeAgencyInUse(Long incidentId, Long agencyId, String code ) throws ServiceException {
		
		try{
			
			Incident incEntity = incidentDao.getById(incidentId);
			
			// check accountCode
			Collection<AccountCode> list = accountCodeDao.getAllByKey(code.toUpperCase(), agencyId);
			
			if((null != list) && (list.size()>0)){
				Boolean alreadyExistsForThisIncident=false;
				
				if(null != incEntity){
					for(IncidentAccountCode iac : incEntity.getIncidentAccountCodes()){
						if(
							(iac.getAccountCode().getAccountCode().equals(code.toUpperCase()))){
								//&&
							//(iac.getAccountCode().getAgency().getId().compareTo(agencyId)==0) )){
							alreadyExistsForThisIncident=true;
						}
					}
				
					if(alreadyExistsForThisIncident){
						super.handleException(ErrorEnum.DUPLICATE_INCIDENT_ACCOUNT_CODE);
					}
				}
				
				return true;
			}
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return false;
	}	
	
	public Boolean isAccountCodeAgencyInUseOther(Long excludeIncidentId, Long agencyId, String code ) throws ServiceException {
		
		try{

			IncidentAccountCodeDao iacDao = (IncidentAccountCodeDao)super.context.getBean("incidentAccountCodeDao");

			Collection<IncidentAccountCode> list = iacDao.getByAgencyAndCode(agencyId, code, excludeIncidentId);
			
			if(list.size()>0)
				return true;
		}catch(Exception e){
			super.handleException(e);
		}
		
		return false;
	}	
	
	
}
