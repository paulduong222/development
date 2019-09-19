package gov.nwcg.isuite.core.controllers.restdata.iap;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.vo.IapAttachmentVo;

public class IapAttachmentData extends DialogueData {

	private IapAttachmentVo iapAttachmentVo;
	private byte[] pdfByteArray;

	public IapAttachmentVo getIapAttachmentVo() {
		return iapAttachmentVo;
	}

	public void setIapAttachmentVo(IapAttachmentVo iapAttachmentVo) {
		this.iapAttachmentVo = iapAttachmentVo;
	}
	
	public byte[] getPdfByteArray() {
		return pdfByteArray;
	}
	
	public void setPdfByteArray(byte[] pdfByteArray) {
		this.pdfByteArray = pdfByteArray;
	}
}
