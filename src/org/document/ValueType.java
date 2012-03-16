/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

/**
 *
 * @author Valery
 */
public interface  ValueType {
    Class getType();
    public Object cloneValue(Object value);
    int compare(Object o1, Object o2);
}
