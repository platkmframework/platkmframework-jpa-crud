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

import java.lang.reflect.Field;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.platkmframework.database.query.common.exception.DaoException;
import org.platkmframework.database.query.manager.QueryManager;
import org.platkmframework.jpa.util.DaoUtil;
import org.platkmframework.persistence.filter.FilterResult;
import org.platkmframework.persistence.filter.criteria.DeleteCriteriaEntity;
import org.platkmframework.persistence.filter.criteria.FilterCriteria;
import org.platkmframework.persistence.filter.criteria.SearchCriteria;
import org.platkmframework.persistence.filter.criteria.WhereCriteria;
import org.platkmframework.persistence.filter.enumerator.ConditionOperator;
import org.platkmframework.persistence.filter.info.FilterData;
import org.platkmframework.persistence.filter.info.FilterDataType;
import org.platkmframework.util.reflection.ReflectionUtil;
import jakarta.persistence.Query;

/**
 *   Author:
 *     Eduardo Iglesias
 *   Contributors:
 *   	Eduardo Iglesias - initial API and implementation
 * @param <E> E
 * @param <I> I
 */
public abstract class EntityDaoImpl<E, I> extends BaseEntityDao<E> implements EntityDao<E, I> {

    /**
     * Atributo logger
     */
    private static Logger logger = LoggerFactory.getLogger(EntityDaoImpl.class);

    /**
     * Atributo fields
     */
    protected List<Field> fields;

    /**
     * Constructor EntityDaoImpl
     * @param entityClass entityClass
     */
    public EntityDaoImpl(Class<E> entityClass) {
        super();
        this.entityClass = entityClass;
        this.fields = ReflectionUtil.getAllFieldHeritage(entityClass);
    }

    /**
     * load
     * @param id id
     * @return E
     */
    @Override
    public E load(I id) {
        return getPlatkmEntityManager().find(this.entityClass, id);
    }

    /**
     * load
     * @param id id
     * @param returnClass returnClass
     * @return F
     * @throws DaoException DaoException
     */
    @Override
    public <F> F load(I id, Class<F> returnClass) throws DaoException {
        try {
            FilterCriteria filterCriteria = new FilterCriteria();
            FilterData filterData = filterCriteria.getSql().stream().filter((obj -> ((FilterData) obj).isType(FilterDataType.SELECTINFO))).findAny().orElse(null);
            filterData.setSelectColumns(" * ");
            filterData = filterCriteria.getSql().stream().filter((obj -> ((FilterData) obj).isType(FilterDataType.FROMINFO))).findAny().orElse(null);
            filterData.setFrom(DaoUtil.getTableName(entityClass));
            filterCriteria.eq("id", id);
            return findOne(filterCriteria, returnClass);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DaoException("No se pudo realizar el proceso, int�ntelo m�s tarde");
        }
    }

    /**
     * load
     * @param ids ids
     * @return List
     * @throws DaoException DaoException
     */
    @Override
    public List<E> load(List<I> ids) throws DaoException {
        try {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.select(DaoUtil.getTableName(this.entityClass)).in("id", ids);
            return getPlatkmEntityManager().getQueryDao().select(searchCriteria, null, this.entityClass);
        } catch (DaoException e) {
            logger.error(e.getMessage());
            throw new DaoException("No se pudo realizar el proceso, int�ntelo m�s tarde");
        }
    }

    /**
     * load
     * @param ids ids
     * @param returnClass returnClass
     * @return List
     * @throws DaoException DaoException
     */
    @Override
    public <F> List<F> load(List<I> ids, Class<F> returnClass) throws DaoException {
        try {
            FilterCriteria filterCriteria = new FilterCriteria();
            FilterData filterData = filterCriteria.getSql().stream().filter((obj -> ((FilterData) obj).isType(FilterDataType.SELECTINFO))).findAny().orElse(null);
            filterData.setSelectColumns(" * ");
            filterData = filterCriteria.getSql().stream().filter((obj -> ((FilterData) obj).isType(FilterDataType.FROMINFO))).findAny().orElse(null);
            filterData.setFrom(DaoUtil.getTableName(entityClass));
            filterCriteria.in("id", ids);
            return find(filterCriteria, returnClass);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DaoException(" error cargando el registro");
        }
    }

    /**
     * save
     * @param entity entity
     */
    @Override
    public void save(E entity) {
        if (DaoUtil.isEntityAnnotationPresent(entity)) {
            if (DaoUtil.isEntityIdFieldWithValue(entity))
                update(entity);
            else
                insert(entity);
        } else {
            insert(entity);
        }
    }

    /**
     * insert
     * @param entity entity
     * @return Object
     */
    @Override
    public Object insert(E entity) {
        getPlatkmEntityManager().persist(entity);
        return DaoUtil.getValueFromIdColumn(entity);
    }

    /**
     * inserList
     * @param entities entities
     */
    @Override
    public void inserList(List<E> entities) {
        if (entities != null) {
            for (int i = 0; i < entities.size(); i++) {
                insert(entities.get(i));
            }
        }
    }

    /**
     * update
     * @param entity entity
     */
    @Override
    public void update(E entity) {
        getPlatkmEntityManager().merge(entity);
    }

    /**
     * updateList
     * @param entities entities
     */
    @Override
    public void updateList(List<E> entities) {
        if (entities != null) {
            for (int i = 0; i < entities.size(); i++) {
                update(entities.get(i));
            }
        }
    }

