package gov.nwcg.isuite.core.rules.incidentresource.save.propagate;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.AirTravel;
import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceMobilization;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
import gov.nwcg.isuite.core.domain.impl.AirTravelImpl;
import gov.nwcg.isuite.core.domain.impl.CostDataImpl;
import gov.nwcg.isuite.core.domain.impl.CountrySubdivisionImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.JetPortImpl;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceMobilizationImpl;
import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.vo.PropagateFieldPromptVo;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;
import java.util.Date;

public class IncidentResourcePropagationHandler {
	public Collection<PropagateFieldPromptVo> propagateFieldPromptVos;
	public Long unknownUnitId;
	
	public IncidentResourcePropagationHandler(){
		
	}
	
	/*
	 * Implementing the propagation rules as outlined in the propagate spreadsheet rules.
	 */
	public void propagateParentChanges(ResourceData origResourceData,Resource parent, Resource child) throws Exception {
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

			// Incident
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(IncidentFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(IncidentFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					Incident inc = new IncidentImpl();
					inc.setId(parentIR.getIncident().getId());
					childIR.setIncident(inc);
				}
			}
			
			// Accounting Code
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(AccountingCodeFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(AccountingCodeFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					IncidentAccountCode defIncidentAccountCode = new IncidentAccountCodeImpl();
					defIncidentAccountCode.setId(parentIR.getWorkPeriod().getDefIncidentAccountCode().getId());
					childIR.getWorkPeriod().setDefIncidentAccountCode(defIncidentAccountCode);
				}
			}
			
			// Status - this is handled in main propagation rule
			
			// Unit ID Field
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(UnitIDFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(UnitIDFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null or unknown
					if(null == child.getOrganizationId() || child.getOrganizationId().compareTo(this.unknownUnitId)==0){
						Organization org = new OrganizationImpl();
						org.setId(parent.getOrganizationId());
						child.setOrganization(org);
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						if(null != origResourceData.unitId && origResourceData.unitId.compareTo(parent.getOrganizationId()) != 0){
							Organization org = new OrganizationImpl();
							org.setId(parent.getOrganizationId());
							child.setOrganization(org);
						}
					}
					
				}
				
			}
			
			// Agency Field
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(AgencyFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(AgencyFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null == child.getAgencyId()){
						Agency agency = new AgencyImpl();
						agency.setId(parent.getAgencyId());
						child.setAgency(agency);
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						if( null != origResourceData.agencyId && (origResourceData.agencyId.compareTo(parent.getAgencyId()) != 0)){
							Agency agency = new AgencyImpl();
							agency.setId(parent.getAgencyId());
							child.setAgency(agency);
						}
					}
					
				}
			}

