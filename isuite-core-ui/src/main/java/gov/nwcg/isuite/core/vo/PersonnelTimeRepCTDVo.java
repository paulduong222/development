package gov.nwcg.isuite.core.vo;

/**
 * A Collection of these VOs will be the data source of the checkbox tree component on the Flex UI
 * on the personnel time report screen.
 */
public class PersonnelTimeRepCTDVo extends CheckboxTreeDataVo{
	
	public static final Integer PERSONNEL_TIME_REPORT_AGENCY 		= 1;
	public static final Integer PERSONNEL_TIME_REPORT_FAX 			= 2;
	public static final Integer PERSONNEL_TIME_REPORT_RESOURCE 		= 3;
	
	public PersonnelTimeRepCTDVo(Integer itemType) {
		super(itemType);
		
		if(!(PERSONNEL_TIME_REPORT_AGENCY.equals(itemType) 
				|| PERSONNEL_TIME_REPORT_FAX.equals(itemType)
				|| PERSONNEL_TIME_REPORT_RESOURCE.equals(itemType))){
			throw new RuntimeException("Invalid Item Type.");
		}
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer();
		String newline = "\n";
		String tab1 = "|--->";
		String tab2 = "   |--->";
		
		str.append(this.getItemLabel());
		str.append(newline);
		if(this.getChildren()!=null) {
			for(CheckboxTreeDataVo child: this.getChildren()) {
				if(child.getItemType().equals(PERSONNEL_TIME_REPORT_FAX)){
					str.append(tab1);
				} else {
					str.append(tab2);
				}
				str.append(child.toString());
			}
		}
		return str.toString();
	}
}
