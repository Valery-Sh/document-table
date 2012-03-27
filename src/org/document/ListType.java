/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a java class that implements
 * <code>java.util.List</code> interface.
 *
 * @author V. Shyshkin
 */
public class ListType implements SchemaType, HasSupportedTypes {

    private List defaultValue;
    protected List<SchemaType> supportedTypes;
    private Class javaType;

    public ListType() {
        defaultValue = new ArrayList(2);
        supportedTypes = new ArrayList();
        javaType = List.class;
    }

    public ListType(Class javaType) {
        this();
        this.javaType = javaType;
    }

    @Override
    public Class getJavaType() {
        return javaType;
    }

    public void setJavaType(Class javaType) {
        this.javaType = javaType;
    }

    public void addSupportedByClass(Class type) {
        if (type == null) {
            throw new NullPointerException("ListType.addSupportedByClass(null)");
        }

        if (DocUtils.isValueType(type)) {
            if (type.isPrimitive()) {
                throw new IllegalArgumentException("ListType doesn't support primitive value type {" + type + "}. ");
            }
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

    public void addSupported(SchemaType type) {
        if (type == null) {
            throw new NullPointerException("ListType.addSupported(null)");
        }
        if ((type instanceof ValueType) && type.getJavaType().isPrimitive()) {
            throw new IllegalArgumentException("ListType doesn't support primitive value type {" + type.getJavaType() + "}. ");
        }
        supportedTypes.add(type);
    }

    protected List<SchemaType> getSupportedTypes() {
        return supportedTypes;
    }

    @Override
    public SchemaType getSupportedByClass(Class clazz) {
        Class type = clazz;
        if (clazz.isPrimitive()) {
            type = DocUtils.getWrapper(clazz);
        }

        SchemaType result = null;
        for (SchemaType st : getSupportedTypes()) {
            if (st.getJavaType().isAssignableFrom(type)) {
                result = st;
                break;
            }
        }
        if (result == null && getSupportedTypes().isEmpty()) {
            if (DocUtils.isValueType(type)) {
               result = new ValueType(type);
            } else if (DocUtils.isListType(type)) {
                result = new ListType(type);
            } else if (DocUtils.isArrayType(type)) {
                result = new ArrayType(type);
            } else if (DocUtils.isEmbeddedType(type)) {
                DocumentSchema ds = DocUtils.createSchema(type);
                result = new EmbeddedType(ds);
            }
        }
        return result;
    }

    public List getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(List value) {
        this.defaultValue = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ListType other = (ListType) obj;
        if (this.defaultValue != other.defaultValue && (this.defaultValue == null || !this.defaultValue.equals(other.defaultValue))) {
            return false;
        }
        if (this.supportedTypes != other.supportedTypes && (this.supportedTypes == null || !this.supportedTypes.equals(other.supportedTypes))) {
            return false;
        }
        if (this.javaType != other.javaType && (this.javaType == null || !this.javaType.equals(other.javaType))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.defaultValue != null ? this.defaultValue.hashCode() : 0);
        hash = 61 * hash + (this.supportedTypes != null ? this.supportedTypes.hashCode() : 0);
        hash = 61 * hash + (this.javaType != null ? this.javaType.hashCode() : 0);
        return hash;
    }
}
