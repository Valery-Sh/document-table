/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document.bind;

/**
 *
 * @author Valery
 */
public abstract class AbstractBinder implements Binder {
    
    protected HasValue dataObject;
    protected HasValue compObject;    
    
    @Override
    public void dataChanged(Object dataValue, Object newValue) {
        
    }

    @Override
    public void componentChanged(Object oldValue, Object newValue) {
        
    }
    

    protected void setComponentValue(Object dataValue) {
        if ( ! ( dataValue instanceof HasValue)) {
            return;
        }
        
        Object cv = componentValueOf(dataValue);
        
    }
    protected abstract Object componentValueOf(Object dataValue);
    protected abstract Object dataValueOf(Object compValue);
    
    
    
    
}
