/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.List;
import org.document.impl.DefaultReference;
import org.document.impl.DefaultSchema;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Valery
 */
public class FieldTest {
    
    public FieldTest() {
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
     * Test of add method, of class Field.
     */
    @Test
    public void testAdd_ValueType() {
        System.out.println("Field: add(ValueType)");
        ValueType type = new ValueType(Integer.class);
        
        // No supported type added yet
        Field instance = new Field("age");
        assertEquals(0,instance.getSupportedTypes().size());
        // add one supported type
        instance.add(type);
        List list = instance.getSupportedTypes();
        assertEquals(1,list.size());        
        // Valid 
        Object expResult = instance.getSupportedTypes().get(0);
        assertEquals(expResult.getClass(),ValueType.class);
        
        type = (ValueType)instance.getSupportedTypes().get(0);
        assertNull(type.getDefaultValue());

        
        type = new ValueType(String.class);
        //
        // add second supported type
        //
        instance.add(type);
        assertEquals(2,instance.getSupportedTypes().size());
        
        list = instance.getSupportedTypes();
        assertEquals(2,list.size());        

        expResult = instance.getSupportedTypes().get(1);
        assertEquals(expResult.getClass(),ValueType.class);
        
        type = (ValueType)instance.getSupportedTypes().get(0);
        assertNull(type.getDefaultValue());

        type = (ValueType)instance.getSupportedTypes().get(1);
        assertNull(type.getDefaultValue());
        
        assertEquals(Integer.class,((ValueType)instance.getSupportedTypes().get(0)).getType());
        assertEquals(String.class,((ValueType)instance.getSupportedTypes().get(1)).getType());        

        
    }

    /**
     * Test of add method, of class Field.
     */
    @Test
    public void testAdd_DocumentSchema() {
        System.out.println("Field: add(DocumentSchema)");
        DocumentSchema schema = new DefaultSchema();
        // No supported type added yet
        Field instance = new Field("address");
        instance.add(schema);
        assertEquals(1,instance.getSupportedTypes().size());
        
    }

    /**
     * Test of add method, of class Field.
     */
    @Test
    public void testAdd_DocumentReference() {
        System.out.println("Field: add(DocumentReference)");
        ReferenceType ref = new ReferenceType();
        // No supported type added yet
        Field instance = new Field("customer");
        instance.add(ref);
        assertEquals(1,instance.getSupportedTypes().size());
        
    }

    /**
     * Test of getSupportedTypes method, of class Field.
     */
    @Test
    public void testGetSupportedTypes() {
        System.out.println("Field: getSupportedTypes()");
        ReferenceType ref = new ReferenceType();
        // No supported type added yet
        Field instance = new Field("customer");
        instance.add(ref);
        assertEquals(1,instance.getSupportedTypes().size());
        
    }

    /**
     * Test of getName method, of class Field.
     */
    @Test
    public void testGetName() {
        System.out.println("Field: getName()");
        Field instance = new Field("customer");
        assertEquals("customer",instance.getName());
        
    }

    /**
     * Test of isRequired method, of class Field.
     */
    @Test
    public void testIsRequired() {
        System.out.println("Field: isRequired()");
        Field instance = new Field("firstName");
        boolean result = instance.isRequired();
        assertFalse(result);
        
        instance = new Field("firstName", true, false);
        result = instance.isRequired();
        assertTrue(result);
        
    }

    /**
     * Test of isNotNull method, of class Field.
     */
    @Test
    public void testIsNotNull() {
        System.out.println("Field: isNotNull()");
        
        Field instance = new Field("firstName");
        boolean result = instance.isNotNull();
        assertFalse(result);
        
        instance = new Field("firstName", false, true);
        result = instance.isNotNull();
        assertTrue(result);
    }

    /**
     * Test of setNotNull method, of class Field.
     */
    @Test
    public void testSetNotNull() {
        System.out.println("Field: setNotNull(boolean)");
        Field instance = new Field("firstName");
        instance.setNotNull(false);
        boolean result = instance.isNotNull();
        assertFalse(result);
        
        instance.setNotNull(true);
        result = instance.isNotNull();
        assertTrue(result);
    }

    /**
     * Test of setRequired method, of class Field.
     */
    @Test
    public void testSetRequired() {
        System.out.println("Field: setRequired(boolean)");
        Field instance = new Field("firstName");
        instance.setRequired(false);
        boolean result = instance.isRequired();
        assertFalse(result);
        
        instance.setRequired(true);
        result = instance.isRequired();
        assertTrue(result);
    }
}
