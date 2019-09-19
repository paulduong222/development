package gov.nwcg.isuite.core.task.util;

import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ResInvParser2 {
	public Collection<String> pdcNames = new ArrayList<String>();
	public Collection<String> orgNames = new ArrayList<String>();
	public HashMap<String, Collection<ResourceVo>> pdcResourceVoMap = new HashMap<String,Collection<ResourceVo>>();
	public int resourceCount=0;
	
	public void getResourceVos(String file) throws Exception {
		XSSFWorkbook myWorkBook = null;
		XSSFSheet mySheet = null;
		
		try{
			File myFile = new File(file);
            FileInputStream fis = new FileInputStream(myFile);

            // Finds the workbook instance for XLSX file
            myWorkBook = new XSSFWorkbook (fis);
           
            // Return first sheet from the XLSX workbook
            mySheet = myWorkBook.getSheetAt(0);
           
            // Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = mySheet.iterator();
           
            // Traversing over each row of XLSX file
            int rowcnt=0;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if(rowcnt>0){
    				ResourceVo vo = new ResourceVo();
    				String pdc="";
    				boolean processResource=true;
                	
                    // For each row, iterate through each columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    
                    int cellcnt=1;
                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();
                        String sval="";
                        double dval=0.0;
                        
                        switch (cell.getCellType()) {
    	                    case Cell.CELL_TYPE_STRING:
    	                    	sval=cell.getStringCellValue();
    	                    	if(StringUtility.hasValue(sval)){
    	                    		sval=sval.trim().toUpperCase();
    	                    	}else{
    	                    		sval="";
    	                    	}
    	                        break;
    	                    case Cell.CELL_TYPE_NUMERIC:
    	                    	dval=cell.getNumericCellValue();
    	                        break;
    	                    case Cell.CELL_TYPE_BOOLEAN:
    	                        //System.out.print(cell.getBooleanCellValue() + "\t");
    	                        break;
    	                    default :
                        }
                        
                        switch(cellcnt){
	                        case 1: // RESID
	                        	String s=String.valueOf(dval);
	                        	if(StringUtility.hasValue(s)){
	                        		int a=s.indexOf(".");
	                        		if(a>0){
	                        			s=s.substring(0, a);
	                        		}
	                        	}
	                        	vo.setRossResId(TypeConverter.convertToLong(s));
	                        	break;
	                        case 2: // RESNAME
	                        	vo.setResourceName(sval);
	                        	break;
	                        case 3: // LASTNAME
	                        	vo.setLastName(sval);
	                        	if(StringUtility.hasValue(sval))
									vo.setPerson(true);
	                        	break;
	                        case 4: // FIRSTNAME
	                        	vo.setFirstName(sval);
	                        	break;
	                        case 5: // PDC
								OrganizationVo pdcVo = new OrganizationVo();
								if(StringUtility.hasValue(sval)){
									pdcVo.setUnitCode(sval);
									vo.setPrimaryDispatchCenterVo(pdcVo);

									pdc=pdcVo.getUnitCode();
									
									if(!pdcNames.contains(pdcVo.getUnitCode())){
										pdcNames.add(pdcVo.getUnitCode());
									}
								}else{
									processResource=false;
								}
	                        	
	                        	break;
	                        case 6: // ORG
								OrganizationVo orgVo = new OrganizationVo();
								if(StringUtility.hasValue(sval)){
									orgVo.setUnitCode(sval);
									vo.setOrganizationVo(orgVo);
								}else{
									processResource=false;
								}
	                        	break;
	                        case 7: // ITEM NAME
								KindVo kindVo = new KindVo();
								if(StringUtility.hasValue(sval)){
									kindVo.setDescription(sval);
									vo.setKindVo(kindVo);
								}
	                        	break;
                        }
                        
                        cellcnt++;
                    }

                    if(processResource==true){
    					this.addToHashMap(pdc, vo);
    					resourceCount++;
    				}
                    
                }
                
                rowcnt++;
            }
			
		}catch(Exception e){
			throw e;
		}finally{
			if(null != mySheet)
				mySheet=null;
			if(null != myWorkBook)
				myWorkBook=null;
		}
	}

	private void addToHashMap(String pdc, ResourceVo vo){
		if(this.pdcResourceVoMap.containsKey(pdc)){
			Collection<ResourceVo> vos = (Collection<ResourceVo>)this.pdcResourceVoMap.get(pdc);
			vos.add(vo);
			this.pdcResourceVoMap.put(pdc, vos);
		}else{
			Collection<ResourceVo> vos = new ArrayList<ResourceVo>();
			vos.add(vo);
			this.pdcResourceVoMap.put(pdc, vos);
		}
	}

	public Collection<ResourceVo> getResourceVosForPdc(String pdc){
		if(this.pdcResourceVoMap.containsKey(pdc)){
			Collection<ResourceVo> vos = (Collection<ResourceVo>)this.pdcResourceVoMap.get(pdc);
			return vos;
		}
		
		return new ArrayList<ResourceVo>();
	}
	
	public void removeFromMap(String pdc){
		try{
			if(this.pdcResourceVoMap.containsKey(pdc)){
				this.pdcResourceVoMap.remove(pdc);
				
			}
		}catch(Exception smother){}
	}

	public static void main(String[] args){
		String zipFile="c:/workspace/isuite-core/EISuiteResourceImportOH.xml.zip";
		
		String xsdBasePath = "c:\\workspace\\isuite-core\\src\\main\\java\\resources\\common\\xsd\\";
		String xmlFile="c:/workspace/isuite-core/ResourceImportNonOH.xml";

		ResInvParser parser = new ResInvParser();
		try{
			FileUtil.deleteFile("c:/workspace/isuite-core/unzip/resinv.xml");
			FileUtil.unzipFile(zipFile, "c:/workspace/isuite-core/unzip/resinv.xml");
			
			parser.getResourceVos(xmlFile, xsdBasePath);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
