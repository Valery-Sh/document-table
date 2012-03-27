/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.*;

/**
 *
 * @author Valery
 */
public class ListTypeTest {
    
    public ListTypeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getJavaType method, of class ListType.
     */
    @Test
    public void testGetJavaType() {
        System.out.println("ListTypeTest: getJavaType()");
        ListType instance = new ListType();
        Class expResult = List.class;
        Class result = instance.getJavaType();
        assertEquals(expResult, result);
        
        instance = new ListType(ArrayList.class);
        expResult = ArrayList.class;
        result = instance.getJavaType();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setJavaType method, of class ListType.
     */
    @Test
    public void testSetJavaType() {
        System.out.println("ListTypeTest: setJavaType(Class)");
        ListType instance = new ListType();
        Class expResult = List.class;
        instance.setJavaType(List.class);
        Class result = instance.getJavaType();
        assertEquals(expResult, result);
        
        instance = new ListType(ArrayList.class);
        expResult = List.class;
        instance.setJavaType(List.class);
        result = instance.getJavaType();
        assertEquals(expResult, result);
    }

    /**
     * Test of put(class) method, of class ListType.ListTypeSet
     */
    @Test
    public void testListType_ListTypeSet_Put() {
        System.out.println("ListType.ListTypeSet: put(Class)");
        
        ListType instance = new ListType();
        instance.getSupportedSchemaTypes().put(String.class);
        Object result = instance.getSupportedSchemaTypes().get(String.class);
        assertEquals(new ValueType(String.class),result);
        assertTrue(result instanceof SchemaType);
        
        instance.getSupportedSchemaTypes().put(Person[].class);
        result = instance.getSupportedSchemaTypes().get(Person[].class);
        assertEquals(new ArrayType(Person[].class),result);
        
        
        //
        // Exception when add primitive type
        //
        try {
            instance.getSupportedSchemaTypes().put(int.class);
            fail("Parameter cannot be primitive");
        } catch(IllegalArgumentException e) {
            System.out.println("Parameter cannot be primitive");
        }
        //
        // Exception when add null
        //
        try {
            instance.getSupportedSchemaTypes().put(null);
            fail("Parameter cannot be nall");
        } catch(NullPointerException e) {
            System.out.println("Parameter cannot be null");
        }
        
    }

    /**
     * Test of add(SchemaType) method, of class ListType.ListTypeSet
     */
    @Test
    public void testListType_ListTypeSet_Add() {
        System.out.println("ListType.ListTypeSet: add(SchemaType)");
        SchemaType type = new ValueType(Integer.class);
        ListType instance = new ListType();
        instance.getSupportedSchemaTypes().add(type);
        Object result = instance.getSupportedSchemaTypes().get(int.class);
        assertEquals(new ValueType(Integer.class),result);
        //
        // Exception when add ValueType of primitive
        //
        try {
            instance.getSupportedSchemaTypes().add(new ValueType(int.class));
            fail("Value type cannot be primitive");
        } catch(IllegalArgumentException e) {
            System.out.println("Value type cannot be primitive");
        }
        //
        // Exception when add null
        //
        try {
            instance.getSupportedSchemaTypes().add(null);
            fail("Parameter cannot be nall");
        } catch(NullPointerException e) {
            System.out.println("Parameter cannot be null");
        }
        
    }

    /**
     * Test of getSupportedTypes method, of class ListType.
     */
    @Test
    public void testGetSupportedSchemaTypes() {
        System.out.println("ListType: getSupportedSchemaTypes()");
        ListType instance = new ListType();
        HashSet result = instance.getSupportedSchemaTypes();
        assertTrue(result.isEmpty());
        instance.getSupportedSchemaTypes().add(new ValueType(String.class));
        result = instance.getSupportedSchemaTypes();        
        assertTrue(result.size() == 1);
        
    }

    /**
     * Test of get(Class) method, of class ListType.ListTypeSet
     */
    @Test
    public void testListType_ListTypeSet_Get() {
        System.out.println("ListType.ListTypeSet: get(Class)");
        ListType.ListTypeSet instance = new ListType.ListTypeSet();
        //
        // When no supported type were specified it is considered
        // that any type is supported
        //
        Object result = instance.get(Integer.class);
        assertEquals(new ValueType(Integer.class),result);        
        // Primitive is replaced by wrapper
        result = instance.get(int.class);
        assertEquals(new ValueType(Integer.class),result); 
        // Java Beans
        result = instance.get(Person.class);
        assertEquals(new EmbeddedType(Person.class),result);        
        // Java array
        result = instance.get(int[][].class);
        assertEquals(new ArrayType(int[][].class),result);        
        // array of Java Bean
        result = instance.get(Person[].class);
        assertEquals(new ArrayType(Person[].class),result);        
        
        //
        // Add supported type and try get it. Now only explicitly added 
        // supported types can be found
        //
        instance.add(new ValueType(Integer.class));
        // Java Bean
        instance.add(new EmbeddedType(Person.class));
        result = instance.get(Person.class);
        assertEquals(new EmbeddedType(Person.class),result);        
        
        result = instance.get(Integer.class);
        assertEquals(new ValueType(Integer.class),result);
        // primitives are allowed
        result = instance.get(int.class);
        assertEquals(new ValueType(Integer.class),result);
        
        result = instance.get(String.class);
        assertNull(result);
        
    }

    /**
     * Test of getDefaultValue method, of class ListType.
     */
    @Test
    public void testGetDefaultValue() {
        System.out.println("ListType: getDefaultValue()");
        ListType instance = new ListType();
        List expResult = new ArrayList();
        List result = instance.getDefaultValue();
        assertEquals(expResult, result);
        instance.setDefaultValue(new LinkedList());
        result = instance.getDefaultValue();
        assertEquals(new LinkedList(),result);
    }

    /**
     * Test of setDefaultValue method, of class ListType.
     */
    @Test
    public void testSetDefaultValue() {
        System.out.println("ListType: setDefaultValue(List)");
        ListType instance = new ListType();
        instance.setDefaultValue(new LinkedList());
        Object result = instance.getDefaultValue();
        assertEquals(new LinkedList(),result);

    }

}
