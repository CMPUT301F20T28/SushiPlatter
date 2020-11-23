package com.example.a301pro;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * This class initializes the property of a book as well as gets the data of a book
 */
public class Book implements Serializable {
    private String imageID;
    private String bookName;
    private String author;
    private String ISBN;
    private String description;
    private String status;
    private String bookId;
    private String borrowerName;
    private String owner;

    /**
     * This constructor initializes the property of a book
     * @param imageID image id of the book
     * @param bookName name of the book
     * @param author author of the book
     * @param ISBN ISBN code of the book
     * @param description description of the book
     * @param status status of the book
     * @param bookId id of the book
     * @param borrowerName borrower of the book
     * @param owner owner of the book
     */
    public Book(String imageID, String bookName, String author, String ISBN, String description, String status, String bookId, String borrowerName, String owner) {
        this.imageID = imageID;
        this.bookName = bookName;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.status = status;
        this.bookId = bookId;
        this.borrowerName = borrowerName;
        this.owner = owner;
    }

    /**
     * This allows the outside to get the image id of a book
     * @return image id of a book
     */
    public String getImageID() {
        return imageID;
    }

    /**
     * This set the image id of a book
     * @param imageID image id to be set
     */
    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    /**
     * This allows the outside to get the name of a book
     * @return name of a book
     */
    public String getBook_name() {
        return bookName;
    }

    /**
     * This set the name of a book
     * @param bookName name of a book to be set
     */
    public void setBook_name(String bookName) {
        this.bookName = bookName;
    }

    /**
     * This allows the outside to get the author's name of a book
     * @return author's name of a book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * This set the author's name of a book
     * @param author name of the author to be set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * This allows the outside to get the ISBN code of a book
     * @return ISBN code of a book
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * This set the ISBN code of a book
     * @param ISBN ISBN code to be set
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * This allows the outside to get the description of a book
     * @return description of a book
     */
    public String getDescription() {
        return description;
    }

    /**
     * This set the description of a book
     * @param description description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This allows the outside to get the status of a book
     * @return status of a book
     */
    public String getStatus() {
        return status;
    }

    /**
     * This set the status of a book
     * @param status status to be set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This allows the outside to get the id of a book
     * @return id of a book
     */
    public String getBook_id() {
        return bookId;
    }

    /**
     * This set the id of a book
     * @param bookId book id to be set
     */
    public void setBook_id(String bookId) {
        this.bookId = bookId;
    }

    /**
     * This allows the outside to get the borrower's name of a book
     * @return borrower's name of a book
     */
    public String getBorrower_name() {
        return borrowerName;
    }

    /**
     * This set the borrower's name of a book
     * @param borrowerName borrower's name to be set
     */
    public void setBorrower_name(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    /**
     * This allows the outside to get the owner's name of a book
     * @return owner's name of a book
     */
    public String getOwner() {
        return owner;
    }

}