			// Check-In Date Field
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(CheckInDateFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(CheckInDateFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null == childIR.getWorkPeriod().getCICheckInDate()){
						childIR.getWorkPeriod().setCICheckInDate(parentIR.getWorkPeriod().getCICheckInDate());
						if(null != childIR.getCostData()){
							if(!DateUtil.hasValue(childIR.getCostData().getAssignDate())){
								childIR .getCostData().setAssignDate(parentIR.getWorkPeriod().getCICheckInDate());
							}else{
								Date assigndate=childIR.getCostData().getAssignDate();
								Date cidate=parentIR.getWorkPeriod().getCICheckInDate();
								if(assigndate.after(cidate))
									childIR .getCostData().setAssignDate(cidate);	
							}
						}
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						childIR.getWorkPeriod().setCICheckInDate(parentIR.getWorkPeriod().getCICheckInDate());
						if(null != childIR.getCostData())
							childIR .getCostData().setAssignDate(parentIR.getWorkPeriod().getCICheckInDate());
						if(null != childIR.getCostData()){
							if(!DateUtil.hasValue(childIR.getCostData().getAssignDate())){
								childIR .getCostData().setAssignDate(parentIR.getWorkPeriod().getCICheckInDate());
							}else{
								Date assigndate=childIR.getCostData().getAssignDate();
								Date cidate=parentIR.getWorkPeriod().getCICheckInDate();
								if(assigndate.after(cidate))
									childIR .getCostData().setAssignDate(cidate);	
							}
						}
					}
					
				}
			}
			
			// Check-In Time Field
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(CheckInTimeFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(CheckInTimeFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					// since time is stored as part of the checkindate, have to verify checkin date is not null
					if(null != childIR.getWorkPeriod().getCICheckInDate() && null!=parentIR.getWorkPeriod().getCICheckInDate()){
						String time=DateUtil.toMilitaryTime(parentIR.getWorkPeriod().getCICheckInDate());
						String childTime=DateUtil.toMilitaryTime(childIR.getWorkPeriod().getCICheckInDate());
						Date newDate=DateUtil.addMilitaryTimeToDate(childIR.getWorkPeriod().getCICheckInDate(), time);
						// when auto propagate is true, only set if child time is null
						if(!StringUtility.hasValue(childTime) || childTime.equals("0000") )
							childIR.getWorkPeriod().setCICheckInDate(newDate);
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						if(null!=parentIR.getWorkPeriod().getCICheckInDate()){
							String time=DateUtil.toMilitaryTime(parentIR.getWorkPeriod().getCICheckInDate());
							Date newDate=null;
							if(null != childIR.getWorkPeriod().getCICheckInDate())
								newDate=DateUtil.addMilitaryTimeToDate(childIR.getWorkPeriod().getCICheckInDate(), time);
							else
								newDate=parentIR.getWorkPeriod().getCICheckInDate();
							childIR.getWorkPeriod().setCICheckInDate(newDate);
						}
					}
				}
			}
			
			// Actual Release Date Field
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(ActualReleaseDateFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(ActualReleaseDateFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null == childIR.getWorkPeriod().getDMReleaseDate())
						childIR.getWorkPeriod().setDMReleaseDate(parentIR.getWorkPeriod().getDMReleaseDate());
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						childIR.getWorkPeriod().setDMReleaseDate(parentIR.getWorkPeriod().getDMReleaseDate());
					}
				}
			}

			// Actual Release Time Field
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(ActualReleaseTimeFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(ActualReleaseTimeFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					// since time is stored as part of the checkindate, have to verify checkin date is not null
					if(null != childIR.getWorkPeriod().getDMReleaseDate() && null!=parentIR.getWorkPeriod().getDMReleaseDate()){
						String time=DateUtil.toMilitaryTime(parentIR.getWorkPeriod().getDMReleaseDate());
						String childTime=DateUtil.toMilitaryTime(childIR.getWorkPeriod().getDMReleaseDate());
						Date newDate=DateUtil.addMilitaryTimeToDate(childIR.getWorkPeriod().getDMReleaseDate(), time);
						// when auto propagate is true, only set if child time is null
						if(!StringUtility.hasValue(childTime) || childTime.equals("0000") )
							childIR.getWorkPeriod().setDMReleaseDate(newDate);
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						if(null!=parentIR.getWorkPeriod().getDMReleaseDate()){
							String time=DateUtil.toMilitaryTime(parentIR.getWorkPeriod().getDMReleaseDate());
							Date newDate=null;
							if(null != childIR.getWorkPeriod().getDMReleaseDate())
								newDate=DateUtil.addMilitaryTimeToDate(childIR.getWorkPeriod().getDMReleaseDate(), time);
							else
								newDate=parentIR.getWorkPeriod().getDMReleaseDate();
							childIR.getWorkPeriod().setDMReleaseDate(newDate);
						}
					}
				}
			}
			
			// Demob City
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(DemobCityFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(DemobCityFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(!StringUtility.hasValue(childIR.getWorkPeriod().getDMTentativeDemobCity()))
						childIR.getWorkPeriod().setDMTentativeDemobCity(parentIR.getWorkPeriod().getDMTentativeDemobCity());
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						childIR.getWorkPeriod().setDMTentativeDemobCity(parentIR.getWorkPeriod().getDMTentativeDemobCity());
					}
				}
			}
			
			// Demob State
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(DemobStateFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(DemobStateFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null == childIR.getWorkPeriod().getDMTentativeDemobStateId()){
						CountrySubdivision cs = new CountrySubdivisionImpl();
						cs.setId(parentIR.getWorkPeriod().getDMTentativeDemobStateId());
						childIR.getWorkPeriod().setDMTentativeDemobState(cs);
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						if( (origResourceData.dmTentativeDemobStateId!=null) && (origResourceData.dmTentativeDemobStateId.compareTo(parentIR.getWorkPeriod().getDMTentativeDemobStateId()) != 0)){
							CountrySubdivision cs = new CountrySubdivisionImpl();
							cs.setId(parentIR.getWorkPeriod().getDMTentativeDemobStateId());
							
							childIR.getWorkPeriod().setDMTentativeDemobState(cs);
						}
					}
				}
			}
			
			// Jetport
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(JetportFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(JetportFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null == childIR.getWorkPeriod().getCIArrivalJetPortId()){
						JetPort jp = new JetPortImpl();
						jp.setId(parentIR.getWorkPeriod().getCIArrivalJetPort().getId());
						
						childIR.getWorkPeriod().setCIArrivalJetPort(jp);
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						if( (origResourceData.ciArrivalJetPortId!=null) && (origResourceData.ciArrivalJetPortId.compareTo(parentIR.getWorkPeriod().getCIArrivalJetPortId()) != 0)){
							JetPort jp = new JetPortImpl();
							jp.setId(parentIR.getWorkPeriod().getCIArrivalJetPortId());
							
							childIR.getWorkPeriod().setCIArrivalJetPort(jp);
						}
					}
				}
			}
			
			// Mobilization Travel Method
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(MobilizationTravelMethodFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(MobilizationTravelMethodFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null == childIR.getWorkPeriod().getCiTravelMethod()){
						childIR.getWorkPeriod().setCiTravelMethod(parentIR.getWorkPeriod().getCiTravelMethod());
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						childIR.getWorkPeriod().setCiTravelMethod(parentIR.getWorkPeriod().getCiTravelMethod());
					}
				}
			}
			
			// Mobilization Date
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(MobilizationDateFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(MobilizationDateFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null == childIR.getWorkPeriod().getCIResourceMobilization() ){
						if(null != parentIR.getWorkPeriod().getCIResourceMobilization()){
							ResourceMobilization rm = new ResourceMobilizationImpl();
							rm.getWorkPeriods().add(childIR.getWorkPeriod());
							rm.setStartDate(parentIR.getWorkPeriod().getCIResourceMobilization().getStartDate());
							rm.setResource(child);
							childIR.getWorkPeriod().setCIResourceMobilization(rm);
						}
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						if(null != parentIR.getWorkPeriod().getCIResourceMobilization()){
							
							ResourceMobilization rm = new ResourceMobilizationImpl();
							
							for(ResourceMobilization r : child.getResourceMobilizations()) {
								rm = r;
								break;
							}
							
							rm.setStartDate(parentIR.getWorkPeriod().getCIResourceMobilization().getStartDate());
							
//							ResourceMobilization rm = new ResourceMobilizationImpl();
//							rm.getWorkPeriods().add(childIR.getWorkPeriod());
//							rm.setStartDate(parentIR.getWorkPeriod().getCIResourceMobilization().getStartDate());
//							rm.setResource(child);
//							childIR.getWorkPeriod().setCIResourceMobilization(rm);
						}
					}
				}
			}
			
			// First Work Day
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(FirstWorkDayFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(FirstWorkDayFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null == childIR.getWorkPeriod().getCIFirstWorkDate())
						childIR.getWorkPeriod().setCIFirstWorkDate(parentIR.getWorkPeriod().getCIFirstWorkDate());
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						childIR.getWorkPeriod().setCIFirstWorkDate(parentIR.getWorkPeriod().getCIFirstWorkDate());
					}
					
				}
			}
			
			// Length of Assignment
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(LengthOfAssignmentFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(LengthOfAssignmentFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null == childIR.getWorkPeriod().getCILengthAtAssignment() || childIR.getWorkPeriod().getCILengthAtAssignment().intValue() < 1)
						childIR.getWorkPeriod().setCILengthAtAssignment(parentIR.getWorkPeriod().getCILengthAtAssignment());
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						childIR.getWorkPeriod().setCILengthAtAssignment(parentIR.getWorkPeriod().getCILengthAtAssignment());
					}
					
				}
			}
			
			// Tentative Release Date
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(TentativeReleaseDateFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(TentativeReleaseDateFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null == childIR.getWorkPeriod().getDMTentativeReleaseDate() )
						childIR.getWorkPeriod().setDMTentativeReleaseDate(parentIR.getWorkPeriod().getDMTentativeReleaseDate());
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						childIR.getWorkPeriod().setDMTentativeReleaseDate(parentIR.getWorkPeriod().getDMTentativeReleaseDate());
					}
				}
			}
			
			// Tentative Release Time
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(TentativeReleaseTimeFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(TentativeReleaseTimeFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null != childIR.getWorkPeriod().getDMTentativeReleaseDate() && null!=parentIR.getWorkPeriod().getDMTentativeReleaseDate()){
						String time=DateUtil.toMilitaryTime(parentIR.getWorkPeriod().getDMTentativeReleaseDate());
						String childTime=DateUtil.toMilitaryTime(childIR.getWorkPeriod().getDMTentativeReleaseDate());
						Date newDate=DateUtil.addMilitaryTimeToDate(childIR.getWorkPeriod().getDMTentativeReleaseDate(), time);
						// when auto propagate is true, only set if child time is null
						if(!StringUtility.hasValue(childTime) || childTime.equals("0000") )
							childIR.getWorkPeriod().setDMTentativeReleaseDate(newDate);
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						if(null!=parentIR.getWorkPeriod().getDMTentativeReleaseDate()){
							String time=DateUtil.toMilitaryTime(parentIR.getWorkPeriod().getDMTentativeReleaseDate());
							Date newDate=null;
							if(null != childIR.getWorkPeriod().getDMTentativeReleaseDate())
								newDate=DateUtil.addMilitaryTimeToDate(childIR.getWorkPeriod().getDMTentativeReleaseDate(), time);
							else
								newDate=parentIR.getWorkPeriod().getDMTentativeReleaseDate();
							childIR.getWorkPeriod().setDMTentativeReleaseDate(newDate);
						}
					}
				}
			}
			
			// Demob Travel method
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(DemobTravelMethodFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(DemobTravelMethodFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null == childIR.getWorkPeriod().getDMTravelMethod()){
						childIR.getWorkPeriod().setDMTravelMethod(parentIR.getWorkPeriod().getDMTravelMethod());
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						childIR.getWorkPeriod().setDMTravelMethod(parentIR.getWorkPeriod().getDMTravelMethod());
					}
				}
			}
			
			// Depart From Jetport
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(DepartJetportFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(DepartJetportFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null != parentIR.getWorkPeriod().getDMAirTravel()){
						if(null == childIR.getWorkPeriod().getDMAirTravel()){
							AirTravel airTravel = new AirTravelImpl();
							airTravel.setId(null);
							childIR.getWorkPeriod().setDMAirTravel(airTravel);
							childIR.getWorkPeriod().getDMAirTravel().setDispatchNotified(false);
							childIR.getWorkPeriod().getDMAirTravel().setItineraryReceived(false);
						}
						if(null == childIR.getWorkPeriod().getDMAirTravel().getJetPortId()){
							JetPort jp = new JetPortImpl();
							jp.setId(parentIR.getWorkPeriod().getDMAirTravel().getJetPortId());
							
							childIR.getWorkPeriod().getDMAirTravel().setJetPort(jp);
						}
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						if(null == childIR.getWorkPeriod().getDMAirTravel()){
							childIR.getWorkPeriod().setDMAirTravel(new AirTravelImpl());
							childIR.getWorkPeriod().getDMAirTravel().setDispatchNotified(false);
							childIR.getWorkPeriod().getDMAirTravel().setItineraryReceived(false);
						}
									
						JetPort jp = new JetPortImpl();
						jp.setId(parentIR.getWorkPeriod().getDMAirTravel().getJetPortId());
							
						childIR.getWorkPeriod().getDMAirTravel().setJetPort(jp);
					}
				}
			}
			
			// Travel Time ICP
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(TravelTimeFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(TravelTimeFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null != parentIR.getWorkPeriod().getDMAirTravel()){
						if(null == childIR.getWorkPeriod().getDMAirTravel()){
							AirTravel airTravel = new AirTravelImpl();
							airTravel.setId(null);
							childIR.getWorkPeriod().setDMAirTravel(airTravel);
							childIR.getWorkPeriod().getDMAirTravel().setDispatchNotified(false);
							childIR.getWorkPeriod().getDMAirTravel().setItineraryReceived(false);
						}
						if(!IntegerUtility.hasValue(childIR.getWorkPeriod().getDMAirTravel().getHoursToAirport())){
							childIR.getWorkPeriod().getDMAirTravel().setHoursToAirport(parentIR.getWorkPeriod().getDMAirTravel().getHoursToAirport());
						}
						if(!IntegerUtility.hasValue(childIR.getWorkPeriod().getDMAirTravel().getMinutesToAirport())){
							childIR.getWorkPeriod().getDMAirTravel().setMinutesToAirport(parentIR.getWorkPeriod().getDMAirTravel().getMinutesToAirport());
						}
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						if(null == childIR.getWorkPeriod().getDMAirTravel()){
							childIR.getWorkPeriod().setDMAirTravel(new AirTravelImpl());
							childIR.getWorkPeriod().getDMAirTravel().setDispatchNotified(false);
							childIR.getWorkPeriod().getDMAirTravel().setItineraryReceived(false);
							JetPort jp = new JetPortImpl();
							jp.setId(parentIR.getWorkPeriod().getDMAirTravel().getJetPortId());
						}
						
						childIR.getWorkPeriod().getDMAirTravel().setHoursToAirport(parentIR.getWorkPeriod().getDMAirTravel().getHoursToAirport());
						childIR.getWorkPeriod().getDMAirTravel().setMinutesToAirport(parentIR.getWorkPeriod().getDMAirTravel().getMinutesToAirport());
					}
				}
			}
			
			// Estimated Date of Arrival
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(TentativeArrivalDateFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(TentativeArrivalDateFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null == childIR.getWorkPeriod().getDmTentativeArrivalDate() )
						childIR.getWorkPeriod().setDmTentativeArrivalDate(parentIR.getWorkPeriod().getDmTentativeArrivalDate());
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						childIR.getWorkPeriod().setDmTentativeArrivalDate(parentIR.getWorkPeriod().getDmTentativeArrivalDate());
					}
				}
			}

			// Estimated Date of Arrival Time
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(TentativeArrivalTimeFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(TentativeArrivalTimeFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// if parent was originally null, apply if child is null
					if(null != childIR.getWorkPeriod().getDmTentativeArrivalDate() && null!=parentIR.getWorkPeriod().getDmTentativeArrivalDate()){
						String time=DateUtil.toMilitaryTime(parentIR.getWorkPeriod().getDmTentativeArrivalDate());
						String childTime=DateUtil.toMilitaryTime(childIR.getWorkPeriod().getDmTentativeArrivalDate());
						Date newDate=DateUtil.addMilitaryTimeToDate(childIR.getWorkPeriod().getDmTentativeArrivalDate(), time);
						// when auto propagate is true, only set if child time is null
						if(!StringUtility.hasValue(childTime) || childTime.equals("0000") )
							childIR.getWorkPeriod().setDmTentativeArrivalDate(newDate);
					}
				}else{
					if(BooleanUtility.isTrue(vo.getPromptUserToPropagateResult())){
						// if new parent value changed, then always apply to child
						if(null!=parentIR.getWorkPeriod().getDmTentativeArrivalDate()){
							String time=DateUtil.toMilitaryTime(parentIR.getWorkPeriod().getDmTentativeArrivalDate());
							Date newDate=null;
							if(null != childIR.getWorkPeriod().getDmTentativeArrivalDate())
								newDate=DateUtil.addMilitaryTimeToDate(childIR.getWorkPeriod().getDmTentativeArrivalDate(), time);
							else
								newDate=parentIR.getWorkPeriod().getDmTentativeArrivalDate();
							childIR.getWorkPeriod().setDmTentativeArrivalDate(newDate);
						}
					}
				}
			}
			
			// Payment Agency
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(PaymentAgencyFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(PaymentAgencyFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// propagate if child is null
					if(null==childIR.getCostData() || (null==childIR.getCostData().getPaymentAgency() || !LongUtility.hasValue(childIR.getCostData().getPaymentAgency().getId()))){
						if(null==childIR.getCostData()){
							CostData cd = new CostDataImpl();
							cd.setIncidentResource(childIR);
							cd.setAccrualLocked(false);
							cd.setUseAccrualsOnly(false);
							cd.setGenerateCosts(parentIR.getCostData().getGenerateCosts());
							childIR.setCostData(cd);
						}
						if(null==childIR.getCostData().getPaymentAgency()){
							Agency agency = new AgencyImpl();
							agency.setId(parentIR.getCostData().getPaymentAgency().getId());
							agency.setAgencyCode(parentIR.getCostData().getPaymentAgency().getAgencyCode());
							childIR.getCostData().setPaymentAgency(agency);
						}
					}
				}
			}
			
			// Assign Date
			if(BooleanUtility.isTrue(PropagateFieldPromptVo.hasFieldVo(AssignDateFieldRule.FIELD_NAME, propagateFieldPromptVos))){
				PropagateFieldPromptVo vo = PropagateFieldPromptVo.getFieldVo(AssignDateFieldRule.FIELD_NAME, propagateFieldPromptVos);
				if(BooleanUtility.isTrue(vo.getFieldAutoPropagate())){
					// propagate if child is null
					if(null==childIR.getCostData() || (!DateUtil.hasValue(childIR.getCostData().getAssignDate()))){
						if(null==childIR.getCostData()){
							CostData cd = new CostDataImpl();
							cd.setIncidentResource(childIR);
							cd.setAccrualLocked(false);
							cd.setUseAccrualsOnly(false);
							cd.setGenerateCosts(parentIR.getCostData().getGenerateCosts());
							childIR.setCostData(cd);
						}
						if(!DateUtil.hasValue(childIR.getCostData().getAssignDate())){
							childIR.getCostData().setAssignDate(parentIR.getCostData().getAssignDate());
						}
					}
				}
			}
			
			
			
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
