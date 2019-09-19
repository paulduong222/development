package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.AdminOffice;
import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.ContractorAgreementNumberHistory;
import gov.nwcg.isuite.core.domain.impl.AdminOfficeImpl;
import gov.nwcg.isuite.core.domain.impl.ContractorAgreementImpl;
import gov.nwcg.isuite.core.domain.impl.ContractorAgreementNumberHistoryImpl;
import gov.nwcg.isuite.core.filter.ContractorAgreementFilter;
import gov.nwcg.isuite.core.persistence.ContractorAgreementDao;
import gov.nwcg.isuite.core.persistence.ContractorAgreementNumberHistoryDao;
import gov.nwcg.isuite.core.service.ContractorAgreementService;
import gov.nwcg.isuite.core.vo.ContractorAgreementNumberHistoryVo;
import gov.nwcg.isuite.core.vo.ContractorAgreementVo;
import gov.nwcg.isuite.core.vo.ContractorAgreementGridVo;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class ContractorAgreementServiceImpl extends BaseService implements ContractorAgreementService {
	private ContractorAgreementDao contractorAgreementDao;

	public ContractorAgreementServiceImpl(){
		
	}
	
	/**
	 * 
	 */
	public void initialization(){
		contractorAgreementDao = (ContractorAgreementDao)super.context.getBean("contractorAgreementDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorAgreementService#getById(java.lang.Long)
	 */
	public ContractorAgreementVo getById(Long id) throws ServiceException {
		ContractorAgreementVo vo = null;
		
		try{
			ContractorAgreement entity = contractorAgreementDao.getById(id,ContractorAgreementImpl.class);
			if(null==entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"ContractorAgreement["+id+"]");

			vo = ContractorAgreementVo.getInstance(entity,true);
		}catch(Exception e){
			super.handleException(e);
		}
		
		return vo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorAgreementService#getGrid(gov.nwcg.isuite.core.filter.ContractorAgreementFilter)
	 */
	public Collection<ContractorAgreementGridVo> getGrid(ContractorAgreementFilter filter) throws ServiceException {
		Collection<ContractorAgreementGridVo> vos = new ArrayList<ContractorAgreementGridVo>();
		
		try{
			vos = contractorAgreementDao.getGrid(filter);
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return vos;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorAgreementService#save(gov.nwcg.isuite.core.vo.ContractorAgreementVo)
	 */
	public ContractorAgreementVo save(ContractorAgreementVo vo) throws ServiceException, ValidationException {
		try{
			if (null != contractorAgreementDao.getByNameId(vo.getAgreementNumber(), vo.getId(), vo.getContractorVo().getId())) {
				super.handleException(ErrorEnum._0237_DUPLICATE_AGREEMENT_NUMBER, vo.getAgreementNumber());
			}
			
			Contractor contractorEntity = ContractorVo.toEntity(null,vo.getContractorVo(), false);
			
			ContractorAgreement entity = ContractorAgreementVo.toEntity(null, vo,true,contractorEntity);
			
			//handling the following here instead of in ContractorAgeementVo.toEntity because of 
			//infinite loop caused by adminOffice also calling ContractorAgeementVo.toEntity
			if (vo.getAdminOfficeId() != 0) {
				AdminOffice adminOfficeEntity = new AdminOfficeImpl();
				adminOfficeEntity.setId(vo.getAdminOfficeId());
				entity.setAdminOffice(adminOfficeEntity);
			} else {
				entity.setAdminOffice(null);
			}
			
			contractorAgreementDao.save(entity);
			
			//save contractor agreement number history
			if (null != vo.getContractorAgreementNumberHistoryVo()) {	
				ContractorAgreementNumberHistoryDao canhDao=(ContractorAgreementNumberHistoryDao)context.getBean("contractorAgreementNumberHistoryDao");
				ContractorAgreementNumberHistory canhEntity = new ContractorAgreementNumberHistoryImpl();
				canhEntity.setContractorAgreement(entity);
				canhEntity.setReasonText(vo.getContractorAgreementNumberHistoryVo().getReasonText());
				canhEntity.setOldAgreementNumber(vo.getContractorAgreementNumberHistoryVo().getOldAgreementNumber());
				canhEntity.setNewAgreementNumber(vo.getContractorAgreementNumberHistoryVo().getNewAgreementNumber());
				
				canhDao.save(canhEntity);
				canhDao.flushAndEvict(canhEntity);
			}
			
			contractorAgreementDao.flushAndEvict(entity);
			
			return ContractorAgreementVo.getInstance(entity, true);
			
		}catch(Exception e){
			super.handleException(e);
		}
		return null;
	}
	

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorAgreementService#deleteAgreement(java.lang.Long)
	 */
	public void deleteContractorAgreement(ContractorAgreementVo persistableVo) throws ServiceException{
		try {
			if( null != persistableVo.getId()){
				ContractorAgreement contractorAgreement = contractorAgreementDao.getById(persistableVo.getId(), ContractorAgreementImpl.class);
				if(null!=contractorAgreement){

					/*
					 * For resource entities, we only set the deleted_date field
					 * instead of physically deleting the entity record.
					 */
					contractorAgreement.setDeletedDate(Calendar.getInstance().getTime());
					contractorAgreementDao.save(contractorAgreement);
				}
			}
			else {
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"ContractorAgreement["+persistableVo.getId()+"]");
			}

		} catch (PersistenceException pe) {
			throw new ServiceException(pe);
		} catch (Exception e){
			throw new ServiceException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorAgreementService#enableContractorAgreements(java.util.Collection)
	 */
	public void enableContractorAgreements(Collection<ContractorAgreementGridVo> contractorAgreements) throws ServiceException {
		try {
			Collection<Long> contractorAgreementIds = new ArrayList<Long>();
			for(ContractorAgreementGridVo vo : contractorAgreements) {
				contractorAgreementIds.add(vo.getId());
			}
			contractorAgreementDao.enableContractorAgreements(contractorAgreementIds);
			
		}catch(Exception e){
			super.handleException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorAgreementService#disableContractorAgreements(java.util.Collection)
	 */
	public void disableContractorAgreements(Collection<ContractorAgreementGridVo> contractorAgreements) throws ServiceException {
		try {
			Collection<Long> contractorAgreementIds = new ArrayList<Long>();
			for(ContractorAgreementGridVo vo : contractorAgreements) {
				contractorAgreementIds.add(vo.getId());
			}
			contractorAgreementDao.disableContractorAgreements(contractorAgreementIds);
			
		}catch(Exception e){
			super.handleException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ContractorAgreementService#hasInvoices(java.lang.Long)
	 */
	public Boolean hasOriginalInvoices(Long contractorAgreementId) throws ServiceException {
		Boolean hasInvoices = false;
		
		try{
			Collection<ContractorAgreement> contractorAgreements = contractorAgreementDao.getAgreementsWithOriginalInvoices(contractorAgreementId);
			
			if(CollectionUtility.hasValue(contractorAgreements)) {
				hasInvoices = true;
			}
		}catch(Exception e){
			super.handleException(e);
		}
		
		return hasInvoices;
	}
}
