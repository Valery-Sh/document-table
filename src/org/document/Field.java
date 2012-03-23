/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valery
 */
public class Field {
    protected Object name;
    protected List<SchemaType> supportedTypes;
    protected boolean required;
    protected boolean notNull;
    
    protected boolean tail;

    public Field(Object name) {
        assert(name != null);
        this.name = name;
        supportedTypes = new ArrayList<SchemaType>();
        //ValueType vt = new ValueType(Object.class);
        //supportedTypes.add(vt);
    }
    public Field(Object name,boolean required,boolean notNull){//,Class ... supported) {
        this(name);
        this.name = name;
        this.required = required;
        this.notNull = notNull;
/*        if ( supported != null ) {
            
            for ( Class clazz : supported) {
                ValueType vt = new ValueType(clazz);
                supportedTypes.add(vt);
            }
        }
        */
    }

    public boolean isTail() {
        return tail;
    }

    public void setTail(boolean tail) {
        this.tail = tail;
    }

    public void add(SchemaType type) {
        supportedTypes.add(type);
    }
    
    protected List<SchemaType> getSupportedTypes() {
        return supportedTypes;
    }

    public Object getName() {
        return name;
    }

    public boolean isRequired() {
        return this.required;
    }

    public boolean isNotNull() {
        return this.notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Field other = (Field) obj;
        if (this.name != other.name && (this.name == null || !this.name.equals(other.name))) {
            return false;
        }
        if (this.supportedTypes != other.supportedTypes && (this.supportedTypes == null || !this.supportedTypes.equals(other.supportedTypes))) {
            return false;
        }
        if (this.required != other.required) {
            return false;
        }
        if (this.notNull != other.notNull) {
            return false;
        }
        if (this.tail != other.tail) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.supportedTypes != null ? this.supportedTypes.hashCode() : 0);
        hash = 97 * hash + (this.required ? 1 : 0);
        hash = 97 * hash + (this.notNull ? 1 : 0);
        hash = 97 * hash + (this.tail ? 1 : 0);
        return hash;
    }
    
}
