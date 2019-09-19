package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswIapPlanPrintOrder", table = "isw_iap_plan_print_order")

public class IswIapPlanPrintOrder {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_PLAN_PRINT_ORDER", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;

	@XmlTransferField(name = "A", sqlname="IAP_PLAN_ID", type="STRING"
		, derivedtable="isw_iap_plan")
	private String a;
	
	@XmlTransferField(name = "B", sqlname="FILETYPE", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname="FILEID", type="LONG"  )
	private Long c;
	
	@XmlTransferField(name = "D", sqlname="POSITION", type="INTEGER")
	private Integer d; 
	
	/**
	 * Default constructor.
	 * 
	 */
	public IswIapPlanPrintOrder() {
	}

}
