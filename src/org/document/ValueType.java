/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.document;

/**
 *
 * @author Valery
 */
public class ValueType {
    
    private Class type;
    private Object defaultValue;
    
    public ValueType(Class type) {
        this.type = type;
    }
    
    public Class getType() {
        return type;
    }
    
    
    public int compare(Object o1, Object o2) {
        int r;
        if ( o1 == null && o2 == null) {
            r = 0;
        } else if ( o1 == null ) {
            r = -1;
        } else if ( o2 == null ) {
            r = 1;
        } else if ( o1 == o2) {
            r = 0;
        } else if ( o1 instanceof Comparable) {
            r = ((Comparable)o1).compareTo((Comparable)o2);
        } else {
            r = -2;
        }
        return r;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(Object value) {
        this.defaultValue = value;
    }
    
}
