
package org.document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author V. Shyshkin
 */
public class GetVisitor extends DocumentVisitor{


    public GetVisitor(Document rootDoc) {
        super(rootDoc);
    }

    @Override
    public void visitDocument(String key) {
        paths = DocUtils.split(key, '/');
        infoList = new ArrayList<DocumentVisitor.VisitorInfo>();
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
        if (info.getSourceSchemaType() instanceof ListType ) {
            st = ((ListType)info.getSourceSchemaType()).getSupportedSchemaTypes().get(result.getClass());
        } else if (info.getSourceSchemaType() instanceof ArrayType ) {
            st = ((ArrayType)info.getSourceSchemaType()).getSupportedSchemaTypes().get(result.getClass());
        } else {
            String nm = paths[infoList.size()-1];
            Field f = ((EmbeddedType)info.getSourceSchemaType()).getSchema().getField(nm);
            //st = f.getSupportedTypes().get(0);
            st = f.getSchemaType();
        }
        
        if (DocUtils.isListType(result.getClass())) {
            visitList((ListType)st, result);
        } else if (DocUtils.isArrayType(result.getClass())) {
            visitArray((ArrayType)st, result);            
        } else {
            visitEmbedded((EmbeddedType)st,result);
        }
        
    }
    


    @Override
    public void visitEmbedded(SchemaType schemaType, Object sourceObject) {
        EmbeddedType embeddedType = (EmbeddedType) schemaType;        
        DocumentVisitor.VisitorInfo info = new DocumentVisitor.VisitorInfo(schemaType, sourceObject);
        infoList.add(info);
        DocumentSchema ds = embeddedType.getSchema();
        int pathIndex = infoList.size() - 1;
        Field f = ds.getField(paths[pathIndex]);
        SchemaType st = f.getSchemaType();        
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
        
        if (DocUtils.isListType(result.getClass())) {
            visitList(st, result);
        } else if (DocUtils.isArrayType(result.getClass())) {
            visitArray(st, result);            
        } else {
            visitEmbedded(st,result);
        }
    }
    
    @Override
    public void visitList(SchemaType schemaType, Object sourceObject) {
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
            st = schemaType.getSupportedSchemaTypes().get(result.getClass());
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
        
        if (DocUtils.isListType(result.getClass())) {
            visitList(st, result);
        } else if (DocUtils.isArrayType(result.getClass())) {
            visitArray(st, result);            
        } else if (DocUtils.isEmbeddedType(result.getClass())) {
            visitEmbedded(st,result);
        }
    }

    @Override
    public void visitArray(SchemaType schemaType,Object sourceObject) {
        visitList(schemaType, sourceObject);        
    }
    
/*    @Override
    public VisitorInfo getInfo(int idx) {
        return this.infoList.get(idx);
    }
    @Override
    public VisitorInfo getInfo() {
        return this.infoList.get(infoList.size()-1);
    }
    
    @Override
    public Object getResult() {
        return this.infoList.get(infoList.size()-1).getResult();
    }
    @Override
    public Exception getException() {
        return this.infoList.get(infoList.size()-1).getException();
    }
*/
    @Override
    public void visitEmbedded(SchemaType schemaType, Object sourceObject, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visitList(SchemaType schemaType, Object sourceObject, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visitArray(SchemaType schemaType, Object sourceObject, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
