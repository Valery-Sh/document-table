/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document.impl;

import java.util.ArrayList;
import java.util.List;
import org.document.DocumentSchema;
import org.document.Field;

/**
 *
 * @author Valery
 */
public class DefaultSchema implements DocumentSchema{
    private Class mappingType;
    private List<Field> fields;
    
    public DefaultSchema() {
        fields = new ArrayList(10);
    }
    public DefaultSchema(Class mappingType ) {
        this();
        this.mappingType = mappingType;
    }    
    
    @Override
    public Class getMappingType() {
        return this.mappingType;
    }

    @Override
    public List getFields() {
        return this.fields;
    }

    @Override
    public Field getField(Object fieldName) {
        for ( Field f : fields) {
            if ( f.getName().equals(fieldName)) {
                return f;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DefaultSchema other = (DefaultSchema) obj;
        if (this.mappingType != other.mappingType && (this.mappingType == null || !this.mappingType.equals(other.mappingType))) {
            return false;
        }
        if (this.fields != other.fields && (this.fields == null || !this.fields.equals(other.fields))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.mappingType != null ? this.mappingType.hashCode() : 0);
        hash = 23 * hash + (this.fields != null ? this.fields.hashCode() : 0);
        return hash;
    }

    
}
