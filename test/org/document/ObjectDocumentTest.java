/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
        
        
        Object expResult;
        Object result;
        
        List family = new ArrayList();
        family.add("son");
        person.setFamily(family);
        
        expResult = new ArrayList();
        ((List)expResult).add("son");
        ArrayType atype = new ArrayType(List.class);
        int idx = 1;
        String paths[] = new String[]{"family","0"};
        DocumentSchema sc = instance.getSchema();
        
        result = instance.getFromArray(atype, family, paths, idx, sc);
        assertEquals("son",result);
        //--------------------------------------
        
        family.add("daughter");
        paths = new String[]{"family","1"};
        
        result = instance.getFromArray(atype, family, paths, idx, sc);
        assertEquals("daughter",result);
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
        

        idx = 1;
        paths = new String[]{"listOfList","0"};
        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals(expList1,result);
        
        paths = new String[]{"listOfList","0","0"};
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals("list1",result);

        list1.add(123);
        // Now listOfList.size=1:
        // elem0 := ["list1",123]
        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals("list1",result);
        
        paths = new String[]{"listOfList","0","1"};        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
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
        
        
        paths = new String[]{"listOfList","0"};        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals(expList1,result);

        paths = new String[]{"listOfList","1"};        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals(expList2,result);
        
        // Now listOfList.size=3:
        // elem0 := ["list1",123]
        // elem1 := [321,"Bill"]
        // elem2 := 99
        
        listOfList.add(99);
        expListOfList.add(99);
        
        paths = new String[]{"listOfList","2"};        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals(99,result);
        

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
        
        paths = new String[]{"listOfList","1"};        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals(expList2,result);
        
        paths = new String[]{"listOfList","1","2"};        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals(expList2_1,result);
        
        
        paths = new String[]{"listOfList","1","2","0"};        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals("list2_1",result);
        
        //
        // EmbeddedType as List element
        //
        Address addr1 = new Address("Michigan","Detroit", "Witherall",124, 97);
        listOfList.add(addr1);
        Address expaddr1 = new Address("Michigan","Detroit", "Witherall",124, 97);        
        expListOfList.add(expaddr1);
        
        paths = new String[]{"listOfList","3"};        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals(expaddr1,result);
        
        
        paths = new String[]{"listOfList","3","state"};        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals("Michigan",result);
        
        paths = new String[]{"listOfList","3","house"};        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals(124,result);
        

        List info1 = new ArrayList();
        info1.add("nice");
        addr1.setInfo(info1);
        
        List expinfo1 = new ArrayList();
        expinfo1.add("nice");
        expaddr1.setInfo(expinfo1);

        paths = new String[]{"listOfList","3","info"};        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals(expinfo1,result);
        
        paths = new String[]{"listOfList","3","info","0"};        
        result = instance.getFromArray(atype, listOfList, paths, idx, sc);
        assertEquals("nice",result);
        
        
    }
    /**
     * Test of getFromComponentType method, of class ObjectDocument.
     */
    @Test
    public void testGetFromComponentType() {
        System.out.println("ObjectDocument: getFromComponentType(ArrayType,Object,String[], int,DocumentSchema)");
        ObjectWithArray owa = new ObjectWithArray();
        String[] stringArray = new String[] {"str1","str2","str3"};
        String[][] stringStringArray = new String[][] {
            {"[0,0]","[0,1]","[0,2]"},
            {"[1,0]","[1,1]","[1,2]"}};        
        
        owa.setStringArray(stringArray);
        owa.setStringStringArray(stringStringArray);
        //
        // expected
        //
        ObjectWithArray expowa = new ObjectWithArray();
        String[] expstringArray = new String[] {"str1","str2","str3"};
        String[][] expstringStringArray = new String[][] {
            {"[0,0]","[0,1]","[0,2]"},
            {"[1,0]","[1,1]","[1,2]"}};        

        expowa.setStringArray(expstringArray);
        expowa.setStringStringArray(expstringStringArray);        
        //
        // Array of type String[]
        //
        ObjectDocument instance = new ObjectDocument(owa);
        DocumentSchema sc = instance.getSchema();
        ComponentType atype = new ComponentType(stringArray.getClass());
        String[] paths = new String[] {"owa","stringArray","0"};
        
        int idx = 2;
        Object result = instance.getFromComponentType(atype, stringArray, paths, idx, sc);
        assertEquals("str1",result);
        
        //
        // Two dimentional array of type String[][]
        //
        atype = new ComponentType(stringStringArray.getClass());
        paths = new String[] {"owa","stringStringArray","0"};
        
        idx = 2;
        result = instance.getFromComponentType(atype, stringStringArray, paths, idx, sc);
        assertArrayEquals(expstringStringArray[0],(String[])result);        

        idx = 3;
        atype = new ComponentType(String[].class);
        paths = new String[] {"owa","stringStringArray","0","0"};
        
        //Object obj = new String
        result = instance.getFromComponentType(atype, stringStringArray[0], paths, idx, sc);
        assertEquals("[0,0]",result);        
        paths = new String[] {"owa","stringStringArray","1","1"};
        
        //Object obj = new String
        result = instance.getFromComponentType(atype, stringStringArray[1], paths, idx, sc);
        assertEquals("[1,1]",result);        
       //
       // Exceptions
       //
       try {
           instance.get("stringStringArray/o");           
           fail("Array index must be of integer type");
       } catch(NumberFormatException e) {
            System.out.println("Array index must be of integer type");           
       }
       try {
           instance.get("stringStringArray/56");           
           fail("Index out of bounds (value=56)");
       } catch(IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds.");           
       }
       
        
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
    }
    /**
     * Test of checkValue method, of class ObjectDocument.
     */
    @Test
    public void testCheckValue() {
        System.out.println("ObjectDocument: checkValue(Object,String)");
        ObjectDocument instance = new ObjectDocument(new HashMap());
        try {
            instance.checkValue(null, "");
            fail("The first parameter cannot be null");
        } catch(NullPointerException e) {
            System.out.println("The first parameter cannot be null");
        }
        try {
            instance.checkValue("string value", "");
            fail("The first parameter cannot be of ValueType");
        } catch(IllegalArgumentException e) {
            System.out.println("The first parameter cannot be of ValueType");
        }
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
        System.out.println("ObjectDocument: get(Object)");
        Person person = new Person("Bill","Smith", new Date(), 1);
        Order order = new Order(125L, 12345);
        person.setOrder(order);
        
        ObjectDocument instance = new ObjectDocument(person);
        
        
        Object expResult;
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
        
        //==============================
        // ComponentType
        //==============================
        
        ObjectWithArray owa = new ObjectWithArray();
        addr1 = new Address("Michigan","Detroit", "Witherall",124, 97);
        Address addr2 = new Address("Illinois", "Chicago","102ND",12, 7);        
        Address[] addressArray = new Address[] {addr1,addr2};
        
        
        String[] stringArray = new String[] {"str1","str2","str3"};
        String[][] stringStringArray = new String[][] {
            {"[0,0]","[0,1]","[0,2]"},
            {"[1,0]","[1,1]","[1,2]"}};        
        
        int[] intArray = new int[] {11,22,33};
        List l1 = new ArrayList();
        l1.add(1);
        l1.add("l1");
                
        List l2 = new ArrayList();
        l2.add(2);
        l2.add("l2");
        
        List[] listArray = new List[] {l1,l2};
        
        
        List list = new ArrayList();
        int[][] intIntArray = new int[][] {{0,1,2},{10,11,12}};
        list.add(intIntArray);
        
        owa.setAddressArray(addressArray);        
        owa.setIntArray(intArray);
        owa.setListArray(listArray);
        owa.setStringArray(stringArray);
        owa.setStringStringArray(stringStringArray);
        owa.setList(list);
        //
        // expected
        //
        ObjectWithArray expowa = new ObjectWithArray();
        expaddr1 = new Address("Michigan","Detroit", "Witherall",124, 97);
        Address expaddr2 = new Address("Illinois", "Chicago","102ND",12, 7);        
        Address[] expaddressArray = new Address[] {expaddr1,expaddr2};
        
        String[] expstringArray = new String[] {"str1","str2","str3"};
        String[][] expstringStringArray = new String[][] {
            {"[0,0]","[0,1]","[0,2]"},
            {"[1,0]","[1,1]","[1,2]"}};        

        int[] expintArray = new int[] {11,22,33};
        List expl1 = new ArrayList();
        expl1.add(1);
        expl1.add("l1");
                
        List expl2 = new ArrayList();
        expl2.add(2);
        expl2.add("l2");
        
        List[] explistArray = new List[] {expl1,expl2};

        int[][] expintIntArray = new int[][] {{0,1,2},{10,11,12}};
        
        List explist = new ArrayList();
        explist.add(expintIntArray);
        
        expowa.setAddressArray(expaddressArray);        
        expowa.setIntArray(expintArray);
        expowa.setListArray(explistArray);
        expowa.setStringArray(expstringArray);
        expowa.setStringStringArray(expstringStringArray);        
        expowa.setList(explist);
        
        instance = new ObjectDocument(owa);
        result = instance.get("stringArray");
        assertEquals(String[].class,result.getClass());
        assertArrayEquals(expstringArray,(String[])result);
        result = instance.get("stringArray/1");
        assertEquals("str2",result);
        
        result = instance.get("intArray");
        assertEquals(int[].class,result.getClass());
        assertArrayEquals(expintArray,(int[])result);
        result = instance.get("intArray/1");
        assertEquals(22,result);

        result = instance.get("listArray");
        assertEquals(List[].class,result.getClass());
        assertArrayEquals(explistArray,(List[])result);
        result = instance.get("listArray/1");
        assertEquals(expl2,result);
        
        result = instance.get("listArray/1/0");
        assertEquals(2,result);
        result = instance.get("listArray/1/1");
        assertEquals("l2",result);
        
        result = instance.get("addressArray/1/city");
        assertEquals("Chicago",result);
        
        result = instance.get("stringStringArray/0");
        assertEquals(String[].class,result.getClass());
        assertArrayEquals(expstringStringArray[0],(String[])result);

        result = instance.get("stringStringArray/0/1");
        assertEquals("[0,1]",result);
        result = instance.get("stringStringArray/1/1");
        assertEquals("[1,1]",result);

        result = instance.get("list");
        assertTrue(result instanceof List);
        assertEquals(1,((List)result).size());
        assertTrue(((List)result).get(0) instanceof int[][]);

        
        result = instance.get("list/0");
        assertArrayEquals(expintIntArray,(int[][])result);
        result = instance.get("list/0/0");
        assertArrayEquals(expintIntArray[0],(int[])result);
        result = instance.get("list/0/1");
        assertArrayEquals(expintIntArray[1],(int[])result);
        
        result = instance.get("list/0/1/2");
        assertEquals(12,result);
        
       //
       // Exceptions
       //
       try {
           instance.get("stringStringArray/o");           
           fail("Array index must be of integer type");
       } catch(NumberFormatException e) {
            System.out.println("Array index must be of integer type");           
       }
       try {
           instance.get("stringStringArray/56");           
           fail("Index out of bounds (value=56)");
       } catch(IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds.");           
       }
        
       //
       // tail Fields
       //
       Field tailField1 = new Field("tail1");
       tailField1.setTail(true);
       instance.getSchema().getFields().add(tailField1);
       result = instance.get("tail1");
       assertNull(result);
       instance.tail.put("tail1","It is tale1 field value");
       result = instance.get("tail1");
       assertEquals("It is tale1 field value",result);
       DocumentSchema addrSchema = DocUtils.createSchema(Address.class);
       EmbeddedType etype = new EmbeddedType(addrSchema);
       Field tailField2 = new Field("addrTail");
       tailField2.setTail(true);
       tailField2.add(etype);
       
       Address addrTail = new Address("Michigan","Detroit", "Witherall",124, 97);
       instance.getSchema().getFields().add(tailField2);
       instance.tail.put("addrTail",addrTail);
       
       Address expaddrTail = new Address("Michigan","Detroit", "Witherall",124, 97);
       result = instance.get("addrTail");
       assertEquals(expaddrTail,result);

       result = instance.get("addrTail/city");
       assertEquals("Detroit",result);

       result = instance.get("tail1");
       assertEquals("It is tale1 field value",result);
       
       
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
