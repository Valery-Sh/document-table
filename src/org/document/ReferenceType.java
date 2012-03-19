/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

/**
 *
 * @author Valery
 */
public class ReferenceType  implements SchemaType {
    
    private Class javaType;
    
    @Override
    public Class getJavaType() {
        return javaType;
    }

}
