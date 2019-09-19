package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceMobilization;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
import gov.nwcg.isuite.core.domain.impl.AirTravelImpl;
import gov.nwcg.isuite.core.domain.impl.CountrySubdivisionImpl;
import gov.nwcg.isuite.core.domain.impl.JetPortImpl;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceMobilizationImpl;
import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.vo.AirTravelVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;

public class IncidentResourceRosterPropagationHandler {
	
	public IncidentResourceRosterPropagationHandler(){
		
	}
	
	/*
	 * Implementing the propagation rules as outlined in the propagate spreadsheet rules.
	 */
	public static void propagateParentChanges(ResourceData origResourceData,Resource parent, Resource child) throws Exception {
		Boolean isParentStrikeTeam=false;
		Boolean isChildStatusDemob=false;
		
		IncidentResource parentIR = parent.getIncidentResources().iterator().next();
		IncidentResource childIR = child.getIncidentResources().iterator().next();

		/*
		 * Establish if isParentStrikeForce is true or false
		 */
		if(null != parentIR){
			for(Assignment a : parentIR.getWorkPeriod().getAssignments()){
				if(a.getEndDate()==null){
					if(BooleanUtility.isTrue(a.getKind().getStrikeTeam())){
						isParentStrikeTeam=true;
					}
					break;
				}
			}
		}
		
		// Establish if child's status is Demob
		for(Assignment a : childIR.getWorkPeriod().getAssignments()){
			if(a.getEndDate()==null){
				if(null != a.getAssignmentStatus() 
						&& a.getAssignmentStatus()==AssignmentStatusTypeEnum.D)
					isChildStatusDemob=true;
				break;
			}
		}
		
		if(null != parentIR && null != childIR){

			if(origResourceData.agencyId==null && null != parent.getAgencyId()){
				// if parent was originally null, apply if child is null
				if(null == child.getAgencyId()){
					Agency agency = new AgencyImpl();
					agency.setId(parent.getAgencyId());
					child.setAgency(agency);
				}
			}else{
				// if new parent value changed, then always apply to child
				if( null != origResourceData.agencyId && (origResourceData.agencyId.compareTo(parent.getAgencyId()) != 0)){
					Agency agency = new AgencyImpl();
					agency.setId(parent.getAgencyId());
					child.setAgency(agency);
				}
			}

			if(origResourceData.unitId==null && null != parent.getOrganizationId()){
				// if parent was originally null, apply if child is null
				if(null == child.getOrganizationId()){
					Organization org = new OrganizationImpl();
					org.setId(parent.getOrganizationId());
					child.setOrganization(org);
				}
			}else{
				// if new parent value changed, then always apply to child
				if(null != origResourceData.unitId && origResourceData.unitId.compareTo(parent.getOrganizationId()) != 0){
					Organization org = new OrganizationImpl();
					org.setId(parent.getOrganizationId());
					child.setOrganization(org);
				}
			}

			if(origResourceData.pdcId==null && null != parent.getPrimaryDispatchCenterId()){
				// if parent was originally null, apply if child is null
				if(null == child.getPrimaryDispatchCenterId()){
					Organization org = new OrganizationImpl();
					org.setId(parent.getPrimaryDispatchCenterId());
					child.setPrimaryDispatchCenter(org);
				}
			}else{
				// if new parent value changed, then always apply to child
				if(null != origResourceData.pdcId && origResourceData.pdcId.compareTo(parent.getPrimaryDispatchCenterId()) != 0){
					Organization org = new OrganizationImpl();
					org.setId(parent.getPrimaryDispatchCenterId());
					child.setPrimaryDispatchCenter(org);
				}
			}
			
			/**
			 * MJG, 11/8/2011: The outer IF clause is under review in response to QC Defect #2035:
			 * "[When editing,] The Accounting Code for the primary resource is not being propagated to the subordinate resources." 
			 */
			if(null == childIR.getWorkPeriod().getDefIncidentAccountCode()){
				if(null != parentIR.getWorkPeriod().getDefIncidentAccountCode()){
					IncidentAccountCode iac = IncidentAccountCodeVo.toEntity(null, IncidentAccountCodeVo.getInstance(parentIR.getWorkPeriod().getDefIncidentAccountCode(), false), false);
					childIR.getWorkPeriod().setDefIncidentAccountCode(iac);
				}
			}else{
				if(null != origResourceData.iacId && origResourceData.iacId.compareTo(parentIR.getWorkPeriod().getDefIncidentAccountCode().getId()) != 0){
					IncidentAccountCode iac = IncidentAccountCodeVo.toEntity(null, IncidentAccountCodeVo.getInstance(parentIR.getWorkPeriod().getDefIncidentAccountCode(), false), false);
					childIR.getWorkPeriod().setDefIncidentAccountCode(iac);
				}
			}

			if(origResourceData.ciCheckInDate==null && null != parentIR.getWorkPeriod().getCICheckInDate()){
				// if parent was originally null, apply if child is null
				if(null == childIR.getWorkPeriod().getCICheckInDate())
					childIR.getWorkPeriod().setCICheckInDate(parentIR.getWorkPeriod().getCICheckInDate());
			}else{
				// if new parent value changed, then always apply to child
				if(origResourceData.ciCheckInDate != parentIR.getWorkPeriod().getCICheckInDate())
					childIR.getWorkPeriod().setCICheckInDate(parentIR.getWorkPeriod().getCICheckInDate());
			}

			// propagate assignDate if checkin date is available
			if(null != childIR.getCostData() && null == childIR.getCostData().getAssignDate()){
				childIR.getCostData().setAssignDate(childIR.getWorkPeriod().getCICheckInDate());
			}
			
			if(origResourceData.ciCheckInDate==null && null != parentIR.getWorkPeriod().getCICheckInDate()){
				// if parent was originally null, apply if child is null
				if(null == childIR.getWorkPeriod().getCICheckInDate())
					childIR.getWorkPeriod().setCICheckInDate(parentIR.getWorkPeriod().getCICheckInDate());
			}else{
				// if new parent value changed, then always apply to child
				if(origResourceData.ciCheckInDate != parentIR.getWorkPeriod().getCICheckInDate())
					childIR.getWorkPeriod().setCICheckInDate(parentIR.getWorkPeriod().getCICheckInDate());
			}
			
			/*
			 * Defect #3270 CR126
			 * Only propagate StrikeTeam release date if child status is not Demobed
			 */
			if(BooleanUtility.isTrue(isParentStrikeTeam)){
				if(BooleanUtility.isFalse(isChildStatusDemob)){
					childIR.getWorkPeriod().setDMReleaseDate(parentIR.getWorkPeriod().getDMReleaseDate());
				}else{
					// do nothing for release date
				}
			}else{
				if(origResourceData.dmReleaseDate==null && null != parentIR.getWorkPeriod().getDMReleaseDate()){
					// if parent was originally null, apply if child is null
					if(null == childIR.getWorkPeriod().getDMReleaseDate())
						childIR.getWorkPeriod().setDMReleaseDate(parentIR.getWorkPeriod().getDMReleaseDate());
				}else{
					// if new parent value changed, then always apply to child
					if(origResourceData.dmReleaseDate != parentIR.getWorkPeriod().getDMReleaseDate())
						childIR.getWorkPeriod().setDMReleaseDate(parentIR.getWorkPeriod().getDMReleaseDate());
				}
			}

			if(null == childIR.getWorkPeriod().getDMTentativeReleaseDate() )
				childIR.getWorkPeriod().setDMTentativeReleaseDate(parentIR.getWorkPeriod().getDMTentativeReleaseDate());
	
			if(null == childIR.getWorkPeriod().getCIFirstWorkDate() )
				childIR.getWorkPeriod().setCIFirstWorkDate(parentIR.getWorkPeriod().getCIFirstWorkDate());

			if(null == childIR.getWorkPeriod().getCIResourceMobilization() ){
				if(null != parentIR.getWorkPeriod().getCIResourceMobilization()){
					ResourceMobilization rm = new ResourceMobilizationImpl();
				
					rm.getWorkPeriods().add(childIR.getWorkPeriod());
					rm.setStartDate(parentIR.getWorkPeriod().getCIResourceMobilization().getStartDate());
					rm.setResource(child);
					
					childIR.getWorkPeriod().setCIResourceMobilization(rm);
				}
			}	


			// Demob City
			if( (origResourceData.dmTentativeDemobCity==null || origResourceData.dmTentativeDemobCity.isEmpty()) && null != parentIR.getWorkPeriod().getDMTentativeDemobCity()){
				// if parent was originally null, apply if child is null
				if(null == childIR.getWorkPeriod().getDMTentativeDemobCity())
					childIR.getWorkPeriod().setDMTentativeDemobCity(parentIR.getWorkPeriod().getDMTentativeDemobCity());
			}else{
				// if new parent value changed, then always apply to child
				if(null != parentIR.getWorkPeriod().getDMTentativeDemobCity())
					childIR.getWorkPeriod().setDMTentativeDemobCity(parentIR.getWorkPeriod().getDMTentativeDemobCity());
			}

			// Demob State
			if(origResourceData.dmTentativeDemobStateId==null && null != parentIR.getWorkPeriod().getDMTentativeDemobStateId()){
				// if parent was originally null, apply if child is null
				if(null == childIR.getWorkPeriod().getDMTentativeDemobStateId()){
					CountrySubdivision cs = new CountrySubdivisionImpl();
					cs.setId(parentIR.getWorkPeriod().getDMTentativeDemobStateId());
					
					childIR.getWorkPeriod().setDMTentativeDemobState(cs);
				}
			}else{
				// if new parent value changed, then always apply to child
				if( (origResourceData.dmTentativeDemobStateId!=null) && (origResourceData.dmTentativeDemobStateId.compareTo(parentIR.getWorkPeriod().getDMTentativeDemobStateId()) != 0)){
					CountrySubdivision cs = new CountrySubdivisionImpl();
					cs.setId(parentIR.getWorkPeriod().getDMTentativeDemobStateId());
					
					childIR.getWorkPeriod().setDMTentativeDemobState(cs);
				}
			}
			
			if(origResourceData.ciArrivalJetPortId==null && null != parentIR.getWorkPeriod().getCIArrivalJetPortId()){
				// if parent was originally null, apply if child is null
				if(null == childIR.getWorkPeriod().getCIArrivalJetPortId()){
					JetPort jp = new JetPortImpl();
					jp.setId(parentIR.getWorkPeriod().getCIArrivalJetPort().getId());
					
					childIR.getWorkPeriod().setCIArrivalJetPort(jp);
				}
			}else{
				// if new parent value changed, then always apply to child
				if( (origResourceData.ciArrivalJetPortId!=null) && (origResourceData.ciArrivalJetPortId.compareTo(parentIR.getWorkPeriod().getCIArrivalJetPortId()) != 0)){
					JetPort jp = new JetPortImpl();
					jp.setId(parentIR.getWorkPeriod().getCIArrivalJetPortId());
					
					childIR.getWorkPeriod().setCIArrivalJetPort(jp);
				}
			}

			if(null != parentIR.getWorkPeriod().getDMAirTravel()){
				if(origResourceData.dmJetportId==null && null != parentIR.getWorkPeriod().getDMAirTravel().getJetPortId()){
					// if parent was originally null, apply if child is null
					if(null != childIR.getWorkPeriod().getDMAirTravel() 
							&& null == childIR.getWorkPeriod().getDMAirTravel().getJetPortId()){
						JetPort jp = new JetPortImpl();
						jp.setId(parentIR.getWorkPeriod().getDMAirTravel().getJetPortId());
						
						childIR.getWorkPeriod().getDMAirTravel().setJetPort(jp);
					}
				}else{
					// if new parent value changed, then always apply to child
					if( (origResourceData.dmJetportId!=null) && (origResourceData.dmJetportId.compareTo(parentIR.getWorkPeriod().getDMAirTravel().getJetPortId()) != 0)){
						if(null == childIR.getWorkPeriod().getDMAirTravel()){
							childIR.getWorkPeriod().setDMAirTravel(new AirTravelImpl());
						}
								
						JetPort jp = new JetPortImpl();
						jp.setId(parentIR.getWorkPeriod().getDMAirTravel().getJetPortId());
						
						childIR.getWorkPeriod().getDMAirTravel().setJetPort(jp);
					}
				}
			}
			
			if(origResourceData.ciTravelMethod==null && null != parentIR.getWorkPeriod().getCiTravelMethod()){
				// if parent was originally null, apply if child is null
				if(null == childIR.getWorkPeriod().getCiTravelMethod()){
					childIR.getWorkPeriod().setCiTravelMethod(parentIR.getWorkPeriod().getCiTravelMethod());
				}
			}else{
				// if new parent value changed, then always apply to child
				if(origResourceData.ciTravelMethod != parentIR.getWorkPeriod().getCiTravelMethod()){
					childIR.getWorkPeriod().setCiTravelMethod(parentIR.getWorkPeriod().getCiTravelMethod());
				}
			}

			if(origResourceData.dmTravelMethod!=null && (origResourceData.dmTravelMethod != parentIR.getWorkPeriod().getCiTravelMethod())){
				// if parent field editted, apply to child
				childIR.getWorkPeriod().setCiTravelMethod(parentIR.getWorkPeriod().getCiTravelMethod());
			}
			
			if(null == childIR.getWorkPeriod().getCILengthAtAssignment() || childIR.getWorkPeriod().getCILengthAtAssignment().intValue() < 1)
				childIR.getWorkPeriod().setCILengthAtAssignment(parentIR.getWorkPeriod().getCILengthAtAssignment());

			if(null == origResourceData.dmTentativeArrivalDate && null != parentIR.getWorkPeriod().getDmTentativeArrivalDate()){
				if(null == childIR.getWorkPeriod().getDmTentativeArrivalDate()){
					childIR.getWorkPeriod().setDmTentativeArrivalDate(parentIR.getWorkPeriod().getDmTentativeArrivalDate());
				}
			}else{
				if(origResourceData.dmTentativeArrivalDate != parentIR.getWorkPeriod().getDmTentativeArrivalDate()){
					if(null == childIR.getWorkPeriod().getDmTentativeArrivalDate())
						childIR.getWorkPeriod().setDmTentativeArrivalDate(parentIR.getWorkPeriod().getDmTentativeArrivalDate());
				}
			}
			
			if(null == childIR.getWorkPeriod().getDMAirTravel()){
				AirTravelVo atvo = new AirTravelVo();
				atvo.setHoursToAirport(new Integer(0));
				atvo.setMinutesToAirport(new Integer(0));
				atvo.setLeaveTime("");
				childIR.getWorkPeriod().setDMAirTravel(AirTravelVo.toEntity(atvo,true));
			}

			if(null != parentIR.getWorkPeriod().getDMAirTravel()){
				if(null == origResourceData.travelHours && null != parentIR.getWorkPeriod().getDMAirTravel().getHoursToAirport()){
					if(null == childIR.getWorkPeriod().getDMAirTravel().getHoursToAirport()
							|| childIR.getWorkPeriod().getDMAirTravel().getHoursToAirport().intValue()==0){
						childIR.getWorkPeriod().getDMAirTravel().setHoursToAirport(parentIR.getWorkPeriod().getDMAirTravel().getHoursToAirport());
					}
				}else{
					if( null != origResourceData.travelHours){
						Integer hours = parentIR.getWorkPeriod().getDMAirTravel().getHoursToAirport() ;
						if(origResourceData.travelHours.compareTo(hours) != 0){
							if(null == childIR.getWorkPeriod().getDMAirTravel().getHoursToAirport()){
								childIR.getWorkPeriod().getDMAirTravel().setHoursToAirport(hours);
							}
						}
					}
				}

				if(null == origResourceData.travelMinutes && null != parentIR.getWorkPeriod().getDMAirTravel().getMinutesToAirport()){
					if(null == childIR.getWorkPeriod().getDMAirTravel().getMinutesToAirport()
							|| childIR.getWorkPeriod().getDMAirTravel().getMinutesToAirport().intValue()==0){
						childIR.getWorkPeriod().getDMAirTravel().setMinutesToAirport(parentIR.getWorkPeriod().getDMAirTravel().getMinutesToAirport());
					}
				}else{
					if( null != origResourceData.travelMinutes){
						Integer minutes = parentIR.getWorkPeriod().getDMAirTravel().getMinutesToAirport() ;
						if(origResourceData.travelMinutes.compareTo(minutes) != 0){
							if(null == childIR.getWorkPeriod().getDMAirTravel().getMinutesToAirport()){
								childIR.getWorkPeriod().getDMAirTravel().setMinutesToAirport(minutes);
							}
						}
					}
				}
				
			}

			
			
			// propagate Employment Info
			//propagateParentEmploymentInfo(origResourceData, parent, child);
		}
		
		
	}

	public static void propagateParentEmploymentInfo(ResourceData origResourceData,Resource parent, Resource child) throws Exception {
		
		IncidentResource parentIR = parent.getIncidentResources().iterator().next();
		IncidentResource childIR = child.getIncidentResources().iterator().next();

		if(null != parentIR && null != childIR){

			// validate parent is not a st or tf
			// employment info propagation only applies to crews
			
			if(parentIR.getResource().getResourceClassification() != ResourceClassificationEnum.ST 
					&& parentIR.getResource().getResourceClassification() != ResourceClassificationEnum.TF ){
			
				if(null != parentIR.getWorkPeriod() && null != parentIR.getWorkPeriod().getAssignments()){
					Assignment currentAssignment = null;
					
					for(Assignment a : parentIR.getWorkPeriod().getAssignments()){
						if(a.getEndDate()==null){
							currentAssignment=a;
							break;
						}
					}
					
					if(null != currentAssignment){
						if(null != currentAssignment.getAssignmentTime()){
							
						}
					}
				}
			}
			
			
		}
	}	
	
		
}
