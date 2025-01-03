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
package org.platkmframework.database.crud.domain.base.dao.service;

import java.util.List;
import org.platkmframework.annotation.AutoWired;
import org.platkmframework.annotation.Service;
import org.platkmframework.database.crud.domain.base.dao.SystemSearchDAO;
import org.platkmframework.database.crud.domain.base.dao.entity.OptionValue;
import org.platkmframework.database.crud.domain.base.exception.CrudException;
import org.platkmframework.database.query.common.exception.DaoException;
import org.platkmframework.persistence.filter.FilterResult;
import org.platkmframework.persistence.filter.criteria.FilterCriteria;
import org.platkmframework.persistence.filter.criteria.WhereCriteria;

/**
 *   Author:
 *     Eduardo Iglesias
 *   Contributors:
 *   	Eduardo Iglesias - initial API and implementation
 */
@Service
public class SystemSearchServiceImpl implements SystemSearchService {

    /**
     * SystemSearchServiceImpl
     */
    public SystemSearchServiceImpl() {
        super();
    }

    /**
     * Atributo systemSearchDAO
     */
    @AutoWired
    private SystemSearchDAO systemSearchDAO;

    /**
     * search
     * @param filter filter
     * @return FilterResult
     * @throws CrudException CrudException
     * @throws DaoException DaoException
     */
    @Deprecated
    @Override
    public FilterResult<?> search(FilterCriteria filter) throws CrudException, DaoException {
        return null;
    }

    /**
     * search
     * @param filter filter
     * @param parameters parameters
     * @param replacements replacements
     * @return FilterResult
     * @throws CrudException CrudException
     * @throws DaoException DaoException
     */
    @Deprecated
    @Override
    public FilterResult<?> search(WhereCriteria filter, List<Object> parameters, String... replacements) throws CrudException, DaoException {
        return null;
    }

    /**
     * search
     * @param filter filter
     * @param replacements replacements
     * @return FilterResult
     * @throws CrudException CrudException
     * @throws DaoException DaoException
     */
    @Deprecated
    @Override
    public FilterResult<?> search(WhereCriteria filter, String... replacements) throws CrudException, DaoException {
        return search(filter, null, replacements);
    }

    /**
     * options
     * @param filter filter
     * @return List
     * @throws CrudException CrudException
     * @throws DaoException DaoException
     */
    @Deprecated
    public List<OptionValue> options(WhereCriteria filter) throws CrudException, DaoException {
        return null;
    }
}
