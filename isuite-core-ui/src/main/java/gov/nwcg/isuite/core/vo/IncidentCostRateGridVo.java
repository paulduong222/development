package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.filter.CostRateGridFilter;
import gov.nwcg.isuite.core.filter.impl.CostRateGridFilterImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.StateEnumType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

public class IncidentCostRateGridVo extends AbstractVo{
	private Long incidentId;
	private Long itemId;
	private String itemCode;
	private String itemDescription;
	private String requestCategory;
	private BigDecimal fedRate;
	private BigDecimal stateRate;
	private BigDecimal contractorRate;
	private String customStateRates;
	private BigDecimal akRate;
	private BigDecimal alRate;
	private BigDecimal arRate;
	private BigDecimal azRate;
	private BigDecimal caRate;
	private BigDecimal coRate;
	private BigDecimal ctRate;
	private BigDecimal deRate;
	private BigDecimal flRate;
	private BigDecimal gaRate;
	private BigDecimal hiRate;
	private BigDecimal iaRate;
	private BigDecimal idRate;
	private BigDecimal ilRate;
	private BigDecimal inRate;
	private BigDecimal ksRate;
	private BigDecimal kyRate;
	private BigDecimal laRate;
	private BigDecimal maRate;
	private BigDecimal mdRate;
	private BigDecimal meRate;
	private BigDecimal miRate;
	private BigDecimal mnRate;
	private BigDecimal moRate;
	private BigDecimal msRate;
	private BigDecimal mtRate;
	private BigDecimal ncRate;
	private BigDecimal ndRate;
	private BigDecimal neRate;
	private BigDecimal nhRate;
	private BigDecimal njRate;
	private BigDecimal nmRate;
	private BigDecimal nvRate;
	private BigDecimal nyRate;
	private BigDecimal ohRate;
	private BigDecimal okRate;
	private BigDecimal orRate;
	private BigDecimal paRate;
	private BigDecimal riRate;
	private BigDecimal scRate;
	private BigDecimal sdRate;
	private BigDecimal tnRate;
	private BigDecimal txRate;
	private BigDecimal utRate;
	private BigDecimal vaRate;
	private BigDecimal vtRate;
	private BigDecimal waRate;
	private BigDecimal wiRate;
	private BigDecimal wvRate;
	private BigDecimal wyRate;
	
	public IncidentCostRateGridVo(){
		super();
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the itemDescription
	 */
	public String getItemDescription() {
		return itemDescription;
	}

	/**
	 * @param itemDescription the itemDescription to set
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	/**
	 * @return the requestCategory
	 */
	public String getrequestCategory() {
		return requestCategory;
	}

	/**
	 * @param requestCategory the requestCategory to set
	 */
	public void setrequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}

	/**
	 * @return the fedRate
	 */
	public BigDecimal getFedRate() {
		return fedRate;
	}

	/**
	 * @param fedRate the fedRate to set
	 */
	public void setFedRate(BigDecimal fedRate) {
		this.fedRate = fedRate;
	}

	/**
	 * @return the stateRate
	 */
	public BigDecimal getStateRate() {
		return stateRate;
	}

	/**
	 * @param stateRate the stateRate to set
	 */
	public void setStateRate(BigDecimal stateRate) {
		this.stateRate = stateRate;
	}

	/**
	 * @return the contractorRate
	 */
	public BigDecimal getContractorRate() {
		return contractorRate;
	}

	/**
	 * @param contractorRate the contractorRate to set
	 */
	public void setContractorRate(BigDecimal contractorRate) {
		this.contractorRate = contractorRate;
	}

	/**
	 * @return the akRate
	 */
	public BigDecimal getAkRate() {
		return akRate;
	}

	/**
	 * @param akRate the akRate to set
	 */
	public void setAkRate(BigDecimal akRate) {
		this.akRate = akRate;
	}

	/**
	 * @return the alRate
	 */
	public BigDecimal getAlRate() {
		return alRate;
	}

