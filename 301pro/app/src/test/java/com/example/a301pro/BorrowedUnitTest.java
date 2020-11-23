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
    public Share MockShare() { return new Share("123456","1234567","testBookName","interesting",
            "Available","testOwnerName");}

    /**
     * Test if it can get correct image id
     */
    @Test
    public void testGetImageID(){
        Share mockShare= MockShare();
        assertEquals("1234567",mockShare.getImageId());
        assertNotEquals("1234567",mockShare.getImageId());
    }

    /**
     * Test if it can set an image id
     */
    @Test
    public void testSetImageID(){
        Share mockShare= MockShare();
        assertEquals("1234567",mockShare.getImageId());
        assertNotEquals("1234567",mockShare.getImageId());

        mockShare.setImageId("1234567");
        assertEquals("1234567",mockShare.getImageId());
        assertNotEquals("123456",mockShare.getImageId());
    }

    /**
     * Test if it can get correct book name
     */
    @Test
    public void testGetBookName(){
        Share mockBook = MockShare();
        assertEquals("testBookName",mockBook.getBook_name());
        assertNotEquals("testBookName1",mockBook.getBook_name());
    }

    /**
     * Test if it can set a book name
     */
    @Test
    public void testSetBookName(){
        Share mockBook = MockShare();
        assertEquals("testBookName",mockBook.getBook_name());
        assertNotEquals("testBookName1",mockBook.getBook_name());

        mockBook.setBook_name("testBookName1");
        assertEquals("testBookName1",mockBook.getBook_name());
        assertNotEquals("testBookName",mockBook.getBook_name());
    }

    /**
     * Test if it can get correct author name
     */
    @Test
    public void testGetOwnerName(){
        Share mockBook = MockShare();
        assertEquals("testOwnerName",mockBook.getOwner());
        assertNotEquals("testOwnerName1",mockBook.getOwner());
    }

    /**
     * Test if it can set an author name
     */
    @Test
    public void testSetOwnerName(){
        Share mockBook = MockShare();
        assertEquals("testOwnerName",mockBook.getOwner());
        assertNotEquals("testOwnerName1",mockBook.getOwner());

        mockBook.setOwner("testAuthorName1");
        assertEquals("testAuthorName1",mockBook.getOwner());
        assertNotEquals("testAuthorName",mockBook.getOwner());
    }



    /**
     * Test if it can get correct description
     */
    @Test
    public void testGetDescription(){
        Share mockBook = MockShare();
        assertEquals("interesting",mockBook.getDes());
        assertNotEquals("boring",mockBook.getDes());
    }

    /**
     * Test if it can set a description
     */
    @Test
    public void testSetDescription(){
        Share mockBook = MockShare();
        assertEquals("interesting",mockBook.getDes());
        assertNotEquals("boring",mockBook.getDes());

        mockBook.setDes("boring");
        assertEquals("boring",mockBook.getDes());
        assertNotEquals("interesting",mockBook.getDes());
    }

    /**
     * Test if it can get correct status
     */
    @Test
    public void testGetStatus(){
        Share mockBook = MockShare();
        assertEquals("Available",mockBook.getSit());
        assertNotEquals("Borrowed",mockBook.getSit());
    }

    /**
     * Test if it can set a status
     */
    @Test
    public void testSetStatus(){
        Share mockBook = MockShare();
        assertEquals("Available",mockBook.getSit());
        assertNotEquals("Borrowed",mockBook.getSit());

        mockBook.setSit("Borrowed");
        assertEquals("Borrowed",mockBook.getSit());
        assertNotEquals("Available",mockBook.getSit());
    }

    /**
     * Test if it can get correct book id
     */
    @Test
    public void testGetBookID(){
        Share mockBook = MockShare();
        assertEquals("123456",mockBook.getBookID());
        assertNotEquals("1234567",mockBook.getBookID());
    }

    /**
     * Test if it can set a book id
     */
    @Test
    public void testSetBookID(){
        Share mockBook = MockShare();
        assertEquals("123456",mockBook.getBookID());
        assertNotEquals("1234567",mockBook.getBookID());

        mockBook.setBookID("1234567");
        assertEquals("1234567",mockBook.getBookID());
        assertNotEquals("123456",mockBook.getBookID());
    }

}
