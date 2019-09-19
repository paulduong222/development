package gov.nwcg.isuite.core.controllers.restdata;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.vo.AdminOfficeGridVo;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.core.vo.AccrualCodeVo;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.ComplexityVo;
import gov.nwcg.isuite.core.vo.CountryCodeSubdivisionVo;
import gov.nwcg.isuite.core.vo.DailyFormVo;
import gov.nwcg.isuite.core.vo.DepartmentVo;
import gov.nwcg.isuite.core.vo.EventTypeVo;
import gov.nwcg.isuite.core.vo.GroupCategoryVo;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.RateGroupVo;
import gov.nwcg.isuite.core.vo.RecommendationVo;
import gov.nwcg.isuite.core.vo.RegionCodeVo;
import gov.nwcg.isuite.core.vo.RequestCategoryVo;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.core.vo.SubGroupCategoryVo;

public class DropdownData {
	public Long id;
	public String code;
	public String desc;
	public String reftype;
	
	public DropdownData() {}
	public DropdownData(Long id, String code, String description, String reftype) {
		this.id = id;
		this.code = code;
		this.desc = description;
		this.reftype = reftype;
	}

	public static Collection<DropdownData> convertFromAdminOffices(Collection<AdminOfficeGridVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(AdminOfficeGridVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getOfficeName()
				, vo.getCountrySubdivision()
				,"adminoffice"
			));
		}
		
		return list;
	}
	
	public static Collection<DropdownData> convertFromAccrualCodeVos(Collection<AccrualCodeVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(AccrualCodeVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				, vo.getCode()
				, vo.getDescription()
				,"accrualcode"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromIncidents(Collection<Incident> entities ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(Incident entity : entities){
			list.add(new DropdownData(
				entity.getId()
				,entity.getIncidentName()
				, ""
				,"incident"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromIncidentAccountCode(Collection<IncidentAccountCode> entities ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(IncidentAccountCode entity : entities){
			list.add(new DropdownData(
				entity.getId()
				,entity.getAccountCode().getAccountCode()
				, entity.getIncident().getIncidentName()
				,"incidentaccountcode"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromOrgs(Collection<OrganizationVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(OrganizationVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getUnitCode()
				,vo.getName()
				,"org"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromEventTypes(Collection<EventTypeVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(EventTypeVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getEventTypeCd()
				,vo.getType()
				,"eventtype"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromAgencies(Collection<AgencyVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(AgencyVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getAgencyCd()
				,vo.getAgencyNm()
				,"agencytype"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromJetports(Collection<JetPortVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(JetPortVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCode()
				,vo.getDescription()
				,"jetporttype"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromKinds(Collection<KindVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(KindVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCode()
				,vo.getDescription()
				,"kindtype"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromAgencyGroups(Collection<AgencyGroupVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(AgencyGroupVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCode()
				,vo.getDescription()
				,"agencygrouptype"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromCountryCodeSubdivisions(Collection<CountryCodeSubdivisionVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(CountryCodeSubdivisionVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCountrySubAbbreviation()
				,vo.getCountrySubName()
				,"countrycodesubdivsion"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromRegionCodes(Collection<RegionCodeVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(RegionCodeVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCode()
				,vo.getDescription()
				,"regioncode"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromRateGroups(Collection<RateGroupVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(RateGroupVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCode()
				,vo.getDescription()
				,"rategroup"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromDailyForms(Collection<DailyFormVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(DailyFormVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCode()
				,vo.getDescription()
				,"dailyforms"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromDepartments(Collection<DepartmentVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(DepartmentVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCode()
				,vo.getDescription()
				,"departments"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromSubGroupCategories(Collection<SubGroupCategoryVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(SubGroupCategoryVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCode()
				,vo.getDescription()
				,"subgroupcategories"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromGroupCategories(Collection<GroupCategoryVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(GroupCategoryVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCode()
				,vo.getDescription()
				,"groupcategories"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromSit209s(Collection<Sit209Vo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(Sit209Vo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCode()
				,vo.getDescription()
				,"sit209s"
			));
		}
		
		return list;
	}

	public static Collection<DropdownData> convertFromRequestCategories(Collection<RequestCategoryVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(RequestCategoryVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCode()
				,vo.getDescription()
				,"requestcategories"
			));
		}
		
		return list;
	}
	
	public static Collection<DropdownData> convertFromRecommendations(Collection<RecommendationVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(RecommendationVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCode()
				,vo.getDescription()
				,"recommendations"
			));
		}
		
		return list;
	}
	
	public static Collection<DropdownData> convertFromComplexities(Collection<ComplexityVo> vos ){
		Collection<DropdownData> list = new ArrayList<DropdownData>();
		
		for(ComplexityVo vo : vos){
			list.add(new DropdownData(
				vo.getId()
				,vo.getCode()
				,vo.getDescription()
				,"complexities"
			));
		}
		
		return list;
	}
}
