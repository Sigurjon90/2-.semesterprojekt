/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.CalendarModule;

import Domain.User.User;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author morte
 */
public class CalendarTest {
    
    public CalendarTest() {
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
     * Test of deleteActivity method, of class Calendar.
     */
    @Test
    public void testDeleteActivity() {
        System.out.println("deleteActivity");
        Activity activity = null;
        Calendar instance = new Calendar();
        instance.deleteActivity(activity);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createActivity method, of class Calendar.
     */
    @Test
    public void testCreateActivity() {
        System.out.println("createActivity");
        String title = "";
        User creator = null;
        String place = "";
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;
        String description = "";
        String type = "";
        Boolean shared = null;
        Boolean entry = null;
        Calendar instance = new Calendar();
        instance.createActivity(title, creator, place, startDate, endDate, description, type, shared, entry);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCalendar method, of class Calendar.
     */
    @Test
    public void testGetCalendar() {
        System.out.println("getCalendar");
        Calendar instance = new Calendar();
        Map<Integer, Activity> expResult = null;
        Map<Integer, Activity> result = instance.getCalendar();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActivity method, of class Calendar.
     */
    @Test
    public void testGetActivity() {
        System.out.println("getActivity");
        int key = 0;
        Calendar instance = new Calendar();
        Activity expResult = null;
        Activity result = instance.getActivity(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getKeySet method, of class Calendar.
     */
    @Test
    public void testGetKeySet() {
        System.out.println("getKeySet");
        Calendar instance = new Calendar();
        Set<Integer> expResult = null;
        Set<Integer> result = instance.getKeySet();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
