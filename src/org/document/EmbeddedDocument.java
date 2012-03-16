/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 *
 * @author Valery
 */
public class EmbeddedDocument<D> extends AbstractDocument<D> {
    public  EmbeddedDocument() {
        this.dataMap = new HashMap();
    }

    public  EmbeddedDocument(D data) {
        this.data = data;
        this.dataMap = new HashMap();
    }
    
    public Object cloneData(Object value) {
        Object target = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(value);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());            
            ObjectInputStream ois = new ObjectInputStream(bis);
            
            target = ois.readObject();
            
        } catch (Exception ex) {
            
        }
        return target;
    }
    
    
}
