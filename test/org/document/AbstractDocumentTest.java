/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.*;

/**
 *
 * @author Valery
 */
public class AbstractDocumentTest {
    
    public AbstractDocumentTest() {
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
     * Test of get method, of class AbstractDocument.
     */
    @Test
    public void testGet() {
        System.out.println("AbstractDocument: get");
        Object key = "name";
        AbstractDocument<Map> instance = new AbstractDocumentMock<Map>();
        instance.put(key, "Bill Gates");
        Object expResult = "Bill Gates";
        Object result = instance.get(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of put method, of class AbstractDocument.
     */
    @Test
    public void testPut() {
        System.out.println("AbstractDocument: put");
        Object key = "name";
        AbstractDocument instance = new AbstractDocumentMock();
        instance.put(key, "Bill Gates");
        Object expResult = "Bill Gates";
        Object result = instance.get(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSchema method, of class AbstractDocument.
     */
    @Test
    public void testGetSchema() {
        System.out.println("getSchema");
        AbstractDocument instance = new AbstractDocumentMock();
        DocumentSchema result = instance.getSchema();
        assertNull(result);
    }

    /**
     * Test of newDataInstance method, of class AbstractDocument.
     */
    @Test
    public void testNewDataInstance() {
        System.out.println("AbstractDocument: newDataInstance");
        AbstractDocument instance = new AbstractDocumentMock();
        
        Class expResult = HashMap.class;
        Object result = instance.newDataInstance();
        assertEquals(expResult, result.getClass());
    }

    /**
     * Test of cloneData method, of class AbstractDocument.
     */
    @Test
    public void testCloneData() {
        System.out.println("AbstractDocument: cloneData()");
        Object key = "name";
        AbstractDocument<Map> instance = new AbstractDocumentMock<Map>();
        instance.put(key, "Bill Gates");
        Map expResult = new HashMap();
        expResult.put(key, "Bill Gates");
        Map result = instance.cloneData();
        assertEquals(expResult, result);
        
        
        
    }
}
