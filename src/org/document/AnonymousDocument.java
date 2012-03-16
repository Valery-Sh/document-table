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
public interface AnonymousDocument extends Serializable {
    
    Object get(Object key);
    void put(Object key,Object value);
    DocumentSchema getSchema();
    void setSchema(DocumentSchema schema);
    
}