	/**
	 * @param alRate the alRate to set
	 */
	public void setAlRate(BigDecimal alRate) {
		this.alRate = alRate;
	}

	/**
	 * @return the arRate
	 */
	public BigDecimal getArRate() {
		return arRate;
	}

	/**
	 * @param arRate the arRate to set
	 */
	public void setArRate(BigDecimal arRate) {
		this.arRate = arRate;
	}

	/**
	 * @return the azRate
	 */
	public BigDecimal getAzRate() {
		return azRate;
	}

	/**
	 * @param azRate the azRate to set
	 */
	public void setAzRate(BigDecimal azRate) {
		this.azRate = azRate;
	}

	/**
	 * @return the caRate
	 */
	public BigDecimal getCaRate() {
		return caRate;
	}

	/**
	 * @param caRate the caRate to set
	 */
	public void setCaRate(BigDecimal caRate) {
		this.caRate = caRate;
	}

	/**
	 * @return the coRate
	 */
	public BigDecimal getCoRate() {
		return coRate;
	}

	/**
	 * @param coRate the coRate to set
	 */
	public void setCoRate(BigDecimal coRate) {
		this.coRate = coRate;
	}

	/**
	 * @return the itemId
	 */
	public Long getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the requestCategory
	 */
	public String getRequestCategory() {
		return requestCategory;
	}

	/**
	 * @param requestCategory the requestCategory to set
	 */
	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the ctRate
	 */
	public BigDecimal getCtRate() {
		return ctRate;
	}

	/**
	 * @param ctRate the ctRate to set
	 */
	public void setCtRate(BigDecimal ctRate) {
		this.ctRate = ctRate;
	}

	/**
	 * @return the deRate
	 */
	public BigDecimal getDeRate() {
		return deRate;
	}

	/**
	 * @param deRate the deRate to set
	 */
	public void setDeRate(BigDecimal deRate) {
		this.deRate = deRate;
	}

	/**
	 * @return the flRate
	 */
	public BigDecimal getFlRate() {
		return flRate;
	}

	/**
	 * @param flRate the flRate to set
	 */
	public void setFlRate(BigDecimal flRate) {
		this.flRate = flRate;
	}

	/**
	 * @return the gaRate
	 */
	public BigDecimal getGaRate() {
		return gaRate;
	}

	/**
	 * @param gaRate the gaRate to set
	 */
	public void setGaRate(BigDecimal gaRate) {
		this.gaRate = gaRate;
	}

	/**
	 * @return the hiRate
	 */
	public BigDecimal getHiRate() {
		return hiRate;
	}

	/**
	 * @param hiRate the hiRate to set
	 */
	public void setHiRate(BigDecimal hiRate) {
		this.hiRate = hiRate;
	}

	/**
	 * @return the iaRate
	 */
	public BigDecimal getIaRate() {
		return iaRate;
	}

	/**
	 * @param iaRate the iaRate to set
	 */
	public void setIaRate(BigDecimal iaRate) {
		this.iaRate = iaRate;
	}

	/**
	 * @return the idRate
	 */
	public BigDecimal getIdRate() {
		return idRate;
	}

	/**
	 * @param idRate the idRate to set
	 */
	public void setIdRate(BigDecimal idRate) {
		this.idRate = idRate;
	}

	/**
	 * @return the ilRate
	 */
	public BigDecimal getIlRate() {
		return ilRate;
	}

	/**
	 * @param ilRate the ilRate to set
	 */
	public void setIlRate(BigDecimal ilRate) {
		this.ilRate = ilRate;
	}

	/**
	 * @return the inRate
	 */
	public BigDecimal getInRate() {
		return inRate;
	}

	/**
	 * @param inRate the inRate to set
	 */
	public void setInRate(BigDecimal inRate) {
		this.inRate = inRate;
	}

	/**
	 * @return the ksRate
	 */
	public BigDecimal getKsRate() {
		return ksRate;
	}

	/**
	 * @param ksRate the ksRate to set
	 */
	public void setKsRate(BigDecimal ksRate) {
		this.ksRate = ksRate;
	}

