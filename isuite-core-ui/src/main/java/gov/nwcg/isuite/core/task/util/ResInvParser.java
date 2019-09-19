package gov.nwcg.isuite.core.task.util;

import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;
import gov.nwcg.isuite.framework.xml.XmlHandler;
import gov.nwcg.isuite.framework.xml.XmlSchemaTypeEnum;
import gov.nwcg.isuite.xml.ross.Dataset;
import gov.nwcg.isuite.xml.ross.Row;
import gov.nwcg.isuite.xml.ross.Row.Value;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ResInvParser {
	public Collection<String> pdcNames = new ArrayList<String>();
	public Collection<String> orgNames = new ArrayList<String>();
	public HashMap<String, Collection<ResourceVo>> pdcResourceVoMap = new HashMap<String,Collection<ResourceVo>>();
	public int resourceCount=0;
	
	public void getResourceVos(String xmlFile,String xsdBasePath) throws Exception {
		
		try{
			XmlHandler xmlHandler = new XmlHandler(XmlSchemaTypeEnum.ROSS_XML_DATA, xsdBasePath);
			
			Dataset rootNode = (Dataset)xmlHandler.unmarshall(new File(xmlFile));
			
			for(Row row : rootNode.getData().getRow()){
				ResourceVo vo = new ResourceVo();
				String pdc="";
				
				boolean processResource=true;
				
				int i = 0;
				for(Value value : row.getValue()){
					i++;
					String fieldVal="";
					if(null != value && null != value.getContent())
						fieldVal=(String)(value.getContent());
					
					switch(i){
						case 1: // ross resid
							vo.setRossResId(0L);
							if(StringUtility.hasValue(fieldVal)){
								try{
									Long rossResId=TypeConverter.convertToLong(fieldVal);
									vo.setRossResId(rossResId);
								}catch(Exception ee){
									
								}
							}
							break;
						case 2: // res name
							vo.setResourceName("");
							if(StringUtility.hasValue(fieldVal))
								vo.setResourceName(fieldVal.trim().toUpperCase());
							break;
						case 3: // last name
							vo.setLastName("");
							if(StringUtility.hasValue(fieldVal)){
								vo.setLastName(fieldVal.trim().toUpperCase());
								vo.setPerson(true);
							}
							break;
						case 4: // first name
							vo.setFirstName("");
							if(StringUtility.hasValue(fieldVal)){
								vo.setFirstName(fieldVal.trim().toUpperCase());
								vo.setPerson(true);
							}
							break;
						case 5: // disp org unit code
							OrganizationVo pdcVo = new OrganizationVo();
							if(StringUtility.hasValue(fieldVal)){
								pdcVo.setUnitCode(fieldVal.trim().toUpperCase());
								vo.setPrimaryDispatchCenterVo(pdcVo);

								pdc=pdcVo.getUnitCode();
								
								if(!pdcNames.contains(pdcVo.getUnitCode())){
									pdcNames.add(pdcVo.getUnitCode());
								}
							}else{
								processResource=false;
							}
							break;
						case 6: // prov org unit code
							OrganizationVo orgVo = new OrganizationVo();
							if(StringUtility.hasValue(fieldVal)){
								orgVo.setUnitCode(fieldVal.trim().toUpperCase());
								vo.setOrganizationVo(orgVo);
							}else{
								processResource=false;
							}
							break;
						case 7: // catalog item name
							KindVo kindVo = new KindVo();
							if(StringUtility.hasValue(fieldVal)){
								kindVo.setDescription(fieldVal.trim().toUpperCase());
								vo.setKindVo(kindVo);
							}
							break;
					}
				}

				if(processResource==true){
					this.addToHashMap(pdc, vo);
					resourceCount++;
				}
			}
			
		}catch(Exception e){
			throw e;
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
