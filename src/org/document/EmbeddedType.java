/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.List;

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

}
