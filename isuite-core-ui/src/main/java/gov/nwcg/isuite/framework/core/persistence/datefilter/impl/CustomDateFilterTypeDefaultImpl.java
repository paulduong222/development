package gov.nwcg.isuite.framework.core.persistence.datefilter.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.nwcg.isuite.framework.core.persistence.datefilter.ICustomDateFilter;

public class CustomDateFilterTypeDefaultImpl implements ICustomDateFilter {

	@Override
	public void applyDateFiltersOracle(Criteria crit, String crypticDateFilterCode, String dbFieldName) throws Exception {
		String sql = null;

		String[] dateNoSlashes = crypticDateFilterCode.split("/");

		for(int i=0; i < dateNoSlashes.length; i++) {
			int num = Integer.parseInt(dateNoSlashes[i]);
			String str = dateNoSlashes[i];

			if(i == 0) { // check month is 01-12
				if(!(0 <= num && num < 13)) {
					return;
				} else {
					if(num < 10 && !(str.startsWith("0") || str.startsWith("1")) ) {
						return;
					}
				}
			} else if(i == 1) { // check day is 0-31
				if(!(0 <= num && num <= 31)) {
					return;
				} else {
					if(num < 10 && 
							!(str.startsWith("0") || str.startsWith("1") || str.startsWith("2") || str.startsWith("3")) ) {
						return;
					}
				}
			}
			//		 else if(i == 2) { // check year length is 4 or less
			//        if(!((dateNoSlashes[i]).length() <= 4)) {
			//          return;
			//        }
			//      }
		}

		for(int i=0; i < dateNoSlashes.length; i++) {
			if(i == 0) {
				sql = "to_char(" + dbFieldName + ", 'MM') like '" + dateNoSlashes[i] + "%'";
				crit.add(Restrictions.sqlRestriction(sql));
			} else if(i == 1) { 
				sql = "to_char(" + dbFieldName + ", 'DD') like '" + dateNoSlashes[i] + "%'";
				crit.add(Restrictions.sqlRestriction(sql));
			} else if(i == 2) {
				sql = "to_char(" + dbFieldName + ", 'YYYY') like '" + dateNoSlashes[i] + "%'";
				crit.add(Restrictions.sqlRestriction(sql));
			}
		}

	}

	@Override
	public String getDateFiltersOracle(String crypticDateFilterCode, String dbFieldName) throws Exception {
		String sql = "";

		String[] dateNoSlashes = crypticDateFilterCode.split("/");

		for(int i=0; i < dateNoSlashes.length; i++) {
			int num = Integer.parseInt(dateNoSlashes[i]);
			String str = dateNoSlashes[i];

			if(i == 0) { // check month is 01-12
				if(!(0 <= num && num < 13)) {
					return "";
				} else {
					if(num < 10 && !(str.startsWith("0") || str.startsWith("1")) ) {
						return "";
					}
				}
			} else if(i == 1) { // check day is 0-31
				if(!(0 <= num && num <= 31)) {
					return "";
				} else {
					if(num < 10 && 
							!(str.startsWith("0") || str.startsWith("1") || str.startsWith("2") || str.startsWith("3")) ) {
						return "";
					}
				}
			}
			//		 else if(i == 2) { // check year length is 4 or less
			//        if(!((dateNoSlashes[i]).length() <= 4)) {
			//          return;
			//        }
			//      }
		}

		for(int i=0; i < dateNoSlashes.length; i++) {
			if(i == 0) {
				sql += " to_char(" + dbFieldName + ", 'MM') like '" + dateNoSlashes[i] + "%' ";
			} else if(i == 1) { 
				sql += " and to_char(" + dbFieldName + ", 'DD') like '" + dateNoSlashes[i] + "%' ";
			} else if(i == 2) {
				sql += " and to_char(" + dbFieldName + ", 'YYYY') like '" + dateNoSlashes[i] + "%' ";
			}
		}

		return sql;
	}
	
	@Override
	public void applyDateFiltersPostgres(Criteria crit,	String crypticDateFilterCode, String dbFieldName) throws Exception {
		// TODO Auto-generated method stub

	}

}
