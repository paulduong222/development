package gov.nwcg.isuite.core.cost.utilities;

import gov.nwcg.isuite.core.vo.AccrualCodeVo;
import gov.nwcg.isuite.core.vo.CostDataVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.ResourceOtherVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class DefaultAccrualCodeHandler {
	private ApplicationContext context;
	public String resourceEmployment="";
	public String resourceAgency="";
	public Boolean resourceAgencyIsState=false;
	public String resourcePaymentAgency="";
	public Boolean resourcePaymentAgencyIsState=false;
	public String resourceItemCode="";
	public String resourceItemCodeCategory="";
	public String resourceUnitState="";
	public String incidentState="";
	public String incidentAgency="";
	
	public DefaultAccrualCodeHandler(ApplicationContext ctx){
		this.context=ctx;
	}

	private AccrualCodeVo getAccrualCodeVo(String code){
		GlobalCacheVo cacheVo = (GlobalCacheVo)context.getBean("globalCacheVo");
		
		// set default to excl
		for(AccrualCodeVo acvo : cacheVo.getAccrualCodeVos()){
			if(acvo.getCode().equals(code)){
				return acvo;
			}
		}
		
		return null;
	}
	
	private void populateResourceValues(IncidentResourceVo irvo){
		if(null != irvo.getResourceVo() && null != irvo.getResourceVo().getAgencyVo()){
			ResourceVo resourceVo = irvo.getResourceVo();
			this.resourceAgency=resourceVo.getAgencyVo().getAgencyCd();
			resourceAgencyIsState=resourceVo.getAgencyVo().getState();
			
			if(null != resourceVo.getOrganizationVo()){
				String unitCode=resourceVo.getOrganizationVo().getUnitCode();
				if(StringUtility.hasValue(unitCode))
					this.resourceUnitState=unitCode.substring(0,2);
			}
		}

		if(null != irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()
				&& null != irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType()){
			this.resourceEmployment=irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType().name();
		}
		
		if(null != irvo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo()){
			this.resourceItemCode=irvo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo().getCode();
			if(null != irvo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo().getRequestCategoryVo()){
				this.resourceItemCodeCategory=irvo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo().getRequestCategoryVo().getCode();
			}
		}

		if(null != irvo.getCostDataVo() && null != irvo.getCostDataVo().getPaymentAgencyVo()){
			this.resourcePaymentAgency=irvo.getCostDataVo().getPaymentAgencyVo().getAgencyCd();
			if(irvo.getCostDataVo().getPaymentAgencyVo().getAgencyCd().length()==2)
				this.resourcePaymentAgencyIsState=true;
		}

		if(null != irvo.getIncidentVo() && null != irvo.getIncidentVo().getCountryCodeSubdivisionVo()){
			this.incidentState=irvo.getIncidentVo().getCountryCodeSubdivisionVo().getCountrySubAbbreviation();
		}
		
		if(null != irvo.getIncidentVo() && null != irvo.getIncidentVo().getAgencyVo()){
			this.incidentAgency=irvo.getIncidentVo().getAgencyVo().getAgencyCd();
		}
	}

	private void populateResourceValues(IncidentResourceOtherVo irovo){
		if(null != irovo.getResourceOtherVo() && null != irovo.getResourceOtherVo().getAgencyVo()){
			ResourceOtherVo resourceOtherVo = irovo.getResourceOtherVo();
			this.resourceAgency=resourceOtherVo.getAgencyVo().getAgencyCd();
			resourceAgencyIsState=resourceOtherVo.getAgencyVo().getState();

			if(null != resourceOtherVo.getKindVo()){
				this.resourceItemCode=resourceOtherVo.getKindVo().getCode();
				if(null != resourceOtherVo.getKindVo().getRequestCategoryVo()){
					this.resourceItemCodeCategory=resourceOtherVo.getKindVo().getRequestCategoryVo().getCode();
				}
			}
			
			this.resourceUnitState="";
			
			
		}
		
		if(null != irovo.getCostDataVo() && null != irovo.getCostDataVo().getPaymentAgencyVo()){
			this.resourcePaymentAgency=irovo.getCostDataVo().getPaymentAgencyVo().getAgencyCd();
		}
		
		if(null != irovo.getIncidentVo() && null != irovo.getIncidentVo().getCountryCodeSubdivisionVo()){
			this.incidentState=irovo.getIncidentVo().getCountryCodeSubdivisionVo().getCountrySubAbbreviation();
		}
		
		if(null != irovo.getIncidentVo() && null != irovo.getIncidentVo().getAgencyVo()){
			this.incidentAgency=irovo.getIncidentVo().getAgencyVo().getAgencyCd();
		}
		
	}

	public void setDefaultAccrualCodeVo(IncidentResourceVo irvo){
		AccrualCodeVo accrualCodeVo = this.getAccrualCodeVo("EXCL");
		
		this.populateResourceValues(irvo);

		if(null==irvo.getCostDataVo())
			irvo.setCostDataVo(new CostDataVo());

		irvo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);

		if(!StringUtility.hasValue(this.resourceAgency)){
			this.resourceAgency="";
			this.resourceAgencyIsState=false;
		}
		
		if(BooleanUtility.isTrue(isCONT())){
			accrualCodeVo=this.getAccrualCodeVo("CONT");
			irvo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);
			return;
		}
		
		if(BooleanUtility.isTrue(isAD())){
			accrualCodeVo=this.getAccrualCodeVo("AD");
			irvo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);
			return;
		}
		
		if(BooleanUtility.isTrue(isResourceAgency())){
			accrualCodeVo=this.getAccrualCodeVo(this.resourceAgency);
			irvo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);
			return;
		}

		if(BooleanUtility.isTrue(isResourceUnitState())){
			accrualCodeVo=this.getAccrualCodeVo(this.resourceUnitState);
			irvo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);
			return;
		}
		
		if(BooleanUtility.isTrue(isAMD())){
			accrualCodeVo=this.getAccrualCodeVo("AMD");
			irvo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);
			return;
		}
		
		if(BooleanUtility.isTrue(isNOAA())){
			accrualCodeVo=this.getAccrualCodeVo("NOAA");
			irvo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);
			return;
		}
		
	}
	
	public void setDefaultAccrualCodeVo(IncidentResourceOtherVo irovo){
		AccrualCodeVo accrualCodeVo = this.getAccrualCodeVo("EXCL");
		
		this.populateResourceValues(irovo);

		if(null==irovo.getCostDataVo())
			irovo.setCostDataVo(new CostDataVo());

		irovo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);
		
		if(!StringUtility.hasValue(this.resourceAgency)){
			this.resourceAgency="";
		}
		
		if(BooleanUtility.isTrue(isCONT())){
			accrualCodeVo=this.getAccrualCodeVo("CONT");
			irovo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);
			return;
		}
		
		if(BooleanUtility.isTrue(isAD())){
			accrualCodeVo=this.getAccrualCodeVo("AD");
			irovo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);
			return;
		}
		
		if(BooleanUtility.isTrue(isResourceAgency())){
			accrualCodeVo=this.getAccrualCodeVo(this.resourceAgency);
			irovo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);
			return;
		}

		if(BooleanUtility.isTrue(isResourceUnitState())){
			accrualCodeVo=this.getAccrualCodeVo(this.resourceUnitState);
			irovo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);
			return;
		}
		
		if(BooleanUtility.isTrue(isAMD())){
			accrualCodeVo=this.getAccrualCodeVo("AMD");
			irovo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);
			return;
		}
		
		if(BooleanUtility.isTrue(isNOAA())){
			accrualCodeVo=this.getAccrualCodeVo("NOAA");
			irovo.getCostDataVo().setAccrualCodeVo(accrualCodeVo);
			return;
		}
		
	}

	private Boolean isCONT(){
		if(StringUtility.hasValue(resourceAgency)){
			if(resourceAgency.equals("PVT") 
					&& (resourceItemCode.equals("CTR") || resourceItemCode.equals("SHW") ))
				return true;
			
			if(StringUtility.hasValue(this.resourceItemCodeCategory) && !this.resourceItemCodeCategory.equals("A")){
				if(resourceAgency.equals("PVT")) {
					if(this.resourcePaymentAgency.equals("USFS"))
						return true;
					else if(this.incidentAgency.equals("USFS") && !StringUtility.hasValue(resourcePaymentAgency))
						return true;
				}
			}
		}
		
		
		/*
		 * Incident Jurisdiction or Resource Payment Agency is USFS
		 * and Resource Agency is PVT 
		 * and ( 
		 * 	Resource Item Code is CTR or SHW
		 * 	or
		 *  Resource is contractor and Resource Item Code Category not aircraft 
		 * )
		 */
		/* dan 8/2014
		if(this.incidentAgency.equals("USFS") || this.resourcePaymentAgency.equals("USFS")){
			if(StringUtility.hasValue(resourceAgency)){
				if(resourceAgency.equals("PVT") 
						&& (resourceItemCode.equals("CTR") || resourceItemCode.equals("SHW") ))
					return true;
				else if(resourceAgency.equals("PVT") && this.resourceEmployment.equals("CONTRACTOR")
							&& !this.resourceItemCodeCategory.equals("A"))
					return true;
			}
		}
		
		String codes="BIA,BLM,BOEM,BOR,BSEE,NPS,OSM,FWS,USGS";
		if(codes.indexOf(this.incidentAgency)>-1 || codes.indexOf(this.resourcePaymentAgency)>-1){
			if(StringUtility.hasValue(resourceAgency)){
				if(resourceAgency.equals("PVT") 
						&& (resourceItemCode.equals("CTR") || resourceItemCode.equals("SHW") ))
					return true;
			}
		}
		*/
		
		return false;
	}
	
	private Boolean isAD(){
		/*
		 * Resource Employment is AD and Resource Agency is USFS
		 */
		if(this.resourceEmployment.equals("AD") && this.resourceAgency.equals("USFS"))
			return true;

		return false;
	}
	
	private Boolean isAMD(){
		if(StringUtility.hasValue(resourceAgency)){
			if(this.resourceAgency.equals("AMD")){
				if(this.resourcePaymentAgency.equals("USFS"))
					return true;
				else if(this.incidentAgency.equals("USFS") && !StringUtility.hasValue(resourcePaymentAgency))
					return true;
			}
		}

		return false;
	}
	
	private Boolean isNOAA(){
		if(StringUtility.hasValue(resourceAgency)){
			if(this.resourceAgency.equals("NWS")){
				if(this.resourcePaymentAgency.equals("USFS"))
					return true;
				else if(this.incidentAgency.equals("USFS") && !StringUtility.hasValue(resourcePaymentAgency))
					return true;
			}
		}

		return false;
	}

	private Boolean isResourceAgency(){

		if(this.resourceAgencyIsState==true){

			// evaluate resource payment agency first if avail
			if(StringUtility.hasValue(this.resourcePaymentAgency) 
					&& this.resourcePaymentAgencyIsState==false){
				
				// 5/6 - E
				if("BIA, BLM, BOEM, BOR, BSEE, NPS, OSM, FWS, USGS".contains(this.resourcePaymentAgency))
					return false;

				if(this.resourcePaymentAgency.equals("USFS"))
					return true;
			}
				
			if(StringUtility.hasValue(this.resourcePaymentAgency) 
					&& this.resourcePaymentAgencyIsState==true){
				
				// 6-F
				if(!this.resourceAgency.equals(this.incidentState))
					return true;
				else 
					return false;
			}
			
			// evaluate incident jurisdiction if avail
			if(StringUtility.hasValue(this.incidentAgency)){
				
				// 5/6 - D
				if(this.incidentAgency.equals("USFS"))
					return true;

				if("BIA, BLM, BOEM, BOR, BSEE, NPS, OSM, FWS, USGS".contains(this.incidentAgency)){
					return false;
				}else{
					if(this.incidentAgency.length()==2){
						if(!this.resourceAgency.equals(this.incidentAgency))
							return true;
						else
							return false;
					}
				}

			}
			
		}
		
		return false;
	}
	
	private Boolean isResourceUnitState(){
		
		if(StringUtility.hasValue(this.resourceUnitState)){

			if(StringUtility.hasValue(this.resourceAgency)){
				if("RUR,CNTY,CTY,CITY,CCC".contains(this.resourceAgency)){

					// evaulate resource payment agency if avail
					if(StringUtility.hasValue(this.resourcePaymentAgency) 
							&& this.resourcePaymentAgencyIsState==false){
						
						// 5/6 - E
						if("BIA, BLM, BOEM, BOR, BSEE, NPS, OSM, FWS, USGS".contains(this.resourcePaymentAgency))
							return false;

						if(this.resourcePaymentAgency.equals("USFS")){
							return true;
						}
					}
						
					if(StringUtility.hasValue(this.resourcePaymentAgency) 
							&& this.resourcePaymentAgencyIsState==true){
						
						// 6-F
						if(!this.resourceUnitState.equals(this.incidentState))
							return true;
						else
							return false;
					}

					// evaluate incident jurisdiction if avail
					if(StringUtility.hasValue(this.incidentAgency)){
						
						// 5/6 - D
						if(this.incidentAgency.equals("USFS"))
							return true;

						if("BIA, BLM, BOEM, BOR, BSEE, NPS, OSM, FWS, USGS".contains(this.incidentAgency)){
							return false;
						}else{
							if(this.incidentAgency.length()==2){
								// 6-F
								if(!this.resourceUnitState.equals(this.incidentState))
									return true;
							}
						}

					}
					
				}
				
			}
			
		}
		
		return false;
	}

	public Long getDefaultAccrualCodeIdVoForResource(){
		AccrualCodeVo accrualCodeVo = this.getAccrualCodeVo("EXCL");
		
		if(!StringUtility.hasValue(this.resourceAgency)){
			this.resourceAgency="";
		}
		
		if(BooleanUtility.isTrue(isCONT())){
			accrualCodeVo=this.getAccrualCodeVo("CONT");
			return accrualCodeVo.getId();
		}
		
		if(BooleanUtility.isTrue(isAD())){
			accrualCodeVo=this.getAccrualCodeVo("AD");
			return accrualCodeVo.getId();
		}
		
		if(BooleanUtility.isTrue(isResourceAgency())){
			accrualCodeVo=this.getAccrualCodeVo(this.resourceAgency);
			return accrualCodeVo.getId();
		}

		if(BooleanUtility.isTrue(isResourceUnitState())){
			accrualCodeVo=this.getAccrualCodeVo(this.resourceUnitState);
			return accrualCodeVo.getId();
		}
		
		if(BooleanUtility.isTrue(isAMD())){
			accrualCodeVo=this.getAccrualCodeVo("AMD");
			return accrualCodeVo.getId();
		}
		
		if(BooleanUtility.isTrue(isNOAA())){
			accrualCodeVo=this.getAccrualCodeVo("NOAA");
			return accrualCodeVo.getId();
		}
		
		return accrualCodeVo.getId();
	}
	
}
