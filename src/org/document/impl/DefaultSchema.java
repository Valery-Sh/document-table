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
        int i = fields.indexOf(fieldName);
        if ( i >= 0 ) {
            return fields.get(i);
        }
        return null;
    }
    
}
