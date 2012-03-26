/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document.impl;

import org.document.Field;

/**
 *
 * @author V. Shyshkin
 */
public class MapSchema extends DefaultSchema {

    
    public MapSchema() {
        super();
    }
    public MapSchema(Class mappingType ) {
        super(mappingType);
    }    
    

    @Override
    public Field getField(Object fieldName) {
        Field f = super.getField(fieldName);
        if ( f == null ) {
            return new Field(fieldName);
        }
        return null;
    }

    
}
