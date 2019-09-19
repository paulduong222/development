package gov.nwcg.isuite.core.cost.generator;

import gov.nwcg.isuite.core.cost.generator.impl.AircraftCostGenerator;
import gov.nwcg.isuite.core.cost.generator.impl.OtherResourceCostGenerator;
import gov.nwcg.isuite.core.cost.generator.impl.ResourceCostGenerator;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.framework.cost.generator.ICostGenerator;

public class GeneratorFactory {

	public static ICostGenerator getIncidentResourceGenerator(IncidentResource incidentResource) throws Exception{
		ICostGenerator gen = null;
		
		/*
		 * Evaluate the type of resource and return the
		 * appropriate cost type generator.
		 * (Resource, Aircraft, ResourceOther)
		 */
		IncidentResourceVo incidentResourceVo = IncidentResourceVo.getInstance(incidentResource,true);
		
		//TODO: handle if aircraft and iscontractor 
		//TODO: handle for resourceother
		Boolean isContractor=false;
		
		if(!isContractor && incidentResourceVo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo().getRequestCategoryVo().getCode().equals("A")){
			gen = new AircraftCostGenerator();
		}else{
			gen = new ResourceCostGenerator();
		}
		
		gen.setIncidentResource(incidentResource);
		gen.setIncidentResourceVo(incidentResourceVo);
		
		return gen;
	}

	public static ICostGenerator getIncidentResourceOtherGenerator(IncidentResourceOther incidentResourceOther) throws Exception{
		ICostGenerator gen = new OtherResourceCostGenerator();
		
		/*
		 * Evaluate the type of resource and return the
		 * appropriate cost type generator.
		 * (Resource, Aircraft, ResourceOther)
		 */
		IncidentResourceOtherVo incidentResourceOtherVo = IncidentResourceOtherVo.getInstance(incidentResourceOther,true);
		
		gen.setIncidentResourceOther(incidentResourceOther);
		gen.setIncidentResourceOtherVo(incidentResourceOtherVo);
		
		return gen;
	}
	
}
