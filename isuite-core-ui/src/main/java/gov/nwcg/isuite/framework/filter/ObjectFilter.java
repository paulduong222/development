package gov.nwcg.isuite.framework.filter;

import gov.nwcg.isuite.core.filter.CountryCodeFilter;
import gov.nwcg.isuite.core.filter.impl.CountryCodeFilterImpl;
import gov.nwcg.isuite.core.vo.CountryCodeVo;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;

public class ObjectFilter  {
	private Class<?> voClass=null;
	private PropertyDescriptor[] descriptors = null;
	
	public ObjectFilter(Class<?> voClass){
		this.voClass=voClass;
	}
	
	public Object filterVo(Object source, Collection<FilterCriteria> criteria) throws Exception {

		descriptors = PropertyUtils.getPropertyDescriptors(voClass);
		
		try{
			if(isMatch(source,criteria)){
				return source;
			}
		}catch(Exception e){
			throw e;
		}
		
		return null;
	}
	
	private boolean isMatch(Object src, Collection<FilterCriteria> criteria) throws Exception {

		for(FilterCriteria crit : criteria){
			if(null != crit){
				Object val = PropertyUtils.getProperty(src, crit.getField());
				
				if(null != val) {
					switch(crit.getType()){
						case FilterCriteria.TYPE_ILIKE:
							String sval = (String)val;
							String cval = (String)crit.getValue();
							if(sval.toUpperCase().startsWith(cval.toUpperCase())){
								return true;
							}
							break;
						case FilterCriteria.TYPE_EQUAL:
							if(crit.getFieldType()==FilterCriteria.FIELD_LONG){
								Long lval = TypeConverter.convertToLong(val);
								Long clval = TypeConverter.convertToLong(crit.getValue());
								
								if(lval.compareTo(clval)==0){
									return true;
								}
							}
							
							break;
					}
				}
			}
		}
		
		return false;
	}
	
	public static void main(String[] args ){
		try{
			Collection<CountryCodeVo> vos = new ArrayList<CountryCodeVo>();
			CountryCodeVo vo1 = new CountryCodeVo();
			vo1.setCountryAbbreviation("ABC");
			vo1.setCountryName("Country 1");
			vos.add(vo1);
			
			CountryCodeVo vo2 = new CountryCodeVo();
			vo2.setCountryAbbreviation("ABCD");
			vo2.setCountryName("Country 2");
			vos.add(vo2);

			CountryCodeVo vo3 = new CountryCodeVo();
			vo3.setCountryAbbreviation("DEF");
			vo3.setCountryName("Other country 3");
			vos.add(vo3);
			
			CountryCodeFilter filter= new CountryCodeFilterImpl();
			filter.setCountryCodeName("country");
			
			Collection<FilterCriteria> criteria = CountryCodeVo.getVoFilterCriteria(filter);
			
			ObjectFilter ofilter = new ObjectFilter(CountryCodeVo.class);

			for(CountryCodeVo src : vos){
				CountryCodeVo ccvo = (CountryCodeVo)ofilter.filterVo(src, criteria);
				
				//if(null != ccvo)
				//	System.out.println(ccvo.getCountryName());
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
