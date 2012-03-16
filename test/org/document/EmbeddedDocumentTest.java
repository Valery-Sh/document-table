/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import org.junit.*;

/**
 *
 * @author Valery
 */
public class EmbeddedDocumentTest {
    
    public EmbeddedDocumentTest() {
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

    @Test
    public void testCloneData() {
        EmbeddedDocument<String> ed = new EmbeddedDocument<String>();
        ed.setData("cloneData Test");
        
        //String s = (String)ed.cloneData(ed.getData());
        String s = (String)ed.cloneData();
        assertEquals("cloneData Test",s);

        ed.setData(null);
        assertNull(ed.getData());
        Person data = new Person("Bob","Smith", new Date(),0);
        EmbeddedDocument<Person> instance = new EmbeddedDocument<Person>();
        instance.setData(data);
        
        Person expResult = new Person("Bob","Smith", new Date(),0);
        //Person result = instance.cloneData(instance.getData());
        Person result = instance.cloneData();
        
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testNewDataInstance() {
        EmbeddedDocument<String> ed = new EmbeddedDocument<String>();
        ed.setData("cloneData Test");
        
        //String s = (String)ed.cloneData(ed.getData());
        String s = (String)ed.newDataInstance();
        //assertEquals("cloneData Test",s);

        ed.setData(null);
        s = (String)ed.newDataInstance();
        assertNull(ed.getData());
        
        Person data = new Person("Bob","Smith", new Date(),0);
        EmbeddedDocument<Person> instance = new EmbeddedDocument<Person>();
        instance.setData(data);
        
        Person expResult = new Person("Bill","Down",null,1);
        //Person result = instance.cloneData(instance.getData());
        Person result = instance.newDataInstance();
        
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
