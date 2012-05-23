package org.document;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author V. Shyshkin
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
/*
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

//    @Override
    protected T newDataInstance() {
        return null;
    }

//    @Override
    protected T cloneData() {
        return null;
    }


}
