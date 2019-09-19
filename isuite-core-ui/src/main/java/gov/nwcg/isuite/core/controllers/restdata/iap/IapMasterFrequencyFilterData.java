package gov.nwcg.isuite.core.controllers.restdata.iap;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.filter.IapMasterFrequencyFilter;

public class IapMasterFrequencyFilterData extends DialogueData {

	private IapMasterFrequencyFilter iapMasterFrequencyFilter;
	private byte[] xmlByteArray;

	public IapMasterFrequencyFilter getIapMasterFrequencyFilter() {
		return iapMasterFrequencyFilter;
	}

	public void setIapMasterFrequencyFilter(IapMasterFrequencyFilter iapMasterFrequencyFilter) {
		this.iapMasterFrequencyFilter = iapMasterFrequencyFilter;
	}
	
	public byte[] getXmlByteArray() {
		return xmlByteArray;
	}
	
	public void setXmlByteArray(byte[] xmlByteArray) {
		this.xmlByteArray = xmlByteArray;
	}
}
