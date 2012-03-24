/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
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

    protected Object getFromArray(ArrayType atype, Object obj, String[] paths, int idx, DocumentSchema sc) {

        int index = 0;
        String path = "";
        for (int i = 0; i <= idx; i++) {
            path += "/" + paths[i];
        }

        try {
            index = Integer.parseInt(paths[idx]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Path '" + path + "' for ArrayType index requies integer type. " + e.getMessage());
        }
        List list = (List) obj;
        Object result;
        try {
            result = list.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Path '" + path + "'. index==" + index + " is greater than  list.size() == " + list.size() + ". " + e.getMessage());
        }
        if (idx == paths.length - 1) {
            return result;
        }

        checkValue(result, path); // might throw exception 


        if (DocUtils.isArrayType(result.getClass())) {
            ArrayType t = (ArrayType) atype.getSupportedType(result.getClass());
            result = getFromArray(t, result, paths, idx + 1, sc);

        } else if (DocUtils.isComponentType(result.getClass())) {
            ComponentType t = (ComponentType) atype.getSupportedType(result.getClass());
            result = getFromComponentType(t, result, paths, idx + 1, sc);

        } else if (DocUtils.isEmbeddedType(result.getClass())) {
            EmbeddedType t = (EmbeddedType) atype.getSupportedType(result.getClass());
            DocumentSchema sc1 = t.getSchema();
            result = getFromEmbedded(result, paths, idx + 1, sc1);
        }

        return result;
    }

    protected Object getFromComponentType(ComponentType atype, Object obj, String[] paths, int idx, DocumentSchema sc) {
        int index = 0;
        String path = "";
        for (int i = 0; i <= idx; i++) {
            path += "/" + paths[i];
        }

        try {
            index = Integer.parseInt(paths[idx]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Path '" + path + "' for ArrayType index requies integer type. " + e.getMessage());
        }

        Object result;
        try {
            result = Array.get(obj, index);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Path '" + path + "'. index==" + index + ". " + e.getMessage());
        }
        if (idx == paths.length - 1) {
            return result;
        }

        checkValue(result, path); // might throw exception 


        if (DocUtils.isArrayType(result.getClass())) {
            ArrayType t = (ArrayType) atype.getSupportedType(result.getClass());
            result = getFromArray(t, result, paths, idx + 1, sc);

        }
        if (DocUtils.isComponentType(result.getClass())) {
            ComponentType t = (ComponentType) atype.getSupportedType(result.getClass());
            result = getFromComponentType(t, result, paths, idx + 1, sc);

        } else if (DocUtils.isEmbeddedType(result.getClass())) {
            EmbeddedType t = (EmbeddedType) atype.getSupportedType(result.getClass());
            DocumentSchema sc1 = t.getSchema();
            result = getFromEmbedded(result, paths, idx + 1, sc1);
        }

        return result;

    }

    
    protected Object getFromEmbedded(Object obj, String[] paths, int idx, DocumentSchema sc) {

        Field f = sc.getField(paths[idx]);
        String path = "";
        for (int i = 0; i <= idx; i++) {
            path += "/" + paths[i];
        }

        if (f == null) {
            throw new NullPointerException("A schema doesn't contain a field for key = " + path);
        }
        String nm = paths[idx];
        Object result = DocUtils.getValue(nm, obj);

        if (idx == paths.length - 1) {
            return result;
        }

        if (result == null) {
            throw new NullPointerException("Null value for key path '" + path + "'");
        }

        if (DocUtils.isValueType(result.getClass())) {
            throw new IllegalArgumentException("Path '" + path + "': requires ValueType");
        }
        if (DocUtils.isArrayType(result.getClass())) {
            result = getFromArray((ArrayType) getSupportedType(f), result, paths, idx + 1, sc);
        } else if (DocUtils.isComponentType(result.getClass())) {
            result = getFromComponentType((ComponentType) getSupportedType(f), result, paths, idx + 1, sc);
        } else {
            DocumentSchema sc1 = ((EmbeddedType) getSupportedType(f)).getSchema();
            result = getFromEmbedded(result, paths, idx + 1, sc1);
        }
        return result;

    }

    protected void checkValue(Object obj, String path) {

        if (obj == null) {
            throw new NullPointerException("Null value for key path '" + path + "'");
        }

        if (DocUtils.isValueType(obj.getClass())) {
            throw new IllegalArgumentException("Path '" + path + "': requires ValueType");
        }
    }

    protected SchemaType getSupportedType(Field f) {
        return f.getSupportedTypes().get(0);
    }

    //@Override
    public Object getOLD(Object key) {
        if (key == null || (key.toString().trim().isEmpty())) {
            return null;
        }
        String[] paths = split(key.toString(), '/');
        // Me must keep in mind that a field may be in 'tail'
        Field f = getSchema().getField(paths[0]);
        if (f.isTail()) {
            return getFromEmbedded(tail, paths, 0, getSchema());
        }

        return getFromEmbedded(getDataObject(), paths, 0, getSchema());
    }

    @Override
    public Object get(Object key) {
        if (key == null || (key.toString().trim().isEmpty())) {
            return null;
        }
        String[] paths = split(key.toString(), '/');
        // Me must keep in mind that a field may be in 'tail'
        Field f = getSchema().getField(paths[0]);
        DocumentVisitor visitor = new DocumentVisitor(key.toString());
        visitor.visitDocument(this);
        if ( visitor.getException() != null ) {
            RuntimeException re = (RuntimeException)visitor.getException();
            throw re;
        }
        return visitor.getResult();
    }
    
    
    protected String[] split(String key, char dlm) {
        String k = key.trim();
        if ((!k.isEmpty()) && key.charAt(0) == dlm) {
            k = key.substring(1);
        }
        String[] result = k.split(String.valueOf(dlm));
        for (int i = 0; i < result.length; i++) {
            result[i] = result[i].trim();
        }
        return result;
    }

    @Override
    public void put(Object key, Object value) {
        if (key == null || (key.toString().trim().isEmpty())) {
            throw new NullPointerException("ObjectDocument.put(null, ..)");
        }
        String[] paths = split(key.toString(), '/');
        // Me must keep in mind that a field may be in 'tail'
        Field f = getSchema().getField(paths[0]);
        if (f.isTail()) {
            putToEmbedded(tail, paths, 0, getSchema());
        }
        putToEmbedded(getDataObject(), paths, 0, getSchema());

    }

    protected void putToEmbedded(Object obj, String[] paths, int idx, DocumentSchema sc) {
        Field f = sc.getField(paths[idx]);
        String path = "";
        for (int i = 0; i <= idx; i++) {
            path += "/" + paths[i];
        }

        if (f == null) {
            throw new NullPointerException("A schema doesn't contain a field for key = " + path);
        }
        String nm = paths[idx];
        /*
         * if ( DocUtils.isValueType(result.getClass())) { throw new
         * IllegalArgumentException("Path '" + path + "': requires ValueType");
         * } if ( DocUtils.isArrayType(result.getClass())) { result =
         * getFromArray((ArrayType)getSupportedType(f),result,paths,idx+1,sc); }
         * else if ( DocUtils.isComponentType(result.getClass())) { result =
         * getFromComponentType((ComponentType)getSupportedType(f),result,paths,idx+1,sc);
         * } else { DocumentSchema sc1 =
         * ((EmbeddedType)getSupportedType(f)).getSchema(); result =
         * getFromEmbedded(result,paths,idx+1,sc1); } return result;
         */
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
