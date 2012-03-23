/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.Date;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Valery
 */
public class ObjectDocumentTest {
    
    public ObjectDocumentTest() {
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
     * Test of getFromArray method, of class ObjectDocument.
     */
    @Test
    public void testGetFromArray() {
/*        System.out.println("getFromArray");
        Object obj = null;
        String[] paths = null;
        int idx = 0;
        DocumentSchema schema = null;
        ObjectDocument instance = null;
        Object expResult = null;
        Object result = instance.getFromArray(obj, paths, idx, schema);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
 */
    }

    /**
     * Test of getFromEmbedded method, of class ObjectDocument.
     */
    @Test
    public void testGetFromEmbedded() {
        System.out.println("getFromEmbedded");
        Object obj = null;
        String[] paths = null;
        int idx = 0;
        DocumentSchema sc = null;
        ObjectDocument instance = null;
        Object expResult = null;
        //Object result = instance.getFromEmbedded(obj, paths, idx, sc);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getSupportedType method, of class ObjectDocument.
     */
    @Test
    public void testGetSupportedType() {
        System.out.println("getSupportedType");
        Field f = null;
        ObjectDocument instance = null;
        SchemaType expResult = null;
        //SchemaType result = instance.getSupportedType(f);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class ObjectDocument.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        Object key = null;
        ObjectDocument instance = null;
        Object expResult = null;
        //Object result = instance.get(key);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of split method, of class ObjectDocument.
     */
    @Test
    public void testSplit() {
        System.out.println("split");
        String key = "";
        char dlm = ' ';
        ObjectDocument instance = null;
        String[] expResult = null;
        //String[] result = instance.split(key, dlm);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of put method, of class ObjectDocument.
     */
    @Test
    public void testPut() {
/*        System.out.println("put");
        Object key = null;
        Object value = null;
        ObjectDocument instance = null;
        instance.put(key, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of getSchema method, of class ObjectDocument.
     */
    @Test
    public void testGetSchema() {
        System.out.println("ObjectDocument: getSchema()");
        Person person = new Person("Bill","Smith", new Date(), 1);
        ObjectDocument instance = new ObjectDocument(person);
        DocumentSchema expResult = DocUtils.createSchema(Person.class);
        DocumentSchema result = instance.getSchema();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDataObject method, of class ObjectDocument.
     */
    @Test
    public void testGetDataObject() {
        System.out.println("ObjectDocument: getDataObject()");
        Person person = new Person("Bill","Smith", new Date(), 1);
        ObjectDocument instance = new ObjectDocument(person);
        Object expResult = person;
        Object result = instance.getDataObject();
        assertEquals(expResult, result);
    }

    /**
     * Test of newDataInstance method, of class ObjectDocument.
     */
    @Test
    public void testNewDataInstance() {
/*        System.out.println("newDataInstance");
        ObjectDocument instance = null;
        Object expResult = null;
        Object result = instance.newDataInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

    /**
     * Test of cloneData method, of class ObjectDocument.
     */
    @Test
    public void testCloneData() {
/*        System.out.println("cloneData");
        ObjectDocument instance = null;
        Object expResult = null;
        Object result = instance.cloneData();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }
}
