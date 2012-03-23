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
     * Creates an instances of the class and appends an object
     * <code>java.lang.Object.class</code> to a list of supported types
     * (<code>supportedTypes</code>).
     */
    public ComponentType() {
        super();
        addSupported(new ValueType(Object.class));
    }

    /**
     *
     * @param type specifies a java array type
     */
    public ComponentType(Class type) {
        super(type);
        Class compType = type.getComponentType();
        if (compType.isArray()) {
            addSupported(new ComponentType(compType));
        } else {
            addByClass(compType);
        }
    }

    @Override
    public boolean isComponentType() {
        return true;
    }

    public Class getBaseType() {

        SchemaType c = this;
        while (c instanceof ComponentType) {
            if ( supportedTypes.isEmpty() ) {
                break;
            }
            c = ((ComponentType) c).supportedTypes.get(0);
        }
        return c.getJavaType();
    }
    public int getDimentionSize() {
        int sz = 0;
        SchemaType c = this;
        while (c instanceof ComponentType) {
            if ( supportedTypes.isEmpty() ) {
                break;
            }
            c = ((ComponentType) c).supportedTypes.get(0);
            sz++;
        }
        
        return sz;
    }
    
}//ComponentType
