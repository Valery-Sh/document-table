/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.Map;

/**
 *
 * @author Valery
 */
public class ObjectDocument<T> extends AbstractDocument{
    
    protected T dataObject;
    protected DocumentSchema localSchema;
    
    public ObjectDocument(T dataObject) {
        assert(dataObject != null);
        this.dataObject = dataObject;
    }
    
    @Override
    public Object get(Object key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void put(Object key, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public DocumentSchema getSchema() {
        DocumentSchema ds;

        if ( this.documentGroup != null ) {
            ds = this.documentGroup.getSchema(this);
        } else if (localSchema != null ) {
            ds = localSchema;
        } else {
            localSchema = DocUtils.createSchema(dataObject.getClass()); 
            ds = localSchema;
        }
        
        return ds;
    }

    public T getDataObject() {
        return dataObject;
    }

    @Override
    protected T newDataInstance() {
        return null;
    }

    @Override
    protected T cloneData() {
        return null;
    }

    
    
}
