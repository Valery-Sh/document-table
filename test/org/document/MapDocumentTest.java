/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.Map;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Valery
 */
public class MapDocumentTest {
    
    public MapDocumentTest() {
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
     * Test of getPrimaryId method, of class MapDocument.
     */
    @Test
    public void testGetPrimaryId() {
/*        System.out.println("getPrimaryId");
        MapDocument instance = new MapDocument();
        Object expResult = null;
        Object result = instance.getPrimaryId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of get method, of class MapDocument.
     */
    @Test
    public void testGet_Object() {
/*        System.out.println("get");
        Object key = null;
        MapDocument instance = new MapDocument();
        Object expResult = null;
        Object result = instance.get(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
*/
    }

    /**
     * Test of getKeyPaths method, of class MapDocument.
     */
    @Test
    public void testGetKeyPaths() {
        System.out.println("MapDocument: getKeyPaths");
        Object key = "name1";
        char dlm = '/';
        MapDocument instance = new MapDocument();
        String[] expResult = new String[] {"name1"};
        String[] result = instance.getKeyPaths(key, dlm);
        assertArrayEquals(expResult, result);
        
        key = " name1 / name2 ";
        expResult = new String[] {"name1","name2"};
        result = instance.getKeyPaths(key, dlm);
        assertArrayEquals(expResult, result);
        assertEquals(2,result.length);
        
    }

    /**
     * Test of get method, of class MapDocument.
     */
    @Test
    public void testGet_Object_DocumentSchema() {
/*        System.out.println("get");
        Object key = null;
        DocumentSchema schema = null;
        MapDocument instance = new MapDocument();
        Object expResult = null;
        Object result = instance.get(key, schema);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
*/
    }

    /**
     * Test of put method, of class MapDocument.
     */
    @Test
    public void testPut() {
/*        System.out.println("put");
        Object key = null;
        Object value = null;
        MapDocument instance = new MapDocument();
        instance.put(key, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of newDataInstance method, of class MapDocument.
     */
    @Test
    public void testNewDataInstance() {
/*        System.out.println("newDataInstance");
        MapDocument instance = new MapDocument();
        Object expResult = null;
        Object result = instance.newDataInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
*/
    }

    /**
     * Test of cloneData method, of class MapDocument.
     */
    @Test
    public void testCloneData() {
/*        System.out.println("cloneData");
        MapDocument instance = new MapDocument();
        Map expResult = null;
        Map result = instance.cloneData();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
*/
    }
}
