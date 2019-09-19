package gov.nwcg.isuite.core.cost.operations.calculator;

import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceDailyCostImpl;
import gov.nwcg.isuite.framework.types.CostLevelEnum;
import gov.nwcg.isuite.framework.types.ResourceCostTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import java.util.Date;

public class FlowdownCalculator {
	public ResourceCostTypeEnum resourceCostType=null;

	/**
	 * Creates and returns a flowdown for the date specified.
	 * 
	 * @param type
	 * @param date
	 * @param irEntity
	 * @return
	 */
	public IncidentResourceDailyCost generateFlowdown(
										IncidentResourceDailyCost previousDailyCost
										,IncidentResourceDailyCost dailyCost
										,Date date){
		
		
		// init the actual daily cost if null
		if(null==dailyCost)
			dailyCost=new IncidentResourceDailyCostImpl();
		else{
			// if dailyCost.isLocked==true then return it with no changes
			if(BooleanUtility.isTrue(dailyCost.getIsLocked()))
				return dailyCost;
		}

		dailyCost.setResourceCostType(this.resourceCostType);
		
		// update with correct values
		dailyCost.setActivityDate(date);
		dailyCost.setCostLevel("F");
		dailyCost.setAccrualCode(previousDailyCost.getAccrualCode());
		dailyCost.setAircraftCostAmount(previousDailyCost.getAircraftCostAmount());
		dailyCost.setCargoPounds(previousDailyCost.getCargoPounds());
		dailyCost.setCostGroup(previousDailyCost.getCostGroup());
		dailyCost.setFlightHours(previousDailyCost.getFlightHours());
		dailyCost.setIncidentAccountCode(previousDailyCost.getIncidentAccountCode());
		dailyCost.setIncidentResource(previousDailyCost.getIncidentResource());
		dailyCost.setIncidentResourceOther(previousDailyCost.getIncidentResourceOther());
		dailyCost.setIncidentShift(previousDailyCost.getIncidentShift());
		//dailyCost.setIsLocked(previousDailyCost.getIsLocked());
		dailyCost.setNumberOfLoads(previousDailyCost.getNumberOfLoads());
		dailyCost.setNumberOfPassengers(previousDailyCost.getNumberOfPassengers());
		dailyCost.setNumberOfTrips(previousDailyCost.getNumberOfTrips());
		dailyCost.setPrimaryTotalAmount(previousDailyCost.getPrimaryTotalAmount());
		dailyCost.setRateType(previousDailyCost.getRateType());
		dailyCost.setResourceCostType(previousDailyCost.getResourceCostType());
		dailyCost.setRetardantGallons(previousDailyCost.getRetardantGallons());
		dailyCost.setSubordinateTotalAmount(previousDailyCost.getSubordinateTotalAmount());
		dailyCost.setAdjustmentAmount(previousDailyCost.getAdjustmentAmount());
		dailyCost.setTotalCostAmount(previousDailyCost.getTotalCostAmount());
		dailyCost.setUnitCostAmount(previousDailyCost.getUnitCostAmount());
		dailyCost.setUnits(previousDailyCost.getUnits());
		dailyCost.setWaterGallons(previousDailyCost.getWaterGallons());
		
		return dailyCost;
	}

	
}
