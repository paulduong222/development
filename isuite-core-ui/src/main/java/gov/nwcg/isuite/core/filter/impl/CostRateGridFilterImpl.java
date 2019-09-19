package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.CostRateGridFilter;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

public class CostRateGridFilterImpl extends CostStateRateFilterImpl implements CostRateGridFilter {
	
	private String requestCategory;
	private String itemCode;
	private String itemDescription;
	private String fedRate;
	private String stateRate;
	private String contractorRate;
	
	private String costRateCategory;

	private String advancedSearch;
	
	public CostRateGridFilterImpl(){
		
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

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateGridFilter#getItemCode()
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateGridFilter#setItemCode(java.lang.String)
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateGridFilter#getItemDescription()
	 */
	public String getItemDescription() {
		return this.itemDescription;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateGridFilter#setItemDescription(java.lang.String)
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateGridFilter#getFedRate()
	 */
	public String getFedRate() {
		return this.fedRate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateGridFilter#setFedRate(java.lang.String)
	 */
	public void setFedRate(String fedRate) {
		this.fedRate = fedRate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateGridFilter#getStateRate()
	 */
	public String getStateRate() {
		return this.stateRate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateGridFilter#setStateRate(java.lang.String)
	 */
	public void setStateRate(String stateRate) {
		this.stateRate = stateRate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateGridFilter#getContractorRate()
	 */
	public String getContractorRate() {
		return this.contractorRate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateGridFilter#setContractorRate(java.lang.String)
	 */
	public void setContractorRate(String contractorRate) {
		this.contractorRate = contractorRate;
	}

	@Override
	public String getCostRateCategory() {
		return this.costRateCategory;
	}

	@Override
	public void setCostRateCategory(String costRateCategory) {
		this.costRateCategory = costRateCategory;
	}

	/**
	 * @param filter
	 * @return
	 */
	public static Collection<String> getStateFilters(CostRateGridFilter filter){
		
		Collection<String> stateFilters = new ArrayList<String>();
		
		if(null != filter){
			if(StringUtility.hasValue(filter.getAkRate()))
				stateFilters.add("AK="+filter.getAkRate());
			
			if(StringUtility.hasValue(filter.getAlRate()))
				stateFilters.add("AL="+filter.getAkRate());
			
			if(StringUtility.hasValue(filter.getArRate()))
				stateFilters.add("AR="+filter.getArRate());
			
			if(StringUtility.hasValue(filter.getAzRate()))
				stateFilters.add("AZ="+filter.getAzRate());

			if(StringUtility.hasValue(filter.getCaRate()))
				stateFilters.add("CA="+filter.getCaRate());

			if(StringUtility.hasValue(filter.getCoRate()))
				stateFilters.add("CO="+filter.getCoRate());
			
			if(StringUtility.hasValue(filter.getCtRate()))
				stateFilters.add("CT="+filter.getCtRate());

			if(StringUtility.hasValue(filter.getDeRate()))
				stateFilters.add("DE="+filter.getDeRate());

			if(StringUtility.hasValue(filter.getFlRate()))
				stateFilters.add("FL="+filter.getFlRate());

			if(StringUtility.hasValue(filter.getGaRate()))
				stateFilters.add("GA="+filter.getGaRate());

			if(StringUtility.hasValue(filter.getHiRate()))
				stateFilters.add("HI="+filter.getHiRate());
			
			if(StringUtility.hasValue(filter.getIaRate()))
				stateFilters.add("IA="+filter.getIaRate());

			if(StringUtility.hasValue(filter.getIdRate()))
				stateFilters.add("ID="+filter.getIdRate());
			
			if(StringUtility.hasValue(filter.getIlRate()))
				stateFilters.add("IL="+filter.getIlRate());

			if(StringUtility.hasValue(filter.getInRate()))
				stateFilters.add("IN="+filter.getInRate());

			if(StringUtility.hasValue(filter.getKsRate()))
				stateFilters.add("KS="+filter.getKsRate());
		
			if(StringUtility.hasValue(filter.getKyRate()))
				stateFilters.add("KY="+filter.getKyRate());

			if(StringUtility.hasValue(filter.getLaRate()))
				stateFilters.add("LA="+filter.getLaRate());

			if(StringUtility.hasValue(filter.getMaRate()))
				stateFilters.add("MA="+filter.getMaRate());
		
			if(StringUtility.hasValue(filter.getMdRate()))
				stateFilters.add("MD="+filter.getMdRate());

			if(StringUtility.hasValue(filter.getMeRate()))
				stateFilters.add("ME="+filter.getMeRate());

			if(StringUtility.hasValue(filter.getMiRate()))
				stateFilters.add("MI="+filter.getMiRate());

			if(StringUtility.hasValue(filter.getMnRate()))
				stateFilters.add("MN="+filter.getMnRate());

			if(StringUtility.hasValue(filter.getMoRate()))
				stateFilters.add("MO="+filter.getMoRate());

			if(StringUtility.hasValue(filter.getMsRate()))
				stateFilters.add("MS="+filter.getMsRate());

			if(StringUtility.hasValue(filter.getMtRate()))
				stateFilters.add("MT="+filter.getMtRate());
			
			if(StringUtility.hasValue(filter.getNcRate()))
				stateFilters.add("NC="+filter.getNcRate());
		
			if(StringUtility.hasValue(filter.getNdRate()))
				stateFilters.add("ND="+filter.getNdRate());

			if(StringUtility.hasValue(filter.getNeRate()))
				stateFilters.add("NE="+filter.getNeRate());
		
			if(StringUtility.hasValue(filter.getNhRate()))
				stateFilters.add("NH="+filter.getNhRate());

			if(StringUtility.hasValue(filter.getNjRate()))
				stateFilters.add("NJ="+filter.getNjRate());

			if(StringUtility.hasValue(filter.getNmRate()))
				stateFilters.add("NM="+filter.getNmRate());

			if(StringUtility.hasValue(filter.getNvRate()))
				stateFilters.add("NV="+filter.getNvRate());
			
			if(StringUtility.hasValue(filter.getNyRate()))
				stateFilters.add("NY="+filter.getNyRate());

			if(StringUtility.hasValue(filter.getOhRate()))
				stateFilters.add("OH="+filter.getOhRate());
			
			if(StringUtility.hasValue(filter.getOkRate()))
				stateFilters.add("OK="+filter.getOkRate());

			if(StringUtility.hasValue(filter.getOrRate()))
				stateFilters.add("OR="+filter.getOrRate());
		
			if(StringUtility.hasValue(filter.getPaRate()))
				stateFilters.add("PA="+filter.getPaRate());
			
			if(StringUtility.hasValue(filter.getRiRate()))
				stateFilters.add("RI="+filter.getRiRate());

			if(StringUtility.hasValue(filter.getScRate()))
				stateFilters.add("SC="+filter.getScRate());

			if(StringUtility.hasValue(filter.getSdRate()))
				stateFilters.add("SD="+filter.getSdRate());
			
			if(StringUtility.hasValue(filter.getTnRate()))
				stateFilters.add("TN="+filter.getTnRate());
			
			if(StringUtility.hasValue(filter.getTxRate()))
				stateFilters.add("TX="+filter.getTxRate());
			
			if(StringUtility.hasValue(filter.getUtRate()))
				stateFilters.add("UT="+filter.getUtRate());
			
			if(StringUtility.hasValue(filter.getVaRate()))
				stateFilters.add("VA="+filter.getVaRate());
			
			if(StringUtility.hasValue(filter.getVtRate()))
				stateFilters.add("VT="+filter.getVtRate());
			
			if(StringUtility.hasValue(filter.getWaRate()))
				stateFilters.add("WA="+filter.getWaRate());

			if(StringUtility.hasValue(filter.getWiRate()))
				stateFilters.add("WI="+filter.getWiRate());
			
			if(StringUtility.hasValue(filter.getWvRate()))
				stateFilters.add("WV="+filter.getWvRate());
		
			if(StringUtility.hasValue(filter.getWyRate()))
				stateFilters.add("WY="+filter.getWyRate());
		}
		
		return stateFilters;
	}

	/**
	 * @return the advancedSearch
	 */
	public String getAdvancedSearch() {
		return advancedSearch;
	}

	/**
	 * @param advancedSearch the advancedSearch to set
	 */
	public void setAdvancedSearch(String advancedSearch) {
		this.advancedSearch = advancedSearch;
	}
	
}
