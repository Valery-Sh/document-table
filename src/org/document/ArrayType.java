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
public class ArrayType {
    
    private List defaultValue;
    
    
    protected List supportedTypes;
    
    public ArrayType() {
        defaultValue = new ArrayList(2);
    }

    public void add(Class type) {
        supportedTypes.add(type);
    }

    public void add(ArrayType arrayType) {
        supportedTypes.add(arrayType);
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

    public List getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(List value) {
        this.defaultValue = value;
    }
    
    
}
