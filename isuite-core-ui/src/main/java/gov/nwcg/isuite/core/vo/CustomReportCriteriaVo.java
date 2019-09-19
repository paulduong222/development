package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import gov.nwcg.isuite.core.domain.CustomReport;
import gov.nwcg.isuite.core.domain.CustomReportFilter;
import gov.nwcg.isuite.core.domain.impl.CustomReportFilterImpl;
import gov.nwcg.isuite.core.domain.impl.CustomReportImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.customreports.enumerators.ComparisonOperatorEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.CriteriaEvaluatorEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.CriteriaTermEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.CustomReportsDataTypeEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.CustomReportsTargetTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class CustomReportCriteriaVo extends AbstractVo implements PersistableVo {
	
	private static final String TARGET_LIST_STRING_SEPARATOR = ",";
	private static final String BLOCK = "B";
	
	private Long customReportId;
	
	private Long groupId;
	private Long parentGroupId;
	
	private Long tempId;
	private Integer displayOrder; 
	
	private String connective;
	private String type;
	
	private CustomReportViewFieldVo sourceViewFieldVo;
	
	private String sourceFieldDisplayName;
	private String sourceFieldSqlName;
	private String sourceFieldDataType;
	
	private String sourceInputValue;
	
	private CustomReportCriteriaEvaluatorVo sourceEvaluator;
	
	private String operatorType; // Operand in the db
	private CustomReportCriteriaOperatorVo operatorVo; // Not in the db
	
	private String targetType;
	private String targetValue;
	private String targetValue2;
	
	private CustomReportViewFieldVo targetViewFieldVo;
	
	private String targetFieldDisplayName;
	private String targetFieldSqlName;
	
	private CustomReportCriteriaTermVo targetTermVo;
	private CustomReportCriteriaEvaluatorVo targetEvaluator;
	
	private Collection<String> targetList = new ArrayList<String>();
	
	private String criteriaDescription;
	
	private Collection<CustomReportCriteriaVo> children = null;
	
	public CustomReportCriteriaVo() {
		super();
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static CustomReportCriteriaVo getInstance(CustomReportFilter entity, boolean cascadable) throws Exception {
		CustomReportCriteriaVo vo = new CustomReportCriteriaVo();

		if(null == entity)
			throw new Exception("Unable to create CustomReportCriteriaVo from null CustomReportFilter entity.");
		
		vo.setId(entity.getId());
		System.out.println("cr criterion vo.id " + vo.getId());
		if(cascadable){
			vo.setCustomReportId(entity.getCustomReportId());
			
			if(entity.getCriteriaType().equalsIgnoreCase(BLOCK)) {
				vo.setGroupId(entity.getId());
			}
			
			vo.setParentGroupId(entity.getParentFilterId());
			vo.setDisplayOrder(entity.getDisplayOrder().intValue());
			
			if(CollectionUtility.hasValue(entity.getChildCustomReportFilters())) {
				vo.setChildren(CustomReportCriteriaVo.getInstances(entity.getChildCustomReportFilters(), true));
			}
			
			vo.setConnective(entity.getConnective());
			vo.setType(entity.getCriteriaType().equalsIgnoreCase("B") ? "BLOCK" : "STATEMENT"); 
			
			if(null != entity.getSourceField()) {
				vo.setSourceViewFieldVo(CustomReportViewFieldVo.getInstance(entity.getSourceField(), true));
			}
			
			vo.setSourceInputValue(entity.getSourceInputValue());
			
			if(StringUtility.hasValue(entity.getSourceEvaluatorType())) {
				vo.setSourceEvaluator(CriteriaEvaluatorEnum.getCriteriaEvaluatorVo(entity.getSourceEvaluatorType()));
			}
			
			if(StringUtility.hasValue(entity.getOperand())) {
				vo.setOperatorVo(ComparisonOperatorEnum.getCriteriaOperatorVo(entity.getOperand()));
			}
			
			vo.setTargetType(entity.getTargetType());
			vo.setTargetValue(entity.getTargetValue1());
			vo.setTargetValue2(entity.getTargetValue2());
			
			if(null != entity.getTargetField()) {
				vo.setTargetViewFieldVo(CustomReportViewFieldVo.getInstance(entity.getTargetField(), true));
			}
			
			if(StringUtility.hasValue(entity.getTargetTermType())) {
				vo.setTargetTermVo(CriteriaTermEnum.getCriteriaTermVo(entity.getTargetTermType()));
			}
			
			if(StringUtility.hasValue(entity.getTargetEvaluatorType())) {
				vo.setTargetEvaluator(CriteriaEvaluatorEnum.getCriteriaEvaluatorVo(entity.getTargetEvaluatorType()));
			}
			
			if(StringUtility.hasValue(entity.getTargetValueList())) {
				vo.setTargetListFromString(entity.getTargetValueList());
			}
			
			vo.setCriteriaDescription(CustomReportCriteriaVo.getCritDesc(vo));
			
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<CustomReportCriteriaVo> getInstances(Collection<CustomReportFilter> entities, boolean cascadable) throws Exception {
		Collection<CustomReportCriteriaVo> vos = new ArrayList<CustomReportCriteriaVo>();
		
		for(CustomReportFilter entity : entities) {
			vos.add(CustomReportCriteriaVo.getInstance(entity, cascadable));
		}
		
		Collections.sort((List<CustomReportCriteriaVo>)vos, new DisplayOrderComparator());
		return vos;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<CustomReportCriteriaVo> getHierarchicalInstances(Collection<CustomReportFilter> entities, boolean cascadable) throws Exception {
		Collection<CustomReportCriteriaVo> vos = new ArrayList<CustomReportCriteriaVo>();
		
		for(CustomReportFilter entity : entities) {
			if(entity.getParentFilterId() == null) {
				vos.add(CustomReportCriteriaVo.getInstance(entity, cascadable));
			}
		}
		
		Collections.sort((List<CustomReportCriteriaVo>)vos, new DisplayOrderComparator());
		
		return vos;
	}
	
	/**
	 * @param entity
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static CustomReportFilter toEntity(CustomReportFilter entity, CustomReportCriteriaVo vo, boolean cascadable,Persistable...persistables)throws Exception {
		if(null == entity){
			entity = new CustomReportFilterImpl();
		}
		
		entity.setId(vo.getId());

		if(cascadable){
			CustomReport customReport = (CustomReport)getPersistableObject(persistables,CustomReportImpl.class);
			if(null != customReport) {
				entity.setCustomReport(customReport);
			}
			
			if(LongUtility.hasValue(vo.getParentGroupId())) {
				CustomReportFilter parentFilter = (CustomReportFilter)getPersistableObject(persistables,CustomReportFilterImpl.class);
				if(null != parentFilter) {
					entity.setCustomReportFilter(parentFilter);
				}
			}
			
			entity.setDisplayOrder(vo.getDisplayOrder().shortValue());
			
			if(CollectionUtility.hasValue(vo.getChildren())) {
				entity.getChildCustomReportFilters().addAll(CustomReportCriteriaVo.toEntityList(vo.getChildren(), true, customReport, entity));
			}
			
			entity.setConnective(vo.getConnective());
			entity.setCriteriaType(vo.getType().equalsIgnoreCase("BLOCK") ? "B" : "S"); 
			
			if(null != vo.getSourceViewFieldVo()) {
				entity.setSourceField(CustomReportViewFieldVo.toEntity(vo.getSourceViewFieldVo(), false));
			}
			
			entity.setSourceInputValue(vo.getSourceInputValue());
			
			if(null != vo.getSourceEvaluator()) {
				entity.setSourceEvaluatorType(vo.getSourceEvaluator().getEvaluatorType());
			}
			
			if(null != vo.getOperatorVo()) {
				entity.setOperand(vo.getOperatorVo().getOperatorType());
			}
			
			entity.setTargetType(vo.getTargetType());
			entity.setTargetValue1(vo.getTargetValue());
			entity.setTargetValue2(vo.getTargetValue2());
			
			if(null != vo.getTargetViewFieldVo()) {
				entity.setTargetField(CustomReportViewFieldVo.toEntity(vo.getTargetViewFieldVo(), false));
			}
			
			if(null != vo.getTargetTermVo()) {
				entity.setTargetTermType(vo.getTargetTermVo().getTermType());
			}
			
			if(null != vo.getTargetEvaluator()) {
				entity.setTargetEvaluatorType(vo.getTargetEvaluator().getEvaluatorType());
			}
			
			if(CollectionUtility.hasValue(vo.getTargetList())) {
				entity.setTargetValueList(vo.getTargetListAsString());
			}
			
		}
		
		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<CustomReportFilter> toEntityList(Collection<CustomReportCriteriaVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<CustomReportFilter> entities = new ArrayList<CustomReportFilter>();

		for(CustomReportCriteriaVo vo : vos) {
			entities.add(CustomReportCriteriaVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	
	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static String getCritDesc(CustomReportCriteriaVo vo) throws Exception {
		String desc = "";
		
		if (vo.getType().equalsIgnoreCase("BLOCK")) {
			return "";
		}
		
		switch(CustomReportsDataTypeEnum.valueOf(vo.getSourceViewFieldVo().getDataType())) {
			case STRING:
				desc = CustomReportCriteriaVo.getStringCritDesc(vo);
				break;
			case DATE:
				desc = CustomReportCriteriaVo.getDateCritDesc(vo);
				break;
			case NUMBER:
				desc = CustomReportCriteriaVo.getNumberCritDesc(vo);
				break;
			case TIME:
				desc = CustomReportCriteriaVo.getTimeCritDesc(vo);
				break;
			case BOOLEAN:
				desc = CustomReportCriteriaVo.getBooleanCritDesc(vo);
				break;
			case CURRENCY:
				desc = CustomReportCriteriaVo.getCurrencyCritDesc(vo);
				break;
		}
		
		return desc;
	}
	
	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static String getStringCritDesc(CustomReportCriteriaVo vo) throws Exception {
		StringBuffer desc = new StringBuffer();
		
		//source evaluator
		switch(CriteriaEvaluatorEnum.valueOf(vo.getSourceEvaluator().getEvaluatorType())) {
			case AS_IS:
					desc.append(vo.getSourceViewFieldVo().getDisplayName());
					desc.append(" ");
				break;
			case AS_TEXT_LENGTH:
					desc.append("string length of ");
					desc.append(vo.getSourceViewFieldVo().getDisplayName());
					desc.append(" ");
				break;
		}
		
		//operator
		if(null != vo.getOperatorVo()) {
			desc.append(vo.getOperatorVo().getDisplayName());
			desc.append(" ");
		}
		
		//target conditions
		if(null != vo.getTargetType()) {
			switch(CustomReportsTargetTypeEnum.valueOf(vo.getTargetType())) {
				case INPUT_VALUE:
					desc.append("'");
					desc.append(vo.getTargetValue());
					desc.append("' ");
					break;
				case SELECT_FIELD:
					switch(CriteriaEvaluatorEnum.valueOf(vo.getSourceEvaluator().getEvaluatorType())) {
						case AS_IS:
							desc.append("Field[");
							desc.append(vo.getTargetViewFieldVo().getDisplayName());
							desc.append("] ");
							break;
					}
					
					break;
				case INPUT_LIST:
					desc.append("(");
					desc.append(vo.getTargetListAsString());
					desc.append(") ");
					break;
			}
		}
		
		return desc.toString();
	}
	
	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static String getDateCritDesc(CustomReportCriteriaVo vo) throws Exception {
		StringBuffer desc = new StringBuffer();
		
		//source evaluator
		switch(CriteriaEvaluatorEnum.valueOf(vo.getSourceEvaluator().getEvaluatorType())) {
			case MM_DD_YYYY:
				desc.append("format(");
				desc.append(vo.getSourceViewFieldVo().getDisplayName());
				desc.append(", mm/dd/yyyy) ");
				break;
			case MONTH:
				desc.append("format(");
				desc.append(vo.getSourceViewFieldVo().getDisplayName());
				desc.append(", month) ");
				break;
			case DAY:
				desc.append("format(");
				desc.append(vo.getSourceViewFieldVo().getDisplayName());
				desc.append(", day) ");
				break;
			case YEAR:
				desc.append("format(");
				desc.append(vo.getSourceViewFieldVo().getDisplayName());
				desc.append(", year) ");
				break;
			case PLUS_N_DAYS:
				desc.append(vo.getSourceViewFieldVo().getDisplayName());
				desc.append(" + ");
				desc.append(vo.getSourceInputValue());
				desc.append(" days ");
				break;
			case MINUS_N_DAYS:
				desc.append(vo.getSourceViewFieldVo().getDisplayName());
				desc.append(" - ");
				desc.append(vo.getSourceInputValue());
				desc.append(" days ");
				break;
			case PLUS_N_MONTHS:
				desc.append(vo.getSourceViewFieldVo().getDisplayName());
				desc.append(" + ");
				desc.append(vo.getSourceInputValue());
				desc.append(" months ");
				break;
			case MINUS_N_MONTHS:
				desc.append(vo.getSourceViewFieldVo().getDisplayName());
				desc.append(" - ");
				desc.append(vo.getSourceInputValue());
				desc.append(" months ");
				break;
		}
		
		//operator
		if(null != vo.getOperatorVo()) {
			desc.append(vo.getOperatorVo().getDisplayName());
			desc.append(" ");
		}
		
		//target conditions
		if(null != vo.getTargetType()) {
			
			switch(CustomReportsTargetTypeEnum.valueOf(vo.getTargetType())) {
				case INPUT_VALUE:
					desc.append(vo.getTargetValue());
					desc.append(" ");
					break;
				case SELECT_DATE:
					desc.append(vo.getTargetValue());
					desc.append(" ");
					break;
				case SELECT_DATES:
					desc.append(vo.getTargetValue());
					desc.append(" and ");
					desc.append(vo.getTargetValue2());
					desc.append(" ");
					break;
				case SELECT_TERM:
					switch(CriteriaEvaluatorEnum.valueOf(vo.getTargetEvaluator().getEvaluatorType())) {
						case MM_DD_YYYY:
							desc.append("format(");
							desc.append(vo.getTargetTermVo().getDisplayName());
							desc.append(", mm/dd/yyyy) ");
							break;
						case PLUS_N_DAYS:
							desc.append(vo.getTargetTermVo().getDisplayName());
							desc.append(" + ");
							desc.append(vo.getTargetValue());
							desc.append(" days ");
							break;
						case MINUS_N_DAYS:
							desc.append(vo.getTargetTermVo().getDisplayName());
							desc.append(" - ");
							desc.append(vo.getTargetValue());
							desc.append(" days ");
							break;
						case PLUS_N_MONTHS:
							desc.append(vo.getTargetTermVo().getDisplayName());
							desc.append(" + ");
							desc.append(vo.getTargetValue());
							desc.append(" months ");
							break;
						case MINUS_N_MONTHS:
							desc.append(vo.getTargetTermVo().getDisplayName());
							desc.append(" - ");
							desc.append(vo.getTargetValue());
							desc.append(" months ");
							break;
					}
					break;
				case SELECT_VIEW_FIELD:
					switch(CriteriaEvaluatorEnum.valueOf(vo.getTargetEvaluator().getEvaluatorType())) {
						case MM_DD_YYYY:
							desc.append("format(");
							desc.append(vo.getTargetViewFieldVo().getDisplayName());
							desc.append(", mm/dd/yyyy) ");
							break;
						case MONTH:
							desc.append("format(");
							desc.append(vo.getTargetViewFieldVo().getDisplayName());
							desc.append(", month) ");
							break;
						case DAY:
							desc.append("format(");
							desc.append(vo.getTargetViewFieldVo().getDisplayName());
							desc.append(", day) ");
							break;
						case YEAR:
							desc.append("format(");
							desc.append(vo.getTargetViewFieldVo().getDisplayName());
							desc.append(", year) ");
							break;
						case PLUS_N_DAYS:
							desc.append(vo.getTargetViewFieldVo().getDisplayName());
							desc.append(" ");
							break;
						case MINUS_N_DAYS:
							desc.append(vo.getTargetViewFieldVo().getDisplayName());
							desc.append(" ");
							break;
						case PLUS_N_MONTHS:
							desc.append(vo.getTargetViewFieldVo().getDisplayName());
							desc.append(" ");
							break;
						case MINUS_N_MONTHS:
							desc.append(vo.getTargetViewFieldVo().getDisplayName());
							desc.append(" ");
							break;
					}
					break;
			}
		}
		
		return desc.toString();
	}
	
	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static String getNumberCritDesc(CustomReportCriteriaVo vo) throws Exception {
		StringBuffer desc = new StringBuffer();
		
		//source field
		desc.append(vo.getSourceViewFieldVo().getDisplayName());
		desc.append(" ");
		
		//operator
		if(null != vo.getOperatorVo()) {
			desc.append(vo.getOperatorVo().getDisplayName());
			desc.append(" ");
		}
		
		//target conditions
		if(null != vo.getTargetType()) {
			switch(CustomReportsTargetTypeEnum.valueOf(vo.getTargetType())) {
				case INPUT_VALUE:
					desc.append(vo.getTargetValue());
					desc.append(" ");
					break;
				case SELECT_NUMBERS:
					desc.append(vo.getTargetValue());
					desc.append(" and ");
					desc.append(vo.getTargetValue2());
					break;
			}
		}
		
		return desc.toString();
	}
	
	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static String getTimeCritDesc(CustomReportCriteriaVo vo) throws Exception {
		StringBuffer desc = new StringBuffer();
		
		//source evaluator
		switch(CriteriaEvaluatorEnum.valueOf(vo.getSourceEvaluator().getEvaluatorType())) {
			case MILITARY_HOUR_MINUTE:
				desc.append("format(");
				desc.append(vo.getSourceViewFieldVo().getDisplayName());
				desc.append(", hour/minute) ");
				break;
			case MILITARY_HOUR:
				desc.append("format(");
				desc.append(vo.getSourceViewFieldVo().getDisplayName());
				desc.append(", hour) ");
				break;
			case MINUTE:
				desc.append("format(");
				desc.append(vo.getSourceViewFieldVo().getDisplayName());
				desc.append(", minute) ");
				break;
			case SECOND:
				desc.append("format(");
				desc.append(vo.getSourceViewFieldVo().getDisplayName());
				desc.append(", second) ");
				break;
		}
		
		//operator
		if(null != vo.getOperatorVo()) {
			desc.append(vo.getOperatorVo().getDisplayName());
			desc.append(" ");
		}
		
		//target conditions
		if(null != vo.getTargetType()) {
			switch(CustomReportsTargetTypeEnum.valueOf(vo.getTargetType())) {
				case INPUT_VALUE:
					desc.append(vo.getTargetValue());
					desc.append(" ");
					break;
				case SELECT_TIMES:
					desc.append(vo.getTargetValue());
					desc.append(" and ");
					desc.append(vo.getTargetValue2());
					desc.append(" ");
					break;
				case SELECT_VIEW_FIELD:
					switch(CriteriaEvaluatorEnum.valueOf(vo.getTargetEvaluator().getEvaluatorType())){
						case MILITARY_HOUR_MINUTE:
							desc.append("format(");
							desc.append(vo.getTargetViewFieldVo().getDisplayName());
							desc.append(", hour/minute) ");
							break;
						case MILITARY_HOUR:
							desc.append("format(");
							desc.append(vo.getTargetViewFieldVo().getDisplayName());
							desc.append(", hour) ");
							break;
						case MINUTE:
							desc.append("format(");
							desc.append(vo.getTargetViewFieldVo().getDisplayName());
							desc.append(", minute) ");
							break;
						case SECOND:
							desc.append("format(");
							desc.append(vo.getTargetViewFieldVo().getDisplayName());
							desc.append(", second) ");
							break;
					}
					break;
			}
		}
		
		return desc.toString();
	}
	
	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static String getBooleanCritDesc(CustomReportCriteriaVo vo) throws Exception {
		StringBuffer desc = new StringBuffer();
		
		//source field
		desc.append(vo.getSourceViewFieldVo().getDisplayName());
		desc.append(" ");
		
		//operator
		if(null != vo.getOperatorVo()) {
			desc.append(vo.getOperatorVo().getDisplayName());
		}
		
		return desc.toString();
	}
	
	/**
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static String getCurrencyCritDesc(CustomReportCriteriaVo vo) throws Exception {
		StringBuffer desc = new StringBuffer();
		
		//source field
		desc.append(vo.getSourceViewFieldVo().getDisplayName());
		desc.append(" ");
		
		//operator
		if(null != vo.getOperatorVo()) {
			desc.append(vo.getOperatorVo().getDisplayName());
			desc.append(" ");
		}
		
		//target conditions
		if(null != vo.getTargetType()) {
			switch(CustomReportsTargetTypeEnum.valueOf(vo.getTargetType())) {
				case INPUT_VALUE:
					desc.append(vo.getTargetValue());
					desc.append(" ");
					break;
				case SELECT_CURRENCIES:
					desc.append(vo.getTargetValue());
					desc.append(" and ");
					desc.append(vo.getTargetValue2());
					break;
			}
		}
		
		return desc.toString();
	}
	
	static class DisplayOrderComparator implements Comparator<CustomReportCriteriaVo>{

		public int compare(CustomReportCriteriaVo vo1, CustomReportCriteriaVo vo2){

			Integer vo1Position = vo1.getDisplayOrder();
			Integer vo2Position = vo2.getDisplayOrder();

			return vo1Position.compareTo(vo2Position);
		}
	}

	/**
	 * @param customReportId the customReportId to set
	 */
	public void setCustomReportId(Long customReportId) {
		this.customReportId = customReportId;
	}

	/**
	 * @return the customReportId
	 */
	public Long getCustomReportId() {
		return customReportId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @param parentGroupId the parentGroupId to set
	 */
	public void setParentGroupId(Long parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	/**
	 * @return the parentGroupId
	 */
	public Long getParentGroupId() {
		return parentGroupId;
	}

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return the displayOrder
	 */
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param connective the connective to set
	 */
	public void setConnective(String connective) {
		this.connective = connective;
	}

	/**
	 * @return the connective
	 */
	public String getConnective() {
		return connective;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param sourceViewFieldVo the sourceViewFieldVo to set
	 */
	public void setSourceViewFieldVo(CustomReportViewFieldVo sourceViewFieldVo) {
		this.sourceViewFieldVo = sourceViewFieldVo;
	}

	/**
	 * @return the sourceViewFieldVo
	 */
	public CustomReportViewFieldVo getSourceViewFieldVo() {
		return sourceViewFieldVo;
	}

	/**
	 * @param sourceFieldSqlName the sourceFieldSqlName to set
	 */
	public void setSourceFieldSqlName(String sourceFieldSqlName) {
		this.sourceFieldSqlName = sourceFieldSqlName;
	}

	/**
	 * @return the sourceFieldSqlName
	 */
	public String getSourceFieldSqlName() {
		return sourceFieldSqlName;
	}

	/**
	 * @param sourceFieldDataType the sourceFieldDataType to set
	 */
	public void setSourceFieldDataType(String sourceFieldDataType) {
		this.sourceFieldDataType = sourceFieldDataType;
	}

	/**
	 * @return the sourceFieldDataType
	 */
	public String getSourceFieldDataType() {
		return sourceFieldDataType;
	}

	/**
	 * @param sourceInputValue the sourceInputValue to set
	 */
	public void setSourceInputValue(String sourceInputValue) {
		this.sourceInputValue = sourceInputValue;
	}

	/**
	 * @return the sourceInputValue
	 */
	public String getSourceInputValue() {
		return sourceInputValue;
	}

	/**
	 * @param sourceEvaluator the sourceEvaluator to set
	 */
	public void setSourceEvaluator(CustomReportCriteriaEvaluatorVo sourceEvaluator) {
		this.sourceEvaluator = sourceEvaluator;
	}

	/**
	 * @return the sourceEvaluator
	 */
	public CustomReportCriteriaEvaluatorVo getSourceEvaluator() {
		return sourceEvaluator;
	}

	/**
	 * @param operatorType the operatorType to set
	 */
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	/**
	 * @return the operatorType
	 */
	public String getOperatorType() {
		return operatorType;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Collection<CustomReportCriteriaVo> children) {
		this.children = children;
	}

	/**
	 * @return the children
	 */
	public Collection<CustomReportCriteriaVo> getChildren() {
		if(null==children) 
			children = new ArrayList<CustomReportCriteriaVo>();
		return children;
	}

	/**
	 * @param operatorVo the operatorVo to set
	 */
	public void setOperatorVo(CustomReportCriteriaOperatorVo operatorVo) {
		this.operatorVo = operatorVo;
	}

	/**
	 * @return the operatorVo
	 */
	public CustomReportCriteriaOperatorVo getOperatorVo() {
		return operatorVo;
	}

	/**
	 * @param targetType the targetType to set
	 */
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	/**
	 * @return the targetType
	 */
	public String getTargetType() {
		return targetType;
	}

	/**
	 * @param targetValue the targetValue to set
	 */
	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}

	/**
	 * @return the targetValue
	 */
	public String getTargetValue() {
		return targetValue;
	}

	/**
	 * @param targetValue2 the targetValue2 to set
	 */
	public void setTargetValue2(String targetValue2) {
		this.targetValue2 = targetValue2;
	}

	/**
	 * @return the targetValue2
	 */
	public String getTargetValue2() {
		return targetValue2;
	}

	/**
	 * @param targetViewFieldVo the targetViewFieldVo to set
	 */
	public void setTargetViewFieldVo(CustomReportViewFieldVo targetViewFieldVo) {
		this.targetViewFieldVo = targetViewFieldVo;
	}

	/**
	 * @return the targetViewFieldVo
	 */
	public CustomReportViewFieldVo getTargetViewFieldVo() {
		return targetViewFieldVo;
	}

	/**
	 * @param targetFieldSqlName the targetFieldSqlName to set
	 */
	public void setTargetFieldSqlName(String targetFieldSqlName) {
		this.targetFieldSqlName = targetFieldSqlName;
	}

	/**
	 * @return the targetFieldSqlName
	 */
	public String getTargetFieldSqlName() {
		return targetFieldSqlName;
	}

	/**
	 * @param targetTermVo the targetTermVo to set
	 */
	public void setTargetTermVo(CustomReportCriteriaTermVo targetTermVo) {
		this.targetTermVo = targetTermVo;
	}

	/**
	 * @return the targetTermVo
	 */
	public CustomReportCriteriaTermVo getTargetTermVo() {
		return targetTermVo;
	}

	/**
	 * @param targetEvaluator the targetEvaluator to set
	 */
	public void setTargetEvaluator(CustomReportCriteriaEvaluatorVo targetEvaluator) {
		this.targetEvaluator = targetEvaluator;
	}

	/**
	 * @return the targetEvaluator
	 */
	public CustomReportCriteriaEvaluatorVo getTargetEvaluator() {
		return targetEvaluator;
	}

	/**
	 * @param targetList the targetList to set
	 */
	public void setTargetList(Collection<String> targetList) {
		this.targetList = targetList;
	}

	/**
	 * @return the targetList
	 */
	public Collection<String> getTargetList() {
		return targetList;
	}
	
	public void setTargetListFromString(String targetListString){
		Collection<String> targetList = null;
		if(targetListString != null) {
			targetList = Arrays.asList(targetListString.split(TARGET_LIST_STRING_SEPARATOR));
		}
		this.setTargetList(targetList);
	}
	
	public String getTargetListAsString() {
		String targetListString = "";
		if(targetList!=null && targetList.size()>0) {
			for(String target: targetList){
				if(StringUtility.hasValue(targetListString)) {
					targetListString += TARGET_LIST_STRING_SEPARATOR;
				}
				targetListString += target;
			}
		}
		return targetListString;
	}
	
	public String getSourceFieldDisplayName() {
		return sourceFieldDisplayName;
	}

	public void setSourceFieldDisplayName(String sourceFieldDisplayName) {
		this.sourceFieldDisplayName = sourceFieldDisplayName;
	}

	/**
	 * @param criteriaDescription the criteriaDescription to set
	 */
	public void setCriteriaDescription(String criteriaDescription) {
		this.criteriaDescription = criteriaDescription;
	}

	/**
	 * @return the criteriaDescription
	 */
	public String getCriteriaDescription() {
		return criteriaDescription;
	}

	public String getTargetFieldDisplayName() {
		return targetFieldDisplayName;
	}

	public void setTargetFieldDisplayName(String targetFieldDisplayName) {
		this.targetFieldDisplayName = targetFieldDisplayName;
	}

	/**
	 * @param tempId the tempId to set
	 */
	public void setTempId(Long tempId) {
		this.tempId = tempId;
	}

	/**
	 * @return the tempId
	 */
	public Long getTempId() {
		return tempId;
	}

}
