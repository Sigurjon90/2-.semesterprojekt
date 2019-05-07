/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.DiaryModule;

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mathi
 */
public class DiaryTest {
    
    public DiaryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of deleteEntry method, of class Diary.
     */
    @org.junit.Test
    public void testDeleteEntry() {
        System.out.println("deleteEntry");
        Entry entry = null;
        Diary instance = new Diary();
        instance.deleteEntry(entry);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMap method, of class Diary.
     */
    @org.junit.Test
    public void testGetMap() {
        System.out.println("getMap");
        Diary instance = new Diary();
        Map<Integer, Entry> expResult = null;
        Map<Integer, Entry> result = instance.getMap();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntry method, of class Diary.
     */
    @org.junit.Test
    public void testGetEntry() {
        System.out.println("getEntry");
        int key = 0;
        Diary instance = new Diary();
        Entry expResult = null;
        Entry result = instance.getEntry(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
