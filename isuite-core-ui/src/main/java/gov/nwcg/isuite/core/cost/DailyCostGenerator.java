package gov.nwcg.isuite.core.cost;

import gov.nwcg.isuite.core.cost.generator.GeneratorFactory;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.framework.cost.generator.ICostGenerator;
import gov.nwcg.isuite.framework.exceptions.DailyCostException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Date;
import java.util.Stack;

import org.springframework.context.ApplicationContext;

public class DailyCostGenerator {
	private ApplicationContext context = null;
	private Boolean updateRatesOnly=false;
	private Stack<Long> parentIdStack = new Stack<Long>();
	
	public DailyCostGenerator(ApplicationContext ctx){
		this.context=ctx;
	}

	public void setUpdateRatesOnly(Boolean val){
		this.updateRatesOnly=val;
	}
	
	/**
	 * Runs the daily costs for all resources in the incident.
	 * 
	 * @param incident
	 * @throws Exception
	 */
	public void runIncidentDailyCosts(Incident incident) throws DailyCostException {
		try{

			/*
			 * Loop through each incident resource in the incident,
			 * and call runResourceDailyCosts
			 */
			
		}catch(Exception e){
			if(e instanceof DailyCostException){
				throw (DailyCostException)e;
			}else{
				DailyCostException dce = new DailyCostException(e.getMessage());
				throw dce;
			}
		}
	}
	
