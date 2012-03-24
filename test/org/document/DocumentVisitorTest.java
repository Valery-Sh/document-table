/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.Date;
import org.document.DocumentVisitor.VisitorInfo;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Valery
 */
public class DocumentVisitorTest {
    
    public DocumentVisitorTest() {
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
     * Test of split method, of class DocumentVisitor.
     */
    @Test
    public void testSplit() {
        System.out.println("split");
        String key = "";
        char dlm = ' ';
        DocumentVisitor instance = null;
        String[] expResult = null;
        //String[] result = document.split(key, dlm);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of visitDocument method, of class DocumentVisitor.
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
        
        
        DocumentVisitor instance = new DocumentVisitor("firstName");
        instance.visitDocument(document);
        assertEquals(1,instance.infoList.size());
        VisitorInfo vinfo = instance.infoList.get(instance.infoList.size()-1);
        assertEquals("Bill",vinfo.getResult());

        instance = new DocumentVisitor("order/orderNum");
        instance.visitDocument(document);
        assertEquals(125L,instance.getResult());
        
        
        //
        // fail when field doesn't exist
        //
        try {
            instance = new DocumentVisitor("frstName");
            instance.visitDocument(document);            
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
            
            DocumentVisitor instance1 = new DocumentVisitor("firstName/lastName");
            instance1.visitDocument(doc);            
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
            DocumentVisitor instance1 = new DocumentVisitor("firstName/lastName");
            instance1.visitDocument(doc);            
            if ( instance1.getException() != null ) {
                throw (IllegalArgumentException)instance1.getException();
            }
            fail("Path requires ValueType");
        } catch(IllegalArgumentException e) {
            System.out.println("fail when Path requires ValueType");            
        }

        
        expResult = 125L;
        instance = new DocumentVisitor("order/orderNum");
        instance.visitDocument(document);
        
        result = instance.getResult();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of continueVisit method, of class DocumentVisitor.
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
        
        
        DocumentVisitor instance = new DocumentVisitor("firstName");
        instance.visitDocument(document);
        assertEquals(1,instance.infoList.size());
        VisitorInfo vinfo = instance.infoList.get(instance.infoList.size()-1);
        assertEquals("Bill",vinfo.getResult());

        instance = new DocumentVisitor("order/orderNum");
        instance.visitDocument(document);
        assertEquals(125L,instance.getResult());

        instance = new DocumentVisitor("order");
        instance.visitDocument(document);
        assertEquals(exporder,instance.getResult());
        instance.continueVisit("orderNum");
        assertEquals(125L,instance.getResult());
        
        //
        // fail when field doesn't exist
        //
        try {
            instance = new DocumentVisitor("frstName");
            instance.visitDocument(document);            
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
            
            DocumentVisitor instance1 = new DocumentVisitor("firstName/lastName");
            instance1.visitDocument(doc);            
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
            DocumentVisitor instance1 = new DocumentVisitor("firstName/lastName");
            instance1.visitDocument(doc);            
            if ( instance1.getException() != null ) {
                throw (IllegalArgumentException)instance1.getException();
            }
            fail("Path requires ValueType");
        } catch(IllegalArgumentException e) {
            System.out.println("fail when Path requires ValueType");            
        }

        
        expResult = 125L;
        instance = new DocumentVisitor("order/orderNum");
        instance.visitDocument(document);
        
        result = instance.getResult();
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test of visitEmbedded method, of class DocumentVisitor.
     */
    @Test
    public void testVisitEmbedded() {
        System.out.println("DocumentVisitorTest: visitEmbedded");
/*        EmbeddedType embeddedType = null;
        Object sourceObject = null;
        DocumentVisitor document = null;
        document.visitEmbedded(embeddedType, sourceObject);
  */
    }

    /**
     * Test of visitArray method, of class DocumentVisitor.
     */
    @Test
    public void testVisitArray() {
        System.out.println("visitArray");
    /*    ArrayType arrayType = null;
        Object sourceObject = null;
        DocumentVisitor document = null;
        document.visitArray(arrayType, sourceObject);
*/    
    }

    /**
     * Test of visitComponent method, of class DocumentVisitor.
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
        DocumentVisitor instance = new DocumentVisitor("stringArray","0");
        instance.visitDocument(document);
        Object result = instance.getResult();
        assertEquals("str1",result);
        
        //
        // Two dimentional array of type String[][]
        //
        
        instance = new DocumentVisitor("stringStringArray","0");
        instance.visitDocument(document);
        result = instance.getResult();
        assertArrayEquals(expstringStringArray[0],(String[])result);        

        instance = new DocumentVisitor("stringStringArray","0","0");
        instance.visitDocument(document);
        result = instance.getResult();
        assertEquals("[0,0]",result);      
        
        
        instance = new DocumentVisitor("stringStringArray","1","1");
        instance.visitDocument(document);
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
}
