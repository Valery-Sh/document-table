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
    SchemaTypeSet getSupportedSchemaTypes();    
}
