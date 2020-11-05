package com.example.a301pro;

//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;



public class BookUnitTest {
//    private Book book;
//
//    @Before
//    public void setUp(){
//        book = new Book("123456","testBookName","testAuthorName","123123",
// "interesting","Available","123456","testBorrowerName","testOwner");}


    public Book MockBook() { return new Book("123456","testBookName","testAuthorName","123123",
            "interesting","Available","123456","testBorrowerName","testOwner");}


    @Test
    public void testGetImageID(){
        Book mockBook = MockBook();
        assertEquals("123456",mockBook.getImageID());
        assertNotEquals("1234567",mockBook.getImageID());
    }


    @Test
    public void testSetImageID(){
        Book mockBook = MockBook();
        assertEquals("123456",mockBook.getImageID());
        assertNotEquals("1234567",mockBook.getImageID());

        mockBook.setImageID("1234567");
        assertEquals("1234567",mockBook.getImageID());
        assertNotEquals("123456",mockBook.getImageID());
    }


    @Test
    public void testGetBookName(){
        Book mockBook = MockBook();
        assertEquals("testBookName",mockBook.getBook_name());
        assertNotEquals("testBookName1",mockBook.getBook_name());
    }


    @Test
    public void testSetBookName(){
        Book mockBook = MockBook();
        assertEquals("testBookName",mockBook.getBook_name());
        assertNotEquals("testBookName1",mockBook.getBook_name());

        mockBook.setBook_name("testBookName1");
        assertEquals("testBookName1",mockBook.getBook_name());
        assertNotEquals("testBookName",mockBook.getBook_name());
    }


    @Test
    public void testGetAuthorName(){
        Book mockBook = MockBook();
        assertEquals("testAuthorName",mockBook.getAuthor());
        assertNotEquals("testAuthorName1",mockBook.getAuthor());
    }


    @Test
    public void testSetAuthorName(){
        Book mockBook = MockBook();
        assertEquals("testAuthorName",mockBook.getAuthor());
        assertNotEquals("testAuthorName1",mockBook.getAuthor());

        mockBook.setAuthor("testAuthorName1");
        assertEquals("testAuthorName1",mockBook.getAuthor());
        assertNotEquals("testAuthorName",mockBook.getAuthor());
    }


    @Test
    public void testGetISBN(){
        Book mockBook = MockBook();
        assertEquals("123123",mockBook.getISBN());
        assertNotEquals("1231234",mockBook.getISBN());
    }


    @Test
    public void testSetISBN(){
        Book mockBook = MockBook();
        assertEquals("123123",mockBook.getISBN());
        assertNotEquals("1231234",mockBook.getISBN());

        mockBook.setISBN("1231234");
        assertEquals("1231234",mockBook.getISBN());
        assertNotEquals("123123",mockBook.getISBN());
    }


    @Test
    public void testGetDescription(){
        Book mockBook = MockBook();
        assertEquals("interesting",mockBook.getDescription());
        assertNotEquals("boring",mockBook.getDescription());
    }


    @Test
    public void testSetDescription(){
        Book mockBook = MockBook();
        assertEquals("interesting",mockBook.getDescription());
        assertNotEquals("boring",mockBook.getDescription());

        mockBook.setDescription("boring");
        assertEquals("boring",mockBook.getDescription());
        assertNotEquals("interesting",mockBook.getDescription());
    }


    @Test
    public void testGetStatus(){
        Book mockBook = MockBook();
        assertEquals("Available",mockBook.getStatus());
        assertNotEquals("Borrowed",mockBook.getStatus());
    }


    @Test
    public void testSetStatus(){
        Book mockBook = MockBook();
        assertEquals("Available",mockBook.getStatus());
        assertNotEquals("Borrowed",mockBook.getStatus());

        mockBook.setStatus("Borrowed");
        assertEquals("Borrowed",mockBook.getStatus());
        assertNotEquals("Available",mockBook.getStatus());
    }


    @Test
    public void testGetBookID(){
        Book mockBook = MockBook();
        assertEquals("123456",mockBook.getBook_id());
        assertNotEquals("1234567",mockBook.getBook_id());
    }


    @Test
    public void testSetBookID(){
        Book mockBook = MockBook();
        assertEquals("123456",mockBook.getBook_id());
        assertNotEquals("1234567",mockBook.getBook_id());

        mockBook.setBook_id("1234567");
        assertEquals("1234567",mockBook.getBook_id());
        assertNotEquals("123456",mockBook.getBook_id());
    }


    @Test
    public void testGetBorrowerName(){
        Book mockBook = MockBook();
        assertEquals("testBorrowerName",mockBook.getBorrower_name());
        assertNotEquals("testBorrowerName1",mockBook.getBorrower_name());
    }


    @Test
    public void testSetBorrowerName(){
        Book mockBook = MockBook();
        assertEquals("testBorrowerName",mockBook.getBorrower_name());
        assertNotEquals("testBorrowerName1",mockBook.getBorrower_name());

        mockBook.setBorrower_name("testBorrowerName1");
        assertEquals("testBorrowerName1",mockBook.getBorrower_name());
        assertNotEquals("testBorrowerName",mockBook.getBorrower_name());
    }


    @Test
    public void testGetOwner(){
        Book mockBook = MockBook();
        assertEquals("testOwner",mockBook.getOwner());
        assertNotEquals("testOwner1",mockBook.getOwner());
    }
}
