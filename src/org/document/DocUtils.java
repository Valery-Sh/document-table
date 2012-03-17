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
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import org.document.impl.DefaultSchema;

/**
 *
 * @author Valery
 */
public class DocUtils {

    public static DocumentSchema createSchema(Class clazz) {
        DocumentSchema schema = new DefaultSchema(clazz);
        try {
            BeanInfo binfo = Introspector.getBeanInfo(clazz, Object.class);
            PropertyDescriptor[] props = binfo.getPropertyDescriptors();

            for (int i = 0; i < props.length; i++) {
                Class ptype = props[i].getPropertyType();
                String pname = props[i].getName();
/*                System.out.println(i + ") " + props[i].getName() + "; ptype: " + props[i].getPropertyType() + "; read Method: " + props[i].getReadMethod());
                
                try {
                    java.lang.reflect.Field rf = ptype.getDeclaredField("myOrder");
                    if (rf != null) {
                        int m = rf.getModifiers();
                        System.out.println("m=" + m + "; M=" + Modifier.TRANSIENT);
                    }

                } catch (Exception ee) {
                    System.out.println("");
                }
  */              
                Field f = new Field(pname, false, false);
                if (isValueType(ptype)) {
                    f.add(new ValueType(ptype));
                } else if (isArrayType(ptype)) {
                    f.add(new ArrayType());
                } else if (DocumentReference.class.isAssignableFrom(ptype)) {
                    f.add(new ReferenceType());
                } else {
                    DocumentSchema embSchema = DocUtils.createSchema(ptype);
                    f.add(embSchema);
                }
                if (f != null) {
                    schema.getFields().add(f);
                }

            }//for

        } catch (IntrospectionException ex) {
        }
        return schema;
    }

    public static boolean isValueType(Class type) {
        return type.isPrimitive()
                || type.equals(java.util.Date.class)
                || type.equals(String.class)
                || type.equals(Boolean.class)
                || type.equals(Integer.class)
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

    public static boolean isArrayType(Class type) {
        return /*
                 * type.equals(Collection.class) || type.equals(Map.class) ||
                 * type.equals(List.class) || type.equals(Set.class)
                 */ type.isArray()
                || Collection.class.isAssignableFrom(type)
                || Map.class.isAssignableFrom(type);

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

    public static Object newInstance(Class type) {

        if (type == null) {
            return null;
        }
        Object r;

        if (type.isPrimitive()) {
            r = primitiveInstance(type);
        } else if (type.equals(String.class)) {
            r = "";
        } else if (type.equals(Collection.class)) {
            r = new ArrayList();
        } else if (type.equals(Map.class)) {
            r = new HashMap();
        } else if (type.equals(List.class)) {
            r = new ArrayList();
        } else if (type.equals(Set.class)) {
            r = new HashSet();
        } else if (type.equals(Document.class)) {
            r = new HashSet();
        } else {
            r = wrapperInstance(type);
        }
        if (r != null) {
            return r;
        }
        try {
            r = type.newInstance();
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
//            return new Integer(0);

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
}
