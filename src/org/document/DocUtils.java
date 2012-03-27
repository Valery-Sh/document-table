/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import org.document.impl.DefaultSchema;
import org.document.impl.MapSchema;
import org.document.impl.NoFieldSchema;

/**
 *
 * @author Valery
 */
public class DocUtils {
    public static int getFieldCount(Class type) {
        int count = 0;
        try {
            BeanInfo binfo = Introspector.getBeanInfo(type, Object.class);
            PropertyDescriptor[] props = binfo.getPropertyDescriptors();
            count = props.length;
        } catch (IntrospectionException ex) {
        }
        
        return count;
    }
    public static <K, V> DocumentSchema createSchema(Map<String, V> map) {
        DocumentSchema schema = new DefaultSchema();
        for (Map.Entry<String, V> mt : map.entrySet()) {
            Class ptype = String.class;
            if (mt.getValue() != null) {
                ptype = mt.getValue().getClass();
            }

            String pname = mt.getKey();

            Field f = createField(pname, ptype);
            if (f != null) {
                schema.getFields().add(f);
            }

        }
        return schema;
    }

    public static DocumentSchema createSchema(Class clazz) {
        DocumentSchema schema;
        if ( isMapType(clazz)) {
            return new MapSchema(clazz);
        } else if ( isSetType(clazz)) {
            return new NoFieldSchema(clazz);
        }

        schema = new DefaultSchema(clazz);
        try {
            BeanInfo binfo = Introspector.getBeanInfo(clazz, Object.class);
            PropertyDescriptor[] props = binfo.getPropertyDescriptors();

            for (int i = 0; i < props.length; i++) {
                Class ptype = props[i].getPropertyType();
                String pname = props[i].getName();

                Field f = createField(pname, ptype);
                if (f != null) {
                    schema.getFields().add(f);
                }
            }//for

        } catch (IntrospectionException ex) {
        }
        return schema;
    }
    /**
     * Create and returns a new instance of type 
     * <code>org.document.Field</code> with a name and type specified by parameters.
     * The type specified by the second parameter may be one of the following:
     * <ul>
     *  <li><i><b>Value type</b></i> means that the method 
     *      <code>DocUtils.isValueType(type)</code> 
     *      returns <code>true</code>
     *  </li> 
     *  <li>
     *      <i><b>Array type</b></i> means that the method 
     *      <code>DocUtils.isListType(type)</code> 
     *      returns <code>true</code>. If the <code>type.isArray()</code> 
     *      is <code>true</code> then a supported type is represented
     *      with a class <code>org.document.ArrayType</code>. 
     *      Otherwise with a class <code>org.document.ListType</code>.
     * </li> 
     *  <li>
     *      <i><b>Embedded type</b></i> means that the method 
     *      <code>DocUtils.isEmbeddedType(type)</code> 
     *      returns <code>true</code>. In general if a type is neither a <i>Value Type</i>,
     *      an <i>Array Type</i> or a <i>Reference Type</i> it is 
     *      considered to be an <i>Embedded Type</i>.
     *  </li> 
     * 
     * </ul>
     * @param name the  name of the field to be created.
     * @param type the class witch represents a supported type. 
     * @return a new instance of type <code>org.document.Field</code>.
     */
    public static Field createField(String name, Class type) {
        Field f = new Field(name, false, false);
        if (isValueType(type)) {
            f.setSchemaType(new ValueType(type));
        } else if (isListType(type)) {
            f.setSchemaType(new ListType(type));
        } else if (isArrayType(type)) {
                f.setSchemaType(new ArrayType(type));
        } else if (DocumentReference.class.isAssignableFrom(type)) {
            f.setSchemaType(new ReferenceType());
        } else {
            DocumentSchema embSchema = DocUtils.createSchema(type);
            f.setSchemaType(new EmbeddedType(embSchema));
        }
        return f;
    }
    public static Object getValue(String key,Object obj) {
        String error = "";
        if ( obj instanceof Map ) {
            return ((Map)obj).get(key);
        }
        try {
            BeanInfo binfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
            PropertyDescriptor[] props = binfo.getPropertyDescriptors();
            
            for (int i = 0; i < props.length; i++) {
                String pname = props[i].getName();
                if ( key.equals(pname) ) {
                    Method m = props[i].getReadMethod();
                    Object v = m.invoke(obj, null);
                    return v;
                }

            }//for

        } catch (IntrospectionException ex) {
            error = ex.getMessage();
        } catch (IllegalAccessException ex) {
            error = ex.getMessage();
        } catch (java.lang.reflect.InvocationTargetException ex) { 
            error = ex.getMessage();
        }
        
        throw new NullPointerException("An object of type " + obj.getClass() + " doesn't contain a field with a name " + key + "(" + error + ")");
    }

