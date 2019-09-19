package gov.nwcg.isuite.framework.cost.generator;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.framework.exceptions.DailyCostException;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public interface ICostGenerator {

	public void setContext(ApplicationContext ctx);
	
	/**
	 * Sets the incidentResource.
	 * 
	 * @param irEntity
	 */
	public void setIncidentResource(IncidentResource irEntity);

	/**
	 * Sets the incidentResourceVo.
	 * 
	 * @param irVo
	 */
	public void setIncidentResourceVo(IncidentResourceVo irVo);
	
	/**
	 * @param roEntity
	 */
	public void setIncidentResourceOther(IncidentResourceOther iroEntity);

	/**
	 * @param roVo
	 */
	public void setIncidentResourceOtherVo(IncidentResourceOtherVo iroVo);
	
	public void setRunEndDate(Date dt);
	
	/**
	 * @throws Exception
	 */
	public void generateCosts(Date startDate,Boolean updateRatesOnly) throws Exception;
	
	/**
	 * @param dailyCostOrigin
	 * @throws Exception
	 */
	public void userFlowdown(IncidentResourceDailyCost dailyCostOrigin,IncidentResourceDailyCostVo preSaveVo) throws DailyCostException;
	
	/**
	 * @throws Exception
	 */
	public void rollup(IncidentResource incidentResource) throws Exception;
	
	public void setIsParent(Boolean val);
	
	public void setGenerateParentEstimates(Boolean val);
	
	public void setCostResourceDataVo(CostResourceDataVo costResourceDataVo);
}
