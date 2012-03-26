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
     * Test of create method, of class ArrayType.
     */
    @Test
    public void testCreate() {
        System.out.println("ComponentType: create(Class)");
        Class type = String[][].class;
        ArrayType instance = new ArrayType(type);
        assertNotNull(instance);
        
        assertEquals(1,instance.getSupportedTypes().size());
        assertEquals(ArrayType.class,instance.getSupportedTypes().get(0).getClass());
        ArrayType lev1 = (ArrayType)instance.getSupportedTypes().get(0);
        assertEquals(1,lev1.getSupportedTypes().size());
        assertEquals(ValueType.class,lev1.getSupportedTypes().get(0).getClass());
        ValueType vt = (ValueType)lev1.supportedTypes.get(0);
        assertEquals(String.class,vt.getJavaType());
        
    }
    
    @Test
    public void testGetBaseType() {
        System.out.println("ComponentType: getBaseType()");
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
        // Tests if contains a single supported class
        // and replacing supported class
        //
/*        instance = new ArrayType();
        assertNotNull(instance.getBaseType());
        assertEquals(1,instance.supportedTypes.size());
        instance.addSupported(new ValueType(Integer.class));
        assertEquals(1,instance.supportedTypes.size());
        assertEquals(Integer.class,instance.supportedTypes.get(0).getJavaType());
        
        instance = new ArrayType();
        ArrayType ct = new ArrayType();
        instance.addSupported(ct);
        assertNotNull(instance.getBaseType());
        assertEquals(1,instance.supportedTypes.size());
        assertEquals(Object.class,instance.getBaseType());
 */
    }
    
    @Test
    public void testGetDimensionSize() {
        Class type = String[].class;
        ArrayType instance = new ArrayType(type);
        assertEquals(1,instance.getDimentionSize());
        
        type = String[][].class;
        instance = new ArrayType(type);
        assertEquals(2,instance.getDimentionSize());
        
/*        instance = new ArrayType();
        assertEquals(1,instance.getDimentionSize());        
        ArrayType ct = new ArrayType();
        instance.addSupported(ct);
        assertEquals(2,instance.getDimentionSize());
*/        
    }
}
