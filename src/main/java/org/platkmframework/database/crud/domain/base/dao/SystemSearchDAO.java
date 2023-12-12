package org.platkmframework.database.crud.domain.base.dao;

import javax.persistence.PersistenceContext;

import org.platkmframework.annotation.Repository;
import org.platkmframework.common.domain.filter.FilterResult;
import org.platkmframework.common.domain.filter.criteria.FilterCriteria;
import org.platkmframework.database.crud.domain.base.dao.entity.BaseEntityDao;
import org.platkmframework.database.query.common.exception.DaoException;
import org.platkmframework.jpa.base.PlatkmEntityManager;

@Repository
public class SystemSearchDAO extends BaseEntityDao   {
	
	@PersistenceContext(unitName = "${platkmframework_persistence_unit}")
	private PlatkmEntityManager platkmEntityManager;

	@Override
	public PlatkmEntityManager getPlatkmEntityManager() {
		return platkmEntityManager;
	}

	public FilterResult<?> search(FilterCriteria filter, Object object, Class<?> enityClass1, Class<?> resultClass) throws DaoException {
		udpdateFilterCriteria(filter); 
		return procesSearchAndFilter(filter, null, enityClass1, resultClass); 
	}
	 
}
