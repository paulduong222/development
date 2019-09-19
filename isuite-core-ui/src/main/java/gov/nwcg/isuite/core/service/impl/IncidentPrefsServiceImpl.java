package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupPrefs;
import gov.nwcg.isuite.core.domain.IncidentPrefs;
import gov.nwcg.isuite.core.domain.IncidentPrefsOtherFields;
import gov.nwcg.isuite.core.domain.IncidentQSKind;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentPrefsImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentQSKindImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentQuestionImpl;
import gov.nwcg.isuite.core.filter.KindFilter;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentPrefsDao;
import gov.nwcg.isuite.core.persistence.IncidentQSKindDao;
import gov.nwcg.isuite.core.persistence.IncidentQuestionDao;
import gov.nwcg.isuite.core.service.IncidentPrefsService;
import gov.nwcg.isuite.core.vo.IncidentGroupPrefsVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsOtherFieldsVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsVo;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author mpoll
 *
 */
public class IncidentPrefsServiceImpl extends BaseService implements IncidentPrefsService {
	private IncidentPrefsDao incidentPrefsDao;
	private IncidentQuestionDao incidentQuestionDao;
	private IncidentQSKindDao incidentQSKindDao;
	private IncidentDao incidentDao;
	private IncidentGroupDao incidentGroupDao;

	/**
	 * Default Constructor
	 */
	public IncidentPrefsServiceImpl() {
	}

