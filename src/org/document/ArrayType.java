/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Valery
 */
public class ArrayType {
    
    private List defaultValue;
    protected boolean componentType;
    
    protected List supportedTypes;
    
    public ArrayType() {
        defaultValue = new ArrayList(2);
    }

    public boolean isComponentType() {
        return componentType;
    }
    
/*    public ArrayType(Class ... supports) {
        defaultValue = new ArrayList(2);
        supportedTypes.addAll(Arrays.asList(supports));
    }
*/
    public void addByClass(Class type) {
        if ( DocUtils.isValueType(type)) {
            add(new ValueType(type));
        } else if ( DocUtils.isArrayType(type) && type.isArray()) {
            add(new ComponentType(type));
        } else if ( DocUtils.isArrayType(type) ) {
            add(new ArrayType());
        } else if (DocumentReference.class.isAssignableFrom(type)) {
            add(new ReferenceType());
        } else {
            DocumentSchema embSchema = DocUtils.createSchema(type);
            add(embSchema);
        }
    }
    
    public void add(ValueType type) {
        supportedTypes.add(type);
    }

    public void add(ArrayType arrayType) {
        supportedTypes.add(arrayType);
    }
    
    public void add(DocumentSchema embedded) {
        supportedTypes.add(embedded);
    }
    public void add(ReferenceType ref) {
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
