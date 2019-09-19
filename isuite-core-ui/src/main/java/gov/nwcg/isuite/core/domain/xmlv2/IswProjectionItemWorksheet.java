package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.math.BigDecimal;
import java.util.Date;

@XmlTransferTable(name = "IswProjectionItemWorksheet", table = "isw_projection_item_wksht"
	,filterincident=" projection_item_id in (" +
					"    select id from isw_projection_item where projection_id in (" +
					"        select id from isw_projection where incident_id in (:INCIDENTID) " +
					"    )" +
					") "
	,filtergroup=" projection_item_id in (" +
				"    select id from isw_projection_item where projection_id in (" +
				"        select id from isw_projection where incident_group_id in (:INCIDENTGROUPID) " +
				"    )" +
				") "
)
public class IswProjectionItemWorksheet {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_PROJECTION_ITEM_WKSHT", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "PROJECTION_DATE", type="DATE")
	private Date a;
	
	@XmlTransferField(name = "B", sqlname = "QUANTITY", type="INTEGER")
	private Integer b;
	
	@XmlTransferField(name = "C", sqlname = "AVERAGE_COST", type="BIGDECIMAL")
	private BigDecimal c;
	
	@XmlTransferField(name = "D", sqlname = "NUMBER_OF_PERSONNEL", type="INTEGER")
	private Integer d;

	@XmlTransferField(name = "E", sqlname="PROJECTION_ITEM_ID", type="STRING"
		,derivedtable="isw_projection_item")
	private String e;

	/**
	 * Default constructor.
	 *
	 */
	public IswProjectionItemWorksheet() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTI() {
		return tI;
	}

	public void setTI(String ti) {
		tI = ti;
	}

	public Date getA() {
		return a;
	}

	public void setA(Date a) {
		this.a = a;
	}

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public BigDecimal getC() {
		return c;
	}

	public void setC(BigDecimal c) {
		this.c = c;
	}

	public Integer getD() {
		return d;
	}

	public void setD(Integer d) {
		this.d = d;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

}
