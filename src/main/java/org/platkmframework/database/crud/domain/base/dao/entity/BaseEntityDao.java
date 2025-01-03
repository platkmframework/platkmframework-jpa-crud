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
import org.platkmframework.annotation.db.CustomJoin;
import org.platkmframework.annotation.db.CustomJoins;
import org.platkmframework.database.query.common.exception.DaoException;
import org.platkmframework.jpa.base.PlatkmORMEntityManager;
import org.platkmframework.jpa.base.PlatkmQuery;
import org.platkmframework.jpa.util.DaoUtil;
import org.platkmframework.persistence.filter.FilterResult;
import org.platkmframework.persistence.filter.criteria.FilterCriteria;
import org.platkmframework.persistence.filter.enumerator.ConditionOperator;
import org.platkmframework.persistence.filter.enumerator.InnerOperator;
import org.platkmframework.persistence.filter.info.FilterData;
import org.platkmframework.persistence.filter.info.FilterDataType;

/**
 *   Author:
 *     Eduardo Iglesias
 *   Contributors:
 *   	Eduardo Iglesias - initial API and implementation
 * @param <E> E
 */
public abstract class BaseEntityDao<E> implements BaseEntityDaoI<E> {

    /**
     * BaseEntityDao
     */
    protected Class<E> entityClass;

    /**
     * search
     * @param filter filter
     * @param params params
     * @return FilterResult
     * @throws DaoException DaoException
     */
    @Override
    public FilterResult<E> search(FilterCriteria filter, List<Object> params) throws DaoException {
        udpdateFilterCriteria(filter);
        return procesSearchAndFilter(filter, params, entityClass, this.entityClass);
    }

    /**
     * search
     * @param filter filter
     * @param params params
     * @param returnClass returnClass
     * @return FilterResult
     * @throws DaoException DaoException
     */
    @Override
    public <F> FilterResult<F> search(FilterCriteria filter, List<Object> params, Class<F> returnClass) throws DaoException {
        udpdateFilterCriteria(filter);
        return procesSearchAndFilter(filter, params, entityClass, returnClass);
    }

    /**
     * udpdateFilterCriteria
     * @param filter filter
     */
    protected void udpdateFilterCriteria(FilterCriteria filter) {
        if (filter.emptyConditions()) {
            FilterData filterData = new FilterData();
            filterData.setFilterDataType(FilterDataType.VALUEINFO);
            filterData.addValue(1, ConditionOperator.equal, 1);
            filter.getSql().add(filterData);
        }
    }

    /**
     * procesSearchAndFilter
     * @param <F> F
     * @param filterCriteria filterCriteria
     * @param params params
     * @param entityClass1 entityClass1
     * @param returnClass returnClass
     * @return FilterResult
     * @throws DaoException DaoException
     */
    protected <F> FilterResult<F> procesSearchAndFilter(FilterCriteria filterCriteria, List<Object> params, Class<E> entityClass1, Class<F> returnClass) throws DaoException {
        if (returnClass == null) {
            throw new DaoException("returnClass should not be null");
        }
        FilterData filterDataSelect = filterCriteria.getSql().stream().filter((obj -> ((FilterData) obj).isType(FilterDataType.SELECTINFO))).findAny().orElse(null);
        if (filterDataSelect == null) {
            filterCriteria.getSql().add(0, new FilterData(FilterDataType.SELECTINFO));
        }
        FilterData filterDataFrom = filterCriteria.getSql().stream().filter((obj -> ((FilterData) obj).isType(FilterDataType.FROMINFO))).findAny().orElse(null);
        String tableName = DaoUtil.getTableName(entityClass1);
        if (filterDataFrom == null) {
            filterDataFrom = new FilterData().addFromInfo(tableName, null);
            filterCriteria.getSql().add(1, filterDataFrom);
        } else {
            filterDataFrom.setFrom(tableName);
        }
        CustomJoins customJoins = returnClass.getAnnotation(CustomJoins.class);
        if (customJoins != null) {
            int position = 2;
            filterDataSelect.setSelectColumns(filterDataFrom.getAlias() + ".*");
            for (CustomJoin customJoin : customJoins.join()) {
                filterDataSelect.setSelectColumns(filterDataSelect.getSelectColumns() + ", " + customJoin.columns());
                filterCriteria.getSql().add(position, new FilterData().addJoinInfo(InnerOperator.inner_join, customJoin.table(), customJoin.alias()));
                position++;
                //TODO se debe quitar el id que esta clavado
                filterCriteria.getSql().add(position, new FilterData().addExpressionInfo(filterDataFrom.getAlias() + "." + customJoin.on(), ConditionOperator.equal, customJoin.alias() + ".id", true));
                position++;
            }
        }
        PlatkmQuery query = getPlatkmEntityManager().createQuery(filterCriteria, params, returnClass);
        FilterResult<F> filterResult = new FilterResult<>();
        filterResult.setPage(query.getPage());
        filterResult.setPageCount(query.getPageCount());
        filterResult.setList(query.getResultList());
        return filterResult;
    }

    /**
     * getPlatkmEntityManager
     * @return PlatkmORMEntityManager
     */
    public abstract PlatkmORMEntityManager getPlatkmEntityManager();

    /**
     * BaseEntityDao
     */
    public BaseEntityDao() {
        super();
    }
}
