package gov.nwcg.isuite.core.reports.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Report data object for Custom Report Jasper File
 */
public class CustomReportData {
	
	public static final int MAX_NUMBER_OF_COLUMNS = 70;
	
	private Object column1;
	private Object column2;
	private Object column3;
	private Object column4;
	private Object column5;
	private Object column6;
	private Object column7;
	private Object column8;
	private Object column9;
	private Object column10;
	private Object column11;
	private Object column12;
	private Object column13;
	private Object column14;
	private Object column15;
	private Object column16;
	private Object column17;
	private Object column18;
	private Object column19;
	private Object column20;
	private Object column21;
	private Object column22;
	private Object column23;
	private Object column24;
	private Object column25;
	private Object column26;
	private Object column27;
	private Object column28;
	private Object column29;
	private Object column30;
	private Object column31;
	private Object column32;
	private Object column33;
	private Object column34;
	private Object column35;
	private Object column36;
	private Object column37;
	private Object column38;
	private Object column39;
	private Object column40;
	private Object column41;
	private Object column42;
	private Object column43;
	private Object column44;
	private Object column45;
	private Object column46;
	private Object column47;
	private Object column48;
	private Object column49;
	private Object column50;
	private Object column51;
	private Object column52;
	private Object column53;
	private Object column54;
	private Object column55;
	private Object column56;
	private Object column57;
	private Object column58;
	private Object column59;
	private Object column60;
	private Object column61;
	private Object column62;
	private Object column63;
	private Object column64;
	private Object column65;
	private Object column66;
	private Object column67;
	private Object column68;
	private Object column69;
	private Object column70;
	
	public CustomReportData() {}
	
	public ArrayList<String> getColumns() {
		ArrayList<String> columns = new ArrayList<String>();
		String columnValue;
		Method getter = null;
		for(int count=1; count<=MAX_NUMBER_OF_COLUMNS; count++){
			columnValue = null;
			try {
				getter = this.getClass().getMethod("getColumn" + count, null);
				columnValue = (String)getter.invoke(this, (Object[])null);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} finally{
				// This line must be outside the try block to ensure that a null object gets added
				// for the column in case there is an exception. 
				columns.add(columnValue); 
			}
		}
		return columns;
	}
	
	/**
	 * Sets all columns of this object using the first [MAX_NUMBER_OF_COLUMNS] elements of an Object based ArrayList.
	 * @param columns
	 */
	public void setColumns(ArrayList<Object> columns){
		if(columns == null) return;
		
		Method setter = null;
		int count = 1;
		String columnValueAsString = null;
		for(Object columnValue : columns) {
			columnValueAsString = (columnValue!=null)?columnValue.toString():null;
			try {
				setter = this.getClass().getMethod("setColumn" + count, java.lang.String.class);
				setter.invoke(this, columnValueAsString);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} finally{
				count++;
			}
			if(count > MAX_NUMBER_OF_COLUMNS) break;
		}
	}
	
	public Object getColumn1() {
		return column1;
	}

	public Object getColumn2() {
		return column2;
	}

	public Object getColumn3() {
		return column3;
	}

	public Object getColumn4() {
		return column4;
	}

	public Object getColumn5() {
		return column5;
	}

	public Object getColumn6() {
		return column6;
	}

	public Object getColumn7() {
		return column7;
	}

	public Object getColumn8() {
		return column8;
	}

	public Object getColumn9() {
		return column9;
	}

	public Object getColumn10() {
		return column10;
	}

	public Object getColumn11() {
		return column11;
	}

	public Object getColumn12() {
		return column12;
	}

	public Object getColumn13() {
		return column13;
	}

	public Object getColumn14() {
		return column14;
	}

	public Object getColumn15() {
		return column15;
	}

	public Object getColumn16() {
		return column16;
	}

	public Object getColumn17() {
		return column17;
	}

	public Object getColumn18() {
		return column18;
	}

	public Object getColumn19() {
		return column19;
	}

	public Object getColumn20() {
		return column20;
	}