	/**
	 * Runs the daily costs for a single resource.
	 * 
	 * @param incidentResource
	 * @param startDate
	 * @throws Exception
	 */
	public void runResourceDailyCosts(IncidentResource incidentResource, Date startDate, Boolean skipInvalidData) throws DailyCostException{
		
		try{
			
			/*
			 * e-ISuite Manage Costs - Daily Cost Use Case.pdf
			 * Page: 21
			 * 	The system must only generate daily costs for a Resource 
			 *  if the Generate Costs checkbox is selected
			 */
			//if(null != incidentResource.getCostData() 
			//		&& BooleanUtility.isTrue(incidentResource.getCostData().getGenerateCosts())){
				
				/*
				 * Get the appropriate CostGenerator based on the type of resource
				 */
				ICostGenerator costGen = 
					GeneratorFactory.getIncidentResourceGenerator(incidentResource);

				costGen.setContext(context);
				
				/*
				 * If this resource has children, run costs for children
				 */
				if(CollectionUtility.hasValue(incidentResource.getResource().getChildResources())){
					int size=incidentResource.getResource().getChildResources().size();
					int cnt=0;
					costGen.setIsParent(true);
					this.parentIdStack.push(incidentResource.getId());
					for(Resource rChild : incidentResource.getResource().getChildResources()){
						cnt++;
						IncidentResource irChild = (IncidentResource)rChild.getIncidentResources().iterator().next();
						
						this.runResourceDailyCosts(irChild, null,true);
					}
					if(cnt>0 && cnt==size)
						this.parentIdStack.pop();					
				}
				
				/*
				 * Set Generate costs flag for this resource
				 */
				IncidentResourceDailyCostDao irdcDao = (IncidentResourceDailyCostDao)context.getBean("incidentResourceDailyCostDao");
				IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
				
				/*
				 * below commented out 11/18/2013
				 * will now handle setting this the first time a subordinate has a time posting.
				 * 
				if(CollectionUtility.hasValue(incidentResource.getResource().getChildResources())){
					int resActualCount=irdcDao.getResourceActualsCount(incidentResource.getIncident().getId(), incidentResource.getResource().getId());
					int subActualCount=irdcDao.getSubordinateActualsCount(incidentResource.getIncident().getId(), incidentResource.getResource().getId());
					Boolean existingValue=incidentResource.getCostData().getGenerateCosts();
					Boolean newValue=false;
					if(subActualCount>0 && resActualCount < 1){
						newValue=false;
					}else
						newValue=true;

					// system will only change the flag one time
					if(existingValue != newValue && BooleanUtility.isFalse(StringBooleanEnum.toBooleanValue(incidentResource.getCostData().getGenerateCostsSys()))){
						incidentResource.getCostData().setGenerateCostsSys(StringBooleanEnum.Y);
						incidentResource.getCostData().setGenerateCosts(newValue);
						irDao.save(incidentResource);
						irDao.flushAndEvict(incidentResource);
						incidentResource=irDao.getById(incidentResource.getId(), IncidentResourceImpl.class);
						costGen.setIncidentResource(incidentResource);
					}else{
						incidentResource.getCostData().setGenerateCostsSys(StringBooleanEnum.Y);
						irDao.save(incidentResource);
						irDao.flushAndEvict(incidentResource);
						incidentResource=irDao.getById(incidentResource.getId(), IncidentResourceImpl.class);
						costGen.setIncidentResource(incidentResource);
					}
				}else{
					// set for child resources, if has actual, set gencosts=true
					if(null != incidentResource.getResource().getParentResource()){
						int resActualCount=irdcDao.getResourceActualsCount(incidentResource.getIncident().getId(), incidentResource.getResource().getId());
						Boolean existingValue=incidentResource.getCostData().getGenerateCosts();
						Boolean newValue=false;
						if(resActualCount > 0){
							newValue=true;
							// system will only change the flag one time
							if(BooleanUtility.isFalse(StringBooleanEnum.toBooleanValue(incidentResource.getCostData().getGenerateCostsSys()))){
								incidentResource.getCostData().setGenerateCostsSys(StringBooleanEnum.Y);
								incidentResource.getCostData().setGenerateCosts(newValue);
								irDao.save(incidentResource);
								irDao.flushAndEvict(incidentResource);
								incidentResource=irDao.getById(incidentResource.getId(), IncidentResourceImpl.class);
								costGen.setIncidentResource(incidentResource);
							}
						}else{
							incidentResource.getCostData().setGenerateCostsSys(StringBooleanEnum.Y);
							irDao.save(incidentResource);
							irDao.flushAndEvict(incidentResource);
							incidentResource=irDao.getById(incidentResource.getId(), IncidentResourceImpl.class);
							costGen.setIncidentResource(incidentResource);
						}
					}
				}
				 */

				/*
				 * Check if costs can be run for this resource
				 */
				String costException=irdcDao.getResourceCostException(incidentResource.getId());
				incidentResource.setDailyCostException((StringUtility.hasValue(costException) ? costException : null));
				irDao.save(incidentResource);
				irDao.flushAndEvict(incidentResource);
				incidentResource=irDao.getById(incidentResource.getId(), IncidentResourceImpl.class);
				costGen.setIncidentResource(incidentResource);
				
				if(StringUtility.hasValue(costException))
					return;
				
				costGen.generateCosts(startDate, this.updateRatesOnly);
				
				/*
				 * If this resource has a parent,  roll up the costs
				 */
				if(null != incidentResource.getResource().getParentResource()){
					Long parentIdStack=0L;
					if(this.parentIdStack.size()>0) parentIdStack=this.parentIdStack.peek();
					
					/*
					 * Get the appropriate CostGenerator based on the type of resource
					 */
					IncidentResource irParent = 
						incidentResource.getResource().getParentResource().getIncidentResources().iterator().next();

					if(parentIdStack.compareTo(irParent.getId())!=0)
						this.runUpdateParentDailyCost(irParent, startDate, null, true);
					/*
					ICostGenerator costGenParent = 
						GeneratorFactory.getIncidentResourceGenerator(irParent);

					costGenParent.setContext(context);
					costGenParent.generateCosts(null);
					*/
				}
				
			//}else{
			//	if(BooleanUtility.isFalse(skipInvalidData)){
			//		DailyCostException dce = new DailyCostException();
			//		Collection<ErrorObject> errors = new ArrayList<ErrorObject>();
			//		errors.add(new ErrorObject("info.generic","Cannot generate cost records for this Resource, Resource has the generate cost flag set to off."));
					
			//		dce.setErrorObjects(errors);
			//		throw dce;
			//	}
			//}
			
		}catch(Exception e){
			if(e instanceof DailyCostException){
				throw (DailyCostException)e;
			}else{
				DailyCostException dce = new DailyCostException(e.getMessage());
				throw dce;
			}
		}
	}

