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
public class ComponentTypeTest {
    
    public ComponentTypeTest() {
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
     * Test of create method, of class ComponentType.
     */
    @Test
    public void testCreate() {
        System.out.println("ComponentType: create(Class)");
        Class type = String[][].class;
        ComponentType instance = new ComponentType(type);
        assertNotNull(instance);
        
        assertEquals(1,instance.getSupportedTypes().size());
        assertEquals(ComponentType.class,instance.getSupportedTypes().get(0).getClass());
        ComponentType lev1 = (ComponentType)instance.getSupportedTypes().get(0);
        assertEquals(1,lev1.getSupportedTypes().size());
        assertEquals(ValueType.class,lev1.getSupportedTypes().get(0).getClass());
        ValueType vt = (ValueType)lev1.supportedTypes.get(0);
        assertEquals(String.class,vt.getJavaType());
        
    }
    
    @Test
    public void testGetBaseType() {
        System.out.println("ComponentType: getBaseType()");
        Class type = String[][].class;
        ComponentType instance = new ComponentType(type);
        assertEquals(String.class,instance.getBaseType());
        
        type = Integer[].class;
        instance = new ComponentType(type);
        assertEquals(Integer.class,instance.getBaseType());
        
        type = Person[].class;
        instance = new ComponentType(type);
        assertEquals(Person.class,instance.getBaseType());
        //
        // Tests if contains a single supported class
        // and replacing supported class
        //
/*        instance = new ComponentType();
        assertNotNull(instance.getBaseType());
        assertEquals(1,instance.supportedTypes.size());
        instance.addSupported(new ValueType(Integer.class));
        assertEquals(1,instance.supportedTypes.size());
        assertEquals(Integer.class,instance.supportedTypes.get(0).getJavaType());
        
        instance = new ComponentType();
        ComponentType ct = new ComponentType();
        instance.addSupported(ct);
        assertNotNull(instance.getBaseType());
        assertEquals(1,instance.supportedTypes.size());
        assertEquals(Object.class,instance.getBaseType());
 */
    }
    
    @Test
    public void testGetDimensionSize() {
        Class type = String[].class;
        ComponentType instance = new ComponentType(type);
        assertEquals(1,instance.getDimentionSize());
        
        type = String[][].class;
        instance = new ComponentType(type);
        assertEquals(2,instance.getDimentionSize());
        
/*        instance = new ComponentType();
        assertEquals(1,instance.getDimentionSize());        
        ComponentType ct = new ComponentType();
        instance.addSupported(ct);
        assertEquals(2,instance.getDimentionSize());
*/        
    }
}
