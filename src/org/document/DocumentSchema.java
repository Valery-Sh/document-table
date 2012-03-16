/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.Map;

/**
 *
 * @author Valery
 */
public interface DocumentSchema<K> {
    Class getMappingType();
    //Field getField(K fieldName);
    //void put(K fieldName,Field field);
    Map<K,Field> getFields();
}