	/*
	 * Method to initialize class using the spring framework
	 */
	public void initialization() {
		incidentPrefsDao = (IncidentPrefsDao)super.context.getBean("incidentPrefsDao");
		incidentQuestionDao = (IncidentQuestionDao)super.context.getBean("incidentQuestionDao");
		incidentQSKindDao = (IncidentQSKindDao)super.context.getBean("incidentQSKindDao");
		incidentDao = (IncidentDao)super.context.getBean("incidentDao");
		incidentGroupDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getById(java.lang.Long)
	 */
	@Override
	public IncidentPrefsVo getById(Long id) throws ServiceException {
		try {
			if (id == null) {
				throw new ServiceException("id cannot be null");
			}

			IncidentPrefs incidentPrefs = incidentPrefsDao.getById(id, IncidentPrefsImpl.class);
			if (incidentPrefs != null) {
				return IncidentPrefsVo.getInstance(incidentPrefs, true);         
			}
			throw new ServiceException("Could not find IncidentPrefs with Id: " + id);
		} catch ( PersistenceException e ) {
			throw new ServiceException(e);
		} catch ( Exception e ) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<IncidentPrefsVo> getByIncidentId(Long incidentId) throws ServiceException {
		Collection<IncidentPrefsVo> incidentPrefsVos = null;
		if(incidentId!=null){
			try {
				List<IncidentPrefs> entities = this.incidentPrefsDao.getByIncidentId(incidentId);
				incidentPrefsVos = IncidentPrefsVo.getInstances(entities, true);
				
				for (IncidentPrefs entity : entities) {
					incidentPrefsDao.flushAndEvict(entity);
				}
			} catch (Exception e) {
				super.handleException(e);
			}
		}
		return incidentPrefsVos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getFinanceFields(java.lang.Long, java.lang.Boolean)
	 */
	@Override
	public Collection<IncidentPrefsVo> getFinanceFields(Long incidentOrGroupId, Boolean isGroup) throws ServiceException {
		try {
			return incidentPrefsDao.getFinanceFields(incidentOrGroupId, isGroup);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getICS221Fields(java.lang.Long, java.lang.Boolean)
	 */
	@Override
	public Collection<IncidentPrefsVo> getICS221Fields(Long incidentOrGroupId, Boolean isGroup) throws ServiceException {
		try {
			return incidentPrefsDao.getICS221Fields(incidentOrGroupId, isGroup);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getICS221OtherFields(java.lang.Long, java.lang.Boolean)
	 */
	@Override
	public Collection<IncidentPrefsVo> getICS221OtherFields(Long incidentOrGroupId, Boolean isGroup) throws ServiceException {
		try {
			return incidentPrefsDao.getICS221OtherFields(incidentOrGroupId, isGroup);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getLogisticsFields(java.lang.Long, java.lang.Boolean)
	 */
	@Override
	public Collection<IncidentPrefsVo> getLogisticsFields(Long incidentOrGroupId, Boolean isGroup) throws ServiceException {
		try {
			return incidentPrefsDao.getLogisticsFields(incidentOrGroupId, isGroup);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getOtherLabels(java.lang.Long, java.lang.Boolean)
	 */
	@Override
	public Collection<IncidentPrefsVo> getOtherLabels(Long incidentOrGroupId, Boolean isGroup) throws ServiceException {
		try {
			return incidentPrefsDao.getOtherLabels(incidentOrGroupId, isGroup);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getPlanningFields(java.lang.Long, java.lang.Boolean)
	 */
	@Override
	public Collection<IncidentPrefsVo> getPlanningFields(Long incidentOrGroupId, Boolean isGroup) throws ServiceException {
		try {
			return incidentPrefsDao.getPlanningFields(incidentOrGroupId, isGroup);
		}
		catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	// MANU - This method is not being called by Flex. Incidents and IncidentGroups require 
	// different set of VOs to transfer the preferences. This method uses only one and was probably written 
	// before the incident and incidentgroup preferences were separated. 
//	/*
//	 * (non-Javadoc)
//	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#savePrefs(java.lang.Long, java.lang.Boolean, java.util.Collection)
//	 */
//	@Override
//	public void savePrefs(Long incidentOrGroupId, Boolean isGroup, Collection<IncidentPrefsVo> vos) throws ServiceException {
//		IncidentPrefs entity = null;
//		try {
//			for (IncidentPrefsVo vo : vos) {
//				if ((null != vo.getId()) && (vo.getId().compareTo(0L) > 0)) {
//					entity = incidentPrefsDao.getById(vo.getId(), IncidentPrefsImpl.class);
//					if (entity == null) 
//						super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentPrefs["+vo.getId()+"]");
//				} else {
//					entity = new IncidentPrefsImpl();
//				}
//				entity = IncidentPrefsVo.toEntity(entity, vo, true);
//				incidentPrefsDao.save(entity);
//			}
//			if(isGroup == true) {
//				this.saveCheckOutPrefsForAllIncidentsInGroup(incidentOrGroupId);
//			}
//		} catch(ServiceException se) {
//			throw se;
//		} catch (Exception e) {
//			super.handleException(e);
//		}
//	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getAirTravelQuestions(java.lang.Long, java.lang.Boolean)
	 */
	@Override
	public Collection<IncidentQuestionVo> getAirTravelQuestions(Long incidentOrGroupId, Boolean isGroup) throws ServiceException {
		try {
			return incidentQuestionDao.getAirTravelQuestions(incidentOrGroupId, isGroup);
		}
		catch ( PersistenceException e ) {
			super.handleException(e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getCheckInQuestions(java.lang.Long, java.lang.Boolean)
	 */
	@Override
	public Collection<IncidentQuestionVo> getCheckInQuestions(Long incidentOrGroupId, Boolean isGroup) throws ServiceException {
		try {
			return incidentQuestionDao.getCheckInQuestions(incidentOrGroupId, isGroup);
		}
		catch ( PersistenceException e ) {
			super.handleException(e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#saveQuestions(java.util.Collection)
	 */
	public Collection<IncidentQuestionVo> saveQuestions(Collection<IncidentQuestionVo> iqVos) throws ServiceException {
		Collection<IncidentQuestionVo> returnVos = null;
		for(IncidentQuestionVo iqVo : iqVos) {
			returnVos = new ArrayList<IncidentQuestionVo>();
			returnVos = this.saveQuestion(iqVo);
		}
		return returnVos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#saveQuestion(gov.nwcg.isuite.core.vo.IncidentQuestionVo)
	 */
	@Override
	public Collection<IncidentQuestionVo> saveQuestion(IncidentQuestionVo vo) throws ServiceException {
		IncidentQuestion entity = null;

		try {
			//Determine if incident or incident group
			long incidentOrGroupId = 0L;
			boolean isGroup = false;
			if ((null != vo.getIncidentVo()) && (null != vo.getIncidentVo().getId())) {
				incidentOrGroupId = vo.getIncidentVo().getId();
			} 
			
			//Retrieve existing IncidentQuestion Record or create a new one, populate, and save
			if ((null != vo.getId()) && (vo.getId().compareTo(0L) > 0)) {
				entity = incidentQuestionDao.getById(vo.getId(), IncidentQuestionImpl.class);
				if (entity == null) 
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentQuestion["+vo.getId()+"]");            
				//            if (entity.getQuestion().isStandard())
				//               super.handleException(ErrorEnum.CANNOT_EDIT_STANDARD_RECORDS,"IncidentQuestion["+vo.getId()+"]");            
			} else {
				//Prevent duplicate questions from being added to an Incident.
				entity = incidentQuestionDao.getByQuestion(vo.getQuestionVo().getQuestion(), incidentOrGroupId, vo.getQuestionVo().getQuestionType());
				if(entity != null) {
					super.handleException(ErrorEnum.DUPLICATE_INCIDENT_QUESTION);
				}
				entity = new IncidentQuestionImpl();

				int nextPosition = incidentQuestionDao.getNextQuestionPosition(incidentOrGroupId,isGroup) + 1;
				vo.setPosition(nextPosition);
			}

			entity = IncidentQuestionVo.toEntity(entity,vo, true);

			incidentQuestionDao.save(entity);
			incidentQuestionDao.flushAndEvict(entity);
			entity = incidentQuestionDao.getById(entity.getId(), IncidentQuestionImpl.class);

			if(isGroup == true) {
				this.saveQuestionChangesToAllIncidentsInGroup(entity);
			}

			//Retrieve updated list of questions and return to calling method
			if (entity.getQuestion().getQuestionType().equals(QuestionTypeEnum.PREPLANNING)) {
				return this.getCheckInQuestions(incidentOrGroupId, isGroup);
			} else if (entity.getQuestion().getQuestionType().equals(QuestionTypeEnum.AIRTRAVEL)) {
				return this.getAirTravelQuestions(incidentOrGroupId, isGroup);
			}

		} catch (Exception e) {
			super.handleException(e);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#saveQuestionPositions(java.util.Collection)
	 */
	public void saveQuestionPositions(Collection<IncidentQuestionVo> vos) throws ServiceException {
		Collection<IncidentQuestion> entities = new ArrayList<IncidentQuestion>();

		try {
			entities=IncidentQuestionVo.toEntityList(vos,true);
			incidentQuestionDao.saveAll(entities);
		} catch ( PersistenceException e ) {
			super.handleException(e);
		} catch ( Exception e ) {
			super.handleException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#deleteQuestion(java.lang.Long)
	 */
	@Override
	public void deleteQuestion(Long incidentQuestionId) throws ServiceException {
		try {
			IncidentQuestion question = incidentQuestionDao.getById(incidentQuestionId, IncidentQuestionImpl.class);
			if (question == null) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentQuestion["+incidentQuestionId+"]");
			//         if (question.getQuestion().isStandard())
			//Need to have message stating "Cannot delete Standard Record"
			// 6/25/10 Will be enforced in the UI as per Trudi's direction
			//####################################################################################################################
			//TODO:  Do not allow deletion of a non-group Incident question if that Incident is part of an IncidentGroup. -dbudge
			//####################################################################################################################
			incidentQuestionDao.delete(question);
		}
		catch ( Exception e ) {
			super.handleException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getAvailablePrefKindCodes(java.lang.Long, java.lang.Boolean, gov.nwcg.isuite.core.filter.KindFilter)
	 */
	@Override
	public Collection<KindVo> getAvailablePrefKindCodes(Long incidentOrGroupId, Boolean isGroup, KindFilter itemFilter) throws ServiceException {
		try {
			return incidentQSKindDao.getAvailablePrefKindCodes(incidentOrGroupId, isGroup, itemFilter);
		}
		catch ( PersistenceException e ) {
			super.handleException(e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getSelectedPrefKindCodes(java.lang.Long, java.lang.Boolean)
	 */
	@Override
	public Collection<KindVo> getSelectedPrefKindCodes(Long incidentOrGroupId, Boolean isGroup) throws ServiceException {
		try {
			return incidentQSKindDao.getSelectedPrefKindCodes(incidentOrGroupId, isGroup);
		}
		catch ( PersistenceException e ) {
			super.handleException(e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#saveKindCodePrefs(java.lang.Long, java.util.Collection, java.lang.Boolean)
	 */
	@Override
	public void saveKindCodePrefs(Long incidentOrGroupId, Collection<KindVo> vos, Boolean isGroup) throws ServiceException {
		try {
			incidentQSKindDao.removeAllQSKindsWithId(incidentOrGroupId, isGroup);

			Collection<IncidentQSKind> entities = new ArrayList<IncidentQSKind>();

			for (KindVo vo : vos) {
				IncidentQSKind entity = new IncidentQSKindImpl();
				if (isGroup) {
					//entity.setIncidentGroup(incidentGroupDao.getById(incidentOrGroupId, IncidentGroupImpl.class));
				} else {
					entity.setIncident(incidentDao.getById(incidentOrGroupId, IncidentImpl.class));
				}

				entity.setKind(KindVo.toEntity(null, vo, false));

				entities.add(entity);
			}

			incidentQSKindDao.saveAll(entities);

		} catch ( PersistenceException e ) {
			super.handleException(e);
		} catch ( Exception e ) {
			super.handleException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#checkForNonStandardTravelQuestions(java.lang.Long)
	 */
	@Override
	public Boolean checkForNonStandardTravelQuestions(Long incidentId) throws ServiceException {
		try {
			return this.incidentQuestionDao.checkForNonStandardTravelQuestions(incidentId);
		}
		catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#checkForNonStandardCheckinQuestions(java.lang.Long)
	 */
	public Boolean checkForNonStandardCheckinQuestions(Long incidentId) throws ServiceException {
		try {
			return this.incidentQuestionDao.checkForNonStandardCheckinQuestions(incidentId);
		}
		catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

	private void saveQuestionChangesToAllIncidentsInGroup(IncidentQuestion entity) throws ServiceException {
		IncidentGroup ig;
		/* dan 9/8/2011 switch to isw_incident_group_question
		try {
			ig = this.incidentGroupDao.getById(entity.getIncidentGroup().getId(), IncidentGroupImpl.class);
			for(Incident i : ig.getIncidents()) {
				IncidentQuestion incidentQuestion = this.incidentQuestionDao.getByQuestionIdAndIncidentId(entity.getQuestion().getId(), i.getId()); 
				if(incidentQuestion != null) {
					incidentQuestion.setIncident(i);
					incidentQuestion.setQuestion(entity.getQuestion());
					incidentQuestion.setVisible(entity.isVisible());
					incidentQuestion.setPosition(entity.getPosition());
					this.incidentQuestionDao.save(incidentQuestion);
				} else {
					IncidentQuestion iqEntity = new IncidentQuestionImpl();
					iqEntity.setIncident(i);
					iqEntity.setQuestion(entity.getQuestion());
					iqEntity.setVisible(entity.isVisible());
					iqEntity.setPosition(entity.getPosition());
					this.incidentQuestionDao.save(iqEntity);
				}
			}
		}
		catch ( Exception e ) {
			super.handleException(e);
		}
		*/
	}
	
	// Manu: Although incident groups use IncidentGroupPrefs, this method is still using IncidentPrefs.
	
//	private void saveCheckOutPrefsForAllIncidentsInGroup(Long groupId) throws ServiceException {
//		IncidentGroup ig;
//		try {
//			Collection<IncidentPrefs> ipEntities = this.incidentPrefsDao.getByIncidentGroupId(groupId);
//			ig = this.incidentGroupDao.getById(groupId, IncidentGroupImpl.class);
//			for(Incident i : ig.getIncidents()) {
//				List<IncidentPrefs> incPrefs = this.incidentPrefsDao.getByIncidentId(i.getId());
//				List<IncidentPrefs> ipsEntities = new ArrayList<IncidentPrefs>();
//				if(incPrefs == null) {
//					for(IncidentPrefs ips : ipEntities) {
//						IncidentPrefs ip = new IncidentPrefsImpl();
//						ip.setIncident(i);
//						ip.setIncidentId(i.getId());
//						ip.setPosition(ips.getPosition());
//						ip.setSelected(ips.isSelected());
//						ipsEntities.add(ip);
//					}
//					this.incidentPrefsDao.saveAll(ipsEntities);
//				} else {
//					ipsEntities = new ArrayList<IncidentPrefs>();
//					ipsEntities.addAll(ipEntities);
//					for(int k = 0; k < incPrefs.size(); k++) {
//						incPrefs.get(k).setIncidentId(i.getId());
//						incPrefs.get(k).setPosition(ipsEntities.get(k).getPosition());
//						incPrefs.get(k).setSelected(ipsEntities.get(k).isSelected());
//					}
//					this.incidentPrefsDao.saveAll(incPrefs);
//				}
//			}
//		} catch(Exception e) {
//			super.handleException(e);
//		}
//	}

	 /* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getIncidentPrefsOtherFields(java.lang.Long)
	 */
	public IncidentPrefsOtherFieldsVo getIncidentPrefsOtherFields(Long incidentId) throws ServiceException {

		try{

			IncidentPrefsOtherFields entity = incidentPrefsDao.getPrefsOtherFields(incidentId);
			
			if(null != entity)
				return IncidentPrefsOtherFieldsVo.getInstance(entity, true);
			else{
				// create the defaults
				IncidentPrefsOtherFieldsVo vo = new IncidentPrefsOtherFieldsVo();
				
//				vo.setIncidentVo(new IncidentVo());
				vo.setIncidentId(incidentId);
//				vo.getIncidentVo().setId(incidentId);
				
				return this.saveIncidentPrefsOtherFields(vo);
			}
		}catch(Exception e){
			super.handleException(e);
		}

		return null;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#saveIncidentPrefsOtherFields(gov.nwcg.isuite.core.vo.IncidentPrefsOtherFieldsVo)
	 */
	public IncidentPrefsOtherFieldsVo saveIncidentPrefsOtherFields(IncidentPrefsOtherFieldsVo vo) throws ServiceException {
		
		try{
			if(null != vo.getIncidentId() && vo.getIncidentId() > 0) {//(null != vo.getIncidentVo().getId() && vo.getIncidentVo().getId() > 0) ){
				
				IncidentDao incidentDao = (IncidentDao)super.context.getBean("incidentDao");
				
				Incident incidentEntity = incidentDao.getById(vo.getIncidentId());//vo.getIncidentVo().getId());
				
				if(null != incidentEntity){
					incidentEntity.setIncidentPrefsOtherFields(IncidentPrefsOtherFieldsVo.toEntity(incidentEntity.getIncidentPrefsOtherFields(), vo, true, incidentEntity));

					incidentDao.save(incidentEntity);
					
					incidentDao.flushAndEvict(incidentEntity);
					
					return this.getIncidentPrefsOtherFields(incidentEntity.getId());
				}
			}
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#doesIncidentBelongToAGroup(java.lang.Long)
	 */
	@Override
	public Boolean doesIncidentBelongToAGroup(Long incidentId) throws ServiceException {
		Long incidentInGroupId = null;
		try {
			incidentInGroupId = incidentGroupDao.getIncidentGroupsIncidentId(incidentId);
		} catch (PersistenceException e) {
			super.handleException(e);
		}
		if(incidentInGroupId != null) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getIncidentQuestionById(java.lang.Long)
	 */
	@Override
	public IncidentQuestionVo getIncidentQuestionById(Long incidentQuestionId) throws ServiceException {
		IncidentQuestion entity = new IncidentQuestionImpl();
		try {
			entity = incidentQuestionDao.getById(incidentQuestionId, IncidentQuestionImpl.class);
			return IncidentQuestionVo.getInstance(entity, true);
		} catch (Exception e) {
			super.handleException(e);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getICS204Block5FieldsForIncident(java.lang.Long, java.lang.Boolean)
	 */
	@Override
	public Collection<IncidentPrefsVo> getICS204Block5FieldsForIncident(Long incidentId, Boolean selected) throws ServiceException {
		Collection<IncidentPrefs> entities;
		try{
			entities = incidentPrefsDao.getICS204Block5FieldsForIncident(incidentId, selected);
	        return IncidentPrefsVo.getInstances(entities, true);
		}catch(Exception e){
			super.handleException(e);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentPrefsService#getICS204Block5FieldsForIncidentGroup(java.lang.Long, java.lang.Boolean)
	 */
	@Override
	public Collection<IncidentGroupPrefsVo> getICS204Block5FieldsForIncidentGroup(Long incidentGroupId, Boolean selected) throws ServiceException {
		Collection<IncidentGroupPrefs> entities;
		try{
			entities = incidentPrefsDao.getICS204Block5FieldsForIncidentGroup(incidentGroupId, selected);
	        return IncidentGroupPrefsVo.getInstances(entities, true);
		}catch(Exception e){
			super.handleException(e);
		}
		return null;
	}
	
}
