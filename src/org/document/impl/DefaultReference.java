/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document.impl;

import org.document.DocumentReference;

/**
 *
 * @author Valery
 */
public class DefaultReference implements DocumentReference {
    private Object reference;

    public DefaultReference(Object reference) {
        this.reference = reference;
    }
    
    @Override
    public Object getReference() {
        return reference;
    }
    
}
