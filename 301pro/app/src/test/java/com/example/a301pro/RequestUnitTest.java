package com.example.a301pro;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

class RequestUnitTest {
    /**
     * share for testing purpose
     * @return Mock share
     */
    // String bookID,String imageId,String bookName, String des, String sit, String owner
    public Request MockRequest() { return new Request("123456","1234567","testBookName","interesting",
            "Available","testOwnerName");}

    /**
     * Test if it can get correct image id
     */
    @Test
    public void testGetImageID(){
        Request mockRequest= MockRequest();
        assertEquals("1234567",mockRequest.getImageId());
        assertNotEquals("1234567",mockRequest.getImageId());
    }

    /**
     * Test if it can set an image id
     */
    @Test
    public void testSetImageID(){
        Request mockRequest= MockRequest();
        assertEquals("1234567",mockRequest.getImageId());
        assertNotEquals("1234567",mockRequest.getImageId());

        mockRequest.setImageId("1234567");
        assertEquals("1234567",mockRequest.getImageId());
        assertNotEquals("123456",mockRequest.getImageId());
    }

    /**
     * Test if it can get correct book name
     */
    @Test
    public void testGetBookName(){
        Request mockRequest= MockRequest();
        assertEquals("testBookName",mockRequest.getBook_name());
        assertNotEquals("testBookName1",mockRequest.getBook_name());
    }

    /**
     * Test if it can set a book name
     */
    @Test
    public void testSetBookName(){
        Request mockRequest= MockRequest();
        assertEquals("testBookName",mockRequest.getBook_name());
        assertNotEquals("testBookName1",mockRequest.getBook_name());

        mockRequest.setBook_name("testBookName1");
        assertEquals("testBookName1",mockRequest.getBook_name());
        assertNotEquals("testBookName",mockRequest.getBook_name());
    }

    /**
     * Test if it can get correct author name
     */
    @Test
    public void testGetRequestForm(){
        Request mockRequest= MockRequest();
        assertEquals("testOwnerName",mockRequest.getRequestFrom());
        assertNotEquals("testOwnerName1",mockRequest.getRequestFrom());
    }

    /**
     * Test if it can set an author name
     */
    @Test
    public void testSetRequestForm(){
        Request mockRequest= MockRequest();
        assertEquals("testOwnerName",mockRequest.getRequestFrom());
        assertNotEquals("testOwnerName1",mockRequest.getRequestFrom());

        mockRequest.setRequestFrom("testAuthorName1");
        assertEquals("testAuthorName1",mockRequest.getRequestFrom());
        assertNotEquals("testAuthorName",mockRequest.getRequestFrom());
    }



    /**
     * Test if it can get correct description
     */
    @Test
    public void testGetDescription(){
        Request mockRequest= MockRequest();
        assertEquals("interesting",mockRequest.getDes());
        assertNotEquals("boring",mockRequest.getDes());
    }

    /**
     * Test if it can set a description
     */
    @Test
    public void testSetDescription(){
        Request mockRequest= MockRequest();
        assertEquals("interesting",mockRequest.getDes());
        assertNotEquals("boring",mockRequest.getDes());

        mockRequest.setDes("boring");
        assertEquals("boring",mockRequest.getDes());
        assertNotEquals("interesting",mockRequest.getDes());
    }

    /**
     * Test if it can get correct status
     */
    @Test
    public void testGetStatus(){
        Request mockRequest= MockRequest();
        assertEquals("Available",mockRequest.getStatus());
        assertNotEquals("Borrowed",mockRequest.getStatus());
    }

    /**
     * Test if it can set a status
     */
    @Test
    public void testSetStatus(){
        Request mockRequest= MockRequest();
        assertEquals("Available",mockRequest.getStatus());
        assertNotEquals("Borrowed",mockRequest.getStatus());

        mockRequest.setStatus("Borrowed");
        assertEquals("Borrowed",mockRequest.getStatus());
        assertNotEquals("Available",mockRequest.getStatus());
    }

    /**
     * Test if it can get correct book id
     */
    @Test
    public void testGetBookID(){
        Request mockRequest= MockRequest();
        assertEquals("123456",mockRequest.getBookID());
        assertNotEquals("1234567",mockRequest.getBookID());
    }

    /**
     * Test if it can set a book id
     */
    @Test
    public void testSetBookID(){
        Request mockRequest= MockRequest();
        assertEquals("123456",mockRequest.getBookID());
        assertNotEquals("1234567",mockRequest.getBookID());

        mockRequest.setBookID("1234567");
        assertEquals("1234567",mockRequest.getBookID());
        assertNotEquals("123456",mockRequest.getBookID());
    }

}
