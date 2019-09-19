package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswAirTravel", table="isw_air_travel"
	,filterincident=" "+
					"id in (" +
					"   select dm_air_travel_id " +
					"   from isw_work_period "+
					"   where incident_resource_id in ("+
					"      select id from isw_incident_resource " +
					"      where incident_id in ( :INCIDENTID )" +
					"   ) " +
					") " +
					" or " +
					"id in (" +
					"   select dm_air_travel_id " +
					"   from isw_work_period "+
					"   where incident_resource_id in ("+
					"      select id from isw_incident_resource " +
					"      where incident_id in ( :INCIDENTID )" +
					"   ) " +
					") "
)
public class IswAirTravel {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_AIR_TRAVEL", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TI", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String tI;
	
	@XmlTransferField(name = "A", sqlname = "JET_PORT_ID", type = "STRING"
		  ,derivedtable="iswl_jet_port")
	private String a;
	
	@XmlTransferField(name = "B", sqlname = "AIRLINE", type="STRING")
	private String b;

	@XmlTransferField(name = "C", sqlname = "IS_DISPATCH_NOTIFIED", type="BOOLEAN")
	private Boolean c;

	@XmlTransferField(name = "D", sqlname = "HOURS_TO_AIRPORT", type="INTEGER")
	private Integer d;

	@XmlTransferField(name = "E", sqlname = "MINUTES_TO_AIRPORT", type="INTEGER")
	private Integer e;

	@XmlTransferField(name = "F", sqlname = "LEAVE_TIME", type="INTEGER")
	private Integer f;

	@XmlTransferField(name = "G", sqlname = "FLIGHT_NUMBER", type="STRING")
	private String g;

	@XmlTransferField(name = "H", sqlname = "FLIGHT_TIME", type="INTEGER")
	private Integer h;

	@XmlTransferField(name = "I", sqlname = "REMARKS", type="STRING")
	private String i;

	@XmlTransferField(name = "J", sqlname = "IS_ITINERARY_RECEIVED", type="BOOLEAN")
	private Boolean j;

	public IswAirTravel() {
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

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public Boolean getC() {
		return c;
	}

	public void setC(Boolean c) {
		this.c = c;
	}

	public Integer getD() {
		return d;
	}

	public void setD(Integer d) {
		this.d = d;
	}

	public Integer getE() {
		return e;
	}

	public void setE(Integer e) {
		this.e = e;
	}

	public Integer getF() {
		return f;
	}

	public void setF(Integer f) {
		this.f = f;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	public Integer getH() {
		return h;
	}

	public void setH(Integer h) {
		this.h = h;
	}

	public String getI() {
		return i;
	}

	public void setI(String i) {
		this.i = i;
	}

	public Boolean getJ() {
		return j;
	}

	public void setJ(Boolean j) {
		this.j = j;
	}

}