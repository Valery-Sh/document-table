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
public class GetVisitor {

    protected Document rootDoc;
    protected List<VisitorInfo> infoList;
    protected String key;
    protected String[] paths;

    public GetVisitor(Document rootDoc) {
        this.rootDoc = rootDoc;
        infoList = new ArrayList<VisitorInfo>();
    }

    public void visitDocument(String ... keys) {
        this.key = "";
        for ( int i=0; i < keys.length;i++) {
            key += keys[i] + "/";
        }
        visitDocument(key);
    }
    public void visitDocument(String key) {
        paths = DocUtils.split(key, '/');
        infoList = new ArrayList<VisitorInfo>();
        Field f = rootDoc.getSchema().getField(paths[0]);
        if (f.isTail()) {
            visitEmbedded(new EmbeddedType(rootDoc.getSchema()), ((ObjectDocument) rootDoc).tail);
        } else {
            visitEmbedded(new EmbeddedType(rootDoc.getSchema()), ((ObjectDocument) rootDoc).getDataObject());
        }
    }

    public void setPaths(String ... path) {
        this.paths = path;
    }

    public void continueVisit(String ... addKeys) {
        this.key = "";
        String[] newkeys = new String[paths.length + addKeys.length];
        System.arraycopy(paths, 0, newkeys, 0, paths.length);
        System.arraycopy(addKeys, 0, newkeys, paths.length, addKeys.length);
        
        this.paths = newkeys;
        for ( int i=0; i < paths.length;i++) {
            key += paths[i] + "/";
        }
        VisitorInfo info = this.getInfo();
        Object result = info.getResult();
        int idx = infoList.size()-1;
        String path = "";
        
        for (int i = 0; i <= idx; i++) {
            path += "/" + paths[i];
        }
        
        if (result == null) {
            info.setException(new NullPointerException("Null value for key path '" + path + "'"));
            return;
        }

        if (DocUtils.isValueType(result.getClass())) {
            info.setException(new IllegalArgumentException("Path '" + path + "': requires ValueType"));            
            return;
        }
        SchemaType st;
        if (info.getSourceSchemaType() instanceof ArrayType ) {
            st = ((ArrayType)info.getSourceSchemaType()).getSupportedType(result.getClass());
        } else if (info.getSourceSchemaType() instanceof ComponentType ) {
            st = ((ComponentType)info.getSourceSchemaType()).getSupportedType(result.getClass());
        } else {
            String nm = paths[infoList.size()-1];
            Field f = ((EmbeddedType)info.getSourceSchemaType()).getSchema().getField(nm);
            st = f.getSupportedTypes().get(0);
        }
        
        if (DocUtils.isArrayType(result.getClass())) {
            visitArray((ArrayType)st, result);
        } else if (DocUtils.isComponentType(result.getClass())) {
            visitComponent((ComponentType)st, result);            
        } else {
            visitEmbedded((EmbeddedType)st,result);
        }
        
    }
    


    public void visitEmbedded(SchemaType schemaType, Object sourceObject) {
        EmbeddedType embeddedType = (EmbeddedType) schemaType;        
        VisitorInfo info = new VisitorInfo(schemaType, sourceObject);
        infoList.add(info);
        DocumentSchema ds = embeddedType.getSchema();
        int pathIndex = infoList.size() - 1;
        Field f = ds.getField(paths[pathIndex]);
        SchemaType st = f.getDefaultType();        
        info.setResultSchemaType(st);        
        
        String path = "";
        for (int i = 0; i <= pathIndex; i++) {
            path += "/" + paths[i];
        }

        if (f == null) {
            info.setException(new NullPointerException("A schema doesn't contain a field for key = " + path));            
            return;

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
        }

        if (DocUtils.isValueType(result.getClass())) {
            info.setException(new IllegalArgumentException("Path '" + path + "': requires ValueType"));            
            return;
        }
        
        //SchemaType st = f.getSupportedTypes().get(0);
        
        if (DocUtils.isArrayType(result.getClass())) {
            visitArray(st, result);
        } else if (DocUtils.isComponentType(result.getClass())) {
            visitComponent(st, result);            
        } else {
            visitEmbedded(st,result);
        }
    }
    
    public void visitArray(SchemaType schemaType, Object sourceObject) {
        visit(schemaType, sourceObject);
    }
    protected void visit(SchemaType schemaType,Object sourceObject) {
        VisitorInfo info = new VisitorInfo(schemaType, sourceObject);
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
        }

        Object result;
        try {
            if ( sourceObject.getClass().isArray()) {
                result = Array.get(sourceObject, index);
            } else {
                result = ((List)sourceObject).get(index);
            }
        } catch (IndexOutOfBoundsException e) {
            info.setException(new IndexOutOfBoundsException("Path '" + path + "'. index==" + index + ". " + e.getMessage()));
            return;
        }
        info.setResult(result);
        SchemaType st = null;
        if ( result != null ) {
            st = ((HasSupportedTypes)schemaType).getSupportedType(result.getClass());
            info.setResultSchemaType(st);
        }
        
