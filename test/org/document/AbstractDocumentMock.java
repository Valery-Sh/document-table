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
public class AbstractDocumentMock<T> extends AbstractDocument{

    private Map<Object,Object> data = new HashMap<Object,Object>();
    
    @Override
    protected T newDataInstance() {
        return (T)new HashMap();
    }

    @Override
    protected T cloneData() {
        Map m = new HashMap();
        
        for ( Map.Entry o : data.entrySet() ) {
            m.put(o.getKey(),o.getValue());
        }
        return (T)m;
        
    }

    @Override
    public Object get(Object key) {
        return data.get(key);
    }

    @Override
    public void put(Object key, Object value) {
        data.put(key, value);
    }
/*    public AbstractDocumentMock() {
        super();
    }
    public AbstractDocumentMock() {
        super();
    }
*/    
}