	/**
	 * @return the kyRate
	 */
	public BigDecimal getKyRate() {
		return kyRate;
	}

	/**
	 * @param kyRate the kyRate to set
	 */
	public void setKyRate(BigDecimal kyRate) {
		this.kyRate = kyRate;
	}

	/**
	 * @return the laRate
	 */
	public BigDecimal getLaRate() {
		return laRate;
	}

	/**
	 * @param laRate the laRate to set
	 */
	public void setLaRate(BigDecimal laRate) {
		this.laRate = laRate;
	}

	/**
	 * @return the maRate
	 */
	public BigDecimal getMaRate() {
		return maRate;
	}

	/**
	 * @param maRate the maRate to set
	 */
	public void setMaRate(BigDecimal maRate) {
		this.maRate = maRate;
	}

	/**
	 * @return the mdRate
	 */
	public BigDecimal getMdRate() {
		return mdRate;
	}

	/**
	 * @param mdRate the mdRate to set
	 */
	public void setMdRate(BigDecimal mdRate) {
		this.mdRate = mdRate;
	}

	/**
	 * @return the meRate
	 */
	public BigDecimal getMeRate() {
		return meRate;
	}

	/**
	 * @param meRate the meRate to set
	 */
	public void setMeRate(BigDecimal meRate) {
		this.meRate = meRate;
	}

	/**
	 * @return the miRate
	 */
	public BigDecimal getMiRate() {
		return miRate;
	}

	/**
	 * @param miRate the miRate to set
	 */
	public void setMiRate(BigDecimal miRate) {
		this.miRate = miRate;
	}

	/**
	 * @return the mnRate
	 */
	public BigDecimal getMnRate() {
		return mnRate;
	}

	/**
	 * @param mnRate the mnRate to set
	 */
	public void setMnRate(BigDecimal mnRate) {
		this.mnRate = mnRate;
	}

	/**
	 * @return the moRate
	 */
	public BigDecimal getMoRate() {
		return moRate;
	}

	/**
	 * @param moRate the moRate to set
	 */
	public void setMoRate(BigDecimal moRate) {
		this.moRate = moRate;
	}

	/**
	 * @return the msRate
	 */
	public BigDecimal getMsRate() {
		return msRate;
	}

	/**
	 * @param msRate the msRate to set
	 */
	public void setMsRate(BigDecimal msRate) {
		this.msRate = msRate;
	}

	/**
	 * @return the mtRate
	 */
	public BigDecimal getMtRate() {
		return mtRate;
	}

	/**
	 * @param mtRate the mtRate to set
	 */
	public void setMtRate(BigDecimal mtRate) {
		this.mtRate = mtRate;
	}

	/**
	 * @return the ncRate
	 */
	public BigDecimal getNcRate() {
		return ncRate;
	}

	/**
	 * @param ncRate the ncRate to set
	 */
	public void setNcRate(BigDecimal ncRate) {
		this.ncRate = ncRate;
	}

	/**
	 * @return the ndRate
	 */
	public BigDecimal getNdRate() {
		return ndRate;
	}

	/**
	 * @param ndRate the ndRate to set
	 */
	public void setNdRate(BigDecimal ndRate) {
		this.ndRate = ndRate;
	}

	/**
	 * @return the neRate
	 */
	public BigDecimal getNeRate() {
		return neRate;
	}

	/**
	 * @param neRate the neRate to set
	 */
	public void setNeRate(BigDecimal neRate) {
		this.neRate = neRate;
	}

	/**
	 * @return the nhRate
	 */
	public BigDecimal getNhRate() {
		return nhRate;
	}

	/**
	 * @param nhRate the nhRate to set
	 */
	public void setNhRate(BigDecimal nhRate) {
		this.nhRate = nhRate;
	}

	/**
	 * @return the njRate
	 */
	public BigDecimal getNjRate() {
		return njRate;
	}

	/**
	 * @param njRate the njRate to set
	 */
	public void setNjRate(BigDecimal njRate) {
		this.njRate = njRate;
	}

