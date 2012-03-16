/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

/**
 *
 * @author Valery
 */
public class Document<K,V> extends AbstractDocument<K,V> {
    
    private Object primaryId;
    
    public Object getPrimaryId() {
        return primaryId;
    }
}
