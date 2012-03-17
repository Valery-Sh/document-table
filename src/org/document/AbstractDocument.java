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
public class AbstractDocument implements Document {
    protected DocumentGroup documentGroup;
    protected Map dataMap = new HashMap();
    protected DocumentSchema schema;
    
    @Override
    public Object get(Object key) {
        return dataMap.get(key);
    }

    @Override
    public void put(Object key, Object value) {
        dataMap.put(key, value);
    }

    @Override
    public DocumentSchema getSchema() {
        return schema;
    }

    
/*    protected void setSchema(DocumentSchema schema) {
        this.schema = schema;
    }
*/
    protected Object newDataInstance() {
        return DocUtils.newInstance(dataMap);
    }    
    protected Map cloneData() {
        return DocUtils.cloneValue(dataMap);
    }
    
}
