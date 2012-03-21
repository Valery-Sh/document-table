/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document.impl;

import java.util.Map;
import org.document.*;

/**
 *
 * @author Valery
 */
public class DefaultGroup extends AbstractDocumentGroup {

    protected Map<Object, DocumentSchema> schemas;

    @Override
    public void addSchema(DocumentSchema schema) {
        if (schema == null) {
            return;
        }
        if ( schemaExists(schema)) {
            return;
        }
        if (schema.getMappingType() != null) {
            schemas.put(schema.getMappingType().getClass(), schema);
            return;
        }
        schemas.put(schema, schema);

    }

    @Override
    public DocumentSchema createSchema(Class clazz) {
        return DocUtils.createSchema(clazz);
    }

    @Override
    public DocumentSchema createSchema(Map map) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean schemaExists(DocumentSchema schema) {
        if (schema.getMappingType() != null && schemaExists(schema.getMappingType())) {
                return true;
        }
        for (Map.Entry<Object, DocumentSchema> en : schemas.entrySet()) {
            if (en.getValue() == schema) {
                return true;
            }
        }
        return false;
        

    }

    @Override
    protected boolean schemaExists(Object obj) {
        if ( schemas.containsKey(obj.getClass())) {
            return true;
        }
        return false;
    }

    @Override
    protected void addSchema(Object javaBean, DocumentSchema schema) {
        schemas.put(javaBean.getClass(), schema);
    }

    @Override
    public DocumentSchema getSchema(Document doc) {
        DocumentSchema schema = null;
        if ( doc instanceof ObjectDocument) {
            schemas.get( ((ObjectDocument)doc).getDataObject().getClass() );
        }
        //TODO если не ObjectDocument
        return schema;
    }
}