	public Object getColumn21() {
		return column21;
	}

	public Object getColumn22() {
		return column22;
	}

	public Object getColumn23() {
		return column23;
	}

	public Object getColumn24() {
		return column24;
	}

	public Object getColumn25() {
		return column25;
	}

	public Object getColumn26() {
		return column26;
	}

	public Object getColumn27() {
		return column27;
	}

	public Object getColumn28() {
		return column28;
	}

	public Object getColumn29() {
		return column29;
	}

	public Object getColumn30() {
		return column30;
	}

	public Object getColumn31() {
		return column31;
	}

	public Object getColumn32() {
		return column32;
	}

	public Object getColumn33() {
		return column33;
	}

	public Object getColumn34() {
		return column34;
	}

	public Object getColumn35() {
		return column35;
	}

	public Object getColumn36() {
		return column36;
	}

	public Object getColumn37() {
		return column37;
	}

	public Object getColumn38() {
		return column38;
	}

	public Object getColumn39() {
		return column39;
	}

	public Object getColumn40() {
		return column40;
	}

	public Object getColumn41() {
		return column41;
	}

	public Object getColumn42() {
		return column42;
	}

	public Object getColumn43() {
		return column43;
	}

	public Object getColumn44() {
		return column44;
	}

	public Object getColumn45() {
		return column45;
	}

	public Object getColumn46() {
		return column46;
	}

	public Object getColumn47() {
		return column47;
	}

	public Object getColumn48() {
		return column48;
	}

	public Object getColumn49() {
		return column49;
	}

	public Object getColumn50() {
		return column50;
	}

	public Object getColumn51() {
		return column51;
	}

	public Object getColumn52() {
		return column52;
	}

	public Object getColumn53() {
		return column53;
	}

	public Object getColumn54() {
		return column54;
	}

	public Object getColumn55() {
		return column55;
	}

	public Object getColumn56() {
		return column56;
	}

	public Object getColumn57() {
		return column57;
	}

	public Object getColumn58() {
		return column58;
	}

	public Object getColumn59() {
		return column59;
	}

	public Object getColumn60() {
		return column60;
	}

	public Object getColumn61() {
		return column61;
	}

	public Object getColumn62() {
		return column62;
	}

	public Object getColumn63() {
		return column63;
	}

	public Object getColumn64() {
		return column64;
	}

	public Object getColumn65() {
		return column65;
	}

	public Object getColumn66() {
		return column66;
	}

	public Object getColumn67() {
		return column67;
	}

	public Object getColumn68() {
		return column68;
	}

	public Object getColumn69() {
		return column69;
	}

	public Object getColumn70() {
		return column70;
	}

	public void setColumn1(Object column1) {
		this.column1 = column1;
	}

	public void setColumn2(Object column2) {
		this.column2 = column2;
	}

	public void setColumn3(Object column3) {
		this.column3 = column3;
	}

	public void setColumn4(Object column4) {
		this.column4 = column4;
	}

	public void setColumn5(Object column5) {
		this.column5 = column5;
	}

	public void setColumn6(Object column6) {
		this.column6 = column6;
	}

	public void setColumn7(Object column7) {
		this.column7 = column7;
	}

	public void setColumn8(Object column8) {
		this.column8 = column8;
	}

	public void setColumn9(Object column9) {
		this.column9 = column9;
	}

	public void setColumn10(Object column10) {
		this.column10 = column10;
	}

	public void setColumn11(Object column11) {
		this.column11 = column11;
	}

	public void setColumn12(Object column12) {
		this.column12 = column12;
	}

	public void setColumn13(Object column13) {
		this.column13 = column13;
	}

	public void setColumn14(Object column14) {
		this.column14 = column14;
	}

	public void setColumn15(Object column15) {
		this.column15 = column15;
	}

	public void setColumn16(Object column16) {
		this.column16 = column16;
	}

	public void setColumn17(Object column17) {
		this.column17 = column17;
	}

