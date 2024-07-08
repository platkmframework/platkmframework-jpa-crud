package org.platkmframework.database.crud.domain.base.dao.service;

import java.util.List;

import org.platkmframework.annotation.AutoWired;
import org.platkmframework.annotation.Service;
import org.platkmframework.annotation.db.ESearchFilter;
import org.platkmframework.annotation.db.QSearchFilter;
import org.platkmframework.annotation.db.SelectOption;
import org.platkmframework.common.domain.filter.FilterResult;
import org.platkmframework.common.domain.filter.criteria.FilterCriteria;
import org.platkmframework.common.domain.filter.criteria.SearchCriteria;
import org.platkmframework.common.domain.filter.criteria.WhereCriteria;
import org.platkmframework.common.domain.filter.enumerator.ConditionOperator;
import org.platkmframework.content.ObjectContainer;
import org.platkmframework.database.crud.domain.base.dao.SystemSearchDAO;
import org.platkmframework.database.crud.domain.base.dao.entity.OptionValue;
import org.platkmframework.database.crud.domain.base.exception.CrudException;
import org.platkmframework.database.query.common.exception.DaoException;
import org.platkmframework.database.query.common.vo.CustomResultInfo;
import org.platkmframework.jpa.util.DaoUtil;

@Service
public class SystemSearchServiceImpl implements SystemSearchService{

	@AutoWired
	private SystemSearchDAO  systemSearchDAO;
	
	@Override
	public FilterResult<?> search(FilterCriteria filter) throws CrudException, DaoException {
		
		Object ob = ObjectContainer.instance().getSearchMapInfo(filter.getCode());
		if(ob instanceof ESearchFilter) {
			ESearchFilter eSearchFilter = (ESearchFilter) ob;
			return systemSearchDAO.search(filter, null, eSearchFilter.enityClass(), eSearchFilter.resultClass());
		}
		return null;
	}
	
	@Override
	public FilterResult<?> search(WhereCriteria filter, List<Object> parameters, String...replacements) throws CrudException, DaoException {
		
		Object ob = ObjectContainer.instance().getSearchMapInfo(filter.getCode());
		if(ob instanceof QSearchFilter) {
			QSearchFilter qSearchFilter = (QSearchFilter) ob;
			return systemSearchDAO.getPlatkmEntityManager().getQueryManagerDao().search(qSearchFilter.querymanagerid(), filter, parameters, qSearchFilter.resultClass(), replacements);
		}
		return null;
	}
	
	@Override
	public FilterResult<?> search(WhereCriteria filter, String...replacements) throws CrudException, DaoException {
		return search(filter, null, replacements);
	}
 
	public List<OptionValue> options(WhereCriteria filter) throws CrudException, DaoException {
	
		Object ob = ObjectContainer.instance().getSearchMapInfo(filter.getCode());
		if(ob instanceof SelectOption) {
			
			if(filter.emptyConditions()) filter.valexp("1", ConditionOperator.equal, 1);
			
			SelectOption selectOption = (SelectOption) ob;
			ob = ObjectContainer.instance().getSearchMapInfo(selectOption.searchCode());
			
			if(ob instanceof ESearchFilter) {
				
				ESearchFilter eSearchFilter = (ESearchFilter) ob; 
				SearchCriteria searchCriteria = new SearchCriteria().select(selectOption.key() + " as key, (" + selectOption.text() + ") as text", 
																			DaoUtil.getTableName(eSearchFilter.enityClass()), "");
				
				searchCriteria.getSql().addAll(filter.getSql());
				return systemSearchDAO.getPlatkmEntityManager().getQueryDao().select(searchCriteria, null, OptionValue.class);
			
			}else if(ob instanceof QSearchFilter) {
				
				QSearchFilter qSearchFilter = (QSearchFilter) ob; 
				CustomResultInfo<OptionValue> customResultInfo = new CustomResultInfo<>(OptionValue.class);
				customResultInfo.addColumn(selectOption.key() + " as key");
				customResultInfo.addColumn( "(" + selectOption.text() + ") as text");
				return systemSearchDAO.getPlatkmEntityManager().getQueryManagerDao().select(qSearchFilter.querymanagerid(), filter, null, customResultInfo);
				
			}
		}
		return null;
	}

}
