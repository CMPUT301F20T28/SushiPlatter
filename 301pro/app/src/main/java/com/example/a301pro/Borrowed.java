package com.example.a301pro;

public class Borrowed {
    private String bookID;
    private String imageId;
    private String book_name;
    private String des;
    private String o_name;
    private String status;
    /**
     * This constructor initializes the property of a Share
     * @param imageId image id of the book
     * @param book_name name of the book
     * @param des description of the book
     * @param status status of the book
     * @param bookID id of the book
     * @param o_name owner of the book
     */
    public Borrowed(String bookID,String imageId,String book_name, String des, String status, String o_name) {
        this.bookID = bookID;
        this.imageId = imageId;
        this.book_name = book_name;
        this.des = des;
        this.status = status;
        this.o_name = o_name;
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
        return book_name;
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
    public String getO_name() {
        return o_name;
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
     * @param o_name description to be set
     */
    public void setO_name(String o_name) {
        this.o_name = o_name;
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
     * @param book_name name of a book to be set
     */
    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }
}
