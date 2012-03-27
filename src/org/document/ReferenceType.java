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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReferenceType other = (ReferenceType) obj;
        if (this.javaType != other.javaType && (this.javaType == null || !this.javaType.equals(other.javaType))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.javaType != null ? this.javaType.hashCode() : 0);
        return hash;
    }

    @Override
    public SchemaTypeSet getSupportedSchemaTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
