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
package org.platkmframework.database.crud.domain.base.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.platkmframework.util.error.InvocationException;
import org.platkmframework.util.reflection.ReflectionUtil;


/**
 *   Author: 
 *     Eduardo Iglesias
 *   Contributors: 
 *   	Eduardo Iglesias - initial API and implementation
 **/
public class EntityMap {
	
	private static final Logger logger = LogManager.getLogger(EntityMap.class);
	
	
	public static <F> F map(Object sourceObj, Class<F> targetClass) {

		try {
			
			F target = ReflectionUtil.createInstance1(targetClass);
			return map(sourceObj, target);
			
		} catch (InvocationException e) { 
			logger.error(e,e);
			return null;
		} 
		
	}
	
	public static <F> F map(Object sourceObj, F target) {
		
		try {
	  
			List<Field> targetFields =  ReflectionUtil.getAllFieldHeritage(target.getClass());
			List<Field> sourceFields =  ReflectionUtil.getAllFieldHeritage(sourceObj.getClass());
			if(targetFields != null){
				Field targetField;   
				String targetColumnName;
				String targetFieldName; 
				Column column;
				Field sourceFieldFound;
				boolean targetAccessValue = false;
				boolean sourceAcessValue = false;
				for (int i = 0; i < targetFields.size(); i++) {
					
					targetColumnName = null;
					targetField     = targetFields.get(i);
					targetFieldName = targetField.getName();
					
					if(targetField.isAnnotationPresent(Column.class)) {
						column = targetField.getAnnotation(Column.class);
						targetColumnName = column.name();  
					} 
					
					sourceFieldFound = null;
					for (Field sourceField : sourceFields) {
						if(StringUtils.isNotBlank(targetColumnName) && 
								sourceField.isAnnotationPresent(Column.class) && 
										targetColumnName.equals(sourceField.getAnnotation(Column.class).name())) {
							sourceFieldFound = sourceField;
							break;
						}else if(sourceField.getName().equals(targetFieldName)){
							sourceFieldFound = sourceField;
							break;
						} 
					}
					if(sourceFieldFound == null) {
						logger.warn("attribute name not found " + targetFieldName);
					}else {
						targetAccessValue = targetField.canAccess(target);
						targetField.setAccessible(true);  
						
						sourceAcessValue = sourceFieldFound.canAccess(sourceObj);
						sourceFieldFound.setAccessible(true);
						
					    targetField.set(target, sourceFieldFound.get(sourceObj)); 
					    
					    targetField.setAccessible(targetAccessValue); 
					    sourceFieldFound.setAccessible(sourceAcessValue); 
					}	
				}
			}	
			return target;
			
		} catch (Exception e) {
			logger.error(e,e);
			return null;
		} 
	}
	 
	public static <E> List<E>  mapperArray(Object[] list, Class<E> class1) 
	{
		List<E> listResult = new ArrayList<>();
		if(list != null) {
			for (int i = 0; i < list.length; i++) {
				listResult.add(map(list[i], class1));
			}
		} 
	    return listResult;
	}	
	
	public static <E> List<E> mapperList(List<?> list, Class<E> class1) 
	{
		List<E> listResult = new ArrayList<>();
		list.stream().forEach(fd -> listResult.add(map(fd, class1)));
	    return listResult;
	}

}
