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
public class MapDocument extends AbstractDocument {
    
    protected Map<String,Object> dataMap = new HashMap<String,Object>();    
    
    private Object primaryId;
    
    public MapDocument() {
        
    }
    public Object getPrimaryId() {
        return primaryId;
    }

    @Override
    public Object get(Object key) {
        if ( key == null || ( key.toString().trim().isEmpty()) ) {
            return null;
        }
        String s = key.toString();
        String[] paths = key.toString().split("/");
        Object value;
        if (getSchema() != null) {
            value = get(key, getSchema());
        } else {
            value = dataMap.get(key.toString());
        }
        return value;
    }
    
    protected String[] getKeyPaths(Object key,char dlm) {
        String[] result = ((String)key).split(String.valueOf(dlm));
        for( int i=0; i < result.length; i++) {
            result[i] = result[i].trim();
        }
        return result;
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
        dataMap.put(key.toString(), value);
    }
    @Override
    protected Object newDataInstance() {
        return DocUtils.newInstance(dataMap);
    }

    @Override
    protected Map cloneData() {
        return DocUtils.cloneValue(dataMap);
    }    
}
