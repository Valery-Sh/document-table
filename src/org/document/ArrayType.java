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

    protected SchemaTypeSet supportedSchemaTypes;
    //protected List<SchemaType> supportedTypes;
    private Class javaType;

    /**
     * Creates an instances of the class and appends an object
     * <code>java.lang.Object.class</code> to a list of supported types (
     * <code>supportedTypes</code>).
     */
    public ArrayType() {
        this(Object[].class);
    }

    /**
     *
     * @param type specifies a java array type
     */
    public ArrayType(Class type) {
        this.javaType = type;
        init(type);
    }

    private void init(Class type) {
        supportedSchemaTypes = new ArrayTypeSet(this);        
        Class compType = type.getComponentType();
        if (compType.isArray()) {
            //addSupported(new ArrayType(compType));
            supportedSchemaTypes.add(new ArrayType(compType));
        } else {
            //addSupportedByClass(compType);
            supportedSchemaTypes.put(compType);
        }
    }

/*    protected void addSupportedByClass(Class type) {
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
*/
    public Class getBaseType() {

        SchemaType c = this;
        while (c instanceof ArrayType) {
            if (supportedSchemaTypes.isEmpty()) {
                break;
            }
            c = (SchemaType)((ArrayType) c).supportedSchemaTypes.toArray()[0];
        }
        return c.getJavaType();
    }

    public int getDimentionSize() {
        int sz = 0;
        SchemaType c = this;
        while (c instanceof ArrayType) {
            if (supportedSchemaTypes.isEmpty()) {
                break;
            }
            c = (SchemaType)((ArrayType) c).supportedSchemaTypes.toArray()[0];
            sz++;
        }

        return sz;
    }

    @Override
    public Class getJavaType() {
        return this.javaType;
    }
/*
    public void addSupported(SchemaType type) {
        if (!supportedTypes.isEmpty()) {
            supportedTypes.set(0, type);
        } else {
            supportedTypes.add(type);
        }
    }

    protected List<SchemaType> getSupportedTypes() {
        return supportedTypes;
    }
*/

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
        final ArrayType other = (ArrayType) obj;
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
        int hash = 5;
        hash = 97 * hash + (this.supportedSchemaTypes != null ? this.supportedSchemaTypes.hashCode() : 0);
        hash = 97 * hash + (this.javaType != null ? this.javaType.hashCode() : 0);
        return hash;
    }

    protected static class ArrayTypeSet extends SchemaTypeSet {
        protected ArrayType owner;
        public ArrayTypeSet(ArrayType owner) {
            this.owner = owner;
        }
        @Override
        public SchemaType put(Class type) {

            if (type == null) {
                throw new NullPointerException("ListType.addSupportedByClass(null)");
            }
            if ( ! this.isEmpty() ) {
                throw new UnsupportedOperationException("supportedSchemaType is not empty. Cannot put SchemaType");
            }
            
//            if (type.isPrimitive()) {
//                throw new IllegalArgumentException("ListType doesn't support primitive value type {" + type + "}. ");
//            }

            SchemaType result = super.put(type);
            return result;
        }

        @Override
        public boolean add(SchemaType schemaType) {
            if (schemaType == null) {
                throw new NullPointerException("ListType.addSupported(null)");
            }
            if ( ! this.isEmpty() ) {
                throw new UnsupportedOperationException("supportedSchemaType is not empty. Cannot add SchemaType");
            }
//            if (schemaType.getJavaType().isPrimitive()) {
//                throw new IllegalArgumentException("ListType doesn't support primitive value type {" + schemaType.getJavaType() + "}. ");
//            }
            this.clear();
            return super.add(schemaType);
        }
    }//class
}//class