	/**
	 * @return the nmRate
	 */
	public BigDecimal getNmRate() {
		return nmRate;
	}

	/**
	 * @param nmRate the nmRate to set
	 */
	public void setNmRate(BigDecimal nmRate) {
		this.nmRate = nmRate;
	}

	/**
	 * @return the nvRate
	 */
	public BigDecimal getNvRate() {
		return nvRate;
	}

	/**
	 * @param nvRate the nvRate to set
	 */
	public void setNvRate(BigDecimal nvRate) {
		this.nvRate = nvRate;
	}

	/**
	 * @return the nyRate
	 */
	public BigDecimal getNyRate() {
		return nyRate;
	}

	/**
	 * @param nyRate the nyRate to set
	 */
	public void setNyRate(BigDecimal nyRate) {
		this.nyRate = nyRate;
	}

	/**
	 * @return the ohRate
	 */
	public BigDecimal getOhRate() {
		return ohRate;
	}

	/**
	 * @param ohRate the ohRate to set
	 */
	public void setOhRate(BigDecimal ohRate) {
		this.ohRate = ohRate;
	}

	/**
	 * @return the okRate
	 */
	public BigDecimal getOkRate() {
		return okRate;
	}

	/**
	 * @param okRate the okRate to set
	 */
	public void setOkRate(BigDecimal okRate) {
		this.okRate = okRate;
	}

	/**
	 * @return the orRate
	 */
	public BigDecimal getOrRate() {
		return orRate;
	}

	/**
	 * @param orRate the orRate to set
	 */
	public void setOrRate(BigDecimal orRate) {
		this.orRate = orRate;
	}

	/**
	 * @return the paRate
	 */
	public BigDecimal getPaRate() {
		return paRate;
	}

	/**
	 * @param paRate the paRate to set
	 */
	public void setPaRate(BigDecimal paRate) {
		this.paRate = paRate;
	}

	/**
	 * @return the riRate
	 */
	public BigDecimal getRiRate() {
		return riRate;
	}

	/**
	 * @param riRate the riRate to set
	 */
	public void setRiRate(BigDecimal riRate) {
		this.riRate = riRate;
	}

	/**
	 * @return the scRate
	 */
	public BigDecimal getScRate() {
		return scRate;
	}

	/**
	 * @param scRate the scRate to set
	 */
	public void setScRate(BigDecimal scRate) {
		this.scRate = scRate;
	}

	/**
	 * @return the sdRate
	 */
	public BigDecimal getSdRate() {
		return sdRate;
	}

	/**
	 * @param sdRate the sdRate to set
	 */
	public void setSdRate(BigDecimal sdRate) {
		this.sdRate = sdRate;
	}

	/**
	 * @return the tnRate
	 */
	public BigDecimal getTnRate() {
		return tnRate;
	}

	/**
	 * @param tnRate the tnRate to set
	 */
	public void setTnRate(BigDecimal tnRate) {
		this.tnRate = tnRate;
	}

	/**
	 * @return the txRate
	 */
	public BigDecimal getTxRate() {
		return txRate;
	}

	/**
	 * @param txRate the txRate to set
	 */
	public void setTxRate(BigDecimal txRate) {
		this.txRate = txRate;
	}

	/**
	 * @return the utRate
	 */
	public BigDecimal getUtRate() {
		return utRate;
	}

	/**
	 * @param utRate the utRate to set
	 */
	public void setUtRate(BigDecimal utRate) {
		this.utRate = utRate;
	}

	/**
	 * @return the vaRate
	 */
	public BigDecimal getVaRate() {
		return vaRate;
	}

	/**
	 * @param vaRate the vaRate to set
	 */
	public void setVaRate(BigDecimal vaRate) {
		this.vaRate = vaRate;
	}

	/**
	 * @return the vtRate
	 */
	public BigDecimal getVtRate() {
		return vtRate;
	}

	/**
	 * @param vtRate the vtRate to set
	 */
	public void setVtRate(BigDecimal vtRate) {
		this.vtRate = vtRate;
	}

