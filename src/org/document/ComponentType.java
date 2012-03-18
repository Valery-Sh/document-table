/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

/**
 *
 * @author Valery
 */
public class ComponentType extends ArrayType {

    /**
     *
     * @param type specifies a java array type
     */
    public ComponentType(Class type) {
        super();

        this.componentType = true;        
        Class compType = type.getComponentType();
        if (compType.isArray()) {
            add(new ComponentType(compType));
        } else {
            addByClass(compType);
        }
    }
    
}//ComponentType
