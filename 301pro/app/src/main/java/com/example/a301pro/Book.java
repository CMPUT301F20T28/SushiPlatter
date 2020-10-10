package com.example.a301pro;

import android.widget.ImageView;

public class Book {
    private int imageID;
    private String book_name;
    private String author;
    private String ISBN;
    private String description;
    private String status;
    private String Borrower_name;

    public Book(int imageID, String book_name, String author, String ISBN, String description, String status, String borrower_name) {
        this.imageID = imageID;
        this.book_name = book_name;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.status = status;
        Borrower_name = borrower_name;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getBorrower_name() {
        return Borrower_name;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBorrower_name(String borrower_name) {
        Borrower_name = borrower_name;
    }
}
