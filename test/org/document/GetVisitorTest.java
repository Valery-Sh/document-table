/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.document.GetVisitor.VisitorInfo;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Valery
 */
public class GetVisitorTest {
    
    public GetVisitorTest() {
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
     * Test of split method, of class GetVisitor.
     */
    @Test
    public void testSplit() {
        System.out.println("split");
        String key = "";
        char dlm = ' ';
        GetVisitor instance = null;
        String[] expResult = null;
        //String[] result = document.split(key, dlm);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of visitDocument method, of class GetVisitor.
     */
    @Test
    public void testVisitDocument() {
        System.out.println("visitDocument");
        System.out.println("ObjectDocument: get(Object)");
        Person person = new Person("Bill","Smith", new Date(), 1);
        Order order = new Order(125L, 12345);
        person.setOrder(order);
        
        Document document = new ObjectDocument(person);
        
        Object expResult;
        Object result = document.get("family");
        
        
        GetVisitor instance = new GetVisitor(document);
        instance.visitDocument("firstName");
        assertEquals(1,instance.infoList.size());
        VisitorInfo vinfo = instance.infoList.get(instance.infoList.size()-1);
        assertEquals("Bill",vinfo.getResult());

        //instance = new GetVisitor(document);
        instance.visitDocument("order/orderNum");
        assertEquals(125L,instance.getResult());
        
        
        //
        // fail when field doesn't exist
        //
        try {
            //instance = new GetVisitor(document);
            instance.visitDocument("frstName");            
            if ( instance.getException() != null ) {
                throw (NullPointerException)instance.getException();
            }
            fail("A schema doesn't contain a field for key = frstName");
        } catch(NullPointerException e) {
            System.out.println("fail when field doesn't exist");            
        }
        //
        // fail when null value for a not last element in the path
        //
        try {
            Person person1 = new Person(null,"Smith", new Date(), 1);
            ObjectDocument doc = new ObjectDocument(person1);
            
            GetVisitor instance1 = new GetVisitor(doc);
            instance1.visitDocument("firstName/lastName");            
            if ( instance1.getException() != null ) {
                throw (NullPointerException)instance1.getException();
            }
            fail("Null value for key path");
        } catch(NullPointerException e) {
            System.out.println("fail when Null value for key path");            
        }
        //
        // fail when there is a path for a ValueType
        //
        try {
            Person person1 = new Person("Bill","Smith", new Date(), 1);
            ObjectDocument doc = new ObjectDocument(person1);
            GetVisitor instance1 = new GetVisitor(doc);
            instance1.visitDocument("firstName/lastName");            
            if ( instance1.getException() != null ) {
                throw (IllegalArgumentException)instance1.getException();
            }
            fail("Path requires ValueType");
        } catch(IllegalArgumentException e) {
            System.out.println("fail when Path requires ValueType");            
        }

        
        expResult = 125L;
        //instance = new GetVisitor("order/orderNum");
        instance.visitDocument("order/orderNum");
        
        result = instance.getResult();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of continueVisit method, of class GetVisitor.
     */
    @Test
    public void testContinueVisit() {
        System.out.println("DocumentVisitorTest: continueVisit(String ...)");
        Person person = new Person("Bill","Smith", new Date(), 1);
        Order order = new Order(125L, 12345);
        person.setOrder(order);
        Order exporder = new Order(125L, 12345);
        
        Document document = new ObjectDocument(person);
        
        Object expResult;
        Object result = document.get("family");
        
        
        GetVisitor instance = new GetVisitor(document);
        instance.visitDocument("firstName");
        assertEquals(1,instance.infoList.size());
        VisitorInfo vinfo = instance.infoList.get(instance.infoList.size()-1);
        assertEquals("Bill",vinfo.getResult());

        
        instance.visitDocument("order/orderNum");
        assertEquals(125L,instance.getResult());

        instance.visitDocument("order");
        assertEquals(exporder,instance.getResult());
        instance.continueVisit("orderNum");
        assertEquals(125L,instance.getResult());
        
        //
        // fail when field doesn't exist
        //
        try {
            instance.visitDocument("frstName");            
            if ( instance.getException() != null ) {
                throw (NullPointerException)instance.getException();
            }
            fail("A schema doesn't contain a field for key = frstName");
        } catch(NullPointerException e) {
            System.out.println("fail when field doesn't exist");            
        }
        //
        // fail when null value for a not last element in the path
        //
        try {
            Person person1 = new Person(null,"Smith", new Date(), 1);
            ObjectDocument doc = new ObjectDocument(person1);
            
            GetVisitor instance1 = new GetVisitor(doc);
            instance1.visitDocument("firstName/lastName");            
            if ( instance1.getException() != null ) {
                throw (NullPointerException)instance1.getException();
            }
            fail("Null value for key path");
        } catch(NullPointerException e) {
            System.out.println("fail when Null value for key path");            
        }
        //
        // fail when there is a path for a ValueType
        //
        try {
            Person person1 = new Person("Bill","Smith", new Date(), 1);
            ObjectDocument doc = new ObjectDocument(person1);
            GetVisitor instance1 = new GetVisitor(doc);
            instance1.visitDocument("firstName/lastName");            
            if ( instance1.getException() != null ) {
                throw (IllegalArgumentException)instance1.getException();
            }
            fail("Path requires ValueType");
        } catch(IllegalArgumentException e) {
            System.out.println("fail when Path requires ValueType");            
        }

        
        expResult = 125L;
        instance.visitDocument("order/orderNum");
        
        result = instance.getResult();
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test of visitEmbedded method, of class GetVisitor.
     */
    @Test
    public void testVisitEmbedded() {
        System.out.println("DocumentVisitorTest: visitEmbedded");
/*        EmbeddedType embeddedType = null;
        Object sourceObject = null;
        GetVisitor document = null;
        document.visitEmbedded(embeddedType, sourceObject);
  */
    }

    /**
     * Test of visitArray method, of class GetVisitor.
     */
    @Test
    public void testVisitArray() {
        System.out.println("visitArray");
    /*    ArrayType arrayType = null;
        Object sourceObject = null;
        GetVisitor document = null;
        document.visitArray(arrayType, sourceObject);
*/    
    }

    /**
     * Test of visitComponent method, of class GetVisitor.
     */
    @Test
    public void testVisitComponent() {
        System.out.println("DocumentVisitor: visitComponent");
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
        ObjectDocument document = new ObjectDocument(owa);
        GetVisitor instance = new GetVisitor(document);
        instance.visitDocument("stringArray","0");
        Object result = instance.getResult();
        assertEquals("str1",result);
        
        //
        // Two dimentional array of type String[][]
        //
        
        
        instance.visitDocument("stringStringArray","0");
        result = instance.getResult();
        assertArrayEquals(expstringStringArray[0],(String[])result);        

        instance.visitDocument("stringStringArray","0","0");
        result = instance.getResult();
        assertEquals("[0,0]",result);      
        
        instance.visitDocument("stringStringArray","1","1");
        result = instance.getResult();
        assertEquals("[1,1]",result);        
       //
       // Exceptions
       //
       try {
           document.get("stringStringArray/o");           
           fail("Array index must be of integer type");
       } catch(NumberFormatException e) {
            System.out.println("Array index must be of integer type");           
       }
       try {
           document.get("stringStringArray/56");           
           fail("Index out of bounds (value=56)");
       } catch(IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds.");           
       }
  
    }
    
    /**
     * Test of visitPutArray method, of class GetVisitor.
     */
    @Test
    public void testVisitPutArray() {
        System.out.println("DocumentVisitor: visitPutArray");
        SchemaType atype = new ComponentType(String[].class);
        Object sourceObject = new String[] {"[0]","[1]"};
        Person person = new Person("Bill","Smith", new Date(), 1);
        
        Document doc = new ObjectDocument(person);
        GetVisitor visitor = new GetVisitor(doc);
        visitor.setPaths("1");
        visitor.visitPutArray(atype, sourceObject, "[11]");
        Object result = visitor.getResult();
        assertEquals("[11]",result);
        //
        // Two dim Array
        //
        sourceObject = new String[][] {
            {"[0,0]","[0,1]","[0,2]"},
            {"[1,0]","[1,1]","[1,2]"}};        
        
        Object newValue = new String[] {"[0.0,0]","[0.0,1]","[0.0,2]"};
        Object expResult = new String[] {"[0.0,0]","[0.0,1]","[0.0,2]"};

        visitor = new GetVisitor(doc);        
        visitor.setPaths("0");
        atype = new ComponentType(String[][].class);
        visitor.visitPutArray(atype, sourceObject, newValue);
        result = visitor.getResult();
        assertArrayEquals((String[])expResult,(String[])result);
        
        sourceObject = new ArrayList();
        ((List)sourceObject).add("elem1");
        ((List)sourceObject).add("elem2");
        newValue = "element2";
        visitor = new GetVisitor(doc);        
        visitor.setPaths("1");
        atype = new ArrayType(List.class);
        visitor.visitPutArray(atype, sourceObject, newValue);
        result = visitor.getResult();
        assertEquals("element2",result);
        
        
    }

    /**
     * Test of visitPutEmbedded method, of class GetVisitor.
     */
    @Test
    public void testVisitPutEmbedded() {
        System.out.println("DocumentVisitor: visitPutArray");
        DocumentSchema ds = DocUtils.createSchema(Person.class);
        SchemaType atype = new EmbeddedType(ds);
        Object sourceObject = new Person("Bill","Smith", new Date(), 1);
        Person person = new Person("Bill","Smith", new Date(), 1);
        
        Document doc = new ObjectDocument(person);
        GetVisitor visitor = new GetVisitor(doc);
        visitor.setPaths("lastName");
        visitor.visitPutEmbedded(atype, sourceObject, "Nelson");
        Object result = visitor.getResult();
        assertEquals("Nelson",result);
    }
    
}
