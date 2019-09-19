package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.AirTravel;
import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceMobilization;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.WorkPeriodOvernightStayInfo;
import gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue;
import gov.nwcg.isuite.core.domain.impl.AirTravelImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.domain.impl.CountrySubdivisionImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceMobilizationImpl;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class WorkPeriodVo extends AbstractVo implements PersistableVo {
	private Collection<AssignmentVo> assignmentVos=new ArrayList<AssignmentVo>();
	private AssignmentVo currentAssignmentVo=new AssignmentVo();
    private JetPortVo ciArrivalJetPortVo=new JetPortVo();
    private ResourceMobilizationVo ciResourceMobilizationVo=new ResourceMobilizationVo();
	private String ciRentalLocation;
	//private Date ciCheckInDate;
	//private String ciCheckInTime="";
	private DateTransferVo ciCheckInDateVo=new DateTransferVo();
	private DateTransferVo ciFirstWorkDateVo=new DateTransferVo();
	//private Date ciFirstWorkDate;
	private String ciPrePlanningRemarks;
	private Long ciLengthAtAssignment;

	//private Date ciTentativeArrivalDate;
	//private String ciTentativeArrivalTime;
	private DateTransferVo ciTentativeArrivalDateVo=new DateTransferVo();
	
	private TravelMethodVo ciTravelMethodVo;
	private String dmTentativeDemobCity;
    private CountryCodeSubdivisionVo dmTentativeDemobStateVo=new CountryCodeSubdivisionVo();
    
	//private Date dmReleaseDate;
	//private String dmReleaseTime="";
	private DateTransferVo dmReleaseDateVo=new DateTransferVo();
	
	//private Date dmTentativeArrivalDate;
	//private String dmTentativeArrivalTime="";
	private DateTransferVo dmTentativeArrivalDateVo=new DateTransferVo();
	
	//private Date dmTentativeReleaseDate;
	//private String dmTentativeReleaseTime="";
	private DateTransferVo dmTentativeReleaseDateVo=new DateTransferVo();
	
	private Boolean dmReAssignable;
	private Boolean dmRestOvernight;
	private Boolean dmReleaseDispatchNotified;
	private Boolean dmPlanningDispatchNotified;
	private Boolean dmCheckoutFormPrinted;
	private String dmReleaseRemarks;
	private String dmPlanningRemarks;
	private AirTravelVo dmAirTravelVo=new AirTravelVo();
	private TravelMethodVo dmTravelMethodVo;
	private Collection<WorkPeriodQuestionValueVo> checkInQuestions = new ArrayList<WorkPeriodQuestionValueVo>();
	private Collection<WorkPeriodQuestionValueVo> airTravelQuestions = new ArrayList<WorkPeriodQuestionValueVo>();
	private Collection<WorkPeriodQuestionValueVo> workPeriodQuestionValueVos=new ArrayList<WorkPeriodQuestionValueVo>();
	private Collection<WorkPeriodOvernightStayInfoVo> workPeriodOvernightStayInfoVos = new ArrayList<WorkPeriodOvernightStayInfoVo>();
	private IncidentAccountCodeVo wpDefaultIncidentAccountCodeVo;
	
	public WorkPeriodVo(){
		   
	}

	/**
	 * Returns a WorkPeriodVo instance from a WorkPeriod entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of WorkPeriodVo
	 * @throws Exception
	 */
	public static WorkPeriodVo getInstance(WorkPeriod entity,boolean cascadable) throws Exception {
		WorkPeriodVo vo = new WorkPeriodVo();

		if(null == entity)
			throw new Exception("Unable to create WorkPeriodVo from null WorkPeriod entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
			if(null != entity.getCIArrivalJetPort())
				vo.setCiArrivalJetPortVo(JetPortVo.getInstance(entity.getCIArrivalJetPort(), true));
			
			if(DateUtil.hasValue(entity.getCICheckInDate()))
				DateTransferVo.populateDate(vo.getCiCheckInDateVo(), entity.getCICheckInDate());
			
			if(DateUtil.hasValue(entity.getCIFirstWorkDate()))
				DateTransferVo.populateDate(vo.getCiFirstWorkDateVo(), entity.getCIFirstWorkDate());
			
			vo.setCiLengthAtAssignment(entity.getCILengthAtAssignment());
			vo.setCiPrePlanningRemarks(entity.getCIPrePlanningRemarks());
			vo.setCiRentalLocation(entity.getCIRentalLocation());
			
			if(null != entity.getCIResourceMobilization())
				vo.setCiResourceMobilizationVo(ResourceMobilizationVo.getInstance(entity.getCIResourceMobilization(), true));

			if(null != entity.getCiTravelMethod()){
				Collection<TravelMethodVo> tmList = TravelMethodTypeEnum.getTravelMethodVoList();
				
				for(TravelMethodVo tmvo : tmList){
					if(tmvo.getCode().equals(entity.getCiTravelMethod().name()) ){
						vo.setCiTravelMethodVo(tmvo);
					}
				}
			}
			
			vo.setCurrentAssignmentVo(new AssignmentVo());
			if( (null != entity.getAssignments() ) && (entity.getAssignments().size()>0) ){
				vo.setAssignmentVos(AssignmentVo.getInstances(entity.getAssignments(),true));
				for(AssignmentVo avo : vo.getAssignmentVos()){
					if(null==avo.getEndDate()){
						vo.setCurrentAssignmentVo(avo);
					}
				}
			}
			if(null != entity.getDMAirTravel())
				vo.setDmAirTravelVo(AirTravelVo.getInstance(entity.getDMAirTravel(), true));
			
			vo.setDmCheckoutFormPrinted(entity.isDMCheckoutFormPrinted());
			vo.setDmPlanningDispatchNotified(entity.isDMPlanningDispatchNotified());
			vo.setDmPlanningRemarks(entity.getDMPlanningRemarks());
			vo.setDmReAssignable(entity.isDMReAssignable());
			
			if(DateUtil.hasValue(entity.getDMReleaseDate()))
				DateTransferVo.populateDate(vo.getDmReleaseDateVo(), entity.getDMReleaseDate());
			//vo.setDmReleaseDate(entity.getDMReleaseDate());
			//if(null != entity.getDMReleaseDate())
			//	vo.setDmReleaseTime(DateUtil.toMilitaryTime(entity.getDMReleaseDate()));
			
			vo.setDmReleaseDispatchNotified(entity.isDMReleaseDispatchNotified());
			vo.setDmRestOvernight(entity.isDMRestOvernight());
			vo.setDmTentativeDemobCity(entity.getDMTentativeDemobCity());
			vo.setDmReleaseRemarks(entity.getDMReleaseRemarks());
			if(null != entity.getDMTentativeDemobState())
				vo.setDmTentativeDemobStateVo(CountryCodeSubdivisionVo.getInstance(entity.getDMTentativeDemobState(),true));
			
			if(DateUtil.hasValue(entity.getDmTentativeArrivalDate()))
				DateTransferVo.populateDate(vo.getDmTentativeArrivalDateVo(), entity.getDmTentativeArrivalDate());
			//vo.setDmTentativeArrivalDate(entity.getDmTentativeArrivalDate());
			//if(null != entity.getDmTentativeArrivalDate())
			//	vo.setDmTentativeArrivalTime(DateUtil.toMilitaryTime(entity.getDmTentativeArrivalDate()));
				
			if(DateUtil.hasValue(entity.getDMTentativeReleaseDate()))
				DateTransferVo.populateDate(vo.getDmTentativeReleaseDateVo(), entity.getDMTentativeReleaseDate());
			//vo.setDmTentativeReleaseDate(entity.getDMTentativeReleaseDate());
			//if(null != entity.getDMTentativeReleaseDate())
			//	vo.setDmTentativeReleaseTime(DateUtil.toMilitaryTime(entity.getDMTentativeReleaseDate()));
			
			if(null != entity.getDMTravelMethod()){
				Collection<TravelMethodVo> tmList = TravelMethodTypeEnum.getTravelMethodVoList();
				
				for(TravelMethodVo tmvo : tmList){
					if(tmvo.getCode().equals(entity.getDMTravelMethod().name()) ){
						vo.setDmTravelMethodVo(tmvo);
					}
				}
			}
			if( null != entity.getWorkPeriodQuestionValues()){
				for(WorkPeriodQuestionValue wpqv : entity.getWorkPeriodQuestionValues()){
					if(wpqv.getIncidentQuestion().getQuestion().getQuestionType()== QuestionTypeEnum.AIRTRAVEL ){
						if(wpqv.getIncidentQuestion().isVisible())
							vo.getAirTravelQuestions().add(WorkPeriodQuestionValueVo.getInstance(wpqv, true));
					}else{
						if(wpqv.getIncidentQuestion().isVisible())
							vo.getCheckInQuestions().add(WorkPeriodQuestionValueVo.getInstance(wpqv,true));
					}
				}
				vo.setWorkPeriodQuestionValueVos(WorkPeriodQuestionValueVo.getInstances(entity.getWorkPeriodQuestionValues(),true));
			}
			
			if(DateUtil.hasValue(entity.getCITentativeArrivalDate()))
				DateTransferVo.populateDate(vo.getCiTentativeArrivalDateVo(), entity.getCITentativeArrivalDate());
			//vo.setCiTentativeArrivalDate(entity.getCITentativeArrivalDate());
			
			if(null != entity.getWpOvernightStayInfos()){
				vo.setWorkPeriodOvernightStayInfoVos(WorkPeriodOvernightStayInfoVo.getInstances(entity.getWpOvernightStayInfos(), true));
			}
			
			if(null != entity.getDefIncidentAccountCodeId()){
				vo.setWpDefaultIncidentAccountCodeVo(IncidentAccountCodeVo.getInstance(entity.getDefIncidentAccountCode(), true));
			}
			
		}

		return vo;
	}

	/**
	 * Returns a WorkPeriod entity from an WorkPeriodVo.
	 * 
	 * @param vo
	 * 			the source WorkPeriodVo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @param persistables
	 * 			Optional array of referenced persistable entities 
	 * @return
	 * 			WorkPeriod entity
	 * @throws Exception
	 */
	public static WorkPeriod toEntity(WorkPeriod entity,WorkPeriodVo vo,boolean cascadable,Persistable...persistables) throws Exception {
		if(null==entity)
			entity = new WorkPeriodImpl();

		entity.setId(vo.getId());

		if(cascadable){

			if( (null != vo.getCiArrivalJetPortVo()) 
					&& 
				(null!=vo.getCiArrivalJetPortVo().getId()) 
					&& 
				(vo.getCiArrivalJetPortVo().getId() > 0)				
			){
				entity.setCIArrivalJetPort(JetPortVo.toEntity(null,vo.getCiArrivalJetPortVo(),false));
			}
			

			if( (null != vo.getCiResourceMobilizationVo())  
					&& (DateTransferVo.hasDateString(vo.getCiResourceMobilizationVo().getStartDateVo())) ) {
				ResourceMobilization resourceMobilization = null;
				if(null==entity.getCIResourceMobilization()){
					resourceMobilization = new ResourceMobilizationImpl();
					resourceMobilization.getWorkPeriods().add(entity);
				}else 
					resourceMobilization = entity.getCIResourceMobilization();
				
				if(DateTransferVo.hasDateString(vo.getCiResourceMobilizationVo().getStartDateVo())){
					Date dt=DateTransferVo.getTransferDate(vo.getCiResourceMobilizationVo().getStartDateVo());
					resourceMobilization.setStartDate(dt);
				}
				
				Resource resourceObject = (Resource)getPersistableObject(persistables,ResourceImpl.class);
				if(null != resourceObject)
					resourceMobilization.setResource(resourceObject);
				
				entity.setCIResourceMobilization(resourceMobilization);
			}
			

			if(DateTransferVo.hasDateString(vo.getCiCheckInDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getCiCheckInDateVo());
				entity.setCICheckInDate(dt);
			}
			
			if(DateTransferVo.hasDateString(vo.getCiFirstWorkDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getCiFirstWorkDateVo());
				entity.setCIFirstWorkDate(dt);
			}
			
			if(DateTransferVo.hasDateString(vo.getCiTentativeArrivalDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getCiTentativeArrivalDateVo());
				entity.setCITentativeArrivalDate(dt);
			}
			
			//Date dt= null;
			//dt=vo.getCiTentativeArrivalDate();
			//if(null != dt){
			//	dt=DateUtil.addMilitaryTimeToDate(dt, vo.getCiTentativeArrivalTime());
			//	entity.setCITentativeArrivalDate(dt);
			//}else
			//	entity.setCITentativeArrivalDate(null);
			
			entity.setCILengthAtAssignment(vo.getCiLengthAtAssignment());
			
			if(null != vo.getCiTravelMethodVo() && null != vo.getCiTravelMethodVo().getCode()){
				entity.setCiTravelMethod(TravelMethodTypeEnum.valueOf(vo.getCiTravelMethodVo().getCode()));
				if(null == vo.getDmTravelMethodVo()){
					vo.setDmTravelMethodVo(new TravelMethodVo());
					vo.getDmTravelMethodVo().setCode(vo.getCiTravelMethodVo().getCode());
				}
			}else
				entity.setCiTravelMethod(null);
			
			if(StringUtility.hasValue(vo.getCiPrePlanningRemarks()))
				entity.setCIPrePlanningRemarks(vo.getCiPrePlanningRemarks());
			else
				entity.setCIPrePlanningRemarks("");
			if(StringUtility.hasValue(vo.getCiRentalLocation()))
				entity.setCIRentalLocation(vo.getCiRentalLocation().toUpperCase());
			else
				entity.setCIRentalLocation("");
			entity.setDMCheckoutFormPrinted(vo.getDmCheckoutFormPrinted());
			entity.setDMPlanningDispatchNotified(vo.getDmPlanningDispatchNotified());
			
			if(StringUtility.hasValue(vo.getDmPlanningRemarks()))
				entity.setDMPlanningRemarks(vo.getDmPlanningRemarks());
			else
				entity.setDMPlanningRemarks("");
			entity.setDMReAssignable(vo.getDmReAssignable());

			Date dt=null;
			
			if(DateTransferVo.hasDateString(vo.getDmTentativeArrivalDateVo())){
				dt=DateTransferVo.getTransferDate(vo.getDmTentativeArrivalDateVo());
				entity.setDmTentativeArrivalDate(dt);
			}else
				entity.setDmTentativeArrivalDate(null);

			//dt = vo.getDmTentativeArrivalDate();
			//if(null != dt){
			//	dt=DateUtil.addMilitaryTimeToDate(dt, vo.getDmTentativeArrivalTime());
			//	entity.setDmTentativeArrivalDate(dt);
			//}else
			//	entity.setDmTentativeArrivalDate(null);
			
			if(DateTransferVo.hasDateString(vo.getDmReleaseDateVo())){
				dt=DateTransferVo.getTransferDate(vo.getDmReleaseDateVo());
				entity.setDMReleaseDate(dt);
			}else
				entity.setDMReleaseDate(null);
			
			entity.setDMReleaseDispatchNotified(vo.getDmReleaseDispatchNotified());
			if(StringUtility.hasValue(vo.getDmReleaseRemarks()))
				entity.setDMReleaseRemarks(vo.getDmReleaseRemarks());
			else
				entity.setDMReleaseRemarks("");
			entity.setDMRestOvernight(vo.getDmRestOvernight());
			if(null != vo.getDmTentativeDemobCity() && vo.getDmTentativeDemobCity().length()>0)
				entity.setDMTentativeDemobCity(vo.getDmTentativeDemobCity().toUpperCase());
			else
				entity.setDMTentativeDemobCity("");
			if( (null != vo.getDmTentativeDemobStateVo()) && 
					LongUtility.hasValue(vo.getDmTentativeDemobStateVo().getId()) ){
				CountrySubdivision state = new CountrySubdivisionImpl();
				state.setId(vo.getDmTentativeDemobStateVo().getId());
				entity.setDMTentativeDemobState(state);
			}else{
				entity.setDMTentativeDemobState(null);
			}
			
			if(DateTransferVo.hasDateString(vo.getDmTentativeReleaseDateVo())){
				dt=DateTransferVo.getTransferDate(vo.getDmTentativeReleaseDateVo());
				entity.setDMTentativeReleaseDate(dt);
			}else
				entity.setDMTentativeReleaseDate(null);
			
			//if(null != vo.getDmTentativeReleaseDate()){
			//	dt=vo.getDmTentativeReleaseDate();
			//	if(null != dt){
//					dt=DateUtil.addTimeToDate(dt, vo.getDmTentativeReleaseTime());
			//		dt = DateUtil.addMilitaryTimeToDate(dt, vo.getDmTentativeReleaseTime());
			//		entity.setDMTentativeReleaseDate(dt);
			//}else
			//	entity.setDMTentativeReleaseDate(null);
			
			if(null == entity.getWorkPeriodQuestionValues())
				entity.setWorkPeriodQuestionValues(new ArrayList<WorkPeriodQuestionValue>());
			
			Collection<WorkPeriodQuestionValue> questionValues = new ArrayList<WorkPeriodQuestionValue>();
			
			if(null != vo.getCheckInQuestions() && vo.getCheckInQuestions().size()>0){
				for(WorkPeriodQuestionValueVo wpqvv : vo.getCheckInQuestions()){
					Boolean bFound=false;
					for(WorkPeriodQuestionValue wpqvEntity : entity.getWorkPeriodQuestionValues()){
						if(wpqvEntity.getId().compareTo(wpqvv.getId())==0){
							wpqvEntity.setQuestionValue(wpqvv.getQuestionValue());
							questionValues.add(wpqvEntity);
							bFound=true;
						}
					}
					if(!bFound){
						questionValues.add(WorkPeriodQuestionValueVo.toEntity(wpqvv, true, entity));
					}
				}
			}

			Boolean needsGroundSupport=false;
			if(null != vo.getDmTravelMethodVo() && null != vo.getDmTravelMethodVo().getCode()){
				entity.setDMTravelMethod(TravelMethodTypeEnum.valueOf(vo.getDmTravelMethodVo().getCode()));
			}else{
				entity.setDMTravelMethod(null);
			}

			Boolean hasAirTravel=false;
			if((null != vo.getCiTravelMethodVo() && null != vo.getCiTravelMethodVo().getCode() )
					&&
				(vo.getCiTravelMethodVo().getCode().equals(TravelMethodTypeEnum.AR.toString()) || vo.getCiTravelMethodVo().getCode().equals(TravelMethodTypeEnum.AIR.toString()) ) ){
				hasAirTravel=true;
			}
			if( (null != vo.getDmTravelMethodVo() && null != vo.getDmTravelMethodVo().getCode())
					&&
				(vo.getDmTravelMethodVo().getCode().equals(TravelMethodTypeEnum.AR.toString()) || vo.getDmTravelMethodVo().getCode().equals(TravelMethodTypeEnum.AIR.toString()) ) ){
				hasAirTravel=true;
			}
			
			if( hasAirTravel){
				if(null != vo.getDmAirTravelVo()){
					
					if(null==entity.getDMAirTravel())
					{
						AirTravel airTravel = new AirTravelImpl();
						airTravel.setId(null);
						
						entity.setDMAirTravel(airTravel);
					}
					if(null != vo.getDmAirTravelVo().getJetPortVo() && ( null != vo.getDmAirTravelVo().getJetPortVo().getId() && vo.getDmAirTravelVo().getJetPortVo().getId() > 0 )){
						entity.getDMAirTravel().setJetPort(JetPortVo.toEntity(null, vo.getDmAirTravelVo().getJetPortVo(), false));
					} else {
						entity.getDMAirTravel().setJetPort(null);
					}
					entity.getDMAirTravel().setHoursToAirport(vo.getDmAirTravelVo().getHoursToAirport());
					entity.getDMAirTravel().setMinutesToAirport(vo.getDmAirTravelVo().getMinutesToAirport());
					entity.getDMAirTravel().setLeaveTime((null != vo.getDmAirTravelVo().getLeaveTime() && !vo.getDmAirTravelVo().getLeaveTime().isEmpty() ? Integer.valueOf(vo.getDmAirTravelVo().getLeaveTime()) : 0 ));
					entity.getDMAirTravel().setDispatchNotified(BooleanUtility.getValue(vo.getDmAirTravelVo().getDispatchNotified()));
					entity.getDMAirTravel().setItineraryReceived(BooleanUtility.getValue(vo.getDmAirTravelVo().getItineraryReceived()));
					if(null != vo.getDmAirTravelVo().getAirline() && vo.getDmAirTravelVo().getAirline().length()>0)
						entity.getDMAirTravel().setAirline(vo.getDmAirTravelVo().getAirline().toUpperCase());
					entity.getDMAirTravel().setFlightNumber(vo.getDmAirTravelVo().getFlightNumber());
					entity.getDMAirTravel().setFlightTime((null != vo.getDmAirTravelVo().getFlightTime() && !vo.getDmAirTravelVo().getFlightTime().isEmpty() ? Integer.valueOf(vo.getDmAirTravelVo().getFlightTime()) : 0 ));
					if(null != vo.getDmAirTravelVo().getRemarks() && vo.getDmAirTravelVo().getRemarks().length()>0)
						entity.getDMAirTravel().setRemarks(vo.getDmAirTravelVo().getRemarks());
					entity.getDMAirTravel().setWorkPeriod(entity);
				}
				
				if(null != vo.getAirTravelQuestions() && vo.getAirTravelQuestions().size()>0){
					for(WorkPeriodQuestionValueVo wpqvv : vo.getAirTravelQuestions()){
						Boolean bFound=false;
						for(WorkPeriodQuestionValue wpqvEntity : entity.getWorkPeriodQuestionValues()){
							if(wpqvEntity.getId().compareTo(wpqvv.getId())==0){
								wpqvEntity.setQuestionValue(wpqvv.getQuestionValue());
								
								if(null != wpqvv.getIncidentQuestionVo()){
									if(wpqvv.getIncidentQuestionVo().getQuestionVo().getQuestion().equals("Is ground support transportation needed?")){
										if(wpqvv.getIncidentQuestionVo().getQuestionVo().getStandard()){
											if(wpqvv.getQuestionValue())
												needsGroundSupport=true;
										}
									}
								}
								questionValues.add(wpqvEntity);
								bFound=true;
							}
						}
						if(!bFound){
							if(wpqvv.getIncidentQuestionVo().getQuestionVo().getQuestion().equals("Is ground support transportation needed?")){
								if(wpqvv.getQuestionValue())
									needsGroundSupport=true;
							}
							questionValues.add(WorkPeriodQuestionValueVo.toEntity(wpqvv, true, entity));
						}
					}
				}

			}else{
			}
			
			
			entity.setGroundSupport(needsGroundSupport);
			entity.getWorkPeriodQuestionValues().clear();
			entity.setWorkPeriodQuestionValues(questionValues);
			
			entity.getWpOvernightStayInfos().clear();
			Collection<WorkPeriodOvernightStayInfo> stayInfoEntities = new ArrayList<WorkPeriodOvernightStayInfo>();
			if(null != vo.getWorkPeriodOvernightStayInfoVos()){
				for(WorkPeriodOvernightStayInfoVo stayInfoVo : vo.getWorkPeriodOvernightStayInfoVos()){
					entity.addWpWpOvernightStayInfo(WorkPeriodOvernightStayInfoVo.toEntity(stayInfoVo, true, entity));
				}
			}

			Assignment assignmentEntity = null;
			if(null != entity && null != entity.getAssignments()){
				if(null != vo.getCurrentAssignmentVo() && LongUtility.hasValue(vo.getCurrentAssignmentVo().getId())){
					for(Assignment a : entity.getAssignments()){
						if(vo.getCurrentAssignmentVo().getId().compareTo(a.getId())==0){
							assignmentEntity=a;
							break;
						}
					}
				}
			}
			if(null != vo.getCurrentAssignmentVo()){
				if(null==assignmentEntity)
					assignmentEntity = new AssignmentImpl();
				assignmentEntity = AssignmentVo.toEntity(assignmentEntity, vo.getCurrentAssignmentVo(), true, entity);
			}

			if(null != assignmentEntity){
				if(null == entity.getAssignments()){
					entity.setAssignments(new ArrayList<Assignment>());
					entity.getAssignments().add(assignmentEntity);
				}else if(entity.getAssignments().size()==0){
					entity.getAssignments().add(assignmentEntity);
				}else {
					// check and see if assignment already exists?
					if( (null != assignmentEntity.getId()) 
							&&
						(assignmentEntity.getId() > 0) 
					){
						Boolean bFound=false;
						Collection<Assignment> newList = new ArrayList<Assignment>();
						
						for(Assignment aEntity : entity.getAssignments()){
							if(aEntity.getId().compareTo(vo.getCurrentAssignmentVo().getId())==0){
								// yes
							}else{
								newList.add(aEntity);
							}
						}
						newList.add(assignmentEntity);
						entity.getAssignments().clear();
						entity.setAssignments(newList);
						
					}else{
						entity.getAssignments().add(assignmentEntity);
					}
				}
			}			
			
			if(AbstractVo.hasValue(vo.getWpDefaultIncidentAccountCodeVo())){
				entity.setDefIncidentAccountCode(IncidentAccountCodeVo.toEntity(null,vo.getWpDefaultIncidentAccountCodeVo(),false));
			} else {
				entity.setDefIncidentAccountCode(null);
			}
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Perform some validation on the WorkPeriod field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source WorkPeriod entity
	 * @throws ValidationException
	 */
	private static void validateEntity(WorkPeriod entity) throws ValidationException {
		// todo
	}

	/**
	 * Returns the assignmentVos.
	 *
	 * @return 
	 *		the assignmentVos to return
	 */
	public Collection<AssignmentVo> getAssignmentVos() {
		return assignmentVos;
	}

	/**
	 * Sets the assignmentVos.
	 *
	 * @param assignmentVos 
	 *			the assignmentVos to set
	 */
	public void setAssignmentVos(Collection<AssignmentVo> assignmentVos) {
		this.assignmentVos = assignmentVos;
	}

	/**
	 * Returns the ciArrivalJetPortVo.
	 *
	 * @return 
	 *		the ciArrivalJetPortVo to return
	 */
	public JetPortVo getCiArrivalJetPortVo() {
		return ciArrivalJetPortVo;
	}

	/**
	 * Sets the ciArrivalJetPortVo.
	 *
	 * @param ciArrivalJetPortVo 
	 *			the ciArrivalJetPortVo to set
	 */
	public void setCiArrivalJetPortVo(JetPortVo ciArrivalJetPortVo) {
		this.ciArrivalJetPortVo = ciArrivalJetPortVo;
	}

	/**
	 * Returns the ciResourceMobilizationVo.
	 *
	 * @return 
	 *		the ciResourceMobilizationVo to return
	 */
	public ResourceMobilizationVo getCiResourceMobilizationVo() {
		return ciResourceMobilizationVo;
	}

	/**
	 * Sets the ciResourceMobilizationVo.
	 *
	 * @param ciResourceMobilizationVo 
	 *			the ciResourceMobilizationVo to set
	 */
	public void setCiResourceMobilizationVo(
			ResourceMobilizationVo ciResourceMobilizationVo) {
		this.ciResourceMobilizationVo = ciResourceMobilizationVo;
	}

	/**
	 * Returns the ciRentalLocation.
	 *
	 * @return 
	 *		the ciRentalLocation to return
	 */
	public String getCiRentalLocation() {
		return ciRentalLocation;
	}

	/**
	 * Sets the ciRentalLocation.
	 *
	 * @param ciRentalLocation 
	 *			the ciRentalLocation to set
	 */
	public void setCiRentalLocation(String ciRentalLocation) {
		this.ciRentalLocation = ciRentalLocation;
	}

	/**
	 * Returns the ciCheckInDate.
	 *
	 * @return 
	 *		the ciCheckInDate to return
	 */
	//public Date getCiCheckInDate() {
	//	return ciCheckInDate;
	//}

	/**
	 * Sets the ciCheckInDate.
	 *
	 * @param ciCheckInDate 
	 *			the ciCheckInDate to set
	 */
	//public void setCiCheckInDate(Date ciCheckInDate) {
	//	this.ciCheckInDate = ciCheckInDate;
	//}

	/**
	 * Returns the ciFirstWorkDate.
	 *
	 * @return 
	 *		the ciFirstWorkDate to return
	 */
	//public Date getCiFirstWorkDate() {
	//	return ciFirstWorkDate;
	//}

	/**
	 * Sets the ciFirstWorkDate.
	 *
	 * @param ciFirstWorkDate 
	 *			the ciFirstWorkDate to set
	 */
	//public void setCiFirstWorkDate(Date ciFirstWorkDate) {
	//	this.ciFirstWorkDate = ciFirstWorkDate;
	//}

	/**
	 * Returns the ciPrePlanningRemarks.
	 *
	 * @return 
	 *		the ciPrePlanningRemarks to return
	 */
	public String getCiPrePlanningRemarks() {
		return ciPrePlanningRemarks;
	}

	/**
	 * Sets the ciPrePlanningRemarks.
	 *
	 * @param ciPrePlanningRemarks 
	 *			the ciPrePlanningRemarks to set
	 */
	public void setCiPrePlanningRemarks(String ciPrePlanningRemarks) {
		this.ciPrePlanningRemarks = ciPrePlanningRemarks;
	}

	/**
	 * Returns the ciLengthAtAssignment.
	 *
	 * @return 
	 *		the ciLengthAtAssignment to return
	 */
	public Long getCiLengthAtAssignment() {
		return ciLengthAtAssignment;
	}

	/**
	 * Sets the ciLengthAtAssignment.
	 *
	 * @param ciLengthAtAssignment 
	 *			the ciLengthAtAssignment to set
	 */
	public void setCiLengthAtAssignment(Long ciLengthAtAssignment) {
		this.ciLengthAtAssignment = ciLengthAtAssignment;
	}

	/**
	 * Returns the dmTentativeDemobCity.
	 *
	 * @return 
	 *		the dmTentativeDemobCity to return
	 */
	public String getDmTentativeDemobCity() {
		return dmTentativeDemobCity;
	}

	/**
	 * Sets the dmTentativeDemobCity.
	 *
	 * @param dmTentativeDemobCity 
	 *			the dmTentativeDemobCity to set
	 */
	public void setDmTentativeDemobCity(String dmTentativeDemobCity) {
		this.dmTentativeDemobCity = dmTentativeDemobCity;
	}

	/**
	 * Returns the dmTentativeDemobStateVo.
	 *
	 * @return 
	 *		the dmTentativeDemobStateVo to return
	 */
	public CountryCodeSubdivisionVo getDmTentativeDemobStateVo() {
		return dmTentativeDemobStateVo;
	}

	/**
	 * Sets the dmTentativeDemobStateVo.
	 *
	 * @param dmTentativeDemobStateVo 
	 *			the dmTentativeDemobStateVo to set
	 */
	public void setDmTentativeDemobStateVo(
			CountryCodeSubdivisionVo dmTentativeDemobStateVo) {
		this.dmTentativeDemobStateVo = dmTentativeDemobStateVo;
	}

	/**
	 * Returns the dmReAssignable.
	 *
	 * @return 
	 *		the dmReAssignable to return
	 */
	public Boolean getDmReAssignable() {
		return dmReAssignable;
	}

	/**
	 * Sets the dmReAssignable.
	 *
	 * @param dmReAssignable 
	 *			the dmReAssignable to set
	 */
	public void setDmReAssignable(Boolean dmReAssignable) {
		this.dmReAssignable = dmReAssignable;
	}

	/**
	 * Returns the dmRestOvernight.
	 *
	 * @return 
	 *		the dmRestOvernight to return
	 */
	public Boolean getDmRestOvernight() {
		return dmRestOvernight;
	}

	/**
	 * Sets the dmRestOvernight.
	 *
	 * @param dmRestOvernight 
	 *			the dmRestOvernight to set
	 */
	public void setDmRestOvernight(Boolean dmRestOvernight) {
		this.dmRestOvernight = dmRestOvernight;
	}

	/**
	 * Returns the dmReleaseDispatchNotified.
	 *
	 * @return 
	 *		the dmReleaseDispatchNotified to return
	 */
	public Boolean getDmReleaseDispatchNotified() {
		return dmReleaseDispatchNotified;
	}

	/**
	 * Sets the dmReleaseDispatchNotified.
	 *
	 * @param dmReleaseDispatchNotified 
	 *			the dmReleaseDispatchNotified to set
	 */
	public void setDmReleaseDispatchNotified(Boolean dmReleaseDispatchNotified) {
		this.dmReleaseDispatchNotified = dmReleaseDispatchNotified;
	}

	/**
	 * Returns the dmPlanningDispatchNotified.
	 *
	 * @return 
	 *		the dmPlanningDispatchNotified to return
	 */
	public Boolean getDmPlanningDispatchNotified() {
		return dmPlanningDispatchNotified;
	}

	/**
	 * Sets the dmPlanningDispatchNotified.
	 *
	 * @param dmPlanningDispatchNotified 
	 *			the dmPlanningDispatchNotified to set
	 */
	public void setDmPlanningDispatchNotified(Boolean dmPlanningDispatchNotified) {
		this.dmPlanningDispatchNotified = dmPlanningDispatchNotified;
	}

	/**
	 * Returns the dmCheckoutFormPrinted.
	 *
	 * @return 
	 *		the dmCheckoutFormPrinted to return
	 */
	public Boolean getDmCheckoutFormPrinted() {
		return dmCheckoutFormPrinted;
	}

	/**
	 * Sets the dmCheckoutFormPrinted.
	 *
	 * @param dmCheckoutFormPrinted 
	 *			the dmCheckoutFormPrinted to set
	 */
	public void setDmCheckoutFormPrinted(Boolean dmCheckoutFormPrinted) {
		this.dmCheckoutFormPrinted = dmCheckoutFormPrinted;
	}

	/**
	 * Returns the dmReleaseRemarks.
	 *
	 * @return 
	 *		the dmReleaseRemarks to return
	 */
	public String getDmReleaseRemarks() {
		return dmReleaseRemarks;
	}

	/**
	 * Sets the dmReleaseRemarks.
	 *
	 * @param dmReleaseRemarks 
	 *			the dmReleaseRemarks to set
	 */
	public void setDmReleaseRemarks(String dmReleaseRemarks) {
		this.dmReleaseRemarks = dmReleaseRemarks;
	}

	/**
	 * Returns the dmPlanningRemarks.
	 *
	 * @return 
	 *		the dmPlanningRemarks to return
	 */
	public String getDmPlanningRemarks() {
		return dmPlanningRemarks;
	}

	/**
	 * Sets the dmPlanningRemarks.
	 *
	 * @param dmPlanningRemarks 
	 *			the dmPlanningRemarks to set
	 */
	public void setDmPlanningRemarks(String dmPlanningRemarks) {
		this.dmPlanningRemarks = dmPlanningRemarks;
	}

	/**
	 * Returns the dmAirTravelVo.
	 *
	 * @return 
	 *		the dmAirTravelVo to return
	 */
	public AirTravelVo getDmAirTravelVo() {
		return dmAirTravelVo;
	}

	/**
	 * Sets the dmAirTravelVo.
	 *
	 * @param dmAirTravelVo 
	 *			the dmAirTravelVo to set
	 */
	public void setDmAirTravelVo(AirTravelVo dmAirTravelVo) {
		this.dmAirTravelVo = dmAirTravelVo;
	}

	/**
	 * Returns the dmTravelMethod.
	 *
	 * @return 
	 *		the dmTravelMethod to return
	 */
	public TravelMethodVo getDmTravelMethodVo() {
		return dmTravelMethodVo;
	}

	/**
	 * Sets the dmTravelMethod.
	 *
	 * @param dmTravelMethod 
	 *			the dmTravelMethod to set
	 */
	public void setDmTravelMethodVo(TravelMethodVo dmTravelMethodVo) {
		this.dmTravelMethodVo = dmTravelMethodVo;
	}

	/**
	 * Returns the currentAssignmentVo.
	 *
	 * @return 
	 *		the currentAssignmentVo to return
	 */
	public AssignmentVo getCurrentAssignmentVo() {
		return currentAssignmentVo;
	}

	/**
	 * Sets the currentAssignmentVo.
	 *
	 * @param currentAssignmentVo 
	 *			the currentAssignmentVo to set
	 */
	public void setCurrentAssignmentVo(AssignmentVo currentAssignmentVo) {
		this.currentAssignmentVo = currentAssignmentVo;
	}
	/**
	 * Returns the ciTravelMethodVo.
	 *
	 * @return 
	 *		the ciTravelMethodVo to return
	 */
	public TravelMethodVo getCiTravelMethodVo() {
		return ciTravelMethodVo;
	}

	/**
	 * Sets the ciTravelMethodVo.
	 *
	 * @param ciTravelMethodVo 
	 *			the ciTravelMethodVo to set
	 */
	public void setCiTravelMethodVo(TravelMethodVo ciTravelMethodVo) {
		this.ciTravelMethodVo = ciTravelMethodVo;
	}

	/**
	 * Returns the checkInQuestions.
	 *
	 * @return 
	 *		the checkInQuestions to return
	 */
	public Collection<WorkPeriodQuestionValueVo> getCheckInQuestions() {
		return checkInQuestions;
	}

	/**
	 * Sets the checkInQuestions.
	 *
	 * @param checkInQuestions 
	 *			the checkInQuestions to set
	 */
	public void setCheckInQuestions(
			Collection<WorkPeriodQuestionValueVo> checkInQuestions) {
		this.checkInQuestions = checkInQuestions;
	}

	/**
	 * Returns the airTravelQuestions.
	 *
	 * @return 
	 *		the airTravelQuestions to return
	 */
	public Collection<WorkPeriodQuestionValueVo> getAirTravelQuestions() {
		return airTravelQuestions;
	}

	/**
	 * Sets the airTravelQuestions.
	 *
	 * @param airTravelQuestions 
	 *			the airTravelQuestions to set
	 */
	public void setAirTravelQuestions(
			Collection<WorkPeriodQuestionValueVo> airTravelQuestions) {
		this.airTravelQuestions = airTravelQuestions;
	}

	/**
	 * Returns the workPeriodQuestionValueVos.
	 *
	 * @return 
	 *		the workPeriodQuestionValueVos to return
	 */
	public Collection<WorkPeriodQuestionValueVo> getWorkPeriodQuestionValueVos() {
		return workPeriodQuestionValueVos;
	}

	/**
	 * Sets the workPeriodQuestionValueVos.
	 *
	 * @param workPeriodQuestionValueVos 
	 *			the workPeriodQuestionValueVos to set
	 */
	public void setWorkPeriodQuestionValueVos(
			Collection<WorkPeriodQuestionValueVo> workPeriodQuestionValueVos) {
		this.workPeriodQuestionValueVos = workPeriodQuestionValueVos;
	}

	/**
	 * Returns the workPeriodOvernightStayInfoVos.
	 *
	 * @return 
	 *		the workPeriodOvernightStayInfoVos to return
	 */
	public Collection<WorkPeriodOvernightStayInfoVo> getWorkPeriodOvernightStayInfoVos() {
		return workPeriodOvernightStayInfoVos;
	}

	/**
	 * Sets the workPeriodOvernightStayInfoVos.
	 *
	 * @param workPeriodOvernightStayInfoVos 
	 *			the workPeriodOvernightStayInfoVos to set
	 */
	public void setWorkPeriodOvernightStayInfoVos(
			Collection<WorkPeriodOvernightStayInfoVo> workPeriodOvernightStayInfoVos) {
		this.workPeriodOvernightStayInfoVos = workPeriodOvernightStayInfoVos;
	}

	public IncidentAccountCodeVo getWpDefaultIncidentAccountCodeVo() {
		return wpDefaultIncidentAccountCodeVo;
	}

	public void setWpDefaultIncidentAccountCodeVo(
			IncidentAccountCodeVo wpDefaultIncidentAccountCodeVo) {
		this.wpDefaultIncidentAccountCodeVo = wpDefaultIncidentAccountCodeVo;
	}

	/**
	 * @return the ciCheckDateVo
	 */
	public DateTransferVo getCiCheckInDateVo() {
		return ciCheckInDateVo;
	}

	/**
	 * @param ciCheckDateVo the ciCheckDateVo to set
	 */
	public void setCiCheckInDateVo(DateTransferVo ciCheckInDateVo) {
		this.ciCheckInDateVo = ciCheckInDateVo;
	}

	/**
	 * @return the ciTentativeArrivalDateVo
	 */
	public DateTransferVo getCiTentativeArrivalDateVo() {
		return ciTentativeArrivalDateVo;
	}

	/**
	 * @param ciTentativeArrivalDateVo the ciTentativeArrivalDateVo to set
	 */
	public void setCiTentativeArrivalDateVo(DateTransferVo ciTentativeArrivalDateVo) {
		this.ciTentativeArrivalDateVo = ciTentativeArrivalDateVo;
	}

	/**
	 * @return the dmReleaseDateVo
	 */
	public DateTransferVo getDmReleaseDateVo() {
		return dmReleaseDateVo;
	}

	/**
	 * @param dmReleaseDateVo the dmReleaseDateVo to set
	 */
	public void setDmReleaseDateVo(DateTransferVo dmReleaseDateVo) {
		this.dmReleaseDateVo = dmReleaseDateVo;
	}

	/**
	 * @return the dmTentativeReleaseDateVo
	 */
	public DateTransferVo getDmTentativeReleaseDateVo() {
		return dmTentativeReleaseDateVo;
	}

	/**
	 * @param dmTentativeReleaseDateVo the dmTentativeReleaseDateVo to set
	 */
	public void setDmTentativeReleaseDateVo(DateTransferVo dmTentativeReleaseDateVo) {
		this.dmTentativeReleaseDateVo = dmTentativeReleaseDateVo;
	}

	/**
	 * @return the dmTentativeArrivalDateVo
	 */
	public DateTransferVo getDmTentativeArrivalDateVo() {
		return dmTentativeArrivalDateVo;
	}

	/**
	 * @param dmTentativeArrivalDateVo the dmTentativeArrivalDateVo to set
	 */
	public void setDmTentativeArrivalDateVo(DateTransferVo dmTentativeArrivalDateVo) {
		this.dmTentativeArrivalDateVo = dmTentativeArrivalDateVo;
	}

	public DateTransferVo getCiFirstWorkDateVo() {
		return ciFirstWorkDateVo;
	}

	public void setCiFirstWorkDateVo(DateTransferVo ciFirstWorkDateVo) {
		this.ciFirstWorkDateVo = ciFirstWorkDateVo;
	}

}