	public void runResourceOtherDailyCosts(IncidentResourceOther incidentResourceOther, Date startDate, Boolean skipInvalidData) throws DailyCostException{
		
		try{
			
			/*
			 * e-ISuite Manage Costs - Daily Cost Use Case.pdf
			 */
			//if(null != incidentResourceOther.getCostData() 
			//		&& BooleanUtility.isTrue(incidentResourceOther.getCostData().getGenerateCosts())){
				
				/*
				 * Get the appropriate CostGenerator based on the type of resource
				 */
				ICostGenerator costGen = 
					GeneratorFactory.getIncidentResourceOtherGenerator(incidentResourceOther);

				costGen.setContext(context);
				
				/*
				 * Generate the costs for this resource
				 */
				costGen.generateCosts(startDate, this.updateRatesOnly);
				
			//}else{
			//	if(BooleanUtility.isFalse(skipInvalidData)){
			//		DailyCostException dce = new DailyCostException();
			//		Collection<ErrorObject> errors = new ArrayList<ErrorObject>();
			//		errors.add(new ErrorObject("info.generic","Cannot generate cost records for this Resource, Resource has the generate cost flag set to off."));
					
			//		dce.setErrorObjects(errors);
			//		throw dce;
			//	}
			//}
			
		}catch(Exception e){
			if(e instanceof DailyCostException){
				throw (DailyCostException)e;
			}else{
				DailyCostException dce = new DailyCostException(e.getMessage());
				throw dce;
			}
		}
	}
	
	/**
	 * @param incidentResource
	 * @param startDate
	 * @param skipInvalidData
	 * @throws DailyCostException
	 */
	public void runUpdateParentDailyCost(IncidentResource incidentResource, Date startDate, Date endDate,Boolean skipInvalidData) throws DailyCostException{

		try{
			//if(null != incidentResource.getCostData() 
			//		&& BooleanUtility.isTrue(incidentResource.getCostData().getGenerateCosts())){

				/*
				 * Get the appropriate CostGenerator based on the type of resource
				 */
				ICostGenerator costGen = 
					GeneratorFactory.getIncidentResourceGenerator(incidentResource);

				costGen.setContext(context);
				costGen.setIsParent(true);
				
				/*
				 * Generate the costs for this resource
				 */
				costGen.generateCosts(startDate,this.updateRatesOnly);

				/*
				 * If this resource has a parent,  roll up the costs
				 */
				if(null != incidentResource.getResource().getParentResource()){
					/*
					 * Get the appropriate CostGenerator based on the type of resource
					 */
					IncidentResource irParent = 
						incidentResource.getResource().getParentResource().getIncidentResources().iterator().next();

					ICostGenerator costGenParent = 
						GeneratorFactory.getIncidentResourceGenerator(irParent);

					costGenParent.setContext(context);
					costGenParent.generateCosts(null,this.updateRatesOnly);
				}

			/*
			}else{
				if(BooleanUtility.isFalse(skipInvalidData)){
					DailyCostException dce = new DailyCostException();
					Collection<ErrorObject> errors = new ArrayList<ErrorObject>();
					errors.add(new ErrorObject("info.generic","Cannot generate cost records for this Resource, Resource has the generate cost flag set to off."));

					dce.setErrorObjects(errors);
					throw dce;
				}
			}
			*/
		}catch(Exception e){
			if(e instanceof DailyCostException){
				throw (DailyCostException)e;
			}else{
				DailyCostException dce = new DailyCostException(e.getMessage());
				throw dce;
			}
		}
	}
	
	/**
	 * Runs the daily costs for a single other resource.
	 * 
	 * @param incidentResourceOther
	 * @param startDate
	 * @throws Exception
	 */
	public void runResourceOtherDailyCosts(IncidentResourceOther iroEntity, Date startDate) throws DailyCostException {
		
		try{
			/*
			 * Get the appropriate CostGenerator based on the type of resource
			 */
			ICostGenerator costGen = GeneratorFactory.getIncidentResourceOtherGenerator(iroEntity);
		
			/*
			 * Generate the costs
			 */
			costGen.generateCosts(startDate,this.updateRatesOnly);
			
		}catch(Exception e){
			if(e instanceof DailyCostException){
				throw (DailyCostException)e;
			}else{
				DailyCostException dce = new DailyCostException(e.getMessage());
				throw dce;
			}
		}
	}
	
