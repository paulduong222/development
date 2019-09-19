package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.AccountCode;
import gov.nwcg.isuite.core.domain.impl.AccountCodeImpl;
import gov.nwcg.isuite.core.persistence.AccountCodeDao;
import gov.nwcg.isuite.core.service.AccountCodeService;
import gov.nwcg.isuite.core.vo.AccountCodeVo;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public class AccountCodeServiceImpl extends BaseService implements AccountCodeService {
	private AccountCodeDao accountCodeDao=null;

	public AccountCodeServiceImpl(){
		
	}
	
	public AccountCodeServiceImpl(final AccountCodeDao accountCodeDao){
		if ( accountCodeDao == null ) {throw new IllegalArgumentException("accountCodeDao cannot be null");}
		this.accountCodeDao = accountCodeDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AccountCodeService#getById(java.lang.Long)
	 */
	public AccountCodeVo getById(Long id) throws ServiceException {
		try{
			AccountCode entity = accountCodeDao.getById(id, AccountCodeImpl.class);

			if(null != entity)
				return AccountCodeVo.getInstance(entity,true);
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AccountCodeService#getByKey(java.lang.String, gov.nwcg.isuite.core.vo.AgencyVo)
	 */
	public AccountCodeVo getByKey(String accountCode, AgencyVo agencyVo) throws ServiceException {
		try{
			AccountCode entity = accountCodeDao.getByKey(accountCode,agencyVo.getId());

			if(null != entity)
				return AccountCodeVo.getInstance(entity,true);
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AccountCodeService#save(gov.nwcg.isuite.core.vo.AccountCodeVo)
	 */
	public AccountCodeVo save(AccountCodeVo vo) throws ServiceException {
		try{
			
			AccountCode entity = null;
			
			if((null != vo.getId()) && (vo.getId() > 0) ){
				entity = accountCodeDao.getById(vo.getId(), AccountCodeImpl.class);
			}else{
				/*
				 * Saving a new account code.
				 * Check if account code with
				 * the same key (account_code + agency_id)
				 * already exists
				 */
				entity = accountCodeDao.getByKey(vo.getAccountCode(), vo.getAgencyVo().getId());
			}
			
			entity = AccountCodeVo.toEntity(entity, vo, true);
			
			accountCodeDao.save(entity);
			
			return AccountCodeVo.getInstance(entity, true);
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

}
