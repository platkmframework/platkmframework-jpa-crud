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

/**
 *   Author:
 *     Eduardo Iglesias
 *   Contributors:
 *   	Eduardo Iglesias - initial API and implementation
 */
public class OptionValue {

    /**
     * Atributo key
     */
    private Object key;

    /**
     * Atributo text
     */
    private String text;

    /**
     * Atributo selected
     */
    private boolean selected;

    /**
     * Atributo data
     */
    private String data;

    /**
     * Constructor OptionValue
     */
    public OptionValue() {
        super();
    }

    /**
     * Constructor OptionValue
     * @param key key
     * @param text text
     */
    public OptionValue(Object key, String text) {
        this.key = key;
        this.text = text;
    }

    /**
     * getKey
     * @return Object
     */
    public Object getKey() {
        return key;
    }

    /**
     * setKey
     * @param key key
     */
    public void setKey(Object key) {
        this.key = key;
    }

    /**
     * getText
     * @return String
     */
    public String getText() {
        return text;
    }

    /**
     * setText
     * @param text text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * isSelected
     * @return boolean
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * setSelected
     * @param selected selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * getData
     * @return String
     */
    public String getData() {
        return data;
    }

    /**
     * setData
     * @param data data
     */
    public void setData(String data) {
        this.data = data;
    }
}
