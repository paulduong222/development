package gov.nwcg.isuite.core.filter;


public interface CostRateGridFilter extends CostStateRateFilter {

	/**
	 * @param category
	 */
	public void setRequestCategory(String category);
	
	/**
	 * @return
	 */
	public String getRequestCategory();
	
	/**
	 * @param itemCode
	 */
	public void setItemCode(String itemCode);
	
	/**
	 * @return
	 */
	public String getItemCode();
	
	/**
	 * @param itemDescription
	 */
	public void setItemDescription(String itemDescription);
	
	/**
	 * @return
	 */
	public String getItemDescription();
	
	/**
	 * @param fedRate
	 */
	public void setFedRate(String fedRate);
	
	/**
	 * @return
	 */
	public String getFedRate();
	
	/**
	 * @param stateRate
	 */
	public void setStateRate(String stateRate);
	
	/**
	 * @return
	 */
	public String getStateRate();
	
	/**
	 * @param contractorRate
	 */
	public void setContractorRate(String contractorRate);
	
	/**
	 * @return
	 */
	public String getContractorRate();
	
	/**
	 * 
	 * @param costRateCategory
	 */
	public void setCostRateCategory(String costRateCategory);
	
	/**
	 * 
	 * @return
	 */
	public String getCostRateCategory();

	/**
	 * @return the advancedSearch
	 */
	public String getAdvancedSearch();

	/**
	 * @param advancedSearch the advancedSearch to set
	 */
	public void setAdvancedSearch(String advancedSearch);

}
