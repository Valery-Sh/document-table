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
public class ComponentType implements SchemaType {

    protected List<SchemaType> supportedTypes;
    private Class javaType;

    /**
     * Creates an instances of the class and appends an object
     * <code>java.lang.Object.class</code> to a list of supported types
     * (
     * <code>supportedTypes</code>).
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
        this.javaType = type;
        supportedTypes = new ArrayList<SchemaType>();
        Class compType = type.getComponentType();
        if (compType.isArray()) {
            addSupported(new ComponentType(compType));
        } else {
            addByClass(compType);
        }
    }

    public void addByClass(Class type) {
        if (DocUtils.isValueType(type)) {
            addSupported(new ValueType(type));
        } else if (DocUtils.isArrayType(type) && type.isArray()) {
            addSupported(new ComponentType(type));
        } else if (DocUtils.isArrayType(type)) {
            addSupported(new ArrayType(type));
        } else if (DocumentReference.class.isAssignableFrom(type)) {
            addSupported(new ReferenceType());
        } else {
            DocumentSchema embSchema = DocUtils.createSchema(type);
            addSupported(new EmbeddedType(embSchema));
        }
    }

    public SchemaType getSupportedType(Class type) {
        SchemaType result = null;
        for (SchemaType st : getSupportedTypes()) {
            if (st.getJavaType().isAssignableFrom(type)) {
                result = st;
                break;
            }
        }
        return result;
    }

    public Class getBaseType() {

        SchemaType c = this;
        while (c instanceof ComponentType) {
            if (supportedTypes.isEmpty()) {
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
            if (supportedTypes.isEmpty()) {
                break;
            }
            c = ((ComponentType) c).supportedTypes.get(0);
            sz++;
        }

        return sz;
    }

    @Override
    public Class getJavaType() {
        return this.javaType;
    }

    public void addSupported(SchemaType type) {
        if (!supportedTypes.isEmpty()) {
            supportedTypes.set(0, type);
            //throw new IndexOutOfBoundsException("org.document.ComponentType may have only one supported type");
        } else {
            supportedTypes.add(type);
        }
    }

    protected List<SchemaType> getSupportedTypes() {
        return supportedTypes;
    }
}//ComponentType
