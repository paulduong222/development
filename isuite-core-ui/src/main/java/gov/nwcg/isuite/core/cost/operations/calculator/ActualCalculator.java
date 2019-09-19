package gov.nwcg.isuite.core.cost.operations.calculator;

import gov.nwcg.isuite.core.cost.data.ActualCostData;
import gov.nwcg.isuite.core.cost.data.DefaultCostRateData;
import gov.nwcg.isuite.core.domain.AccrualCode;
import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.ContractorRate;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.impl.AccrualCodeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceDailyCostImpl;
import gov.nwcg.isuite.framework.types.AdjustmentTypeEnum;
import gov.nwcg.isuite.framework.types.CostLevelEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceCostTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class ActualCalculator {
	// Default Rate 
	public DefaultCostRateData defaultCostRateData=null;
	public ResourceCostTypeEnum resourceCostType=null;
	public Boolean isUpdatingRate=false;
	private IncidentResourceDailyCost tempDailyCost;
	private Long iacId=0L;
	
	/**
	 * Creates and returns an actual for the date specified.
	 * 
	 * @param type
	 * @param date
	 * @param irEntity
	 * @return
	 */
	public IncidentResourceDailyCost generateActual(IncidentResourceDailyCost dailyCost
										,ResourceCostTypeEnum type
										,Date date
										,IncidentResource irEntity
										,IncidentResourceOther iroEntity
										,Collection<AssignmentTimePost> timePosts
										,Collection<TimeAssignAdjust> adjustments) {
		
		Boolean isNewCost=false;
		String previousCostLevel=null;

		if(null!=dailyCost.getIncidentAccountCode())
			this.iacId=dailyCost.getIncidentAccountCode().getId();
		
		// init the actual daily cost if null
		if(null==dailyCost){
			isNewCost=true;
			this.isUpdatingRate=true;
			dailyCost=new IncidentResourceDailyCostImpl();
		}else{
			// if dailyCost.isLocked==true then return it with no changes
			if(BooleanUtility.isTrue(dailyCost.getIsLocked()))
				return dailyCost;
			previousCostLevel=dailyCost.getCostLevel();
		}
	
		this.tempDailyCost=dailyCost;
		
		dailyCost.setActivityDate(date);
		dailyCost.setCostLevel("A");
		dailyCost.setIncidentResource(irEntity);
		dailyCost.setResourceCostType(this.resourceCostType);
		
		if(null!=irEntity.getCostData()
				&& null!=irEntity.getCostData().getAccrualCode()){
			AccrualCode accrualCode = new AccrualCodeImpl();
			accrualCode.setId(irEntity.getCostData().getAccrualCode().getId());
			dailyCost.setAccrualCode(accrualCode);
		}
		
		if(null != this.defaultCostRateData.costGroup){
			dailyCost.setCostGroup(this.defaultCostRateData.costGroup);
		}
		
		if(null != this.defaultCostRateData.incidentShift){
			dailyCost.setIncidentShift(this.defaultCostRateData.incidentShift);
		}
		
		//TODO: need to add incident_resource_other_id to daily cost table
		//dailyCost.setIncidentResourceOther(iroEntity);

		// get total amount/units of all time posts for this date
		ActualCostData actualCostData = new ActualCostData();

		// get amounts based on employment type
		if(null==this.defaultCostRateData.employmentType){
			if(LongUtility.hasValue(dailyCost.getId())){
				return dailyCost;
			}
		}else if(this.defaultCostRateData.employmentType==EmploymentTypeEnum.FED){
			/*
			 * FED 
			 */
			actualCostData.rateAmount=this.defaultCostRateData.rateAmount.doubleValue();
			actualCostData.rateType=this.defaultCostRateData.rateType;
			
			actualCostData=getTotalTimeAmountFed(actualCostData,date,timePosts,adjustments);

			// set rate type
			dailyCost.setRateType(actualCostData.rateType);
			
			if(this.isUpdatingRate==true){
				// set unit cost amount 
				dailyCost.setUnitCostAmount(BigDecimal.valueOf(actualCostData.rateAmount));
			}else{
				//if(isNewCost==false && previousCostLevel != CostLevelEnum.A)
				//	dailyCost.setUnitCostAmount(BigDecimal.valueOf(actualCostData.rateAmount));
					
				// set rate amount in case emp type changed
				dailyCost.setUnitCostAmount(BigDecimal.valueOf(actualCostData.rateAmount));
			}

			dailyCost.setUnits(new BigDecimal(actualCostData.units));
			
			// set incident account code (should already be set?)
			//dailyCost.setIncidentAccountCode(actualCostData.iac);
			
		}else if(this.defaultCostRateData.employmentType==EmploymentTypeEnum.OTHER){
			/*
			 * OTHER 
			 */
			actualCostData=getTotalTimeAmountOther(actualCostData,date,timePosts,adjustments);

			// set rate type
			dailyCost.setRateType(actualCostData.rateType);
			
			if(this.isUpdatingRate==true){
				dailyCost.setUnitCostAmount(BigDecimal.valueOf(actualCostData.rateAmount));
			}else{
				// set rate amount in case emp type changed
				dailyCost.setUnitCostAmount(BigDecimal.valueOf(actualCostData.rateAmount));
			}
			
			dailyCost.setUnits(new BigDecimal(actualCostData.units));
			
			// set incident account code
			dailyCost.setIncidentAccountCode(actualCostData.iac);
			
		}else if(this.defaultCostRateData.employmentType==EmploymentTypeEnum.AD){
			/*
			 * AD 
			 */
			actualCostData=getTotalTimeAmountAd(actualCostData,date,timePosts,adjustments);

			// set rate type
			dailyCost.setRateType(actualCostData.rateType);
			
			if(this.isUpdatingRate==true){
				// set unit cost amount 
				dailyCost.setUnitCostAmount(BigDecimal.valueOf(actualCostData.rateAmount));
			}else{
				// set rate amount in case emp type changed
				dailyCost.setUnitCostAmount(BigDecimal.valueOf(actualCostData.rateAmount));
			}
			
			dailyCost.setUnits(new BigDecimal(actualCostData.units));

			// set incident account code
			dailyCost.setIncidentAccountCode(actualCostData.iac);
			
		}else if(this.defaultCostRateData.employmentType==EmploymentTypeEnum.CONTRACTOR){
			/*
			 * CONTRACTOR
			 */
			try{
				actualCostData=getTotalTimeAmountContractor(actualCostData,date,timePosts,adjustments, irEntity);
			}catch(Exception e){
				//System.out.println(e.getMessage());
			}
			
			// set rate type
			dailyCost.setUnits(BigDecimal.valueOf(1));
			dailyCost.setRateType("DAILY");
			//if(this.isUpdatingRate==true){
				// set unit cost amount 
				dailyCost.setUnitCostAmount(BigDecimal.valueOf(actualCostData.rateAmount));
			//}
			
			// set incident account code
			dailyCost.setIncidentAccountCode(actualCostData.iac);
		}
		
		// add time post total to adjustment total
		double totalPrimaryAmount=(actualCostData.totalAmount + actualCostData.adjustmentAmount);
		
		dailyCost.setPrimaryTotalAmount(BigDecimal.valueOf(totalPrimaryAmount));
		if(null != dailyCost.getSubordinateTotalAmount())
			dailyCost.setTotalCostAmount(dailyCost.getPrimaryTotalAmount().add(dailyCost.getSubordinateTotalAmount()));
		else
			dailyCost.setTotalCostAmount(dailyCost.getPrimaryTotalAmount());
		dailyCost.setUnits(new BigDecimal(actualCostData.units));
		dailyCost.setAdjustmentAmount(BigDecimal.valueOf(actualCostData.adjustmentAmount));
		
		return dailyCost;
	}

	/**
	 * Return the total amount of all time posts for FED.
	 * 
	 * @param date
	 * @param totalUnits
	 * @param timePosts
	 * @return
	 */
	private ActualCostData getTotalTimeAmountFed(ActualCostData actualCostData,Date date
						, Collection<AssignmentTimePost> timePosts, Collection<TimeAssignAdjust> adjustments){
		double timeTotal = 0.0;
		double units=0.0;
		double rateAmount=actualCostData.rateAmount;
		Boolean hasFedPosting=false;
		
		for(AssignmentTimePost atp : timePosts){
			if(DateUtil.isSameDate(atp.getPostStartDate(), date)
					&& atp.getIncidentAccountCode().getId().compareTo(iacId)==0){

				actualCostData.iac=atp.getIncidentAccountCode();
				
				// since atp could have been posted under a different emp type
				// keep track of the unit count
				if(atp.getEmploymentType()==EmploymentTypeEnum.FED){
					hasFedPosting=true;
					rateAmount=actualCostData.rateAmount;
					units=units+atp.getQuantity().doubleValue();
				}else if(atp.getEmploymentType()==EmploymentTypeEnum.OTHER){
					units=units+atp.getQuantity().doubleValue();
				}else if(atp.getEmploymentType()==EmploymentTypeEnum.AD){
					units=units+atp.getQuantity().doubleValue();
				}
			}
			
		}

		BigDecimal amt=BigDecimal.valueOf(0.0);
		actualCostData.units=units;
		
		if(hasFedPosting==true){
			amt=new BigDecimal(units).multiply(new BigDecimal(rateAmount));
			//atp.getQuantity().multiply(atp.getRateAmount());
			actualCostData.rateAmount=rateAmount;
		}else if(LongUtility.hasValue(this.tempDailyCost.getId())){
			actualCostData.rateAmount=this.tempDailyCost.getUnitCostAmount().doubleValue();
			amt=this.tempDailyCost.getUnits().multiply(this.tempDailyCost.getUnitCostAmount());
		}else{
			// ?
		}
		
		// sum up all amt's
		timeTotal+=amt.doubleValue();
		
		// apply adustments
		double adjTotal = 0.0;
		for(TimeAssignAdjust taa : adjustments ){
			if(DateUtil.isSameDate(taa.getActivityDate(), date)
					&& taa.getIncidentAccountCode().getId().compareTo(iacId)==0){

				BigDecimal amt2=taa.getAdjustmentAmount();
				
				// sum up all adjustments
				if(taa.getAdjustmentType()==AdjustmentTypeEnum.ADDITION)
					adjTotal+=amt2.doubleValue();
				else
					adjTotal+= (-amt2.doubleValue());
			}
			
		}
		actualCostData.adjustmentAmount=adjTotal;
		
		actualCostData.totalAmount=timeTotal;
		
		return actualCostData;
	}

	/**
	 * Return the total amount of all time posts for OTHER.
	 * 
	 * @param actualCostData
	 * @param date
	 * @param timePosts
	 * @return
	 */
	private ActualCostData getTotalTimeAmountOther(ActualCostData actualCostData,Date date
										, Collection<AssignmentTimePost> timePosts, Collection<TimeAssignAdjust> adjustments){
		double timeTotal = 0.0;
		double units=0.0;
		double rateAmount=actualCostData.rateAmount;
		Boolean hasOtherPosting=false;
		
		for(AssignmentTimePost atp : timePosts){
			if(DateUtil.isSameDate(atp.getPostStartDate(), date)
					&& atp.getIncidentAccountCode().getId().compareTo(iacId)==0){

				actualCostData.iac=atp.getIncidentAccountCode();
				
				// since atp could have been posted under a different emp type
				// keep track of the unit count
				if(atp.getEmploymentType()==EmploymentTypeEnum.OTHER){
					hasOtherPosting=true;
					units=units+atp.getQuantity().doubleValue();
					
					// use rateamount from atp (last atp rateamount is used)
					rateAmount=atp.getOtherRate().doubleValue();
					//actualCostData.rateAmount=atp.getRateAmount().doubleValue();
					//actualCostData.units=atp.getQuantity().intValue();
				}else if(atp.getEmploymentType()==EmploymentTypeEnum.FED){
					units=units+atp.getQuantity().doubleValue();
				}else if(atp.getEmploymentType()==EmploymentTypeEnum.AD){
					units=units+atp.getQuantity().doubleValue();
				}
			}
			
		}

		BigDecimal amt=BigDecimal.valueOf(0.0);
		actualCostData.units=units;
		
		if(hasOtherPosting==true){
			amt=new BigDecimal(units).multiply(new BigDecimal(rateAmount));
			//atp.getQuantity().multiply(atp.getRateAmount());
			actualCostData.rateAmount=rateAmount;
		}else if(LongUtility.hasValue(this.tempDailyCost.getId())){
			actualCostData.rateAmount=this.tempDailyCost.getUnitCostAmount().doubleValue();
			amt=this.tempDailyCost.getUnits().multiply(this.tempDailyCost.getUnitCostAmount());
		}else{
			// ?
		}
		
		// sum up all amt's
		timeTotal+=amt.doubleValue();

		// apply adustments
		double adjTotal = 0.0;
		for(TimeAssignAdjust taa : adjustments ){
			if(DateUtil.isSameDate(taa.getActivityDate(), date)
				&& taa.getIncidentAccountCode().getId().compareTo(iacId)==0){
				BigDecimal amt2=taa.getAdjustmentAmount();
				
				// sum up all adjustments
				if(taa.getAdjustmentType()==AdjustmentTypeEnum.ADDITION)
					adjTotal+=amt2.doubleValue();
				else
					adjTotal+= (-amt2.doubleValue());
			}
			
		}
		actualCostData.adjustmentAmount=adjTotal;
		
		actualCostData.totalAmount=timeTotal;
		
		return actualCostData;
	}

	/**
	 * Return the total amount of all time posts for AD.
	 * 
	 * @param actualCostData
	 * @param date
	 * @param timePosts
	 * @return
	 */
	private ActualCostData getTotalTimeAmountAd(ActualCostData actualCostData,Date date
			, Collection<AssignmentTimePost> timePosts, Collection<TimeAssignAdjust> adjustments){
		
		double timeTotal = 0.0;
		double units=0.0;
		double rateAmount=actualCostData.rateAmount;
		Boolean hasAdPosting=false;
		
		for(AssignmentTimePost atp : timePosts){
			if(DateUtil.isSameDate(atp.getPostStartDate(), date)
					&& atp.getIncidentAccountCode().getId().compareTo(iacId)==0){

				actualCostData.iac=atp.getIncidentAccountCode();
		
				// since atp could have been posted under a different emp type
				// keep track of the unit count
				if(atp.getEmploymentType()==EmploymentTypeEnum.AD){
					hasAdPosting=true;
					units=units+atp.getQuantity().doubleValue();
					
					// use rateamount from atp (last atp rateamount is used)
					rateAmount=atp.getRateAmount().doubleValue();
					//actualCostData.rateAmount=atp.getRateAmount().doubleValue();
					//actualCostData.units=atp.getQuantity().intValue();
				}else if(atp.getEmploymentType()==EmploymentTypeEnum.FED){
					units=units+atp.getQuantity().doubleValue();
				}else if(atp.getEmploymentType()==EmploymentTypeEnum.OTHER){
					units=units+atp.getQuantity().doubleValue();
				}
				
			}
			
		}

		BigDecimal amt=BigDecimal.valueOf(0.0);
		actualCostData.units=units;
		
		if(hasAdPosting==true){
			amt=new BigDecimal(units).multiply(new BigDecimal(rateAmount));
			//atp.getQuantity().multiply(atp.getRateAmount());
			actualCostData.rateAmount=rateAmount;
		}else if(LongUtility.hasValue(this.tempDailyCost.getId())){
			actualCostData.rateAmount=this.tempDailyCost.getUnitCostAmount().doubleValue();
			amt=this.tempDailyCost.getUnits().multiply(this.tempDailyCost.getUnitCostAmount());
		}else{
			// ?
		}
		
		// sum up all amt's
		timeTotal+=amt.doubleValue();
		
		
		// apply adustments
		double adjTotal = 0.0;
		for(TimeAssignAdjust taa : adjustments ){
			if(DateUtil.isSameDate(taa.getActivityDate(), date)
					&& taa.getIncidentAccountCode().getId().compareTo(iacId)==0){

				BigDecimal amt2=taa.getAdjustmentAmount();
				
				// sum up all adjustments
				if(taa.getAdjustmentType()==AdjustmentTypeEnum.ADDITION)
					adjTotal+=amt2.doubleValue();
				else
					adjTotal+= (-amt2.doubleValue());
			}
			
		}
		actualCostData.adjustmentAmount=adjTotal;
		
		actualCostData.totalAmount=timeTotal;
		
		return actualCostData;
	}
	
	/**
	 * Return the total amount of all time posts for CONTRACTOR.
	 * 
	 * @param actualCostData
	 * @param date
	 * @param timePosts
	 * @return
	 */
	private ActualCostData getTotalTimeAmountContractor(ActualCostData actualCostData,Date date
						, Collection<AssignmentTimePost> timePosts, Collection<TimeAssignAdjust> adjustments, IncidentResource irEntity) throws Exception{
		double timeTotal = 0.0;

		HashMap<Long,BigDecimal> hashMap = new HashMap<Long,BigDecimal>();
		Collection<ContractorRate> contractorRates = new ArrayList<ContractorRate>();
		
		for(Assignment a : irEntity.getWorkPeriod().getAssignments()){
			if(!DateUtil.hasValue(a.getEndDate())){
				AssignmentTime at = a.getAssignmentTime();
				if(null != at && null!= at.getContractorPaymentInfo()){
					contractorRates=at.getContractorPaymentInfo().getContractorRates();
				}
				break;
			}
		}
		
		Collection<ContractorTimeDetail> ctDetails = new ArrayList<ContractorTimeDetail>();
		
		for(AssignmentTimePost atp : timePosts){
			if(DateUtil.isSameDate(atp.getPostStartDate(), date)
					&& atp.getIncidentAccountCode().getId().compareTo(iacId)==0){

				actualCostData.iac=atp.getIncidentAccountCode();
				
				Collection<ContractorTimeDetail> tdList = this.buildTimeDetails(atp);
				for(ContractorTimeDetail td : tdList){
					//System.out.println(td.getUom());
					ctDetails.add(td);
				}
			}
		}			
		
		ctDetails = this.reSortThisDayData(ctDetails);
		
		BigDecimal amt=BigDecimal.valueOf(0.0);

		if(CollectionUtility.hasValue(ctDetails)){
			Collection<ContractorTimeDetail> ctTmpDetails = new ArrayList<ContractorTimeDetail>();
			int i=0;
			for(ContractorTimeDetail td : ctDetails){
				
				ContractorTimeDetail tmpdata = (ContractorTimeDetail)td.clone();

				if(i>0){
					if(previousLineItemSame(i,(i-1),ctDetails)==true){
						// determine the subtotal amount
						Double subTotal = td.getTotalAmount();
						subTotal=determineSubTotalAmount(i,(i-1),ctDetails,subTotal);
						tmpdata.setTotalAmount(subTotal);
						tmpdata.setFinalAmount(subTotal);

						if(null != tmpdata.getGuaranteeAmount()){
							if(tmpdata.getGuaranteeAmount() > subTotal)
								tmpdata.setFinalAmount(tmpdata.getGuaranteeAmount());
						}
					}
				}

				// check for setting next line and totals
				if(nextLineItemSame(i,(i+1),ctDetails)==true){
					tmpdata.setFinalAmount(0.0);
					tmpdata.setGuaranteeAmount(0.0);
					//tmpdata.setTotalAmount(0.0);
					//tmpdata.setNoLineTotal("See Next Line");
				}else{
					if(null != td.getGuaranteeAmount()){
						if(td.getGuaranteeAmount() > tmpdata.getTotalAmount()){
							tmpdata.setGuaranteeAmount(td.getGuaranteeAmount());
							tmpdata.setFinalAmount(td.getGuaranteeAmount());
						}
					}
				}
				
				ctTmpDetails.add(tmpdata);
				i++;
			}

			for(ContractorTimeDetail td : ctTmpDetails){
				amt=amt.add(new BigDecimal(td.getFinalAmount()));
			}
			
		}
		
		timeTotal+=amt.doubleValue();
		
		// apply adustments
		double adjTotal = 0.0;
		for(TimeAssignAdjust taa : adjustments ){
			if(DateUtil.isSameDate(taa.getActivityDate(), date)
					&& taa.getIncidentAccountCode().getId().compareTo(iacId)==0){

				BigDecimal amt2=taa.getAdjustmentAmount();
				
				// sum up all adjustments
				if(taa.getAdjustmentType()==AdjustmentTypeEnum.ADDITION)
					adjTotal+=amt2.doubleValue();
				else
					adjTotal+= (-amt2.doubleValue());
			}
			
		}
		actualCostData.adjustmentAmount=adjTotal;
		actualCostData.rateType="DAILY";
		actualCostData.units=1;
		actualCostData.rateAmount=timeTotal;
		actualCostData.totalAmount=timeTotal;
		
		return actualCostData;
	}

	private Boolean nextLineItemSame(int sourceIdx, int targetIdx,Collection<ContractorTimeDetail> tdDetails) {
		Boolean rtn=false;

		int x=0;
		ContractorTimeDetail sourceData=null;

		for(ContractorTimeDetail d : tdDetails){
			if(x==sourceIdx){
				sourceData=d;
			}
			if(x==targetIdx){
				if(sourceData.getContractorRateId().equals(d.getContractorRateId())
						&& sourceData.getUom().equals(d.getUom())){
					return true;
				}else{
					// is same posting uom same?
					if(sourceData.getUom().equals(d.getUom()))
						return true;
				}
			}

			x++;
		}

		return false;
	}

	private Boolean previousLineItemSame(int sourceIdx, int targetIdx,Collection<ContractorTimeDetail> tdDetails) {
		Boolean rtn=false;

		int x=0;
		ContractorTimeDetail targetData=null;

		for(ContractorTimeDetail d : tdDetails){
			if(x==targetIdx){
				targetData=d;
			}
			if(x==sourceIdx){
				if(targetData.getContractorRateId().equals(d.getContractorRateId())
						&& targetData.getUom().equals(d.getUom())){
					return true;
				}else{
					// is same posting uom same?
					if(targetData.getUom().equals(d.getUom()))
						return true;
				}
			}

			x++;
		}

		return false;
	}
	
	private Double determineSubTotalAmount(int sourceIdx, int targetIdx,Collection<ContractorTimeDetail> tdDetails, Double subTotal) {

		int x=0;
		ContractorTimeDetail targetData=null;

		for(ContractorTimeDetail d : tdDetails){
			if(x==targetIdx){
				targetData=d;
			}
			if(x==sourceIdx){
				if(targetData.getContractorRateId().equals(d.getContractorRateId())){
					subTotal=subTotal + targetData.getTotalAmount();

					if( (targetIdx-1) >= 0){
						if(previousLineItemSame(sourceIdx,(targetIdx-1),tdDetails)==true){
							// determine the subtotal amount
							subTotal=determineSubTotalAmount(sourceIdx,(targetIdx-1),tdDetails,subTotal);
						}
					}
				}
			}

			x++;
		}

		return subTotal;

	}
	
	private Collection<ContractorTimeDetail> reSortThisDayData(Collection<ContractorTimeDetail> data) {
		Collection<ContractorTimeDetail> rtnData= new ArrayList<ContractorTimeDetail>();
		
		// sort through list and put primary's first, then the rest
		for(ContractorTimeDetail d : data){
			if(d.getPostType().equals("PRIMARY"))
				rtnData.add(d);
		}
		for(ContractorTimeDetail d : data){
			if(d.getPostType().equals("GUARANTEE"))
				rtnData.add(d);
		}
		for(ContractorTimeDetail d : data){
			if(!d.getPostType().equals("PRIMARY") && !d.getPostType().equals("GUARANTEE"))
				rtnData.add(d);
		}
		
		return rtnData;
	}

	private Collection<ContractorTimeDetail> buildTimeDetails(AssignmentTimePost atp) {
		Collection<ContractorTimeDetail> list = new ArrayList<ContractorTimeDetail>();
		ContractorTimeDetail td = new ContractorTimeDetail();
		ContractorTimeDetail td2 = new ContractorTimeDetail();

		td.setPostType(atp.getContractorPostType());
		td.setContractorRateId(String.valueOf(atp.getRefContractorRateId()));
		td2.setPostType(atp.getContractorPostType());
		td2.setContractorRateId(String.valueOf(atp.getRefContractorRateId()));
		
		if (atp.getContractorPostType().equals("GUARANTEE")){
			// guarantee posting
			if(BooleanUtility.isTrue(atp.getIsHalfRate())){
				td.setGuaranteeAmount(atp.getGuaranteeAmount() != null ? 
						(atp.getGuaranteeAmount().doubleValue() / 2) : null);
				td.setTotalAmount(td.getGuaranteeAmount());
				td.setWorkedTotalAmount(td.getGuaranteeAmount());
				td.setFinalAmount(td.getGuaranteeAmount());
			}else{
				td.setGuaranteeAmount(atp.getGuaranteeAmount() != null ? 
						atp.getGuaranteeAmount().doubleValue() : null);
				td.setTotalAmount(atp.getGuaranteeAmount().doubleValue());
				td.setWorkedTotalAmount(td.getTotalAmount());
				td.setFinalAmount(td.getTotalAmount());
			}
			td.setWorkedUnitType(atp.getUnitOfMeasure());
			td.setUom(atp.getUnitOfMeasure());
		}else if (atp.getContractorPostType().equals("SPECIAL")){
			AssignmentTimePost satp=atp;
			// special posting
			td.setSpecialUnits(satp.getQuantity() != null ? 
					satp.getQuantity().doubleValue() : 0.0);
			
			td.setSpecialUnitType(satp.getUnitOfMeasure());
			td.setUom("SPECIAL"+satp.getUnitOfMeasure());
			
			if(BooleanUtility.isTrue(satp.getIsHalfRate())){
				td.setSpecialRate(satp.getRateAmount() != null ? 
						(satp.getRateAmount().doubleValue() / 2) : 0.0);
				td.setSpecialTotalAmount(td.getSpecialUnits() * (td.getSpecialRate().doubleValue() / 2));
			}else{
				td.setSpecialRate(satp.getRateAmount() != null ? 
						satp.getRateAmount().doubleValue() : 0.0);
				td.setSpecialTotalAmount(td.getSpecialUnits() * td.getSpecialRate());
			}
			if(null != satp.getRefContractorRate() && DecimalUtil.hasValue(satp.getRefContractorRate().getGuaranteeAmount())){
				td.setGuaranteeAmount(satp.getRefContractorRate().getGuaranteeAmount().doubleValue());
			}else{
				td.setGuaranteeAmount(satp.getGuaranteeAmount() != null ? 
						satp.getGuaranteeAmount().doubleValue() : null);
			}
			
			td.setTotalAmount(td.getSpecialTotalAmount());
			td.setFinalAmount(td.getSpecialTotalAmount());
			
			if(td.getGuaranteeAmount()!=null){
				if(td.getGuaranteeAmount().doubleValue() > td.getFinalAmount()){
					td.setFinalAmount(td.getGuaranteeAmount().doubleValue());
				}
			}
			
		}else if (atp.getContractorPostType().equals("PRIMARY")){
			// primary posting
			td.setWorkedUnits(atp.getQuantity() != null ? atp.getQuantity().doubleValue() : 0.0);
			
			td.setWorkedUnitType(atp.getUnitOfMeasure());
			td.setUom(atp.getUnitOfMeasure());

			if(BooleanUtility.isTrue(atp.getIsHalfRate()))
				td.setWorkedRate(atp.getRateAmount() != null ? (atp.getRateAmount().doubleValue()/2) : 0.0);
			else
				td.setWorkedRate(atp.getRateAmount() != null ? atp.getRateAmount().doubleValue() : 0.0);
			td.setWorkedTotalAmount(td.getWorkedUnits() * td.getWorkedRate());
			
			if(null != atp.getRefContractorRate() && DecimalUtil.hasValue(atp.getRefContractorRate().getGuaranteeAmount())){
				td.setGuaranteeAmount(atp.getRefContractorRate().getGuaranteeAmount().doubleValue());
			}else{
				td.setGuaranteeAmount(atp.getGuaranteeAmount() != null ? 
						atp.getGuaranteeAmount().doubleValue() : null);
			}
			
			td.setTotalAmount(td.getWorkedTotalAmount());
			td.setFinalAmount(td.getTotalAmount());
			
			if(td.getGuaranteeAmount()!=null){
				if(td.getGuaranteeAmount().doubleValue() > td.getFinalAmount()){
					td.setFinalAmount(td.getGuaranteeAmount().doubleValue());
				}
			}
		}

		list.add(td);
		if(atp.getContractorPostType().equals("BOTH"))
			list.add(td2);
		
		return list;
	}
	
	/**
	 * @param date
	 * @return
	 */
	private double getTotalAdjustmentAmount(Date date,int totalAdjUnits,Collection<TimeAssignAdjust> adjustments){
		BigDecimal adjTotal = BigDecimal.valueOf(0.0);
		
		for(TimeAssignAdjust taa : adjustments){
			if(DateUtil.isSameDate(taa.getActivityDate(), date)){
				
				if(taa.getAdjustmentType()==AdjustmentTypeEnum.ADDITION){
					adjTotal.add(taa.getAdjustmentAmount());
				}else{
					adjTotal.subtract(taa.getAdjustmentAmount());
				}
				totalAdjUnits+=1;
			}
		}
		
		return adjTotal.doubleValue();
	}
	
	
}
