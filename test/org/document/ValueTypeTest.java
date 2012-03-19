/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Valery
 */
public class ValueTypeTest {
    
    public ValueTypeTest() {
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
     * Test of getJavaType method, of class ValueType.
     */
    @Test
    public void testGetType() {
        System.out.println("ValueType: getType()");
        ValueType instance = new ValueType(Integer.class);
        Class expResult = Integer.class;
        Class result = instance.getJavaType();
        assertEquals(expResult, result);
    }

    /**
     * Test of compare method, of class ValueType.
     */
    @Test
    public void testCompare() {
        System.out.println("ValueTypeImplTest: compare");
        Object o1 = null;
        Object o2 = null;
        ValueType instance = new ValueType(Integer.class );
        int expResult = 0;
        int result = instance.compare(o1, o2);
        assertEquals(expResult, result);
        
        o2 = new Integer(5);
        expResult = -1;
        result = instance.compare(o1, o2);
        assertEquals(expResult, result);

        o1 = new Integer(5);
        o2 = null;
        expResult = 1;
        result = instance.compare(o1, o2);
        assertEquals(expResult, result);

        o1 = new Integer(5);
        o2 = new Integer(5);
        expResult = 0;
        result = instance.compare(o1, o2);
        assertEquals(expResult, result);

        o1 = new Integer(5);
        o2 = new Integer(6);
        expResult = -1;
        result = instance.compare(o1, o2);
        assertEquals(expResult, result);

        o1 = new Integer(6);
        o2 = new Integer(5);
        expResult = 1;
        result = instance.compare(o1, o2);
        assertEquals(expResult, result);
        
        o1 = new Object();
        o2 = new Object();
        expResult = -2;
        result = instance.compare(o1, o2);
        assertEquals(expResult, result);
        
        o1 = new Object();
        o2 = o1;
        expResult = 0;
        result = instance.compare(o1, o2);
        assertEquals(expResult, result);
        

    }

    /**
     * Test of getDefaultValue method, of class ValueType.
     */
    @Test
    public void testGetDefaultValue() {

        System.out.println("ValueType: getDefaultValue()");
        ValueType instance = new ValueType(Integer.class);

        Integer result = (Integer)instance.getDefaultValue();
        assertNull(result);
        
        instance.setDefaultValue(123);
        Integer expResult = 123;
        result = (Integer)instance.getDefaultValue();
        assertEquals(expResult,result);
    }

    /**
     * Test of setDefaultValue method, of class ValueType.
     */
    @Test
    public void testSetDefaultValue() {
        System.out.println("ValueType: setDefaultValue()");
        ValueType instance = new ValueType(Integer.class);
        
        instance.setDefaultValue(123);
        Integer expResult = 123;
        Integer result = (Integer)instance.getDefaultValue();
        assertEquals(expResult,result);
        
        instance.setDefaultValue(null);
        result = (Integer)instance.getDefaultValue();
        assertNull(result);
        
    }
}
