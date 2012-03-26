/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document.impl;

import java.util.List;
import org.document.DocumentSchema;
import org.document.Field;

/**
 *
 * @author Valery
 */
public class NoFieldSchema extends DefaultSchema {

    public NoFieldSchema() {
        super();
    }
    public NoFieldSchema(Class mappingType ) {
        super(mappingType);
    }    

    @Override
    public List getFields() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Field getField(Object fieldName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
