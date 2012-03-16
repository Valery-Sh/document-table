/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.document.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import org.document.AnonymousDocument;
import org.document.ValueType;

/**
 *
 * @author Valery
 */
public class ValueTypeImpl implements ValueType{
    
    private Class type;
    
    public ValueTypeImpl(Class clazz) {
        type = clazz;
    }
    
    @Override
    public Class getType() {
        return type;
    }
//outputStream = new ObjectOutputStream(new FileOutputStream(filename));
    public Object cloneValue(Object value) {
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
    
    public Object valueInstance() {
        Object r;

        if ( type.isPrimitive() ) {
            r = primitiveInstance();
        } else if ( type.equals(String.class) ) {
            r = "";
        } else if ( type.equals(Collection.class) ) {
            r = new ArrayList();
        } else if ( type.equals(Map.class) ) {
            r = new HashMap();
        } else if ( type.equals(List.class) ) {
            r = new ArrayList();
        } else if ( type.equals(Set.class) ) {
            r = new HashSet();
        } else if ( type.equals(AnonymousDocument.class) ) {
            r = new HashSet();
        } else {
            r = wrapperInstance();
        }
        if ( r != null ) {
            return r;
        }
        try {
            r = type.newInstance();
        } catch(InstantiationException e) {
           System.out.println(e.getMessage());  
        } catch(IllegalAccessException e) {
           System.out.println(e.getMessage());  
        }
        return r;
    }
    protected Object primitiveInstance() {
        Object r = null;
        
        if ( type.equals(Boolean.TYPE) ) {
            r = false;
        } else if ( type.equals(Integer.TYPE) ) {
            r = 0;
//            return new Integer(0);
            
        } else if ( type.equals(Byte.TYPE) ) {
            r = new Byte("0");
        } else if ( type.equals(Float.TYPE) ) {
            r = 0.0f;
        } else if ( type.equals(Double.TYPE) ) {
            r = 0.0d;
        } else if ( type.equals(Character.TYPE) ) {
            r =' ';
        } else if ( type.equals(Long.TYPE) ) {
            r = 0L;
        }
        return r;
    }    
    protected Object wrapperInstance() {
        Object r = null;

        if ( type.equals(Boolean.class) ) {
            r = false;
        } else if ( type.equals(Integer.class) ) {
            r = 0;
        } else if ( type.equals(Byte.class) ) {
            r = new Byte("0");
        } else if ( type.equals(Float.class) ) {
            r = 0.0f;
        } else if ( type.equals(Double.class) ) {
            r = 0.0d;
        } else if ( type.equals(Character.class) ) {
            r =' ';
        } else if ( type.equals(Long.class) ) {
            r = 0L;
        } else if ( type.equals(java.sql.Time.class) ) {
            r = new java.sql.Time(0);
        } else if ( type.equals(java.sql.Timestamp.class) ) {
            r = new java.sql.Timestamp(0);
        } else if ( type.equals(java.sql.Date.class) ) {
            r = new java.sql.Date(0);
        } else if ( type.equals(BigInteger.class) ) {
            r = new BigInteger("0");
        } else if ( type.equals(BigDecimal.class) ) {
            r = new BigDecimal(0);
        }
        return r;
    }    
    
    
    @Override
    public int compare(Object o1, Object o2) {
        int r;
        if ( o1 == null && o2 == null) {
            r = 0;
        } else if ( o1 == null ) {
            r = -1;
        } else if ( o2 == null ) {
            r = 1;
        } else if ( o1 == o2) {
            r = 0;
        } else if ( o1 instanceof Comparable) {
            r = ((Comparable)o1).compareTo((Comparable)o2);
        } else {
            r = -2;
        }
        return r;
    }
    
}
