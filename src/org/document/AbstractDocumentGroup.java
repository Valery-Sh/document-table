/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

/**
 *
 * @author Valery
 */
public abstract class AbstractDocumentGroup implements DocumentGroup {
    
    @Override
    public Document create() {
        MapDocument d = new MapDocument();
        d.documentGroup = this;
        return d;
    }
    @Override
    public Document create(DocumentSchema schema) {
        MapDocument d = (MapDocument)create();
        if ( ! schemaExists(schema)) {
            this.addSchema(schema);
        }
        return d;
    }    
    @Override
    public ObjectDocument create(Object javaBean) {
        ObjectDocument d = new ObjectDocument(javaBean);
        if ( ! schemaExists(javaBean) ) {
            DocumentSchema schema = this.createSchema(javaBean.getClass());
            this.addSchema(javaBean,schema);
        }
        
        return d;
    }
    
    protected void setGroup(Document doc) {
        if ( ! (doc instanceof AbstractDocument)) {
            return;
        }
        ((AbstractDocument)doc).documentGroup = this;
    }
    protected abstract boolean schemaExists(DocumentSchema schema);
    protected abstract boolean schemaExists(Object obj);
    protected abstract void addSchema(Object javaBean,DocumentSchema schema);
    
    //protected abstract void addSchema(DocumentSchema schema);
    
}
