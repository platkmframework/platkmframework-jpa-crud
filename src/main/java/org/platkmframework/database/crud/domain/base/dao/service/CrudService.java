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

import org.platkmframework.database.query.common.exception.DaoException;
import org.platkmframework.persistence.filter.FilterResult;
import org.platkmframework.persistence.filter.criteria.FilterCriteria;

/**
 *   Author:
 *     Eduardo Iglesias
 *   Contributors:
 *   	Eduardo Iglesias - initial API and implementation
 * @param <VO> VO
 * @param <I> I
 */
public interface CrudService<VO, I> {

    /**
     * crudLoad
     * @param id id
     * @return VO
     * @throws DaoException DaoException
     */
    VO crudLoad(I id) throws DaoException;

    /**
     * search
     * @param filter filter
     * @return FilterResult
     * @throws DaoException DaoException
     */
    FilterResult<VO> search(FilterCriteria filter) throws DaoException;

    /**
     * crudCreate
     * @param vo vo
     * @return I
     * @throws DaoException DaoException
     */
    I crudCreate(VO vo) throws DaoException;

    /**
     * crudUpdate
     * @param vo vo
     * @throws DaoException DaoException
     */
    void crudUpdate(VO vo) throws DaoException;

    /**
     * crudDelete
     * @param id id
     * @throws DaoException DaoException
     */
    void crudDelete(I id) throws DaoException;
}
