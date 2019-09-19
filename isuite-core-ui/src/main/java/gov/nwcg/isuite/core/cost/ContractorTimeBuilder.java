package gov.nwcg.isuite.core.cost;

import gov.nwcg.isuite.core.cost.operations.calculator.ContractorTimeDetail;
import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.core.vo.TimePostVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class ContractorTimeBuilder {

	public static Collection<ContractorTimeDetail> buildTimeDetails(TimePostVo tpvo,Collection<ContractorRateVo> contractorRateVos) {
		Collection<ContractorTimeDetail> list = new ArrayList<ContractorTimeDetail>();
		ContractorTimeDetail td = new ContractorTimeDetail();
		ContractorTimeDetail td2 = new ContractorTimeDetail();

		td.setPostType(StringUtility.hasValue(tpvo.getContractorPostType()) ? tpvo.getContractorPostType() :"");
		td.setContractorRateId(String.valueOf(tpvo.getContractorRateId()));
		td2.setPostType(StringUtility.hasValue(tpvo.getContractorPostType())? tpvo.getContractorPostType() :"");
		td2.setContractorRateId(String.valueOf(tpvo.getContractorRateId()));
		
		if (tpvo.getContractorPostType().equals("GUARANTEE")){
			// guarantee posting
			if(BooleanUtility.isTrue(tpvo.getHalfRate())){
				td.setGuaranteeAmount(tpvo.getGuaranteeAmount() != null ? 
						(tpvo.getGuaranteeAmount().doubleValue() / 2) : null);
				td.setTotalAmount(td.getGuaranteeAmount());
				td.setWorkedTotalAmount(td.getGuaranteeAmount());
				td.setFinalAmount(td.getGuaranteeAmount());
			}else{
				td.setGuaranteeAmount(tpvo.getGuaranteeAmount() != null ? 
						tpvo.getGuaranteeAmount().doubleValue() : null);
				td.setTotalAmount(tpvo.getGuaranteeAmount().doubleValue());
				td.setWorkedTotalAmount(td.getTotalAmount());
				td.setFinalAmount(td.getTotalAmount());
			}
			td.setWorkedUnitType(tpvo.getUnitOfMeasure());
			td.setUom(tpvo.getUnitOfMeasure());
		}else if (tpvo.getContractorPostType().equals("SPECIAL")){
			TimePostVo specialTpvo=tpvo;
			// special posting
			td.setSpecialUnits(specialTpvo.getQuantity() != null ? 
					specialTpvo.getQuantity().doubleValue() : 0.0);
			
			td.setSpecialUnitType(specialTpvo.getUnitOfMeasure());
			td.setUom("SPECIAL"+specialTpvo.getUnitOfMeasure());
			
			if(BooleanUtility.isTrue(specialTpvo.getHalfRate())){
				td.setSpecialRate(specialTpvo.getRateAmount() != null ? 
						(specialTpvo.getRateAmount().doubleValue() / 2) : 0.0);
				td.setSpecialTotalAmount(td.getSpecialUnits() * (td.getSpecialRate().doubleValue() ));
			}else{
				td.setSpecialRate(specialTpvo.getRateAmount() != null ? 
						specialTpvo.getRateAmount().doubleValue() : 0.0);
				td.setSpecialTotalAmount(td.getSpecialUnits() * td.getSpecialRate());
			}

			// try and get the rate info
			ContractorRateVo specialRateVo = null;
			if(LongUtility.hasValue(specialTpvo.getContractorRateId())){
				for(ContractorRateVo crv : contractorRateVos){
					if(crv.getId().compareTo(specialTpvo.getContractorRateId())==0){
						specialRateVo=crv;
						break;
					}
				}
			}
			if(null != specialRateVo && DecimalUtil.hasValue(specialRateVo.getGuaranteeAmount())){
				td.setGuaranteeAmount(specialRateVo.getGuaranteeAmount().doubleValue());
			}else{
				td.setGuaranteeAmount(DecimalUtil.hasValue(specialRateVo.getGuaranteeAmount()) ? 
															specialRateVo.getGuaranteeAmount().doubleValue() : null);
			}
			
			td.setTotalAmount(td.getSpecialTotalAmount());
			td.setFinalAmount(td.getSpecialTotalAmount());
			
			if(td.getGuaranteeAmount()!=null){
				if(td.getGuaranteeAmount().doubleValue() > td.getFinalAmount()){
					td.setFinalAmount(td.getGuaranteeAmount().doubleValue());
				}
			}
			
		}else if (tpvo.getContractorPostType().equals("PRIMARY")){
			// primary posting
			td.setWorkedUnits(tpvo.getQuantity() != null ? tpvo.getQuantity().doubleValue() : 0.0);
			
			td.setWorkedUnitType(tpvo.getUnitOfMeasure());
			td.setUom(tpvo.getUnitOfMeasure());

			if(BooleanUtility.isTrue(tpvo.getHalfRate())){
				if(BooleanUtility.isTrue(tpvo.getGuaranteePosting())){
					td.setWorkedRate(tpvo.getGuaranteeAmount() != null ? (tpvo.getGuaranteeAmount().doubleValue()/2) : 0.0);
				}else{
					td.setWorkedRate(tpvo.getRateAmount() != null ? (tpvo.getRateAmount().doubleValue()/2) : 0.0);
				}
			}else{
				if(BooleanUtility.isTrue(tpvo.getGuaranteePosting())){
					td.setWorkedRate(tpvo.getGuaranteeAmount() != null ? (tpvo.getGuaranteeAmount().doubleValue()) : 0.0);
				}else{
					td.setWorkedRate(tpvo.getRateAmount() != null ? tpvo.getRateAmount().doubleValue() : 0.0);
				}
			}
			
			td.setWorkedTotalAmount(td.getWorkedUnits() * td.getWorkedRate());
			
			// try and get the rate info
			ContractorRateVo rateVo = null;
			if(LongUtility.hasValue(tpvo.getContractorRateId())){
				for(ContractorRateVo crv : contractorRateVos){
					if(crv.getId().compareTo(tpvo.getContractorRateId())==0){
						rateVo=crv;
						break;
					}
				}
			}

			if(null != rateVo && DecimalUtil.hasValue(rateVo.getGuaranteeAmount())){
				td.setGuaranteeAmount(rateVo.getGuaranteeAmount().doubleValue());
			}else{
				td.setGuaranteeAmount(DecimalUtil.hasValue(rateVo.getGuaranteeAmount()) ? 
															rateVo.getGuaranteeAmount().doubleValue() : null);
			}
			
			td.setTotalAmount(td.getWorkedTotalAmount());
			td.setFinalAmount(td.getTotalAmount());
			
			if(td.getGuaranteeAmount()!=null){
				if(td.getGuaranteeAmount().doubleValue() > td.getFinalAmount()){
					if(BooleanUtility.isTrue(tpvo.getHalfRate())){
						td.setFinalAmount(td.getGuaranteeAmount().doubleValue()/2);
					}else{
						td.setFinalAmount(td.getGuaranteeAmount().doubleValue());
					}
				}
			}
		}

		list.add(td);
		if(tpvo.getContractorPostType().equals("BOTH"))
			list.add(td2);
		
		return list;
	}

	public static Collection<ContractorTimeDetail> reSortThisDayData(Collection<ContractorTimeDetail> data) {
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
	
	public static BigDecimal getDayAmount(Collection<ContractorTimeDetail> ctDetails) throws Exception {
		
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
					if(null != td.getGuaranteeAmount() && !DecimalUtil.hasDoubleValue(tmpdata.getFinalAmount())){
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
		
		return amt;
	}
	
	private static Boolean nextLineItemSame(int sourceIdx, int targetIdx,Collection<ContractorTimeDetail> tdDetails) {
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
	
	private static Boolean previousLineItemSame(int sourceIdx, int targetIdx,Collection<ContractorTimeDetail> tdDetails) {
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
	
	private static Double determineSubTotalAmount(int sourceIdx, int targetIdx,Collection<ContractorTimeDetail> tdDetails, Double subTotal) {

		int x=0;
		ContractorTimeDetail targetData=null;

		for(ContractorTimeDetail d : tdDetails){
			if(x==targetIdx){
				targetData=d;
			}
			if(x==sourceIdx){
				/*
				 9/7/2014  Dan - commenting out primaryPosting=true, if 2 contractor special postings
				 				 on same day, total for day is incorrect, defect 4367
				*/
				//if(targetData.getContractorRateId().equals(d.getContractorRateId())){
					subTotal=subTotal + targetData.getTotalAmount();

					if( (targetIdx-1) >= 0){
						if(previousLineItemSame(sourceIdx,(targetIdx-1),tdDetails)==true){
							// determine the subtotal amount
							subTotal=determineSubTotalAmount(sourceIdx,(targetIdx-1),tdDetails,subTotal);
						}
					}
				//}
			}

			x++;
		}

		return subTotal;

	}
	
}
