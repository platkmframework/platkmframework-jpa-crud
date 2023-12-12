package org.platkmframework.database.crud.domain.base.dao.entity;

import java.util.List;

import org.platkmframework.annotation.db.CustomJoin;
import org.platkmframework.annotation.db.CustomJoins;
import org.platkmframework.common.domain.filter.FilterResult;
import org.platkmframework.common.domain.filter.criteria.FilterCriteria;
import org.platkmframework.common.domain.filter.enumerator.ConditionOperator;
import org.platkmframework.common.domain.filter.enumerator.InnerOperator;
import org.platkmframework.common.domain.filter.info.FilterData;
import org.platkmframework.common.domain.filter.info.FilterDataType;
import org.platkmframework.database.query.common.exception.DaoException;
import org.platkmframework.jpa.base.PlatkmEntityManager;
import org.platkmframework.jpa.base.PlatkmQuery;
import org.platkmframework.jpa.util.DaoUtil;
 

public abstract class BaseEntityDao<E> implements BaseEntityDaoI<E> {
	
	protected Class<E> entityClass; 
	
	
	@Override
	public FilterResult<E> search(FilterCriteria filter, List<Object> params) throws DaoException { 
		udpdateFilterCriteria(filter); 
		return procesSearchAndFilter(filter, params, entityClass, this.entityClass);   
	}
   
	@Override
	public <F> FilterResult<F> search(FilterCriteria filter, List<Object> params, Class<F> returnClass) throws DaoException{  
		udpdateFilterCriteria(filter); 
		return procesSearchAndFilter(filter, params, entityClass, returnClass); 
	} 
	
	protected void udpdateFilterCriteria(FilterCriteria filter) {
		if(filter.emptyConditions()) {
			FilterData filterData = new FilterData();
			filterData.setFilterDataType(FilterDataType.VALUEINFO);
			filterData.addValue(1, ConditionOperator.equal, 1);
			filter.getSql().add(filterData);
		}
	}
	
	protected <F> FilterResult<F>  procesSearchAndFilter(FilterCriteria filterCriteria , List<Object> params, Class<E> entityClass1,  Class<F> returnClass) throws DaoException {

		if(returnClass == null) {
			throw new DaoException("returnClass should not be null");
		}
		/**if(filter.getSql().isEmpty()) {
			logger.error(" filterCriteria to remove enitty " + this.entityClass.getName() + " is empty" );
			throw new DaoException("criteria is empty");
		}*/
		
		FilterData filterDataSelect = filterCriteria.getSql().stream()
				  .filter( (obj-> ((FilterData)obj).isType(FilterDataType.SELECTINFO)))
				  .findAny()
				  .orElse(null);
		
		if(filterDataSelect == null) {
			filterCriteria.getSql().add(0, new FilterData(FilterDataType.SELECTINFO));  
		}
		 
		FilterData filterDataFrom = filterCriteria.getSql().stream()
													  .filter( (obj-> ((FilterData)obj).isType(FilterDataType.FROMINFO)))
													  .findAny()
													  .orElse(null);  
		String tableName = DaoUtil.getTableName(entityClass1);
		if(filterDataFrom == null) {
			filterDataFrom = new FilterData().addFromInfo(tableName, null);
			filterCriteria.getSql().add(1, filterDataFrom);
		}else {
			filterDataFrom.setFrom(tableName);
		} 
		
		/**ProcessInfo processInfo = new ProcessInfo(true, ProcessType.SELECT);  
		processInfo.setApplyAdditional(false); 
		*/  
		CustomJoins customJoins = returnClass.getAnnotation(CustomJoins.class);
		if(customJoins != null) {
			int position = 2;
			filterDataSelect.setSelectColumns(filterDataFrom.getAlias() + ".*");
			
			for (CustomJoin customJoin : customJoins.join()) {
				filterDataSelect.setSelectColumns( filterDataSelect.getSelectColumns() + ", " +  customJoin.columns());
				
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
	
	public abstract PlatkmEntityManager getPlatkmEntityManager();

}
