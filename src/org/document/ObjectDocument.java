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
public class ObjectDocument<T> extends AbstractDocument {

    protected T dataObject;
    protected Map tail;
    protected DocumentSchema localSchema;

    public ObjectDocument(T dataObject) {
        assert (dataObject != null);
        this.dataObject = dataObject;
        tail = new HashMap();
    }

    @Override
    public Object get(Object key) {
        if (key == null || (key.toString().trim().isEmpty())) {
            return null;
        }
        String[] paths = DocUtils.split(key.toString(), '/');
        // Me must keep in mind that a field may be in 'tail'
        //Field f = getSchema().getField(paths[0]);
        GetVisitor visitor = new GetVisitor(this);
        visitor.visitDocument(key.toString());
        if ( visitor.getException() != null ) {
            RuntimeException re = (RuntimeException)visitor.getException();
            throw re;
        }
        return visitor.getResult();
    }
    @Override
    public void put(Object key, Object value) {
        if (key == null || (key.toString().trim().isEmpty())) {
            return;
        }
        PutVisitor visitor = new PutVisitor(this);
        visitor.visitDocument(key.toString(), value);
        
        if ( visitor.getException() != null ) {
            throw (RuntimeException)visitor.getException();
        }
    }
    //@Override
/*    public void putOld(Object key, Object value) {
        if (key == null || (key.toString().trim().isEmpty())) {
            return;
        }
        String[] p = DocUtils.split(key.toString(), '/');
        String[] paths = new String[p.length];
        System.arraycopy(p, 0, paths, 0, p.length);
        // Me must keep in mind that a field may be in 'tail'
        Field f = getSchema().getField(paths[0]);
        String nm = p[0];
        if ( p.length > 1 ) {
            paths = new String[p.length-1];
            System.arraycopy(p, 0, paths, 0, p.length-1);
            
        }
        
        GetVisitor visitor = new GetVisitor(this);
        if ( p.length > 1 ) {
            visitor.visitDocument(paths);            
            Object sourceObject = visitor.getResult();
            if ( sourceObject == null ) {
                throw new NullPointerException("Null value for key path '" + key + "'");
            }
            if ( visitor.getException() != null ) {
                throw (RuntimeException)visitor.getException();
            }
            SchemaType st = visitor.getInfo().getResultSchemaType();
            visitor.visitPut(st, sourceObject, p[p.length-1], value);
        } else {
            Object sourceObject = f.isTail() ? tail: getDataObject();
            SchemaType st = new EmbeddedType(getSchema());
            visitor.visitPut(st, sourceObject, p[0], value);
        }
        
        if ( visitor.getException() != null ) {
            RuntimeException re = (RuntimeException)visitor.getException();
            throw re;
        }
        
        //return visitor.getResult();

    }
  */  
    @Override
    public DocumentSchema getSchema() {
        DocumentSchema ds;

        if (this.documentGroup != null) {
            ds = this.documentGroup.getSchema(this);
        } else if (localSchema != null) {
            ds = localSchema;
        } else {
            localSchema = DocUtils.createSchema(dataObject.getClass());
            ds = localSchema;
        }

        return ds;
    }

    public T getDataObject() {
        return dataObject;
    }

    @Override
    protected T newDataInstance() {
        return null;
    }

    @Override
    protected T cloneData() {
        return null;
    }


}
