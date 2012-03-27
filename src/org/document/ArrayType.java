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
public class ArrayType implements SchemaType, HasSupportedTypes {

    protected List<SchemaType> supportedTypes;
    private Class javaType;

    /**
     * Creates an instances of the class and appends an object
     * <code>java.lang.Object.class</code> to a list of supported types
     * (
     * <code>supportedTypes</code>).
     */
    public ArrayType() {
        super();
        addSupported(new ValueType(Object.class));
    }

    /**
     *
     * @param type specifies a java array type
     */
    public ArrayType(Class type) {
        this.javaType = type;
        supportedTypes = new ArrayList<SchemaType>();
        Class compType = type.getComponentType();
        if (compType.isArray()) {
            addSupported(new ArrayType(compType));
        } else {
            addByClass(compType);
        }
    }

    public void addByClass(Class type) {
        if (DocUtils.isValueType(type)) {
            addSupported(new ValueType(type));
        } else if (DocUtils.isListType(type) && type.isArray()) {
            addSupported(new ArrayType(type));
        } else if (DocUtils.isListType(type)) {
            addSupported(new ListType(type));
        } else if (DocumentReference.class.isAssignableFrom(type)) {
            addSupported(new ReferenceType());
        } else {
            DocumentSchema embSchema = DocUtils.createSchema(type);
            addSupported(new EmbeddedType(embSchema));
        }
    }

    @Override
    public SchemaType getSupportedByClass(Class type) {
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
        while (c instanceof ArrayType) {
            if (supportedTypes.isEmpty()) {
                break;
            }
            c = ((ArrayType) c).supportedTypes.get(0);
        }
        return c.getJavaType();
    }

    public int getDimentionSize() {
        int sz = 0;
        SchemaType c = this;
        while (c instanceof ArrayType) {
            if (supportedTypes.isEmpty()) {
                break;
            }
            c = ((ArrayType) c).supportedTypes.get(0);
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
            //throw new IndexOutOfBoundsException("org.document.ArrayType may have only one supported type");
        } else {
            supportedTypes.add(type);
        }
    }

    protected List<SchemaType> getSupportedTypes() {
        return supportedTypes;
    }
}