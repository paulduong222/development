package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.AirTravel;
import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
import gov.nwcg.isuite.core.domain.impl.AirTravelImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.domain.impl.CountrySubdivisionImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;

import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.support.lob.LobCreator;

public class IncidentResourceInWorkVo extends AbstractVo {
	private IncidentResourceCommonVo incidentResourceCommonVo=null;
	private IncidentResourceCheckInDemobVo incidentResourceCheckInDemobVo=null;
	
	public IncidentResourceInWorkVo(){
		
	}

	public static Resource toEntity(Resource entity, IncidentResourceInWorkVo vo) throws Exception {

		if(null == entity){
			entity = new ResourceImpl();
			entity.setEnabled(true);
		}
		
		/*
		 * Start by populating the resource entity.
		 * If relational data if available, the populateResourceEntity method
		 * will populate the incidentResource.
		 */
		populateResourceEntity(entity,vo);
		
		return entity;
	}

	private static void populateResourceEntity(Resource entity,IncidentResourceInWorkVo vo) throws Exception {
		if(null != vo.getIncidentResourceCommonVo()){
			IncidentResourceCommonVo commonVo = vo.getIncidentResourceCommonVo();
			
			entity.setFirstName(commonVo.getFirstName());
			entity.setLastName(commonVo.getLastName());
			entity.setResourceName(commonVo.getResourceName());
			entity.setPhone(commonVo.getPhone());
			entity.setPerson(commonVo.getPerson());
			entity.setPermanent(commonVo.getPermanent());
			entity.setResourceClassification(commonVo.getResourceClassification());
			entity.setNameOnPictureId(commonVo.getNameOnPictureId());
			
			// Set agency if exists
			if( (null != commonVo.getResourceAgencyId()) && (commonVo.getResourceAgencyId()>0L) ){
				Agency agency = new AgencyImpl();
				agency.setId(commonVo.getResourceAgencyId());
				entity.setAgency(agency);
			}

			// Set organization if exists
			if( (null != commonVo.getResourceOrganizationId()) && (commonVo.getResourceOrganizationId()>0L) ){
				Organization org= new OrganizationImpl();
				org.setId(commonVo.getResourceOrganizationId());
				entity.setOrganization(org);
			}

			if( (null == entity.getIncidentResources()) || (entity.getIncidentResources().size()<1) ){
				// create new incidentResource instance
				IncidentResource incidentResource = new IncidentResourceImpl();
				
				// create new incident instance
				Incident incident = new IncidentImpl();
				incident.setId(vo.incidentResourceCommonVo.getIncidentId());
				
				// associate incident with incidentResource
				incidentResource.setIncident(incident);
				
				// associate incidentResource with resource
				incidentResource.setResource(entity);
				
				// create new work period
				WorkPeriod workPeriod = new WorkPeriodImpl();
				
				// associate incidentResource with workPeriod
				workPeriod.setIncidentResource(incidentResource);
				
				// create new assignment
				Assignment assignment = new AssignmentImpl();
				
				workPeriod.getAssignments().add(assignment);
				
				// associate workPeriod with incidentResource
				incidentResource.setWorkPeriod(workPeriod);
				
				// add incidentResource to resource
				entity.getIncidentResources().add(incidentResource);
				
			}	
			
			populateIncidentResourceEntity(((List<IncidentResource>)entity.getIncidentResources()).get(0),vo);
		}
	}
	
	private static void populateIncidentResourceEntity(IncidentResource entity,IncidentResourceInWorkVo vo) throws Exception {
		entity.setNameAtIncident("");
		
		populateWorkPeriodEntity(entity.getWorkPeriod(),vo);
	}

