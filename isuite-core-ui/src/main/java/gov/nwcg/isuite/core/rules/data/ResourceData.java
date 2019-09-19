package gov.nwcg.isuite.core.rules.data;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Date;

public class ResourceData {
	public Long unitId=null;
	public Long pdcId=null;
	public Long agencyId=null;
	public Boolean leader=false;
	public Integer leaderType=99;
	public TravelMethodTypeEnum ciTravelMethod=null;
	public Date ciCheckInDate=null;
	public Long ciArrivalJetPortId=null;
	
	public Date dmTentativeReleaseDate=null;
	public Date dmReleaseDate=null;
    public String dmTentativeDemobCity=null;
    public Long dmTentativeDemobStateId=null;
    public TravelMethodTypeEnum dmTravelMethod=null;
    public Long dmJetportId=null;
    public Date dmTentativeArrivalDate=null;
    public Integer travelHours=null;
    public Integer travelMinutes=null;
    public AssignmentStatusTypeEnum status=null;
    
    public Long iacId=null;

	public Long incidentId=null;
	public String unitCode="";
	public String agencyCode="";
	public Long lengthOfAssignment;
	public Date firstWorkDate=null;
	public Date mobilizationDate=null;
	public Long paymentAgencyId=null;
	public Date assignDate=null;
	public String ciCheckInTime;
	public String dmReleaseTime;
	public String dmTentativeReleaseTime;
    public String dmTentativeArrivalTime;;
	
    public EmploymentTypeEnum employmentType;
    public Long assignmentTimeId;
    
    public ResourceData(Resource resource){
    	IncidentResource ir = null;
    	if(null != resource){
    		if(resource.getIncidentResources().size() > 0) {
    			ir = resource.getIncidentResources().iterator().next();

    			incidentId=ir.getIncident().getId();
    			
    			unitId=resource.getOrganizationId();
    			pdcId=resource.getPrimaryDispatchCenterId();
    			
    			if(null != resource.getAgency())
    				agencyId=resource.getAgency().getId();
    			else
    				agencyId=resource.getAgencyId();
    			
    			if(null != resource.getAgency())
    				agencyCode=resource.getAgency().getAgencyCode();
    			leader=resource.isLeader();
    			leaderType=resource.getLeaderType();
    			ciTravelMethod=ir.getWorkPeriod().getCiTravelMethod();

    			if(null != ir.getWorkPeriod().getCIResourceMobilization()){
        			mobilizationDate=ir.getWorkPeriod().getCIResourceMobilization().getStartDate();
    			}

    			dmReleaseDate=ir.getWorkPeriod().getDMReleaseDate();
    			if(null != dmReleaseDate)
    				dmReleaseTime=DateUtil.toMilitaryTime(dmReleaseDate);

       			dmTentativeReleaseDate=ir.getWorkPeriod().getDMTentativeReleaseDate();
    			if(null != dmTentativeReleaseDate)
    				dmTentativeReleaseTime=DateUtil.toMilitaryTime(dmTentativeReleaseDate);
    			
    			ciCheckInDate=ir.getWorkPeriod().getCICheckInDate();
    			if(null != ciCheckInDate)
    				ciCheckInTime=DateUtil.toMilitaryTime(ciCheckInDate);
    			
    			dmTentativeDemobCity=ir.getWorkPeriod().getDMTentativeDemobCity();
    			dmTentativeDemobStateId=ir.getWorkPeriod().getDMTentativeDemobStateId();
    			ciArrivalJetPortId=ir.getWorkPeriod().getCIArrivalJetPortId();			
    			dmTravelMethod=ir.getWorkPeriod().getDMTravelMethod();
    			
    			dmTentativeArrivalDate=ir.getWorkPeriod().getDmTentativeArrivalDate();
    			if(null != dmTentativeArrivalDate)
    				dmTentativeArrivalTime=DateUtil.toMilitaryTime(dmTentativeArrivalDate);

    			if(null != ir.getWorkPeriod().getDMAirTravel()){
    				dmJetportId=ir.getWorkPeriod().getDMAirTravel().getJetPortId();
    				travelHours=ir.getWorkPeriod().getDMAirTravel().getHoursToAirport();
    				travelMinutes=ir.getWorkPeriod().getDMAirTravel().getMinutesToAirport();
    			}
    			
    			if(null != ir.getWorkPeriod().getAssignments()){
    				for(Assignment assignment : ir.getWorkPeriod().getAssignments()){
    					if(assignment.getEndDate()==null){
    						status=assignment.getAssignmentStatus();
    						if(assignment.getAssignmentTime() != null){
    							employmentType=assignment.getAssignmentTime().getEmploymentType();
    							assignmentTimeId=assignment.getAssignmentTime().getId();
    						}
    						break;
    					}
    				}
    			}
    			
    			if(null != ir.getIncident())
    				incidentId=ir.getIncident().getId();
    			if(null != resource.getOrganization())
    				unitCode=resource.getOrganization().getUnitCode();
    			lengthOfAssignment=ir.getWorkPeriod().getCILengthAtAssignment();
    			firstWorkDate=ir.getWorkPeriod().getCIFirstWorkDate();
    			if(null != ir.getCostData()){
        			if(null != ir.getCostData().getPaymentAgency())
        				paymentAgencyId=ir.getCostData().getPaymentAgency().getId();
        			assignDate=ir.getCostData().getAssignDate();
    			}
    			if(null != ir.getWorkPeriod().getDefIncidentAccountCode())
    				iacId=ir.getWorkPeriod().getDefIncidentAccountCode().getId();
    		}
    	}
    }

