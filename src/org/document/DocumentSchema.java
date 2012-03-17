/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.List;

/**
 *
 * @author Valery
 */
public interface DocumentSchema<K> {
    Class getMappingType();
    List<Field> getFields();
    Field getField(Object fieldName);
}
