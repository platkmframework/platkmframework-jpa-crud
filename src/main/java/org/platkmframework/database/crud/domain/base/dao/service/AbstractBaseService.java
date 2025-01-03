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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.platkmframework.database.crud.domain.base.dao.EntityMap;
import org.platkmframework.database.crud.domain.base.dao.entity.EntityDaoImpl;
import org.platkmframework.database.query.common.exception.DaoException;
import org.platkmframework.jpa.exception.DatabaseConnectionException;
import org.platkmframework.persistence.filter.FilterResult;
import org.platkmframework.persistence.filter.converter.UIFilterToSearchCriteriaConverter;
import org.platkmframework.persistence.filter.criteria.FilterCriteria;
import org.platkmframework.persistence.filter.criteria.SearchCriteria;
import org.platkmframework.persistence.filter.ui.Filter;

/**
 *   Author:
 *     Eduardo Iglesias
 *   Contributors:
 *   	Eduardo Iglesias - initial API and implementation
 * @param <E> E
 * @param <VO> VO
 * @param <I> I
 */
public abstract class AbstractBaseService<E, VO, I> implements BaseService<VO, I> {

    /**
     * Atributo logger
     */
    private static Logger logger = LoggerFactory.getLogger(AbstractBaseService.class);

    /**
     * Atributo reposity
     */
    protected EntityDaoImpl<E, I> reposity;

    /**
     * Atributo returnClass
     */
    protected Class<VO> returnClass;

    /**
     * Constructor AbstractBaseService
     * @param reposity reposity
     * @param returnClass returnClass
     */
    public AbstractBaseService(EntityDaoImpl<E, I> reposity, Class<VO> returnClass) {
        super();
        this.reposity = reposity;
        this.returnClass = returnClass;
    }

    /**
     * search
     * @param filter filter
     * @return FilterResult
     * @throws DaoException DaoException
     */
    @Override
    public FilterResult<VO> search(Filter<?> filter) throws DaoException {
        return search(UIFilterToSearchCriteriaConverter.instance().convertToFilterCriteria(filter));
    }

    /**
     * search
     * @param filter filter
     * @return FilterResult
     * @throws DaoException DaoException
     */
    @Override
    public FilterResult<VO> search(FilterCriteria filter) throws DaoException {
        try {
            return reposity.search(filter, null, returnClass);
        } catch (DaoException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * create
     * @param vo vo
     * @throws DaoException DaoException
     */
    @Override
    public void create(VO vo) throws DaoException {
        try {
            E entity = EntityMap.map(vo, this.reposity.getEntityClass());
            reposity.insert(entity);
            //vo.setId(entity.getId());
        } catch (DatabaseConnectionException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * update
     * @param vo vo
     * @throws DaoException DaoException
     */
    @Override
    public void update(VO vo) throws DaoException {
        try {
            E entity = EntityMap.map(vo, this.reposity.getEntityClass());
            EntityMap.map(vo, entity);
            reposity.update(entity);
        } catch (DatabaseConnectionException e) {
            logger.error(e.getMessage());
            ;
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * removeById
     * @param id id
     * @throws DaoException DaoException
     */
    @Override
    public void removeById(I id) throws DaoException {
        try {
            reposity.removeById(id);
        } catch (DatabaseConnectionException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * save
     * @param vo vo
     * @throws DaoException DaoException
     */
    @Override
    public void save(VO vo) throws DaoException {
        try {
            E entity = EntityMap.map(vo, this.reposity.getEntityClass());
            reposity.save(entity);
        } catch (DatabaseConnectionException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * load
     * @param id id
     * @return VO
     * @throws DaoException DaoException
     */
    @Override
    public VO load(I id) throws DaoException {
        try {
            return reposity.load(id, this.returnClass);
        } catch (DaoException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * findAll
     * @return List
     * @throws DaoException DaoException
     */
    @Override
    public List<VO> findAll() throws DaoException {
        try {
            return reposity.selectAll(this.returnClass);
        } catch (DaoException e) {
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * find
     * @param filter filter
     * @return List
     * @throws DaoException DaoException
     */
    @Override
    public List<VO> find(Filter<?> filter) throws DaoException {
        return find(UIFilterToSearchCriteriaConverter.instance().convertToFilterCriteria(filter));
    }

    /**
     * find
     * @param filter filter
     * @return List
     * @throws DaoException DaoException
     */
    @Override
    public List<VO> find(FilterCriteria filter) throws DaoException {
        try {
            return reposity.find(filter, this.returnClass);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new DaoException(e.getMessage());
        }
    }

    /**
     * checkInUse
     * @param childs childs
     * @param id id
     * @throws DaoException DaoException
     */
    protected void checkInUse(String[] childs, Long id) throws DaoException {
        if (childs != null) {
            String[] tableInfo;
            List<Object[]> list;
            for (int i = 0; i < childs.length; i++) {
                tableInfo = childs[i].split(",");
                list = (List<Object[]>) reposity.getPlatkmEntityManager().getQueryDao().select(new SearchCriteria().select("count(*)", tableInfo[0], "").where().eq(tableInfo[1], id), null);
                Integer count = Integer.valueOf((list.get(0)[0]).toString());
                if (count > 0) {
                    throw new DaoException("El registro no se puede elimar porque esá siendo usado");
                }
            }
        }
    }

    /**
     * getReposity
     * @return EntityDaoImpl
     */
    public EntityDaoImpl<E, I> getReposity() {
        return reposity;
    }

    /**
     * setReposity
     * @param reposity reposity
     */
    public void setReposity(EntityDaoImpl<E, I> reposity) {
        this.reposity = reposity;
    }
}
