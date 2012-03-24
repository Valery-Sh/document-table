/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

/**
 *
 * @author Valery
 */
public class EmbeddedType implements SchemaType{
    
    private DocumentSchema schema;

    public EmbeddedType(DocumentSchema schema) {
        this.schema = schema;
    }

    public DocumentSchema getSchema() {
        return schema;
    }
    
    
    @Override
    public Class getJavaType() {
        return schema.getMappingType() != null ? schema.getMappingType() : null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmbeddedType other = (EmbeddedType) obj;
        if (this.schema != other.schema && (this.schema == null || !this.schema.equals(other.schema))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.schema != null ? this.schema.hashCode() : 0);
        return hash;
    }

}
