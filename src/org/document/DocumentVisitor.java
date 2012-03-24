/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valery
 */
public class DocumentVisitor {

    protected Document rootDoc;
    protected List<VisitorInfo> infoList;
    protected String key;
    protected String[] paths;

    public DocumentVisitor(String key) {
        this.key = key;
        //paths = split(key,'/');
        infoList = new ArrayList<VisitorInfo>();

    }

    public DocumentVisitor(String ... keys) {
        this.key = "";
        for ( int i=0; i < keys.length;i++) {
            key += keys[i] + "/";
        }
        this.paths = keys;
        //paths = split(key,'/');
        infoList = new ArrayList<VisitorInfo>();

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

    public void visitDocument(Document doc) {
        this.rootDoc = doc;
        paths = split(key, '/');
        //VisitorInfo info = new VisitorInfo(doc);
        //infoList.add(info);
        Field f = doc.getSchema().getField(paths[0]);
        if (f.isTail()) {
            //return getFromEmbedded(tail, paths, 0, getSchema());
            visitEmbedded(new EmbeddedType(doc.getSchema()), ((ObjectDocument) doc).tail);
        } else {
            visitEmbedded(new EmbeddedType(doc.getSchema()), ((ObjectDocument) doc).getDataObject());
        }
        //return getFromEmbedded(doc.getDataObject(), paths, 0, getSchema());



    }

    public void visitEmbedded(EmbeddedType embeddedType, Object sourceObject) {

        VisitorInfo info = new VisitorInfo(embeddedType, sourceObject);
        infoList.add(info);
        DocumentSchema ds = embeddedType.getSchema();
        int pathIndex = infoList.size() - 1;
        Field f = ds.getField(paths[pathIndex]);
        String path = "";
        for (int i = 0; i <= pathIndex; i++) {
            path += "/" + paths[i];
        }

        if (f == null) {
            throw new NullPointerException("A schema doesn't contain a field for key = " + path);
        }
        String nm = paths[pathIndex];
        Object result = DocUtils.getValue(nm, sourceObject);
        info.setResult(result);
        
        if (pathIndex == paths.length - 1) {
            return;
        }

        if (result == null) {
            info.setException(new NullPointerException("Null value for key path '" + path + "'"));
            return;
            //throw new NullPointerException("Null value for key path '" + path + "'");
        }

        if (DocUtils.isValueType(result.getClass())) {
            info.setException(new IllegalArgumentException("Path '" + path + "': requires ValueType"));            
            return;
            //throw new IllegalArgumentException("Path '" + path + "': requires ValueType");
        }
        if (DocUtils.isArrayType(result.getClass())) {
            SchemaType st = ((ObjectDocument)rootDoc).getSupportedType(f);
            visitArray((ArrayType)st, result);
            //result = getFromArray((ArrayType)st, result, paths, idx + 1, sc);
        } else if (DocUtils.isComponentType(result.getClass())) {
            SchemaType st = ((ObjectDocument)rootDoc).getSupportedType(f);            
            visitComponent((ComponentType)st, result);            
            //result = getFromComponentType((ComponentType) getSupportedType(f), result, paths, idx + 1, sc);
        } else {
            SchemaType st = ((ObjectDocument)rootDoc).getSupportedType(f);
            visitEmbedded((EmbeddedType)st,result);
            //DocumentSchema sc1 = ((EmbeddedType) getSupportedType(f)).getSchema();
            //result = getFromEmbedded(result, paths, idx + 1, sc1);
        }
        //return result;


    }
    public void visitArray(ArrayType arrayType, Object sourceObject) {
        VisitorInfo info = new VisitorInfo(arrayType, sourceObject);
        infoList.add(info);
        int idx = infoList.size() - 1;
                
        int index;
        String path = "";
        
        for (int i = 0; i <= idx; i++) {
            path += "/" + paths[i];
        }

        try {
            index = Integer.parseInt(paths[idx]);
        } catch (NumberFormatException e) {
            info.setException(new NumberFormatException("Path '" + path + "' for ArrayType index requies integer type. " + e.getMessage()));
            return;
            //throw new NumberFormatException("Path '" + path + "' for ArrayType index requies integer type. " + e.getMessage());
        }
        List list = (List) sourceObject;
        Object result;
        try {
            result = list.get(index);
        } catch (IndexOutOfBoundsException e) {
            info.setException(new IndexOutOfBoundsException("Path '" + path + "'. index==" + index + " is greater than  list.size() == " + list.size() + ". " + e.getMessage()));
            return;
            //throw new IndexOutOfBoundsException("Path '" + path + "'. index==" + index + " is greater than  list.size() == " + list.size() + ". " + e.getMessage());
        }
        info.setResult(result);
        if (idx == paths.length - 1) {
            return;
        }

        if (result == null) {
            info.setException(new NullPointerException("Null value for key path '" + path + "'"));
            return;
            //throw new NullPointerException("Null value for key path '" + path + "'");
        }

        if (DocUtils.isValueType(result.getClass())) {
            info.setException(new IllegalArgumentException("Path '" + path + "': requires ValueType"));
            return;
            //throw new IllegalArgumentException("Path '" + path + "': requires ValueType");
        }

        if (DocUtils.isArrayType(result.getClass())) {
            ArrayType t = (ArrayType) arrayType.getSupportedType(result.getClass());
            visitArray(t, result);
            //result = getFromArray(t, result, paths, idx + 1, sc);

        } else if (DocUtils.isComponentType(result.getClass())) {
            ComponentType t = (ComponentType) arrayType.getSupportedType(result.getClass());
            visitComponent(t, result);            
            //result = getFromComponentType(t, result, paths, idx + 1, sc);

        } else if (DocUtils.isEmbeddedType(result.getClass())) {
            EmbeddedType t = (EmbeddedType) arrayType.getSupportedType(result.getClass());
            visitEmbedded(t,result);
//            DocumentSchema sc1 = t.getSchema();
//            result = getFromEmbedded(result, paths, idx + 1, sc1);
        }
        
        
    }
    public void visitComponent(ComponentType componentType,Object sourceObject) {
        VisitorInfo info = new VisitorInfo(componentType, sourceObject);
        infoList.add(info);
        int idx = infoList.size() - 1;
                
        int index;
        String path = "";
        
        for (int i = 0; i <= idx; i++) {
            path += "/" + paths[i];
        }
        try {
            index = Integer.parseInt(paths[idx]);
        } catch (NumberFormatException e) {
            info.setException(new NumberFormatException("Path '" + path + "' for ArrayType index requies integer type. " + e.getMessage()));
            return;
            //throw new NumberFormatException("Path '" + path + "' for ArrayType index requies integer type. " + e.getMessage());
        }

        Object result;
        try {
            result = Array.get(sourceObject, index);
        } catch (IndexOutOfBoundsException e) {
            info.setException(new IndexOutOfBoundsException("Path '" + path + "'. index==" + index + ". " + e.getMessage()));
            return;
            //throw new IndexOutOfBoundsException("Path '" + path + "'. index==" + index + ". " + e.getMessage());
        }
        info.setResult(result);
        if (idx == paths.length - 1) {
            return;
        }
        if (result == null) {
            info.setException(new NullPointerException("Null value for key path '" + path + "'"));
            return;
            //throw new NullPointerException("Null value for key path '" + path + "'");
        }

        if (DocUtils.isValueType(result.getClass())) {
            info.setException(new IllegalArgumentException("Path '" + path + "': requires ValueType"));
            return;
            //throw new IllegalArgumentException("Path '" + path + "': requires ValueType");
        }

        if (DocUtils.isArrayType(result.getClass())) {
            ArrayType t = (ArrayType) componentType.getSupportedType(result.getClass());
            visitArray(t, result);
            //result = getFromArray(t, result, paths, idx + 1, sc);

        } else if (DocUtils.isComponentType(result.getClass())) {
            ComponentType t = (ComponentType) componentType.getSupportedType(result.getClass());
            visitComponent(t, result);            
            //result = getFromComponentType(t, result, paths, idx + 1, sc);

        } else if (DocUtils.isEmbeddedType(result.getClass())) {
            EmbeddedType t = (EmbeddedType) componentType.getSupportedType(result.getClass());
            visitEmbedded(t,result);
//            DocumentSchema sc1 = t.getSchema();
//            result = getFromEmbedded(result, paths, idx + 1, sc1);
        }

        
    }
    
    public VisitorInfo getInfo(int idx) {
        return this.infoList.get(idx);
    }
    
    public Object getResult() {
        return this.infoList.get(infoList.size()-1).getResult();
    }
    public Exception getException() {
        return this.infoList.get(infoList.size()-1).getException();
    }
    
    public static class VisitorInfo {

        private Document rootDocument;
//        EmbeddedType embeddedType;        
        private SchemaType schemaType;
        private Exception exception;
        private Object result;
        private Object sourceObject;

        public VisitorInfo(Document doc) {
            this.rootDocument = doc;
        }

        public VisitorInfo(SchemaType embeddedType, Object sourceObject) {
            this.schemaType = embeddedType;
            this.sourceObject = sourceObject;
        }
/*        public VisitorInfo(ArrayType arrayType, Object sourceObject) {
            this.schemaType = arrayType;
            this.sourceObject = sourceObject;
        }
*/
        public Exception getException() {
            return exception;
        }

        public void setException(Exception exception) {
            this.exception = exception;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public Document getRootDocument() {
            return rootDocument;
        }

        public void setRootDocument(Document rootDocument) {
            this.rootDocument = rootDocument;
        }

        public SchemaType getSchemaType() {
            return schemaType;
        }

        public void setSchemaType(SchemaType schemaType) {
            this.schemaType = schemaType;
        }

        public Object getSourceObject() {
            return sourceObject;
        }

        public void setSourceObject(Object sourceObject) {
            this.sourceObject = sourceObject;
        }
        
        
    }
}
