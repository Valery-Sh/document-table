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
    protected Object getFromArray(Object obj,String[] paths, int idx,DocumentSchema schema) {
        return null;
    }
    protected Object getFromEmbedded(Object obj,String[] paths, int idx,DocumentSchema sc) {
        Field f = sc.getField(paths[idx]);
        String path = "";
        for ( int i=0; i <= idx; i++) {
            path += "/" + paths[i];
        }
        
        if (f == null) {
            throw new NullPointerException("A schema doesn't contain a field for key = " + path );
        }
        String nm = paths[idx];
        Object result = DocUtils.getValue(nm, obj);
        
        if ( idx == paths.length - 1) {
            return result;
        }
        
        if ( result == null) {
            throw new NullPointerException("Null value for key path '" + path + "'");            
        }

        if ( DocUtils.isValueType(result.getClass())) {
            throw new IllegalArgumentException("Path '" + path + "': requirs ValueType");
        }
        if ( DocUtils.isArrayType(result.getClass())) {
            result = getFromArray(result,paths,idx+1,sc);
        } else {
            DocumentSchema sc1 = ((EmbeddedType)getSupportedType(f)).getSchema();
            result = getFromEmbedded(result,paths,idx+1,sc1);
        }   
        return result;
        
    }
    protected SchemaType getSupportedType(Field f) {
        return f.getSupportedTypes().get(0);
    }
    @Override
    public Object get(Object key) {
        if (key == null || (key.toString().trim().isEmpty())) {
            return null;
        }
        String[] paths = split(key.toString(), '/');
        return getFromEmbedded(getDataObject(), paths, 0, getSchema());
    }

    protected String[] split(String key, char dlm) {
        String[] result = key.split(String.valueOf(dlm));
        for (int i = 0; i < result.length; i++) {
            result[i] = result[i].trim();
        }
        return result;
    }

    @Override
    public void put(Object key, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

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
