package gov.nwcg.isuite.framework.core.persistence.datefilter;

public enum CustomDateFilterEnum {
	
	CustomDateFilterType1(
			"0"
			,"gov.nwcg.isuite.framework.core.persistence.datefilter.impl.CustomDateFilterType1Impl"
			,"January to September, All Years."
			),
	CustomDateFilterType2(
			"1"
			,"gov.nwcg.isuite.framework.core.persistence.datefilter.impl.CustomDateFilterType2Impl"
			,"October to December, All Years."
			),
	CustomDateFilterTypeDefault(
			"default"
			,"gov.nwcg.isuite.framework.core.persistence.datefilter.impl.CustomDateFilterTypeDefaultImpl"
			,"Default date filter.  Should handle most scenarios."
			);
	
	private String crypticDateFilterCode;
	private String filterClass;
	private String description;
	
	private CustomDateFilterEnum(String crypticDateFilterCode
									,String filterClass, String description) {
		
		this.crypticDateFilterCode = crypticDateFilterCode;
		this.filterClass=filterClass;
		this.description = description;
	}

	/**
	 * @return the crypticDateFilterCode
	 */
	public String getCrypticDateFilterCode() {
		return crypticDateFilterCode;
	}

	/**
	 * @return the filterClass
	 */
	public String getFilterClass() {
		return filterClass;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
