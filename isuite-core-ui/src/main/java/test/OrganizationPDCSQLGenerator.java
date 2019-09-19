package test;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class OrganizationPDCSQLGenerator {
	
	private static final String FILE_PATH = "/Users/Karen/Desktop/ReferenceData/UnitID.xlsx";

	public OrganizationPDCSQLGenerator() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Collection<String> units = getUnitsFromExcel();
	}
	
	 private static Collection<String> getUnitsFromExcel() {
		 Collection<String> orgpdcs = new ArrayList<String>();
		 
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
			 
//			 System.out.println(cnt);
			 for(int r = 0; r < rows; r++) {
				 cnt++;
				 row = sheet.getRow(r);
				 //oracle version
				 String sql = "INSERT INTO ISW_ORGANIZATION_PDC (ORGANIZATION_ID, PDC_ID) VALUES " +
				 				"((SELECT ID FROM ISW_ORGANIZATION WHERE UNIT_CODE = '{0}' AND IS_DISPATCH_CENTER = 0), " +
				 				"(SELECT ID FROM ISW_ORGANIZATION WHERE UNIT_CODE = '{1}' AND IS_DISPATCH_CENTER = 1));";
				 
				 //pg version
//				 String sql = "INSERT INTO ISW_ORGANIZATION_PDC (ORGANIZATION_ID, PDC_ID) VALUES " +
//	 				"((SELECT ID FROM ISW_ORGANIZATION WHERE UNIT_CODE = '{0}' AND IS_DISPATCH_CENTER = FALSE), " +
//	 				"(SELECT ID FROM ISW_ORGANIZATION WHERE UNIT_CODE = '{1}' AND IS_DISPATCH_CENTER = TRUE));";
				 
				 if(row != null) {
					 for(int c = 0; c < cols; c++) {
						 cell = row.getCell((short)c);
						 if(cell != null) {
							 switch (c) {
							 	case 1:
							 		sql = sql.replace("{0}", generateCellString(cell));
							 		break;
							 	case 7:
							 		sql = sql.replace("{1}", generateCellString(cell));
							 		break;
							 	default:
							 		break;
							 }
						 }
					 }
				 }
				 
				 orgpdcs.add(sql);
			 }
			 
			 for(String sRec : orgpdcs){
				 System.out.println(sRec);
			 }
			 
//			 System.out.println(cnt);
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