        if (idx == paths.length - 1) {
            return;
        }
        if (result == null) {
            info.setException(new NullPointerException("Null value for key path '" + path + "'"));
            return;
        }

        if (DocUtils.isValueType(result.getClass())) {
            info.setException(new IllegalArgumentException("Path '" + path + "': requires ValueType"));
            return;
        }
        
        if (DocUtils.isArrayType(result.getClass())) {
            visitArray(st, result);
        } else if (DocUtils.isComponentType(result.getClass())) {
            visitComponent(st, result);            
        } else if (DocUtils.isEmbeddedType(result.getClass())) {
            visitEmbedded(st,result);
        }
        
    }
    
    public void visitPut(SchemaType schemaType,Object sourceObject,String key,Object newValue) {
        this.paths = new String[] {key};
        this.infoList.clear();
        if ( schemaType instanceof ArrayType || schemaType instanceof ComponentType ) {
            this.visitPutArray(schemaType, sourceObject, newValue);
        } else if (schemaType instanceof EmbeddedType ) {
            this.visitPutEmbedded(schemaType, sourceObject, newValue);
        }
    }
    
    protected void visitPutArray(SchemaType schemaType,Object sourceObject,Object newValue) {
        VisitorInfo info = new VisitorInfo(schemaType, sourceObject);
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
        }

        try {
            if ( sourceObject.getClass().isArray()) {
                Array.set(sourceObject, index, newValue);
                info.setResult(Array.get(sourceObject, index));
            } else {
                List l = (List)sourceObject; 
                if ( index >= l.size()) {
                    l.add(newValue);
                    index = l.size()-1;
                } else {
                    l.set(index,newValue);
                }
                info.setResult(((List)sourceObject).get(index));
            }
        } catch (NullPointerException e) {
            info.setException(new NullPointerException("Path '" + path + "'. index==" + index + ". " + e.getMessage()));
        } catch (ArrayIndexOutOfBoundsException e) {
            info.setException(new ArrayIndexOutOfBoundsException("Path '" + path + "'. index==" + index + ". " + e.getMessage()));
        }
        
    }
    public void visitPutEmbedded(SchemaType schemaType, Object sourceObject,Object newValue) {
        EmbeddedType embeddedType = (EmbeddedType) schemaType;        
        VisitorInfo info = new VisitorInfo(schemaType, sourceObject);
        infoList.add(info);
        DocumentSchema ds = embeddedType.getSchema();
        int pathIndex = infoList.size() - 1;
        Field f = ds.getField(paths[pathIndex]);
        SchemaType st = f.getDefaultType();        
        
        String path = "";
        for (int i = 0; i <= pathIndex; i++) {
            path += "/" + paths[i];
        }

        if (f == null) {
            info.setException(new NullPointerException("A schema doesn't contain a field for key = " + path));            
            return;
        }
        String nm = paths[pathIndex];
        Object result = null;
        try {
            result = DocUtils.setValue(nm, sourceObject,newValue);
            info.setResult(result);
        } catch(Exception e) {
            info.setException(e);
        }
    }
    
    public void visitComponent(SchemaType schemaType,Object sourceObject) {
        visit(schemaType, sourceObject);        
    }
    
    public VisitorInfo getInfo(int idx) {
        return this.infoList.get(idx);
    }
    public VisitorInfo getInfo() {
        return this.infoList.get(infoList.size()-1);
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
        private SchemaType sourceSchemaType;
        private SchemaType resultSchemaType;
        
        private Exception exception;
        private Object result;
        private Object sourceObject;

        public VisitorInfo(Document doc) {
            this.rootDocument = doc;
        }

        public VisitorInfo(SchemaType embeddedType, Object sourceObject) {
            this.sourceSchemaType = embeddedType;
            this.sourceObject = sourceObject;
        }
/*        public VisitorInfo(ArrayType arrayType, Object sourceObject) {
            this.sourceSchemaType = arrayType;
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

        public SchemaType getSourceSchemaType() {
            return sourceSchemaType;
        }

        public void setSourceSchemaType(SchemaType schemaType) {
            this.sourceSchemaType = schemaType;
        }

        public Object getSourceObject() {
            return sourceObject;
        }

        public void setSourceObject(Object sourceObject) {
            this.sourceObject = sourceObject;
        }

        public SchemaType getResultSchemaType() {
            return resultSchemaType;
        }

        public void setResultSchemaType(SchemaType resultSchemaType) {
            this.resultSchemaType = resultSchemaType;
        }
        
        
    }
}
