/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

/**
 *
 * @author Valery
 */
public interface SchemaType {
    Class getJavaType();
    //SchemaType getSupportedByClass(Class clazz);    
    SchemaTypeSet getSupportedSchemaTypes();    
    
    //void addSupported(SchemaType type);
    //List<SchemaType> getSupportedTypes();
/*    void addSupported(ValueType type);
    void addSupported(ArrayType arrayType);
    void addSupported(DocumentSchema embedded);
    void addSupported(ReferenceType ref);
*/    
}
