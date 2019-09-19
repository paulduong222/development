package gov.nwcg.isuite.core.task.util;

import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ResInvParser3 {
	public Collection<String> pdcNames = new ArrayList<String>();
	public Collection<String> orgNames = new ArrayList<String>();
	public HashMap<String, Collection<ResourceVo>> pdcResourceVoMap = new HashMap<String,Collection<ResourceVo>>();
	public int resourceCount=0;
	
	public int getResourceVos(String file, String filetype,Collection<String> logEntries) throws Exception {
		logEntries.add("ResInvParser3.getResourceVos() start");
		XSSFWorkbook myWorkBook = null;
		XSSFSheet mySheet = null;
		
		try{
			File myFile = new File(file);
            FileInputStream fis = new FileInputStream(myFile);
    		logEntries.add("ResInvParser3.getResourceVos() after init FileInputStream");

            // Finds the workbook instance for XLSX file
            myWorkBook = new XSSFWorkbook (fis);
    		logEntries.add("ResInvParser3.getResourceVos() after init XSSFWorkbook");
           
            // Return first sheet from the XLSX workbook
            mySheet = myWorkBook.getSheetAt(0);
    		logEntries.add("ResInvParser3.getResourceVos() after init myWorkBook.getSheetAt(0)");

    		pdcNames = new ArrayList<String>();
    		orgNames = new ArrayList<String>();
    		pdcResourceVoMap = new HashMap<String,Collection<ResourceVo>>();
    		
            // Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = mySheet.iterator();
    		logEntries.add("ResInvParser3.getResourceVos() after mySheet.iterator()");
           
            // Traversing over each row of XLSX file
            int rowcnt=0;
            int totalCellCount=0;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                int lastCellNum=(int)row.getLastCellNum();
                
                if(rowcnt==1){
                	// total cell count, depending on how the .xlsx file 
                	// was created, the cellIterator will skip null cells
                    Iterator<Cell> cellIteratorTmp = row.cellIterator();
                    while (cellIteratorTmp.hasNext() && totalCellCount<lastCellNum) {
                        Cell cell = cellIteratorTmp.next();
                    	totalCellCount++;
                    }
                }

            	if(rowcnt>0){
            		//logEntries.add("ResInvParser3.getResourceVos() processig row:"+rowcnt);
    				ResourceVo vo = new ResourceVo();
    				String pdc="";
    				boolean processResource=true;
                	
                    // For each row, iterate through each columns
            		//logEntries.add("ResInvParser3.getResourceVos() before row.cellIterator()");
                    Iterator<Cell> cellIterator = row.cellIterator();
            		//logEntries.add("ResInvParser3.getResourceVos() after row.cellIterator()");
                    
                    int cellcnt=1;
                    while (cellIterator.hasNext() && cellcnt<(totalCellCount+1)) {

                        Cell cell = cellIterator.next();
                		//logEntries.add("ResInvParser3.getResourceVos() after cellIterator.next() " + cell);
                        String sval="";
                        double dval=0.0;
                        
                        switch (cell.getCellType()) {
    	                    case Cell.CELL_TYPE_STRING:
    	                		//logEntries.add("ResInvParser3.getResourceVos() cell type STRING");
    	                    	sval=cell.getStringCellValue();
    	                		//logEntries.add("ResInvParser3.getResourceVos() cell type STRING " + sval);
    	                    	if(StringUtility.hasValue(sval)){
    	                    		sval=sval.trim().toUpperCase();
    	                    	}else{
    	                    		sval="";
    	                    	}
    	                        break;
    	                    case Cell.CELL_TYPE_NUMERIC:
    	                		//logEntries.add("ResInvParser3.getResourceVos() cell type NUMERIC");
    	                    	dval=cell.getNumericCellValue();
    	                		//logEntries.add("ResInvParser3.getResourceVos() cell type NUMERIC " + dval);
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
	                        	vo.setResourceName(StringUtility.fixStringSpecialChars(sval, 55));
	                        	break;
	                        case 3: // LASTNAME or PDC depending totalCellCount
	                        	if(filetype.equalsIgnoreCase("OH") || totalCellCount>5){
		                        	vo.setLastName(StringUtility.fixStringSpecialChars(sval, 35));
		                        	if(StringUtility.hasValue(sval))
										vo.setPerson(true);
	                        	}else{
	                        		vo.setLastName("");
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
	                        	}
	                        	break;
	                        case 4: // FIRSTNAME or UNITCODE depending on totalCellCount
	                        	if(filetype.equalsIgnoreCase("OH") || totalCellCount>5){
		                        	if(StringUtility.hasValue(sval))
										vo.setPerson(true);
		                        	vo.setFirstName(StringUtility.fixStringSpecialChars(sval, 35));
	                        	}else{
	                        		vo.setFirstName("");
									OrganizationVo orgVo = new OrganizationVo();
									if(StringUtility.hasValue(sval)){
										orgVo.setUnitCode(sval);
										vo.setOrganizationVo(orgVo);
									}else{
										processResource=false;
									}
	                        	}
	                        	break;
	                        case 5: // PDC or CatalogItem  
	                        	if(filetype.equalsIgnoreCase("OH") || totalCellCount>5){
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
	                        	}else{
									KindVo kindVo = new KindVo();
									if(StringUtility.hasValue(sval)){
										kindVo.setDescription(sval);
										vo.setKindVo(kindVo);
									}
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
                		//logEntries.add("ResInvParser3.getResourceVos() before addToHashMap");
    					this.addToHashMap(pdc, vo);
                		//logEntries.add("ResInvParser3.getResourceVos() after addToHashMap");
    					resourceCount++;
    				}
                    
                }
                
                rowcnt++;
            }
			
    		logEntries.add("ResInvParser3.getResourceVos() Finish Rowcount: " + rowcnt);
		}catch(Exception e){
    		logEntries.add("ResInvParser3.getResourceVos() Execption: " + e.getMessage());
			throw e;
		}finally{
			if(null != mySheet)
				mySheet=null;
			if(null != myWorkBook)
				myWorkBook=null;
    		logEntries.add("ResInvParser3.getResourceVos() Finally end ");
		}
		return 1;
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

	public Collection<String> getDataInsertSqls() {
		Collection<String> sqls = new ArrayList<String>();

		sqls.add("truncate table isw_resinv_file_data");
		
		Date sysDate = Calendar.getInstance().getTime();
		
		for(String pdcName : pdcNames){
			Collection<ResourceVo> pdcResourceVos = getResourceVosForPdc(pdcName);

			if(CollectionUtility.hasValue(pdcResourceVos)){
				for(ResourceVo vo : pdcResourceVos){
					String pdcCode="";
					String unitCode="";
					String catalogDesc="";
					if(null != vo.getPrimaryDispatchCenterVo()
							&& StringUtility.hasValue(vo.getPrimaryDispatchCenterVo().getUnitCode())){
						pdcCode=vo.getPrimaryDispatchCenterVo().getUnitCode();
					}
					if(null != vo.getOrganizationVo()
							&& StringUtility.hasValue(vo.getOrganizationVo().getUnitCode())){
						unitCode=vo.getOrganizationVo().getUnitCode();
					}
					if(null != vo.getKindVo() && StringUtility.hasValue(vo.getKindVo().getDescription())){
						catalogDesc=vo.getKindVo().getDescription();
					}
					String sql="INSERT INTO ISW_RESINV_FILE_DATA " +
							   "(ROSS_RES_ID,RES_NAME,LAST_NAME,FIRST_NAME,RES_DISP_ORG_UNIT_CODE,RES_PROV_UNIT_CODE,CATALOG_ITEM_NAME,ORG_ID,PDC_ID,KIND_ID) " + 
							   "VALUES (" +
							   "" + vo.getRossResId() + 
							   ",'"+vo.getResourceName()+"'"+
							   ",'"+vo.getLastName()+"'"+
							   ",'"+vo.getFirstName()+"'"+
							   ",'"+pdcCode+"'"+
							   ",'"+unitCode+"'"+
							   ",'"+catalogDesc+"'"+
							   ","+(StringUtility.hasValue(unitCode) ? "(select min(id) from isw_organization where is_dispatch_center=0 and unit_code='"+unitCode+"')" : 0L)+""+
							   ","+(StringUtility.hasValue(pdcCode) ? "(select min(id) from isw_organization where is_dispatch_center=1 and unit_code='"+pdcCode+"')" : 0L)+""+
							   ","+(StringUtility.hasValue(catalogDesc) ? "(select min(id) from iswl_kind where description='"+catalogDesc+"')" : 0L)+""+
							   ") ";
					sqls.add(sql);
				}
				
			}
		}

		return sqls;
	}
	
}
