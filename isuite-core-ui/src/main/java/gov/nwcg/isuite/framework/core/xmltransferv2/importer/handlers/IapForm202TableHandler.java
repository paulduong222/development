package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.core.persistence.hibernate.query.IapDefaultSettingsQuery;
import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class IapForm202TableHandler extends BaseTableHandler implements TableHandler {
	
	
	public IapForm202TableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		return true;
	}

	public void doPostInsertProcesses() throws Exception {
		this.updateClobData();
		
	}

	public void doPostUpdateProcesses() throws Exception {
		this.updateClobData();

	}

	private void updateClobData() throws Exception {
		Long iapForm202Id=(Long)XmlTransferUtil.invokeGetMethod(xmlObject, "Id");
		
		XmlField xfObjectives=xmlTable.getXmlFieldBySqlName("OBJECTIVES");
		XmlField xfCommanEmph=xmlTable.getXmlFieldBySqlName("COMMAND_EMPHASIS");
		XmlField xfGSA=xmlTable.getXmlFieldBySqlName("GEN_SIT_AWARENESS");
		
		String objectives=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xfObjectives.name);
		String commandEmphasis=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xfCommanEmph.name);
		String gsa=(String)XmlTransferUtil.invokeGetMethod(xmlObject, xfGSA.name);

		this.updateClobField(iapForm202Id,"objectives", objectives);
		this.updateClobField(iapForm202Id,"command_emphasis", commandEmphasis);
		this.updateClobField(iapForm202Id,"gen_sit_awareness", gsa);
	}
	
	private void updateClobField(Long id, String field, String data) throws Exception {
		String sql="";
		
		if(StringUtility.hasValue(data)){
			int start=0;
			
			if(data.length()>2048){
				int end=2048;
				int datalen=data.length();
				int count=datalen / 2048;
				int mod=datalen%2048;
				for(int i=0;i<count;i++){
					String s=data.substring(start, end);

					s=s.replaceAll("&apos;", "''");
					//val=val.replaceAll("'", "''");
					s=s.replaceAll("\"", "''");
					s=s.replaceAll("&lt;", "<");
					s=s.replaceAll("&gt;", ">");
					s=s.replaceAll("&amp;", "&");

					if(i==0){
						sql="UPDATE isw_iap_form_202 " +
						"SET "+field+" = '" + s + "' " +
						"WHERE id = " + id + " ";
					}else{
						sql="UPDATE isw_iap_form_202 " +
						"SET "+field+" = " + field + " || '" + s + "' " +
						"WHERE id = " + id + " ";
					}
					
					dao.executeUpdate(sql);
					
					start=(i+1)*2048;
					end=start+2048;
				}
				
				if(mod>0){
					String s=data.substring(start, (start+mod));

					s=s.replaceAll("&apos;", "''");
					//val=val.replaceAll("'", "''");
					s=s.replaceAll("\"", "''");
					s=s.replaceAll("&lt;", "<");
					s=s.replaceAll("&gt;", ">");
					s=s.replaceAll("&amp;", "&");
					
					sql="UPDATE isw_iap_form_202 " +
					"SET "+field+" = " + field + " || '" + s + "' " +
					"WHERE id = " + id + " ";
					
					dao.executeUpdate(sql);
				}
					
			}else{
				String s = data;

				s=s.replaceAll("&apos;", "''");
				//val=val.replaceAll("'", "''");
				s=s.replaceAll("\"", "''");
				s=s.replaceAll("&lt;", "<");
				s=s.replaceAll("&gt;", ">");
				s=s.replaceAll("&amp;", "&");
				
				sql="UPDATE isw_iap_form_202 " +
				"SET "+field+" = '" + s + "' " +
				"WHERE id = " + id + " ";
				
				dao.executeUpdate(sql);
			}
		}
	}
	
	public static void main(String[] args){

		String sequence="nextVal('SEQ_IAP_POSITION_ITEM_CODE')";
		for(IapDefaultSettingsQuery._204Settings _204Enum : IapDefaultSettingsQuery._204Settings.values()){
			String sql = _204Enum.sql;
			sql=sql.replaceAll(":fieldName", "INCIDENT_GROUP_ID");
			sql=sql.replaceAll(":seqId", sequence);
			sql=sql.replaceAll(":incidentId", String.valueOf(1));
			
			System.out.println(sql+";");
			
		}
		
		
		String s="01234567890123456789";
		int datalen=s.length();
		int count=datalen / 3;
		int mod=datalen%3;
		int start=0;
		int end=3;
		
		for(int i=0;i<count;i++){
			String s1=s.substring(start, end);
			System.out.println(s1);
			start=(i+1)*3;
			end=start+3;
		}

		if(mod>0){
			String s3=s.substring(start,(start+mod));
			System.out.println(s3);
		}
	}
}