    public static Object setValue(String key,Object obj, Object newValue) {
        String error = "";
        if ( obj instanceof Map ) {
            ((Map)obj).put(key,newValue);
            return newValue;
        }
        try {
            BeanInfo binfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
            PropertyDescriptor[] props = binfo.getPropertyDescriptors();
            
            for (int i = 0; i < props.length; i++) {
                String pname = props[i].getName();
                if ( key.equals(pname) ) {
                    Method m = props[i].getWriteMethod();
                    m.invoke(obj, newValue);
                    m = props[i].getReadMethod();
                    return m.invoke(obj, null);
                }

            }//for

        } catch (IntrospectionException ex) {
            error = ex.getMessage();
        } catch (IllegalAccessException ex) {
            error = ex.getMessage();
        } catch (java.lang.reflect.InvocationTargetException ex) { 
            error = ex.getMessage();
        }
        
        throw new NullPointerException("An object of type " + obj.getClass() + " doesn't contain a field with a name " + key + "(" + error + ")");
    }
    
    public static boolean isValueType(Class type) {
        return type.isPrimitive()
                || type.equals(java.lang.Object.class)
                || type.equals(java.util.Date.class)
                || type.equals(String.class)
                || type.equals(Boolean.class)
                || type.equals(Integer.class)
                || type.equals(Short.class)
                || type.equals(Byte.class)
                || type.equals(Float.class)
                || type.equals(Double.class)
                || type.equals(Character.class)
                || type.equals(Long.class)
                || type.equals(java.sql.Time.class)
                || type.equals(java.sql.Timestamp.class)
                || type.equals(java.sql.Date.class)
                || type.equals(BigInteger.class)
                || type.equals(BigDecimal.class);
    }

    public static boolean isListType(Class type) {
        return List.class.isAssignableFrom(type);
    }
    public static boolean isMapType(Class type) {
        return Map.class.isAssignableFrom(type);
    }
    public static boolean isSetType(Class type) {
        return Set.class.isAssignableFrom(type);
    }

    public static boolean isArrayType(Class type) {
        return  type.isArray();
    }

    
    public static boolean isEmbeddedType(Class type) {
        return  ! ( isListType(type) || isArrayType(type) || isValueType(type));

    }
    
    
    public static <T> T cloneValue(T value) {
        if (value == null) {
            return null;
        }

        T target = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(value);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);

