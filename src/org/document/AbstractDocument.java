/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.Map;

/**
 *
 * @author Valery
 */
public abstract class AbstractDocument<T> implements Document {

    protected DocumentGroup documentGroup;
//    protected Map dataMap = new HashMap();
    protected DocumentSchema schema;

/*    @Override
    public Object get(Object key) {
        Object value;
        if (getSchema() != null) {
            value = get(key, getSchema());
        } else {
            value = dataMap.get(key);
        }
        return dataMap.get(key);
    }

    protected Object get(Object key, DocumentSchema schema) {
        Object value = null;
        Field f = schema.getField(key);
        if (f == null) {
            return null;
        }
        return value;
    }

    @Override
    public void put(Object key, Object value) {
        dataMap.put(key, value);
    }
*/
    @Override
    public DocumentSchema getSchema() {
        return schema;
    }
    protected abstract T newDataInstance();

    protected abstract T cloneData();
/*    protected Object newDataInstance() {
        return DocUtils.newInstance(dataMap);
    }

    protected Map cloneData() {
        return DocUtils.cloneValue(dataMap);
    }
*/
}
