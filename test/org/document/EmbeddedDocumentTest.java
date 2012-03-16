/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.document;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.fail;
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
    public void testSomeMethod() {
        EmbeddedDocument<String> ed = new EmbeddedDocument<String>();
        ed.setData("Valery");
        //String s = ed1.getData();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
