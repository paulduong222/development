package gov.nwcg.isuite.core.rules.rossimport.finalimport;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceMobilization;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.domain.impl.CostDataImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceMobilizationImpl;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.RossXmlFileVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;

public class IncidentResourceBuilder {
	private ApplicationContext context=null;
	private RossXmlFileVo rxfVo;
	private GlobalCacheVo gcVo;
	private Incident incidentEntity;
	private String reimportReqNum="";
	
	public IncidentResourceBuilder(ApplicationContext ctx,RossXmlFileVo rxfVo, Incident incidentEntity){
		context=ctx;
		this.rxfVo=rxfVo;
		this.incidentEntity = incidentEntity;
	}
	
	/**
	 * @param ripResVos
	 * @param dialogueVo
	 * @throws Exception
	 */
	public int createIncidentResources(Collection<RossImportProcessResourceVo> ripResVos, DialogueVo dialogueVo) throws Exception {

		int cnt=0;
		
		IncidentResourceDao irDao=(IncidentResourceDao)context.getBean("incidentResourceDao");
		ResourceDao rDao=(ResourceDao)context.getBean("resourceDao");
		
		gcVo = (GlobalCacheVo)context.getBean("globalCacheVo");
	
		Collection<Resource> resources = new ArrayList<Resource>();
		
		HashMap<Long,RossImportProcessResourceVo> processedMap = new HashMap<Long,RossImportProcessResourceVo>();
		
		for(RossImportProcessResourceVo ripResVo : ripResVos){

			//System.out.println(ripResVo.getRequestNumber());
			if(ripResVo.getRequestNumber().equals("E-21.4")){
				//System.out.println("");
			}
			
			if(!processedMap.containsKey(ripResVo.getRossResReqId())){
				
				try{
					if(!ripResVo.getExcludeResource() && !LongUtility.hasValue(ripResVo.getEisuiteResourceId())){
						
						// process all top level resources and any children
						if(ripResVo.getRequestNumber().indexOf(".")<0){
							Resource resourceEntity = toResourceEntity(ripResVo,dialogueVo);

							processedMap.put(ripResVo.getRossResReqId(), ripResVo);

							// build roster if available
							this.buildRoster(ripResVo
											, resourceEntity
											, ripResVos
											, processedMap
											, resources
											, dialogueVo);
							
							/*
							// check for any children
							for(RossImportProcessResourceVo ripResVo2 : ripResVos){
								if(ripResVo.getRequestNumber().equals("E-21.1")){
									System.out.println("");
								}
								int idx=ripResVo.getRequestNumber().lastIndexOf(".");
								String tmpReqNum=ripResVo.getRequestNumber();
								if(idx>0)
									tmpReqNum=ripResVo.getRequestNumber().substring(0,(idx));
								
								if(ripResVo2.getRequestNumber().startsWith(tmpReqNum+".")){
									if(!ripResVo2.getExcludeResource() && !LongUtility.hasValue(ripResVo2.getEisuiteResourceId())){
										if(!ripResVo2.getExcludeFromRoster()){
											Resource resourceChild = toResourceEntity(ripResVo2,dialogueVo);
											resourceChild.setParentResource(resourceEntity);
											resourceEntity.getChildResources().add(resourceChild);
											processedMap.put(ripResVo2.getRossResReqId(), ripResVo2);
										}else{
											// add it as a single
											Resource resourceSingle = toResourceEntity(ripResVo2,dialogueVo);
											processedMap.put(ripResVo2.getRossResReqId(), ripResVo2);
											resources.add(resourceSingle);
										}
									}
								}
							}
	
							*/
							resources.add(resourceEntity);
							
						}
						
					}
					
				}catch(Exception e){
					//System.out.println(e.getMessage());
					// smother for now, remove after development testing
				}
			}
		}

		for(RossImportProcessResourceVo ripResVo : ripResVos){
			if(ripResVo.getRequestNumber().equals("O-9.40")){
				//System.out.println("");
			}

			if(!processedMap.containsKey(ripResVo.getRossResReqId())){
				
				try{
					if(!ripResVo.getExcludeResource() && !LongUtility.hasValue(ripResVo.getEisuiteResourceId())){
						// process any children not yet processed
						if(ripResVo.getRequestNumber().indexOf(".")>0){
							String parentRequestNumber=getParentRequestNumber(ripResVo.getRequestNumber());
								
							// probaly a straggler child resource
							// determine if we have a parent already or import as single
							boolean hasEISuiteParent = false;
							Resource parentResource=new ResourceImpl();
							Collection<IncidentResourceVo> irVos= irDao.getByIncidentId(incidentEntity.getId());
								
								for(IncidentResourceVo irvo : irVos){
									String preqnum=irvo.getWorkPeriodVo().getCurrentAssignmentVo().getRequestNumber();
									if(StringUtility.hasValue(preqnum) && StringUtility.hasValue(parentRequestNumber)){
										if(preqnum.equalsIgnoreCase(parentRequestNumber)){
											hasEISuiteParent=true;
											parentResource.setId(irvo.getResourceVo().getId());
										}
									}
								}
								
								if(hasEISuiteParent==true){
									// if the parent is already in eisuite
									// if not, import as single
									Resource resourceSingle = toResourceEntity(ripResVo,dialogueVo);
									resourceSingle.setParentResource(parentResource);
									processedMap.put(ripResVo.getRossResReqId(), ripResVo);
									resources.add(resourceSingle);
									
								}else{
									// if not, import as single
									Resource resourceSingle = toResourceEntity(ripResVo,dialogueVo);
									processedMap.put(ripResVo.getRossResReqId(), ripResVo);
									resources.add(resourceSingle);
								}
						}
						
					}
				}catch(Exception e){
					
				}
			}
		}

		// update rossresid
		for(RossImportProcessResourceVo ripResVo : ripResVos){
			Resource res = null;
			// is ripResVo in resources collection?
			Boolean inCollection=false;
			Collection<Resource> updatedResources = new ArrayList<Resource>();

			for(Resource r : resources){
				if(LongUtility.hasValue(ripResVo.getEisuiteResourceId())){
					if(ripResVo.getEisuiteResourceId().compareTo(r.getId())==0){
						inCollection=true;
						r.setRossResId(ripResVo.getRossResId());
					}
				}
				
				updatedResources.add(r);
				
				if(inCollection==true){
					break;
				}
			}

			if(inCollection==false){
				if(LongUtility.hasValue(ripResVo.getEisuiteResourceId())){
					Resource r = rDao.getById(ripResVo.getEisuiteResourceId(), ResourceImpl.class);
					r.setRossResId(ripResVo.getRossResId());
					(r.getIncidentResources().iterator().next()).setRossResReqId(ripResVo.getRossResReqId());
					updatedResources.add(r);
				}
			}
			
			resources = updatedResources;
		}
		
		if(resources.size() > 0){
			ResourceDao resourceDao=(ResourceDao)context.getBean("resourceDao");
			try{
				/*
				for(Resource r : resources){
					System.out.println(r.getResourceName());
					System.out.println(r.getLastName());
					resourceDao.save(r);
				}
				*/
				resourceDao.saveAll(resources);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			cnt=resources.size();
		}
		
		return cnt;
		
	}

	private void buildRoster(RossImportProcessResourceVo parentVo
							,Resource parentResource
							,Collection<RossImportProcessResourceVo> ripResVos
							,HashMap<Long,RossImportProcessResourceVo> processedMap
							,Collection<Resource> resources
							,DialogueVo dialogueVo) throws Exception{
	
		String reqNum=parentVo.getRequestNumber();
		if(!StringUtility.hasValue(reqNum))
			return;
		
		for(RossImportProcessResourceVo ripResVo2 : ripResVos){
			if(ripResVo2.getRequestNumber().equals("E-21.4")){
				//System.out.println("");
			}
			
			if(!processedMap.containsKey(ripResVo2.getRossResReqId())){
				if(ripResVo2.getRequestNumber().startsWith(reqNum+".")){
					// make sure its not a child another level in the roster
					String rNum=ripResVo2.getRequestNumber();
					int idx2=rNum.indexOf(reqNum+".");
					String tmp=rNum.substring((reqNum+".").length(),rNum.length());
					idx2=tmp.indexOf(".");
					if(idx2 < 0){
						if(!ripResVo2.getExcludeResource() && !LongUtility.hasValue(ripResVo2.getEisuiteResourceId())){
							if(!ripResVo2.getExcludeFromRoster()){
								Resource resourceChild = toResourceEntity(ripResVo2,dialogueVo);
								processedMap.put(ripResVo2.getRossResReqId(), ripResVo2);
								
								// build roster if available
								this.buildRoster(ripResVo2, resourceChild, ripResVos, processedMap, resources, dialogueVo);
								
								resourceChild.setParentResource(parentResource);
								parentResource.getChildResources().add(resourceChild);
								processedMap.put(ripResVo2.getRossResReqId(), ripResVo2);
							}else{
								// add it as a single
								Resource resourceSingle = toResourceEntity(ripResVo2,dialogueVo);
								processedMap.put(ripResVo2.getRossResReqId(), ripResVo2);
								resources.add(resourceSingle);
							}
						}
					}
					
				}
			}
		}

	}
	
	private String getParentRequestNumber(String childReqNum) {
		String pReqNum="";
		
		if(StringUtility.hasValue(childReqNum)){
			int idx=childReqNum.lastIndexOf(".");
			//int idx=childReqNum.indexOf(".");
			if(idx > 0){
				pReqNum=childReqNum.substring(0,(idx));
			}
		}
		
		return pReqNum;
	}
	
	public Resource buildResource(RossImportProcessResourceVo vo,DialogueVo dialogueVo) throws Exception {
		gcVo = (GlobalCacheVo)context.getBean("globalCacheVo");
		IncidentResourceDao irDao=(IncidentResourceDao)context.getBean("incidentResourceDao");
		
		Resource resource = toResourceEntity(vo,dialogueVo);

		// determine if need to roster the resource
		if(StringUtility.hasValue(this.reimportReqNum)){
			if(this.reimportReqNum.indexOf(".")>0){
				String parentRequestNumber=getParentRequestNumber(this.reimportReqNum);
					
				// probaly a straggler child resource
				// determine if we have a parent already or import as single
				boolean hasEISuiteParent = false;
				Resource parentResource=new ResourceImpl();
				Collection<IncidentResourceVo> irVos= irDao.getByIncidentId(incidentEntity.getId());
					
					for(IncidentResourceVo irvo : irVos){
						String preqnum=irvo.getWorkPeriodVo().getCurrentAssignmentVo().getRequestNumber();
						if(StringUtility.hasValue(preqnum) && StringUtility.hasValue(parentRequestNumber)){
							if(preqnum.equalsIgnoreCase(parentRequestNumber)){
								hasEISuiteParent=true;
								parentResource.setId(irvo.getResourceVo().getId());
							}
						}
					}
					
					if(hasEISuiteParent==true){
						// if the parent is already in eisuite
						// if not, import as single
						resource.setParentResource(parentResource);
						
					}else{
						// if not, import as single
					}
			}
			
		}
		
		return resource;
	}
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private Resource toResourceEntity(RossImportProcessResourceVo vo, DialogueVo dialogueVo) throws Exception {
		Resource resourceEntity = new ResourceImpl();
		
		resourceEntity.setRossResId(vo.getRossResId());
		resourceEntity.setResourceName(StringUtility.leftTrim(vo.getResourceName(),55));
		/*
		if(StringUtility.hasValue(resourceEntity.getResourceName())){
			String name = resourceEntity.getResourceName();
			name=name.replaceAll("&#39;", "'");
			
			resourceEntity.setResourceName(name);
		}
		*/
		
		resourceEntity.setEnabled(Boolean.TRUE);
		
		resourceEntity.setPerson(Boolean.FALSE);

		if(DateUtil.hasValue(vo.getMobEtd())){
			ResourceMobilization rm = new ResourceMobilizationImpl();
			rm.setStartDate(vo.getMobEtd());
			rm.setResource(resourceEntity);
			resourceEntity.setResourceMobilizations(new ArrayList<ResourceMobilization>());
			resourceEntity.getResourceMobilizations().add(rm);
		}
		
		/*
		 * Rule to determine if resource is a person or not:
		 * 	 if req_catalog_name == 'OVERHEAD' and req_category_name = 'POSITIONS' then person
		 */
		String reqCatalogName=(StringUtility.hasValue(vo.getRequestCatalogName()) ? vo.getRequestCatalogName() : "" );
		String reqCategoryName=(StringUtility.hasValue(vo.getRequestCategoryName()) ? vo.getRequestCategoryName() : "" );

		if(reqCatalogName.equals("OVERHEAD") && (reqCategoryName.equals("POSITIONS") ) ){
			// if we have first/last name, use it, otherwise parse resourcename
			if(StringUtility.hasValue(vo.getLastName()) && StringUtility.hasValue(vo.getFirstName())){
				resourceEntity.setLastName(StringUtility.leftTrim(vo.getLastName(),35).trim());
				resourceEntity.setFirstName(StringUtility.leftTrim((vo.getFirstName() + (StringUtility.hasValue(vo.getMiddleName()) ? " " + vo.getMiddleName().trim() : "") ),35).trim());
			}else{
				// try and parse it
				if(StringUtility.hasValue(vo.getResourceName())){
					if(vo.getResourceName().contains(",")){
						String fname = StringUtility.getTokenValue(vo.getResourceName(), ",", 2);
						String lname=StringUtility.getTokenValue(vo.getResourceName(), ",", 1);
						resourceEntity.setLastName(StringUtility.leftTrim(lname,35).trim());
						resourceEntity.setFirstName(StringUtility.leftTrim(fname,35).trim());
					}else{
						resourceEntity.setLastName("UNKNOWN");
						resourceEntity.setFirstName("UNKNOWN");
					}
				}else{
					resourceEntity.setLastName("UNKNOWN");
					resourceEntity.setFirstName("UNKNOWN");
				}
				
			}
			resourceEntity.setPerson(Boolean.TRUE);
		}else{
			// non - person
			resourceEntity.setPerson(Boolean.FALSE);
		}
		
		if(BooleanUtility.isTrue(resourceEntity.isPerson())){
			resourceEntity.setNumberOfPersonnel(1L);
		}		
		
		if(StringUtility.hasValue(vo.getRossGroupAssignment())){
			
			resourceEntity.setRossGroupAssignment(vo.getRossGroupAssignment());
		}
		
		/*
		 * Determine if the resource is contracted
		 */
		resourceEntity.setContracted(Boolean.FALSE);
		
		resourceEntity.setPermanent(Boolean.FALSE);
		resourceEntity.setLeader(Boolean.FALSE);
		
		if(null != vo.getUnitId() && vo.getUnitId()>0){
			OrganizationVo orgVo = OrganizationVo.getById(vo.getUnitId(), gcVo.getOrganizationVos());
			if(null != orgVo){
				resourceEntity.setOrganization(OrganizationVo.toEntity(null, orgVo, false));
				
				if(orgVo.getUnitCode().equals("UNK")){
					Organization pdc = new OrganizationImpl();
					pdc.setId(999998L);
					resourceEntity.setPrimaryDispatchCenter(pdc);
				}else{
					if(CollectionUtility.hasValue(orgVo.getDispatchCenters())){
						OrganizationVo pdcVo = orgVo.getDispatchCenters().iterator().next();
						if(null != pdcVo){
							Organization pdc2 = new OrganizationImpl();
							pdc2.setId(pdcVo.getId());
							resourceEntity.setPrimaryDispatchCenter(pdc2);
						}
					}
				}
			}
		}
		
		if(null != vo.getAgencyId() && vo.getAgencyId()>0){
			AgencyVo agencyVo = AgencyVo.getById(vo.getAgencyId(), gcVo.getAgencyVos());
			if(null != agencyVo)
				resourceEntity.setAgency(AgencyVo.toEntity(null,agencyVo,false));
		}

		IncidentResource incidentResourceEntity = this.toIncidentResource(resourceEntity, vo, dialogueVo);
		
		// add incidentResource to resource
		resourceEntity.getIncidentResources().add(incidentResourceEntity);
		
		return resourceEntity;
	}
	

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public IncidentResource toIncidentResource(Resource resourceEntity, RossImportProcessResourceVo vo, DialogueVo dialogueVo) throws Exception {
		// create new incidentResource instance
		IncidentResource incidentResource = new IncidentResourceImpl();

		// associate incident with incidentResource
		incidentResource.setIncident(incidentEntity);
		
		// associate incidentResource with resource
		incidentResource.setResource(resourceEntity);

		incidentResource.setRossResReqId(vo.getRossResReqId());
		
		incidentResource.setNameAtIncident("");
		
		// associate workPeriod with incidentResource
		incidentResource.setWorkPeriod(toWorkPeriod(incidentResource,vo,dialogueVo));
		
		// associate costdata with incidentResource
		CostData cdEntity = new CostDataImpl();
		cdEntity.setIncidentResource(incidentResource);
		cdEntity.setGenerateCosts(true);
		incidentResource.setCostData(cdEntity);
		
		return incidentResource;
	}

	private WorkPeriod toWorkPeriod(IncidentResource irEntity, RossImportProcessResourceVo vo, DialogueVo dialogueVo) throws Exception {
		WorkPeriod wpEntity = new WorkPeriodImpl();

		// associate incidentResource with workPeriod
		wpEntity.setIncidentResource(irEntity);

		wpEntity.setDMReAssignable(Boolean.FALSE);
		wpEntity.setDMCheckoutFormPrinted(Boolean.FALSE);
		wpEntity.setDMPlanningDispatchNotified(Boolean.FALSE);
		wpEntity.setDMReleaseDispatchNotified(Boolean.FALSE);
		wpEntity.setDMRestOvernight(Boolean.FALSE);
		
		// create new assignment
		Assignment assignment = new AssignmentImpl();
			
		wpEntity.setAssignments(new ArrayList<Assignment>());
		wpEntity.getAssignments().add(toAssignment(wpEntity,vo,dialogueVo));

		if(CollectionUtility.hasValue(irEntity.getResource().getResourceMobilizations()) &&
				irEntity.getResource().getResourceMobilizations().size() > 0){
			ResourceMobilization rm = irEntity.getResource().getResourceMobilizations().iterator().next();
			wpEntity.setCIResourceMobilization(rm);
		}
		
		return wpEntity;
	}

	/**
	 * @param wpEntity
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private Assignment toAssignment(WorkPeriod wpEntity, RossImportProcessResourceVo vo, DialogueVo dialogueVo) throws Exception {
		Assignment assignmentEntity = new AssignmentImpl();

		assignmentEntity.setWorkPeriods(new ArrayList<WorkPeriod>());
		assignmentEntity.getWorkPeriods().add(wpEntity);
		assignmentEntity.setTraining(Boolean.FALSE);
		assignmentEntity.setRequestNumber(vo.getRequestNumber());
		this.reimportReqNum=vo.getRequestNumber();
		assignmentEntity.setAssignmentStatus(AssignmentStatusTypeEnum.F);
		
		if(null != vo.getItemId() && vo.getItemId() > 0){
			KindVo kindVo = KindVo.getById(vo.getItemId(), gcVo.getKindVos());
			if(null != kindVo)
				assignmentEntity.setKind(KindVo.toEntity(null,kindVo, false));
		}else if(StringUtility.hasValue(vo.getItemCode())){
			KindVo kindVo = KindVo.getByCode(vo.getItemCode(),gcVo.getKindVos());
			if(null != kindVo)
				assignmentEntity.setKind(KindVo.toEntity(null, kindVo, false));
		}
		
		if(null==assignmentEntity.getKind()){
			// set to default unknown assignment kind
			// sme's want to still import it with an unknown assignment kind code
			KindVo kindVo = new KindVo();
			kindVo.setId(999999L);
			assignmentEntity.setKind(KindVo.toEntity(null,kindVo,false));
		}
		
		return assignmentEntity;
	}
	
	public static void main(String[] args){
		String reqNum="E-21.4.2";
		int idx=reqNum.indexOf("E-21"+".");
		String tmp=reqNum.substring("E-21.".length(),reqNum.length());
		idx=tmp.indexOf(".");
		if(idx < 0){
			System.out.println(tmp);
		}
	}
}
