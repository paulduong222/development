package gov.nwcg.isuite.core.controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.DropdownData;
import gov.nwcg.isuite.core.controllers.restdata.KindSubData;
import gov.nwcg.isuite.core.persistence.AccrualCodeDao;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.service.AgencyService2;
import gov.nwcg.isuite.core.service.IncidentGroupService2;
import gov.nwcg.isuite.core.service.IncidentService2;
import gov.nwcg.isuite.core.service.JetportService2;
import gov.nwcg.isuite.core.service.KindService2;
import gov.nwcg.isuite.core.service.OrganizationService2;
import gov.nwcg.isuite.core.service.SystemService;
import gov.nwcg.isuite.core.vo.AccrualCodeVo;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResourceReferenceDataVo;
import gov.nwcg.isuite.core.vo.ResourceTimeReferenceDataVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.IsuiteException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

@Controller
@RequestMapping("/refdata")
public class ReferenceDataController extends BaseRestController {

	@Autowired
	private SystemService service;

	@Autowired
	private AgencyService2 agencyService2;
	@Autowired
	private JetportService2 jetportService2;
	@Autowired
	private KindService2 kindService2;
	@Autowired
	private OrganizationService2 orgService2;
	@Autowired
	private IncidentGroupService2 incidentGroupService2;
	@Autowired
	private IncidentService2 incidentService2;
	@Autowired
	private AccrualCodeDao accrualCodeDao;
	
