package gov.nwcg.isuite.framework.customreports.enumerators;

import gov.nwcg.isuite.core.vo.CustomReportColumnAggregateVo;
import gov.nwcg.isuite.framework.customreports.fieldaggregators.AbstractFieldAggregator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum FieldAggregatorTypeEnum {
	
	BOOLEAN_COUNT("BOOLEAN_COUNT","COUNT","BOOLEAN","BooleanFieldAggregator")
	
	,CURRENCY_AVG("CURRENCY_AVG","AVERAGE","CURRENCY","CurrencyFieldAggregator")
	,CURRENCY_COUNT("CURRENCY_COUNT","COUNT","CURRENCY","CurrencyFieldAggregator")
	,CURRENCY_MAX("CURRENCY_MAX","MAXIMUM","CURRENCY","CurrencyFieldAggregator")
	,CURRENCY_MEDIAN("CURRENCY_MEDIAN","MEDIAN","CURRENCY","CurrencyFieldAggregator")
	,CURRENCY_MIN("CURRENCY_MIN","MINIMUM","CURRENCY","CurrencyFieldAggregator")
	,CURRENCY_SUM("CURRENCY_SUM","SUM","CURRENCY","CurrencyFieldAggregator")
	
	,DATE_COUNT("DATE_COUNT","COUNT","DATE","DateFieldAggregator")
	,DATE_MAX("DATE_MAX","MAXIMUM","DATE","DateFieldAggregator")
	,DATE_MEDIAN("DATE_MEDIAN","MEDIAN","DATE","DateFieldAggregator")
	,DATE_MIN("DATE_MIN","MINIMUM","DATE","DateFieldAggregator")
	
	,NUMBER_AVG("NUMBER_AVG","AVERAGE","NUMBER","NumberFieldAggregator")
	,NUMBER_COUNT("NUMBER_COUNT","COUNT","NUMBER","NumberFieldAggregator")
	,NUMBER_MAX("NUMBER_MAX","MAXIMUM","NUMBER","NumberFieldAggregator")
	,NUMBER_MEDIAN("NUMBER_MEDIAN","MEDIAN","NUMBER","NumberFieldAggregator")
	,NUMBER_MIN("NUMBER_MIN","MINIMUM","NUMBER","NumberFieldAggregator")
	,NUMBER_SUM("NUMBER_SUM","SUM","NUMBER","NumberFieldAggregator")
	
	,TEXT_COUNT("TEXT_COUNT","COUNT","STRING","TextFieldAggregator")
	
	,TIME_COUNT("TIME_COUNT","COUNT","TIME","TimeFieldAggregator")
	,TIME_MAX("TIME_MAX","MAXIMUM","TIME","TimeFieldAggregator")
	,TIME_MEDIAN("TIME_MEDIAN","MEDIAN","TIME","TimeFieldAggregator")
	,TIME_MIN("TIME_MIN","MINIMUM","TIME","TimeFieldAggregator")
	;
   
	private String aggregateType="";
	private String displayName="";
	private String dataType="";
	private String aggregatorClassName;

   	private static final String PACKAGE_PREFIX="gov.nwcg.isuite.framework.customreports.fieldaggregators.";
   
   	FieldAggregatorTypeEnum(String aggregateType,String displayName,String dataType,String aggregatorClassName){
   		this.displayName=displayName;
   		this.aggregateType=aggregateType;
   		this.dataType=dataType;
   		this.aggregatorClassName=aggregatorClassName;
   	}

	public static AbstractFieldAggregator getFieldAggregator(String aggregateType, String databaseType) throws Exception {
	   for(FieldAggregatorTypeEnum fieldAggregator : FieldAggregatorTypeEnum.values()){
		   if(fieldAggregator.aggregateType.equals(aggregateType)){
				Class targetClass=Class.forName(PACKAGE_PREFIX + fieldAggregator.aggregatorClassName);
				AbstractFieldAggregator aggregatorClass=(AbstractFieldAggregator)targetClass.newInstance();
				aggregatorClass.setAggregateType(fieldAggregator.aggregateType);
				aggregatorClass.setDatabaseType(databaseType);
				
				return aggregatorClass;
		   }
	   }
	   
	   return null;
	}
	
	/**
	 * This method is used primarily to test this Enum. It returns back a list of 
	 * Aggregators based on the datatype of the FieldAggregatorTypeEnum.
	 * @param dataType
	 * @param databaseType
	 * @return
	 * @throws Exception
	 */
	public static List<AbstractFieldAggregator> getFieldAggregatorsByDataType(String dataType, String databaseType) throws Exception {
		List<AbstractFieldAggregator> aggregatorList = new ArrayList<AbstractFieldAggregator>();
		
		for(FieldAggregatorTypeEnum fieldAggregator : FieldAggregatorTypeEnum.values()){
		   if(fieldAggregator.dataType.equals(dataType)){
			   	Class targetClass=Class.forName(PACKAGE_PREFIX + fieldAggregator.aggregatorClassName);
				AbstractFieldAggregator aggregatorClass=(AbstractFieldAggregator)targetClass.newInstance();
				aggregatorClass.setAggregateType(fieldAggregator.aggregateType);
				aggregatorClass.setDatabaseType(databaseType);
				
				aggregatorList.add(aggregatorClass);
		   }
		}
		return aggregatorList;
	}
	
	/**
	 * Returns Collection of CustomReportColumnAggregateVo objects. This will be called by flex to 
	 * populate the drop down showing the aggregating options available to a datatype.
	 * @param dataType
	 * @return
	 */
	public static Collection<CustomReportColumnAggregateVo> getCRColumnAggregateVos(String dataType) {
		Collection<CustomReportColumnAggregateVo> vos = new ArrayList<CustomReportColumnAggregateVo>();
		
		for(FieldAggregatorTypeEnum fieldAggregator : FieldAggregatorTypeEnum.values()){
		   if(fieldAggregator.dataType.equals(dataType)){
				vos.add(new CustomReportColumnAggregateVo(fieldAggregator.aggregateType, fieldAggregator.displayName, fieldAggregator.dataType));
		   }
		}
		return vos;
	}
	
	/**
	 * Returns all values in this enum.
	 * @return Collection of CustomReportColumnAggregateVo
	 */
	public static Collection<CustomReportColumnAggregateVo> getCRColumnAggregateVos() {
		Collection<CustomReportColumnAggregateVo> vos = new ArrayList<CustomReportColumnAggregateVo>();
		
		for(FieldAggregatorTypeEnum fieldAggregator : FieldAggregatorTypeEnum.values()){
			vos.add(new CustomReportColumnAggregateVo(fieldAggregator.aggregateType, fieldAggregator.displayName, fieldAggregator.dataType));
		}
		return vos;
	}
	
	/**
	 * @param aggregateType
	 * @return
	 */
	public static CustomReportColumnAggregateVo getCRColumnAggregateVo(String aggregateType) {
		FieldAggregatorTypeEnum fieldAggregatorTypeEnum = FieldAggregatorTypeEnum.valueOf(aggregateType);
		CustomReportColumnAggregateVo vo = CustomReportColumnAggregateVo.getInstance(fieldAggregatorTypeEnum);
		
		return vo;
	}
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the aggregateType
	 */
	public String getAggregateType() {
		return aggregateType;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @return the aggregatorClassName
	 */
	public String getAggregatorClassName() {
		return aggregatorClassName;
	}
}
