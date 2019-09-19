package gov.nwcg.isuite.framework.core.persistence;

public enum DynamicDataSourceType {
	MASTER
	,DEFAULT0
	,DEFAULT1
	,DEFAULT2
	,DEFAULT3
	,DEFAULT4
	,DEFAULT5
	,DEFAULT6
	,DEFAULT7
	,DEFAULT8
	,DEFAULT9
	,DEFAULT10
	,DEFAULT11
	,DEFAULT12
	,DEFAULT13
	,DEFAULT14
	,DEFAULT15
	,DEFAULT16
	,DEFAULT17
	,DEFAULT18
	,DEFAULT19
	,DEFAULT20
	;
	
	static DynamicDataSourceType getDynamicDataSourceType(String name){
		if(name.equals("default0DataSource"))
			return DynamicDataSourceType.DEFAULT0;
		else if(name.equals("default1DataSource"))
			return DynamicDataSourceType.DEFAULT1;
		else if(name.equals("default2DataSource"))
			return DynamicDataSourceType.DEFAULT2;
		else if(name.equals("default3DataSource"))
			return DynamicDataSourceType.DEFAULT3;
		else if(name.equals("default4DataSource"))
			return DynamicDataSourceType.DEFAULT4;
		else if(name.equals("default5DataSource"))
			return DynamicDataSourceType.DEFAULT5;
		else if(name.equals("default6DataSource"))
			return DynamicDataSourceType.DEFAULT6;
		else if(name.equals("default7DataSource"))
			return DynamicDataSourceType.DEFAULT7;
		else if(name.equals("default8DataSource"))
			return DynamicDataSourceType.DEFAULT8;
		else if(name.equals("default9DataSource"))
			return DynamicDataSourceType.DEFAULT9;
		else if(name.equals("default10DataSource"))
			return DynamicDataSourceType.DEFAULT10;
		else if(name.equals("default11DataSource"))
			return DynamicDataSourceType.DEFAULT11;
		else if(name.equals("default12DataSource"))
			return DynamicDataSourceType.DEFAULT12;
		else if(name.equals("default13DataSource"))
			return DynamicDataSourceType.DEFAULT13;
		else if(name.equals("default14DataSource"))
			return DynamicDataSourceType.DEFAULT14;
		else if(name.equals("default15DataSource"))
			return DynamicDataSourceType.DEFAULT15;
		else if(name.equals("default16DataSource"))
			return DynamicDataSourceType.DEFAULT16;
		else if(name.equals("default17DataSource"))
			return DynamicDataSourceType.DEFAULT17;
		else if(name.equals("default18DataSource"))
			return DynamicDataSourceType.DEFAULT18;
		else if(name.equals("default19DataSource"))
			return DynamicDataSourceType.DEFAULT19;
		else if(name.equals("default20DataSource"))
			return DynamicDataSourceType.DEFAULT20;
		else
			return DynamicDataSourceType.MASTER;
	}
}
