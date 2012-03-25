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

    public DocumentVisitor(Document rootDoc) {
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
        if (info.getSchemaType() instanceof ArrayType ) {
            st = ((ArrayType)info.getSchemaType()).getSupportedType(result.getClass());
        } else if (info.getSchemaType() instanceof ComponentType ) {
            st = ((ComponentType)info.getSchemaType()).getSupportedType(result.getClass());
        } else {
            String nm = paths[infoList.size()-1];
            Field f = ((EmbeddedType)info.getSchemaType()).getSchema().getField(nm);
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
/*        ArrayType arrayType = (ArrayType)schemaType;
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
        SchemaType st = arrayType.getSupportedType(result.getClass());
        if (DocUtils.isArrayType(result.getClass())) {
            visitArray(st, result);
        } else if (DocUtils.isComponentType(result.getClass())) {
            visitComponent(st, result);            
        } else if (DocUtils.isEmbeddedType(result.getClass())) {
            visitEmbedded(st,result);
        }
     */
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
        
        SchemaType st = ((HasSupportedTypes)schemaType).getSupportedType(result.getClass());
        if (DocUtils.isArrayType(result.getClass())) {
            visitArray(st, result);
        } else if (DocUtils.isComponentType(result.getClass())) {
            visitComponent(st, result);            
        } else if (DocUtils.isEmbeddedType(result.getClass())) {
            visitEmbedded(st,result);
        }
        
    }
    
    public void visitComponent(SchemaType schemaType,Object sourceObject) {
        visit(schemaType, sourceObject);        
        //ComponentType componentType  = (ComponentType) schemaType;      
/*        VisitorInfo info = new VisitorInfo(schemaType, sourceObject);
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
            result = Array.get(sourceObject, index);
        } catch (IndexOutOfBoundsException e) {
            info.setException(new IndexOutOfBoundsException("Path '" + path + "'. index==" + index + ". " + e.getMessage()));
            return;
        }
        info.setResult(result);
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
        
        SchemaType st = ((HasSupportedTypes)schemaType).getSupportedType(result.getClass());
        if (DocUtils.isArrayType(result.getClass())) {
            visitArray(st, result);
        } else if (DocUtils.isComponentType(result.getClass())) {
            visitComponent(st, result);            
        } else if (DocUtils.isEmbeddedType(result.getClass())) {
            visitEmbedded(st,result);
        }
*/
        
    }
    public void visitComponentOld(SchemaType schemaType,Object sourceObject) {
        ComponentType componentType  = (ComponentType) schemaType;      
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
        }

        Object result;
        try {
            result = Array.get(sourceObject, index);
        } catch (IndexOutOfBoundsException e) {
            info.setException(new IndexOutOfBoundsException("Path '" + path + "'. index==" + index + ". " + e.getMessage()));
            return;
        }
        info.setResult(result);
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
        
        SchemaType st = componentType.getSupportedType(result.getClass());
        if (DocUtils.isArrayType(result.getClass())) {
            visitArray(st, result);
        } else if (DocUtils.isComponentType(result.getClass())) {
            visitComponent(st, result);            
        } else if (DocUtils.isEmbeddedType(result.getClass())) {
            visitEmbedded(st,result);
        }

        
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
