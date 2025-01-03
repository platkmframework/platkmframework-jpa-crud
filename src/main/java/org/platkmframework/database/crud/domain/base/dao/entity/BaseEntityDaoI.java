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
package org.platkmframework.database.crud.domain.base.dao.entity;

import java.util.List;
import org.platkmframework.database.query.common.exception.DaoException;
import org.platkmframework.persistence.filter.FilterResult;
import org.platkmframework.persistence.filter.criteria.FilterCriteria;

/**
 *   Author:
 *     Eduardo Iglesias
 *   Contributors:
 *   	Eduardo Iglesias - initial API and implementation
 * @param <E> E
 */
public interface BaseEntityDaoI<E> {

    /**
     * search
     * @param filter filter
     * @param params params
     * @return FilterResult
     * @throws DaoException DaoException
     */
    public FilterResult<E> search(FilterCriteria filter, List<Object> params) throws DaoException;

    /**
     * search
     * @param <F> F
     * @param filter filter
     * @param params params
     * @param returnClass returnClass
     * @return FilterResult
     * @throws DaoException DaoException
     */
    public <F> FilterResult<F> search(FilterCriteria filter, List<Object> params, Class<F> returnClass) throws DaoException;
}
