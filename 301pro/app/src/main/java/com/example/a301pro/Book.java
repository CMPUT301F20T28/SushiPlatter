package com.example.a301pro;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * This class initializes the property of a book as well as gets the data of a book
 */
public class Book implements Serializable {
    private String imageID;
    private String book_name;
    private String author;
    private String ISBN;
    private String description;
    private String status;
    private String book_id;
    private String borrower_name;
    private String owner;

    /**
     * This constructor initializes the property of a book
     * @param imageID image id of the book
     * @param book_name name of the book
     * @param author author of the book
     * @param ISBN ISBN code of the book
     * @param description description of the book
     * @param status status of the book
     * @param book_id id of the book
     * @param borrower_name borrower of the book
     * @param owner owner of the book
     */
    public Book(String imageID, String book_name, String author, String ISBN, String description, String status, String book_id, String borrower_name, String owner) {
        this.imageID = imageID;
        this.book_name = book_name;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.status = status;
        this.book_id = book_id;
        this.borrower_name = borrower_name;
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
        return book_name;
    }

    /**
     * This set the name of a book
     * @param book_name name of a book to be set
     */
    public void setBook_name(String book_name) {
        this.book_name = book_name;
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
        return book_id;
    }

    /**
     * This set the id of a book
     * @param book_id book id to be set
     */
    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    /**
     * This allows the outside to get the borrower's name of a book
     * @return borrower's name of a book
     */
    public String getBorrower_name() {
        return borrower_name;
    }

    /**
     * This set the borrower's name of a book
     * @param borrower_name borrower's name to be set
     */
    public void setBorrower_name(String borrower_name) {
        this.borrower_name = borrower_name;
    }

    /**
     * This allows the outside to get the owner's name of a book
     * @return owner's name of a book
     */
    public String getOwner() {
        return owner;
    }

}
