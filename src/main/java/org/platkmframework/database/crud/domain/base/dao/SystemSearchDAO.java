/**
 * ****************************************************************************
 *  Copyright(c) 2023 the original author Eduardo Iglesias Taylor.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  	 https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Contributors:
 *  	Eduardo Iglesias Taylor - initial API and implementation
 * *****************************************************************************
 */
package org.platkmframework.database.crud.domain.base.dao;

import org.platkmframework.annotation.Repository;
import org.platkmframework.database.crud.domain.base.dao.entity.BaseEntityDao;
import org.platkmframework.database.query.common.exception.DaoException;
import org.platkmframework.jpa.base.PlatkmORMEntityManager;
import org.platkmframework.persistence.filter.FilterResult;
import org.platkmframework.persistence.filter.criteria.FilterCriteria;
import jakarta.persistence.PersistenceContext;

/**
 *   Author:
 *     Eduardo Iglesias
 *   Contributors:
 *   	Eduardo Iglesias - initial API and implementation
 */
@Repository
public class SystemSearchDAO extends BaseEntityDao {

    /**
     * SystemSearchDAO
     */
    public SystemSearchDAO() {
        super();
    }

    /**
     * Atributo platkmEntityManager
     */
    @PersistenceContext(unitName = "${platkmframework_persistence_unit}")
    private PlatkmORMEntityManager platkmEntityManager;

    /**
     * getPlatkmEntityManager
     * @return PlatkmORMEntityManager
     */
    @Override
    public PlatkmORMEntityManager getPlatkmEntityManager() {
        return platkmEntityManager;
    }

    /**
     * search
     * @param filter filter
     * @param object object
     * @param enityClass1 enityClass1
     * @param resultClass resultClass
     * @return FilterResult
     * @throws DaoException DaoException
     */
    public FilterResult<?> search(FilterCriteria filter, Object object, Class<?> enityClass1, Class<?> resultClass) throws DaoException {
        udpdateFilterCriteria(filter);
        return procesSearchAndFilter(filter, null, enityClass1, resultClass);
    }
}
