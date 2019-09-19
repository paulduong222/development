package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.persistence.RateClassDao;
import gov.nwcg.isuite.core.service.RateClassService;
import gov.nwcg.isuite.core.vo.RateClassVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public class RateClassServiceImpl extends BaseService implements RateClassService {
   private RateClassDao rateClassDao;
   
   public RateClassServiceImpl(){
	   
   }
   
   public void initialization(){
	   rateClassDao = (RateClassDao)super.context.getBean("rateClassDao");
   }


	public RateClassVo getById(Long id) throws ServiceException {
		return null;
	}

	public Collection<RateClassVo> getPicklist() throws ServiceException {

		try{
			Collection<RateClassVo> vos = rateClassDao.getPicklist();
			
			return vos;
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}
   
   
}
