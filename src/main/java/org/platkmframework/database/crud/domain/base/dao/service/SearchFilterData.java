package org.platkmframework.database.crud.domain.base.dao.service;

import java.util.Map;

public class SearchFilterData {
	
	private String fastsearch; 
	private String defaultOrderColumn;
	private String defaultOrderType;
	private Map<String, SearchFilterConditionData> searchMap;
	
	
	public SearchFilterData(String fastsearch, String defaultOrderColumn, String defaultOrderType) {
		super();
		this.fastsearch = fastsearch;
		this.defaultOrderColumn = defaultOrderColumn;
		this.defaultOrderType = defaultOrderType;
	}

	public String getFastsearch() {
		return fastsearch;
	}

	public void setFastsearch(String fastsearch) {
		this.fastsearch = fastsearch;
	}

	public Map<String, SearchFilterConditionData> getSearchMap() {
		return searchMap;
	}

	public void setSearchMap(Map<String, SearchFilterConditionData> searchMap) {
		this.searchMap = searchMap;
	}

	public String getDefaultOrderColumn() {
		return defaultOrderColumn;
	}

	public void setDefaultOrderColumn(String defaultOrderColumn) {
		this.defaultOrderColumn = defaultOrderColumn;
	}

	public String getDefaultOrderType() {
		return defaultOrderType;
	}

	public void setDefaultOrderType(String defaultOrderType) {
		this.defaultOrderType = defaultOrderType;
	}
	
}