	public void setColumn18(Object column18) {
		this.column18 = column18;
	}

	public void setColumn19(Object column19) {
		this.column19 = column19;
	}

	public void setColumn20(Object column20) {
		this.column20 = column20;
	}

	public void setColumn21(Object column21) {
		this.column21 = column21;
	}

	public void setColumn22(Object column22) {
		this.column22 = column22;
	}

	public void setColumn23(Object column23) {
		this.column23 = column23;
	}

	public void setColumn24(Object column24) {
		this.column24 = column24;
	}

	public void setColumn25(Object column25) {
		this.column25 = column25;
	}

	public void setColumn26(Object column26) {
		this.column26 = column26;
	}

	public void setColumn27(Object column27) {
		this.column27 = column27;
	}

	public void setColumn28(Object column28) {
		this.column28 = column28;
	}

	public void setColumn29(Object column29) {
		this.column29 = column29;
	}

	public void setColumn30(Object column30) {
		this.column30 = column30;
	}

	public void setColumn31(Object column31) {
		this.column31 = column31;
	}

	public void setColumn32(Object column32) {
		this.column32 = column32;
	}

	public void setColumn33(Object column33) {
		this.column33 = column33;
	}

	public void setColumn34(Object column34) {
		this.column34 = column34;
	}

	public void setColumn35(Object column35) {
		this.column35 = column35;
	}

	public void setColumn36(Object column36) {
		this.column36 = column36;
	}

	public void setColumn37(Object column37) {
		this.column37 = column37;
	}

	public void setColumn38(Object column38) {
		this.column38 = column38;
	}

	public void setColumn39(Object column39) {
		this.column39 = column39;
	}

	public void setColumn40(Object column40) {
		this.column40 = column40;
	}

	public void setColumn41(Object column41) {
		this.column41 = column41;
	}

	public void setColumn42(Object column42) {
		this.column42 = column42;
	}

	public void setColumn43(Object column43) {
		this.column43 = column43;
	}

	public void setColumn44(Object column44) {
		this.column44 = column44;
	}

	public void setColumn45(Object column45) {
		this.column45 = column45;
	}

	public void setColumn46(Object column46) {
		this.column46 = column46;
	}

	public void setColumn47(Object column47) {
		this.column47 = column47;
	}

	public void setColumn48(Object column48) {
		this.column48 = column48;
	}

	public void setColumn49(Object column49) {
		this.column49 = column49;
	}

	public void setColumn50(Object column50) {
		this.column50 = column50;
	}

	public void setColumn51(Object column51) {
		this.column51 = column51;
	}

	public void setColumn52(Object column52) {
		this.column52 = column52;
	}

	public void setColumn53(Object column53) {
		this.column53 = column53;
	}

	public void setColumn54(Object column54) {
		this.column54 = column54;
	}

	public void setColumn55(Object column55) {
		this.column55 = column55;
	}

	public void setColumn56(Object column56) {
		this.column56 = column56;
	}

	public void setColumn57(Object column57) {
		this.column57 = column57;
	}

	public void setColumn58(Object column58) {
		this.column58 = column58;
	}

	public void setColumn59(Object column59) {
		this.column59 = column59;
	}

	public void setColumn60(Object column60) {
		this.column60 = column60;
	}

	public void setColumn61(Object column61) {
		this.column61 = column61;
	}

	public void setColumn62(Object column62) {
		this.column62 = column62;
	}

	public void setColumn63(Object column63) {
		this.column63 = column63;
	}

	public void setColumn64(Object column64) {
		this.column64 = column64;
	}

	public void setColumn65(Object column65) {
		this.column65 = column65;
	}

	public void setColumn66(Object column66) {
		this.column66 = column66;
	}

	public void setColumn67(Object column67) {
		this.column67 = column67;
	}

	public void setColumn68(Object column68) {
		this.column68 = column68;
	}

	public void setColumn69(Object column69) {
		this.column69 = column69;
	}

	public void setColumn70(Object column70) {
		this.column70 = column70;
	}
}
