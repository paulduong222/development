package gov.nwcg.isuite.framework.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

public class CriteriaBuilder {

	
	/**
	 * Adds the not null criteria to the crit object.
	 * 
	 * @param crit
	 * @param filterCriteria
	 * @throws Exception
	 */
	public static void addCriteria(Criteria crit, Collection<FilterCriteria> filterCriteria) throws Exception {
	
		if( (null != crit) && (null != filterCriteria)){
			for(FilterCriteria fc : filterCriteria){
				
				if(null!=fc){

					switch(fc.getType())
					{
						case FilterCriteria.TYPE_EQUAL:
							if( (null!=fc.getField()) && (null!=fc.getValue()))
								crit.add(Expression.eq(fc.getField(), fc.getValue()));
							break;
						case FilterCriteria.TYPE_NOT_EQUAL:
							if( (null!=fc.getField()) && (null!=fc.getValue()))
								crit.add(Expression.ne(fc.getField(), fc.getValue()));
							break;
						case FilterCriteria.TYPE_ILIKE:
							if( (null!=fc.getField()) && (null!=fc.getValue()))
								crit.add(Expression.ilike(fc.getField(), (String)fc.getValue(),MatchMode.START));
							break;
						case FilterCriteria.TYPE_LIKE:
							if( (null!=fc.getField()) && (null!=fc.getValue()))
								crit.add(Expression.like(fc.getField(), fc.getValue()));
							break;
						case FilterCriteria.TYPE_ISNULL:
							if(null!=fc.getField())
								crit.add(Expression.isNull(fc.getField()));
							break;
						case FilterCriteria.TYPE_ISNOTNULL:
							if(null!=fc.getField())
								crit.add(Expression.isNotNull(fc.getField()));
							break;
						case FilterCriteria.TYPE_IN_STRING:
							if( (null!=fc.getField()) && (null!=fc.getValue()) ){
								Collection<String> collection = (Collection<String>)fc.getValue();
								crit.add(Expression.in(fc.getField(), collection));
							}
							break;
						case FilterCriteria.TYPE_OR:
							if (null != fc.getField() && null != fc.getField2() && null != fc.getValue() && null != fc.getValue2()) {
								crit.add(Expression.or(Expression.ilike(fc.getField(), fc.getValue().toString(), MatchMode.START), Expression.ilike(fc.getField2(), fc.getValue2().toString(), MatchMode.START)));
							}
							break;
					}
				}
			}
		}
	}

	
}
