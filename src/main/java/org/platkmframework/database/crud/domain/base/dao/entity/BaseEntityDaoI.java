package org.platkmframework.database.crud.domain.base.dao.entity;

import java.util.List;

import org.platkmframework.common.domain.filter.FilterResult;
import org.platkmframework.common.domain.filter.criteria.FilterCriteria;
import org.platkmframework.database.query.common.exception.DaoException; 

public interface BaseEntityDaoI<E> {
	
	public FilterResult<E> search(FilterCriteria filter, List<Object> params) throws DaoException; 
	
	public <F> FilterResult<F> search(FilterCriteria filter, List<Object> params, Class<F> returnClass) throws DaoException;  
}
