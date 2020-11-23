package com.example.a301pro;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

class BorrowedUnitTest {
    /**
     * share for testing purpose
     * @return Mock share
     */
    // String bookID,String imageId,String bookName, String des, String sit, String owner
    public Borrowed MockBorrowed() {

        return new Borrowed("123456","1234567","testBookName","interesting",
                "Available","testOwnerName");
 }

    /**
     * Test if it can get correct image id
     */
    @Test
    public void testGetImageID(){

        Borrowed mockBorrowed= MockBorrowed();
        assertEquals("1234567",mockBorrowed.getImageId());
        assertNotEquals("1234567",mockBorrowed.getImageId());
    }

    /**
     * Test if it can set an image id
     */
    @Test
    public void testSetImageID(){
        Borrowed mockBorrowed= MockBorrowed();
        assertEquals("1234567",mockBorrowed.getImageId());
        assertNotEquals("1234567",mockBorrowed.getImageId());

        mockBorrowed.setImageId("1234567");
        assertEquals("1234567",mockBorrowed.getImageId());
        assertNotEquals("123456",mockBorrowed.getImageId());
    }

    /**
     * Test if it can get correct book name
     */
    @Test
    public void testGetBookName(){
        Borrowed mockBorrowed= MockBorrowed();
        assertEquals("testBookName",mockBorrowed.getBook_name());
        assertNotEquals("testBookName1",mockBorrowed.getBook_name());
    }

    /**
     * Test if it can set a book name
     */
    @Test
    public void testSetBookName(){
        Borrowed mockBorrowed= MockBorrowed();
        assertEquals("testBookName",mockBorrowed.getBook_name());
        assertNotEquals("testBookName1",mockBorrowed.getBook_name());

        mockBorrowed.setBook_name("testBookName1");
        assertEquals("testBookName1",mockBorrowed.getBook_name());
        assertNotEquals("testBookName",mockBorrowed.getBook_name());
    }

    /**
     * Test if it can get correct author name
     */
    @Test
    public void testGetOwnerName(){
        Borrowed mockBorrowed= MockBorrowed();
        assertEquals("testOwnerName",mockBorrowed.getO_name());
        assertNotEquals("testOwnerName1",mockBorrowed.getO_name());
    }

    /**
     * Test if it can set an author name
     */
    @Test
    public void testSetOwnerName(){
        Borrowed mockBorrowed= MockBorrowed();
        assertEquals("testOwnerName",mockBorrowed.getO_name());
        assertNotEquals("testOwnerName1",mockBorrowed.getO_name());

        mockBorrowed.setOwner("testAuthorName1");
        assertEquals("testAuthorName1",mockBorrowed.getO_name());
        assertNotEquals("testAuthorName",mockBorrowed.getO_name());
    }



    /**
     * Test if it can get correct description
     */
    @Test
    public void testGetDescription(){
        Borrowed mockBorrowed= MockBorrowed();
        assertEquals("interesting",mockBorrowed.getDes());
        assertNotEquals("boring",mockBorrowed.getDes());
    }

    /**
     * Test if it can set a description
     */
    @Test
    public void testSetDescription(){
        Borrowed mockBorrowed= MockBorrowed();
        assertEquals("interesting",mockBorrowed.getDes());
        assertNotEquals("boring",mockBorrowed.getDes());

        mockBorrowed.setDes("boring");
        assertEquals("boring",mockBorrowed.getDes());
        assertNotEquals("interesting",mockBorrowed.getDes());
    }

    /**
     * Test if it can get correct status
     */
    @Test
    public void testGetStatus(){
        Borrowed mockBorrowed= MockBorrowed();
        assertEquals("Available",mockBorrowed.getStatus());
        assertNotEquals("Borrowed",mockBorrowed.getStatus());
    }

    /**
     * Test if it can set a status
     */
    @Test
    public void testSetStatus(){
        Borrowed mockBorrowed= MockBorrowed();
        assertEquals("Available",mockBorrowed.getStatus());
        assertNotEquals("Borrowed",mockBorrowed.getStatus());

        mockBorrowed.setStatus("Borrowed");
        assertEquals("Borrowed",mockBorrowed.getStatus());
        assertNotEquals("Available",mockBorrowed.getStatus());
    }

    /**
     * Test if it can get correct book id
     */
    @Test
    public void testGetBookID(){
        Borrowed mockBorrowed= MockBorrowed();
        assertEquals("123456",mockBorrowed.getBookID());
        assertNotEquals("1234567",mockBorrowed.getBookID());
    }

    /**
     * Test if it can set a book id
     */
    @Test
    public void testSetBookID(){
        Borrowed mockBorrowed= MockBorrowed();
        assertEquals("123456",mockBorrowed.getBookID());
        assertNotEquals("1234567",mockBorrowed.getBookID());

        mockBorrowed.setBookID("1234567");
        assertEquals("1234567",mockBorrowed.getBookID());
        assertNotEquals("123456",mockBorrowed.getBookID());
    }

}
