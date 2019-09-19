package gov.nwcg.isuite.core.cost.utilities;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;

import java.util.Date;

public class StartDateResolver {

	
	/**
	 * This method will try to resolve the incident resource daily cost 
	 * start date to use for the daily cost calculations.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Date resolveIRStartDate(IncidentResourceVo vo) throws Exception {
		Date startDate=null;
		
		try{
			/*
			 * e-ISuite Manage Costs - Daily Cost Use Case.pdf
			 * Page: 11
			 * 
			 *   The system must start generating Daily Costs for a Resource 
			 *   based on the Resource’s Assign Date.
			 */
			if(null != vo && null != vo.getCostDataVo()){
				if(DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
					Date dt=DateTransferVo.getTransferDate(vo.getCostDataVo().getAssignDateVo());
					return dt;
				}
			}else
				return null;
		}catch(Exception e){
			
		}
		
		return startDate;
	}
	
	public static Date resolveIROStartDate(IncidentResourceOtherVo iroVo) throws Exception {
		Date startDate=null;
		
		try{
			if(null != iroVo && null != iroVo.getCostDataVo())
				if(DateTransferVo.hasDateString(iroVo.getCostDataVo().getAssignDateVo())){
					Date dt=DateTransferVo.getTransferDate(iroVo.getCostDataVo().getAssignDateVo());
					return dt;
				}
			else
				return null;
		}catch(Exception e){
			
		}
		
		return startDate;
	}
	
	
}