	@RequestMapping(value = "/standardOrgs", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getStandardOrgs() throws IsuiteException {
		GlobalCacheVo vo = service.getGlobalCache();
		Collection<OrganizationVo> returnOrgs = new ArrayList<OrganizationVo>();
		for(OrganizationVo org : vo.getOrganizationVos()) {
			if(BooleanUtility.isTrue(org.getStandard()) && BooleanUtility.isFalse(org.getDispatchCenter())){
				returnOrgs.add(org);
			}
		}
		
		DialogueVo dvo = new DialogueVo();
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_ORGS");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dvo.setCourseOfActionVo(coaVo);
		dvo.setRecordset(returnOrgs);
//		dvo.setRecordset(DropdownData.convertFromOrgs(vo.getOrganizationVos()));
		return dvo;
	}
	
	@RequestMapping(value = "/orgtypes", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getOrgTypes() throws IsuiteException {
		GlobalCacheVo vo = service.getGlobalCache();
		Collection<OrganizationVo> returnOrgs = new ArrayList<OrganizationVo>();
		for(OrganizationVo org : vo.getOrganizationVos()) {
			if(BooleanUtility.isTrue(org.getStandard()) && BooleanUtility.isFalse(org.getDispatchCenter())){
				returnOrgs.add(org);
			}
		}
		
		DialogueVo dvo = new DialogueVo();
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_ORG_TYPES");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dvo.setCourseOfActionVo(coaVo);
		dvo.setRecordset(DropdownData.convertFromOrgs(vo.getOrganizationVos()));
		return dvo;
	}

	@RequestMapping(value = "/pdcs", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getPdcs() throws IsuiteException {
		GlobalCacheVo vo = service.getGlobalCache();
		DialogueVo dvo = new DialogueVo();
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_PDCS");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dvo.setCourseOfActionVo(coaVo);
		dvo.setRecordset(DropdownData.convertFromOrgs(vo.getPdcVos()));
		return dvo;
	}
	
	@RequestMapping(value = "/eventtypes", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getEventTypes() throws IsuiteException {
		GlobalCacheVo vo = service.getGlobalCache();
		DialogueVo dvo = new DialogueVo();
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_EVENT_TYPES");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dvo.setCourseOfActionVo(coaVo);
		dvo.setRecordset(DropdownData.convertFromEventTypes(vo.getEventTypeVos()));
		return dvo;
	}

	@RequestMapping(value = "/agencytypes", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getAgencyTypes() throws IsuiteException {
		GlobalCacheVo vo = service.getGlobalCache();
		DialogueVo dvo = new DialogueVo();
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_AGENCY_TYPES");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dvo.setCourseOfActionVo(coaVo);
		dvo.setRecordset(DropdownData.convertFromAgencies(vo.getAgencyVos()));
		return dvo;
	}

	@RequestMapping(value = "/agencygrouptypes", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getAgencyGroupTypes() throws IsuiteException {
		GlobalCacheVo vo = service.getGlobalCache();
		DialogueVo dvo = new DialogueVo();
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_AGENCY_GROUP_TYPES");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dvo.setCourseOfActionVo(coaVo);
		dvo.setRecordset(DropdownData.convertFromAgencyGroups(vo.getAgencyGroupVos()));
		return dvo;
	}

	@RequestMapping(value = "/states", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getStates() throws IsuiteException {
		GlobalCacheVo vo = service.getGlobalCache();
		DialogueVo dvo = new DialogueVo();
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_STATES");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dvo.setCourseOfActionVo(coaVo);
		dvo.setRecordset(DropdownData.convertFromCountryCodeSubdivisions(vo.getCountryCodeSubdivisionVos()));
		return dvo;
	}

	@RequestMapping(value = "/regions", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getRegions() throws IsuiteException {
		GlobalCacheVo vo = service.getGlobalCache();
		DialogueVo dvo = new DialogueVo();
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_REGIONS");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dvo.setCourseOfActionVo(coaVo);
		dvo.setRecordset(DropdownData.convertFromRegionCodes(vo.getRegionCodeVos()));
		return dvo;
	}

	@RequestMapping(value = "/rategrouptypes", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getRateGroupTypes() throws IsuiteException {
		GlobalCacheVo vo = service.getGlobalCache();
		DialogueVo dvo = new DialogueVo();
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_RATE_GROUP_TYPES");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dvo.setCourseOfActionVo(coaVo);
		dvo.setRecordset(DropdownData.convertFromRateGroups(vo.getRateGroupVos()));
		return dvo;
	}

	@RequestMapping(value = "/kindsubtypes", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getKindSubTypes() throws IsuiteException {
		GlobalCacheVo vo = service.getGlobalCache();
		DialogueVo dvo = new DialogueVo();
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_KIND_SUB_TYPES");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
		dvo.setCourseOfActionVo(coaVo);
		
		KindSubData data = new KindSubData();
		data.setDailyFormTypeData(DropdownData.convertFromDailyForms(vo.getDailyFormVos()));
		data.setDepartmentTypeData(DropdownData.convertFromDepartments(vo.getDepartmentVos()));
		data.setSit209TypeData(DropdownData.convertFromSit209s(vo.getSit209CodeVos()));
		data.setSubGroupCategoryTypeData(DropdownData.convertFromSubGroupCategories(vo.getSubGroupCategoryVos()));
		data.setGroupCategoryTypeData(DropdownData.convertFromGroupCategories(vo.getGroupCategoryVos()));
		data.setRequestCategoryTypeData(DropdownData.convertFromRequestCategories(vo.getRequestCategoryVos()));
		
		dvo.setResultObject(data);
		return dvo;
	}
	
	@RequestMapping(value = "/recommendations", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getRecommendations() throws IsuiteException {
		GlobalCacheVo vo = service.getGlobalCache();
		DialogueVo dvo = new DialogueVo();
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_RECOMMENDATIONS");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dvo.setCourseOfActionVo(coaVo);
		dvo.setRecordset(DropdownData.convertFromRecommendations(vo.getRecommendationVos()));
		return dvo;
	}
	
	@RequestMapping(value = "/complexities", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getComplexities() throws IsuiteException {
		GlobalCacheVo vo = service.getGlobalCache();
		DialogueVo dvo = new DialogueVo();
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_COMPLEXITIES");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dvo.setCourseOfActionVo(coaVo);
		dvo.setRecordset(DropdownData.convertFromComplexities(vo.getComplexityVos()));
		return dvo;
	}

	@RequestMapping(value = "/resourceRefData", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getResourceReferenceData(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId
			) throws IsuiteException {
		
		ResourceReferenceDataVo vo = new ResourceReferenceDataVo();
		
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_RESOURCE_REF_DATA");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);

		DialogueVo dvo = this.agencyService2.getGridIncidentorGroup(incidentId, incidentGroupId, false, null);
		if ( null != dvo && CollectionUtility.hasValue(dvo.getRecordset())){
			Collection<AgencyVo> agencies = (Collection<AgencyVo>)dvo.getRecordset();
			vo.setAgencyDropdownData(DropdownData.convertFromAgencies(agencies));
		}

		dvo = this.jetportService2.getGridIncidentorGroup(incidentId, incidentGroupId, false, null);
		if ( null != dvo && CollectionUtility.hasValue(dvo.getRecordset())){
			Collection<JetPortVo> jetports = (Collection<JetPortVo>)dvo.getRecordset();
			vo.setJetportDropdownData(DropdownData.convertFromJetports(jetports));
		}

		dvo = this.kindService2.getGridIncidentorGroup(incidentId, incidentGroupId, false, null);
		if ( null != dvo && CollectionUtility.hasValue(dvo.getRecordset())){
			Collection<KindVo> kinds = (Collection<KindVo>)dvo.getRecordset();
			vo.setKindDropdownData(DropdownData.convertFromKinds(kinds));
			vo.setKindVos(kinds);
		}
		
		dvo = this.orgService2.getGridIncidentorGroup(incidentId, incidentGroupId, false, null);
		if ( null != dvo && CollectionUtility.hasValue(dvo.getRecordset())){
			Collection<OrganizationVo> orgs = (Collection<OrganizationVo>)dvo.getRecordset();
			vo.setUnitDropdownData(DropdownData.convertFromOrgs(orgs));
		}

		dvo = this.incidentService2.getIncidentAccountCodeDropdownList(incidentId, incidentGroupId, null);
		if ( null != dvo && CollectionUtility.hasValue(dvo.getRecordset())){
			vo.setIncidentAccountCodeData((Collection<DropdownData>)dvo.getRecordset());
		}

		if (LongUtility.hasValue(incidentId)){
			
		} else if (LongUtility.hasValue(incidentGroupId)){
			dvo = this.incidentGroupService2.getIncidentDropdownList(incidentGroupId, null);
			if ( null != dvo && CollectionUtility.hasValue(dvo.getRecordset())){
				Collection<DropdownData> list = (Collection<DropdownData>)dvo.getRecordset();
				vo.setIncidentData(list);
			}
		}

		Collection<AccrualCodeVo> accrualCodeVos = this.accrualCodeDao.getPicklist();
		if ( CollectionUtility.hasValue(accrualCodeVos)) {
			Collection<DropdownData> list = DropdownData.convertFromAccrualCodeVos(accrualCodeVos);
			vo.setAccrualCodeData(list);
		}

		vo.setSpecialPayVos(super.getGlobalCacheVo().getSpecialPayVos());
		try { 
			vo.setRateClassRateVos(service.getRateClassRates());
		} catch (Exception e){}

		// get contractor list
		ContractorDao contractorDao = (ContractorDao)context.getBean("contractorDao");
		
		Long groupId = ( LongUtility.hasValue(incidentGroupId) ? incidentGroupId: null);
		
		if ( !LongUtility.hasValue(groupId) && LongUtility.hasValue(incidentId)){
			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			groupId = incidentDao.getIncidentGroupId(incidentId);
		}
		Collection<ContractorVo> contractorVos = contractorDao.getLightList(null, groupId);		
		vo.setContractorVos(contractorVos);

		dvo.setCourseOfActionVo(coaVo);
		dvo.setRecordset(null);
		dvo.setResultObject(vo);
		return dvo;
	}
	
	@RequestMapping(value = "/resourceTimeRefData", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getResourceTimeReferenceData() throws IsuiteException {
		
		ResourceTimeReferenceDataVo vo = new ResourceTimeReferenceDataVo();
		
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("GET_RESOURCE_TIME_REF_DATA");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);

		DialogueVo dvo = new DialogueVo();
		
		vo.setSpecialPayVos(super.getGlobalCacheVo().getSpecialPayVos());
		try { 
			vo.setRateClassRateVos(service.getRateClassRates());
		} catch (Exception e){}

		dvo.setCourseOfActionVo(coaVo);
		dvo.setRecordset(null);
		dvo.setResultObject(vo);
		return dvo;
	}

}	

