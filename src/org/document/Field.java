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
    protected List supportedTypes;
    protected boolean required;
    protected boolean notNull;

    public Field(Object name) {
        assert(name != null);
        this.name = name;
        supportedTypes = new ArrayList();
        //ValueType vt = new ValueType(Object.class);
        //supportedTypes.add(vt);
    }
    public Field(Object name,boolean required,boolean notNull,Class ... supported) {
        assert(name != null);
        this.name = name;
        this.required = required;
        this.notNull = notNull;
        if ( supported != null ) {
            for ( Class clazz : supported) {
                ValueType vt = new ValueType(clazz);
                supportedTypes.add(vt);
            }
        }
    }

    public Field(Object name,boolean required,boolean notNull) {
        this(name, required, notNull,(Class[])null);
    }

/*    public Field(Object name,boolean required,boolean notNull,DocumentSchema ... schema) {
        this(name, required, notNull);
        if ( schema != null ) {
            for ( DocumentSchema dc : schema){
                this.add(dc);
            }
        }
    }
*/
    
    public void add(ValueType type) {
        supportedTypes.add(type);
    }

    public void add(DocumentSchema embedded) {
        supportedTypes.add(embedded);
    }
    public void add(DocumentReference ref) {
        supportedTypes.add(ref);
    }

    public List getSupportedTypes() {
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
    
}
