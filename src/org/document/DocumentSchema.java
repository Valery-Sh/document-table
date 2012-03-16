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
public interface DocumentSchema<K,V> {
    Class getMappingType();
    Field getField(K fieldName);
    Field put(K fieldName,Class prefferedType);
}
