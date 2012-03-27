/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.HashSet;

/**
 *
 * @author Valery
 */
public class SchemaTypeSet extends HashSet<SchemaType>{
    public SchemaType put(Class clazz) {
        SchemaType result = schemaTypeOf(clazz);
        super.add(result);
        return result;
    }
    public SchemaType get(Class clazz) {
        SchemaType result = null;
        for ( Object o : this) {
            SchemaType st = (SchemaType)o;
            if (st.getJavaType().isAssignableFrom(clazz)) {
                result = st;
                break;
            }

        }
        return result;
    }
    
    public static SchemaType schemaTypeOf(Class type) {
        SchemaType result;
        if (DocUtils.isValueType(type)) {
            result = new ValueType(type);
        } else if (DocUtils.isListType(type)) {
            result = new ListType(type);
        } else if (DocUtils.isArrayType(type)) {
            result = new ArrayType(type);
        } else if (DocumentReference.class.isAssignableFrom(type)) {
            result = new ReferenceType();
        } else {
            //DocumentSchema embSchema = DocUtils.createSchema(type);
            result = new EmbeddedType(type);
        }
        return result;
    }
    
}
