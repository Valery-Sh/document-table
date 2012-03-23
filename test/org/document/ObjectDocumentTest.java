/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        System.out.println("ObjectDocument: getFromArray(ArrayType,Object,String[], int,DocumentSchema)");
        Person person = new Person("Bill","Smith", new Date(), 1);
        Order order = new Order(125L, 12345);
        person.setOrder(order);
        
        ObjectDocument instance = new ObjectDocument(person);
        
        Object obj = instance.getDataObject();
        String[] paths = new String[] {"firstName"};
        int idx = 0;
        DocumentSchema sc = instance.getSchema();
        Object expResult = null;
        Object result = instance.get("family");
        assertNull(result);
        List family = new ArrayList();
        family.add("son");
        person.setFamily(family);
        expResult = new ArrayList();
        result = instance.get("family");
        ((List)expResult).add("son");
        assertEquals(expResult,result);
        
        result = instance.get("family/0");
        assertEquals("son",result);
        
        family.add("daughter");
        
        result = instance.get("family/0");
        assertEquals("son",result);
        
        result = instance.get("family/1");
        assertEquals("daughter",result);
        
        
        result = instance.get("family");
        ((List)expResult).add("daughter");        
        assertEquals(expResult,result);
        //
        // array of arrays
        //
        List listOfList = new ArrayList();
        List list1 = new ArrayList();
        String s1 = "list1";
        list1.add(s1);
        listOfList.add(list1);
        person.setListOfList(listOfList);
        // Now listOfList.size=1:
        // elem0 := ["list1]
        List expListOfList = new ArrayList();
        List expList1 = new ArrayList();
        expList1.add("list1");
        expListOfList.add(expList1);
        
        
        result = instance.get("listOfList");
        assertEquals(expListOfList,result);
        
        result = instance.get("listOfList/0");
        assertEquals(expList1,result);
        
        result = instance.get("listOfList/0/0");
        assertEquals("list1",result);

        list1.add(123);
        // Now listOfList.size=1:
        // elem0 := ["list1",123]
        
        result = instance.get("listOfList/0/0");
        assertEquals("list1",result);
        
        result = instance.get("listOfList/0/1");
        assertEquals(123,result);
        
        expList1.add(123);
        List list2 = new ArrayList();
        list2.add(321);
        list2.add("Bill");
        listOfList.add(list2);
        // Now listOfList.size=2:
        // elem0 := ["list1",123]
        // elem1 := [321,"Bill"]
        
        List expList2 = new ArrayList();
        expList2.add(321);
        expList2.add("Bill");
        
        expListOfList.add(expList2);
        
        
        result = instance.get("listOfList");
        assertEquals(expListOfList,result);
        
        result = instance.get("listOfList/0");
        assertEquals(expList1,result);
        result = instance.get("listOfList/1");
        assertEquals(expList2,result);
        // Now listOfList.size=3:
        // elem0 := ["list1",123]
        // elem1 := [321,"Bill"]
        // elem2 := 99
        
        listOfList.add(99);
        expListOfList.add(99);
        result = instance.get("listOfList/2");        
        assertEquals(99,result);
        
        result = instance.get("listOfList");        
        assertEquals(expListOfList,result);

        List list2_1 = new ArrayList();
        list2_1.add("list2_1");
        list2.add(list2_1);
        // Now listOfList.size=3:
        // elem0 := ["list1",123]
        // elem1 := [321,"Bill",["list2_1"]]
        // elem2 := 99
        List expList2_1 = new ArrayList();
        expList2_1.add("list2_1");
        expList2.add(expList2_1);
        // Now listOfList.size=4:
        // elem0 := ["list1",123]
        // elem1 := [321,"Bill",["list2_1"]]
        // elem2 := 99
        // elem2 := addr1
        
        result = instance.get("listOfList");        
        assertEquals(expListOfList,result);
        
        result = instance.get("listOfList/1");        
        assertEquals(expList2,result);
        result = instance.get("listOfList/1/2");        
        assertEquals(expList2_1,result);
        
        
        result = instance.get("listOfList/1/2/0");        
        assertEquals("list2_1",result);
        
        //
        // EmbeddedType as List element
        //
        Address addr1 = new Address("Michigan","Detroit", "Witherall",124, 97);
        listOfList.add(addr1);
        Address expaddr1 = new Address("Michigan","Detroit", "Witherall",124, 97);        
        expListOfList.add(expaddr1);
        result = instance.get("listOfList");
        assertEquals(expListOfList,result);
        
        result = instance.get("listOfList/3");
        assertEquals(expaddr1,result);
        result = instance.get("listOfList/3/state");
        assertEquals("Michigan",result);
        result = instance.get("listOfList/3/house");
        assertEquals(124,result);
        
        List info1 = new ArrayList();
        info1.add("nice");
        addr1.setInfo(info1);
        
        List expinfo1 = new ArrayList();
        expinfo1.add("nice");
        expaddr1.setInfo(expinfo1);
        result = instance.get("listOfList");
        assertEquals(expListOfList,result);
        result = instance.get("listOfList/3/info");
        assertEquals(expinfo1,result);
        result = instance.get("listOfList/3/info/0");
        assertEquals("nice",result);
        
        
    }

    /**
     * Test of getFromEmbedded method, of class ObjectDocument.
     */
    @Test
    public void testGetFromEmbedded() {
        System.out.println("ObjectDocument: getFromEmbedded");
        Person person = new Person("Bill","Smith", new Date(), 1);
        Order order = new Order(125L, 12345);
        person.setOrder(order);
        
        ObjectDocument instance = new ObjectDocument(person);
        
        Object obj = instance.getDataObject();
        String[] paths = new String[] {"firstName"};
        int idx = 0;
        DocumentSchema sc = instance.getSchema();

        
        Object expResult = "Bill";
        Object result = instance.getFromEmbedded(obj, paths, idx, sc);
        assertEquals(expResult, result);
        //
        // fail when field doesn't exist
        //
        try {
            String[] paths1 = new String[] {"frstName"};
            instance.getFromEmbedded(obj, paths1, idx, sc);
            fail("A schema doesn't contain a field for key = frstName");
        } catch(NullPointerException e) {
            System.out.println("fail when field doesn't exist");            
        }
        //
        // fail when null value for a not last element in the path
        //
        try {
            Person person1 = new Person(null,"Smith", new Date(), 1);
            String[] paths1 = new String[] {"firstName","lastName"};
            ObjectDocument instance1 = new ObjectDocument(person1);
            instance1.getFromEmbedded(instance1.getDataObject(), paths1, idx, sc);
            fail("Null value for key path");
        } catch(NullPointerException e) {
            System.out.println("fail when Null value for key path");            
        }
        //
        // fail when there is a path for a ValueType
        //
        try {
            Person person1 = new Person("Bill","Smith", new Date(), 1);
            String[] paths1 = new String[] {"firstName","lastName"};
            ObjectDocument instance1 = new ObjectDocument(person1);
            instance1.getFromEmbedded(instance1.getDataObject(), paths1, idx, sc);
            fail("Path requires ValueType");
        } catch(IllegalArgumentException e) {
            System.out.println("fail when Path requires ValueType");            
        }
        
        paths = new String[] {"order","orderNum"};
        idx = 0;
        
        expResult = 125L;
        result = instance.getFromEmbedded(obj, paths, idx, sc);
        assertEquals(expResult, result);
        
        
        
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
        char dlm = '/';
        Person person = new Person("Bill","Smith", new Date(), 1);
        ObjectDocument instance = new ObjectDocument(person);
        String[] expResult = new String[] {""};
        String[] result = instance.split(key, dlm);
        assertArrayEquals(expResult, result);
        
        key = "name1 /name2/ name3";
        expResult = new String[] {"name1","name2","name3"};
        result = instance.split(key, dlm);
        assertArrayEquals(expResult, result);

        key = "/name1 /name2/ name3";
        expResult = new String[] {"name1","name2","name3"};
        result = instance.split(key, dlm);
        assertArrayEquals(expResult, result);
        

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
