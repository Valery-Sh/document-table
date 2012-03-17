/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

/**
 *
 * @author Valery
 */
public class ObjectDocument<T> extends AbstractDocument{
    
    protected T javaBean;
    protected DocumentSchema localSchema;
    
    public ObjectDocument(T javaBean) {
        assert(javaBean != null);
        this.javaBean = javaBean;
    }
    @Override
    public DocumentSchema getSchema() {
        DocumentSchema ds;

        if ( this.documentGroup != null ) {
            ds = this.documentGroup.getSchema(this);
        } else if (localSchema != null ) {
            ds = localSchema;
        } else {
            localSchema = DocUtils.createSchema(javaBean.getClass()); 
            ds = localSchema;
        }
        
        return ds;
    }

    public T getJavaBean() {
        return javaBean;
    }
    
    
}
