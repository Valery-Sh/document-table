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
public abstract class AbstractDocument<K,V> implements AnonymousDocument<K,V> {
    private Map<K,V> data = new HashMap<K,V>();
    private DocumentSchema schema;
    
    @Override
    public V get(K key) {
        return data.get(key);
    }

    @Override
    public void put(K key, V value) {
        data.put(key, value);
    }

    @Override
    public DocumentSchema getSchema() {
        return schema;
    }

    @Override
    public void setSchema(DocumentSchema schema) {
        this.schema = schema;
    }
    
}
