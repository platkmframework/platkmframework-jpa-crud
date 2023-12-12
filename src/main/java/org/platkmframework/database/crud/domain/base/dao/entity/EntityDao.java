/*******************************************************************************
 * Copyright(c) 2023 the original author Eduardo Iglesias Taylor.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	 https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * 	Eduardo Iglesias Taylor - initial API and implementation
 *******************************************************************************/
package org.platkmframework.database.crud.domain.base.dao.entity;
 
import java.util.List;

import org.platkmframework.common.domain.filter.criteria.DeleteCriteriaEntity;
import org.platkmframework.common.domain.filter.criteria.FilterCriteria;
import org.platkmframework.common.domain.filter.criteria.WhereCriteria;
import org.platkmframework.database.query.common.exception.DaoException; 

/**
 *   Author: 
 *     Eduardo Iglesias
 *   Contributors: 
 *   	Eduardo Iglesias - initial API and implementation
 * @param <E>
 * @param <I>
 **/
public interface EntityDao<E,I> extends BaseEntityDaoI<E>
{
	 
	public E load(I id); 
	
	public E loadByCode(String code) throws DaoException;  
	
	public <F> F load(I id, Class<F> class1) throws DaoException; 
	
	public List<E> load(List<I> ids) throws DaoException; 
	
	public <F> List<F> load(List<I> ids, Class<F> returnClass) throws DaoException; 
	  
	public void save(E entity);
	
	public Object insert(E entity);
	
	public void inserList(List<E> entities);
	
	public void updateList(List<E> entities);
	
	public void update(E entity);
	
	public void removeById(I id);
	
	public void remove(E entity);
	 
	public List<E> selectAll();
	
	public <F> List<F> selectAll( Class<F> returnClass) throws DaoException; 
	
	public E loadFirstRecord(); 

	public List<E> find(FilterCriteria filter) throws DaoException; 
	
	public <F> List<F> find(FilterCriteria filter, Class<F> returnClass) throws DaoException; 
	 
	public void remove(DeleteCriteriaEntity deleteCriteriaEntity) throws DaoException; 
	
	public Integer count(WhereCriteria filter) throws DaoException;
	
	public E findOne(FilterCriteria filter) throws DaoException;
	
	public <F> F findOne(FilterCriteria filter,  Class<F> returnClass) throws DaoException;
	


}