	/**
	 * @return the waRate
	 */
	public BigDecimal getWaRate() {
		return waRate;
	}

	/**
	 * @param waRate the waRate to set
	 */
	public void setWaRate(BigDecimal waRate) {
		this.waRate = waRate;
	}

	/**
	 * @return the wiRate
	 */
	public BigDecimal getWiRate() {
		return wiRate;
	}

	/**
	 * @param wiRate the wiRate to set
	 */
	public void setWiRate(BigDecimal wiRate) {
		this.wiRate = wiRate;
	}

	/**
	 * @return the wvRate
	 */
	public BigDecimal getWvRate() {
		return wvRate;
	}

	/**
	 * @param wvRate the wvRate to set
	 */
	public void setWvRate(BigDecimal wvRate) {
		this.wvRate = wvRate;
	}

	/**
	 * @return the wyRate
	 */
	public BigDecimal getWyRate() {
		return wyRate;
	}

	/**
	 * @param wyRate the wyRate to set
	 */
	public void setWyRate(BigDecimal wyRate) {
		this.wyRate = wyRate;
	}

	public String getCustomStateRates() {
		return customStateRates;
	}

	/**
	 * @param customStateRates the customStateRates to set
	 */
	public void setCustomStateRates(String customStateRates) {
		this.customStateRates = customStateRates;
		try{
			StringTokenizer st = new StringTokenizer(this.customStateRates,",");
			while(st.hasMoreTokens()){
				StringTokenizer stateNameRate = new StringTokenizer(st.nextToken(),"=");
				
				String stateName=stateNameRate.nextToken();
				String stateRate=stateNameRate.nextToken();
				
				int idx=StateEnumType.getIndexByName(stateName);
				
				switch(idx){
					case 0:
						this.akRate=new BigDecimal(stateRate);
						break;
					case 1:
						this.alRate=new BigDecimal(stateRate);
						break;
					case 2:
						this.arRate=new BigDecimal(stateRate);
						break;
					case 3:
						this.azRate= new BigDecimal(stateRate);
						break;
					case 4:
						this.caRate=new BigDecimal(stateRate);
						break;
					case 5:
						this.coRate=new BigDecimal(stateRate);
						break;
					case 6:
						this.ctRate=new BigDecimal(stateRate);
						break;
					case 7:
						this.deRate=new BigDecimal(stateRate);
						break;
					case 8:
						this.flRate=new BigDecimal(stateRate);
						break;
					case 9:
						this.gaRate=new BigDecimal(stateRate);
						break;
					case 10:
						this.hiRate=new BigDecimal(stateRate);
						break;
					case 11:
						this.iaRate=new BigDecimal(stateRate);
						break;
					case 12:
						this.idRate=new BigDecimal(stateRate);
						break;
					case 13:
						this.ilRate=new BigDecimal(stateRate);
						break;
					case 14:
						this.inRate=new BigDecimal(stateRate);
						break;
					case 15:
						this.ksRate=new BigDecimal(stateRate);
						break;
					case 16:
						this.kyRate=new BigDecimal(stateRate);
						break;
					case 17:
						this.laRate=new BigDecimal(stateRate);
						break;
					case 18:
						this.maRate=new BigDecimal(stateRate);
						break;
					case 19:
						this.mdRate=new BigDecimal(stateRate);
						break;
					case 20:
						this.meRate=new BigDecimal(stateRate);
						break;
					case 21:
						this.miRate=new BigDecimal(stateRate);
						break;
					case 22:
						this.mnRate=new BigDecimal(stateRate);
						break;
					case 23:
						this.moRate=new BigDecimal(stateRate);
						break;
					case 24:
						this.msRate=new BigDecimal(stateRate);
						break;
					case 25:
						this.mtRate=new BigDecimal(stateRate);
						break;
					case 26:
						this.ncRate=new BigDecimal(stateRate);
						break;
					case 27:
						this.ndRate=new BigDecimal(stateRate);
						break;
					case 28:
						this.neRate=new BigDecimal(stateRate);
						break;
					case 29:
						this.nhRate=new BigDecimal(stateRate);
						break;
					case 30:
						this.njRate=new BigDecimal(stateRate);
						break;
					case 31:
						this.nmRate=new BigDecimal(stateRate);
						break;
					case 32:
						this.nvRate=new BigDecimal(stateRate);
						break;
					case 33:
						this.nyRate=new BigDecimal(stateRate);
						break;
					case 34:
						this.ohRate=new BigDecimal(stateRate);
						break;
					case 35:
						this.okRate=new BigDecimal(stateRate);
						break;
					case 36:
						this.orRate=new BigDecimal(stateRate);
						break;
					case 37:
						this.paRate=new BigDecimal(stateRate);
						break;
					case 38:
						this.riRate=new BigDecimal(stateRate);
						break;
					case 39:
						this.scRate=new BigDecimal(stateRate);
						break;
					case 40:
						this.sdRate=new BigDecimal(stateRate);
						break;
					case 41:
						this.tnRate=new BigDecimal(stateRate);
						break;
					case 42:
						this.txRate=new BigDecimal(stateRate);
						break;
					case 43:
						this.utRate=new BigDecimal(stateRate);
						break;
					case 44:
						this.vaRate=new BigDecimal(stateRate);
						break;
					case 45:
						this.vtRate=new BigDecimal(stateRate);
						break;
					case 46:
						this.waRate=new BigDecimal(stateRate);
						break;
					case 47:
						this.wiRate=new BigDecimal(stateRate);
						break;
					case 48:
						this.wvRate=new BigDecimal(stateRate);
						break;
					case 49:
						this.wyRate=new BigDecimal(stateRate);
						break;
				}
			}
		}catch(Exception e){}
	}

