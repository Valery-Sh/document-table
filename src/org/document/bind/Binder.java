/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document.bind;

/**
 *
 * @author Valery
 */
public interface Binder {
    void dataChanged(Object oldValue, Object newValue);
    void componentChanged(Object oldValue, Object newValue);
    
}
