package org.platkmframework.database.crud.domain.base.dao.service;

public class SearchFilterConditionData {
	
	String label;
	String code; 
	String column;  
	
	public SearchFilterConditionData(String label, String code, String column) {
		super();
		this.label = label;
		this.code = code;
		this.column = column; 
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	
}