    /**
     * remove
     * @param entity entity
     */
    @Override
    public void remove(E entity) {
        getPlatkmEntityManager().remove(entity);
    }

    /**
     * removeById
     * @param id id
     */
    @Override
    public void removeById(I id) {
        String idColumnName = DaoUtil.getIdColumnName(this.entityClass);
        Query query = getPlatkmEntityManager().createNativeQuery(" DELETE FROM " + DaoUtil.getTableName(getEntityClass()) + " where " + idColumnName + "=?");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    /**
     * remove
     * @param deleteCriteriaEntity deleteCriteriaEntity
     * @throws DaoException DaoException
     */
    @Override
    public void remove(DeleteCriteriaEntity deleteCriteriaEntity) throws DaoException {
        //findFilter.getSql().add(0, new FilterData(FilterDataType.DELETEINFO));
        FilterData fromInfo = deleteCriteriaEntity.getSql().stream().filter((obj -> (((FilterData) obj).isType(FilterDataType.FROMINFO)))).findAny().orElse(null);
        String tableName = DaoUtil.getTableName(entityClass);
        if (fromInfo == null) {
            fromInfo = new FilterData().addFromInfo(tableName, null);
            deleteCriteriaEntity.getSql().add(1, fromInfo);
        } else {
            fromInfo.setFrom(tableName);
        }
        /**
         * ProcessInfo processInfo = new ProcessInfo(true, ProcessType.DELETE);
         * 		processInfo.setApplyAdditional(false);
         */
        Query query = getPlatkmEntityManager().createQuery(deleteCriteriaEntity, null);
        query.executeUpdate();
    }

    /**
     * selectAll
     * @return List
     */
    @Override
    public List<E> selectAll() {
        Query query = getPlatkmEntityManager().createNativeQuery(" SELECT * FROM " + DaoUtil.getTableName(getEntityClass()), entityClass);
        return query.getResultList();
    }

    /**
     * selectAll
     * @param returnClass returnClass
     * @return List
     * @throws DaoException DaoException
     */
    @Override
    public <F> List<F> selectAll(Class<F> returnClass) throws DaoException {
        try {
            FilterCriteria filterCriteria = new FilterCriteria();
            FilterData filterData = filterCriteria.getSql().stream().filter((obj -> ((FilterData) obj).isType(FilterDataType.SELECTINFO))).findAny().orElse(null);
            filterData.setSelectColumns(" * ");
            filterData = filterCriteria.getSql().stream().filter((obj -> ((FilterData) obj).isType(FilterDataType.FROMINFO))).findAny().orElse(null);
            filterData.setFrom(DaoUtil.getTableName(entityClass));
            filterCriteria.valexp("1", ConditionOperator.equal, 1);
            return find(filterCriteria, returnClass);
            //return queryDao.select(filterCriteria, null, returnClass);
        } catch (DaoException e) {
            logger.error(e.getMessage());
            throw new DaoException("error");
        }
    }

    /**
     * loadFirstRecord
     * @return E
     */
    public E loadFirstRecord() {
        return getPlatkmEntityManager().createQuery("SELET * FROM " + DaoUtil.getTableName(entityClass), entityClass).setMaxResults(1).getSingleResult();
    }

    /**
     * find
     * @param filter filter
     * @return List
     * @throws DaoException DaoException
     */
    @Override
    public List<E> find(FilterCriteria filter) throws DaoException {
        return find(filter, this.entityClass);
    }

    /**
     * find
     * @param filter filter
     * @param returnClass returnClass
     * @return List
     * @throws DaoException DaoException
     */
    @Override
    public <F> List<F> find(FilterCriteria filter, Class<F> returnClass) throws DaoException {
        udpdateFilterCriteria(filter);
        FilterResult<F> filterResult = procesSearchAndFilter(filter, null, this.entityClass, returnClass);
        return filterResult.getList();
    }

    /**
     * count
     * @param whereCriteria whereCriteria
     * @return Integer
     * @throws DaoException DaoException
     */
    @Override
    public Integer count(WhereCriteria whereCriteria) throws DaoException {
        String tableName = DaoUtil.getTableName(entityClass);
        whereCriteria.addArguments("tableName", tableName);
        whereCriteria.addArguments("tableAlias", "");
        FilterResult<?> queryResult = getPlatkmEntityManager().getQueryManagerDao().search(QueryManager.C_FIND_BY_COUNT_CUSTOM_TABLE, whereCriteria, null, null);
        Object ob = queryResult.getList().get(0);
        return Integer.parseInt(((Object[]) ob)[0].toString());
    }

    /**
     * findOne
     * @param eq eq
     * @return E
     * @throws DaoException DaoException
     */
    @Override
    public E findOne(FilterCriteria eq) throws DaoException {
        return findOne(eq, this.entityClass);
    }

    /**
     * loadByCode
     * @param code code
     * @return E
     * @throws DaoException DaoException
     */
    public E loadByCode(String code) throws DaoException {
        return findOne(new FilterCriteria().eq("code", code), this.entityClass);
    }

    /**
     * findOne
     * @param filterCriteria filterCriteria
     * @param returnClass returnClass
     * @return F
     * @throws DaoException DaoException
     */
    @Override
    public <F> F findOne(FilterCriteria filterCriteria, Class<F> returnClass) throws DaoException {
        List<F> list = find(filterCriteria, returnClass);
        if (list != null && !list.isEmpty())
            return list.get(0);
        return null;
    }

    /**
     * getEntityClass
     * @return Class
     */
    public Class<E> getEntityClass() {
        return entityClass;
    }
}
