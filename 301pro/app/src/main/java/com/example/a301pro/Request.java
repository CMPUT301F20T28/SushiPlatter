package com.example.a301pro;

/**
 * This class initializes the property of a borrowed book as well as gets the data of a book
 */
public class Request {
    private String bookID;
    private String imageId;
    private String bookName;
    private String ISBN;
    private String des;
    private String requestFrom;
    private String status;
    /**
     * This constructor initializes the property of a borrowed book
     * @param imageId image id of the book
     * @param bookName name of the book
     * @param des description of the book
     * @param status status of the book
     * @param bookID id of the book
     * @param requestFrom name of the request sender
     */
    public Request(String bookID,String imageId,String ISBN,String bookName, String des,
                   String status, String requestFrom) {
        this.bookID = bookID;
        this.imageId = imageId;
        this.ISBN = ISBN;
        this.bookName = bookName;
        this.des = des;
        this.status = status;
        this.requestFrom = requestFrom;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * This allows the outside to get the owner's name of a book
     * @return ID of a book
     */
    public String getBookID() {
        return bookID;
    }
    /**
     * This set the image id of a book
     * @param bookID bookid to be set
     */
    public void setBookID(String bookID) {
        this.bookID = bookID;
    }
    /**
     * This allows the outside to get the image id of a book
     * @return image id of a book
     */
    public String getImageId() {
        return imageId;
    }
    /**
     * This set the image id of a book
     * @param imageId image id to be set
     */
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    /**
     * This allows the outside to get the image id of a book
     * @return image id of a book
     */
    public String getBook_name() {
        return bookName;
    }
    /**
     * This allows the outside to get the description of a book
     * @return description of a book
     */
    public String getDes() {
        return des;
    }
    /**
     * This allows the outside to get the owner's name of a book
     * @return owner's name of a book
     */
    public String getRequestFrom() {
        return requestFrom;
    }
    /**
     * This allows the outside to get the status of a book
     * @return status of a book
     */
    public String getStatus() {
        return status;
    }
    /**
     * This set the description of a book
     * @param des description to be set
     */
    public void setDes(String des) {
        this.des = des;
    }
    /**
     * This set the description of a book
     * @param requestFrom description to be set
     */
    public void setRequestFrom(String requestFrom) {
        this.requestFrom = requestFrom;
    }

    /**
     * This set the status of a book
     * @param status status to be set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * This set the name of a book
     * @param bookName name of a book to be set
     */
    public void setBook_name(String bookName) {
        this.bookName = bookName;
    }
}