	private static void populateWorkPeriodEntity(WorkPeriod entity,IncidentResourceInWorkVo vo) throws Exception {
		if(null != vo.getIncidentResourceCheckInDemobVo()){
			IncidentResourceCheckInDemobVo data = vo.getIncidentResourceCheckInDemobVo();
			
			if( (null != data.getCiJetPortVo()) && (data.getCiJetPortVo().getId()>0L) ){
				entity.setCIArrivalJetPort(JetPortVo.toEntity(null,data.getCiJetPortVo(),false));
			}

			if( (null != data.getCiResourceMobilizationVo()) && (null != data.getCiResourceMobilizationVo().getStartDate()) ) {
				// TODO:
			}
			
			entity.setCICheckInDate(vo.getIncidentResourceCommonVo().getCiCheckInDate());
			entity.setCIFirstWorkDate(data.getCiFirstWorkDate());
			entity.setCILengthAtAssignment(data.getCiLengthAtAssignment());
			entity.setCIPrePlanningRemarks( data.getCiPrePlanningRemarks());
			entity.setCIRentalLocation(data.getCiRentalLocation());
			entity.setDMCheckoutFormPrinted(data.getDmCheckoutFormPrinted());
			entity.setDMPlanningDispatchNotified(data.getDmPlanningDispatchNotified());
			entity.setDMPlanningRemarks(data.getDmPlanningRemarks());
			entity.setDMReAssignable(data.getDmReAssignable());
			entity.setDMReleaseDate(vo.getIncidentResourceCommonVo().getDmReleaseDate());
			entity.setDMReleaseDispatchNotified(data.getDmReleaseDispatchNotified());
			entity.setDMReleaseRemarks(data.getDmReleaseRemarks());
			entity.setDMRestOvernight(data.getDmRestOvernight());
			entity.setDMTentativeDemobCity(data.getDmTentativeDemobCity());
			if( (null != data.getDmTentativeDemobState()) && (data.getDmTentativeDemobState() > 0L ) ){
				CountrySubdivision state = new CountrySubdivisionImpl();
				state.setId(data.getDmTentativeDemobState());
				entity.setDMTentativeDemobState(state);
			}
			entity.setDMTentativeReleaseDate(data.getDmTentativeReleaseDate());
			entity.setDMTravelMethod(data.getDmTravelMethod());
			if(data.getDmTravelMethod().equals(TravelMethodTypeEnum.AR) || data.getDmTravelMethod().equals(TravelMethodTypeEnum.AIR)){
				if(null != data.getDmAirTravelVo()){
					
					if(null==entity.getDMAirTravel())
					{
						AirTravel airTravel = new AirTravelImpl();
						airTravel.setId(null);
						
						entity.setDMAirTravel(airTravel);
					}
					entity.getDMAirTravel().setHoursToAirport(data.getDmAirTravelVo().getHoursToAirport());
					entity.getDMAirTravel().setMinutesToAirport(data.getDmAirTravelVo().getMinutesToAirport());
					entity.getDMAirTravel().setLeaveTime((null != data.getDmAirTravelVo().getLeaveTime() && !data.getDmAirTravelVo().getLeaveTime().isEmpty() ? Integer.valueOf(data.getDmAirTravelVo().getLeaveTime()) : 0 ));
					entity.getDMAirTravel().setDispatchNotified(data.getDmAirTravelVo().getDispatchNotified());
					entity.getDMAirTravel().setItineraryReceived(data.getDmAirTravelVo().getItineraryReceived());
					entity.getDMAirTravel().setAirline(data.getDmAirTravelVo().getAirline());
					entity.getDMAirTravel().setFlightNumber(data.getDmAirTravelVo().getFlightNumber());
					entity.getDMAirTravel().setFlightTime((null != data.getDmAirTravelVo().getFlightTime() && !data.getDmAirTravelVo().getFlightTime().isEmpty() ? Integer.valueOf(data.getDmAirTravelVo().getFlightTime()) : 0));
					entity.getDMAirTravel().setRemarks(data.getDmAirTravelVo().getRemarks());
					entity.getDMAirTravel().setWorkPeriod(entity);
				}
			}
			
			populateAssignmentEntity(((List<Assignment>)entity.getAssignments()).get(0),vo);
		}
	}
	
	private static void populateAssignmentEntity(Assignment entity,IncidentResourceInWorkVo vo) throws Exception {
		entity.setRequestNumber(vo.getIncidentResourceCommonVo().getRequestNumber());
		
		if(null != vo.getIncidentResourceCheckInDemobVo()){
			IncidentResourceCheckInDemobVo data = vo.getIncidentResourceCheckInDemobVo();
			
			entity.setAssignmentStatus(vo.getIncidentResourceCommonVo().getAssignmentStatus());
			
			if( (null != vo.getIncidentResourceCommonVo().getKindId()) && (vo.getIncidentResourceCommonVo().getKindId()>0L) ){
				Kind kind = new KindImpl();
				kind.setId(vo.getIncidentResourceCommonVo().getKindId());
				entity.setKind(kind);
			}

			// TODO: setStartDate
			entity.setStartDate(Calendar.getInstance().getTime());
			
			entity.setTraining(vo.getIncidentResourceCommonVo().getTraining());
		}
	}
	
	/**
	 * Returns the incidentResourceCheckInDemobVo.
	 *
	 * @return 
	 *		the incidentResourceCheckInDemobVo to return
	 */
	public IncidentResourceCheckInDemobVo getIncidentResourceCheckInDemobVo() {
		return incidentResourceCheckInDemobVo;
	}

	/**
	 * Sets the incidentResourceCheckInDemobVo.
	 *
	 * @param incidentResourceCheckInDemobVo 
	 *			the incidentResourceCheckInDemobVo to set
	 */
	public void setIncidentResourceCheckInDemobVo(
			IncidentResourceCheckInDemobVo incidentResourceCheckInDemobVo) {
		this.incidentResourceCheckInDemobVo = incidentResourceCheckInDemobVo;
	}

	/**
	 * Returns the incidentResourceCommonVo.
	 *
	 * @return 
	 *		the incidentResourceCommonVo to return
	 */
	public IncidentResourceCommonVo getIncidentResourceCommonVo() {
		return incidentResourceCommonVo;
	}

	/**
	 * Sets the incidentResourceCommonVo.
	 *
	 * @param incidentResourceCommonVo 
	 *			the incidentResourceCommonVo to set
	 */
	public void setIncidentResourceCommonVo(
			IncidentResourceCommonVo incidentResourceCommonVo) {
		this.incidentResourceCommonVo = incidentResourceCommonVo;
	}
	
	
}
