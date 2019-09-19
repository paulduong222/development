package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.GridColumn;
import gov.nwcg.isuite.core.filter.GridColumnFilter;
import gov.nwcg.isuite.core.persistence.GridColumnDao;
import gov.nwcg.isuite.core.service.GridColumnService;
import gov.nwcg.isuite.core.vo.GridColumnVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * 
 * @author bsteiner
 */
public class GridColumnServiceImpl extends BaseService implements GridColumnService {
	private GridColumnDao gridColumnDao;

	public GridColumnServiceImpl(){

	}

	public void initialization(){
		gridColumnDao = (GridColumnDao)super.context.getBean("gridColumnDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.GridColumnService#getGridColumns(gov.nwcg.isuite.core.filter.GridColumnFilter)
	 */
	public Collection<GridColumnVo> getGridColumns(GridColumnFilter filter) throws ServiceException {

		try{
			Collection<GridColumn> entities = gridColumnDao.getGridColumns(filter);

			if(null!=entities){
				return GridColumnVo.getInstances(entities, true);
			}

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}

		return null;
	}


}