            target = (T) ois.readObject();

        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return target;
    }

    public static Object newArrayTypeInstance(Class type) {

        if (type == null || !type.isArray()) {
            return null;
        }
        Object result;
        Class aclass;
        int[] dims = new int[]{0}; // to calculate an array dimmention
        dims[0] = 1;
        aclass = getBaseComponentType(type, dims);
        int[] d = new int[dims[0]]; // all element values == 0
        result = Array.newInstance(aclass, d);
        return result;
    }
    public static Class getWrapper(Class primitive) {
        if ( ! primitive.isPrimitive()) {
            return primitive;
        }
        Class result = null;
        if ( primitive.equals(int.class)) {
            result = Integer.class;
        } else if ( primitive.equals(long.class)) {
            result = Long.class;
        } else if ( primitive.equals(byte.class)) {
            result = Byte.class;
        } else if ( primitive.equals(char.class)) {
            result = Character.class;
        } else if ( primitive.equals(boolean.class)) {
            result = Boolean.class;
        } else if ( primitive.equals(short.class)) {
            result = Short.class;
        } else if ( primitive.equals(float.class)) {
            result = Float.class;
        } else if ( primitive.equals(double.class)) {
            result = Double.class;
        } 
        return result;
    }
    public static Class getBaseComponentType(Class type, int[] dimCount) {

        if (type == null) {
            return null;
        }
        Class result = type.getComponentType();
        if (result.isArray()) {
            dimCount[0]++;
            result = getBaseComponentType(result, new int[1]);
        }
        return result;
    }

    public static Object newInstance(Class type) {

        if (type == null) {
            return null;
        }
        Object r;

        if (type.isPrimitive()) {
            r = primitiveInstance(type);
        } else if (type.equals(String.class)  ) {
            r = "";
        } else if (type.equals(Object.class)  ) {
            r = new java.lang.Object();
        }  else if (type.equals(Collection.class)) {
            r = new ArrayList();
        } else if (type.equals(Map.class)) {
            r = new HashMap();
        } else if (type.equals(List.class)) {
            r = new ArrayList();
        } else if (type.equals(Set.class)) {
            r = new HashSet();
        } else {
            r = wrapperInstance(type);
        }
        if (r != null) {
            return r;
        }
        try {
            if (type.isArray()) {
                Class ct = type.getComponentType();
                r = Array.newInstance(ct, 0);
            } else {
                r = type.newInstance();
            }
        } catch (InstantiationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }

    public static Object newInstance(Object source) {
        if (source == null) {
            return null;
        }
        return newInstance(source.getClass());
    }

    protected static Object primitiveInstance(Class type) {
        Object r = null;

        if (type.equals(Boolean.TYPE)) {
            r = false;
        } else if (type.equals(Integer.TYPE)) {
            r = 0;
        } else if (type.equals(Short.TYPE)) {
            r = 0;
        } else if (type.equals(Byte.TYPE)) {
            r = new Byte("0");
        } else if (type.equals(Float.TYPE)) {
            r = 0.0f;
        } else if (type.equals(Double.TYPE)) {
            r = 0.0d;
        } else if (type.equals(Character.TYPE)) {
            r = ' ';
        } else if (type.equals(Long.TYPE)) {
            r = 0L;
        }
        return r;
    }

    protected static Object wrapperInstance(Class type) {
        Object r = null;

        if (type.equals(Boolean.class)) {
            r = false;
        } else if (type.equals(Integer.class)) {
            r = 0;
        } else if (type.equals(Byte.class)) {
            r = new Byte("0");
        } else if (type.equals(Short.class)) {
            r = new Short("0");
        } else if (type.equals(Float.class)) {
            r = 0.0f;
        } else if (type.equals(Double.class)) {
            r = 0.0d;
        } else if (type.equals(Character.class)) {
            r = ' ';
        } else if (type.equals(Long.class)) {
            r = 0L;
        } else if (type.equals(java.sql.Time.class)) {
            r = new java.sql.Time(0);
        } else if (type.equals(java.sql.Timestamp.class)) {
            r = new java.sql.Timestamp(0);
        } else if (type.equals(java.sql.Date.class)) {
            r = new java.sql.Date(0);
        } else if (type.equals(BigInteger.class)) {
            r = new BigInteger("0");
        } else if (type.equals(BigDecimal.class)) {
            r = new BigDecimal(0);
        }
        return r;
    }
    
    public static String[] split(String key, char dlm) {
        String k = key.trim();
        if ((!k.isEmpty()) && key.charAt(0) == dlm) {
            k = key.substring(1);
        }
        String[] result = k.split(String.valueOf(dlm));
        for (int i = 0; i < result.length; i++) {
            result[i] = result[i].trim();
        }
        return result;
    }
    
}
