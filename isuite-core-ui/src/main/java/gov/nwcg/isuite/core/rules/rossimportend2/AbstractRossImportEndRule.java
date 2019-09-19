package gov.nwcg.isuite.core.rules.rossimportend2;

import gov.nwcg.isuite.core.domain.AccrualCode;
import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.impl.AccrualCodeImpl;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
import gov.nwcg.isuite.core.domain.impl.JetPortImpl;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.vo.AccrualCodeVo;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.KindAltDescVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.RossXmlFileVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossImportVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class AbstractRossImportEndRule extends AbstractRule {
	protected RossImportVo rossImportVo;
	protected RossXmlFileVo rxfVo;
	protected UserVo userVo;
	protected String nextProcess;
	protected GlobalCacheVo gcVo;
	
	public AbstractRossImportEndRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(RossImportProcessEndRuleFactory2.ObjectTypeEnum.ROSS_XML_FILE_VO.name()))
			rxfVo = (RossXmlFileVo)object;
		if(objectName.equals(RossImportProcessEndRuleFactory2.ObjectTypeEnum.ROSS_IMPORT_VO.name()))
			rossImportVo = (RossImportVo)object;
		if(objectName.equals(RossImportProcessEndRuleFactory2.ObjectTypeEnum.USER_VO.name()))
			userVo = (UserVo)object;
		if(objectName.equals(RossImportProcessEndRuleFactory2.ObjectTypeEnum.NEXT_PROCESS.name()))
			nextProcess = (String)object;
	}
	
	protected Agency getAgency(String agencyCode, Organization org) {
		
		if(StringUtility.hasValue(agencyCode)){
			for(AgencyVo vo : gcVo.getAgencyVos()){
				if(vo.getAgencyCd().equalsIgnoreCase(agencyCode)){
					Agency agency = new AgencyImpl();
					agency.setId(vo.getId());
					return agency;
				}
			}
		}

		// if no agency match from above, try to derive the agency by 2 letter state in org unit code
		if(null != org){
			String unitCode=org.getUnitCode();
			if(StringUtility.hasValue(unitCode) && !unitCode.equalsIgnoreCase("UNK") && unitCode.length()>1){
				String stateCode=unitCode.substring(0, 2);
				for(AgencyVo vo : gcVo.getAgencyVos()){
					if(BooleanUtility.isTrue(vo.getStandard())){
						if(vo.getAgencyCd().equalsIgnoreCase(stateCode)){
							Agency agency = new AgencyImpl();
							agency.setId(vo.getId());
							return agency;
						}
					}
				}
			}
		}
		
		//Agency agency = new AgencyImpl();
		//agency.setId(999999L);
		//return agency;
		return null;
	}

	protected Kind getKind(String code,String itemName, Collection<KindAltDescVo> kindAltDescVos) {
		
		if(StringUtility.hasValue(code)){
			// check by code
			for(KindVo vo : gcVo.getKindVos()){
				if(vo.getCode().equalsIgnoreCase(code)){
					Kind kind = new KindImpl();
					kind.setId(vo.getId());
					return kind;
				}
			}
		}

		if(StringUtility.hasValue(itemName)){
			// check by desc
			for(KindVo vo : gcVo.getKindVos()){
				if(vo.getDescription().equalsIgnoreCase(itemName)){
					Kind kind = new KindImpl();
					kind.setId(vo.getId());
					return kind;
				}
			}
			
			for(KindAltDescVo kadVo : kindAltDescVos) {
				if(kadVo.getDescription().equalsIgnoreCase(itemName)){
					Kind kind = new KindImpl();
					kind.setId(kadVo.getKindVo().getId());
					return kind;
				}
			}
		}
		
		Kind kind = new KindImpl();
		for(KindVo vo : gcVo.getKindVos()){
			if(vo.getCode().equals("UNK") && BooleanUtility.isTrue(vo.isStandard())){
				kind.setId(vo.getId());
				return kind;
			}
		}

		return null;
	}

	protected Organization getHomeUnit(String homeUnitCode) {

		if(StringUtility.hasValue(homeUnitCode)){
			for(OrganizationVo vo : gcVo.getOrganizationVos()){
				if(BooleanUtility.isFalse(vo.getDispatchCenter())){
					if(vo.getUnitCode().equalsIgnoreCase(homeUnitCode)){
						Organization org = new OrganizationImpl();
						org.setId(vo.getId());
						org.setUnitCode(vo.getUnitCode());
						return org;
					}
				}
			}
		}

		// default to UNK
		for(OrganizationVo vo : gcVo.getOrganizationVos()){
			if(BooleanUtility.isFalse(vo.getDispatchCenter())){
				if(vo.getUnitCode().equalsIgnoreCase("UNK")){
					Organization org = new OrganizationImpl();
					org.setId(vo.getId());
					org.setUnitCode(vo.getUnitCode());
					return org;
				}
			}
		}

		return null;
	}

	protected Organization getPdc(String homeUnitCode) {
		
		for(OrganizationVo vo : gcVo.getOrganizationVos()){
			if(vo.getUnitCode().equalsIgnoreCase(homeUnitCode)){
				if(CollectionUtility.hasValue(vo.getDispatchCenters())){
					OrganizationVo pdcVo = vo.getDispatchCenters().iterator().next();
					if(null != pdcVo){
						Organization pdc2 = new OrganizationImpl();
						pdc2.setId(pdcVo.getId());
						return pdc2;
					}
				}
			}
		}
		
		// default to UNK
		for(OrganizationVo vo : gcVo.getOrganizationVos()){
			if(BooleanUtility.isTrue(vo.getDispatchCenter())){
				if(vo.getUnitCode().equalsIgnoreCase("UNK")){
					Organization org = new OrganizationImpl();
					org.setId(vo.getId());
					return org;
				}
			}
		}
	
		return null;
	}

	protected JetPort getJetPort(String code) {
		
		for(JetPortVo vo : gcVo.getJetPortVos()){
			if(vo.getCode().equalsIgnoreCase(code)){
				JetPort jp = new JetPortImpl();
				jp.setId(vo.getId());
				return jp;
			}
		}
	
		return null;
	}

	protected AccrualCode getDefaultAccrualCode() {
		
		for(AccrualCodeVo vo : gcVo.getAccrualCodeVos()){
			if(vo.getCode().equalsIgnoreCase("EXCL")){
				AccrualCode ac = new AccrualCodeImpl();
				ac.setId(vo.getId());
				return ac;
			}
		}
	
		return null;
	}

	protected String getJetPortCode(String rossJetPort){
		String code="";
		
		if(StringUtility.hasValue(rossJetPort)){
			// extract the airport code from parenthesis
			int firstP=rossJetPort.indexOf("(");
			if(firstP > -1){
				String tmp=rossJetPort.substring(firstP+1, rossJetPort.length());
				int secondP=tmp.indexOf(")");
				if(secondP > -1)
					code=tmp.substring(0,secondP);
			}
		}
		
		return code;
	}
	
	public static void main(String[] args){
		AbstractRossImportEndRule test = new AbstractRossImportEndRule(null,"");
		String t=test.getJetPortCode("salt lake city (SLC)");
		System.out.println(t);
	}
}