	public String toAdvancedSearchString() {
		StringBuffer str = new StringBuffer();
		str
		//.append(itemId)
		.append(""+itemCode)
		.append(","+itemDescription)
		.append(","+requestCategory)
		.append(","+fedRate)
		.append(","+stateRate)
		.append(","+contractorRate)
		.append(","+customStateRates);
		
		StringTokenizer st = new StringTokenizer(this.customStateRates,",");
		while(st.hasMoreTokens()){
			String val = st.nextToken();
			str.append(","+val);
		}
		
		return str.toString();
	}
	
	/**
	 * Applies the state filters and returns the filtered collection.
	 * 
	 * @param vos
	 * @param filter
	 * @return
	 */
	public static Collection<IncidentCostRateGridVo> applyStateFilters(Collection<IncidentCostRateGridVo> vos,CostRateGridFilter filter) {
		Collection<IncidentCostRateGridVo> vos2 = new ArrayList<IncidentCostRateGridVo>();
		
		Collection<String> stateFilters = CostRateGridFilterImpl.getStateFilters(filter);
		
		if(stateFilters.size()>0){
			
			for(IncidentCostRateGridVo vo : vos){
				boolean bMatch=true;
				for(String stateFilter : stateFilters){
					if(vo.getCustomStateRates().indexOf(stateFilter) < 0){
						bMatch=false;
						break;
					}
				}
				
				if(bMatch)
					vos2.add(vo);
			}
			
		}else{
			return vos;
		}
		
		return vos2;
	}
	
	/**
	 * Applies the advanced search text and returns the filtered collection.
	 * 
	 * @param vos
	 * @param advancedSearch
	 * @return
	 */
	public static Collection<IncidentCostRateGridVo> applyAdvancedSearch(Collection<IncidentCostRateGridVo> vos , String advancedSearch) {
		Collection<IncidentCostRateGridVo> vos2 = new ArrayList<IncidentCostRateGridVo>();
		
		/*
		 * Do advanced search on the vo.toStringContents value.
		 * Any vo that contains the advancedSearch string is returned.
		 */
		for(IncidentCostRateGridVo vo : vos){
				
			if(vo.toAdvancedSearchString().toUpperCase().indexOf(advancedSearch.toUpperCase())>-1)
				vos2.add(vo);
		}

		return vos2;
	}

}
