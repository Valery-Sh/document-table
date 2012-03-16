/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.io.Serializable;

/**
 *
 * @author Valery
 */
public interface AnonymousDocument<K,V> extends Serializable {
    V get(K key);
    void put(K key,V value);
    DocumentSchema getSchema();
    void setSchema(DocumentSchema schema);
    
}
