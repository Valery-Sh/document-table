/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

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
     * Test of addSupportedByClass method, of class ListType.
     */
    @Test
    public void testAddSupportedByClass() {
        System.out.println("ListTypeTest: addSupportedByClass(Class)");
        Class type = String.class;
        ListType instance = new ListType();
        instance.addSupportedByClass(type);
        Object result = instance.getSupportedByClass(String.class);
        assertNotNull(result);
        assertTrue(result instanceof SchemaType);
        result = instance.getSupportedByClass(Integer.class);
        assertNull(result);
        //
        // Exception when add primitive type
        //
        try {
            instance.addSupportedByClass(int.class);
            fail("Parameter cannot be primitive");
        } catch(IllegalArgumentException e) {
            System.out.println("Parameter cannot be primitive");
        }
        //
        // Exception when add null
        //
        try {
            instance.addSupportedByClass(null);
            fail("Parameter cannot be nall");
        } catch(NullPointerException e) {
            System.out.println("Parameter cannot be null");
        }
        
    }

    /**
     * Test of addSupported method, of class ListType.
     */
    @Test
    public void testAddSupported() {
        System.out.println("ListTypeTest: addSupported(SchemaType)");
        SchemaType type = new ValueType(Integer.class);
        ListType instance = new ListType();
        instance.addSupported(type);
        Object result = instance.getSupportedByClass(int.class);
        assertEquals(new ValueType(Integer.class),result);
        //
        // Exception when add ValueType of primitive
        //
        try {
            instance.addSupported(new ValueType(int.class));
            fail("Value type cannot be primitive");
        } catch(IllegalArgumentException e) {
            System.out.println("Value type cannot be primitive");
        }
        //
        // Exception when add null
        //
        try {
            instance.addSupported(null);
            fail("Parameter cannot be nall");
        } catch(NullPointerException e) {
            System.out.println("Parameter cannot be null");
        }
        
    }

    /**
     * Test of getSupportedTypes method, of class ListType.
     */
    @Test
    public void testGetSupportedTypes() {
        System.out.println("ListTypeTest: getSupportedTypes()");
        ListType instance = new ListType();
        List expResult = null;
        List result = instance.getSupportedTypes();
        assertTrue(result.isEmpty());
        instance.addSupported(new ValueType(String.class));
        result = instance.getSupportedTypes();        
        assertTrue(result.size() == 1);
        assertEquals(String.class, ((SchemaType)result.get(0)).getJavaType());
        
    }

    /**
     * Test of getSupportedByClass method, of class ListType.
     */
    @Test
    public void testGetSupportedByClass() {
        System.out.println("ListTypeTest: getSupportedByClass(Class)");
        ListType instance = new ListType();
        List expResult = null;
        //
        // When no supported type were specified it is considered
        // that any type is supported
        //
        Object result = instance.getSupportedByClass(Integer.class);
        assertEquals(new ValueType(Integer.class),result);        
        // Primitive is replaced by wrapper
        result = instance.getSupportedByClass(int.class);
        assertEquals(new ValueType(Integer.class),result); 
        // Java Beans
        result = instance.getSupportedByClass(Person.class);
        EmbeddedType et = new EmbeddedType(DocUtils.createSchema(Person.class));
        assertEquals(et,result);        
        
        //
        // Add supported type and try get it. Now only explicitly added 
        // supported types can be found
        //
        instance.addSupported(new ValueType(Integer.class));
        // Java Bean
        instance.addSupported(et);
        result = instance.getSupportedByClass(Person.class);
        EmbeddedType expet = new EmbeddedType(DocUtils.createSchema(Person.class));
        assertEquals(expet,result);        
        
        result = instance.getSupportedByClass(Integer.class);
        assertEquals(new ValueType(Integer.class),result);
        // primitives are allowed
        result = instance.getSupportedByClass(int.class);
        assertEquals(new ValueType(Integer.class),result);
        
        result = instance.getSupportedByClass(String.class);
        assertNull(result);
        
    }

    /**
     * Test of getDefaultValue method, of class ListType.
     */
    @Test
    public void testGetDefaultValue() {
        System.out.println("getDefaultValue");
        ListType instance = new ListType();
        List expResult = null;
        List result = instance.getDefaultValue();
//        assertEquals(expResult, result);
    }

    /**
     * Test of setDefaultValue method, of class ListType.
     */
    @Test
    public void testSetDefaultValue() {
        System.out.println("setDefaultValue");
        List value = null;
        ListType instance = new ListType();
        instance.setDefaultValue(value);
    }

}