    public ResourceData(IncidentResourceVo vo){
    	ResourceVo resourceVo = vo.getResourceVo();
    	
    	if(null != resourceVo){
   			unitId=resourceVo.getOrganizationVo().getId();

   			incidentId=vo.getIncidentVo().getId();
   			
   			if(null != resourceVo.getAgencyVo())
   				agencyId=resourceVo.getAgencyVo().getId();
    			
   			leader=resourceVo.isLeader();
    		leaderType=resourceVo.getLeaderType();
    		if(null != vo.getWorkPeriodVo().getCiTravelMethodVo()){
        		ciTravelMethod=TravelMethodTypeEnum.valueOf(vo.getWorkPeriodVo().getCiTravelMethodVo().getCode());
    		}

			Date releaseDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getDmReleaseDateVo());
   			dmReleaseDate=releaseDate;
   			if(StringUtility.hasValue(vo.getWorkPeriodVo().getDmReleaseDateVo().getTimeString()))
  				dmReleaseTime=vo.getWorkPeriodVo().getDmReleaseDateVo().getTimeString();
   			
			Date tentReleaseDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getDmTentativeReleaseDateVo());
			dmTentativeReleaseDate=tentReleaseDate;
   			if(StringUtility.hasValue(vo.getWorkPeriodVo().getDmTentativeReleaseDateVo().getTimeString()))
   				dmTentativeReleaseTime=vo.getWorkPeriodVo().getDmTentativeReleaseDateVo().getTimeString();
   			
