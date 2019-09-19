package test;

import gov.nwcg.isuite.framework.core.xmltransferv2.exporter.AbstractXmlTransferExporterContext;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class UnitRefDataGenerator {
	
	private static final String FILE_PATH = "/Users/Karen/Desktop/ReferenceData/UnitID.xlsx";

	public UnitRefDataGenerator() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Collection<String> units = getUnitsFromExcel();
	}
	
	 private static Collection<String> getUnitsFromExcel() {
		 Collection<String> oracleUnits = new ArrayList<String>();
		 Collection<String> pgUnits = new ArrayList<String>();
		 
		 FileInputStream fis = null;
		 
		 try {
			 fis = new FileInputStream(FILE_PATH);
			 
			 Workbook workbook = new XSSFWorkbook(fis);
			 int numberOfSheets = workbook.getNumberOfSheets();
			 Sheet sheet = workbook.getSheetAt(0);
			 Row row;
			 Cell cell;
			 int rows = sheet.getPhysicalNumberOfRows();
			 int cols = 0; 
			 int tmp = 0;
			 int cnt = 0;
			 
			 //trick to get the correct number of columns
			 for(int i = 0; i < 10 || i < rows; i++) {
				 row = sheet.getRow(i);
				 if(row != null) {
					 tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					 if(tmp > cols) cols = tmp;
				 }
			 }
			 
			 System.out.println(cnt);
			 for(int r = 0; r < rows; r++) {
				 cnt++;
				 row = sheet.getRow(r);
				 StringBuffer sbOracle = new StringBuffer();
				 StringBuffer sbPG = new StringBuffer();
				 
				 sbOracle.append("INSERT INTO ISW_ORGANIZATION (ID, UNIT_CODE, ORGANIZATION_NAME, AGENCY_ID, IS_DISPATCH_CENTER, IS_LOCAL, IS_STANDARD, IS_ACTIVE,  TRANSFERABLE_IDENTITY) ");
				 sbOracle.append(" VALUES (SEQ_ORGANIZATION.nextVal, ");
				 
				 sbPG.append("INSERT INTO ISW_ORGANIZATION (ID, UNIT_CODE, ORGANIZATION_NAME, AGENCY_ID, IS_DISPATCH_CENTER, IS_LOCAL, IS_STANDARD, IS_ACTIVE,  TRANSFERABLE_IDENTITY) ");
				 sbPG.append(" VALUES (nextVal('SEQ_ORGANIZATION'), ");
				 
				 
				 if(row != null) {
					 for(int c = 0; c < cols; c++) {
						 cell = row.getCell((short)c);
						 if(cell != null) {
							 switch (c) {
							 	case 0:
							 		
							 		break;
							 	case 1:
							 		sbOracle.append("'" +  generateCellString(cell) + "', ");
							 		sbPG.append("'" +  generateCellString(cell) + "', ");
							 		break;
							 	case 2:
							 		sbOracle.append("'" +generateCellString(cell) + "', ");
							 		sbPG.append("'" +generateCellString(cell) + "', ");
							 		break;
							 	case 4:
							 		sbOracle.append("(select id from iswl_agency where agency_code = '" +   generateCellString(cell) + "'), ");
							 		sbPG.append("(select id from iswl_agency where agency_code = '" +   generateCellString(cell) + "'), ");
							 		break;
							 	case 5:
							 		if (generateCellString(cell).equalsIgnoreCase("YES")){
							 			sbOracle.append("1, ");
							 			sbPG.append("TRUE, ");
							 		}else{
							 			sbOracle.append("0, ");
							 			sbPG.append("FALSE, ");
							 		}
							 		break;
							 	default:
							 		break;
							 }
						 }
					 }
				 }
				 
				 sbOracle.append("0, 1, 'Y', '"); 
				 sbPG.append("FALSE, TRUE, 'Y', '"); 
				 
				 String sUuid = AbstractXmlTransferExporterContext.generateUuid2("ISW_ORGANIZATION").toUpperCase();
				 sUuid = sUuid.replaceAll("-", "");
				 
				 sbOracle.append(sUuid + "');");
				 sbPG.append(sUuid + "');");
				 
				 oracleUnits.add(sbOracle.toString());
				 pgUnits.add(sbPG.toString());
				 
//				 System.out.println(sb.toString());
			 }
			 
			 for(String sRec : oracleUnits){
				 System.out.println(sRec);
			 }
			 
			 System.out.println("POSTGRES");
			 for(String sRec : pgUnits){
				 System.out.println(sRec);
			 }
			 
			 System.out.println(cnt);
			 fis.close();
		 }catch (Exception e) {
			 e.printStackTrace();
		 } 
		 
		 return null;
	 }
	 
	 private static String generateCellString(Cell cell){
		 if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
			 return cell.getStringCellValue().toUpperCase();
	 }else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
		 System.out.println(String.valueOf(cell.getNumericCellValue()).toUpperCase());
	 }
		 return "";
	 }
	 
}