	/**
	 * @param startDate
	 * @param dailyCostOrigin
	 * @throws DailyCostException
	 */
	public void runResourceUserFlowdown(IncidentResourceDailyCost dailyCostOrigin, IncidentResourceDailyCostVo preSaveVo) throws DailyCostException{
		
		try{
			
			/*
			 * Get the appropriate CostGenerator based on the type of resource
			 */
			ICostGenerator costGen = 
				GeneratorFactory.getIncidentResourceGenerator(dailyCostOrigin.getIncidentResource());

			costGen.setContext(context);
				
				
			/*
			 * Generate the flowdown
			 */
			costGen.userFlowdown(dailyCostOrigin, preSaveVo);
			
			/*
			 * If this resource has a parent,  roll up the costs
			 */
			if(null != dailyCostOrigin.getIncidentResource().getResource().getParentResource()){
				/*
				 * Get the appropriate CostGenerator based on the type of resource
				 */
				IncidentResource irParent = 
					dailyCostOrigin.getIncidentResource().getResource().getParentResource().getIncidentResources().iterator().next();
				
				this.runUpdateParentDailyCost(irParent, null, null,true);
				/*
				ICostGenerator costGenParent = 
					GeneratorFactory.getIncidentResourceGenerator(irParent);

				costGenParent.setContext(context);
				costGenParent.generateCosts(null);
				*/
			}
			
			
		}catch(Exception e){
			if(e instanceof DailyCostException){
				throw (DailyCostException)e;
			}else{
				DailyCostException dce = new DailyCostException(e.getMessage());
				throw dce;
			}
		}
	}
	
	public void runResourceOtherUserFlowdown(IncidentResourceDailyCost dailyCostOrigin, IncidentResourceDailyCostVo preSaveVo) throws DailyCostException{
		
		try{
			
			/*
			 * Get the appropriate CostGenerator based on the type of resource
			 */
			ICostGenerator costGen = GeneratorFactory.getIncidentResourceOtherGenerator(dailyCostOrigin.getIncidentResourceOther());

			costGen.setContext(context);
				
				
			/*
			 * Generate the flowdown
			 */
			costGen.userFlowdown(dailyCostOrigin,preSaveVo);
			
		}catch(Exception e){
			if(e instanceof DailyCostException){
				throw (DailyCostException)e;
			}else{
				DailyCostException dce = new DailyCostException(e.getMessage());
				throw dce;
			}
		}
	}

	public void runPropagatePrimaryData(IncidentResource parent, Long costGroupId, Long shiftId) throws Exception {
		
		for(Resource child : parent.getResource().getChildResources()){

			// run update sql
			IncidentResourceDailyCostDao irdcDao = (IncidentResourceDailyCostDao)context.getBean("incidentResourceDailyCostDao");
			Long childId = child.getIncidentResources().iterator().next().getId();

			if(LongUtility.hasValue(costGroupId))
				irdcDao.setCostGroupId(childId, costGroupId);
			
			if(LongUtility.hasValue(shiftId))
				irdcDao.setShiftId(childId, shiftId);
			
			if(CollectionUtility.hasValue(child.getChildResources())){
				this.runPropagatePrimaryData(child.getIncidentResources().iterator().next(), costGroupId, shiftId);
			}
		}
		
	}

	public void runPropagateCostGroup(IncidentResource parent, Long costGroupId, Long shiftId) throws Exception {
		
		for(Resource child : parent.getResource().getChildResources()){

			// run update sql
			IncidentResourceDailyCostDao irdcDao = (IncidentResourceDailyCostDao)context.getBean("incidentResourceDailyCostDao");
			Long childId = child.getIncidentResources().iterator().next().getId();

			if(LongUtility.hasValue(costGroupId))
				irdcDao.setCostGroupId(childId, costGroupId);
			
			if(LongUtility.hasValue(shiftId))
				irdcDao.setShiftId(childId, shiftId);
			
			if(CollectionUtility.hasValue(child.getChildResources())){
				this.runPropagateCostGroup(child.getIncidentResources().iterator().next(), costGroupId, shiftId);
			}
		}
		
	}

}
