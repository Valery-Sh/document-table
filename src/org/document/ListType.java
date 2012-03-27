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
public class ListType implements SchemaType {

    private List defaultValue;
    protected SchemaTypeSet supportedSchemaTypes;
    private Class javaType;

    public ListType() {
        defaultValue = new ArrayList(2);
        supportedSchemaTypes = new ListType.ListTypeSet();
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
/*
    public void addSupportedByClass(Class type) {
        if (type == null) {
            throw new NullPointerException("ListType.addSupportedByClass(null)");
        }

        if (DocUtils.isValueType(type)) {
            if (type.isPrimitive()) {
                throw new IllegalArgumentException("ListType doesn't support primitive value type {" + type + "}. ");
            }
            addSupported(new ValueType(type));
        } else if (DocUtils.isListType(type)) {
            addSupported(new ListType(type));
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
*/
    public List getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(List value) {
        this.defaultValue = value;
    }


    @Override
    public SchemaTypeSet getSupportedSchemaTypes() {
        return this.supportedSchemaTypes;
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
        if (this.supportedSchemaTypes != other.supportedSchemaTypes && (this.supportedSchemaTypes == null || !this.supportedSchemaTypes.equals(other.supportedSchemaTypes))) {
            return false;
        }
        if (this.javaType != other.javaType && (this.javaType == null || !this.javaType.equals(other.javaType))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.defaultValue != null ? this.defaultValue.hashCode() : 0);
        hash = 13 * hash + (this.supportedSchemaTypes != null ? this.supportedSchemaTypes.hashCode() : 0);
        hash = 13 * hash + (this.javaType != null ? this.javaType.hashCode() : 0);
        return hash;
    }

    protected static class ListTypeSet extends SchemaTypeSet {

        @Override
        public SchemaType get(Class clazz) {
            Class type = clazz;
            if (clazz.isPrimitive()) {
                type = DocUtils.getWrapper(clazz);
            }

            SchemaType result = super.get(type);
            
            if (result == null && this.isEmpty()) {
                result = schemaTypeOf(type);
            }
            return result;
        }

        @Override
        public SchemaType put(Class type) {

            if (type == null) {
                throw new NullPointerException("ListType.addSupportedByClass(null)");
            }
            if (type.isPrimitive()) {
                throw new IllegalArgumentException("ListType doesn't support primitive value type {" + type + "}. ");
            }

            SchemaType result = super.put(type);
            return result;
        }

        @Override
        public boolean add(SchemaType schemaType) {
            if (schemaType == null) {
                throw new NullPointerException("ListType.addSupported(null)");
            }
            if (schemaType.getJavaType().isPrimitive()) {
                throw new IllegalArgumentException("ListType doesn't support primitive value type {" + schemaType.getJavaType() + "}. ");
            }
            return super.add(schemaType);
        }
    }//class
}