package org.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.*;


/**
 *
 * @author V. Shyshkin
 */
public class PutVisitorTest {
    
    public PutVisitorTest() {
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
     * Test of visitDocument method, of class PutVisitor.
     */
    @Test
    public void testVisitDocument() {
        System.out.println("visitDocument");
/*        String key = "";
        Object value = null;
        PutVisitor instance = null;
        instance.visitDocument(key, value);
*/    }

    /**
     * Test of visitPut method, of class PutVisitor.
     */
    @Test
    public void testVisitPut() {
        System.out.println("visitPut");
/*        SchemaType schemaType = null;
        Object sourceObject = null;
        Object newValue = null;
        PutVisitor instance = null;
        instance.visitPut(schemaType, sourceObject, newValue);
  */
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
        PutVisitor visitor = new PutVisitor(doc);
        visitor.setPaths("1");
        visitor.visitArray(atype, sourceObject, "[11]");
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

        visitor = new PutVisitor(doc);        
        visitor.setPaths("0");
        atype = new ComponentType(String[][].class);
        visitor.visitArray(atype, sourceObject, newValue);
        result = visitor.getResult();
        assertArrayEquals((String[])expResult,(String[])result);
        
        sourceObject = new ArrayList();
        ((List)sourceObject).add("elem1");
        ((List)sourceObject).add("elem2");
        newValue = "element2";
        visitor = new PutVisitor(doc);        
        visitor.setPaths("1");
        atype = new ArrayType(List.class);
        visitor.visitArray(atype, sourceObject, newValue);
        result = visitor.getResult();
        assertEquals("element2",result);
        
        
    }

    /**
     * Test of visitEmbedded method, of class PutVisitor.
     */

    public void testVisitEmbedded() {
        System.out.println("PutVisitor: visitEmbedded");
        DocumentSchema ds = DocUtils.createSchema(Person.class);
        SchemaType atype = new EmbeddedType(ds);
        Object sourceObject = new Person("Bill","Smith", new Date(), 1);
        Person person = new Person("Bill","Smith", new Date(), 1);
        
        Document doc = new ObjectDocument(person);
        PutVisitor visitor = new PutVisitor(doc);
        visitor.setPaths("lastName");
        visitor.visitEmbedded(atype, sourceObject, "Nelson");
        Object result = visitor.getResult();
        assertEquals("Nelson",result);
    }


    /**
     * Test of getInfo method, of class PutVisitor.
     */
    @Test
    public void testGetInfo_int() {
        System.out.println("getInfo");
/*        int idx = 0;
        PutVisitor instance = null;
        VisitorInfo expResult = null;
        VisitorInfo result = instance.getInfo(idx);
        assertEquals(expResult, result);
*/
    }

    /**
     * Test of getInfo method, of class PutVisitor.
     */
    @Test
    public void testGetInfo_0args() {
        System.out.println("getInfo");
/*        PutVisitor instance = null;
        VisitorInfo expResult = null;
        VisitorInfo result = instance.getInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
 */
    }

    /**
     * Test of getResult method, of class PutVisitor.
     */
    @Test
    public void testGetResult() {
        System.out.println("getResult");
/*        PutVisitor instance = null;
        Object expResult = null;
        Object result = instance.getResult();
        assertEquals(expResult, result);
*/
    }

    /**
     * Test of getException method, of class PutVisitor.
     */
    @Test
    public void testGetException() {
        System.out.println("getException");
/*        PutVisitor instance = null;
        Exception expResult = null;
        Exception result = instance.getException();
        assertEquals(exp
        * Result, result);
*/
    }
}
