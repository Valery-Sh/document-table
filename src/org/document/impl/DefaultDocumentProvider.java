/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document.impl;

import java.util.Map;
import org.document.MapDocument;
import org.document.DocumentProvider;
import org.document.DocumentSchema;
import org.document.ObjectDocument;

/**
 *
 * @author Valery
 */
public class DefaultDocumentProvider implements DocumentProvider{

    @Override
    public MapDocument create() {
        
        return new MapDocument();
    }

    @Override
    public MapDocument create(DocumentSchema schema) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ObjectDocument create(Object javaBean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
