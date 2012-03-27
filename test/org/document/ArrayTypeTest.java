/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.HashSet;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Valery
 */
public class ArrayTypeTest {
    
    public ArrayTypeTest() {
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
     * Test of put(class) method, of class ListType.ListTypeSet
     */
    @Test
    public void testArrayType_ArrayTypeSet_Put() {
        System.out.println("ArrayType.ArrayTypeSet: put(Class)");
        
        ArrayType instance = new ArrayType(String[].class);
        //
        // ArrayType has allready been created. Cannot invoke put method
        //
        try {
            instance.getSupportedSchemaTypes().put(int.class);
            fail("ArrayType has allready been created. Cannot invoke put method");
        } catch(UnsupportedOperationException e) {
            System.out.println("ArrayType has allready been created. Cannot invoke put method");
        }
        //
        // ArrayType has allready been created. Cannot invoke add method
        //
        try {
            instance.getSupportedSchemaTypes().add(new ArrayType(String[].class));
            fail("ArrayType has allready been created. Cannot invoke add method");
        } catch(UnsupportedOperationException e) {
            System.out.println("ArrayType has allready been created. Cannot invoke add method");
        }
        
    }

    /**
     * Test of add(SchemaType) method, of class ArrayType.ArrayTypeSet
     */
    @Test
    public void testListType_ListTypeSet_Add() {
        System.out.println("ListType.ListTypeSet: add(SchemaType)");
        ArrayType instance = new ArrayType(String[].class);
        
        //
        // ArrayType has allready been created. Cannot invoke add method
        //
        try {
            instance.getSupportedSchemaTypes().add(new ArrayType(String[].class));
            fail("ArrayType has allready been created. Cannot invoke add method");
        } catch(UnsupportedOperationException e) {
            System.out.println("ArrayType has allready been created. Cannot invoke add method");
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
     * Test of get(Class) method, of class ArrayType.ArrayTypeSet
     */
    @Test
    public void testArrayType_ArrayTypeSet_Get() {
        System.out.println("ArrayType.ArrayTypeSet: get(Class)");
        ArrayType owner = new ArrayType(String[].class);
        SchemaType st = owner.supportedSchemaTypes.get(String.class);
        assertEquals(new ValueType(String.class),st);
        
        owner = new ArrayType(String[][].class);
        st = owner.supportedSchemaTypes.get(String.class);
        assertNull(st);
        st = owner.supportedSchemaTypes.get(String[].class);        
        assertEquals(new ArrayType(String[].class),st);        
        
        //
        // Valid get call sequance 
        //
        owner = new ArrayType(Person[][][].class);
        st = owner.getSupportedSchemaTypes().get(Person[][].class);
        assertEquals(new ArrayType(Person[][].class),st);   
        
        st = st.getSupportedSchemaTypes().get(Person[].class);        
        assertEquals(new ArrayType(Person[].class),st);   
        
        st = st.getSupportedSchemaTypes().get(Person.class);        
        assertEquals(new EmbeddedType(Person.class),st);        
        
    }

    
    @Test
    public void testGetBaseType() {
        System.out.println("ArrayType: getBaseType()");
        Class type = String[][].class;
        ArrayType instance = new ArrayType(type);
        assertEquals(String.class,instance.getBaseType());
        
        type = Integer[].class;
        instance = new ArrayType(type);
        assertEquals(Integer.class,instance.getBaseType());
        
        type = Person[].class;
        instance = new ArrayType(type);
        assertEquals(Person.class,instance.getBaseType());
        //
        // Constructor without parameters
        //
        instance = new ArrayType();
        assertEquals(Object.class,instance.getBaseType());
        
    }
    
    @Test
    public void testGetDimensionSize() {
        Class type = String[].class;
        ArrayType instance = new ArrayType(type);
        assertEquals(1,instance.getDimentionSize());
        
        type = String[][].class;
        instance = new ArrayType(type);
        assertEquals(2,instance.getDimentionSize());

        //
        // Constructor without parameters
        //
        instance = new ArrayType();
        assertEquals(1,instance.getDimentionSize());
        
        
    }
}
