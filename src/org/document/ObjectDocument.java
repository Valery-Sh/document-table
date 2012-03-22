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

    protected Object getEmbedded(String key, Object obj, DocumentSchema schema, String path) {
        return null;
    }
    protected boolean checkPaths(String[] paths) {
        boolean b = true;
        
        return b;
    }
    
    private void checkPaths(String[] paths, int start,DocumentSchema sc) {
        Field f = sc.getField(paths[start]);
        if ( f == null ) {
           throw new IllegalArgumentException("The field with a name '" + paths[start]+"' doesn't exist");
        }
        SchemaType st = getSupportedType(f);
        if ( st instanceof ValueType && start < paths.length - 1  ) {
            throw new IllegalArgumentException("Too long path for a ValueType");
        } else if ( st instanceof ValueType ) {
            return;
        } else if ( st instanceof ArrayType ) {
            if ( start == paths.length - 1) {
                return;
            }
            if ( start < paths.length - 1) {
                throw new IllegalArgumentException("Too long path for aa ArrayType");
            }
            try {
                Integer.parseInt(paths[start]);
            } catch(NumberFormatException e) {
                throw new NumberFormatException("ArrayType: " + e.getMessage());
            }
        }
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
        DocumentSchema sc = getSchema();
        Object value = getDataObject();
        for (int i = 0; i < paths.length; i++) {
            String sub = "";
            for (int n = 0; n <= i; n++) {
                sub += "/" + paths[n];
            }
            //Object value = getEmbedded(paths[i],o,sc,sub);
        }
        int i = 0;
        Field f = getSchema().getField(paths[0]);
        if (f == null) {
            throw new NullPointerException("A schema doesn't contain a field for key = " + key);
        }
        Object result;
        if (f.isTail()) {
            result = tail.get(key);
        } else {
            result = DocUtils.getValue(key.toString(), dataObject);
        }
        if (result == null && i < paths.length - 1) {
            throw new NullPointerException("Null value for key path '" + sub + "'");
        } else if (result == null) {
            return null;
        }
        if (DocUtils.isValueType(result.getClass())) {
            if (i < paths.length - 1) {
                throw new IllegalArgumentException("Path '" + sub + "': required array or embedded but found value type");
            }
        }
        for (int i = 0; i < paths.length; i++) {
        }
        return result;
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