   			if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiCheckInDateVo())){
   				ciCheckInDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getCiCheckInDateVo());
   	   			if(StringUtility.hasValue(vo.getWorkPeriodVo().getCiCheckInDateVo().getTimeString()))
   	   				ciCheckInTime=vo.getWorkPeriodVo().getCiCheckInDateVo().getTimeString();
   			}
   			
   			/*
   			ciCheckInDate=vo.getWorkPeriodVo().getCiCheckInDate();
   			if(StringUtility.hasValue(vo.getWorkPeriodVo().getCiCheckInTime())){
   				ciCheckInTime=vo.getWorkPeriodVo().getCiCheckInTime();
   			}
    		*/

   			if(null != vo.getWorkPeriodVo().getCiResourceMobilizationVo()
   					&& DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiResourceMobilizationVo().getStartDateVo())){
   				mobilizationDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getCiResourceMobilizationVo().getStartDateVo());
   			}
   			
   			dmTentativeDemobCity=vo.getWorkPeriodVo().getDmTentativeDemobCity();
   			if(null != vo.getWorkPeriodVo().getDmTentativeDemobStateVo())
	   			dmTentativeDemobStateId=vo.getWorkPeriodVo().getDmTentativeDemobStateVo().getId();
   			
   			if(null != vo.getWorkPeriodVo().getCiArrivalJetPortVo())
   				ciArrivalJetPortId=vo.getWorkPeriodVo().getCiArrivalJetPortVo().getId();

   			if(null != vo.getWorkPeriodVo().getDmTravelMethodVo())
   				dmTravelMethod=TravelMethodTypeEnum.valueOf(vo.getWorkPeriodVo().getDmTravelMethodVo().getCode());
    			
			Date tentArrivalDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getDmTentativeArrivalDateVo());
			dmTentativeArrivalDate=tentArrivalDate;
   			if(StringUtility.hasValue(vo.getWorkPeriodVo().getDmTentativeArrivalDateVo().getTimeString()))
   				dmTentativeArrivalTime=vo.getWorkPeriodVo().getDmTentativeArrivalDateVo().getTimeString();
   			
   			if(null != vo.getWorkPeriodVo().getDmAirTravelVo()){
   				if(null != vo.getWorkPeriodVo().getDmAirTravelVo().getJetPortVo())	
   					dmJetportId=vo.getWorkPeriodVo().getDmAirTravelVo().getJetPortVo().getId();
   				travelHours=vo.getWorkPeriodVo().getDmAirTravelVo().getHoursToAirport();
   				travelMinutes=vo.getWorkPeriodVo().getDmAirTravelVo().getMinutesToAirport();
   			}
    			
   			if(null != vo.getWorkPeriodVo().getCurrentAssignmentVo()){
   				status=vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus();
   				if(StringUtility.hasValue(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode()))
   					status=AssignmentStatusTypeEnum.valueOf(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode());
   			}
    			
   			if(null != vo.getIncidentVo())
   				incidentId=vo.getIncidentVo().getId();
   			if(null != resourceVo.getOrganizationVo())
   				unitCode=resourceVo.getOrganizationVo().getUnitCode();
   			lengthOfAssignment=vo.getWorkPeriodVo().getCiLengthAtAssignment();
			if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiFirstWorkDateVo())){
				firstWorkDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getCiFirstWorkDateVo());
			}
   			if(null != vo.getCostDataVo()){
   	   			if(null != vo.getCostDataVo().getPaymentAgencyVo())
   	   				paymentAgencyId=vo.getCostDataVo().getPaymentAgencyVo().getId();
   	   			
				if(DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
					try{
						Date dt=DateTransferVo.getTransferDate(vo.getCostDataVo().getAssignDateVo());
						assignDate=dt;
					}catch(Exception smother){}
				}
   			}

    		if(null != vo.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo())
    			iacId=vo.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo().getId();

    	}
    }
    
    public ResourceData(IncidentResource ir){
    	Resource resource = ir.getResource();
    	
    	if(null != resource){
    		if(resource.getIncidentResources().size() > 0) {
    			ir = resource.getIncidentResources().iterator().next();

    			unitId=resource.getOrganizationId();
    			pdcId=resource.getPrimaryDispatchCenterId();
    			agencyId=resource.getAgencyId();
    			leader=resource.isLeader();
    			leaderType=resource.getLeaderType();
    			ciTravelMethod=ir.getWorkPeriod().getCiTravelMethod();
    			
    			dmReleaseDate=ir.getWorkPeriod().getDMReleaseDate();
    			if(null != dmReleaseDate)
    				dmReleaseTime=DateUtil.toMilitaryTime(dmReleaseDate);
    			
    			ciCheckInDate=ir.getWorkPeriod().getCICheckInDate();
    			if(null != ciCheckInDate)
    				ciCheckInTime=DateUtil.toMilitaryTime(ciCheckInDate);
    			
    			dmTentativeDemobCity=ir.getWorkPeriod().getDMTentativeDemobCity();
    			dmTentativeDemobStateId=ir.getWorkPeriod().getDMTentativeDemobStateId();
    			ciArrivalJetPortId=ir.getWorkPeriod().getCIArrivalJetPortId();			
    			dmTravelMethod=ir.getWorkPeriod().getDMTravelMethod();

    			dmTentativeArrivalDate=ir.getWorkPeriod().getDmTentativeArrivalDate();
    			if(null != dmTentativeArrivalDate)
    				dmTentativeArrivalTime=DateUtil.toMilitaryTime(dmTentativeArrivalDate);

    			if(null != ir.getWorkPeriod().getDMAirTravel()){
    				dmJetportId=ir.getWorkPeriod().getDMAirTravel().getJetPortId();
    				travelHours=ir.getWorkPeriod().getDMAirTravel().getHoursToAirport();
    				travelMinutes=ir.getWorkPeriod().getDMAirTravel().getMinutesToAirport();
    			}
    			
    			if(null != ir.getWorkPeriod().getAssignments()){
    				for(Assignment assignment : ir.getWorkPeriod().getAssignments()){
    					if(assignment.getEndDate()==null){
    						status=assignment.getAssignmentStatus();
    						break;
    					}
    				}
    			}
    			
    			if(null != ir.getIncident())
    				incidentId=ir.getIncident().getId();
    			if(null != resource.getOrganization())
    				unitCode=resource.getOrganization().getUnitCode();
    			lengthOfAssignment=ir.getWorkPeriod().getCILengthAtAssignment();
    			firstWorkDate=ir.getWorkPeriod().getCIFirstWorkDate();
    			if(null != ir.getCostData().getPaymentAgency())
    				paymentAgencyId=ir.getCostData().getPaymentAgency().getId();
    			assignDate=ir.getCostData().getAssignDate();
    			if(null != ir.getWorkPeriod().getDefIncidentAccountCode())
    				iacId=ir.getWorkPeriod().getDefIncidentAccountCode().getId();
    		}
    	}
    }

}
