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
import org.platkmframework.persistence.filter.criteria.DeleteCriteriaEntity;
import org.platkmframework.persistence.filter.criteria.FilterCriteria;
import org.platkmframework.persistence.filter.criteria.WhereCriteria;

/**
 *   Author:
 *     Eduardo Iglesias
 *   Contributors:
 *   	Eduardo Iglesias - initial API and implementation
 * @param <E> E
 * @param <I> I
 */
public interface EntityDao<E, I> extends BaseEntityDaoI<E> {

    /**
     * load
     * @param id id
     * @return E
     */
    public E load(I id);

    /**
     * loadByCode
     * @param code code
     * @return E
     * @throws DaoException DaoException
     */
    public E loadByCode(String code) throws DaoException;

    /** 
     * 
     * load
     * @param <F> F
     * @param id id
     * @param class1 class1
     * @return F
     * @throws DaoException DaoException
     */
    public <F> F load(I id, Class<F> class1) throws DaoException;

    /**
     * load
     * @param ids ids
     * @return List
     * @throws DaoException DaoException
     */
    public List<E> load(List<I> ids) throws DaoException;

    /**
     * load
     * @param <F> F
     * @param ids ids
     * @param returnClass returnClass
     * @return List
     * @throws DaoException DaoException
     */
    public <F> List<F> load(List<I> ids, Class<F> returnClass) throws DaoException;

    /**
     * save
     * @param entity entity
     */
    public void save(E entity);

    /**
     * insert
     * @param entity entity
     * @return Object
     */
    public Object insert(E entity);

    /**
     * inserList
     * @param entities entities
     */
    public void inserList(List<E> entities);

    /**
     * updateList
     * @param entities entities
     */
    public void updateList(List<E> entities);

    /**
     * update
     * @param entity entity
     */
    public void update(E entity);

    /**
     * removeById
     * @param id id
     */
    public void removeById(I id);

    /**
     * remove
     * @param entity entity
     */
    public void remove(E entity);

    /**
     * selectAll
     * @return List
     */
    public List<E> selectAll();

    /**
     * selectAll
     * @param <F> F
     * @param returnClass returnClass
     * @return List
     * @throws DaoException DaoException
     */
    public <F> List<F> selectAll(Class<F> returnClass) throws DaoException;

    /**
     * loadFirstRecord
     * @return E
     */
    public E loadFirstRecord();

    /**
     * find
     * @param filter filter
     * @return List
     * @throws DaoException DaoException
     */
    public List<E> find(FilterCriteria filter) throws DaoException;

    /**
     * find
     * @param <F> F
     * @param filter filter
     * @param returnClass returnClass
     * @return List
     * @throws DaoException DaoException
     */
    public <F> List<F> find(FilterCriteria filter, Class<F> returnClass) throws DaoException;

    /**
     * remove
     * @param deleteCriteriaEntity deleteCriteriaEntity
     * @throws DaoException DaoException
     */
    public void remove(DeleteCriteriaEntity deleteCriteriaEntity) throws DaoException;

    /**
     * count
     * @param filter filter
     * @return Integer
     * @throws DaoException DaoException
     */
    public Integer count(WhereCriteria filter) throws DaoException;

    /**
     * findOne
     * @param filter filter
     * @return E
     * @throws DaoException DaoException
     */
    public E findOne(FilterCriteria filter) throws DaoException;

    /**
     * findOne
     * @param <F> F
     * @param filter filter
     * @param returnClass returnClass
     * @return F
     * @throws DaoException DaoException
     */
    public <F> F findOne(FilterCriteria filter, Class<F> returnClass) throws DaoException;
}
