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
import org.platkmframework.database.query.common.exception.DaoException;
import org.platkmframework.persistence.filter.FilterResult;
import org.platkmframework.persistence.filter.criteria.FilterCriteria;
import org.platkmframework.persistence.filter.ui.Filter;

/**
 *   Author:
 *     Eduardo Iglesias
 *   Contributors:
 *   	Eduardo Iglesias - initial API and implementation
 * @param <VO> VO
 * @param <I> I
 */
public interface BaseService<VO, I> {

    /**
     * search
     * @param filter filter
     * @return FilterResult
     * @throws DaoException DaoException
     */
    public FilterResult<VO> search(FilterCriteria filter) throws DaoException;

    /**
     * search
     * @param filter filter
     * @return FilterResult
     * @throws DaoException DaoException
     */
    public FilterResult<VO> search(Filter<?> filter) throws DaoException;

    /**
     * create
     * @param vo vo
     * @throws DaoException DaoException
     */
    public void create(VO vo) throws DaoException;

    /**
     * update
     * @param entity entity
     * @throws DaoException DaoException
     */
    public void update(VO entity) throws DaoException;

    /**
     * save
     * @param entity entity
     * @throws DaoException DaoException
     */
    public void save(VO entity) throws DaoException;

    /**
     * removeById
     * @param id id
     * @throws DaoException DaoException
     */
    public void removeById(I id) throws DaoException;

    /**
     * load
     * @param id id
     * @return VO
     * @throws DaoException DaoException
     */
    public VO load(I id) throws DaoException;

    /**
     * findAll
     * @return List
     * @throws DaoException DaoException
     */
    public List<VO> findAll() throws DaoException;

    /**
     * find
     * @param filter filter
     * @return List
     * @throws DaoException DaoException
     */
    public List<VO> find(FilterCriteria filter) throws DaoException;

    /**
     * find
     * @param filter filter
     * @return List
     * @throws DaoException DaoException
     */
    public List<VO> find(Filter<?> filter) throws DaoException;
}
