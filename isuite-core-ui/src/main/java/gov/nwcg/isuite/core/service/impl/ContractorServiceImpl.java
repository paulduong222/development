package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.ContractorNameHistory;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.impl.ContractorImpl;
import gov.nwcg.isuite.core.domain.impl.ContractorNameHistoryImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.filter.ContractorFilter;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.core.persistence.ContractorNameHistoryDao;
import gov.nwcg.isuite.core.service.ContractorService;
import gov.nwcg.isuite.core.vo.ContractorAgreementVo;
import gov.nwcg.isuite.core.vo.ContractorGridVo;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class ContractorServiceImpl extends BaseService implements ContractorService {
	private ContractorDao contractorDao;

	public ContractorServiceImpl(){
		
	}
	
	/**
	 * 
	 */
	public void initialization(){
		contractorDao = (ContractorDao)super.context.getBean("contractorDao");
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService#deleteContractor(gov.nwcg.isuite.core.vo.ContractorVo)
	 */
	public void deleteContractor(ContractorVo vo) throws ServiceException {
		try{
			Contractor entity = contractorDao.getById(vo.getId(),ContractorImpl.class);
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Contractor["+vo.getId()+"]");
			
			entity.setDeletedDate(Calendar.getInstance().getTime());
			contractorDao.save(entity);
		}catch(Exception e){
			super.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService#getById(java.lang.Long)
	 */
	public ContractorVo getById(Long id) throws ServiceException {
		ContractorVo vo = null;
		
		try{
			Contractor entity = contractorDao.getById(id,ContractorImpl.class);
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Contractor["+id+"]");

			vo = ContractorVo.getInstance(entity,true);
		}catch(Exception e){
			super.handleException(e);
		}
		
		return vo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService#getGrid(gov.nwcg.isuite.core.filter.ContractorFilter)
	 */
	public Collection<ContractorGridVo> getGrid(ContractorFilter filter) throws ServiceException {
		if (filter.getIncidentIds() == null && filter.getWorkAreaId() == null ) {
			throw new ServiceException("WorkAreaId or IncidentId is required.");
		}
		
		Collection<ContractorGridVo> vos = new ArrayList<ContractorGridVo>();
		
		try{
			vos = contractorDao.getGrid(filter);
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return vos;
	}

	public Collection<ContractorVo> getContractors(ContractorFilter filter) throws ServiceException {
		if (filter.getIncidentIds() == null && filter.getWorkAreaId() == null ) {
			throw new ServiceException("WorkAreaId or IncidentId is required.");
		}
		
		Collection<ContractorVo> vos = new ArrayList<ContractorVo>();
		
		try{
			Long workAreaId=filter.getWorkAreaId();
			Long incidentGroupId=filter.getIncidentGroupId();
			Collection<Long> incidentIds = filter.getIncidentIds();
			
			filter.setWorkAreaId(null);
			filter.setIncidentGroupId(null);
			filter.setIncidentIds(new ArrayList<Long>());
			
			if(LongUtility.hasValue(workAreaId)){
				filter.setWorkAreaId(workAreaId);
				vos = contractorDao.getAll(filter);
			}

			if(LongUtility.hasValue(incidentGroupId)){
				filter.setWorkAreaId(null);
				filter.setIncidentGroupId(incidentGroupId);
				vos.addAll(contractorDao.getAll(filter));
			}
			
			if(CollectionUtility.hasValue(incidentIds)){
				filter.setWorkAreaId(null);
				filter.setIncidentGroupId(null);
				filter.setIncidentIds(incidentIds);
				vos.addAll(contractorDao.getAll(filter));
			}
			
			return vos;
		}catch(Exception e){
			throw new ServiceException(new ErrorObject(ErrorEnum._90000_ERROR,e.getMessage()));
		}
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService#save(gov.nwcg.isuite.core.vo.ContractorVo)
	 */
	public ContractorVo save(ContractorVo vo) throws ServiceException, ValidationException {
		try{
			vo.setEnabled(Boolean.TRUE);
			
			Contractor entity = ContractorVo.toEntity(null,vo,true);
			if (null != vo.getIncidentVos() && vo.getIncidentVos().size() > 0) {
				Collection<Long> incidentIds = new ArrayList<Long>();
				for (IncidentVo incidentVo : vo.getIncidentVos()) {
					incidentIds.add(incidentVo.getId());
				}
				
				if (null != contractorDao.getByContractorNameIds(vo.getName().toUpperCase(), incidentIds, null, vo.getId())) {
					super.handleException(ErrorEnum._0238_DUPLICATE_CONTRACTOR_NAME, vo.getName().toUpperCase());
				}
				
				entity.setIncidents(IncidentVo.toEntityList(vo.getIncidentVos(), false));
			} else {
				if (null != vo.getWorkAreaVos()) {
					Collection<Long> workAreaIds = new ArrayList<Long>();
					for (WorkAreaVo workAreaVo : vo.getWorkAreaVos()) {
						workAreaIds.add(workAreaVo.getId());
					}
					
					if (null != contractorDao.getByContractorNameIds(vo.getName().toUpperCase(), null, workAreaIds, vo.getId())) {
						super.handleException(ErrorEnum._0238_DUPLICATE_CONTRACTOR_NAME, vo.getName());
					}
					
				}
			}
			
			//save contractor name history if contractor name was included on an original
			//OF-286 invoice
			if (LongUtility.hasValue(vo.getId())) {
				Collection<String> resources = this.getContractedResourcesWithOriginalInvoices(vo.getId());
				
				if(CollectionUtility.hasValue(resources)) {
					Contractor originalEntity = contractorDao.getById(vo.getId(),ContractorImpl.class);
					ContractorVo originalVo = ContractorVo.getInstance(originalEntity, true);
					contractorDao.flushAndEvict(originalEntity);
					
					if (!originalVo.getName().equals(entity.getName())) {
						ContractorNameHistoryDao cnhDao=(ContractorNameHistoryDao)context.getBean("contractorNameHistoryDao");
						ContractorNameHistory cnhEntity = new ContractorNameHistoryImpl();
						cnhEntity.setContractor(entity);
						cnhEntity.setOldContractorName(originalVo.getName());
						cnhEntity.setNewContractorName(entity.getName());
						
						cnhDao.save(cnhEntity);
						cnhDao.flushAndEvict(cnhEntity);
					}
					
				}
			}
			
			contractorDao.save(entity);
			
			contractorDao.flushAndEvict(entity);
			return ContractorVo.getInstance(entity, true);
			
		}catch(Exception e){
			super.handleException(e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService#saveAgreement(java.lang.Long, gov.nwcg.isuite.core.vo.ContractorAgreementVo)
	 */
	public void saveAgreement(Long contractorId,ContractorAgreementVo vo) throws ServiceException{
		try{
			
		}catch(Exception e){
			super.handleException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService#deleteAgreement(java.lang.Long)
	 */
	public void deleteAgreement(Long id) throws ServiceException{
		try{
			
		}catch(Exception e){
			super.handleException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService#disableContractors(java.util.Collection)
	 */
	public void disableContractors(Collection<ContractorGridVo> contractors) throws ServiceException {
		try {
			Collection<Long> contractorIds = new ArrayList<Long>();
			for(ContractorGridVo vo : contractors) {
				contractorIds.add(vo.getId());
			}
			contractorDao.disableContractors(contractorIds);
			
		}catch(Exception e){
			super.handleException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService#enableContractors(java.util.Collection)
	 */
	public void enableContractors(Collection<ContractorGridVo> contractors) throws ServiceException {
		try {
			Collection<Long> contractorIds = new ArrayList<Long>();
			for(ContractorGridVo vo : contractors) {
				contractorIds.add(vo.getId());
			}
			contractorDao.enableContractors(contractorIds);
			
		}catch(Exception e){
			super.handleException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService#addContractorsToIncident(java.util.Collection)
	 */
	public void addContractorsToIncident(Collection<ContractorGridVo> contractors) throws ServiceException {
		
		try{
			if (null == contractors || contractors.size() < 1) {
				throw new Exception("No contractors to add to the incidents");
			}
			
			//check for duplicate contractor names
			Collection<String> names = new ArrayList<String>();
			
			for (ContractorGridVo vo: contractors) {
				Collection<Long> incidentIds = new ArrayList<Long>();
				incidentIds.add(vo.getIncidentId());
				
				if (null != contractorDao.getByContractorNameIds(vo.getName(), incidentIds, null, vo.getId())) {
					names.add(vo.getName());
				}
			}
			
			if (names.size() > 0) {
				super.handleException(ErrorEnum._0238_DUPLICATE_CONTRACTOR_NAME, names.toString());
			}
			
			//add contractors to incident
			Collection<Contractor> entities = new ArrayList<Contractor>();
			
			for (ContractorGridVo vo: contractors) {
				
				Contractor contractorEntity = contractorDao.getById(vo.getId(), ContractorImpl.class);
				Incident incidentEntity = new IncidentImpl();
				incidentEntity.setId(vo.getIncidentId());
				contractorEntity.getIncidents().add(incidentEntity);
				entities.add(contractorEntity);
			}
			
			contractorDao.saveAll(entities);
			
		}catch(Exception e){
			super.handleException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorService#getContractedResourcesWithOriginalInvoices(java.lang.Long)
	 */
	public Collection<String> getContractedResourcesWithOriginalInvoices(Long contractorId) throws ServiceException {
		Collection<String> resourceNames = null;
		
		try{
			resourceNames = contractorDao.getContractedResourcesWithOriginalInvoices(contractorId);
		}catch(Exception e){
			super.handleException(e);
		}
		
		return resourceNames;
	}
}
