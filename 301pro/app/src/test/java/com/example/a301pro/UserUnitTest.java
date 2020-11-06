package com.example.a301pro;

import static org.junit.Assert.*;
import org.junit.Test;

public class UserUnitTest {

    public User MockUser(){ return new User("testUserName","testUserEmail","123456",
            "testFirstName","testLastName","123456","testUID");
    }


    @Test
    public void testUserName(){
        User mockUser = MockUser();
        assertEquals("testUserName",mockUser.getUserName());
        assertNotEquals("testUserName1",mockUser.getUserName());

        mockUser.setUserName("testUserName1");
        assertEquals("testUserName1",mockUser.getUserName());
        assertNotEquals("testUserName",mockUser.getUserName());
    }


    @Test
    public void testUserEmail(){
        User mockUser = MockUser();
        assertEquals("testUserEmail",mockUser.getEmail());
        assertNotEquals("testUserEmail1",mockUser.getEmail());

        mockUser.setEmail("testUserEmail1");
        assertEquals("testUserEmail1",mockUser.getEmail());
        assertNotEquals("testUserEmail",mockUser.getEmail());
    }


    @Test
    public void testPassword(){
        User mockUser = MockUser();
        assertEquals("123456",mockUser.getPassword());
        assertNotEquals("1234567",mockUser.getPassword());

        assertTrue(mockUser.getPassword().length()>=6);
        mockUser.setPassword("1234567");
        assertEquals("1234567",mockUser.getPassword());
        assertNotEquals("123456",mockUser.getPassword());


        mockUser.setPassword("1234");
        assertFalse(mockUser.getPassword().length()>=6);
    }


    @Test
    public void testName(){
        User mockUser = MockUser();
        assertEquals("testLastName",mockUser.getLastName());
        assertEquals("testFirstName",mockUser.getFirstName());

        mockUser.setLastName("changedLastName");
        mockUser.setFirstName("changedFirstName");

        assertEquals("changedLastName",mockUser.getLastName());
        assertEquals("changedFirstName",mockUser.getFirstName());

        assertNotEquals("testLastName",mockUser.getLastName());
        assertNotEquals("testFirstName",mockUser.getFirstName());

    }


    @Test
    public void testPhoneNum(){
        User mockUser = MockUser();
        assertEquals("123456",mockUser.getPhoneNumber());
        assertNotEquals("1234567",mockUser.getPhoneNumber());

        mockUser.setPhoneNumber("1234567");
        assertEquals("1234567",mockUser.getPhoneNumber());
        assertNotEquals("123456",mockUser.getPhoneNumber());

    }


    @Test
    public void testUID(){
        User mockUser = MockUser();
        assertEquals("testUID",mockUser.getUID());
        assertNotEquals("changedUID",mockUser.getUID());
    }

}
