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
public class ArrayType implements SchemaType {

    private List defaultValue;
    protected List<SchemaType> supportedTypes;
    private Class javaType;

    public ArrayType() {
        defaultValue = new ArrayList(2);
        supportedTypes = new ArrayList();
    }

    public ArrayType(Class javaType) {
        this();
        this.javaType = javaType;
    }

    public boolean isComponentType() {
        return false;
    }

    @Override
    public Class getJavaType() {
        return javaType;
    }

    public void setJavaType(Class javaType) {
        this.javaType = javaType;
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

    public void addSupported(SchemaType type) {
        if (isComponentType() && !supportedTypes.isEmpty()) {
            supportedTypes.set(0, type);
            //throw new IndexOutOfBoundsException("org.document.ComponentType may have only one supported type");
        } else {
            supportedTypes.add(type);
        }
    }

    protected List<SchemaType> getSupportedTypes() {
        return supportedTypes;
    }

    public SchemaType getSupportedType(Class type) {
        SchemaType result = null;
        for (SchemaType st : getSupportedTypes()) {
            if (st.getJavaType().equals(type)) {
                result = st;
                break;
            }
        }
        if ( result == null && getSupportedTypes().isEmpty() ) {
            if ( DocUtils.isValueType(type)) {
                result = new ValueType(type);
            } else if ( DocUtils.isArrayType(type)) {
                if ( ! type.isArray() ) {
                    result = new ArrayType(type);
                } else {
                    //TODO
                }
            } else if ( DocUtils.isEmbeddedType(type)) {
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
        final ArrayType other = (ArrayType) obj;
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
