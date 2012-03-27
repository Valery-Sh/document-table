/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.document;

/**
 *
 * @author Valery
 */
public class ValueType implements SchemaType{
    
    private Class javaType;
    private Object defaultValue;
    
    public ValueType(Class type) {
        this.javaType = type;
    }
    
    @Override
    public Class getJavaType() {
        return javaType;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ValueType other = (ValueType) obj;
        if (this.javaType != other.javaType && (this.javaType == null || !this.javaType.equals(other.javaType))) {
            return false;
        }
        if (this.defaultValue != other.defaultValue && (this.defaultValue == null || !this.defaultValue.equals(other.defaultValue))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.javaType != null ? this.javaType.hashCode() : 0);
        hash = 67 * hash + (this.defaultValue != null ? this.defaultValue.hashCode() : 0);
        return hash;
    }

    @Override
    public SchemaTypeSet getSupportedSchemaTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
