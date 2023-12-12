package org.platkmframework.database.crud.domain.base.dao.service;

import java.util.List;

import org.platkmframework.common.domain.filter.FilterResult;
import org.platkmframework.common.domain.filter.criteria.FilterCriteria;
import org.platkmframework.common.domain.filter.criteria.WhereCriteria;
import org.platkmframework.comon.service.exception.ServiceException;
import org.platkmframework.database.crud.domain.base.dao.entity.OptionValue;
import org.platkmframework.database.query.common.exception.DaoException; 

public interface SystemSearchService {

	FilterResult<?> search(FilterCriteria filter) throws ServiceException, DaoException;
	
	FilterResult<?> search(WhereCriteria filter, String...replacements) throws ServiceException, DaoException;
	
	FilterResult<?> search(WhereCriteria filter, List<Object> parameters, String...replacements) throws ServiceException, DaoException;

	List<OptionValue> options(WhereCriteria filter) throws ServiceException, DaoException;

}
