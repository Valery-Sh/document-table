/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Valery
 */
public abstract class AbstractDocument<D> implements AnonymousDocument {
    protected D data;
    protected Map dataMap = new HashMap();
    protected DocumentSchema schema;
    
    @Override
    public Object get(Object key) {
        return dataMap.get(key);
    }

    @Override
    public void put(Object key, Object value) {
        dataMap.put(key, value);
    }

    @Override
    public DocumentSchema getSchema() {
        return schema;
    }

    
    protected void setSchema(DocumentSchema schema) {
        this.schema = schema;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
    public D newDataInstance() {
        return (D)DocUtils.newInstance((D)getData());
    }    
    public D cloneData() {
        return DocUtils.cloneValue((D)getData());
    }
    
}
