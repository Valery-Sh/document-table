package org.document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author V. Shyshkin
 */
public class PutVisitor extends DocumentVisitor {


    public PutVisitor(Document rootDoc) {
        super(rootDoc);
    }

    public void visitDocument(String key,Object value) {
        paths = DocUtils.split(key, '/');
        infoList = new ArrayList<GetVisitor.VisitorInfo>();

        String[] decrPaths = new String[paths.length];
        System.arraycopy(paths, 0, decrPaths, 0, paths.length);
        // Me must keep in mind that a field may be in 'tail'
        Field f = rootDoc.getSchema().getField(paths[0]);

        if ( paths.length > 1 ) {
            decrPaths = new String[paths.length-1];
            System.arraycopy(paths, 0, decrPaths, 0, paths.length-1);
        }
        
        GetVisitor visitor = new GetVisitor(rootDoc);
        if ( paths.length > 1 ) {
            visitor.visitDocument(decrPaths);            
            Object sourceObject = visitor.getResult();
            if ( sourceObject == null ) {
                throw new NullPointerException("Null value for key path '" + key + "'");
            }
            if ( visitor.getException() != null ) {
                throw (RuntimeException)visitor.getException();
            }
            SchemaType st = visitor.getInfo().getResultSchemaType();
            visitPut(st, sourceObject,value);

        } else {
            Object sourceObject = f.isTail() ? ((ObjectDocument)rootDoc).tail: ((ObjectDocument)rootDoc).getDataObject();
            SchemaType st = new EmbeddedType(rootDoc.getSchema());
            visitPut(st, sourceObject,value);
        }
        
        if ( getException() != null ) {
            RuntimeException re = (RuntimeException)visitor.getException();
            throw re;
        }
        
        
    }

    protected void visitPut(SchemaType schemaType,Object sourceObject,Object newValue) {
        String lastKey = paths[0];
        if ( paths.length > 1 ) {
            lastKey = paths[paths.length-1];
        }
        this.paths = new String[] {lastKey};
        this.infoList.clear();
        if ( schemaType instanceof ArrayType || schemaType instanceof ComponentType ) {
            this.visitArray(schemaType, sourceObject, newValue);
        } else if (schemaType instanceof EmbeddedType ) {
            this.visitEmbedded(schemaType, sourceObject, newValue);
        }
    }

    protected void visitArray(SchemaType schemaType,Object sourceObject,Object newValue) {
        GetVisitor.VisitorInfo info = new GetVisitor.VisitorInfo(schemaType, sourceObject);
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
    public void visitEmbedded(SchemaType schemaType, Object sourceObject,Object newValue) {
        EmbeddedType embeddedType = (EmbeddedType) schemaType;        
        GetVisitor.VisitorInfo info = new GetVisitor.VisitorInfo(schemaType, sourceObject);
        infoList.add(info);
        DocumentSchema ds = embeddedType.getSchema();
        int pathIndex = infoList.size() - 1;
        Field f = ds.getField(paths[pathIndex]);
        
        String path = "";
        for (int i = 0; i <= pathIndex; i++) {
            path += "/" + paths[i];
        }

        if (f == null) {
            info.setException(new NullPointerException("A schema doesn't contain a field for key = " + path));            
            return;
        }
        String nm = paths[pathIndex];
        Object result;
        try {
            result = DocUtils.setValue(nm, sourceObject,newValue);
            info.setResult(result);
        } catch(Exception e) {
            info.setException(e);
        }
    }
    public GetVisitor.VisitorInfo getInfo(int idx) {
        return this.infoList.get(idx);
    }
    public GetVisitor.VisitorInfo getInfo() {
        return this.infoList.get(infoList.size()-1);
    }
    
    public Object getResult() {
        return this.infoList.get(infoList.size()-1).getResult();
    }
    public Exception getException() {
        return this.infoList.get(infoList.size()-1).getException();
    }
    
}
