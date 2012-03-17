/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Valery
 */
public class DocUtilsTest {
    
    public DocUtilsTest() {
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
     * Test of cloneValue method, of class DocUtils.
     */
    @Test
    public void testCreateSchema() {
        DocUtils.createSchema(Person.class);
    }
    /**
     * Test of cloneValue method, of class DocUtils.
     */
    @Test
    public void testCloneData() {
        System.out.println("DocUtils: cloneData");
        Object value = new Integer(11);
        Object target;
        target = DocUtils.cloneValue(value);
        assertEquals(value,target);
        assertEquals(Integer.class,target.getClass());


        value = new ArrayList();
        List list = (List)value;
        list.add(0);
        list.add("a");
        
        //target = new ArrayList();
        target = DocUtils.cloneValue(value);
        assertEquals(value,target);
        assertEquals(ArrayList.class,target.getClass());

        target = DocUtils.cloneValue(value);
        assertEquals(value,target);
        assertEquals(ArrayList.class,target.getClass());

        value = new HashMap();
        Map map = (Map)value;
        map.put(0,1);
        map.put("a",0.3f);
        
        target = DocUtils.cloneValue(value);
        assertEquals(value,target);
        assertEquals(HashMap.class,target.getClass());

        value = new HashSet();
        Set set = (Set)value;
        set.add(1);
        set.add("a");
        
        //target = new ArrayList();

        target = DocUtils.cloneValue(value);
        assertEquals(value,target);
        assertEquals(HashSet.class,target.getClass());
    }

    /**
     * Test of newInstance method, of class DocUtils.
     */
    @Test
    public void testNewInstance_Class() {
        System.out.println("DocUtils: newInstance");
        //ValueTypeImpl instance = new ValueTypeImpl(Integer.class );
        Object expResult = new Integer(0);
        Object result = DocUtils.newInstance(Integer.class);
        assertEquals(Integer.class, result.getClass());

        //instance = new ValueTypeImpl(Long.class );
        expResult = 0L;
        result = DocUtils.newInstance(Long.class);
        assertEquals(Long.class, result.getClass());
        
        //instance = new ValueTypeImpl(Float.class );
        expResult = 0.0f;
        result = DocUtils.newInstance(Float.class);
        assertEquals(Float.class, result.getClass());

        //instance = new ValueTypeImpl(Double.class );
        expResult = 0.0d;
        result = DocUtils.newInstance(Double.class);
        assertEquals(Double.class, result.getClass());
        
//        instance = new ValueTypeImpl(Boolean.class );
        expResult = false;
        result = DocUtils.newInstance(Boolean.class);
        assertEquals(Boolean.class, result.getClass());

//        instance = new ValueTypeImpl(Byte.class );
        expResult = "0";
        result = DocUtils.newInstance(Byte.class);
        assertEquals(Byte.class, result.getClass());

//        instance = new ValueTypeImpl(Character.class );
        expResult = ' ';
        result = DocUtils.newInstance(Character.class);
        assertEquals(Character.class, result.getClass());

//        instance = new ValueTypeImpl(Date.class );
        expResult = new Date();
        result = DocUtils.newInstance(Date.class);
        assertEquals(Date.class, result.getClass());
        
        //instance = new ValueTypeImpl(java.sql.Date.class );
        expResult = new java.sql.Date(0);
        result = DocUtils.newInstance(java.sql.Date.class);
        assertEquals(java.sql.Date.class, result.getClass());

//        instance = new ValueTypeImpl(java.sql.Timestamp.class );
        expResult = new java.sql.Timestamp(0);
        result = DocUtils.newInstance(java.sql.Timestamp.class);
        assertEquals(java.sql.Timestamp.class, result.getClass());
        
//        instance = new ValueTypeImpl(Time.class );
        expResult = new Time(0);
        result = DocUtils.newInstance(Time.class);
        assertEquals(Time.class, result.getClass());
        
//        instance = new ValueTypeImpl(BigInteger.class );
        expResult = new BigInteger("0");
        result = DocUtils.newInstance(BigInteger.class);
        assertEquals(BigInteger.class, result.getClass());

        expResult = new BigDecimal(0);
        result = DocUtils.newInstance(BigDecimal.class);
        assertEquals(BigDecimal.class, result.getClass());

        expResult = 0;
        result = DocUtils.newInstance(int.class);
        assertEquals(Integer.class, result.getClass());

        expResult = ' ';
        result = DocUtils.newInstance(char.class);
        assertEquals(Character.class, result.getClass());
        
        expResult = false;
        result = DocUtils.newInstance(boolean.class);
        assertEquals(Boolean.class, result.getClass());
        
        expResult = 0L;
        result = DocUtils.newInstance(long.class);
        assertEquals(Long.class, result.getClass());

//        instance = new ValueTypeImpl(byte.class );
        expResult = 0;
        result = DocUtils.newInstance(byte.class);
        assertEquals(Byte.class, result.getClass());

//        instance = new ValueTypeImpl(float.class );
        expResult = 0;
        result = DocUtils.newInstance(float.class);
        assertEquals(Float.class, result.getClass());

//        instance = new ValueTypeImpl(double.class );
        expResult = 0;
        result = DocUtils.newInstance(double.class);
        assertEquals(Double.class, result.getClass());
        
//        instance = new ValueTypeImpl(Collection.class );
        expResult = new ArrayList();
        result = DocUtils.newInstance(ArrayList.class);
        assertEquals(ArrayList.class, result.getClass());

//        instance = new ValueTypeImpl(List.class );
        expResult = new ArrayList();
        result = DocUtils.newInstance(List.class);
        assertEquals(ArrayList.class, result.getClass());

//        instance = new ValueTypeImpl(Set.class );
        expResult = new HashSet();
        result = DocUtils.newInstance(Set.class);
        assertEquals(HashSet.class, result.getClass());

//        instance = new ValueTypeImpl(Map.class );
        expResult = new HashMap();
        result = DocUtils.newInstance(Map.class);
        assertEquals(HashMap.class, result.getClass());

    }

}
