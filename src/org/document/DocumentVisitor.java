package org.document;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author V. Shyshkin
 */
public abstract  class DocumentVisitor {

    protected Document rootDoc;
    protected List<DocumentVisitor.VisitorInfo> infoList;
    protected String key;
    protected String[] paths;

    public DocumentVisitor(Document rootDoc) {
        this.rootDoc = rootDoc;
        infoList = new ArrayList<DocumentVisitor.VisitorInfo>();
    }

    public void visitDocument(String key) {
        paths = DocUtils.split(key, '/');
        infoList = new ArrayList<DocumentVisitor.VisitorInfo>();
    }
    public void visitDocument(String key,Object value) {
    }

    public void setPaths(String ... path) {
        this.paths = path;
    }


    public abstract void visitEmbedded(SchemaType schemaType, Object sourceObject);
    
    public abstract void visitList(SchemaType schemaType, Object sourceObject);
    public abstract void visitArray(SchemaType schemaType,Object sourceObject);
    public abstract void visitEmbedded(SchemaType schemaType, Object sourceObject, Object value);
    public abstract void visitList(SchemaType schemaType, Object sourceObject, Object value);
    public abstract void visitArray(SchemaType schemaType,Object sourceObject, Object value);
    
    public DocumentVisitor.VisitorInfo getInfo(int idx) {
        return this.infoList.get(idx);
    }
    public DocumentVisitor.VisitorInfo getInfo() {
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
