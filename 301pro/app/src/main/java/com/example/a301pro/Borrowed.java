package com.example.a301pro;

public class Borrowed {
    private int imageId;
    private String book_name;
    private String des;
    private String o_name;
    private String status;

    public Borrowed(int imageId,String book_name, String des, String status, String o_name) {
        this.imageId = imageId;
        this.book_name = book_name;
        this.des = des;
        this.status = status;
        this.o_name = o_name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getDes() {
        return des;
    }

    public String getO_name() {
        return o_name;
    }

    public String getStatus() {
        return status;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setO_name(String o_name) {
        this.o_name = o_name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }
}
