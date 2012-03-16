/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import org.document.ValueType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.util.*;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author Valery
 */
public class ValueTypelTest {
    
    public ValueTypelTest() {
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
     * Test of getType method, of class ValueType.
     */
    @Test
    public void testGetType() {
/*        System.out.println("getType");
        ValueType instance = null;
        Class expResult = null;
        Class result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of cloneValue method, of class ValueType.
     */
    @Test
    public void testCloneValue() {
/*        System.out.println("ValueTypelTest: cloneValue");
        Object value = new Integer(11);
        Object target = new Integer(0);
        ValueType instance = new ValueType(Integer.class );
        target = instance.cloneValue(value);
        assertEquals(value,target);
        assertEquals(Integer.class,target.getClass());


        value = new ArrayList();
        List list = (List)value;
        list.add(0);
        list.add("a");
        
        //target = new ArrayList();
        instance = new ValueType(List.class );
        target = instance.cloneValue(value);
        assertEquals(value,target);
        assertEquals(ArrayList.class,target.getClass());

        instance = new ValueType(ArrayList.class );
        target = instance.cloneValue(value);
        assertEquals(value,target);
        assertEquals(ArrayList.class,target.getClass());

        value = new HashMap();
        Map map = (Map)value;
        map.put(0,1);
        map.put("a",0.3f);
        
        //target = new ArrayList();
        instance = new ValueType(Map.class );
        target = instance.cloneValue(value);
        assertEquals(value,target);
        assertEquals(HashMap.class,target.getClass());

        value = new HashSet();
        Set set = (Set)value;
        set.add(1);
        set.add("a");
        
        //target = new ArrayList();
        instance = new ValueType(Set.class );
        target = instance.cloneValue(value);
        assertEquals(value,target);
        assertEquals(HashSet.class,target.getClass());
  */      
        
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
     * Test of compare method, of class ValueType.
     */
    @Test
    public void testValueInstance() {
/*        System.out.println("ValueTypelTest: valueInstance");
        ValueType instance = new ValueType(Integer.class );
        Object expResult = new Integer(0);
        Object result = instance.valueInstance();
        assertEquals(Integer.class, result.getClass());

        instance = new ValueType(Long.class );
        expResult = 0L;
        result = instance.valueInstance();
        assertEquals(Long.class, result.getClass());
        
        instance = new ValueType(Float.class );
        expResult = 0.0f;
        result = instance.valueInstance();
        assertEquals(Float.class, result.getClass());

        instance = new ValueType(Double.class );
        expResult = 0.0d;
        result = instance.valueInstance();
        assertEquals(Double.class, result.getClass());
        
        instance = new ValueType(Boolean.class );
        expResult = false;
        result = instance.valueInstance();
        assertEquals(Boolean.class, result.getClass());

        instance = new ValueType(Byte.class );
        expResult = "0";
        result = instance.valueInstance();
        assertEquals(Byte.class, result.getClass());

        instance = new ValueType(Character.class );
        expResult = ' ';
        result = instance.valueInstance();
        assertEquals(Character.class, result.getClass());

        instance = new ValueType(Date.class );
        expResult = new Date();
        result = instance.valueInstance();
        assertEquals(Date.class, result.getClass());
        
        instance = new ValueType(java.sql.Date.class );
        expResult = new java.sql.Date(0);
        result = instance.valueInstance();
        assertEquals(java.sql.Date.class, result.getClass());

        instance = new ValueType(java.sql.Timestamp.class );
        expResult = new java.sql.Timestamp(0);
        result = instance.valueInstance();
        assertEquals(java.sql.Timestamp.class, result.getClass());
        
        instance = new ValueType(Time.class );
        expResult = new Time(0);
        result = instance.valueInstance();
        assertEquals(Time.class, result.getClass());
        
        instance = new ValueType(BigInteger.class );
        expResult = new BigInteger("0");
        result = instance.valueInstance();
        assertEquals(BigInteger.class, result.getClass());

        instance = new ValueType(BigDecimal.class );
        expResult = new BigDecimal(0);
        result = instance.valueInstance();
        assertEquals(BigDecimal.class, result.getClass());

        instance = new ValueType(int.class );
        expResult = 0;
        result = instance.valueInstance();
        assertEquals(Integer.class, result.getClass());

        instance = new ValueType(char.class );
        expResult = ' ';
        result = instance.valueInstance();
        assertEquals(Character.class, result.getClass());
        
        instance = new ValueType(boolean.class );
        expResult = false;
        result = instance.valueInstance();
        assertEquals(Boolean.class, result.getClass());
        
        instance = new ValueType(long.class );
        expResult = 0L;
        result = instance.valueInstance();
        assertEquals(Long.class, result.getClass());

        instance = new ValueType(byte.class );
        expResult = 0;
        result = instance.valueInstance();
        assertEquals(Byte.class, result.getClass());

        instance = new ValueType(float.class );
        expResult = 0;
        result = instance.valueInstance();
        assertEquals(Float.class, result.getClass());

        instance = new ValueType(double.class );
        expResult = 0;
        result = instance.valueInstance();
        assertEquals(Double.class, result.getClass());
        
        instance = new ValueType(Collection.class );
        expResult = new ArrayList();
        result = instance.valueInstance();
        assertEquals(ArrayList.class, result.getClass());

        instance = new ValueType(List.class );
        expResult = new ArrayList();
        result = instance.valueInstance();
        assertEquals(ArrayList.class, result.getClass());

        instance = new ValueType(Set.class );
        expResult = new HashMap();
        result = instance.valueInstance();
        assertEquals(HashSet.class, result.getClass());

        instance = new ValueType(Map.class );
        expResult = new HashMap();
        result = instance.valueInstance();
        assertEquals(HashMap.class, result.getClass());
  */      
    }
    
}
