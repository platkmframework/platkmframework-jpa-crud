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
package org.platkmframework.database.crud.domain.base.dao.service;
  
import java.util.List;

import org.platkmframework.common.domain.filter.FilterResult;
import org.platkmframework.common.domain.filter.criteria.FilterCriteria;
import org.platkmframework.database.query.common.exception.DaoException;


/**
 *   Author: 
 *     Eduardo Iglesias
 *   Contributors: 
 *   	Eduardo Iglesias - initial API and implementation
 **/ 
public interface BaseService<VO , I>
{
	
	public FilterResult<VO> search(FilterCriteria filter) throws DaoException;
	
	public void create(VO vo) throws DaoException;
	
	public void update(VO entity) throws DaoException;
	
	public void save(VO entity) throws DaoException;
	
	public void removeById(I id) throws DaoException;
	   
	public VO load(I id) throws DaoException;
	
	public List<VO> findAll() throws DaoException;

	public List<VO> find(FilterCriteria filter) throws DaoException;

}
