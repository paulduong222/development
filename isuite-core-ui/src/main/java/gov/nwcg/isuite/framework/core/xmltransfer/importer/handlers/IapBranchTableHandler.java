package gov.nwcg.isuite.framework.core.xmltransfer.importer.handlers;

import gov.nwcg.isuite.framework.core.xmltransfer.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class IapBranchTableHandler extends BaseTableHandler implements TableHandler {
	
	
	public IapBranchTableHandler(){
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
		Long iapBranchId=(Long)XmlTransferUtil.invokeGetMethod(xmlObject, "Id");
		
		String specialInstructions=(String)XmlTransferUtil.invokeGetMethod(xmlObject, "SpecialInstructions");
		String workAssignment=(String)XmlTransferUtil.invokeGetMethod(xmlObject, "WorkAssignment");

		this.updateClobField(iapBranchId,"special_instructions", specialInstructions);
		this.updateClobField(iapBranchId,"work_assignment", workAssignment);
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
						sql="UPDATE isw_iap_branch " +
						"SET "+field+" = '" + s + "' " +
						"WHERE id = " + id + " ";
					}else{
						sql="UPDATE isw_iap_branch " +
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
					
					sql="UPDATE isw_iap_branch " +
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
				
				sql="UPDATE isw_iap_branch " +
				"SET "+field+" = '" + s + "' " +
				"WHERE id = " + id + " ";
				
				dao.executeUpdate(sql);
			}
		}
	}
}
