
package org.document;

import java.util.Map;

/**
 *
 * @author Valery
 */
public interface DocumentGroup {
//    Document create();
//    Document create(DocumentSchema schema);    
    ObjectDocument create(Object javaBean);
    void addSchema(DocumentSchema schema);
    DocumentSchema createSchema(Class clazz);
    DocumentSchema createSchema(Map map);
    DocumentSchema getSchema(Document doc);
    
}
