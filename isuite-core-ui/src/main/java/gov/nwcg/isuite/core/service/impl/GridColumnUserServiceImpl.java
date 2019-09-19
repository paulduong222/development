package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.GridColumnUser;
import gov.nwcg.isuite.core.filter.GridColumnUserFilter;
import gov.nwcg.isuite.core.persistence.GridColumnUserDao;
import gov.nwcg.isuite.core.service.GridColumnUserService;
import gov.nwcg.isuite.core.vo.GridColumnUserVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.ErrorEnum;

import java.util.Collection;

/**
 * 
 * @author bsteiner
 */
public class GridColumnUserServiceImpl extends BaseService implements GridColumnUserService {
   private GridColumnUserDao gridColumnUserDao;

   public GridColumnUserServiceImpl(){
	   
   }
   
   public void initialization(){
	   gridColumnUserDao = (GridColumnUserDao)super.context.getBean("gridColumnUserDao");
   }

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.GridColumnUserService#getUserGridColumns(gov.nwcg.isuite.core.filter.GridColumnUserFilter)
	 */
	public Collection<GridColumnUserVo> getUserGridColumns(GridColumnUserFilter filter) throws ServiceException {
	
		try{
			Collection<GridColumnUser> entities = gridColumnUserDao.getGridColumns(filter);
			
			if(null!=entities){
				return GridColumnUserVo.getInstances(entities, true);
			}
			
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.GridColumnUserService#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<GridColumnUserVo> vos) throws ServiceException, ValidationException {
		try{
		   // Verify at least two selections have been made
		   int selectedCount = 0;
		   for (GridColumnUserVo vo : vos) {
		      if (vo.getVisible()) {
		         selectedCount++;
		      }
		   }
		   
		   if (selectedCount < 2) {
            super.handleException(new ServiceException(new ErrorObject(ErrorEnum._0204_LESS_THAN_TWO_PREFERENCE_COLUMN_SELECTIONS)));
		   }
		   
			Collection<GridColumnUser> gridColumnUsers = GridColumnUserVo.toEntityList(vos,true);

			gridColumnUserDao.saveAll(gridColumnUsers);
			
		}catch (PersistenceException pe){
			throw new ServiceException (pe);
		}catch (ValidationException ve){
			throw ve;
		}catch(Exception e){
			throw new ServiceException (e);
		}
	}

   
}
