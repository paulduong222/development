package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.impl.CountrySubdivisionImpl;
import gov.nwcg.isuite.core.filter.CountrySubdivisionFilter;
import gov.nwcg.isuite.core.persistence.CountryCodeSubdivisionDao;
import gov.nwcg.isuite.core.service.CountrySubdivisionService;
import gov.nwcg.isuite.core.vo.CountryCodeSubdivisionVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.ErrorEnum;

import java.util.Collection;

/**
 * This class allows a user
 * to add/edit/and otherwise modify a CountrySubdivision record in the system
 * 
 * @author gdyer
 */
public class CountrySubdivisionServiceImpl extends BaseService implements CountrySubdivisionService {
	private CountryCodeSubdivisionDao countryCodeSubdivisionDao;

	/**
	 * Default Constructor
	 */
	public CountrySubdivisionServiceImpl() {
	}

	public void initialization(){
		countryCodeSubdivisionDao = (CountryCodeSubdivisionDao)super.context.getBean("countryCodeSubdivisionDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CountrySubdivisionService#getById(java.lang.Long)
	 */
	@Override
	public CountryCodeSubdivisionVo getById(Long id) throws ServiceException {
		
		try {
			if (id == null) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"CountrySubdivision[id=null]");

			CountrySubdivision entity = countryCodeSubdivisionDao.getById(id, CountrySubdivisionImpl.class);

			if (null == entity) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"CountrySubdivision[id=null]");
				
			return CountryCodeSubdivisionVo.getInstance(entity, true);         
			
		} catch ( Exception e ) {
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CountrySubdivisionService#getGrid(gov.nwcg.isuite.core.filter.CountrySubdivisionFilter)
	 */
	@Override
	public Collection<CountryCodeSubdivisionVo> getGrid(CountrySubdivisionFilter filter) throws ServiceException {
		try {
			Collection<CountryCodeSubdivisionVo> rtnval = countryCodeSubdivisionDao.getGrid(filter);
			return rtnval;
		} catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CountrySubdivisionService#save(gov.nwcg.isuite.core.vo.CountrySubdivisionVo)
	 */
	@Override
	public CountryCodeSubdivisionVo save(CountryCodeSubdivisionVo vo) throws ServiceException {
		CountrySubdivision entity = null;

		try {

			if( (null != vo.getId()) && (vo.getId().compareTo(0L) > 0)) {
				/*
				 *  Updating existing one
				 */
				entity = countryCodeSubdivisionDao.getById(vo.getId(), CountrySubdivisionImpl.class);

				if (entity == null) 
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"CountrySubdivision["+vo.getId()+"]");
				if (entity.isStandard()) {
					super.handleException(ErrorEnum.CANNOT_EDIT_STANDARD_RECORDS);
				}

			} else {
				/*
				 * Adding new one, new ones are always non-standard
				 */
				entity = new CountrySubdivisionImpl();
				vo.setStandard(false);
			}

			if( (null == vo.getId()) || (vo.getId() < 1 ) ){
				if (!countryCodeSubdivisionDao.isCodeUnique(vo.getCountrySubAbbreviation())) 
					super.handleException(ErrorEnum._0219_DUPLICATE_REFERENCE_DATA);
			}
			
			entity = CountryCodeSubdivisionVo.toEntity(vo, true);
			
			countryCodeSubdivisionDao.save(entity);
			
			//entity=countryCodeSubdivisionDao.getById(entity.getId(), CountrySubdivisionImpl.class);
				
			return CountryCodeSubdivisionVo.getInstance(entity, true);

		} catch(ServiceException se){
			throw se;
		}catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.CountrySubdivisionService#delete(java.util.Collection)
	 */
	@Override
	public void delete(Collection<Long> countryCodeSubdivisionIds) throws ServiceException {
		try {
			if ((countryCodeSubdivisionIds == null) || (countryCodeSubdivisionIds.size() < 1)) {
				throw new ServiceException("invalid or missing Ids");
			}
			for (Long countryCodeSubdivisionId : countryCodeSubdivisionIds) {
				CountrySubdivision persistable = countryCodeSubdivisionDao.getById(countryCodeSubdivisionId, CountryCodeSubdivisionVo.class);
				if (persistable != null) {
					if (!persistable.isStandard()) {
						countryCodeSubdivisionDao.delete(persistable);
					} else {
						super.handleException(ErrorEnum.CANNOT_EDIT_STANDARD_RECORDS);
					}
				} else {
					throw new ServiceException("cannot find record with id: " + countryCodeSubdivisionId);
				}
			}
		} catch (ServiceException se){
			throw se;
		} catch ( Exception e ) {
			super.handleException(e);
		}
	}

}
