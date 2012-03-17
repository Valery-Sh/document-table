/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

/**
 *
 * @author Valery
 */
public interface DocumentProvider {
    MapDocument create(); //Schemaless
    MapDocument create(DocumentSchema schema);
    ObjectDocument create(Object javaBean); //Schemaless
    //ObjectDocument create(Object javaBean); //Schemaless
    
}
